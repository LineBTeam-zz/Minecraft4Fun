/*     */ package de.rob1n.prospam.gui;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import de.rob1n.prospam.exception.NotFoundException;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiManager
/*     */ {
/*  24 */   private Set<InventoryGui> guis = new HashSet();
/*     */   
/*     */   public GuiManager(ProSpam plugin)
/*     */   {
/*  28 */     GuiListener guiListener = new GuiListener(null);
/*  29 */     Bukkit.getPluginManager().registerEvents(guiListener, plugin);
/*     */   }
/*     */   
/*     */   private InventoryGui getInventoryGuiById(String id) throws Exception
/*     */   {
/*  34 */     for (InventoryGui inventory : this.guis)
/*     */     {
/*  36 */       if (inventory.getId().equals(id)) {
/*  37 */         return inventory;
/*     */       }
/*     */     }
/*  40 */     throw new NotFoundException("Inventory not found");
/*     */   }
/*     */   
/*     */   private boolean isInventoryGui(String id)
/*     */   {
/*  45 */     for (InventoryGui inventory : this.guis)
/*     */     {
/*  47 */       if (inventory.getId().equals(id)) {
/*  48 */         return true;
/*     */       }
/*     */     }
/*  51 */     return false;
/*     */   }
/*     */   
/*     */   public boolean openInventoryGui(Player player, String name, Set<Item> items)
/*     */   {
/*  56 */     InventoryGui gui = new InventoryGui(name, items);
/*  57 */     gui.open(player);
/*     */     
/*  59 */     return this.guis.add(gui);
/*     */   }
/*     */   
/*     */   private class GuiListener implements Listener {
/*     */     private GuiListener() {}
/*     */     
/*     */     @EventHandler(ignoreCancelled=true)
/*     */     public void onInventoryClick(InventoryClickEvent event) {
/*  67 */       Inventory inventory = event.getInventory();
/*  68 */       HumanEntity humanEntity = event.getWhoClicked();
/*     */       
/*     */ 
/*  71 */       if (!(humanEntity instanceof Player)) {
/*  72 */         return;
/*     */       }
/*  74 */       Player player = (Player)event.getWhoClicked();
/*     */       
/*     */       try
/*     */       {
/*  78 */         InventoryGui gui = GuiManager.this.getInventoryGuiById(HiddenId.grabId(inventory.getTitle()));
/*     */         
/*  80 */         gui.getClickAction(event.getRawSlot()).onClick(player);
/*  81 */         event.setCancelled(true);
/*     */       }
/*     */       catch (Exception ignored) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     @EventHandler
/*     */     public void onInventoryClose(InventoryCloseEvent event)
/*     */     {
/*  92 */       Inventory inventory = event.getInventory();
/*     */       
/*     */       try
/*     */       {
/*  96 */         InventoryGui gui = GuiManager.this.getInventoryGuiById(HiddenId.grabId(inventory.getTitle()));
/*     */         
/*  98 */         GuiManager.this.guis.remove(gui);
/*     */       }
/*     */       catch (Exception ignored) {}
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     @EventHandler(ignoreCancelled=true)
/*     */     public void onInventoryDrag(InventoryDragEvent event)
/*     */     {
/* 109 */       Inventory inventory = event.getInventory();
/*     */       
/* 111 */       if (GuiManager.this.isInventoryGui(HiddenId.grabId(inventory.getTitle())))
/*     */       {
/* 113 */         event.setCancelled(true);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\gui\GuiManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */