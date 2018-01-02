/*    */ package me.minebuilders.clearlag.removetype;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.config.ConfigValueType;
/*    */ import me.minebuilders.clearlag.entities.EntityTable;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ 
/*    */ @ConfigPath(path="auto-removal")
/*    */ public class AutoClear extends LimitClear
/*    */ {
/*    */   @ConfigValue(valueType=ConfigValueType.ENTITY_TYPE_TABLE)
/*    */   private EntityTable removeEntities;
/*    */   
/*    */   public boolean isRemovable(Entity e)
/*    */   {
/* 18 */     if ((e instanceof LivingEntity)) return this.removeEntities.containsEntity(e);
/* 19 */     return super.isRemovable(e);
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\removetype\AutoClear.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */