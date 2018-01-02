/*    */ package me.minebuilders.clearlag.commands;
/*    */ 
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import me.minebuilders.clearlag.managers.EntityManager;
/*    */ import me.minebuilders.clearlag.modules.CommandModule;
/*    */ import me.minebuilders.clearlag.removetype.CmdClear;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ 
/*    */ public class ClearCmd extends CommandModule
/*    */ {
/*    */   @me.minebuilders.clearlag.annotations.ConfigModule
/* 14 */   private CmdClear cmdClear = new CmdClear();
/*    */   
/*    */   @me.minebuilders.clearlag.annotations.AutoWire
/*    */   private EntityManager entityManager;
/*    */   
/*    */   public ClearCmd()
/*    */   {
/* 21 */     this.name = "clear";
/* 22 */     this.desc = "(Clears entities from your worlds)";
/*    */   }
/*    */   
/*    */   protected void run(CommandSender sender, String[] args)
/*    */   {
/* 27 */     int i = this.entityManager.removeEntities(this.cmdClear);
/*    */     
/* 29 */     if (Config.getConfig().getBoolean("command-remove.broadcast-removal")) {
/* 30 */       Util.broadcast(Config.getConfig().getString("auto-removal.broadcast-message").replace("+RemoveAmount", "" + i));
/*    */     } else {
/* 32 */       Util.msg("§bYou just removed §3" + i + "§b entities!", sender);
/*    */     }
/*    */   }
/*    */ }


