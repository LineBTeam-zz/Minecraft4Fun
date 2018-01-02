/*    */ package me.minebuilders.clearlag.removetype;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.config.ConfigValueType;
/*    */ import me.minebuilders.clearlag.entities.EntityTable;
/*    */ import me.minebuilders.clearlag.modules.ClearModule;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.HumanEntity;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ 
/*    */ @ConfigPath(path="kill-mobs")
/*    */ public class KillMobsClear extends ClearModule
/*    */ {
/*    */   @ConfigValue(valueType=ConfigValueType.ENTITY_TYPE_TABLE)
/*    */   private EntityTable mobFilter;
/*    */   
/*    */   public boolean isRemovable(Entity e)
/*    */   {
/* 20 */     return ((e instanceof LivingEntity)) && (!(e instanceof HumanEntity)) && (!this.mobFilter.containsEntity(e));
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\removetype\KillMobsClear.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */