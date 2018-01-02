/*     */ package net.coreprotect.listener;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import net.coreprotect.CoreProtect;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.consumer.Queue;
/*     */ import net.coreprotect.database.Database;
/*     */ import net.coreprotect.database.Lookup;
/*     */ import net.coreprotect.model.BlockInfo;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.Chest;
/*     */ import org.bukkit.block.DoubleChest;
/*     */ import org.bukkit.entity.ArmorStand;
/*     */ import org.bukkit.entity.EnderCrystal;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.HumanEntity;
/*     */ import org.bukkit.entity.ItemFrame;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.block.Action;
/*     */ import org.bukkit.event.block.SignChangeEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*     */ import org.bukkit.event.player.PlayerArmorStandManipulateEvent;
/*     */ import org.bukkit.event.player.PlayerBucketEmptyEvent;
/*     */ import org.bukkit.event.player.PlayerBucketFillEvent;
/*     */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEntityEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ import org.bukkit.inventory.EntityEquipment;
/*     */ import org.bukkit.inventory.EquipmentSlot;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.InventoryHolder;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.PlayerInventory;
/*     */ 
/*     */ public class PlayerListener extends Queue implements org.bukkit.event.Listener
/*     */ {
/*  55 */   public static ConcurrentHashMap<String, Object[]> lastInspectorEvent = new ConcurrentHashMap();
/*     */   
/*     */   private void onInventoryInteract(HumanEntity entity, Inventory inventory) {
/*  58 */     World world = entity.getWorld();
/*  59 */     if (Functions.checkConfig(world, "item-transactions") == 1) {
/*  60 */       Player player = (Player)entity;
/*  61 */       if (inventory != null) {
/*  62 */         InventoryHolder i = inventory.getHolder();
/*  63 */         if (i != null) {
/*  64 */           Material type = Material.CHEST;
/*  65 */           Location l = null;
/*  66 */           if ((i instanceof BlockState)) {
/*  67 */             BlockState state = (BlockState)i;
/*  68 */             type = state.getType();
/*  69 */             if (BlockInfo.containers.contains(type)) {
/*  70 */               l = state.getLocation();
/*     */             }
/*     */           }
/*  73 */           else if ((i instanceof DoubleChest)) {
/*  74 */             DoubleChest state = (DoubleChest)i;
/*  75 */             l = state.getLocation();
/*     */           }
/*  77 */           if (l != null) {
/*  78 */             int x = l.getBlockX();
/*  79 */             int y = l.getBlockY();
/*  80 */             int z = l.getBlockZ();
/*  81 */             for (HumanEntity viewer : inventory.getViewers()) {
/*  82 */               if (!viewer.getName().equals(player.getName())) {
/*  83 */                 String logging_chest_id = viewer.getName().toLowerCase() + "." + x + "." + y + "." + z;
/*  84 */                 if (Config.old_container.get(logging_chest_id) != null) {
/*  85 */                   int size_old = ((List)Config.old_container.get(logging_chest_id)).size();
/*  86 */                   if (Config.force_containers.get(logging_chest_id) == null) {
/*  87 */                     Config.force_containers.put(logging_chest_id, new ArrayList());
/*     */                   }
/*  89 */                   List<ItemStack[]> list = (List)Config.force_containers.get(logging_chest_id);
/*  90 */                   if (list.size() < size_old) {
/*  91 */                     list.add(Functions.get_container_state(inventory.getContents()));
/*  92 */                     Config.force_containers.put(logging_chest_id, list);
/*     */                   }
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/*  98 */             int chest_id = 0;
/*  99 */             String logging_chest_id = player.getName().toLowerCase() + "." + x + "." + y + "." + z;
/* 100 */             if (Config.logging_chest.get(logging_chest_id) != null) {
/* 101 */               if (Config.force_containers.get(logging_chest_id) != null) {
/* 102 */                 int force_size = ((List)Config.force_containers.get(logging_chest_id)).size();
/* 103 */                 List<ItemStack[]> list = (List)Config.old_container.get(logging_chest_id);
/* 104 */                 if (list.size() > force_size) {
/* 105 */                   list.set(force_size, Functions.get_container_state(inventory.getContents()));
/*     */                 }
/*     */                 else {
/* 108 */                   list.add(Functions.get_container_state(inventory.getContents()));
/*     */                 }
/* 110 */                 Config.old_container.put(logging_chest_id, list);
/*     */               }
/* 112 */               chest_id = ((Integer)Config.logging_chest.get(logging_chest_id)).intValue() + 1;
/*     */             }
/*     */             else {
/* 115 */               List<ItemStack[]> list = new ArrayList();
/* 116 */               list.add(Functions.get_container_state(inventory.getContents()));
/* 117 */               Config.old_container.put(logging_chest_id, list);
/*     */             }
/* 119 */             Config.logging_chest.put(logging_chest_id, Integer.valueOf(chest_id));
/* 120 */             Queue.queueContainerTransaction(player.getName(), l.getBlock().getState(), type, inventory, chest_id);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onInventoryClick(InventoryClickEvent event) {
/* 129 */     onInventoryInteract(event.getWhoClicked(), event.getInventory());
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onInventoryDragEvent(InventoryDragEvent event) {
/* 134 */     onInventoryInteract(event.getWhoClicked(), event.getInventory());
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onPlayerArmorStandManipulateEvent(PlayerArmorStandManipulateEvent event) {
/* 139 */     Player player = event.getPlayer();
/* 140 */     final ArmorStand armorStand = event.getRightClicked();
/* 141 */     EntityEquipment equipment = armorStand.getEquipment();
/* 142 */     ItemStack[] contents = equipment.getArmorContents();
/* 143 */     ItemStack item = event.getArmorStandItem();
/* 144 */     ItemStack playerItem = event.getPlayerItem();
/*     */     
/* 146 */     if (Functions.checkConfig(player.getWorld(), "item-transactions") != 1) {
/* 147 */       return;
/*     */     }
/*     */     
/* 150 */     if ((Config.inspecting.get(player.getName()) != null) && 
/* 151 */       (((Boolean)Config.inspecting.get(player.getName())).booleanValue())) {
/* 152 */       final Player player2 = player;
/* 153 */       if (BlockInfo.containers.contains(Material.ARMOR_STAND))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 192 */         Runnable runnable = new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             try
/*     */             {
/* 159 */               if (Config.converter_running == true) {
/* 160 */                 player2.sendMessage("§3CoreProtect §f- Upgrade in progress. Please try again later.");
/* 161 */                 return;
/*     */               }
/* 163 */               if (Config.purge_running == true) {
/* 164 */                 player2.sendMessage("§3CoreProtect §f- Purge in progress. Please try again later.");
/* 165 */                 return;
/*     */               }
/* 167 */               Connection connection = Database.getConnection(false);
/* 168 */               if (connection != null) {
/* 169 */                 Statement statement = connection.createStatement();
/* 170 */                 Location l = armorStand.getLocation();
/* 171 */                 String blockdata = Lookup.chest_transactions(statement, l, player2.getName(), 1, 7);
/* 172 */                 if (blockdata.contains("\n")) {
/* 173 */                   for (String b : blockdata.split("\n")) {
/* 174 */                     player2.sendMessage(b);
/*     */                   }
/*     */                   
/*     */                 } else {
/* 178 */                   player2.sendMessage(blockdata);
/*     */                 }
/* 180 */                 statement.close();
/* 181 */                 connection.close();
/*     */               }
/*     */               else {
/* 184 */                 player2.sendMessage("§3CoreProtect §f- Database busy. Please try again later.");
/*     */               }
/*     */             }
/*     */             catch (Exception e) {
/* 188 */               e.printStackTrace();
/*     */             }
/*     */             
/*     */           }
/* 192 */         };
/* 193 */         Thread thread = new Thread(runnable);
/* 194 */         thread.start();
/*     */       }
/* 196 */       event.setCancelled(true);
/*     */     }
/*     */     
/*     */ 
/* 200 */     if (event.isCancelled()) {
/* 201 */       return;
/*     */     }
/* 203 */     if ((item == null) && (playerItem == null)) {
/* 204 */       return;
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
/* 219 */     Material type = Material.ARMOR_STAND;
/* 220 */     Location l = armorStand.getLocation();
/* 221 */     int x = l.getBlockX();
/* 222 */     int y = l.getBlockY();
/* 223 */     int z = l.getBlockZ();
/*     */     
/* 225 */     int chest_id = 0;
/* 226 */     String logging_chest_id = player.getName().toLowerCase() + "." + x + "." + y + "." + z;
/* 227 */     if (Config.logging_chest.get(logging_chest_id) != null) {
/* 228 */       if (Config.force_containers.get(logging_chest_id) != null) {
/* 229 */         int force_size = ((List)Config.force_containers.get(logging_chest_id)).size();
/* 230 */         List<ItemStack[]> list = (List)Config.old_container.get(logging_chest_id);
/* 231 */         if (list.size() > force_size) {
/* 232 */           list.set(force_size, Functions.get_container_state(contents));
/*     */         }
/*     */         else {
/* 235 */           list.add(Functions.get_container_state(contents));
/*     */         }
/* 237 */         Config.old_container.put(logging_chest_id, list);
/*     */       }
/* 239 */       chest_id = ((Integer)Config.logging_chest.get(logging_chest_id)).intValue() + 1;
/*     */     }
/*     */     else {
/* 242 */       List<ItemStack[]> list = new ArrayList();
/* 243 */       list.add(Functions.get_container_state(contents));
/* 244 */       Config.old_container.put(logging_chest_id, list);
/*     */     }
/* 246 */     Config.logging_chest.put(logging_chest_id, Integer.valueOf(chest_id));
/* 247 */     Queue.queueContainerTransaction(player.getName(), l.getBlock().getState(), type, equipment, chest_id);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   protected void onPlayerBucketEmpty(PlayerBucketEmptyEvent event) {
/* 252 */     String player = event.getPlayer().getName();
/* 253 */     Block block = event.getBlockClicked().getRelative(event.getBlockFace());
/* 254 */     World world = block.getWorld();
/* 255 */     int inspect = 0;
/*     */     
/* 257 */     if ((Config.inspecting.get(player) != null) && 
/* 258 */       (((Boolean)Config.inspecting.get(player)).booleanValue())) {
/* 259 */       inspect = 1;
/* 260 */       event.setCancelled(true);
/*     */     }
/*     */     
/* 263 */     if ((!event.isCancelled()) && (Functions.checkConfig(world, "buckets") == 1) && (inspect == 0)) {
/* 264 */       int wid = Functions.getWorldId(block.getWorld().getName());
/* 265 */       int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 266 */       Material type = Material.WATER;
/* 267 */       if (event.getBucket().equals(Material.LAVA_BUCKET)) {
/* 268 */         type = Material.LAVA;
/*     */       }
/*     */       
/* 271 */       Config.lookup_cache.put("" + block.getX() + "." + block.getY() + "." + block.getZ() + "." + wid + "", new Object[] { Integer.valueOf(unixtimestamp), player, type });
/* 272 */       Queue.queueBlockPlace(player, block.getState(), type, 0);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   protected void onPlayerBucketFill(PlayerBucketFillEvent event) {
/* 278 */     String player = event.getPlayer().getName();
/* 279 */     Block block = event.getBlockClicked().getRelative(event.getBlockFace());
/* 280 */     World world = block.getWorld();
/* 281 */     Material type = block.getType();
/* 282 */     int data = Functions.getData(block);
/*     */     
/* 284 */     int inspect = 0;
/* 285 */     if ((Config.inspecting.get(player) != null) && 
/* 286 */       (((Boolean)Config.inspecting.get(player)).booleanValue())) {
/* 287 */       inspect = 1;
/* 288 */       event.setCancelled(true);
/*     */     }
/*     */     
/* 291 */     if ((!event.isCancelled()) && (Functions.checkConfig(world, "buckets") == 1) && (inspect == 0)) {
/* 292 */       Queue.queueBlockBreak(player, block.getState(), type, data);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onPlayerChat(AsyncPlayerChatEvent event) {
/* 298 */     Player player = event.getPlayer();
/* 299 */     if ((!event.getMessage().startsWith("/")) && (Functions.checkConfig(player.getWorld(), "player-messages") == 1)) {
/* 300 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 301 */       Queue.queuePlayerChat(player, event.getMessage(), time);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
/* 307 */     Player player = event.getPlayer();
/* 308 */     if (Functions.checkConfig(player.getWorld(), "player-commands") == 1) {
/* 309 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 310 */       Queue.queuePlayerCommand(player, event.getMessage(), time);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.LOWEST)
/*     */   protected void onPlayerInteract(PlayerInteractEvent event) {
/* 316 */     Player player = event.getPlayer();
/* 317 */     World world = player.getWorld();
/* 318 */     boolean inspecting_or_something = false;
/* 319 */     List<Material> interact_blocks = Arrays.asList(new Material[] { Material.SPRUCE_DOOR, Material.BIRCH_DOOR, Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.SPRUCE_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.ACACIA_FENCE_GATE, Material.DISPENSER, Material.NOTE_BLOCK, Material.CHEST, Material.FURNACE, Material.BURNING_FURNACE, Material.WOODEN_DOOR, Material.LEVER, Material.STONE_BUTTON, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.TRAP_DOOR, Material.FENCE_GATE, Material.BREWING_STAND, Material.WOOD_BUTTON, Material.ANVIL, Material.TRAPPED_CHEST, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON, Material.HOPPER, Material.DROPPER, Material.BLACK_SHULKER_BOX, Material.BLUE_SHULKER_BOX, Material.BROWN_SHULKER_BOX, Material.CYAN_SHULKER_BOX, Material.GRAY_SHULKER_BOX, Material.GREEN_SHULKER_BOX, Material.LIGHT_BLUE_SHULKER_BOX, Material.LIME_SHULKER_BOX, Material.MAGENTA_SHULKER_BOX, Material.ORANGE_SHULKER_BOX, Material.PINK_SHULKER_BOX, Material.PURPLE_SHULKER_BOX, Material.RED_SHULKER_BOX, Material.SILVER_SHULKER_BOX, Material.WHITE_SHULKER_BOX, Material.YELLOW_SHULKER_BOX });
/* 320 */     if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
/* 321 */       if ((Config.inspecting.get(player.getName()) != null) && 
/* 322 */         (((Boolean)Config.inspecting.get(player.getName())).booleanValue()))
/*     */       {
/* 324 */         Block check_block = event.getClickedBlock();
/*     */         
/*     */ 
/* 327 */         if ((check_block.getType().equals(Material.DOUBLE_PLANT)) && 
/* 328 */           (Functions.getData(check_block) >= 8)) {
/* 329 */           check_block = world.getBlockAt(check_block.getX(), check_block.getY() - 1, check_block.getZ());
/*     */         }
/*     */         
/*     */ 
/* 333 */         final Block block_final = check_block;
/* 334 */         final Player player_final = player;
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 371 */         Runnable runnable = new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             try
/*     */             {
/* 339 */               if (Config.converter_running == true) {
/* 340 */                 player_final.sendMessage("§3CoreProtect §f- Upgrade in progress. Please try again later.");
/* 341 */                 return;
/*     */               }
/* 343 */               if (Config.purge_running == true) {
/* 344 */                 player_final.sendMessage("§3CoreProtect §f- Purge in progress. Please try again later.");
/* 345 */                 return;
/*     */               }
/* 347 */               Connection connection = Database.getConnection(false);
/* 348 */               if (connection != null) {
/* 349 */                 Statement statement = connection.createStatement();
/* 350 */                 String result_data = Lookup.block_lookup(statement, block_final, player_final.getName(), 0, 1, 7);
/* 351 */                 if (result_data.contains("\n")) {
/* 352 */                   for (String b : result_data.split("\n")) {
/* 353 */                     player_final.sendMessage(b);
/*     */                   }
/*     */                   
/* 356 */                 } else if (result_data.length() > 0) {
/* 357 */                   player_final.sendMessage(result_data);
/*     */                 }
/* 359 */                 statement.close();
/* 360 */                 connection.close();
/*     */               }
/*     */               else {
/* 363 */                 player_final.sendMessage("§3CoreProtect §f- Database busy. Please try again later.");
/*     */               }
/*     */             }
/*     */             catch (Exception e) {
/* 367 */               e.printStackTrace();
/*     */             }
/*     */             
/*     */           }
/* 371 */         };
/* 372 */         Thread thread = new Thread(runnable);
/* 373 */         thread.start();
/* 374 */         event.setCancelled(true);
/*     */       }
/*     */       
/*     */     }
/* 378 */     else if ((event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && 
/* 379 */       (Config.inspecting.get(player.getName()) != null) && 
/* 380 */       (((Boolean)Config.inspecting.get(player.getName())).booleanValue())) {
/* 381 */       List<Material> safe_blocks = Arrays.asList(new Material[] { Material.WOODEN_DOOR, Material.LEVER, Material.STONE_BUTTON, Material.TRAP_DOOR, Material.FENCE_GATE, Material.SPRUCE_DOOR, Material.BIRCH_DOOR, Material.JUNGLE_DOOR, Material.ACACIA_DOOR, Material.DARK_OAK_DOOR, Material.SPRUCE_FENCE_GATE, Material.BIRCH_FENCE_GATE, Material.JUNGLE_FENCE_GATE, Material.DARK_OAK_FENCE_GATE, Material.ACACIA_FENCE_GATE });
/* 382 */       Block block = event.getClickedBlock();
/* 383 */       if (block != null) {
/* 384 */         final Material type = block.getType();
/* 385 */         if (interact_blocks.contains(type)) {
/* 386 */           final Block cblock = event.getClickedBlock();
/* 387 */           final Player player2 = player;
/* 388 */           if ((BlockInfo.containers.contains(type)) && (Functions.checkConfig(world, "item-transactions") == 1))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 441 */             Runnable runnable = new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/*     */                 try
/*     */                 {
/* 394 */                   if (Config.converter_running == true) {
/* 395 */                     player2.sendMessage("§3CoreProtect §f- Upgrade in progress. Please try again later.");
/* 396 */                     return;
/*     */                   }
/* 398 */                   if (Config.purge_running == true) {
/* 399 */                     player2.sendMessage("§3CoreProtect §f- Purge in progress. Please try again later.");
/* 400 */                     return;
/*     */                   }
/* 402 */                   Connection connection = Database.getConnection(false);
/* 403 */                   if (connection != null) {
/* 404 */                     Statement statement = connection.createStatement();
/* 405 */                     Location l = null;
/* 406 */                     if ((type.equals(Material.CHEST)) || (type.equals(Material.TRAPPED_CHEST))) {
/* 407 */                       Chest chest = (Chest)cblock.getState();
/* 408 */                       InventoryHolder i = chest.getInventory().getHolder();
/* 409 */                       if ((i instanceof DoubleChest)) {
/* 410 */                         DoubleChest c = (DoubleChest)i;
/* 411 */                         l = c.getLocation();
/*     */                       }
/*     */                       else {
/* 414 */                         l = chest.getLocation();
/*     */                       }
/*     */                     }
/* 417 */                     if (l == null) {
/* 418 */                       l = cblock.getLocation();
/*     */                     }
/* 420 */                     String blockdata = Lookup.chest_transactions(statement, l, player2.getName(), 1, 7);
/* 421 */                     if (blockdata.contains("\n")) {
/* 422 */                       for (String b : blockdata.split("\n")) {
/* 423 */                         player2.sendMessage(b);
/*     */                       }
/*     */                       
/*     */                     } else {
/* 427 */                       player2.sendMessage(blockdata);
/*     */                     }
/* 429 */                     statement.close();
/* 430 */                     connection.close();
/*     */                   }
/*     */                   else {
/* 433 */                     player2.sendMessage("§3CoreProtect §f- Database busy. Please try again later.");
/*     */                   }
/*     */                 }
/*     */                 catch (Exception e) {
/* 437 */                   e.printStackTrace();
/*     */                 }
/*     */                 
/*     */               }
/* 441 */             };
/* 442 */             Thread thread = new Thread(runnable);
/* 443 */             thread.start();
/* 444 */             event.setCancelled(true);
/*     */           }
/*     */           else
/*     */           {
/* 448 */             Block interact_block = cblock;
/* 449 */             if ((type.equals(Material.WOODEN_DOOR)) || (type.equals(Material.SPRUCE_DOOR)) || (type.equals(Material.BIRCH_DOOR)) || (type.equals(Material.JUNGLE_DOOR)) || (type.equals(Material.ACACIA_DOOR)) || (type.equals(Material.DARK_OAK_DOOR))) {
/* 450 */               int y = interact_block.getY() - 1;
/* 451 */               Block block_under = interact_block.getWorld().getBlockAt(interact_block.getX(), y, interact_block.getZ());
/* 452 */               if (block_under.getType().equals(type)) {
/* 453 */                 interact_block = block_under;
/*     */               }
/*     */             }
/* 456 */             final Block final_interact_block = interact_block;
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 493 */             Runnable runnable = new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/*     */                 try
/*     */                 {
/* 461 */                   if (Config.converter_running == true) {
/* 462 */                     player2.sendMessage("§3CoreProtect §f- Upgrade in progress. Please try again later.");
/* 463 */                     return;
/*     */                   }
/* 465 */                   if (Config.purge_running == true) {
/* 466 */                     player2.sendMessage("§3CoreProtect §f- Purge in progress. Please try again later.");
/* 467 */                     return;
/*     */                   }
/* 469 */                   Connection connection = Database.getConnection(false);
/* 470 */                   if (connection != null) {
/* 471 */                     Statement statement = connection.createStatement();
/* 472 */                     String blockdata = Lookup.interaction_lookup(statement, final_interact_block, player2.getName(), 0, 1, 7);
/* 473 */                     if (blockdata.contains("\n")) {
/* 474 */                       for (String b : blockdata.split("\n")) {
/* 475 */                         player2.sendMessage(b);
/*     */                       }
/*     */                       
/*     */                     } else {
/* 479 */                       player2.sendMessage(blockdata);
/*     */                     }
/* 481 */                     statement.close();
/* 482 */                     connection.close();
/*     */                   }
/*     */                   else {
/* 485 */                     player2.sendMessage("§3CoreProtect §f- Database busy. Please try again later.");
/*     */                   }
/*     */                 }
/*     */                 catch (Exception e) {
/* 489 */                   e.printStackTrace();
/*     */                 }
/*     */                 
/*     */               }
/* 493 */             };
/* 494 */             Thread thread = new Thread(runnable);
/* 495 */             thread.start();
/* 496 */             if (!safe_blocks.contains(type)) {
/* 497 */               event.setCancelled(true);
/*     */             }
/*     */           }
/* 500 */           inspecting_or_something = true;
/*     */         }
/*     */         else {
/* 503 */           boolean performLookup = true;
/* 504 */           EquipmentSlot eventHand = event.getHand();
/* 505 */           String uuid = event.getPlayer().getUniqueId().toString();
/* 506 */           long systemTime = System.currentTimeMillis();
/*     */           
/* 508 */           if (lastInspectorEvent.get(uuid) != null) {
/* 509 */             Object[] lastEvent = (Object[])lastInspectorEvent.get(uuid);
/* 510 */             long lastTime = ((Long)lastEvent[0]).longValue();
/* 511 */             EquipmentSlot lastHand = (EquipmentSlot)lastEvent[1];
/*     */             
/* 513 */             long timeSince = systemTime - lastTime;
/* 514 */             if ((timeSince < 50L) && (!eventHand.equals(lastHand))) {
/* 515 */               performLookup = false;
/*     */             }
/*     */           }
/*     */           
/* 519 */           if (performLookup) {
/* 520 */             final Player player2 = player;
/* 521 */             final Block fblock = event.getClickedBlock().getRelative(event.getBlockFace());
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 571 */             Runnable runnable = new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/*     */                 try
/*     */                 {
/* 526 */                   if (Config.converter_running == true) {
/* 527 */                     player2.sendMessage("§3CoreProtect §f- Upgrade in progress. Please try again later.");
/* 528 */                     return;
/*     */                   }
/* 530 */                   if (Config.purge_running == true) {
/* 531 */                     player2.sendMessage("§3CoreProtect §f- Purge in progress. Please try again later.");
/* 532 */                     return;
/*     */                   }
/* 534 */                   Connection connection = Database.getConnection(false);
/* 535 */                   if (connection != null) {
/* 536 */                     Statement statement = connection.createStatement();
/* 537 */                     if (fblock.getType().equals(Material.AIR)) {
/* 538 */                       String blockdata = Lookup.block_lookup(statement, fblock, player2.getName(), 0, 1, 7);
/* 539 */                       if (blockdata.contains("\n")) {
/* 540 */                         for (String b : blockdata.split("\n")) {
/* 541 */                           player2.sendMessage(b);
/*     */                         }
/*     */                         
/* 544 */                       } else if (blockdata.length() > 0) {
/* 545 */                         player2.sendMessage(blockdata);
/*     */                       }
/*     */                     }
/*     */                     else {
/* 549 */                       String blockdata = Lookup.block_lookup(statement, fblock, player2.getName(), 0, 1, 7);
/* 550 */                       if (blockdata.contains("\n")) {
/* 551 */                         for (String b : blockdata.split("\n")) {
/* 552 */                           player2.sendMessage(b);
/*     */                         }
/*     */                         
/* 555 */                       } else if (blockdata.length() > 0) {
/* 556 */                         player2.sendMessage(blockdata);
/*     */                       }
/*     */                     }
/* 559 */                     statement.close();
/* 560 */                     connection.close();
/*     */                   }
/*     */                   else {
/* 563 */                     player2.sendMessage("§3CoreProtect §f- Database busy. Please try again later.");
/*     */                   }
/*     */                 }
/*     */                 catch (Exception e) {
/* 567 */                   e.printStackTrace();
/*     */                 }
/*     */                 
/*     */               }
/* 571 */             };
/* 572 */             Thread thread = new Thread(runnable);
/* 573 */             thread.start();
/*     */             
/* 575 */             Functions.updateInventory(event.getPlayer());
/* 576 */             lastInspectorEvent.put(uuid, new Object[] { Long.valueOf(systemTime), eventHand });
/*     */           }
/* 578 */           event.setCancelled(true);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 585 */     Block block = event.getClickedBlock();
/* 586 */     if (block != null) {
/* 587 */       Material type = block.getType();
/* 588 */       if (interact_blocks.contains(type)) {
/* 589 */         boolean valid_click = true;
/* 590 */         if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/* 591 */           valid_click = false;
/*     */         }
/* 593 */         if ((!inspecting_or_something) && (valid_click == true) && (event.getHand().equals(EquipmentSlot.HAND)) && 
/* 594 */           (!event.isCancelled()) && (Functions.checkConfig(world, "player-interactions") == 1)) {
/* 595 */           Block interact_block = event.getClickedBlock();
/* 596 */           if ((type.equals(Material.WOODEN_DOOR)) || (type.equals(Material.SPRUCE_DOOR)) || (type.equals(Material.BIRCH_DOOR)) || (type.equals(Material.JUNGLE_DOOR)) || (type.equals(Material.ACACIA_DOOR)) || (type.equals(Material.DARK_OAK_DOOR))) {
/* 597 */             int y = interact_block.getY() - 1;
/* 598 */             Block block_under = interact_block.getWorld().getBlockAt(interact_block.getX(), y, interact_block.getZ());
/* 599 */             if (block_under.getType().equals(type)) {
/* 600 */               interact_block = block_under;
/*     */             }
/*     */           }
/* 603 */           Queue.queuePlayerInteraction(player.getName(), interact_block.getState());
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onPlayerInteract_Monitor(PlayerInteractEvent event)
/*     */   {
/* 613 */     if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
/* 614 */       World world = event.getClickedBlock().getWorld();
/* 615 */       if ((!event.isCancelled()) && (Functions.checkConfig(world, "block-break") == 1)) {
/* 616 */         Block relative_block = event.getClickedBlock().getRelative(event.getBlockFace());
/* 617 */         if (relative_block.getType().equals(Material.FIRE)) {
/* 618 */           Player player = event.getPlayer();
/* 619 */           Material type = relative_block.getType();
/* 620 */           int data = Functions.getData(relative_block);
/* 621 */           Queue.queueBlockBreak(player.getName(), relative_block.getState(), type, data);
/*     */         }
/*     */       }
/*     */     }
/* 625 */     else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
/* 626 */       World world = event.getClickedBlock().getWorld();
/* 627 */       if ((!event.isCancelled()) && (Functions.checkConfig(world, "block-place") == 1)) {
/* 628 */         Player player = event.getPlayer();
/* 629 */         List<Material> entityBlockTypes = Arrays.asList(new Material[] { Material.ARMOR_STAND, Material.END_CRYSTAL });
/* 630 */         Material handType = null;
/* 631 */         ItemStack mainHand = player.getInventory().getItemInMainHand();
/* 632 */         ItemStack offHand = player.getInventory().getItemInOffHand();
/* 633 */         if ((event.getHand().equals(EquipmentSlot.HAND)) && (mainHand != null) && (entityBlockTypes.contains(mainHand.getType()))) {
/* 634 */           handType = mainHand.getType();
/*     */         }
/* 636 */         else if ((event.getHand().equals(EquipmentSlot.OFF_HAND)) && (offHand != null) && (entityBlockTypes.contains(offHand.getType()))) {
/* 637 */           handType = offHand.getType();
/*     */         }
/*     */         else {
/* 640 */           return;
/*     */         }
/*     */         
/* 643 */         if (handType.equals(Material.END_CRYSTAL)) {
/* 644 */           Location crystalLocation = event.getClickedBlock().getLocation();
/* 645 */           if ((crystalLocation.getBlock().getType().equals(Material.OBSIDIAN)) || (crystalLocation.getBlock().getType().equals(Material.BEDROCK))) {
/* 646 */             crystalLocation.setY(crystalLocation.getY() + 1.0D);
/* 647 */             boolean exists = false;
/* 648 */             for (Entity entity : crystalLocation.getChunk().getEntities()) {
/* 649 */               if (((entity instanceof EnderCrystal)) && 
/* 650 */                 (entity.getLocation().getBlockX() == crystalLocation.getBlockX()) && (entity.getLocation().getBlockY() == crystalLocation.getBlockY()) && (entity.getLocation().getBlockZ() == crystalLocation.getBlockZ())) {
/* 651 */                 exists = true;
/* 652 */                 break;
/*     */               }
/*     */             }
/*     */             
/* 656 */             if (!exists) {
/* 657 */               final Player playerFinal = player;
/* 658 */               final Location locationFinal = crystalLocation;
/* 659 */               CoreProtect.getInstance().getServer().getScheduler().runTask(CoreProtect.getInstance(), new Runnable()
/*     */               {
/*     */                 public void run() {
/*     */                   try {
/* 663 */                     boolean exists = false;
/* 664 */                     int showingBottom = 0;
/* 665 */                     for (Entity entity : locationFinal.getChunk().getEntities()) {
/* 666 */                       if (((entity instanceof EnderCrystal)) && 
/* 667 */                         (entity.getLocation().getBlockX() == locationFinal.getBlockX()) && (entity.getLocation().getBlockY() == locationFinal.getBlockY()) && (entity.getLocation().getBlockZ() == locationFinal.getBlockZ())) {
/* 668 */                         EnderCrystal enderCrystal = (EnderCrystal)entity;
/* 669 */                         showingBottom = enderCrystal.isShowingBottom() ? 1 : 0;
/* 670 */                         exists = true;
/* 671 */                         break;
/*     */                       }
/*     */                     }
/*     */                     
/* 675 */                     if (exists) {
/* 676 */                       PlayerListener.queueBlockPlace(playerFinal.getName(), locationFinal.getBlock().getState(), locationFinal.getBlock(), locationFinal.getBlock().getState(), Material.END_CRYSTAL, showingBottom, 1);
/*     */                     }
/*     */                   }
/*     */                   catch (Exception e) {
/* 680 */                     e.printStackTrace();
/*     */                   }
/*     */                 }
/*     */               });
/*     */             }
/*     */           }
/*     */         }
/*     */         else {
/* 688 */           Block relative_block = event.getClickedBlock().getRelative(event.getBlockFace());
/* 689 */           Location location_1 = relative_block.getLocation();
/* 690 */           Location location_2 = event.getClickedBlock().getLocation();
/* 691 */           String key_1 = world.getName() + "-" + location_1.getBlockX() + "-" + location_1.getBlockY() + "-" + location_1.getBlockZ();
/* 692 */           String key_2 = world.getName() + "-" + location_2.getBlockX() + "-" + location_2.getBlockY() + "-" + location_2.getBlockZ();
/* 693 */           Object[] keys = { key_1, key_2, handType };
/* 694 */           Config.entity_block_mapper.put(player.getUniqueId(), keys);
/*     */         }
/*     */       }
/*     */     }
/* 698 */     else if (event.getAction().equals(Action.PHYSICAL)) {
/* 699 */       Block block = event.getClickedBlock();
/* 700 */       if (block == null) {
/* 701 */         return;
/*     */       }
/* 703 */       if (!block.getType().equals(Material.SOIL)) {
/* 704 */         return;
/*     */       }
/* 706 */       World world = block.getWorld();
/* 707 */       if ((!event.isCancelled()) && (Functions.checkConfig(world, "block-break") == 1)) {
/* 708 */         Player player = event.getPlayer();
/* 709 */         Block block_above = world.getBlockAt(block.getX(), block.getY() + 1, block.getZ());
/* 710 */         Material type = block_above.getType();
/* 711 */         if (!type.equals(Material.AIR))
/*     */         {
/* 713 */           int data = Functions.getData(block_above);
/* 714 */           Queue.queueBlockBreak(player.getName(), block_above.getState(), type, data);
/*     */         }
/* 716 */         Queue.queueBlockPlace(player.getName(), block, block.getState(), Material.DIRT, Functions.getData(block));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
/* 723 */     if ((event instanceof PlayerArmorStandManipulateEvent)) {
/* 724 */       return;
/*     */     }
/* 726 */     Player player = event.getPlayer();
/* 727 */     Entity entity = event.getRightClicked();
/* 728 */     World world = entity.getWorld();
/* 729 */     if (((entity instanceof ItemFrame)) && (!event.isCancelled()) && (Functions.checkConfig(world, "block-place") == 1)) {
/* 730 */       ItemFrame frame = (ItemFrame)entity;
/* 731 */       if ((frame.getItem().getType().equals(Material.AIR) == true) && (!player.getInventory().getItemInMainHand().getType().equals(Material.AIR))) {
/* 732 */         Material t = Material.ITEM_FRAME;
/* 733 */         int hand = Functions.block_id(player.getInventory().getItemInMainHand().getType());
/* 734 */         int d = 0;
/* 735 */         if (frame.getItem() != null) {
/* 736 */           d = Functions.block_id(frame.getItem().getType());
/*     */         }
/* 738 */         String playername = player.getName();
/* 739 */         Block block = frame.getLocation().getBlock();
/* 740 */         Queue.queueBlockBreak(playername, block.getState(), t, d);
/* 741 */         Queue.queueBlockPlace(playername, block.getState(), t, hand);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onPlayerJoin(PlayerJoinEvent event)
/*     */   {
/* 749 */     Player player = event.getPlayer();
/* 750 */     int time = (int)(System.currentTimeMillis() / 1000L);
/* 751 */     Queue.queuePlayerLogin(player, time, Functions.checkConfig(player.getWorld(), "player-sessions"), Functions.checkConfig(player.getWorld(), "username-changes"));
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   public void onPlayerQuit(PlayerQuitEvent event) {
/* 756 */     Player player = event.getPlayer();
/* 757 */     if (Functions.checkConfig(player.getWorld(), "player-sessions") == 1) {
/* 758 */       int time = (int)(System.currentTimeMillis() / 1000L);
/* 759 */       Queue.queuePlayerQuit(player, time);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onSignChange(SignChangeEvent event) {
/* 765 */     String player = event.getPlayer().getName();
/* 766 */     Block block = event.getBlock();
/* 767 */     String line1 = event.getLine(0);
/* 768 */     String line2 = event.getLine(1);
/* 769 */     String line3 = event.getLine(2);
/* 770 */     String line4 = event.getLine(3);
/*     */     
/* 772 */     if ((!event.isCancelled()) && (Functions.checkConfig(block.getWorld(), "sign-text") == 1)) {
/* 773 */       Queue.queueSignText(player, block.getState(), line1, line2, line3, line4, 0);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\listener\PlayerListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */