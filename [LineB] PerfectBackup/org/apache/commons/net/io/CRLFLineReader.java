/*    */ package org.apache.commons.net.io;
/*    */ 
/*    */ import java.io.BufferedReader;
/*    */ import java.io.IOException;
/*    */ import java.io.Reader;
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
/*    */ public final class CRLFLineReader
/*    */   extends BufferedReader
/*    */ {
/*    */   private static final char LF = '\n';
/*    */   private static final char CR = '\r';
/*    */   
/*    */   public CRLFLineReader(Reader reader)
/*    */   {
/* 43 */     super(reader);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public String readLine()
/*    */     throws IOException
/*    */   {
/* 54 */     StringBuilder sb = new StringBuilder();
/*    */     
/* 56 */     boolean prevWasCR = false;
/* 57 */     synchronized (this.lock) { int intch;
/* 58 */       while ((intch = read()) != -1) {
/*    */         int intch;
/* 60 */         if ((prevWasCR) && (intch == 10)) {
/* 61 */           return sb.substring(0, sb.length() - 1);
/*    */         }
/* 63 */         if (intch == 13) {
/* 64 */           prevWasCR = true;
/*    */         } else {
/* 66 */           prevWasCR = false;
/*    */         }
/* 68 */         sb.append((char)intch);
/*    */       } }
/*    */     int intch;
/* 71 */     String string = sb.toString();
/* 72 */     if (string.length() == 0) {
/* 73 */       return null;
/*    */     }
/* 75 */     return string;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\io\CRLFLineReader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */