/*    */ package net.risenphoenix.ipcheck.objects;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.UUID;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ public class UserObject
/*    */ {
/*    */   private String User;
/*    */   private UUID uuid;
/*    */   private ArrayList<String> IPs;
/*    */   private boolean isBanned;
/*    */   private boolean isExempt;
/*    */   private boolean isRejoinExempt;
/*    */   private boolean isProtected;
/*    */   
/*    */   public UserObject(String User, UUID uuid, ArrayList<String> IPs, boolean isBanned, boolean isExempt, boolean isRejoinExempt, boolean isProtected)
/*    */   {
/* 51 */     this.User = User;
/* 52 */     this.uuid = uuid;
/* 53 */     this.IPs = IPs;
/* 54 */     this.isBanned = isBanned;
/* 55 */     this.isExempt = isExempt;
/* 56 */     this.isRejoinExempt = isRejoinExempt;
/* 57 */     this.isProtected = isProtected;
/*    */   }
/*    */   
/*    */   public UserObject(String user, boolean isBanned) {
/* 61 */     this.User = user;
/* 62 */     this.isBanned = isBanned;
/*    */   }
/*    */   
/*    */   public final String getUser() {
/* 66 */     return this.User;
/*    */   }
/*    */   
/*    */   public final UUID getUUID() {
/* 70 */     return this.uuid;
/*    */   }
/*    */   
/*    */   public final ArrayList<String> getIPs() {
/* 74 */     return this.IPs;
/*    */   }
/*    */   
/*    */   public final int getNumberOfIPs() {
/* 78 */     return this.IPs.size();
/*    */   }
/*    */   
/*    */   public final boolean getBannedStatus() {
/* 82 */     return this.isBanned;
/*    */   }
/*    */   
/*    */   public final boolean getExemptStatus() {
/* 86 */     return this.isExempt;
/*    */   }
/*    */   
/*    */   public boolean getRejoinExemptStatus() {
/* 90 */     return this.isRejoinExempt;
/*    */   }
/*    */   
/*    */   public boolean getProtectedStatus() {
/* 94 */     return this.isProtected;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\objects\UserObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */