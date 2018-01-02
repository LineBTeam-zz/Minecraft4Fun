/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.text.ParseException;
/*     */ import java.util.StringTokenizer;
/*     */ import org.apache.commons.net.ftp.FTPClientConfig;
/*     */ import org.apache.commons.net.ftp.FTPFile;
/*     */ import org.apache.commons.net.ftp.FTPListParseEngine;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VMSFTPEntryParser
/*     */   extends ConfigurableFTPFileEntryParserImpl
/*     */ {
/*     */   private static final String DEFAULT_DATE_FORMAT = "d-MMM-yyyy HH:mm:ss";
/*     */   private static final String REGEX = "(.*;[0-9]+)\\s*(\\d+)/\\d+\\s*(\\S+)\\s+(\\S+)\\s+\\[(([0-9$A-Za-z_]+)|([0-9$A-Za-z_]+),([0-9$a-zA-Z_]+))\\]?\\s*\\([a-zA-Z]*,([a-zA-Z]*),([a-zA-Z]*),([a-zA-Z]*)\\)";
/*     */   
/*     */   public VMSFTPEntryParser()
/*     */   {
/*  78 */     this(null);
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
/*     */   public VMSFTPEntryParser(FTPClientConfig config)
/*     */   {
/*  95 */     super("(.*;[0-9]+)\\s*(\\d+)/\\d+\\s*(\\S+)\\s+(\\S+)\\s+\\[(([0-9$A-Za-z_]+)|([0-9$A-Za-z_]+),([0-9$a-zA-Z_]+))\\]?\\s*\\([a-zA-Z]*,([a-zA-Z]*),([a-zA-Z]*),([a-zA-Z]*)\\)");
/*  96 */     configure(config);
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
/* 113 */     long longBlock = 512L;
/*     */     
/* 115 */     if (matches(entry))
/*     */     {
/* 117 */       FTPFile f = new FTPFile();
/* 118 */       f.setRawListing(entry);
/* 119 */       String name = group(1);
/* 120 */       String size = group(2);
/* 121 */       String datestr = group(3) + " " + group(4);
/* 122 */       String owner = group(5);
/* 123 */       String[] permissions = new String[3];
/* 124 */       permissions[0] = group(9);
/* 125 */       permissions[1] = group(10);
/* 126 */       permissions[2] = group(11);
/*     */       try
/*     */       {
/* 129 */         f.setTimestamp(super.parseTimestamp(datestr));
/*     */       }
/*     */       catch (ParseException localParseException) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 139 */       StringTokenizer t = new StringTokenizer(owner, ",");
/* 140 */       String user; String user; String grp; String user; switch (t.countTokens()) {
/*     */       case 1: 
/* 142 */         String grp = null;
/* 143 */         user = t.nextToken();
/* 144 */         break;
/*     */       case 2: 
/* 146 */         String grp = t.nextToken();
/* 147 */         user = t.nextToken();
/* 148 */         break;
/*     */       default: 
/* 150 */         grp = null;
/* 151 */         user = null;
/*     */       }
/*     */       
/* 154 */       if (name.lastIndexOf(".DIR") != -1)
/*     */       {
/* 156 */         f.setType(1);
/*     */       }
/*     */       else
/*     */       {
/* 160 */         f.setType(0);
/*     */       }
/*     */       
/*     */ 
/* 164 */       if (isVersioning())
/*     */       {
/* 166 */         f.setName(name);
/*     */       }
/*     */       else
/*     */       {
/* 170 */         name = name.substring(0, name.lastIndexOf(";"));
/* 171 */         f.setName(name);
/*     */       }
/*     */       
/*     */ 
/* 175 */       long sizeInBytes = Long.parseLong(size) * longBlock;
/* 176 */       f.setSize(sizeInBytes);
/*     */       
/* 178 */       f.setGroup(grp);
/* 179 */       f.setUser(user);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 187 */       for (int access = 0; access < 3; access++)
/*     */       {
/* 189 */         String permission = permissions[access];
/*     */         
/* 191 */         f.setPermission(access, 0, permission.indexOf('R') >= 0);
/* 192 */         f.setPermission(access, 1, permission.indexOf('W') >= 0);
/* 193 */         f.setPermission(access, 2, permission.indexOf('E') >= 0);
/*     */       }
/*     */       
/* 196 */       return f;
/*     */     }
/* 198 */     return null;
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
/*     */   public String readNextEntry(BufferedReader reader)
/*     */     throws IOException
/*     */   {
/* 217 */     String line = reader.readLine();
/* 218 */     StringBuilder entry = new StringBuilder();
/* 219 */     while (line != null)
/*     */     {
/* 221 */       if ((line.startsWith("Directory")) || (line.startsWith("Total"))) {
/* 222 */         line = reader.readLine();
/*     */       }
/*     */       else
/*     */       {
/* 226 */         entry.append(line);
/* 227 */         if (line.trim().endsWith(")")) {
/*     */           break;
/*     */         }
/*     */         
/* 231 */         line = reader.readLine();
/*     */       } }
/* 233 */     return entry.length() == 0 ? null : entry.toString();
/*     */   }
/*     */   
/*     */   protected boolean isVersioning() {
/* 237 */     return false;
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
/* 248 */     return new FTPClientConfig(
/* 249 */       "VMS", 
/* 250 */       "d-MMM-yyyy HH:mm:ss", 
/* 251 */       null, null, null, null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public FTPFile[] parseFileList(InputStream listStream)
/*     */     throws IOException
/*     */   {
/* 262 */     FTPListParseEngine engine = new FTPListParseEngine(this);
/* 263 */     engine.readServerList(listStream, null);
/* 264 */     return engine.getFiles();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\VMSFTPEntryParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */