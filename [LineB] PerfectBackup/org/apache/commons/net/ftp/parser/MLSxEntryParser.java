/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Locale;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.commons.net.ftp.FTPFile;
/*     */ import org.apache.commons.net.ftp.FTPFileEntryParserImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MLSxEntryParser
/*     */   extends FTPFileEntryParserImpl
/*     */ {
/*  58 */   private static final MLSxEntryParser PARSER = new MLSxEntryParser();
/*     */   
/*  60 */   private static final HashMap<String, Integer> TYPE_TO_INT = new HashMap();
/*     */   
/*  62 */   static { TYPE_TO_INT.put("file", Integer.valueOf(0));
/*  63 */     TYPE_TO_INT.put("cdir", Integer.valueOf(1));
/*  64 */     TYPE_TO_INT.put("pdir", Integer.valueOf(1));
/*  65 */     TYPE_TO_INT.put("dir", Integer.valueOf(1));
/*     */   }
/*     */   
/*  68 */   private static int[] UNIX_GROUPS = {
/*     */   
/*  70 */     0, 1, 
/*  71 */     2 };
/*     */   
/*     */ 
/*  74 */   private static int[][] UNIX_PERMS = {
/*  75 */     new int[0], 
/*  76 */     { 2 }, 
/*  77 */     { 1 }, 
/*  78 */     { 2, 1 }, 
/*  79 */     new int[1], 
/*  80 */     { 0, 2 }, 
/*  81 */     { 0, 1 }, 
/*  82 */     { 0, 1, 2 } };
/*     */   
/*     */ 
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
/*  96 */     String[] parts = entry.split(" ", 2);
/*  97 */     if (parts.length != 2) {
/*  98 */       return null;
/*     */     }
/* 100 */     FTPFile file = new FTPFile();
/* 101 */     file.setRawListing(entry);
/* 102 */     file.setName(parts[1]);
/* 103 */     String[] facts = parts[0].split(";");
/* 104 */     boolean hasUnixMode = parts[0].toLowerCase(Locale.ENGLISH).contains("unix.mode=");
/* 105 */     String[] arrayOfString1; int j = (arrayOfString1 = facts).length; for (int i = 0; i < j; i++) { String fact = arrayOfString1[i];
/* 106 */       String[] factparts = fact.split("=");
/*     */       
/*     */ 
/*     */ 
/* 110 */       if (factparts.length == 2)
/*     */       {
/*     */ 
/* 113 */         String factname = factparts[0].toLowerCase(Locale.ENGLISH);
/* 114 */         String factvalue = factparts[1];
/* 115 */         String valueLowerCase = factvalue.toLowerCase(Locale.ENGLISH);
/* 116 */         if ("size".equals(factname)) {
/* 117 */           file.setSize(Long.parseLong(factvalue));
/*     */         }
/* 119 */         else if ("sizd".equals(factname)) {
/* 120 */           file.setSize(Long.parseLong(factvalue));
/*     */         }
/* 122 */         else if ("modify".equals(factname)) {
/*     */           SimpleDateFormat sdf;
/*     */           SimpleDateFormat sdf;
/* 125 */           if (factvalue.contains(".")) {
/* 126 */             sdf = new SimpleDateFormat("yyyyMMddHHmmss.SSS");
/*     */           } else {
/* 128 */             sdf = new SimpleDateFormat("yyyyMMddHHmmss");
/*     */           }
/* 130 */           TimeZone GMT = TimeZone.getTimeZone("GMT");
/* 131 */           sdf.setTimeZone(GMT);
/* 132 */           GregorianCalendar gc = new GregorianCalendar(GMT);
/*     */           try {
/* 134 */             gc.setTime(sdf.parse(factvalue));
/*     */           }
/*     */           catch (ParseException localParseException) {}
/*     */           
/* 138 */           file.setTimestamp(gc);
/*     */         }
/* 140 */         else if ("type".equals(factname)) {
/* 141 */           Integer intType = (Integer)TYPE_TO_INT.get(valueLowerCase);
/* 142 */           if (intType == null) {
/* 143 */             file.setType(3);
/*     */           } else {
/* 145 */             file.setType(intType.intValue());
/*     */           }
/*     */         }
/* 148 */         else if (factname.startsWith("unix.")) {
/* 149 */           String unixfact = factname.substring("unix.".length()).toLowerCase(Locale.ENGLISH);
/* 150 */           if ("group".equals(unixfact)) {
/* 151 */             file.setGroup(factvalue);
/* 152 */           } else if ("owner".equals(unixfact)) {
/* 153 */             file.setUser(factvalue);
/* 154 */           } else if ("mode".equals(unixfact)) {
/* 155 */             int off = factvalue.length() - 3;
/* 156 */             for (int i = 0; i < 3; i++) {
/* 157 */               int ch = factvalue.charAt(off + i) - '0';
/* 158 */               if ((ch >= 0) && (ch <= 7)) { int[] arrayOfInt;
/* 159 */                 int m = (arrayOfInt = UNIX_PERMS[ch]).length; for (int k = 0; k < m; k++) { int p = arrayOfInt[k];
/* 160 */                   file.setPermission(UNIX_GROUPS[i], p, true);
/*     */                 }
/*     */                 
/*     */               }
/*     */               
/*     */             }
/*     */           }
/*     */         }
/* 168 */         else if ((!hasUnixMode) && ("perm".equals(factname))) {
/* 169 */           doUnixPerms(file, valueLowerCase);
/*     */         }
/*     */       } }
/* 172 */     return file;
/*     */   }
/*     */   
/*     */ 
/*     */   private void doUnixPerms(FTPFile file, String valueLowerCase)
/*     */   {
/*     */     char[] arrayOfChar;
/* 179 */     int j = (arrayOfChar = valueLowerCase.toCharArray()).length; for (int i = 0; i < j; i++) { char c = arrayOfChar[i];
/*     */       
/* 181 */       switch (c) {
/*     */       case 'a': 
/* 183 */         file.setPermission(0, 1, true);
/* 184 */         break;
/*     */       case 'c': 
/* 186 */         file.setPermission(0, 1, true);
/* 187 */         break;
/*     */       case 'd': 
/* 189 */         file.setPermission(0, 1, true);
/* 190 */         break;
/*     */       case 'e': 
/* 192 */         file.setPermission(0, 0, true);
/* 193 */         break;
/*     */       case 'f': 
/*     */         break;
/*     */       
/*     */       case 'l': 
/* 198 */         file.setPermission(0, 2, true);
/* 199 */         break;
/*     */       case 'm': 
/* 201 */         file.setPermission(0, 1, true);
/* 202 */         break;
/*     */       case 'p': 
/* 204 */         file.setPermission(0, 1, true);
/* 205 */         break;
/*     */       case 'r': 
/* 207 */         file.setPermission(0, 0, true);
/* 208 */         break;
/*     */       case 'w': 
/* 210 */         file.setPermission(0, 1, true);
/*     */       }
/*     */       
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static FTPFile parseEntry(String entry)
/*     */   {
/* 220 */     return PARSER.parseFTPEntry(entry);
/*     */   }
/*     */   
/*     */   public static MLSxEntryParser getInstance() {
/* 224 */     return PARSER;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\MLSxEntryParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */