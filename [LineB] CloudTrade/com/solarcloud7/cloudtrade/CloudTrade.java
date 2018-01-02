/*     */ package com.solarcloud7.cloudtrade;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class CloudTrade extends org.bukkit.plugin.java.JavaPlugin
/*     */ {
/*  17 */   private final PlayerListener playerListener = new PlayerListener(this);
/*  18 */   private final BlockListener blockListener = new BlockListener(this);
/*  19 */   private final EntityListener entityListener = new EntityListener(this);
/*  20 */   private final CommandListener commandListener = new CommandListener(this);
/*  21 */   private final InventoryListener inventoryListener = new InventoryListener(this);
/*     */   
/*  23 */   private FileConfiguration lang = null;
/*  24 */   private File langFile = null;
/*     */   
/*     */ 
/*  27 */   private static HashMap<String, TradePlayer> traders = new HashMap();
/*  28 */   private static HashMap<String, TradePlayer> requests = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onEnable()
/*     */   {
/*  35 */     saveDefaultConfig();
/*     */     
/*  37 */     PluginManager pm = getServer().getPluginManager();
/*  38 */     pm.registerEvents(this.playerListener, this);
/*  39 */     pm.registerEvents(this.blockListener, this);
/*  40 */     pm.registerEvents(this.entityListener, this);
/*  41 */     pm.registerEvents(this.inventoryListener, this);
/*     */     
/*     */ 
/*  44 */     getCommand("trade").setExecutor(this.commandListener);
/*  45 */     getCommand("tradereload").setExecutor(this.commandListener);
/*     */     
/*     */ 
/*  48 */     getConfig().options().copyDefaults(true);
/*  49 */     saveConfig();
/*     */     
/*     */ 
/*     */ 
/*  53 */     saveDefaultLangConfig();
/*  54 */     reloadLangFile();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onDisable() {}
/*     */   
/*     */ 
/*     */ 
/*     */   public void reloadLangFile()
/*     */   {
/*  65 */     if (this.langFile == null) {
/*  66 */       this.langFile = new File(getDataFolder(), "lang.yml");
/*     */     }
/*  68 */     this.lang = YamlConfiguration.loadConfiguration(this.langFile);
/*     */     
/*     */ 
/*  71 */     InputStream defConfigStream = getResource("lang.yml");
/*  72 */     if (defConfigStream != null) {
/*  73 */       YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(defConfigStream);
/*  74 */       this.lang.setDefaults(defConfig);
/*     */     }
/*  76 */     getLogger().info("Loaded Language File: " + this.lang.getString("LanguageName"));
/*     */   }
/*     */   
/*     */   public FileConfiguration getLangFile() {
/*  80 */     if (this.lang == null) {
/*  81 */       reloadLangFile();
/*     */     }
/*  83 */     return this.lang;
/*     */   }
/*     */   
/*  86 */   public void saveDefaultLangConfig() { if (this.langFile == null) {
/*  87 */       this.langFile = new File(getDataFolder(), "lang.yml");
/*     */     }
/*  89 */     if (!this.langFile.exists()) {
/*  90 */       saveResource("lang.yml", false);
/*     */     }
/*     */   }
/*     */   
/*     */   public void saveLangFile() {
/*  95 */     if ((this.lang == null) || (this.langFile == null)) {
/*  96 */       return;
/*     */     }
/*     */     try {
/*  99 */       getLangFile().save(this.langFile);
/*     */     } catch (IOException ex) {
/* 101 */       getLogger().log(Level.SEVERE, "Could not save config to " + this.langFile, ex);
/*     */     }
/*     */   }
/*     */   
/*     */   public String c(String name) {
/* 106 */     String caption = getLangFile().getString(name);
/* 107 */     if (caption == null) {
/* 108 */       getLogger().warning("Missing caption: " + name);
/* 109 */       caption = "&c[missing caption]";
/*     */     }
/*     */     
/* 112 */     caption = org.bukkit.ChatColor.translateAlternateColorCodes('&', caption);
/*     */     
/* 114 */     return caption;
/*     */   }
/*     */   
/*     */   public HashMap<String, TradePlayer> getRequests() {
/* 118 */     return requests;
/*     */   }
/*     */   
/*     */   public HashMap<String, TradePlayer> getTraders() {
/* 122 */     return traders;
/*     */   }
/*     */ }


