/*    */ package me.minebuilders.clearlag.reflection;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.minebuilders.clearlag.annotations.AutoWire;
/*    */ import me.minebuilders.clearlag.modules.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AutoWirer
/*    */ {
/* 15 */   private List<Object> wireables = new ArrayList();
/*    */   
/*    */   public void addWireable(Object ob) {
/* 18 */     this.wireables.add(ob);
/*    */   }
/*    */   
/*    */   public void addWireables(Object... obs) {
/* 22 */     for (Object obj : obs)
/* 23 */       addWireable(obj);
/*    */   }
/*    */   
/*    */   public void wireObject(Object object) throws IllegalAccessException {
/* 27 */     Class<?> clazz = object.getClass();
/* 28 */     while ((clazz != null) && (clazz != Object.class) && (clazz != Module.class)) {
/*    */       Field field;
/* 30 */       for (field : clazz.getDeclaredFields())
/*    */       {
/* 32 */         if (field.isAnnotationPresent(AutoWire.class)) {
/* 33 */           field.setAccessible(true);
/*    */           
/* 35 */           if (field.get(object) == null)
/*    */           {
/* 37 */             for (Object wire : this.wireables)
/*    */             {
/* 39 */               if (field.getType().isAssignableFrom(wire.getClass()))
/*    */               {
/* 41 */                 field.set(object, wire);
/*    */                 
/* 43 */                 break;
/*    */               }
/*    */             }
/*    */           }
/*    */         }
/*    */       }
/* 49 */       clazz = clazz.getSuperclass();
/*    */     }
/*    */   }
/*    */   
/*    */   public List<Object> getWires() {
/* 54 */     return this.wireables;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\reflection\AutoWirer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */