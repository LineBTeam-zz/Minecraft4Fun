/*     */ package net.coreprotect.command;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Statement;
/*     */ import java.text.DecimalFormat;
/*     */ import java.text.NumberFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.coreprotect.CoreProtect;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.database.Database;
/*     */ import net.coreprotect.database.Lookup;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class LookupCommand
/*     */ {
/*     */   protected static void runCommand(CommandSender player, boolean permission, String[] args)
/*     */   {
/*  26 */     int resultc = args.length;
/*  27 */     Location lo = CommandHandler.parseLocation(player, args);
/*     */     
/*  29 */     List<String> arg_users = CommandHandler.parseUsers(args);
/*  30 */     Integer[] arg_radius = CommandHandler.parseRadius(args, player, lo);
/*  31 */     int arg_noisy = CommandHandler.parseNoisy(args);
/*  32 */     List<String> arg_exclude_users = CommandHandler.parseExcludedUsers(player, args);
/*  33 */     List<Object> arg_exclude = CommandHandler.parseExcluded(player, args);
/*  34 */     List<Object> arg_blocks = CommandHandler.parseRestricted(player, args);
/*  35 */     String ts = CommandHandler.parseTimeString(args);
/*  36 */     int rbseconds = CommandHandler.parseTime(args);
/*  37 */     int arg_wid = CommandHandler.parseWorld(args);
/*  38 */     List<Integer> arg_action = CommandHandler.parseAction(args);
/*  39 */     boolean count = CommandHandler.parseCount(args);
/*  40 */     boolean worldedit = CommandHandler.parseWorldEdit(args);
/*     */     
/*  42 */     boolean page_lookup = false;
/*     */     
/*  44 */     if ((arg_blocks == null) || (arg_exclude == null) || (arg_exclude_users == null)) {
/*  45 */       return;
/*     */     }
/*     */     
/*  48 */     int arg_excluded = arg_exclude.size();
/*  49 */     int arg_restricted = arg_blocks.size();
/*     */     
/*  51 */     if ((arg_action.size() == 0) && (arg_blocks.size() > 0)) {
/*  52 */       for (Object arg_block : arg_blocks) {
/*  53 */         if ((arg_block instanceof Material)) {
/*  54 */           arg_action.add(Integer.valueOf(0));
/*  55 */           arg_action.add(Integer.valueOf(1));
/*     */         }
/*  57 */         else if ((arg_block instanceof org.bukkit.entity.EntityType)) {
/*  58 */           arg_action.add(Integer.valueOf(3));
/*     */         }
/*     */       }
/*     */     }
/*  62 */     if (arg_wid == -1) {
/*  63 */       String world_name = CommandHandler.parseWorldName(args);
/*  64 */       player.sendMessage("§3CoreProtect §f- World \"" + world_name + "\" not found.");
/*  65 */       return;
/*     */     }
/*     */     
/*  68 */     int type = 0;
/*  69 */     if (Config.lookup_type.get(player.getName()) != null) {
/*  70 */       type = ((Integer)Config.lookup_type.get(player.getName())).intValue();
/*     */     }
/*  72 */     if ((type == 0) && (resultc > 1)) {
/*  73 */       type = 4;
/*     */     }
/*  75 */     else if (resultc > 2) {
/*  76 */       type = 4;
/*     */     }
/*  78 */     else if (resultc > 1) {
/*  79 */       page_lookup = true;
/*  80 */       String dat = args[1];
/*  81 */       if (dat.contains(":")) {
/*  82 */         String[] split = dat.split(":");
/*  83 */         String check1 = split[0].replaceAll("[^a-zA-Z_]", "");
/*  84 */         String check2 = "";
/*  85 */         if (split.length > 1) {
/*  86 */           check2 = split[1].replaceAll("[^a-zA-Z_]", "");
/*     */         }
/*  88 */         if ((check1.length() > 0) || (check2.length() > 0)) {
/*  89 */           type = 4;
/*  90 */           page_lookup = false;
/*     */         }
/*     */       }
/*     */       else {
/*  94 */         String check1 = dat.replaceAll("[^a-zA-Z_]", "");
/*  95 */         if (check1.length() > 0) {
/*  96 */           type = 4;
/*  97 */           page_lookup = false;
/*     */         }
/*     */       }
/*     */     }
/* 101 */     if ((arg_action.contains(Integer.valueOf(6))) || (arg_action.contains(Integer.valueOf(7))) || (arg_action.contains(Integer.valueOf(8))) || (arg_action.contains(Integer.valueOf(9)))) {
/* 102 */       page_lookup = true;
/*     */     }
/*     */     
/* 105 */     if ((!permission) && (
/* 106 */       (!page_lookup) || (!player.hasPermission("coreprotect.inspect")))) {
/* 107 */       player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/* 108 */       return;
/*     */     }
/*     */     
/* 111 */     if (Config.converter_running == true) {
/* 112 */       player.sendMessage("§3CoreProtect §f- Upgrade in progress. Please try again later.");
/* 113 */       return;
/*     */     }
/* 115 */     if (Config.purge_running == true) {
/* 116 */       player.sendMessage("§3CoreProtect §f- Purge in progress. Please try again later.");
/* 117 */       return;
/*     */     }
/* 119 */     if (resultc < 2) {
/* 120 */       player.sendMessage("§3CoreProtect §f- Please use \"/co l <params>\".");
/* 121 */       return;
/*     */     }
/* 123 */     if (arg_action.contains(Integer.valueOf(-1))) {
/* 124 */       player.sendMessage("§3CoreProtect §f- That is not a valid action.");
/* 125 */       return;
/*     */     }
/* 127 */     if ((worldedit == true) && (arg_radius == null)) {
/* 128 */       player.sendMessage("§3CoreProtect §f- WorldEdit selection not found.");
/* 129 */       return;
/*     */     }
/* 131 */     if ((arg_radius != null) && (arg_radius[0].intValue() == -1)) {
/* 132 */       player.sendMessage("§3CoreProtect §f- Please enter a valid radius.");
/* 133 */       return;
/*     */     }
/* 135 */     boolean allPermission = false;
/* 136 */     if (player.isOp()) {
/* 137 */       allPermission = true;
/*     */     }
/* 139 */     if (!allPermission) {
/* 140 */       if ((!page_lookup) && ((arg_action.size() == 0) || (arg_action.contains(Integer.valueOf(0))) || (arg_action.contains(Integer.valueOf(1)))) && (!player.hasPermission("coreprotect.lookup.block"))) {
/* 141 */         player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/* 142 */         return;
/*     */       }
/* 144 */       if ((arg_action.contains(Integer.valueOf(2))) && (!player.hasPermission("coreprotect.lookup.click"))) {
/* 145 */         player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/* 146 */         return;
/*     */       }
/* 148 */       if ((arg_action.contains(Integer.valueOf(3))) && (!player.hasPermission("coreprotect.lookup.kill"))) {
/* 149 */         player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/* 150 */         return;
/*     */       }
/* 152 */       if ((arg_action.contains(Integer.valueOf(4))) && (!player.hasPermission("coreprotect.lookup.container"))) {
/* 153 */         player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/* 154 */         return;
/*     */       }
/* 156 */       if ((arg_action.contains(Integer.valueOf(6))) && (!player.hasPermission("coreprotect.lookup.chat"))) {
/* 157 */         player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/* 158 */         return;
/*     */       }
/* 160 */       if ((arg_action.contains(Integer.valueOf(7))) && (!player.hasPermission("coreprotect.lookup.command"))) {
/* 161 */         player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/* 162 */         return;
/*     */       }
/* 164 */       if ((arg_action.contains(Integer.valueOf(8))) && (!player.hasPermission("coreprotect.lookup.session"))) {
/* 165 */         player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/* 166 */         return;
/*     */       }
/* 168 */       if ((arg_action.contains(Integer.valueOf(9))) && (!player.hasPermission("coreprotect.lookup.username"))) {
/* 169 */         player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/* 170 */         return;
/*     */       }
/*     */     }
/* 173 */     if ((arg_action.contains(Integer.valueOf(6))) || (arg_action.contains(Integer.valueOf(7))) || (arg_action.contains(Integer.valueOf(8))) || (arg_action.contains(Integer.valueOf(9)))) {
/* 174 */       if ((!arg_action.contains(Integer.valueOf(8))) && ((arg_radius != null) || (arg_wid > 0) || (worldedit == true))) {
/* 175 */         player.sendMessage("§3CoreProtect §f- \"r:\" can't be used with that action.");
/* 176 */         return;
/*     */       }
/* 178 */       if (arg_blocks.size() > 0) {
/* 179 */         player.sendMessage("§3CoreProtect §f- \"b:\" can't be used with that action.");
/* 180 */         return;
/*     */       }
/* 182 */       if (arg_exclude.size() > 0) {
/* 183 */         player.sendMessage("§3CoreProtect §f- \"e:\" can't be used with that action.");
/* 184 */         return;
/*     */       }
/*     */     }
/* 187 */     if (resultc > 2) {
/* 188 */       String bid = args[1];
/* 189 */       if ((bid.equalsIgnoreCase("type")) || (bid.equalsIgnoreCase("id"))) {
/* 190 */         type = 6;
/*     */       }
/*     */     }
/* 193 */     if ((rbseconds <= 0) && (!page_lookup) && (type == 4) && ((arg_blocks.size() > 0) || (arg_users.size() > 0))) {
/* 194 */       player.sendMessage("§3CoreProtect §f- Please specify the amount of time to lookup.");
/* 195 */       return;
/*     */     }
/*     */     
/* 198 */     if (type == 1) {
/* 199 */       boolean default_re = true;
/* 200 */       int p = 0;
/* 201 */       int re = 7;
/* 202 */       if (resultc > 1) {
/* 203 */         String pages = args[1];
/* 204 */         if (pages.contains(":")) {
/* 205 */           String[] data = pages.split(":");
/* 206 */           pages = data[0];
/* 207 */           String results = "";
/* 208 */           if (data.length > 1) {
/* 209 */             results = data[1];
/*     */           }
/* 211 */           results = results.replaceAll("[^0-9]", "");
/* 212 */           if (results.length() > 0) {
/* 213 */             int r = Integer.parseInt(results);
/* 214 */             if (r > 0) {
/* 215 */               re = r;
/* 216 */               default_re = false;
/*     */             }
/*     */           }
/*     */         }
/* 220 */         pages = pages.replaceAll("[^0-9]", "");
/* 221 */         if (pages.length() > 0) {
/* 222 */           int pa = Integer.parseInt(pages);
/* 223 */           if (pa > 0) {
/* 224 */             p = pa;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 229 */       if (p <= 0) {
/* 230 */         p = 1;
/*     */       }
/* 232 */       String lcommand = (String)Config.lookup_command.get(player.getName());
/* 233 */       String[] data = lcommand.split("\\.");
/* 234 */       int x = Integer.parseInt(data[0]);
/* 235 */       int y = Integer.parseInt(data[1]);
/* 236 */       int z = Integer.parseInt(data[2]);
/* 237 */       int wid = Integer.parseInt(data[3]);
/* 238 */       int x2 = Integer.parseInt(data[4]);
/* 239 */       int y2 = Integer.parseInt(data[5]);
/* 240 */       int z2 = Integer.parseInt(data[6]);
/* 241 */       if (default_re == true) {
/* 242 */         re = Integer.parseInt(data[7]);
/*     */       }
/*     */       
/* 245 */       String bc = x + "." + y + "." + z + "." + wid + "." + x2 + "." + y2 + "." + z2 + "." + re;
/* 246 */       Config.lookup_command.put(player.getName(), bc);
/*     */       
/* 248 */       String world = Functions.getWorldName(wid);
/* 249 */       double dx = 0.5D * (x + x2);
/* 250 */       double dy = 0.5D * (y + y2);
/* 251 */       double dz = 0.5D * (z + z2);
/* 252 */       Location location = new Location(CoreProtect.getInstance().getServer().getWorld(world), dx, dy, dz);
/* 253 */       final CommandSender player2 = player;
/* 254 */       final int p2 = p;
/* 255 */       final int final_limit = re;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 285 */       Runnable runnable = new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*     */           try
/*     */           {
/* 261 */             Connection connection = Database.getConnection(false);
/* 262 */             if (connection != null) {
/* 263 */               Statement statement = connection.createStatement();
/* 264 */               String blockdata = Lookup.chest_transactions(statement, this.val$location, player2.getName(), p2, final_limit);
/* 265 */               if (blockdata.contains("\n")) {
/* 266 */                 for (String b : blockdata.split("\n")) {
/* 267 */                   player2.sendMessage(b);
/*     */                 }
/*     */                 
/*     */               } else {
/* 271 */                 player2.sendMessage(blockdata);
/*     */               }
/* 273 */               statement.close();
/* 274 */               connection.close();
/*     */             }
/*     */             else {
/* 277 */               player2.sendMessage("§3CoreProtect §f- Database busy. Please try again later.");
/*     */             }
/*     */           }
/*     */           catch (Exception e) {
/* 281 */             e.printStackTrace();
/*     */           }
/*     */           
/*     */         }
/* 285 */       };
/* 286 */       Thread thread = new Thread(runnable);
/* 287 */       thread.start();
/*     */     }
/* 289 */     else if ((type == 2) || (type == 3) || (type == 7)) {
/* 290 */       boolean default_re = true;
/* 291 */       int page = 1;
/* 292 */       int re = 7;
/* 293 */       if (resultc > 1) {
/* 294 */         String pages = args[1];
/* 295 */         if (pages.contains(":")) {
/* 296 */           String[] data = pages.split(":");
/* 297 */           pages = data[0];
/* 298 */           String results = "";
/* 299 */           if (data.length > 1) {
/* 300 */             results = data[1];
/*     */           }
/* 302 */           results = results.replaceAll("[^0-9]", "");
/* 303 */           if (results.length() > 0) {
/* 304 */             int r = Integer.parseInt(results);
/* 305 */             if (r > 0) {
/* 306 */               re = r;
/* 307 */               default_re = false;
/*     */             }
/*     */           }
/*     */         }
/* 311 */         pages = pages.replaceAll("[^0-9]", "");
/* 312 */         if (pages.length() > 0) {
/* 313 */           int p = Integer.parseInt(pages);
/* 314 */           if (p > 0) {
/* 315 */             page = p;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 321 */       String lcommand = (String)Config.lookup_command.get(player.getName());
/* 322 */       String[] data = lcommand.split("\\.");
/* 323 */       int x = Integer.parseInt(data[0]);
/* 324 */       int y = Integer.parseInt(data[1]);
/* 325 */       int z = Integer.parseInt(data[2]);
/* 326 */       int wid = Integer.parseInt(data[3]);
/* 327 */       int lookup_type = Integer.parseInt(data[4]);
/* 328 */       if (default_re == true) {
/* 329 */         re = Integer.parseInt(data[5]);
/*     */       }
/*     */       
/* 332 */       String bc = x + "." + y + "." + z + "." + wid + "." + lookup_type + "." + re;
/* 333 */       Config.lookup_command.put(player.getName(), bc);
/*     */       
/* 335 */       String world = Functions.getWorldName(wid);
/* 336 */       final org.bukkit.block.Block fblock = CoreProtect.getInstance().getServer().getWorld(world).getBlockAt(x, y, z);
/* 337 */       final CommandSender player2 = player;
/* 338 */       final int p2 = page;
/* 339 */       final int final_limit = re;
/* 340 */       int t = type;
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 375 */       Runnable runnable = new Runnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*     */           try
/*     */           {
/* 345 */             Connection connection = Database.getConnection(false);
/* 346 */             if (connection != null) {
/* 347 */               Statement statement = connection.createStatement();
/* 348 */               String blockdata = null;
/* 349 */               if (this.val$t == 7) {
/* 350 */                 blockdata = Lookup.interaction_lookup(statement, fblock, player2.getName(), 0, p2, final_limit);
/*     */               }
/*     */               else {
/* 353 */                 blockdata = Lookup.block_lookup(statement, fblock, player2.getName(), 0, p2, final_limit);
/*     */               }
/* 355 */               if (blockdata.contains("\n")) {
/* 356 */                 for (String b : blockdata.split("\n")) {
/* 357 */                   player2.sendMessage(b);
/*     */                 }
/*     */                 
/* 360 */               } else if (blockdata.length() > 0) {
/* 361 */                 player2.sendMessage(blockdata);
/*     */               }
/* 363 */               statement.close();
/* 364 */               connection.close();
/*     */             }
/*     */             else {
/* 367 */               player2.sendMessage("§3CoreProtect §f- Database busy. Please try again later.");
/*     */             }
/*     */           }
/*     */           catch (Exception e) {
/* 371 */             e.printStackTrace();
/*     */           }
/*     */           
/*     */         }
/* 375 */       };
/* 376 */       Thread thread = new Thread(runnable);
/* 377 */       thread.start();
/*     */     }
/* 379 */     else if ((type == 4) || (type == 5)) {
/* 380 */       boolean default_re = true;
/* 381 */       int pa = 1;
/* 382 */       int re = 4;
/* 383 */       if ((arg_action.contains(Integer.valueOf(6))) || (arg_action.contains(Integer.valueOf(7))) || (arg_action.contains(Integer.valueOf(9)))) {
/* 384 */         re = 7;
/*     */       }
/* 386 */       if ((type == 5) && 
/* 387 */         (resultc > 1)) {
/* 388 */         String pages = args[1];
/* 389 */         if (pages.contains(":")) {
/* 390 */           String[] data = pages.split(":");
/* 391 */           pages = data[0];
/* 392 */           String results = "";
/* 393 */           if (data.length > 1) {
/* 394 */             results = data[1];
/*     */           }
/* 396 */           results = results.replaceAll("[^0-9]", "");
/* 397 */           if (results.length() > 0) {
/* 398 */             int r = Integer.parseInt(results);
/* 399 */             if (r > 0) {
/* 400 */               re = r;
/* 401 */               default_re = false;
/*     */             }
/*     */           }
/*     */         }
/* 405 */         pages = pages.replaceAll("[^0-9]", "");
/* 406 */         if (pages.length() > 0) {
/* 407 */           int p = Integer.parseInt(pages);
/* 408 */           if (p > 0) {
/* 409 */             pa = p;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 415 */       int g = 1;
/* 416 */       if ((arg_users.contains("#global")) && 
/* 417 */         (arg_radius == null)) {
/* 418 */         g = 0;
/*     */       }
/*     */       
/*     */ 
/* 422 */       if ((g == 1) && ((page_lookup == true) || (arg_blocks.size() > 0) || (arg_users.size() > 0) || ((arg_users.size() == 0) && (arg_radius != null)))) {
/* 423 */         int max_radius = ((Integer)Config.config.get("max-radius")).intValue();
/* 424 */         if (arg_radius != null) {
/* 425 */           int radius_value = arg_radius[0].intValue();
/* 426 */           if ((radius_value > max_radius) && (max_radius > 0)) {
/* 427 */             player.sendMessage("§3CoreProtect §f- The maximum lookup radius is " + max_radius + ".");
/* 428 */             player.sendMessage("§3CoreProtect §f- Don't specify a radius to do a global lookup.");
/* 429 */             return;
/*     */           }
/*     */         }
/*     */         
/* 433 */         if (arg_users.size() == 0) {
/* 434 */           arg_users.add("#global");
/*     */         }
/* 436 */         List<String> rollbackusers = arg_users;
/* 437 */         int c = 0;
/* 438 */         for (String ruser : rollbackusers) {
/* 439 */           List<Player> players = CoreProtect.getInstance().getServer().matchPlayer(ruser);
/* 440 */           for (Player p : players) {
/* 441 */             if (p.getName().equalsIgnoreCase(ruser)) {
/* 442 */               rollbackusers.set(c, p.getName());
/*     */             }
/*     */           }
/* 445 */           c++;
/*     */         }
/*     */         
/* 448 */         int cs = -1;
/* 449 */         int x = 0;
/* 450 */         int y = 0;
/* 451 */         int z = 0;
/* 452 */         int wid = 0;
/*     */         
/* 454 */         if (type == 5) {
/* 455 */           String lcommand = (String)Config.lookup_command.get(player.getName());
/* 456 */           String[] data = lcommand.split("\\.");
/* 457 */           x = Integer.parseInt(data[0]);
/* 458 */           y = Integer.parseInt(data[1]);
/* 459 */           z = Integer.parseInt(data[2]);
/* 460 */           wid = Integer.parseInt(data[3]);
/* 461 */           cs = Integer.parseInt(data[4]);
/*     */           
/* 463 */           arg_noisy = Integer.parseInt(data[5]);
/* 464 */           arg_excluded = Integer.parseInt(data[6]);
/* 465 */           arg_restricted = Integer.parseInt(data[7]);
/* 466 */           arg_wid = Integer.parseInt(data[8]);
/* 467 */           if (default_re == true) {
/* 468 */             re = Integer.parseInt(data[9]);
/*     */           }
/*     */           
/* 471 */           rollbackusers = (List)Config.lookup_ulist.get(player.getName());
/* 472 */           arg_blocks = (List)Config.lookup_blist.get(player.getName());
/* 473 */           arg_exclude = (List)Config.lookup_elist.get(player.getName());
/* 474 */           arg_exclude_users = (List)Config.lookup_e_userlist.get(player.getName());
/* 475 */           arg_action = (List)Config.lookup_alist.get(player.getName());
/* 476 */           arg_radius = (Integer[])Config.lookup_radius.get(player.getName());
/* 477 */           ts = (String)Config.lookup_time.get(player.getName());
/* 478 */           rbseconds = 1;
/*     */         }
/*     */         else {
/* 481 */           if (lo != null) {
/* 482 */             x = lo.getBlockX();
/* 483 */             z = lo.getBlockZ();
/* 484 */             wid = Functions.getWorldId(lo.getWorld().getName());
/*     */           }
/*     */           
/* 487 */           if (rollbackusers.contains("#container")) {
/* 488 */             if ((arg_action.contains(Integer.valueOf(6))) || (arg_action.contains(Integer.valueOf(7))) || (arg_action.contains(Integer.valueOf(8))) || (arg_action.contains(Integer.valueOf(9)))) {
/* 489 */               player.sendMessage("§3CoreProtect §f- \"#container\" is an invalid username.");
/* 490 */               return;
/*     */             }
/*     */             
/* 493 */             boolean valid = false;
/* 494 */             if (Config.lookup_type.get(player.getName()) != null) {
/* 495 */               int lookup_type = ((Integer)Config.lookup_type.get(player.getName())).intValue();
/* 496 */               if (lookup_type == 1) {
/* 497 */                 valid = true;
/*     */               }
/* 499 */               else if ((lookup_type == 5) && 
/* 500 */                 (((List)Config.lookup_ulist.get(player.getName())).contains("#container"))) {
/* 501 */                 valid = true;
/*     */               }
/*     */             }
/*     */             
/*     */ 
/* 506 */             if (valid == true) {
/* 507 */               if ((!player.hasPermission("coreprotect.lookup.container")) && (!allPermission)) {
/* 508 */                 player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/* 509 */                 return;
/*     */               }
/* 511 */               String lcommand = (String)Config.lookup_command.get(player.getName());
/* 512 */               String[] data = lcommand.split("\\.");
/* 513 */               x = Integer.parseInt(data[0]);
/* 514 */               y = Integer.parseInt(data[1]);
/* 515 */               z = Integer.parseInt(data[2]);
/* 516 */               wid = Integer.parseInt(data[3]);
/* 517 */               arg_action.add(Integer.valueOf(5));
/* 518 */               arg_radius = null;
/* 519 */               arg_wid = 0;
/*     */             }
/*     */             else {
/* 522 */               player.sendMessage("§3CoreProtect §f- Please inspect a valid container first.");
/* 523 */               return;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/* 528 */         final List<String> rollbackusers2 = rollbackusers;
/* 529 */         int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 530 */         if (cs == -1) {
/* 531 */           if (rbseconds <= 0) {
/* 532 */             cs = 0;
/*     */           }
/*     */           else {
/* 535 */             cs = unixtimestamp - rbseconds;
/*     */           }
/*     */         }
/* 538 */         final int stime = cs;
/* 539 */         final Integer[] radius = arg_radius;
/*     */         try
/*     */         {
/* 542 */           player.sendMessage("§3CoreProtect §f- Lookup searching. Please wait...");
/* 543 */           final CommandSender player2 = player;
/* 544 */           final int final_x = x;
/* 545 */           final int final_y = y;
/* 546 */           final int final_z = z;
/* 547 */           final int final_wid = wid;
/* 548 */           final int final_arg_wid = arg_wid;
/* 549 */           final int noisy = arg_noisy;
/* 550 */           final String rtime = ts;
/* 551 */           final int excluded = arg_excluded;
/* 552 */           final int restricted = arg_restricted;
/*     */           
/* 554 */           final List<Object> blist = arg_blocks;
/* 555 */           final List<Object> elist = arg_exclude;
/* 556 */           final List<String> euserlist = arg_exclude_users;
/* 557 */           final int page = pa;
/* 558 */           final int display_results = re;
/* 559 */           final int type_lookup = type;
/* 560 */           Location final_location = lo;
/* 561 */           final List<Integer> final_arg_action = arg_action;
/* 562 */           final boolean final_count = count;
/*     */           
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 844 */           Runnable runnable = new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/*     */               try
/*     */               {
/* 568 */                 List<String> uuid_list = new ArrayList();
/*     */                 
/* 570 */                 Location location = this.val$final_location;
/* 571 */                 boolean exists = false;
/* 572 */                 String bc = final_x + "." + final_y + "." + final_z + "." + final_wid + "." + stime + "." + noisy + "." + excluded + "." + restricted + "." + final_arg_wid + "." + display_results;
/* 573 */                 Config.lookup_command.put(player2.getName(), bc);
/* 574 */                 Config.lookup_page.put(player2.getName(), Integer.valueOf(page));
/* 575 */                 Config.lookup_time.put(player2.getName(), rtime);
/* 576 */                 Config.lookup_type.put(player2.getName(), Integer.valueOf(5));
/* 577 */                 Config.lookup_elist.put(player2.getName(), elist);
/* 578 */                 Config.lookup_e_userlist.put(player2.getName(), euserlist);
/* 579 */                 Config.lookup_blist.put(player2.getName(), blist);
/* 580 */                 Config.lookup_ulist.put(player2.getName(), rollbackusers2);
/* 581 */                 Config.lookup_alist.put(player2.getName(), final_arg_action);
/* 582 */                 Config.lookup_radius.put(player2.getName(), radius);
/*     */                 
/* 584 */                 Connection connection = Database.getConnection(false);
/* 585 */                 if (connection != null) {
/* 586 */                   Statement statement = connection.createStatement();
/* 587 */                   String baduser = "";
/* 588 */                   for (String check : rollbackusers2) {
/* 589 */                     if (((!check.equals("#global")) && (!check.equals("#container"))) || (final_arg_action.contains(Integer.valueOf(9)))) {
/* 590 */                       exists = Lookup.playerExists(connection, check);
/* 591 */                       if (!exists) {
/* 592 */                         baduser = check;
/* 593 */                         break;
/*     */                       }
/* 595 */                       if ((final_arg_action.contains(Integer.valueOf(9))) && 
/* 596 */                         (Config.uuid_cache.get(check.toLowerCase()) != null)) {
/* 597 */                         String uuid = (String)Config.uuid_cache.get(check.toLowerCase());
/* 598 */                         uuid_list.add(uuid);
/*     */                       }
/*     */                     }
/*     */                     else
/*     */                     {
/* 603 */                       exists = true;
/*     */                     }
/*     */                   }
/* 606 */                   if (exists == true) {
/* 607 */                     for (String check : euserlist) {
/* 608 */                       if (!check.equals("#global")) {
/* 609 */                         exists = Lookup.playerExists(connection, check);
/* 610 */                         if (!exists) {
/* 611 */                           baduser = check;
/* 612 */                           break;
/*     */                         }
/*     */                       }
/*     */                       else {
/* 616 */                         baduser = "#global";
/* 617 */                         exists = false;
/*     */                       }
/*     */                     }
/*     */                   }
/*     */                   
/* 622 */                   if (exists == true) {
/* 623 */                     List<String> user_list = new ArrayList();
/* 624 */                     if (!final_arg_action.contains(Integer.valueOf(9))) {
/* 625 */                       user_list = rollbackusers2;
/*     */                     }
/*     */                     
/* 628 */                     int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 629 */                     boolean restrict_world = false;
/* 630 */                     if (radius != null) {
/* 631 */                       restrict_world = true;
/*     */                     }
/* 633 */                     if (location == null) {
/* 634 */                       restrict_world = false;
/*     */                     }
/* 636 */                     if (final_arg_wid > 0) {
/* 637 */                       restrict_world = true;
/* 638 */                       location = new Location(CoreProtect.getInstance().getServer().getWorld(Functions.getWorldName(final_arg_wid)), final_x, final_y, final_z);
/*     */                     }
/* 640 */                     else if (location != null) {
/* 641 */                       location = new Location(CoreProtect.getInstance().getServer().getWorld(Functions.getWorldName(final_wid)), final_x, final_y, final_z);
/*     */                     }
/*     */                     
/* 644 */                     int row_max = page * display_results;
/* 645 */                     int page_start = row_max - display_results;
/* 646 */                     int rows = 0;
/* 647 */                     boolean check_rows = true;
/* 648 */                     if ((type_lookup == 5) && (page > 1)) {
/* 649 */                       rows = ((Integer)Config.lookup_rows.get(player2.getName())).intValue();
/* 650 */                       if (page_start < rows) {
/* 651 */                         check_rows = false;
/*     */                       }
/*     */                     }
/*     */                     
/* 655 */                     if (check_rows == true) {
/* 656 */                       rows = Lookup.countLookupRows(statement, player2, uuid_list, user_list, blist, elist, euserlist, final_arg_action, location, radius, stime, restrict_world, true);
/* 657 */                       Config.lookup_rows.put(player2.getName(), Integer.valueOf(rows));
/*     */                     }
/* 659 */                     if (final_count == true) {
/* 660 */                       String row_format = NumberFormat.getInstance().format(rows);
/* 661 */                       player2.sendMessage("§3CoreProtect §f- " + row_format + " row(s) found.");
/*     */                     }
/* 663 */                     else if (page_start < rows) {
/* 664 */                       String arrows = "                      ";
/* 665 */                       if (rows > display_results) {
/* 666 */                         int total_pages = (int)Math.ceil(rows / (display_results + 0.0D));
/*     */                         
/* 668 */                         String page_back = "«";
/* 669 */                         String page_next = "»";
/*     */                         
/* 671 */                         if ((page > 1) && (page < total_pages)) {
/* 672 */                           arrows = page_back + " | " + page_next;
/*     */                         }
/* 674 */                         else if (page > 1) {
/* 675 */                           arrows = "    " + page_back;
/*     */                         }
/*     */                         else {
/* 678 */                           arrows = "    " + page_next;
/*     */                         }
/*     */                       }
/* 681 */                       arrows = "";
/*     */                       
/* 683 */                       List<String[]> lookup_list = Lookup.performPartialLookup(statement, player2, uuid_list, user_list, blist, elist, euserlist, final_arg_action, location, radius, stime, page_start, display_results, restrict_world, true);
/* 684 */                       player2.sendMessage("§f----- §3CoreProtect Lookup Results §f-----" + arrows);
/* 685 */                       if ((final_arg_action.contains(Integer.valueOf(6))) || (final_arg_action.contains(Integer.valueOf(7)))) {
/* 686 */                         for (String[] data : lookup_list) {
/* 687 */                           String time = data[0];
/* 688 */                           String dplayer = data[1];
/* 689 */                           String message = data[2];
/* 690 */                           double time_since = unixtimestamp - Double.parseDouble(time);
/* 691 */                           time_since /= 60.0D;
/* 692 */                           time_since /= 60.0D;
/* 693 */                           String timeago = new DecimalFormat("0.00").format(time_since);
/* 694 */                           player2.sendMessage("§7" + timeago + "/h ago §f- §3" + dplayer + ": §f" + message + "");
/*     */                         }
/*     */                         
/* 697 */                       } else if (final_arg_action.contains(Integer.valueOf(8))) {
/* 698 */                         for (String[] data : lookup_list) {
/* 699 */                           String time = data[0];
/* 700 */                           String dplayer = data[1];
/* 701 */                           int wid = Integer.parseInt(data[2]);
/* 702 */                           int x = Integer.parseInt(data[3]);
/* 703 */                           int y = Integer.parseInt(data[4]);
/* 704 */                           int z = Integer.parseInt(data[5]);
/* 705 */                           int action = Integer.parseInt(data[6]);
/* 706 */                           double time_since = unixtimestamp - Double.parseDouble(time);
/* 707 */                           time_since /= 60.0D;
/* 708 */                           time_since /= 60.0D;
/* 709 */                           String timeago = new DecimalFormat("0.00").format(time_since);
/* 710 */                           String action_string = "in";
/* 711 */                           if (action == 0) {
/* 712 */                             action_string = "out";
/*     */                           }
/* 714 */                           String world = Functions.getWorldName(wid);
/* 715 */                           double time_length = timeago.replaceAll("[^0-9]", "").length() * 1.5D;
/* 716 */                           int padding = (int)(time_length + 12.5D);
/* 717 */                           String left_padding = StringUtils.leftPad("", padding, ' ');
/* 718 */                           player2.sendMessage("§7" + timeago + "/h ago §f- §3" + dplayer + " §flogged §3" + action_string + "§f.");
/* 719 */                           player2.sendMessage("§f" + left_padding + "§7^ §o(x" + x + "/y" + y + "/z" + z + "/" + world + ")");
/*     */                         }
/*     */                         
/* 722 */                       } else if (final_arg_action.contains(Integer.valueOf(9))) {
/* 723 */                         for (String[] data : lookup_list) {
/* 724 */                           String time = data[0];
/* 725 */                           String user = (String)Config.uuid_cache_reversed.get(data[1]);
/* 726 */                           String username = data[2];
/* 727 */                           double time_since = unixtimestamp - Double.parseDouble(time);
/* 728 */                           time_since /= 60.0D;
/* 729 */                           time_since /= 60.0D;
/* 730 */                           String timeago = new DecimalFormat("0.00").format(time_since);
/* 731 */                           player2.sendMessage("§7" + timeago + "/h ago §f- §3" + user + " §flogged in as §3" + username + "§f.");
/*     */                         }
/*     */                         
/*     */                       } else {
/* 735 */                         for (String[] data : lookup_list) {
/* 736 */                           String string_amount = "";
/* 737 */                           int drb = Integer.parseInt(data[8]);
/* 738 */                           String rbd = "";
/* 739 */                           if (drb == 1) {
/* 740 */                             rbd = "§m";
/*     */                           }
/* 742 */                           int amount = 0;
/* 743 */                           String time = data[0];
/* 744 */                           String dplayer = data[1];
/* 745 */                           int x = Integer.parseInt(data[2]);
/* 746 */                           int y = Integer.parseInt(data[3]);
/* 747 */                           int z = Integer.parseInt(data[4]);
/* 748 */                           String dtype = data[5];
/* 749 */                           int ddata = Integer.parseInt(data[6]);
/* 750 */                           int daction = Integer.parseInt(data[7]);
/* 751 */                           int wid = Integer.parseInt(data[9]);
/* 752 */                           String a = "placed";
/* 753 */                           String tag = "§f-";
/* 754 */                           if ((final_arg_action.contains(Integer.valueOf(4))) || (final_arg_action.contains(Integer.valueOf(5)))) {
/* 755 */                             amount = Integer.parseInt(data[10]);
/* 756 */                             string_amount = "x" + amount + " ";
/* 757 */                             a = "added";
/*     */                           }
/* 759 */                           if (daction == 0) {
/* 760 */                             a = "removed";
/*     */ 
/*     */                           }
/* 763 */                           else if (daction == 2) {
/* 764 */                             a = "clicked";
/*     */ 
/*     */                           }
/* 767 */                           else if (daction == 3) {
/* 768 */                             a = "killed";
/*     */                           }
/* 770 */                           double time_since = unixtimestamp - Double.parseDouble(time);
/* 771 */                           time_since /= 60.0D;
/* 772 */                           time_since /= 60.0D;
/* 773 */                           String timeago = new DecimalFormat("0.00").format(time_since);
/*     */                           
/* 775 */                           double time_length = timeago.replaceAll("[^0-9]", "").length() * 1.5D;
/* 776 */                           int padding = (int)(time_length + 12.5D);
/* 777 */                           String left_padding = StringUtils.leftPad("", padding, ' ');
/*     */                           
/* 779 */                           String world = Functions.getWorldName(wid);
/* 780 */                           String dname = "";
/* 781 */                           boolean isPlayer = false;
/* 782 */                           if (daction == 3) {
/* 783 */                             int dTypeInt = Integer.parseInt(dtype);
/* 784 */                             if (dTypeInt == 0) {
/* 785 */                               if (Config.player_id_cache_reversed.get(Integer.valueOf(ddata)) == null) {
/* 786 */                                 Database.loadUserName(connection, ddata);
/*     */                               }
/* 788 */                               dname = (String)Config.player_id_cache_reversed.get(Integer.valueOf(ddata));
/* 789 */                               isPlayer = true;
/*     */                             }
/*     */                             else {
/* 792 */                               dname = Functions.getEntityType(dTypeInt).name();
/*     */                             }
/*     */                           }
/*     */                           else {
/* 796 */                             dname = Functions.getType(Integer.parseInt(dtype)).name().toLowerCase();
/* 797 */                             dname = Functions.nameFilter(dname, ddata);
/*     */                           }
/* 799 */                           if ((dname.length() > 0) && (!isPlayer)) {
/* 800 */                             dname = "minecraft:" + dname.toLowerCase() + "";
/*     */                           }
/*     */                           
/*     */ 
/* 804 */                           if (dname.contains("minecraft:")) {
/* 805 */                             String[] block_name_split = dname.split(":");
/* 806 */                             dname = block_name_split[1];
/*     */                           }
/*     */                           
/*     */ 
/* 810 */                           player2.sendMessage("§7" + timeago + "/h ago " + tag + " §3" + rbd + "" + dplayer + " §f" + rbd + "" + a + " " + string_amount + "§3" + rbd + "" + dname + "§f.");
/* 811 */                           player2.sendMessage("§f" + left_padding + "§7^ §o(x" + x + "/y" + y + "/z" + z + "/" + world + ")");
/*     */                         }
/*     */                       }
/* 814 */                       if (rows > display_results) {
/* 815 */                         int total_pages = (int)Math.ceil(rows / (display_results + 0.0D));
/* 816 */                         if ((final_arg_action.contains(Integer.valueOf(6))) || (final_arg_action.contains(Integer.valueOf(7))) || (final_arg_action.contains(Integer.valueOf(9)))) {
/* 817 */                           player2.sendMessage("-----");
/*     */                         }
/* 819 */                         player2.sendMessage("§fPage " + page + "/" + total_pages + ". View older data by typing \"§3/co l <page>§f\".");
/*     */                       }
/*     */                     }
/* 822 */                     else if (rows > 0) {
/* 823 */                       player2.sendMessage("§3CoreProtect §f- No results found for that page.");
/*     */                     }
/*     */                     else {
/* 826 */                       player2.sendMessage("§3CoreProtect §f- No results found.");
/*     */                     }
/*     */                   }
/*     */                   else {
/* 830 */                     player2.sendMessage("§3CoreProtect §f- User \"" + baduser + "\" not found.");
/*     */                   }
/* 832 */                   statement.close();
/* 833 */                   connection.close();
/*     */                 }
/*     */                 else {
/* 836 */                   player2.sendMessage("§3CoreProtect §f- Database busy. Please try again later.");
/*     */                 }
/*     */               }
/*     */               catch (Exception e) {
/* 840 */                 e.printStackTrace();
/*     */               }
/*     */               
/*     */             }
/* 844 */           };
/* 845 */           Thread thread = new Thread(runnable);
/* 846 */           thread.start();
/*     */         }
/*     */         catch (Exception e) {
/* 849 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 854 */         player.sendMessage("§3CoreProtect §f- Please use \"/co l <params>\".");
/*     */       }
/*     */     }
/* 857 */     else if (type == 6) {
/* 858 */       String bid = args[2];
/* 859 */       bid = bid.replaceAll("[^0-9]", "");
/* 860 */       if (bid.length() > 0) {
/* 861 */         int b = Integer.parseInt(bid);
/* 862 */         if (b > 0) {
/* 863 */           String bname = Functions.block_name_lookup(b);
/* 864 */           if (bname.length() > 0) {
/* 865 */             player.sendMessage("§3CoreProtect §f- The name of block ID #" + b + " is \"" + bname + "\".");
/*     */           }
/*     */           else {
/* 868 */             player.sendMessage("§3CoreProtect §f- No data found for block ID #" + b + ".");
/*     */           }
/*     */         }
/*     */         else {
/* 872 */           player.sendMessage("§3CoreProtect §f- Please use \"/co lookup type <ID>\".");
/*     */         }
/*     */       }
/*     */       else {
/* 876 */         player.sendMessage("§3CoreProtect §f- Please use \"/co lookup type <ID>\".");
/*     */       }
/*     */     }
/*     */     else {
/* 880 */       player.sendMessage("§3CoreProtect §f- Please use \"/co l <params>\".");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\command\LookupCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */