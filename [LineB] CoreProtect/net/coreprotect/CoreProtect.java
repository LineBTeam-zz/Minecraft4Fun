/*     */ package net.coreprotect;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Map;
/*     */ import net.coreprotect.command.CommandHandler;
/*     */ import net.coreprotect.consumer.Consumer;
/*     */ import net.coreprotect.listener.EntityListener;
/*     */ import net.coreprotect.listener.HangingListener;
/*     */ import net.coreprotect.model.Config;
/*     */ import net.coreprotect.thread.CacheCleanUp;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ public class CoreProtect extends JavaPlugin
/*     */ {
/*     */   protected static CoreProtect instance;
/*     */   
/*     */   public static CoreProtect getInstance()
/*     */   {
/*  24 */     return instance;
/*     */   }
/*     */   
/*  27 */   private CoreProtectAPI api = new CoreProtectAPI();
/*     */   
/*     */   public CoreProtectAPI getAPI() {
/*  30 */     return this.api;
/*     */   }
/*     */   
/*     */   private static boolean performVersionChecks() {
/*     */     try {
/*  35 */       String requiredBukkitVersion = "1.11";
/*  36 */       String[] bukkitVersion = getInstance().getServer().getBukkitVersion().split("-|\\.");
/*  37 */       if (Functions.newVersion(bukkitVersion[0] + "." + bukkitVersion[1], requiredBukkitVersion)) {
/*  38 */         System.out.println("[CoreProtect] Spigot " + requiredBukkitVersion + " or higher is required.");
/*  39 */         return false;
/*     */       }
/*  41 */       String requiredJavaVersion = "1.7";
/*  42 */       String[] javaVersion = System.getProperty("java.version").split("\\.");
/*  43 */       if (Functions.newVersion(javaVersion[0] + "." + javaVersion[1], requiredJavaVersion)) {
/*  44 */         System.out.println("[CoreProtect] Java " + requiredJavaVersion + " or higher is required.");
/*  45 */         return false;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  49 */       e.printStackTrace();
/*  50 */       return false;
/*     */     }
/*     */     
/*  53 */     return true;
/*     */   }
/*     */   
/*     */   public void onEnable()
/*     */   {
/*  58 */     instance = this;
/*  59 */     PluginDescriptionFile pluginDescription = getDescription();
/*  60 */     boolean start = performVersionChecks();
/*  61 */     if (start == true) {
/*     */       try {
/*  63 */         Consumer.initialize();
/*  64 */         getServer().getPluginManager().registerEvents(new net.coreprotect.listener.BlockListener(), this);
/*  65 */         getServer().getPluginManager().registerEvents(new net.coreprotect.listener.PlayerListener(), this);
/*  66 */         getServer().getPluginManager().registerEvents(new EntityListener(), this);
/*  67 */         getServer().getPluginManager().registerEvents(new HangingListener(), this);
/*  68 */         getServer().getPluginManager().registerEvents(new net.coreprotect.listener.WorldListener(), this);
/*  69 */         getCommand("coreprotect").setExecutor(CommandHandler.getInstance());
/*  70 */         getCommand("core").setExecutor(CommandHandler.getInstance());
/*  71 */         getCommand("co").setExecutor(CommandHandler.getInstance());
/*     */         
/*  73 */         boolean exists = new File("plugins/CoreProtect/").exists();
/*  74 */         if (!exists) {
/*  75 */           new File("plugins/CoreProtect").mkdir();
/*     */         }
/*  77 */         start = Config.performInitialization();
/*     */       }
/*     */       catch (Exception e) {
/*  80 */         e.printStackTrace();
/*  81 */         start = false;
/*     */       }
/*     */     }
/*     */     
/*  85 */     if (start == true) {
/*  86 */       System.out.println("[CoreProtect] " + pluginDescription.getName() + " has been successfully enabled!");
/*  87 */       if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/*  88 */         System.out.println("[CoreProtect] Using MySQL for data storage.");
/*     */       }
/*     */       else {
/*  91 */         System.out.println("[CoreProtect] Using SQLite for data storage.");
/*     */       }
/*     */       
/*  94 */       if (((Integer)Config.config.get("check-updates")).intValue() == 1) {
/*  95 */         Thread checkUpdateThread = new Thread(new net.coreprotect.thread.CheckUpdate(true));
/*  96 */         checkUpdateThread.start();
/*     */       }
/*     */       
/*  99 */       Thread cacheCleanUpThread = new Thread(new CacheCleanUp());
/* 100 */       cacheCleanUpThread.start();
/*     */       
/* 102 */       Thread consumerThread = new Thread(new Consumer());
/* 103 */       consumerThread.start();
/*     */       
/*     */       try
/*     */       {
/* 107 */         Metrics metrics = new Metrics(this);
/* 108 */         metrics.start();
/*     */ 
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */     else
/*     */     {
/* 115 */       System.out.println("[CoreProtect] " + pluginDescription.getName() + " was unable to start.");
/* 116 */       getServer().getPluginManager().disablePlugin(this);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDisable() {}
/*     */   
/*     */   private static void safeShutdown()
/*     */   {
/*     */     try
/*     */     {
/* 127 */       int time_start = (int)(System.currentTimeMillis() / 1000L);
/* 128 */       boolean processConsumer = Config.server_running;
/* 129 */       if (Config.converter_running == true) {
/* 130 */         processConsumer = false;
/*     */       }
/* 132 */       boolean message_shown = false;
/* 133 */       Config.server_running = false;
/*     */       
/* 135 */       while (((Consumer.isRunning() == true) || (Config.converter_running == true)) && (!Config.purge_running)) {
/* 136 */         int time = (int)(System.currentTimeMillis() / 1000L);
/* 137 */         if ((time > time_start) && (!message_shown)) {
/* 138 */           if (Config.converter_running == true) {
/* 139 */             Functions.messageOwner("Finishing up data conversion. Please wait...");
/*     */           }
/*     */           else {
/* 142 */             Functions.messageOwner("Finishing up data logging. Please wait...");
/*     */           }
/* 144 */           message_shown = true;
/*     */         }
/* 146 */         Thread.sleep(1L);
/*     */       }
/*     */       
/* 149 */       if (message_shown == true) {
/* 150 */         System.out.println("[CoreProtect] Success! Resuming server shutdown.");
/*     */       }
/*     */       
/* 153 */       if (processConsumer == true) {
/* 154 */         net.coreprotect.consumer.Process.processConsumer(Consumer.current_consumer);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 158 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\CoreProtect.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */