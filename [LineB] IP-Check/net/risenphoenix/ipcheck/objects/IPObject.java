/*    */ package net.risenphoenix.ipcheck.objects;
/*    */ 
/*    */ import java.util.ArrayList;
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
/*    */ public class IPObject
/*    */ {
/*    */   private String IP;
/*    */   private ArrayList<String> Users;
/*    */   private boolean isBanned;
/*    */   private boolean isExempt;
/*    */   private boolean isRejoinExempt;
/*    */   
/*    */   public IPObject(String IP, ArrayList<String> Users, boolean isBanned, boolean isExempt, boolean isRejoinExempt)
/*    */   {
/* 45 */     this.IP = IP;
/* 46 */     this.Users = Users;
/* 47 */     this.isBanned = isBanned;
/* 48 */     this.isExempt = isExempt;
/* 49 */     this.isRejoinExempt = isRejoinExempt;
/*    */   }
/*    */   
/*    */   public IPObject(String IP, boolean isBanned) {
/* 53 */     this.IP = IP;
/* 54 */     this.isBanned = isBanned;
/*    */   }
/*    */   
/*    */   public final int getNumberOfUsers() {
/* 58 */     return this.Users.size();
/*    */   }
/*    */   
/*    */   public final String getIP() {
/* 62 */     return this.IP;
/*    */   }
/*    */   
/*    */   public final ArrayList<String> getUsers() {
/* 66 */     return this.Users;
/*    */   }
/*    */   
/*    */   public final boolean getBannedStatus() {
/* 70 */     return this.isBanned;
/*    */   }
/*    */   
/*    */   public final boolean getExemptStatus() {
/* 74 */     return this.isExempt;
/*    */   }
/*    */   
/*    */   public boolean getRejoinExemptStatus() {
/* 78 */     return this.isRejoinExempt;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\objects\IPObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */