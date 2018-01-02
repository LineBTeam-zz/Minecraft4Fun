/*    */ package me.minebuilders.clearlag.removetype;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.config.ConfigValueType;
/*    */ import me.minebuilders.clearlag.entities.EntityTable;
/*    */ import me.minebuilders.clearlag.modules.ClearModule;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class AreaClear extends ClearModule
/*    */ {
/*    */   @ConfigValue(valueType=ConfigValueType.ENTITY_TYPE_TABLE, path="area-filter")
/*    */   private EntityTable types;
/*    */   
/*    */   public boolean isRemovable(Entity e)
/*    */   {
/* 17 */     return (!(e instanceof Player)) && (!this.types.containsEntity(e));
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\removetype\AreaClear.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */