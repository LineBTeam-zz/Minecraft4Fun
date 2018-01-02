/*     */ package net.coreprotect.consumer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.coreprotect.CoreProtect;
/*     */ import net.coreprotect.Functions;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.CreatureSpawner;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class Queue
/*     */ {
/*     */   protected static void queueAdvancedBreak(String user, BlockState block, Material type, int data, Material break_type, int block_number)
/*     */   {
/*  21 */     int consumer_id = Consumer.getConsumerId();
/*  22 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(0), type, Integer.valueOf(data), break_type, Integer.valueOf(0), Integer.valueOf(block_number) });
/*  23 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   protected static void queueArtInsert(int id, String name) {
/*  27 */     Location location = new Location((World)CoreProtect.getInstance().getServer().getWorlds().get(0), 0.0D, 0.0D, 0.0D);
/*  28 */     int consumer_id = Consumer.getConsumerId();
/*  29 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(22), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(id) });
/*  30 */     queueStandardData(consumer_id, new String[] { name, null }, location);
/*     */   }
/*     */   
/*     */   protected static void queueBlockBreak(String user, BlockState block, Material type, int data) {
/*  34 */     queueBlockBreak(user, block, type, data, null, 0);
/*     */   }
/*     */   
/*     */   protected static void queueBlockBreak(String user, BlockState block, Material type, int data, Material break_type, int block_number) {
/*  38 */     if (type.equals(Material.MOB_SPAWNER)) {
/*  39 */       CreatureSpawner mobSpawner = (CreatureSpawner)block;
/*  40 */       data = Functions.getSpawnerType(mobSpawner.getSpawnedType());
/*     */     }
/*  42 */     else if ((type.equals(Material.DOUBLE_PLANT)) && 
/*  43 */       (data >= 8) && (!user.startsWith("#"))) {
/*  44 */       if (block_number == 5) {
/*  45 */         return;
/*     */       }
/*  47 */       block = block.getWorld().getBlockAt(block.getX(), block.getY() - 1, block.getZ()).getState();
/*  48 */       data = Functions.getData(block);
/*     */     }
/*     */     
/*  51 */     int consumer_id = Consumer.getConsumerId();
/*  52 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(0), type, Integer.valueOf(data), break_type, Integer.valueOf(0), Integer.valueOf(block_number) });
/*  53 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   protected static void queueBlockPlace(Player player, BlockState final_placed, Block placed, BlockState replaced, Material force_t, int force_d) {
/*  57 */     queueBlockPlace(player.getName(), final_placed, placed, replaced, force_t, force_d, 0);
/*     */   }
/*     */   
/*     */   protected static void queueBlockPlace(String player, Block placed) {
/*  61 */     queueBlockPlace(player, placed.getState(), placed, null, null, -1, 0);
/*     */   }
/*     */   
/*     */   protected static void queueBlockPlace(String player, Block placed, BlockState replaced, int force) {
/*  65 */     queueBlockPlace(player, placed.getState(), placed, replaced, null, -1, force);
/*     */   }
/*     */   
/*     */   protected static void queueBlockPlace(String user, Block placed, BlockState replaced, Material type, int data) {
/*  69 */     queueBlockPlace(user, placed.getState(), placed, replaced, type, data, 1);
/*     */   }
/*     */   
/*     */   protected static void queueBlockPlace(String user, BlockState block_location, Block block_type, BlockState block_replaced, Material force_t, int force_d, int force_data)
/*     */   {
/*  74 */     Material type = block_type.getType();
/*  75 */     int data = Functions.getData(block_type);
/*  76 */     Material replace_type = null;
/*  77 */     int replace_data = 0;
/*     */     
/*  79 */     if (type.equals(Material.MOB_SPAWNER)) {
/*  80 */       CreatureSpawner mobSpawner = (CreatureSpawner)block_location;
/*  81 */       data = Functions.getSpawnerType(mobSpawner.getSpawnedType());
/*  82 */       force_data = 1;
/*     */     }
/*  84 */     if (block_replaced != null) {
/*  85 */       replace_type = block_replaced.getType();
/*  86 */       replace_data = Functions.getData(block_replaced);
/*  87 */       if ((replace_type.equals(Material.DOUBLE_PLANT)) && (replace_data >= 8)) {
/*  88 */         BlockState block_below = block_replaced.getWorld().getBlockAt(block_replaced.getX(), block_replaced.getY() - 1, block_replaced.getZ()).getState();
/*  89 */         Material below_type = block_below.getType();
/*  90 */         int below_data = Functions.getData(block_below);
/*  91 */         queueBlockBreak(user, block_below, below_type, below_data);
/*     */       }
/*     */     }
/*     */     
/*  95 */     if (force_t != null) {
/*  96 */       type = force_t;
/*  97 */       force_data = 1;
/*     */     }
/*  99 */     if (force_d != -1) {
/* 100 */       data = force_d;
/* 101 */       force_data = 1;
/*     */     }
/*     */     
/* 104 */     int consumer_id = Consumer.getConsumerId();
/* 105 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(1), type, Integer.valueOf(data), replace_type, Integer.valueOf(replace_data), Integer.valueOf(force_data) });
/* 106 */     queueStandardData(consumer_id, new String[] { user, null }, block_location);
/*     */   }
/*     */   
/*     */   protected static void queueBlockPlace(String user, BlockState placed, BlockState replaced, Material force_type) {
/* 110 */     queueBlockPlace(user, placed, placed.getBlock(), replaced, force_type, -1, 0);
/*     */   }
/*     */   
/*     */   protected static void queueBlockPlace(String player, BlockState placed, BlockState replaced, Material type, int data) {
/* 114 */     queueBlockPlace(player, placed, placed.getBlock(), replaced, type, data, 1);
/*     */   }
/*     */   
/*     */   protected static void queueBlockPlace(String player, BlockState placed, Material type, int data) {
/* 118 */     queueBlockPlace(player, placed, placed.getBlock(), null, type, data, 1);
/*     */   }
/*     */   
/*     */   protected static void queueBlockPlaceDelayed(String user, final Block placed, final BlockState replaced, int ticks) {
/* 122 */     CoreProtect.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CoreProtect.getInstance(), new Runnable()
/*     */     {
/*     */       public void run() {
/*     */         try {
/* 126 */           Queue.queueBlockPlace(this.val$user, placed.getState(), placed, replaced, null, -1, 0);
/*     */         }
/*     */         catch (Exception e) {
/* 129 */           e.printStackTrace(); } } }, ticks);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected static void queueContainerBreak(String user, BlockState block, Material type, org.bukkit.inventory.ItemStack[] old_inventory)
/*     */   {
/* 136 */     int consumer_id = Consumer.getConsumerId();
/* 137 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(3), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(0) });
/* 138 */     ((Map)Consumer.consumer_containers.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), old_inventory);
/* 139 */     block.setType(type);
/* 140 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   protected static void queueContainerRollbackUpdate(String user, Location location, List<Object[]> list, int action) {
/* 144 */     if (location == null) {
/* 145 */       location = new Location((World)CoreProtect.getInstance().getServer().getWorlds().get(0), 0.0D, 0.0D, 0.0D);
/*     */     }
/* 147 */     int consumer_id = Consumer.getConsumerId();
/* 148 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(8), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(action) });
/* 149 */     ((Map)Consumer.consumer_object_array_list.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), list);
/* 150 */     queueStandardData(consumer_id, new String[] { user, null }, location);
/*     */   }
/*     */   
/*     */   protected static void queueContainerTransaction(String user, BlockState block, Material type, Object inventory, int chest_id) {
/* 154 */     int consumer_id = Consumer.getConsumerId();
/* 155 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(5), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(chest_id) });
/* 156 */     ((Map)Consumer.consumer_inventories.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), inventory);
/* 157 */     block.setType(type);
/* 158 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   protected static void queueEntityInsert(int id, String name) {
/* 162 */     Location location = new Location((World)CoreProtect.getInstance().getServer().getWorlds().get(0), 0.0D, 0.0D, 0.0D);
/* 163 */     int consumer_id = Consumer.getConsumerId();
/* 164 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(23), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(id) });
/* 165 */     queueStandardData(consumer_id, new String[] { name, null }, location);
/*     */   }
/*     */   
/*     */   protected static void queueEntityKill(String user, Location location, List<Object> data, org.bukkit.entity.EntityType type) {
/* 169 */     int consumer_id = Consumer.getConsumerId();
/* 170 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(16), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(0) });
/* 171 */     ((Map)Consumer.consumer_object_list.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), data);
/* 172 */     queueStandardData(consumer_id, new String[] { user, null }, new Object[] { location.getBlock().getState(), type });
/*     */   }
/*     */   
/*     */   protected static void queueEntitySpawn(String user, BlockState block, org.bukkit.entity.EntityType type, int data) {
/* 176 */     int consumer_id = Consumer.getConsumerId();
/* 177 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(17), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(data) });
/* 178 */     queueStandardData(consumer_id, new String[] { user, null }, new Object[] { block, type });
/*     */   }
/*     */   
/*     */   protected static void queueHangingRemove(String user, BlockState block, int delay) {
/* 182 */     int consumer_id = Consumer.getConsumerId();
/* 183 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(18), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(delay) });
/* 184 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   protected static void queueHangingSpawn(String user, BlockState block, Material type, int data, int delay) {
/* 188 */     int consumer_id = Consumer.getConsumerId();
/* 189 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(19), type, Integer.valueOf(data), null, Integer.valueOf(0), Integer.valueOf(delay) });
/* 190 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   protected static void queueMaterialInsert(int id, String name) {
/* 194 */     Location location = new Location((World)CoreProtect.getInstance().getServer().getWorlds().get(0), 0.0D, 0.0D, 0.0D);
/* 195 */     int consumer_id = Consumer.getConsumerId();
/* 196 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(21), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(id) });
/* 197 */     queueStandardData(consumer_id, new String[] { name, null }, location);
/*     */   }
/*     */   
/*     */   protected static void queueNaturalBlockBreak(String user, BlockState block, Block relative, Material type, int data) {
/* 201 */     List<BlockState> relative_list = new ArrayList();
/* 202 */     if (relative != null) {
/* 203 */       relative_list.add(relative.getState());
/*     */     }
/* 205 */     int consumer_id = Consumer.getConsumerId();
/* 206 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(20), type, Integer.valueOf(data), null, Integer.valueOf(0), Integer.valueOf(0) });
/* 207 */     ((Map)Consumer.consumer_block_list.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), relative_list);
/* 208 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   protected static void queuePlayerChat(Player player, String message, int time) {
/* 212 */     int consumer_id = Consumer.getConsumerId();
/* 213 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(12), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(time) });
/* 214 */     ((Map)Consumer.consumer_strings.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), message);
/* 215 */     queueStandardData(consumer_id, new String[] { player.getName(), null }, player.getLocation());
/*     */   }
/*     */   
/*     */   protected static void queuePlayerCommand(Player player, String message, int time) {
/* 219 */     int consumer_id = Consumer.getConsumerId();
/* 220 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(13), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(time) });
/* 221 */     ((Map)Consumer.consumer_strings.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), message);
/* 222 */     queueStandardData(consumer_id, new String[] { player.getName(), null }, player.getLocation().getBlock().getState());
/*     */   }
/*     */   
/*     */   protected static void queuePlayerInteraction(String user, BlockState block) {
/* 226 */     int consumer_id = Consumer.getConsumerId();
/* 227 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(4), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(0) });
/* 228 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   protected static void queuePlayerKill(String user, Location location, String player) {
/* 232 */     int consumer_id = Consumer.getConsumerId();
/* 233 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(24), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(0) });
/* 234 */     queueStandardData(consumer_id, new String[] { user, null }, new Object[] { location.getBlock().getState(), player });
/*     */   }
/*     */   
/*     */   protected static void queuePlayerLogin(Player player, int time, int configSessions, int configUsernames) {
/* 238 */     int consumer_id = Consumer.getConsumerId();
/* 239 */     String uuid = player.getUniqueId().toString();
/* 240 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(14), null, Integer.valueOf(configSessions), null, Integer.valueOf(configUsernames), Integer.valueOf(time) });
/* 241 */     ((Map)Consumer.consumer_strings.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), uuid);
/* 242 */     queueStandardData(consumer_id, new String[] { player.getName(), uuid }, player.getLocation().getBlock().getState());
/*     */   }
/*     */   
/*     */   protected static void queuePlayerQuit(Player player, int time) {
/* 246 */     int consumer_id = Consumer.getConsumerId();
/* 247 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(15), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(time) });
/* 248 */     queueStandardData(consumer_id, new String[] { player.getName(), null }, player.getLocation().getBlock().getState());
/*     */   }
/*     */   
/*     */   protected static void queueRollbackUpdate(String user, Location location, List<Object[]> list, int action) {
/* 252 */     if (location == null) {
/* 253 */       location = new Location((World)CoreProtect.getInstance().getServer().getWorlds().get(0), 0.0D, 0.0D, 0.0D);
/*     */     }
/* 255 */     int consumer_id = Consumer.getConsumerId();
/* 256 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(7), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(action) });
/* 257 */     ((Map)Consumer.consumer_object_array_list.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), list);
/* 258 */     queueStandardData(consumer_id, new String[] { user, null }, location);
/*     */   }
/*     */   
/*     */   protected static void queueSignText(String user, BlockState block, String line1, String line2, String line3, String line4, int offset) {
/* 262 */     int consumer_id = Consumer.getConsumerId();
/* 263 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(2), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(offset) });
/* 264 */     ((Map)Consumer.consumer_signs.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), new String[] { line1, line2, line3, line4 });
/* 265 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   protected static void queueSignUpdate(String user, BlockState block, int action, int time) {
/* 269 */     int consumer_id = Consumer.getConsumerId();
/* 270 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(10), null, Integer.valueOf(action), null, Integer.valueOf(0), Integer.valueOf(time) });
/* 271 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   protected static void queueSkullUpdate(String user, BlockState block, int row_id) {
/* 275 */     int consumer_id = Consumer.getConsumerId();
/* 276 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(11), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(row_id) });
/* 277 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   private static void queueStandardData(int consumer_id, String[] user, Object object) {
/* 281 */     ((Map)Consumer.consumer_users.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), user);
/* 282 */     ((Map)Consumer.consumer_object.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), object);
/* 283 */     Consumer.consumer_id.put(Integer.valueOf(Consumer.current_consumer), Integer.valueOf(consumer_id + 1));
/*     */   }
/*     */   
/*     */   protected static void queueStructureGrow(String user, BlockState block, List<BlockState> block_list) {
/* 287 */     int consumer_id = Consumer.getConsumerId();
/* 288 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(6), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(0) });
/* 289 */     ((Map)Consumer.consumer_block_list.get(Integer.valueOf(Consumer.current_consumer))).put(Integer.valueOf(consumer_id), block_list);
/* 290 */     queueStandardData(consumer_id, new String[] { user, null }, block);
/*     */   }
/*     */   
/*     */   protected static void queueWorldInsert(int id, String world) {
/* 294 */     Location location = new Location((World)CoreProtect.getInstance().getServer().getWorlds().get(0), 0.0D, 0.0D, 0.0D);
/* 295 */     int consumer_id = Consumer.getConsumerId();
/* 296 */     ((ArrayList)Consumer.consumer.get(Integer.valueOf(Consumer.current_consumer))).add(new Object[] { Integer.valueOf(consumer_id), Integer.valueOf(9), null, Integer.valueOf(0), null, Integer.valueOf(0), Integer.valueOf(id) });
/* 297 */     queueStandardData(consumer_id, new String[] { world, null }, location);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\consumer\Queue.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */