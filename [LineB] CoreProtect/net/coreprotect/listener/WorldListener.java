/*    */ package net.coreprotect.listener;
/*    */ 
/*    */ import java.util.List;
/*    */ import net.coreprotect.Functions;
/*    */ import net.coreprotect.consumer.Queue;
/*    */ import net.coreprotect.database.Lookup;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.TreeType;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.LeavesDecayEvent;
/*    */ import org.bukkit.event.world.PortalCreateEvent;
/*    */ import org.bukkit.event.world.StructureGrowEvent;
/*    */ 
/*    */ public class WorldListener
/*    */   extends Queue implements Listener
/*    */ {
/*    */   @EventHandler(priority=EventPriority.MONITOR)
/*    */   protected void onLeavesDecay(LeavesDecayEvent event)
/*    */   {
/* 27 */     World world = event.getBlock().getWorld();
/* 28 */     if ((!event.isCancelled()) && (Functions.checkConfig(world, "leaf-decay") == 1)) {
/* 29 */       String player = "#decay";
/* 30 */       Block block = event.getBlock();
/* 31 */       Material type = event.getBlock().getType();
/* 32 */       int data = Functions.getData(event.getBlock());
/* 33 */       Queue.queueBlockBreak(player, block.getState(), type, data);
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler(priority=EventPriority.MONITOR)
/*    */   protected void OnPortalCreate(PortalCreateEvent event) {
/* 39 */     World world = event.getWorld();
/* 40 */     String user; if ((!event.isCancelled()) && (Functions.checkConfig(world, "portals") == 1)) {
/* 41 */       user = "#portal";
/* 42 */       for (Block block : event.getBlocks()) {
/* 43 */         Material type = block.getType();
/* 44 */         if (type.equals(Material.FIRE)) {
/* 45 */           String result_data = Lookup.who_placed_cache(block);
/* 46 */           if (result_data.length() <= 0) break;
/* 47 */           user = result_data; break;
/*    */         }
/*    */       }
/*    */       
/*    */ 
/* 52 */       for (Block block : event.getBlocks()) {
/* 53 */         Material type = block.getType();
/* 54 */         if ((user.equals("#portal")) && (!type.equals(Material.OBSIDIAN))) {
/* 55 */           Queue.queueBlockPlaceDelayed(user, block, null, 20);
/*    */         }
/* 57 */         else if (type.equals(Material.AIR)) {
/* 58 */           Queue.queueBlockPlace(user, block.getState(), Material.PORTAL, Functions.getData(block));
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler(priority=EventPriority.MONITOR)
/*    */   protected void onStructureGrow(StructureGrowEvent event)
/*    */   {
/* 67 */     TreeType treeType = event.getSpecies();
/* 68 */     String user = "#tree";
/* 69 */     int tree = 1;
/*    */     
/*    */ 
/* 72 */     if (treeType == null) {
/* 73 */       return;
/*    */     }
/*    */     
/* 76 */     List<BlockState> blocks = event.getBlocks();
/* 77 */     if (blocks.size() <= 1) {
/* 78 */       for (BlockState block : blocks) {
/* 79 */         if (block.getType().equals(Material.SAPLING)) {
/* 80 */           return;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 85 */     if (treeType.name().toLowerCase().contains("mushroom")) {
/* 86 */       user = "#mushroom";
/* 87 */       tree = 0;
/*    */     }
/*    */     
/* 90 */     if (!event.isCancelled()) {
/* 91 */       World world = event.getWorld();
/* 92 */       if (((tree == 1) && (Functions.checkConfig(world, "tree-growth") == 1)) || ((tree == 0) && (Functions.checkConfig(world, "mushroom-growth") == 1))) {
/* 93 */         Player player = event.getPlayer();
/* 94 */         Location location = event.getLocation();
/* 95 */         if (player != null) {
/* 96 */           user = player.getName();
/*    */         }
/* 98 */         Queue.queueStructureGrow(user, world.getBlockAt(location).getState(), blocks);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\listener\WorldListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */