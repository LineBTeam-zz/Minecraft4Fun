/*     */ package com.solarcloud7.cloudtrade;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class CommandListener implements CommandExecutor
/*     */ {
/*     */   private CloudTrade p;
/*     */   
/*     */   public CommandListener(CloudTrade plugin)
/*     */   {
/*  21 */     this.p = plugin;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*     */   {
/*  28 */     if (!(sender instanceof Player)) {
/*  29 */       sender.sendMessage(this.p.c("errPlayer"));
/*  30 */       return true;
/*     */     }
/*  32 */     if (cmd.getName().equalsIgnoreCase("tradereload")) {
/*  33 */       if (!sender.hasPermission("cloudtrade.reload")) {
/*  34 */         sender.sendMessage(this.p.c("errPermissions"));
/*  35 */         return true;
/*     */       }
/*  37 */       this.p.reloadConfig();
/*  38 */       this.p.reloadLangFile();
/*  39 */       sender.sendMessage(this.p.c("reloaded"));
/*  40 */       return true;
/*     */     }
/*     */     
/*     */ 
/*  44 */     if (!cmd.getName().equalsIgnoreCase("trade")) {
/*  45 */       return false;
/*     */     }
/*     */     
/*  48 */     if (!sender.hasPermission("cloudtrade.cantrade")) {
/*  49 */       sender.sendMessage(this.p.c("errPermissions"));
/*  50 */       return true;
/*     */     }
/*     */     
/*  53 */     if (!this.p.getConfig().getBoolean("useCommandsForTrading")) {
/*  54 */       sender.sendMessage(this.p.c("errTradeCommandsOff"));
/*  55 */       return true;
/*     */     }
/*     */     
/*  58 */     if (args.length != 1) {
/*  59 */       return false;
/*     */     }
/*     */     
/*     */ 
/*  63 */     if (args[0].equalsIgnoreCase("accept"))
/*  64 */       return accept(sender, args);
/*  65 */     if (args[0].equalsIgnoreCase("deny")) {
/*  66 */       return decline(sender, args);
/*     */     }
/*  68 */     return request(sender, args);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean request(CommandSender sender, String[] args)
/*     */   {
/*  75 */     List<Player> matches = this.p.getServer().matchPlayer(args[0]);
/*     */     
/*  77 */     if (matches.isEmpty()) {
/*  78 */       sender.sendMessage(this.p.c("errTargetOffline").replace("%target", args[0]));
/*  79 */       return true;
/*     */     }
/*     */     
/*     */ 
/*  83 */     Player pSender = (Player)sender;
/*  84 */     Player pTarget = (Player)matches.get(0);
/*     */     
/*  86 */     if ((this.p.getConfig().getBoolean("bothPlayersMustHavePermissions")) && 
/*  87 */       (!pTarget.hasPermission("cloudtrade.cantrade"))) {
/*  88 */       pSender.sendMessage(this.p.c("errTargetNoPermission"));
/*  89 */       return true;
/*     */     }
/*     */     
/*     */ 
/*  93 */     if (pSender == pTarget) {
/*  94 */       pSender.sendMessage(this.p.c("errTradeSelf"));
/*  95 */       return true;
/*     */     }
/*     */     
/*  98 */     if (pSender.getGameMode() == GameMode.CREATIVE) {
/*  99 */       pSender.sendMessage(this.p.c("errTradeCreative"));
/*     */     }
/*     */     
/*     */ 
/* 103 */     if (!distCheck(pSender, pTarget)) {
/* 104 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 108 */     TradeManager tm = new TradeManager(this.p, pSender, pTarget);
/*     */     
/* 110 */     if (tm.isSenderBusy()) {
/* 111 */       pSender.sendMessage(this.p.c("errBusyWithTrade"));
/* 112 */       return true; }
/* 113 */     if (tm.isTargetBusy()) {
/* 114 */       pSender.sendMessage(this.p.c("errTargetBusy"));
/* 115 */       return true;
/*     */     }
/* 117 */     tm.sendRequest();
/*     */     
/* 119 */     return true;
/*     */   }
/*     */   
/*     */   private boolean distCheck(Player pSender, Player pTarget) {
/* 123 */     int maxDistance = this.p.getConfig().getInt("max-distance");
/*     */     
/* 125 */     if (!this.p.getConfig().getBoolean("tradeFromDifferentWorlds"))
/*     */     {
/*     */ 
/* 128 */       if (!pSender.getWorld().getName().equalsIgnoreCase(pTarget.getWorld().getName())) {
/* 129 */         pSender.sendMessage(this.p.c("errDifferentWorlds").replace("%player", pTarget.getName()));
/* 130 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 134 */       double realDistance = pSender.getLocation().distance(pTarget.getLocation());
/* 135 */       int dist = (int)realDistance;
/*     */       
/* 137 */       if (realDistance > maxDistance) {
/* 138 */         String m = this.p.c("errTooFarAway");
/* 139 */         m = m.replace("%target", pTarget.getName());
/* 140 */         m = m.replace("%maxDistance", Integer.toString(maxDistance));
/* 141 */         m = m.replace("%dist", Integer.toString(dist));
/* 142 */         pSender.sendMessage(m);
/* 143 */         return false;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 148 */     if (!pSender.getWorld().getName().equalsIgnoreCase(pTarget.getWorld().getName()))
/*     */     {
/* 150 */       if (maxDistance != 0) {
/* 151 */         pSender.sendMessage(this.p.c("errDifferentWorldsWithMaxTradeDistance").replace("%target", pTarget.getName()));
/* 152 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 156 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean accept(CommandSender sender, String[] args)
/*     */   {
/* 162 */     TradePlayer target = (TradePlayer)this.p.getRequests().get(sender.getName());
/*     */     
/*     */ 
/* 165 */     if (target == null) {
/* 166 */       sender.sendMessage(this.p.c("errNothingToAccept"));
/* 167 */       return true;
/*     */     }
/*     */     
/* 170 */     Player pSender = (Player)sender;
/* 171 */     if (pSender.getGameMode() == GameMode.CREATIVE) {
/* 172 */       pSender.sendMessage(this.p.c("errTradeCreative"));
/*     */     }
/*     */     
/* 175 */     Player s = target.getTradeManager().getTPsender().getPlayer();
/* 176 */     Player t = target.getTradeManager().getTPtarget().getPlayer();
/*     */     
/*     */ 
/* 179 */     if (!distCheck(t, s)) {
/* 180 */       return true;
/*     */     }
/*     */     
/* 183 */     target.getTradeManager().acceptTrade();
/* 184 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */   private boolean decline(CommandSender sender, String[] args)
/*     */   {
/* 190 */     TradePlayer target = (TradePlayer)this.p.getRequests().get(sender.getName());
/*     */     
/* 192 */     if (target == null) {
/* 193 */       sender.sendMessage(this.p.c("errNothingToDeny"));
/* 194 */       return true;
/*     */     }
/*     */     
/* 197 */     Player s = target.getTradeManager().getTPsender().getPlayer();
/* 198 */     Player t = target.getTradeManager().getTPtarget().getPlayer();
/*     */     
/*     */ 
/* 201 */     if (!distCheck(t, s)) {
/* 202 */       return true;
/*     */     }
/*     */     
/* 205 */     target.getTradeManager().declineTrade();
/* 206 */     return true;
/*     */   }
/*     */ }


