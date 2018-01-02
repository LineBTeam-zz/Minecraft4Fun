/*    */ package me.au2001.PerfectBackup;
/*    */ 
/*    */ import java.util.Calendar;
/*    */ 
/*    */ public abstract class CronRunnable extends java.util.TimerTask { int second;
/*    */   int minute;
/*    */   int hour;
/*    */   int day;
/*    */   int month;
/*    */   int week;
/*    */   
/* 12 */   public CronRunnable(String expression) { String[] parts = expression.split(" ");
/* 13 */     if ((parts.length == 6) && (expression.matches("([0-9]{1,2} |\\* ){5}([0-9]{1,2}|\\*)"))) {
/* 14 */       this.second = (parts[0].equals("*") ? -1 : Integer.parseInt(parts[0]));
/* 15 */       this.minute = (parts[1].equals("*") ? -1 : Integer.parseInt(parts[1]));
/* 16 */       this.hour = (parts[2].equals("*") ? -1 : Integer.parseInt(parts[2]));
/* 17 */       this.day = (parts[3].equals("*") ? -1 : Integer.parseInt(parts[3]));
/* 18 */       this.month = (parts[4].equals("*") ? -1 : Integer.parseInt(parts[4]));
/* 19 */       this.week = (parts[5].equals("*") ? -1 : Integer.parseInt(parts[5]));
/*    */       
/* 21 */       if ((this.second != -1) && ((this.second < 0) || (this.second > 59)))
/* 22 */         throw new IllegalArgumentException("Error in cron task format \"" + expression + "\": Second (1) must be between 0 and 59");
/* 23 */       if ((this.minute != -1) && ((this.minute < 0) || (this.minute > 59)))
/* 24 */         throw new IllegalArgumentException("Error in cron task format \"" + expression + "\": Minute (2) must be between 0 and 59");
/* 25 */       if ((this.hour != -1) && ((this.hour < 0) || (this.hour > 23)))
/* 26 */         throw new IllegalArgumentException("Error in cron task format \"" + expression + "\": Hour of the Day (3) must be between 0 and 23");
/* 27 */       if ((this.day != -1) && ((this.day < 1) || (this.day > 31)))
/* 28 */         throw new IllegalArgumentException("Error in cron task format \"" + expression + "\": Day of the Month (4) must be between 1 and 31");
/* 29 */       if ((this.month != -1) && ((this.month < 1) || (this.month > 12)))
/* 30 */         throw new IllegalArgumentException("Error in cron task format \"" + expression + "\": Month (5) must be between 1 and 12");
/* 31 */       if ((this.week != -1) && ((this.week < 1) || (this.week > 7)))
/* 32 */         throw new IllegalArgumentException("Error in cron task format \"" + expression + "\": Day of the Week (6) must be between 1 and 7");
/*    */     } else {
/* 34 */       throw new IllegalArgumentException("Invalid cron task format \"" + expression + "\"");
/*    */     }
/* 36 */     new java.util.Timer().scheduleAtFixedRate(this, 0L, 1000L);
/*    */   }
/*    */   
/*    */   public void run() {
/* 40 */     Calendar cal = Calendar.getInstance();
/* 41 */     if ((this.second != -1) && (this.second != cal.get(13))) return;
/* 42 */     if ((this.minute != -1) && (this.minute != cal.get(12))) return;
/* 43 */     if ((this.hour != -1) && (this.hour != cal.get(11))) return;
/* 44 */     if ((this.day != -1) && (this.day != cal.get(5))) return;
/* 45 */     if ((this.month != -1) && (this.month != cal.get(2))) return;
/* 46 */     if ((this.week != -1) && (this.week != cal.get(7))) return;
/* 47 */     exec();
/*    */   }
/*    */   
/*    */   public abstract void exec();
/*    */ }
