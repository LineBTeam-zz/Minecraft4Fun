/*     */ package net.risenphoenix.commons.database;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import java.util.logging.Level;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
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
/*     */ public class DatabaseConnection
/*     */ {
/*     */   private final Plugin plugin;
/*  41 */   public Connection c = null;
/*     */   
/*     */   private String driver;
/*     */   private String connectionString;
/*     */   
/*     */   public DatabaseConnection(Plugin plugin)
/*     */   {
/*  48 */     this.plugin = plugin;
/*     */     
/*  50 */     this.driver = "org.sqlite.JDBC";
/*     */     
/*     */ 
/*  53 */     this.connectionString = ("jdbc:sqlite:" + new File(new StringBuilder().append(this.plugin.getDataFolder()).append(File.separator).append("store.db").toString()).getAbsolutePath());
/*  54 */     openConnection();
/*     */   }
/*     */   
/*     */   public DatabaseConnection(Plugin plugin, String dbName) {
/*  58 */     this.plugin = plugin;
/*     */     
/*  60 */     this.driver = "org.sqlite.JDBC";
/*     */     
/*     */ 
/*  63 */     this.connectionString = ("jdbc:sqlite:" + new File(new StringBuilder().append(this.plugin.getDataFolder()).append(File.separator).append(dbName).append(".db").toString()).getAbsolutePath());
/*  64 */     openConnection();
/*     */   }
/*     */   
/*     */ 
/*     */   public DatabaseConnection(Plugin plugin, String hostname, int port, String database, String username, String pwd)
/*     */   {
/*  70 */     this.plugin = plugin;
/*     */     
/*  72 */     this.driver = "com.mysql.jdbc.Driver";
/*  73 */     this.connectionString = ("jdbc:mysql://" + hostname + ":" + port + "/" + database + "?user=" + username + "&password=" + pwd + "&IdleTimeout=0");
/*     */     
/*     */ 
/*  76 */     openConnection();
/*     */   }
/*     */   
/*     */   public Connection openConnection() {
/*     */     try {
/*  81 */       Class.forName(this.driver);
/*  82 */       this.c = DriverManager.getConnection(this.connectionString);
/*     */       
/*     */ 
/*  85 */       this.plugin.sendConsoleMessage(Level.INFO, this.plugin
/*  86 */         .getLocalizationManager()
/*  87 */         .getLocalString("DB_OPEN_SUC"));
/*     */       
/*  89 */       return this.c;
/*     */     } catch (SQLException e) {
/*  91 */       this.plugin.sendConsoleMessage(Level.SEVERE, this.plugin
/*  92 */         .getLocalizationManager()
/*  93 */         .getLocalString("DB_CNCT_ERR") + e.getLocalizedMessage());
/*     */     } catch (ClassNotFoundException e) {
/*  95 */       this.plugin.sendConsoleMessage(Level.SEVERE, this.plugin
/*  96 */         .getLocalizationManager()
/*  97 */         .getLocalString("BAD_DB_DRVR") + this.driver);
/*     */     } catch (Exception e) {
/*  99 */       e.printStackTrace();
/*     */     }
/*     */     
/* 102 */     return this.c;
/*     */   }
/*     */   
/*     */   public void closeConnection() {
/*     */     try {
/* 107 */       if (this.c != null) this.c.close();
/* 108 */       this.plugin.sendConsoleMessage(Level.INFO, this.plugin
/* 109 */         .getLocalizationManager()
/* 110 */         .getLocalString("DB_CLOSE_SUC"));
/*     */     } catch (SQLException e) {
/* 112 */       this.plugin.sendConsoleMessage(Level.SEVERE, this.plugin
/* 113 */         .getLocalizationManager()
/* 114 */         .getLocalString("DB_CLOSE_ERR") + e.getLocalizedMessage());
/*     */     }
/*     */     
/* 117 */     this.c = null;
/*     */   }
/*     */   
/*     */   public Connection getConnection() {
/* 121 */     return this.c;
/*     */   }
/*     */   
/*     */   public boolean isConnected() {
/*     */     try {
/* 126 */       return (this.c != null) && (!this.c.isClosed());
/*     */     } catch (SQLException e) {
/* 128 */       this.plugin.sendConsoleMessage(Level.SEVERE, e
/* 129 */         .getLocalizedMessage()); }
/* 130 */     return false;
/*     */   }
/*     */   
/*     */   public Result query(PreparedStatement stmt)
/*     */   {
/* 135 */     return query(stmt, true);
/*     */   }
/*     */   
/*     */   public Result query(final PreparedStatement stmt, boolean retry) {
/*     */     try {
/* 140 */       if (!isConnected()) { openConnection();
/*     */       }
/* 142 */       if (stmt.execute()) {
/* 143 */         return new Result(stmt, stmt.getResultSet());
/*     */       }
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
/* 171 */       return null;
/*     */     }
/*     */     catch (SQLException e)
/*     */     {
/* 145 */       this.plugin.sendConsoleMessage(Level.SEVERE, this.plugin
/* 146 */         .getLocalizationManager()
/* 147 */         .getLocalString("DB_QUERY_ERR") + e.getMessage());
/*     */       
/* 149 */       if ((retry) && (e.getMessage().contains("_BUSY"))) {
/* 150 */         this.plugin.sendConsoleMessage(Level.SEVERE, this.plugin
/* 151 */           .getLocalizationManager()
/* 152 */           .getLocalString("DB_QUERY_RETRY") + e.getMessage());
/*     */         
/* 154 */         this.plugin.getServer().getScheduler()
/* 155 */           .scheduleSyncDelayedTask(this.plugin, new Runnable()
/*     */           {
/*     */ 
/*     */ 
/*     */             public void run() {
/* 159 */               DatabaseConnection.this.query(stmt, false); } }, 20L);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/*     */       try {
/* 165 */         if (stmt != null) stmt.close();
/*     */       } catch (SQLException e) {
/* 167 */         e.getStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public class Result
/*     */   {
/*     */     private ResultSet resultSet;
/*     */     private Statement statement;
/*     */     
/*     */     public Result(Statement stmt, ResultSet rset)
/*     */     {
/* 180 */       this.statement = stmt;
/* 181 */       this.resultSet = rset;
/*     */     }
/*     */     
/*     */     public ResultSet getResultSet() {
/* 185 */       return this.resultSet;
/*     */     }
/*     */     
/*     */     public String getStatement() {
/* 189 */       return this.statement.toString();
/*     */     }
/*     */     
/*     */     public void close() {
/*     */       try {
/* 194 */         this.statement.close();
/* 195 */         this.resultSet.close();
/*     */       } catch (SQLException e) {
/* 197 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\database\DatabaseConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */