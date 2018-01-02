/*    */ package com.solarcloud7.cloudtrade;
/*    */ 
/*    */ import org.bukkit.Server;
/*    */ 
/*    */ public class TradeRequest
/*    */ {
/*    */   private TradeManager tradeManager;
/*    */   private CloudTrade plugin;
/*    */   TradePlayer sender;
/*    */   TradePlayer target;
/*    */   private int taskId;
/*    */   
/*    */   public TradeRequest(TradeManager _tradeManager) {
/* 14 */     this.tradeManager = _tradeManager;
/* 15 */     this.plugin = this.tradeManager.getPlugin();
/*    */   }
/*    */   
/*    */   public void requestTrade() {
/* 19 */     this.sender = this.tradeManager.getTPsender();
/* 20 */     this.target = this.tradeManager.getTPsender();
/*    */     
/*    */ 
/* 23 */     timeRequest(this.plugin.getConfig().getInt("tradeAcceptTimeout"));
/*    */   }
/*    */   
/*    */   private void timeRequest(int seconds)
/*    */   {
/*    */     try {
/* 29 */       long s = seconds * 20;
/* 30 */       this.taskId = this.plugin.getServer().getScheduler().scheduleSyncDelayedTask(this.plugin, new Runnable()
/*    */       {
/*    */ 
/* 33 */         public void run() { TradeRequest.this.cancelRequest(); } }, s);
/*    */     }
/*    */     catch (Exception e) {}
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */   public void cancelRequest()
/*    */   {
/* 42 */     if (this.tradeManager.isAccepted() == true) {
/* 43 */       return;
/*    */     }
/* 45 */     if (this.tradeManager.isDeclined() == true) {
/* 46 */       return;
/*    */     }
/* 48 */     this.tradeManager.declineTrade();
/*    */   }
/*    */   
/*    */   public int getTaskId() {
/* 52 */     return this.taskId;
/*    */   }
/*    */ }

