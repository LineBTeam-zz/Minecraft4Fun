/*    */ package me.minebuilders.clearlag.entities.attributes;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ 
/*    */ public class EntityLifeLimitAttribute
/*    */   extends EntityAttribute<Entity>
/*    */ {
/*    */   private int lifeLimit;
/*    */   
/*    */   public EntityLifeLimitAttribute(int lifeLimit)
/*    */   {
/* 13 */     this.lifeLimit = lifeLimit;
/*    */   }
/*    */   
/*    */   public boolean containsData(Entity entity)
/*    */   {
/* 18 */     return (!this.reversed ? 1 : 0) == (entity.getTicksLived() >= this.lifeLimit ? 1 : 0);
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\entities\attributes\EntityLifeLimitAttribute.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */