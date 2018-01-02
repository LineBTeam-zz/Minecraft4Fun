/*    */ package me.minebuilders.clearlag.commands;
/*    */ 
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class TpChunkCmd extends me.minebuilders.clearlag.modules.CommandModule
/*    */ {
/*    */   public TpChunkCmd()
/*    */   {
/* 14 */     this.name = "tpchunk";
/* 15 */     this.argLength = 3;
/* 16 */     this.usage = "<x> <z> <world>";
/* 17 */     this.desc = "(Teleport to chunks)";
/*    */   }
/*    */   
/*    */   protected void run(Player player, String[] args)
/*    */   {
/*    */     try {
/* 23 */       World world = org.bukkit.Bukkit.getServer().getWorld(args[2]);
/*    */       
/* 25 */       Block b = world.getChunkAt(Integer.parseInt(args[0]), Integer.parseInt(args[1])).getBlock(8, 0, 8);
/*    */       
/* 27 */       int X = b.getX();
/* 28 */       int Y = b.getY();
/* 29 */       int Z = b.getZ();
/*    */       
/* 31 */       player.teleport(new Location(world, X, world.getHighestBlockYAt(new Location(world, X, Y, Z)), Z));
/*    */       
/* 33 */       Util.msg("§bTeleported to chunk: &3" + X + "&7, &3" + Z, player);
/*    */     }
/*    */     catch (Exception e) {
/* 36 */       Util.msg("&cInvalid arguments!", player);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\commands\TpChunkCmd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */