/*     */ package net.risenphoenix.ipcheck.util;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Random;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class Messages
/*     */ {
/*  39 */   private static final ArrayList<String> MESSAGES_RANDOM_1 = new ArrayList()
/*     */   {
/*     */     private static final long serialVersionUID = 3159470443160594003L;
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  90 */   private static final ArrayList<String> MESSAGES_ERROR = new ArrayList()
/*     */   {
/*     */     private static final long serialVersionUID = -3593223368287276483L;
/*     */   };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String getRandomMessage()
/*     */   {
/* 140 */     int random = new Random().nextInt(MESSAGES_RANDOM_1.size());
/*     */     
/* 142 */     return (String)MESSAGES_RANDOM_1.get(random);
/*     */   }
/*     */   
/*     */   public static String getErrorMessage()
/*     */   {
/* 147 */     int random = new Random().nextInt(MESSAGES_ERROR.size());
/*     */     
/* 149 */     return (String)MESSAGES_ERROR.get(random);
/*     */   }
/*     */   
/*     */   public static String getSeasonalMessage(String date) {
/* 153 */     if (date.equals("01-01")) return "Happy New Year!";
/* 154 */     if (date.equals("02-14")) return "Won't you be my Valentine? <3";
/* 155 */     if (date.equals("03-09")) return "In remembrance...";
/* 156 */     if (date.equals("04-01")) { return "Your current subscription expires today! Renew immediately! The current subscription price is: $49.95";
/*     */     }
/*     */     
/* 159 */     if (date.equals("04-02")) { return "April fools! Of course this plugin is free! :)";
/*     */     }
/* 161 */     if (date.equals("04-12")) return "Happy Birthday Jnk!";
/* 162 */     if (date.equals("05-05")) return "So who here likes Mayonase?";
/* 163 */     if (date.equals("07-04")) return "Happy Fourth of July!";
/* 164 */     if (date.equals("12-25")) { return "Merry Christmas from IP-Check. =D";
/*     */     }
/* 166 */     return null;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\util\Messages.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */