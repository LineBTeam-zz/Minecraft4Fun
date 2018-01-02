/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.modules.EventModule;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
/*    */ 
/*    */ @ConfigPath(path="mob-breeding-limiter")
/*    */ public class EntityBreedListener extends EventModule
/*    */ {
/*    */   @ConfigValue
/*    */   private int maxMobs;
/*    */   @ConfigValue
/*    */   private int checkRadius;
/*    */   
/*    */   @EventHandler
/*    */   public void onCreatureSpawn(CreatureSpawnEvent event)
/*    */   {
/*    */     int i;
/* 23 */     if ((event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.EGG) || (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.BREEDING)) {
/* 24 */       Entity e = event.getEntity();
/*    */       
/* 26 */       i = 0;
/*    */       
/* 28 */       for (Entity ee : e.getNearbyEntities(this.checkRadius, this.checkRadius, this.checkRadius))
/*    */       {
/* 30 */         if ((ee instanceof org.bukkit.entity.Ageable)) {
/* 31 */           i++;
/*    */           
/* 33 */           if (i >= this.maxMobs) {
/* 34 */             event.setCancelled(true);
/* 35 */             return;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\EntityBreedListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */