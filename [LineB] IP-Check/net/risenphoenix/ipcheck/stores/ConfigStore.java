/*     */ package net.risenphoenix.ipcheck.stores;
/*     */ 
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationOption;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationOption.ConfigOptionType;
/*     */ import net.risenphoenix.commons.stores.ConfigurationStore;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigStore
/*     */   extends ConfigurationStore
/*     */ {
/*     */   public ConfigStore(Plugin plugin)
/*     */   {
/*  41 */     super(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */   public void initializeStore()
/*     */   {
/*  47 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.String, "language"));
/*     */     
/*     */ 
/*     */ 
/*  51 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "use-mysql"));
/*     */     
/*     */ 
/*  54 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.String, "dbUsername"));
/*     */     
/*     */ 
/*  57 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.String, "dbPassword"));
/*     */     
/*     */ 
/*  60 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.String, "dbHostname"));
/*     */     
/*     */ 
/*  63 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.String, "dbName"));
/*     */     
/*     */ 
/*  66 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Integer, "dbPort"));
/*     */     
/*     */ 
/*     */ 
/*  70 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "dbGenerated"));
/*     */     
/*     */ 
/*     */ 
/*  74 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "notify-on-login"));
/*     */     
/*     */ 
/*  77 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "warn-on-rejoin-attempt"));
/*     */     
/*     */ 
/*  80 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "descriptive-notice"));
/*     */     
/*     */ 
/*  83 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "secure-mode"));
/*     */     
/*     */ 
/*  86 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "active-mode"));
/*     */     
/*     */ 
/*  89 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "should-ban-on-secure-kick"));
/*     */     
/*     */ 
/*  92 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "should-manage-bans"));
/*     */     
/*     */ 
/*     */ 
/*  96 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Integer, "min-account-notify-threshold"));
/*     */     
/*     */ 
/*  99 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Integer, "secure-kick-threshold"));
/*     */     
/*     */ 
/*     */ 
/* 103 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.String, "secure-kick-message"));
/*     */     
/*     */ 
/* 106 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.String, "ban-message"));
/*     */     
/*     */ 
/* 109 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.String, "kick-message"));
/*     */     
/*     */ 
/*     */ 
/* 113 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "disable-update-detection"));
/*     */     
/*     */ 
/* 116 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "disable-metrics-monitoring"));
/*     */     
/*     */ 
/*     */ 
/* 120 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "use-geoip-services"));
/*     */     
/*     */ 
/* 123 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "allow-geoip-download"));
/*     */     
/*     */ 
/* 126 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "use-country-blacklist"));
/*     */     
/*     */ 
/* 129 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Boolean, "use-blacklist-as-whitelist"));
/*     */     
/*     */ 
/* 132 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.StringList, "country-blacklist"));
/*     */     
/*     */ 
/* 135 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.String, "blocked-message"));
/*     */     
/*     */ 
/* 138 */     add(new ConfigurationOption(ConfigurationOption.ConfigOptionType.Integer, "config-version"));
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\stores\ConfigStore.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */