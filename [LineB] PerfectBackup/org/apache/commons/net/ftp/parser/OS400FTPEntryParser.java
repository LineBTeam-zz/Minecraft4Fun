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
/*     */ public class OS400FTPEntryParser
/*     */   extends ConfigurableFTPFileEntryParserImpl
/*     */ {
/*     */   private static final String DEFAULT_DATE_FORMAT = "yy/MM/dd HH:mm:ss";
/*     */   private static final String REGEX = "(\\S+)\\s+(\\d+)\\s+(\\S+)\\s+(\\S+)\\s+(\\*\\S+)\\s+(\\S+/?)\\s*";
/*     */   
/*     */   public OS400FTPEntryParser()
/*     */   {
/*  54 */     this(null);
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
/*     */   public OS400FTPEntryParser(FTPClientConfig config)
/*     */   {
/*  71 */     super("(\\S+)\\s+(\\d+)\\s+(\\S+)\\s+(\\S+)\\s+(\\*\\S+)\\s+(\\S+/?)\\s*");
/*  72 */     configure(config);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FTPFile parseFTPEntry(String entry)
/*     */   {
/*  80 */     FTPFile file = new FTPFile();
/*  81 */     file.setRawListing(entry);
/*     */     
/*     */ 
/*  84 */     if (matches(entry))
/*     */     {
/*  86 */       String usr = group(1);
/*  87 */       String filesize = group(2);
/*  88 */       String datestr = group(3) + " " + group(4);
/*  89 */       String typeStr = group(5);
/*  90 */       String name = group(6);
/*     */       
/*     */       try
/*     */       {
/*  94 */         file.setTimestamp(super.parseTimestamp(datestr));
/*     */       }
/*     */       catch (ParseException localParseException) {}
/*     */       
/*     */       int type;
/*     */       
/*     */       int type;
/*     */       
/* 102 */       if (typeStr.equalsIgnoreCase("*STMF"))
/*     */       {
/* 104 */         type = 0;
/*     */       } else { int type;
/* 106 */         if (typeStr.equalsIgnoreCase("*DIR"))
/*     */         {
/* 108 */           type = 1;
/*     */         }
/*     */         else
/*     */         {
/* 112 */           type = 3;
/*     */         }
/*     */       }
/* 115 */       file.setType(type);
/*     */       
/* 117 */       file.setUser(usr);
/*     */       
/*     */       try
/*     */       {
/* 121 */         file.setSize(Long.parseLong(filesize));
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 128 */       if (name.endsWith("/"))
/*     */       {
/* 130 */         name = name.substring(0, name.length() - 1);
/*     */       }
/* 132 */       int pos = name.lastIndexOf('/');
/* 133 */       if (pos > -1)
/*     */       {
/* 135 */         name = name.substring(pos + 1);
/*     */       }
/*     */       
/* 138 */       file.setName(name);
/*     */       
/* 140 */       return file;
/*     */     }
/* 142 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected FTPClientConfig getDefaultConfiguration()
/*     */   {
/* 153 */     return new FTPClientConfig(
/* 154 */       "OS/400", 
/* 155 */       "yy/MM/dd HH:mm:ss", 
/* 156 */       null, null, null, null);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\OS400FTPEntryParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */