/*     */ package org.apache.commons.net.io;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.net.Socket;
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
/*     */ public final class Util
/*     */ {
/*     */   public static final int DEFAULT_COPY_BUFFER_SIZE = 1024;
/*     */   
/*     */   public static final long copyStream(InputStream source, OutputStream dest, int bufferSize, long streamSize, CopyStreamListener listener, boolean flush)
/*     */     throws CopyStreamException
/*     */   {
/*  95 */     long total = 0L;
/*  96 */     byte[] buffer = new byte[bufferSize >= 0 ? bufferSize : 'Ѐ'];
/*     */     try
/*     */     {
/*     */       int bytes;
/* 100 */       while ((bytes = source.read(buffer)) != -1)
/*     */       {
/*     */         int bytes;
/*     */         
/*     */ 
/* 105 */         if (bytes == 0)
/*     */         {
/* 107 */           bytes = source.read();
/* 108 */           if (bytes < 0) {
/*     */             break;
/*     */           }
/* 111 */           dest.write(bytes);
/* 112 */           if (flush) {
/* 113 */             dest.flush();
/*     */           }
/* 115 */           total += 1L;
/* 116 */           if (listener != null) {
/* 117 */             listener.bytesTransferred(total, 1, streamSize);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 122 */           dest.write(buffer, 0, bytes);
/* 123 */           if (flush) {
/* 124 */             dest.flush();
/*     */           }
/* 126 */           total += bytes;
/* 127 */           if (listener != null) {
/* 128 */             listener.bytesTransferred(total, bytes, streamSize);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e) {
/* 134 */       throw new CopyStreamException("IOException caught while copying.", 
/* 135 */         total, e);
/*     */     }
/*     */     int bytes;
/* 138 */     return total;
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
/*     */   public static final long copyStream(InputStream source, OutputStream dest, int bufferSize, long streamSize, CopyStreamListener listener)
/*     */     throws CopyStreamException
/*     */   {
/* 179 */     return copyStream(source, dest, bufferSize, streamSize, listener, 
/* 180 */       true);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final long copyStream(InputStream source, OutputStream dest, int bufferSize)
/*     */     throws CopyStreamException
/*     */   {
/* 210 */     return copyStream(source, dest, bufferSize, 
/* 211 */       -1L, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final long copyStream(InputStream source, OutputStream dest)
/*     */     throws CopyStreamException
/*     */   {
/* 221 */     return copyStream(source, dest, 1024);
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
/*     */   public static final long copyReader(Reader source, Writer dest, int bufferSize, long streamSize, CopyStreamListener listener)
/*     */     throws CopyStreamException
/*     */   {
/* 263 */     long total = 0L;
/* 264 */     char[] buffer = new char[bufferSize >= 0 ? bufferSize : 'Ѐ'];
/*     */     try
/*     */     {
/*     */       int chars;
/* 268 */       while ((chars = source.read(buffer)) != -1)
/*     */       {
/*     */         int chars;
/*     */         
/* 272 */         if (chars == 0)
/*     */         {
/* 274 */           chars = source.read();
/* 275 */           if (chars < 0) {
/*     */             break;
/*     */           }
/* 278 */           dest.write(chars);
/* 279 */           dest.flush();
/* 280 */           total += 1L;
/* 281 */           if (listener != null) {
/* 282 */             listener.bytesTransferred(total, chars, streamSize);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 287 */           dest.write(buffer, 0, chars);
/* 288 */           dest.flush();
/* 289 */           total += chars;
/* 290 */           if (listener != null) {
/* 291 */             listener.bytesTransferred(total, chars, streamSize);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e) {
/* 297 */       throw new CopyStreamException("IOException caught while copying.", 
/* 298 */         total, e);
/*     */     }
/*     */     int chars;
/* 301 */     return total;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final long copyReader(Reader source, Writer dest, int bufferSize)
/*     */     throws CopyStreamException
/*     */   {
/* 330 */     return copyReader(source, dest, bufferSize, 
/* 331 */       -1L, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static final long copyReader(Reader source, Writer dest)
/*     */     throws CopyStreamException
/*     */   {
/* 341 */     return copyReader(source, dest, 1024);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void closeQuietly(Closeable closeable)
/*     */   {
/* 352 */     if (closeable != null) {
/*     */       try {
/* 354 */         closeable.close();
/*     */       }
/*     */       catch (IOException localIOException) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void closeQuietly(Socket socket)
/*     */   {
/* 368 */     if (socket != null) {
/*     */       try {
/* 370 */         socket.close();
/*     */       }
/*     */       catch (IOException localIOException) {}
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\io\Util.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */