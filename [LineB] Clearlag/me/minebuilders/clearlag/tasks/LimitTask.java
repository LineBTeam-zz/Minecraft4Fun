/*    */ package me.minebuilders.clearlag.tasks;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.annotations.ConfigModule;
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import me.minebuilders.clearlag.modules.TaskModule;
/*    */ import me.minebuilders.clearlag.removetype.LimitClear;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ @ConfigPath(path="limit")
/*    */ public class LimitTask extends TaskModule
/*    */ {
/*    */   @ConfigValue
/*    */   private int max;
/*    */   @ConfigModule
/* 23 */   private LimitClear limitClear = new LimitClear();
/*    */   
/*    */   public void run()
/*    */   {
/* 27 */     List<Entity> ents = new ArrayList();
/*    */     
/* 29 */     for (World w : Bukkit.getWorlds()) {
/* 30 */       ents.addAll(this.limitClear.getRemovables(w.getEntities(), w));
/*    */     }
/*    */     
/* 33 */     if (ents.size() <= this.max) { return;
/*    */     }
/* 35 */     for (Entity entity : ents) {
/* 36 */       entity.remove();
/*    */     }
/*    */     
/* 39 */     if (Config.getConfig().getBoolean("limit.broadcast-removal")) {
/* 40 */       Util.broadcast(Config.getConfig().getString("limit.broadcast-message").replace("+RemoveAmount", "" + ents.size()));
/*    */     }
/*    */     
/* 43 */     ents.clear();
/*    */   }
/*    */   
/*    */   public int getInterval()
/*    */   {
/* 48 */     return Config.getConfig().getInt("limit.check-interval") * 20;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\tasks\LimitTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */