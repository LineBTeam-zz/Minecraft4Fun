/*     */ package org.apache.commons.net.io;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
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
/*     */ public final class ToNetASCIIOutputStream
/*     */   extends FilterOutputStream
/*     */ {
/*     */   private boolean __lastWasCR;
/*     */   
/*     */   public ToNetASCIIOutputStream(OutputStream output)
/*     */   {
/*  47 */     super(output);
/*  48 */     this.__lastWasCR = false;
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
/*     */   public synchronized void write(int ch)
/*     */     throws IOException
/*     */   {
/*  67 */     switch (ch)
/*     */     {
/*     */     case 13: 
/*  70 */       this.__lastWasCR = true;
/*  71 */       this.out.write(13);
/*  72 */       return;
/*     */     case 10: 
/*  74 */       if (!this.__lastWasCR) {
/*  75 */         this.out.write(13);
/*     */       }
/*     */       break;
/*     */     }
/*  79 */     this.__lastWasCR = false;
/*  80 */     this.out.write(ch);
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
/*     */   public synchronized void write(byte[] buffer)
/*     */     throws IOException
/*     */   {
/*  97 */     write(buffer, 0, buffer.length);
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
/*     */   public synchronized void write(byte[] buffer, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 115 */     while (length-- > 0) {
/* 116 */       write(buffer[(offset++)]);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\io\ToNetASCIIOutputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */