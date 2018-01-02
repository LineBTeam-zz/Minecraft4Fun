/*     */ package org.apache.commons.net.ftp;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStreamWriter;
/*     */ import java.net.InetAddress;
/*     */ import java.net.Socket;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSocket;
/*     */ import javax.net.ssl.SSLSocketFactory;
/*     */ import javax.net.ssl.TrustManager;
/*     */ import org.apache.commons.net.util.Base64;
/*     */ import org.apache.commons.net.util.SSLContextUtils;
/*     */ import org.apache.commons.net.util.TrustManagerUtils;
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
/*     */ public class FTPSClient
/*     */   extends FTPClient
/*     */ {
/*     */   public static final int DEFAULT_FTPS_DATA_PORT = 989;
/*     */   public static final int DEFAULT_FTPS_PORT = 990;
/*  57 */   private static final String[] PROT_COMMAND_VALUE = { "C", "E", "S", "P" };
/*     */   
/*     */ 
/*     */   private static final String DEFAULT_PROT = "C";
/*     */   
/*     */ 
/*     */   private static final String DEFAULT_PROTOCOL = "TLS";
/*     */   
/*     */   private static final String CMD_AUTH = "AUTH";
/*     */   
/*     */   private static final String CMD_ADAT = "ADAT";
/*     */   
/*     */   private static final String CMD_PROT = "PROT";
/*     */   
/*     */   private static final String CMD_PBSZ = "PBSZ";
/*     */   
/*     */   private static final String CMD_MIC = "MIC";
/*     */   
/*     */   private static final String CMD_CONF = "CONF";
/*     */   
/*     */   private static final String CMD_ENC = "ENC";
/*     */   
/*     */   private static final String CMD_CCC = "CCC";
/*     */   
/*     */   private final boolean isImplicit;
/*     */   
/*     */   private final String protocol;
/*     */   
/*  85 */   private String auth = "TLS";
/*     */   
/*     */   private SSLContext context;
/*     */   
/*     */   private Socket plainSocket;
/*     */   
/*  91 */   private boolean isCreation = true;
/*     */   
/*  93 */   private boolean isClientMode = true;
/*     */   
/*  95 */   private boolean isNeedClientAuth = false;
/*     */   
/*  97 */   private boolean isWantClientAuth = false;
/*     */   
/*  99 */   private String[] suites = null;
/*     */   
/* 101 */   private String[] protocols = null;
/*     */   
/*     */ 
/* 104 */   private TrustManager trustManager = TrustManagerUtils.getValidateServerCertificateTrustManager();
/*     */   
/*     */ 
/* 107 */   private KeyManager keyManager = null;
/*     */   @Deprecated
/*     */   public static String KEYSTORE_ALGORITHM;
/*     */   @Deprecated
/*     */   public static String TRUSTSTORE_ALGORITHM;
/*     */   
/*     */   public FTPSClient()
/*     */   {
/* 115 */     this("TLS", false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FTPSClient(boolean isImplicit)
/*     */   {
/* 124 */     this("TLS", isImplicit);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FTPSClient(String protocol)
/*     */   {
/* 133 */     this(protocol, false);
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
/*     */   public FTPSClient(String protocol, boolean isImplicit)
/*     */   {
/* 146 */     this.protocol = protocol;
/* 147 */     this.isImplicit = isImplicit;
/* 148 */     if (isImplicit) {
/* 149 */       setDefaultPort(990);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FTPSClient(boolean isImplicit, SSLContext context)
/*     */   {
/* 160 */     this("TLS", isImplicit);
/* 161 */     this.context = context;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FTPSClient(SSLContext context)
/*     */   {
/* 171 */     this(false, context);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setAuthValue(String auth)
/*     */   {
/* 181 */     this.auth = auth;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getAuthValue()
/*     */   {
/* 189 */     return this.auth;
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
/*     */   protected void _connectAction_()
/*     */     throws IOException
/*     */   {
/* 204 */     if (this.isImplicit) {
/* 205 */       sslNegotiation();
/*     */     }
/* 207 */     super._connectAction_();
/*     */     
/* 209 */     if (!this.isImplicit) {
/* 210 */       execAUTH();
/* 211 */       sslNegotiation();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void execAUTH()
/*     */     throws SSLException, IOException
/*     */   {
/* 222 */     int replyCode = sendCommand("AUTH", this.auth);
/* 223 */     if (334 != replyCode)
/*     */     {
/*     */ 
/* 226 */       if (234 != replyCode) {
/* 227 */         throw new SSLException(getReplyString());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void initSslContext()
/*     */     throws IOException
/*     */   {
/* 236 */     if (this.context == null) {
/* 237 */       this.context = SSLContextUtils.createSSLContext(this.protocol, getKeyManager(), getTrustManager());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void sslNegotiation()
/*     */     throws IOException
/*     */   {
/* 247 */     this.plainSocket = this._socket_;
/* 248 */     initSslContext();
/*     */     
/* 250 */     SSLSocketFactory ssf = this.context.getSocketFactory();
/* 251 */     String ip = this._socket_.getInetAddress().getHostAddress();
/* 252 */     int port = this._socket_.getPort();
/* 253 */     SSLSocket socket = 
/* 254 */       (SSLSocket)ssf.createSocket(this._socket_, ip, port, false);
/* 255 */     socket.setEnableSessionCreation(this.isCreation);
/* 256 */     socket.setUseClientMode(this.isClientMode);
/*     */     
/* 258 */     if (!this.isClientMode) {
/* 259 */       socket.setNeedClientAuth(this.isNeedClientAuth);
/* 260 */       socket.setWantClientAuth(this.isWantClientAuth);
/*     */     }
/*     */     
/* 263 */     if (this.protocols != null) {
/* 264 */       socket.setEnabledProtocols(this.protocols);
/*     */     }
/* 266 */     if (this.suites != null) {
/* 267 */       socket.setEnabledCipherSuites(this.suites);
/*     */     }
/* 269 */     socket.startHandshake();
/*     */     
/* 271 */     this._socket_ = socket;
/* 272 */     this._controlInput_ = new BufferedReader(new InputStreamReader(
/* 273 */       socket.getInputStream(), getControlEncoding()));
/* 274 */     this._controlOutput_ = new BufferedWriter(new OutputStreamWriter(
/* 275 */       socket.getOutputStream(), getControlEncoding()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private KeyManager getKeyManager()
/*     */   {
/* 283 */     return this.keyManager;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setKeyManager(KeyManager keyManager)
/*     */   {
/* 293 */     this.keyManager = keyManager;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEnabledSessionCreation(boolean isCreation)
/*     */   {
/* 301 */     this.isCreation = isCreation;
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
/*     */   public boolean getEnableSessionCreation()
/*     */   {
/* 314 */     if ((this._socket_ instanceof SSLSocket)) {
/* 315 */       return ((SSLSocket)this._socket_).getEnableSessionCreation();
/*     */     }
/* 317 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setNeedClientAuth(boolean isNeedClientAuth)
/*     */   {
/* 325 */     this.isNeedClientAuth = isNeedClientAuth;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getNeedClientAuth()
/*     */   {
/* 335 */     if ((this._socket_ instanceof SSLSocket)) {
/* 336 */       return ((SSLSocket)this._socket_).getNeedClientAuth();
/*     */     }
/* 338 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setWantClientAuth(boolean isWantClientAuth)
/*     */   {
/* 348 */     this.isWantClientAuth = isWantClientAuth;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getWantClientAuth()
/*     */   {
/* 358 */     if ((this._socket_ instanceof SSLSocket)) {
/* 359 */       return ((SSLSocket)this._socket_).getWantClientAuth();
/*     */     }
/* 361 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setUseClientMode(boolean isClientMode)
/*     */   {
/* 370 */     this.isClientMode = isClientMode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getUseClientMode()
/*     */   {
/* 381 */     if ((this._socket_ instanceof SSLSocket)) {
/* 382 */       return ((SSLSocket)this._socket_).getUseClientMode();
/*     */     }
/* 384 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEnabledCipherSuites(String[] cipherSuites)
/*     */   {
/* 393 */     this.suites = new String[cipherSuites.length];
/* 394 */     System.arraycopy(cipherSuites, 0, this.suites, 0, cipherSuites.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getEnabledCipherSuites()
/*     */   {
/* 404 */     if ((this._socket_ instanceof SSLSocket)) {
/* 405 */       return ((SSLSocket)this._socket_).getEnabledCipherSuites();
/*     */     }
/* 407 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setEnabledProtocols(String[] protocolVersions)
/*     */   {
/* 416 */     this.protocols = new String[protocolVersions.length];
/* 417 */     System.arraycopy(protocolVersions, 0, this.protocols, 0, protocolVersions.length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getEnabledProtocols()
/*     */   {
/* 427 */     if ((this._socket_ instanceof SSLSocket)) {
/* 428 */       return ((SSLSocket)this._socket_).getEnabledProtocols();
/*     */     }
/* 430 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void execPBSZ(long pbsz)
/*     */     throws SSLException, IOException
/*     */   {
/* 442 */     if ((pbsz < 0L) || (4294967295L < pbsz)) {
/* 443 */       throw new IllegalArgumentException();
/*     */     }
/* 445 */     int status = sendCommand("PBSZ", String.valueOf(pbsz));
/* 446 */     if (200 != status) {
/* 447 */       throw new SSLException(getReplyString());
/*     */     }
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
/*     */ 
/*     */   public long parsePBSZ(long pbsz)
/*     */     throws SSLException, IOException
/*     */   {
/* 464 */     execPBSZ(pbsz);
/* 465 */     long minvalue = pbsz;
/* 466 */     String remainder = extractPrefixedData("PBSZ=", getReplyString());
/* 467 */     if (remainder != null) {
/* 468 */       long replysz = Long.parseLong(remainder);
/* 469 */       if (replysz < minvalue) {
/* 470 */         minvalue = replysz;
/*     */       }
/*     */     }
/* 473 */     return minvalue;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void execPROT(String prot)
/*     */     throws SSLException, IOException
/*     */   {
/* 494 */     if (prot == null) {
/* 495 */       prot = "C";
/*     */     }
/* 497 */     if (!checkPROTValue(prot)) {
/* 498 */       throw new IllegalArgumentException();
/*     */     }
/* 500 */     if (200 != sendCommand("PROT", prot)) {
/* 501 */       throw new SSLException(getReplyString());
/*     */     }
/* 503 */     if ("C".equals(prot)) {
/* 504 */       setSocketFactory(null);
/* 505 */       setServerSocketFactory(null);
/*     */     } else {
/* 507 */       setSocketFactory(new FTPSSocketFactory(this.context));
/* 508 */       setServerSocketFactory(new FTPSServerSocketFactory(this.context));
/* 509 */       initSslContext();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean checkPROTValue(String prot)
/*     */   {
/*     */     String[] arrayOfString;
/*     */     
/* 519 */     int j = (arrayOfString = PROT_COMMAND_VALUE).length; for (int i = 0; i < j; i++) { String element = arrayOfString[i];
/*     */       
/* 521 */       if (element.equals(prot)) {
/* 522 */         return true;
/*     */       }
/*     */     }
/* 525 */     return false;
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
/*     */ 
/*     */   public int sendCommand(String command, String args)
/*     */     throws IOException
/*     */   {
/* 541 */     int repCode = super.sendCommand(command, args);
/*     */     
/* 543 */     if ("CCC".equals(command)) {
/* 544 */       if (200 == repCode) {
/* 545 */         this._socket_.close();
/* 546 */         this._socket_ = this.plainSocket;
/* 547 */         this._controlInput_ = new BufferedReader(
/* 548 */           new InputStreamReader(
/* 549 */           this._socket_.getInputStream(), getControlEncoding()));
/* 550 */         this._controlOutput_ = new BufferedWriter(
/* 551 */           new OutputStreamWriter(
/* 552 */           this._socket_.getOutputStream(), getControlEncoding()));
/*     */       } else {
/* 554 */         throw new SSLException(getReplyString());
/*     */       }
/*     */     }
/* 557 */     return repCode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static String PROVIDER;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public static String STORE_TYPE;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   protected Socket _openDataConnection_(int command, String arg)
/*     */     throws IOException
/*     */   {
/* 580 */     return _openDataConnection_(FTPCommand.getCommand(command), arg);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected Socket _openDataConnection_(String command, String arg)
/*     */     throws IOException
/*     */   {
/* 600 */     Socket socket = super._openDataConnection_(command, arg);
/* 601 */     _prepareDataSocket_(socket);
/* 602 */     if ((socket instanceof SSLSocket)) {
/* 603 */       SSLSocket sslSocket = (SSLSocket)socket;
/*     */       
/* 605 */       sslSocket.setUseClientMode(this.isClientMode);
/* 606 */       sslSocket.setEnableSessionCreation(this.isCreation);
/*     */       
/*     */ 
/* 609 */       if (!this.isClientMode) {
/* 610 */         sslSocket.setNeedClientAuth(this.isNeedClientAuth);
/* 611 */         sslSocket.setWantClientAuth(this.isWantClientAuth);
/*     */       }
/* 613 */       if (this.suites != null) {
/* 614 */         sslSocket.setEnabledCipherSuites(this.suites);
/*     */       }
/* 616 */       if (this.protocols != null) {
/* 617 */         sslSocket.setEnabledProtocols(this.protocols);
/*     */       }
/* 619 */       sslSocket.startHandshake();
/*     */     }
/*     */     
/* 622 */     return socket;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void _prepareDataSocket_(Socket socket)
/*     */     throws IOException
/*     */   {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public TrustManager getTrustManager()
/*     */   {
/* 644 */     return this.trustManager;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTrustManager(TrustManager trustManager)
/*     */   {
/* 655 */     this.trustManager = trustManager;
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
/*     */ 
/*     */   public void disconnect()
/*     */     throws IOException
/*     */   {
/* 671 */     super.disconnect();
/* 672 */     setSocketFactory(null);
/* 673 */     setServerSocketFactory(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int execAUTH(String mechanism)
/*     */     throws IOException
/*     */   {
/* 686 */     return sendCommand("AUTH", mechanism);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int execADAT(byte[] data)
/*     */     throws IOException
/*     */   {
/* 699 */     if (data != null)
/*     */     {
/* 701 */       return sendCommand("ADAT", Base64.encodeBase64StringUnChunked(data));
/*     */     }
/*     */     
/*     */ 
/* 705 */     return sendCommand("ADAT");
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
/*     */   public int execCCC()
/*     */     throws IOException
/*     */   {
/* 720 */     int repCode = sendCommand("CCC");
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
/* 732 */     return repCode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int execMIC(byte[] data)
/*     */     throws IOException
/*     */   {
/* 745 */     if (data != null)
/*     */     {
/* 747 */       return sendCommand("MIC", Base64.encodeBase64StringUnChunked(data));
/*     */     }
/*     */     
/*     */ 
/* 751 */     return sendCommand("MIC", "");
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
/*     */   public int execCONF(byte[] data)
/*     */     throws IOException
/*     */   {
/* 765 */     if (data != null)
/*     */     {
/* 767 */       return sendCommand("CONF", Base64.encodeBase64StringUnChunked(data));
/*     */     }
/*     */     
/*     */ 
/* 771 */     return sendCommand("CONF", "");
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
/*     */   public int execENC(byte[] data)
/*     */     throws IOException
/*     */   {
/* 785 */     if (data != null)
/*     */     {
/* 787 */       return sendCommand("ENC", Base64.encodeBase64StringUnChunked(data));
/*     */     }
/*     */     
/*     */ 
/* 791 */     return sendCommand("ENC", "");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public byte[] parseADATReply(String reply)
/*     */   {
/* 803 */     if (reply == null) {
/* 804 */       return null;
/*     */     }
/* 806 */     return Base64.decodeBase64(extractPrefixedData("ADAT=", reply));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private String extractPrefixedData(String prefix, String reply)
/*     */   {
/* 817 */     int idx = reply.indexOf(prefix);
/* 818 */     if (idx == -1) {
/* 819 */       return null;
/*     */     }
/*     */     
/* 822 */     return reply.substring(idx + prefix.length()).trim();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\FTPSClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */