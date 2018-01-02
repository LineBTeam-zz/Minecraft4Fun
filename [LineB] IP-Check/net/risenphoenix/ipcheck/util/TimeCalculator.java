/*    */ package net.risenphoenix.ipcheck.util;
/*    */ 
/*    */ import java.text.ParseException;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Date;
/*    */ import net.risenphoenix.ipcheck.IPCheck;
/*    */ import net.risenphoenix.ipcheck.database.DatabaseController;
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
/*    */ public class TimeCalculator
/*    */ {
/*    */   private String arg;
/*    */   private DatabaseController dbc;
/*    */   
/*    */   public TimeCalculator(String player)
/*    */   {
/* 46 */     this.arg = player;
/* 47 */     this.dbc = IPCheck.getInstance().getDatabaseController();
/*    */   }
/*    */   
/*    */   public String getLastTime()
/*    */   {
/* 52 */     SimpleDateFormat sParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     try
/*    */     {
/* 60 */       Date currentTime = sParse.parse(this.dbc.getCurrentTimeStamp());
/* 61 */       lastTime = sParse.parse(this.dbc.getLastTime(this.arg));
/*    */     } catch (ParseException e) {
/*    */       Date lastTime;
/* 64 */       e.printStackTrace();
/* 65 */       return "ERROR";
/*    */     }
/*    */     Date lastTime;
/*    */     Date currentTime;
/* 69 */     long timeOffset = (currentTime.getTime() - lastTime.getTime()) / 1000L;
/*    */     
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 75 */     int days = (int)timeOffset / 86400;
/* 76 */     timeOffset -= days * 86400;
/*    */     
/* 78 */     int hours = (int)timeOffset / 3600;
/* 79 */     timeOffset -= hours * 3600;
/*    */     
/* 81 */     int minutes = (int)timeOffset / 60;
/* 82 */     timeOffset -= minutes * 60;
/*    */     
/* 84 */     int seconds = (int)timeOffset;
/*    */     
/*    */ 
/*    */ 
/* 88 */     if (seconds == 0) { seconds++;
/*    */     }
/*    */     
/* 91 */     String d = days == 1 ? " Day, " : " Days, ";
/* 92 */     String h = hours == 1 ? " Hour, " : " Hours, ";
/* 93 */     String m = minutes == 1 ? " Minute, " : " Minutes, ";
/* 94 */     String s = seconds == 1 ? " Second ago." : " Seconds ago.";
/*    */     
/*    */ 
/* 97 */     return (days > 0 ? days + d : "") + (hours > 0 ? hours + h : "") + (minutes > 0 ? minutes + m : "") + (seconds > 0 ? seconds + s : "");
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\util\TimeCalculator.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */