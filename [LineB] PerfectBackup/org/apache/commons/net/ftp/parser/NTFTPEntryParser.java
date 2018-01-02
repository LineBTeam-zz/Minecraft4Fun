/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import org.apache.commons.net.ftp.Configurable;
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
/*     */ 
/*     */ public class NTFTPEntryParser
/*     */   extends ConfigurableFTPFileEntryParserImpl
/*     */ {
/*     */   private static final String DEFAULT_DATE_FORMAT = "MM-dd-yy hh:mma";
/*     */   private static final String DEFAULT_DATE_FORMAT2 = "MM-dd-yy kk:mm";
/*     */   private final FTPTimestampParser timestampParser;
/*     */   private static final String REGEX = "(\\S+)\\s+(\\S+)\\s+(?:(<DIR>)|([0-9]+))\\s+(\\S.*)";
/*     */   
/*     */   public NTFTPEntryParser()
/*     */   {
/*  62 */     this(null);
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
/*     */   public NTFTPEntryParser(FTPClientConfig config)
/*     */   {
/*  79 */     super("(\\S+)\\s+(\\S+)\\s+(?:(<DIR>)|([0-9]+))\\s+(\\S.*)");
/*  80 */     configure(config);
/*  81 */     FTPClientConfig config2 = new FTPClientConfig(
/*  82 */       "WINDOWS", 
/*  83 */       "MM-dd-yy kk:mm", 
/*  84 */       null, null, null, null);
/*  85 */     config2.setDefaultDateFormatStr("MM-dd-yy kk:mm");
/*  86 */     this.timestampParser = new FTPTimestampParserImpl();
/*  87 */     ((Configurable)this.timestampParser).configure(config2);
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
/*     */   public FTPFile parseFTPEntry(String entry)
/*     */   {
/* 103 */     FTPFile f = new FTPFile();
/* 104 */     f.setRawListing(entry);
/*     */     
/* 106 */     if (matches(entry))
/*     */     {
/* 108 */       String datestr = group(1) + " " + group(2);
/* 109 */       String dirString = group(3);
/* 110 */       String size = group(4);
/* 111 */       String name = group(5);
/*     */       try
/*     */       {
/* 114 */         f.setTimestamp(super.parseTimestamp(datestr));
/*     */ 
/*     */       }
/*     */       catch (ParseException e)
/*     */       {
/*     */         try
/*     */         {
/* 121 */           f.setTimestamp(this.timestampParser.parseTimestamp(datestr));
/*     */         }
/*     */         catch (ParseException localParseException1) {}
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 129 */       if ((name == null) || (name.equals(".")) || (name.equals("..")))
/*     */       {
/* 131 */         return null;
/*     */       }
/* 133 */       f.setName(name);
/*     */       
/*     */ 
/* 136 */       if ("<DIR>".equals(dirString))
/*     */       {
/* 138 */         f.setType(1);
/* 139 */         f.setSize(0L);
/*     */       }
/*     */       else
/*     */       {
/* 143 */         f.setType(0);
/* 144 */         if (size != null)
/*     */         {
/* 146 */           f.setSize(Long.parseLong(size));
/*     */         }
/*     */       }
/* 149 */       return f;
/*     */     }
/* 151 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FTPClientConfig getDefaultConfiguration()
/*     */   {
/* 162 */     return new FTPClientConfig(
/* 163 */       "WINDOWS", 
/* 164 */       "MM-dd-yy hh:mma", 
/* 165 */       null, null, null, null);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\NTFTPEntryParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */