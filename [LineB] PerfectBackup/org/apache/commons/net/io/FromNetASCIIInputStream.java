/*     */ package org.apache.commons.net.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ import java.io.UnsupportedEncodingException;
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
/*     */ public final class FromNetASCIIInputStream
/*     */   extends PushbackInputStream
/*     */ {
/*     */   static final boolean _noConversionRequired;
/*  42 */   static final String _lineSeparator = System.getProperty("line.separator");
/*  43 */   static { _noConversionRequired = _lineSeparator.equals("\r\n");
/*     */     try {
/*  45 */       _lineSeparatorBytes = _lineSeparator.getBytes("US-ASCII");
/*     */     } catch (UnsupportedEncodingException e) {
/*  47 */       throw new RuntimeException("Broken JVM - cannot find US-ASCII charset!", e);
/*     */     } }
/*     */   
/*     */   static final byte[] _lineSeparatorBytes;
/*  51 */   private int __length = 0;
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
/*     */   public static final boolean isConversionRequired()
/*     */   {
/*  64 */     return !_noConversionRequired;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FromNetASCIIInputStream(InputStream input)
/*     */   {
/*  73 */     super(input, _lineSeparatorBytes.length + 1);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int __read()
/*     */     throws IOException
/*     */   {
/*  81 */     int ch = super.read();
/*     */     
/*  83 */     if (ch == 13)
/*     */     {
/*  85 */       ch = super.read();
/*  86 */       if (ch == 10)
/*     */       {
/*  88 */         unread(_lineSeparatorBytes);
/*  89 */         ch = super.read();
/*     */         
/*  91 */         this.__length -= 1;
/*     */       }
/*     */       else
/*     */       {
/*  95 */         if (ch != -1) {
/*  96 */           unread(ch);
/*     */         }
/*  98 */         return 13;
/*     */       }
/*     */     }
/*     */     
/* 102 */     return ch;
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
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/* 122 */     if (_noConversionRequired) {
/* 123 */       return super.read();
/*     */     }
/*     */     
/* 126 */     return __read();
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
/*     */   public int read(byte[] buffer)
/*     */     throws IOException
/*     */   {
/* 144 */     return read(buffer, 0, buffer.length);
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
/*     */   public int read(byte[] buffer, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 165 */     if (_noConversionRequired) {
/* 166 */       return super.read(buffer, offset, length);
/*     */     }
/*     */     
/* 169 */     if (length < 1) {
/* 170 */       return 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 175 */     int ch = available();
/*     */     
/* 177 */     this.__length = (length > ch ? ch : length);
/*     */     
/*     */ 
/* 180 */     if (this.__length < 1) {
/* 181 */       this.__length = 1;
/*     */     }
/*     */     
/*     */ 
/* 185 */     if ((ch = __read()) == -1) {
/* 186 */       return -1;
/*     */     }
/*     */     
/* 189 */     int off = offset;
/*     */     
/*     */     do
/*     */     {
/* 193 */       buffer[(offset++)] = ((byte)ch);
/*     */     }
/* 195 */     while ((--this.__length > 0) && ((ch = __read()) != -1));
/*     */     
/*     */ 
/* 198 */     return offset - off;
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
/*     */   public int available()
/*     */     throws IOException
/*     */   {
/* 214 */     if (this.in == null) {
/* 215 */       throw new IOException("Stream closed");
/*     */     }
/* 217 */     return this.buf.length - this.pos + this.in.available();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\io\FromNetASCIIInputStream.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */