/*     */ package org.apache.commons.net.ftp;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.Inet6Address;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.net.util.Base64;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FTPHTTPClient
/*     */   extends FTPClient
/*     */ {
/*     */   private final String proxyHost;
/*     */   private final int proxyPort;
/*     */   private final String proxyUsername;
/*     */   private final String proxyPassword;
/*  45 */   private static final byte[] CRLF = { 13, 10 };
/*  46 */   private final Base64 base64 = new Base64();
/*     */   private String tunnelHost;
/*     */   
/*     */   public FTPHTTPClient(String proxyHost, int proxyPort, String proxyUser, String proxyPass)
/*     */   {
/*  51 */     this.proxyHost = proxyHost;
/*  52 */     this.proxyPort = proxyPort;
/*  53 */     this.proxyUsername = proxyUser;
/*  54 */     this.proxyPassword = proxyPass;
/*  55 */     this.tunnelHost = null;
/*     */   }
/*     */   
/*     */   public FTPHTTPClient(String proxyHost, int proxyPort) {
/*  59 */     this(proxyHost, proxyPort, null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected Socket _openDataConnection_(int command, String arg)
/*     */     throws IOException
/*     */   {
/*  75 */     return super._openDataConnection_(command, arg);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Socket _openDataConnection_(String command, String arg)
/*     */     throws IOException
/*     */   {
/*  88 */     if (getDataConnectionMode() != 2) {
/*  89 */       throw new IllegalStateException("Only passive connection mode supported");
/*     */     }
/*     */     
/*  92 */     boolean isInet6Address = getRemoteAddress() instanceof Inet6Address;
/*  93 */     String passiveHost = null;
/*     */     
/*  95 */     boolean attemptEPSV = (isUseEPSVwithIPv4()) || (isInet6Address);
/*  96 */     if ((attemptEPSV) && (epsv() == 229)) {
/*  97 */       _parseExtendedPassiveModeReply((String)this._replyLines.get(0));
/*  98 */       passiveHost = this.tunnelHost;
/*     */     } else {
/* 100 */       if (isInet6Address) {
/* 101 */         return null;
/*     */       }
/*     */       
/* 104 */       if (pasv() != 227) {
/* 105 */         return null;
/*     */       }
/* 107 */       _parsePassiveModeReply((String)this._replyLines.get(0));
/* 108 */       passiveHost = getPassiveHost();
/*     */     }
/*     */     
/* 111 */     Socket socket = new Socket(this.proxyHost, this.proxyPort);
/* 112 */     InputStream is = socket.getInputStream();
/* 113 */     OutputStream os = socket.getOutputStream();
/* 114 */     tunnelHandshake(passiveHost, getPassivePort(), is, os);
/* 115 */     if ((getRestartOffset() > 0L) && (!restart(getRestartOffset()))) {
/* 116 */       socket.close();
/* 117 */       return null;
/*     */     }
/*     */     
/* 120 */     if (!FTPReply.isPositivePreliminary(sendCommand(command, arg))) {
/* 121 */       socket.close();
/* 122 */       return null;
/*     */     }
/*     */     
/* 125 */     return socket;
/*     */   }
/*     */   
/*     */   public void connect(String host, int port)
/*     */     throws SocketException, IOException
/*     */   {
/* 131 */     this._socket_ = new Socket(this.proxyHost, this.proxyPort);
/* 132 */     this._input_ = this._socket_.getInputStream();
/* 133 */     this._output_ = this._socket_.getOutputStream();
/*     */     try {
/* 135 */       tunnelHandshake(host, port, this._input_, this._output_);
/*     */     }
/*     */     catch (Exception e) {
/* 138 */       IOException ioe = new IOException("Could not connect to " + host + " using port " + port);
/* 139 */       ioe.initCause(e);
/* 140 */       throw ioe;
/*     */     }
/* 142 */     super._connectAction_();
/*     */   }
/*     */   
/*     */   private void tunnelHandshake(String host, int port, InputStream input, OutputStream output) throws IOException, UnsupportedEncodingException
/*     */   {
/* 147 */     String connectString = "CONNECT " + host + ":" + port + " HTTP/1.1";
/* 148 */     String hostString = "Host: " + host + ":" + port;
/*     */     
/* 150 */     this.tunnelHost = host;
/* 151 */     output.write(connectString.getBytes("UTF-8"));
/* 152 */     output.write(CRLF);
/* 153 */     output.write(hostString.getBytes("UTF-8"));
/* 154 */     output.write(CRLF);
/*     */     
/* 156 */     if ((this.proxyUsername != null) && (this.proxyPassword != null)) {
/* 157 */       String auth = this.proxyUsername + ":" + this.proxyPassword;
/* 158 */       String header = "Proxy-Authorization: Basic " + 
/* 159 */         this.base64.encodeToString(auth.getBytes("UTF-8"));
/* 160 */       output.write(header.getBytes("UTF-8"));
/*     */     }
/* 162 */     output.write(CRLF);
/*     */     
/* 164 */     List<String> response = new ArrayList();
/* 165 */     BufferedReader reader = new BufferedReader(
/* 166 */       new InputStreamReader(input, getCharsetName()));
/*     */     
/* 168 */     for (String line = reader.readLine(); (line != null) && (
/* 169 */           line.length() > 0); line = reader.readLine()) {
/* 170 */       response.add(line);
/*     */     }
/*     */     
/* 173 */     int size = response.size();
/* 174 */     if (size == 0) {
/* 175 */       throw new IOException("No response from proxy");
/*     */     }
/*     */     
/* 178 */     String code = null;
/* 179 */     String resp = (String)response.get(0);
/* 180 */     if ((resp.startsWith("HTTP/")) && (resp.length() >= 12)) {
/* 181 */       code = resp.substring(9, 12);
/*     */     } else {
/* 183 */       throw new IOException("Invalid response from proxy: " + resp);
/*     */     }
/*     */     
/* 186 */     if (!"200".equals(code)) {
/* 187 */       StringBuilder msg = new StringBuilder();
/* 188 */       msg.append("HTTPTunnelConnector: connection failed\r\n");
/* 189 */       msg.append("Response received from the proxy:\r\n");
/* 190 */       for (String line : response) {
/* 191 */         msg.append(line);
/* 192 */         msg.append("\r\n");
/*     */       }
/* 194 */       throw new IOException(msg.toString());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\FTPHTTPClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */