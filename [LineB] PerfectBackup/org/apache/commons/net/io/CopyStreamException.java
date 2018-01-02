/*    */ package org.apache.commons.net.io;
/*    */ 
/*    */ import java.io.IOException;
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
/*    */ public class CopyStreamException
/*    */   extends IOException
/*    */ {
/*    */   private static final long serialVersionUID = -2602899129433221532L;
/*    */   private final long totalBytesTransferred;
/*    */   
/*    */   public CopyStreamException(String message, long bytesTransferred, IOException exception)
/*    */   {
/* 47 */     super(message);
/* 48 */     initCause(exception);
/* 49 */     this.totalBytesTransferred = bytesTransferred;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public long getTotalBytesTransferred()
/*    */   {
/* 60 */     return this.totalBytesTransferred;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public IOException getIOException()
/*    */   {
/* 69 */     return (IOException)getCause();
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\io\CopyStreamException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */