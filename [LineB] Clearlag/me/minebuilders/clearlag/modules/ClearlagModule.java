/*    */ package me.minebuilders.clearlag.modules;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ClearlagModule
/*    */   implements Module
/*    */ {
/*  8 */   private boolean enabled = false;
/*    */   
/*    */   public void setEnabled() {
/* 11 */     this.enabled = true;
/*    */   }
/*    */   
/*    */   public void setDisabled() {
/* 15 */     this.enabled = false;
/*    */   }
/*    */   
/*    */   public boolean isEnabled() {
/* 19 */     return this.enabled;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\modules\ClearlagModule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */