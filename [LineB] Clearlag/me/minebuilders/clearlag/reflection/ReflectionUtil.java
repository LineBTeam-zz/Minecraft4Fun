/*    */ package me.minebuilders.clearlag.reflection;
/*    */ 
/*    */ import java.lang.reflect.Method;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ReflectionUtil
/*    */ {
/*    */   public static Method getMethodByName(Class<?> clazz, String methodName)
/*    */   {
/* 11 */     for (Method meth : clazz.getDeclaredMethods()) {
/* 12 */       if (meth.getName().equals(methodName)) {
/* 13 */         return meth;
/*    */       }
/*    */     }
/* 16 */     return null;
/*    */   }
/*    */   
/*    */   public static Object castPrimitedValues(Class<?> field, Object value) {
/* 20 */     if ((value instanceof Number)) {
/* 21 */       if (field == Integer.TYPE) {
/* 22 */         return Integer.valueOf(((Number)value).intValue());
/*    */       }
/* 24 */       if (field == Long.TYPE) {
/* 25 */         return Long.valueOf(((Number)value).longValue());
/*    */       }
/* 27 */       if (field == Float.TYPE) {
/* 28 */         return Float.valueOf(((Number)value).floatValue());
/*    */       }
/* 30 */       if (field == Double.TYPE) {
/* 31 */         return Double.valueOf(((Number)value).doubleValue());
/*    */       }
/*    */     }
/* 34 */     return value;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\reflection\ReflectionUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */