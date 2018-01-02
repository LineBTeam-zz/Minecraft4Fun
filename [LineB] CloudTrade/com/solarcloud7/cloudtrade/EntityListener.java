/*    */ package com.solarcloud7.cloudtrade;
/*    */ 
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
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
/*    */ public class EntityListener
/*    */   implements Listener
/*    */ {
/*    */   private final CloudTrade plugin;
/* 21 */   private int itemNumber = 0;
/*    */   
/*    */   public EntityListener(CloudTrade instance) {
/* 24 */     this.plugin = instance;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {}
/*    */ }


