/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.config.ConfigValueType;
/*    */ import me.minebuilders.clearlag.entities.EntityTable;
/*    */ import me.minebuilders.clearlag.modules.EventModule;
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ import org.bukkit.event.world.ChunkLoadEvent;
/*    */ 
/*    */ @ConfigPath(path="chunk-entity-limiter")
/*    */ public class ChunkEntityLimiterListener extends EventModule
/*    */ {
/*    */   @ConfigValue
/*    */   private int limit;
/*    */   @ConfigValue(valueType=ConfigValueType.ENTITY_TYPE_TABLE)
/*    */   private EntityTable entities;
/*    */   
/*    */   @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
/*    */   public void onChunkLoad(ChunkLoadEvent event)
/*    */   {
/* 26 */     trim(event.getChunk().getEntities());
/*    */   }
/*    */   
/*    */   @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
/*    */   public void onSpawn(CreatureSpawnEvent event) {
/* 31 */     if (trim(event.getLocation().getChunk().getEntities())) {
/* 32 */       event.setCancelled(isRemovableEntity(event.getEntity()));
/*    */     }
/*    */   }
/*    */   
/*    */   private boolean trim(Entity[] es) {
/* 37 */     int count = 0;
/*    */     
/* 39 */     for (Entity e : es) {
/* 40 */       if (isRemovableEntity(e)) {
/* 41 */         count++;
/*    */         
/* 43 */         if (count > this.limit) {
/* 44 */           e.remove();
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 49 */     return count >= this.limit;
/*    */   }
/*    */   
/*    */   private boolean isRemovableEntity(Entity entity) {
/* 53 */     return this.entities.containsEntity(entity);
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\ChunkEntityLimiterListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */