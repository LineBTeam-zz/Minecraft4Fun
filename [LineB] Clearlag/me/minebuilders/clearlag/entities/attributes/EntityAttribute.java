/*    */ package me.minebuilders.clearlag.entities.attributes;
/*    */ 
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class EntityAttribute<T extends Entity>
/*    */ {
/* 10 */   protected boolean reversed = false;
/*    */   
/*    */   public boolean isReversed() {
/* 13 */     return this.reversed;
/*    */   }
/*    */   
/*    */   public void setReversed(boolean reversed) {
/* 17 */     this.reversed = reversed;
/*    */   }
/*    */   
/*    */   public abstract boolean containsData(T paramT);
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\entities\attributes\EntityAttribute.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */