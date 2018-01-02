/*    */ package me.minebuilders.clearlag.tasks;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ import org.bukkit.entity.Arrow;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ 
/*    */ @ConfigPath(path="live-time")
/*    */ public class LiveTask extends me.minebuilders.clearlag.modules.TaskModule
/*    */ {
/*    */   @ConfigValue
/*    */   private int itemlivetime;
/*    */   @ConfigValue
/*    */   private int moblivetime;
/*    */   @ConfigValue
/*    */   private int arrowkilltime;
/*    */   @ConfigValue
/*    */   private boolean itemtimer;
/*    */   @ConfigValue
/*    */   private boolean mobtimer;
/*    */   @ConfigValue
/*    */   private boolean arrowtimer;
/*    */   
/*    */   public void run()
/*    */   {
/* 33 */     for (World w : ) {
/* 34 */       for (Entity e : w.getEntities()) {
/* 35 */         if ((this.mobtimer) && ((e instanceof LivingEntity)) && (!(e instanceof HumanEntity))) {
/* 36 */           if (e.getTicksLived() > this.moblivetime) e.remove();
/* 37 */         } else if ((this.itemtimer) && ((e instanceof Item))) {
/* 38 */           if (e.getTicksLived() > this.itemlivetime) e.remove();
/* 39 */         } else if ((this.arrowtimer) && ((e instanceof Arrow)) && 
/* 40 */           (e.getTicksLived() > this.arrowkilltime)) { e.remove();
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public int getInterval()
/*    */   {
/* 48 */     return Config.getConfig().getInt("live-time.interval") * 20;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\tasks\LiveTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */