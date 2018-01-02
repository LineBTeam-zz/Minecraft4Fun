/*     */ package net.coreprotect.database;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.consumer.Queue;
/*     */ import net.coreprotect.model.BlockInfo;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.SkullType;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.Sign;
/*     */ import org.bukkit.block.Skull;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class Database extends Queue
/*     */ {
/*     */   public static void beginTransaction(Statement statement)
/*     */   {
/*     */     try
/*     */     {
/*  34 */       if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/*  35 */         statement.executeUpdate("START TRANSACTION");
/*     */       }
/*     */       else {
/*  38 */         statement.executeUpdate("BEGIN TRANSACTION");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  42 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void commitTransaction(Statement statement) {
/*     */     try {
/*  48 */       if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/*  49 */         statement.executeUpdate("COMMIT");
/*     */       }
/*     */       else {
/*  52 */         statement.executeUpdate("COMMIT TRANSACTION");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  56 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void containerBreakCheck(String user, Material type, Object container, Location location) {
/*  61 */     if ((BlockInfo.containers.contains(type)) && (!BlockInfo.shulker_boxes.contains(type)) && 
/*  62 */       (Functions.checkConfig(location.getWorld(), "item-transactions") == 1)) {
/*     */       try {
/*  64 */         ItemStack[] contents = Functions.getContainerContents(type, container, location);
/*  65 */         if (contents != null) {
/*  66 */           BlockState blockState = location.getBlock().getState();
/*  67 */           List<ItemStack[]> force_list = new ArrayList();
/*  68 */           force_list.add(Functions.get_container_state(contents));
/*  69 */           Config.force_containers.put(user.toLowerCase() + "." + blockState.getX() + "." + blockState.getY() + "." + blockState.getZ(), force_list);
/*  70 */           ItemStack[] containerState = Functions.get_container_state(contents);
/*  71 */           Queue.queueContainerBreak(user, blockState, type, containerState);
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/*  75 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static Connection getConnection(boolean force)
/*     */   {
/*  82 */     Connection connection = null;
/*     */     try {
/*  84 */       if ((!force) && ((Config.converter_running == true) || (Config.purge_running == true))) {
/*  85 */         return connection;
/*     */       }
/*  87 */       if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/*  88 */         String database = "jdbc:mysql://" + Config.host + ":" + Config.port + "/" + Config.database + "?useUnicode=true&characterEncoding=utf-8&connectTimeout=10000&useSSL=false";
/*  89 */         Class.forName(Config.driver).newInstance();
/*  90 */         connection = DriverManager.getConnection(database, Config.username, Config.password);
/*     */         
/*     */ 
/*  93 */         Statement statement = connection.createStatement();
/*  94 */         statement.executeUpdate("SET NAMES 'utf8'");
/*  95 */         statement.close();
/*     */       }
/*     */       else {
/*  98 */         long start_time = System.currentTimeMillis();
/*  99 */         while ((net.coreprotect.consumer.Consumer.is_paused == true) && (!force)) {
/* 100 */           Thread.sleep(1L);
/* 101 */           long pause_time = System.currentTimeMillis() - start_time;
/* 102 */           if (pause_time >= 250L) {
/* 103 */             return connection;
/*     */           }
/*     */         }
/* 106 */         String database = "jdbc:sqlite:" + Config.sqlite + "";
/* 107 */         Class.forName("org.sqlite.JDBC");
/* 108 */         connection = DriverManager.getConnection(database);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 112 */       e.printStackTrace();
/*     */     }
/* 114 */     return connection;
/*     */   }
/*     */   
/*     */   public static List<Object> getEntityData(Statement statement, BlockState block, String query) {
/* 118 */     List<Object> result = new ArrayList();
/*     */     try {
/* 120 */       ResultSet rs = statement.executeQuery(query);
/* 121 */       while (rs.next()) {
/* 122 */         byte[] data = rs.getBytes("data");
/* 123 */         ByteArrayInputStream bais = new ByteArrayInputStream(data);
/* 124 */         ObjectInputStream ins = new ObjectInputStream(bais);
/*     */         
/* 126 */         List<Object> input = (List)ins.readObject();
/* 127 */         result = input;
/*     */       }
/* 129 */       rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 132 */       e.printStackTrace();
/*     */     }
/* 134 */     return result;
/*     */   }
/*     */   
/*     */   public static void getSignData(Statement statement, BlockState block, String query) {
/*     */     try {
/* 139 */       if (!(block instanceof Sign)) {
/* 140 */         return;
/*     */       }
/* 142 */       Sign sign = (Sign)block;
/* 143 */       ResultSet rs = statement.executeQuery(query);
/* 144 */       while (rs.next()) {
/* 145 */         String line1 = rs.getString("line_1");
/* 146 */         String line2 = rs.getString("line_2");
/* 147 */         String line3 = rs.getString("line_3");
/* 148 */         String line4 = rs.getString("line_4");
/* 149 */         sign.setLine(0, line1);
/* 150 */         sign.setLine(1, line2);
/* 151 */         sign.setLine(2, line3);
/* 152 */         sign.setLine(3, line4);
/*     */       }
/* 154 */       rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 157 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void getSkullData(Statement statement, BlockState block, String query) {
/*     */     try {
/* 163 */       if (!(block instanceof Skull)) {
/* 164 */         return;
/*     */       }
/* 166 */       Skull skull = (Skull)block;
/* 167 */       ResultSet rs = statement.executeQuery(query);
/* 168 */       while (rs.next()) {
/* 169 */         int type = rs.getInt("type");
/* 170 */         int data = rs.getInt("data");
/* 171 */         int rotation = rs.getInt("rotation");
/* 172 */         String owner = rs.getString("owner");
/* 173 */         SkullType skulltype = Functions.getSkullType(type);
/* 174 */         BlockFace skullrotation = Functions.getBlockFace(rotation);
/* 175 */         skull = (Skull)Functions.setRawData(skull, (byte)data);
/* 176 */         skull.setSkullType(skulltype);
/* 177 */         skull.setRotation(skullrotation);
/* 178 */         if ((owner != null) && (owner.length() > 0)) {
/* 179 */           skull.setOwner(owner);
/*     */         }
/*     */       }
/* 182 */       rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 185 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void insertBlock(PreparedStatement preparedStmt, int time, int id, int wid, int x, int y, int z, int type, int data, List<Object> meta, int action, int rolled_back) {
/*     */     try {
/* 191 */       byte[] byte_data = null;
/* 192 */       if (meta != null) {
/* 193 */         byte_data = Functions.convertByteData(meta);
/*     */       }
/* 195 */       preparedStmt.setInt(1, time);
/* 196 */       preparedStmt.setInt(2, id);
/* 197 */       preparedStmt.setInt(3, wid);
/* 198 */       preparedStmt.setInt(4, x);
/* 199 */       preparedStmt.setInt(5, y);
/* 200 */       preparedStmt.setInt(6, z);
/* 201 */       preparedStmt.setInt(7, type);
/* 202 */       preparedStmt.setInt(8, data);
/* 203 */       preparedStmt.setObject(9, byte_data);
/* 204 */       preparedStmt.setInt(10, action);
/* 205 */       preparedStmt.setInt(11, rolled_back);
/* 206 */       preparedStmt.executeUpdate();
/* 207 */       preparedStmt.clearParameters();
/*     */     }
/*     */     catch (Exception e) {
/* 210 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void insertChat(PreparedStatement preparedStmt, int time, int user, String message) {
/*     */     try {
/* 216 */       preparedStmt.setInt(1, time);
/* 217 */       preparedStmt.setInt(2, user);
/* 218 */       preparedStmt.setString(3, message);
/* 219 */       preparedStmt.executeUpdate();
/* 220 */       preparedStmt.clearParameters();
/*     */     }
/*     */     catch (Exception e) {
/* 223 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void insertCommand(PreparedStatement preparedStmt, int time, int user, String message) {
/*     */     try {
/* 229 */       preparedStmt.setInt(1, time);
/* 230 */       preparedStmt.setInt(2, user);
/* 231 */       preparedStmt.setString(3, message);
/* 232 */       preparedStmt.executeUpdate();
/* 233 */       preparedStmt.clearParameters();
/*     */     }
/*     */     catch (Exception e) {
/* 236 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void insertContainer(PreparedStatement preparedStmt, int time, int id, int wid, int x, int y, int z, int type, int data, int amount, List<List<Map<String, Object>>> metadata, int action, int rolled_back) {
/*     */     try {
/* 242 */       byte[] byte_data = Functions.convertByteData(metadata);
/* 243 */       preparedStmt.setInt(1, time);
/* 244 */       preparedStmt.setInt(2, id);
/* 245 */       preparedStmt.setInt(3, wid);
/* 246 */       preparedStmt.setInt(4, x);
/* 247 */       preparedStmt.setInt(5, y);
/* 248 */       preparedStmt.setInt(6, z);
/* 249 */       preparedStmt.setInt(7, type);
/* 250 */       preparedStmt.setInt(8, data);
/* 251 */       preparedStmt.setInt(9, amount);
/* 252 */       preparedStmt.setObject(10, byte_data);
/* 253 */       preparedStmt.setInt(11, action);
/* 254 */       preparedStmt.setInt(12, rolled_back);
/* 255 */       preparedStmt.executeUpdate();
/* 256 */       preparedStmt.clearParameters();
/*     */     }
/*     */     catch (Exception e) {
/* 259 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void insertEntity(PreparedStatement preparedStmt, int time, List<Object> data) {
/*     */     try {
/* 265 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/* 266 */       ObjectOutputStream oos = new ObjectOutputStream(bos);
/* 267 */       oos.writeObject(data);
/* 268 */       oos.flush();
/* 269 */       oos.close();
/* 270 */       bos.close();
/* 271 */       byte[] byte_data = bos.toByteArray();
/* 272 */       preparedStmt.setInt(1, time);
/* 273 */       preparedStmt.setObject(2, byte_data);
/* 274 */       preparedStmt.executeUpdate();
/* 275 */       preparedStmt.clearParameters();
/*     */     }
/*     */     catch (Exception e) {
/* 278 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void insertMaterial(PreparedStatement preparedStmt, int id, String name) {
/*     */     try {
/* 284 */       preparedStmt.setInt(1, id);
/* 285 */       preparedStmt.setString(2, name);
/* 286 */       preparedStmt.executeUpdate();
/* 287 */       preparedStmt.clearParameters();
/*     */     }
/*     */     catch (Exception e) {
/* 290 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void insertSession(PreparedStatement preparedStmt, int time, int user, int wid, int x, int y, int z, int action) {
/*     */     try {
/* 296 */       preparedStmt.setInt(1, time);
/* 297 */       preparedStmt.setInt(2, user);
/* 298 */       preparedStmt.setInt(3, wid);
/* 299 */       preparedStmt.setInt(4, x);
/* 300 */       preparedStmt.setInt(5, y);
/* 301 */       preparedStmt.setInt(6, z);
/* 302 */       preparedStmt.setInt(7, action);
/* 303 */       preparedStmt.executeUpdate();
/* 304 */       preparedStmt.clearParameters();
/*     */     }
/*     */     catch (Exception e) {
/* 307 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void insertSign(PreparedStatement preparedStmt, int time, int id, int wid, int x, int y, int z, String line1, String line2, String line3, String line4) {
/*     */     try {
/* 313 */       preparedStmt.setInt(1, time);
/* 314 */       preparedStmt.setInt(2, id);
/* 315 */       preparedStmt.setInt(3, wid);
/* 316 */       preparedStmt.setInt(4, x);
/* 317 */       preparedStmt.setInt(5, y);
/* 318 */       preparedStmt.setInt(6, z);
/* 319 */       preparedStmt.setString(7, line1);
/* 320 */       preparedStmt.setString(8, line2);
/* 321 */       preparedStmt.setString(9, line3);
/* 322 */       preparedStmt.setString(10, line4);
/* 323 */       preparedStmt.executeUpdate();
/* 324 */       preparedStmt.clearParameters();
/*     */     }
/*     */     catch (Exception e) {
/* 327 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void insertSkull(PreparedStatement preparedStmt, int time, int type, int data, int rotation, String owner) {
/*     */     try {
/* 333 */       preparedStmt.setInt(1, time);
/* 334 */       preparedStmt.setInt(2, type);
/* 335 */       preparedStmt.setInt(3, data);
/* 336 */       preparedStmt.setInt(4, rotation);
/* 337 */       preparedStmt.setString(5, owner);
/* 338 */       preparedStmt.executeUpdate();
/* 339 */       preparedStmt.clearParameters();
/*     */     }
/*     */     catch (Exception e) {
/* 342 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private static int insertUser(Connection connection, String user) {
/* 347 */     int id = -1;
/*     */     try {
/* 349 */       int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 350 */       PreparedStatement preparedStmt = connection.prepareStatement("INSERT INTO " + Config.prefix + "user (time, user) VALUES (?, ?)", 1);
/* 351 */       preparedStmt.setInt(1, unixtimestamp);
/* 352 */       preparedStmt.setString(2, user);
/*     */       
/* 354 */       preparedStmt.executeUpdate();
/* 355 */       ResultSet keys = preparedStmt.getGeneratedKeys();
/* 356 */       keys.next();
/* 357 */       id = keys.getInt(1);
/* 358 */       keys.close();
/* 359 */       preparedStmt.close();
/*     */     }
/*     */     catch (Exception e) {
/* 362 */       e.printStackTrace();
/*     */     }
/* 364 */     return id;
/*     */   }
/*     */   
/*     */   public static void insertWorld(PreparedStatement preparedStmt, int id, String world) {
/*     */     try {
/* 369 */       preparedStmt.setInt(1, id);
/* 370 */       preparedStmt.setString(2, world);
/* 371 */       preparedStmt.executeUpdate();
/* 372 */       preparedStmt.clearParameters();
/*     */     }
/*     */     catch (Exception e) {
/* 375 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static int loadUserID(Connection connection, String user, String uuid)
/*     */   {
/* 381 */     int id = -1;
/*     */     try {
/* 383 */       String where = "user LIKE ?";
/* 384 */       if (uuid != null) {
/* 385 */         where = where + " OR uuid = ?";
/*     */       }
/*     */       
/* 388 */       String query = "SELECT rowid as id, uuid FROM " + Config.prefix + "user WHERE " + where + " ORDER BY rowid ASC LIMIT 0, 1";
/* 389 */       PreparedStatement preparedStmt = connection.prepareStatement(query);
/* 390 */       preparedStmt.setString(1, user);
/* 391 */       if (uuid != null) {
/* 392 */         preparedStmt.setString(2, uuid);
/*     */       }
/* 394 */       ResultSet rs = preparedStmt.executeQuery();
/* 395 */       while (rs.next()) {
/* 396 */         id = rs.getInt("id");
/* 397 */         uuid = rs.getString("uuid");
/*     */       }
/* 399 */       rs.close();
/* 400 */       preparedStmt.close();
/* 401 */       if (id == -1)
/*     */       {
/* 403 */         id = insertUser(connection, user);
/*     */       }
/* 405 */       Config.player_id_cache.put(user.toLowerCase(), Integer.valueOf(id));
/* 406 */       Config.player_id_cache_reversed.put(Integer.valueOf(id), user);
/* 407 */       if (uuid != null) {
/* 408 */         Config.uuid_cache.put(user.toLowerCase(), uuid);
/* 409 */         Config.uuid_cache_reversed.put(uuid, user);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 413 */       e.printStackTrace();
/*     */     }
/* 415 */     return id;
/*     */   }
/*     */   
/*     */   public static String loadUserName(Connection connection, int id)
/*     */   {
/* 420 */     String user = "";
/* 421 */     String uuid = null;
/*     */     try {
/* 423 */       Statement statement = connection.createStatement();
/* 424 */       String query = "SELECT user, uuid FROM " + Config.prefix + "user WHERE rowid='" + id + "' LIMIT 0, 1";
/* 425 */       ResultSet rs = statement.executeQuery(query);
/* 426 */       while (rs.next()) {
/* 427 */         user = rs.getString("user");
/* 428 */         uuid = rs.getString("uuid");
/*     */       }
/* 430 */       if (user.length() == 0) {
/* 431 */         return user;
/*     */       }
/* 433 */       Config.player_id_cache.put(user.toLowerCase(), Integer.valueOf(id));
/* 434 */       Config.player_id_cache_reversed.put(Integer.valueOf(id), user);
/* 435 */       if (uuid != null) {
/* 436 */         Config.uuid_cache.put(user.toLowerCase(), uuid);
/* 437 */         Config.uuid_cache_reversed.put(uuid, user);
/*     */       }
/* 439 */       rs.close();
/* 440 */       statement.close();
/*     */     }
/*     */     catch (Exception e) {
/* 443 */       e.printStackTrace();
/*     */     }
/* 445 */     return user;
/*     */   }
/*     */   
/*     */   public static void performUpdate(Statement statement, int id, int action, int table) {
/*     */     try {
/* 450 */       int rolled_back = 1;
/* 451 */       if (action == 1) {
/* 452 */         rolled_back = 0;
/*     */       }
/* 454 */       if (table == 1) {
/* 455 */         statement.executeUpdate("UPDATE " + Config.prefix + "container SET rolled_back='" + rolled_back + "' WHERE rowid='" + id + "'");
/*     */       }
/*     */       else {
/* 458 */         statement.executeUpdate("UPDATE " + Config.prefix + "block SET rolled_back='" + rolled_back + "' WHERE rowid='" + id + "'");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 462 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static PreparedStatement prepareStatement(Connection connection, int type, boolean keys) {
/* 467 */     PreparedStatement prepared_statement = null;
/*     */     try {
/* 469 */       String query_0 = "INSERT INTO " + Config.prefix + "sign (time, user, wid, x, y, z, line_1, line_2, line_3, line_4) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/* 470 */       String query_1 = "INSERT INTO " + Config.prefix + "block (time, user, wid, x, y, z, type, data, meta, action, rolled_back) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/* 471 */       String query_2 = "INSERT INTO " + Config.prefix + "skull (time, type, data, rotation, owner) VALUES (?, ?, ?, ?, ?)";
/* 472 */       String query_3 = "INSERT INTO " + Config.prefix + "container (time, user, wid, x, y, z, type, data, amount, metadata, action, rolled_back) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
/* 473 */       String query_4 = "INSERT INTO " + Config.prefix + "world (id, world) VALUES (?, ?)";
/* 474 */       String query_5 = "INSERT INTO " + Config.prefix + "chat (time, user, message) VALUES (?, ?, ?)";
/* 475 */       String query_6 = "INSERT INTO " + Config.prefix + "command (time, user, message) VALUES (?, ?, ?)";
/* 476 */       String query_7 = "INSERT INTO " + Config.prefix + "session (time, user, wid, x, y, z, action) VALUES (?, ?, ?, ?, ?, ?, ?)";
/* 477 */       String query_8 = "INSERT INTO " + Config.prefix + "entity (time, data) VALUES (?, ?)";
/* 478 */       String query_9 = "INSERT INTO " + Config.prefix + "material_map (id, material) VALUES (?, ?)";
/* 479 */       String query_10 = "INSERT INTO " + Config.prefix + "art_map (id, art) VALUES (?, ?)";
/* 480 */       String query_11 = "INSERT INTO " + Config.prefix + "entity_map (id, entity) VALUES (?, ?)";
/* 481 */       switch (type) {
/*     */       case 0: 
/* 483 */         prepared_statement = prepareStatement(connection, query_0, keys);
/* 484 */         break;
/*     */       case 1: 
/* 486 */         prepared_statement = prepareStatement(connection, query_1, keys);
/* 487 */         break;
/*     */       case 2: 
/* 489 */         prepared_statement = prepareStatement(connection, query_2, keys);
/* 490 */         break;
/*     */       case 3: 
/* 492 */         prepared_statement = prepareStatement(connection, query_3, keys);
/* 493 */         break;
/*     */       case 4: 
/* 495 */         prepared_statement = prepareStatement(connection, query_4, keys);
/* 496 */         break;
/*     */       case 5: 
/* 498 */         prepared_statement = prepareStatement(connection, query_5, keys);
/* 499 */         break;
/*     */       case 6: 
/* 501 */         prepared_statement = prepareStatement(connection, query_6, keys);
/* 502 */         break;
/*     */       case 7: 
/* 504 */         prepared_statement = prepareStatement(connection, query_7, keys);
/* 505 */         break;
/*     */       case 8: 
/* 507 */         prepared_statement = prepareStatement(connection, query_8, keys);
/* 508 */         break;
/*     */       case 9: 
/* 510 */         prepared_statement = prepareStatement(connection, query_9, keys);
/* 511 */         break;
/*     */       case 10: 
/* 513 */         prepared_statement = prepareStatement(connection, query_10, keys);
/* 514 */         break;
/*     */       case 11: 
/* 516 */         prepared_statement = prepareStatement(connection, query_11, keys);
/*     */       }
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 521 */       e.printStackTrace();
/*     */     }
/* 523 */     return prepared_statement;
/*     */   }
/*     */   
/*     */   private static PreparedStatement prepareStatement(Connection connection, String query, boolean keys) {
/* 527 */     PreparedStatement prepared_statement = null;
/*     */     try {
/* 529 */       if (keys == true) {
/* 530 */         prepared_statement = connection.prepareStatement(query, 1);
/*     */       }
/*     */       else {
/* 533 */         prepared_statement = connection.prepareStatement(query);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 537 */       e.printStackTrace();
/*     */     }
/* 539 */     return prepared_statement;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\database\Database.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */