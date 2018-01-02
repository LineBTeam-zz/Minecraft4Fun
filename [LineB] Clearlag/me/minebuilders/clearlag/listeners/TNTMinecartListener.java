/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.modules.EventModule;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.minecart.ExplosiveMinecart;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.vehicle.VehicleCreateEvent;
/*    */ 
/*    */ @ConfigPath(path="tnt-minecart")
/*    */ public class TNTMinecartListener extends EventModule
/*    */ {
/*    */   @ConfigValue
/*    */   private int radius;
/*    */   @ConfigValue
/*    */   private int max;
/*    */   
/*    */   @EventHandler
/*    */   public void onVehicleCreate(VehicleCreateEvent event)
/*    */   {
/* 22 */     Entity mine = event.getVehicle();
/*    */     
/* 24 */     if ((mine instanceof ExplosiveMinecart)) {
/* 25 */       int max = 0;
/*    */       
/* 27 */       for (Entity tnt : mine.getNearbyEntities(this.radius, this.radius, this.radius)) {
/* 28 */         if ((tnt instanceof ExplosiveMinecart)) {
/* 29 */           max++;
/*    */         }
/*    */       }
/*    */       
/* 33 */       if (max >= this.max) {
/* 34 */         mine.remove();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\TNTMinecartListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */