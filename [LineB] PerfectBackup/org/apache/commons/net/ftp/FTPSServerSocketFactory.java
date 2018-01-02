/*    */ package org.apache.commons.net.ftp;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.InetAddress;
/*    */ import java.net.ServerSocket;
/*    */ import javax.net.ServerSocketFactory;
/*    */ import javax.net.ssl.SSLContext;
/*    */ import javax.net.ssl.SSLServerSocket;
/*    */ import javax.net.ssl.SSLServerSocketFactory;
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
/*    */ 
/*    */ 
/*    */ public class FTPSServerSocketFactory
/*    */   extends ServerSocketFactory
/*    */ {
/*    */   private final SSLContext context;
/*    */   
/*    */   public FTPSServerSocketFactory(SSLContext context)
/*    */   {
/* 38 */     this.context = context;
/*    */   }
/*    */   
/*    */   public ServerSocket createServerSocket()
/*    */     throws IOException
/*    */   {
/* 44 */     return init(this.context.getServerSocketFactory().createServerSocket());
/*    */   }
/*    */   
/*    */   public ServerSocket createServerSocket(int port) throws IOException
/*    */   {
/* 49 */     return init(this.context.getServerSocketFactory().createServerSocket(port));
/*    */   }
/*    */   
/*    */   public ServerSocket createServerSocket(int port, int backlog) throws IOException
/*    */   {
/* 54 */     return init(this.context.getServerSocketFactory().createServerSocket(port, backlog));
/*    */   }
/*    */   
/*    */   public ServerSocket createServerSocket(int port, int backlog, InetAddress ifAddress) throws IOException
/*    */   {
/* 59 */     return init(this.context.getServerSocketFactory().createServerSocket(port, backlog, ifAddress));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ServerSocket init(ServerSocket socket)
/*    */   {
/* 70 */     ((SSLServerSocket)socket).setUseClientMode(true);
/* 71 */     return socket;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\FTPSServerSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */