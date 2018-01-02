/*     */ package org.apache.commons.net.io;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
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
/*     */ public final class ToNetASCIIInputStream
/*     */   extends FilterInputStream
/*     */ {
/*     */   private static final int __NOTHING_SPECIAL = 0;
/*     */   private static final int __LAST_WAS_CR = 1;
/*     */   private static final int __LAST_WAS_NL = 2;
/*     */   private int __status;
/*     */   
/*     */   public ToNetASCIIInputStream(InputStream input)
/*     */   {
/*  50 */     super(input);
/*  51 */     this.__status = 0;
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
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*  69 */     if (this.__status == 2)
/*     */     {
/*  71 */       this.__status = 0;
/*  72 */       return 10;
/*     */     }
/*     */     
/*  75 */     int ch = this.in.read();
/*     */     
/*  77 */     switch (ch)
/*     */     {
/*     */     case 13: 
/*  80 */       this.__status = 1;
/*  81 */       return 13;
/*     */     case 10: 
/*  83 */       if (this.__status != 1)
/*     */       {
/*  85 */         this.__status = 2;
/*  86 */         return 13;
/*     */       }
/*     */       break;
/*     */     }
/*  90 */     this.__status = 0;
/*  91 */     return ch;
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
/*     */   public int read(byte[] buffer)
/*     */     throws IOException
/*     */   {
/* 112 */     return read(buffer, 0, buffer.length);
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
/*     */   public int read(byte[] buffer, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 135 */     if (length < 1) {
/* 136 */       return 0;
/*     */     }
/*     */     
/* 139 */     int ch = available();
/*     */     
/* 141 */     if (length > ch) {
/* 142 */       length = ch;
/*     */     }
/*     */     
/*     */ 
/* 146 */     if (length < 1) {
/* 147 */       length = 1;
/*     */     }
/*     */     
/* 150 */     if ((ch = read()) == -1) {
/* 151 */       return -1;
/*     */     }
/*     */     
/* 154 */     int off = offset;
/*     */     
/*     */     do
/*     */     {
/* 158 */       buffer[(offset++)] = ((byte)ch);
/*     */       
/* 160 */       length--; } while ((length > 0) && ((ch = read()) != -1));
/*     */     
/* 162 */     return offset - off;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 169 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public int available()
/*     */     throws IOException
/*     */   {
/* 177 */     int result = this.in.available();
/*     */     
/* 179 */     if (this.__status == 2) {
/* 180 */       return result + 1;
/*     */     }
/*     */     
/* 183 */     return result;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\io\ToNetASCIIInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */