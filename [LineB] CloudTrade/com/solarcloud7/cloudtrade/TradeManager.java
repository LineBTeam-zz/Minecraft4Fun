/*     */ package com.solarcloud7.cloudtrade;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.Sound;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryView;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class TradeManager
/*     */ {
/*     */   private CloudTrade p;
/*     */   private TradeRequest tradeRequest;
/*     */   private TradeChestLayout tcLayout;
/*  25 */   private boolean targetAccepted = false;
/*  26 */   private boolean targetDeclined = false;
/*     */   
/*     */   private TradePlayer TPsender;
/*     */   
/*     */   private TradePlayer TPtarget;
/*     */   
/*     */   private Inventory tradeChest;
/*  33 */   private boolean SenderAprove = false;
/*  34 */   private boolean TargetAprove = false;
/*     */   
/*     */   List<ItemStack> senderInventory;
/*     */   
/*     */   List<ItemStack> targetInventory;
/*     */   
/*     */   public static String endTradeMessage;
/*     */   
/*  42 */   public static final List<Integer> senderSlots = Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(9), Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(12), Integer.valueOf(18), Integer.valueOf(19), Integer.valueOf(20), Integer.valueOf(21), Integer.valueOf(27), Integer.valueOf(28), Integer.valueOf(29), Integer.valueOf(30), Integer.valueOf(36), Integer.valueOf(37), Integer.valueOf(38), Integer.valueOf(39), Integer.valueOf(45), Integer.valueOf(46), Integer.valueOf(47), Integer.valueOf(48) });
/*  43 */   public static final List<Integer> targetSlots = Arrays.asList(new Integer[] { Integer.valueOf(6), Integer.valueOf(7), Integer.valueOf(8), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(17), Integer.valueOf(23), Integer.valueOf(24), Integer.valueOf(25), Integer.valueOf(26), Integer.valueOf(32), Integer.valueOf(33), Integer.valueOf(34), Integer.valueOf(35), Integer.valueOf(41), Integer.valueOf(42), Integer.valueOf(43), Integer.valueOf(44), Integer.valueOf(50), Integer.valueOf(51), Integer.valueOf(52), Integer.valueOf(53) });
/*     */   
/*     */   public static enum Role
/*     */   {
/*  47 */     SENDER,  TARGET;
/*     */     
/*     */     private Role() {} }
/*  50 */   String[] protectedNames = { "Aceitar", "Divisor", "Instuções", "Status" };
/*  51 */   List protNameList = Arrays.asList(this.protectedNames);
/*     */   
/*     */   public TradeManager(CloudTrade plugin, Player main, Player target) {
/*  54 */     this.p = plugin;
/*  55 */     this.TPsender = new TradePlayer(this, main, Role.SENDER);
/*  56 */     this.TPtarget = new TradePlayer(this, target, Role.TARGET);
/*     */     
/*  58 */     this.tcLayout = new TradeChestLayout(this);
/*     */     
/*  60 */     endTradeMessage = this.p.c("tradeComplete");
/*     */   }
/*     */   
/*     */   public void onItemChange()
/*     */   {
/*  65 */     this.SenderAprove = false;
/*  66 */     this.TargetAprove = false;
/*     */     
/*  68 */     this.tcLayout.resetStatusBlock(this.SenderAprove, this.TargetAprove);
/*  69 */     updateRequest();
/*     */   }
/*     */   
/*     */   public void completeTrade()
/*     */   {
/*  74 */     List<ItemStack> targItems = getTargetItems();
/*  75 */     List<ItemStack> sendItems = getSenderItems();
/*     */     TradeLogger log;
/*  77 */     if (this.p.getConfig().getBoolean("logAllTrades")) {
/*  78 */       log = new TradeLogger(this, targItems, sendItems);
/*     */     }
/*     */     
/*  81 */     addToInventorySender(targItems);
/*  82 */     addToInventoryTarget(sendItems);
/*     */     
/*  84 */     this.TPsender.getPlayer().sendMessage(endTradeMessage);
/*  85 */     this.TPtarget.getPlayer().sendMessage(endTradeMessage);
/*     */     
/*  87 */     this.p.getTraders().remove(this.TPsender.getPlayer().getName());
/*  88 */     this.p.getTraders().remove(this.TPtarget.getPlayer().getName());
/*     */     
/*  90 */     this.p.getRequests().remove(this.TPtarget.getPlayer().getName());
/*     */     
/*  92 */     this.TPsender.getPlayer().getOpenInventory().close();
/*  93 */     this.TPtarget.getPlayer().getOpenInventory().close();
/*     */     
/*  95 */     this.TPsender.getPlayer().playSound(this.TPsender.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 1.0F);
/*  96 */     this.TPtarget.getPlayer().playSound(this.TPtarget.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 1.0F);
/*     */     
/*  98 */     this.TPsender.getPlayer().updateInventory();
/*  99 */     this.TPtarget.getPlayer().updateInventory();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onItemAccept(boolean _isSender)
/*     */   {
/* 106 */     if (_isSender) {
/* 107 */       this.SenderAprove = true;
/*     */     } else {
/* 109 */       this.TargetAprove = true;
/*     */     }
/*     */     
/* 112 */     if ((this.SenderAprove) && (this.TargetAprove)) {
/* 113 */       completeTrade();
/*     */     }
/*     */     else {
/* 116 */       this.tcLayout.updateStatusBlock(this.SenderAprove, this.TargetAprove);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void sendRequest()
/*     */   {
/* 126 */     this.p.getTraders().put(this.TPsender.getPlayer().getName(), this.TPsender);
/* 127 */     this.p.getTraders().put(this.TPtarget.getPlayer().getName(), this.TPtarget);
/*     */     
/*     */ 
/* 130 */     this.p.getRequests().put(this.TPtarget.getPlayer().getName(), this.TPtarget);
/*     */     
/*     */ 
/* 133 */     this.tradeRequest = new TradeRequest(this);
/* 134 */     this.tradeRequest.requestTrade();
/*     */     
/*     */ 
/* 137 */     this.TPsender.getPlayer().sendMessage(this.p.c("requestSent").replace("%player", this.TPtarget.getPlayer().getName()));
/* 138 */     this.TPtarget.getPlayer().sendMessage(this.p.c("requestRecieved").replace("%player", this.TPsender.getPlayer().getName()));
/*     */   }
/*     */   
/*     */   public void acceptTrade()
/*     */   {
/* 143 */     this.targetAccepted = true;
/*     */     
/* 145 */     startTrade();
/*     */   }
/*     */   
/*     */   public void declineTrade()
/*     */   {
/* 150 */     this.p.getServer().getScheduler().cancelTask(this.tradeRequest.getTaskId());
/*     */     
/* 152 */     this.p.getTraders().remove(this.TPsender.getPlayer().getName());
/* 153 */     this.p.getTraders().remove(this.TPtarget.getPlayer().getName());
/*     */     
/* 155 */     this.p.getRequests().remove(this.TPtarget.getPlayer().getName());
/* 156 */     this.TPsender.getPlayer().sendMessage(this.p.c("denyRecieved").replace("%player", this.TPtarget.getPlayer().getName()));
/* 157 */     this.TPtarget.getPlayer().sendMessage(this.p.c("denyRecieved").replace("%player", this.TPsender.getPlayer().getName()));
/*     */   }
/*     */   
/*     */   public void startTrade()
/*     */   {
/* 162 */     this.tradeChest = this.tcLayout.createTradeChest();
/*     */     
/*     */ 
/* 165 */     this.TPsender.getPlayer().openInventory(this.tradeChest);
/* 166 */     this.TPsender.getPlayer().playSound(this.TPsender.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 1.0F);
/* 167 */     this.TPtarget.getPlayer().openInventory(this.tradeChest);
/* 168 */     this.TPtarget.getPlayer().playSound(this.TPtarget.getPlayer().getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 1.0F);
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isProtectedBlock(ItemStack testblock)
/*     */   {
/* 174 */     if (testblock.getItemMeta() == null) {
/* 175 */       return false;
/*     */     }
/*     */     
/* 178 */     String displaName = testblock.getItemMeta().getDisplayName();
/*     */     
/* 180 */     if (this.protNameList.contains(displaName)) {
/* 181 */       return true;
/*     */     }
/* 183 */     return false;
/*     */   }
/*     */   
/*     */   public boolean isPlaceableSlot(int slot, boolean isSender) {
/* 187 */     if (isSender) {
/* 188 */       if (senderSlots.contains(Integer.valueOf(slot))) {
/* 189 */         return true;
/*     */       }
/*     */     }
/* 192 */     else if (targetSlots.contains(Integer.valueOf(slot))) {
/* 193 */       return true;
/*     */     }
/*     */     
/* 196 */     return false;
/*     */   }
/*     */   
/*     */   public void updateRequest() {
/*     */     try {
/* 201 */       this.p.getServer().getScheduler().scheduleSyncDelayedTask(this.p, new Runnable()
/*     */       {
/*     */         public void run() {
/* 204 */           TradeManager.this.TPsender.getPlayer().updateInventory();
/* 205 */           TradeManager.this.TPtarget.getPlayer().updateInventory();
/*     */         }
/*     */       });
/*     */     }
/*     */     catch (Exception e) {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void endTrade()
/*     */   {
/* 216 */     this.p.getTraders().remove(this.TPsender.getPlayer().getName());
/* 217 */     this.p.getTraders().remove(this.TPtarget.getPlayer().getName());
/*     */     
/* 219 */     this.p.getRequests().remove(this.TPtarget.getPlayer().getName());
/*     */     
/* 221 */     addToInventorySender(getSenderItems());
/* 222 */     addToInventoryTarget(getTargetItems());
/*     */     
/* 224 */     this.TPsender.getPlayer().sendMessage(this.p.c("tradeCanceled"));
/* 225 */     this.TPsender.getPlayer().getOpenInventory().close();
/* 226 */     this.TPsender.getPlayer().playSound(this.TPsender.getPlayer().getLocation(), Sound.ZOMBIE_PIG_ANGRY, 10.0F, 1.0F);
/* 227 */     this.TPtarget.getPlayer().sendMessage(this.p.c("tradeCanceled"));
/* 228 */     this.TPtarget.getPlayer().getOpenInventory().close();
/* 229 */     this.TPtarget.getPlayer().playSound(this.TPtarget.getPlayer().getLocation(), Sound.ZOMBIE_PIG_ANGRY, 10.0F, 1.0F);
/*     */     
/* 231 */     this.TPsender.getPlayer().updateInventory();
/* 232 */     this.TPtarget.getPlayer().updateInventory();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void resetTrade() {}
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSenderBusy()
/*     */   {
/* 245 */     boolean busy = this.p.getTraders().containsKey(this.TPsender.getPlayer().getName());
/* 246 */     return busy;
/*     */   }
/*     */   
/*     */   public boolean isTargetBusy()
/*     */   {
/* 251 */     boolean busy = this.p.getTraders().containsKey(this.TPtarget.getPlayer().getName());
/* 252 */     return busy;
/*     */   }
/*     */   
/*     */   public CloudTrade getPlugin() {
/* 256 */     return this.p;
/*     */   }
/*     */   
/*     */   public TradePlayer getTPsender() {
/* 260 */     return this.TPsender;
/*     */   }
/*     */   
/*     */   public TradePlayer getTPtarget() {
/* 264 */     return this.TPtarget;
/*     */   }
/*     */   
/*     */   public boolean isAccepted()
/*     */   {
/* 269 */     return this.targetAccepted;
/*     */   }
/*     */   
/*     */   public void setAccepted(boolean _targetAccepted) {
/* 273 */     this.targetAccepted = _targetAccepted;
/*     */   }
/*     */   
/*     */   public boolean isDeclined()
/*     */   {
/* 278 */     return this.targetDeclined;
/*     */   }
/*     */   
/*     */   public void setDeclined(boolean _targetDeclined) {
/* 282 */     this.targetDeclined = _targetDeclined;
/*     */   }
/*     */   
/*     */   public List<ItemStack> getSenderItems() {
/* 286 */     this.senderInventory = new ArrayList();
/*     */     
/* 288 */     for (int i = 0; i < senderSlots.size(); i++) {
/* 289 */       int slot = ((Integer)senderSlots.get(i)).intValue();
/* 290 */       this.senderInventory.add(this.tradeChest.getItem(slot));
/*     */     }
/* 292 */     return this.senderInventory;
/*     */   }
/*     */   
/*     */   public List<ItemStack> getTargetItems() {
/* 296 */     this.targetInventory = new ArrayList();
/*     */     
/* 298 */     for (int i = 0; i < targetSlots.size(); i++) {
/* 299 */       int slot = ((Integer)targetSlots.get(i)).intValue();
/* 300 */       this.targetInventory.add(this.tradeChest.getItem(slot));
/*     */     }
/* 302 */     return this.targetInventory;
/*     */   }
/*     */   
/*     */   public void addToInventorySender(List<ItemStack> addedItemStacks) {
/* 306 */     String messageItems = this.p.c("added");
/* 307 */     Inventory sortInventory = this.p.getServer().createInventory(null, TradeChestLayout.ChestSize);
/*     */     
/* 309 */     for (int i = 0; i < addedItemStacks.size(); i++) {
/* 310 */       if (addedItemStacks.get(i) != null)
/*     */       {
/*     */ 
/* 313 */         sortInventory.addItem(new ItemStack[] { (ItemStack)addedItemStacks.get(i) });
/*     */       }
/*     */     }
/* 316 */     for (int i = 0; i < sortInventory.getContents().length; i++) {
/* 317 */       ItemStack oneItem = sortInventory.getItem(i);
/* 318 */       if (oneItem != null)
/*     */       {
/*     */ 
/*     */ 
/* 322 */         if (this.TPsender.getPlayer().getInventory().firstEmpty() == -1)
/*     */         {
/* 324 */           Location loc = this.TPsender.getPlayer().getLocation();
/* 325 */           this.TPsender.getPlayer().getWorld().dropItem(loc, oneItem);
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 330 */           this.TPsender.getPlayer().getInventory().addItem(new ItemStack[] { oneItem });
/*     */         }
/*     */         
/*     */ 
/* 334 */         String oneItemDescription = ChatColor.AQUA + oneItem.getType().name() + "x" + ChatColor.DARK_GREEN + oneItem.getAmount() + "  ";
/*     */         
/* 336 */         messageItems = messageItems + oneItemDescription;
/*     */       } }
/* 338 */     if (this.p.getConfig().getBoolean("displayItemsAfterTrade")) {
/* 339 */       this.TPsender.getPlayer().sendMessage(messageItems);
/*     */     }
/*     */   }
/*     */   
/*     */   public void addToInventoryTarget(List<ItemStack> addedItemStacks) {
/* 344 */     String messageItems = this.p.c("added");
/* 345 */     Inventory sortInventory = this.p.getServer().createInventory(null, TradeChestLayout.ChestSize);
/*     */     
/* 347 */     for (int i = 0; i < addedItemStacks.size(); i++) {
/* 348 */       if (addedItemStacks.get(i) != null)
/*     */       {
/*     */ 
/* 351 */         sortInventory.addItem(new ItemStack[] { (ItemStack)addedItemStacks.get(i) });
/*     */       }
/*     */     }
/* 354 */     for (int i = 0; i < sortInventory.getContents().length; i++) {
/* 355 */       ItemStack oneItem = sortInventory.getItem(i);
/* 356 */       if (oneItem != null)
/*     */       {
/*     */ 
/*     */ 
/* 360 */         if (this.TPtarget.getPlayer().getInventory().firstEmpty() == -1)
/*     */         {
/* 362 */           Location loc = this.TPtarget.getPlayer().getLocation();
/* 363 */           this.TPtarget.getPlayer().getWorld().dropItem(loc, oneItem);
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 368 */           this.TPtarget.getPlayer().getInventory().addItem(new ItemStack[] { oneItem });
/*     */         }
/*     */         
/*     */ 
/* 372 */         String oneItemDescription = ChatColor.AQUA + oneItem.getType().name() + "x" + ChatColor.DARK_GREEN + oneItem.getAmount() + "  ";
/*     */         
/* 374 */         messageItems = messageItems + oneItemDescription;
/*     */       } }
/* 376 */     if (this.p.getConfig().getBoolean("displayItemsAfterTrade")) {
/* 377 */       this.TPtarget.getPlayer().sendMessage(messageItems);
/*     */     }
/*     */   }
/*     */ }


