/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.modules.EventModule;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.block.BlockIgniteEvent;
/*    */ import org.bukkit.event.block.BlockIgniteEvent.IgniteCause;
/*    */ 
/*    */ @ConfigPath(path="firespread-reducer")
/*    */ public class FireSpreadListener extends EventModule
/*    */ {
/* 13 */   private long lastspread = System.currentTimeMillis();
/*    */   @ConfigValue
/*    */   private int time;
/*    */   
/*    */   @EventHandler
/*    */   public void fireSpread(BlockIgniteEvent e)
/*    */   {
/* 20 */     if (e.getCause() == BlockIgniteEvent.IgniteCause.SPREAD)
/*    */     {
/* 22 */       if (System.currentTimeMillis() > this.lastspread) {
/* 23 */         this.lastspread = (System.currentTimeMillis() + this.time);
/*    */       } else {
/* 25 */         e.setCancelled(true);
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\FireSpreadListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */