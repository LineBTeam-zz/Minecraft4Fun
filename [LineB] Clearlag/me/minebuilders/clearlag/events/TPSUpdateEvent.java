/*    */ package me.minebuilders.clearlag.events;
/*    */ 
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class TPSUpdateEvent extends Event
/*    */ {
/*  8 */   private static final HandlerList handlers = new HandlerList();
/*    */   private double tps;
/*    */   
/*    */   public TPSUpdateEvent(double tps)
/*    */   {
/* 13 */     this.tps = tps;
/*    */   }
/*    */   
/*    */   public double getTPS() {
/* 17 */     return this.tps;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 22 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 26 */     return handlers;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\events\TPSUpdateEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */