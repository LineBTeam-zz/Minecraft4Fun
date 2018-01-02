/*    */ package de.rob1n.prospam.gui;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
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
/*    */ 
/*    */ 
/*    */ public class Item
/*    */ {
/*    */   private int slot;
/*    */   private ItemStack itemStack;
/*    */   private ClickAction clickAction;
/* 25 */   private static final ItemStack spacerItemStack = new ItemStack(Material.STONE_BUTTON);
/* 26 */   public static final NoClickAction NO_CLICK_ACTION = new NoClickAction(null);
/*    */   
/*    */   public int getSlot()
/*    */   {
/* 30 */     return this.slot;
/*    */   }
/*    */   
/*    */   public ItemStack getItemStack()
/*    */   {
/* 35 */     return this.itemStack;
/*    */   }
/*    */   
/*    */   public ClickAction getClickAction()
/*    */   {
/* 40 */     return this.clickAction;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Item(int slot, ItemStack itemStack, String name, ClickAction clickAction)
/*    */   {
/* 50 */     this(slot, itemStack, name, "", clickAction);
/*    */   }
/*    */   
/*    */   public Item(int slot, ItemStack itemStack, String name, String description, ClickAction clickAction)
/*    */   {
/* 55 */     this.slot = slot;
/* 56 */     this.itemStack = (itemStack == null ? new ItemStack(Material.AIR) : itemStack);
/* 57 */     this.clickAction = clickAction;
/*    */     
/* 59 */     ItemMeta itemMeta = this.itemStack.getItemMeta();
/*    */     
/* 61 */     if (!name.isEmpty())
/*    */     {
/* 63 */       if (itemMeta != null)
/*    */       {
/* 65 */         itemMeta.setDisplayName(name);
/* 66 */         this.itemStack.setItemMeta(itemMeta);
/*    */       }
/*    */     }
/*    */     
/* 70 */     if (!description.isEmpty())
/*    */     {
/* 72 */       if (itemMeta != null)
/*    */       {
/* 74 */         itemMeta.setLore(Arrays.asList(new String[] { description }));
/* 75 */         this.itemStack.setItemMeta(itemMeta);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public static Item getSpacerItem(int slot)
/*    */   {
/* 82 */     return new Item(slot, spacerItemStack, "-", NO_CLICK_ACTION);
/*    */   }
/*    */   
/*    */   private static class NoClickAction
/*    */     implements Item.ClickAction
/*    */   {
/*    */     public void onClick(Player player) {}
/*    */   }
/*    */   
/*    */   public static abstract interface ClickAction
/*    */   {
/*    */     public abstract void onClick(Player paramPlayer);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\gui\Item.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */