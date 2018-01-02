/*    */ package org.apache.commons.net.ftp.parser;
/*    */ 
/*    */ import org.apache.commons.net.ftp.FTPFile;
/*    */ import org.apache.commons.net.ftp.FTPFileEntryParser;
/*    */ import org.apache.commons.net.ftp.FTPFileEntryParserImpl;
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
/*    */ public class CompositeFileEntryParser
/*    */   extends FTPFileEntryParserImpl
/*    */ {
/*    */   private final FTPFileEntryParser[] ftpFileEntryParsers;
/*    */   private FTPFileEntryParser cachedFtpFileEntryParser;
/*    */   
/*    */   public CompositeFileEntryParser(FTPFileEntryParser[] ftpFileEntryParsers)
/*    */   {
/* 42 */     this.cachedFtpFileEntryParser = null;
/* 43 */     this.ftpFileEntryParsers = ftpFileEntryParsers;
/*    */   }
/*    */   
/*    */ 
/*    */   public FTPFile parseFTPEntry(String listEntry)
/*    */   {
/* 49 */     if (this.cachedFtpFileEntryParser != null)
/*    */     {
/* 51 */       FTPFile matched = this.cachedFtpFileEntryParser.parseFTPEntry(listEntry);
/* 52 */       if (matched != null)
/*    */       {
/* 54 */         return matched;
/*    */       }
/*    */     }
/*    */     else {
/*    */       FTPFileEntryParser[] arrayOfFTPFileEntryParser;
/* 59 */       int j = (arrayOfFTPFileEntryParser = this.ftpFileEntryParsers).length; for (int i = 0; i < j; i++) { FTPFileEntryParser ftpFileEntryParser = arrayOfFTPFileEntryParser[i];
/*    */         
/* 61 */         FTPFile matched = ftpFileEntryParser.parseFTPEntry(listEntry);
/* 62 */         if (matched != null)
/*    */         {
/* 64 */           this.cachedFtpFileEntryParser = ftpFileEntryParser;
/* 65 */           return matched;
/*    */         }
/*    */       }
/*    */     }
/* 69 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\CompositeFileEntryParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */