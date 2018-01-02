/*    */ package net.risenphoenix.ipcheck.events;
/*    */ 
/*    */ import net.risenphoenix.commons.localization.LocalizationManager;
/*    */ import net.risenphoenix.ipcheck.IPCheck;
/*    */ import org.bukkit.ChatColor;
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
/*    */ public class RejoinNotification
/*    */ {
/*    */   private IPCheck ipc;
/*    */   private LocalizationManager local;
/*    */   private Player player;
/*    */   
/*    */   public RejoinNotification(IPCheck ipc, Player player)
/*    */   {
/* 45 */     this.ipc = ipc;
/* 46 */     this.local = ipc.getLocalizationManager();
/* 47 */     this.player = player;
/*    */     
/* 49 */     execute();
/*    */   }
/*    */   
/*    */   private void execute() {
/* 53 */     Player[] online = this.ipc.getOnlinePlayers();
/*    */     
/* 55 */     for (int i = 0; i < online.length; i++) {
/* 56 */       displayWarning(online[i]);
/*    */     }
/*    */   }
/*    */   
/*    */   private void displayWarning(Player p) {
/* 61 */     if ((p.hasPermission("ipcheck.getnotify")) || (p.isOp())) {
/* 62 */       this.ipc.sendPlayerMessage(p, ChatColor.DARK_GRAY + "---------------------------------------", false);
/*    */       
/*    */ 
/* 65 */       this.ipc.sendPlayerMessage(p, ChatColor.RED + this.local
/* 66 */         .getLocalString("REJOIN_WARN") + " " + ChatColor.LIGHT_PURPLE + this.player
/* 67 */         .getDisplayName() + ChatColor.RED + this.local
/* 68 */         .getLocalString("REJOIN_EXPLAIN"));
/*    */       
/* 70 */       this.ipc.sendPlayerMessage(p, ChatColor.DARK_GRAY + "---------------------------------------", false);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\events\RejoinNotification.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */