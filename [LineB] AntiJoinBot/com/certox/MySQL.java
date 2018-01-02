/*     */ package com.certox;
/*     */ 
/*     */ import com.evilmidget38.UUIDFetcher;
/*     */ import java.io.PrintStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashMap;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MySQL
/*     */ {
/*     */   private Connection con;
/*  23 */   HashMap<String, Boolean> ipBlacklist = new HashMap();
/*  24 */   HashMap<String, Boolean> userBlacklist = new HashMap();
/*     */   
/*  26 */   boolean lite = false;
/*     */   
/*     */   public void connect(String dbHost, int dbPort, String database, String dbUser, String dbPassword, boolean offline) throws ClassNotFoundException, SQLException {
/*  29 */     this.lite = offline;
/*  30 */     if (!offline) {
/*  31 */       Class.forName("com.mysql.jdbc.Driver");
/*  32 */       this.con = DriverManager.getConnection("jdbc:mysql://" + dbHost + ":" + dbPort + "/" + database + "?" + "user=" + dbUser + "&" + "password=" + dbPassword);
/*  33 */       return;
/*     */     }
/*  35 */     Class.forName("org.sqlite.JDBC");
/*  36 */     this.con = DriverManager.getConnection("jdbc:sqlite:plugins/AntiJoinBot/offline_data.db");
/*     */   }
/*     */   
/*     */   public void initDB() throws SQLException {
/*  40 */     if (!isConnected())
/*  41 */       return;
/*  42 */     Statement stm = this.con.createStatement();
/*  43 */     stm.execute("CREATE TABLE IF NOT EXISTS `ajb_blacklist` (IP varchar(15),blocked varchar(5),PRIMARY KEY(IP))");
/*  44 */     stm.execute("CREATE TABLE IF NOT EXISTS `ajb_uuid` (UUID char(36) NOT NULL,blocked varchar(5),PRIMARY KEY(UUID))");
/*     */   }
/*     */   
/*     */   public void initDBOffline() throws SQLException {
/*  48 */     if (!isConnected())
/*  49 */       return;
/*  50 */     Statement stm = this.con.createStatement();
/*  51 */     stm.execute("CREATE TABLE IF NOT EXISTS `ajb_blacklist` (IP varchar(15),blocked varchar(5),PRIMARY KEY(IP))");
/*  52 */     stm.execute("CREATE TABLE IF NOT EXISTS `ajb_user` (name varchar(32),blocked varchar(32),PRIMARY KEY(`name`, `blocked`))");
/*     */   }
/*     */   
/*     */   public void alterUUID() throws SQLException {
/*  56 */     if (!isConnected())
/*  57 */       return;
/*  58 */     Statement stm = this.con.createStatement();
/*  59 */     stm.execute("CREATE TABLE IF NOT EXISTS `ajb_uuid` (UUID char(36) NOT NULL,blocked varchar(5) NOT NULL,PRIMARY KEY(UUID))");
/*     */   }
/*     */   
/*     */   public void migrateUUID() throws Exception, SQLException {
/*  63 */     if (!isConnected())
/*  64 */       return;
/*  65 */     Statement stm = this.con.createStatement();
/*  66 */     Statement rstm = this.con.createStatement();
/*  67 */     ResultSet rs = stm.executeQuery("SELECT * from `ajb_user`");
/*  68 */     while (rs.next()) {
/*  69 */       UUID response = UUIDFetcher.getUUIDOf(rs.getString("name"));
/*  70 */       if (response != null) {
/*  71 */         if (this.lite) {
/*  72 */           rstm.execute("INSERT OR REPLACE INTO `ajb_uuid` (UUID, blocked) VALUES ('" + response + "', '" + rs.getString("blocked") + "')");
/*     */         } else {
/*  74 */           rstm.execute("REPLACE `ajb_uuid` (UUID, blocked) VALUES ('" + response + "', '" + rs.getString("blocked") + "')");
/*     */         }
/*     */       } else {
/*  77 */         System.out.println("[AJB] Couldn't find the UUID for " + rs.getString("name") + ". Skipping it");
/*     */       }
/*     */     }
/*  80 */     stm.execute("DROP TABLE `ajb_user`");
/*  81 */     rstm.close();
/*     */   }
/*     */   
/*     */   public boolean isConnected() throws SQLException {
/*  85 */     return (this.con != null) && (!this.con.isClosed());
/*     */   }
/*     */   
/*     */   public void closeConnection() throws SQLException {
/*  89 */     if (!isConnected())
/*  90 */       return;
/*     */     try {
/*  92 */       this.con.close();
/*     */     } catch (SQLException e) {
/*  94 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadDBtoRAM() throws SQLException {
/*  99 */     if (!isConnected())
/* 100 */       return;
/* 101 */     int ipBlacklistCount = 0;
/* 102 */     int userBlacklistCount = 0;
/* 103 */     Statement stm = this.con.createStatement();
/* 104 */     ResultSet rs = stm.executeQuery("SELECT * from `ajb_blacklist`");
/* 105 */     while (rs.next()) {
/* 106 */       this.ipBlacklist.put(rs.getString("IP"), Boolean.valueOf(Boolean.parseBoolean(rs.getString("blocked"))));
/* 107 */       ipBlacklistCount++;
/*     */     }
/* 109 */     stm = this.con.createStatement();
/* 110 */     rs = stm.executeQuery("SELECT * from `ajb_uuid`");
/* 111 */     while (rs.next()) {
/* 112 */       this.userBlacklist.put(rs.getString("uuid"), Boolean.valueOf(Boolean.parseBoolean(rs.getString("blocked"))));
/* 113 */       userBlacklistCount++;
/*     */     }
/*     */     
/* 116 */     System.out.println("[AJB] Loaded: " + ipBlacklistCount + " IP's");
/* 117 */     System.out.println("[AJB] Loaded: " + userBlacklistCount + " User's");
/*     */   }
/*     */   
/*     */   public void loadDBOfflinetoRAM() throws SQLException {
/* 121 */     if (!isConnected())
/* 122 */       return;
/* 123 */     int ipBlacklistCount = 0;
/* 124 */     int userBlacklistCount = 0;
/* 125 */     Statement stm = this.con.createStatement();
/* 126 */     ResultSet rs = stm.executeQuery("SELECT * from `ajb_blacklist`");
/* 127 */     while (rs.next()) {
/* 128 */       this.ipBlacklist.put(rs.getString("IP"), Boolean.valueOf(Boolean.parseBoolean(rs.getString("blocked"))));
/* 129 */       ipBlacklistCount++;
/*     */     }
/* 131 */     stm = this.con.createStatement();
/* 132 */     rs = stm.executeQuery("SELECT * from `ajb_user`");
/* 133 */     while (rs.next()) {
/* 134 */       this.userBlacklist.put(rs.getString("name"), Boolean.valueOf(Boolean.parseBoolean(rs.getString("blocked"))));
/* 135 */       userBlacklistCount++;
/*     */     }
/*     */     
/* 138 */     System.out.println("[AJB] Loaded: " + ipBlacklistCount + " IP's");
/* 139 */     System.out.println("[AJB] Loaded: " + userBlacklistCount + " User's");
/*     */   }
/*     */   
/*     */   public void startTransaction() throws SQLException {
/* 143 */     if (!isConnected())
/* 144 */       return;
/* 145 */     Statement stm = this.con.createStatement();
/* 146 */     if (this.lite) {
/* 147 */       stm.execute("BEGIN TRANSACTION");
/*     */     } else
/* 149 */       stm.execute("START TRANSACTION");
/*     */   }
/*     */   
/*     */   public void setIP(String IP, boolean blocked) throws SQLException {
/* 153 */     this.ipBlacklist.put(IP, Boolean.valueOf(blocked));
/* 154 */     if (!isConnected())
/* 155 */       return;
/* 156 */     Statement stm = this.con.createStatement();
/* 157 */     if (this.lite) {
/* 158 */       stm.execute("INSERT OR REPLACE INTO `ajb_blacklist` (IP, blocked) VALUES ('" + IP + "', '" + blocked + "')");
/*     */     } else
/* 160 */       stm.execute("REPLACE `ajb_blacklist` (IP, blocked) VALUES ('" + IP + "', '" + blocked + "')");
/*     */   }
/*     */   
/*     */   public void setUserName(Player p, boolean blocked) throws SQLException {
/* 164 */     this.userBlacklist.put(p.getName(), Boolean.valueOf(blocked));
/* 165 */     if (!isConnected())
/* 166 */       return;
/* 167 */     Statement stm = this.con.createStatement();
/* 168 */     if (this.lite) {
/* 169 */       stm.execute("INSERT OR REPLACE INTO `ajb_user` (name, blocked) VALUES ('" + p.getName() + "', '" + blocked + "')");
/*     */     } else
/* 171 */       stm.execute("REPLACE `ajb_user` (name, blocked) VALUES ('" + p.getName() + "', '" + blocked + "')");
/*     */   }
/*     */   
/*     */   public void setUser(Player p, boolean blocked) throws SQLException {
/* 175 */     this.userBlacklist.put(p.getUniqueId().toString(), Boolean.valueOf(blocked));
/* 176 */     if (!isConnected())
/* 177 */       return;
/* 178 */     Statement stm = this.con.createStatement();
/* 179 */     if (this.lite) {
/* 180 */       stm.execute("INSERT OR REPLACE INTO `ajb_uuid` (UUID, blocked) VALUES ('" + p.getUniqueId() + "', '" + blocked + "')");
/*     */     } else
/* 182 */       stm.execute("REPLACE `ajb_uuid` (UUID, blocked) VALUES ('" + p.getUniqueId() + "', '" + blocked + "')");
/*     */   }
/*     */   
/*     */   public void setUserName(String user, boolean blocked) throws SQLException {
/* 186 */     this.userBlacklist.put(user, Boolean.valueOf(blocked));
/* 187 */     if (!isConnected())
/* 188 */       return;
/* 189 */     Statement stm = this.con.createStatement();
/* 190 */     if (this.lite) {
/* 191 */       stm.execute("INSERT OR REPLACE INTO `ajb_user` (name, blocked) VALUES ('" + user + "', '" + blocked + "')");
/*     */     } else
/* 193 */       stm.execute("REPLACE `ajb_user` (name, blocked) VALUES ('" + user + "', '" + blocked + "')");
/*     */   }
/*     */   
/*     */   public void setUser(UUID user, boolean blocked) throws SQLException {
/* 197 */     this.userBlacklist.put(user.toString(), Boolean.valueOf(blocked));
/* 198 */     if (!isConnected())
/* 199 */       return;
/* 200 */     Statement stm = this.con.createStatement();
/* 201 */     if (this.lite) {
/* 202 */       stm.execute("INSERT OR REPLACE INTO `ajb_uuid` (UUID, blocked) VALUES ('" + user + "', '" + blocked + "')");
/*     */     } else
/* 204 */       stm.execute("REPLACE `ajb_uuid` (UUID, blocked) VALUES ('" + user + "', '" + blocked + "')");
/*     */   }
/*     */   
/*     */   public void commit() throws SQLException {
/* 208 */     if (!isConnected())
/* 209 */       return;
/* 210 */     Statement stm = this.con.createStatement();
/* 211 */     stm.execute("COMMIT");
/*     */   }
/*     */ }


