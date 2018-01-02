/*    */ package me.minebuilders.clearlag.commands;
/*    */ 
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.annotations.AutoWire;
/*    */ import me.minebuilders.clearlag.managers.EntityManager;
/*    */ import me.minebuilders.clearlag.modules.CommandModule;
/*    */ import me.minebuilders.clearlag.removetype.KillMobsClear;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class KillmobsCmd extends CommandModule
/*    */ {
/*    */   @me.minebuilders.clearlag.annotations.ConfigModule
/* 13 */   private KillMobsClear killMobsClear = new KillMobsClear();
/*    */   
/*    */   @AutoWire
/*    */   private EntityManager entityManager;
/*    */   
/*    */   public KillmobsCmd()
/*    */   {
/* 20 */     this.name = "killmobs";
/* 21 */     this.desc = "(Clears mobs from your worlds)";
/*    */   }
/*    */   
/*    */   protected void run(CommandSender sender, String[] args)
/*    */   {
/* 26 */     Util.msg("§3" + this.entityManager.removeEntities(this.killMobsClear) + " §bMonstros foram removidos!", sender);
/*    */   }
/*    */ }
