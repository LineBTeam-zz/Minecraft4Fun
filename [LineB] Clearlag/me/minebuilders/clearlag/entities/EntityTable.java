/*    */ package me.minebuilders.clearlag.entities;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.minebuilders.clearlag.entities.attributes.EntityAttribute;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityTable
/*    */ {
/* 14 */   private ArrayList<ArrayList<EntityAttribute<Entity>>> entities = new ArrayList(EntityType.values().length);
/*    */   
/* 16 */   private static final ArrayList<EntityAttribute<Entity>> FAKE_ENTRY = new ArrayList(0);
/*    */   
/*    */   public EntityTable() {
/* 19 */     for (int i = 0; i < EntityType.values().length; i++)
/* 20 */       this.entities.add(null);
/*    */   }
/*    */   
/*    */   public boolean containsEntity(Entity entity) {
/* 24 */     ArrayList<EntityAttribute<Entity>> con = (ArrayList)this.entities.get(entity.getType().ordinal());
/*    */     
/* 26 */     if (con != null) {
/* 27 */       for (EntityAttribute<Entity> e : con)
/* 28 */         if (!e.containsData(entity))
/* 29 */           return false;
/* 30 */       return true;
/*    */     }
/* 32 */     return false;
/*    */   }
/*    */   
/*    */   public void addEntityAttribute(EntityType type, EntityAttribute<Entity> entity) {
/* 36 */     ArrayList<EntityAttribute<Entity>> con = (ArrayList)this.entities.get(type.ordinal());
/*    */     
/* 38 */     if (con == null) {
/* 39 */       con = new ArrayList(1);
/* 40 */       this.entities.set(type.ordinal(), con);
/*    */     }
/*    */     
/* 43 */     con.add(entity);
/*    */   }
/*    */   
/*    */   public void setEntityAttributes(EntityType type, ArrayList<EntityAttribute<Entity>> entityAttributes) {
/* 47 */     this.entities.set(type.ordinal(), entityAttributes);
/*    */   }
/*    */   
/*    */   public void setEntity(EntityType type) {
/* 51 */     this.entities.set(type.ordinal(), FAKE_ENTRY);
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\entities\EntityTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */