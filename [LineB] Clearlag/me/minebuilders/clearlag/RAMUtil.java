/*    */ package me.minebuilders.clearlag;
/*    */ 
/*    */ public class RAMUtil
/*    */ {
/*    */   private static final long MB = 1048576L;
/*    */   
/*    */   public static long getTotalMemory() {
/*  8 */     return Runtime.getRuntime().totalMemory() / 1048576L;
/*    */   }
/*    */   
/*    */   public static long getFreeMemory() {
/* 12 */     return Runtime.getRuntime().freeMemory() / 1048576L;
/*    */   }
/*    */   
/*    */   public static long getUsedMemory() {
/* 16 */     return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1048576L;
/*    */   }
/*    */   
/*    */   public static long getMaxMemory() {
/* 20 */     return Runtime.getRuntime().maxMemory() / 1048576L;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\RAMUtil.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */