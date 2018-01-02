/*    */ package me.minebuilders.clearlag.commands;
/*    */ 
/*    */ import me.minebuilders.clearlag.Clearlag;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import me.minebuilders.clearlag.modules.CommandModule;
/*    */ import me.minebuilders.clearlag.modules.Module;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class ReloadCmd extends CommandModule
/*    */ {
/*    */   @me.minebuilders.clearlag.annotations.AutoWire
/*    */   private Config config;
/*    */   
/*    */   public ReloadCmd()
/*    */   {
/* 18 */     this.name = "reload";
/* 19 */     this.desc = "(Reloads clearlag)";
/*    */   }
/*    */   
/*    */ 
/*    */   protected void run(CommandSender sender, String[] args)
/*    */   {
/* 25 */     Util.msg("§bAttempting to reload modules...", sender);
/*    */     
/* 27 */     for (Module mod : Clearlag.getModules())
/*    */     {
/* 29 */       if (mod.isEnabled()) {
/* 30 */         ConfigPath configPath = (ConfigPath)mod.getClass().getAnnotation(ConfigPath.class);
/*    */         
/* 32 */         if (configPath != null) {
/* 33 */           mod.setDisabled();
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 38 */     this.config.reloadConfig();
/*    */     
/* 40 */     for (Module mod : Clearlag.getModules())
/*    */     {
/* 42 */       if (!mod.isEnabled()) {
/* 43 */         ConfigPath configPath = (ConfigPath)mod.getClass().getAnnotation(ConfigPath.class);
/*    */         
/* 45 */         if (((configPath != null) && (Config.getConfig().getBoolean(configPath.path() + ".enabled"))) || (configPath == null)) {
/* 46 */           mod.setEnabled();
/*    */         }
/*    */       }
/*    */     }
/*    */     try
/*    */     {
/* 52 */       this.config.setModuleConfigValues();
/*    */     } catch (Exception e) {
/* 54 */       e.printStackTrace();
/*    */     }
/*    */     
/* 57 */     Util.msg("§bModules have been reloaded!", sender);
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\commands\ReloadCmd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */