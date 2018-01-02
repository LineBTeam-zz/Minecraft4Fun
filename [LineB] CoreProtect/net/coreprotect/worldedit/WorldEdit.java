/*    */ package net.coreprotect.worldedit;
/*    */ 
/*    */ import com.sk89q.worldedit.bukkit.WorldEditPlugin;
/*    */ import com.sk89q.worldedit.extension.platform.Actor;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import net.coreprotect.Functions;
/*    */ import net.coreprotect.consumer.Queue;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.block.Sign;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class WorldEdit
/*    */   extends Queue
/*    */ {
/*    */   public static WorldEditPlugin getWorldEdit(Server server)
/*    */   {
/* 22 */     Plugin pl = server.getPluginManager().getPlugin("WorldEdit");
/*    */     
/*    */ 
/* 25 */     if ((pl == null) || (!(pl instanceof WorldEditPlugin))) {
/* 26 */       return null;
/*    */     }
/*    */     
/* 29 */     return (WorldEditPlugin)pl;
/*    */   }
/*    */   
/*    */   protected static void logData(Actor actor, BlockState oldBlock, Material oldBlockType, BlockState newBlock, ItemStack[] container_contents) {
/* 33 */     List<Material> signs = Arrays.asList(new Material[] { Material.SIGN_POST, Material.WALL_SIGN });
/* 34 */     Material old_type = oldBlock.getType();
/* 35 */     int old_data = Functions.getData(oldBlock);
/* 36 */     Material new_type = newBlock.getType();
/* 37 */     int new_data = Functions.getData(newBlock);
/* 38 */     if ((!old_type.equals(new_type)) || (old_data != new_data)) {
/*    */       try {
/* 40 */         if ((Functions.checkConfig(oldBlock.getWorld(), "sign-text") == 1) && (signs.contains(old_type))) {
/* 41 */           Sign sign = (Sign)oldBlock;
/* 42 */           String line1 = sign.getLine(0);
/* 43 */           String line2 = sign.getLine(1);
/* 44 */           String line3 = sign.getLine(2);
/* 45 */           String line4 = sign.getLine(3);
/* 46 */           Queue.queueSignText(actor.getName(), newBlock, line1, line2, line3, line4, 5);
/*    */         }
/* 48 */         if (container_contents != null) {
/* 49 */           Queue.queueContainerBreak(actor.getName(), newBlock, oldBlockType, container_contents);
/*    */         }
/*    */       }
/*    */       catch (Exception e) {
/* 53 */         e.printStackTrace();
/*    */       }
/*    */       
/* 56 */       if (new_type.equals(Material.SKULL))
/*    */       {
/* 58 */         Queue.queueBlockPlaceDelayed(actor.getName(), newBlock.getBlock(), oldBlock, 0);
/*    */       }
/* 60 */       else if ((old_type.equals(Material.AIR) == true) && (!new_type.equals(Material.AIR)))
/*    */       {
/* 62 */         Queue.queueBlockPlace(actor.getName(), newBlock, newBlock.getType(), Functions.getData(newBlock));
/*    */       }
/* 64 */       else if ((!old_type.equals(Material.AIR)) && (!new_type.equals(Material.AIR)))
/*    */       {
/* 66 */         queueBlockPlace(actor.getName(), newBlock, oldBlock, newBlock.getType(), Functions.getData(newBlock));
/*    */       }
/* 68 */       else if ((!old_type.equals(Material.AIR)) && (new_type.equals(Material.AIR) == true))
/*    */       {
/* 70 */         Queue.queueBlockBreak(actor.getName(), oldBlock, old_type, old_data);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\worldedit\WorldEdit.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */