/*     */ package net.risenphoenix.ipcheck.actions;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*     */ import net.risenphoenix.ipcheck.objects.UserObject;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
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
/*     */ 
/*     */ public class ActionBanAll
/*     */ {
/*     */   private DatabaseController db;
/*     */   private ConfigurationManager config;
/*     */   private String tsOne;
/*     */   private String tsTwo;
/*     */   private String message;
/*     */   private boolean banning;
/*     */   
/*     */   public ActionBanAll(IPCheck ipcheck, String timeStampOne, String timeStampTwo, String message, boolean banning)
/*     */   {
/*  55 */     IPCheck ipc = ipcheck;
/*  56 */     this.db = ipc.getDatabaseController();
/*  57 */     this.config = ipc.getConfigurationManager();
/*     */     
/*  59 */     this.tsOne = timeStampOne;
/*  60 */     this.tsTwo = timeStampTwo;
/*  61 */     this.message = message;
/*  62 */     this.banning = banning;
/*     */   }
/*     */   
/*     */   public Object[] execute()
/*     */   {
/*  67 */     ArrayList<UserObject> pResults = this.db.getPlayersByDate(this.tsOne, this.tsTwo);
/*  68 */     int validAccounts = 0;
/*     */     
/*  70 */     int depthUser = 0;
/*  71 */     int depthIP = 0;
/*     */     
/*     */ 
/*  74 */     String acctName = "";
/*     */     
/*     */ 
/*  77 */     if (pResults.size() > 0)
/*     */     {
/*  79 */       ArrayList<StringBuilder> sbBanUser = new ArrayList();
/*  80 */       ArrayList<StringBuilder> sbBanIP = new ArrayList();
/*     */       
/*     */ 
/*  83 */       StringBuilder userBanString = new StringBuilder();
/*  84 */       StringBuilder ipBanString = new StringBuilder();
/*     */       
/*     */ 
/*     */ 
/*  88 */       String banMsg = (this.message == null) || (this.message.length() <= 0) ? this.config.getString("ban-message") : this.message;
/*     */       
/*     */ 
/*  91 */       for (UserObject upo : pResults)
/*     */       {
/*  93 */         String ip = this.db.getLastKnownIP(upo.getUser());
/*     */         
/*     */ 
/*  96 */         if (this.db.isBannedIP(ip) != this.banning)
/*     */         {
/*  98 */           ipBanString.append(ip + "' or ip='");
/*  99 */           depthIP++;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 109 */           if ((ipBanString.length() >= 50000) || (depthIP >= 998)) {
/* 110 */             sbBanIP.add(ipBanString);
/* 111 */             ipBanString = new StringBuilder();
/* 112 */             depthIP = 0;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 118 */         if (upo.getBannedStatus() != this.banning)
/*     */         {
/*     */ 
/*     */ 
/* 122 */           if (validAccounts == 0) { acctName = upo.getUser();
/*     */           }
/*     */           
/* 125 */           validAccounts++;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 130 */           if (this.banning) {
/* 131 */             Player p = Bukkit.getPlayer(upo.getUser());
/*     */             
/* 133 */             if (p != null) {
/* 134 */               if (!this.db.isProtectedPlayer(p.getName())) {
/* 135 */                 p.kickPlayer(banMsg);
/* 136 */                 p.setBanned(true);
/*     */               }
/*     */             }
/*     */             else {
/* 140 */               OfflinePlayer op = Bukkit.getOfflinePlayer(upo.getUser());
/*     */               
/* 142 */               if (op != null) {
/* 143 */                 op.setBanned(true);
/*     */               }
/*     */               
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 150 */             OfflinePlayer p = Bukkit.getOfflinePlayer(upo.getUser());
/*     */             
/* 152 */             if (p != null) {
/* 153 */               p.setBanned(false);
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 158 */           if ((!this.banning) || ((this.banning) && (!this.db.isProtectedPlayer(upo
/* 159 */             .getUser())))) {
/* 160 */             userBanString.append(upo.getUser().toLowerCase() + "' or lower(username)='");
/*     */             
/* 162 */             depthUser++;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 173 */           if ((userBanString.length() >= 50000) || (depthUser >= 998)) {
/* 174 */             sbBanUser.add(userBanString);
/* 175 */             userBanString = new StringBuilder();
/* 176 */             depthUser = 0;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 182 */       sbBanIP.add(ipBanString);
/* 183 */       sbBanUser.add(userBanString);
/*     */       
/*     */ 
/* 186 */       if (((StringBuilder)sbBanUser.get(0)).length() > 0) {
/* 187 */         for (StringBuilder sb : sbBanUser)
/*     */         {
/*     */ 
/*     */ 
/* 191 */           String syntax = sb.toString().substring(0, sb
/* 192 */             .toString().length() - 21);
/*     */           
/* 194 */           this.db.batchBanPlayers(syntax, banMsg, Boolean.valueOf(this.banning));
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 199 */       if (((StringBuilder)sbBanIP.get(0)).length() > 0) {
/* 200 */         for (StringBuilder sb : sbBanIP)
/*     */         {
/*     */ 
/*     */ 
/* 204 */           String syntax = sb.toString().substring(0, sb.length() - 8);
/*     */           
/* 206 */           this.db.batchBanIPs(syntax, this.banning);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 212 */     return new Object[] { Integer.valueOf(validAccounts), acctName };
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\actions\ActionBanAll.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */