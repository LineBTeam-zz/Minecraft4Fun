/*     */ package com.solarcloud7.cloudtrade;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.event.inventory.InventoryMoveItemEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class InventoryListener implements org.bukkit.event.Listener
/*     */ {
/*     */   private final CloudTrade p;
/*     */   
/*     */   public InventoryListener(CloudTrade instance)
/*     */   {
/*  21 */     this.p = instance;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryMoveItem(InventoryMoveItemEvent event)
/*     */   {
/*  27 */     if (!event.getDestination().getTitle().equals("Cloud Trading")) {
/*  28 */       return;
/*     */     }
/*     */     
/*  31 */     TradePlayer target = (TradePlayer)this.p.getTraders().get(((HumanEntity)event.getSource().getViewers().get(0)).getName());
/*     */     
/*     */ 
/*  34 */     if (target == null) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   @EventHandler
/*     */   public void onInventoryClick(InventoryClickEvent event)
/*     */   {
/*  43 */     if (!event.getView().getTitle().equalsIgnoreCase("Cloud Trading")) {
/*  44 */       return;
/*     */     }
/*     */     
/*     */ 
/*  48 */     TradePlayer target = (TradePlayer)this.p.getTraders().get(event.getWhoClicked().getName());
/*     */     
/*  50 */     if (target == null) {
/*  51 */       return;
/*     */     }
/*     */     
/*  54 */     if (event.getAction() == org.bukkit.event.inventory.InventoryAction.COLLECT_TO_CURSOR) {
/*  55 */       event.setCancelled(true);
/*  56 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  68 */     TradeManager tradeManager = target.getTradeManager();
/*     */     
/*  70 */     if (event.isShiftClick()) {
/*  71 */       target.getPlayer().sendMessage(this.p.c("errNoShiftClick"));
/*  72 */       event.setCancelled(true);
/*  73 */       target.getPlayer().updateInventory();
/*  74 */       return;
/*     */     }
/*     */     
/*  77 */     ItemStack item = event.getCurrentItem();
/*  78 */     if (item == null) {
/*  79 */       return;
/*     */     }
/*     */     
/*  82 */     if (tradeManager.isProtectedBlock(item)) {
/*  83 */       event.setCancelled(true);
/*     */       
/*     */ 
/*  86 */       if (item.getItemMeta().getDisplayName().contains("Accept"))
/*     */       {
/*  88 */         if (((String)item.getItemMeta().getLore().get(0)).contains(event.getWhoClicked().getName())) {
/*  89 */           tradeManager.onItemAccept(target.isSender);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/*  94 */     else if (target.getTradeManager().isPlaceableSlot(event.getRawSlot(), !target.isSender())) {
/*  95 */       target.getPlayer().sendMessage(this.p.c("errUseOtherSide"));
/*     */       
/*  97 */       event.setCancelled(true);
/*  98 */       target.getTradeManager().updateRequest();
/*     */     }
/*     */     else
/*     */     {
/* 102 */       tradeManager.onItemChange();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   @EventHandler
/*     */   public void onInventoryClose(InventoryCloseEvent event)
/*     */   {
/* 111 */     if (!event.getView().getTitle().equalsIgnoreCase("Cloud Trading")) {
/* 112 */       return;
/*     */     }
/*     */     
/* 115 */     Player sender = (Player)event.getPlayer();
/*     */     
/*     */ 
/* 118 */     TradePlayer target = (TradePlayer)this.p.getTraders().get(sender.getName());
/*     */     
/*     */ 
/* 121 */     if (target == null) {
/* 122 */       return;
/*     */     }
/*     */     
/*     */ 
/* 126 */     if (target.getTradeManager().isAccepted()) {
/* 127 */       target.getTradeManager().endTrade();
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryOpen(org.bukkit.event.inventory.InventoryOpenEvent event)
/*     */   {
/* 134 */     Player player = (Player)event.getPlayer();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryDrag(InventoryDragEvent event)
/*     */   {
/* 140 */     if (!event.getInventory().getTitle().equals("Cloud Trading")) {
/* 141 */       return;
/*     */     }
/*     */     
/* 144 */     if (event.getInventorySlots().size() > 1)
/*     */     {
/* 146 */       event.setCancelled(true);
/*     */     }
/* 148 */     java.util.Set<Integer> test = event.getRawSlots();
/* 149 */     int rawSlot = ((Integer)test.iterator().next()).intValue();
/*     */     
/*     */ 
/* 152 */     TradePlayer target = (TradePlayer)this.p.getTraders().get(event.getWhoClicked().getName());
/*     */     
/* 154 */     if (target == null) {
/* 155 */       return;
/*     */     }
/*     */     
/*     */ 
/* 159 */     ItemStack item = event.getOldCursor();
/* 160 */     if (item == null) {
/* 161 */       return;
/*     */     }
/* 163 */     TradeManager tradeManager = target.getTradeManager();
/*     */     
/* 165 */     if (tradeManager.isProtectedBlock(item)) {
/* 166 */       event.setCancelled(true);
/*     */       
/*     */ 
/* 169 */       if (item.getItemMeta().getDisplayName().contains("Aceitar"))
/*     */       {
/* 171 */         if (((String)item.getItemMeta().getLore().get(0)).contains(event.getWhoClicked().getName())) {
/* 172 */           tradeManager.onItemAccept(target.isSender);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/* 177 */     else if (target.getTradeManager().isPlaceableSlot(rawSlot, !target.isSender())) {
/* 178 */       target.getPlayer().sendMessage(this.p.c("errUseOtherSide"));
/*     */       
/* 180 */       event.setCancelled(true);
/* 181 */       target.getTradeManager().updateRequest();
/*     */     }
/*     */     else
/*     */     {
/* 185 */       tradeManager.onItemChange();
/*     */     }
/*     */   }
/*     */ }


