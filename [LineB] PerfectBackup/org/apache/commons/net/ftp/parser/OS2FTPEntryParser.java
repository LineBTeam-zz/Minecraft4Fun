/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import org.apache.commons.net.ftp.FTPClientConfig;
/*     */ import org.apache.commons.net.ftp.FTPFile;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class OS2FTPEntryParser
/*     */   extends ConfigurableFTPFileEntryParserImpl
/*     */ {
/*     */   private static final String DEFAULT_DATE_FORMAT = "MM-dd-yy HH:mm";
/*     */   private static final String REGEX = "\\s*([0-9]+)\\s*(\\s+|[A-Z]+)\\s*(DIR|\\s+)\\s*(\\S+)\\s+(\\S+)\\s+(\\S.*)";
/*     */   
/*     */   public OS2FTPEntryParser()
/*     */   {
/*  58 */     this(null);
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
/*     */   public OS2FTPEntryParser(FTPClientConfig config)
/*     */   {
/*  75 */     super("\\s*([0-9]+)\\s*(\\s+|[A-Z]+)\\s*(DIR|\\s+)\\s*(\\S+)\\s+(\\S+)\\s+(\\S.*)");
/*  76 */     configure(config);
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
/*     */   public FTPFile parseFTPEntry(String entry)
/*     */   {
/*  93 */     FTPFile f = new FTPFile();
/*  94 */     if (matches(entry))
/*     */     {
/*  96 */       String size = group(1);
/*  97 */       String attrib = group(2);
/*  98 */       String dirString = group(3);
/*  99 */       String datestr = group(4) + " " + group(5);
/* 100 */       String name = group(6);
/*     */       try
/*     */       {
/* 103 */         f.setTimestamp(super.parseTimestamp(datestr));
/*     */       }
/*     */       catch (ParseException localParseException) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 112 */       if ((dirString.trim().equals("DIR")) || (attrib.trim().equals("DIR")))
/*     */       {
/* 114 */         f.setType(1);
/*     */       }
/*     */       else
/*     */       {
/* 118 */         f.setType(0);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/* 123 */       f.setName(name.trim());
/*     */       
/*     */ 
/* 126 */       f.setSize(Long.parseLong(size.trim()));
/*     */       
/* 128 */       return f;
/*     */     }
/* 130 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected FTPClientConfig getDefaultConfiguration()
/*     */   {
/* 142 */     return new FTPClientConfig(
/* 143 */       "OS/2", 
/* 144 */       "MM-dd-yy HH:mm", 
/* 145 */       null, null, null, null);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\OS2FTPEntryParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */