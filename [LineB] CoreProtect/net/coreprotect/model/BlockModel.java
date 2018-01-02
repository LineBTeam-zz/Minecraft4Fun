/*    */ package net.coreprotect.model;
/*    */ 
/*    */ public class BlockModel
/*    */ {
/*    */   private int type;
/*    */   private int data;
/*    */   
/*    */   public BlockModel(int type, int data) {
/*  9 */     this.type = type;
/* 10 */     this.data = data;
/*    */   }
/*    */   
/*    */   public int getData() {
/* 14 */     return this.data;
/*    */   }
/*    */   
/*    */   public int getType() {
/* 18 */     return this.type;
/*    */   }
/*    */   
/*    */   public void setData(int data) {
/* 22 */     this.data = data;
/*    */   }
/*    */   
/*    */   public void setType(int type) {
/* 26 */     this.type = type;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\model\BlockModel.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */