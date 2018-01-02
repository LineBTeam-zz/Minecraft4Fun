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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NetwareFTPEntryParser
/*     */   extends ConfigurableFTPFileEntryParserImpl
/*     */ {
/*     */   private static final String DEFAULT_DATE_FORMAT = "MMM dd yyyy";
/*     */   private static final String DEFAULT_RECENT_DATE_FORMAT = "MMM dd HH:mm";
/*     */   private static final String REGEX = "(d|-){1}\\s+\\[(.*)\\]\\s+(\\S+)\\s+(\\d+)\\s+(\\S+\\s+\\S+\\s+((\\d+:\\d+)|(\\d{4})))\\s+(.*)";
/*     */   
/*     */   public NetwareFTPEntryParser()
/*     */   {
/*  67 */     this(null);
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
/*     */   public NetwareFTPEntryParser(FTPClientConfig config)
/*     */   {
/*  83 */     super("(d|-){1}\\s+\\[(.*)\\]\\s+(\\S+)\\s+(\\d+)\\s+(\\S+\\s+\\S+\\s+((\\d+:\\d+)|(\\d{4})))\\s+(.*)");
/*  84 */     configure(config);
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
/*     */   public FTPFile parseFTPEntry(String entry)
/*     */   {
/* 116 */     FTPFile f = new FTPFile();
/* 117 */     if (matches(entry)) {
/* 118 */       String dirString = group(1);
/* 119 */       String attrib = group(2);
/* 120 */       String user = group(3);
/* 121 */       String size = group(4);
/* 122 */       String datestr = group(5);
/* 123 */       String name = group(9);
/*     */       try
/*     */       {
/* 126 */         f.setTimestamp(super.parseTimestamp(datestr));
/*     */       }
/*     */       catch (ParseException localParseException) {}
/*     */       
/*     */ 
/*     */ 
/* 132 */       if (dirString.trim().equals("d")) {
/* 133 */         f.setType(1);
/*     */       }
/*     */       else {
/* 136 */         f.setType(0);
/*     */       }
/*     */       
/* 139 */       f.setUser(user);
/*     */       
/*     */ 
/* 142 */       f.setName(name.trim());
/*     */       
/*     */ 
/* 145 */       f.setSize(Long.parseLong(size.trim()));
/*     */       
/*     */ 
/*     */ 
/* 149 */       if (attrib.indexOf("R") != -1) {
/* 150 */         f.setPermission(0, 0, 
/* 151 */           true);
/*     */       }
/* 153 */       if (attrib.indexOf("W") != -1) {
/* 154 */         f.setPermission(0, 1, 
/* 155 */           true);
/*     */       }
/*     */       
/* 158 */       return f;
/*     */     }
/* 160 */     return null;
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
/* 172 */     return new FTPClientConfig("NETWARE", 
/* 173 */       "MMM dd yyyy", "MMM dd HH:mm", null, null, 
/* 174 */       null);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\NetwareFTPEntryParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */