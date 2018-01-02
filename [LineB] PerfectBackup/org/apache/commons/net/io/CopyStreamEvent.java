/*     */ package org.apache.commons.net.io;
/*     */ 
/*     */ import java.util.EventObject;
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
/*     */ public class CopyStreamEvent
/*     */   extends EventObject
/*     */ {
/*     */   private static final long serialVersionUID = -964927635655051867L;
/*     */   public static final long UNKNOWN_STREAM_SIZE = -1L;
/*     */   private final int bytesTransferred;
/*     */   private final long totalBytesTransferred;
/*     */   private final long streamSize;
/*     */   
/*     */   public CopyStreamEvent(Object source, long totalBytesTransferred, int bytesTransferred, long streamSize)
/*     */   {
/*  61 */     super(source);
/*  62 */     this.bytesTransferred = bytesTransferred;
/*  63 */     this.totalBytesTransferred = totalBytesTransferred;
/*  64 */     this.streamSize = streamSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getBytesTransferred()
/*     */   {
/*  75 */     return this.bytesTransferred;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getTotalBytesTransferred()
/*     */   {
/*  86 */     return this.totalBytesTransferred;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getStreamSize()
/*     */   {
/*  97 */     return this.streamSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 105 */     return 
/*     */     
/*     */ 
/*     */ 
/* 109 */       getClass().getName() + "[source=" + this.source + ", total=" + this.totalBytesTransferred + ", bytes=" + this.bytesTransferred + ", size=" + this.streamSize + "]";
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\io\CopyStreamEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */