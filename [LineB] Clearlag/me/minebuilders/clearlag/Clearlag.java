/*     */ package me.minebuilders.clearlag;
/*     */ 
/*     */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*     */ import me.minebuilders.clearlag.commands.ChunkCmd;
/*     */ import me.minebuilders.clearlag.commands.ClearCmd;
/*     */ import me.minebuilders.clearlag.config.Config;
/*     */ import me.minebuilders.clearlag.listeners.ChunkOverloadListener;
/*     */ import me.minebuilders.clearlag.listeners.DispenceLimitEvent;
/*     */ import me.minebuilders.clearlag.listeners.EntityBreedListener;
/*     */ import me.minebuilders.clearlag.listeners.TNTMinecartListener;
/*     */ import me.minebuilders.clearlag.modules.Module;
/*     */ import me.minebuilders.clearlag.reflection.AutoWirer;
/*     */ import me.minebuilders.clearlag.tasks.LimitTask;
/*     */ import me.minebuilders.clearlag.tasks.RAMCheckTask;
/*     */ import org.bukkit.configuration.Configuration;
/*     */ 
/*     */ public class Clearlag extends org.bukkit.plugin.java.JavaPlugin
/*     */ {
/*     */   private static Clearlag instance;
/*     */   private static Module[] modules;
/*     */   @me.minebuilders.clearlag.annotations.AutoWire
/*     */   private Config config;
/*  23 */   private AutoWirer autoWirer = new AutoWirer();
/*     */   
/*     */   public void onEnable()
/*     */   {
/*  27 */     instance = this;
/*     */     
/*  29 */     this.autoWirer.addWireable(instance);
/*  30 */     this.autoWirer.addWireable(new Config());
/*  31 */     this.autoWirer.addWireable(Config.getConfig());
/*  32 */     this.autoWirer.addWireable(new CommandListener());
/*  33 */     this.autoWirer.addWireable(new me.minebuilders.clearlag.tasks.HaltTask());
/*     */     
/*  35 */     modules = new Module[] { new me.minebuilders.clearlag.managers.EntityManager(), new ClearCmd(), new me.minebuilders.clearlag.commands.AreaCmd(), new me.minebuilders.clearlag.commands.CheckCmd(), new ChunkCmd(), new me.minebuilders.clearlag.commands.GcCmd(), new me.minebuilders.clearlag.commands.TpsCmd(), new me.minebuilders.clearlag.commands.KillmobsCmd(), new me.minebuilders.clearlag.commands.ReloadCmd(), new me.minebuilders.clearlag.commands.TpChunkCmd(), new me.minebuilders.clearlag.commands.UnloadChunksCmd(), new me.minebuilders.clearlag.commands.HaltCmd(), new me.minebuilders.clearlag.commands.AdminCmd(), new me.minebuilders.clearlag.listeners.ChunkLimiterListener(), new DispenceLimitEvent(), new me.minebuilders.clearlag.listeners.EggSpawnListener(), new me.minebuilders.clearlag.listeners.EntityAISpawnListener(), new me.minebuilders.clearlag.listeners.FireSpreadListener(), new me.minebuilders.clearlag.listeners.ItemMergeListener(), new me.minebuilders.clearlag.listeners.MobLimitListener(), new me.minebuilders.clearlag.listeners.MobSpawerListener(), new TNTMinecartListener(), new me.minebuilders.clearlag.listeners.TntReduceListener(), new me.minebuilders.clearlag.listeners.ChunkEntityLimiterListener(), new EntityBreedListener(), new ChunkOverloadListener(), new me.minebuilders.clearlag.tasks.TPSTask(), new me.minebuilders.clearlag.tasks.LiveTask(), new me.minebuilders.clearlag.tasks.ClearTask(), new LimitTask(), new me.minebuilders.clearlag.tasks.TPSCheckTask(), new RAMCheckTask(), new me.minebuilders.clearlag.tasks.LogPurger() };
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  71 */     this.autoWirer.addWireables(modules);
/*     */     
/*  73 */     for (Object module : this.autoWirer.getWires()) {
/*     */       try {
/*  75 */         this.autoWirer.wireObject(module);
/*     */       } catch (IllegalAccessException e) {
/*  77 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/*  81 */     startModules();
/*     */     
/*  83 */     if (Config.getConfig().getBoolean("settings.auto-update")) {
/*  84 */       new BukkitUpdater(getFile());
/*     */     }
/*     */     
/*  87 */     Util.log("Clearlag is now enabled!");
/*     */   }
/*     */   
/*     */   public void startModules() {
/*  91 */     Util.log("Loading modules...");
/*     */     
/*  93 */     for (Module mod : modules)
/*     */     {
/*  95 */       ConfigPath configPath = (ConfigPath)mod.getClass().getAnnotation(ConfigPath.class);
/*     */       
/*  97 */       if (((configPath != null) && (Config.getConfig().getBoolean(configPath.path() + ".enabled"))) || (configPath == null)) {
/*  98 */         mod.setEnabled();
/*     */       }
/*     */     }
/*     */     
/* 102 */     Util.log("Modules enabed, loading config values");
/*     */     try
/*     */     {
/* 105 */       this.config.setModuleConfigValues();
/*     */     } catch (Exception e) {
/* 107 */       e.printStackTrace();
/*     */     }
/*     */     
/* 110 */     Util.log("Modules have been loaded!");
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDisable()
/*     */   {
/* 116 */     Util.log("Clearlag is now disabled!");
/*     */   }
/*     */   
/*     */   public static Clearlag getInstance() {
/* 120 */     return instance;
/*     */   }
/*     */   
/*     */   public static Module[] getModules() {
/* 124 */     return modules;
/*     */   }
/*     */   
/*     */   public static Module getModule(String name) {
/* 128 */     for (Module module : modules) {
/* 129 */       if (module.getClass().getSimpleName().equalsIgnoreCase(name)) {
/* 130 */         return module;
/*     */       }
/*     */     }
/* 133 */     return null;
/*     */   }
/*     */   
/*     */   public AutoWirer getAutoWirer() {
/* 137 */     return this.autoWirer;
/*     */   }
/*     */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\Clearlag.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */