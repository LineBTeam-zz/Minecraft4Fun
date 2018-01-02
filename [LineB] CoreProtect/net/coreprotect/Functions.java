/*      */ package net.coreprotect;
/*      */ 
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.ObjectOutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.sql.Connection;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.Statement;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.SortedSet;
/*      */ import net.coreprotect.consumer.Queue;
/*      */ import net.coreprotect.database.Database;
/*      */ import net.coreprotect.database.Lookup;
/*      */ import net.coreprotect.model.BlockInfo;
/*      */ import net.coreprotect.model.Config;
/*      */ import net.coreprotect.worldedit.CoreProtectEditSessionEvent;
/*      */ import org.bukkit.Art;
/*      */ import org.bukkit.Chunk;
/*      */ import org.bukkit.DyeColor;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.Server;
/*      */ import org.bukkit.SkullType;
/*      */ import org.bukkit.World;
/*      */ import org.bukkit.attribute.Attributable;
/*      */ import org.bukkit.attribute.Attribute;
/*      */ import org.bukkit.attribute.AttributeInstance;
/*      */ import org.bukkit.attribute.AttributeModifier;
/*      */ import org.bukkit.block.Banner;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.block.BlockFace;
/*      */ import org.bukkit.block.BlockState;
/*      */ import org.bukkit.block.Chest;
/*      */ import org.bukkit.block.CommandBlock;
/*      */ import org.bukkit.block.ShulkerBox;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.command.ConsoleCommandSender;
/*      */ import org.bukkit.configuration.serialization.DelegateDeserialization;
/*      */ import org.bukkit.enchantments.Enchantment;
/*      */ import org.bukkit.entity.AbstractHorse;
/*      */ import org.bukkit.entity.Ageable;
/*      */ import org.bukkit.entity.ChestedHorse;
/*      */ import org.bukkit.entity.Creeper;
/*      */ import org.bukkit.entity.Enderman;
/*      */ import org.bukkit.entity.Entity;
/*      */ import org.bukkit.entity.EntityType;
/*      */ import org.bukkit.entity.Horse;
/*      */ import org.bukkit.entity.Horse.Color;
/*      */ import org.bukkit.entity.Horse.Style;
/*      */ import org.bukkit.entity.IronGolem;
/*      */ import org.bukkit.entity.ItemFrame;
/*      */ import org.bukkit.entity.LivingEntity;
/*      */ import org.bukkit.entity.Llama;
/*      */ import org.bukkit.entity.Llama.Color;
/*      */ import org.bukkit.entity.Ocelot;
/*      */ import org.bukkit.entity.Ocelot.Type;
/*      */ import org.bukkit.entity.Painting;
/*      */ import org.bukkit.entity.Pig;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.entity.Sheep;
/*      */ import org.bukkit.entity.Slime;
/*      */ import org.bukkit.entity.Tameable;
/*      */ import org.bukkit.entity.Villager;
/*      */ import org.bukkit.entity.Villager.Profession;
/*      */ import org.bukkit.entity.Wolf;
/*      */ import org.bukkit.entity.Zombie;
/*      */ import org.bukkit.entity.ZombieVillager;
/*      */ import org.bukkit.inventory.EntityEquipment;
/*      */ import org.bukkit.inventory.HorseInventory;
/*      */ import org.bukkit.inventory.Inventory;
/*      */ import org.bukkit.inventory.InventoryHolder;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.inventory.MerchantRecipe;
/*      */ import org.bukkit.inventory.meta.ItemMeta;
/*      */ import org.bukkit.material.MaterialData;
/*      */ import org.bukkit.plugin.Plugin;
/*      */ import org.bukkit.plugin.PluginManager;
/*      */ import org.bukkit.scheduler.BukkitScheduler;
/*      */ 
/*      */ public class Functions extends Queue
/*      */ {
/*   87 */   private static java.util.regex.Pattern csvSplitter = java.util.regex.Pattern.compile(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
/*      */   
/*      */   public static int block_id(Material material) {
/*   90 */     if (material == null) {
/*   91 */       material = Material.AIR;
/*      */     }
/*   93 */     return block_id(material.name(), true);
/*      */   }
/*      */   
/*      */   public static int block_id(String name, boolean internal) {
/*   97 */     int id = -1;
/*      */     
/*   99 */     name = name.toLowerCase().trim();
/*  100 */     if (!name.contains(":")) {
/*  101 */       name = "minecraft:" + name;
/*      */     }
/*      */     
/*  104 */     if (Config.materials.get(name) != null) {
/*  105 */       id = ((Integer)Config.materials.get(name)).intValue();
/*      */     }
/*  107 */     else if (internal == true) {
/*  108 */       int mid = Config.material_id + 1;
/*  109 */       Config.materials.put(name, Integer.valueOf(mid));
/*  110 */       Config.materials_reversed.put(Integer.valueOf(mid), name);
/*  111 */       Config.material_id = mid;
/*  112 */       Queue.queueMaterialInsert(mid, name);
/*  113 */       id = ((Integer)Config.materials.get(name)).intValue();
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  124 */     return id;
/*      */   }
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
/*      */   public static String block_name_lookup(int id)
/*      */   {
/*  146 */     String name = "";
/*  147 */     if (Config.materials_reversed.get(Integer.valueOf(id)) != null) {
/*  148 */       name = (String)Config.materials_reversed.get(Integer.valueOf(id));
/*      */     }
/*  150 */     else if (BlockInfo.legacy_block_names.get(Integer.valueOf(id)) != null) {
/*  151 */       name = (String)BlockInfo.legacy_block_names.get(Integer.valueOf(id));
/*      */     }
/*  153 */     return name;
/*      */   }
/*      */   
/*      */   public static String block_name_short(int id) {
/*  157 */     String name = block_name_lookup(id);
/*  158 */     if (name.contains(":")) {
/*  159 */       String[] block_name_split = name.split(":");
/*  160 */       name = block_name_split[1];
/*      */     }
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
/*  188 */     return name;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static int checkConfig(World world, String option)
/*      */   {
/*  198 */     int result = -1;
/*  199 */     if (Config.config.get(world.getName() + "-" + option) != null) {
/*  200 */       result = ((Integer)Config.config.get(world.getName() + "-" + option)).intValue();
/*      */     }
/*  202 */     else if (Config.config.get(option) != null) {
/*  203 */       result = ((Integer)Config.config.get(option)).intValue();
/*      */     }
/*  205 */     return result;
/*      */   }
/*      */   
/*      */   public static void combine_items(Material material, ItemStack[] items) {
/*  209 */     if (material.equals(Material.ARMOR_STAND)) {
/*  210 */       return;
/*      */     }
/*      */     try {
/*  213 */       int c1 = 0;
/*  214 */       for (ItemStack o1 : items) {
/*  215 */         int c2 = 0;
/*  216 */         for (ItemStack o2 : items) {
/*  217 */           if ((o1 != null) && (o2 != null) && 
/*  218 */             (o1.isSimilar(o2)) && (c2 > c1)) {
/*  219 */             int namount = o1.getAmount() + o2.getAmount();
/*  220 */             o1.setAmount(namount);
/*  221 */             o2.setAmount(0);
/*      */           }
/*      */           
/*  224 */           c2++;
/*      */         }
/*  226 */         c1++;
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  230 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static Integer[] convertArray(String[] array) {
/*  235 */     List<Integer> list = new ArrayList();
/*  236 */     for (String item : array) {
/*  237 */       list.add(Integer.valueOf(Integer.parseInt(item)));
/*      */     }
/*  239 */     return (Integer[])list.toArray(new Integer[list.size()]);
/*      */   }
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
/*      */   public static byte[] convertByteData(Object data)
/*      */   {
/*  255 */     byte[] result = null;
/*      */     try {
/*  257 */       ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*  258 */       ObjectOutputStream oos = new ObjectOutputStream(bos);
/*  259 */       oos.writeObject(data);
/*  260 */       oos.flush();
/*  261 */       oos.close();
/*  262 */       bos.close();
/*  263 */       result = bos.toByteArray();
/*      */     }
/*      */     catch (Exception e) {
/*  266 */       e.printStackTrace();
/*      */     }
/*  268 */     return result;
/*      */   }
/*      */   
/*      */   public static void createDatabaseTables(String prefix, boolean purge) {
/*  272 */     Config.databaseTables.clear();
/*  273 */     Config.databaseTables.addAll(Arrays.asList(new String[] { "art_map", "block", "chat", "command", "container", "entity", "entity_map", "material_map", "session", "sign", "skull", "user", "username_log", "version", "world" }));
/*      */     
/*  275 */     if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/*  276 */       boolean success = false;
/*      */       try {
/*  278 */         Connection connection = Database.getConnection(true);
/*  279 */         if (connection != null) {
/*  280 */           String index = "";
/*  281 */           Statement statement = connection.createStatement();
/*  282 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "art_map(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),id int(8),art varchar(255)) ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  283 */           index = ", INDEX(wid,x,z,time), INDEX(user,time), INDEX(type,time)";
/*  284 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "block(rowid int(10) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid), time int(10), user int(8), wid int(4), x int(8), y int(3), z int(8), type int(6), data int(8), meta blob, action int(2), rolled_back tinyint(1)" + index + ") ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  285 */           index = ", INDEX(time), INDEX(user,time)";
/*  286 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "chat(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),time int(10), user int(8), message varchar(255)" + index + ") ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  287 */           index = ", INDEX(time), INDEX(user,time)";
/*  288 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "command(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),time int(10), user int(8), message varchar(255)" + index + ") ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  289 */           index = ", INDEX(wid,x,z,time), INDEX(user,time), INDEX(type,time)";
/*  290 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "container(rowid int(10) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid), time int(10), user int(8), wid int(4), x int(8), y int(3), z int(8), type int(6), data int(6), amount int(4), metadata blob, action int(2), rolled_back tinyint(1)" + index + ") ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  291 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "entity(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid), time int(10), data blob) ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  292 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "entity_map(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),id int(8),entity varchar(255)) ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  293 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "material_map(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),id int(8),material varchar(255)) ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  294 */           index = ", INDEX(wid,x,z,time), INDEX(action,time), INDEX(user,time), INDEX(time)";
/*  295 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "session(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),time int(10), user int(8), wid int(4), x int(8), y int (3), z int(8), action int(1)" + index + ") ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  296 */           index = ", INDEX(wid,x,z,y,time)";
/*  297 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "sign(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),time int(10), user int(8), wid int(4), x int(8), y int(3), z int(8), line_1 varchar(100), line_2 varchar(100), line_3 varchar(100), line_4 varchar(100)" + index + ") ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  298 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "skull(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid), time int(10), type int(2), data int(1), rotation int(2), owner varchar(16)) ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  299 */           index = ", INDEX(user), INDEX(uuid)";
/*  300 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "user(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),time int(10),user varchar(32),uuid varchar(64)" + index + ") ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  301 */           index = ", INDEX(uuid,user)";
/*  302 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "username_log(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),time int(10),uuid varchar(64),user varchar(32)" + index + ") ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  303 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "version(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),time int(10),version varchar(16)) ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  304 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "world(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),id int(8),world varchar(255)) ENGINE=InnoDB DEFAULT CHARACTER SET utf8");
/*  305 */           statement.close();
/*  306 */           connection.close();
/*  307 */           success = true;
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/*  311 */         e.printStackTrace();
/*      */       }
/*  313 */       if (!success) {
/*  314 */         Config.config.put("use-mysql", Integer.valueOf(0));
/*      */       }
/*      */     }
/*  317 */     if (((Integer)Config.config.get("use-mysql")).intValue() == 0) {
/*      */       try {
/*  319 */         Connection connection = Database.getConnection(true);
/*  320 */         Statement statement = connection.createStatement();
/*  321 */         List<String> tableData = new ArrayList();
/*  322 */         List<String> indexData = new ArrayList();
/*  323 */         String query = "SELECT type,name FROM sqlite_master WHERE type='table';";
/*  324 */         ResultSet rs = statement.executeQuery(query);
/*  325 */         while (rs.next()) {
/*  326 */           String type = rs.getString("type");
/*  327 */           if (type.equalsIgnoreCase("table")) {
/*  328 */             tableData.add(rs.getString("name"));
/*      */           }
/*  330 */           else if (type.equalsIgnoreCase("index")) {
/*  331 */             indexData.add(rs.getString("name"));
/*      */           }
/*      */         }
/*  334 */         rs.close();
/*      */         
/*  336 */         if (purge) {
/*  337 */           query = "ATTACH DATABASE '" + Config.sqlite + ".tmp' AS tmp_db";
/*  338 */           PreparedStatement preparedStmt = connection.prepareStatement(query);
/*  339 */           preparedStmt.execute();
/*  340 */           preparedStmt.close();
/*      */         }
/*      */         
/*  343 */         if (!tableData.contains(prefix + "art_map")) {
/*  344 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "art_map (id INTEGER, art TEXT);");
/*      */         }
/*  346 */         if (!tableData.contains(prefix + "block")) {
/*  347 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "block (time INTEGER, user INTEGER, wid INTEGER, x INTEGER, y INTEGER, z INTEGER, type INTEGER, data INTEGER, meta BLOB, action INTEGER, rolled_back INTEGER);");
/*      */         }
/*  349 */         if (!tableData.contains(prefix + "chat")) {
/*  350 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "chat (time INTEGER, user INTEGER, message TEXT);");
/*      */         }
/*  352 */         if (!tableData.contains(prefix + "command")) {
/*  353 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "command (time INTEGER, user INTEGER, message TEXT);");
/*      */         }
/*  355 */         if (!tableData.contains(prefix + "container")) {
/*  356 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "container (time INTEGER, user INTEGER, wid INTEGER, x INTEGER, y INTEGER, z INTEGER, type INTEGER, data INTEGER, amount INTEGER, metadata BLOB, action INTEGER, rolled_back INTEGER);");
/*      */         }
/*  358 */         if (!tableData.contains(prefix + "entity")) {
/*  359 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "entity (id INTEGER PRIMARY KEY ASC, time INTEGER, data BLOB);");
/*      */         }
/*  361 */         if (!tableData.contains(prefix + "entity_map")) {
/*  362 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "entity_map (id INTEGER, entity TEXT);");
/*      */         }
/*  364 */         if (!tableData.contains(prefix + "material_map")) {
/*  365 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "material_map (id INTEGER, material TEXT);");
/*      */         }
/*  367 */         if (!tableData.contains(prefix + "session")) {
/*  368 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "session (time INTEGER, user INTEGER, wid INTEGER, x INTEGER, y INTEGER, z INTEGER, action INTEGER);");
/*      */         }
/*  370 */         if (!tableData.contains(prefix + "sign")) {
/*  371 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "sign (time INTEGER, user INTEGER, wid INTEGER, x INTEGER, y INTEGER, z INTEGER, line_1 TEXT, line_2 TEXT, line_3 TEXT, line_4 TEXT);");
/*      */         }
/*  373 */         if (!tableData.contains(prefix + "skull")) {
/*  374 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "skull (id INTEGER PRIMARY KEY ASC, time INTEGER, type INTEGER, data INTEGER, rotation INTEGER, owner TEXT);");
/*      */         }
/*  376 */         if (!tableData.contains(prefix + "user")) {
/*  377 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "user (id INTEGER PRIMARY KEY ASC, time INTEGER, user TEXT, uuid TEXT);");
/*      */         }
/*  379 */         if (!tableData.contains(prefix + "username_log")) {
/*  380 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "username_log (id INTEGER PRIMARY KEY ASC, time INTEGER, uuid TEXT, user TEXT);");
/*      */         }
/*  382 */         if (!tableData.contains(prefix + "version")) {
/*  383 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "version (time INTEGER, version TEXT);");
/*      */         }
/*  385 */         if (!tableData.contains(prefix + "world")) {
/*  386 */           statement.executeUpdate("CREATE TABLE IF NOT EXISTS " + prefix + "world (id INTEGER, world TEXT);");
/*      */         }
/*  388 */         if (!purge) {
/*      */           try {
/*  390 */             if (!indexData.contains("block_index")) {
/*  391 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS block_index ON " + prefix + "block(wid,x,z,time);");
/*      */             }
/*  393 */             if (!indexData.contains("block_user_index")) {
/*  394 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS block_user_index ON " + prefix + "block(user,time);");
/*      */             }
/*  396 */             if (!indexData.contains("block_type_index")) {
/*  397 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS block_type_index ON " + prefix + "block(type,time);");
/*      */             }
/*  399 */             if (!indexData.contains("chat_index")) {
/*  400 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS chat_index ON " + prefix + "chat(time);");
/*      */             }
/*  402 */             if (!indexData.contains("chat_user_index")) {
/*  403 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS chat_user_index ON " + prefix + "chat(user,time);");
/*      */             }
/*  405 */             if (!indexData.contains("command_index")) {
/*  406 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS command_index ON " + prefix + "command(time);");
/*      */             }
/*  408 */             if (!indexData.contains("command_user_index")) {
/*  409 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS command_user_index ON " + prefix + "command(user,time);");
/*      */             }
/*  411 */             if (!indexData.contains("container_index")) {
/*  412 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS container_index ON " + prefix + "container(wid,x,z,time);");
/*      */             }
/*  414 */             if (!indexData.contains("container_user_index")) {
/*  415 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS container_user_index ON " + prefix + "container(user,time);");
/*      */             }
/*  417 */             if (!indexData.contains("container_type_index")) {
/*  418 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS container_type_index ON " + prefix + "container(type,time);");
/*      */             }
/*  420 */             if (!indexData.contains("session_index")) {
/*  421 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS session_index ON " + prefix + "session(wid,x,z,time);");
/*      */             }
/*  423 */             if (!indexData.contains("session_action_index")) {
/*  424 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS session_action_index ON " + prefix + "session(action,time);");
/*      */             }
/*  426 */             if (!indexData.contains("session_user_index")) {
/*  427 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS session_user_index ON " + prefix + "session(user,time);");
/*      */             }
/*  429 */             if (!indexData.contains("session_time_index")) {
/*  430 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS session_time_index ON " + prefix + "session(time);");
/*      */             }
/*  432 */             if (!indexData.contains("sign_index")) {
/*  433 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS sign_index ON " + prefix + "sign(wid,x,z,y,time);");
/*      */             }
/*  435 */             if (!indexData.contains("user_index")) {
/*  436 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS user_index ON " + prefix + "user(user);");
/*      */             }
/*  438 */             if (!indexData.contains("uuid_index")) {
/*  439 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS uuid_index ON " + prefix + "user(uuid);");
/*      */             }
/*  441 */             if (!indexData.contains("username_log_uuid_index")) {
/*  442 */               statement.executeUpdate("CREATE INDEX IF NOT EXISTS username_log_uuid_index ON " + prefix + "username_log(uuid,user);");
/*      */             }
/*      */           }
/*      */           catch (Exception e) {
/*  446 */             System.out.println("[CoreProtect] Unable to validate database structure.");
/*      */           }
/*      */         }
/*  449 */         statement.close();
/*  450 */         connection.close();
/*      */       }
/*      */       catch (Exception e) {
/*  453 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static ItemMeta deserializeItemMeta(Class<? extends ItemMeta> itemMetaClass, Map<String, Object> args) {
/*  459 */     DelegateDeserialization delegate = (DelegateDeserialization)itemMetaClass.getAnnotation(DelegateDeserialization.class);
/*  460 */     return (ItemMeta)org.bukkit.configuration.serialization.ConfigurationSerialization.deserializeObject(args, delegate.value());
/*      */   }
/*      */   
/*      */   public static <K, V extends Comparable<? super V>> SortedSet<Map.Entry<K, V>> entriesSortedByValues(Map<K, V> map) {
/*  464 */     SortedSet<Map.Entry<K, V>> sortedEntries = new java.util.TreeSet(new java.util.Comparator()
/*      */     {
/*      */       public int compare(Map.Entry<K, V> e1, Map.Entry<K, V> e2) {
/*  467 */         int res = ((Comparable)e1.getValue()).compareTo(e2.getValue());
/*  468 */         return res != 0 ? res : 1;
/*      */       }
/*  470 */     });
/*  471 */     sortedEntries.addAll(map.entrySet());
/*  472 */     return sortedEntries;
/*      */   }
/*      */   
/*      */   public static Block fallingSand(Block b, BlockState bs, String player) {
/*  476 */     Block bl = b;
/*  477 */     int timestamp = (int)(System.currentTimeMillis() / 1000L);
/*  478 */     Material type = b.getType();
/*  479 */     if (bs != null) {
/*  480 */       type = bs.getType();
/*      */     }
/*  482 */     int x = b.getX();
/*  483 */     int y = b.getY();
/*  484 */     int z = b.getZ();
/*  485 */     World world = b.getWorld();
/*  486 */     int wid = getWorldId(world.getName());
/*  487 */     int yc = y - 1;
/*  488 */     if (BlockInfo.falling_block_types.contains(type))
/*      */     {
/*  490 */       int bottomfound = 0;
/*  491 */       while (bottomfound == 0) {
/*  492 */         if (yc < 0) {
/*  493 */           bl = world.getBlockAt(x, yc + 1, z);
/*  494 */           bottomfound = 1;
/*      */         }
/*      */         else {
/*  497 */           Block block_down = world.getBlockAt(x, yc, z);
/*  498 */           Material down = block_down.getType();
/*  499 */           if ((!down.equals(Material.AIR)) && (!down.equals(Material.WATER)) && (!down.equals(Material.STATIONARY_WATER)) && (!down.equals(Material.LAVA)) && (!down.equals(Material.STATIONARY_LAVA)) && (!down.equals(Material.SNOW))) {
/*  500 */             bl = world.getBlockAt(x, yc + 1, z);
/*  501 */             bottomfound = 1;
/*      */           }
/*      */           else {
/*  504 */             String cords = "" + x + "." + yc + "." + z + "." + wid + "";
/*  505 */             Object[] data = (Object[])Config.lookup_cache.get(cords);
/*  506 */             if (data != null) {
/*  507 */               Material t = (Material)data[2];
/*  508 */               if (type.equals(t)) {
/*  509 */                 bl = world.getBlockAt(x, yc + 1, z);
/*  510 */                 bottomfound = 1;
/*      */               }
/*      */             }
/*      */           }
/*  514 */           yc--;
/*      */         }
/*      */       }
/*  517 */       Config.lookup_cache.put("" + x + "." + bl.getY() + "." + z + "." + wid + "", new Object[] { Integer.valueOf(timestamp), player, type });
/*      */     }
/*  519 */     return bl;
/*      */   }
/*      */   
/*      */   public static ItemStack[] get_container_state(ItemStack[] array) {
/*  523 */     ItemStack[] result = (ItemStack[])array.clone();
/*  524 */     int c = 0;
/*  525 */     for (ItemStack i : array) {
/*  526 */       ItemStack clone = null;
/*  527 */       if (i != null) {
/*  528 */         clone = i.clone();
/*      */       }
/*  530 */       result[c] = clone;
/*  531 */       c++;
/*      */     }
/*  533 */     return result;
/*      */   }
/*      */   
/*      */   public static int getArtId(Art art) {
/*  537 */     return art.getId();
/*      */   }
/*      */   
/*      */   public static int getArtId(String name, boolean internal) {
/*  541 */     int id = -1;
/*  542 */     name = name.toLowerCase().trim();
/*      */     
/*  544 */     if (Config.art.get(name) != null) {
/*  545 */       id = ((Integer)Config.art.get(name)).intValue();
/*      */     }
/*  547 */     else if (internal == true) {
/*  548 */       int artID = Config.art_id + 1;
/*  549 */       Config.art.put(name, Integer.valueOf(artID));
/*  550 */       Config.art_reversed.put(Integer.valueOf(artID), name);
/*  551 */       Config.art_id = artID;
/*  552 */       Queue.queueArtInsert(artID, name);
/*  553 */       id = ((Integer)Config.art.get(name)).intValue();
/*      */     }
/*      */     
/*  556 */     return id;
/*      */   }
/*      */   
/*      */   public static String getArtName(int id)
/*      */   {
/*  561 */     String artname = "";
/*  562 */     if (Config.art_reversed.get(Integer.valueOf(id)) != null) {
/*  563 */       artname = (String)Config.art_reversed.get(Integer.valueOf(id));
/*      */     }
/*  565 */     return artname;
/*      */   }
/*      */   
/*      */   public static int getBlockFace(BlockFace rotation) {
/*  569 */     switch (rotation) {
/*      */     case NORTH: 
/*  571 */       return 0;
/*      */     case NORTH_NORTH_EAST: 
/*  573 */       return 1;
/*      */     case NORTH_EAST: 
/*  575 */       return 2;
/*      */     case EAST_NORTH_EAST: 
/*  577 */       return 3;
/*      */     case EAST: 
/*  579 */       return 4;
/*      */     case EAST_SOUTH_EAST: 
/*  581 */       return 5;
/*      */     case SOUTH_EAST: 
/*  583 */       return 6;
/*      */     case SOUTH_SOUTH_EAST: 
/*  585 */       return 7;
/*      */     case SOUTH: 
/*  587 */       return 8;
/*      */     case SOUTH_SOUTH_WEST: 
/*  589 */       return 9;
/*      */     case SOUTH_WEST: 
/*  591 */       return 10;
/*      */     case WEST_SOUTH_WEST: 
/*  593 */       return 11;
/*      */     case WEST: 
/*  595 */       return 12;
/*      */     case WEST_NORTH_WEST: 
/*  597 */       return 13;
/*      */     case NORTH_WEST: 
/*  599 */       return 14;
/*      */     case NORTH_NORTH_WEST: 
/*  601 */       return 15;
/*      */     }
/*  603 */     throw new IllegalArgumentException("Invalid BlockFace rotation: " + rotation);
/*      */   }
/*      */   
/*      */   public static BlockFace getBlockFace(int rotation)
/*      */   {
/*  608 */     switch (rotation) {
/*      */     case 0: 
/*  610 */       return BlockFace.NORTH;
/*      */     case 1: 
/*  612 */       return BlockFace.NORTH_NORTH_EAST;
/*      */     case 2: 
/*  614 */       return BlockFace.NORTH_EAST;
/*      */     case 3: 
/*  616 */       return BlockFace.EAST_NORTH_EAST;
/*      */     case 4: 
/*  618 */       return BlockFace.EAST;
/*      */     case 5: 
/*  620 */       return BlockFace.EAST_SOUTH_EAST;
/*      */     case 6: 
/*  622 */       return BlockFace.SOUTH_EAST;
/*      */     case 7: 
/*  624 */       return BlockFace.SOUTH_SOUTH_EAST;
/*      */     case 8: 
/*  626 */       return BlockFace.SOUTH;
/*      */     case 9: 
/*  628 */       return BlockFace.SOUTH_SOUTH_WEST;
/*      */     case 10: 
/*  630 */       return BlockFace.SOUTH_WEST;
/*      */     case 11: 
/*  632 */       return BlockFace.WEST_SOUTH_WEST;
/*      */     case 12: 
/*  634 */       return BlockFace.WEST;
/*      */     case 13: 
/*  636 */       return BlockFace.WEST_NORTH_WEST;
/*      */     case 14: 
/*  638 */       return BlockFace.NORTH_WEST;
/*      */     case 15: 
/*  640 */       return BlockFace.NORTH_NORTH_WEST;
/*      */     }
/*  642 */     throw new AssertionError(rotation);
/*      */   }
/*      */   
/*      */   public static ItemStack[] getContainerContents(Material type, Object container, Location location)
/*      */   {
/*  647 */     ItemStack[] contents = null;
/*  648 */     if ((checkConfig(location.getWorld(), "item-transactions") == 1) && 
/*  649 */       (BlockInfo.containers.contains(type))) {
/*      */       try {
/*  651 */         if (type.equals(Material.ARMOR_STAND)) {
/*  652 */           LivingEntity entity = (LivingEntity)container;
/*  653 */           EntityEquipment equipment = getEntityEquipment(entity);
/*  654 */           if (equipment != null) {
/*  655 */             contents = equipment.getArmorContents();
/*      */           }
/*      */         }
/*      */         else {
/*  659 */           Block block = (Block)container;
/*  660 */           Inventory inventory = getContainerInventory(block.getState(), false);
/*  661 */           if (inventory != null) {
/*  662 */             contents = inventory.getContents();
/*      */           }
/*      */         }
/*      */         
/*  666 */         if (contents != null) {
/*  667 */           contents = get_container_state(contents);
/*      */         }
/*      */       }
/*      */       catch (Exception e) {
/*  671 */         e.printStackTrace();
/*      */       }
/*      */     }
/*      */     
/*  675 */     return contents;
/*      */   }
/*      */   
/*      */   public static Inventory getContainerInventory(BlockState block_state, boolean singleBlock) {
/*  679 */     Inventory inventory = null;
/*      */     try {
/*  681 */       if ((block_state instanceof InventoryHolder)) {
/*  682 */         if (singleBlock == true) {
/*  683 */           List<Material> chests = Arrays.asList(new Material[] { Material.CHEST, Material.TRAPPED_CHEST });
/*  684 */           Material block_type = block_state.getType();
/*  685 */           if (chests.contains(block_type)) {
/*  686 */             inventory = ((Chest)block_state).getBlockInventory();
/*      */           }
/*      */         }
/*  689 */         if (inventory == null) {
/*  690 */           inventory = ((InventoryHolder)block_state).getInventory();
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  695 */       e.printStackTrace();
/*      */     }
/*  697 */     return inventory;
/*      */   }
/*      */   
/*      */   public static byte getData(Block block) {
/*  701 */     return block.getData();
/*      */   }
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
/*      */   public static byte getData(BlockState block)
/*      */   {
/*  718 */     return block.getData().getData();
/*      */   }
/*      */   
/*      */   public static Enchantment getEnchantmentFromId(int id) {
/*  722 */     return Enchantment.getById(id);
/*      */   }
/*      */   
/*      */   public static EntityEquipment getEntityEquipment(LivingEntity entity) {
/*  726 */     EntityEquipment equipment = null;
/*      */     try {
/*  728 */       equipment = entity.getEquipment();
/*      */     }
/*      */     catch (Exception e) {
/*  731 */       e.printStackTrace();
/*      */     }
/*  733 */     return equipment;
/*      */   }
/*      */   
/*      */   public static int getEntityId(EntityType type) {
/*  737 */     return getEntityId(type.name(), true);
/*      */   }
/*      */   
/*      */   public static int getEntityId(String name, boolean internal) {
/*  741 */     int id = -1;
/*  742 */     name = name.toLowerCase().trim();
/*      */     
/*  744 */     if (Config.entities.get(name) != null) {
/*  745 */       id = ((Integer)Config.entities.get(name)).intValue();
/*      */     }
/*  747 */     else if (internal == true) {
/*  748 */       int entityID = Config.entity_id + 1;
/*  749 */       Config.entities.put(name, Integer.valueOf(entityID));
/*  750 */       Config.entities_reversed.put(Integer.valueOf(entityID), name);
/*  751 */       Config.entity_id = entityID;
/*  752 */       Queue.queueEntityInsert(entityID, name);
/*  753 */       id = ((Integer)Config.entities.get(name)).intValue();
/*      */     }
/*      */     
/*  756 */     return id;
/*      */   }
/*      */   
/*      */   public static Material getEntityMaterial(EntityType type) {
/*  760 */     switch (type) {
/*      */     case ARMOR_STAND: 
/*  762 */       return Material.ARMOR_STAND;
/*      */     case ENDER_CRYSTAL: 
/*  764 */       return Material.END_CRYSTAL;
/*      */     }
/*  766 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */   public static String getEntityName(int id)
/*      */   {
/*  772 */     String entityName = "";
/*  773 */     if (Config.entities_reversed.get(Integer.valueOf(id)) != null) {
/*  774 */       entityName = (String)Config.entities_reversed.get(Integer.valueOf(id));
/*      */     }
/*  776 */     return entityName;
/*      */   }
/*      */   
/*      */   public static EntityType getEntityType(int id)
/*      */   {
/*  781 */     EntityType entitytype = null;
/*  782 */     if (Config.entities_reversed.get(Integer.valueOf(id)) != null) {
/*  783 */       String name = (String)Config.entities_reversed.get(Integer.valueOf(id));
/*  784 */       if (name.contains("minecraft:")) {
/*  785 */         String[] block_name_split = name.split(":");
/*  786 */         name = block_name_split[1];
/*      */       }
/*  788 */       entitytype = EntityType.valueOf(name.toUpperCase());
/*      */     }
/*  790 */     return entitytype;
/*      */   }
/*      */   
/*      */   public static EntityType getEntityType(String name)
/*      */   {
/*  795 */     EntityType type = null;
/*  796 */     name = name.toLowerCase().trim();
/*  797 */     if (name.contains("minecraft:") == true) {
/*  798 */       name = name.split(":")[1];
/*      */     }
/*      */     
/*  801 */     if (Config.entities.get(name) != null) {
/*  802 */       type = EntityType.valueOf(name.toUpperCase());
/*      */     }
/*      */     
/*  805 */     return type;
/*      */   }
/*      */   
/*      */   public static int getHangingDelay(Map<String, Integer> hanging_delay, int row_wid, int row_x, int row_y, int row_z) {
/*  809 */     String token = row_wid + "." + row_x + "." + row_y + "." + row_z;
/*  810 */     int delay = 0;
/*  811 */     if (hanging_delay.get(token) != null) {
/*  812 */       delay = ((Integer)hanging_delay.get(token)).intValue() + 1;
/*      */     }
/*  814 */     hanging_delay.put(token, Integer.valueOf(delay));
/*  815 */     return delay;
/*      */   }
/*      */   
/*      */   public static Material getMaterialFromId(Integer id) {
/*  819 */     return Material.getMaterial(id.intValue());
/*      */   }
/*      */   
/*      */   public static int getMaterialId(Material material) {
/*  823 */     return block_id(material.name(), true);
/*      */   }
/*      */   
/*      */   public static byte getRawData(BlockState block) {
/*  827 */     return block.getRawData();
/*      */   }
/*      */   
/*      */   public static SkullType getSkullType(int type) {
/*  831 */     switch (type) {
/*      */     case 0: 
/*  833 */       return SkullType.SKELETON;
/*      */     case 1: 
/*  835 */       return SkullType.WITHER;
/*      */     case 2: 
/*  837 */       return SkullType.ZOMBIE;
/*      */     case 3: 
/*  839 */       return SkullType.PLAYER;
/*      */     case 4: 
/*  841 */       return SkullType.CREEPER;
/*      */     case 5: 
/*  843 */       return SkullType.DRAGON;
/*      */     }
/*  845 */     return SkullType.SKELETON;
/*      */   }
/*      */   
/*      */   public static int getSkullType(SkullType type)
/*      */   {
/*  850 */     switch (type) {
/*      */     case SKELETON: 
/*  852 */       return 0;
/*      */     case WITHER: 
/*  854 */       return 1;
/*      */     case ZOMBIE: 
/*  856 */       return 2;
/*      */     case PLAYER: 
/*  858 */       return 3;
/*      */     case CREEPER: 
/*  860 */       return 4;
/*      */     case DRAGON: 
/*  862 */       return 5;
/*      */     }
/*  864 */     return 0;
/*      */   }
/*      */   
/*      */   public static int getSpawnerType(EntityType type)
/*      */   {
/*  869 */     switch (type) {
/*      */     case ZOMBIE: 
/*  871 */       return 1;
/*      */     case SKELETON: 
/*  873 */       return 2;
/*      */     case SPIDER: 
/*  875 */       return 3;
/*      */     case CAVE_SPIDER: 
/*  877 */       return 4;
/*      */     case SILVERFISH: 
/*  879 */       return 5;
/*      */     case BLAZE: 
/*  881 */       return 6;
/*      */     }
/*  883 */     return 0;
/*      */   }
/*      */   
/*      */   public static EntityType getSpawnerType(int type)
/*      */   {
/*  888 */     switch (type) {
/*      */     case 1: 
/*  890 */       return EntityType.ZOMBIE;
/*      */     case 2: 
/*  892 */       return EntityType.SKELETON;
/*      */     case 3: 
/*  894 */       return EntityType.SPIDER;
/*      */     case 4: 
/*  896 */       return EntityType.CAVE_SPIDER;
/*      */     case 5: 
/*  898 */       return EntityType.SILVERFISH;
/*      */     case 6: 
/*  900 */       return EntityType.BLAZE;
/*      */     }
/*  902 */     return EntityType.PIG;
/*      */   }
/*      */   
/*      */ 
/*      */   public static Material getType(Block block)
/*      */   {
/*  908 */     return block.getType();
/*      */   }
/*      */   
/*      */   public static Material getType(int id)
/*      */   {
/*  913 */     Material material = null;
/*  914 */     if ((Config.materials_reversed.get(Integer.valueOf(id)) != null) && (id > 0)) {
/*  915 */       String name = (String)Config.materials_reversed.get(Integer.valueOf(id));
/*  916 */       if (name.contains("minecraft:")) {
/*  917 */         String[] block_name_split = name.split(":");
/*  918 */         name = block_name_split[1];
/*      */       }
/*  920 */       material = Material.getMaterial(name.toUpperCase());
/*      */     }
/*  922 */     return material;
/*      */   }
/*      */   
/*      */   public static Material getType(String name)
/*      */   {
/*  927 */     Material material = null;
/*  928 */     name = name.toLowerCase().trim();
/*  929 */     if (!name.contains(":")) {
/*  930 */       name = "minecraft:" + name;
/*      */     }
/*      */     
/*  933 */     if (BlockInfo.legacy_block_ids.get(name) != null) {
/*  934 */       int legacy_id = ((Integer)BlockInfo.legacy_block_ids.get(name)).intValue();
/*  935 */       material = Material.getMaterial(legacy_id);
/*      */     }
/*      */     else {
/*  938 */       if (name.contains("minecraft:") == true) {
/*  939 */         name = name.split(":")[1];
/*      */       }
/*      */       
/*  942 */       name = name.toUpperCase();
/*  943 */       material = Material.getMaterial(name);
/*      */       
/*  945 */       if (material == null) {
/*  946 */         List<String> stone_map = Arrays.asList(new String[] { "granite", "polished_granite", "diorite", "polished_diorite", "andesite", "polished_andesite" });
/*  947 */         if (stone_map.contains(name.toLowerCase())) {
/*  948 */           material = Material.getMaterial("STONE");
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*  953 */     return material;
/*      */   }
/*      */   
/*      */   public static int getWorldId(String name) {
/*  957 */     int id = -1;
/*      */     try {
/*  959 */       if (Config.worlds.get(name) == null) {
/*  960 */         int wid = Config.world_id + 1;
/*  961 */         Config.worlds.put(name, Integer.valueOf(wid));
/*  962 */         Config.worlds_reversed.put(Integer.valueOf(wid), name);
/*  963 */         Config.world_id = wid;
/*  964 */         Queue.queueWorldInsert(wid, name);
/*      */       }
/*  966 */       id = ((Integer)Config.worlds.get(name)).intValue();
/*      */     }
/*      */     catch (Exception e) {
/*  969 */       e.printStackTrace();
/*      */     }
/*  971 */     return id;
/*      */   }
/*      */   
/*      */   public static String getWorldName(int id) {
/*  975 */     String name = "";
/*      */     try {
/*  977 */       if (Config.worlds_reversed.get(Integer.valueOf(id)) != null) {
/*  978 */         name = (String)Config.worlds_reversed.get(Integer.valueOf(id));
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/*  982 */       e.printStackTrace();
/*      */     }
/*  984 */     return name;
/*      */   }
/*      */   
/*      */   public static void iceBreakCheck(BlockState block, String user, Material type) {
/*  988 */     if (type.equals(Material.ICE)) {
/*  989 */       int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/*  990 */       int wid = getWorldId(block.getWorld().getName());
/*  991 */       Config.lookup_cache.put("" + block.getX() + "." + block.getY() + "." + block.getZ() + "." + wid + "", new Object[] { Integer.valueOf(unixtimestamp), user, Material.WATER });
/*      */     }
/*      */   }
/*      */   
/*      */   public static boolean listContains(List<Material> list, Material value) {
/*  996 */     boolean result = false;
/*  997 */     for (Material list_value : list) {
/*  998 */       if (list_value.equals(value)) {
/*  999 */         result = true;
/* 1000 */         break;
/*      */       }
/*      */     }
/* 1003 */     return result;
/*      */   }
/*      */   
/*      */   public static void loadWorldEdit() {
/*      */     try {
/* 1008 */       boolean validVersion = true;
/* 1009 */       CoreProtect plugin = CoreProtect.getInstance();
/* 1010 */       String version = plugin.getServer().getPluginManager().getPlugin("WorldEdit").getDescription().getVersion();
/* 1011 */       if (version.contains(".")) {
/* 1012 */         String[] version_split = version.replaceAll("[^0-9.]", "").split("\\.");
/* 1013 */         double value = Double.parseDouble(version_split[0] + "." + version_split[1]);
/* 1014 */         if ((value > 0.0D) && (value < 6.0D)) {
/* 1015 */           validVersion = false;
/*      */         }
/*      */       }
/* 1018 */       else if (version.contains("-")) {
/* 1019 */         int value = Integer.parseInt(version.split("-")[0].replaceAll("[^0-9]", ""));
/* 1020 */         if ((value > 0) && (value < 3122)) {
/* 1021 */           validVersion = false;
/*      */         }
/*      */       }
/* 1024 */       if (validVersion == true) {
/* 1025 */         CoreProtectEditSessionEvent.register();
/*      */       }
/*      */       else {
/* 1028 */         System.out.println("[CoreProtect] Invalid WorldEdit version found.");
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1032 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */   
/*      */   public static int matchWorld(String name) {
/* 1037 */     int id = -1;
/*      */     try {
/* 1039 */       String result = "";
/* 1040 */       name = name.replaceFirst("#", "").toLowerCase().trim();
/* 1041 */       for (World world : CoreProtect.getInstance().getServer().getWorlds()) {
/* 1042 */         String world_name = world.getName();
/* 1043 */         if (world_name.toLowerCase().equals(name)) {
/* 1044 */           result = world.getName();
/* 1045 */           break;
/*      */         }
/* 1047 */         if (world_name.toLowerCase().endsWith(name)) {
/* 1048 */           result = world.getName();
/*      */         }
/* 1050 */         else if (world_name.toLowerCase().replaceAll("[^a-zA-Z0-9]", "").endsWith(name)) {
/* 1051 */           result = world.getName();
/*      */         }
/*      */       }
/* 1054 */       if (result.length() > 0) {
/* 1055 */         id = getWorldId(result);
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1059 */       e.printStackTrace();
/*      */     }
/* 1061 */     return id;
/*      */   }
/*      */   
/*      */   public static void messageOwner(String string) {
/* 1065 */     if (string.startsWith("-")) {
/* 1066 */       CoreProtect.getInstance().getServer().getConsoleSender().sendMessage(string);
/* 1067 */       for (Player player : CoreProtect.getInstance().getServer().getOnlinePlayers()) {
/* 1068 */         if (player.isOp()) {
/* 1069 */           player.sendMessage(string);
/*      */         }
/*      */       }
/*      */     }
/*      */     else {
/* 1074 */       CoreProtect.getInstance().getServer().getConsoleSender().sendMessage("[CoreProtect] " + string);
/* 1075 */       for (Player player : CoreProtect.getInstance().getServer().getOnlinePlayers()) {
/* 1076 */         if (player.isOp()) {
/* 1077 */           player.sendMessage("§3CoreProtect §f- " + string);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public static void messageOwnerAndUser(CommandSender user, String string) {
/* 1084 */     CoreProtect.getInstance().getServer().getConsoleSender().sendMessage("[CoreProtect] " + string);
/* 1085 */     for (Player player : CoreProtect.getInstance().getServer().getOnlinePlayers()) {
/* 1086 */       if ((player.isOp() == true) && (!player.getName().equals(user.getName()))) {
/* 1087 */         player.sendMessage("§3CoreProtect §f- " + string);
/*      */       }
/*      */     }
/* 1090 */     if (((user instanceof Player)) && 
/* 1091 */       (((Player)user).isOnline() == true)) {
/* 1092 */       user.sendMessage("§3CoreProtect §f- " + string);
/*      */     }
/*      */   }
/*      */   
/*      */   public static String nameFilter(String name, int data)
/*      */   {
/* 1098 */     if (name.equals("stone")) {
/* 1099 */       switch (data) {
/*      */       case 1: 
/* 1101 */         name = "granite";
/* 1102 */         break;
/*      */       case 2: 
/* 1104 */         name = "polished_granite";
/* 1105 */         break;
/*      */       case 3: 
/* 1107 */         name = "diorite";
/* 1108 */         break;
/*      */       case 4: 
/* 1110 */         name = "polished_diorite";
/* 1111 */         break;
/*      */       case 5: 
/* 1113 */         name = "andesite";
/* 1114 */         break;
/*      */       case 6: 
/* 1116 */         name = "polished_andesite";
/*      */       }
/*      */       
/*      */     }
/* 1120 */     return name;
/*      */   }
/*      */   
/*      */   public static ItemStack newItemStack1(int type, int amount) {
/* 1124 */     return new ItemStack(type, amount);
/*      */   }
/*      */   
/*      */   public static ItemStack newItemStack1(int type, int amount, short data) {
/* 1128 */     return new ItemStack(type, amount, data);
/*      */   }
/*      */   
/*      */   public static boolean newVersion(Integer[] old_version, Integer[] current_version) {
/* 1132 */     boolean result = false;
/* 1133 */     if (old_version[0].intValue() < current_version[0].intValue())
/*      */     {
/* 1135 */       result = true;
/*      */     }
/* 1137 */     else if ((old_version[0] == current_version[0]) && (old_version[1].intValue() < current_version[1].intValue()))
/*      */     {
/* 1139 */       result = true;
/*      */     }
/* 1141 */     else if ((old_version.length >= 3) && (old_version[0] == current_version[0]) && (old_version[1] == current_version[1]) && (old_version[2].intValue() < current_version[2].intValue()))
/*      */     {
/* 1143 */       result = true;
/*      */     }
/* 1145 */     return result;
/*      */   }
/*      */   
/*      */   public static boolean newVersion(Integer[] oldVersion, String currentVersion) {
/* 1149 */     String[] currentVersionSplit = currentVersion.split("\\.");
/* 1150 */     return newVersion(oldVersion, convertArray(currentVersionSplit));
/*      */   }
/*      */   
/*      */   public static boolean newVersion(String oldVersion, Integer[] currentVersion) {
/* 1154 */     String[] oldVersionSplit = oldVersion.split("\\.");
/* 1155 */     return newVersion(convertArray(oldVersionSplit), currentVersion);
/*      */   }
/*      */   
/*      */   public static boolean newVersion(String oldVersion, String currentVersion) {
/* 1159 */     String[] oldVersionSplit = oldVersion.split("\\.");
/* 1160 */     String[] currentVersionSplit = currentVersion.split("\\.");
/* 1161 */     return newVersion(convertArray(oldVersionSplit), convertArray(currentVersionSplit));
/*      */   }
/*      */   
/*      */   public static String[] parseCSVString(String string) {
/* 1165 */     String[] result = null;
/*      */     
/* 1167 */     if (string.indexOf("\"") > -1) {
/* 1168 */       result = csvSplitter.split(string, -1);
/*      */     }
/*      */     else {
/* 1171 */       result = string.split(",", -1);
/*      */     }
/*      */     
/* 1174 */     for (int i = 0; i < result.length; i++) {
/* 1175 */       String value = result[i];
/* 1176 */       if (value.length() == 0) {
/* 1177 */         value = null;
/*      */       }
/* 1179 */       else if (string.indexOf("\"") > -1) {
/* 1180 */         value = value.replaceAll("^\"|\"$", "");
/* 1181 */         value = value.replaceAll("\"\"", "\"");
/*      */       }
/* 1183 */       result[i] = value;
/*      */     }
/*      */     
/* 1186 */     return result;
/*      */   }
/*      */   
/*      */   public static List<Object> processMeta(BlockState block) {
/* 1190 */     List<Object> meta = new ArrayList();
/*      */     try {
/* 1192 */       if ((block instanceof CommandBlock)) {
/* 1193 */         CommandBlock command_block = (CommandBlock)block;
/* 1194 */         String command = command_block.getCommand();
/* 1195 */         if (command.length() > 0) {
/* 1196 */           meta.add(command);
/*      */         }
/*      */       }
/* 1199 */       else if ((block instanceof Banner)) {
/* 1200 */         Banner banner = (Banner)block;
/* 1201 */         meta.add(banner.getBaseColor());
/* 1202 */         List<org.bukkit.block.banner.Pattern> patterns = banner.getPatterns();
/* 1203 */         for (org.bukkit.block.banner.Pattern pattern : patterns) {
/* 1204 */           meta.add(pattern.serialize());
/*      */         }
/*      */       }
/* 1207 */       else if ((block instanceof ShulkerBox)) {
/* 1208 */         ShulkerBox shulkerBox = (ShulkerBox)block;
/* 1209 */         ItemStack[] inventory = shulkerBox.getInventory().getStorageContents();
/* 1210 */         int slot = 0;
/* 1211 */         for (ItemStack itemStack : inventory) {
/* 1212 */           if ((itemStack != null) && (!itemStack.getType().equals(Material.AIR))) {
/* 1213 */             Map<Integer, Object> itemMap = new java.util.HashMap();
/* 1214 */             ItemStack item = itemStack.clone();
/* 1215 */             List<List<Map<String, Object>>> metadata = net.coreprotect.database.Logger.getItemMeta(item, item.getType(), slot);
/* 1216 */             item.setItemMeta(null);
/* 1217 */             itemMap.put(Integer.valueOf(0), item.serialize());
/* 1218 */             itemMap.put(Integer.valueOf(1), metadata);
/* 1219 */             meta.add(itemMap);
/*      */           }
/* 1221 */           slot++;
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (Exception e) {
/* 1226 */       e.printStackTrace();
/*      */     }
/* 1228 */     if (meta.size() == 0) {
/* 1229 */       meta = null;
/*      */     }
/* 1231 */     return meta;
/*      */   }
/*      */   
/*      */   public static void removeHanging(BlockState block, int delay) {
/* 1235 */     CoreProtect.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CoreProtect.getInstance(), new Runnable()
/*      */     {
/*      */       public void run() {
/*      */         try {
/* 1239 */           for (Entity e : this.val$block.getChunk().getEntities()) {
/* 1240 */             if (((e instanceof ItemFrame)) || ((e instanceof Painting))) {
/* 1241 */               Location el = e.getLocation();
/* 1242 */               if ((el.getBlockX() == this.val$block.getX()) && (el.getBlockY() == this.val$block.getY()) && (el.getBlockZ() == this.val$block.getZ())) {
/* 1243 */                 e.remove();
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception e) {
/* 1249 */           e.printStackTrace(); } } }, delay);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static void sendBlockChange(Player player, Location location, Material type, byte data)
/*      */   {
/* 1256 */     player.sendBlockChange(location, type, data);
/*      */   }
/*      */   
/*      */   public static void setData(Block block, byte data) {
/* 1260 */     block.setData(data);
/*      */   }
/*      */   
/*      */   public static BlockState setRawData(BlockState block, byte data) {
/* 1264 */     block.setRawData(data);
/* 1265 */     return block;
/*      */   }
/*      */   
/*      */   public static void setTypeAndData(Block block, Material type, byte data, boolean update) {
/* 1269 */     block.setType(type, update);
/* 1270 */     block.setData(data);
/*      */   }
/*      */   
/*      */   public static void setTypeId1(Block block, int type) {
/* 1274 */     block.setTypeId(type);
/*      */   }
/*      */   
/*      */   public static void setTypeId1(Block block, int type, boolean update) {
/* 1278 */     block.setTypeId(type, update);
/*      */   }
/*      */   
/*      */   public static void setTypeIdAndData1(Block block, int type, byte data, boolean update) {
/* 1282 */     block.setTypeIdAndData(type, data, update);
/*      */   }
/*      */   
/*      */   public static void spawnEntity(BlockState block, final EntityType type, final List<Object> list) {
/* 1286 */     CoreProtect.getInstance().getServer().getScheduler().runTask(CoreProtect.getInstance(), new Runnable()
/*      */     {
/*      */       public void run() {
/*      */         try {
/* 1290 */           Location location = this.val$block.getLocation();
/* 1291 */           location.setX(location.getX() + 0.5D);
/* 1292 */           location.setZ(location.getZ() + 0.5D);
/* 1293 */           entity = this.val$block.getLocation().getWorld().spawnEntity(location, type);
/*      */           
/* 1295 */           if (list.size() == 0) {
/* 1296 */             return;
/*      */           }
/*      */           
/* 1299 */           List<Object> age = (List)list.get(0);
/*      */           
/* 1301 */           List<Object> tame = (List)list.get(1);
/*      */           
/* 1303 */           List<Object> data = (List)list.get(2);
/*      */           
/* 1305 */           if (list.size() >= 5) {
/* 1306 */             entity.setCustomNameVisible(((Boolean)list.get(3)).booleanValue());
/* 1307 */             entity.setCustomName((String)list.get(4));
/*      */           }
/*      */           
/* 1310 */           int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 1311 */           int wid = Functions.getWorldId(this.val$block.getWorld().getName());
/* 1312 */           String token = "" + this.val$block.getX() + "." + this.val$block.getY() + "." + this.val$block.getZ() + "." + wid + "." + type.name() + "";
/* 1313 */           Config.entity_cache.put(token, new Object[] { Integer.valueOf(unixtimestamp), Integer.valueOf(entity.getEntityId()) });
/*      */           int count;
/* 1315 */           Ageable ageable; if ((entity instanceof Ageable)) {
/* 1316 */             count = 0;
/* 1317 */             ageable = (Ageable)entity;
/* 1318 */             for (Object value : age) {
/* 1319 */               if (count == 0) {
/* 1320 */                 int set = ((Integer)value).intValue();
/* 1321 */                 ageable.setAge(set);
/*      */               }
/* 1323 */               else if (count == 1) {
/* 1324 */                 boolean set = ((Boolean)value).booleanValue();
/* 1325 */                 ageable.setAgeLock(set);
/*      */               }
/* 1327 */               else if (count == 2) {
/* 1328 */                 boolean set = ((Boolean)value).booleanValue();
/* 1329 */                 if (set == true) {
/* 1330 */                   ageable.setAdult();
/*      */                 }
/*      */                 else {
/* 1333 */                   ageable.setBaby();
/*      */                 }
/*      */               }
/* 1336 */               else if (count == 3) {
/* 1337 */                 boolean set = ((Boolean)value).booleanValue();
/* 1338 */                 ageable.setBreed(set);
/*      */               }
/* 1340 */               else if ((count == 4) && (value != null))
/*      */               {
/* 1342 */                 double set = ((Double)value).doubleValue();
/* 1343 */                 ageable.setMaxHealth(set);
/*      */               }
/* 1345 */               count++; } }
/*      */           int count;
/*      */           Tameable tameable;
/* 1348 */           if ((entity instanceof Tameable)) {
/* 1349 */             count = 0;
/* 1350 */             tameable = (Tameable)entity;
/* 1351 */             for (Object value : tame) {
/* 1352 */               if (count == 0) {
/* 1353 */                 boolean set = ((Boolean)value).booleanValue();
/* 1354 */                 tameable.setTamed(set);
/*      */               }
/* 1356 */               else if (count == 1) {
/* 1357 */                 String set = (String)value;
/* 1358 */                 if (set.length() > 0) {
/* 1359 */                   Player owner = CoreProtect.getInstance().getServer().getPlayer(set);
/* 1360 */                   if (owner == null) {
/* 1361 */                     org.bukkit.OfflinePlayer offline_player = CoreProtect.getInstance().getServer().getOfflinePlayer(set);
/* 1362 */                     if (offline_player != null) {
/* 1363 */                       tameable.setOwner(offline_player);
/*      */                     }
/*      */                   }
/*      */                   else {
/* 1367 */                     tameable.setOwner(owner);
/*      */                   }
/*      */                 }
/*      */               }
/* 1371 */               count++;
/*      */             } }
/*      */           Attributable attributable;
/* 1374 */           if (((entity instanceof Attributable)) && (list.size() >= 6)) {
/* 1375 */             attributable = (Attributable)entity;
/*      */             
/* 1377 */             List<Object> attributes = (List)list.get(5);
/* 1378 */             for (Object value : attributes)
/*      */             {
/* 1380 */               List<Object> attributeData = (List)value;
/* 1381 */               Attribute attribute = (Attribute)attributeData.get(0);
/* 1382 */               Double baseValue = (Double)attributeData.get(1);
/*      */               
/* 1384 */               List<Object> attributeModifiers = (List)attributeData.get(2);
/*      */               
/* 1386 */               entityAttribute = attributable.getAttribute(attribute);
/* 1387 */               if (entityAttribute != null) {
/* 1388 */                 entityAttribute.setBaseValue(baseValue.doubleValue());
/* 1389 */                 for (AttributeModifier modifier : entityAttribute.getModifiers()) {
/* 1390 */                   entityAttribute.removeModifier(modifier);
/*      */                 }
/* 1392 */                 for (Object modifier : attributeModifiers)
/*      */                 {
/* 1394 */                   Map<String, Object> serializedModifier = (Map)modifier;
/* 1395 */                   entityAttribute.addModifier(AttributeModifier.deserialize(serializedModifier));
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */           AttributeInstance entityAttribute;
/* 1401 */           count = 0;
/* 1402 */           for (Object value : data) {
/* 1403 */             if ((entity instanceof Creeper)) {
/* 1404 */               Creeper creeper = (Creeper)entity;
/* 1405 */               if (count == 0) {
/* 1406 */                 boolean set = ((Boolean)value).booleanValue();
/* 1407 */                 creeper.setPowered(set);
/*      */               }
/*      */             }
/* 1410 */             else if ((entity instanceof Enderman)) {
/* 1411 */               Enderman enderman = (Enderman)entity;
/* 1412 */               if (count == 0)
/*      */               {
/* 1414 */                 Map<String, Object> set = (Map)value;
/* 1415 */                 MaterialData materialdata = ItemStack.deserialize(set).getData();
/* 1416 */                 enderman.setCarriedMaterial(materialdata);
/*      */               }
/*      */             }
/* 1419 */             else if ((entity instanceof IronGolem)) {
/* 1420 */               IronGolem irongolem = (IronGolem)entity;
/* 1421 */               if (count == 0) {
/* 1422 */                 boolean set = ((Boolean)value).booleanValue();
/* 1423 */                 irongolem.setPlayerCreated(set);
/*      */               }
/*      */             }
/* 1426 */             else if ((entity instanceof Ocelot)) {
/* 1427 */               Ocelot ocelot = (Ocelot)entity;
/* 1428 */               if (count == 0) {
/* 1429 */                 Ocelot.Type set = (Ocelot.Type)value;
/* 1430 */                 ocelot.setCatType(set);
/*      */               }
/* 1432 */               else if (count == 1) {
/* 1433 */                 boolean set = ((Boolean)value).booleanValue();
/* 1434 */                 ocelot.setSitting(set);
/*      */               }
/*      */             }
/* 1437 */             else if ((entity instanceof Pig)) {
/* 1438 */               Pig pig = (Pig)entity;
/* 1439 */               if (count == 0) {
/* 1440 */                 boolean set = ((Boolean)value).booleanValue();
/* 1441 */                 pig.setSaddle(set);
/*      */               }
/*      */             }
/* 1444 */             else if ((entity instanceof Sheep)) {
/* 1445 */               Sheep sheep = (Sheep)entity;
/* 1446 */               if (count == 0) {
/* 1447 */                 boolean set = ((Boolean)value).booleanValue();
/* 1448 */                 sheep.setSheared(set);
/*      */               }
/* 1450 */               else if (count == 1) {
/* 1451 */                 DyeColor set = (DyeColor)value;
/* 1452 */                 sheep.setColor(set);
/*      */               }
/*      */             }
/* 1455 */             else if ((entity instanceof Slime)) {
/* 1456 */               Slime slime = (Slime)entity;
/* 1457 */               if (count == 0) {
/* 1458 */                 int set = ((Integer)value).intValue();
/* 1459 */                 slime.setSize(set);
/*      */               }
/*      */             }
/* 1462 */             else if ((entity instanceof Villager)) {
/* 1463 */               Villager villager = (Villager)entity;
/* 1464 */               if (count == 0) {
/* 1465 */                 Villager.Profession set = (Villager.Profession)value;
/* 1466 */                 villager.setProfession(set);
/*      */               }
/* 1468 */               else if (count == 1) {
/* 1469 */                 int set = ((Integer)value).intValue();
/* 1470 */                 villager.setRiches(set);
/*      */               }
/* 1472 */               else if (count == 2) {
/* 1473 */                 List<MerchantRecipe> merchantRecipes = new ArrayList();
/*      */                 
/* 1475 */                 List<Object> set = (List)value;
/* 1476 */                 for (Object recipes : set)
/*      */                 {
/* 1478 */                   List<Object> recipe = (List)recipes;
/*      */                   
/* 1480 */                   List<Object> itemMap = (List)recipe.get(0);
/*      */                   
/* 1482 */                   ItemStack result = ItemStack.deserialize((Map)itemMap.get(0));
/*      */                   
/* 1484 */                   List<List<Map<String, Object>>> metadata = (List)itemMap.get(1);
/* 1485 */                   Object[] populatedStack = Lookup.populateItemStack(result, metadata);
/* 1486 */                   result = (ItemStack)populatedStack[1];
/* 1487 */                   int uses = ((Integer)recipe.get(1)).intValue();
/* 1488 */                   int maxUses = ((Integer)recipe.get(2)).intValue();
/* 1489 */                   boolean experienceReward = ((Boolean)recipe.get(3)).booleanValue();
/* 1490 */                   List<ItemStack> merchantIngredients = new ArrayList();
/*      */                   
/* 1492 */                   List<Object> ingredients = (List)recipe.get(4);
/* 1493 */                   for (Object ingredient : ingredients)
/*      */                   {
/* 1495 */                     List<Object> ingredientMap = (List)ingredient;
/*      */                     
/* 1497 */                     ItemStack item = ItemStack.deserialize((Map)ingredientMap.get(0));
/*      */                     
/* 1499 */                     List<List<Map<String, Object>>> itemMetaData = (List)ingredientMap.get(1);
/* 1500 */                     populatedStack = Lookup.populateItemStack(item, itemMetaData);
/* 1501 */                     item = (ItemStack)populatedStack[1];
/* 1502 */                     merchantIngredients.add(item);
/*      */                   }
/* 1504 */                   MerchantRecipe merchantRecipe = new MerchantRecipe(result, uses, maxUses, experienceReward);
/* 1505 */                   merchantRecipe.setIngredients(merchantIngredients);
/* 1506 */                   merchantRecipes.add(merchantRecipe);
/*      */                 }
/* 1508 */                 if (merchantRecipes.size() > 0) {
/* 1509 */                   villager.setRecipes(merchantRecipes);
/*      */                 }
/*      */               }
/*      */             }
/* 1513 */             else if ((entity instanceof Wolf)) {
/* 1514 */               Wolf wolf = (Wolf)entity;
/* 1515 */               if (count == 0) {
/* 1516 */                 boolean set = ((Boolean)value).booleanValue();
/* 1517 */                 wolf.setSitting(set);
/*      */               }
/* 1519 */               else if (count == 1) {
/* 1520 */                 DyeColor set = (DyeColor)value;
/* 1521 */                 wolf.setCollarColor(set);
/*      */               }
/*      */             }
/* 1524 */             else if ((entity instanceof ZombieVillager)) {
/* 1525 */               ZombieVillager zombieVillager = (ZombieVillager)entity;
/* 1526 */               if (count == 0) {
/* 1527 */                 boolean set = ((Boolean)value).booleanValue();
/* 1528 */                 zombieVillager.setBaby(set);
/*      */               }
/* 1530 */               else if (count == 1) {
/* 1531 */                 Villager.Profession set = (Villager.Profession)value;
/* 1532 */                 zombieVillager.setVillagerProfession(set);
/*      */               }
/*      */             }
/* 1535 */             else if ((entity instanceof Zombie)) {
/* 1536 */               Zombie zombie = (Zombie)entity;
/* 1537 */               if (count == 0) {
/* 1538 */                 boolean set = ((Boolean)value).booleanValue();
/* 1539 */                 zombie.setBaby(set);
/*      */               }
/*      */             }
/* 1542 */             else if ((entity instanceof AbstractHorse)) {
/* 1543 */               AbstractHorse abstractHorse = (AbstractHorse)entity;
/* 1544 */               if ((count == 0) && (value != null))
/*      */               {
/* 1546 */                 boolean set = ((Boolean)value).booleanValue();
/* 1547 */                 if ((entity instanceof ChestedHorse)) {
/* 1548 */                   ChestedHorse chestedHorse = (ChestedHorse)entity;
/* 1549 */                   chestedHorse.setCarryingChest(set);
/*      */                 }
/*      */               }
/* 1552 */               else if ((count == 1) && (value != null))
/*      */               {
/* 1554 */                 Horse.Color set = (Horse.Color)value;
/* 1555 */                 if ((entity instanceof Horse)) {
/* 1556 */                   Horse horse = (Horse)entity;
/* 1557 */                   horse.setColor(set);
/*      */                 }
/*      */               }
/* 1560 */               else if (count == 2) {
/* 1561 */                 int set = ((Integer)value).intValue();
/* 1562 */                 abstractHorse.setDomestication(set);
/*      */               }
/* 1564 */               else if (count == 3) {
/* 1565 */                 double set = ((Double)value).doubleValue();
/* 1566 */                 abstractHorse.setJumpStrength(set);
/*      */               }
/* 1568 */               else if (count == 4) {
/* 1569 */                 int set = ((Integer)value).intValue();
/* 1570 */                 abstractHorse.setMaxDomestication(set);
/*      */               }
/* 1572 */               else if ((count == 5) && (value != null))
/*      */               {
/* 1574 */                 Horse.Style set = (Horse.Style)value;
/* 1575 */                 Horse horse = (Horse)entity;
/* 1576 */                 horse.setStyle(set);
/*      */               }
/* 1578 */               if ((entity instanceof Horse)) {
/* 1579 */                 Horse horse = (Horse)entity;
/* 1580 */                 if (count == 7) {
/* 1581 */                   if (value != null)
/*      */                   {
/* 1583 */                     ItemStack set = ItemStack.deserialize((Map)value);
/* 1584 */                     horse.getInventory().setArmor(set);
/*      */                   }
/*      */                 }
/* 1587 */                 else if (count == 8) {
/* 1588 */                   if (value != null)
/*      */                   {
/* 1590 */                     ItemStack set = ItemStack.deserialize((Map)value);
/* 1591 */                     horse.getInventory().setSaddle(set);
/*      */                   }
/*      */                 }
/* 1594 */                 else if (count == 9) {
/* 1595 */                   Horse.Color set = (Horse.Color)value;
/* 1596 */                   horse.setColor(set);
/*      */                 }
/* 1598 */                 else if (count == 10) {
/* 1599 */                   Horse.Style set = (Horse.Style)value;
/* 1600 */                   horse.setStyle(set);
/*      */                 }
/*      */               }
/* 1603 */               else if ((entity instanceof ChestedHorse)) {
/* 1604 */                 if (count == 7) {
/* 1605 */                   ChestedHorse chestedHorse = (ChestedHorse)entity;
/* 1606 */                   boolean set = ((Boolean)value).booleanValue();
/* 1607 */                   chestedHorse.setCarryingChest(set);
/*      */                 }
/* 1609 */                 if ((entity instanceof Llama)) {
/* 1610 */                   Llama llama = (Llama)entity;
/* 1611 */                   if (count == 8) {
/* 1612 */                     if (value != null)
/*      */                     {
/* 1614 */                       ItemStack set = ItemStack.deserialize((Map)value);
/* 1615 */                       llama.getInventory().setDecor(set);
/*      */                     }
/*      */                   }
/* 1618 */                   else if (count == 9) {
/* 1619 */                     Llama.Color set = (Llama.Color)value;
/* 1620 */                     llama.setColor(set);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/* 1625 */             count++;
/*      */           }
/*      */         } catch (Exception e) { Entity entity;
/*      */           int count;
/* 1629 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   public static void spawnHanging(BlockState blockstate, final Material row_type, final int row_data, int delay) {
/* 1636 */     CoreProtect.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CoreProtect.getInstance(), new Runnable()
/*      */     {
/*      */       public void run() {
/*      */         try {
/* 1640 */           Block block = this.val$blockstate.getBlock();
/* 1641 */           int row_x = block.getX();
/* 1642 */           int row_y = block.getY();
/* 1643 */           int row_z = block.getZ();
/*      */           
/* 1645 */           for (Entity e : block.getChunk().getEntities()) {
/* 1646 */             if (((row_type.equals(Material.ITEM_FRAME)) && ((e instanceof ItemFrame))) || ((row_type.equals(Material.PAINTING)) && ((e instanceof Painting)))) {
/* 1647 */               Location el = e.getLocation();
/* 1648 */               if ((el.getBlockX() == row_x) && (el.getBlockY() == row_y) && (el.getBlockZ() == row_z)) {
/* 1649 */                 e.remove();
/* 1650 */                 break;
/*      */               }
/*      */             }
/*      */           }
/*      */           
/* 1655 */           int dx1 = row_x + 1;
/* 1656 */           int dx2 = row_x - 1;
/* 1657 */           int dz1 = row_z + 1;
/* 1658 */           int dz2 = row_z - 1;
/* 1659 */           Block c1 = block.getWorld().getBlockAt(dx1, row_y, row_z);
/* 1660 */           Block c2 = block.getWorld().getBlockAt(dx2, row_y, row_z);
/* 1661 */           Block c3 = block.getWorld().getBlockAt(row_x, row_y, dz1);
/* 1662 */           Block c4 = block.getWorld().getBlockAt(row_x, row_y, dz2);
/*      */           
/* 1664 */           BlockFace face_set = null;
/* 1665 */           if (!BlockInfo.non_attachable.contains(c1.getType())) {
/* 1666 */             face_set = BlockFace.WEST;
/* 1667 */             block = c1;
/*      */           }
/* 1669 */           else if (!BlockInfo.non_attachable.contains(c2.getType())) {
/* 1670 */             face_set = BlockFace.EAST;
/* 1671 */             block = c2;
/*      */           }
/* 1673 */           else if (!BlockInfo.non_attachable.contains(c3.getType())) {
/* 1674 */             face_set = BlockFace.NORTH;
/* 1675 */             block = c3;
/*      */           }
/* 1677 */           else if (!BlockInfo.non_attachable.contains(c4.getType())) {
/* 1678 */             face_set = BlockFace.SOUTH;
/* 1679 */             block = c4;
/*      */           }
/*      */           
/* 1682 */           BlockFace face = null;
/* 1683 */           if (BlockInfo.non_solid_entity_blocks.contains(Functions.getType(block.getRelative(BlockFace.EAST)))) {
/* 1684 */             face = BlockFace.EAST;
/*      */           }
/* 1686 */           else if (BlockInfo.non_solid_entity_blocks.contains(Functions.getType(block.getRelative(BlockFace.NORTH)))) {
/* 1687 */             face = BlockFace.NORTH;
/*      */           }
/* 1689 */           else if (BlockInfo.non_solid_entity_blocks.contains(Functions.getType(block.getRelative(BlockFace.WEST)))) {
/* 1690 */             face = BlockFace.WEST;
/*      */           }
/* 1692 */           else if (BlockInfo.non_solid_entity_blocks.contains(Functions.getType(block.getRelative(BlockFace.SOUTH)))) {
/* 1693 */             face = BlockFace.SOUTH;
/*      */           }
/*      */           
/* 1696 */           if ((face_set != null) && (face != null)) {
/* 1697 */             if (row_type.equals(Material.PAINTING)) {
/* 1698 */               String art_name = Functions.getArtName(row_data);
/* 1699 */               Art painting = Art.getByName(art_name.toUpperCase());
/* 1700 */               int height = painting.getBlockHeight();
/* 1701 */               int width = painting.getBlockWidth();
/* 1702 */               int painting_x = row_x;
/* 1703 */               int painting_y = row_y;
/* 1704 */               int painting_z = row_z;
/* 1705 */               if ((height != 1) || (width != 1)) {
/* 1706 */                 if ((height > 1) && 
/* 1707 */                   (height != 3)) {
/* 1708 */                   painting_y -= 1;
/*      */                 }
/*      */                 
/* 1711 */                 if (width > 1) {
/* 1712 */                   if (face_set.equals(BlockFace.WEST)) {
/* 1713 */                     painting_z--;
/*      */                   }
/* 1715 */                   else if (face_set.equals(BlockFace.SOUTH)) {
/* 1716 */                     painting_x--;
/*      */                   }
/*      */                 }
/*      */               }
/* 1720 */               Block spawn_block = block.getRelative(face);
/* 1721 */               Material current_type = spawn_block.getType();
/* 1722 */               int current_data = Functions.getData(spawn_block);
/* 1723 */               Functions.setTypeAndData(spawn_block, Material.AIR, (byte)0, true);
/* 1724 */               Painting hanging = null;
/*      */               try {
/* 1726 */                 hanging = (Painting)block.getWorld().spawn(spawn_block.getLocation(), Painting.class);
/*      */               }
/*      */               catch (Exception e) {}
/*      */               
/* 1730 */               if (hanging != null) {
/* 1731 */                 Functions.setTypeAndData(spawn_block, current_type, (byte)current_data, true);
/* 1732 */                 hanging.teleport(block.getWorld().getBlockAt(painting_x, painting_y, painting_z).getLocation());
/* 1733 */                 hanging.setFacingDirection(face_set, true);
/* 1734 */                 hanging.setArt(painting, true);
/*      */               }
/*      */             }
/* 1737 */             else if (row_type.equals(Material.ITEM_FRAME)) {
/*      */               try {
/* 1739 */                 Block spawn_block = block.getRelative(face);
/* 1740 */                 Material current_type = spawn_block.getType();
/* 1741 */                 int current_data = Functions.getData(spawn_block);
/* 1742 */                 Functions.setTypeAndData(spawn_block, Material.AIR, (byte)0, true);
/* 1743 */                 ItemFrame hanging = null;
/* 1744 */                 hanging = (ItemFrame)block.getWorld().spawn(spawn_block.getLocation(), ItemFrame.class);
/* 1745 */                 if (hanging != null) {
/* 1746 */                   Functions.setTypeAndData(spawn_block, current_type, (byte)current_data, true);
/* 1747 */                   hanging.teleport(block.getWorld().getBlockAt(row_x, row_y, row_z).getLocation());
/* 1748 */                   hanging.setFacingDirection(face_set, true);
/* 1749 */                   Material row_data_material = Functions.getType(row_data);
/* 1750 */                   if (row_data_material != null) {
/* 1751 */                     ItemStack istack = new ItemStack(row_data_material, 1);
/* 1752 */                     hanging.setItem(istack);
/*      */                   }
/*      */                 }
/*      */               }
/*      */               catch (Exception e) {}
/*      */             }
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/* 1762 */           e.printStackTrace(); } } }, delay);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public static boolean successfulQuery(Connection connection, String query)
/*      */   {
/* 1769 */     boolean result = false;
/*      */     try {
/* 1771 */       PreparedStatement preparedStmt = connection.prepareStatement(query);
/* 1772 */       ResultSet resultSet = preparedStmt.executeQuery();
/* 1773 */       if (resultSet.isBeforeFirst()) {
/* 1774 */         result = true;
/*      */       }
/* 1776 */       resultSet.close();
/* 1777 */       preparedStmt.close();
/*      */     }
/*      */     catch (Exception e) {
/* 1780 */       e.printStackTrace();
/*      */     }
/* 1782 */     return result;
/*      */   }
/*      */   
/*      */   public static String[] toStringArray(String input) {
/* 1786 */     int commaCount = input.replaceAll("[^,]", "").length();
/* 1787 */     if (commaCount == 8) {
/* 1788 */       String[] data = input.split(",");
/* 1789 */       String action_time = data[0];
/* 1790 */       String action_player = data[1];
/* 1791 */       String action_cords = data[2];
/* 1792 */       String[] datacords = action_cords.split("\\.");
/* 1793 */       String action_x = datacords[0];
/* 1794 */       String action_y = datacords[1];
/* 1795 */       String action_z = datacords[2];
/* 1796 */       String action_type = data[3];
/* 1797 */       String action_data = data[4];
/* 1798 */       String action_action = data[5];
/* 1799 */       String action_rb = data[6];
/* 1800 */       String action_wid = data[7].trim();
/* 1801 */       return new String[] { action_time, action_player, action_x, action_y, action_z, action_type, action_data, action_action, action_rb, action_wid };
/*      */     }
/* 1803 */     return null;
/*      */   }
/*      */   
/*      */   public static void updateBlock(BlockState block) {
/* 1807 */     CoreProtect.getInstance().getServer().getScheduler().runTask(CoreProtect.getInstance(), new Runnable()
/*      */     {
/*      */       public void run() {
/*      */         try {
/* 1811 */           this.val$block.update();
/*      */         }
/*      */         catch (Exception e) {
/* 1814 */           e.printStackTrace();
/*      */         }
/*      */       }
/*      */     });
/*      */   }
/*      */   
/*      */   public static void updateInventory(Player player) {
/* 1821 */     player.updateInventory();
/*      */   }
/*      */   
/*      */   public static boolean checkWorldEdit() {
/* 1825 */     boolean result = false;
/* 1826 */     CoreProtect pl = CoreProtect.getInstance();
/* 1827 */     for (World world : pl.getServer().getWorlds()) {
/* 1828 */       if (checkConfig(world, "worldedit") == 1) {
/* 1829 */         result = true;
/* 1830 */         break;
/*      */       }
/*      */     }
/* 1833 */     if (result) {
/* 1834 */       Plugin plugin = pl.getServer().getPluginManager().getPlugin("WorldEdit");
/* 1835 */       if ((plugin == null) || (CoreProtectEditSessionEvent.isInitialized() == true)) {
/* 1836 */         result = false;
/*      */       }
/*      */     }
/*      */     
/* 1840 */     return result;
/*      */   }
/*      */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\Functions.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */