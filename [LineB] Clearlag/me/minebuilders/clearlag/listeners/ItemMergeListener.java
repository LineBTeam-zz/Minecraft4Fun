/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.entity.ItemSpawnEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.material.MaterialData;
/*    */ 
/*    */ @ConfigPath(path="item-merger")
/*    */ public class ItemMergeListener extends me.minebuilders.clearlag.modules.EventModule
/*    */ {
/*    */   @ConfigValue
/*    */   private int radius;
/*    */   
/*    */   @EventHandler
/*    */   public void onItemDrop(ItemSpawnEvent event)
/*    */   {
/* 22 */     ItemStack i = event.getEntity().getItemStack();
/* 23 */     Material type = i.getType();
/* 24 */     int c = i.getAmount();
/* 25 */     MaterialData data = i.getData();
/*    */     
/* 27 */     for (Entity entity : event.getEntity().getNearbyEntities(this.radius, this.radius, this.radius)) {
/* 28 */       if (((entity instanceof Item)) && (!entity.isDead()))
/*    */       {
/* 30 */         ItemStack ni = ((Item)entity).getItemStack();
/*    */         
/* 32 */         if ((type == ni.getType()) && (data.equals(ni.getData())) && (i.getDurability() == ni.getDurability()) && (i.getMaxStackSize() >= ni.getAmount() + c))
/*    */         {
/*    */ 
/* 35 */           if (entity.isDead()) return;
/* 36 */           entity.remove();
/* 37 */           i.setAmount(ni.getAmount() + c);
/* 38 */           return;
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\ItemMergeListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */