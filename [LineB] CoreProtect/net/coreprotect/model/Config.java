/*     */ package net.coreprotect.model;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.RandomAccessFile;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.coreprotect.CoreProtect;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.consumer.Queue;
/*     */ import net.coreprotect.database.Database;
/*     */ import net.coreprotect.patch.Patch;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class Config extends Queue
/*     */ {
/*  27 */   public static String driver = "com.mysql.jdbc.Driver";
/*  28 */   public static String sqlite = "plugins/CoreProtect/database.db";
/*  29 */   public static String host = "127.0.0.1";
/*  30 */   public static int port = 3306;
/*  31 */   public static String database = "database";
/*  32 */   public static String username = "root";
/*  33 */   public static String password = "";
/*  34 */   public static String prefix = "co_";
/*  35 */   public static boolean server_running = false;
/*  36 */   public static boolean converter_running = false;
/*  37 */   public static boolean purge_running = false;
/*  38 */   public static int world_id = 0;
/*  39 */   public static int material_id = 0;
/*  40 */   public static int entity_id = 0;
/*  41 */   public static int art_id = 0;
/*  42 */   public static Map<String, Integer> worlds = Collections.synchronizedMap(new HashMap());
/*  43 */   public static Map<Integer, String> worlds_reversed = Collections.synchronizedMap(new HashMap());
/*  44 */   public static Map<String, Integer> materials = Collections.synchronizedMap(new HashMap());
/*  45 */   public static Map<Integer, String> materials_reversed = Collections.synchronizedMap(new HashMap());
/*  46 */   public static Map<String, Integer> entities = Collections.synchronizedMap(new HashMap());
/*  47 */   public static Map<Integer, String> entities_reversed = Collections.synchronizedMap(new HashMap());
/*  48 */   public static Map<String, Integer> art = Collections.synchronizedMap(new HashMap());
/*  49 */   public static Map<Integer, String> art_reversed = Collections.synchronizedMap(new HashMap());
/*  50 */   public static Map<String, Integer> config = Collections.synchronizedMap(new HashMap());
/*  51 */   public static Map<String, int[]> rollback_hash = Collections.synchronizedMap(new HashMap());
/*  52 */   public static Map<String, Boolean> inspecting = Collections.synchronizedMap(new HashMap());
/*  53 */   public static Map<String, Object[]> lookup_cache = Collections.synchronizedMap(new HashMap());
/*  54 */   public static Map<String, Object[]> break_cache = Collections.synchronizedMap(new HashMap());
/*  55 */   public static Map<String, Object[]> piston_cache = Collections.synchronizedMap(new HashMap());
/*  56 */   public static Map<String, Object[]> entity_cache = Collections.synchronizedMap(new HashMap());
/*  57 */   public static Map<String, Boolean> blacklist = Collections.synchronizedMap(new HashMap());
/*  58 */   public static Map<String, Integer> logging_chest = Collections.synchronizedMap(new HashMap());
/*  59 */   public static Map<String, List<ItemStack[]>> old_container = Collections.synchronizedMap(new HashMap());
/*  60 */   public static Map<String, List<ItemStack[]>> force_containers = Collections.synchronizedMap(new HashMap());
/*  61 */   public static Map<String, Integer> lookup_type = Collections.synchronizedMap(new HashMap());
/*  62 */   public static Map<String, Integer> lookup_page = Collections.synchronizedMap(new HashMap());
/*  63 */   public static Map<String, String> lookup_command = Collections.synchronizedMap(new HashMap());
/*  64 */   public static Map<String, List<Object>> lookup_blist = Collections.synchronizedMap(new HashMap());
/*  65 */   public static Map<String, List<Object>> lookup_elist = Collections.synchronizedMap(new HashMap());
/*  66 */   public static Map<String, List<String>> lookup_e_userlist = Collections.synchronizedMap(new HashMap());
/*  67 */   public static Map<String, List<String>> lookup_ulist = Collections.synchronizedMap(new HashMap());
/*  68 */   public static Map<String, List<Integer>> lookup_alist = Collections.synchronizedMap(new HashMap());
/*  69 */   public static Map<String, Integer[]> lookup_radius = Collections.synchronizedMap(new HashMap());
/*  70 */   public static Map<String, String> lookup_time = Collections.synchronizedMap(new HashMap());
/*  71 */   public static Map<String, Integer> lookup_rows = Collections.synchronizedMap(new HashMap());
/*  72 */   public static Map<String, String> uuid_cache = Collections.synchronizedMap(new HashMap());
/*  73 */   public static Map<String, String> uuid_cache_reversed = Collections.synchronizedMap(new HashMap());
/*  74 */   public static Map<String, Integer> player_id_cache = Collections.synchronizedMap(new HashMap());
/*  75 */   public static Map<Integer, String> player_id_cache_reversed = Collections.synchronizedMap(new HashMap());
/*  76 */   public static Map<String, List<Object[]>> last_rollback = Collections.synchronizedMap(new HashMap());
/*  77 */   public static Map<String, Boolean> active_rollbacks = Collections.synchronizedMap(new HashMap());
/*  78 */   public static Map<UUID, Object[]> entity_block_mapper = Collections.synchronizedMap(new HashMap());
/*  79 */   public static ConcurrentHashMap<String, String> language = new ConcurrentHashMap();
/*  80 */   public static List<String> databaseTables = new ArrayList();
/*     */   
/*     */   private static void checkPlayers(Connection connection) {
/*  83 */     player_id_cache.clear();
/*  84 */     for (Player player : CoreProtect.getInstance().getServer().getOnlinePlayers()) {
/*  85 */       if (player_id_cache.get(player.getName().toLowerCase()) == null) {
/*  86 */         Database.loadUserID(connection, player.getName(), player.getUniqueId().toString());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private static void loadBlacklist() {
/*     */     try {
/*  93 */       blacklist.clear();
/*  94 */       String blacklist = "plugins/CoreProtect/blacklist.txt";
/*  95 */       boolean exists = new File(blacklist).exists();
/*  96 */       if (exists) {
/*  97 */         RandomAccessFile blfile = new RandomAccessFile(blacklist, "rw");
/*  98 */         long blc = blfile.length();
/*  99 */         if (blc > 0L) {
/* 100 */           while (blfile.getFilePointer() < blfile.length()) {
/* 101 */             String blacklist_user = blfile.readLine().replaceAll(" ", "").toLowerCase();
/* 102 */             if (blacklist_user.length() > 0) {
/* 103 */               blacklist.put(blacklist_user, Boolean.valueOf(true));
/*     */             }
/*     */           }
/*     */         }
/* 107 */         blfile.close();
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 111 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private static void loadConfig() {
/*     */     try {
/* 117 */       String confighead = "#CoreProtect Config\n";
/* 118 */       String noisy = "\n# If enabled, extra data is displayed when doing rollbacks and restores.\n# If disabled, you can manually trigger it in-game by adding \"#verbose\"\n# to the end of your rollback statement.\nverbose: true\n";
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 123 */       String mysql = "\n# MySQL is optional and not required.\n# If you prefer to use MySQL, enable the following and fill out the fields.\nuse-mysql: false\ntable-prefix: co_\nmysql-host: 127.0.0.1\nmysql-port: 3306\nmysql-database: database\nmysql-username: root\nmysql-password: \n";
/* 124 */       String update = "\n# If enabled, CoreProtect will check for updates when your server starts up.\n# If an update is available, you'll be notified via your server console.\ncheck-updates: true\n";
/* 125 */       String api = "\n# If enabled, other plugins will be able to utilize the CoreProtect API.\napi-enabled: true\n";
/* 126 */       String defaultradius = "\n# If no radius is specified in a rollback or restore, this value will be\n# used as the radius. Set to \"0\" to disable automatically adding a radius.\ndefault-radius: 10\n";
/* 127 */       String maxradius = "\n# The maximum radius that can be used in a command. Set to \"0\" to disable.\n# To run a rollback or restore without a radius, you can use \"r:#global\".\nmax-radius: 100\n";
/* 128 */       String rollbackitems = "\n# If enabled, items taken from containers (etc) will be included in rollbacks.\nrollback-items: true\n";
/* 129 */       String rollbackentities = "\n# If enabled, entities, such as killed animals, will be included in rollbacks.\nrollback-entities: true\n";
/* 130 */       String skipgenericdata = "\n# If enabled, generic data, like zombies burning in daylight, won't be logged.\nskip-generic-data: true\n";
/* 131 */       String blockplace = "\n# Logs blocks placed by players.\nblock-place: true\n";
/* 132 */       String blockbreak = "\n# Logs blocks broken by players.\nblock-break: true\n";
/* 133 */       String naturalbreak = "\n# Logs blocks that break off of other blocks; for example, a sign or torch\n# falling off of a dirt block that a player breaks. This is required for\n# beds/doors to properly rollback.\nnatural-break: true\n";
/* 134 */       String blockmovement = "\n# Properly track block movement, such as sand or gravel falling.\nblock-movement: true\n";
/* 135 */       String pistons = "\n# Properly track blocks moved by pistons.\npistons: true\n";
/* 136 */       String blockburn = "\n# Logs blocks that burn up in a fire.\nblock-burn: true\n";
/* 137 */       String blockignite = "\n# Logs when a block naturally ignites, such as from fire spreading.\nblock-ignite: true\n";
/* 138 */       String explosions = "\n# Logs explosions, such as TNT and Creepers.\nexplosions: true\n";
/* 139 */       String entitychange = "\n# Track when an entity changes a block, such as an Enderman destroying blocks.\nentity-change: true\n";
/* 140 */       String entitykills = "\n# Logs killed entities, such as killed cows and enderman.\nentity-kills: true\n";
/* 141 */       String signtext = "\n# Logs text on signs. If disabled, signs will be blank when rolled back.\nsign-text: true\n";
/* 142 */       String buckets = "\n# Logs lava and water sources placed/removed by players who are using buckets.\nbuckets: true\n";
/* 143 */       String leafdecay = "\n# Logs natural tree leaf decay.\nleaf-decay: true\n";
/* 144 */       String treegrowth = "\n# Logs tree growth. Trees are linked to the player who planted the sappling.\ntree-growth: true\n";
/* 145 */       String mushroomgrowth = "\n# Logs mushroom growth.\nmushroom-growth: true\n";
/* 146 */       String vinegrowth = "\n# Logs natural vine growth.\nvine-growth: true\n";
/* 147 */       String portals = "\n# Logs when portals such as Nether portals generate naturally.\nportals: true\n";
/* 148 */       String waterflow = "\n# Logs water flow. If water destroys other blocks, such as torches,\n# this allows it to be properly rolled back.\nwater-flow: true\n";
/* 149 */       String lavaflow = "\n# Logs lava flow. If lava destroys other blocks, such as torches,\n# this allows it to be properly rolled back.\nlava-flow: true\n";
/* 150 */       String liquidtracking = "\n# Allows liquid to be properly tracked and linked to players.\n# For example, if a player places water which flows and destroys torches,\n# it can all be properly restored by rolling back that single player.\nliquid-tracking: true\n";
/* 151 */       String itemlogging = "\n# Track item transactions, such as when a player takes items from a\n# chest, furnace, or dispenser. Necessary for any item based rollbacks.\nitem-transactions: true\n";
/* 152 */       String playerinteract = "\n# Track player interactions, such as when a player opens a door, presses\n# a button, or opens a chest. Player interactions can't be rolled back.\nplayer-interactions: true\n";
/* 153 */       String playermessages = "\n# Logs messages that players send in the chat.\nplayer-messages: true\n";
/* 154 */       String playercommands = "\n# Logs all commands used by players.\nplayer-commands: true\n";
/* 155 */       String playersessions = "\n# Logs the logins and logouts of players.\nplayer-sessions: true\n";
/* 156 */       String usernamechanges = "\n# Logs when a player changes their Minecraft username.\nusername-changes: true\n";
/* 157 */       String worldedit = "\n# Logs changes made via the plugin \"WorldEdit\" if it's in use on your server.\nworldedit: true\n";
/*     */       
/* 159 */       config.clear();
/* 160 */       File config_file = new File("plugins/CoreProtect/config.yml");
/* 161 */       boolean exists = config_file.exists();
/* 162 */       if (!exists) {
/* 163 */         config_file.createNewFile();
/*     */       }
/*     */       
/* 166 */       File dir = new File("plugins/CoreProtect");
/* 167 */       String[] children = dir.list();
/* 168 */       if (children != null) {
/* 169 */         for (String element : children) {
/* 170 */           String filename = element;
/* 171 */           if ((!filename.startsWith(".")) && (filename.endsWith(".yml"))) {
/*     */             try {
/* 173 */               String key = filename.replaceAll(".yml", "-");
/* 174 */               if (key.equals("config-")) {
/* 175 */                 key = "";
/*     */               }
/* 177 */               RandomAccessFile configfile = new RandomAccessFile("plugins/CoreProtect/" + filename, "rw");
/* 178 */               long config_length = configfile.length();
/* 179 */               if (config_length > 0L) {
/* 180 */                 while (configfile.getFilePointer() < configfile.length()) {
/* 181 */                   String line = configfile.readLine();
/* 182 */                   if ((line.contains(":")) && (!line.startsWith("#"))) {
/* 183 */                     line = line.replaceFirst(":", "§ ");
/* 184 */                     String[] i2 = line.split("§");
/* 185 */                     String option = i2[0].trim().toLowerCase();
/* 186 */                     if (key.length() == 0) {
/* 187 */                       if (option.equals("verbose")) {
/* 188 */                         String setting = i2[1].trim().toLowerCase();
/* 189 */                         if (setting.startsWith("t")) {
/* 190 */                           config.put(key + "verbose", Integer.valueOf(1));
/*     */                         }
/* 192 */                         else if (setting.startsWith("f")) {
/* 193 */                           config.put("verbose", Integer.valueOf(0));
/*     */                         }
/*     */                       }
/* 196 */                       if (option.equals("use-mysql")) {
/* 197 */                         String setting = i2[1].trim().toLowerCase();
/* 198 */                         if (setting.startsWith("t")) {
/* 199 */                           config.put("use-mysql", Integer.valueOf(1));
/*     */                         }
/* 201 */                         else if (setting.startsWith("f")) {
/* 202 */                           config.put("use-mysql", Integer.valueOf(0));
/*     */                         }
/*     */                       }
/* 205 */                       if (option.equals("table-prefix")) {
/* 206 */                         prefix = i2[1].trim();
/*     */                       }
/* 208 */                       if (option.equals("mysql-host")) {
/* 209 */                         host = i2[1].trim();
/*     */                       }
/* 211 */                       if (option.equals("mysql-port")) {
/* 212 */                         String setting = i2[1].trim();
/* 213 */                         setting = setting.replaceAll("[^0-9]", "");
/* 214 */                         if (setting.length() == 0) {
/* 215 */                           setting = "0";
/*     */                         }
/* 217 */                         port = Integer.parseInt(setting);
/*     */                       }
/* 219 */                       if (option.equals("mysql-database")) {
/* 220 */                         database = i2[1].trim();
/*     */                       }
/* 222 */                       if (option.equals("mysql-username")) {
/* 223 */                         username = i2[1].trim();
/*     */                       }
/* 225 */                       if (option.equals("mysql-password")) {
/* 226 */                         password = i2[1].trim();
/*     */                       }
/* 228 */                       if (option.equals("check-updates")) {
/* 229 */                         String setting = i2[1].trim().toLowerCase();
/* 230 */                         if (setting.startsWith("t")) {
/* 231 */                           config.put("check-updates", Integer.valueOf(1));
/*     */                         }
/* 233 */                         else if (setting.startsWith("f")) {
/* 234 */                           config.put("check-updates", Integer.valueOf(0));
/*     */                         }
/*     */                       }
/* 237 */                       if (option.equals("api-enabled")) {
/* 238 */                         String setting = i2[1].trim().toLowerCase();
/* 239 */                         if (setting.startsWith("t")) {
/* 240 */                           config.put("api-enabled", Integer.valueOf(1));
/*     */                         }
/* 242 */                         else if (setting.startsWith("f")) {
/* 243 */                           config.put("api-enabled", Integer.valueOf(0));
/*     */                         }
/*     */                       }
/* 246 */                       if (option.equals("default-radius")) {
/* 247 */                         String setting = i2[1].trim();
/* 248 */                         setting = setting.replaceAll("[^0-9]", "");
/* 249 */                         if (setting.length() == 0) {
/* 250 */                           setting = "0";
/*     */                         }
/* 252 */                         config.put("default-radius", Integer.valueOf(Integer.parseInt(setting)));
/*     */                       }
/* 254 */                       if (option.equals("max-radius")) {
/* 255 */                         String setting = i2[1].trim();
/* 256 */                         setting = setting.replaceAll("[^0-9]", "");
/* 257 */                         if (setting.length() == 0) {
/* 258 */                           setting = "0";
/*     */                         }
/* 260 */                         config.put("max-radius", Integer.valueOf(Integer.parseInt(setting)));
/*     */                       }
/* 262 */                       if (option.equals("rollback-items")) {
/* 263 */                         String setting = i2[1].trim().toLowerCase();
/* 264 */                         if (setting.startsWith("t")) {
/* 265 */                           config.put("rollback-items", Integer.valueOf(1));
/*     */                         }
/* 267 */                         else if (setting.startsWith("f")) {
/* 268 */                           config.put("rollback-items", Integer.valueOf(0));
/*     */                         }
/*     */                       }
/* 271 */                       if (option.equals("rollback-entities")) {
/* 272 */                         String setting = i2[1].trim().toLowerCase();
/* 273 */                         if (setting.startsWith("t")) {
/* 274 */                           config.put("rollback-entities", Integer.valueOf(1));
/*     */                         }
/* 276 */                         else if (setting.startsWith("f")) {
/* 277 */                           config.put("rollback-entities", Integer.valueOf(0));
/*     */                         }
/*     */                       }
/*     */                     }
/* 281 */                     if (option.equals("skip-generic-data")) {
/* 282 */                       String setting = i2[1].trim().toLowerCase();
/* 283 */                       if (setting.startsWith("t")) {
/* 284 */                         config.put(key + "skip-generic-data", Integer.valueOf(1));
/*     */                       }
/* 286 */                       else if (setting.startsWith("f")) {
/* 287 */                         config.put(key + "skip-generic-data", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 290 */                     if (option.equals("block-place")) {
/* 291 */                       String setting = i2[1].trim().toLowerCase();
/* 292 */                       if (setting.startsWith("t")) {
/* 293 */                         config.put(key + "block-place", Integer.valueOf(1));
/*     */                       }
/* 295 */                       else if (setting.startsWith("f")) {
/* 296 */                         config.put(key + "block-place", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 299 */                     if (option.equals("block-break")) {
/* 300 */                       String setting = i2[1].trim().toLowerCase();
/* 301 */                       if (setting.startsWith("t")) {
/* 302 */                         config.put(key + "block-break", Integer.valueOf(1));
/*     */                       }
/* 304 */                       else if (setting.startsWith("f")) {
/* 305 */                         config.put(key + "block-break", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 308 */                     if (option.equals("natural-break")) {
/* 309 */                       String setting = i2[1].trim().toLowerCase();
/* 310 */                       if (setting.startsWith("t")) {
/* 311 */                         config.put(key + "natural-break", Integer.valueOf(1));
/*     */                       }
/* 313 */                       else if (setting.startsWith("f")) {
/* 314 */                         config.put(key + "natural-break", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 317 */                     if (option.equals("block-movement")) {
/* 318 */                       String setting = i2[1].trim().toLowerCase();
/* 319 */                       if (setting.startsWith("t")) {
/* 320 */                         config.put(key + "block-movement", Integer.valueOf(1));
/*     */                       }
/* 322 */                       else if (setting.startsWith("f")) {
/* 323 */                         config.put(key + "block-movement", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 326 */                     if (option.equals("pistons")) {
/* 327 */                       String setting = i2[1].trim().toLowerCase();
/* 328 */                       if (setting.startsWith("t")) {
/* 329 */                         config.put(key + "pistons", Integer.valueOf(1));
/*     */                       }
/* 331 */                       else if (setting.startsWith("f")) {
/* 332 */                         config.put(key + "pistons", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 335 */                     if (option.equals("block-burn")) {
/* 336 */                       String setting = i2[1].trim().toLowerCase();
/* 337 */                       if (setting.startsWith("t")) {
/* 338 */                         config.put(key + "block-burn", Integer.valueOf(1));
/*     */                       }
/* 340 */                       else if (setting.startsWith("f")) {
/* 341 */                         config.put(key + "block-burn", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 344 */                     if (option.equals("block-ignite")) {
/* 345 */                       String setting = i2[1].trim().toLowerCase();
/* 346 */                       if (setting.startsWith("t")) {
/* 347 */                         config.put(key + "block-ignite", Integer.valueOf(1));
/*     */                       }
/* 349 */                       else if (setting.startsWith("f")) {
/* 350 */                         config.put(key + "block-ignite", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 353 */                     if (option.equals("explosions")) {
/* 354 */                       String setting = i2[1].trim().toLowerCase();
/* 355 */                       if (setting.startsWith("t")) {
/* 356 */                         config.put(key + "explosions", Integer.valueOf(1));
/*     */                       }
/* 358 */                       else if (setting.startsWith("f")) {
/* 359 */                         config.put(key + "explosions", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 362 */                     if (option.equals("entity-change")) {
/* 363 */                       String setting = i2[1].trim().toLowerCase();
/* 364 */                       if (setting.startsWith("t")) {
/* 365 */                         config.put(key + "entity-change", Integer.valueOf(1));
/*     */                       }
/* 367 */                       else if (setting.startsWith("f")) {
/* 368 */                         config.put(key + "entity-change", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 371 */                     if (option.equals("entity-kills")) {
/* 372 */                       String setting = i2[1].trim().toLowerCase();
/* 373 */                       if (setting.startsWith("t")) {
/* 374 */                         config.put(key + "entity-kills", Integer.valueOf(1));
/*     */                       }
/* 376 */                       else if (setting.startsWith("f")) {
/* 377 */                         config.put(key + "entity-kills", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 380 */                     if (option.equals("sign-text")) {
/* 381 */                       String setting = i2[1].trim().toLowerCase();
/* 382 */                       if (setting.startsWith("t")) {
/* 383 */                         config.put(key + "sign-text", Integer.valueOf(1));
/*     */                       }
/* 385 */                       else if (setting.startsWith("f")) {
/* 386 */                         config.put(key + "sign-text", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 389 */                     if (option.equals("buckets")) {
/* 390 */                       String setting = i2[1].trim().toLowerCase();
/* 391 */                       if (setting.startsWith("t")) {
/* 392 */                         config.put(key + "buckets", Integer.valueOf(1));
/*     */                       }
/* 394 */                       else if (setting.startsWith("f")) {
/* 395 */                         config.put(key + "buckets", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 398 */                     if (option.equals("leaf-decay")) {
/* 399 */                       String setting = i2[1].trim().toLowerCase();
/* 400 */                       if (setting.startsWith("t")) {
/* 401 */                         config.put(key + "leaf-decay", Integer.valueOf(1));
/*     */                       }
/* 403 */                       else if (setting.startsWith("f")) {
/* 404 */                         config.put(key + "leaf-decay", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 407 */                     if (option.equals("tree-growth")) {
/* 408 */                       String setting = i2[1].trim().toLowerCase();
/* 409 */                       if (setting.startsWith("t")) {
/* 410 */                         config.put(key + "tree-growth", Integer.valueOf(1));
/*     */                       }
/* 412 */                       else if (setting.startsWith("f")) {
/* 413 */                         config.put(key + "tree-growth", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 416 */                     if (option.equals("mushroom-growth")) {
/* 417 */                       String setting = i2[1].trim().toLowerCase();
/* 418 */                       if (setting.startsWith("t")) {
/* 419 */                         config.put(key + "mushroom-growth", Integer.valueOf(1));
/*     */                       }
/* 421 */                       else if (setting.startsWith("f")) {
/* 422 */                         config.put(key + "mushroom-growth", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 425 */                     if (option.equals("vine-growth")) {
/* 426 */                       String setting = i2[1].trim().toLowerCase();
/* 427 */                       if (setting.startsWith("t")) {
/* 428 */                         config.put(key + "vine-growth", Integer.valueOf(1));
/*     */                       }
/* 430 */                       else if (setting.startsWith("f")) {
/* 431 */                         config.put(key + "vine-growth", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 434 */                     if (option.equals("portals")) {
/* 435 */                       String setting = i2[1].trim().toLowerCase();
/* 436 */                       if (setting.startsWith("t")) {
/* 437 */                         config.put(key + "portals", Integer.valueOf(1));
/*     */                       }
/* 439 */                       else if (setting.startsWith("f")) {
/* 440 */                         config.put(key + "portals", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 443 */                     if (option.equals("water-flow")) {
/* 444 */                       String setting = i2[1].trim().toLowerCase();
/* 445 */                       if (setting.startsWith("t")) {
/* 446 */                         config.put(key + "water-flow", Integer.valueOf(1));
/*     */                       }
/* 448 */                       else if (setting.startsWith("f")) {
/* 449 */                         config.put(key + "water-flow", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 452 */                     if (option.equals("lava-flow")) {
/* 453 */                       String setting = i2[1].trim().toLowerCase();
/* 454 */                       if (setting.startsWith("t")) {
/* 455 */                         config.put(key + "lava-flow", Integer.valueOf(1));
/*     */                       }
/* 457 */                       else if (setting.startsWith("f")) {
/* 458 */                         config.put(key + "lava-flow", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 461 */                     if (option.equals("liquid-tracking")) {
/* 462 */                       String setting = i2[1].trim().toLowerCase();
/* 463 */                       if (setting.startsWith("t")) {
/* 464 */                         config.put(key + "liquid-tracking", Integer.valueOf(1));
/*     */                       }
/* 466 */                       else if (setting.startsWith("f")) {
/* 467 */                         config.put(key + "liquid-tracking", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/*     */                     
/* 471 */                     if (option.equals("item-transactions")) {
/* 472 */                       String setting = i2[1].trim().toLowerCase();
/* 473 */                       if (setting.startsWith("t")) {
/* 474 */                         config.put(key + "item-transactions", Integer.valueOf(1));
/*     */                       }
/* 476 */                       else if (setting.startsWith("f")) {
/* 477 */                         config.put(key + "item-transactions", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 480 */                     if (option.equals("player-interactions")) {
/* 481 */                       String setting = i2[1].trim().toLowerCase();
/* 482 */                       if (setting.startsWith("t")) {
/* 483 */                         config.put(key + "player-interactions", Integer.valueOf(1));
/*     */                       }
/* 485 */                       else if (setting.startsWith("f")) {
/* 486 */                         config.put(key + "player-interactions", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 489 */                     if (option.equals("player-messages")) {
/* 490 */                       String setting = i2[1].trim().toLowerCase();
/* 491 */                       if (setting.startsWith("t")) {
/* 492 */                         config.put(key + "player-messages", Integer.valueOf(1));
/*     */                       }
/* 494 */                       else if (setting.startsWith("f")) {
/* 495 */                         config.put(key + "player-messages", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 498 */                     if (option.equals("player-commands")) {
/* 499 */                       String setting = i2[1].trim().toLowerCase();
/* 500 */                       if (setting.startsWith("t")) {
/* 501 */                         config.put(key + "player-commands", Integer.valueOf(1));
/*     */                       }
/* 503 */                       else if (setting.startsWith("f")) {
/* 504 */                         config.put(key + "player-commands", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 507 */                     if (option.equals("player-sessions")) {
/* 508 */                       String setting = i2[1].trim().toLowerCase();
/* 509 */                       if (setting.startsWith("t")) {
/* 510 */                         config.put(key + "player-sessions", Integer.valueOf(1));
/*     */                       }
/* 512 */                       else if (setting.startsWith("f")) {
/* 513 */                         config.put(key + "player-sessions", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 516 */                     if (option.equals("username-changes")) {
/* 517 */                       String setting = i2[1].trim().toLowerCase();
/* 518 */                       if (setting.startsWith("t")) {
/* 519 */                         config.put(key + "username-changes", Integer.valueOf(1));
/*     */                       }
/* 521 */                       else if (setting.startsWith("f")) {
/* 522 */                         config.put(key + "username-changes", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/* 525 */                     if (option.equals("worldedit")) {
/* 526 */                       String setting = i2[1].trim().toLowerCase();
/* 527 */                       if (setting.startsWith("t")) {
/* 528 */                         config.put(key + "worldedit", Integer.valueOf(1));
/*     */                       }
/* 530 */                       else if (setting.startsWith("f")) {
/* 531 */                         config.put(key + "worldedit", Integer.valueOf(0));
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                 }
/*     */               }
/*     */               
/* 538 */               if (key.length() == 0) {
/* 539 */                 if (config_length < 1L) {
/* 540 */                   configfile.write(confighead.getBytes());
/*     */                 }
/* 542 */                 if (config.get("verbose") == null) {
/* 543 */                   config.put("verbose", Integer.valueOf(1));
/* 544 */                   configfile.seek(configfile.length());
/* 545 */                   configfile.write(noisy.getBytes());
/*     */                 }
/* 547 */                 if (config.get("use-mysql") == null) {
/* 548 */                   config.put("use-mysql", Integer.valueOf(0));
/* 549 */                   configfile.seek(configfile.length());
/* 550 */                   configfile.write(mysql.getBytes());
/*     */                 }
/* 552 */                 if (config.get("check-updates") == null) {
/* 553 */                   config.put("check-updates", Integer.valueOf(1));
/* 554 */                   configfile.seek(configfile.length());
/* 555 */                   configfile.write(update.getBytes());
/*     */                 }
/* 557 */                 if (config.get("api-enabled") == null) {
/* 558 */                   config.put("api-enabled", Integer.valueOf(1));
/* 559 */                   configfile.seek(configfile.length());
/* 560 */                   configfile.write(api.getBytes());
/*     */                 }
/* 562 */                 if (config.get("default-radius") == null) {
/* 563 */                   config.put("default-radius", Integer.valueOf(10));
/* 564 */                   configfile.seek(configfile.length());
/* 565 */                   configfile.write(defaultradius.getBytes());
/*     */                 }
/* 567 */                 if (config.get("max-radius") == null) {
/* 568 */                   config.put("max-radius", Integer.valueOf(100));
/* 569 */                   configfile.seek(configfile.length());
/* 570 */                   configfile.write(maxradius.getBytes());
/*     */                 }
/* 572 */                 if (config.get("rollback-items") == null) {
/* 573 */                   config.put("rollback-items", Integer.valueOf(1));
/* 574 */                   configfile.seek(configfile.length());
/* 575 */                   configfile.write(rollbackitems.getBytes());
/*     */                 }
/* 577 */                 if (config.get("rollback-entities") == null) {
/* 578 */                   config.put("rollback-entities", Integer.valueOf(1));
/* 579 */                   configfile.seek(configfile.length());
/* 580 */                   configfile.write(rollbackentities.getBytes());
/*     */                 }
/* 582 */                 if (config.get("skip-generic-data") == null) {
/* 583 */                   config.put("skip-generic-data", Integer.valueOf(1));
/* 584 */                   configfile.seek(configfile.length());
/* 585 */                   configfile.write(skipgenericdata.getBytes());
/*     */                 }
/* 587 */                 if (config.get("block-place") == null) {
/* 588 */                   config.put("block-place", Integer.valueOf(1));
/* 589 */                   configfile.seek(configfile.length());
/* 590 */                   configfile.write(blockplace.getBytes());
/*     */                 }
/* 592 */                 if (config.get("block-break") == null) {
/* 593 */                   config.put("block-break", Integer.valueOf(1));
/* 594 */                   configfile.seek(configfile.length());
/* 595 */                   configfile.write(blockbreak.getBytes());
/*     */                 }
/* 597 */                 if (config.get("natural-break") == null) {
/* 598 */                   config.put("natural-break", Integer.valueOf(1));
/* 599 */                   configfile.seek(configfile.length());
/* 600 */                   configfile.write(naturalbreak.getBytes());
/*     */                 }
/* 602 */                 if (config.get("block-movement") == null) {
/* 603 */                   config.put("block-movement", Integer.valueOf(1));
/* 604 */                   configfile.seek(configfile.length());
/* 605 */                   configfile.write(blockmovement.getBytes());
/*     */                 }
/* 607 */                 if (config.get("pistons") == null) {
/* 608 */                   config.put("pistons", Integer.valueOf(1));
/* 609 */                   configfile.seek(configfile.length());
/* 610 */                   configfile.write(pistons.getBytes());
/*     */                 }
/* 612 */                 if (config.get("block-burn") == null) {
/* 613 */                   config.put("block-burn", Integer.valueOf(1));
/* 614 */                   configfile.seek(configfile.length());
/* 615 */                   configfile.write(blockburn.getBytes());
/*     */                 }
/* 617 */                 if (config.get("block-ignite") == null) {
/* 618 */                   config.put("block-ignite", Integer.valueOf(1));
/* 619 */                   configfile.seek(configfile.length());
/* 620 */                   configfile.write(blockignite.getBytes());
/*     */                 }
/* 622 */                 if (config.get("explosions") == null) {
/* 623 */                   config.put("explosions", Integer.valueOf(1));
/* 624 */                   configfile.seek(configfile.length());
/* 625 */                   configfile.write(explosions.getBytes());
/*     */                 }
/* 627 */                 if (config.get("entity-change") == null) {
/* 628 */                   config.put("entity-change", Integer.valueOf(1));
/* 629 */                   configfile.seek(configfile.length());
/* 630 */                   configfile.write(entitychange.getBytes());
/*     */                 }
/* 632 */                 if (config.get("entity-kills") == null) {
/* 633 */                   config.put("entity-kills", Integer.valueOf(1));
/* 634 */                   configfile.seek(configfile.length());
/* 635 */                   configfile.write(entitykills.getBytes());
/*     */                 }
/* 637 */                 if (config.get("sign-text") == null) {
/* 638 */                   config.put("sign-text", Integer.valueOf(1));
/* 639 */                   configfile.seek(configfile.length());
/* 640 */                   configfile.write(signtext.getBytes());
/*     */                 }
/* 642 */                 if (config.get("buckets") == null) {
/* 643 */                   config.put("buckets", Integer.valueOf(1));
/* 644 */                   configfile.seek(configfile.length());
/* 645 */                   configfile.write(buckets.getBytes());
/*     */                 }
/* 647 */                 if (config.get("leaf-decay") == null) {
/* 648 */                   config.put("leaf-decay", Integer.valueOf(1));
/* 649 */                   configfile.seek(configfile.length());
/* 650 */                   configfile.write(leafdecay.getBytes());
/*     */                 }
/* 652 */                 if (config.get("tree-growth") == null) {
/* 653 */                   config.put("tree-growth", Integer.valueOf(1));
/* 654 */                   configfile.seek(configfile.length());
/* 655 */                   configfile.write(treegrowth.getBytes());
/*     */                 }
/* 657 */                 if (config.get("mushroom-growth") == null) {
/* 658 */                   config.put("mushroom-growth", Integer.valueOf(1));
/* 659 */                   configfile.seek(configfile.length());
/* 660 */                   configfile.write(mushroomgrowth.getBytes());
/*     */                 }
/* 662 */                 if (config.get("vine-growth") == null) {
/* 663 */                   config.put("vine-growth", Integer.valueOf(1));
/* 664 */                   configfile.seek(configfile.length());
/* 665 */                   configfile.write(vinegrowth.getBytes());
/*     */                 }
/* 667 */                 if (config.get("portals") == null) {
/* 668 */                   config.put("portals", Integer.valueOf(1));
/* 669 */                   configfile.seek(configfile.length());
/* 670 */                   configfile.write(portals.getBytes());
/*     */                 }
/* 672 */                 if (config.get("water-flow") == null) {
/* 673 */                   config.put("water-flow", Integer.valueOf(1));
/* 674 */                   configfile.seek(configfile.length());
/* 675 */                   configfile.write(waterflow.getBytes());
/*     */                 }
/* 677 */                 if (config.get("lava-flow") == null) {
/* 678 */                   config.put("lava-flow", Integer.valueOf(1));
/* 679 */                   configfile.seek(configfile.length());
/* 680 */                   configfile.write(lavaflow.getBytes());
/*     */                 }
/* 682 */                 if (config.get("liquid-tracking") == null) {
/* 683 */                   config.put("liquid-tracking", Integer.valueOf(1));
/* 684 */                   configfile.seek(configfile.length());
/* 685 */                   configfile.write(liquidtracking.getBytes());
/*     */                 }
/* 687 */                 if (config.get("item-transactions") == null) {
/* 688 */                   config.put("item-transactions", Integer.valueOf(1));
/* 689 */                   configfile.seek(configfile.length());
/* 690 */                   configfile.write(itemlogging.getBytes());
/*     */                 }
/* 692 */                 if (config.get("player-interactions") == null) {
/* 693 */                   config.put("player-interactions", Integer.valueOf(1));
/* 694 */                   configfile.seek(configfile.length());
/* 695 */                   configfile.write(playerinteract.getBytes());
/*     */                 }
/* 697 */                 if (config.get("player-messages") == null) {
/* 698 */                   config.put("player-messages", Integer.valueOf(1));
/* 699 */                   configfile.seek(configfile.length());
/* 700 */                   configfile.write(playermessages.getBytes());
/*     */                 }
/* 702 */                 if (config.get("player-commands") == null) {
/* 703 */                   config.put("player-commands", Integer.valueOf(1));
/* 704 */                   configfile.seek(configfile.length());
/* 705 */                   configfile.write(playercommands.getBytes());
/*     */                 }
/* 707 */                 if (config.get("player-sessions") == null) {
/* 708 */                   config.put("player-sessions", Integer.valueOf(1));
/* 709 */                   configfile.seek(configfile.length());
/* 710 */                   configfile.write(playersessions.getBytes());
/*     */                 }
/* 712 */                 if (config.get("username-changes") == null) {
/* 713 */                   config.put("username-changes", Integer.valueOf(1));
/* 714 */                   configfile.seek(configfile.length());
/* 715 */                   configfile.write(usernamechanges.getBytes());
/*     */                 }
/* 717 */                 if (config.get("worldedit") == null) {
/* 718 */                   config.put("worldedit", Integer.valueOf(1));
/* 719 */                   configfile.seek(configfile.length());
/* 720 */                   configfile.write(worldedit.getBytes());
/*     */                 }
/*     */               }
/* 723 */               configfile.close();
/*     */             }
/*     */             catch (Exception e) {
/* 726 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 733 */       if (((Integer)config.get("use-mysql")).intValue() == 0) {
/* 734 */         prefix = "co_";
/*     */       }
/*     */       
/* 737 */       loadBlacklist();
/*     */     }
/*     */     catch (Exception e) {
/* 740 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void loadDatabase() {
/* 745 */     if (((Integer)config.get("use-mysql")).intValue() == 0) {
/*     */       try {
/* 747 */         File tempFile = File.createTempFile("CoreProtect_" + System.currentTimeMillis(), ".tmp");
/* 748 */         tempFile.setExecutable(true);
/* 749 */         if (!tempFile.canExecute()) {
/* 750 */           File tempFolder = new File("cache");
/* 751 */           boolean exists = tempFolder.exists();
/* 752 */           if (!exists) {
/* 753 */             tempFolder.mkdir();
/*     */           }
/* 755 */           System.setProperty("java.io.tmpdir", "cache");
/*     */         }
/* 757 */         tempFile.delete();
/*     */       }
/*     */       catch (Exception e) {
/* 760 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */     
/* 764 */     Functions.createDatabaseTables(prefix, false);
/*     */   }
/*     */   
/*     */   private static void loadTypes(Statement statement) {
/*     */     try {
/* 769 */       materials.clear();
/* 770 */       materials_reversed.clear();
/* 771 */       material_id = 0;
/*     */       
/* 773 */       String query = "SELECT id,material FROM " + prefix + "material_map";
/* 774 */       ResultSet rs = statement.executeQuery(query);
/* 775 */       while (rs.next()) {
/* 776 */         int id = rs.getInt("id");
/* 777 */         String material = rs.getString("material");
/* 778 */         materials.put(material, Integer.valueOf(id));
/* 779 */         materials_reversed.put(Integer.valueOf(id), material);
/* 780 */         if (id > material_id) {
/* 781 */           material_id = id;
/*     */         }
/*     */       }
/* 784 */       rs.close();
/*     */       
/* 786 */       art.clear();
/* 787 */       art_reversed.clear();
/* 788 */       art_id = 0;
/*     */       
/* 790 */       query = "SELECT id,art FROM " + prefix + "art_map";
/* 791 */       rs = statement.executeQuery(query);
/* 792 */       while (rs.next()) {
/* 793 */         int id = rs.getInt("id");
/* 794 */         String art = rs.getString("art");
/* 795 */         art.put(art, Integer.valueOf(id));
/* 796 */         art_reversed.put(Integer.valueOf(id), art);
/* 797 */         if (id > art_id) {
/* 798 */           art_id = id;
/*     */         }
/*     */       }
/* 801 */       rs.close();
/*     */       
/* 803 */       entities.clear();
/* 804 */       entities_reversed.clear();
/* 805 */       entity_id = 0;
/*     */       
/* 807 */       query = "SELECT id,entity FROM " + prefix + "entity_map";
/* 808 */       rs = statement.executeQuery(query);
/* 809 */       while (rs.next()) {
/* 810 */         int id = rs.getInt("id");
/* 811 */         String entity = rs.getString("entity");
/* 812 */         entities.put(entity, Integer.valueOf(id));
/* 813 */         entities_reversed.put(Integer.valueOf(id), entity);
/* 814 */         if (id > entity_id) {
/* 815 */           entity_id = id;
/*     */         }
/*     */       }
/* 818 */       rs.close();
/*     */     }
/*     */     catch (Exception e) {
/* 821 */       e.printStackTrace();
/*     */     }
/*     */     
/* 824 */     BlockInfo.loadData();
/*     */   }
/*     */   
/*     */   private static void loadWorlds(Statement statement) {
/*     */     try {
/* 829 */       worlds.clear();
/* 830 */       worlds_reversed.clear();
/* 831 */       world_id = 0;
/*     */       
/* 833 */       String query = "SELECT id,world FROM " + prefix + "world";
/* 834 */       ResultSet rs = statement.executeQuery(query);
/* 835 */       while (rs.next()) {
/* 836 */         int id = rs.getInt("id");
/* 837 */         String world = rs.getString("world");
/* 838 */         worlds.put(world, Integer.valueOf(id));
/* 839 */         worlds_reversed.put(Integer.valueOf(id), world);
/* 840 */         if (id > world_id) {
/* 841 */           world_id = id;
/*     */         }
/*     */       }
/*     */       
/* 845 */       List<World> worlds = CoreProtect.getInstance().getServer().getWorlds();
/* 846 */       for (World world : worlds) {
/* 847 */         String worldname = world.getName();
/* 848 */         if (worlds.get(worldname) == null) {
/* 849 */           int id = world_id + 1;
/* 850 */           worlds.put(worldname, Integer.valueOf(id));
/* 851 */           worlds_reversed.put(Integer.valueOf(id), worldname);
/* 852 */           world_id = id;
/* 853 */           Queue.queueWorldInsert(id, worldname);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 858 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean performInitialization() {
/*     */     try {
/* 864 */       loadConfig();
/* 865 */       loadDatabase();
/*     */       
/* 867 */       Connection connection = Database.getConnection(true);
/* 868 */       Statement statement = connection.createStatement();
/*     */       
/* 870 */       checkPlayers(connection);
/* 871 */       loadWorlds(statement);
/* 872 */       loadTypes(statement);
/*     */       
/*     */ 
/* 875 */       if (Functions.checkWorldEdit()) {
/* 876 */         Functions.loadWorldEdit();
/*     */       }
/*     */       
/* 879 */       server_running = true;
/* 880 */       Patch.versionCheck(statement);
/*     */       
/* 882 */       statement.close();
/* 883 */       connection.close();
/*     */       
/* 885 */       return true;
/*     */     }
/*     */     catch (Exception e) {
/* 888 */       e.printStackTrace();
/*     */     }
/*     */     
/* 891 */     return false;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\model\Config.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */