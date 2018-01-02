/*     */ package net.coreprotect.listener;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.consumer.Queue;
/*     */ import net.coreprotect.database.Database;
/*     */ import net.coreprotect.database.Lookup;
/*     */ import net.coreprotect.model.BlockInfo;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Banner;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.Sign;
/*     */ import org.bukkit.block.Skull;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.block.BlockBurnEvent;
/*     */ import org.bukkit.event.block.BlockFromToEvent;
/*     */ import org.bukkit.event.block.BlockIgniteEvent;
/*     */ import org.bukkit.event.block.BlockPistonEvent;
/*     */ import org.bukkit.event.block.BlockPistonExtendEvent;
/*     */ import org.bukkit.event.block.BlockPistonRetractEvent;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.block.BlockSpreadEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class BlockListener extends Queue implements Listener
/*     */ {
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onBlockBreak(BlockBreakEvent event)
/*     */   {
/*  42 */     if (!event.isCancelled()) {
/*  43 */       String player = event.getPlayer().getName();
/*  44 */       Block bl = event.getBlock();
/*  45 */       int x = bl.getX();
/*  46 */       int y = bl.getY();
/*  47 */       int z = bl.getZ();
/*  48 */       World world = bl.getWorld();
/*     */       
/*  50 */       Location l1 = new Location(world, x + 1, y, z);
/*  51 */       Location l2 = new Location(world, x - 1, y, z);
/*  52 */       Location l3 = new Location(world, x, y, z + 1);
/*  53 */       Location l4 = new Location(world, x, y, z - 1);
/*  54 */       Location l5 = new Location(world, x, y + 1, z);
/*  55 */       Location l6 = new Location(world, x, y - 1, z);
/*     */       
/*  57 */       int l = 1;
/*  58 */       int m = 7;
/*  59 */       if (Functions.checkConfig(world, "natural-break") == 0) {
/*  60 */         l = 6;
/*     */       }
/*  62 */       if (Functions.checkConfig(world, "block-break") == 0) {
/*  63 */         m = 6;
/*     */       }
/*  65 */       Block block = bl;
/*  66 */       Material type = bl.getType();
/*  67 */       int data = Functions.getData(bl);
/*     */       
/*  69 */       while (l < m)
/*     */       {
/*  71 */         Location lc = l1;
/*  72 */         if (l == 2) {
/*  73 */           lc = l2;
/*     */         }
/*  75 */         if (l == 3) {
/*  76 */           lc = l3;
/*     */         }
/*  78 */         if (l == 4) {
/*  79 */           lc = l4;
/*     */         }
/*  81 */         if (l == 5) {
/*  82 */           lc = l5;
/*     */         }
/*     */         
/*  85 */         Block b = block;
/*  86 */         boolean check_down = false;
/*  87 */         Material bt = type;
/*  88 */         int bd = data;
/*  89 */         int log = 1;
/*     */         
/*  91 */         if (l < 6) {
/*  92 */           if ((l == 4) && ((type.equals(Material.WOODEN_DOOR)) || (type.equals(Material.SPRUCE_DOOR)) || (type.equals(Material.BIRCH_DOOR)) || (type.equals(Material.JUNGLE_DOOR)) || (type.equals(Material.ACACIA_DOOR)) || (type.equals(Material.DARK_OAK_DOOR)) || (type.equals(Material.IRON_DOOR_BLOCK)))) {
/*  93 */             lc = l6;
/*  94 */             check_down = true;
/*     */           }
/*  96 */           Block block_t = world.getBlockAt(lc);
/*  97 */           Material t = block_t.getType();
/*  98 */           if ((l == 5) && 
/*  99 */             (BlockInfo.falling_block_types.contains(t)) && 
/* 100 */             (Functions.checkConfig(world, "block-movement") == 1))
/*     */           {
/* 102 */             int yc = y + 2;
/* 103 */             int topfound = 0;
/* 104 */             while (topfound == 0) {
/* 105 */               Block block_up = world.getBlockAt(x, yc, z);
/* 106 */               Material up = block_up.getType();
/* 107 */               if (!BlockInfo.falling_block_types.contains(up)) {
/* 108 */                 lc = new Location(world, x, yc - 1, z);
/* 109 */                 topfound = 1;
/*     */               }
/* 111 */               yc++;
/*     */             }
/*     */           }
/*     */           
/*     */ 
/* 116 */           if (!BlockInfo.track_any.contains(t)) {
/* 117 */             if ((l != 5) && (!check_down)) {
/* 118 */               if (!BlockInfo.track_side.contains(t)) {
/* 119 */                 log = 0;
/*     */ 
/*     */ 
/*     */               }
/* 123 */               else if ((type.equals(Material.STONE_BUTTON)) || (type.equals(Material.WOOD_BUTTON))) {
/* 124 */                 Block check = world.getBlockAt(lc);
/* 125 */                 int check_data = Functions.getData(check);
/* 126 */                 if (check_data != l) {
/* 127 */                   log = 0;
/*     */                 }
/*     */               }
/* 130 */               else if ((t.equals(Material.RAILS)) || (t.equals(Material.POWERED_RAIL)) || (t.equals(Material.DETECTOR_RAIL)) || (t.equals(Material.ACTIVATOR_RAIL))) {
/* 131 */                 int check_data = Functions.getData(block_t);
/* 132 */                 if ((l == 1) && (check_data != 3)) {
/* 133 */                   log = 0;
/*     */                 }
/* 135 */                 else if ((l == 2) && (check_data != 2)) {
/* 136 */                   log = 0;
/*     */                 }
/* 138 */                 else if ((l == 3) && (check_data != 4)) {
/* 139 */                   log = 0;
/*     */                 }
/* 141 */                 else if ((l == 4) && (check_data != 5)) {
/* 142 */                   log = 0;
/*     */                 }
/*     */               }
/* 145 */               else if ((t.equals(Material.BED_BLOCK) == true) && (!type.equals(Material.BED_BLOCK))) {
/* 146 */                 log = 0;
/*     */ 
/*     */               }
/*     */               
/*     */ 
/*     */ 
/*     */             }
/* 153 */             else if (!BlockInfo.track_top.contains(t)) {
/* 154 */               log = 0;
/*     */             }
/*     */             
/* 157 */             if (log == 0) {
/* 158 */               if (type.equals(Material.PISTON_EXTENSION)) {
/* 159 */                 if ((t.equals(Material.PISTON_STICKY_BASE)) || (t.equals(Material.PISTON_BASE))) {
/* 160 */                   log = 1;
/*     */                 }
/*     */               }
/* 163 */               else if ((l == 5) && 
/* 164 */                 (BlockInfo.falling_block_types.contains(t))) {
/* 165 */                 log = 1;
/*     */               }
/*     */               
/*     */             }
/*     */             
/*     */ 
/*     */           }
/* 172 */           else if (t.equals(Material.PISTON_EXTENSION)) {
/* 173 */             if ((!type.equals(Material.PISTON_STICKY_BASE)) && (!type.equals(Material.PISTON_BASE))) {
/* 174 */               log = 0;
/*     */             }
/*     */           }
/*     */           else {
/* 178 */             Block check = world.getBlockAt(lc);
/* 179 */             int check_data = Functions.getData(check);
/* 180 */             if (check_data != l) {
/* 181 */               log = 0;
/*     */             }
/*     */           }
/*     */           
/* 185 */           if (log == 1) {
/* 186 */             b = world.getBlockAt(lc);
/* 187 */             bt = b.getType();
/* 188 */             bd = Functions.getData(b);
/*     */           }
/*     */         }
/* 191 */         BlockState b1 = b.getState();
/* 192 */         Material bt1 = bt;
/* 193 */         int bd1 = bd;
/* 194 */         int bn = l;
/*     */         
/* 196 */         if ((log == 1) && ((bt.equals(Material.SKULL)) || (bt.equals(Material.WALL_BANNER)) || (bt.equals(Material.STANDING_BANNER)))) {
/*     */           try {
/* 198 */             if (((b1 instanceof Banner)) || ((b1 instanceof Skull))) {
/* 199 */               Queue.queueAdvancedBreak(player, b1, bt1, bd1, type, bn);
/*     */             }
/* 201 */             log = 0;
/*     */           }
/*     */           catch (Exception e) {
/* 204 */             e.printStackTrace();
/*     */           }
/*     */         }
/* 207 */         if ((log == 1) && ((bt.equals(Material.SIGN_POST)) || (bt.equals(Material.WALL_SIGN))) && 
/* 208 */           (Functions.checkConfig(world, "sign-text") == 1)) {
/*     */           try {
/* 210 */             Sign sign = (Sign)b.getState();
/* 211 */             String line1 = sign.getLine(0);
/* 212 */             String line2 = sign.getLine(1);
/* 213 */             String line3 = sign.getLine(2);
/* 214 */             String line4 = sign.getLine(3);
/* 215 */             Queue.queueSignText(player, b1, line1, line2, line3, line4, 5);
/*     */           }
/*     */           catch (Exception e) {
/* 218 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */         
/* 222 */         if (log == 1) {
/* 223 */           Database.containerBreakCheck(player, block.getType(), block, block.getLocation());
/* 224 */           Functions.iceBreakCheck(b1, player, bt1);
/* 225 */           Queue.queueBlockBreak(player, b1, bt1, bd1, type, bn);
/*     */         }
/* 227 */         l++;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onBlockBurn(BlockBurnEvent event) {
/* 234 */     World world = event.getBlock().getWorld();
/* 235 */     if ((!event.isCancelled()) && (Functions.checkConfig(world, "block-burn") == 1)) {
/* 236 */       String player = "#fire";
/* 237 */       Block block = event.getBlock();
/* 238 */       Material type = block.getType();
/* 239 */       int data = Functions.getData(event.getBlock());
/* 240 */       Queue.queueBlockBreak(player, block.getState(), type, data);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onBlockFromTo(BlockFromToEvent event) {
/* 246 */     Material type = event.getBlock().getType();
/* 247 */     Material type2 = event.getToBlock().getType();
/* 248 */     if (!event.isCancelled()) {
/* 249 */       World world = event.getBlock().getWorld();
/* 250 */       if (((Functions.checkConfig(world, "water-flow") == 1) && ((type.equals(Material.WATER)) || (type.equals(Material.STATIONARY_WATER)))) || ((Functions.checkConfig(world, "lava-flow") == 1) && ((type.equals(Material.LAVA)) || (type.equals(Material.STATIONARY_LAVA))))) {
/* 251 */         List<Material> flow_break = Arrays.asList(new Material[] { Material.AIR, Material.SAPLING, Material.POWERED_RAIL, Material.DETECTOR_RAIL, Material.WEB, Material.LONG_GRASS, Material.DEAD_BUSH, Material.YELLOW_FLOWER, Material.RED_ROSE, Material.BROWN_MUSHROOM, Material.RED_MUSHROOM, Material.TORCH, Material.FIRE, Material.REDSTONE_WIRE, Material.CROPS, Material.RAILS, Material.LEVER, Material.REDSTONE_TORCH_OFF, Material.REDSTONE_TORCH_ON, Material.STONE_BUTTON, Material.SNOW, Material.DIODE_BLOCK_OFF, Material.DIODE_BLOCK_ON, Material.VINE, Material.COCOA, Material.TRIPWIRE_HOOK, Material.TRIPWIRE, Material.CARROT, Material.POTATO, Material.WOOD_BUTTON, Material.SKULL, Material.REDSTONE_COMPARATOR_OFF, Material.REDSTONE_COMPARATOR_ON, Material.ACTIVATOR_RAIL, Material.CARPET, Material.DOUBLE_PLANT });
/* 252 */         if ((flow_break.contains(type2)) || (((!type.equals(Material.WATER)) && (!type.equals(Material.STATIONARY_WATER))) || ((type2.equals(Material.LAVA)) || (type2.equals(Material.STATIONARY_LAVA)) || (((type.equals(Material.LAVA)) || (type.equals(Material.STATIONARY_LAVA))) && ((type2.equals(Material.WATER)) || (type2.equals(Material.STATIONARY_WATER))))))) {
/* 253 */           String f = "#flow";
/* 254 */           if ((type.equals(Material.WATER)) || (type.equals(Material.STATIONARY_WATER))) {
/* 255 */             f = "#water";
/*     */           }
/* 257 */           else if ((type.equals(Material.LAVA)) || (type.equals(Material.STATIONARY_LAVA))) {
/* 258 */             f = "#lava";
/*     */           }
/*     */           
/* 261 */           Block block = event.getBlock();
/* 262 */           int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 263 */           int x = event.getToBlock().getX();
/* 264 */           int y = event.getToBlock().getY();
/* 265 */           int z = event.getToBlock().getZ();
/* 266 */           int wid = Functions.getWorldId(block.getWorld().getName());
/* 267 */           if (Functions.checkConfig(world, "liquid-tracking") == 1) {
/* 268 */             String p = Lookup.who_placed_cache(block);
/* 269 */             if (p.length() > 0) {
/* 270 */               f = p;
/*     */             }
/*     */           }
/* 273 */           Config.lookup_cache.put("" + x + "." + y + "." + z + "." + wid + "", new Object[] { Integer.valueOf(unixtimestamp), f, type });
/* 274 */           Queue.queueBlockPlace(f, event.getToBlock(), event.getToBlock().getState(), type, Functions.getData(event.getBlock()));
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onBlockIgnite(BlockIgniteEvent event) {
/* 282 */     World world = event.getBlock().getWorld();
/* 283 */     if ((!event.isCancelled()) && (Functions.checkConfig(world, "block-ignite") == 1)) {
/* 284 */       Block block = event.getBlock();
/* 285 */       if (block == null) {
/* 286 */         return;
/*     */       }
/* 288 */       if (event.getPlayer() == null) {
/* 289 */         Queue.queueBlockPlace("#fire", block.getState(), block.getState(), Material.FIRE);
/*     */       }
/*     */       else {
/* 292 */         Player player = event.getPlayer();
/* 293 */         Queue.queueBlockPlace(player.getName(), block.getState(), null, Material.FIRE);
/* 294 */         int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 295 */         int world_id = Functions.getWorldId(block.getWorld().getName());
/* 296 */         Config.lookup_cache.put("" + block.getX() + "." + block.getY() + "." + block.getZ() + "." + world_id + "", new Object[] { Integer.valueOf(unixtimestamp), player.getName(), block.getType() });
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   protected void onBlockPiston(BlockPistonEvent event) {
/* 302 */     List<Block> event_blocks = null;
/* 303 */     if ((event instanceof BlockPistonExtendEvent)) {
/* 304 */       event_blocks = ((BlockPistonExtendEvent)event).getBlocks();
/*     */     }
/* 306 */     else if ((event instanceof BlockPistonRetractEvent)) {
/* 307 */       event_blocks = ((BlockPistonRetractEvent)event).getBlocks();
/*     */     }
/*     */     
/* 310 */     World world = event.getBlock().getWorld();
/* 311 */     List<BlockState> blocks; String e; int c; if ((Functions.checkConfig(world, "pistons") == 1) && (!event.isCancelled())) {
/* 312 */       List<Block> nblocks = new ArrayList();
/* 313 */       blocks = new ArrayList();
/*     */       
/* 315 */       for (Block block : event_blocks) {
/* 316 */         Block block_relative = block.getRelative(event.getDirection());
/* 317 */         if (Functions.checkConfig(world, "block-movement") == 1) {
/* 318 */           block_relative = Functions.fallingSand(block_relative, block.getState(), "#piston");
/*     */         }
/* 320 */         nblocks.add(block_relative);
/* 321 */         blocks.add(block.getState());
/*     */       }
/*     */       
/* 324 */       Block b = event.getBlock();
/* 325 */       BlockFace d = event.getDirection();
/* 326 */       Block bm = b.getRelative(d);
/* 327 */       int wid = Functions.getWorldId(bm.getWorld().getName());
/*     */       
/* 329 */       int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 330 */       int log = 0;
/* 331 */       int l = 0;
/* 332 */       while (l <= nblocks.size()) {
/* 333 */         int ll = l - 1;
/* 334 */         Block n = null;
/* 335 */         if (ll == -1) {
/* 336 */           n = bm;
/*     */         }
/*     */         else {
/* 339 */           n = (Block)nblocks.get(ll);
/*     */         }
/* 341 */         if (n != null) {
/* 342 */           int x = n.getX();
/* 343 */           int y = n.getY();
/* 344 */           int z = n.getZ();
/* 345 */           Material t = n.getType();
/* 346 */           String cords = "" + x + "." + y + "." + z + "." + wid + "." + t.name() + "";
/* 347 */           if (Config.piston_cache.get(cords) == null) {
/* 348 */             log = 1;
/*     */           }
/* 350 */           Config.piston_cache.put(cords, new Object[] { Integer.valueOf(unixtimestamp) });
/*     */         }
/* 352 */         l++;
/*     */       }
/* 354 */       if (log == 1) {
/* 355 */         e = "#piston";
/* 356 */         for (BlockState block : blocks) {
/* 357 */           Queue.queueBlockBreak(e, block, block.getType(), Functions.getData(block));
/*     */         }
/*     */         
/*     */ 
/* 361 */         c = 0;
/* 362 */         for (Block nblock : nblocks) {
/* 363 */           BlockState block = (BlockState)blocks.get(c);
/* 364 */           Queue.queueBlockPlace(e, nblock.getState(), block.getType(), Functions.getData(block));
/* 365 */           c++;
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onBlockPistonExtend(BlockPistonExtendEvent event) {
/* 373 */     onBlockPiston(event);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onBlockPistonRetract(BlockPistonRetractEvent event) {
/* 378 */     onBlockPiston(event);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onBlockPlace(BlockPlaceEvent event) {
/* 383 */     World world = event.getBlockPlaced().getWorld();
/* 384 */     if ((!event.isCancelled()) && (Functions.checkConfig(world, "block-place") == 1)) {
/* 385 */       Player player = event.getPlayer();
/* 386 */       Block b = event.getBlockPlaced();
/* 387 */       Block block = b;
/* 388 */       BlockState breplaced = event.getBlockReplacedState();
/* 389 */       Material force_type = null;
/* 390 */       int force_data = -1;
/* 391 */       boolean abort = false;
/*     */       
/* 393 */       Material block_type = b.getType();
/* 394 */       List<Material> stairs = Arrays.asList(new Material[] { Material.WOOD_STAIRS, Material.COBBLESTONE_STAIRS, Material.BRICK_STAIRS, Material.SMOOTH_STAIRS, Material.NETHER_BRICK_STAIRS, Material.SANDSTONE_STAIRS, Material.SPRUCE_WOOD_STAIRS, Material.BIRCH_WOOD_STAIRS, Material.JUNGLE_WOOD_STAIRS, Material.QUARTZ_STAIRS });
/*     */       
/* 396 */       List<Material> dir_blocks = Arrays.asList(new Material[] { Material.PISTON_STICKY_BASE, Material.PISTON_BASE, Material.DIODE_BLOCK_OFF, Material.SKULL, Material.REDSTONE_COMPARATOR_OFF });
/*     */       
/* 398 */       if ((Functions.listContains(BlockInfo.containers, block_type)) || (Functions.listContains(dir_blocks, block_type)) || (Functions.listContains(stairs, block_type))) {
/* 399 */         Queue.queueBlockPlaceDelayed(player.getName(), block, breplaced, 0);
/* 400 */         abort = true;
/*     */       }
/* 402 */       else if (block_type.equals(Material.FIRE)) {
/* 403 */         ItemStack item = event.getItemInHand();
/* 404 */         if (!item.getType().equals(Material.FIRE)) {
/* 405 */           abort = true;
/*     */         }
/*     */       }
/*     */       
/* 409 */       if (!abort) {
/* 410 */         if (Functions.checkConfig(world, "block-movement") == 1) {
/* 411 */           block = Functions.fallingSand(block, null, player.getName());
/* 412 */           if (!block.equals(b)) {
/* 413 */             force_type = b.getType();
/*     */           }
/*     */         }
/* 416 */         Queue.queueBlockPlace(player, block.getState(), b, breplaced, force_type, force_data);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onBlockSpread(BlockSpreadEvent event)
/*     */   {
/* 424 */     if ((!event.isCancelled()) && (Functions.checkConfig(event.getBlock().getWorld(), "vine-growth") == 1)) {
/* 425 */       Block block = event.getBlock();
/* 426 */       BlockState blockstate = event.getNewState();
/* 427 */       if (blockstate.getType().equals(Material.VINE)) {
/* 428 */         Queue.queueBlockPlace("#vine", block.getState(), blockstate.getType(), Functions.getData(blockstate));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\listener\BlockListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */