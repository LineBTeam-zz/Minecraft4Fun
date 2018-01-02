/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.TNTPrimed;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.entity.EntityExplodeEvent;
/*    */ 
/*    */ @ConfigPath(path="tnt-reducer")
/*    */ public class TntReduceListener extends me.minebuilders.clearlag.modules.EventModule
/*    */ {
/*    */   @EventHandler
/*    */   public void onBoom(EntityExplodeEvent event)
/*    */   {
/* 15 */     Entity e = event.getEntity();
/* 16 */     if ((e instanceof TNTPrimed)) {
/* 17 */       for (Entity tnt : e.getNearbyEntities(4.0D, 4.0D, 4.0D)) {
/* 18 */         if ((tnt instanceof TNTPrimed)) {
/* 19 */           tnt.remove();
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\TntReduceListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */