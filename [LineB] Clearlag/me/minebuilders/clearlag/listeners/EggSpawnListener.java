/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.modules.EventModule;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
/*    */ 
/*    */ @ConfigPath(path="mobegg-limiter")
/*    */ public class EggSpawnListener extends EventModule
/*    */ {
/*    */   @ConfigValue
/*    */   private int maxMobs;
/*    */   @ConfigValue
/*    */   private int checkRadius;
/*    */   
/*    */   @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=true)
/*    */   public void onCreatureSpawn(CreatureSpawnEvent event)
/*    */   {
/* 23 */     if (event.getSpawnReason() == CreatureSpawnEvent.SpawnReason.SPAWNER_EGG) {
/* 24 */       Entity e = event.getEntity();
/*    */       
/* 26 */       if (e.getNearbyEntities(this.checkRadius, this.checkRadius, this.checkRadius).size() > this.maxMobs) {
/* 27 */         event.setCancelled(true);
/* 28 */         return;
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\EggSpawnListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */