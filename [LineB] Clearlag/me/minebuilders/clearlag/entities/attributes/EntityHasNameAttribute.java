/*    */ package me.minebuilders.clearlag.entities.attributes;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityHasNameAttribute
/*    */   extends EntityAttribute<Entity>
/*    */ {
/*    */   public boolean containsData(Entity entity)
/*    */   {
/* 12 */     return (!this.reversed ? 1 : 0) == (entity.getCustomName() != null ? 1 : 0);
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\entities\attributes\EntityHasNameAttribute.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */