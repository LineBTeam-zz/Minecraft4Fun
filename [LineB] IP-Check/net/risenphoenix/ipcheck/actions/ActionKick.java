/*     */ package net.risenphoenix.ipcheck.actions;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*     */ import net.risenphoenix.ipcheck.objects.IPObject;
/*     */ import org.bukkit.Bukkit;
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
/*     */ public class ActionKick
/*     */ {
/*     */   private IPCheck ipc;
/*     */   private DatabaseController db;
/*     */   private LocalizationManager local;
/*     */   private ConfigurationManager config;
/*     */   private CommandSender sender;
/*     */   private String input;
/*     */   private String message;
/*     */   
/*     */   public ActionKick(IPCheck ipcheck, CommandSender sender, String input, String message)
/*     */   {
/*  55 */     this.ipc = ipcheck;
/*  56 */     this.db = ipcheck.getDatabaseController();
/*  57 */     this.local = ipcheck.getLocalizationManager();
/*  58 */     this.config = ipcheck.getConfigurationManager();
/*     */     
/*  60 */     this.sender = sender;
/*  61 */     this.input = input;
/*  62 */     this.message = message;
/*     */   }
/*     */   
/*     */ 
/*     */   public Object[] execute()
/*     */   {
/*  68 */     int accounts = 0;
/*     */     
/*     */ 
/*  71 */     String ip_filter = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
/*     */     IPObject ipo;
/*     */     IPObject ipo;
/*  74 */     if (this.input.matches(ip_filter)) {
/*  75 */       ipo = this.db.getIPObject(this.input);
/*     */     } else {
/*  77 */       ipo = this.db.getIPObject(this.db.getLastKnownIP(this.input));
/*     */     }
/*     */     
/*     */ 
/*  81 */     if (!this.db.isValidIP(ipo.getIP())) {
/*  82 */       this.ipc.sendPlayerMessage(this.sender, this.local.getLocalString("NO_FIND"));
/*  83 */       return new Object[] { Integer.valueOf(0) };
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  88 */     String kickMsg = (this.message == null) || (this.message.length() <= 0) ? this.config.getString("kick-message") : this.message;
/*     */     
/*     */ 
/*  91 */     for (String s : ipo.getUsers())
/*     */     {
/*  93 */       Player player = Bukkit.getPlayer(s);
/*     */       
/*     */ 
/*  96 */       if (player != null) {
/*  97 */         player.kickPlayer(kickMsg);
/*  98 */         accounts++;
/*     */       }
/*     */     }
/*     */     
/*     */     String calledAcct;
/*     */     String calledAcct;
/* 104 */     if (this.input.matches(ip_filter)) {
/* 105 */       calledAcct = (String)ipo.getUsers().get(0);
/*     */     } else {
/* 107 */       calledAcct = this.input;
/*     */     }
/*     */     
/* 110 */     return new Object[] { Integer.valueOf(accounts), calledAcct };
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\actions\ActionKick.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */