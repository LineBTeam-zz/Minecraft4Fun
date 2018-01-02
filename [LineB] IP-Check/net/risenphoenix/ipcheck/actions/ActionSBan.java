/*    */ package net.risenphoenix.ipcheck.actions;
/*    */ 
/*    */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*    */ import net.risenphoenix.ipcheck.IPCheck;
/*    */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*    */ import net.risenphoenix.ipcheck.objects.UserObject;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.entity.Player;
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
/*    */ public class ActionSBan
/*    */ {
/*    */   private DatabaseController db;
/*    */   private ConfigurationManager config;
/*    */   private String input;
/*    */   private String message;
/*    */   
/*    */   public ActionSBan(IPCheck ipcheck, String input, String message)
/*    */   {
/* 50 */     this.db = ipcheck.getDatabaseController();
/* 51 */     this.config = ipcheck.getConfigurationManager();
/*    */     
/* 53 */     this.input = input;
/* 54 */     this.message = message;
/*    */   }
/*    */   
/*    */ 
/*    */   public Object[] execute()
/*    */   {
/* 60 */     String banMsg = (this.message == null) || (this.message.length() <= 0) ? this.config.getString("ban-message") : this.message;
/*    */     
/*    */ 
/* 63 */     UserObject upo = this.db.getUserObject(this.input);
/*    */     
/*    */ 
/* 66 */     if (upo.getBannedStatus()) {
/* 67 */       return new Object[] { Integer.valueOf(0), this.input };
/*    */     }
/*    */     
/*    */ 
/* 71 */     OfflinePlayer offline = Bukkit.getOfflinePlayer(this.input);
/* 72 */     offline.setBanned(true);
/*    */     
/*    */ 
/* 75 */     this.db.banPlayer(this.input, banMsg);
/*    */     
/*    */ 
/* 78 */     Player banPlayer = Bukkit.getPlayer(this.input);
/*    */     
/* 80 */     if (banPlayer != null) {
/* 81 */       banPlayer.kickPlayer(banMsg);
/*    */     }
/*    */     
/*    */ 
/* 85 */     return new Object[] { Integer.valueOf(1), this.input };
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\actions\ActionSBan.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */