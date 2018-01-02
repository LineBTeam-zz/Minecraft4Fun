/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.text.ParseException;
/*     */ import java.util.Calendar;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ConfigurableFTPFileEntryParserImpl
/*     */   extends RegexFTPFileEntryParserImpl
/*     */   implements Configurable
/*     */ {
/*     */   private final FTPTimestampParser timestampParser;
/*     */   
/*     */   public ConfigurableFTPFileEntryParserImpl(String regex)
/*     */   {
/*  59 */     super(regex);
/*  60 */     this.timestampParser = new FTPTimestampParserImpl();
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
/*     */   public Calendar parseTimestamp(String timestampStr)
/*     */     throws ParseException
/*     */   {
/*  74 */     return this.timestampParser.parseTimestamp(timestampStr);
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
/*     */   public void configure(FTPClientConfig config)
/*     */   {
/*  93 */     if ((this.timestampParser instanceof Configurable)) {
/*  94 */       FTPClientConfig defaultCfg = getDefaultConfiguration();
/*  95 */       if (config != null) {
/*  96 */         if (config.getDefaultDateFormatStr() == null) {
/*  97 */           config.setDefaultDateFormatStr(defaultCfg.getDefaultDateFormatStr());
/*     */         }
/*  99 */         if (config.getRecentDateFormatStr() == null) {
/* 100 */           config.setRecentDateFormatStr(defaultCfg.getRecentDateFormatStr());
/*     */         }
/* 102 */         ((Configurable)this.timestampParser).configure(config);
/*     */       } else {
/* 104 */         ((Configurable)this.timestampParser).configure(defaultCfg);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected abstract FTPClientConfig getDefaultConfiguration();
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\ConfigurableFTPFileEntryParserImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */