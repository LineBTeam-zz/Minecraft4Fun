/*     */ package net.risenphoenix.ipcheck;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.logging.Level;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.CommandManager;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.commons.database.DatabaseConnection;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
/*     */ import net.risenphoenix.ipcheck.commands.block.BlockManager;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*     */ import net.risenphoenix.ipcheck.events.PlayerLoginListener;
/*     */ import net.risenphoenix.ipcheck.objects.GeoIPObject;
/*     */ import net.risenphoenix.ipcheck.objects.StatsObject;
/*     */ import net.risenphoenix.ipcheck.stores.CmdStore;
/*     */ import net.risenphoenix.ipcheck.stores.ConfigStore;
/*     */ import net.risenphoenix.ipcheck.stores.LocaleStore;
/*     */ import net.risenphoenix.ipcheck.util.DateStamp;
/*     */ import net.risenphoenix.ipcheck.util.Messages;
/*     */ import net.risenphoenix.ipcheck.util.Metrics;
/*     */ import net.risenphoenix.ipcheck.util.Updater;
/*     */ import net.risenphoenix.ipcheck.util.Updater.UpdateType;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class IPCheck
/*     */   extends Plugin
/*     */   implements Listener
/*     */ {
/*     */   private static IPCheck instance;
/*     */   private DatabaseController dbController;
/*     */   private ConfigStore config;
/*     */   private Updater updater;
/*     */   private Metrics metrics;
/*     */   private StatsObject statsObject;
/*  74 */   private GeoIPObject geoIPOBject = null;
/*  75 */   private BlockManager blockManager = null;
/*     */   
/*     */ 
/*  78 */   private boolean hasRegistered = false;
/*     */   
/*     */ 
/*  81 */   private boolean isDevBuild = false;
/*     */   
/*     */ 
/*  84 */   private int configVersion = 5;
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onPlayerLogin(PlayerLoginEvent e) {
/*  88 */     new PlayerLoginListener(this, e);
/*     */   }
/*     */   
/*     */   public void onStartup()
/*     */   {
/*  93 */     instance = this;
/*     */     
/*     */ 
/*  96 */     setPluginName(ChatColor.GOLD, "IP-Check");
/*  97 */     setMessageColor(ChatColor.YELLOW);
/*     */     
/*     */ 
/* 100 */     if (!this.hasRegistered) {
/* 101 */       getServer().getPluginManager().registerEvents(this, this);
/* 102 */       this.hasRegistered = true;
/*     */     }
/*     */     
/*     */ 
/* 106 */     this.config = new ConfigStore(this);
/* 107 */     getConfigurationManager().initializeConfigurationStore(this.config);
/*     */     
/*     */ 
/* 110 */     LocaleStore locStore = new LocaleStore(this);
/* 111 */     getLocalizationManager().appendLocalizationStore(locStore);
/*     */     
/*     */ 
/*     */ 
/* 115 */     if (getConfigurationManager().getInteger("config-version") != this.configVersion) {
/* 116 */       sendConsoleMessage(Level.WARNING, getLocalizationManager()
/* 117 */         .getLocalString("CONFIG_VER_MISMATCH"));
/*     */     }
/*     */     
/*     */ 
/* 121 */     if (getConfigurationManager().getBoolean("use-geoip-services")) {
/* 122 */       this.geoIPOBject = new GeoIPObject(this);
/*     */     }
/*     */     
/* 125 */     this.blockManager = new BlockManager(this);
/*     */     
/*     */ 
/* 128 */     if (getConfigurationManager().getBoolean("use-mysql"))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 135 */       this.dbController = new DatabaseController(this, getConfigurationManager().getString("dbHostname"), getConfigurationManager().getInteger("dbPort"), getConfigurationManager().getString("dbName"), getConfigurationManager().getString("dbUsername"), getConfigurationManager().getString("dbPassword"));
/*     */     }
/*     */     else
/*     */     {
/* 139 */       this.dbController = new DatabaseController(this);
/*     */     }
/*     */     
/*     */ 
/* 143 */     CmdStore cmdStore = new CmdStore(this);
/* 144 */     getCommandManager().registerStore(cmdStore);
/*     */     
/*     */ 
/* 147 */     this.statsObject = new StatsObject(this);
/*     */     
/*     */ 
/* 150 */     if (!this.isDevBuild)
/*     */     {
/*     */ 
/* 153 */       if (!getConfigurationManager().getBoolean("disable-update-detection")) {
/* 154 */         this.updater = new Updater(this, 55121, getFile(), Updater.UpdateType.DEFAULT, true);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 160 */       if (!getConfigurationManager().getBoolean("disable-metrics-monitoring")) {
/*     */         try {
/* 162 */           this.metrics = new Metrics(this);
/* 163 */           this.metrics.start();
/*     */         } catch (IOException e) {
/* 165 */           sendConsoleMessage(Level.SEVERE, getLocalizationManager()
/* 166 */             .getLocalString("METRICS_ERR"));
/*     */         }
/*     */       }
/*     */     } else {
/* 170 */       sendConsoleMessage(Level.INFO, getLocalizationManager()
/* 171 */         .getLocalString("DEV_BUILD_WARN"));
/*     */     }
/*     */     
/*     */ 
/* 175 */     showRandomMessage();
/*     */   }
/*     */   
/*     */   public void onShutdown()
/*     */   {
/* 180 */     this.dbController.getDatabaseConnection().closeConnection();
/*     */   }
/*     */   
/*     */   public static IPCheck getInstance() {
/* 184 */     return instance;
/*     */   }
/*     */   
/*     */   public DatabaseController getDatabaseController() {
/* 188 */     return this.dbController;
/*     */   }
/*     */   
/*     */   public StatsObject getStatisticsObject() {
/* 192 */     return this.statsObject;
/*     */   }
/*     */   
/*     */   public GeoIPObject getGeoIPObject() {
/* 196 */     return this.geoIPOBject;
/*     */   }
/*     */   
/*     */   public BlockManager getBlockManager() {
/* 200 */     return this.blockManager;
/*     */   }
/*     */   
/*     */   public String getVersion() {
/* 204 */     return "2.0.7";
/*     */   }
/*     */   
/*     */   public int getBuildNumber() {
/* 208 */     return 2084;
/*     */   }
/*     */   
/*     */   private void showRandomMessage() {
/* 212 */     DateStamp ds = new DateStamp();
/* 213 */     String ran = Messages.getSeasonalMessage(ds.getCustomStamp("MM-dd"));
/*     */     
/* 215 */     if (ran != null) {
/* 216 */       sendConsoleMessage(Level.INFO, ran);
/*     */     } else {
/* 218 */       sendConsoleMessage(Level.INFO, Messages.getRandomMessage());
/*     */     }
/*     */   }
/*     */   
/*     */   public final Player[] getOnlinePlayers() {
/*     */     try {
/* 224 */       Collection<? extends Player> result = Bukkit.getOnlinePlayers();
/* 225 */       return (Player[])result.toArray(new Player[result.size()]);
/*     */     } catch (NoSuchMethodError err) {
/* 227 */       sendConsoleMessage(Level.INFO, getLocalizationManager()
/* 228 */         .getLocalString("VER_COMP_ERR"));
/*     */     }
/*     */     
/* 231 */     return new Player[0];
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\IPCheck.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */