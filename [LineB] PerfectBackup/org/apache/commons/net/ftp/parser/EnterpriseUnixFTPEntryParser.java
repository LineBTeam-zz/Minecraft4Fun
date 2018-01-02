/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.util.Calendar;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EnterpriseUnixFTPEntryParser
/*     */   extends RegexFTPFileEntryParserImpl
/*     */ {
/*     */   private static final String MONTHS = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)";
/*     */   private static final String REGEX = "(([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z]))(\\S*)\\s*(\\S+)\\s*(\\S*)\\s*(\\d*)\\s*(\\d*)\\s*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s*((?:[012]\\d*)|(?:3[01]))\\s*((\\d\\d\\d\\d)|((?:[01]\\d)|(?:2[0123])):([012345]\\d))\\s(\\S*)(\\s*.*)";
/*     */   
/*     */   public EnterpriseUnixFTPEntryParser()
/*     */   {
/*  70 */     super("(([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z])([\\-]|[A-Z]))(\\S*)\\s*(\\S+)\\s*(\\S*)\\s*(\\d*)\\s*(\\d*)\\s*(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)\\s*((?:[012]\\d*)|(?:3[01]))\\s*((\\d\\d\\d\\d)|((?:[01]\\d)|(?:2[0123])):([012345]\\d))\\s(\\S*)(\\s*.*)");
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
/*  87 */     FTPFile file = new FTPFile();
/*  88 */     file.setRawListing(entry);
/*     */     
/*  90 */     if (matches(entry))
/*     */     {
/*  92 */       String usr = group(14);
/*  93 */       String grp = group(15);
/*  94 */       String filesize = group(16);
/*  95 */       String mo = group(17);
/*  96 */       String da = group(18);
/*  97 */       String yr = group(20);
/*  98 */       String hr = group(21);
/*  99 */       String min = group(22);
/* 100 */       String name = group(23);
/*     */       
/* 102 */       file.setType(0);
/* 103 */       file.setUser(usr);
/* 104 */       file.setGroup(grp);
/*     */       try
/*     */       {
/* 107 */         file.setSize(Long.parseLong(filesize));
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException) {}
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 114 */       Calendar cal = Calendar.getInstance();
/* 115 */       cal.set(14, 0);
/* 116 */       cal.set(13, 
/* 117 */         0);
/* 118 */       cal.set(12, 
/* 119 */         0);
/* 120 */       cal.set(11, 
/* 121 */         0);
/*     */       
/*     */       try
/*     */       {
/* 125 */         int pos = "(Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec)".indexOf(mo);
/* 126 */         int month = pos / 4;
/* 127 */         if (yr != null)
/*     */         {
/*     */ 
/* 130 */           cal.set(1, 
/* 131 */             Integer.parseInt(yr));
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 136 */           int year = cal.get(1);
/*     */           
/*     */ 
/*     */ 
/* 140 */           if (cal.get(2) < month)
/*     */           {
/* 142 */             year--;
/*     */           }
/* 144 */           cal.set(1, 
/* 145 */             year);
/* 146 */           cal.set(11, 
/* 147 */             Integer.parseInt(hr));
/* 148 */           cal.set(12, 
/* 149 */             Integer.parseInt(min));
/*     */         }
/* 151 */         cal.set(2, 
/* 152 */           month);
/* 153 */         cal.set(5, 
/* 154 */           Integer.parseInt(da));
/* 155 */         file.setTimestamp(cal);
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException1) {}
/*     */       
/*     */ 
/*     */ 
/* 161 */       file.setName(name);
/*     */       
/* 163 */       return file;
/*     */     }
/* 165 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\EnterpriseUnixFTPEntryParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */