/*    */ package me.minebuilders.clearlag.modules;
/*    */ 
/*    */ import java.util.LinkedList;
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Entity;
/*    */ 
/*    */ public abstract class ClearModule extends ClearlagModule
/*    */ {
/*    */   public List<Entity> getRemovables(List<Entity> list, World w)
/*    */   {
/* 13 */     List<Entity> en = new LinkedList();
/*    */     
/* 15 */     if (isWorldEnabled(w))
/*    */     {
/* 17 */       for (Entity ent : list) {
/* 18 */         if (isRemovable(ent)) {
/* 19 */           en.add(ent);
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 25 */     return en;
/*    */   }
/*    */   
/*    */   public List<Entity> getAllRemovables()
/*    */   {
/* 30 */     List<Entity> en = new LinkedList();
/*    */     
/* 32 */     for (World w : Bukkit.getWorlds())
/*    */     {
/* 34 */       if (isWorldEnabled(w))
/*    */       {
/* 36 */         for (Entity ent : w.getEntities()) {
/* 37 */           if (isRemovable(ent)) {
/* 38 */             en.add(ent);
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 44 */     return en;
/*    */   }
/*    */   
/*    */   public abstract boolean isRemovable(Entity paramEntity);
/*    */   
/*    */   public boolean isWorldEnabled(World w) {
/* 50 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\modules\ClearModule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */