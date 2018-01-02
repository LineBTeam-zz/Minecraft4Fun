/*     */ package org.apache.commons.net.io;
/*     */ 
/*     */ import java.util.EventListener;
/*     */ import org.apache.commons.net.util.ListenerList;
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
/*     */ public class CopyStreamAdapter
/*     */   implements CopyStreamListener
/*     */ {
/*     */   private final ListenerList internalListeners;
/*     */   
/*     */   public CopyStreamAdapter()
/*     */   {
/*  50 */     this.internalListeners = new ListenerList();
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
/*     */   public void bytesTransferred(CopyStreamEvent event)
/*     */   {
/*  66 */     for (EventListener listener : this.internalListeners)
/*     */     {
/*  68 */       ((CopyStreamListener)listener).bytesTransferred(event);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize)
/*     */   {
/*  91 */     for (EventListener listener : this.internalListeners)
/*     */     {
/*  93 */       ((CopyStreamListener)listener).bytesTransferred(
/*  94 */         totalBytesTransferred, bytesTransferred, streamSize);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addCopyStreamListener(CopyStreamListener listener)
/*     */   {
/* 106 */     this.internalListeners.addListener(listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeCopyStreamListener(CopyStreamListener listener)
/*     */   {
/* 116 */     this.internalListeners.removeListener(listener);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\io\CopyStreamAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */