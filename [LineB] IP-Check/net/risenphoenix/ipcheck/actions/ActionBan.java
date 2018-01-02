/*     */ package net.risenphoenix.ipcheck.actions;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*     */ import net.risenphoenix.ipcheck.objects.IPObject;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
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
/*     */ public class ActionBan
/*     */ {
/*     */   private IPCheck ipc;
/*     */   private DatabaseController db;
/*     */   private LocalizationManager local;
/*     */   private ConfigurationManager config;
/*     */   private CommandSender sender;
/*     */   private String input;
/*     */   private String message;
/*     */   private boolean banning;
/*     */   
/*     */   public ActionBan(IPCheck ipcheck, CommandSender sender, String input, String message, boolean banning)
/*     */   {
/*  57 */     this.ipc = ipcheck;
/*  58 */     this.db = ipcheck.getDatabaseController();
/*  59 */     this.local = ipcheck.getLocalizationManager();
/*  60 */     this.config = ipcheck.getConfigurationManager();
/*     */     
/*  62 */     this.sender = sender;
/*  63 */     this.input = input;
/*  64 */     this.message = message;
/*  65 */     this.banning = banning;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object[] execute()
/*     */   {
/*  71 */     int accounts = 0;
/*     */     
/*     */ 
/*  74 */     String ip_filter = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
/*     */     IPObject ipo;
/*     */     IPObject ipo;
/*  77 */     if (this.input.matches(ip_filter)) {
/*  78 */       ipo = this.db.getIPObject(this.input);
/*     */     } else {
/*  80 */       ipo = this.db.getIPObject(this.db.getLastKnownIP(this.input));
/*     */     }
/*     */     
/*     */ 
/*  84 */     if (!this.db.isValidIP(ipo.getIP())) {
/*  85 */       this.ipc.sendPlayerMessage(this.sender, this.local.getLocalString("NO_FIND"));
/*  86 */       return new Object[] { Integer.valueOf(0) };
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  91 */     String banMsg = (this.message == null) || (this.message.length() <= 0) ? this.config.getString("ban-message") : this.message;
/*     */     
/*     */ 
/*  94 */     boolean playerProtected = false;
/*     */     
/*     */ 
/*  97 */     for (String s : ipo.getUsers())
/*     */     {
/*  99 */       OfflinePlayer offPlayer = Bukkit.getOfflinePlayer(s);
/*     */       
/*     */ 
/* 102 */       if (this.db.isBannedPlayer(s) != this.banning)
/*     */       {
/* 104 */         if ((this.banning) && 
/* 105 */           (this.db.isProtectedPlayer(s))) {
/* 106 */           playerProtected = true;
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/*     */ 
/* 112 */           accounts++;
/*     */           
/*     */ 
/* 115 */           offPlayer.setBanned(this.banning);
/*     */           
/*     */ 
/* 118 */           if (this.banning) {
/* 119 */             this.db.banPlayer(s, banMsg);
/*     */           } else {
/* 121 */             this.db.unbanPlayer(s);
/*     */           }
/*     */           
/*     */ 
/* 125 */           if (this.banning)
/*     */           {
/* 127 */             Player banPlayer = Bukkit.getPlayer(s);
/*     */             
/* 129 */             if (banPlayer != null) {
/* 130 */               banPlayer.kickPlayer(banMsg);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 137 */     if (this.banning) {
/* 138 */       if ((!ipo.getBannedStatus()) && (!playerProtected)) {
/* 139 */         Bukkit.banIP(ipo.getIP());
/* 140 */         this.db.banIP(ipo.getIP());
/*     */       }
/*     */     }
/* 143 */     else if (ipo.getBannedStatus()) {
/* 144 */       Bukkit.unbanIP(ipo.getIP());
/* 145 */       this.db.unbanIP(ipo.getIP());
/*     */     }
/*     */     
/*     */     String calledAcct;
/*     */     
/*     */     String calledAcct;
/* 151 */     if (this.input.matches(ip_filter)) {
/* 152 */       calledAcct = (String)ipo.getUsers().get(0);
/*     */     } else {
/* 154 */       calledAcct = this.input;
/*     */     }
/*     */     
/* 157 */     return new Object[] { Integer.valueOf(accounts), calledAcct };
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\actions\ActionBan.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */