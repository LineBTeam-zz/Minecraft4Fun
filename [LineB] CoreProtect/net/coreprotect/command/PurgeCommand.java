/*     */ package net.coreprotect.command;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.ResultSetMetaData;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.coreprotect.CoreProtect;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.database.Database;
/*     */ import net.coreprotect.model.Config;
/*     */ import net.coreprotect.patch.Patch;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ 
/*     */ public class PurgeCommand extends net.coreprotect.consumer.Consumer
/*     */ {
/*     */   protected static void runCommand(final CommandSender player, boolean permission, String[] args)
/*     */   {
/*  25 */     int resultc = args.length;
/*  26 */     int seconds = CommandHandler.parseTime(args);
/*  27 */     if (Config.converter_running == true) {
/*  28 */       player.sendMessage("§3CoreProtect §f- Upgrade in progress. Please try again later.");
/*  29 */       return;
/*     */     }
/*  31 */     if (Config.purge_running == true) {
/*  32 */       player.sendMessage("§3CoreProtect §f- Purge in progress. Please try again later.");
/*  33 */       return;
/*     */     }
/*  35 */     if (permission != true) {
/*  36 */       player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/*  37 */       return;
/*     */     }
/*  39 */     if (resultc <= 1) {
/*  40 */       player.sendMessage("§3CoreProtect §f- Please use \"/co purge t:<time>\".");
/*  41 */       return;
/*     */     }
/*  43 */     if (seconds <= 0) {
/*  44 */       player.sendMessage("§3CoreProtect §f- Please use \"/co purge t:<time>\".");
/*  45 */       return;
/*     */     }
/*  47 */     if (((player instanceof Player)) && (seconds < 2592000)) {
/*  48 */       player.sendMessage("§3CoreProtect §f- You can only purge data older than 30 days.");
/*  49 */       return;
/*     */     }
/*  51 */     if (seconds < 86400) {
/*  52 */       player.sendMessage("§3CoreProtect §f- You can only purge data older than 24 hours.");
/*  53 */       return;
/*     */     }
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 326 */     Runnable runnable = new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/*  61 */           int timestamp = (int)(System.currentTimeMillis() / 1000L);
/*  62 */           int ptime = timestamp - this.val$seconds;
/*  63 */           long removed = 0L;
/*     */           
/*  65 */           Connection connection = null;
/*  66 */           for (int i = 0; i <= 5; i++) {
/*  67 */             connection = Database.getConnection(false);
/*  68 */             if (connection != null) {
/*     */               break;
/*     */             }
/*  71 */             Thread.sleep(1000L);
/*     */           }
/*     */           
/*  74 */           if (connection == null) {
/*  75 */             Functions.messageOwnerAndUser(player, "Database busy. Please try again later.");
/*  76 */             return;
/*     */           }
/*     */           
/*  79 */           Functions.messageOwnerAndUser(player, "Data purge started. This may take some time.");
/*  80 */           Functions.messageOwnerAndUser(player, "Do not restart your server until completed.");
/*     */           
/*  82 */           Config.purge_running = true;
/*  83 */           while (!PurgeCommand.pause_success) {
/*  84 */             Thread.sleep(1L);
/*     */           }
/*  86 */           net.coreprotect.consumer.Consumer.is_paused = true;
/*     */           
/*  88 */           String query = "";
/*  89 */           PreparedStatement preparedStmt = null;
/*  90 */           boolean abort = false;
/*  91 */           String purge_prefix = "tmp_" + Config.prefix;
/*     */           
/*  93 */           if (((Integer)Config.config.get("use-mysql")).intValue() == 0) {
/*  94 */             query = "ATTACH DATABASE '" + Config.sqlite + ".tmp' AS tmp_db";
/*  95 */             preparedStmt = connection.prepareStatement(query);
/*  96 */             preparedStmt.execute();
/*  97 */             preparedStmt.close();
/*  98 */             purge_prefix = "tmp_db." + Config.prefix;
/*     */           }
/*     */           
/* 101 */           String[] version_split = CoreProtect.getInstance().getDescription().getVersion().split("\\.");
/* 102 */           Integer[] current_version = { Integer.valueOf(Integer.parseInt(version_split[0])), Integer.valueOf(Integer.parseInt(version_split[1])), Integer.valueOf(Integer.parseInt(version_split[2])) };
/* 103 */           Integer[] last_version = Patch.getLastVersion(connection);
/*     */           
/* 105 */           boolean newVersion = Functions.newVersion(last_version, current_version);
/* 106 */           if (newVersion == true) {
/* 107 */             Functions.messageOwnerAndUser(player, "Purge failed. Please try again later.");
/* 108 */             net.coreprotect.consumer.Consumer.is_paused = false;
/* 109 */             Config.purge_running = false;
/* 110 */             return;
/*     */           }
/*     */           
/* 113 */           for (String table : Config.databaseTables) {
/*     */             try {
/* 115 */               query = "DROP TABLE IF EXISTS " + purge_prefix + table + "";
/* 116 */               preparedStmt = connection.prepareStatement(query);
/* 117 */               preparedStmt.execute();
/* 118 */               preparedStmt.close();
/*     */             }
/*     */             catch (Exception e) {
/* 121 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */           
/* 125 */           Functions.createDatabaseTables(purge_prefix, true);
/*     */           
/* 127 */           List<String> purge_tables = Arrays.asList(new String[] { "sign", "container", "skull", "session", "chat", "command", "block" });
/* 128 */           for (String table : Config.databaseTables) {
/* 129 */             String tableName = table.replaceAll("_", " ");
/* 130 */             Functions.messageOwnerAndUser(player, "Processing " + tableName + " data...");
/*     */             
/* 132 */             String columns = "";
/* 133 */             ResultSet rs = connection.createStatement().executeQuery("SELECT * FROM " + purge_prefix + table);
/* 134 */             ResultSetMetaData resultSetMetaData = rs.getMetaData();
/* 135 */             int columnCount = resultSetMetaData.getColumnCount();
/* 136 */             for (int i = 1; i <= columnCount; i++) {
/* 137 */               String name = resultSetMetaData.getColumnName(i);
/* 138 */               if (columns.length() == 0) {
/* 139 */                 columns = name;
/*     */               }
/*     */               else {
/* 142 */                 columns = columns + "," + name;
/*     */               }
/*     */             }
/* 145 */             rs.close();
/*     */             
/* 147 */             boolean error = false;
/*     */             try {
/* 149 */               String time_limit = "";
/* 150 */               if (purge_tables.contains(table)) {
/* 151 */                 time_limit = " WHERE time >= '" + ptime + "'";
/*     */               }
/* 153 */               query = "INSERT INTO " + purge_prefix + table + " SELECT " + columns + " FROM " + Config.prefix + table + time_limit;
/* 154 */               preparedStmt = connection.prepareStatement(query);
/* 155 */               preparedStmt.execute();
/* 156 */               preparedStmt.close();
/*     */             }
/*     */             catch (Exception e) {
/* 159 */               error = true;
/* 160 */               e.printStackTrace();
/*     */             }
/*     */             
/* 163 */             if (error) {
/* 164 */               Functions.messageOwnerAndUser(player, "Unable to process " + tableName + " data!");
/* 165 */               Functions.messageOwnerAndUser(player, "Attempting to repair. This may take some time...");
/*     */               try
/*     */               {
/* 168 */                 if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/* 169 */                   query = "TRUNCATE TABLE " + purge_prefix + table;
/*     */                 }
/*     */                 else {
/* 172 */                   query = "DELETE FROM " + purge_prefix + table;
/*     */                 }
/* 174 */                 preparedStmt = connection.prepareStatement(query);
/* 175 */                 preparedStmt.execute();
/* 176 */                 preparedStmt.close();
/*     */               }
/*     */               catch (Exception e) {
/* 179 */                 e.printStackTrace();
/*     */               }
/*     */               try
/*     */               {
/* 183 */                 query = "REINDEX " + Config.prefix + table;
/* 184 */                 if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/* 185 */                   query = "OPTIMIZE TABLE " + Config.prefix + table;
/*     */                 }
/* 187 */                 preparedStmt = connection.prepareStatement(query);
/* 188 */                 preparedStmt.execute();
/* 189 */                 preparedStmt.close();
/*     */               }
/*     */               catch (Exception e) {
/* 192 */                 e.printStackTrace();
/*     */               }
/*     */               try
/*     */               {
/* 196 */                 String index = " USE INDEX ()";
/* 197 */                 if (((Integer)Config.config.get("use-mysql")).intValue() == 0) {
/* 198 */                   index = " NOT INDEXED";
/*     */                 }
/* 200 */                 query = "INSERT INTO " + purge_prefix + table + " SELECT " + columns + " FROM " + Config.prefix + table + index;
/* 201 */                 preparedStmt = connection.prepareStatement(query);
/* 202 */                 preparedStmt.execute();
/* 203 */                 preparedStmt.close();
/*     */               }
/*     */               catch (Exception e) {
/* 206 */                 e.printStackTrace();
/* 207 */                 abort = true;
/* 208 */                 break;
/*     */               }
/*     */               
/* 211 */               if (purge_tables.contains(table)) {
/*     */                 try {
/* 213 */                   query = "DELETE FROM " + purge_prefix + table + " WHERE time < '" + ptime + "'";
/* 214 */                   preparedStmt = connection.prepareStatement(query);
/* 215 */                   preparedStmt.execute();
/* 216 */                   preparedStmt.close();
/*     */                 }
/*     */                 catch (Exception e) {
/* 219 */                   e.printStackTrace();
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/* 224 */             int old_count = 0;
/*     */             try {
/* 226 */               query = "SELECT COUNT(*) as count FROM " + Config.prefix + table + " LIMIT 0, 1";
/* 227 */               preparedStmt = connection.prepareStatement(query);
/* 228 */               ResultSet resultSet = preparedStmt.executeQuery();
/* 229 */               while (resultSet.next()) {
/* 230 */                 old_count = resultSet.getInt("count");
/*     */               }
/* 232 */               resultSet.close();
/* 233 */               preparedStmt.close();
/*     */             }
/*     */             catch (Exception e) {
/* 236 */               e.printStackTrace();
/*     */             }
/*     */             
/* 239 */             int new_count = 0;
/*     */             try {
/* 241 */               query = "SELECT COUNT(*) as count FROM " + purge_prefix + table + " LIMIT 0, 1";
/* 242 */               preparedStmt = connection.prepareStatement(query);
/* 243 */               ResultSet resultSet = preparedStmt.executeQuery();
/* 244 */               while (resultSet.next()) {
/* 245 */                 new_count = resultSet.getInt("count");
/*     */               }
/* 247 */               resultSet.close();
/* 248 */               preparedStmt.close();
/*     */             }
/*     */             catch (Exception e) {
/* 251 */               e.printStackTrace();
/*     */             }
/*     */             
/* 254 */             removed += old_count - new_count;
/*     */             
/* 256 */             if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/*     */               try {
/* 258 */                 query = "DROP TABLE IF EXISTS _" + purge_prefix + table;
/* 259 */                 preparedStmt = connection.prepareStatement(query);
/* 260 */                 preparedStmt.execute();
/* 261 */                 preparedStmt.close();
/*     */                 
/* 263 */                 query = "RENAME TABLE " + Config.prefix + table + " TO _" + purge_prefix + table + ", " + purge_prefix + table + " TO " + Config.prefix + table + ", _" + purge_prefix + table + " TO " + purge_prefix + table;
/* 264 */                 preparedStmt = connection.prepareStatement(query);
/* 265 */                 preparedStmt.execute();
/* 266 */                 preparedStmt.close();
/*     */                 
/* 268 */                 query = "DROP TABLE " + purge_prefix + table;
/* 269 */                 preparedStmt = connection.prepareStatement(query);
/* 270 */                 preparedStmt.execute();
/* 271 */                 preparedStmt.close();
/*     */               }
/*     */               catch (Exception e) {
/* 274 */                 e.printStackTrace();
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 279 */           if ((abort == true) && (((Integer)Config.config.get("use-mysql")).intValue() == 1)) {
/* 280 */             for (String table : Config.databaseTables) {
/*     */               try {
/* 282 */                 query = "DROP TABLE IF EXISTS tmp_" + Config.prefix + table + "";
/* 283 */                 preparedStmt = connection.prepareStatement(query);
/* 284 */                 preparedStmt.execute();
/* 285 */                 preparedStmt.close();
/*     */               }
/*     */               catch (Exception e) {
/* 288 */                 e.printStackTrace();
/*     */               }
/*     */             }
/*     */           }
/* 292 */           connection.close();
/*     */           
/* 294 */           if (abort) {
/* 295 */             if (((Integer)Config.config.get("use-mysql")).intValue() == 0) {
/* 296 */               new File(Config.sqlite + ".tmp").delete();
/*     */             }
/* 298 */             Config.loadDatabase();
/* 299 */             Functions.messageOwnerAndUser(player, "§cPurge failed. Database may be corrupt.");
/* 300 */             net.coreprotect.consumer.Consumer.is_paused = false;
/* 301 */             Config.purge_running = false;
/* 302 */             return;
/*     */           }
/*     */           
/* 305 */           if (((Integer)Config.config.get("use-mysql")).intValue() == 0) {
/* 306 */             new File(Config.sqlite).delete();
/* 307 */             new File(Config.sqlite + ".tmp").renameTo(new File(Config.sqlite));
/* 308 */             Functions.messageOwnerAndUser(player, "Indexing database. Please wait...");
/*     */           }
/*     */           
/* 311 */           Config.loadDatabase();
/*     */           
/* 313 */           Functions.messageOwnerAndUser(player, "Data purge successful.");
/* 314 */           Functions.messageOwnerAndUser(player, NumberFormat.getInstance().format(removed) + " row(s) of data deleted.");
/*     */         }
/*     */         catch (Exception e) {
/* 317 */           Functions.messageOwnerAndUser(player, "Purge failed. Please try again later.");
/* 318 */           e.printStackTrace();
/*     */         }
/*     */         
/* 321 */         net.coreprotect.consumer.Consumer.is_paused = false;
/* 322 */         Config.purge_running = false;
/*     */       }
/*     */       
/*     */ 
/* 326 */     };
/* 327 */     Thread thread = new Thread(runnable);
/* 328 */     thread.start();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\command\PurgeCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */