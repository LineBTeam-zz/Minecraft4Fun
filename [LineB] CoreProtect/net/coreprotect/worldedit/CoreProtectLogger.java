/*    */ package net.coreprotect.worldedit;
/*    */ 
/*    */ import com.sk89q.worldedit.Vector;
/*    */ import com.sk89q.worldedit.blocks.BaseBlock;
/*    */ import com.sk89q.worldedit.bukkit.BukkitWorld;
/*    */ import com.sk89q.worldedit.extension.platform.Actor;
/*    */ import com.sk89q.worldedit.extent.Extent;
/*    */ import com.sk89q.worldedit.extent.logging.AbstractLoggingExtent;
/*    */ import net.coreprotect.Functions;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.block.Block;
/*    */ import org.bukkit.block.BlockState;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class CoreProtectLogger
/*    */   extends AbstractLoggingExtent
/*    */ {
/*    */   private final Actor eventActor;
/*    */   private final com.sk89q.worldedit.world.World eventWorld;
/*    */   
/*    */   public CoreProtectLogger(Actor actor, com.sk89q.worldedit.world.World world, Extent extent)
/*    */   {
/* 23 */     super(extent);
/* 24 */     this.eventActor = actor;
/* 25 */     this.eventWorld = world;
/*    */   }
/*    */   
/*    */   protected void onBlockChange(Vector position, BaseBlock baseBlock)
/*    */   {
/* 30 */     if (!(this.eventWorld instanceof BukkitWorld)) {
/* 31 */       return;
/*    */     }
/* 33 */     org.bukkit.World world = ((BukkitWorld)this.eventWorld).getWorld();
/* 34 */     if (Functions.checkConfig(world, "worldedit") == 0) {
/* 35 */       return;
/*    */     }
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 47 */     Block currentBlock = world.getBlockAt(position.getBlockX(), position.getBlockY(), position.getBlockZ());
/* 48 */     Material oldBlockType = currentBlock.getType();
/* 49 */     ItemStack[] containerData = Functions.getContainerContents(oldBlockType, currentBlock, currentBlock.getLocation());
/*    */     
/* 51 */     BlockState oldBlock = currentBlock.getState();
/* 52 */     BlockState newBlock = currentBlock.getState();
/* 53 */     newBlock.setTypeId(baseBlock.getType());
/* 54 */     newBlock.setRawData((byte)baseBlock.getData());
/*    */     
/* 56 */     WorldEdit.logData(this.eventActor, oldBlock, oldBlockType, newBlock, containerData);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\worldedit\CoreProtectLogger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */