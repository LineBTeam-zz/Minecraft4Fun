/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.List;
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
/*     */ public class MVSFTPEntryParser
/*     */   extends ConfigurableFTPFileEntryParserImpl
/*     */ {
/*     */   static final int UNKNOWN_LIST_TYPE = -1;
/*     */   static final int FILE_LIST_TYPE = 0;
/*     */   static final int MEMBER_LIST_TYPE = 1;
/*     */   static final int UNIX_LIST_TYPE = 2;
/*     */   static final int JES_LEVEL_1_LIST_TYPE = 3;
/*     */   static final int JES_LEVEL_2_LIST_TYPE = 4;
/*  47 */   private int isType = -1;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private UnixFTPEntryParser unixFTPEntryParser;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final String DEFAULT_DATE_FORMAT = "yyyy/MM/dd HH:mm";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final String FILE_LIST_REGEX = "\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+[FV]\\S*\\s+\\S+\\s+\\S+\\s+(PS|PO|PO-E)\\s+(\\S+)\\s*";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final String MEMBER_LIST_REGEX = "(\\S+)\\s+\\S+\\s+\\S+\\s+(\\S+)\\s+(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s*";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final String JES_LEVEL_1_LIST_REGEX = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static final String JES_LEVEL_2_LIST_REGEX = "(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+).*";
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public MVSFTPEntryParser()
/*     */   {
/* 217 */     super("");
/* 218 */     super.configure(null);
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
/* 234 */     boolean isParsed = false;
/* 235 */     FTPFile f = new FTPFile();
/*     */     
/* 237 */     if (this.isType == 0) {
/* 238 */       isParsed = parseFileList(f, entry);
/* 239 */     } else if (this.isType == 1) {
/* 240 */       isParsed = parseMemberList(f, entry);
/* 241 */       if (!isParsed) {
/* 242 */         isParsed = parseSimpleEntry(f, entry);
/*     */       }
/* 244 */     } else if (this.isType == 2) {
/* 245 */       isParsed = parseUnixList(f, entry);
/* 246 */     } else if (this.isType == 3) {
/* 247 */       isParsed = parseJeslevel1List(f, entry);
/* 248 */     } else if (this.isType == 4) {
/* 249 */       isParsed = parseJeslevel2List(f, entry);
/*     */     }
/*     */     
/* 252 */     if (!isParsed) {
/* 253 */       f = null;
/*     */     }
/*     */     
/* 256 */     return f;
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
/*     */   private boolean parseFileList(FTPFile file, String entry)
/*     */   {
/* 285 */     if (matches(entry)) {
/* 286 */       file.setRawListing(entry);
/* 287 */       String name = group(2);
/* 288 */       String dsorg = group(1);
/* 289 */       file.setName(name);
/*     */       
/*     */ 
/* 292 */       if ("PS".equals(dsorg)) {
/* 293 */         file.setType(0);
/*     */       }
/* 295 */       else if (("PO".equals(dsorg)) || ("PO-E".equals(dsorg)))
/*     */       {
/* 297 */         file.setType(1);
/*     */       }
/*     */       else {
/* 300 */         return false;
/*     */       }
/*     */       
/* 303 */       return true;
/*     */     }
/*     */     
/* 306 */     return false;
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
/*     */   private boolean parseMemberList(FTPFile file, String entry)
/*     */   {
/* 329 */     if (matches(entry)) {
/* 330 */       file.setRawListing(entry);
/* 331 */       String name = group(1);
/* 332 */       String datestr = group(2) + " " + group(3);
/* 333 */       file.setName(name);
/* 334 */       file.setType(0);
/*     */       try {
/* 336 */         file.setTimestamp(super.parseTimestamp(datestr));
/*     */       } catch (ParseException e) {
/* 338 */         e.printStackTrace();
/*     */         
/*     */ 
/* 341 */         return false;
/*     */       }
/* 343 */       return true;
/*     */     }
/*     */     
/* 346 */     return false;
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
/*     */   private boolean parseSimpleEntry(FTPFile file, String entry)
/*     */   {
/* 359 */     if ((entry != null) && (entry.trim().length() > 0)) {
/* 360 */       file.setRawListing(entry);
/* 361 */       String name = entry.split(" ")[0];
/* 362 */       file.setName(name);
/* 363 */       file.setType(0);
/* 364 */       return true;
/*     */     }
/* 366 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean parseUnixList(FTPFile file, String entry)
/*     */   {
/* 377 */     file = this.unixFTPEntryParser.parseFTPEntry(entry);
/* 378 */     if (file == null) {
/* 379 */       return false;
/*     */     }
/* 381 */     return true;
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
/*     */   private boolean parseJeslevel1List(FTPFile file, String entry)
/*     */   {
/* 399 */     if ((matches(entry)) && 
/* 400 */       (group(3).equalsIgnoreCase("OUTPUT"))) {
/* 401 */       file.setRawListing(entry);
/* 402 */       String name = group(2);
/* 403 */       file.setName(name);
/* 404 */       file.setType(0);
/* 405 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 409 */     return false;
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
/*     */   private boolean parseJeslevel2List(FTPFile file, String entry)
/*     */   {
/* 428 */     if ((matches(entry)) && 
/* 429 */       (group(4).equalsIgnoreCase("OUTPUT"))) {
/* 430 */       file.setRawListing(entry);
/* 431 */       String name = group(2);
/* 432 */       file.setName(name);
/* 433 */       file.setType(0);
/* 434 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 438 */     return false;
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
/*     */   public List<String> preParse(List<String> orig)
/*     */   {
/* 452 */     if ((orig != null) && (orig.size() > 0)) {
/* 453 */       String header = (String)orig.get(0);
/* 454 */       if ((header.indexOf("Volume") >= 0) && (header.indexOf("Dsname") >= 0)) {
/* 455 */         setType(0);
/* 456 */         super.setRegex("\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s+[FV]\\S*\\s+\\S+\\s+\\S+\\s+(PS|PO|PO-E)\\s+(\\S+)\\s*");
/* 457 */       } else if ((header.indexOf("Name") >= 0) && (header.indexOf("Id") >= 0)) {
/* 458 */         setType(1);
/* 459 */         super.setRegex("(\\S+)\\s+\\S+\\s+\\S+\\s+(\\S+)\\s+(\\S+)\\s+\\S+\\s+\\S+\\s+\\S+\\s+\\S+\\s*");
/* 460 */       } else if (header.indexOf("total") == 0) {
/* 461 */         setType(2);
/* 462 */         this.unixFTPEntryParser = new UnixFTPEntryParser();
/* 463 */       } else if (header.indexOf("Spool Files") >= 30) {
/* 464 */         setType(3);
/* 465 */         super.setRegex("(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s*");
/* 466 */       } else if ((header.indexOf("JOBNAME") == 0) && 
/* 467 */         (header.indexOf("JOBID") > 8)) {
/* 468 */         setType(4);
/* 469 */         super.setRegex("(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+)\\s+(\\S+).*");
/*     */       } else {
/* 471 */         setType(-1);
/*     */       }
/*     */       
/* 474 */       if (this.isType != 3) {
/* 475 */         orig.remove(0);
/*     */       }
/*     */     }
/*     */     
/* 479 */     return orig;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   void setType(int type)
/*     */   {
/* 487 */     this.isType = type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   protected FTPClientConfig getDefaultConfiguration()
/*     */   {
/* 495 */     return new FTPClientConfig("MVS", 
/* 496 */       "yyyy/MM/dd HH:mm", null, null, null, null);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\MVSFTPEntryParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */