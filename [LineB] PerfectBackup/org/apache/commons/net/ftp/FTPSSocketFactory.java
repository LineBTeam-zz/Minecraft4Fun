/*    */ package org.apache.commons.net.ftp;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
/*    */ import java.net.ServerSocket;
/*    */ import java.net.Socket;
/*    */ import java.net.UnknownHostException;
/*    */ import javax.net.SocketFactory;
/*    */ import javax.net.ssl.SSLContext;
/*    */ import javax.net.ssl.SSLServerSocket;
/*    */ import javax.net.ssl.SSLServerSocketFactory;
/*    */ import javax.net.ssl.SSLSocketFactory;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FTPSSocketFactory
/*    */   extends SocketFactory
/*    */ {
/*    */   private final SSLContext context;
/*    */   
/*    */   public FTPSSocketFactory(SSLContext context)
/*    */   {
/* 39 */     this.context = context;
/*    */   }
/*    */   
/*    */   public Socket createSocket()
/*    */     throws IOException
/*    */   {
/* 45 */     return this.context.getSocketFactory().createSocket();
/*    */   }
/*    */   
/*    */   public Socket createSocket(String address, int port) throws UnknownHostException, IOException
/*    */   {
/* 50 */     return this.context.getSocketFactory().createSocket(address, port);
/*    */   }
/*    */   
/*    */   public Socket createSocket(InetAddress address, int port) throws IOException
/*    */   {
/* 55 */     return this.context.getSocketFactory().createSocket(address, port);
/*    */   }
/*    */   
/*    */   public Socket createSocket(String address, int port, InetAddress localAddress, int localPort) throws UnknownHostException, IOException
/*    */   {
/* 60 */     return this.context.getSocketFactory().createSocket(address, port, localAddress, localPort);
/*    */   }
/*    */   
/*    */   public Socket createSocket(InetAddress address, int port, InetAddress localAddress, int localPort) throws IOException
/*    */   {
/* 65 */     return this.context.getSocketFactory().createSocket(address, port, localAddress, localPort);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   @Deprecated
/*    */   public ServerSocket createServerSocket(int port)
/*    */     throws IOException
/*    */   {
/* 74 */     return init(this.context.getServerSocketFactory().createServerSocket(port));
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public ServerSocket createServerSocket(int port, int backlog) throws IOException
/*    */   {
/* 80 */     return init(this.context.getServerSocketFactory().createServerSocket(port, backlog));
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException
/*    */   {
/* 86 */     return init(this.context.getServerSocketFactory().createServerSocket(port, backlog, ifAddress));
/*    */   }
/*    */   
/*    */   @Deprecated
/*    */   public ServerSocket init(ServerSocket socket) throws IOException
/*    */   {
/* 92 */     ((SSLServerSocket)socket).setUseClientMode(true);
/* 93 */     return socket;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\FTPSSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */