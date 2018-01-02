/*    */ package com.LineB.xrayinformer;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CustomRunnable
/*    */   implements Runnable
/*    */ {
/*    */   CommandSender sender;
/*    */   String world;
/*    */   int oreid;
/*    */   String bantype;
/*    */   float maxrate;
/*    */   boolean banned;
/*    */   int hours;
/*    */   
/*    */   public CustomRunnable(CommandSender sender, String world, int oreid, String bantype, float maxrate, boolean banned, int hours)
/*    */   {
/* 26 */     this.sender = sender;
/* 27 */     this.hours = hours;
/* 28 */     this.world = world;
/* 29 */     this.oreid = oreid;
/* 30 */     this.bantype = bantype;
/* 31 */     this.maxrate = maxrate;
/* 32 */     this.banned = banned;
/*    */   }
/*    */   
/*    */   public void run() {}
/*    */ }

 */