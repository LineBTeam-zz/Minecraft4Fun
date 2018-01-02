/*    */ package com.solarcloud7.cloudtrade;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class TradePlayer
/*    */ {
/*    */   private final TradeManager tradeManager;
/* 11 */   private Player player = null;
/*    */   
/*    */   boolean isSender;
/*    */   
/*    */   public TradePlayer(TradeManager _tradeManager, Player _player, TradeManager.Role _role)
/*    */   {
/* 17 */     this.tradeManager = _tradeManager;
/* 18 */     this.player = _player;
/* 19 */     this.isSender = (_role == TradeManager.Role.SENDER);
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 23 */     return this.player;
/*    */   }
/*    */   
/*    */   public TradeManager getTradeManager() {
/* 27 */     return this.tradeManager;
/*    */   }
/*    */   
/* 30 */   public boolean isTradeManagerSender() { return this.tradeManager.getTPsender() == this; }
/*    */   
/*    */   public boolean isSender()
/*    */   {
/* 34 */     return this.isSender;
/*    */   }
/*    */ }
