/*    */ package me.minebuilders.clearlag.commands;
/*    */ 
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.annotations.AutoWire;
/*    */ import me.minebuilders.clearlag.managers.EntityManager;
/*    */ import me.minebuilders.clearlag.modules.CommandModule;
/*    */ import me.minebuilders.clearlag.removetype.AreaClear;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class AreaCmd extends CommandModule
/*    */ {
/*    */   @me.minebuilders.clearlag.annotations.ConfigModule
/* 14 */   private AreaClear areaClear = new AreaClear();
/*    */   
/*    */   @AutoWire
/*    */   private EntityManager entityManager;
/*    */   
/*    */   public AreaCmd()
/*    */   {
/* 21 */     this.name = "area";
/* 22 */     this.argLength = 1;
/* 23 */     this.usage = "<radius>";
/* 24 */     this.desc = "(Clears entities from your radius)";
/*    */   }
/*    */   
/*    */   protected void run(Player player, String[] args)
/*    */   {
/*    */     try {
/* 30 */       Integer rad = Integer.valueOf(Integer.parseInt(args[0]));
/*    */       
/* 32 */       int i = this.entityManager.removeEntities(this.areaClear.getRemovables(player.getNearbyEntities(rad.intValue(), rad.intValue(), rad.intValue()), player.getWorld()), player.getWorld());
/*    */       
/* 34 */       Util.msg("&3" + i + " &bObjetos foram removidos em uma distância de &3" + rad + "&b!", player);
/*    */     } catch (Exception e) {
/* 36 */       player.sendMessage(ChatColor.RED + "Invalid integer specified:" + args[0]);
/*    */     }
/*    */   }
/*    */ }
