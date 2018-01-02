/*     */ package org.apache.commons.net;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Proxy;
/*     */ import java.net.ServerSocket;
/*     */ import java.net.Socket;
/*     */ import java.net.UnknownHostException;
/*     */ import javax.net.SocketFactory;
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
/*     */ 
/*     */ 
/*     */ public class DefaultSocketFactory
/*     */   extends SocketFactory
/*     */ {
/*     */   private final Proxy connProxy;
/*     */   
/*     */   public DefaultSocketFactory()
/*     */   {
/*  53 */     this(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public DefaultSocketFactory(Proxy proxy)
/*     */   {
/*  64 */     this.connProxy = proxy;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Socket createSocket()
/*     */     throws IOException
/*     */   {
/*  77 */     if (this.connProxy != null)
/*     */     {
/*  79 */       return new Socket(this.connProxy);
/*     */     }
/*  81 */     return new Socket();
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
/*     */   public Socket createSocket(String host, int port)
/*     */     throws UnknownHostException, IOException
/*     */   {
/*  97 */     if (this.connProxy != null)
/*     */     {
/*  99 */       Socket s = new Socket(this.connProxy);
/* 100 */       s.connect(new InetSocketAddress(host, port));
/* 101 */       return s;
/*     */     }
/* 103 */     return new Socket(host, port);
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
/*     */   public Socket createSocket(InetAddress address, int port)
/*     */     throws IOException
/*     */   {
/* 118 */     if (this.connProxy != null)
/*     */     {
/* 120 */       Socket s = new Socket(this.connProxy);
/* 121 */       s.connect(new InetSocketAddress(address, port));
/* 122 */       return s;
/*     */     }
/* 124 */     return new Socket(address, port);
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
/*     */   public Socket createSocket(String host, int port, InetAddress localAddr, int localPort)
/*     */     throws UnknownHostException, IOException
/*     */   {
/* 144 */     if (this.connProxy != null)
/*     */     {
/* 146 */       Socket s = new Socket(this.connProxy);
/* 147 */       s.bind(new InetSocketAddress(localAddr, localPort));
/* 148 */       s.connect(new InetSocketAddress(host, port));
/* 149 */       return s;
/*     */     }
/* 151 */     return new Socket(host, port, localAddr, localPort);
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
/*     */   public Socket createSocket(InetAddress address, int port, InetAddress localAddr, int localPort)
/*     */     throws IOException
/*     */   {
/* 170 */     if (this.connProxy != null)
/*     */     {
/* 172 */       Socket s = new Socket(this.connProxy);
/* 173 */       s.bind(new InetSocketAddress(localAddr, localPort));
/* 174 */       s.connect(new InetSocketAddress(address, port));
/* 175 */       return s;
/*     */     }
/* 177 */     return new Socket(address, port, localAddr, localPort);
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
/*     */   public ServerSocket createServerSocket(int port)
/*     */     throws IOException
/*     */   {
/* 191 */     return new ServerSocket(port);
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
/*     */   public ServerSocket createServerSocket(int port, int backlog)
/*     */     throws IOException
/*     */   {
/* 208 */     return new ServerSocket(port, backlog);
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
/*     */   public ServerSocket createServerSocket(int port, int backlog, InetAddress bindAddr)
/*     */     throws IOException
/*     */   {
/* 228 */     return new ServerSocket(port, backlog, bindAddr);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\DefaultSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */