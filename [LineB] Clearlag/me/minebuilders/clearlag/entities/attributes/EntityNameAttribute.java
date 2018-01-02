/*    */ package me.minebuilders.clearlag.entities.attributes;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ 
/*    */ public class EntityNameAttribute
/*    */   extends EntityAttribute<Entity>
/*    */ {
/*    */   private String name;
/*    */   
/*    */   public EntityNameAttribute(String name)
/*    */   {
/* 13 */     this.name = name;
/*    */   }
/*    */   
/*    */   public boolean containsData(Entity entity)
/*    */   {
/* 18 */     return (!this.reversed) == this.name.equals(entity.getCustomName());
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\entities\attributes\EntityNameAttribute.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */