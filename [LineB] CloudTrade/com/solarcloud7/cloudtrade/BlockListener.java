/*    */ package com.solarcloud7.cloudtrade;
/*    */ 
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.block.BlockCanBuildEvent;
/*    */ import org.bukkit.event.block.BlockPhysicsEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BlockListener
/*    */   implements Listener
/*    */ {
/*    */   private final CloudTrade plugin;
/*    */   
/*    */   public BlockListener(CloudTrade instance)
/*    */   {
/* 21 */     this.plugin = instance;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onBlockPhysics(BlockPhysicsEvent event) {}
/*    */   
/*    */   @EventHandler
/*    */   public void onBlockCanBuild(BlockCanBuildEvent event) {}
/*    */ }


/