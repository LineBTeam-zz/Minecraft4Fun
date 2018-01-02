/*     */ package org.apache.commons.net.ftp;
/*     */ 
/*     */ import java.text.DateFormatSymbols;
/*     */ import java.util.Collection;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.StringTokenizer;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FTPClientConfig
/*     */ {
/*     */   public static final String SYST_UNIX = "UNIX";
/*     */   public static final String SYST_VMS = "VMS";
/*     */   public static final String SYST_NT = "WINDOWS";
/*     */   public static final String SYST_OS2 = "OS/2";
/*     */   public static final String SYST_OS400 = "OS/400";
/*     */   public static final String SYST_AS400 = "AS/400";
/*     */   public static final String SYST_MVS = "MVS";
/*     */   public static final String SYST_L8 = "TYPE: L8";
/*     */   public static final String SYST_NETWARE = "NETWARE";
/*     */   public static final String SYST_MACOS_PETER = "MACOS PETER";
/*     */   private final String serverSystemKey;
/* 218 */   private String defaultDateFormatStr = null;
/* 219 */   private String recentDateFormatStr = null;
/* 220 */   private boolean lenientFutureDates = true;
/* 221 */   private String serverLanguageCode = null;
/* 222 */   private String shortMonthNames = null;
/* 223 */   private String serverTimeZoneId = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FTPClientConfig(String systemKey)
/*     */   {
/* 232 */     this.serverSystemKey = systemKey;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public FTPClientConfig()
/*     */   {
/* 240 */     this("UNIX");
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
/*     */   public FTPClientConfig(String systemKey, String defaultDateFormatStr, String recentDateFormatStr, String serverLanguageCode, String shortMonthNames, String serverTimeZoneId)
/*     */   {
/* 266 */     this(systemKey);
/*     */     
/*     */ 
/* 269 */     this.serverLanguageCode = serverLanguageCode;
/* 270 */     this.shortMonthNames = shortMonthNames;
/* 271 */     this.serverTimeZoneId = serverTimeZoneId;
/*     */   }
/*     */   
/* 274 */   private static final Map<String, Object> LANGUAGE_CODE_MAP = new TreeMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   static
/*     */   {
/* 284 */     LANGUAGE_CODE_MAP.put("en", Locale.ENGLISH);
/* 285 */     LANGUAGE_CODE_MAP.put("de", Locale.GERMAN);
/* 286 */     LANGUAGE_CODE_MAP.put("it", Locale.ITALIAN);
/* 287 */     LANGUAGE_CODE_MAP.put("es", new Locale("es", "", ""));
/* 288 */     LANGUAGE_CODE_MAP.put("pt", new Locale("pt", "", ""));
/* 289 */     LANGUAGE_CODE_MAP.put("da", new Locale("da", "", ""));
/* 290 */     LANGUAGE_CODE_MAP.put("sv", new Locale("sv", "", ""));
/* 291 */     LANGUAGE_CODE_MAP.put("no", new Locale("no", "", ""));
/* 292 */     LANGUAGE_CODE_MAP.put("nl", new Locale("nl", "", ""));
/* 293 */     LANGUAGE_CODE_MAP.put("ro", new Locale("ro", "", ""));
/* 294 */     LANGUAGE_CODE_MAP.put("sq", new Locale("sq", "", ""));
/* 295 */     LANGUAGE_CODE_MAP.put("sh", new Locale("sh", "", ""));
/* 296 */     LANGUAGE_CODE_MAP.put("sk", new Locale("sk", "", ""));
/* 297 */     LANGUAGE_CODE_MAP.put("sl", new Locale("sl", "", ""));
/*     */     
/*     */ 
/*     */ 
/* 301 */     LANGUAGE_CODE_MAP.put("fr", 
/* 302 */       "jan|fév|mar|avr|mai|jun|jui|aoû|sep|oct|nov|déc");
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
/*     */   public String getServerSystemKey()
/*     */   {
/* 316 */     return this.serverSystemKey;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getDefaultDateFormatStr()
/*     */   {
/* 325 */     return this.defaultDateFormatStr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getRecentDateFormatStr()
/*     */   {
/* 334 */     return this.recentDateFormatStr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getServerTimeZoneId()
/*     */   {
/* 342 */     return this.serverTimeZoneId;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getShortMonthNames()
/*     */   {
/* 353 */     return this.shortMonthNames;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getServerLanguageCode()
/*     */   {
/* 363 */     return this.serverLanguageCode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isLenientFutureDates()
/*     */   {
/* 374 */     return this.lenientFutureDates;
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
/*     */   public void setDefaultDateFormatStr(String defaultDateFormatStr)
/*     */   {
/* 391 */     this.defaultDateFormatStr = defaultDateFormatStr;
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
/*     */   public void setRecentDateFormatStr(String recentDateFormatStr)
/*     */   {
/* 412 */     this.recentDateFormatStr = recentDateFormatStr;
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
/*     */   public void setLenientFutureDates(boolean lenientFutureDates)
/*     */   {
/* 436 */     this.lenientFutureDates = lenientFutureDates;
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
/*     */   public void setServerTimeZoneId(String serverTimeZoneId)
/*     */   {
/* 453 */     this.serverTimeZoneId = serverTimeZoneId;
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
/*     */   public void setShortMonthNames(String shortMonthNames)
/*     */   {
/* 474 */     this.shortMonthNames = shortMonthNames;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setServerLanguageCode(String serverLanguageCode)
/*     */   {
/* 518 */     this.serverLanguageCode = serverLanguageCode;
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
/*     */   public static DateFormatSymbols lookupDateFormatSymbols(String languageCode)
/*     */   {
/* 535 */     Object lang = LANGUAGE_CODE_MAP.get(languageCode);
/* 536 */     if (lang != null) {
/* 537 */       if ((lang instanceof Locale))
/* 538 */         return new DateFormatSymbols((Locale)lang);
/* 539 */       if ((lang instanceof String)) {
/* 540 */         return getDateFormatSymbols((String)lang);
/*     */       }
/*     */     }
/* 543 */     return new DateFormatSymbols(Locale.US);
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
/*     */   public static DateFormatSymbols getDateFormatSymbols(String shortmonths)
/*     */   {
/* 556 */     String[] months = splitShortMonthString(shortmonths);
/* 557 */     DateFormatSymbols dfs = new DateFormatSymbols(Locale.US);
/* 558 */     dfs.setShortMonths(months);
/* 559 */     return dfs;
/*     */   }
/*     */   
/*     */   private static String[] splitShortMonthString(String shortmonths) {
/* 563 */     StringTokenizer st = new StringTokenizer(shortmonths, "|");
/* 564 */     int monthcnt = st.countTokens();
/* 565 */     if (12 != monthcnt) {
/* 566 */       throw new IllegalArgumentException(
/* 567 */         "expecting a pipe-delimited string containing 12 tokens");
/*     */     }
/* 569 */     String[] months = new String[13];
/* 570 */     int pos = 0;
/* 571 */     while (st.hasMoreTokens()) {
/* 572 */       months[(pos++)] = st.nextToken();
/*     */     }
/* 574 */     months[pos] = "";
/* 575 */     return months;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static Collection<String> getSupportedLanguageCodes()
/*     */   {
/* 587 */     return LANGUAGE_CODE_MAP.keySet();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\FTPClientConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */