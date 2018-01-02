/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
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
/*     */ public class UnixFTPEntryParser
/*     */   extends ConfigurableFTPFileEntryParserImpl
/*     */ {
/*     */   static final String DEFAULT_DATE_FORMAT = "MMM d yyyy";
/*     */   static final String DEFAULT_RECENT_DATE_FORMAT = "MMM d HH:mm";
/*     */   static final String NUMERIC_DATE_FORMAT = "yyyy-MM-dd HH:mm";
/*  61 */   public static final FTPClientConfig NUMERIC_DATE_CONFIG = new FTPClientConfig(
/*  62 */     "UNIX", 
/*  63 */     "yyyy-MM-dd HH:mm", 
/*  64 */     null, null, null, null);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static final String REGEX = "([bcdelfmpSs-])(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s*(\\d+)\\s+(?:(\\S+(?:\\s\\S+)*?)\\s+)?(?:(\\S+(?:\\s\\S+)*)\\s+)?(\\d+(?:,\\s*\\d+)?)\\s+((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3}))\\s+(\\d+(?::\\d+)?)\\s+(\\S*)(\\s*.*)";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UnixFTPEntryParser()
/*     */   {
/* 121 */     this(null);
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
/*     */   public UnixFTPEntryParser(FTPClientConfig config)
/*     */   {
/* 138 */     super("([bcdelfmpSs-])(((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-]))((r|-)(w|-)([xsStTL-])))\\+?\\s*(\\d+)\\s+(?:(\\S+(?:\\s\\S+)*?)\\s+)?(?:(\\S+(?:\\s\\S+)*)\\s+)?(\\d+(?:,\\s*\\d+)?)\\s+((?:\\d+[-/]\\d+[-/]\\d+)|(?:\\S{3}\\s+\\d{1,2})|(?:\\d{1,2}\\s+\\S{3}))\\s+(\\d+(?::\\d+)?)\\s+(\\S*)(\\s*.*)");
/* 139 */     configure(config);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public List<String> preParse(List<String> original)
/*     */   {
/* 147 */     ListIterator<String> iter = original.listIterator();
/* 148 */     while (iter.hasNext()) {
/* 149 */       String entry = (String)iter.next();
/* 150 */       if (entry.matches("^total \\d+$")) {
/* 151 */         iter.remove();
/*     */       }
/*     */     }
/* 154 */     return original;
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
/*     */   public FTPFile parseFTPEntry(String entry)
/*     */   {
/* 169 */     FTPFile file = new FTPFile();
/* 170 */     file.setRawListing(entry);
/*     */     
/* 172 */     boolean isDevice = false;
/*     */     
/* 174 */     if (matches(entry))
/*     */     {
/* 176 */       String typeStr = group(1);
/* 177 */       String hardLinkCount = group(15);
/* 178 */       String usr = group(16);
/* 179 */       String grp = group(17);
/* 180 */       String filesize = group(18);
/* 181 */       String datestr = group(19) + " " + group(20);
/* 182 */       String name = group(21);
/* 183 */       String endtoken = group(22);
/*     */       
/*     */       try
/*     */       {
/* 187 */         file.setTimestamp(super.parseTimestamp(datestr));
/*     */       }
/*     */       catch (ParseException localParseException) {}
/*     */       
/*     */       int type;
/*     */       
/*     */       int type;
/*     */       int type;
/*     */       int type;
/*     */       int type;
/*     */       int type;
/* 198 */       switch (typeStr.charAt(0))
/*     */       {
/*     */       case 'd': 
/* 201 */         type = 1;
/* 202 */         break;
/*     */       case 'e': 
/* 204 */         type = 2;
/* 205 */         break;
/*     */       case 'l': 
/* 207 */         type = 2;
/* 208 */         break;
/*     */       case 'b': 
/*     */       case 'c': 
/* 211 */         isDevice = true;
/* 212 */         type = 0;
/* 213 */         break;
/*     */       case '-': 
/*     */       case 'f': 
/* 216 */         type = 0;
/* 217 */         break;
/*     */       default: 
/* 219 */         type = 3;
/*     */       }
/*     */       
/* 222 */       file.setType(type);
/*     */       
/* 224 */       int g = 4;
/* 225 */       for (int access = 0; access < 3; g += 4)
/*     */       {
/*     */ 
/* 228 */         file.setPermission(access, 0, 
/* 229 */           !group(g).equals("-"));
/* 230 */         file.setPermission(access, 1, 
/* 231 */           !group(g + 1).equals("-"));
/*     */         
/* 233 */         String execPerm = group(g + 2);
/* 234 */         if ((!execPerm.equals("-")) && (!Character.isUpperCase(execPerm.charAt(0))))
/*     */         {
/* 236 */           file.setPermission(access, 2, true);
/*     */         }
/*     */         else
/*     */         {
/* 240 */           file.setPermission(access, 2, false);
/*     */         }
/* 225 */         access++;
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 244 */       if (!isDevice)
/*     */       {
/*     */         try
/*     */         {
/* 248 */           file.setHardLinkCount(Integer.parseInt(hardLinkCount));
/*     */         }
/*     */         catch (NumberFormatException localNumberFormatException) {}
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 256 */       file.setUser(usr);
/* 257 */       file.setGroup(grp);
/*     */       
/*     */       try
/*     */       {
/* 261 */         file.setSize(Long.parseLong(filesize));
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException1) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 268 */       if (endtoken == null)
/*     */       {
/* 270 */         file.setName(name);
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/* 276 */         name = name + endtoken;
/* 277 */         if (type == 2)
/*     */         {
/*     */ 
/* 280 */           int end = name.indexOf(" -> ");
/*     */           
/* 282 */           if (end == -1)
/*     */           {
/* 284 */             file.setName(name);
/*     */           }
/*     */           else
/*     */           {
/* 288 */             file.setName(name.substring(0, end));
/* 289 */             file.setLink(name.substring(end + 4));
/*     */           }
/*     */           
/*     */         }
/*     */         else
/*     */         {
/* 295 */           file.setName(name);
/*     */         }
/*     */       }
/* 298 */       return file;
/*     */     }
/* 300 */     return null;
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
/* 311 */     return new FTPClientConfig(
/* 312 */       "UNIX", 
/* 313 */       "MMM d yyyy", 
/* 314 */       "MMM d HH:mm", 
/* 315 */       null, null, null);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\UnixFTPEntryParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */