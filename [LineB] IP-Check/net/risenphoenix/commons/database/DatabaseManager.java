/*     */ package net.risenphoenix.commons.database;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.logging.Level;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
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
/*     */ public class DatabaseManager
/*     */ {
/*     */   private final Plugin plugin;
/*     */   private DatabaseConnection connection;
/*     */   private DatabaseType type;
/*  45 */   private boolean debug = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  50 */   private String hostname = null;
/*  51 */   private int port = -1;
/*  52 */   private String database = null;
/*  53 */   private String username = null;
/*  54 */   private String password = null;
/*     */   
/*     */ 
/*  57 */   private String dbName = null;
/*     */   
/*     */   public DatabaseManager(Plugin plugin)
/*     */   {
/*  61 */     this.plugin = plugin;
/*  62 */     this.connection = new DatabaseConnection(plugin);
/*  63 */     this.type = DatabaseType.SQLITE;
/*     */   }
/*     */   
/*     */   public DatabaseManager(Plugin plugin, String dbName)
/*     */   {
/*  68 */     this.plugin = plugin;
/*     */     
/*     */ 
/*  71 */     this.dbName = dbName;
/*     */     
/*     */ 
/*  74 */     this.connection = new DatabaseConnection(plugin, dbName);
/*  75 */     this.type = DatabaseType.SQLITE;
/*     */   }
/*     */   
/*     */ 
/*     */   public DatabaseManager(Plugin plugin, String hostname, int port, String database, String username, String pwd)
/*     */   {
/*  81 */     this.plugin = plugin;
/*     */     
/*     */ 
/*  84 */     this.hostname = hostname;
/*  85 */     this.port = port;
/*  86 */     this.database = database;
/*  87 */     this.username = username;
/*  88 */     this.password = pwd;
/*     */     
/*  90 */     this.connection = new DatabaseConnection(plugin, hostname, port, database, username, pwd);
/*     */     
/*     */ 
/*  93 */     this.type = DatabaseType.MYSQL;
/*     */   }
/*     */   
/*     */   public void enableDebug(boolean shouldDebug) {
/*  97 */     this.debug = shouldDebug;
/*  98 */     this.plugin.sendConsoleMessage(Level.INFO, this.plugin
/*  99 */       .getLocalizationManager().getLocalString("DB_DEBUG_ACTIVE"));
/*     */   }
/*     */   
/*     */   public final boolean executeStatement(StatementObject stmt)
/*     */   {
/*     */     try {
/* 105 */       confirmConnection();
/*     */       
/* 107 */       PreparedStatement statement = stmt.getStatement(this.connection.getConnection());
/*     */       
/* 109 */       if (this.debug) {
/* 110 */         this.plugin.sendConsoleMessage(Level.INFO, statement.toString());
/*     */       }
/* 112 */       stmt.getStatement(this.connection.getConnection()).executeUpdate();
/* 113 */       return true;
/*     */     } catch (Exception e) {
/* 115 */       e.printStackTrace();
/*     */     }
/*     */     
/* 118 */     return false;
/*     */   }
/*     */   
/*     */   public final Object executeQuery(StatementObject stmt, QueryFilter filter)
/*     */   {
/* 123 */     ResultSet res = null;
/* 124 */     obj = null;
/*     */     try
/*     */     {
/* 127 */       confirmConnection();
/*     */       
/* 129 */       res = stmt.getStatement(this.connection.getConnection()).executeQuery();
/* 130 */       obj = filter.onExecute(res);
/* 131 */       res.close();
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
/* 145 */       return obj;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 133 */       this.plugin.sendConsoleMessage(Level.SEVERE, e
/* 134 */         .getMessage());
/*     */     } finally {
/*     */       try {
/* 137 */         if (res != null) res.close();
/*     */       } catch (Exception e) {
/* 139 */         this.plugin.sendConsoleMessage(Level.SEVERE, e
/* 140 */           .getMessage());
/* 141 */         res = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void confirmConnection()
/*     */   {
/* 149 */     if (this.connection == null) {
/* 150 */       if (getDatabaseType().equals(DatabaseType.MYSQL)) {
/* 151 */         this.connection = new DatabaseConnection(getPlugin(), this.hostname, this.port, this.database, this.username, this.password);
/*     */ 
/*     */       }
/* 154 */       else if (this.dbName == null) {
/* 155 */         this.connection = new DatabaseConnection(getPlugin());
/*     */       } else {
/* 157 */         this.connection = new DatabaseConnection(getPlugin(), this.dbName);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public final Plugin getPlugin()
/*     */   {
/* 164 */     return this.plugin;
/*     */   }
/*     */   
/*     */   public final DatabaseConnection getDatabaseConnection() {
/* 168 */     return this.connection;
/*     */   }
/*     */   
/*     */   public final DatabaseType getDatabaseType() {
/* 172 */     return this.type;
/*     */   }
/*     */   
/*     */   public static enum DatabaseType {
/* 176 */     MYSQL, 
/* 177 */     SQLITE;
/*     */     
/*     */     private DatabaseType() {}
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\database\DatabaseManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */