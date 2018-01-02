/*     */ package org.apache.commons.net.io;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.Reader;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class DotTerminatedMessageReader
/*     */   extends BufferedReader
/*     */ {
/*     */   private static final char LF = '\n';
/*     */   private static final char CR = '\r';
/*     */   private static final int DOT = 46;
/*     */   private boolean atBeginning;
/*     */   private boolean eof;
/*     */   private boolean seenCR;
/*     */   
/*     */   public DotTerminatedMessageReader(Reader reader)
/*     */   {
/*  57 */     super(reader);
/*     */     
/*  59 */     this.atBeginning = true;
/*  60 */     this.eof = false;
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
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*  77 */     synchronized (this.lock) {
/*  78 */       if (this.eof) {
/*  79 */         return -1;
/*     */       }
/*  81 */       int chint = super.read();
/*  82 */       if (chint == -1) {
/*  83 */         this.eof = true;
/*  84 */         return -1;
/*     */       }
/*  86 */       if (this.atBeginning) {
/*  87 */         this.atBeginning = false;
/*  88 */         if (chint == 46) {
/*  89 */           mark(2);
/*  90 */           chint = super.read();
/*  91 */           if (chint == -1)
/*     */           {
/*  93 */             this.eof = true;
/*  94 */             return 46;
/*     */           }
/*  96 */           if (chint == 46)
/*     */           {
/*  98 */             return chint;
/*     */           }
/* 100 */           if (chint == 13) {
/* 101 */             chint = super.read();
/* 102 */             if (chint == -1)
/*     */             {
/* 104 */               reset();
/* 105 */               return 46;
/*     */             }
/* 107 */             if (chint == 10) {
/* 108 */               this.atBeginning = true;
/* 109 */               this.eof = true;
/*     */               
/* 111 */               return -1;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 116 */           reset();
/* 117 */           return 46;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 122 */       if (this.seenCR) {
/* 123 */         this.seenCR = false;
/* 124 */         if (chint == 10) {
/* 125 */           this.atBeginning = true;
/*     */         }
/*     */       }
/* 128 */       if (chint == 13) {
/* 129 */         this.seenCR = true;
/*     */       }
/* 131 */       return chint;
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
/*     */   public int read(char[] buffer)
/*     */     throws IOException
/*     */   {
/* 149 */     return read(buffer, 0, buffer.length);
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
/*     */   public int read(char[] buffer, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 169 */     if (length < 1)
/*     */     {
/* 171 */       return 0;
/*     */     }
/*     */     
/* 174 */     synchronized (this.lock) {
/*     */       int ch;
/* 176 */       if ((ch = read()) == -1)
/*     */       {
/* 178 */         return -1;
/*     */       }
/*     */       
/* 181 */       int off = offset;
/*     */       
/*     */       do
/*     */       {
/* 185 */         buffer[(offset++)] = ((char)ch);
/*     */         
/* 187 */         length--; } while ((length > 0) && ((ch = read()) != -1));
/*     */       
/* 189 */       return offset - off;
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
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 210 */     synchronized (this.lock)
/*     */     {
/* 212 */       while ((!this.eof) && 
/*     */       
/* 214 */         (read() != -1)) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 219 */       this.eof = true;
/* 220 */       this.atBeginning = false;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String readLine()
/*     */     throws IOException
/*     */   {
/* 232 */     StringBuilder sb = new StringBuilder();
/*     */     
/* 234 */     synchronized (this.lock) { int intch;
/* 235 */       while ((intch = read()) != -1) {
/*     */         int intch;
/* 237 */         if ((intch == 10) && (this.atBeginning)) {
/* 238 */           return sb.substring(0, sb.length() - 1);
/*     */         }
/* 240 */         sb.append((char)intch);
/*     */       } }
/*     */     int intch;
/* 243 */     String string = sb.toString();
/* 244 */     if (string.length() == 0) {
/* 245 */       return null;
/*     */     }
/*     */     
/*     */ 
/* 249 */     return string;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\io\DotTerminatedMessageReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */