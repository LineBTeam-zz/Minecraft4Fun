/*    */ package com.LineB.xrayinformer;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.FileConfigurationOptions;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Config
/*    */ {
/*    */   private XrayInformer plugin;
/*    */   private FileConfiguration config;
/*    */   public static String defaultWorld;
/*    */   
/*    */   public Config(XrayInformer plugin)
/*    */   {
/* 22 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   public void load()
/*    */   {
/* 27 */     this.plugin.reloadConfig();
/* 28 */     this.config = this.plugin.getConfig();
/*    */     
/* 30 */     this.config.addDefault("logging_plugin", "logblock");
/* 31 */     this.config.addDefault("default_world", "world");
/* 32 */     this.config.addDefault("checkOnPlayerJoin", Boolean.valueOf(true));
/* 33 */     this.config.addDefault("diamond", Boolean.valueOf(true));
/* 34 */     this.config.addDefault("gold", Boolean.valueOf(true));
/* 35 */     this.config.addDefault("lapis", Boolean.valueOf(true));
/* 36 */     this.config.addDefault("iron", Boolean.valueOf(true));
/* 37 */     this.config.addDefault("mossy", Boolean.valueOf(true));
/* 38 */     this.config.addDefault("emerald", Boolean.valueOf(true));
/* 39 */     this.config.addDefault("diamond_warn", Double.valueOf(3.2D));
/* 40 */     this.config.addDefault("diamond_confirmed", Double.valueOf(3.8D));
/* 41 */     this.config.addDefault("gold_warn", Double.valueOf(8.0D));
/* 42 */     this.config.addDefault("gold_confirmed", Double.valueOf(10.0D));
/* 43 */     this.config.addDefault("emerald_warn", Double.valueOf(0.3D));
/* 44 */     this.config.addDefault("emerald_confirmed", Double.valueOf(0.5D));
/* 45 */     this.config.addDefault("lapis_warn", Double.valueOf(3.2D));
/* 46 */     this.config.addDefault("lapis_confirmed", Double.valueOf(3.8D));
/* 47 */     this.config.addDefault("iron_warn", Double.valueOf(40.0D));
/* 48 */     this.config.addDefault("iron_confirmed", Double.valueOf(100.0D));
/* 49 */     this.config.addDefault("mossy_warn", Double.valueOf(40.0D));
/* 50 */     this.config.addDefault("mossy_confirmed", Double.valueOf(100.0D));
/* 51 */     this.config.addDefault("logOreBreaks.diamond", Boolean.valueOf(true));
/* 52 */     this.config.addDefault("logOreBreaks.emerald", Boolean.valueOf(false));
/* 53 */     this.config.addDefault("logOreBreaks.iron", Boolean.valueOf(false));
/* 54 */     this.config.addDefault("logOreBreaks.gold", Boolean.valueOf(false));
/* 55 */     this.config.addDefault("logOreBreaks.lapis", Boolean.valueOf(false));
/* 56 */     this.config.addDefault("logOreBreaks.mossy", Boolean.valueOf(false));
/*    */     
/* 58 */     this.config.options().copyDefaults(true);
/* 59 */     this.plugin.saveConfig();
/*    */     
/* 61 */     defaultWorld = this.config.getString("default_world");
/*    */   }
/*    */   
/*    */   public boolean isActive(String ore)
/*    */   {
/* 66 */     return this.config.getBoolean(ore);
/*    */   }
/*    */   
/*    */   public double getRate(String type, String ore)
/*    */   {
/* 71 */     return this.config.getDouble(ore + "_" + type);
/*    */   }
/*    */   
/*    */   public void setLogger(String logger) {
/* 75 */     this.config.set("logging_plugin", logger.toLowerCase());
/* 76 */     this.plugin.getLogger().info(logger + " detectado e sendo utilizado:");
/* 77 */     this.plugin.saveConfig();
/*    */   }
/*    */ }



 */