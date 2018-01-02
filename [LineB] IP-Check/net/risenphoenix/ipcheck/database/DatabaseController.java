/*      */ package net.risenphoenix.ipcheck.database;
/*      */ 
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.UUID;
/*      */ import net.risenphoenix.commons.Plugin;
/*      */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*      */ import net.risenphoenix.commons.database.DatabaseManager;
/*      */ import net.risenphoenix.commons.database.DatabaseManager.DatabaseType;
/*      */ import net.risenphoenix.commons.database.QueryFilter;
/*      */ import net.risenphoenix.commons.database.StatementObject;
/*      */ import net.risenphoenix.ipcheck.objects.IPObject;
/*      */ import net.risenphoenix.ipcheck.objects.UserObject;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.OfflinePlayer;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class DatabaseController
/*      */   extends DatabaseManager
/*      */ {
/*      */   public DatabaseController(Plugin plugin)
/*      */   {
/*   50 */     super(plugin, "ip-check");
/*      */     
/*      */ 
/*      */ 
/*   54 */     dropTables();
/*   55 */     initializeSQLiteTables();
/*      */   }
/*      */   
/*      */ 
/*      */   public DatabaseController(Plugin plugin, String hostname, int port, String database, String username, String pwd)
/*      */   {
/*   61 */     super(plugin, hostname, port, database, username, pwd);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*   66 */     dropTables();
/*   67 */     initializeMySQLTables();
/*      */   }
/*      */   
/*      */   private void dropTables() {
/*   71 */     if (!getPlugin().getConfigurationManager().getBoolean("dbGenerated"))
/*      */     {
/*   73 */       String SQL_0 = "DROP TABLE IF EXISTS ipcheck_log;";
/*   74 */       String SQL_1 = "DROP TABLE IF EXISTS ipcheck_user;";
/*   75 */       String SQL_2 = "DROP TABLE IF EXISTS ipcheck_ip;";
/*      */       
/*      */ 
/*   78 */       executeStatement(new StatementObject(getPlugin(), SQL_0));
/*   79 */       executeStatement(new StatementObject(getPlugin(), SQL_1));
/*   80 */       executeStatement(new StatementObject(getPlugin(), SQL_2));
/*      */       
/*      */ 
/*   83 */       getPlugin().getConfigurationManager()
/*   84 */         .setConfigurationOption("dbGenerated", Boolean.valueOf(true));
/*      */     }
/*      */   }
/*      */   
/*      */   public void initializeSQLiteTables()
/*      */   {
/*   90 */     String TABLE_IPC_LOG = "CREATE TABLE IF NOT EXISTS ipcheck_log ( ip TEXT,username TEXT,timestamp TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,PRIMARY KEY(username,ip));";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   96 */     String TABLE_IPC_USER = "CREATE TABLE IF NOT EXISTS ipcheck_user ( username TEXT,uuid TEXT,timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,banmessage TEXT,banned INTEGER DEFAULT 0,exempted INTEGER DEFAULT 0,rejoinexempt INTEGER DEFAULT 0,protected INTEGER DEFAULT 0,PRIMARY KEY(username));";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  107 */     String TABLE_IPC_IP = "CREATE TABLE IF NOT EXISTS ipcheck_ip ( ip TEXT,timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,banned INTEGER DEFAULT 0,exempted INTEGER DEFAULT 0,rejoinexempt INTEGER DEFAULT 0,PRIMARY KEY(ip));";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  115 */     executeStatement(new StatementObject(getPlugin(), TABLE_IPC_IP));
/*      */     
/*  117 */     executeStatement(new StatementObject(getPlugin(), TABLE_IPC_LOG));
/*      */     
/*  119 */     executeStatement(new StatementObject(getPlugin(), TABLE_IPC_USER));
/*      */     
/*      */ 
/*  122 */     executeColumnUpdate();
/*      */   }
/*      */   
/*      */   public void initializeMySQLTables()
/*      */   {
/*  127 */     String TABLE_IPC_LOG = "CREATE TABLE IF NOT EXISTS ipcheck_log ( ip varchar(15) NOT NULL,username varchar(255) NOT NULL,timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,PRIMARY KEY (ip,username));";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  134 */     String TABLE_IPC_USER = "CREATE TABLE IF NOT EXISTS ipcheck_user ( username varchar(255) NOT NULL,uuid varchar(255),timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,banmessage varchar(255),banned bit(1) NOT NULL DEFAULT b'0',exempted bit(1) NOT NULL DEFAULT b'0',rejoinexempt bit(1) NOT NULL DEFAULT b'0',protected bit(1) NOT NULL DEFAULT b'0',PRIMARY KEY (username));";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  146 */     String TABLE_IPC_IP = "CREATE TABLE IF NOT EXISTS ipcheck_ip ( ip varchar(15) NOT NULL,timestamp timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,banned bit(1) NOT NULL DEFAULT b'0',exempted bit(1) NOT NULL DEFAULT b'0',rejoinexempt bit(1) NOT NULL DEFAULT b'0',PRIMARY KEY (ip));";
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  155 */     executeStatement(new StatementObject(getPlugin(), TABLE_IPC_IP));
/*      */     
/*  157 */     executeStatement(new StatementObject(getPlugin(), TABLE_IPC_LOG));
/*      */     
/*  159 */     executeStatement(new StatementObject(getPlugin(), TABLE_IPC_USER));
/*      */     
/*      */ 
/*  162 */     executeColumnUpdate();
/*      */   }
/*      */   
/*      */   public final void log(String player, String ip) {
/*  166 */     addIP(ip);
/*  167 */     addPlayer(player);
/*      */     
/*  169 */     String SQL = "replace into ipcheck_log (ip,username) VALUES (?, ?)";
/*  170 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] { ip, player
/*  171 */       .toLowerCase() }));
/*      */   }
/*      */   
/*      */   public final void log(UUID uuid, String player, String ip) {
/*  175 */     log(player, ip);
/*      */     
/*  177 */     addUUID(uuid);
/*      */   }
/*      */   
/*      */   public final void addIP(String ip)
/*      */   {
/*  182 */     String SQL = "insert " + (getPlugin().getConfigurationManager().getBoolean("use-mysql") ? "" : "or ") + "ignore into " + "ipcheck_ip (ip) values (?)";
/*      */     
/*      */ 
/*  185 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] { ip }));
/*      */   }
/*      */   
/*      */ 
/*      */   public final void addPlayer(String player)
/*      */   {
/*  191 */     String SQL = "insert " + (getPlugin().getConfigurationManager().getBoolean("use-mysql") ? "" : "or ") + "ignore into " + "ipcheck_user (username) values (?)";
/*      */     
/*      */ 
/*  194 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  195 */       .toLowerCase() }));
/*      */   }
/*      */   
/*      */   public final void addUUID(UUID uuid) {
/*  199 */     String player = Bukkit.getOfflinePlayer(uuid).getName();
/*      */     
/*  201 */     String SQL = "update ipcheck_user set uuid=? where lower(username) = ?";
/*      */     
/*      */ 
/*  204 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] {uuid
/*  205 */       .toString(), player.toLowerCase() }));
/*      */   }
/*      */   
/*      */   public final UUID getUUID(String player) {
/*  209 */     String SQL = "select uuid from ipcheck_user where lower(username) = ?";
/*      */     
/*      */ 
/*  212 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  215 */         UUID uuid = null;
/*      */         try
/*      */         {
/*  218 */           while (res.next()) {
/*  219 */             if ((res.getString("uuid") != null) && 
/*  220 */               (res.getString("uuid").length() > 0)) {
/*  221 */               uuid = UUID.fromString(res.getString("uuid"));
/*      */             }
/*      */           }
/*      */         } catch (SQLException e) {
/*  225 */           e.printStackTrace();
/*      */         }
/*      */         
/*  228 */         return uuid;
/*      */       }
/*      */       
/*  231 */     };
/*  232 */     return (UUID)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  233 */       .toLowerCase() }), filter);
/*      */   }
/*      */   
/*      */ 
/*      */   public final void purgePlayer(String player)
/*      */   {
/*  239 */     String STMT_1 = "delete from ipcheck_user where lower(username) = ?";
/*  240 */     String STMT_2 = "delete from ipcheck_log where lower(username) = ?";
/*      */     
/*  242 */     executeStatement(new StatementObject(getPlugin(), STMT_1, new Object[] {player
/*  243 */       .toLowerCase() }));
/*      */     
/*  245 */     executeStatement(new StatementObject(getPlugin(), STMT_2, new Object[] {player
/*  246 */       .toLowerCase() }));
/*      */   }
/*      */   
/*      */ 
/*      */   public final void exemptPlayer(String player)
/*      */   {
/*  252 */     String SQL = "update ipcheck_user set exempted=1 where lower(username) = ?";
/*      */     
/*      */ 
/*  255 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  256 */       .toLowerCase() }));
/*      */   }
/*      */   
/*      */   public final void unexemptPlayer(String player) {
/*  260 */     String SQL = "update ipcheck_user set exempted=0 where lower(username) = ?";
/*      */     
/*      */ 
/*  263 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  264 */       .toLowerCase() }));
/*      */   }
/*      */   
/*      */   public final boolean isExemptPlayer(String player) {
/*  268 */     String SQL = "select exempted from ipcheck_user where lower(username) = ?";
/*      */     
/*      */ 
/*  271 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  274 */         boolean isExempt = false;
/*      */         try
/*      */         {
/*  277 */           while (res.next()) {
/*  278 */             int exempt = Integer.parseInt(res.getString("exempted"));
/*  279 */             isExempt = exempt == 1;
/*      */           }
/*      */         } catch (SQLException e) {
/*  282 */           e.printStackTrace();
/*      */         }
/*      */         
/*  285 */         return Boolean.valueOf(isExempt);
/*      */       }
/*      */       
/*  288 */     };
/*  289 */     return ((Boolean)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  290 */       .toLowerCase() }), filter)).booleanValue();
/*      */   }
/*      */   
/*      */   public ArrayList<String> getPlayerExemptList()
/*      */   {
/*  294 */     String SQL = "select username from ipcheck_user where exempted=1";
/*      */     
/*  296 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  299 */         ArrayList<String> users = new ArrayList();
/*      */         try
/*      */         {
/*  302 */           while (res.next()) {
/*  303 */             users.add(res.getString("username"));
/*      */           }
/*      */         } catch (SQLException e) {
/*  306 */           e.printStackTrace();
/*      */         }
/*      */         
/*  309 */         return users;
/*      */       }
/*      */       
/*  312 */     };
/*  313 */     return (ArrayList)executeQuery(new StatementObject(
/*  314 */       getPlugin(), SQL), filter);
/*      */   }
/*      */   
/*      */ 
/*      */   public final void banPlayer(String player, String message)
/*      */   {
/*  320 */     String SQL = "update ipcheck_user set banned=1, banmessage = ? where lower(username) = ?";
/*      */     
/*      */ 
/*  323 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] { message, player
/*  324 */       .toLowerCase() }));
/*      */   }
/*      */   
/*      */   public final void batchBanPlayers(String list, String msg, Boolean ban)
/*      */   {
/*  329 */     String SQL = "update ipcheck_user set banned = ?, banmessage = ? where lower(username) = '" + list.toLowerCase();
/*      */     
/*  331 */     int bit = ban.booleanValue() ? 1 : 0;
/*      */     
/*  333 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] {
/*  334 */       Integer.valueOf(bit), msg }));
/*      */   }
/*      */   
/*      */   public final void unbanPlayer(String player) {
/*  338 */     String SQL = "update ipcheck_user set banned = 0 where lower(username) = ?";
/*      */     
/*      */ 
/*  341 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  342 */       .toLowerCase() }));
/*      */   }
/*      */   
/*      */   public final boolean isBannedPlayer(String player) {
/*  346 */     String SQL = "select banned from ipcheck_user where lower(username) = ?";
/*      */     
/*      */ 
/*  349 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  352 */         boolean isBanned = false;
/*      */         try
/*      */         {
/*  355 */           if (res.next()) {
/*  356 */             int banned = Integer.parseInt(res.getString("banned"));
/*  357 */             isBanned = banned == 1;
/*      */           }
/*      */         }
/*      */         catch (SQLException e) {
/*  361 */           e.printStackTrace();
/*      */         }
/*      */         
/*  364 */         return Boolean.valueOf(isBanned);
/*      */       }
/*      */       
/*  367 */     };
/*  368 */     return ((Boolean)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  369 */       .toLowerCase() }), filter)).booleanValue();
/*      */   }
/*      */   
/*      */   public final boolean isValidPlayer(String player)
/*      */   {
/*  373 */     String SQL = "select username from ipcheck_user where lower(username) = ?";
/*      */     
/*      */ 
/*  376 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*      */         try {
/*  380 */           return Boolean.valueOf(res.next());
/*      */         } catch (SQLException e) {
/*  382 */           e.printStackTrace();
/*      */         }
/*      */         
/*  385 */         return Boolean.valueOf(false);
/*      */       }
/*      */       
/*  388 */     };
/*  389 */     return ((Boolean)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  390 */       .toLowerCase() }), filter)).booleanValue();
/*      */   }
/*      */   
/*      */   public final String getBanMessage(String player)
/*      */   {
/*  394 */     String SQL = "select banmessage from ipcheck_user where lower(username) = ?";
/*      */     
/*      */ 
/*  397 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  400 */         String message = null;
/*      */         try
/*      */         {
/*  403 */           if (res.next()) {
/*  404 */             message = res.getString("banmessage");
/*      */           }
/*      */         }
/*      */         catch (SQLException e) {
/*  408 */           e.printStackTrace();
/*      */         }
/*      */         
/*  411 */         return message;
/*      */       }
/*      */       
/*  414 */     };
/*  415 */     return (String)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  416 */       .toLowerCase() }), filter);
/*      */   }
/*      */   
/*      */ 
/*      */   public final void purgeIP(String ip)
/*      */   {
/*  422 */     String STMT_1 = "delete from ipcheck_ip where ip = ?";
/*  423 */     String STMT_2 = "delete from ipcheck_log where ip = ?";
/*      */     
/*  425 */     executeStatement(new StatementObject(getPlugin(), STMT_1, new Object[] { ip }));
/*      */     
/*      */ 
/*  428 */     executeStatement(new StatementObject(getPlugin(), STMT_2, new Object[] { ip }));
/*      */   }
/*      */   
/*      */   public final void exemptIP(String ip)
/*      */   {
/*  433 */     String SQL = "update ipcheck_ip set exempted = 1 where ip = ?";
/*      */     
/*  435 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] { ip }));
/*      */   }
/*      */   
/*      */   public final void unexemptIP(String ip)
/*      */   {
/*  440 */     String SQL = "update ipcheck_ip set exempted = 0 where ip = ?";
/*      */     
/*  442 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] { ip }));
/*      */   }
/*      */   
/*      */   public final boolean isExemptIP(String ip)
/*      */   {
/*  447 */     String SQL = "select exempted from ipcheck_ip where ip = ?";
/*      */     
/*  449 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  452 */         boolean isExempt = false;
/*      */         try
/*      */         {
/*  455 */           while (res.next()) {
/*  456 */             int exempt = Integer.parseInt(res.getString("exempted"));
/*  457 */             isExempt = exempt == 1;
/*      */           }
/*      */         } catch (SQLException e) {
/*  460 */           e.printStackTrace();
/*      */         }
/*      */         
/*  463 */         return Boolean.valueOf(isExempt);
/*      */       }
/*      */       
/*  466 */     };
/*  467 */     return ((Boolean)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] { ip }), filter)).booleanValue();
/*      */   }
/*      */   
/*      */   public ArrayList<String> getIPExemptList()
/*      */   {
/*  472 */     String SQL = "select ip from ipcheck_ip where exempted=1";
/*      */     
/*  474 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  477 */         ArrayList<String> ips = new ArrayList();
/*      */         try
/*      */         {
/*  480 */           while (res.next()) {
/*  481 */             ips.add(res.getString("ip"));
/*      */           }
/*      */         } catch (SQLException e) {
/*  484 */           e.printStackTrace();
/*      */         }
/*      */         
/*  487 */         return ips;
/*      */       }
/*      */       
/*  490 */     };
/*  491 */     return (ArrayList)executeQuery(new StatementObject(
/*  492 */       getPlugin(), SQL), filter);
/*      */   }
/*      */   
/*      */   public final void banIP(String ip) {
/*  496 */     String SQL = "update ipcheck_ip set banned = 1 where ip = ?";
/*      */     
/*  498 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] { ip }));
/*      */   }
/*      */   
/*      */ 
/*      */   public final void batchBanIPs(String list, boolean banning)
/*      */   {
/*  504 */     String SQL = "update ipcheck_ip set banned = ? where ip = '" + list.toLowerCase();
/*      */     
/*  506 */     int bit = banning ? 1 : 0;
/*      */     
/*  508 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] {
/*  509 */       Integer.valueOf(bit) }));
/*      */   }
/*      */   
/*      */   public final void unbanIP(String ip) {
/*  513 */     String SQL = "update ipcheck_ip set banned = 0 where ip = ?";
/*      */     
/*  515 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] { ip }));
/*      */   }
/*      */   
/*      */   public final boolean isBannedIP(String ip)
/*      */   {
/*  520 */     String SQL = "select banned from ipcheck_ip where ip = ?";
/*      */     
/*  522 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  525 */         boolean isBanned = false;
/*      */         try
/*      */         {
/*  528 */           if (res.next()) {
/*  529 */             int banned = Integer.parseInt(res.getString("banned"));
/*  530 */             isBanned = banned == 1;
/*      */           }
/*      */         }
/*      */         catch (SQLException e) {
/*  534 */           e.printStackTrace();
/*      */         }
/*      */         
/*  537 */         return Boolean.valueOf(isBanned);
/*      */       }
/*      */       
/*  540 */     };
/*  541 */     return ((Boolean)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] { ip }), filter)).booleanValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public final IPObject getIPObject(String ip)
/*      */   {
/*  548 */     String SQL = "select username from ipcheck_log where ip = ?";
/*      */     
/*  550 */     QueryFilter filter = new QueryFilter(new Object[] { ip, this })
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  553 */         DatabaseController dbC = (DatabaseController)getData()[1];
/*  554 */         ArrayList<String> users = new ArrayList();
/*  555 */         String ip = (String)getData()[0];
/*      */         try
/*      */         {
/*  558 */           while (res.next()) {
/*  559 */             users.add(res.getString("username"));
/*      */           }
/*      */         } catch (SQLException e) {
/*  562 */           e.printStackTrace();
/*      */         }
/*      */         
/*      */ 
/*  566 */         return new IPObject(ip, users, dbC.isBannedIP(ip), dbC.isExemptIP(ip), dbC.isRejoinExemptIP(ip));
/*      */       }
/*      */       
/*  569 */     };
/*  570 */     return (IPObject)executeQuery(new StatementObject(
/*  571 */       getPlugin(), SQL, new Object[] { ip }), filter);
/*      */   }
/*      */   
/*      */   public final UserObject getUserObject(String player) {
/*  575 */     String SQL = "select ip from ipcheck_log where lower(username) = ?";
/*  576 */     boolean isBanned = isBannedPlayer(player);
/*  577 */     boolean isExempt = isExemptPlayer(player);
/*  578 */     boolean isRejoin = isRejoinExemptPlayer(player);
/*  579 */     boolean isProtec = isProtectedPlayer(player);
/*  580 */     UUID uuid = getUUID(player);
/*      */     
/*  582 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  585 */         ArrayList<String> ips = new ArrayList();
/*      */         try
/*      */         {
/*  588 */           while (res.next()) {
/*  589 */             if (!ips.contains(res.getString("ip"))) {
/*  590 */               ips.add(res.getString("ip"));
/*      */             }
/*      */           }
/*      */         } catch (SQLException e) {
/*  594 */           e.printStackTrace();
/*      */         }
/*      */         
/*  597 */         return ips;
/*      */       }
/*      */       
/*  600 */     };
/*  601 */     ArrayList<String> ips = (ArrayList)executeQuery(new StatementObject(
/*  602 */       getPlugin(), SQL, new Object[] {player
/*  603 */       .toLowerCase() }), filter);
/*      */     
/*  605 */     return new UserObject(player.toLowerCase(), uuid, ips, isBanned, isExempt, isRejoin, isProtec);
/*      */   }
/*      */   
/*      */   public final ArrayList<String> getPlayersByUUID(UUID uuid)
/*      */   {
/*  610 */     String SQL = "select username from ipcheck_user where uuid = ?";
/*      */     
/*  612 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  615 */         ArrayList<String> users = new ArrayList();
/*      */         try
/*      */         {
/*  618 */           while (res.next()) {
/*  619 */             if (!users.contains(res.getString("username"))) {
/*  620 */               users.add(res.getString("username"));
/*      */             }
/*      */           }
/*      */         } catch (SQLException e) {
/*  624 */           e.printStackTrace();
/*      */         }
/*      */         
/*  627 */         return users;
/*      */       }
/*      */       
/*  630 */     };
/*  631 */     return (ArrayList)executeQuery(new StatementObject(
/*  632 */       getPlugin(), SQL, new Object[] { uuid.toString() }), filter);
/*      */   }
/*      */   
/*      */   public final String getLastKnownIP(String player) {
/*  636 */     String SQL = "select ip from ipcheck_log where lower(username) = ? order by timestamp desc limit 1;";
/*      */     
/*      */ 
/*  639 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  642 */         String returning = "NO_FIND";
/*      */         try
/*      */         {
/*  645 */           if (res.next()) returning = res.getString("ip");
/*      */         } catch (SQLException e) {
/*  647 */           e.printStackTrace();
/*      */         }
/*      */         
/*  650 */         return returning;
/*      */       }
/*      */       
/*  653 */     };
/*  654 */     return (String)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  655 */       .toLowerCase() }), filter);
/*      */   }
/*      */   
/*      */   public final boolean isValidIP(String ip) {
/*  659 */     String SQL = "select ip from ipcheck_ip where ip = ?";
/*      */     
/*  661 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*      */         try {
/*  665 */           return Boolean.valueOf(res.next());
/*      */         } catch (SQLException e) {
/*  667 */           e.printStackTrace();
/*      */         }
/*      */         
/*  670 */         return Boolean.valueOf(false);
/*      */       }
/*      */     };
/*      */     
/*  674 */     if (ip.equals("NO_FIND")) { return false;
/*      */     }
/*  676 */     return ((Boolean)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] { ip }), filter)).booleanValue();
/*      */   }
/*      */   
/*      */   public final String getLogTime(String player)
/*      */   {
/*  681 */     String SQL = "select timestamp from ipcheck_user where lower(username) = ?";
/*      */     
/*      */ 
/*  684 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  687 */         String returning = null;
/*      */         try
/*      */         {
/*  690 */           if (res.next()) returning = res.getString("timestamp");
/*      */         } catch (SQLException e) {
/*  692 */           e.printStackTrace();
/*      */         }
/*      */         
/*  695 */         return returning;
/*      */       }
/*      */       
/*  698 */     };
/*  699 */     return (String)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  700 */       .toLowerCase() }), filter);
/*      */   }
/*      */   
/*      */   public final String getLastTime(String player) {
/*  704 */     String SQL = "select timestamp from ipcheck_log where lower(username) = ? order by timestamp desc limit 1;";
/*      */     
/*      */ 
/*  707 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  710 */         String returning = null;
/*      */         try
/*      */         {
/*  713 */           if (res.next()) returning = res.getString("timestamp");
/*      */         } catch (SQLException e) {
/*  715 */           e.printStackTrace();
/*      */         }
/*      */         
/*  718 */         return returning;
/*      */       }
/*      */       
/*  721 */     };
/*  722 */     return (String)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  723 */       .toLowerCase() }), filter);
/*      */   }
/*      */   
/*      */   public final String getCurrentTimeStamp() {
/*  727 */     String SQL = "select CURRENT_TIMESTAMP";
/*      */     
/*  729 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  732 */         String date = null;
/*      */         try
/*      */         {
/*  735 */           res.next();
/*  736 */           date = res.getString("CURRENT_TIMESTAMP");
/*      */         } catch (SQLException e) {
/*  738 */           e.printStackTrace();
/*      */         }
/*      */         
/*  741 */         return date;
/*      */       }
/*      */       
/*  744 */     };
/*  745 */     return (String)executeQuery(new StatementObject(getPlugin(), SQL), filter);
/*      */   }
/*      */   
/*      */ 
/*      */   public final ArrayList<UserObject> getPlayersByDate(String dateOne, String dateTwo)
/*      */   {
/*  751 */     String SQL = "select username from ipcheck_user where timestamp >= ? and timestamp <= ?";
/*      */     
/*      */ 
/*  754 */     QueryFilter filter = new QueryFilter(new Object[] { this })
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  757 */         DatabaseController dbC = (DatabaseController)getData()[0];
/*  758 */         ArrayList<UserObject> users = new ArrayList();
/*      */         try
/*      */         {
/*  761 */           while (res.next()) {
/*  762 */             String user = res.getString("username");
/*  763 */             users.add(new UserObject(user, dbC
/*  764 */               .isBannedPlayer(user)));
/*      */           }
/*      */         } catch (SQLException e) {
/*  767 */           e.printStackTrace();
/*      */         }
/*      */         
/*  770 */         return users;
/*      */       }
/*      */       
/*  773 */     };
/*  774 */     return (ArrayList)executeQuery(new StatementObject(
/*  775 */       getPlugin(), SQL, new Object[] { dateOne, dateTwo }), filter);
/*      */   }
/*      */   
/*      */   public final ArrayList<UserObject> fetchAllPlayers() {
/*  779 */     String SQL = "select * from ipcheck_user";
/*      */     
/*  781 */     QueryFilter filter = new QueryFilter(new Object[] { this })
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  784 */         DatabaseController dbC = (DatabaseController)getData()[0];
/*  785 */         ArrayList<UserObject> users = new ArrayList();
/*      */         try
/*      */         {
/*  788 */           while (res.next()) {
/*  789 */             String user = res.getString("username");
/*  790 */             users.add(new UserObject(user, dbC
/*  791 */               .isBannedPlayer(user)));
/*      */           }
/*      */         } catch (SQLException e) {
/*  794 */           e.printStackTrace();
/*      */         }
/*      */         
/*  797 */         return users;
/*      */       }
/*      */       
/*  800 */     };
/*  801 */     return (ArrayList)executeQuery(new StatementObject(
/*  802 */       getPlugin(), SQL), filter);
/*      */   }
/*      */   
/*      */   public final ArrayList<UserObject> fetchBannedPlayers() {
/*  806 */     String SQL = "select * from ipcheck_user";
/*      */     
/*  808 */     QueryFilter filter = new QueryFilter(new Object[] { this })
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  811 */         DatabaseController dbC = (DatabaseController)getData()[0];
/*  812 */         ArrayList<UserObject> users = new ArrayList();
/*      */         try
/*      */         {
/*  815 */           while (res.next()) {
/*  816 */             if (res.getString("banned").equals("1")) {
/*  817 */               String user = res.getString("username");
/*  818 */               users.add(new UserObject(user, dbC
/*  819 */                 .isBannedPlayer(user)));
/*      */             }
/*      */           }
/*      */         } catch (SQLException e) {
/*  823 */           e.printStackTrace();
/*      */         }
/*      */         
/*  826 */         return users;
/*      */       }
/*      */       
/*  829 */     };
/*  830 */     return (ArrayList)executeQuery(new StatementObject(
/*  831 */       getPlugin(), SQL), filter);
/*      */   }
/*      */   
/*      */   public final ArrayList<IPObject> fetchAllIPs()
/*      */   {
/*  836 */     String SQL = "select * from ipcheck_ip";
/*      */     
/*  838 */     QueryFilter filter = new QueryFilter(new Object[] { this })
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  841 */         DatabaseController dbC = (DatabaseController)getData()[0];
/*  842 */         ArrayList<IPObject> ips = new ArrayList();
/*      */         try
/*      */         {
/*  845 */           while (res.next()) {
/*  846 */             String ip = res.getString("ip");
/*  847 */             ips.add(new IPObject(ip, dbC.isBannedIP(ip)));
/*      */           }
/*      */         } catch (SQLException e) {
/*  850 */           e.printStackTrace();
/*      */         }
/*      */         
/*  853 */         return ips;
/*      */       }
/*      */       
/*  856 */     };
/*  857 */     return (ArrayList)executeQuery(new StatementObject(
/*  858 */       getPlugin(), SQL), filter);
/*      */   }
/*      */   
/*      */   public final ArrayList<IPObject> fetchBannedIPs() {
/*  862 */     String SQL = "select * from ipcheck_ip";
/*      */     
/*  864 */     QueryFilter filter = new QueryFilter(new Object[] { this })
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  867 */         DatabaseController dbC = (DatabaseController)getData()[0];
/*  868 */         ArrayList<IPObject> ips = new ArrayList();
/*      */         try
/*      */         {
/*  871 */           while (res.next()) {
/*  872 */             if (res.getString("banned").equals("1")) {
/*  873 */               String ip = res.getString("ip");
/*  874 */               ips.add(new IPObject(ip, dbC.isBannedIP(ip)));
/*      */             }
/*      */           }
/*      */         } catch (SQLException e) {
/*  878 */           e.printStackTrace();
/*      */         }
/*      */         
/*  881 */         return ips;
/*      */       }
/*      */       
/*  884 */     };
/*  885 */     return (ArrayList)executeQuery(new StatementObject(
/*  886 */       getPlugin(), SQL), filter);
/*      */   }
/*      */   
/*      */   public final void setRejoinExemptPlayer(String player, boolean exempt) {
/*  890 */     String SQL = "update ipcheck_user set rejoinexempt = ? where username = ?";
/*      */     
/*  892 */     int value = exempt ? 1 : 0;
/*      */     
/*  894 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] {
/*  895 */       Integer.valueOf(value), player.toLowerCase() }));
/*      */   }
/*      */   
/*      */   public final boolean isRejoinExemptPlayer(String player) {
/*  899 */     String SQL = "select rejoinexempt from ipcheck_user where username = ?";
/*      */     
/*  901 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  904 */         boolean isExempt = false;
/*      */         try
/*      */         {
/*  907 */           if (res.next()) {
/*  908 */             isExempt = res.getString("rejoinexempt").equals("1");
/*      */           }
/*      */         } catch (SQLException e) {
/*  911 */           e.printStackTrace();
/*      */         }
/*      */         
/*  914 */         return Boolean.valueOf(isExempt);
/*      */       }
/*      */       
/*  917 */     };
/*  918 */     return ((Boolean)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] {player
/*  919 */       .toLowerCase() }), filter)).booleanValue();
/*      */   }
/*      */   
/*      */   public final void setRejoinExemptIP(String ip, boolean exempt)
/*      */   {
/*  923 */     String SQL = "update ipcheck_ip set rejoinexempt = ? where ip = ?";
/*  924 */     int value = exempt ? 1 : 0;
/*      */     
/*  926 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] {
/*  927 */       Integer.valueOf(value), ip }));
/*      */   }
/*      */   
/*      */   public final boolean isRejoinExemptIP(String ip) {
/*  931 */     String SQL = "select rejoinexempt from ipcheck_ip where ip = ?";
/*      */     
/*  933 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/*  936 */         boolean isExempt = false;
/*      */         try
/*      */         {
/*  939 */           if (res.next()) {
/*  940 */             isExempt = res.getString("rejoinexempt").equals("1");
/*      */           }
/*      */         } catch (SQLException e) {
/*  943 */           e.printStackTrace();
/*      */         }
/*      */         
/*  946 */         return Boolean.valueOf(isExempt);
/*      */       }
/*      */       
/*  949 */     };
/*  950 */     return ((Boolean)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] { ip }), filter)).booleanValue();
/*      */   }
/*      */   
/*      */   public final ArrayList<UserObject> fetchRejoinExemptPlayers()
/*      */   {
/*  955 */     String SQL = "select username FROM ipcheck_user WHERE rejoinexempt = 1";
/*      */     
/*  957 */     QueryFilter filter = new QueryFilter(new Object[] { this })
/*      */     {
/*      */       public Object onExecute(ResultSet res)
/*      */       {
/*  961 */         ArrayList<UserObject> upos = new ArrayList();
/*      */         
/*      */ 
/*  964 */         DatabaseController dbc = (DatabaseController)getData()[0];
/*      */         
/*      */         try
/*      */         {
/*  968 */           while (res.next())
/*  969 */             upos.add(dbc.getUserObject(res.getString("username")));
/*      */         } catch (SQLException e) {
/*  971 */           e.printStackTrace();
/*      */         }
/*      */         
/*  974 */         return upos;
/*      */       }
/*      */       
/*      */ 
/*  978 */     };
/*  979 */     return (ArrayList)executeQuery(new StatementObject(
/*  980 */       getPlugin(), SQL), filter);
/*      */   }
/*      */   
/*      */   public final ArrayList<IPObject> fetchRejoinExemptIPs() {
/*  984 */     String SQL = "select ip FROM ipcheck_ip WHERE rejoinexempt = 1";
/*      */     
/*  986 */     QueryFilter filter = new QueryFilter(new Object[] { this })
/*      */     {
/*      */       public Object onExecute(ResultSet res)
/*      */       {
/*  990 */         ArrayList<IPObject> ipos = new ArrayList();
/*      */         
/*      */ 
/*  993 */         DatabaseController dbc = (DatabaseController)getData()[0];
/*      */         
/*      */         try
/*      */         {
/*  997 */           while (res.next())
/*  998 */             ipos.add(dbc.getIPObject(res.getString("ip")));
/*      */         } catch (SQLException e) {
/* 1000 */           e.printStackTrace();
/*      */         }
/*      */         
/* 1003 */         return ipos;
/*      */       }
/*      */       
/*      */ 
/* 1007 */     };
/* 1008 */     return (ArrayList)executeQuery(new StatementObject(
/* 1009 */       getPlugin(), SQL), filter);
/*      */   }
/*      */   
/*      */   public final void protectPlayer(String player) {
/* 1013 */     String SQL = "update ipcheck_user set protected=1 where lower(username) = ?";
/*      */     
/*      */ 
/* 1016 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] {player
/* 1017 */       .toLowerCase() }));
/*      */   }
/*      */   
/*      */   public final void unprotectPlayer(String player) {
/* 1021 */     String SQL = "update ipcheck_user set protected=0 where lower(username) = ?";
/*      */     
/*      */ 
/* 1024 */     executeStatement(new StatementObject(getPlugin(), SQL, new Object[] {player
/* 1025 */       .toLowerCase() }));
/*      */   }
/*      */   
/*      */   public final boolean isProtectedPlayer(String player) {
/* 1029 */     String SQL = "select protected FROM ipcheck_user WHERE lower(username) = ?";
/*      */     
/*      */ 
/* 1032 */     QueryFilter filter = new QueryFilter()
/*      */     {
/*      */       public Object onExecute(ResultSet res) {
/* 1035 */         boolean result = false;
/*      */         try
/*      */         {
/* 1038 */           if (res.next())
/* 1039 */             result = res.getString("protected").equals("1");
/*      */         } catch (SQLException e) {
/* 1041 */           e.printStackTrace();
/*      */         }
/*      */         
/* 1044 */         return Boolean.valueOf(result);
/*      */       }
/*      */       
/* 1047 */     };
/* 1048 */     return ((Boolean)executeQuery(new StatementObject(getPlugin(), SQL, new Object[] {player
/* 1049 */       .toLowerCase() }), filter)).booleanValue();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void executeColumnUpdate()
/*      */   {
/* 1055 */     boolean hasRejoinPlayer = true;
/* 1056 */     boolean hasRejoinIP = true;
/* 1057 */     boolean hasProtectedPlayer = true;
/* 1058 */     boolean hasUUID = true;
/*      */     
/*      */ 
/* 1061 */     if (getDatabaseType() == DatabaseManager.DatabaseType.SQLITE) {
/* 1062 */       String SQL_P = "PRAGMA table_info(ipcheck_user);";
/*      */       
/* 1064 */       QueryFilter filter = new QueryFilter()
/*      */       {
/*      */         public Object onExecute(ResultSet res) {
/* 1067 */           boolean[] flags = new boolean[3];
/*      */           try
/*      */           {
/* 1070 */             while (res.next()) {
/* 1071 */               if (res.getString("name").equals("rejoinexempt"))
/* 1072 */                 flags[0] = true;
/* 1073 */               if (res.getString("name").equals("protected"))
/* 1074 */                 flags[1] = true;
/* 1075 */               if (res.getString("name").equals("uuid"))
/* 1076 */                 flags[2] = true;
/*      */             }
/*      */           } catch (SQLException e) {
/* 1079 */             e.printStackTrace();
/*      */           }
/*      */           
/* 1082 */           return flags;
/*      */         }
/*      */         
/*      */ 
/* 1086 */       };
/* 1087 */       boolean[] res = (boolean[])executeQuery(new StatementObject(
/* 1088 */         getPlugin(), SQL_P), filter);
/*      */       
/* 1090 */       hasRejoinPlayer = res[0];
/* 1091 */       hasProtectedPlayer = res[1];
/* 1092 */       hasUUID = res[2];
/*      */       
/* 1094 */       String SQL_I = "PRAGMA table_info(ipcheck_ip);";
/*      */       
/* 1096 */       QueryFilter filter_two = new QueryFilter()
/*      */       {
/*      */         public Object onExecute(ResultSet res) {
/* 1099 */           boolean flag = false;
/*      */           try
/*      */           {
/* 1102 */             while (res.next()) {
/* 1103 */               if (res.getString("name").equals("rejoinexempt"))
/* 1104 */                 flag = true;
/*      */             }
/*      */           } catch (SQLException e) {
/* 1107 */             e.printStackTrace();
/*      */           }
/*      */           
/* 1110 */           return Boolean.valueOf(flag);
/*      */         }
/*      */         
/*      */ 
/* 1114 */       };
/* 1115 */       hasRejoinIP = ((Boolean)executeQuery(new StatementObject(
/* 1116 */         getPlugin(), SQL_I), filter_two)).booleanValue();
/*      */       
/*      */ 
/*      */ 
/* 1119 */       if (!hasRejoinPlayer) {
/* 1120 */         String SQL_ZERO = "ALTER TABLE ipcheck_user ADD COLUMN rejoinexempt INTEGER DEFAULT 0";
/*      */         
/*      */ 
/* 1123 */         executeStatement(new StatementObject(getPlugin(), SQL_ZERO));
/*      */       }
/*      */       
/*      */ 
/* 1127 */       if (!hasProtectedPlayer) {
/* 1128 */         String SQL_ZERO = "ALTER TABLE ipcheck_user ADD COLUMN protected INTEGER DEFAULT 0";
/*      */         
/*      */ 
/* 1131 */         executeStatement(new StatementObject(getPlugin(), SQL_ZERO));
/*      */       }
/*      */       
/*      */ 
/* 1135 */       if (!hasUUID) {
/* 1136 */         String SQL_ZERO = "ALTER TABLE ipcheck_user ADD COLUMN uuid TEXT";
/*      */         
/*      */ 
/* 1139 */         executeStatement(new StatementObject(getPlugin(), SQL_ZERO));
/*      */       }
/*      */       
/*      */ 
/* 1143 */       if (!hasRejoinIP) {
/* 1144 */         String SQL_ZERO = "ALTER TABLE ipcheck_ip ADD COLUMN rejoinexempt INTEGER DEFAULT 0";
/*      */         
/*      */ 
/* 1147 */         executeStatement(new StatementObject(getPlugin(), SQL_ZERO));
/*      */       }
/*      */       
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/* 1155 */       String SQL_P = "SHOW COLUMNS FROM " + getPlugin().getConfigurationManager().getString("dbName") + ".ipcheck_user";
/*      */       
/*      */ 
/* 1158 */       QueryFilter filter = new QueryFilter()
/*      */       {
/*      */         public Object onExecute(ResultSet res) {
/* 1161 */           boolean[] flags = new boolean[3];
/*      */           try
/*      */           {
/* 1164 */             while (res.next()) {
/* 1165 */               if (res.getString("Field").equals("rejoinexempt"))
/* 1166 */                 flags[0] = true;
/* 1167 */               if (res.getString("Field").equals("protected"))
/* 1168 */                 flags[1] = true;
/* 1169 */               if (res.getString("Field").equals("uuid"))
/* 1170 */                 flags[2] = true;
/*      */             }
/*      */           } catch (SQLException e) {
/* 1173 */             e.printStackTrace();
/*      */           }
/*      */           
/* 1176 */           return flags;
/*      */         }
/*      */         
/*      */ 
/* 1180 */       };
/* 1181 */       boolean[] res = (boolean[])executeQuery(new StatementObject(
/* 1182 */         getPlugin(), SQL_P), filter);
/*      */       
/* 1184 */       hasRejoinPlayer = res[0];
/* 1185 */       hasProtectedPlayer = res[1];
/* 1186 */       hasUUID = res[2];
/*      */       
/*      */ 
/* 1189 */       String SQL_I = "SHOW COLUMNS FROM " + getPlugin().getConfigurationManager().getString("dbName") + ".ipcheck_ip";
/*      */       
/*      */ 
/* 1192 */       QueryFilter filter_two = new QueryFilter()
/*      */       {
/*      */         public Object onExecute(ResultSet res) {
/* 1195 */           boolean flag = false;
/*      */           try
/*      */           {
/* 1198 */             while (res.next()) {
/* 1199 */               if (res.getString("Field").equals("rejoinexempt"))
/* 1200 */                 flag = true;
/*      */             }
/*      */           } catch (SQLException e) {
/* 1203 */             e.printStackTrace();
/*      */           }
/*      */           
/* 1206 */           return Boolean.valueOf(flag);
/*      */         }
/*      */         
/*      */ 
/* 1210 */       };
/* 1211 */       hasRejoinIP = ((Boolean)executeQuery(new StatementObject(
/* 1212 */         getPlugin(), SQL_I), filter_two)).booleanValue();
/*      */       
/*      */ 
/*      */ 
/* 1215 */       if (!hasRejoinPlayer) {
/* 1216 */         String SQL_ZERO = "ALTER TABLE ipcheck_user ADD COLUMN rejoinexempt bit(1) NOT NULL DEFAULT b'0'";
/*      */         
/*      */ 
/* 1219 */         executeStatement(new StatementObject(getPlugin(), SQL_ZERO));
/*      */       }
/*      */       
/*      */ 
/* 1223 */       if (!hasProtectedPlayer) {
/* 1224 */         String SQL_ZERO = "ALTER TABLE ipcheck_user ADD COLUMN protected bit(1) NOT NULL DEFAULT b'0'";
/*      */         
/*      */ 
/* 1227 */         executeStatement(new StatementObject(getPlugin(), SQL_ZERO));
/*      */       }
/*      */       
/*      */ 
/* 1231 */       if (!hasUUID) {
/* 1232 */         String SQL_ZERO = "ALTER TABLE ipcheck_user ADD COLUMN uuid varchar(255)";
/*      */         
/*      */ 
/* 1235 */         executeStatement(new StatementObject(getPlugin(), SQL_ZERO));
/*      */       }
/*      */       
/*      */ 
/* 1239 */       if (!hasRejoinIP) {
/* 1240 */         String SQL_ZERO = "ALTER TABLE ipcheck_ip ADD COLUMN rejoinexempt bit(1) NOT NULL DEFAULT b'0'";
/*      */         
/*      */ 
/* 1243 */         executeStatement(new StatementObject(getPlugin(), SQL_ZERO));
/*      */       }
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\database\DatabaseController.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */