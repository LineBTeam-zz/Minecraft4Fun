/*     */ package org.apache.commons.net.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.Writer;
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
/*     */ public final class DotTerminatedMessageWriter
/*     */   extends Writer
/*     */ {
/*     */   private static final int __NOTHING_SPECIAL_STATE = 0;
/*     */   private static final int __LAST_WAS_CR_STATE = 1;
/*     */   private static final int __LAST_WAS_NL_STATE = 2;
/*     */   private int __state;
/*     */   private Writer __output;
/*     */   
/*     */   public DotTerminatedMessageWriter(Writer output)
/*     */   {
/*  57 */     super(output);
/*  58 */     this.__output = output;
/*  59 */     this.__state = 0;
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
/*     */   public void write(int ch)
/*     */     throws IOException
/*     */   {
/*  77 */     synchronized (this.lock)
/*     */     {
/*  79 */       switch (ch)
/*     */       {
/*     */       case 13: 
/*  82 */         this.__state = 1;
/*  83 */         this.__output.write(13);
/*  84 */         return;
/*     */       case 10: 
/*  86 */         if (this.__state != 1) {
/*  87 */           this.__output.write(13);
/*     */         }
/*  89 */         this.__output.write(10);
/*  90 */         this.__state = 2;
/*  91 */         return;
/*     */       
/*     */       case 46: 
/*  94 */         if (this.__state == 2) {
/*  95 */           this.__output.write(46);
/*     */         }
/*     */         break;
/*     */       }
/*  99 */       this.__state = 0;
/* 100 */       this.__output.write(ch);
/* 101 */       return;
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
/*     */   public void write(char[] buffer, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 120 */     synchronized (this.lock)
/*     */     {
/* 122 */       while (length-- > 0) {
/* 123 */         write(buffer[(offset++)]);
/*     */       }
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
/*     */   public void write(char[] buffer)
/*     */     throws IOException
/*     */   {
/* 139 */     write(buffer, 0, buffer.length);
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
/*     */   public void write(String string)
/*     */     throws IOException
/*     */   {
/* 153 */     write(string.toCharArray());
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
/*     */   public void write(String string, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 169 */     write(string.toCharArray(), offset, length);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 182 */     synchronized (this.lock)
/*     */     {
/* 184 */       this.__output.flush();
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
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 200 */     synchronized (this.lock)
/*     */     {
/* 202 */       if (this.__output == null) {
/* 203 */         return;
/*     */       }
/*     */       
/* 206 */       if (this.__state == 1) {
/* 207 */         this.__output.write(10);
/* 208 */       } else if (this.__state != 2) {
/* 209 */         this.__output.write("\r\n");
/*     */       }
/*     */       
/* 212 */       this.__output.write(".\r\n");
/*     */       
/* 214 */       this.__output.flush();
/* 215 */       this.__output = null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\io\DotTerminatedMessageWriter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */