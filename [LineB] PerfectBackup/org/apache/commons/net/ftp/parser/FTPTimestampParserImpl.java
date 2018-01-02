/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.text.ParseException;
/*     */ import java.text.ParsePosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.commons.net.ftp.Configurable;
/*     */ import org.apache.commons.net.ftp.FTPClientConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FTPTimestampParserImpl
/*     */   implements FTPTimestampParser, Configurable
/*     */ {
/*     */   private SimpleDateFormat defaultDateFormat;
/*     */   private SimpleDateFormat recentDateFormat;
/*  46 */   private boolean lenientFutureDates = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FTPTimestampParserImpl()
/*     */   {
/*  53 */     setDefaultDateFormat("MMM d yyyy");
/*  54 */     setRecentDateFormat("MMM d HH:mm");
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
/*     */   public Calendar parseTimestamp(String timestampStr)
/*     */     throws ParseException
/*     */   {
/*  75 */     Calendar now = Calendar.getInstance();
/*  76 */     return parseTimestamp(timestampStr, now);
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
/*     */   public Calendar parseTimestamp(String timestampStr, Calendar serverTime)
/*     */     throws ParseException
/*     */   {
/*  94 */     Calendar working = (Calendar)serverTime.clone();
/*  95 */     working.setTimeZone(getServerTimeZone());
/*     */     
/*  97 */     Date parsed = null;
/*     */     
/*  99 */     if (this.recentDateFormat != null) {
/* 100 */       Calendar now = (Calendar)serverTime.clone();
/* 101 */       now.setTimeZone(getServerTimeZone());
/* 102 */       if (this.lenientFutureDates)
/*     */       {
/*     */ 
/* 105 */         now.add(5, 1);
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
/* 116 */       String year = Integer.toString(now.get(1));
/* 117 */       String timeStampStrPlusYear = timestampStr + " " + year;
/* 118 */       SimpleDateFormat hackFormatter = new SimpleDateFormat(this.recentDateFormat.toPattern() + " yyyy", 
/* 119 */         this.recentDateFormat.getDateFormatSymbols());
/* 120 */       hackFormatter.setLenient(false);
/* 121 */       hackFormatter.setTimeZone(this.recentDateFormat.getTimeZone());
/* 122 */       ParsePosition pp = new ParsePosition(0);
/* 123 */       parsed = hackFormatter.parse(timeStampStrPlusYear, pp);
/*     */       
/* 125 */       if ((parsed != null) && (pp.getIndex() == timeStampStrPlusYear.length())) {
/* 126 */         working.setTime(parsed);
/* 127 */         if (working.after(now)) {
/* 128 */           working.add(1, -1);
/*     */         }
/* 130 */         return working;
/*     */       }
/*     */     }
/*     */     
/* 134 */     ParsePosition pp = new ParsePosition(0);
/* 135 */     parsed = this.defaultDateFormat.parse(timestampStr, pp);
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 143 */     if ((parsed != null) && (pp.getIndex() == timestampStr.length())) {
/* 144 */       working.setTime(parsed);
/*     */     } else {
/* 146 */       throw new ParseException(
/* 147 */         "Timestamp '" + timestampStr + "' could not be parsed using a server time of " + 
/* 148 */         serverTime.getTime().toString(), 
/* 149 */         pp.getErrorIndex());
/*     */     }
/* 151 */     return working;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public SimpleDateFormat getDefaultDateFormat()
/*     */   {
/* 158 */     return this.defaultDateFormat;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDefaultDateFormatString()
/*     */   {
/* 164 */     return this.defaultDateFormat.toPattern();
/*     */   }
/*     */   
/*     */ 
/*     */   private void setDefaultDateFormat(String format)
/*     */   {
/* 170 */     if (format != null) {
/* 171 */       this.defaultDateFormat = new SimpleDateFormat(format);
/* 172 */       this.defaultDateFormat.setLenient(false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public SimpleDateFormat getRecentDateFormat()
/*     */   {
/* 179 */     return this.recentDateFormat;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getRecentDateFormatString()
/*     */   {
/* 185 */     return this.recentDateFormat.toPattern();
/*     */   }
/*     */   
/*     */ 
/*     */   private void setRecentDateFormat(String format)
/*     */   {
/* 191 */     if (format != null) {
/* 192 */       this.recentDateFormat = new SimpleDateFormat(format);
/* 193 */       this.recentDateFormat.setLenient(false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] getShortMonths()
/*     */   {
/* 202 */     return this.defaultDateFormat.getDateFormatSymbols().getShortMonths();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public TimeZone getServerTimeZone()
/*     */   {
/* 210 */     return this.defaultDateFormat.getTimeZone();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void setServerTimeZone(String serverTimeZoneId)
/*     */   {
/* 219 */     TimeZone serverTimeZone = TimeZone.getDefault();
/* 220 */     if (serverTimeZoneId != null) {
/* 221 */       serverTimeZone = TimeZone.getTimeZone(serverTimeZoneId);
/*     */     }
/* 223 */     this.defaultDateFormat.setTimeZone(serverTimeZone);
/* 224 */     if (this.recentDateFormat != null) {
/* 225 */       this.recentDateFormat.setTimeZone(serverTimeZone);
/*     */     }
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
/*     */   public void configure(FTPClientConfig config)
/*     */   {
/* 253 */     DateFormatSymbols dfs = null;
/*     */     
/* 255 */     String languageCode = config.getServerLanguageCode();
/* 256 */     String shortmonths = config.getShortMonthNames();
/* 257 */     if (shortmonths != null) {
/* 258 */       dfs = FTPClientConfig.getDateFormatSymbols(shortmonths);
/* 259 */     } else if (languageCode != null) {
/* 260 */       dfs = FTPClientConfig.lookupDateFormatSymbols(languageCode);
/*     */     } else {
/* 262 */       dfs = FTPClientConfig.lookupDateFormatSymbols("en");
/*     */     }
/*     */     
/*     */ 
/* 266 */     String recentFormatString = config.getRecentDateFormatStr();
/* 267 */     if (recentFormatString == null) {
/* 268 */       this.recentDateFormat = null;
/*     */     } else {
/* 270 */       this.recentDateFormat = new SimpleDateFormat(recentFormatString, dfs);
/* 271 */       this.recentDateFormat.setLenient(false);
/*     */     }
/*     */     
/* 274 */     String defaultFormatString = config.getDefaultDateFormatStr();
/* 275 */     if (defaultFormatString == null) {
/* 276 */       throw new IllegalArgumentException("defaultFormatString cannot be null");
/*     */     }
/* 278 */     this.defaultDateFormat = new SimpleDateFormat(defaultFormatString, dfs);
/* 279 */     this.defaultDateFormat.setLenient(false);
/*     */     
/* 281 */     setServerTimeZone(config.getServerTimeZoneId());
/*     */     
/* 283 */     this.lenientFutureDates = config.isLenientFutureDates();
/*     */   }
/*     */   
/*     */ 
/*     */   boolean isLenientFutureDates()
/*     */   {
/* 289 */     return this.lenientFutureDates;
/*     */   }
/*     */   
/*     */ 
/*     */   void setLenientFutureDates(boolean lenientFutureDates)
/*     */   {
/* 295 */     this.lenientFutureDates = lenientFutureDates;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\FTPTimestampParserImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */