/*    */ package me.minebuilders.clearlag.managers;
/*    */ 
/*    */ import java.util.List;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.events.EntityRemoveEvent;
/*    */ import me.minebuilders.clearlag.modules.ClearModule;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class EntityManager extends me.minebuilders.clearlag.modules.ClearlagModule
/*    */ {
/*    */   @ConfigValue(path="settings.enable-api")
/*    */   private boolean enabled;
/*    */   
/*    */   public int removeEntities(ClearModule mod)
/*    */   {
/* 20 */     int removed = 0;
/*    */     
/* 22 */     for (World w : Bukkit.getWorlds()) {
/* 23 */       removed += removeEntities(mod.getRemovables(w.getEntities(), w), w);
/*    */     }
/*    */     
/* 26 */     return removed;
/*    */   }
/*    */   
/*    */   public int removeEntities(List<Entity> removables, World w) {
/* 30 */     EntityRemoveEvent et = new EntityRemoveEvent(removables, w);
/*    */     
/* 32 */     if (this.enabled) {
/* 33 */       Bukkit.getPluginManager().callEvent(et);
/*    */     }
/* 35 */     return Util.removeEntitys(et.getEntityList());
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\managers\EntityManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */