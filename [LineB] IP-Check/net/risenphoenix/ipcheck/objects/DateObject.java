/*    */ package net.risenphoenix.ipcheck.objects;
/*    */ 
/*    */ import java.sql.Timestamp;
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
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
/*    */ public class DateObject
/*    */ {
/*    */   private String player;
/*    */   private Timestamp date;
/*    */   
/*    */   public DateObject(String player, String datestamp)
/*    */   {
/* 43 */     this.player = player;
/* 44 */     this.date = parseTimestamp(datestamp);
/*    */   }
/*    */   
/*    */   public Timestamp getDate() {
/* 48 */     return this.date;
/*    */   }
/*    */   
/*    */   public String getPlayer() {
/* 52 */     return this.player;
/*    */   }
/*    */   
/*    */   private Timestamp parseTimestamp(String stamp) {
/* 56 */     SimpleDateFormat dF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
/* 57 */     Date parsedDate = null;
/*    */     try
/*    */     {
/* 60 */       parsedDate = dF.parse(stamp);
/*    */     } catch (ParseException e) {
/* 62 */       e.printStackTrace();
/*    */     }
/*    */     
/* 65 */     return new Timestamp(parsedDate.getTime());
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\objects\DateObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */