/*      */ package net.coreprotect.database;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.ObjectInputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.TreeMap;
/*      */ import net.coreprotect.CoreProtect;
/*      */ import net.coreprotect.Functions;
/*      */ import net.coreprotect.consumer.Queue;
/*      */ import net.coreprotect.model.BlockInfo;
/*      */ import net.coreprotect.model.Config;
/*      */ import org.bukkit.Chunk;
/*      */ import org.bukkit.Color;
/*      */ import org.bukkit.DyeColor;
/*      */ import org.bukkit.FireworkEffect;
/*      */ import org.bukkit.FireworkEffect.Builder;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.block.Banner;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.block.BlockState;
/*      */ import org.bukkit.block.CommandBlock;
/*      */ import org.bukkit.block.CreatureSpawner;
/*      */ import org.bukkit.block.banner.Pattern;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.entity.ArmorStand;
/*      */ import org.bukkit.entity.EnderCrystal;
/*      */ import org.bukkit.entity.Entity;
/*      */ import org.bukkit.entity.EntityType;
/*      */ import org.bukkit.entity.LivingEntity;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.inventory.EntityEquipment;
/*      */ import org.bukkit.inventory.Inventory;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.inventory.meta.BannerMeta;
/*      */ import org.bukkit.inventory.meta.FireworkEffectMeta;
/*      */ import org.bukkit.inventory.meta.FireworkMeta;
/*      */ import org.bukkit.inventory.meta.LeatherArmorMeta;
/*      */ import org.bukkit.inventory.meta.MapMeta;
/*      */ import org.bukkit.inventory.meta.PotionMeta;
/*      */ 
/*      */ public class Lookup extends Queue
/*      */ {
/*      */   public static String block_lookup(Statement statement, Block block, String user, int offset, int page, int limit)
/*      */   {
/*   61 */     String result = "";
/*      */     try {
/*   63 */       if (block == null) {
/*   64 */         return result;
/*      */       }
/*   66 */       boolean found = false;
/*   67 */       int x = block.getX();
/*   68 */       int y = block.getY();
/*   69 */       int z = block.getZ();
/*   70 */       int time = (int)(System.currentTimeMillis() / 1000L);
/*   71 */       int wid = Functions.getWorldId(block.getWorld().getName());
/*   72 */       int check_time = 0;
/*   73 */       int count = 0;
/*   74 */       int row_max = page * limit;
/*   75 */       int page_start = row_max - limit;
/*   76 */       if (offset > 0) {
/*   77 */         check_time = time - offset;
/*      */       }
/*      */       
/*   80 */       String query = "SELECT COUNT(*) as count from " + Config.prefix + "block WHERE wid = '" + wid + "' AND x = '" + x + "' AND z = '" + z + "' AND y = '" + y + "' AND action IN(0,1) AND time >= '" + check_time + "' LIMIT 0, 1";
/*   81 */       ResultSet rs = statement.executeQuery(query);
/*   82 */       while (rs.next()) {
/*   83 */         count = rs.getInt("count");
/*      */       }
/*   85 */       rs.close();
/*   86 */       int total_pages = (int)Math.ceil(count / (limit + 0.0D));
/*      */       
/*   88 */       query = "SELECT time,user,action,type,data,rolled_back FROM " + Config.prefix + "block WHERE wid = '" + wid + "' AND x = '" + x + "' AND z = '" + z + "' AND y = '" + y + "' AND action IN(0,1) AND time >= '" + check_time + "' ORDER BY rowid DESC LIMIT " + page_start + ", " + limit + "";
/*   89 */       rs = statement.executeQuery(query);
/*   90 */       while (rs.next()) {
/*   91 */         int result_userid = rs.getInt("user");
/*   92 */         int result_action = rs.getInt("action");
/*   93 */         int result_type = rs.getInt("type");
/*   94 */         int result_data = rs.getInt("data");
/*   95 */         int result_time = rs.getInt("time");
/*   96 */         int result_rolled_back = rs.getInt("rolled_back");
/*   97 */         if (Config.player_id_cache_reversed.get(Integer.valueOf(result_userid)) == null) {
/*   98 */           Database.loadUserName(statement.getConnection(), result_userid);
/*      */         }
/*  100 */         String result_user = (String)Config.player_id_cache_reversed.get(Integer.valueOf(result_userid));
/*  101 */         double time_since = time - (result_time + 0.0D);
/*  102 */         time_since /= 60.0D;
/*  103 */         time_since /= 60.0D;
/*  104 */         String timeago = new DecimalFormat("0.00").format(time_since);
/*  105 */         if (!found) {
/*  106 */           result = "§f----- §3CoreProtect §f----- §7(x" + x + "/y" + y + "/z" + z + ")\n";
/*      */         }
/*  108 */         found = true;
/*  109 */         String a2 = "placed";
/*  110 */         if (result_action == 0) {
/*  111 */           a2 = "removed";
/*      */         }
/*  113 */         else if (result_action == 2) {
/*  114 */           a2 = "clicked";
/*      */         }
/*  116 */         else if (result_action == 3) {
/*  117 */           a2 = "killed";
/*      */         }
/*  119 */         String rbd = "";
/*  120 */         if (result_rolled_back == 1) {
/*  121 */           rbd = "§m";
/*      */         }
/*  123 */         String dname = "";
/*  124 */         if (result_action == 3) {
/*  125 */           dname = Functions.getEntityType(result_type).name();
/*      */         }
/*      */         else {
/*  128 */           dname = Functions.nameFilter(Functions.getType(result_type).name().toLowerCase(), result_data);
/*  129 */           dname = "minecraft:" + dname.toLowerCase();
/*      */         }
/*  131 */         if (dname.length() > 0) {
/*  132 */           dname = "" + dname + "";
/*      */         }
/*      */         
/*      */ 
/*  136 */         if (dname.contains("minecraft:")) {
/*  137 */           String[] block_name_split = dname.split(":");
/*  138 */           dname = block_name_split[1];
/*      */         }
/*      */         
/*  141 */         result = result + "§7" + timeago + "/h ago §f- §3" + rbd + "" + result_user + " §f" + rbd + "" + a2 + " §3" + rbd + "" + dname + "§f.\n";
/*      */       }
/*  143 */       rs.close();
/*  144 */       if (found == true) {
/*  145 */         if (count > limit) {
/*  146 */           String n = "§f-----\n";
/*  147 */           n = n + "§fPage " + page + "/" + total_pages + ". View older data by typing \"§3/co l <page>§f\".\n";
/*  148 */           result = result + n;
/*      */         }
/*      */       }
/*  151 */       else if (!found) {
/*  152 */         if ((row_max > count) && (count > 0)) {
/*  153 */           result = "§3CoreProtect §f- §fNo block data found for that page.";
/*      */         }
/*      */         else {
/*  156 */           result = "§3CoreProtect §f- §fNo block data found for this location.";
/*      */         }
/*      */       }
/*  159 */       String bc = x + "." + y + "." + z + "." + wid + ".0." + limit;
/*  160 */       Config.lookup_page.put(user, Integer.valueOf(page));
/*  161 */       Config.lookup_type.put(user, Integer.valueOf(2));
/*  162 */       Config.lookup_command.put(user, bc);
/*      */     }
/*      */     catch (Exception e) {
/*  165 */       e.printStackTrace();
/*      */     }
/*  167 */     return result;
/*      */   }
/*      */   
/*      */   public static List<String[]> block_lookup_api(Block block, int offset) {
/*  171 */     List<String[]> result = new ArrayList();
/*      */     try {
/*  173 */       if (block == null) {
/*  174 */         return result;
/*      */       }
/*  176 */       int x = block.getX();
/*  177 */       int y = block.getY();
/*  178 */       int z = block.getZ();
/*  179 */       int time = (int)(System.currentTimeMillis() / 1000L);
/*  180 */       int wid = Functions.getWorldId(block.getWorld().getName());
/*  181 */       int check_time = 0;
/*  182 */       if (offset > 0) {
/*  183 */         check_time = time - offset;
/*      */       }
/*      */       
/*  186 */       Connection connection = Database.getConnection(false);
/*  187 */       if (connection == null) {
/*  188 */         return result;
/*      */       }
/*  190 */       Statement statement = connection.createStatement();
/*  191 */       String query = "SELECT time,user,action,type,data,rolled_back FROM " + Config.prefix + "block WHERE wid = '" + wid + "' AND x = '" + x + "' AND z = '" + z + "' AND y = '" + y + "' AND time > '" + check_time + "' ORDER BY rowid DESC";
/*  192 */       ResultSet rs = statement.executeQuery(query);
/*  193 */       while (rs.next()) {
/*  194 */         int result_time = rs.getInt("time");
/*  195 */         int result_userid = rs.getInt("user");
/*  196 */         int result_action = rs.getInt("action");
/*  197 */         int result_type = rs.getInt("type");
/*  198 */         int result_data = rs.getInt("data");
/*  199 */         int result_rolled_back = rs.getInt("rolled_back");
/*  200 */         if (Config.player_id_cache_reversed.get(Integer.valueOf(result_userid)) == null) {
/*  201 */           Database.loadUserName(connection, result_userid);
/*      */         }
/*  203 */         String result_user = (String)Config.player_id_cache_reversed.get(Integer.valueOf(result_userid));
/*  204 */         String line = result_time + "," + result_user + "," + x + "." + y + "." + z + "," + result_type + "," + result_data + "," + result_action + "," + result_rolled_back + "," + wid + ",";
/*  205 */         String[] ldata = Functions.toStringArray(line);
/*  206 */         result.add(ldata);
/*      */       }
/*  208 */       rs.close();
/*  209 */       statement.close();
/*  210 */       connection.close();
/*      */     }
/*      */     catch (Exception e) {
/*  213 */       e.printStackTrace();
/*      */     }
/*  215 */     return result;
/*      */   }
/*      */   
/*      */   public static String chest_transactions(Statement statement, Location l, String lookup_user, int page, int limit) {
/*  219 */     String result = "";
/*      */     try {
/*  221 */       if (l == null) {
/*  222 */         return result;
/*      */       }
/*  224 */       boolean found = false;
/*  225 */       int x = (int)Math.floor(l.getX());
/*  226 */       int y = (int)Math.floor(l.getY());
/*  227 */       int z = (int)Math.floor(l.getZ());
/*  228 */       int x2 = (int)Math.ceil(l.getX());
/*  229 */       int y2 = (int)Math.ceil(l.getY());
/*  230 */       int z2 = (int)Math.ceil(l.getZ());
/*  231 */       int time = (int)(System.currentTimeMillis() / 1000L);
/*  232 */       int wid = Functions.getWorldId(l.getWorld().getName());
/*  233 */       int count = 0;
/*  234 */       int row_max = page * limit;
/*  235 */       int page_start = row_max - limit;
/*      */       
/*  237 */       String query = "SELECT COUNT(*) as count from " + Config.prefix + "container WHERE wid = '" + wid + "' AND (x = '" + x + "' OR x = '" + x2 + "') AND (z = '" + z + "' OR z = '" + z2 + "') AND y = '" + y + "' LIMIT 0, 1";
/*  238 */       ResultSet rs = statement.executeQuery(query);
/*  239 */       while (rs.next()) {
/*  240 */         count = rs.getInt("count");
/*      */       }
/*  242 */       rs.close();
/*  243 */       int total_pages = (int)Math.ceil(count / (limit + 0.0D));
/*      */       
/*  245 */       query = "SELECT time,user,action,type,data,amount,rolled_back FROM " + Config.prefix + "container WHERE wid = '" + wid + "' AND (x = '" + x + "' OR x = '" + x2 + "') AND (z = '" + z + "' OR z = '" + z2 + "') AND y = '" + y + "' ORDER BY rowid DESC LIMIT " + page_start + ", " + limit + "";
/*  246 */       rs = statement.executeQuery(query);
/*  247 */       while (rs.next()) {
/*  248 */         int result_userid = rs.getInt("user");
/*  249 */         int result_action = rs.getInt("action");
/*  250 */         int result_type = rs.getInt("type");
/*  251 */         int result_data = rs.getInt("data");
/*  252 */         int result_time = rs.getInt("time");
/*  253 */         int result_amount = rs.getInt("amount");
/*  254 */         int result_rolled_back = rs.getInt("rolled_back");
/*      */         
/*  256 */         if (Config.player_id_cache_reversed.get(Integer.valueOf(result_userid)) == null) {
/*  257 */           Database.loadUserName(statement.getConnection(), result_userid);
/*      */         }
/*  259 */         String result_user = (String)Config.player_id_cache_reversed.get(Integer.valueOf(result_userid));
/*  260 */         double time_since = time - (result_time + 0.0D);
/*  261 */         time_since /= 60.0D;
/*  262 */         time_since /= 60.0D;
/*  263 */         String timeago = new DecimalFormat("0.00").format(time_since);
/*  264 */         if (!found) {
/*  265 */           result = "§f----- §3Container Transactions §f----- §7(x" + x + "/y" + y + "/z" + z + ")\n";
/*      */         }
/*  267 */         found = true;
/*  268 */         String a2 = "added";
/*  269 */         if (result_action == 0) {
/*  270 */           a2 = "removed";
/*      */         }
/*      */         
/*  273 */         String rbd = "";
/*  274 */         if (result_rolled_back == 1) {
/*  275 */           rbd = "§m";
/*      */         }
/*  277 */         String dname = Functions.getType(result_type).name().toLowerCase();
/*  278 */         dname = Functions.nameFilter(dname, result_data);
/*  279 */         if (dname.length() > 0) {
/*  280 */           dname = "minecraft:" + dname.toLowerCase() + "";
/*      */         }
/*      */         
/*      */ 
/*  284 */         if (dname.contains("minecraft:")) {
/*  285 */           String[] block_name_split = dname.split(":");
/*  286 */           dname = block_name_split[1];
/*      */         }
/*      */         
/*  289 */         result = result + "§7" + timeago + "/h ago §f- §3" + rbd + "" + result_user + " §f" + rbd + "" + a2 + " x" + result_amount + " §3" + rbd + "" + dname + "§f.\n";
/*      */       }
/*  291 */       rs.close();
/*  292 */       if (found == true) {
/*  293 */         if (count > limit) {
/*  294 */           String n = "§f-----\n";
/*  295 */           n = n + "§fPage " + page + "/" + total_pages + ". View older data by typing \"§3/co l <page>§f\".\n";
/*  296 */           result = result + n;
/*      */         }
/*      */       }
/*  299 */       else if (!found) {
/*  300 */         if ((row_max > count) && (count > 0)) {
/*  301 */           result = "§3CoreProtect §f- §fNo container transactions found for that page.";
/*      */         }
/*      */         else {
/*  304 */           result = "§3CoreProtect §f- §fNo container transactions at this location.";
/*      */         }
/*      */       }
/*  307 */       String bc = x + "." + y + "." + z + "." + wid + "." + x2 + "." + y2 + "." + z2 + "." + limit;
/*  308 */       Config.lookup_type.put(lookup_user, Integer.valueOf(1));
/*  309 */       Config.lookup_page.put(lookup_user, Integer.valueOf(page));
/*  310 */       Config.lookup_command.put(lookup_user, bc);
/*      */     }
/*      */     catch (Exception e) {
/*  313 */       e.printStackTrace();
/*      */     }
/*  315 */     return result;
/*      */   }
/*      */   
/*      */   private static List<String[]> convertRawLookup(Statement statement, List<Object[]> list) {
/*  319 */     List<String[]> new_list = new ArrayList();
/*  320 */     if (list == null) {
/*  321 */       return null;
/*      */     }
/*  323 */     for (int i = 0; i < list.size(); i++) {
/*  324 */       Object[] map = (Object[])list.get(i);
/*  325 */       int new_length = map.length - 1;
/*  326 */       String[] results = new String[new_length];
/*  327 */       for (int i2 = 0; i2 < map.length; i2++) {
/*      */         try {
/*  329 */           int new_id = i2 - 1;
/*  330 */           if (i2 == 2) {
/*  331 */             if ((map[i2] instanceof Integer)) {
/*  332 */               int user_id = ((Integer)map[i2]).intValue();
/*  333 */               if (Config.player_id_cache_reversed.get(Integer.valueOf(user_id)) == null) {
/*  334 */                 Database.loadUserName(statement.getConnection(), user_id);
/*      */               }
/*  336 */               String user_result = (String)Config.player_id_cache_reversed.get(Integer.valueOf(user_id));
/*  337 */               results[new_id] = user_result;
/*      */             }
/*      */             else {
/*  340 */               results[new_id] = ((String)map[i2]);
/*      */             }
/*      */           }
/*  343 */           else if (i2 > 0) {
/*  344 */             if ((map[i2] instanceof Integer)) {
/*  345 */               results[new_id] = map[i2].toString();
/*      */             }
/*  347 */             else if ((map[i2] instanceof String)) {
/*  348 */               results[new_id] = ((String)map[i2]);
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception e) {
/*  353 */           e.printStackTrace();
/*      */         }
/*      */       }
/*  356 */       new_list.add(results);
/*      */     }
/*  358 */     return new_list;
/*      */   }
/*      */   
/*      */   public static int countLookupRows(Statement statement, CommandSender user, List<String> check_uuids, List<String> check_users, List<Object> restrict_list, List<Object> exclude_list, List<String> exclude_user_list, List<Integer> action_list, Location location, Integer[] radius, int check_time, boolean restrict_world, boolean lookup) {
/*  362 */     int rows = 0;
/*      */     try {
/*  364 */       while (net.coreprotect.consumer.Consumer.is_paused == true) {
/*  365 */         Thread.sleep(1L);
/*      */       }
/*  367 */       net.coreprotect.consumer.Consumer.is_paused = true;
/*  368 */       ResultSet rs = rawLookupResultSet(statement, user, check_uuids, check_users, restrict_list, exclude_list, exclude_user_list, action_list, location, radius, check_time, -1, -1, restrict_world, lookup, true);
/*  369 */       while (rs.next()) {
/*  370 */         rows = rs.getInt("count");
/*      */       }
/*  372 */       rs.close();
/*      */     }
/*      */     catch (Exception e) {
/*  375 */       e.printStackTrace();
/*      */     }
/*  377 */     net.coreprotect.consumer.Consumer.is_paused = false;
/*  378 */     return rows;
/*      */   }
/*      */   
/*      */   public static void finishRollbackRestore(CommandSender user, Location location, List<String> check_users, List<Object> restrict_list, List<Object> exclude_list, List<String> exclude_user_list, List<Integer> action_list, String time_string, int file, int seconds, int item_count, int block_count, int entity_count, int rollback_type, Integer[] radius, boolean verbose, boolean restrict_world, int preview) {
/*      */     try {
/*  383 */       if (preview == 2) {
/*  384 */         user.sendMessage("§3CoreProtect §f- Preview cancelled.");
/*  385 */         return;
/*      */       }
/*  387 */       user.sendMessage("-----");
/*  388 */       String users = "";
/*  389 */       for (String value : check_users) {
/*  390 */         if (users.length() == 0) {
/*  391 */           users = "" + value + "";
/*      */         }
/*      */         else {
/*  394 */           users = users + ", " + value;
/*      */         }
/*      */       }
/*  397 */       if ((users.equals("#global")) && (restrict_world)) {
/*  398 */         users = "#" + location.getWorld().getName();
/*      */       }
/*  400 */       if (preview > 0) {
/*  401 */         user.sendMessage("§3CoreProtect §f- Preview completed for \"" + users + "\".");
/*      */       }
/*  403 */       else if (rollback_type == 1) {
/*  404 */         user.sendMessage("§3CoreProtect §f- Restore completed for \"" + users + "\".");
/*      */       }
/*  406 */       else if (rollback_type == 0) {
/*  407 */         user.sendMessage("§3CoreProtect §f- Rollback completed for \"" + users + "\".");
/*      */       }
/*  409 */       if (preview == 1) {
/*  410 */         user.sendMessage("§3CoreProtect §f- Time:" + time_string + ".");
/*      */       }
/*  412 */       else if (rollback_type == 1) {
/*  413 */         user.sendMessage("§3CoreProtect §f- Restored" + time_string + ".");
/*      */       }
/*  415 */       else if (rollback_type == 0) {
/*  416 */         user.sendMessage("§3CoreProtect §f- Rolled back" + time_string + ".");
/*      */       }
/*  418 */       if (radius != null) {
/*  419 */         int worldedit = radius[7].intValue();
/*  420 */         if (worldedit == 0) {
/*  421 */           int rad = radius[0].intValue();
/*  422 */           user.sendMessage("§3CoreProtect §f- Radius: " + rad + " block(s).");
/*      */         }
/*      */         else {
/*  425 */           user.sendMessage("§3CoreProtect §f- Radius: #worldedit.");
/*      */         }
/*      */       }
/*  428 */       if ((restrict_world == true) && (radius == null) && 
/*  429 */         (location != null)) {
/*  430 */         user.sendMessage("§3CoreProtect §f- Limited to world: \"" + location.getWorld().getName() + "\".");
/*      */       }
/*      */       
/*  433 */       if (action_list.contains(Integer.valueOf(4))) {
/*  434 */         if (action_list.contains(Integer.valueOf(0))) {
/*  435 */           user.sendMessage("§3CoreProtect §f- Limited to action: \"-container\".");
/*      */         }
/*  437 */         else if (action_list.contains(Integer.valueOf(1))) {
/*  438 */           user.sendMessage("§3CoreProtect §f- Limited to action: \"+container\".");
/*      */         }
/*      */       }
/*  441 */       else if ((action_list.contains(Integer.valueOf(0))) && (action_list.contains(Integer.valueOf(1)))) {
/*  442 */         user.sendMessage("§3CoreProtect §f- Limited to action: \"block-change\".");
/*      */       }
/*  444 */       else if (action_list.contains(Integer.valueOf(0))) {
/*  445 */         user.sendMessage("§3CoreProtect §f- Limited to action: \"block-break\".");
/*      */       }
/*  447 */       else if (action_list.contains(Integer.valueOf(1))) {
/*  448 */         user.sendMessage("§3CoreProtect §f- Limited to action: \"block-place\".");
/*      */       }
/*  450 */       else if (action_list.contains(Integer.valueOf(3))) {
/*  451 */         user.sendMessage("§3CoreProtect §f- Limited to action: \"entity-kill\".");
/*      */       }
/*  453 */       if (restrict_list.size() > 0) {
/*  454 */         String r = "";
/*  455 */         int rc = 0;
/*  456 */         for (Object rt : restrict_list) {
/*  457 */           String value_name = "";
/*  458 */           if ((rt instanceof Material)) {
/*  459 */             value_name = ((Material)rt).name().toLowerCase();
/*      */           }
/*  461 */           else if ((rt instanceof EntityType)) {
/*  462 */             value_name = ((EntityType)rt).name().toLowerCase();
/*      */           }
/*      */           
/*  465 */           if (rc == 0) {
/*  466 */             r = "" + value_name + "";
/*      */           }
/*      */           else {
/*  469 */             r = r + ", " + value_name;
/*      */           }
/*  471 */           rc++;
/*      */         }
/*  473 */         user.sendMessage("§3CoreProtect §f- Limited to block type(s): " + r + ".");
/*      */       }
/*  475 */       if (exclude_list.size() > 0) {
/*  476 */         String e = "";
/*  477 */         int ec = 0;
/*  478 */         for (Object et : exclude_list) {
/*  479 */           String value_name = "";
/*  480 */           if ((et instanceof Material)) {
/*  481 */             value_name = ((Material)et).name().toLowerCase();
/*      */           }
/*  483 */           else if ((et instanceof EntityType)) {
/*  484 */             value_name = ((EntityType)et).name().toLowerCase();
/*      */           }
/*      */           
/*  487 */           if (ec == 0) {
/*  488 */             e = "" + value_name + "";
/*      */           }
/*      */           else {
/*  491 */             e = e + ", " + value_name;
/*      */           }
/*  493 */           ec++;
/*      */         }
/*  495 */         user.sendMessage("§3CoreProtect §f- Excluded block type(s): " + e + ".");
/*      */       }
/*  497 */       if (exclude_user_list.size() > 0) {
/*  498 */         String e = "";
/*  499 */         int ec = 0;
/*  500 */         for (String et : exclude_user_list) {
/*  501 */           if (ec == 0) {
/*  502 */             e = "" + et + "";
/*      */           }
/*      */           else {
/*  505 */             e = e + ", " + et;
/*      */           }
/*  507 */           ec++;
/*      */         }
/*  509 */         user.sendMessage("§3CoreProtect §f- Excluded user(s): " + e + ".");
/*      */       }
/*  511 */       if (action_list.contains(Integer.valueOf(5))) {
/*  512 */         user.sendMessage("§3CoreProtect §f- Approx. " + block_count + " item(s) changed.");
/*      */       }
/*  514 */       else if (preview == 0) {
/*  515 */         if (item_count > 0) {
/*  516 */           user.sendMessage("§3CoreProtect §f- Approx. " + item_count + " item(s) changed.");
/*      */         }
/*  518 */         if (entity_count > 0) {
/*  519 */           if (entity_count == 1) {
/*  520 */             user.sendMessage("§3CoreProtect §f- Approx. " + entity_count + " entity changed.");
/*      */           }
/*      */           else {
/*  523 */             user.sendMessage("§3CoreProtect §f- Approx. " + entity_count + " entities changed.");
/*      */           }
/*      */         }
/*  526 */         user.sendMessage("§3CoreProtect §f- Approx. " + block_count + " block(s) changed.");
/*      */       }
/*  528 */       else if (preview > 0) {
/*  529 */         user.sendMessage("§3CoreProtect §f- Approx. " + block_count + " block(s) to change.");
/*      */       }
/*  531 */       if ((verbose == true) && (preview == 0) && 
/*  532 */         (file > -1)) {
/*  533 */         user.sendMessage("§3CoreProtect §f- Modified " + file + " chunk(s).");
/*      */       }
/*      */       
/*      */ 
/*  537 */       if (preview == 0) {
/*  538 */         user.sendMessage("§3CoreProtect §f- Time taken: " + seconds + " second(s).");
/*      */       }
/*  540 */       user.sendMessage("-----");
/*  541 */       if (preview > 0) {
/*  542 */         user.sendMessage("§3CoreProtect §f- Please select: \"/co apply\" or \"/co cancel\".");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  546 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static String interaction_lookup(Statement statement, Block block, String user, int offset, int page, int limit) {
/*  551 */     String result = "";
/*      */     try {
/*  553 */       if (block == null) {
/*  554 */         return result;
/*      */       }
/*  556 */       boolean found = false;
/*  557 */       int x = block.getX();
/*  558 */       int y = block.getY();
/*  559 */       int z = block.getZ();
/*  560 */       int time = (int)(System.currentTimeMillis() / 1000L);
/*  561 */       int wid = Functions.getWorldId(block.getWorld().getName());
/*  562 */       int check_time = 0;
/*  563 */       int count = 0;
/*  564 */       int row_max = page * limit;
/*  565 */       int page_start = row_max - limit;
/*  566 */       if (offset > 0) {
/*  567 */         check_time = time - offset;
/*      */       }
/*  569 */       String query = "SELECT COUNT(*) as count from " + Config.prefix + "block WHERE wid = '" + wid + "' AND x = '" + x + "' AND z = '" + z + "' AND y = '" + y + "' AND action='2' AND time >= '" + check_time + "' LIMIT 0, 1";
/*  570 */       ResultSet rs = statement.executeQuery(query);
/*  571 */       while (rs.next()) {
/*  572 */         count = rs.getInt("count");
/*      */       }
/*  574 */       rs.close();
/*  575 */       int total_pages = (int)Math.ceil(count / (limit + 0.0D));
/*      */       
/*  577 */       query = "SELECT time,user,action,type,data,rolled_back FROM " + Config.prefix + "block WHERE wid = '" + wid + "' AND x = '" + x + "' AND z = '" + z + "' AND y = '" + y + "' AND action='2' AND time >= '" + check_time + "' ORDER BY rowid DESC LIMIT " + page_start + ", " + limit + "";
/*  578 */       rs = statement.executeQuery(query);
/*  579 */       while (rs.next()) {
/*  580 */         int result_userid = rs.getInt("user");
/*  581 */         int result_action = rs.getInt("action");
/*  582 */         int result_type = rs.getInt("type");
/*  583 */         int result_data = rs.getInt("data");
/*  584 */         int result_time = rs.getInt("time");
/*  585 */         int result_rolled_back = rs.getInt("rolled_back");
/*  586 */         if (Config.player_id_cache_reversed.get(Integer.valueOf(result_userid)) == null) {
/*  587 */           Database.loadUserName(statement.getConnection(), result_userid);
/*      */         }
/*  589 */         String result_user = (String)Config.player_id_cache_reversed.get(Integer.valueOf(result_userid));
/*  590 */         double time_since = time - (result_time + 0.0D);
/*  591 */         time_since /= 60.0D;
/*  592 */         time_since /= 60.0D;
/*  593 */         String timeago = new DecimalFormat("0.00").format(time_since);
/*  594 */         if (!found) {
/*  595 */           result = "§f----- §3Player Interactions §f----- §7(x" + x + "/y" + y + "/z" + z + ")\n";
/*      */         }
/*  597 */         found = true;
/*  598 */         String a2 = "placed";
/*  599 */         if (result_action == 0) {
/*  600 */           a2 = "removed";
/*      */         }
/*  602 */         else if (result_action == 2) {
/*  603 */           a2 = "clicked";
/*      */         }
/*  605 */         String rbd = "";
/*  606 */         if (result_rolled_back == 1) {
/*  607 */           rbd = "§m";
/*      */         }
/*  609 */         String dname = Functions.getType(result_type).name().toLowerCase();
/*  610 */         dname = Functions.nameFilter(dname, result_data);
/*  611 */         if (dname.length() > 0) {
/*  612 */           dname = "minecraft:" + dname.toLowerCase() + "";
/*      */         }
/*      */         
/*      */ 
/*  616 */         if (dname.contains("minecraft:")) {
/*  617 */           String[] block_name_split = dname.split(":");
/*  618 */           dname = block_name_split[1];
/*      */         }
/*      */         
/*  621 */         result = result + "§7" + timeago + "/h ago §f- §3" + rbd + "" + result_user + " §f" + rbd + "" + a2 + " §3" + rbd + "" + dname + "§f.\n";
/*      */       }
/*  623 */       rs.close();
/*  624 */       if (found == true) {
/*  625 */         if (count > limit) {
/*  626 */           String n = "§f-----\n";
/*  627 */           n = n + "§fPage " + page + "/" + total_pages + ". View older data by typing \"§3/co l <page>§f\".\n";
/*  628 */           result = result + n;
/*      */         }
/*      */       }
/*  631 */       else if (!found) {
/*  632 */         if ((row_max > count) && (count > 0)) {
/*  633 */           result = "§3CoreProtect §f- §fNo player interactions found for that page.";
/*      */         }
/*      */         else {
/*  636 */           result = "§3CoreProtect §f- §fNo player interactions found for this location.";
/*      */         }
/*      */       }
/*  639 */       String bc = x + "." + y + "." + z + "." + wid + ".2." + limit;
/*  640 */       Config.lookup_page.put(user, Integer.valueOf(page));
/*  641 */       Config.lookup_type.put(user, Integer.valueOf(7));
/*  642 */       Config.lookup_command.put(user, bc);
/*      */     }
/*      */     catch (Exception e) {
/*  645 */       e.printStackTrace();
/*      */     }
/*  647 */     return result;
/*      */   }
/*      */   
/*      */   public static void modifyContainerItems(Material type, Object container, int slot, ItemStack itemstack, int action) {
/*      */     try {
/*  652 */       ItemStack[] contents = null;
/*  653 */       if (type.equals(Material.ARMOR_STAND)) {
/*  654 */         EntityEquipment equipment = (EntityEquipment)container;
/*  655 */         if (equipment != null) {
/*  656 */           contents = equipment.getArmorContents();
/*  657 */           if (action == 1) {
/*  658 */             itemstack.setAmount(1);
/*      */           }
/*      */           else {
/*  661 */             itemstack.setType(Material.AIR);
/*  662 */             itemstack.setAmount(0);
/*      */           }
/*  664 */           if (slot >= 0) {
/*  665 */             contents[slot] = itemstack;
/*      */           }
/*  667 */           equipment.setArmorContents(contents);
/*      */         }
/*      */       }
/*      */       else {
/*  671 */         Inventory inventory = (Inventory)container;
/*  672 */         if (inventory != null) {
/*  673 */           int count = 0;
/*  674 */           int amount = itemstack.getAmount();
/*  675 */           itemstack.setAmount(1);
/*  676 */           while (count < amount) {
/*  677 */             if (action == 1) {
/*  678 */               inventory.addItem(new ItemStack[] { itemstack });
/*      */             }
/*      */             else {
/*  681 */               inventory.removeItem(new ItemStack[] { itemstack });
/*      */             }
/*  683 */             count++;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  689 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static void performContainerRollbackRestore(Statement statement, CommandSender user, List<String> check_uuids, List<String> check_users, String time_string, List<Object> restrict_list, List<Object> exclude_list, List<String> exclude_user_list, List<Integer> action_list, final Location location, Integer[] radius, int check_time, boolean restrict_world, boolean lookup, boolean verbose, final int rollback_type) {
/*      */     try {
/*  695 */       long time1 = System.currentTimeMillis();
/*  696 */       final List<Object[]> lookup_list = performLookupRaw(statement, user, check_uuids, check_users, restrict_list, exclude_list, exclude_user_list, action_list, location, radius, check_time, -1, -1, restrict_world, lookup);
/*  697 */       if (rollback_type == 1) {
/*  698 */         Collections.reverse(lookup_list);
/*      */       }
/*      */       
/*  701 */       String user_string = "#server";
/*  702 */       if (user != null) {
/*  703 */         user_string = user.getName();
/*      */       }
/*  705 */       Queue.queueContainerRollbackUpdate(user_string, location, lookup_list, rollback_type);
/*      */       
/*  707 */       String final_user_string = user_string;
/*  708 */       Config.rollback_hash.put(user_string, new int[] { 0, 0, 0, 0 });
/*  709 */       CoreProtect.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CoreProtect.getInstance(), new Runnable()
/*      */       {
/*      */         public void run() {
/*      */           try {
/*  713 */             int[] rollback_hash_data = (int[])Config.rollback_hash.get(this.val$final_user_string);
/*  714 */             int item_count = rollback_hash_data[0];
/*      */             
/*  716 */             int entity_count = rollback_hash_data[2];
/*  717 */             Block block = location.getBlock();
/*  718 */             if (!block.getWorld().isChunkLoaded(block.getChunk())) {
/*  719 */               block.getWorld().loadChunk(block.getChunk());
/*      */             }
/*  721 */             Object container = null;
/*  722 */             Material type = block.getType();
/*      */             
/*  724 */             if (BlockInfo.containers.contains(type)) {
/*  725 */               container = Functions.getContainerInventory(block.getState(), false);
/*      */             }
/*      */             else {
/*  728 */               for (Entity entity : block.getChunk().getEntities()) {
/*  729 */                 if (((entity instanceof ArmorStand)) && 
/*  730 */                   (entity.getLocation().getBlockX() == location.getBlockX()) && (entity.getLocation().getBlockY() == location.getBlockY()) && (entity.getLocation().getBlockZ() == location.getBlockZ())) {
/*  731 */                   type = Material.ARMOR_STAND;
/*  732 */                   container = Functions.getEntityEquipment((LivingEntity)entity);
/*      */                 }
/*      */               }
/*      */             }
/*      */             
/*  737 */             int modify_count = 0;
/*  738 */             if (container != null) {
/*  739 */               for (Object[] row : lookup_list)
/*      */               {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  747 */                 int row_type_raw = ((Integer)row[6]).intValue();
/*  748 */                 int row_data = ((Integer)row[7]).intValue();
/*  749 */                 int row_action = ((Integer)row[8]).intValue();
/*  750 */                 int row_rolled_back = ((Integer)row[9]).intValue();
/*      */                 
/*  752 */                 int row_amount = ((Integer)row[11]).intValue();
/*  753 */                 byte[] row_metadata = (byte[])row[12];
/*  754 */                 Material row_type = Functions.getType(row_type_raw);
/*      */                 
/*  756 */                 if (((rollback_type == 0) && (row_rolled_back == 0)) || ((rollback_type == 1) && (row_rolled_back == 1))) {
/*  757 */                   modify_count += row_amount;
/*  758 */                   int action = 0;
/*  759 */                   if ((rollback_type == 0) && (row_action == 0)) {
/*  760 */                     action = 1;
/*      */                   }
/*  762 */                   if ((rollback_type == 1) && (row_action == 1)) {
/*  763 */                     action = 1;
/*      */                   }
/*  765 */                   ItemStack itemstack = new ItemStack(row_type, row_amount, (short)row_data);
/*  766 */                   Object[] populatedStack = Lookup.populateItemStack(itemstack, row_metadata);
/*  767 */                   int slot = ((Integer)populatedStack[0]).intValue();
/*  768 */                   itemstack = (ItemStack)populatedStack[1];
/*  769 */                   Lookup.modifyContainerItems(type, container, slot, itemstack, action);
/*      */                 }
/*      */               }
/*      */             }
/*  773 */             Config.rollback_hash.put(this.val$final_user_string, new int[] { item_count, modify_count, entity_count, 1 });
/*      */           }
/*      */           catch (Exception e) {
/*  776 */             e.printStackTrace(); } } }, 0L);
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*  781 */       int[] rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/*  782 */       int next = rollback_hash_data[3];
/*  783 */       int sleep_time = 0;
/*  784 */       while (next == 0) {
/*  785 */         sleep_time += 5;
/*  786 */         Thread.sleep(5L);
/*  787 */         rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/*  788 */         next = rollback_hash_data[3];
/*  789 */         if (sleep_time > 300000) {
/*  790 */           System.out.println("[CoreProtect] Rollback or restore aborted.");
/*      */         }
/*      */       }
/*      */       
/*  794 */       rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/*  795 */       int block_count = rollback_hash_data[1];
/*  796 */       long time2 = System.currentTimeMillis();
/*  797 */       int seconds = (int)((time2 - time1) / 1000L);
/*      */       
/*  799 */       if (user != null) {
/*  800 */         int file = -1;
/*  801 */         if (block_count > 0) {
/*  802 */           file = 1;
/*      */         }
/*  804 */         int item_count = 0;
/*  805 */         int entity_count = 0;
/*  806 */         finishRollbackRestore(user, location, check_users, restrict_list, exclude_list, exclude_user_list, action_list, time_string, file, seconds, item_count, block_count, entity_count, rollback_type, radius, verbose, restrict_world, 0);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  810 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static List<String[]> performLookup(Statement statement, CommandSender user, List<String> check_uuids, List<String> check_users, List<Object> restrict_list, List<Object> exclude_list, List<String> exclude_user_list, List<Integer> action_list, Location location, Integer[] radius, int check_time, boolean restrict_world, boolean lookup) {
/*  815 */     List<String[]> new_list = new ArrayList();
/*      */     try {
/*  817 */       List<Object[]> lookup_list = performLookupRaw(statement, user, check_uuids, check_users, restrict_list, exclude_list, exclude_user_list, action_list, location, radius, check_time, -1, -1, restrict_world, lookup);
/*  818 */       new_list = convertRawLookup(statement, lookup_list);
/*      */     }
/*      */     catch (Exception e) {
/*  821 */       e.printStackTrace();
/*      */     }
/*  823 */     return new_list;
/*      */   }
/*      */   
/*      */   public static List<Object[]> performLookupRaw(Statement statement, CommandSender user, List<String> check_uuids, List<String> check_users, List<Object> restrict_list, List<Object> exclude_list, List<String> exclude_user_list, List<Integer> action_list, Location location, Integer[] radius, int check_time, int limit_offset, int limit_count, boolean restrict_world, boolean lookup) {
/*  827 */     List<Object[]> list = new ArrayList();
/*  828 */     List<Integer> invalid_rollback_actions = new ArrayList();
/*  829 */     invalid_rollback_actions.add(Integer.valueOf(2));
/*  830 */     if ((((Integer)Config.config.get("rollback-entities")).intValue() == 0) && (!action_list.contains(Integer.valueOf(3)))) {
/*  831 */       invalid_rollback_actions.add(Integer.valueOf(3));
/*      */     }
/*      */     try {
/*  834 */       while (net.coreprotect.consumer.Consumer.is_paused == true) {
/*  835 */         Thread.sleep(1L);
/*      */       }
/*  837 */       net.coreprotect.consumer.Consumer.is_paused = true;
/*  838 */       ResultSet rs = rawLookupResultSet(statement, user, check_uuids, check_users, restrict_list, exclude_list, exclude_user_list, action_list, location, radius, check_time, limit_offset, limit_count, restrict_world, lookup, false);
/*  839 */       while (rs.next()) {
/*  840 */         if ((action_list.contains(Integer.valueOf(6))) || (action_list.contains(Integer.valueOf(7)))) {
/*  841 */           int result_id = rs.getInt("id");
/*  842 */           int result_time = rs.getInt("time");
/*  843 */           int result_userid = rs.getInt("user");
/*  844 */           String result_message = rs.getString("message");
/*  845 */           Object[] data_array = { Integer.valueOf(result_id), Integer.valueOf(result_time), Integer.valueOf(result_userid), result_message };
/*  846 */           list.add(data_array);
/*      */         }
/*  848 */         else if (action_list.contains(Integer.valueOf(8))) {
/*  849 */           int result_id = rs.getInt("id");
/*  850 */           int result_time = rs.getInt("time");
/*  851 */           int result_userid = rs.getInt("user");
/*  852 */           int result_wid = rs.getInt("wid");
/*  853 */           int result_x = rs.getInt("x");
/*  854 */           int result_y = rs.getInt("y");
/*  855 */           int result_z = rs.getInt("z");
/*  856 */           int result_action = rs.getInt("action");
/*  857 */           Object[] data_array = { Integer.valueOf(result_id), Integer.valueOf(result_time), Integer.valueOf(result_userid), Integer.valueOf(result_wid), Integer.valueOf(result_x), Integer.valueOf(result_y), Integer.valueOf(result_z), Integer.valueOf(result_action) };
/*  858 */           list.add(data_array);
/*      */         }
/*  860 */         else if (action_list.contains(Integer.valueOf(9))) {
/*  861 */           int result_id = rs.getInt("id");
/*  862 */           int result_time = rs.getInt("time");
/*  863 */           String result_uuid = rs.getString("uuid");
/*  864 */           String result_user = rs.getString("user");
/*  865 */           Object[] data_array = { Integer.valueOf(result_id), Integer.valueOf(result_time), result_uuid, result_user };
/*  866 */           list.add(data_array);
/*      */         }
/*      */         else {
/*  869 */           int result_amount = 0;
/*  870 */           byte[] result_meta = null;
/*  871 */           int result_id = rs.getInt("id");
/*  872 */           int result_userid = rs.getInt("user");
/*  873 */           int result_action = rs.getInt("action");
/*  874 */           int result_type = rs.getInt("type");
/*  875 */           int result_data = rs.getInt("data");
/*  876 */           int result_rolled_back = rs.getInt("rolled_back");
/*  877 */           int result_time = rs.getInt("time");
/*  878 */           int result_x = rs.getInt("x");
/*  879 */           int result_y = rs.getInt("y");
/*  880 */           int result_z = rs.getInt("z");
/*  881 */           int result_wid = rs.getInt("wid");
/*  882 */           if ((action_list.contains(Integer.valueOf(4))) || (action_list.contains(Integer.valueOf(5)))) {
/*  883 */             result_amount = rs.getInt("amount");
/*  884 */             result_meta = rs.getBytes("metadata");
/*      */           }
/*      */           else {
/*  887 */             result_meta = rs.getBytes("meta");
/*      */           }
/*  889 */           boolean valid = true;
/*  890 */           if ((!lookup) && 
/*  891 */             (invalid_rollback_actions.contains(Integer.valueOf(result_action)))) {
/*  892 */             valid = false;
/*      */           }
/*      */           
/*  895 */           if (valid == true) {
/*  896 */             if ((action_list.contains(Integer.valueOf(4))) || (action_list.contains(Integer.valueOf(5)))) {
/*  897 */               Object[] data_array = { Integer.valueOf(result_id), Integer.valueOf(result_time), Integer.valueOf(result_userid), Integer.valueOf(result_x), Integer.valueOf(result_y), Integer.valueOf(result_z), Integer.valueOf(result_type), Integer.valueOf(result_data), Integer.valueOf(result_action), Integer.valueOf(result_rolled_back), Integer.valueOf(result_wid), Integer.valueOf(result_amount), result_meta };
/*  898 */               list.add(data_array);
/*      */             }
/*      */             else {
/*  901 */               Object[] data_array = { Integer.valueOf(result_id), Integer.valueOf(result_time), Integer.valueOf(result_userid), Integer.valueOf(result_x), Integer.valueOf(result_y), Integer.valueOf(result_z), Integer.valueOf(result_type), Integer.valueOf(result_data), Integer.valueOf(result_action), Integer.valueOf(result_rolled_back), Integer.valueOf(result_wid), result_meta };
/*  902 */               list.add(data_array);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  907 */       rs.close();
/*      */     }
/*      */     catch (Exception e) {
/*  910 */       e.printStackTrace();
/*      */     }
/*  912 */     net.coreprotect.consumer.Consumer.is_paused = false;
/*  913 */     return list;
/*      */   }
/*      */   
/*      */   public static List<String[]> performPartialLookup(Statement statement, CommandSender user, List<String> check_uuids, List<String> check_users, List<Object> restrict_list, List<Object> exclude_list, List<String> exclude_user_list, List<Integer> action_list, Location location, Integer[] radius, int check_time, int limit_offset, int limit_count, boolean restrict_world, boolean lookup) {
/*  917 */     List<String[]> new_list = new ArrayList();
/*      */     try {
/*  919 */       List<Object[]> lookup_list = performLookupRaw(statement, user, check_uuids, check_users, restrict_list, exclude_list, exclude_user_list, action_list, location, radius, check_time, limit_offset, limit_count, restrict_world, lookup);
/*  920 */       new_list = convertRawLookup(statement, lookup_list);
/*      */     }
/*      */     catch (Exception e) {
/*  923 */       e.printStackTrace();
/*      */     }
/*  925 */     return new_list;
/*      */   }
/*      */   
/*      */   public static List<String[]> performRollbackRestore(Statement statement, CommandSender user, List<String> check_uuids, List<String> check_users, String time_string, List<Object> restrict_list, List<Object> exclude_list, List<String> exclude_user_list, List<Integer> action_list, Location location, Integer[] radius, int check_time, boolean restrict_world, boolean lookup, boolean verbose, final int rollback_type, final int preview) {
/*  929 */     List<String[]> list = new ArrayList();
/*      */     try {
/*  931 */       long time1 = System.currentTimeMillis();
/*  932 */       List<Object[]> lookup_list = new ArrayList();
/*  933 */       if ((!action_list.contains(Integer.valueOf(4))) && (!action_list.contains(Integer.valueOf(5))) && (!check_users.contains("#container"))) {
/*  934 */         lookup_list = performLookupRaw(statement, user, check_uuids, check_users, restrict_list, exclude_list, exclude_user_list, action_list, location, radius, check_time, -1, -1, restrict_world, lookup);
/*      */       }
/*  936 */       if (lookup_list == null) {
/*  937 */         return null;
/*      */       }
/*      */       
/*  940 */       List<Object[]> item_list = new ArrayList();
/*  941 */       if ((((Integer)Config.config.get("rollback-items")).intValue() == 1) && (!check_users.contains("#container")) && ((action_list.size() == 0) || (action_list.contains(Integer.valueOf(4)))) && (preview == 0)) {
/*  942 */         List<Integer> item_action_list = new ArrayList();
/*  943 */         item_action_list.addAll(action_list);
/*  944 */         if (!item_action_list.contains(Integer.valueOf(4))) {
/*  945 */           item_action_list.add(Integer.valueOf(4));
/*      */         }
/*  947 */         item_list = performLookupRaw(statement, user, check_uuids, check_users, restrict_list, exclude_list, exclude_user_list, item_action_list, location, radius, check_time, -1, -1, restrict_world, lookup);
/*      */       }
/*      */       
/*  950 */       TreeMap<String, Integer> chunk_list = new TreeMap();
/*  951 */       HashMap<String, ArrayList<Object[]>> data_list = new HashMap();
/*  952 */       HashMap<String, ArrayList<Object[]>> item_data_list = new HashMap();
/*  953 */       int list_c = 0;
/*  954 */       while (list_c < 2) {
/*  955 */         List<Object[]> scan_list = lookup_list;
/*  956 */         if (list_c == 1) {
/*  957 */           scan_list = item_list;
/*      */         }
/*  959 */         for (Object[] result : scan_list) {
/*  960 */           int user_id = ((Integer)result[2]).intValue();
/*  961 */           int chunk_x = ((Integer)result[3]).intValue() >> 4;
/*  962 */           int chunk_z = ((Integer)result[5]).intValue() >> 4;
/*      */           
/*      */ 
/*      */ 
/*  966 */           if (chunk_list.get(chunk_x + "." + chunk_z) == null) {
/*  967 */             int distance = 0;
/*  968 */             if (location != null) {
/*  969 */               distance = (int)Math.sqrt(Math.pow(((Integer)result[3]).intValue() - location.getBlockX(), 2.0D) + Math.pow(((Integer)result[5]).intValue() - location.getBlockZ(), 2.0D));
/*      */             }
/*  971 */             chunk_list.put(chunk_x + "." + chunk_z, Integer.valueOf(distance));
/*      */           }
/*  973 */           if (Config.player_id_cache_reversed.get(Integer.valueOf(user_id)) == null) {
/*  974 */             Database.loadUserName(statement.getConnection(), user_id);
/*      */           }
/*  976 */           HashMap<String, ArrayList<Object[]>> modify_list = data_list;
/*  977 */           if (list_c == 1) {
/*  978 */             modify_list = item_data_list;
/*      */           }
/*  980 */           if (modify_list.get(chunk_x + "." + chunk_z) == null) {
/*  981 */             data_list.put(chunk_x + "." + chunk_z, new ArrayList());
/*  982 */             item_data_list.put(chunk_x + "." + chunk_z, new ArrayList());
/*      */           }
/*  984 */           ((ArrayList)modify_list.get(chunk_x + "." + chunk_z)).add(result);
/*      */         }
/*  986 */         list_c++;
/*      */       }
/*      */       
/*  989 */       if (rollback_type == 1) {
/*  990 */         Iterator<Map.Entry<String, ArrayList<Object[]>>> it = data_list.entrySet().iterator();
/*  991 */         while (it.hasNext()) {
/*  992 */           Collections.reverse((List)((Map.Entry)it.next()).getValue());
/*      */         }
/*  994 */         it = item_data_list.entrySet().iterator();
/*  995 */         while (it.hasNext()) {
/*  996 */           Collections.reverse((List)((Map.Entry)it.next()).getValue());
/*      */         }
/*      */       }
/*      */       
/* 1000 */       int file = 0;
/* 1001 */       String user_string = "#server";
/* 1002 */       if (user != null) {
/* 1003 */         user_string = user.getName();
/* 1004 */         if ((verbose == true) && (preview == 0)) {
/* 1005 */           user.sendMessage("§3CoreProtect §f- Found " + chunk_list.size() + " chunk(s) to modify.");
/*      */         }
/*      */       }
/*      */       
/*      */ 
/* 1010 */       if (preview == 0) {
/* 1011 */         Queue.queueRollbackUpdate(user_string, location, lookup_list, rollback_type);
/* 1012 */         Queue.queueContainerRollbackUpdate(user_string, location, item_list, rollback_type);
/*      */       }
/*      */       
/* 1015 */       Config.rollback_hash.put(user_string, new int[] { 0, 0, 0, 0 });
/* 1016 */       final String final_user_string = user_string;
/* 1017 */       for (Map.Entry<String, Integer> entry : Functions.entriesSortedByValues(chunk_list)) {
/* 1018 */         file++;
/* 1019 */         int item_count = 0;
/* 1020 */         int block_count = 0;
/* 1021 */         int entity_count = 0;
/* 1022 */         int[] rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/* 1023 */         item_count = rollback_hash_data[0];
/* 1024 */         block_count = rollback_hash_data[1];
/* 1025 */         entity_count = rollback_hash_data[2];
/* 1026 */         String[] chunk_cords = ((String)entry.getKey()).split("\\.");
/* 1027 */         final int final_chunk_x = Integer.parseInt(chunk_cords[0]);
/* 1028 */         final int final_chunk_z = Integer.parseInt(chunk_cords[1]);
/* 1029 */         final CommandSender final_user = user;
/* 1030 */         HashMap<String, ArrayList<Object[]>> final_block_list = data_list;
/* 1031 */         final HashMap<String, ArrayList<Object[]>> final_item_list = item_data_list;
/* 1032 */         Config.rollback_hash.put(final_user_string, new int[] { item_count, block_count, entity_count, 0 });
/* 1033 */         CoreProtect.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CoreProtect.getInstance(), new Runnable()
/*      */         {
/*      */           public void run() {
/*      */             try {
/* 1037 */               boolean clearInventories = false;
/* 1038 */               if (((Integer)Config.config.get("rollback-items")).intValue() == 1) {
/* 1039 */                 clearInventories = true;
/*      */               }
/* 1041 */               ArrayList<Object[]> data = (ArrayList)this.val$final_block_list.get(final_chunk_x + "." + final_chunk_z);
/* 1042 */               ArrayList<Object[]> item_data = (ArrayList)final_item_list.get(final_chunk_x + "." + final_chunk_z);
/* 1043 */               Map<String, Integer> hanging_delay = new HashMap();
/* 1044 */               for (Object[] row : data) {
/* 1045 */                 int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 1046 */                 int[] rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/* 1047 */                 int item_count = rollback_hash_data[0];
/* 1048 */                 int block_count = rollback_hash_data[1];
/* 1049 */                 int entity_count = rollback_hash_data[2];
/*      */                 
/* 1051 */                 int row_time = ((Integer)row[1]).intValue();
/* 1052 */                 int row_userid = ((Integer)row[2]).intValue();
/* 1053 */                 int row_x = ((Integer)row[3]).intValue();
/* 1054 */                 int row_y = ((Integer)row[4]).intValue();
/* 1055 */                 int row_z = ((Integer)row[5]).intValue();
/* 1056 */                 int row_type_raw = ((Integer)row[6]).intValue();
/* 1057 */                 int row_data = ((Integer)row[7]).intValue();
/* 1058 */                 int row_action = ((Integer)row[8]).intValue();
/* 1059 */                 int row_rolled_back = ((Integer)row[9]).intValue();
/* 1060 */                 int row_wid = ((Integer)row[10]).intValue();
/* 1061 */                 byte[] row_meta = (byte[])row[11];
/* 1062 */                 Material row_type = Functions.getType(row_type_raw);
/*      */                 
/* 1064 */                 List<Object> meta = null;
/* 1065 */                 if (row_meta != null) {
/* 1066 */                   ByteArrayInputStream bais = new ByteArrayInputStream(row_meta);
/* 1067 */                   ObjectInputStream ins = new ObjectInputStream(bais);
/*      */                   
/* 1069 */                   List<Object> list = (List)ins.readObject();
/* 1070 */                   meta = list;
/*      */                 }
/*      */                 
/* 1073 */                 String row_user = (String)Config.player_id_cache_reversed.get(Integer.valueOf(row_userid));
/* 1074 */                 int old_type_raw = row_type_raw;
/* 1075 */                 Material old_type_material = Functions.getType(old_type_raw);
/*      */                 
/* 1077 */                 if ((row_action == 1) && (rollback_type == 0)) {
/* 1078 */                   row_type = Material.AIR;
/* 1079 */                   row_type_raw = 0;
/*      */                 }
/* 1081 */                 else if ((row_action == 0) && (rollback_type == 1)) {
/* 1082 */                   row_type = Material.AIR;
/* 1083 */                   row_type_raw = 0;
/*      */                 }
/* 1085 */                 else if ((row_action == 4) && (rollback_type == 0)) {
/* 1086 */                   row_type = null;
/* 1087 */                   row_type_raw = 0;
/*      */                 }
/* 1089 */                 else if ((row_action == 3) && (rollback_type == 1)) {
/* 1090 */                   row_type = null;
/* 1091 */                   row_type_raw = 0;
/*      */                 }
/*      */                 
/* 1094 */                 if (preview > 0) {
/* 1095 */                   if (row_action != 3) {
/* 1096 */                     Player player = (Player)final_user;
/* 1097 */                     String world = Functions.getWorldName(row_wid);
/* 1098 */                     Location location = new Location(CoreProtect.getInstance().getServer().getWorld(world), row_x, row_y, row_z);
/* 1099 */                     if (preview == 2) {
/* 1100 */                       Block block = location.getBlock();
/* 1101 */                       Material block_type = block.getType();
/* 1102 */                       byte block_data = Functions.getData(block);
/* 1103 */                       if ((!block_type.equals(Material.PAINTING)) && (!block_type.equals(Material.ITEM_FRAME)) && (!block_type.equals(Material.ARMOR_STAND))) {
/* 1104 */                         Functions.sendBlockChange(player, location, block_type, block_data);
/* 1105 */                         block_count++;
/*      */                       }
/*      */                       
/*      */                     }
/* 1109 */                     else if ((!row_type.equals(Material.PAINTING)) && (!row_type.equals(Material.ITEM_FRAME)) && (!row_type.equals(Material.ARMOR_STAND))) {
/* 1110 */                       Functions.sendBlockChange(player, location, row_type, (byte)row_data);
/* 1111 */                       block_count++;
/*      */                     }
/*      */                   }
/*      */                 } else { boolean removed;
/*      */                   int entity_id;
/* 1116 */                   if (row_action == 3) {
/* 1117 */                     String world = Functions.getWorldName(row_wid);
/* 1118 */                     Block block = CoreProtect.getInstance().getServer().getWorld(world).getBlockAt(row_x, row_y, row_z);
/* 1119 */                     if (!CoreProtect.getInstance().getServer().getWorld(world).isChunkLoaded(block.getChunk())) {
/* 1120 */                       CoreProtect.getInstance().getServer().getWorld(world).loadChunk(block.getChunk());
/*      */                     }
/* 1122 */                     if (row_type_raw > 0)
/*      */                     {
/* 1124 */                       if (row_rolled_back == 0) {
/* 1125 */                         EntityType entity_type = Functions.getEntityType(row_type_raw);
/* 1126 */                         Lookup.queueEntitySpawn(row_user, block.getState(), entity_type, row_data);
/* 1127 */                         entity_count++;
/*      */                       }
/*      */                     }
/* 1130 */                     else if (old_type_raw > 0)
/*      */                     {
/* 1132 */                       if (row_rolled_back == 1) {
/* 1133 */                         removed = false;
/* 1134 */                         entity_id = -1;
/* 1135 */                         String entity_name = Functions.getEntityType(old_type_raw).name();
/* 1136 */                         String token = "" + row_x + "." + row_y + "." + row_z + "." + row_wid + "." + entity_name + "";
/* 1137 */                         Object[] cached_entity = (Object[])Config.entity_cache.get(token);
/* 1138 */                         if (cached_entity != null) {
/* 1139 */                           entity_id = ((Integer)cached_entity[1]).intValue();
/*      */                         }
/* 1141 */                         int xmin = row_x - 5;
/* 1142 */                         int xmax = row_x + 5;
/* 1143 */                         int ymin = row_y - 1;
/* 1144 */                         int ymax = row_y + 1;
/* 1145 */                         int zmin = row_z - 5;
/* 1146 */                         int zmax = row_z + 5;
/* 1147 */                         for (Entity e : block.getChunk().getEntities()) {
/* 1148 */                           if (entity_id > -1) {
/* 1149 */                             int id = e.getEntityId();
/* 1150 */                             if (id == entity_id) {
/* 1151 */                               entity_count++;
/* 1152 */                               removed = true;
/* 1153 */                               e.remove();
/* 1154 */                               break;
/*      */                             }
/*      */                             
/*      */                           }
/* 1158 */                           else if (e.getType().equals(Functions.getEntityType(old_type_raw))) {
/* 1159 */                             Location el = e.getLocation();
/* 1160 */                             int e_x = el.getBlockX();
/* 1161 */                             int e_y = el.getBlockY();
/* 1162 */                             int e_z = el.getBlockZ();
/* 1163 */                             if ((e_x >= xmin) && (e_x <= xmax) && (e_y >= ymin) && (e_y <= ymax) && (e_z >= zmin) && (e_z <= zmax)) {
/* 1164 */                               entity_count++;
/* 1165 */                               removed = true;
/* 1166 */                               e.remove();
/* 1167 */                               break;
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                         
/* 1172 */                         if ((!removed) && (entity_id > -1)) {
/* 1173 */                           for (Entity e : block.getWorld().getLivingEntities()) {
/* 1174 */                             int id = e.getEntityId();
/* 1175 */                             if (id == entity_id) {
/* 1176 */                               entity_count++;
/* 1177 */                               removed = true;
/* 1178 */                               e.remove();
/* 1179 */                               break;
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                   else {
/* 1187 */                     List<Material> update_state = Arrays.asList(new Material[] { Material.POWERED_RAIL, Material.DETECTOR_RAIL, Material.TORCH, Material.REDSTONE_WIRE, Material.BURNING_FURNACE, Material.LEVER, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON, Material.GLOWSTONE, Material.JACK_O_LANTERN, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.REDSTONE_LAMP_ON, Material.BEACON, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON, Material.DAYLIGHT_DETECTOR, Material.REDSTONE_BLOCK, Material.HOPPER, Material.ACTIVATOR_RAIL });
/*      */                     
/* 1189 */                     String world = Functions.getWorldName(row_wid);
/* 1190 */                     Block block = CoreProtect.getInstance().getServer().getWorld(world).getBlockAt(row_x, row_y, row_z);
/* 1191 */                     if (!CoreProtect.getInstance().getServer().getWorld(world).isChunkLoaded(block.getChunk())) {
/* 1192 */                       CoreProtect.getInstance().getServer().getWorld(world).loadChunk(block.getChunk());
/*      */                     }
/*      */                     
/* 1195 */                     boolean change_block = true;
/* 1196 */                     boolean count_block = true;
/* 1197 */                     Material ctype = block.getType();
/* 1198 */                     int cdata = Functions.getData(block);
/*      */                     
/* 1200 */                     if ((row_rolled_back == 1) && (rollback_type == 0)) {
/* 1201 */                       count_block = false;
/*      */                     }
/*      */                     
/* 1204 */                     if ((row_type.equals(ctype)) && (!old_type_material.equals(Material.PAINTING)) && (!old_type_material.equals(Material.ITEM_FRAME)) && (!old_type_material.equals(Material.ARMOR_STAND)) && (!old_type_material.equals(Material.END_CRYSTAL)))
/*      */                     {
/* 1206 */                       if (row_data == cdata) {
/* 1207 */                         change_block = false;
/*      */                       }
/* 1209 */                       count_block = false;
/*      */                     }
/* 1211 */                     else if (!ctype.equals(Material.AIR)) {
/* 1212 */                       count_block = true; }
/*      */                     List<Material> c2;
/*      */                     int c;
/* 1215 */                     if (count_block == true) {
/* 1216 */                       List<Material> c1 = Arrays.asList(new Material[] { Material.GRASS, Material.WATER, Material.LAVA });
/* 1217 */                       c2 = Arrays.asList(new Material[] { Material.DIRT, Material.STATIONARY_WATER, Material.STATIONARY_LAVA });
/* 1218 */                       c = 0;
/* 1219 */                       for (Material cv1 : c1) {
/* 1220 */                         Material cv2 = (Material)c2.get(c);
/* 1221 */                         if (((row_type.equals(cv1)) && (ctype.equals(cv2))) || ((row_type.equals(cv2)) && (ctype.equals(cv1)))) {
/* 1222 */                           count_block = false;
/*      */                         }
/* 1224 */                         c++;
/*      */                       }
/*      */                     }
/*      */                     try
/*      */                     {
/* 1229 */                       if (change_block == true)
/* 1230 */                         if ((row_type.equals(Material.AIR)) && ((old_type_material.equals(Material.PAINTING)) || (old_type_material.equals(Material.ITEM_FRAME)))) {
/* 1231 */                           int delay = Functions.getHangingDelay(hanging_delay, row_wid, row_x, row_y, row_z);
/* 1232 */                           Lookup.queueHangingRemove(row_user, block.getState(), delay);
/*      */                         }
/* 1234 */                         else if ((row_type.equals(Material.DOUBLE_PLANT)) || ((row_type.equals(Material.AIR)) && (old_type_material.equals(Material.DOUBLE_PLANT)))) {
/* 1235 */                           if (row_data < 8) {
/* 1236 */                             int top_data = 8;
/* 1237 */                             if ((row_data == 0) || (row_data == 4)) {
/* 1238 */                               top_data = 9;
/*      */                             }
/* 1240 */                             Block block_above = CoreProtect.getInstance().getServer().getWorld(world).getBlockAt(row_x, row_y + 1, row_z);
/* 1241 */                             Functions.setTypeAndData(block, row_type, (byte)row_data, false);
/* 1242 */                             Functions.setTypeAndData(block_above, row_type, (byte)top_data, false);
/*      */                           }
/*      */                         }
/* 1245 */                         else if ((row_type.equals(Material.PAINTING)) || (row_type.equals(Material.ITEM_FRAME))) {
/* 1246 */                           int delay = Functions.getHangingDelay(hanging_delay, row_wid, row_x, row_y, row_z);
/* 1247 */                           Lookup.queueHangingSpawn(row_user, block.getState(), row_type, row_data, delay);
/*      */                         }
/* 1249 */                         else if (row_type.equals(Material.ARMOR_STAND)) {
/* 1250 */                           Location location = block.getLocation();
/* 1251 */                           location.setX(location.getX() + 0.5D);
/* 1252 */                           location.setZ(location.getZ() + 0.5D);
/* 1253 */                           location.setYaw(row_data);
/* 1254 */                           boolean exists = false;
/* 1255 */                           for (Entity entity : block.getChunk().getEntities()) {
/* 1256 */                             if (((entity instanceof ArmorStand)) && 
/* 1257 */                               (entity.getLocation().getBlockX() == location.getBlockX()) && (entity.getLocation().getBlockY() == location.getBlockY()) && (entity.getLocation().getBlockZ() == location.getBlockZ())) {
/* 1258 */                               exists = true;
/*      */                             }
/*      */                           }
/*      */                           
/* 1262 */                           if (!exists) {
/* 1263 */                             Entity entity = block.getLocation().getWorld().spawnEntity(location, EntityType.ARMOR_STAND);
/* 1264 */                             entity.teleport(location);
/*      */                           }
/*      */                         }
/* 1267 */                         else if (row_type.equals(Material.END_CRYSTAL)) {
/* 1268 */                           Location location = block.getLocation();
/* 1269 */                           location.setX(location.getX() + 0.5D);
/* 1270 */                           location.setZ(location.getZ() + 0.5D);
/* 1271 */                           boolean exists = false;
/* 1272 */                           for (Entity entity : block.getChunk().getEntities()) {
/* 1273 */                             if (((entity instanceof EnderCrystal)) && 
/* 1274 */                               (entity.getLocation().getBlockX() == location.getBlockX()) && (entity.getLocation().getBlockY() == location.getBlockY()) && (entity.getLocation().getBlockZ() == location.getBlockZ())) {
/* 1275 */                               exists = true;
/*      */                             }
/*      */                           }
/*      */                           
/* 1279 */                           if (!exists) {
/* 1280 */                             Entity entity = block.getLocation().getWorld().spawnEntity(location, EntityType.ENDER_CRYSTAL);
/* 1281 */                             EnderCrystal enderCrystal = (EnderCrystal)entity;
/* 1282 */                             enderCrystal.setShowingBottom(row_data != 0);
/* 1283 */                             entity.teleport(location);
/*      */                           }
/*      */                         }
/* 1286 */                         else if ((row_type.equals(Material.AIR)) && (old_type_material.equals(Material.END_CRYSTAL))) {
/* 1287 */                           for (Entity entity : block.getChunk().getEntities()) {
/* 1288 */                             if (((entity instanceof EnderCrystal)) && 
/* 1289 */                               (entity.getLocation().getBlockX() == row_x) && (entity.getLocation().getBlockY() == row_y) && (entity.getLocation().getBlockZ() == row_z)) {
/* 1290 */                               entity.remove();
/*      */                             }
/*      */                             
/*      */                           }
/*      */                         }
/* 1295 */                         else if ((rollback_type != 0) || (row_action != 0) || (!row_type.equals(Material.AIR)))
/*      */                         {
/*      */ 
/* 1298 */                           if ((row_type.equals(Material.AIR)) || (row_type.equals(Material.TNT))) {
/* 1299 */                             if (clearInventories) {
/* 1300 */                               if (BlockInfo.containers.contains(ctype)) {
/* 1301 */                                 Inventory inventory = Functions.getContainerInventory(block.getState(), false);
/* 1302 */                                 if (inventory != null) {
/* 1303 */                                   inventory.clear();
/*      */                                 }
/*      */                               }
/* 1306 */                               else if ((BlockInfo.containers.contains(Material.ARMOR_STAND)) && 
/* 1307 */                                 (old_type_material.equals(Material.ARMOR_STAND))) {
/* 1308 */                                 for (Entity entity : block.getChunk().getEntities()) {
/* 1309 */                                   if (((entity instanceof ArmorStand)) && 
/* 1310 */                                     (entity.getLocation().getBlockX() == row_x) && (entity.getLocation().getBlockY() == row_y) && (entity.getLocation().getBlockZ() == row_z)) {
/* 1311 */                                     EntityEquipment equipment = Functions.getEntityEquipment((LivingEntity)entity);
/* 1312 */                                     if (equipment != null) {
/* 1313 */                                       equipment.clear();
/*      */                                     }
/* 1315 */                                     Location location = entity.getLocation();
/* 1316 */                                     location.setY(location.getY() - 1.0D);
/* 1317 */                                     entity.teleport(location);
/* 1318 */                                     entity.remove();
/*      */                                   }
/*      */                                 }
/*      */                               }
/*      */                             }
/*      */                             
/*      */ 
/* 1325 */                             Functions.setTypeAndData(block, row_type, (byte)row_data, false);
/* 1326 */                             if (count_block == true) {
/* 1327 */                               block_count++;
/*      */                             }
/*      */                           }
/* 1330 */                           else if (row_type.equals(Material.MOB_SPAWNER)) {
/*      */                             try {
/* 1332 */                               Functions.setTypeAndData(block, row_type, (byte)0, false);
/* 1333 */                               CreatureSpawner mobSpawner = (CreatureSpawner)block.getState();
/* 1334 */                               mobSpawner.setSpawnedType(Functions.getSpawnerType(row_data));
/* 1335 */                               if (count_block == true) {
/* 1336 */                                 block_count++;
/*      */                               }
/*      */                               
/*      */ 
/*      */                             }
/*      */                             catch (Exception e) {}
/*      */                           }
/* 1343 */                           else if (row_type.equals(Material.SKULL)) {
/* 1344 */                             block.setType(row_type, false);
/* 1345 */                             Lookup.queueSkullUpdate(row_user, block.getState(), row_data);
/* 1346 */                             if (count_block == true) {
/* 1347 */                               block_count++;
/*      */                             }
/*      */                           }
/* 1350 */                           else if ((row_type.equals(Material.SIGN_POST)) || (row_type.equals(Material.WALL_SIGN))) {
/* 1351 */                             Functions.setTypeAndData(block, row_type, (byte)row_data, false);
/* 1352 */                             Lookup.queueSignUpdate(row_user, block.getState(), rollback_type, row_time);
/* 1353 */                             if (count_block == true)
/* 1354 */                               block_count++;
/*      */                           } else {
/*      */                             Inventory inventory;
/* 1357 */                             if (BlockInfo.shulker_boxes.contains(row_type)) {
/* 1358 */                               Functions.setTypeAndData(block, row_type, (byte)row_data, false);
/* 1359 */                               if (count_block == true) {
/* 1360 */                                 block_count++;
/*      */                               }
/* 1362 */                               if (meta != null) {
/* 1363 */                                 inventory = Functions.getContainerInventory(block.getState(), false);
/* 1364 */                                 for (Object value : meta)
/* 1365 */                                   if ((value instanceof Map))
/*      */                                   {
/* 1367 */                                     Map<Integer, Object> itemMap = (Map)value;
/*      */                                     
/* 1369 */                                     ItemStack item = ItemStack.deserialize((Map)itemMap.get(Integer.valueOf(0)));
/*      */                                     
/* 1371 */                                     List<List<Map<String, Object>>> metadata = (List)itemMap.get(Integer.valueOf(1));
/*      */                                     
/* 1373 */                                     Object[] populatedStack = Lookup.populateItemStack(item, metadata);
/* 1374 */                                     item = (ItemStack)populatedStack[1];
/* 1375 */                                     Lookup.modifyContainerItems(item.getType(), inventory, 0, item, 1);
/*      */                                   }
/*      */                               }
/*      */                             } else {
/*      */                               CommandBlock command_block;
/* 1380 */                               if (row_type.equals(Material.COMMAND)) {
/* 1381 */                                 Functions.setTypeAndData(block, row_type, (byte)row_data, false);
/* 1382 */                                 if (count_block == true) {
/* 1383 */                                   block_count++;
/*      */                                 }
/* 1385 */                                 if (meta != null) {
/* 1386 */                                   command_block = (CommandBlock)block.getState();
/* 1387 */                                   for (Object value : meta) {
/* 1388 */                                     if ((value instanceof String)) {
/* 1389 */                                       String string = (String)value;
/* 1390 */                                       command_block.setCommand(string);
/* 1391 */                                       command_block.update();
/*      */                                     }
/*      */                                   }
/*      */                                 }
/*      */                               }
/* 1396 */                               else if ((row_type.equals(Material.WALL_BANNER)) || (row_type.equals(Material.STANDING_BANNER))) {
/* 1397 */                                 Functions.setTypeAndData(block, row_type, (byte)row_data, false);
/* 1398 */                                 if (count_block == true) {
/* 1399 */                                   block_count++;
/*      */                                 }
/* 1401 */                                 if (meta != null) {
/* 1402 */                                   Banner banner = (Banner)block.getState();
/* 1403 */                                   for (Object value : meta) {
/* 1404 */                                     if ((value instanceof DyeColor)) {
/* 1405 */                                       banner.setBaseColor((DyeColor)value);
/*      */                                     }
/* 1407 */                                     else if ((value instanceof Map))
/*      */                                     {
/* 1409 */                                       Pattern pattern = new Pattern((Map)value);
/* 1410 */                                       banner.addPattern(pattern);
/*      */                                     }
/*      */                                   }
/* 1413 */                                   banner.update();
/*      */                                 }
/*      */                               }
/* 1416 */                               else if (update_state.contains(row_type)) {
/* 1417 */                                 Functions.setTypeAndData(block, row_type, (byte)row_data, true);
/* 1418 */                                 if (count_block == true) {
/* 1419 */                                   block_count++;
/*      */                                 }
/*      */                               }
/* 1422 */                               else if ((row_type != ctype) && (BlockInfo.containers.contains(row_type)) && (BlockInfo.containers.contains(ctype))) {
/* 1423 */                                 block.setType(Material.AIR);
/* 1424 */                                 Functions.setTypeAndData(block, row_type, (byte)row_data, false);
/* 1425 */                                 if (count_block == true) {
/* 1426 */                                   block_count++;
/*      */                                 }
/*      */                               }
/*      */                               else {
/* 1430 */                                 if (BlockInfo.containers.contains(row_type))
/*      */                                 {
/*      */ 
/* 1433 */                                   block.setType(row_type);
/* 1434 */                                   Functions.setData(block, (byte)row_data);
/*      */                                 }
/*      */                                 else
/*      */                                 {
/* 1438 */                                   Functions.setTypeAndData(block, row_type, (byte)row_data, false);
/*      */                                 }
/* 1440 */                                 if (count_block == true)
/* 1441 */                                   block_count++;
/*      */                               }
/*      */                             }
/*      */                           }
/*      */                         }
/*      */                     } catch (Exception e) {
/* 1447 */                       e.printStackTrace();
/*      */                     }
/*      */                     
/* 1450 */                     if ((!row_type.equals(Material.AIR)) && (change_block == true) && 
/* 1451 */                       (row_user.length() > 0)) {
/* 1452 */                       Config.lookup_cache.put("" + row_x + "." + row_y + "." + row_z + "." + row_wid + "", new Object[] { Integer.valueOf(unixtimestamp), row_user, row_type });
/*      */                     }
/*      */                   }
/*      */                 }
/*      */                 
/* 1457 */                 Config.rollback_hash.put(final_user_string, new int[] { item_count, block_count, entity_count, 0 });
/*      */               }
/* 1459 */               hanging_delay.clear();
/*      */               
/* 1461 */               Object container = null;
/* 1462 */               Material container_type = null;
/* 1463 */               boolean container_init = false;
/* 1464 */               int last_x = 0;
/* 1465 */               int last_y = 0;
/* 1466 */               int last_z = 0;
/* 1467 */               int last_wid = 0;
/* 1468 */               for (Object[] row : item_data) {
/* 1469 */                 int[] rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/* 1470 */                 int item_count = rollback_hash_data[0];
/* 1471 */                 int block_count = rollback_hash_data[1];
/* 1472 */                 int entity_count = rollback_hash_data[2];
/* 1473 */                 int row_x = ((Integer)row[3]).intValue();
/* 1474 */                 int row_y = ((Integer)row[4]).intValue();
/* 1475 */                 int row_z = ((Integer)row[5]).intValue();
/* 1476 */                 int row_type_raw = ((Integer)row[6]).intValue();
/* 1477 */                 int row_data = ((Integer)row[7]).intValue();
/* 1478 */                 int row_action = ((Integer)row[8]).intValue();
/* 1479 */                 int row_rolled_back = ((Integer)row[9]).intValue();
/* 1480 */                 int row_wid = ((Integer)row[10]).intValue();
/* 1481 */                 int row_amount = ((Integer)row[11]).intValue();
/* 1482 */                 byte[] row_metadata = (byte[])row[12];
/* 1483 */                 Material row_type = Functions.getType(row_type_raw);
/*      */                 
/* 1485 */                 if (((rollback_type == 0) && (row_rolled_back == 0)) || ((rollback_type == 1) && (row_rolled_back == 1))) {
/* 1486 */                   if ((!container_init) || (row_x != last_x) || (row_y != last_y) || (row_z != last_z) || (row_wid != last_wid)) {
/* 1487 */                     container = null;
/* 1488 */                     String world = Functions.getWorldName(row_wid);
/* 1489 */                     Block block = CoreProtect.getInstance().getServer().getWorld(world).getBlockAt(row_x, row_y, row_z);
/* 1490 */                     if (!CoreProtect.getInstance().getServer().getWorld(world).isChunkLoaded(block.getChunk())) {
/* 1491 */                       CoreProtect.getInstance().getServer().getWorld(world).loadChunk(block.getChunk());
/*      */                     }
/*      */                     
/* 1494 */                     if (BlockInfo.containers.contains(block.getType())) {
/* 1495 */                       container = Functions.getContainerInventory(block.getState(), false);
/* 1496 */                       container_type = block.getType();
/*      */                     }
/* 1498 */                     else if (BlockInfo.containers.contains(Material.ARMOR_STAND)) {
/* 1499 */                       for (Entity entity : block.getChunk().getEntities()) {
/* 1500 */                         if (((entity instanceof ArmorStand)) && 
/* 1501 */                           (entity.getLocation().getBlockX() == row_x) && (entity.getLocation().getBlockY() == row_y) && (entity.getLocation().getBlockZ() == row_z)) {
/* 1502 */                           container = Functions.getEntityEquipment((LivingEntity)entity);
/* 1503 */                           container_type = Material.ARMOR_STAND;
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                     
/*      */ 
/* 1509 */                     last_x = row_x;
/* 1510 */                     last_y = row_y;
/* 1511 */                     last_z = row_z;
/* 1512 */                     last_wid = row_wid;
/*      */                   }
/* 1514 */                   if (container != null) {
/* 1515 */                     int action = 0;
/* 1516 */                     if ((rollback_type == 0) && (row_action == 0)) {
/* 1517 */                       action = 1;
/*      */                     }
/* 1519 */                     if ((rollback_type == 1) && (row_action == 1)) {
/* 1520 */                       action = 1;
/*      */                     }
/* 1522 */                     ItemStack itemstack = new ItemStack(row_type, row_amount, (short)row_data);
/* 1523 */                     Object[] populatedStack = Lookup.populateItemStack(itemstack, row_metadata);
/* 1524 */                     int slot = ((Integer)populatedStack[0]).intValue();
/* 1525 */                     itemstack = (ItemStack)populatedStack[1];
/* 1526 */                     Lookup.modifyContainerItems(container_type, container, slot, itemstack, action);
/* 1527 */                     item_count += row_amount;
/*      */                   }
/* 1529 */                   container_init = true;
/*      */                 }
/* 1531 */                 Config.rollback_hash.put(final_user_string, new int[] { item_count, block_count, entity_count, 0 });
/*      */               }
/* 1533 */               int[] rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/* 1534 */               int item_count = rollback_hash_data[0];
/* 1535 */               int block_count = rollback_hash_data[1];
/* 1536 */               int entity_count = rollback_hash_data[2];
/* 1537 */               Config.rollback_hash.put(final_user_string, new int[] { item_count, block_count, entity_count, 1 });
/*      */               
/*      */ 
/* 1540 */               if (((final_user instanceof Player)) && (preview == 0)) {
/* 1541 */                 Player player = (Player)final_user;
/* 1542 */                 Location location = player.getLocation();
/* 1543 */                 Chunk chunk = location.getChunk();
/* 1544 */                 if ((chunk.getX() == final_chunk_x) && (chunk.getZ() == final_chunk_z)) {
/* 1545 */                   List<Integer> unsafe_blocks = Arrays.asList(new Integer[] { Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(51) });
/* 1546 */                   int player_x = location.getBlockX();
/* 1547 */                   int player_y = location.getBlockY();
/* 1548 */                   int player_z = location.getBlockZ();
/* 1549 */                   int check_y = player_y - 1;
/* 1550 */                   boolean safe_block = false;
/* 1551 */                   boolean place_safe = false;
/* 1552 */                   while (!safe_block) {
/* 1553 */                     int above = check_y + 1;
/* 1554 */                     if (above > 256) {
/* 1555 */                       above = 256;
/*      */                     }
/* 1557 */                     Block block_type1 = location.getWorld().getBlockAt(player_x, check_y, player_z);
/* 1558 */                     Block block_type2 = location.getWorld().getBlockAt(player_x, above, player_z);
/* 1559 */                     Material type1 = block_type1.getType();
/* 1560 */                     Material type2 = block_type2.getType();
/* 1561 */                     if ((BlockInfo.non_solid_entity_blocks.contains(type1)) && (BlockInfo.non_solid_entity_blocks.contains(type2))) {
/* 1562 */                       if (unsafe_blocks.contains(type1)) {
/* 1563 */                         place_safe = true;
/*      */                       }
/*      */                       else {
/* 1566 */                         safe_block = true;
/* 1567 */                         if (place_safe == true) {
/* 1568 */                           int below = check_y - 1;
/* 1569 */                           Block block_below = location.getWorld().getBlockAt(player_x, below, player_z);
/* 1570 */                           if (unsafe_blocks.contains(block_below.getType())) {
/* 1571 */                             block_type1.setType(Material.DIRT);
/* 1572 */                             check_y++;
/*      */                           }
/*      */                         }
/*      */                       }
/*      */                     }
/* 1577 */                     if (check_y >= 256) {
/* 1578 */                       safe_block = true;
/*      */                     }
/* 1580 */                     if ((safe_block == true) && (check_y > player_y)) {
/* 1581 */                       if (check_y > 256) {
/* 1582 */                         check_y = 256;
/*      */                       }
/* 1584 */                       location.setY(check_y);
/* 1585 */                       player.teleport(location);
/* 1586 */                       player.sendMessage("§3CoreProtect §f- Teleported you to safety.");
/* 1587 */                       if (place_safe == true) {
/* 1588 */                         player.sendMessage("§3CoreProtect §f- Placed a dirt block under you.");
/*      */                       }
/*      */                     }
/* 1591 */                     check_y++;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */             catch (Exception e) {
/* 1597 */               e.printStackTrace();
/* 1598 */               int[] rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/* 1599 */               int item_count = rollback_hash_data[0];
/* 1600 */               int block_count = rollback_hash_data[1];
/* 1601 */               int entity_count = rollback_hash_data[2];
/* 1602 */               Config.rollback_hash.put(final_user_string, new int[] { item_count, block_count, entity_count, 2 }); } } }, 0L);
/*      */         
/*      */ 
/*      */ 
/*      */ 
/* 1607 */         rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/* 1608 */         int next = rollback_hash_data[3];
/* 1609 */         int sleep_time = 0;
/* 1610 */         int abort = 0;
/* 1611 */         while (next == 0) {
/* 1612 */           if (preview == 1)
/*      */           {
/* 1614 */             sleep_time += 1;
/* 1615 */             Thread.sleep(1L);
/*      */           }
/*      */           else {
/* 1618 */             sleep_time += 5;
/* 1619 */             Thread.sleep(5L);
/*      */           }
/* 1621 */           rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/* 1622 */           next = rollback_hash_data[3];
/* 1623 */           if (sleep_time > 300000) {
/* 1624 */             abort = 1;
/*      */           }
/*      */         }
/*      */         
/*      */ 
/* 1629 */         if ((abort == 1) || (next == 2)) {
/* 1630 */           System.out.println("[CoreProtect] Rollback or restore aborted.");
/* 1631 */           break;
/*      */         }
/*      */         
/* 1634 */         rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/* 1635 */         item_count = rollback_hash_data[0];
/* 1636 */         block_count = rollback_hash_data[1];
/* 1637 */         entity_count = rollback_hash_data[2];
/* 1638 */         Config.rollback_hash.put(final_user_string, new int[] { item_count, block_count, entity_count, 0 });
/*      */         
/* 1640 */         if ((verbose == true) && (user != null) && (preview == 0)) {
/* 1641 */           user.sendMessage("§3CoreProtect §f- Modified " + file + "/" + chunk_list.size() + " chunk(s).");
/*      */         }
/*      */       }
/*      */       
/* 1645 */       int[] rollback_hash_data = (int[])Config.rollback_hash.get(final_user_string);
/* 1646 */       int item_count = rollback_hash_data[0];
/* 1647 */       int block_count = rollback_hash_data[1];
/* 1648 */       int entity_count = rollback_hash_data[2];
/* 1649 */       long time2 = System.currentTimeMillis();
/* 1650 */       int seconds = (int)((time2 - time1) / 1000L);
/*      */       
/* 1652 */       if (user != null) {
/* 1653 */         finishRollbackRestore(user, location, check_users, restrict_list, exclude_list, exclude_user_list, action_list, time_string, file, seconds, item_count, block_count, entity_count, rollback_type, radius, verbose, restrict_world, preview);
/*      */       }
/* 1655 */       return convertRawLookup(statement, lookup_list);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1659 */       e.printStackTrace();
/*      */     }
/* 1661 */     return null;
/*      */   }
/*      */   
/*      */   public static boolean playerExists(Connection connection, String user) {
/*      */     try {
/* 1666 */       int id = -1;
/* 1667 */       String uuid = null;
/* 1668 */       if (Config.player_id_cache.get(user.toLowerCase()) != null) {
/* 1669 */         return true;
/*      */       }
/* 1671 */       String query = "SELECT rowid as id, uuid FROM " + Config.prefix + "user WHERE user LIKE ? LIMIT 0, 1";
/* 1672 */       PreparedStatement preparedStmt = connection.prepareStatement(query);
/* 1673 */       preparedStmt.setString(1, user);
/* 1674 */       ResultSet rs = preparedStmt.executeQuery();
/* 1675 */       while (rs.next()) {
/* 1676 */         id = rs.getInt("id");
/* 1677 */         uuid = rs.getString("uuid");
/*      */       }
/* 1679 */       rs.close();
/* 1680 */       preparedStmt.close();
/* 1681 */       if (id > -1) {
/* 1682 */         if (uuid != null) {
/* 1683 */           Config.uuid_cache.put(user.toLowerCase(), uuid);
/* 1684 */           Config.uuid_cache_reversed.put(uuid, user);
/*      */         }
/* 1686 */         Config.player_id_cache.put(user.toLowerCase(), Integer.valueOf(id));
/* 1687 */         Config.player_id_cache_reversed.put(Integer.valueOf(id), user);
/* 1688 */         return true;
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1692 */       e.printStackTrace();
/*      */     }
/* 1694 */     return false;
/*      */   }
/*      */   
/*      */   public static Object[] populateItemStack(ItemStack itemstack, List<List<Map<String, Object>>> list) {
/* 1698 */     int slot = 0;
/*      */     try {
/* 1700 */       row_type = itemstack.getType();
/* 1701 */       item_count = 0;
/* 1702 */       effect_builder = FireworkEffect.builder();
/* 1703 */       for (List<Map<String, Object>> map : list) {
/* 1704 */         Map<String, Object> mapData = (Map)map.get(0);
/* 1705 */         if (mapData.get("slot") != null) {
/* 1706 */           slot = ((Integer)mapData.get("slot")).intValue();
/*      */         }
/* 1708 */         else if (item_count == 0) {
/* 1709 */           org.bukkit.inventory.meta.ItemMeta meta = Functions.deserializeItemMeta(itemstack.getItemMeta().getClass(), (Map)map.get(0));
/* 1710 */           itemstack.setItemMeta(meta);
/*      */ 
/*      */         }
/* 1713 */         else if ((row_type.equals(Material.LEATHER_HELMET)) || (row_type.equals(Material.LEATHER_CHESTPLATE)) || (row_type.equals(Material.LEATHER_LEGGINGS)) || (row_type.equals(Material.LEATHER_BOOTS))) {
/* 1714 */           for (Map<String, Object> l : map) {
/* 1715 */             LeatherArmorMeta meta = (LeatherArmorMeta)itemstack.getItemMeta();
/* 1716 */             Color color = Color.deserialize(l);
/* 1717 */             meta.setColor(color);
/* 1718 */             itemstack.setItemMeta(meta);
/*      */           }
/*      */         }
/* 1721 */         else if (row_type.equals(Material.POTION)) {
/* 1722 */           for (Map<String, Object> l : map) {
/* 1723 */             PotionMeta meta = (PotionMeta)itemstack.getItemMeta();
/* 1724 */             org.bukkit.potion.PotionEffect effect = new org.bukkit.potion.PotionEffect(l);
/* 1725 */             meta.addCustomEffect(effect, true);
/* 1726 */             itemstack.setItemMeta(meta);
/*      */           }
/*      */         }
/* 1729 */         else if (row_type.equals(Material.BANNER)) {
/* 1730 */           for (Map<String, Object> l : map) {
/* 1731 */             BannerMeta meta = (BannerMeta)itemstack.getItemMeta();
/* 1732 */             Pattern pattern = new Pattern(l);
/* 1733 */             meta.addPattern(pattern);
/* 1734 */             itemstack.setItemMeta(meta);
/*      */           }
/*      */         }
/* 1737 */         else if (row_type.equals(Material.MAP)) {
/* 1738 */           for (Map<String, Object> l : map) {
/* 1739 */             MapMeta meta = (MapMeta)itemstack.getItemMeta();
/* 1740 */             Color color = Color.deserialize(l);
/* 1741 */             meta.setColor(color);
/* 1742 */             itemstack.setItemMeta(meta);
/*      */           }
/*      */         }
/* 1745 */         else if ((row_type.equals(Material.FIREWORK)) || (row_type.equals(Material.FIREWORK_CHARGE))) {
/* 1746 */           if (item_count == 1) {
/* 1747 */             for (Map<String, Object> l : map) {
/* 1748 */               boolean hasFlicker = ((Boolean)l.get("flicker")).booleanValue();
/* 1749 */               boolean hasTrail = ((Boolean)l.get("trail")).booleanValue();
/* 1750 */               effect_builder.flicker(hasFlicker);
/* 1751 */               effect_builder.trail(hasTrail);
/*      */             }
/*      */           }
/* 1754 */           else if (item_count == 2) {
/* 1755 */             for (Map<String, Object> l : map) {
/* 1756 */               Color color = Color.deserialize(l);
/* 1757 */               effect_builder.withColor(color);
/*      */             }
/*      */           }
/* 1760 */           else if (item_count == 3) {
/* 1761 */             for (Map<String, Object> l : map) {
/* 1762 */               Color color = Color.deserialize(l);
/* 1763 */               effect_builder.withFade(color);
/*      */             }
/* 1765 */             FireworkEffect effect = effect_builder.build();
/* 1766 */             if (row_type.equals(Material.FIREWORK)) {
/* 1767 */               FireworkMeta meta = (FireworkMeta)itemstack.getItemMeta();
/* 1768 */               meta.addEffect(effect);
/* 1769 */               itemstack.setItemMeta(meta);
/*      */             }
/* 1771 */             else if (row_type.equals(Material.FIREWORK_CHARGE)) {
/* 1772 */               FireworkEffectMeta meta = (FireworkEffectMeta)itemstack.getItemMeta();
/* 1773 */               meta.setEffect(effect);
/* 1774 */               itemstack.setItemMeta(meta);
/*      */             }
/* 1776 */             effect_builder = FireworkEffect.builder();
/* 1777 */             item_count = 0;
/*      */           }
/*      */         }
/*      */         
/* 1781 */         item_count++;
/*      */       } } catch (Exception e) { Material row_type;
/*      */       int item_count;
/*      */       FireworkEffect.Builder effect_builder;
/* 1785 */       e.printStackTrace();
/*      */     }
/* 1787 */     return new Object[] { Integer.valueOf(slot), itemstack };
/*      */   }
/*      */   
/*      */   private static Object[] populateItemStack(ItemStack itemstack, byte[] metadata) {
/*      */     try {
/* 1792 */       ByteArrayInputStream bais = new ByteArrayInputStream(metadata);
/* 1793 */       ObjectInputStream ins = new ObjectInputStream(bais);
/*      */       
/* 1795 */       List<List<Map<String, Object>>> list = (List)ins.readObject();
/* 1796 */       return populateItemStack(itemstack, list);
/*      */     }
/*      */     catch (Exception e) {
/* 1799 */       e.printStackTrace();
/*      */     }
/* 1801 */     return tmp50_43;
/*      */   }
/*      */   
/*      */   private static ResultSet rawLookupResultSet(Statement statement, CommandSender user, List<String> check_uuids, List<String> check_users, List<Object> restrict_list, List<Object> exclude_list, List<String> exclude_user_list, List<Integer> action_list, Location location, Integer[] radius, int check_time, int limit_offset, int limit_count, boolean restrict_world, boolean lookup, boolean count) {
/* 1805 */     ResultSet rs = null;
/*      */     try {
/* 1807 */       List<Integer> valid_actions = Arrays.asList(new Integer[] { Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3) });
/* 1808 */       if (radius != null) {
/* 1809 */         restrict_world = true;
/*      */       }
/* 1811 */       boolean valid_action = false;
/* 1812 */       String query_extra = "";
/* 1813 */       String query_limit = "";
/* 1814 */       String query_table = "block";
/* 1815 */       String action = "";
/* 1816 */       String exclude = "";
/* 1817 */       String restrict = "";
/* 1818 */       String users = "";
/* 1819 */       String uuids = "";
/* 1820 */       String exclude_users = "";
/* 1821 */       String index = "";
/*      */       
/* 1823 */       if (check_uuids.size() > 0) {
/* 1824 */         String list = "";
/* 1825 */         for (String value : check_uuids) {
/* 1826 */           if (list.length() == 0) {
/* 1827 */             list = "'" + value + "'";
/*      */           }
/*      */           else {
/* 1830 */             list = list + ",'" + value + "'";
/*      */           }
/*      */         }
/* 1833 */         uuids = list;
/*      */       }
/* 1835 */       if (!check_users.contains("#global")) {
/* 1836 */         String list = "";
/* 1837 */         for (String value : check_users) {
/* 1838 */           if (!value.equals("#container")) {
/* 1839 */             if (Config.player_id_cache.get(value.toLowerCase()) == null) {
/* 1840 */               Database.loadUserID(statement.getConnection(), value, null);
/*      */             }
/* 1842 */             int userid = ((Integer)Config.player_id_cache.get(value.toLowerCase())).intValue();
/* 1843 */             if (list.length() == 0) {
/* 1844 */               list = "" + userid + "";
/*      */             }
/*      */             else {
/* 1847 */               list = list + "," + userid;
/*      */             }
/*      */           }
/*      */         }
/* 1851 */         users = list;
/*      */       }
/* 1853 */       if (restrict_list.size() > 0) {
/* 1854 */         String list = "";
/* 1855 */         for (Object value : restrict_list) {
/* 1856 */           String value_name = "";
/* 1857 */           if ((value instanceof Material)) {
/* 1858 */             value_name = ((Material)value).name();
/* 1859 */             if (list.length() == 0) {
/* 1860 */               list = "" + Functions.block_id(value_name, false) + "";
/*      */             }
/*      */             else {
/* 1863 */               list = list + "," + Functions.block_id(value_name, false);
/*      */             }
/*      */           }
/* 1866 */           else if ((value instanceof EntityType)) {
/* 1867 */             value_name = ((EntityType)value).name();
/* 1868 */             if (list.length() == 0) {
/* 1869 */               list = "" + Functions.getEntityId(value_name, false) + "";
/*      */             }
/*      */             else {
/* 1872 */               list = list + "," + Functions.getEntityId(value_name, false);
/*      */             }
/*      */           }
/*      */         }
/* 1876 */         restrict = list;
/*      */       }
/* 1878 */       if (exclude_list.size() > 0) {
/* 1879 */         String list = "";
/* 1880 */         for (Object value : exclude_list) {
/* 1881 */           String value_name = "";
/* 1882 */           if ((value instanceof Material)) {
/* 1883 */             value_name = ((Material)value).name();
/* 1884 */             if (list.length() == 0) {
/* 1885 */               list = "" + Functions.block_id(value_name, false) + "";
/*      */             }
/*      */             else {
/* 1888 */               list = list + "," + Functions.block_id(value_name, false);
/*      */             }
/*      */           }
/* 1891 */           else if ((value instanceof EntityType)) {
/* 1892 */             value_name = ((EntityType)value).name();
/* 1893 */             if (list.length() == 0) {
/* 1894 */               list = "" + Functions.getEntityId(value_name, false) + "";
/*      */             }
/*      */             else {
/* 1897 */               list = list + "," + Functions.getEntityId(value_name, false);
/*      */             }
/*      */           }
/*      */         }
/* 1901 */         exclude = list;
/*      */       }
/* 1903 */       if (exclude_user_list.size() > 0) {
/* 1904 */         String list = "";
/* 1905 */         for (String value : exclude_user_list) {
/* 1906 */           if (Config.player_id_cache.get(value.toLowerCase()) == null) {
/* 1907 */             Database.loadUserID(statement.getConnection(), value, null);
/*      */           }
/* 1909 */           int userid = ((Integer)Config.player_id_cache.get(value.toLowerCase())).intValue();
/* 1910 */           if (list.length() == 0) {
/* 1911 */             list = "" + userid + "";
/*      */           }
/*      */           else {
/* 1914 */             list = list + "," + userid;
/*      */           }
/*      */         }
/* 1917 */         exclude_users = list;
/*      */       }
/* 1919 */       if (action_list.size() > 0) {
/* 1920 */         String list = "";
/* 1921 */         for (Integer value : action_list) {
/* 1922 */           if (valid_actions.contains(value)) {
/* 1923 */             if (list.length() == 0) {
/* 1924 */               list = "" + value + "";
/*      */             }
/*      */             else {
/* 1927 */               list = list + "," + value;
/*      */             }
/*      */           }
/*      */         }
/* 1931 */         action = list;
/*      */       }
/* 1933 */       for (Integer value : action_list) {
/* 1934 */         if (valid_actions.contains(value)) {
/* 1935 */           valid_action = true;
/*      */         }
/*      */       }
/* 1938 */       if (restrict_world == true) {
/* 1939 */         int wid = Functions.getWorldId(location.getWorld().getName());
/* 1940 */         query_extra = query_extra + " wid=" + wid + " AND";
/*      */       }
/* 1942 */       if (radius != null) {
/* 1943 */         int xmin = radius[1].intValue();
/* 1944 */         int xmax = radius[2].intValue();
/* 1945 */         int ymin = radius[3].intValue();
/* 1946 */         int ymax = radius[4].intValue();
/* 1947 */         int zmin = radius[5].intValue();
/* 1948 */         int zmax = radius[6].intValue();
/* 1949 */         String query_y = "";
/* 1950 */         if ((ymin > -1) && (ymax > -1)) {
/* 1951 */           query_y = " y >= '" + ymin + "' AND y <= '" + ymax + "' AND";
/*      */         }
/* 1953 */         query_extra = query_extra + " x >= '" + xmin + "' AND x <= '" + xmax + "' AND z >= '" + zmin + "' AND z <= '" + zmax + "' AND" + query_y;
/*      */       }
/* 1955 */       else if (action_list.contains(Integer.valueOf(5))) {
/* 1956 */         int wid = Functions.getWorldId(location.getWorld().getName());
/* 1957 */         int x = (int)Math.floor(location.getX());
/* 1958 */         int z = (int)Math.floor(location.getZ());
/* 1959 */         int x2 = (int)Math.ceil(location.getX());
/* 1960 */         int z2 = (int)Math.ceil(location.getZ());
/* 1961 */         query_extra = query_extra + " wid=" + wid + " AND (x = '" + x + "' OR x = '" + x2 + "') AND (z = '" + z + "' OR z = '" + z2 + "') AND y = '" + location.getBlockY() + "' AND";
/*      */       }
/* 1963 */       if (valid_action == true) {
/* 1964 */         query_extra = query_extra + " action IN(" + action + ") AND";
/*      */       }
/* 1966 */       if (restrict.length() > 0) {
/* 1967 */         query_extra = query_extra + " type IN(" + restrict + ") AND";
/*      */       }
/* 1969 */       if (exclude.length() > 0) {
/* 1970 */         query_extra = query_extra + " type NOT IN(" + exclude + ") AND";
/*      */       }
/* 1972 */       if (uuids.length() > 0) {
/* 1973 */         query_extra = query_extra + " uuid IN(" + uuids + ") AND";
/*      */       }
/* 1975 */       if (users.length() > 0) {
/* 1976 */         query_extra = query_extra + " user IN(" + users + ") AND";
/*      */       }
/* 1978 */       if (exclude_users.length() > 0) {
/* 1979 */         query_extra = query_extra + " user NOT IN(" + exclude_users + ") AND";
/*      */       }
/* 1981 */       if (check_time > 0) {
/* 1982 */         query_extra = query_extra + " time > '" + check_time + "' AND";
/*      */       }
/* 1984 */       if (query_extra.length() > 0) {
/* 1985 */         query_extra = query_extra.substring(0, query_extra.length() - 4);
/*      */       }
/* 1987 */       if (query_extra.length() == 0) {
/* 1988 */         query_extra = " 1";
/*      */       }
/* 1990 */       if ((limit_offset > -1) && (limit_count > -1)) {
/* 1991 */         query_limit = " LIMIT " + limit_offset + ", " + limit_count + "";
/*      */       }
/*      */       
/* 1994 */       String rows = "rowid as id,time,user,wid,x,y,z,action,type,data,meta,rolled_back";
/* 1995 */       String query_order = " ORDER BY rowid DESC";
/*      */       
/* 1997 */       if (lookup == true) {
/* 1998 */         query_order = " ORDER BY time DESC";
/*      */       }
/* 2000 */       if ((action_list.contains(Integer.valueOf(4))) || (action_list.contains(Integer.valueOf(5)))) {
/* 2001 */         query_table = "container";
/* 2002 */         rows = "rowid as id,time,user,wid,x,y,z,action,type,data,rolled_back,amount,metadata";
/*      */       }
/* 2004 */       else if ((action_list.contains(Integer.valueOf(6))) || (action_list.contains(Integer.valueOf(7)))) {
/* 2005 */         query_table = "chat";
/* 2006 */         rows = "rowid as id,time,user,message";
/* 2007 */         if (action_list.contains(Integer.valueOf(7))) {
/* 2008 */           query_table = "command";
/*      */         }
/*      */       }
/* 2011 */       else if (action_list.contains(Integer.valueOf(8))) {
/* 2012 */         query_table = "session";
/* 2013 */         rows = "rowid as id,time,user,wid,x,y,z,action";
/*      */       }
/* 2015 */       else if (action_list.contains(Integer.valueOf(9))) {
/* 2016 */         query_table = "username_log";
/* 2017 */         rows = "rowid as id,time,uuid,user";
/*      */       }
/* 2019 */       if (count == true) {
/* 2020 */         rows = "COUNT(*) as count";
/* 2021 */         query_limit = " LIMIT 0, 1";
/* 2022 */         query_order = "";
/*      */       }
/* 2024 */       if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/* 2025 */         if ((radius == null) || (users.length() > 0) || (restrict.length() > 0))
/*      */         {
/* 2027 */           if (users.length() <= 0) {}
/*      */ 
/*      */         }
/*      */         
/*      */ 
/*      */       }
/* 2033 */       else if (query_table.equals("block")) {
/* 2034 */         if ((restrict.length() > 0) || (exclude.length() > 0)) {
/* 2035 */           index = "INDEXED BY block_type_index ";
/*      */         }
/* 2037 */         if ((users.length() > 0) || (exclude_users.length() > 0)) {
/* 2038 */           index = "INDEXED BY block_user_index ";
/*      */         }
/* 2040 */         if ((radius != null) || (action_list.contains(Integer.valueOf(5))) || ((index.equals("")) && (restrict_world == true))) {
/* 2041 */           index = "INDEXED BY block_index ";
/*      */         }
/*      */       }
/*      */       
/* 2045 */       String query = "SELECT " + rows + " FROM " + Config.prefix + query_table + " " + index + "WHERE" + query_extra + query_order + query_limit + "";
/* 2046 */       rs = statement.executeQuery(query);
/*      */     }
/*      */     catch (Exception e) {
/* 2049 */       e.printStackTrace();
/*      */     }
/* 2051 */     return rs;
/*      */   }
/*      */   
/*      */   public static String who_placed(Statement statement, BlockState block) {
/* 2055 */     String result = "";
/*      */     try {
/* 2057 */       if (block == null) {
/* 2058 */         return result;
/*      */       }
/* 2060 */       int x = block.getX();
/* 2061 */       int y = block.getY();
/* 2062 */       int z = block.getZ();
/* 2063 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 2064 */       int wid = Functions.getWorldId(block.getWorld().getName());
/* 2065 */       String query = "SELECT user,type FROM " + Config.prefix + "block WHERE wid = '" + wid + "' AND x = '" + x + "' AND z = '" + z + "' AND y = '" + y + "' AND rolled_back = '0' AND action='1' ORDER BY rowid DESC LIMIT 0, 1";
/* 2066 */       ResultSet rs = statement.executeQuery(query);
/* 2067 */       while (rs.next()) {
/* 2068 */         int result_userid = rs.getInt("user");
/* 2069 */         int result_type = rs.getInt("type");
/* 2070 */         if (Config.player_id_cache_reversed.get(Integer.valueOf(result_userid)) == null) {
/* 2071 */           Database.loadUserName(statement.getConnection(), result_userid);
/*      */         }
/* 2073 */         result = (String)Config.player_id_cache_reversed.get(Integer.valueOf(result_userid));
/* 2074 */         if (result.length() > 0) {
/* 2075 */           Material result_material = Functions.getType(result_type);
/* 2076 */           Config.lookup_cache.put("" + x + "." + y + "." + z + "." + wid + "", new Object[] { Integer.valueOf(time), result, result_material });
/*      */         }
/*      */       }
/* 2079 */       rs.close();
/*      */     }
/*      */     catch (Exception e) {
/* 2082 */       e.printStackTrace();
/*      */     }
/* 2084 */     return result;
/*      */   }
/*      */   
/*      */   public static String who_placed_cache(Block block) {
/* 2088 */     String result = "";
/*      */     try {
/* 2090 */       if (block == null) {
/* 2091 */         return result;
/*      */       }
/* 2093 */       int x = block.getX();
/* 2094 */       int y = block.getY();
/* 2095 */       int z = block.getZ();
/* 2096 */       int wid = Functions.getWorldId(block.getWorld().getName());
/* 2097 */       String cords = "" + x + "." + y + "." + z + "." + wid + "";
/* 2098 */       Object[] data = (Object[])Config.lookup_cache.get(cords);
/* 2099 */       if (data != null) {
/* 2100 */         result = (String)data[1];
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2104 */       e.printStackTrace();
/*      */     }
/* 2106 */     return result;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static String who_removed_cache(BlockState block)
/*      */   {
/* 2113 */     String result = "";
/*      */     try {
/* 2115 */       if (block != null) {
/* 2116 */         int x = block.getX();
/* 2117 */         int y = block.getY();
/* 2118 */         int z = block.getZ();
/* 2119 */         int wid = Functions.getWorldId(block.getWorld().getName());
/* 2120 */         String cords = "" + x + "." + y + "." + z + "." + wid + "";
/* 2121 */         Object[] data = (Object[])Config.break_cache.get(cords);
/* 2122 */         if (data != null) {
/* 2123 */           result = (String)data[1];
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 2128 */       e.printStackTrace();
/*      */     }
/* 2130 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\database\Lookup.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */