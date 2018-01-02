/*     */ package com.solarcloud7.cloudtrade;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
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
/*     */ public class TradeChestLayout
/*     */ {
/*     */   private CloudTrade plugin;
/*     */   private TradeManager tradeManager;
/*     */   private TradePlayer TPsender;
/*     */   private TradePlayer TPtarget;
/*     */   private Inventory tradeChest;
/*     */   private ItemStack separator;
/*     */   private ItemStack status;
/*     */   private ItemStack cancelButton;
/*     */   private ItemStack pAccept;
/*     */   private ItemStack tAccept;
/*  33 */   public static int ChestSize = 54;
/*     */   
/*     */ 
/*     */   public static final String tradeboxName = "Cloud Trading";
/*     */   
/*     */ 
/*  39 */   String[] protectedNames = { "Acitar", "Divisor", "Intruções", "Status" };
/*  40 */   List protNameList = Arrays.asList(this.protectedNames);
/*     */   
/*     */   public TradeChestLayout(TradeManager _tradeManager) {
/*  43 */     this.tradeManager = _tradeManager;
/*  44 */     this.plugin = this.tradeManager.getPlugin();
/*  45 */     this.TPsender = this.tradeManager.getTPsender();
/*  46 */     this.TPtarget = this.tradeManager.getTPtarget();
/*     */   }
/*     */   
/*     */   public Inventory createTradeChest()
/*     */   {
/*  51 */     this.tradeChest = this.plugin.getServer().createInventory(null, ChestSize, "Cloud Trading");
/*     */     
/*     */ 
/*  54 */     this.separator = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)15);
/*  55 */     ItemMeta IM = this.separator.getItemMeta();
/*  56 */     IM.setLore(null);
/*  57 */     IM.setDisplayName("Divisor");
/*  58 */     List<String> separatorLore = Arrays.asList(new String[] { this.TPsender.getPlayer().getName(), "<------", "", this.TPtarget.getPlayer().getName(), "------>" });
/*  59 */     IM.setLore(separatorLore);
/*  60 */     this.separator.setItemMeta(IM);
/*     */     
/*     */ 
/*  63 */     this.pAccept = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
/*  64 */     ItemMeta pAcceptMeta = this.pAccept.getItemMeta();
/*  65 */     pAcceptMeta.setLore(null);
/*  66 */     pAcceptMeta.setDisplayName("Aceitar");
/*  67 */     List<String> pAcceptMetaLore = Arrays.asList(new String[] { this.TPsender.getPlayer().getName() });
/*  68 */     pAcceptMeta.setLore(pAcceptMetaLore);
/*  69 */     this.pAccept.setItemMeta(pAcceptMeta);
/*     */     
/*     */ 
/*  72 */     this.tAccept = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
/*  73 */     ItemMeta tAcceptMeta = this.tAccept.getItemMeta();
/*  74 */     tAcceptMeta.setLore(null);
/*  75 */     tAcceptMeta.setDisplayName("Aceitar");
/*  76 */     List<String> tAcceptMetaLore = Arrays.asList(new String[] { this.TPtarget.getPlayer().getName() });
/*  77 */     tAcceptMeta.setLore(tAcceptMetaLore);
/*  78 */     this.tAccept.setItemMeta(tAcceptMeta);
/*     */     
/*     */ 
/*  81 */     String sender = this.TPsender.getPlayer().getName() + " Aceito: False";
/*  82 */     String target = this.TPtarget.getPlayer().getName() + " Aceito: False";
/*     */     
/*     */ 
/*  85 */     this.status = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
/*  86 */     ItemMeta statusButtonMeta = this.status.getItemMeta();
/*  87 */     statusButtonMeta.setDisplayName("Status");
/*  88 */     List<String> statusLore = Arrays.asList(new String[] { sender, target });
/*  89 */     statusButtonMeta.setLore(statusLore);
/*  90 */     this.status.setItemMeta(statusButtonMeta);
/*     */     
/*     */ 
/*     */ 
/*  94 */     this.tradeChest.setItem(3, this.pAccept);
/*  95 */     this.tradeChest.setItem(4, this.status);
/*  96 */     this.tradeChest.setItem(5, this.tAccept);
/*  97 */     this.tradeChest.setItem(13, this.separator);
/*  98 */     this.tradeChest.setItem(22, this.separator);
/*  99 */     this.tradeChest.setItem(31, this.separator);
/* 100 */     this.tradeChest.setItem(40, this.separator);
/* 101 */     this.tradeChest.setItem(49, this.separator);
/* 102 */     return this.tradeChest;
/*     */   }
/*     */   
/*     */   public void updateStatusBlock(boolean SenderAprove, boolean TargetAprove) {
/* 106 */     String sender = this.TPtarget.getPlayer().getName() + " Aceitou: " + SenderAprove;
/* 107 */     String target = this.TPsender.getPlayer().getName() + " Aceitou: " + TargetAprove;
/*     */     
/*     */ 
/* 110 */     this.status = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)5);
/* 111 */     ItemMeta statusButtonMeta = this.status.getItemMeta();
/* 112 */     statusButtonMeta.setDisplayName("Status");
/* 113 */     List<String> statusLore = Arrays.asList(new String[] { sender, target });
/* 114 */     statusButtonMeta.setLore(statusLore);
/* 115 */     this.status.setItemMeta(statusButtonMeta);
/* 116 */     this.tradeChest.setItem(4, this.status);
/*     */     
/*     */ 
/* 119 */     this.pAccept = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
/* 120 */     ItemMeta pAcceptMeta = this.pAccept.getItemMeta();
/* 121 */     pAcceptMeta.setLore(null);
/* 122 */     pAcceptMeta.setDisplayName("Aceitou");
/* 123 */     List<String> pAcceptMetaLore = Arrays.asList(new String[] { this.TPsender.getPlayer().getName() });
/* 124 */     pAcceptMeta.setLore(pAcceptMetaLore);
/* 125 */     this.pAccept.setItemMeta(pAcceptMeta);
/* 126 */     if (SenderAprove) {
/* 127 */       this.tradeChest.setItem(3, this.pAccept);
/*     */     } else {
/* 129 */       this.tradeChest.setItem(3, this.pAccept);
/*     */     }
/*     */     
/*     */ 
/* 133 */     this.tAccept = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
/* 134 */     ItemMeta tAcceptMeta = this.tAccept.getItemMeta();
/* 135 */     tAcceptMeta.setLore(null);
/* 136 */     tAcceptMeta.setDisplayName("Aceitou");
/* 137 */     List<String> tAcceptMetaLore = Arrays.asList(new String[] { this.TPtarget.getPlayer().getName() });
/* 138 */     tAcceptMeta.setLore(tAcceptMetaLore);
/* 139 */     this.tAccept.setItemMeta(tAcceptMeta);
/* 140 */     if (TargetAprove) {
/* 141 */       this.tradeChest.setItem(5, this.tAccept);
/*     */     } else {
/* 143 */       this.tradeChest.setItem(5, this.tAccept);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void resetStatusBlock(boolean SenderAprove, boolean TargetAprove)
/*     */   {
/* 151 */     String sender = this.TPsender.getPlayer().getName() + " Aceitou: False";
/* 152 */     String target = this.TPtarget.getPlayer().getName() + " Aceitou: False";
/*     */     
/*     */ 
/* 155 */     this.status = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)14);
/* 156 */     ItemMeta statusButtonMeta = this.status.getItemMeta();
/* 157 */     statusButtonMeta.setDisplayName("Status");
/* 158 */     List<String> statusLore = Arrays.asList(new String[] { sender, target });
/* 159 */     statusButtonMeta.setLore(statusLore);
/* 160 */     this.status.setItemMeta(statusButtonMeta);
/*     */     
/* 162 */     this.tradeChest.setItem(4, this.status);
/*     */     
/*     */ 
/* 165 */     this.pAccept = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
/* 166 */     ItemMeta pAcceptMeta = this.pAccept.getItemMeta();
/* 167 */     pAcceptMeta.setLore(null);
/* 168 */     pAcceptMeta.setDisplayName("Aceitar");
/* 169 */     List<String> pAcceptMetaLore = Arrays.asList(new String[] { this.TPsender.getPlayer().getName() });
/* 170 */     pAcceptMeta.setLore(pAcceptMetaLore);
/* 171 */     this.pAccept.setItemMeta(pAcceptMeta);
/* 172 */     if (SenderAprove) {
/* 173 */       this.tradeChest.setItem(3, this.pAccept);
/*     */     } else {
/* 175 */       this.tradeChest.setItem(3, this.pAccept);
/*     */     }
/*     */     
/*     */ 
/* 179 */     this.tAccept = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)11);
/* 180 */     ItemMeta tAcceptMeta = this.tAccept.getItemMeta();
/* 181 */     tAcceptMeta.setLore(null);
/* 182 */     tAcceptMeta.setDisplayName("Aceitar");
/* 183 */     List<String> tAcceptMetaLore = Arrays.asList(new String[] { this.TPtarget.getPlayer().getName() });
/* 184 */     tAcceptMeta.setLore(tAcceptMetaLore);
/* 185 */     this.tAccept.setItemMeta(tAcceptMeta);
/* 186 */     if (TargetAprove) {
/* 187 */       this.tradeChest.setItem(5, this.tAccept);
/*     */     } else {
/* 189 */       this.tradeChest.setItem(5, this.tAccept);
/*     */     }
/*     */   }
/*     */ }


