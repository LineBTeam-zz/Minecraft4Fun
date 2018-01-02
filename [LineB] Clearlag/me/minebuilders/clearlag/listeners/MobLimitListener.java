/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import me.minebuilders.clearlag.Clearlag;
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.modules.EventModule;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Animals;
/*    */ import org.bukkit.entity.Creature;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Villager;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ @ConfigPath(path="spawn-limiter")
/*    */ public class MobLimitListener
/*    */   extends EventModule implements Runnable
/*    */ {
/*    */   @ConfigValue
/*    */   private int animals;
/*    */   @ConfigValue
/*    */   private int mobs;
/*    */   @ConfigValue
/*    */   private int interval;
/* 29 */   private int sched = -1;
/*    */   
/* 31 */   public boolean canAnimalspawn = true;
/*    */   
/* 33 */   public boolean canMobspawn = true;
/*    */   
/*    */   public void run()
/*    */   {
/* 37 */     int animals = 0;
/* 38 */     int mobs = 0;
/*    */     
/* 40 */     for (World world : Bukkit.getWorlds()) {
/* 41 */       for (Entity e : world.getEntities()) {
/* 42 */         if (((e instanceof Animals)) || ((e instanceof Villager))) animals++;
/* 43 */         if ((e instanceof Creature)) { mobs++;
/*    */         }
/*    */       }
/*    */     }
/* 47 */     if (animals >= this.animals)
/* 48 */       this.canAnimalspawn = false; else {
/* 49 */       this.canAnimalspawn = true;
/*    */     }
/* 51 */     if (mobs >= this.mobs)
/* 52 */       this.canMobspawn = false; else
/* 53 */       this.canMobspawn = true;
/*    */   }
/*    */   
/*    */   public void setEnabled() {
/* 57 */     super.setEnabled();
/*    */     
/* 59 */     this.sched = Bukkit.getScheduler().scheduleSyncRepeatingTask(Clearlag.getInstance(), this, this.interval * 20L, this.interval * 20L);
/*    */   }
/*    */   
/*    */   public void setDisabled()
/*    */   {
/* 64 */     super.setDisabled();
/*    */     
/* 66 */     if (this.sched != -1) Bukkit.getServer().getScheduler().cancelTask(this.sched);
/*    */   }
/*    */   
/*    */   @EventHandler(priority=EventPriority.LOWEST, ignoreCancelled=true)
/*    */   public void onMobSpawn(CreatureSpawnEvent e) {
/* 71 */     Entity en = e.getEntity();
/* 72 */     if ((!this.canAnimalspawn) && ((en instanceof Animals))) {
/* 73 */       e.setCancelled(true);
/* 74 */     } else if ((!this.canMobspawn) && ((en instanceof Creature))) {
/* 75 */       e.setCancelled(true);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\MobLimitListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */