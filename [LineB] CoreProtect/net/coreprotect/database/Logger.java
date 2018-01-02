/*     */ package net.coreprotect.database;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.bukkit.Color;
/*     */ import org.bukkit.FireworkEffect;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.Skull;
/*     */ import org.bukkit.block.banner.Pattern;
/*     */ import org.bukkit.inventory.EntityEquipment;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.BannerMeta;
/*     */ import org.bukkit.inventory.meta.FireworkEffectMeta;
/*     */ import org.bukkit.inventory.meta.FireworkMeta;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*     */ import org.bukkit.inventory.meta.MapMeta;
/*     */ import org.bukkit.inventory.meta.PotionMeta;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ 
/*     */ public class Logger
/*     */ {
/*     */   public static List<List<Map<String, Object>>> getItemMeta(ItemStack i, Material type, int slot)
/*     */   {
/*  36 */     List<List<Map<String, Object>>> metadata = new ArrayList();
/*  37 */     List<Map<String, Object>> list = new ArrayList();
/*  38 */     if (i.hasItemMeta() == true) {
/*  39 */       if ((i.getItemMeta() instanceof LeatherArmorMeta)) {
/*  40 */         LeatherArmorMeta meta = (LeatherArmorMeta)i.getItemMeta().clone();
/*  41 */         LeatherArmorMeta sub_meta = meta.clone();
/*  42 */         meta.setColor(null);
/*  43 */         list.add(meta.serialize());
/*  44 */         metadata.add(list);
/*  45 */         list = new ArrayList();
/*  46 */         list.add(sub_meta.getColor().serialize());
/*  47 */         metadata.add(list);
/*     */       }
/*  49 */       else if ((i.getItemMeta() instanceof FireworkMeta)) {
/*  50 */         FireworkMeta meta = (FireworkMeta)i.getItemMeta().clone();
/*  51 */         FireworkMeta sub_meta = meta.clone();
/*  52 */         meta.clearEffects();
/*  53 */         list.add(meta.serialize());
/*  54 */         metadata.add(list);
/*  55 */         if (sub_meta.hasEffects()) {
/*  56 */           for (FireworkEffect effect : sub_meta.getEffects()) {
/*  57 */             getFireworkEffect(effect, metadata);
/*     */           }
/*     */         }
/*     */       }
/*  61 */       else if ((i.getItemMeta() instanceof PotionMeta)) {
/*  62 */         PotionMeta meta = (PotionMeta)i.getItemMeta().clone();
/*  63 */         PotionMeta sub_meta = meta.clone();
/*  64 */         meta.clearCustomEffects();
/*  65 */         list.add(meta.serialize());
/*  66 */         metadata.add(list);
/*  67 */         if (sub_meta.hasCustomEffects()) {
/*  68 */           for (PotionEffect effect : sub_meta.getCustomEffects()) {
/*  69 */             list = new ArrayList();
/*  70 */             list.add(effect.serialize());
/*  71 */             metadata.add(list);
/*     */           }
/*     */         }
/*     */       }
/*  75 */       else if ((i.getItemMeta() instanceof FireworkEffectMeta)) {
/*  76 */         FireworkEffectMeta meta = (FireworkEffectMeta)i.getItemMeta().clone();
/*  77 */         FireworkEffectMeta sub_meta = meta.clone();
/*  78 */         meta.setEffect(null);
/*  79 */         list.add(meta.serialize());
/*  80 */         metadata.add(list);
/*  81 */         if (sub_meta.hasEffect()) {
/*  82 */           FireworkEffect effect = sub_meta.getEffect();
/*  83 */           getFireworkEffect(effect, metadata);
/*     */         }
/*     */       }
/*  86 */       else if ((i.getItemMeta() instanceof BannerMeta)) {
/*  87 */         BannerMeta meta = (BannerMeta)i.getItemMeta().clone();
/*  88 */         BannerMeta sub_meta = (BannerMeta)meta.clone();
/*  89 */         meta.setPatterns(new ArrayList());
/*  90 */         list.add(meta.serialize());
/*  91 */         metadata.add(list);
/*  92 */         for (Pattern pattern : sub_meta.getPatterns()) {
/*  93 */           list = new ArrayList();
/*  94 */           list.add(pattern.serialize());
/*  95 */           metadata.add(list);
/*     */         }
/*     */       }
/*  98 */       else if ((i.getItemMeta() instanceof MapMeta)) {
/*  99 */         MapMeta meta = (MapMeta)i.getItemMeta().clone();
/* 100 */         MapMeta sub_meta = meta.clone();
/* 101 */         meta.setColor(null);
/* 102 */         list.add(meta.serialize());
/* 103 */         metadata.add(list);
/* 104 */         list = new ArrayList();
/* 105 */         list.add(sub_meta.getColor().serialize());
/* 106 */         metadata.add(list);
/*     */       }
/*     */       else {
/* 109 */         ItemMeta meta = i.getItemMeta().clone();
/* 110 */         list.add(meta.serialize());
/* 111 */         metadata.add(list);
/*     */       }
/*     */     }
/* 114 */     if (type.equals(Material.ARMOR_STAND)) {
/* 115 */       Map<String, Object> meta = new HashMap();
/* 116 */       meta.put("slot", Integer.valueOf(slot));
/* 117 */       list = new ArrayList();
/* 118 */       list.add(meta);
/* 119 */       metadata.add(list);
/*     */     }
/*     */     
/* 122 */     return metadata;
/*     */   }
/*     */   
/*     */   public static void container_logger(PreparedStatement preparedStmt, String user, Material type, ItemStack[] items, int action, Location l) {
/*     */     try {
/* 127 */       if (Config.blacklist.get(user.toLowerCase()) != null) {
/* 128 */         return;
/*     */       }
/* 130 */       int slot = 0;
/* 131 */       for (ItemStack i : items) {
/* 132 */         if ((i != null) && 
/* 133 */           (i.getAmount() > 0) && (!i.getType().equals(Material.AIR))) {
/* 134 */           List<List<Map<String, Object>>> metadata = getItemMeta(i, type, slot);
/* 135 */           int wid = Functions.getWorldId(l.getWorld().getName());
/* 136 */           int userid = ((Integer)Config.player_id_cache.get(user.toLowerCase())).intValue();
/* 137 */           int time = (int)(System.currentTimeMillis() / 1000L);
/* 138 */           int x = l.getBlockX();
/* 139 */           int y = l.getBlockY();
/* 140 */           int z = l.getBlockZ();
/* 141 */           int type_id = Functions.block_id(i.getType().name(), true);
/* 142 */           int data = i.getDurability();
/* 143 */           int amount = i.getAmount();
/* 144 */           Database.insertContainer(preparedStmt, time, userid, wid, x, y, z, type_id, data, amount, metadata, action, 0);
/*     */         }
/*     */         
/* 147 */         slot++;
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 151 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private static void getFireworkEffect(FireworkEffect effect, List<List<Map<String, Object>>> metadata) {
/* 156 */     List<Map<String, Object>> color_list = new ArrayList();
/* 157 */     List<Map<String, Object>> fade_list = new ArrayList();
/* 158 */     List<Map<String, Object>> list = new ArrayList();
/* 159 */     for (Color color : effect.getColors()) {
/* 160 */       color_list.add(color.serialize());
/*     */     }
/* 162 */     for (Color color : effect.getFadeColors()) {
/* 163 */       fade_list.add(color.serialize());
/*     */     }
/* 165 */     Map<String, Object> has_check = new HashMap();
/* 166 */     has_check.put("flicker", Boolean.valueOf(effect.hasFlicker()));
/* 167 */     has_check.put("trail", Boolean.valueOf(effect.hasTrail()));
/* 168 */     list.add(has_check);
/* 169 */     metadata.add(list);
/* 170 */     metadata.add(color_list);
/* 171 */     metadata.add(fade_list);
/*     */   }
/*     */   
/*     */   public static void log_break(PreparedStatement preparedStmt, String user, Location location, int type, int data, List<Object> meta) {
/*     */     try {
/* 176 */       if ((Config.blacklist.get(user.toLowerCase()) != null) || (location == null)) {
/* 177 */         return;
/*     */       }
/*     */       
/* 180 */       Material check_type = Functions.getType(type);
/* 181 */       if (check_type == null) {
/* 182 */         return;
/*     */       }
/* 184 */       if (check_type.equals(Material.AIR)) {
/* 185 */         return;
/*     */       }
/*     */       
/* 188 */       int wid = Functions.getWorldId(location.getWorld().getName());
/* 189 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 190 */       int x = location.getBlockX();
/* 191 */       int y = location.getBlockY();
/* 192 */       int z = location.getBlockZ();
/* 193 */       Config.break_cache.put("" + x + "." + y + "." + z + "." + wid + "", new Object[] { Integer.valueOf(time), user, Integer.valueOf(type) });
/* 194 */       int userid = ((Integer)Config.player_id_cache.get(user.toLowerCase())).intValue();
/* 195 */       Database.insertBlock(preparedStmt, time, userid, wid, x, y, z, type, data, meta, 0, 0);
/*     */     }
/*     */     catch (Exception e) {
/* 198 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_chat(PreparedStatement preparedStmt, int time, String user, String message) {
/*     */     try {
/* 204 */       if (Config.blacklist.get(user.toLowerCase()) != null) {
/* 205 */         return;
/*     */       }
/* 207 */       int userid = ((Integer)Config.player_id_cache.get(user.toLowerCase())).intValue();
/* 208 */       Database.insertChat(preparedStmt, time, userid, message);
/*     */     }
/*     */     catch (Exception e) {
/* 211 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_command(PreparedStatement preparedStmt, int time, String user, String message) {
/*     */     try {
/* 217 */       if (Config.blacklist.get(user.toLowerCase()) != null) {
/* 218 */         return;
/*     */       }
/* 220 */       if (Config.blacklist.get((message + " ").split(" ")[0].toLowerCase()) != null) {
/* 221 */         return;
/*     */       }
/* 223 */       int userid = ((Integer)Config.player_id_cache.get(user.toLowerCase())).intValue();
/* 224 */       Database.insertCommand(preparedStmt, time, userid, message);
/*     */     }
/*     */     catch (Exception e) {
/* 227 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_container(PreparedStatement preparedStmt, String player, Material type, Object container, Location l) {
/*     */     try {
/* 233 */       ItemStack[] contents = null;
/* 234 */       if (type.equals(Material.ARMOR_STAND)) {
/* 235 */         EntityEquipment equipment = (EntityEquipment)container;
/* 236 */         if (equipment != null) {
/* 237 */           contents = equipment.getArmorContents();
/*     */         }
/*     */       }
/*     */       else {
/* 241 */         Inventory inventory = (Inventory)container;
/* 242 */         if (inventory != null) {
/* 243 */           contents = inventory.getContents();
/*     */         }
/*     */       }
/*     */       
/* 247 */       if (contents == null) {
/* 248 */         return;
/*     */       }
/*     */       
/* 251 */       String logging_container_id = player.toLowerCase() + "." + l.getBlockX() + "." + l.getBlockY() + "." + l.getBlockZ();
/* 252 */       List<ItemStack[]> old_list = (List)Config.old_container.get(logging_container_id);
/* 253 */       ItemStack[] oi1 = (ItemStack[])old_list.get(0);
/* 254 */       ItemStack[] old_inventory = Functions.get_container_state(oi1);
/* 255 */       ItemStack[] new_inventory = Functions.get_container_state(contents);
/* 256 */       if (Config.force_containers.get(logging_container_id) != null) {
/* 257 */         List<ItemStack[]> force_list = (List)Config.force_containers.get(logging_container_id);
/* 258 */         new_inventory = Functions.get_container_state((ItemStack[])force_list.get(0));
/* 259 */         force_list.remove(0);
/* 260 */         if (force_list.size() == 0) {
/* 261 */           Config.force_containers.remove(logging_container_id);
/*     */         }
/*     */         else {
/* 264 */           Config.force_containers.put(logging_container_id, force_list);
/*     */         }
/*     */       }
/*     */       
/* 268 */       for (ItemStack oldi : old_inventory) {
/* 269 */         for (ItemStack newi : new_inventory) {
/* 270 */           if ((oldi != null) && (newi != null) && 
/* 271 */             (oldi.isSimilar(newi))) {
/* 272 */             int oamount = oldi.getAmount();
/* 273 */             int namount = newi.getAmount();
/* 274 */             if (namount >= oamount) {
/* 275 */               namount -= oamount;
/* 276 */               oldi.setAmount(0);
/* 277 */               newi.setAmount(namount);
/*     */             }
/*     */             else {
/* 280 */               oamount -= namount;
/* 281 */               oldi.setAmount(oamount);
/* 282 */               newi.setAmount(0);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 288 */       Functions.combine_items(type, old_inventory);
/* 289 */       Functions.combine_items(type, new_inventory);
/* 290 */       container_logger(preparedStmt, player, type, old_inventory, 0, l);
/* 291 */       container_logger(preparedStmt, player, type, new_inventory, 1, l);
/* 292 */       old_list.remove(0);
/* 293 */       Config.old_container.put(logging_container_id, old_list);
/*     */     }
/*     */     catch (Exception e) {
/* 296 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_container_break(PreparedStatement preparedStmt, String player, Location l, Material type, ItemStack[] old_inventory) {
/*     */     try {
/* 302 */       Functions.combine_items(type, old_inventory);
/* 303 */       container_logger(preparedStmt, player, type, old_inventory, 0, l);
/* 304 */       String logging_container_id = player.toLowerCase() + "." + l.getBlockX() + "." + l.getBlockY() + "." + l.getBlockZ();
/*     */       
/*     */ 
/* 307 */       if (Config.force_containers.get(logging_container_id) != null) {
/* 308 */         Config.force_containers.remove(logging_container_id);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 312 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_entity_kill(PreparedStatement preparedStmt, PreparedStatement preparedStmt2, String user, BlockState block, List<Object> data, int type) {
/*     */     try {
/* 318 */       if (Config.blacklist.get(user.toLowerCase()) != null) {
/* 319 */         return;
/*     */       }
/* 321 */       int wid = Functions.getWorldId(block.getWorld().getName());
/* 322 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 323 */       int x = block.getX();
/* 324 */       int y = block.getY();
/* 325 */       int z = block.getZ();
/* 326 */       int userid = ((Integer)Config.player_id_cache.get(user.toLowerCase())).intValue();
/* 327 */       Database.insertEntity(preparedStmt2, time, data);
/* 328 */       ResultSet keys = preparedStmt2.getGeneratedKeys();
/* 329 */       keys.next();
/* 330 */       int entity_key = keys.getInt(1);
/* 331 */       keys.close();
/* 332 */       Database.insertBlock(preparedStmt, time, userid, wid, x, y, z, type, entity_key, null, 3, 0);
/*     */     }
/*     */     catch (Exception e) {
/* 335 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_interact(PreparedStatement preparedStmt, String user, BlockState block) {
/*     */     try {
/* 341 */       int type = Functions.block_id(block.getType().name(), true);
/* 342 */       if ((Config.blacklist.get(user.toLowerCase()) != null) || (Functions.getType(type).equals(Material.AIR))) {
/* 343 */         return;
/*     */       }
/* 345 */       int wid = Functions.getWorldId(block.getWorld().getName());
/* 346 */       int userid = ((Integer)Config.player_id_cache.get(user.toLowerCase())).intValue();
/* 347 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 348 */       int x = block.getX();
/* 349 */       int y = block.getY();
/* 350 */       int z = block.getZ();
/* 351 */       int data = Functions.getData(block);
/* 352 */       Database.insertBlock(preparedStmt, time, userid, wid, x, y, z, type, data, null, 2, 0);
/*     */     }
/*     */     catch (Exception e) {
/* 355 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_place(PreparedStatement preparedStmt, String user, BlockState block, int replaced_type, int replaced_data, Material force_type, int force_data, boolean force, List<Object> meta) {
/*     */     try {
/* 361 */       if (Config.blacklist.get(user.toLowerCase()) != null) {
/* 362 */         return;
/*     */       }
/* 364 */       Material type = block.getType();
/* 365 */       int data = Functions.getData(block);
/* 366 */       if ((force_type != null) && (force == true)) {
/* 367 */         type = force_type;
/* 368 */         if ((type.equals(Material.MOB_SPAWNER)) || (type.equals(Material.PAINTING)) || (type.equals(Material.ITEM_FRAME)) || (type.equals(Material.SKULL)) || (type.equals(Material.ARMOR_STAND)) || (type.equals(Material.END_CRYSTAL))) {
/* 369 */           data = force_data;
/*     */         }
/* 371 */         else if (user.startsWith("#")) {
/* 372 */           data = force_data;
/*     */         }
/*     */       }
/* 375 */       else if ((force_type != null) && (!type.equals(force_type))) {
/* 376 */         type = force_type;
/* 377 */         data = force_data;
/*     */       }
/*     */       
/* 380 */       if (type.equals(Material.AIR)) {
/* 381 */         return;
/*     */       }
/* 383 */       int userid = ((Integer)Config.player_id_cache.get(user.toLowerCase())).intValue();
/* 384 */       int wid = Functions.getWorldId(block.getWorld().getName());
/* 385 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 386 */       int x = block.getX();
/* 387 */       int y = block.getY();
/* 388 */       int z = block.getZ();
/* 389 */       int dx = x;
/* 390 */       int dy = y;
/* 391 */       int dz = z;
/* 392 */       Material doubletype = type;
/* 393 */       int doubledata = data;
/* 394 */       int logdouble = 0;
/*     */       
/* 396 */       if (user.length() > 0) {
/* 397 */         Config.lookup_cache.put("" + x + "." + y + "." + z + "." + wid + "", new Object[] { Integer.valueOf(time), user, type });
/*     */       }
/*     */       
/* 400 */       if ((type.equals(Material.BED_BLOCK)) || (type.equals(Material.WOODEN_DOOR)) || (type.equals(Material.SPRUCE_DOOR)) || (type.equals(Material.BIRCH_DOOR)) || (type.equals(Material.JUNGLE_DOOR)) || (type.equals(Material.ACACIA_DOOR)) || (type.equals(Material.DARK_OAK_DOOR)) || (type.equals(Material.IRON_DOOR_BLOCK))) {
/* 401 */         if (type.equals(Material.BED_BLOCK)) {
/* 402 */           doubledata = data + 8;
/* 403 */           if (data == 0) {
/* 404 */             dz = z + 1;
/*     */           }
/* 406 */           else if (data == 1) {
/* 407 */             dx = x - 1;
/*     */           }
/* 409 */           else if (data == 2) {
/* 410 */             dz = z - 1;
/*     */           }
/* 412 */           else if (data == 3) {
/* 413 */             dx = x + 1;
/*     */           }
/*     */         }
/* 416 */         else if (((type.equals(Material.WOODEN_DOOR)) || (type.equals(Material.SPRUCE_DOOR)) || (type.equals(Material.BIRCH_DOOR)) || (type.equals(Material.JUNGLE_DOOR)) || (type.equals(Material.ACACIA_DOOR)) || (type.equals(Material.DARK_OAK_DOOR)) || (type.equals(Material.IRON_DOOR_BLOCK))) && 
/* 417 */           (data < 9)) {
/* 418 */           dy = y + 1;
/* 419 */           doubledata = data + 8;
/*     */         }
/*     */         
/* 422 */         logdouble = 1;
/*     */       }
/*     */       
/* 425 */       int internal_type = Functions.block_id(type.name(), true);
/* 426 */       int internal_doubletype = Functions.block_id(doubletype.name(), true);
/*     */       
/* 428 */       if ((replaced_type > 0) && (!Functions.getType(replaced_type).equals(Material.AIR))) {
/* 429 */         Database.insertBlock(preparedStmt, time, userid, wid, x, y, z, replaced_type, replaced_data, null, 0, 0);
/*     */       }
/*     */       
/* 432 */       Database.insertBlock(preparedStmt, time, userid, wid, x, y, z, internal_type, data, meta, 1, 0);
/* 433 */       if (logdouble == 1) {
/* 434 */         Database.insertBlock(preparedStmt, time, userid, wid, dx, dy, dz, internal_doubletype, doubledata, null, 1, 0);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 438 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_player_kill(PreparedStatement preparedStmt, String user, BlockState block, String player) {
/*     */     try {
/* 444 */       if (Config.blacklist.get(user.toLowerCase()) != null) {
/* 445 */         return;
/*     */       }
/* 447 */       int wid = Functions.getWorldId(block.getWorld().getName());
/* 448 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 449 */       int x = block.getX();
/* 450 */       int y = block.getY();
/* 451 */       int z = block.getZ();
/* 452 */       int userid = ((Integer)Config.player_id_cache.get(user.toLowerCase())).intValue();
/* 453 */       if (Config.player_id_cache.get(player.toLowerCase()) == null) {
/* 454 */         Database.loadUserID(preparedStmt.getConnection(), player, null);
/*     */       }
/* 456 */       int playerid = ((Integer)Config.player_id_cache.get(player.toLowerCase())).intValue();
/* 457 */       Database.insertBlock(preparedStmt, time, userid, wid, x, y, z, 0, playerid, null, 3, 0);
/*     */     }
/*     */     catch (Exception e) {
/* 460 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_session(PreparedStatement preparedStmt, String user, BlockState block, int time, int action) {
/*     */     try {
/* 466 */       if (Config.blacklist.get(user.toLowerCase()) != null) {
/* 467 */         return;
/*     */       }
/* 469 */       int x = block.getX();
/* 470 */       int y = block.getY();
/* 471 */       int z = block.getZ();
/* 472 */       int wid = Functions.getWorldId(block.getWorld().getName());
/* 473 */       int userid = ((Integer)Config.player_id_cache.get(user.toLowerCase())).intValue();
/* 474 */       Database.insertSession(preparedStmt, time, userid, wid, x, y, z, action);
/*     */     }
/*     */     catch (Exception e) {
/* 477 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_skull_break(PreparedStatement preparedStmt, PreparedStatement preparedStmt2, String user, BlockState block) {
/*     */     try {
/* 483 */       if ((Config.blacklist.get(user.toLowerCase()) != null) || (block == null)) {
/* 484 */         return;
/*     */       }
/* 486 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 487 */       int type = Functions.block_id(block.getType().name(), true);
/* 488 */       Skull skull = (Skull)block;
/* 489 */       String skull_owner = "";
/* 490 */       int skull_type = Functions.getSkullType(skull.getSkullType());
/* 491 */       int skull_rotation = Functions.getBlockFace(skull.getRotation());
/* 492 */       if (skull.hasOwner()) {
/* 493 */         skull_owner = skull.getOwner();
/*     */       }
/* 495 */       int skull_data = Functions.getRawData(skull);
/* 496 */       Database.insertSkull(preparedStmt2, time, skull_type, skull_data, skull_rotation, skull_owner);
/* 497 */       ResultSet keys = preparedStmt2.getGeneratedKeys();
/* 498 */       keys.next();
/* 499 */       int skull_key = keys.getInt(1);
/* 500 */       keys.close();
/* 501 */       log_break(preparedStmt, user, block.getLocation(), type, skull_key, null);
/*     */     }
/*     */     catch (Exception e) {
/* 504 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_skull_place(PreparedStatement preparedStmt, PreparedStatement preparedStmt2, String user, BlockState block, int replace_type, int replace_data) {
/*     */     try {
/* 510 */       if ((Config.blacklist.get(user.toLowerCase()) != null) || (block == null)) {
/* 511 */         return;
/*     */       }
/* 513 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 514 */       Material type = block.getType();
/* 515 */       int skull_key = 0;
/*     */       
/* 517 */       if ((block instanceof Skull)) {
/* 518 */         Skull skull = (Skull)block;
/* 519 */         String skull_owner = "";
/* 520 */         int skull_type = Functions.getSkullType(skull.getSkullType());
/* 521 */         int skull_rotation = Functions.getBlockFace(skull.getRotation());
/* 522 */         if (skull.hasOwner()) {
/* 523 */           skull_owner = skull.getOwner();
/*     */         }
/* 525 */         int skull_data = Functions.getRawData(skull);
/* 526 */         Database.insertSkull(preparedStmt2, time, skull_type, skull_data, skull_rotation, skull_owner);
/* 527 */         ResultSet keys = preparedStmt2.getGeneratedKeys();
/* 528 */         keys.next();
/* 529 */         skull_key = keys.getInt(1);
/* 530 */         keys.close();
/*     */       }
/*     */       
/* 533 */       log_place(preparedStmt, user, block, replace_type, replace_data, type, skull_key, true, null);
/*     */     }
/*     */     catch (Exception e) {
/* 536 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void log_username(Connection connection, String user, String uuid, int configUsernames, int time) {
/*     */     try {
/* 542 */       if (Config.blacklist.get(user.toLowerCase()) != null) {
/* 543 */         return;
/*     */       }
/*     */       
/* 546 */       int id_row = -1;
/* 547 */       String user_row = null;
/* 548 */       String query = "SELECT rowid as id, user FROM " + Config.prefix + "user WHERE uuid = ? LIMIT 0, 1";
/* 549 */       PreparedStatement preparedStmt = connection.prepareStatement(query);
/* 550 */       preparedStmt.setString(1, uuid);
/* 551 */       ResultSet rs = preparedStmt.executeQuery();
/* 552 */       while (rs.next()) {
/* 553 */         id_row = rs.getInt("id");
/* 554 */         user_row = rs.getString("user").toLowerCase();
/*     */       }
/* 556 */       rs.close();
/* 557 */       preparedStmt.close();
/*     */       
/* 559 */       boolean update = false;
/* 560 */       if (user_row == null) {
/* 561 */         id_row = ((Integer)Config.player_id_cache.get(user.toLowerCase())).intValue();
/* 562 */         update = true;
/*     */       }
/* 564 */       else if (!user.equalsIgnoreCase(user_row)) {
/* 565 */         update = true;
/*     */       }
/*     */       
/* 568 */       if (update) {
/* 569 */         preparedStmt = connection.prepareStatement("UPDATE " + Config.prefix + "user SET user = ?, uuid = ? WHERE rowid = ?");
/* 570 */         preparedStmt.setString(1, user);
/* 571 */         preparedStmt.setString(2, uuid);
/* 572 */         preparedStmt.setInt(3, id_row);
/* 573 */         preparedStmt.executeUpdate();
/* 574 */         preparedStmt.close();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 587 */         boolean foundUUID = false;
/* 588 */         query = "SELECT rowid as id FROM " + Config.prefix + "username_log WHERE uuid = ? AND user = ? LIMIT 0, 1";
/* 589 */         PreparedStatement preparedStatement = connection.prepareStatement(query);
/* 590 */         preparedStatement.setString(1, uuid);
/* 591 */         preparedStatement.setString(2, user);
/* 592 */         rs = preparedStatement.executeQuery();
/* 593 */         while (rs.next()) {
/* 594 */           foundUUID = true;
/*     */         }
/* 596 */         rs.close();
/* 597 */         preparedStatement.close();
/*     */         
/* 599 */         if (!foundUUID) {
/* 600 */           update = true;
/*     */         }
/*     */       }
/*     */       
/* 604 */       if ((update == true) && (configUsernames == 1)) {
/* 605 */         preparedStmt = connection.prepareStatement("INSERT INTO " + Config.prefix + "username_log (time, uuid, user) VALUES (?, ?, ?)");
/* 606 */         preparedStmt.setInt(1, time);
/* 607 */         preparedStmt.setString(2, uuid);
/* 608 */         preparedStmt.setString(3, user);
/* 609 */         preparedStmt.executeUpdate();
/* 610 */         preparedStmt.close();
/*     */       }
/*     */       
/* 613 */       Config.player_id_cache.put(user.toLowerCase(), Integer.valueOf(id_row));
/* 614 */       Config.player_id_cache_reversed.put(Integer.valueOf(id_row), user);
/* 615 */       Config.uuid_cache.put(user.toLowerCase(), uuid);
/* 616 */       Config.uuid_cache_reversed.put(uuid, user);
/*     */     }
/*     */     catch (Exception e) {
/* 619 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void sign_text(PreparedStatement preparedStmt, String user, BlockState block, String line1, String line2, String line3, String line4, int time_offset) {
/*     */     try {
/* 625 */       if (Config.blacklist.get(user.toLowerCase()) != null) {
/* 626 */         return;
/*     */       }
/* 628 */       int userid = ((Integer)Config.player_id_cache.get(user.toLowerCase())).intValue();
/* 629 */       int wid = Functions.getWorldId(block.getWorld().getName());
/* 630 */       int time = (int)(System.currentTimeMillis() / 1000L) - time_offset;
/* 631 */       int x = block.getX();
/* 632 */       int y = block.getY();
/* 633 */       int z = block.getZ();
/* 634 */       Database.insertSign(preparedStmt, time, userid, wid, x, y, z, line1, line2, line3, line4);
/*     */     }
/*     */     catch (Exception e) {
/* 637 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\database\Logger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */