/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.modules.EventModule;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.block.BlockDispenseEvent;
/*    */ 
/*    */ @ConfigPath(path="dispenser-reducer")
/*    */ public class DispenceLimitEvent extends EventModule
/*    */ {
/*    */   private long lastspread;
/*    */   @ConfigValue
/*    */   private long time;
/*    */   
/*    */   @EventHandler
/*    */   public void onBlockDispenseEvent(BlockDispenseEvent e)
/*    */   {
/* 19 */     if (System.currentTimeMillis() > this.lastspread) {
/* 20 */       this.lastspread = (System.currentTimeMillis() + this.time);
/*    */     } else {
/* 22 */       e.setCancelled(true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\DispenceLimitEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */