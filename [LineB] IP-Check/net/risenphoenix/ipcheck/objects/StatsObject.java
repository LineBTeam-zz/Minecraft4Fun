/*     */ package net.risenphoenix.ipcheck.objects;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Map;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.commons.database.DatabaseManager.DatabaseType;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
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
/*     */ public class StatsObject
/*     */ {
/*     */   private IPCheck ipc;
/*     */   private int bannedPlayerSession;
/*     */   private int logPlayerSession;
/*     */   private int warningIssuedSession;
/*     */   private int kickIssuedSession;
/*     */   private int unbannedPlayerSession;
/*     */   
/*     */   public StatsObject(IPCheck ipc)
/*     */   {
/*  50 */     this.ipc = ipc;
/*     */   }
/*     */   
/*     */   public String getPluginVersion() {
/*  54 */     return this.ipc.getVersion();
/*     */   }
/*     */   
/*     */   public Map<String, String> getLibraryVersion() {
/*  58 */     return this.ipc.getVersionInfo();
/*     */   }
/*     */   
/*     */   public String getJavaVersion() {
/*  62 */     return System.getProperty("java.version");
/*     */   }
/*     */   
/*     */   public String getOperatingSystem() {
/*  66 */     return System.getProperty("os.name");
/*     */   }
/*     */   
/*     */   public String getOperatingSystemArch() {
/*  70 */     return System.getProperty("os.arch");
/*     */   }
/*     */   
/*     */   public int getPlayersLogged() {
/*  74 */     return this.ipc.getDatabaseController().fetchAllPlayers().size();
/*     */   }
/*     */   
/*     */   public int getIPsLogged() {
/*  78 */     return this.ipc.getDatabaseController().fetchAllIPs().size();
/*     */   }
/*     */   
/*     */   public int getPlayersExempt() {
/*  82 */     return this.ipc.getDatabaseController().getPlayerExemptList().size();
/*     */   }
/*     */   
/*     */   public int getIPsExempt() {
/*  86 */     return this.ipc.getDatabaseController().getIPExemptList().size();
/*     */   }
/*     */   
/*     */   public int getPlayersRejoinExempt() {
/*  90 */     return this.ipc.getDatabaseController().fetchRejoinExemptPlayers().size();
/*     */   }
/*     */   
/*     */   public int getIPsRejoinExempt() {
/*  94 */     return this.ipc.getDatabaseController().fetchRejoinExemptIPs().size();
/*     */   }
/*     */   
/*     */   public int getPlayersBanned() {
/*  98 */     return this.ipc.getDatabaseController().fetchBannedPlayers().size();
/*     */   }
/*     */   
/*     */   public int getIPsBanned() {
/* 102 */     return this.ipc.getDatabaseController().fetchBannedIPs().size();
/*     */   }
/*     */   
/*     */   public DatabaseManager.DatabaseType getDatabaseType() {
/* 106 */     return this.ipc.getDatabaseController().getDatabaseType();
/*     */   }
/*     */   
/*     */   public void logPlayerBan(int count) {
/* 110 */     this.bannedPlayerSession += count;
/*     */   }
/*     */   
/*     */   public void logPlayerJoin(int count) {
/* 114 */     this.logPlayerSession += count;
/*     */   }
/*     */   
/*     */   public void logWarningIssue(int count) {
/* 118 */     this.warningIssuedSession += count;
/*     */   }
/*     */   
/*     */   public void logKickIssue(int count) {
/* 122 */     this.kickIssuedSession += count;
/*     */   }
/*     */   
/*     */   public void logPlayerUnban(int count) {
/* 126 */     this.unbannedPlayerSession += count;
/*     */   }
/*     */   
/*     */   public int getBannedPlayerSession() {
/* 130 */     return this.bannedPlayerSession;
/*     */   }
/*     */   
/*     */   public int getLogPlayerSession() {
/* 134 */     return this.logPlayerSession;
/*     */   }
/*     */   
/*     */   public int getWarningIssuedSession() {
/* 138 */     return this.warningIssuedSession;
/*     */   }
/*     */   
/*     */   public int getKickIssuedSession() {
/* 142 */     return this.kickIssuedSession;
/*     */   }
/*     */   
/*     */   public int getUnbannedPlayerSession() {
/* 146 */     return this.unbannedPlayerSession;
/*     */   }
/*     */   
/*     */   public boolean getSecureStatus() {
/* 150 */     return this.ipc.getConfigurationManager().getBoolean("secure-mode");
/*     */   }
/*     */   
/*     */   public boolean getActiveStatus() {
/* 154 */     return this.ipc.getConfigurationManager().getBoolean("active-mode");
/*     */   }
/*     */   
/*     */   public boolean getBlackListStatus() {
/* 158 */     return this.ipc.getConfigurationManager().getBoolean("use-country-blacklist");
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\objects\StatsObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */