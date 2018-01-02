/*     */ package com.solarcloud7.cloudtrade;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.entity.PlayerDeathEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerListener
/*     */   implements Listener
/*     */ {
/*     */   private final CloudTrade p;
/*     */   
/*     */   public PlayerListener(CloudTrade instance)
/*     */   {
/*  26 */     this.p = instance;
/*     */   }
/*     */   
/*     */ 
/*     */   @EventHandler
/*     */   public void onPlayerJoin(PlayerJoinEvent event) {}
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerQuit(PlayerQuitEvent event)
/*     */   {
/*  36 */     Player sender = event.getPlayer();
/*     */     
/*  38 */     TradePlayer target = (TradePlayer)this.p.getTraders().get(sender.getName());
/*  39 */     if (target == null) {
/*  40 */       return;
/*     */     }
/*     */     
/*     */ 
/*  44 */     if (target.getTradeManager().isAccepted()) {
/*  45 */       target.getTradeManager().endTrade();
/*     */     } else {
/*  47 */       target.getTradeManager().declineTrade();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onPlayerInteract(PlayerInteractEntityEvent event)
/*     */   {
/*  55 */     if (event.getRightClicked().getType() != EntityType.PLAYER) {
/*  56 */       return;
/*     */     }
/*     */     
/*  59 */     Player player = event.getPlayer();
/*  60 */     Player target = (Player)event.getRightClicked();
/*     */     
/*  62 */     Boolean isShift = Boolean.valueOf(player.isSneaking());
/*  63 */     Boolean isControl = Boolean.valueOf(player.isSprinting());
/*  64 */     Boolean shift = Boolean.valueOf(this.p.getConfig().getBoolean("shift-right-click"));
/*  65 */     Boolean control = Boolean.valueOf(this.p.getConfig().getBoolean("sprint-click"));
/*     */     
/*  67 */     if ((!shift.booleanValue()) && (!control.booleanValue())) {
/*  68 */       return;
/*     */     }
/*  70 */     Boolean pass = Boolean.valueOf(false);
/*     */     
/*  72 */     if ((shift.booleanValue()) && 
/*  73 */       (isShift.booleanValue())) {
/*  74 */       pass = Boolean.valueOf(true);
/*     */     }
/*     */     
/*  77 */     if ((control.booleanValue()) && 
/*  78 */       (isControl.booleanValue())) {
/*  79 */       pass = Boolean.valueOf(true);
/*     */     }
/*     */     
/*  82 */     if (!pass.booleanValue()) {
/*  83 */       return;
/*     */     }
/*     */     
/*     */ 
/*  87 */     if (!player.hasPermission("cloudtrade.cantrade")) {
/*  88 */       player.sendMessage(this.p.c("errPermissions"));
/*  89 */       return;
/*     */     }
/*  91 */     if (!player.hasPermission("cloudtrade.rightclickrequest")) {
/*  92 */       player.sendMessage(this.p.c("errPermissionsRightClick"));
/*  93 */       return;
/*     */     }
/*  95 */     if ((this.p.getConfig().getBoolean("bothPlayersMustHavePermissions")) && 
/*  96 */       (!target.hasPermission("cloudtrade.cantrade"))) {
/*  97 */       player.sendMessage(this.p.c("errTargetNoPermission"));
/*  98 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 103 */     TradePlayer TPplayer = (TradePlayer)this.p.getRequests().get(player.getName());
/* 104 */     if (TPplayer != null) {
/* 105 */       TPplayer.getTradeManager().acceptTrade();
/* 106 */       return;
/*     */     }
/*     */     
/*     */ 
/* 110 */     TradeManager tm = new TradeManager(this.p, player, target);
/*     */     
/* 112 */     if (tm.isSenderBusy()) {
/* 113 */       player.sendMessage(this.p.c("errBusyWithTrade"));
/* 114 */       return; }
/* 115 */     if (tm.isTargetBusy()) {
/* 116 */       player.sendMessage(this.p.c("errTargetBusy"));
/* 117 */       return;
/*     */     }
/* 119 */     tm.sendRequest();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerDeath(PlayerDeathEvent event)
/*     */   {
/* 125 */     Player sender = event.getEntity();
/*     */     
/* 127 */     TradePlayer target = (TradePlayer)this.p.getTraders().get(sender.getName());
/* 128 */     if (target == null) {
/* 129 */       return;
/*     */     }
/*     */     
/*     */ 
/* 133 */     if (target.getTradeManager().isAccepted()) {
/* 134 */       target.getTradeManager().endTrade();
/*     */     } else {
/* 136 */       target.getTradeManager().declineTrade();
/*     */     }
/*     */   }
/*     */ }


