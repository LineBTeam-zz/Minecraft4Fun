/*     */ package net.coreprotect.command;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.coreprotect.CoreProtect;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.database.Database;
/*     */ import net.coreprotect.database.Lookup;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.block.Chest;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class RollbackRestoreCommand
/*     */ {
/*     */   protected static void runCommand(CommandSender player, boolean permission, String[] args, int force_seconds)
/*     */   {
/*  26 */     Location lo = CommandHandler.parseLocation(player, args);
/*  27 */     List<String> arg_uuids = new ArrayList();
/*  28 */     List<String> arg_users = CommandHandler.parseUsers(args);
/*  29 */     Integer[] arg_radius = CommandHandler.parseRadius(args, player, lo);
/*  30 */     int arg_noisy = CommandHandler.parseNoisy(args);
/*  31 */     List<Object> arg_exclude = CommandHandler.parseExcluded(player, args);
/*  32 */     List<String> arg_exclude_users = CommandHandler.parseExcludedUsers(player, args);
/*  33 */     List<Object> arg_blocks = CommandHandler.parseRestricted(player, args);
/*  34 */     String ts = CommandHandler.parseTimeString(args);
/*  35 */     int rbseconds = CommandHandler.parseTime(args);
/*  36 */     int arg_wid = CommandHandler.parseWorld(args);
/*  37 */     List<Integer> arg_action = CommandHandler.parseAction(args);
/*  38 */     boolean count = CommandHandler.parseCount(args);
/*  39 */     boolean worldedit = CommandHandler.parseWorldEdit(args);
/*  40 */     boolean forceglobal = CommandHandler.parseForceGlobal(args);
/*  41 */     int preview = CommandHandler.parsePreview(args);
/*  42 */     String corecommand = args[0].toLowerCase();
/*     */     
/*  44 */     if ((arg_blocks == null) || (arg_exclude == null) || (arg_exclude_users == null)) {
/*  45 */       return;
/*     */     }
/*     */     
/*  48 */     if ((arg_action.size() == 0) && (arg_blocks.size() > 0)) {
/*  49 */       for (Object arg_block : arg_blocks) {
/*  50 */         if ((arg_block instanceof Material)) {
/*  51 */           arg_action.add(Integer.valueOf(0));
/*  52 */           arg_action.add(Integer.valueOf(1));
/*     */         }
/*  54 */         else if ((arg_block instanceof org.bukkit.entity.EntityType)) {
/*  55 */           arg_action.add(Integer.valueOf(3));
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*  60 */     if (count == true) {
/*  61 */       LookupCommand.runCommand(player, permission, args);
/*  62 */       return;
/*     */     }
/*  64 */     if (Config.converter_running == true) {
/*  65 */       player.sendMessage("§3CoreProtect §f- Upgrade in progress. Please try again later.");
/*  66 */       return;
/*     */     }
/*  68 */     if (Config.purge_running == true) {
/*  69 */       player.sendMessage("§3CoreProtect §f- Purge in progress. Please try again later.");
/*  70 */       return;
/*     */     }
/*  72 */     if (arg_wid == -1) {
/*  73 */       String world_name = CommandHandler.parseWorldName(args);
/*  74 */       player.sendMessage("§3CoreProtect §f- World \"" + world_name + "\" not found.");
/*  75 */       return;
/*     */     }
/*  77 */     if ((preview > 0) && (!(player instanceof Player))) {
/*  78 */       player.sendMessage("§3CoreProtect §f- You can only preview rollbacks in-game.");
/*  79 */       return;
/*     */     }
/*  81 */     if (arg_action.contains(Integer.valueOf(-1))) {
/*  82 */       player.sendMessage("§3CoreProtect §f- That is not a valid action.");
/*  83 */       return;
/*     */     }
/*  85 */     if ((worldedit == true) && (arg_radius == null)) {
/*  86 */       player.sendMessage("§3CoreProtect §f- WorldEdit selection not found.");
/*  87 */       return;
/*     */     }
/*  89 */     if ((arg_radius != null) && (arg_radius[0].intValue() == -1)) {
/*  90 */       player.sendMessage("§3CoreProtect §f- Please enter a valid radius.");
/*  91 */       return;
/*     */     }
/*  93 */     if (Config.active_rollbacks.get(player.getName()) != null) {
/*  94 */       player.sendMessage("§3CoreProtect §f- A rollback/restore is already in progress.");
/*  95 */       return;
/*     */     }
/*  97 */     if ((preview > 1) && (force_seconds <= 0)) {
/*  98 */       preview = 1;
/*     */     }
/*     */     
/* 101 */     if (permission == true) {
/* 102 */       int a = 0;
/* 103 */       if ((corecommand.equals("restore")) || (corecommand.equals("rs")) || (corecommand.equals("re"))) {
/* 104 */         a = 1;
/*     */       }
/* 106 */       int final_action = a;
/*     */       
/* 108 */       int default_radius = ((Integer)Config.config.get("default-radius")).intValue();
/* 109 */       if ((((player instanceof Player)) || ((player instanceof org.bukkit.command.BlockCommandSender))) && (arg_radius == null) && (default_radius > 0) && (!forceglobal)) {
/* 110 */         Location location = lo;
/* 111 */         int xmin = location.getBlockX() - default_radius;
/* 112 */         int xmax = location.getBlockX() + default_radius;
/* 113 */         int zmin = location.getBlockZ() - default_radius;
/* 114 */         int zmax = location.getBlockZ() + default_radius;
/* 115 */         arg_radius = new Integer[] { Integer.valueOf(default_radius), Integer.valueOf(xmin), Integer.valueOf(xmax), Integer.valueOf(-1), Integer.valueOf(-1), Integer.valueOf(zmin), Integer.valueOf(zmax), Integer.valueOf(0) };
/*     */       }
/*     */       
/*     */ 
/* 119 */       int g = 1;
/* 120 */       if ((arg_users.contains("#global")) && 
/* 121 */         (arg_radius == null)) {
/* 122 */         g = 0;
/*     */       }
/*     */       
/*     */ 
/* 126 */       if ((arg_users.size() == 0) && (arg_wid > 0)) {
/* 127 */         if (final_action == 0) {
/* 128 */           player.sendMessage("§3CoreProtect §f- You did not specify a rollback user.");
/*     */         }
/*     */         else {
/* 131 */           player.sendMessage("§3CoreProtect §f- You did not specify a restore user.");
/*     */         }
/* 133 */         return;
/*     */       }
/*     */       
/* 136 */       if ((g == 1) && ((arg_users.size() > 0) || ((arg_users.size() == 0) && (arg_radius != null)))) {
/* 137 */         int max_radius = ((Integer)Config.config.get("max-radius")).intValue();
/* 138 */         if (arg_radius != null) {
/* 139 */           int radius_value = arg_radius[0].intValue();
/* 140 */           if ((radius_value > max_radius) && (max_radius > 0)) {
/* 141 */             player.sendMessage("§3CoreProtect §f- The maximum " + corecommand.toLowerCase() + " radius is " + max_radius + ".");
/* 142 */             player.sendMessage("§3CoreProtect §f- Use \"r:#global\" to do a global " + corecommand.toLowerCase() + ".");
/* 143 */             return;
/*     */           }
/*     */         }
/* 146 */         if (arg_action.size() > 0) {
/* 147 */           if (arg_action.contains(Integer.valueOf(4)) == true) {
/* 148 */             if ((arg_users.contains("#global") == true) || (arg_users.size() == 0)) {
/* 149 */               player.sendMessage("§3CoreProtect §f- To use that action, please specify a user.");
/* 150 */               return;
/*     */             }
/* 152 */             if (preview > 0) {
/* 153 */               player.sendMessage("§3CoreProtect §f- You can't preview container transactions.");
/*     */             }
/*     */             
/*     */           }
/* 157 */           else if ((!arg_action.contains(Integer.valueOf(0))) && (!arg_action.contains(Integer.valueOf(1))) && (!arg_action.contains(Integer.valueOf(3)))) {
/* 158 */             if (final_action == 0) {
/* 159 */               player.sendMessage("§3CoreProtect §f- That action can't be used with a rollback.");
/*     */             }
/*     */             else {
/* 162 */               player.sendMessage("§3CoreProtect §f- That action can't be used with a restore.");
/*     */             }
/* 164 */             return;
/*     */           }
/*     */         }
/*     */         
/* 168 */         if (arg_users.size() == 0) {
/* 169 */           arg_users.add("#global");
/*     */         }
/* 171 */         List<String> rollbackusers = arg_users;
/* 172 */         int c = 0;
/* 173 */         for (String ruser : rollbackusers) {
/* 174 */           List<Player> players = CoreProtect.getInstance().getServer().matchPlayer(ruser);
/* 175 */           for (Player p : players) {
/* 176 */             if (p.getName().equalsIgnoreCase(ruser)) {
/* 177 */               rollbackusers.set(c, p.getName());
/*     */             }
/*     */           }
/* 180 */           c++;
/*     */         }
/*     */         
/* 183 */         int wid = 0;
/* 184 */         int x = 0;
/* 185 */         int y = 0;
/* 186 */         int z = 0;
/* 187 */         if (rollbackusers.contains("#container")) {
/* 188 */           boolean valid = false;
/* 189 */           if (Config.lookup_type.get(player.getName()) != null) {
/* 190 */             int lookup_type = ((Integer)Config.lookup_type.get(player.getName())).intValue();
/* 191 */             if (lookup_type == 1) {
/* 192 */               valid = true;
/*     */             }
/* 194 */             else if ((lookup_type == 5) && 
/* 195 */               (((List)Config.lookup_ulist.get(player.getName())).contains("#container"))) {
/* 196 */               valid = true;
/*     */             }
/*     */           }
/*     */           
/* 200 */           if (valid == true) {
/* 201 */             if (preview > 0) {
/* 202 */               player.sendMessage("§3CoreProtect §f- You can't preview container transactions.");
/* 203 */               return;
/*     */             }
/*     */             
/* 206 */             String lcommand = (String)Config.lookup_command.get(player.getName());
/* 207 */             String[] data = lcommand.split("\\.");
/* 208 */             x = Integer.parseInt(data[0]);
/* 209 */             y = Integer.parseInt(data[1]);
/* 210 */             z = Integer.parseInt(data[2]);
/* 211 */             wid = Integer.parseInt(data[3]);
/* 212 */             arg_action.add(Integer.valueOf(5));
/* 213 */             arg_radius = null;
/* 214 */             arg_wid = 0;
/* 215 */             lo = new Location(CoreProtect.getInstance().getServer().getWorld(Functions.getWorldName(wid)), x, y, z);
/* 216 */             Block block = lo.getBlock();
/* 217 */             if ((block.getState() instanceof Chest)) {
/* 218 */               BlockFace[] block_sides = { BlockFace.NORTH, BlockFace.EAST, BlockFace.SOUTH, BlockFace.WEST };
/* 219 */               for (BlockFace face : block_sides) {
/* 220 */                 if ((block.getRelative(face, 1).getState() instanceof Chest)) {
/* 221 */                   Block relative = block.getRelative(face, 1);
/* 222 */                   int x2 = relative.getX();
/* 223 */                   int z2 = relative.getZ();
/* 224 */                   double new_x = (x + x2) / 2.0D;
/* 225 */                   double new_z = (z + z2) / 2.0D;
/* 226 */                   lo.setX(new_x);
/* 227 */                   lo.setZ(new_z);
/* 228 */                   break;
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           else
/*     */           {
/* 235 */             player.sendMessage("§3CoreProtect §f- Please inspect a valid container first.");
/* 236 */             return;
/*     */           }
/*     */         }
/*     */         
/* 240 */         final List<String> rollbackusers2 = rollbackusers;
/* 241 */         if (rbseconds > 0) {
/* 242 */           int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 243 */           int seconds = unixtimestamp - rbseconds;
/* 244 */           if (force_seconds > 0) {
/* 245 */             seconds = force_seconds;
/*     */           }
/* 247 */           final int stime = seconds;
/* 248 */           final Integer[] radius = arg_radius;
/*     */           try {
/* 250 */             final CommandSender player2 = player;
/* 251 */             final int noisy = arg_noisy;
/* 252 */             final String rtime = ts;
/* 253 */             final List<String> uuid_list = arg_uuids;
/* 254 */             final List<Object> blist = arg_blocks;
/* 255 */             final List<Object> elist = arg_exclude;
/* 256 */             final List<String> euserlist = arg_exclude_users;
/* 257 */             final Location location_final = lo;
/* 258 */             final int final_arg_wid = arg_wid;
/* 259 */             final List<Integer> final_arg_action = arg_action;
/* 260 */             final String[] final_args = args;
/* 261 */             final int final_preview = preview;
/*     */             
/* 263 */             Config.active_rollbacks.put(player.getName(), Boolean.valueOf(true));
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
/* 377 */             Runnable runnable = new Runnable()
/*     */             {
/*     */               public void run()
/*     */               {
/*     */                 try
/*     */                 {
/* 269 */                   int action = this.val$final_action;
/* 270 */                   Location location = location_final;
/* 271 */                   Connection connection = Database.getConnection(false);
/* 272 */                   if (connection != null) {
/* 273 */                     Statement statement = connection.createStatement();
/* 274 */                     String baduser = "";
/* 275 */                     boolean exists = false;
/* 276 */                     for (String check : rollbackusers2) {
/* 277 */                       if ((!check.equals("#global")) && (!check.equals("#container"))) {
/* 278 */                         exists = Lookup.playerExists(connection, check);
/* 279 */                         if (!exists) {
/* 280 */                           baduser = check;
/* 281 */                           break;
/*     */                         }
/*     */                       }
/*     */                       else {
/* 285 */                         exists = true;
/*     */                       }
/*     */                     }
/* 288 */                     if (exists == true) {
/* 289 */                       for (String check : euserlist) {
/* 290 */                         if (!check.equals("#global")) {
/* 291 */                           exists = Lookup.playerExists(connection, check);
/* 292 */                           if (!exists) {
/* 293 */                             baduser = check;
/* 294 */                             break;
/*     */                           }
/*     */                         }
/*     */                         else {
/* 298 */                           baduser = "#global";
/* 299 */                           exists = false;
/*     */                         }
/*     */                       }
/*     */                     }
/* 303 */                     if (exists == true) {
/* 304 */                       boolean restrict_world = false;
/* 305 */                       if (radius != null) {
/* 306 */                         restrict_world = true;
/*     */                       }
/* 308 */                       if (location == null) {
/* 309 */                         restrict_world = false;
/*     */                       }
/* 311 */                       if (final_arg_wid > 0) {
/* 312 */                         restrict_world = true;
/* 313 */                         location = new Location(CoreProtect.getInstance().getServer().getWorld(Functions.getWorldName(final_arg_wid)), 0.0D, 0.0D, 0.0D);
/*     */                       }
/* 315 */                       boolean verbose = false;
/* 316 */                       if (noisy == 1) {
/* 317 */                         verbose = true;
/*     */                       }
/*     */                       
/* 320 */                       String users = "";
/* 321 */                       for (String value : rollbackusers2) {
/* 322 */                         if (users.length() == 0) {
/* 323 */                           users = "" + value + "";
/*     */                         }
/*     */                         else {
/* 326 */                           users = users + ", " + value;
/*     */                         }
/*     */                       }
/* 329 */                       if ((users.equals("#global")) && (restrict_world))
/*     */                       {
/* 331 */                         users = "#" + location.getWorld().getName();
/*     */                       }
/* 333 */                       if (final_preview == 2) {
/* 334 */                         player2.sendMessage("§3CoreProtect §f- Cancelling preview...");
/*     */                       }
/* 336 */                       else if (final_preview == 1) {
/* 337 */                         player2.sendMessage("§3CoreProtect §f- Preview started on \"" + users + "\".");
/*     */                       }
/* 339 */                       else if (action == 0) {
/* 340 */                         player2.sendMessage("§3CoreProtect §f- Rollback started on \"" + users + "\".");
/*     */                       }
/*     */                       else {
/* 343 */                         player2.sendMessage("§3CoreProtect §f- Restore started on \"" + users + "\".");
/*     */                       }
/*     */                       
/* 346 */                       if (final_arg_action.contains(Integer.valueOf(5))) {
/* 347 */                         Lookup.performContainerRollbackRestore(statement, player2, uuid_list, rollbackusers2, rtime, blist, elist, euserlist, final_arg_action, location, radius, stime, restrict_world, false, verbose, action);
/*     */                       }
/*     */                       else {
/* 350 */                         Lookup.performRollbackRestore(statement, player2, uuid_list, rollbackusers2, rtime, blist, elist, euserlist, final_arg_action, location, radius, stime, restrict_world, false, verbose, action, final_preview);
/* 351 */                         if (final_preview < 2) {
/* 352 */                           List<Object[]> list = new ArrayList();
/* 353 */                           list.add(new Object[] { Integer.valueOf(stime) });
/* 354 */                           list.add(final_args);
/* 355 */                           Config.last_rollback.put(player2.getName(), list);
/*     */                         }
/*     */                       }
/*     */                     }
/*     */                     else {
/* 360 */                       player2.sendMessage("§3CoreProtect §f- User \"" + baduser + "\" not found.");
/*     */                     }
/* 362 */                     statement.close();
/* 363 */                     connection.close();
/*     */                   }
/*     */                   else {
/* 366 */                     player2.sendMessage("§3CoreProtect §f- Database busy. Please try again later.");
/*     */                   }
/*     */                 }
/*     */                 catch (Exception e) {
/* 370 */                   e.printStackTrace();
/*     */                 }
/* 372 */                 if (Config.active_rollbacks.get(player2.getName()) != null) {
/* 373 */                   Config.active_rollbacks.remove(player2.getName());
/*     */                 }
/*     */                 
/*     */               }
/* 377 */             };
/* 378 */             Thread thread = new Thread(runnable);
/* 379 */             thread.start();
/*     */           }
/*     */           catch (Exception e) {
/* 382 */             e.printStackTrace();
/*     */           }
/*     */           
/*     */         }
/* 386 */         else if (final_action == 0) {
/* 387 */           player.sendMessage("§3CoreProtect §f- Please specify the amount of time to rollback.");
/*     */         }
/*     */         else {
/* 390 */           player.sendMessage("§3CoreProtect §f- Please specify the amount of time to restore.");
/*     */         }
/*     */         
/*     */ 
/*     */       }
/* 395 */       else if (final_action == 0) {
/* 396 */         player.sendMessage("§3CoreProtect §f- You did not specify a rollback radius.");
/*     */       }
/*     */       else {
/* 399 */         player.sendMessage("§3CoreProtect §f- You did not specify a restore radius.");
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 404 */       player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\command\RollbackRestoreCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */