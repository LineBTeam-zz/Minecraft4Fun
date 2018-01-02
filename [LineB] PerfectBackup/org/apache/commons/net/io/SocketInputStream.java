/*    */ package org.apache.commons.net.io;
/*    */ 
/*    */ import java.io.FilterInputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.net.Socket;
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
/*    */ public class SocketInputStream
/*    */   extends FilterInputStream
/*    */ {
/*    */   private final Socket __socket;
/*    */   
/*    */   public SocketInputStream(Socket socket, InputStream stream)
/*    */   {
/* 51 */     super(stream);
/* 52 */     this.__socket = socket;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void close()
/*    */     throws IOException
/*    */   {
/* 65 */     super.close();
/* 66 */     this.__socket.close();
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\io\SocketInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */