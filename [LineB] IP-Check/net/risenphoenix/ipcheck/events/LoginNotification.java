/*     */ package net.risenphoenix.ipcheck.events;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*     */ import net.risenphoenix.ipcheck.objects.StatsObject;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
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
/*     */ public class LoginNotification
/*     */ {
/*     */   private IPCheck ipc;
/*     */   private DatabaseController db;
/*     */   private ConfigurationManager config;
/*     */   private LocalizationManager local;
/*     */   private Player player;
/*     */   private String ip;
/*     */   private ArrayList<String> accounts;
/*     */   
/*     */   public LoginNotification(IPCheck ipc, Player player, String ip, ArrayList<String> accounts)
/*     */   {
/*  55 */     this.ipc = ipc;
/*  56 */     this.db = ipc.getDatabaseController();
/*  57 */     this.config = ipc.getConfigurationManager();
/*  58 */     this.local = ipc.getLocalizationManager();
/*     */     
/*  60 */     this.player = player;
/*  61 */     this.ip = ip;
/*  62 */     this.accounts = accounts;
/*     */     
/*  64 */     execute();
/*     */   }
/*     */   
/*     */   private void execute() {
/*  68 */     Player[] online = this.ipc.getOnlinePlayers();
/*  69 */     int threshold = this.config.getInteger("min-account-notify-threshold");
/*  70 */     int acctNum = this.accounts.size();
/*     */     
/*     */ 
/*  73 */     if (acctNum > threshold)
/*     */     {
/*     */ 
/*  76 */       if ((!this.db.isExemptPlayer(this.player.getName())) && (!this.db.isExemptIP(this.ip)))
/*     */       {
/*     */ 
/*  79 */         this.ipc.getStatisticsObject().logWarningIssue(1);
/*     */         
/*  81 */         for (Player anOnline : online) {
/*  82 */           displayReport(anOnline);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void displayReport(Player p) {
/*  89 */     if ((p.hasPermission("ipcheck.getnotify")) || (p.isOp())) {
/*  90 */       if (this.config.getBoolean("descriptive-notice"))
/*     */       {
/*     */ 
/*  93 */         this.ipc.sendPlayerMessage(p, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */         
/*     */ 
/*  96 */         this.ipc.sendPlayerMessage(p, "Report for: " + ChatColor.LIGHT_PURPLE + this.player
/*  97 */           .getName(), false);
/*  98 */         this.ipc.sendPlayerMessage(p, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 103 */         if ((p.hasPermission("ipcheck.showip")) || (p.isOp())) {
/* 104 */           this.ipc.sendPlayerMessage(p, "IP Address: " + ChatColor.LIGHT_PURPLE + this.ip, false);
/*     */         }
/*     */         
/*     */ 
/* 108 */         this.ipc.sendPlayerMessage(p, ChatColor.LIGHT_PURPLE + this.player
/* 109 */           .getName() + ChatColor.RED + " was found to have " + ChatColor.YELLOW + this.accounts + ChatColor.RED + " possible alternative accounts. " + "Perform command " + ChatColor.LIGHT_PURPLE + "'/ipc " + this.player
/*     */           
/*     */ 
/*     */ 
/* 113 */           .getDisplayName() + "'" + ChatColor.RED + " for " + "more information.", false);
/*     */         
/*     */ 
/* 116 */         this.ipc.sendPlayerMessage(p, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */       }
/*     */       else
/*     */       {
/* 120 */         this.ipc.sendPlayerMessage(p, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */         
/*     */ 
/* 123 */         this.ipc.sendPlayerMessage(p, ChatColor.RED + this.local
/* 124 */           .getLocalString("LOGIN_WARN") + " " + ChatColor.LIGHT_PURPLE + this.player
/* 125 */           .getDisplayName() + ChatColor.RED + this.local
/* 126 */           .getLocalString("LOGIN_EXPLAIN"));
/*     */         
/* 128 */         this.ipc.sendPlayerMessage(p, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\events\LoginNotification.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */