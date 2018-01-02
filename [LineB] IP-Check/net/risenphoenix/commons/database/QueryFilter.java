/*    */ package net.risenphoenix.commons.database;
/*    */ 
/*    */ import java.sql.ResultSet;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class QueryFilter
/*    */ {
/* 37 */   private Object[] data = null;
/*    */   
/*    */   public QueryFilter() {}
/*    */   
/*    */   public QueryFilter(Object[] data) {
/* 42 */     this.data = data;
/*    */   }
/*    */   
/*    */   public Object onExecute(ResultSet resultSet) {
/* 46 */     return null;
/*    */   }
/*    */   
/*    */   public Object[] getData() {
/* 50 */     return this.data;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\database\QueryFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */