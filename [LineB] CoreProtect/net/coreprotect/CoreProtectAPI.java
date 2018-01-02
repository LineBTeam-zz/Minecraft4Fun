/*     */ package net.coreprotect;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.coreprotect.consumer.Queue;
/*     */ import net.coreprotect.database.Database;
/*     */ import net.coreprotect.database.Lookup;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class CoreProtectAPI extends Queue
/*     */ {
/*     */   public class ParseResult
/*     */   {
/*  23 */     String[] parse = null;
/*     */     
/*     */     public ParseResult(String[] data) {
/*  26 */       this.parse = data;
/*     */     }
/*     */     
/*     */     public int getActionId() {
/*  30 */       return Integer.parseInt(this.parse[7]);
/*     */     }
/*     */     
/*     */     public String getActionString() {
/*  34 */       int ActionID = Integer.parseInt(this.parse[7]);
/*  35 */       String result = "Unknown";
/*  36 */       if (ActionID == 0) {
/*  37 */         result = "Removal";
/*     */       }
/*  39 */       else if (ActionID == 1) {
/*  40 */         result = "Placement";
/*     */       }
/*  42 */       else if (ActionID == 2) {
/*  43 */         result = "Interaction";
/*     */       }
/*  45 */       return result;
/*     */     }
/*     */     
/*     */     public int getData() {
/*  49 */       return Integer.parseInt(this.parse[6]);
/*     */     }
/*     */     
/*     */     public String getPlayer() {
/*  53 */       return this.parse[1];
/*     */     }
/*     */     
/*     */     public int getTime() {
/*  57 */       return Integer.parseInt(this.parse[0]);
/*     */     }
/*     */     
/*     */     public Material getType() {
/*  61 */       int ActionID = getActionId();
/*  62 */       int type = Integer.parseInt(this.parse[5]);
/*  63 */       String dname = "";
/*  64 */       if (ActionID == 3) {
/*  65 */         dname = Functions.getEntityType(type).name();
/*     */       }
/*     */       else {
/*  68 */         dname = Functions.getType(type).name().toLowerCase();
/*  69 */         dname = Functions.nameFilter(dname, getData());
/*     */       }
/*  71 */       return Functions.getType(dname);
/*     */     }
/*     */     
/*     */     @Deprecated
/*     */     public int getTypeId() {
/*  76 */       return getType().getId();
/*     */     }
/*     */     
/*     */     public int getX() {
/*  80 */       return Integer.parseInt(this.parse[2]);
/*     */     }
/*     */     
/*     */     public int getY() {
/*  84 */       return Integer.parseInt(this.parse[3]);
/*     */     }
/*     */     
/*     */     public int getZ() {
/*  88 */       return Integer.parseInt(this.parse[4]);
/*     */     }
/*     */     
/*     */     public boolean isRolledBack() {
/*  92 */       return Integer.parseInt(this.parse[8]) == 1;
/*     */     }
/*     */     
/*     */     public String worldName() {
/*  96 */       return Functions.getWorldName(Integer.parseInt(this.parse[9]));
/*     */     }
/*     */   }
/*     */   
/*     */   private static List<Object> parseList(List<Object> list) {
/* 101 */     List<Object> result = new ArrayList();
/* 102 */     if (list != null) {
/* 103 */       for (Object value : list) {
/* 104 */         if (((value instanceof Material)) || ((value instanceof EntityType))) {
/* 105 */           result.add(value);
/*     */         }
/* 107 */         else if ((value instanceof Integer)) {
/* 108 */           Material material = Functions.getMaterialFromId((Integer)value);
/* 109 */           result.add(material);
/*     */         }
/*     */       }
/*     */     }
/* 113 */     return result;
/*     */   }
/*     */   
/*     */   public int APIVersion() {
/* 117 */     return 5;
/*     */   }
/*     */   
/*     */   public List<String[]> blockLookup(Block block, int time) {
/* 121 */     if (((Integer)Config.config.get("api-enabled")).intValue() == 1) {
/* 122 */       return Lookup.block_lookup_api(block, time);
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */   
/*     */   public boolean hasPlaced(String user, Block block, int time, int offset)
/*     */   {
/* 129 */     boolean match = false;
/* 130 */     int stime; if (((Integer)Config.config.get("api-enabled")).intValue() == 1) {
/* 131 */       int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 132 */       stime = unixtimestamp - offset;
/* 133 */       List<String[]> check = blockLookup(block, time);
/* 134 */       for (String[] value : check) {
/* 135 */         ParseResult result = parseResult(value);
/* 136 */         if ((user.equalsIgnoreCase(result.getPlayer())) && (result.getActionId() == 1) && (result.getTime() <= stime)) {
/* 137 */           match = true;
/* 138 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 142 */     return match;
/*     */   }
/*     */   
/*     */   public boolean hasRemoved(String user, Block block, int time, int offset)
/*     */   {
/* 147 */     boolean match = false;
/* 148 */     int stime; if (((Integer)Config.config.get("api-enabled")).intValue() == 1) {
/* 149 */       int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 150 */       stime = unixtimestamp - offset;
/* 151 */       List<String[]> check = blockLookup(block, time);
/* 152 */       for (String[] value : check) {
/* 153 */         ParseResult result = parseResult(value);
/* 154 */         if ((user.equalsIgnoreCase(result.getPlayer())) && (result.getActionId() == 1) && (result.getTime() <= stime)) {
/* 155 */           match = true;
/* 156 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 160 */     return match;
/*     */   }
/*     */   
/*     */   public boolean isEnabled() {
/* 164 */     if ((Config.config.get("api-enabled") != null) && 
/* 165 */       (((Integer)Config.config.get("api-enabled")).intValue() == 1)) {
/* 166 */       return true;
/*     */     }
/*     */     
/* 169 */     return false;
/*     */   }
/*     */   
/*     */   public boolean logChat(Player player, String message) {
/* 173 */     if ((((Integer)Config.config.get("api-enabled")).intValue() == 1) && (Functions.checkConfig(player.getWorld(), "player-messages") == 1) && 
/* 174 */       (player != null) && (message != null) && 
/* 175 */       (message.length() > 0) && (!message.startsWith("/"))) {
/* 176 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 177 */       Queue.queuePlayerChat(player, message, time);
/* 178 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 182 */     return false;
/*     */   }
/*     */   
/*     */   public boolean logCommand(Player player, String command) {
/* 186 */     if ((((Integer)Config.config.get("api-enabled")).intValue() == 1) && (Functions.checkConfig(player.getWorld(), "player-commands") == 1) && 
/* 187 */       (player != null) && (command != null) && 
/* 188 */       (command.length() > 0) && (command.startsWith("/") == true)) {
/* 189 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 190 */       Queue.queuePlayerCommand(player, command, time);
/* 191 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 195 */     return false;
/*     */   }
/*     */   
/*     */   public boolean logInteraction(String user, Location location) {
/* 199 */     if ((((Integer)Config.config.get("api-enabled")).intValue() == 1) && 
/* 200 */       (user != null) && (location != null) && 
/* 201 */       (user.length() > 0)) {
/* 202 */       Queue.queuePlayerInteraction(user, location.getBlock().getState());
/* 203 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 207 */     return false;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public boolean logPlacement(String user, Location location, int type, byte data) {
/* 212 */     if ((((Integer)Config.config.get("api-enabled")).intValue() == 1) && 
/* 213 */       (user != null) && (location != null) && 
/* 214 */       (user.length() > 0)) {
/* 215 */       Material material = Material.getMaterial(type);
/* 216 */       Queue.queueBlockPlace(user, location.getBlock().getState(), material, data);
/* 217 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 221 */     return false;
/*     */   }
/*     */   
/*     */   public boolean logPlacement(String user, Location location, Material type, byte data) {
/* 225 */     if ((((Integer)Config.config.get("api-enabled")).intValue() == 1) && 
/* 226 */       (user != null) && (location != null) && 
/* 227 */       (user.length() > 0)) {
/* 228 */       Queue.queueBlockPlace(user, location.getBlock().getState(), type, data);
/* 229 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 233 */     return false;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public boolean logRemoval(String user, Location location, int type, byte data) {
/* 238 */     if ((((Integer)Config.config.get("api-enabled")).intValue() == 1) && 
/* 239 */       (user != null) && (location != null) && 
/* 240 */       (user.length() > 0)) {
/* 241 */       Material material = Material.getMaterial(type);
/* 242 */       Queue.queueBlockBreak(user, location.getBlock().getState(), material, data);
/* 243 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 247 */     return false;
/*     */   }
/*     */   
/*     */   public boolean logRemoval(String user, Location location, Material type, byte data) {
/* 251 */     if ((((Integer)Config.config.get("api-enabled")).intValue() == 1) && 
/* 252 */       (user != null) && (location != null) && 
/* 253 */       (user.length() > 0)) {
/* 254 */       Queue.queueBlockBreak(user, location.getBlock().getState(), type, data);
/* 255 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 259 */     return false;
/*     */   }
/*     */   
/*     */   public ParseResult parseResult(String[] data) {
/* 263 */     return new ParseResult(data);
/*     */   }
/*     */   
/*     */   public List<String[]> performLookup(int time, List<String> restrict_users, List<String> exclude_users, List<Object> restrict_blocks, List<Object> exclude_blocks, List<Integer> action_list, int radius, Location radius_location) {
/* 267 */     if (((Integer)Config.config.get("api-enabled")).intValue() == 1) {
/* 268 */       return processData(time, radius, radius_location, parseList(restrict_blocks), parseList(exclude_blocks), restrict_users, exclude_users, action_list, 0, 1, -1, -1, false);
/*     */     }
/* 270 */     return null;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public List<String[]> performLookup(String user, int time, int radius, Location location, List<Object> restrict, List<Object> exclude) {
/* 275 */     if (((Integer)Config.config.get("api-enabled")).intValue() == 1) {
/* 276 */       return processData(user, time, radius, location, parseList(restrict), parseList(exclude), 0, 1, -1, -1, false);
/*     */     }
/* 278 */     return null;
/*     */   }
/*     */   
/*     */   public List<String[]> performPartialLookup(int time, List<String> restrict_users, List<String> exclude_users, List<Object> restrict_blocks, List<Object> exclude_blocks, List<Integer> action_list, int radius, Location radius_location, int limit_offset, int limit_count) {
/* 282 */     if (((Integer)Config.config.get("api-enabled")).intValue() == 1) {
/* 283 */       return processData(time, radius, radius_location, parseList(restrict_blocks), parseList(exclude_blocks), restrict_users, exclude_users, action_list, 0, 1, limit_offset, limit_count, true);
/*     */     }
/* 285 */     return null;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public List<String[]> performPartialLookup(String user, int time, int radius, Location location, List<Object> restrict, List<Object> exclude, int limit_offset, int limit_count) {
/* 290 */     if (((Integer)Config.config.get("api-enabled")).intValue() == 1) {
/* 291 */       return processData(user, time, radius, location, parseList(restrict), parseList(exclude), 0, 1, limit_offset, limit_count, true);
/*     */     }
/* 293 */     return null;
/*     */   }
/*     */   
/*     */   public void performPurge(int time) {
/* 297 */     Server server = CoreProtect.getInstance().getServer();
/* 298 */     server.dispatchCommand(server.getConsoleSender(), "co purge t:" + time + "s");
/*     */   }
/*     */   
/*     */   public List<String[]> performRestore(int time, List<String> restrict_users, List<String> exclude_users, List<Object> restrict_blocks, List<Object> exclude_blocks, List<Integer> action_list, int radius, Location radius_location) {
/* 302 */     if (((Integer)Config.config.get("api-enabled")).intValue() == 1) {
/* 303 */       return processData(time, radius, radius_location, parseList(restrict_blocks), parseList(exclude_blocks), restrict_users, exclude_users, action_list, 1, 2, -1, -1, false);
/*     */     }
/* 305 */     return null;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public List<String[]> performRestore(String user, int time, int radius, Location location, List<Object> restrict, List<Object> exclude) {
/* 310 */     if (((Integer)Config.config.get("api-enabled")).intValue() == 1) {
/* 311 */       return processData(user, time, radius, location, parseList(restrict), parseList(exclude), 1, 2, -1, -1, false);
/*     */     }
/* 313 */     return null;
/*     */   }
/*     */   
/*     */   public List<String[]> performRollback(int time, List<String> restrict_users, List<String> exclude_users, List<Object> restrict_blocks, List<Object> exclude_blocks, List<Integer> action_list, int radius, Location radius_location) {
/* 317 */     if (((Integer)Config.config.get("api-enabled")).intValue() == 1) {
/* 318 */       return processData(time, radius, radius_location, parseList(restrict_blocks), parseList(exclude_blocks), restrict_users, exclude_users, action_list, 0, 2, -1, -1, false);
/*     */     }
/* 320 */     return null;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   public List<String[]> performRollback(String user, int time, int radius, Location location, List<Object> restrict, List<Object> exclude) {
/* 325 */     if (((Integer)Config.config.get("api-enabled")).intValue() == 1) {
/* 326 */       return processData(user, time, radius, location, parseList(restrict), parseList(exclude), 0, 2, -1, -1, false);
/*     */     }
/* 328 */     return null;
/*     */   }
/*     */   
/*     */   private List<String[]> processData(int time, int radius, Location location, List<Object> restrict_blocks, List<Object> exclude_blocks, List<String> restrict_users, List<String> exclude_users, List<Integer> action_list, int action, int lookup, int offset, int row_count, boolean use_limit)
/*     */   {
/* 333 */     List<String[]> result = new ArrayList();
/* 334 */     List<String> uuids = new ArrayList();
/* 335 */     if (restrict_users == null) {
/* 336 */       restrict_users = new ArrayList();
/*     */     }
/* 338 */     if (exclude_users == null) {
/* 339 */       exclude_users = new ArrayList();
/*     */     }
/* 341 */     if (action_list == null) {
/* 342 */       action_list = new ArrayList();
/*     */     }
/*     */     
/* 345 */     if ((action_list.size() == 0) && (restrict_blocks.size() > 0)) {
/* 346 */       for (Object arg_block : restrict_blocks) {
/* 347 */         if ((arg_block instanceof Material)) {
/* 348 */           action_list.add(Integer.valueOf(0));
/* 349 */           action_list.add(Integer.valueOf(1));
/*     */         }
/* 351 */         else if ((arg_block instanceof EntityType)) {
/* 352 */           action_list.add(Integer.valueOf(3));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 357 */     if (restrict_users.size() == 0) {
/* 358 */       restrict_users.add("#global");
/*     */     }
/* 360 */     int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 361 */     int stime = unixtimestamp - time;
/* 362 */     if (radius < 1) {
/* 363 */       radius = -1;
/*     */     }
/* 365 */     if ((restrict_users.contains("#global")) && (radius == -1)) {
/* 366 */       return null;
/*     */     }
/* 368 */     if ((radius > -1) && (location == null)) {
/* 369 */       return null;
/*     */     }
/*     */     try {
/* 372 */       Connection connection = Database.getConnection(false);
/* 373 */       if (connection != null) {
/* 374 */         Statement statement = connection.createStatement();
/* 375 */         boolean restrict_world = false;
/* 376 */         if (radius > 0) {
/* 377 */           restrict_world = true;
/*     */         }
/* 379 */         if (location == null) {
/* 380 */           restrict_world = false;
/*     */         }
/* 382 */         Integer[] arg_radius = null;
/* 383 */         if ((location != null) && (radius > 0)) {
/* 384 */           int xmin = location.getBlockX() - radius;
/* 385 */           int xmax = location.getBlockX() + radius;
/* 386 */           int zmin = location.getBlockZ() - radius;
/* 387 */           int zmax = location.getBlockZ() + radius;
/* 388 */           arg_radius = new Integer[] { Integer.valueOf(radius), Integer.valueOf(xmin), Integer.valueOf(xmax), Integer.valueOf(-1), Integer.valueOf(-1), Integer.valueOf(zmin), Integer.valueOf(zmax), Integer.valueOf(0) };
/*     */         }
/* 390 */         if (lookup == 1) {
/* 391 */           if (location != null) {
/* 392 */             restrict_world = true;
/*     */           }
/* 394 */           if (use_limit == true) {
/* 395 */             result = Lookup.performPartialLookup(statement, null, uuids, restrict_users, restrict_blocks, exclude_blocks, exclude_users, action_list, location, arg_radius, stime, offset, row_count, restrict_world, true);
/*     */           }
/*     */           else {
/* 398 */             result = Lookup.performLookup(statement, null, uuids, restrict_users, restrict_blocks, exclude_blocks, exclude_users, action_list, location, arg_radius, stime, restrict_world, true);
/*     */           }
/*     */         }
/*     */         else {
/* 402 */           boolean verbose = false;
/* 403 */           result = Lookup.performRollbackRestore(statement, null, uuids, restrict_users, null, restrict_blocks, exclude_blocks, exclude_users, action_list, location, arg_radius, stime, restrict_world, false, verbose, action, 0);
/*     */         }
/* 405 */         statement.close();
/* 406 */         connection.close();
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 410 */       e.printStackTrace();
/*     */     }
/* 412 */     return result;
/*     */   }
/*     */   
/*     */   @Deprecated
/*     */   private List<String[]> processData(String user, int time, int radius, Location location, List<Object> restrict_blocks, List<Object> exclude_blocks, int action, int lookup, int offset, int row_count, boolean use_limit) {
/* 417 */     ArrayList<String> restrict_users = new ArrayList();
/* 418 */     if (user != null) {
/* 419 */       restrict_users.add(user);
/*     */     }
/* 421 */     return processData(time, radius, location, restrict_blocks, exclude_blocks, restrict_users, null, null, action, lookup, offset, row_count, use_limit);
/*     */   }
/*     */   
/*     */   public void testAPI() {
/* 425 */     System.out.println("[CoreProtect] API Test Successful.");
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\CoreProtectAPI.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */