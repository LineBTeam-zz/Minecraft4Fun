/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ import org.bukkit.event.world.ChunkUnloadEvent;
/*    */ 
/*    */ 
/*    */ @me.minebuilders.clearlag.annotations.ConfigPath(path="mobspawner")
/*    */ public class MobSpawerListener
/*    */   extends me.minebuilders.clearlag.modules.EventModule
/*    */ {
/*    */   @ConfigValue
/*    */   private int maxSpawn;
/*    */   @ConfigValue
/*    */   private boolean removeMobsOnChunkUnload;
/*    */   private HashMap<ChunkKey, List<Entity>> map;
/*    */   
/* 26 */   public MobSpawerListener() { this.map = new HashMap(); }
/*    */   
/*    */   private boolean isOverLimit(List<Entity> en) {
/* 29 */     Iterator<Entity> ee = en.iterator();
/* 30 */     int amount = 0;
/*    */     
/* 32 */     while (ee.hasNext()) {
/* 33 */       if (((Entity)ee.next()).isDead()) {
/* 34 */         ee.remove();
/*    */       } else {
/* 36 */         amount++;
/*    */       }
/*    */     }
/* 39 */     return amount >= this.maxSpawn;
/*    */   }
/*    */   
/*    */   @EventHandler(priority=org.bukkit.event.EventPriority.MONITOR)
/*    */   public void onCreatureSpawn(CreatureSpawnEvent event) {
/* 44 */     LivingEntity e = event.getEntity();
/*    */     
/* 46 */     if (event.getSpawnReason() == org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.SPAWNER)
/*    */     {
/* 48 */       ChunkKey key = new ChunkKey(event.getLocation().getChunk());
/*    */       
/* 50 */       List<Entity> entities = (List)this.map.get(key);
/*    */       
/* 52 */       if (entities == null) {
/* 53 */         entities = new java.util.ArrayList();
/* 54 */         this.map.put(key, entities);
/*    */       }
/*    */       
/* 57 */       if (this.removeMobsOnChunkUnload) {
/* 58 */         e.setRemoveWhenFarAway(true);
/*    */       }
/* 60 */       if (!isOverLimit(entities)) {
/* 61 */         entities.add(e);
/*    */       } else {
/* 63 */         event.setCancelled(true);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler(ignoreCancelled=false)
/*    */   public void onChunkUnload(ChunkUnloadEvent event) {
/* 70 */     this.map.remove(new ChunkKey(event.getChunk()));
/*    */   }
/*    */   
/*    */   private class ChunkKey {
/*    */     private int x;
/*    */     private int z;
/*    */     private UUID worldUuid;
/*    */     private int hashCode;
/*    */     
/*    */     ChunkKey(Chunk c) {
/* 80 */       this.x = c.getX();
/* 81 */       this.z = c.getZ();
/* 82 */       this.worldUuid = c.getWorld().getUID();
/* 83 */       this.hashCode = (this.x * 33 + this.z * 67 + c.getWorld().hashCode());
/*    */     }
/*    */     
/*    */     public int hashCode()
/*    */     {
/* 88 */       return this.x * 33 + this.z * 67 + this.worldUuid.hashCode() / 4;
/*    */     }
/*    */     
/*    */     public boolean equals(Object obj)
/*    */     {
/* 93 */       if (obj == this) {
/* 94 */         return true;
/*    */       }
/* 96 */       ChunkKey ck = (ChunkKey)obj;
/*    */       
/* 98 */       return (ck.x == this.x) && (ck.z == this.z) && (ck.worldUuid == this.worldUuid);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\MobSpawerListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */