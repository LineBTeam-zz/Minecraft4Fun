/*    */ package net.risenphoenix.ipcheck.util;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Calendar;
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
/*    */ public class DateStamp
/*    */ {
/*    */   public String getErrorStamp()
/*    */   {
/* 40 */     DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
/*    */     
/*    */ 
/* 43 */     Calendar cal = Calendar.getInstance();
/* 44 */     return dateFormat.format(cal.getTime());
/*    */   }
/*    */   
/*    */   public String getCustomStamp(String regex) {
/* 48 */     DateFormat dateFormat = new SimpleDateFormat(regex);
/*    */     
/*    */ 
/* 51 */     Calendar cal = Calendar.getInstance();
/* 52 */     return dateFormat.format(cal.getTime());
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\util\DateStamp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */