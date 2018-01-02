/*     */ package net.coreprotect.consumer;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.database.Database;
/*     */ import net.coreprotect.database.Logger;
/*     */ import net.coreprotect.database.Lookup;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.Skull;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class Process
/*     */ {
/*     */   public static void processConsumer(int process_id)
/*     */   {
/*     */     try
/*     */     {
/*  28 */       Connection connection = Database.getConnection(false);
/*  29 */       if (connection == null) {
/*  30 */         return;
/*     */       }
/*  32 */       Consumer.is_paused = true;
/*  33 */       Statement statement = connection.createStatement();
/*  34 */       ArrayList<Object[]> consumer_data = (ArrayList)Consumer.consumer.get(Integer.valueOf(process_id));
/*  35 */       Map<Integer, String[]> users = (Map)Consumer.consumer_users.get(Integer.valueOf(process_id));
/*  36 */       Map<Integer, Object> blocks = (Map)Consumer.consumer_object.get(Integer.valueOf(process_id));
/*     */       
/*     */ 
/*  39 */       Database.beginTransaction(statement);
/*  40 */       for (Map.Entry<Integer, String[]> entry : users.entrySet()) {
/*  41 */         String[] user_data = (String[])entry.getValue();
/*  42 */         String user = user_data[0];
/*  43 */         String uuid = user_data[1];
/*  44 */         if (Config.player_id_cache.get(user.toLowerCase()) == null) {
/*  45 */           Database.loadUserID(connection, user, uuid);
/*     */         }
/*     */       }
/*  48 */       Database.commitTransaction(statement);
/*     */       
/*     */ 
/*  51 */       PreparedStatement preparedStmt_signs = Database.prepareStatement(connection, 0, false);
/*  52 */       PreparedStatement preparedStmt_blocks = Database.prepareStatement(connection, 1, false);
/*  53 */       PreparedStatement preparedStmt_skulls = Database.prepareStatement(connection, 2, true);
/*  54 */       PreparedStatement preparedStmt_containers = Database.prepareStatement(connection, 3, false);
/*  55 */       PreparedStatement preparedStmt_worlds = Database.prepareStatement(connection, 4, false);
/*  56 */       PreparedStatement preparedStmt_chat = Database.prepareStatement(connection, 5, false);
/*  57 */       PreparedStatement preparedStmt_command = Database.prepareStatement(connection, 6, false);
/*  58 */       PreparedStatement preparedStmt_session = Database.prepareStatement(connection, 7, false);
/*  59 */       PreparedStatement preparedStmt_entities = Database.prepareStatement(connection, 8, true);
/*  60 */       PreparedStatement preparedStmt_materials = Database.prepareStatement(connection, 9, false);
/*  61 */       PreparedStatement preparedStmt_art = Database.prepareStatement(connection, 10, false);
/*  62 */       PreparedStatement preparedStmt_entity = Database.prepareStatement(connection, 11, false);
/*     */       
/*     */ 
/*  65 */       Database.beginTransaction(statement);
/*  66 */       for (int i = 0; i < consumer_data.size(); i++) {
/*  67 */         Object[] data = (Object[])consumer_data.get(i);
/*  68 */         if (data != null) {
/*  69 */           int id = ((Integer)data[0]).intValue();
/*  70 */           int action = ((Integer)data[1]).intValue();
/*  71 */           Material block_type = (Material)data[2];
/*  72 */           int block_data = ((Integer)data[3]).intValue();
/*  73 */           Material replace_type = (Material)data[4];
/*  74 */           int replace_data = ((Integer)data[5]).intValue();
/*  75 */           int force_data = ((Integer)data[6]).intValue();
/*     */           
/*  77 */           if ((users.get(Integer.valueOf(id)) != null) && (blocks.get(Integer.valueOf(id)) != null)) {
/*  78 */             String user = ((String[])users.get(Integer.valueOf(id)))[0];
/*  79 */             Object object = blocks.get(Integer.valueOf(id));
/*     */             try
/*     */             {
/*  82 */               switch (action) {
/*     */               case 0: 
/*  84 */                 processBlockBreak(preparedStmt_blocks, preparedStmt_skulls, process_id, id, block_type, block_data, replace_type, force_data, user, object);
/*  85 */                 break;
/*     */               case 1: 
/*  87 */                 processBlockPlace(preparedStmt_blocks, preparedStmt_skulls, block_type, block_data, replace_type, replace_data, force_data, user, object);
/*  88 */                 break;
/*     */               case 2: 
/*  90 */                 processSignText(preparedStmt_signs, process_id, id, force_data, user, object);
/*  91 */                 break;
/*     */               case 3: 
/*  93 */                 processContainerBreak(preparedStmt_containers, process_id, id, user, object);
/*  94 */                 break;
/*     */               case 4: 
/*  96 */                 processPlayerInteraction(preparedStmt_blocks, user, object);
/*  97 */                 break;
/*     */               case 5: 
/*  99 */                 processContainerTransaction(preparedStmt_containers, process_id, id, force_data, user, object);
/* 100 */                 break;
/*     */               case 6: 
/* 102 */                 processStructureGrowth(statement, preparedStmt_blocks, process_id, id, user, object);
/* 103 */                 break;
/*     */               case 7: 
/* 105 */                 processRollbackUpdate(statement, process_id, id, force_data, 0);
/* 106 */                 break;
/*     */               case 8: 
/* 108 */                 processRollbackUpdate(statement, process_id, id, force_data, 1);
/* 109 */                 break;
/*     */               case 9: 
/* 111 */                 processWorldInsert(preparedStmt_worlds, user, force_data);
/* 112 */                 break;
/*     */               case 10: 
/* 114 */                 processSignUpdate(statement, object, block_data, force_data);
/* 115 */                 break;
/*     */               case 11: 
/* 117 */                 processSkullUpdate(statement, object, force_data);
/* 118 */                 break;
/*     */               case 12: 
/* 120 */                 processPlayerChat(preparedStmt_chat, process_id, id, force_data, user);
/* 121 */                 break;
/*     */               case 13: 
/* 123 */                 processPlayerCommand(preparedStmt_command, process_id, id, force_data, user);
/* 124 */                 break;
/*     */               case 14: 
/* 126 */                 processPlayerLogin(connection, preparedStmt_session, process_id, id, object, block_data, replace_data, force_data, user);
/* 127 */                 break;
/*     */               case 15: 
/* 129 */                 processPlayerLogout(preparedStmt_session, object, force_data, user);
/* 130 */                 break;
/*     */               case 16: 
/* 132 */                 processEntityKill(preparedStmt_blocks, preparedStmt_entities, process_id, id, object, user);
/* 133 */                 break;
/*     */               case 17: 
/* 135 */                 processEntitySpawn(statement, object, force_data);
/* 136 */                 break;
/*     */               case 18: 
/* 138 */                 processHangingRemove(object, force_data);
/* 139 */                 break;
/*     */               case 19: 
/* 141 */                 processHangingSpawn(object, block_type, block_data, force_data);
/* 142 */                 break;
/*     */               case 20: 
/* 144 */                 processNaturalBlockBreak(statement, preparedStmt_blocks, process_id, id, user, object, block_type, block_data);
/* 145 */                 break;
/*     */               case 21: 
/* 147 */                 processMaterialInsert(preparedStmt_materials, user, force_data);
/* 148 */                 break;
/*     */               case 22: 
/* 150 */                 processMaterialInsert(preparedStmt_art, user, force_data);
/* 151 */                 break;
/*     */               case 23: 
/* 153 */                 processMaterialInsert(preparedStmt_entity, user, force_data);
/* 154 */                 break;
/*     */               case 24: 
/* 156 */                 processPlayerKill(preparedStmt_blocks, id, object, user);
/*     */               }
/*     */             }
/*     */             catch (Exception e)
/*     */             {
/* 161 */               e.printStackTrace();
/*     */             }
/* 163 */             users.remove(Integer.valueOf(id));
/* 164 */             blocks.remove(Integer.valueOf(id));
/*     */           }
/*     */         }
/*     */       }
/* 168 */       Database.commitTransaction(statement);
/*     */       
/* 170 */       consumer_data.clear();
/* 171 */       preparedStmt_signs.close();
/* 172 */       preparedStmt_blocks.close();
/* 173 */       preparedStmt_skulls.close();
/* 174 */       preparedStmt_containers.close();
/* 175 */       preparedStmt_worlds.close();
/* 176 */       preparedStmt_chat.close();
/* 177 */       preparedStmt_command.close();
/* 178 */       preparedStmt_session.close();
/* 179 */       preparedStmt_entities.close();
/* 180 */       preparedStmt_materials.close();
/* 181 */       preparedStmt_art.close();
/* 182 */       preparedStmt_entity.close();
/* 183 */       statement.close();
/* 184 */       connection.close();
/*     */     }
/*     */     catch (Exception e) {
/* 187 */       e.printStackTrace();
/*     */     }
/*     */     
/* 190 */     Consumer.is_paused = false;
/*     */   }
/*     */   
/*     */   private static void processBlockBreak(PreparedStatement preparedStmt, PreparedStatement preparedStmt_skulls, int process_id, int id, Material block_type, int block_data, Material replace_type, int force_data, String user, Object object) {
/* 194 */     if ((object instanceof BlockState)) {
/* 195 */       BlockState block = (BlockState)object;
/* 196 */       List<Object> meta = Functions.processMeta(block);
/* 197 */       if ((block instanceof Skull)) {
/* 198 */         Logger.log_skull_break(preparedStmt, preparedStmt_skulls, user, block);
/*     */       }
/*     */       else {
/* 201 */         Logger.log_break(preparedStmt, user, block.getLocation(), Functions.block_id(block_type), block_data, meta);
/* 202 */         if ((force_data == 5) && 
/* 203 */           ((block_type.equals(Material.WOODEN_DOOR)) || (block_type.equals(Material.SPRUCE_DOOR)) || (block_type.equals(Material.BIRCH_DOOR)) || (block_type.equals(Material.JUNGLE_DOOR)) || (block_type.equals(Material.ACACIA_DOOR)) || (block_type.equals(Material.DARK_OAK_DOOR)) || (block_type.equals(Material.IRON_DOOR_BLOCK))) && (!replace_type.equals(Material.WOODEN_DOOR)) && (!replace_type.equals(Material.SPRUCE_DOOR)) && (!replace_type.equals(Material.BIRCH_DOOR)) && (!replace_type.equals(Material.JUNGLE_DOOR)) && (!replace_type.equals(Material.ACACIA_DOOR)) && (!replace_type.equals(Material.DARK_OAK_DOOR)) && (!replace_type.equals(Material.IRON_DOOR_BLOCK))) {
/* 204 */           int d = block_data;
/* 205 */           if (block_data < 9) {
/* 206 */             d = block_data + 8;
/*     */           }
/* 208 */           Location location = block.getLocation();
/* 209 */           location.setY(location.getY() + 1.0D);
/* 210 */           Logger.log_break(preparedStmt, user, location, Functions.block_id(block_type), d, null);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processBlockPlace(PreparedStatement preparedStmt, PreparedStatement preparedStmt_skulls, Material block_type, int block_data, Material replace_type, int replace_data, int force_data, String user, Object object)
/*     */   {
/* 218 */     if ((object instanceof BlockState)) {
/* 219 */       BlockState block = (BlockState)object;
/* 220 */       List<Object> meta = Functions.processMeta(block);
/* 221 */       if (block_type.equals(Material.SKULL)) {
/* 222 */         Logger.log_skull_place(preparedStmt, preparedStmt_skulls, user, block, Functions.block_id(replace_type), replace_data);
/*     */       }
/* 224 */       else if (force_data == 1) {
/* 225 */         Logger.log_place(preparedStmt, user, block, Functions.block_id(replace_type), replace_data, block_type, block_data, true, meta);
/*     */       }
/*     */       else {
/* 228 */         Logger.log_place(preparedStmt, user, block, Functions.block_id(replace_type), replace_data, block_type, block_data, false, meta);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processContainerBreak(PreparedStatement preparedStmt, int process_id, int id, String user, Object object) {
/* 234 */     if ((object instanceof BlockState)) {
/* 235 */       BlockState block = (BlockState)object;
/* 236 */       Map<Integer, ItemStack[]> containers = (Map)Consumer.consumer_containers.get(Integer.valueOf(process_id));
/* 237 */       if (containers.get(Integer.valueOf(id)) != null) {
/* 238 */         ItemStack[] container = (ItemStack[])containers.get(Integer.valueOf(id));
/* 239 */         Logger.log_container_break(preparedStmt, user, block.getLocation(), block.getType(), container);
/* 240 */         containers.remove(Integer.valueOf(id));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processContainerTransaction(PreparedStatement preparedStmt, int process_id, int id, int force_data, String user, Object object) {
/* 246 */     if ((object instanceof BlockState)) {
/* 247 */       BlockState block = (BlockState)object;
/* 248 */       Map<Integer, Object> inventories = (Map)Consumer.consumer_inventories.get(Integer.valueOf(process_id));
/* 249 */       if (inventories.get(Integer.valueOf(id)) != null) {
/* 250 */         Object inventory = inventories.get(Integer.valueOf(id));
/* 251 */         String logging_chest_id = user.toLowerCase() + "." + block.getX() + "." + block.getY() + "." + block.getZ();
/* 252 */         if (Config.logging_chest.get(logging_chest_id) != null) {
/* 253 */           int current_chest = ((Integer)Config.logging_chest.get(logging_chest_id)).intValue();
/* 254 */           if (Config.old_container.get(logging_chest_id) == null) {
/* 255 */             return;
/*     */           }
/* 257 */           int force_size = 0;
/* 258 */           if (Config.force_containers.get(logging_chest_id) != null) {
/* 259 */             force_size = ((List)Config.force_containers.get(logging_chest_id)).size();
/*     */           }
/* 261 */           if ((current_chest == force_data) || (force_size > 0)) {
/* 262 */             Logger.log_container(preparedStmt, user, block.getType(), inventory, block.getLocation());
/* 263 */             List<ItemStack[]> old = (List)Config.old_container.get(logging_chest_id);
/* 264 */             if (old.size() == 0) {
/* 265 */               Config.old_container.remove(logging_chest_id);
/* 266 */               Config.logging_chest.remove(logging_chest_id);
/*     */             }
/*     */           }
/*     */         }
/* 270 */         inventories.remove(Integer.valueOf(id));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processEntityKill(PreparedStatement preparedStmt, PreparedStatement preparedStmt_entities, int process_id, int id, Object object, String user) {
/* 276 */     if ((object instanceof Object[])) {
/* 277 */       BlockState block = (BlockState)((Object[])(Object[])object)[0];
/* 278 */       EntityType type = (EntityType)((Object[])(Object[])object)[1];
/* 279 */       Map<Integer, List<Object>> object_lists = (Map)Consumer.consumer_object_list.get(Integer.valueOf(process_id));
/* 280 */       if (object_lists.get(Integer.valueOf(id)) != null) {
/* 281 */         List<Object> object_list = (List)object_lists.get(Integer.valueOf(id));
/* 282 */         int entityId = Functions.getEntityId(type);
/* 283 */         Logger.log_entity_kill(preparedStmt, preparedStmt_entities, user, block, object_list, entityId);
/* 284 */         object_lists.remove(Integer.valueOf(id));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processEntitySpawn(Statement statement, Object object, int row_id) {
/* 290 */     if ((object instanceof Object[])) {
/* 291 */       BlockState block = (BlockState)((Object[])(Object[])object)[0];
/* 292 */       EntityType type = (EntityType)((Object[])(Object[])object)[1];
/* 293 */       String query = "SELECT data FROM " + Config.prefix + "entity WHERE rowid='" + row_id + "' LIMIT 0, 1";
/* 294 */       List<Object> data = Database.getEntityData(statement, block, query);
/* 295 */       Functions.spawnEntity(block, type, data);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processHangingRemove(Object object, int delay) {
/* 300 */     if ((object instanceof BlockState)) {
/* 301 */       BlockState block = (BlockState)object;
/* 302 */       Functions.removeHanging(block, delay);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processHangingSpawn(Object object, Material type, int data, int delay) {
/* 307 */     if ((object instanceof BlockState)) {
/* 308 */       BlockState block = (BlockState)object;
/* 309 */       Functions.spawnHanging(block, type, data, delay);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processMaterialInsert(PreparedStatement preparedStmt, String name, int material_id) {
/* 314 */     Database.insertMaterial(preparedStmt, material_id, name);
/*     */   }
/*     */   
/*     */   private static void processNaturalBlockBreak(Statement statement, PreparedStatement preparedStmt, int process_id, int id, String user, Object object, Material block_type, int block_data) {
/* 318 */     if ((object instanceof BlockState)) {
/* 319 */       BlockState block = (BlockState)object;
/* 320 */       Map<Integer, List<BlockState>> block_lists = (Map)Consumer.consumer_block_list.get(Integer.valueOf(process_id));
/* 321 */       if (block_lists.get(Integer.valueOf(id)) != null) {
/* 322 */         List<BlockState> block_list = (List)block_lists.get(Integer.valueOf(id));
/* 323 */         for (BlockState list_block : block_list) {
/* 324 */           String removed = Lookup.who_removed_cache(list_block);
/* 325 */           if (removed.length() > 0) {
/* 326 */             user = removed;
/*     */           }
/*     */         }
/* 329 */         block_lists.remove(Integer.valueOf(id));
/* 330 */         Logger.log_break(preparedStmt, user, block.getLocation(), Functions.block_id(block_type), block_data, null);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processPlayerChat(PreparedStatement preparedStmt, int process_id, int id, int time, String user) {
/* 336 */     Map<Integer, String> strings = (Map)Consumer.consumer_strings.get(Integer.valueOf(process_id));
/* 337 */     if (strings.get(Integer.valueOf(id)) != null) {
/* 338 */       String message = (String)strings.get(Integer.valueOf(id));
/* 339 */       Logger.log_chat(preparedStmt, time, user, message);
/* 340 */       strings.remove(Integer.valueOf(id));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processPlayerCommand(PreparedStatement preparedStmt, int process_id, int id, int time, String user) {
/* 345 */     Map<Integer, String> strings = (Map)Consumer.consumer_strings.get(Integer.valueOf(process_id));
/* 346 */     if (strings.get(Integer.valueOf(id)) != null) {
/* 347 */       String message = (String)strings.get(Integer.valueOf(id));
/* 348 */       Logger.log_command(preparedStmt, time, user, message);
/* 349 */       strings.remove(Integer.valueOf(id));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processPlayerInteraction(PreparedStatement preparedStmt, String user, Object object) {
/* 354 */     if ((object instanceof BlockState)) {
/* 355 */       BlockState block = (BlockState)object;
/* 356 */       Logger.log_interact(preparedStmt, user, block);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processPlayerKill(PreparedStatement preparedStmt, int id, Object object, String user) {
/* 361 */     if ((object instanceof Object[])) {
/* 362 */       BlockState block = (BlockState)((Object[])(Object[])object)[0];
/* 363 */       String player = (String)((Object[])(Object[])object)[1];
/* 364 */       Logger.log_player_kill(preparedStmt, user, block, player);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processPlayerLogin(Connection connection, PreparedStatement preparedStmt, int process_id, int id, Object object, int configSessions, int configUsernames, int time, String user) {
/* 369 */     if ((object instanceof BlockState)) {
/* 370 */       Map<Integer, String> strings = (Map)Consumer.consumer_strings.get(Integer.valueOf(process_id));
/* 371 */       if (strings.get(Integer.valueOf(id)) != null) {
/* 372 */         String uuid = (String)strings.get(Integer.valueOf(id));
/* 373 */         BlockState block = (BlockState)object;
/* 374 */         Logger.log_username(connection, user, uuid, configUsernames, time);
/* 375 */         if (configSessions == 1) {
/* 376 */           Logger.log_session(preparedStmt, user, block, time, 1);
/*     */         }
/* 378 */         strings.remove(Integer.valueOf(id));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processPlayerLogout(PreparedStatement preparedStmt, Object object, int time, String user) {
/* 384 */     if ((object instanceof BlockState)) {
/* 385 */       BlockState block = (BlockState)object;
/* 386 */       Logger.log_session(preparedStmt, user, block, time, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processRollbackUpdate(Statement statement, int process_id, int id, int action, int table) {
/* 391 */     Map<Integer, List<Object[]>> update_lists = (Map)Consumer.consumer_object_array_list.get(Integer.valueOf(process_id));
/* 392 */     if (update_lists.get(Integer.valueOf(id)) != null) {
/* 393 */       List<Object[]> list = (List)update_lists.get(Integer.valueOf(id));
/* 394 */       for (Object[] list_row : list) {
/* 395 */         int rowid = ((Integer)list_row[0]).intValue();
/* 396 */         int rolled_back = ((Integer)list_row[9]).intValue();
/* 397 */         if (rolled_back == action) {
/* 398 */           Database.performUpdate(statement, rowid, action, table);
/*     */         }
/*     */       }
/* 401 */       update_lists.remove(Integer.valueOf(id));
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processSignText(PreparedStatement preparedStmt, int process_id, int id, int force_data, String user, Object object) {
/* 406 */     if ((object instanceof BlockState)) {
/* 407 */       BlockState block = (BlockState)object;
/* 408 */       Map<Integer, String[]> signs = (Map)Consumer.consumer_signs.get(Integer.valueOf(process_id));
/* 409 */       if (signs.get(Integer.valueOf(id)) != null) {
/* 410 */         String[] sign_text = (String[])signs.get(Integer.valueOf(id));
/* 411 */         Logger.sign_text(preparedStmt, user, block, sign_text[0], sign_text[1], sign_text[2], sign_text[3], force_data);
/* 412 */         signs.remove(Integer.valueOf(id));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void processSignUpdate(Statement statement, Object object, int action, int time)
/*     */   {
/* 423 */     if ((object instanceof BlockState)) {
/* 424 */       BlockState block = (BlockState)object;
/* 425 */       int x = block.getX();
/* 426 */       int y = block.getY();
/* 427 */       int z = block.getZ();
/* 428 */       int wid = Functions.getWorldId(block.getWorld().getName());
/* 429 */       String query = "";
/* 430 */       if (action == 0) {
/* 431 */         query = "SELECT line_1, line_2, line_3, line_4 FROM " + Config.prefix + "sign WHERE wid='" + wid + "' AND x='" + x + "' AND z='" + z + "' AND y='" + y + "' AND time < '" + time + "' ORDER BY rowid DESC LIMIT 0, 1";
/*     */       }
/*     */       else {
/* 434 */         query = "SELECT line_1, line_2, line_3, line_4 FROM " + Config.prefix + "sign WHERE wid='" + wid + "' AND x='" + x + "' AND z='" + z + "' AND y='" + y + "' AND time >= '" + time + "' ORDER BY rowid ASC LIMIT 0, 1";
/*     */       }
/* 436 */       Database.getSignData(statement, block, query);
/* 437 */       Functions.updateBlock(block);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static void processSkullUpdate(Statement statement, Object object, int row_id)
/*     */   {
/* 447 */     if ((object instanceof BlockState)) {
/* 448 */       BlockState block = (BlockState)object;
/* 449 */       String query = "SELECT type,data,rotation,owner FROM " + Config.prefix + "skull WHERE rowid='" + row_id + "' LIMIT 0, 1";
/* 450 */       Database.getSkullData(statement, block, query);
/* 451 */       Functions.updateBlock(block);
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processStructureGrowth(Statement statement, PreparedStatement preparedStmt, int process_id, int id, String user, Object object) {
/* 456 */     if ((object instanceof BlockState)) {
/* 457 */       BlockState block = (BlockState)object;
/* 458 */       Map<Integer, List<BlockState>> block_lists = (Map)Consumer.consumer_block_list.get(Integer.valueOf(process_id));
/* 459 */       if (block_lists.get(Integer.valueOf(id)) != null) {
/* 460 */         List<BlockState> block_list = (List)block_lists.get(Integer.valueOf(id));
/* 461 */         String result_data = Lookup.who_placed(statement, block);
/* 462 */         if (result_data.length() > 0) {
/* 463 */           user = result_data;
/*     */         }
/* 465 */         for (BlockState list_block : block_list) {
/* 466 */           if (list_block.getY() >= block.getY()) {
/* 467 */             Logger.log_place(preparedStmt, user, list_block, 0, 0, null, -1, false, null);
/*     */           }
/*     */         }
/* 470 */         block_lists.remove(Integer.valueOf(id));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void processWorldInsert(PreparedStatement preparedStmt, String world, int world_id) {
/* 476 */     Database.insertWorld(preparedStmt, world_id, world);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\consumer\Process.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */