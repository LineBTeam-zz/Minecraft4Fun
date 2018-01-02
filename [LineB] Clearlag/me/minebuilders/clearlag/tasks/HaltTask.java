/*     */ package me.minebuilders.clearlag.tasks;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import me.minebuilders.clearlag.modules.EventModule;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.block.BlockBurnEvent;
/*     */ import org.bukkit.event.block.BlockFadeEvent;
/*     */ import org.bukkit.event.block.BlockFormEvent;
/*     */ import org.bukkit.event.block.BlockFromToEvent;
/*     */ import org.bukkit.event.block.BlockIgniteEvent;
/*     */ import org.bukkit.event.block.BlockSpreadEvent;
/*     */ import org.bukkit.event.block.LeavesDecayEvent;
/*     */ import org.bukkit.event.entity.EntityExplodeEvent;
/*     */ 
/*     */ public class HaltTask extends EventModule
/*     */ {
/*     */   @me.minebuilders.clearlag.annotations.AutoWire
/*     */   private me.minebuilders.clearlag.managers.EntityManager entityManager;
/*     */   private HashMap<World, Integer[]> valuelist;
/*     */   
/*     */   public void setEnabled()
/*     */   {
/*  26 */     super.setEnabled();
/*     */     
/*  28 */     this.valuelist = new HashMap(org.bukkit.Bukkit.getWorlds().size());
/*     */     
/*  30 */     for (World w : org.bukkit.Bukkit.getWorlds())
/*     */     {
/*  32 */       this.entityManager.removeEntities(new me.minebuilders.clearlag.modules.ClearModule()
/*     */       {
/*     */         public boolean isRemovable(Entity e)
/*     */         {
/*  36 */           return ((e instanceof org.bukkit.entity.Item)) || ((e instanceof org.bukkit.entity.TNTPrimed)) || ((e instanceof org.bukkit.entity.ExperienceOrb)) || ((e instanceof org.bukkit.entity.FallingBlock)) || ((e instanceof org.bukkit.entity.Monster));
/*     */         }
/*     */         
/*     */         public boolean isWorldEnabled(World w)
/*     */         {
/*  41 */           return true;
/*     */         }
/*     */         
/*     */ 
/*  45 */       });
/*  46 */       Integer[] values = new Integer[6];
/*     */       
/*  48 */       values[0] = Integer.valueOf(w.getAmbientSpawnLimit());
/*  49 */       w.setAmbientSpawnLimit(0);
/*  50 */       values[1] = Integer.valueOf(w.getAnimalSpawnLimit());
/*  51 */       w.setAnimalSpawnLimit(0);
/*  52 */       values[2] = Integer.valueOf(w.getMonsterSpawnLimit());
/*  53 */       w.setMonsterSpawnLimit(0);
/*  54 */       values[3] = Integer.valueOf((int)w.getTicksPerAnimalSpawns());
/*  55 */       w.setTicksPerAnimalSpawns(0);
/*  56 */       values[4] = Integer.valueOf((int)w.getTicksPerMonsterSpawns());
/*  57 */       w.setTicksPerMonsterSpawns(0);
/*  58 */       values[5] = Integer.valueOf(w.getWaterAnimalSpawnLimit());
/*  59 */       w.setWaterAnimalSpawnLimit(0);
/*     */       
/*  61 */       this.valuelist.put(w, values);
/*     */     }
/*     */   }
/*     */   
/*     */   public void setDisabled()
/*     */   {
/*  67 */     super.setDisabled();
/*     */     
/*  69 */     for (World w : this.valuelist.keySet()) {
/*  70 */       Integer[] values = (Integer[])this.valuelist.get(w);
/*  71 */       w.setAmbientSpawnLimit(values[0].intValue());
/*  72 */       w.setAnimalSpawnLimit(values[1].intValue());
/*  73 */       w.setMonsterSpawnLimit(values[2].intValue());
/*  74 */       w.setTicksPerAnimalSpawns(values[3].intValue());
/*  75 */       w.setTicksPerMonsterSpawns(values[4].intValue());
/*  76 */       w.setWaterAnimalSpawnLimit(values[5].intValue());
/*     */     }
/*     */     
/*  79 */     this.valuelist = null;
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onFire(BlockIgniteEvent e) {
/*  84 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onFireBurn(BlockBurnEvent e) {
/*  89 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onExplode(EntityExplodeEvent e) {
/*  94 */     e.setCancelled(true);
/*  95 */     e.blockList().clear();
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onDecay(LeavesDecayEvent e) {
/* 100 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onForm(BlockFormEvent e) {
/* 105 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onSpread(BlockSpreadEvent e) {
/* 110 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onFade(BlockFadeEvent e) {
/* 115 */     e.setCancelled(true);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   public void onBlockFromTo(BlockFromToEvent e) {
/* 120 */     e.setCancelled(true);
/*     */   }
/*     */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\tasks\HaltTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */