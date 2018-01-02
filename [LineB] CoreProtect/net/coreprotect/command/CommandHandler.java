/*      */ package net.coreprotect.command;
/*      */ 
/*      */ import com.sk89q.worldedit.LocalSession;
/*      */ import com.sk89q.worldedit.Vector;
/*      */ import com.sk89q.worldedit.bukkit.WorldEditPlugin;
/*      */ import com.sk89q.worldedit.regions.Region;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import net.coreprotect.Functions;
/*      */ import net.coreprotect.model.BlockInfo;
/*      */ import net.coreprotect.model.Config;
/*      */ import net.coreprotect.worldedit.WorldEdit;
/*      */ import org.bukkit.Location;
/*      */ import org.bukkit.Material;
/*      */ import org.bukkit.block.Block;
/*      */ import org.bukkit.command.BlockCommandSender;
/*      */ import org.bukkit.command.Command;
/*      */ import org.bukkit.command.CommandExecutor;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.entity.EntityType;
/*      */ import org.bukkit.entity.Player;
/*      */ 
/*      */ public class CommandHandler
/*      */   implements CommandExecutor
/*      */ {
/*      */   private static CommandHandler instance;
/*   29 */   protected static List<Material> natural_blocks = BlockInfo.natural_blocks;
/*      */   
/*      */   public static CommandHandler getInstance() {
/*   32 */     if (instance == null) {
/*   33 */       instance = new CommandHandler();
/*      */     }
/*   35 */     return instance;
/*      */   }
/*      */   
/*      */   protected static List<Integer> parseAction(String[] args_input) {
/*   39 */     String[] args = (String[])args_input.clone();
/*   40 */     List<Integer> result = new ArrayList();
/*   41 */     int count = 0;
/*   42 */     int next = 0;
/*   43 */     for (String i : args) {
/*   44 */       if (count > 0) {
/*   45 */         i = i.trim().toLowerCase();
/*   46 */         i = i.replaceAll("\\\\", "");
/*   47 */         i = i.replaceAll("'", "");
/*      */         
/*   49 */         if ((i.equals("a:")) || (i.equals("action:"))) {
/*   50 */           next = 1;
/*      */         }
/*   52 */         else if ((next == 1) || (i.startsWith("a:")) || (i.startsWith("action:"))) {
/*   53 */           result.clear();
/*   54 */           i = i.replaceAll("action:", "");
/*   55 */           i = i.replaceAll("a:", "");
/*   56 */           if (i.startsWith("#")) {
/*   57 */             i = i.replaceFirst("#", "");
/*      */           }
/*   59 */           if ((i.equals("broke")) || (i.equals("break")) || (i.equals("remove")) || (i.equals("destroy")) || (i.equals("block-break")) || (i.equals("block-remove")) || (i.equals("-block")) || (i.equals("block-"))) {
/*   60 */             result.add(Integer.valueOf(0));
/*      */           }
/*   62 */           else if ((i.equals("placed")) || (i.equals("place")) || (i.equals("block-place")) || (i.equals("+block")) || (i.equals("block+"))) {
/*   63 */             result.add(Integer.valueOf(1));
/*      */           }
/*   65 */           else if ((i.equals("block")) || (i.equals("block-change")) || (i.equals("change"))) {
/*   66 */             result.add(Integer.valueOf(0));
/*   67 */             result.add(Integer.valueOf(1));
/*      */           }
/*   69 */           else if ((i.equals("click")) || (i.equals("clicks")) || (i.equals("interact")) || (i.equals("interaction")) || (i.equals("player-interact")) || (i.equals("player-interaction")) || (i.equals("player-click"))) {
/*   70 */             result.add(Integer.valueOf(2));
/*      */           }
/*   72 */           else if ((i.equals("death")) || (i.equals("deaths")) || (i.equals("entity-death")) || (i.equals("entity-deaths")) || (i.equals("kill")) || (i.equals("kills")) || (i.equals("entity-kill")) || (i.equals("entity-kills"))) {
/*   73 */             result.add(Integer.valueOf(3));
/*      */           }
/*   75 */           else if ((i.equals("container")) || (i.equals("container-change")) || (i.equals("containers")) || (i.equals("chest")) || (i.equals("transaction")) || (i.equals("transactions"))) {
/*   76 */             result.add(Integer.valueOf(4));
/*      */           }
/*   78 */           else if ((i.equals("-container")) || (i.equals("container-")) || (i.equals("remove-container"))) {
/*   79 */             result.add(Integer.valueOf(4));
/*   80 */             result.add(Integer.valueOf(0));
/*      */           }
/*   82 */           else if ((i.equals("+container")) || (i.equals("container+")) || (i.equals("container-add")) || (i.equals("add-container"))) {
/*   83 */             result.add(Integer.valueOf(4));
/*   84 */             result.add(Integer.valueOf(1));
/*      */           }
/*   86 */           else if (i.equals("chat")) {
/*   87 */             result.add(Integer.valueOf(6));
/*      */           }
/*   89 */           else if ((i.equals("command")) || (i.equals("commands"))) {
/*   90 */             result.add(Integer.valueOf(7));
/*      */           }
/*   92 */           else if ((i.equals("login")) || (i.equals("+session")) || (i.equals("session+")) || (i.equals("+connection")) || (i.equals("connection+"))) {
/*   93 */             result.add(Integer.valueOf(8));
/*   94 */             result.add(Integer.valueOf(1));
/*      */           }
/*   96 */           else if ((i.equals("logout")) || (i.equals("-session")) || (i.equals("session-")) || (i.equals("-connection")) || (i.equals("connection-"))) {
/*   97 */             result.add(Integer.valueOf(8));
/*   98 */             result.add(Integer.valueOf(0));
/*      */           }
/*  100 */           else if ((i.equals("session")) || (i.equals("sessions")) || (i.equals("connection")) || (i.equals("connections"))) {
/*  101 */             result.add(Integer.valueOf(8));
/*      */           }
/*  103 */           else if ((i.equals("username")) || (i.equals("usernames")) || (i.equals("user")) || (i.equals("users")) || (i.equals("name")) || (i.equals("names")) || (i.equals("uuid")) || (i.equals("uuids")) || (i.equals("username-change")) || (i.equals("username-changes")) || (i.equals("name-change")) || (i.equals("name-changes"))) {
/*  104 */             result.add(Integer.valueOf(9));
/*      */           }
/*      */           else {
/*  107 */             result.add(Integer.valueOf(-1));
/*      */           }
/*  109 */           next = 0;
/*      */         }
/*      */         else {
/*  112 */           next = 0;
/*      */         }
/*      */       }
/*  115 */       count++;
/*      */     }
/*  117 */     return result;
/*      */   }
/*      */   
/*      */   protected static Location parseCoordinates(Location location, String[] args_input) {
/*  121 */     String[] args = (String[])args_input.clone();
/*  122 */     int count = 0;
/*  123 */     int next = 0;
/*  124 */     for (String i : args) {
/*  125 */       if (count > 0) {
/*  126 */         i = i.trim().toLowerCase();
/*  127 */         i = i.replaceAll("\\\\", "");
/*  128 */         i = i.replaceAll("'", "");
/*      */         
/*  130 */         if ((i.equals("c:")) || (i.equals("coord:")) || (i.equals("coords:")) || (i.equals("cord:")) || (i.equals("cords:")) || (i.equals("coordinate:")) || (i.equals("coordinates:")) || (i.equals("cordinate:")) || (i.equals("cordinates:"))) {
/*  131 */           next = 2;
/*      */         }
/*  133 */         else if ((next == 2) || (i.startsWith("c:")) || (i.startsWith("coord:")) || (i.startsWith("coords:")) || (i.startsWith("cord:")) || (i.startsWith("cords:")) || (i.startsWith("coordinate:")) || (i.startsWith("coordinates:")) || (i.startsWith("cordinate:")) || (i.startsWith("cordinates:"))) {
/*  134 */           i = i.replaceAll("coordinates:", "");
/*  135 */           i = i.replaceAll("coordinate:", "");
/*  136 */           i = i.replaceAll("cordinates:", "");
/*  137 */           i = i.replaceAll("cordinate:", "");
/*  138 */           i = i.replaceAll("coords:", "");
/*  139 */           i = i.replaceAll("coord:", "");
/*  140 */           i = i.replaceAll("cords:", "");
/*  141 */           i = i.replaceAll("cord:", "");
/*  142 */           i = i.replaceAll("c:", "");
/*  143 */           if (i.contains(",")) {
/*  144 */             String[] i2 = i.split(",");
/*  145 */             double x = 0.0D;
/*  146 */             double y = 0.0D;
/*  147 */             double z = 0.0D;
/*  148 */             int c_count = 0;
/*  149 */             for (String coord : i2) {
/*  150 */               coord = coord.replaceAll("[^0-9.\\-]", "");
/*  151 */               if ((coord.length() > 0) && (!coord.equals(".")) && (!coord.equals("-"))) {
/*  152 */                 double parsedCoord = Double.parseDouble(coord);
/*  153 */                 if (c_count == 0) {
/*  154 */                   x = parsedCoord;
/*      */                 }
/*  156 */                 else if (c_count == 1) {
/*  157 */                   z = parsedCoord;
/*      */                 }
/*  159 */                 else if (c_count == 2) {
/*  160 */                   y = z;
/*  161 */                   z = parsedCoord;
/*      */                 }
/*  163 */                 c_count++;
/*      */               }
/*      */             }
/*  166 */             if (y < 0.0D) {
/*  167 */               y = 0.0D;
/*      */             }
/*  169 */             if (y > 255.0D) {
/*  170 */               y = 255.0D;
/*      */             }
/*  172 */             if (c_count > 1) {
/*  173 */               location.setX(x);
/*  174 */               location.setY(y);
/*  175 */               location.setZ(z);
/*      */             }
/*      */           }
/*  178 */           next = 0;
/*      */         }
/*      */         else {
/*  181 */           next = 0;
/*      */         }
/*      */       }
/*  184 */       count++;
/*      */     }
/*  186 */     return location;
/*      */   }
/*      */   
/*      */   protected static boolean parseCount(String[] args_input) {
/*  190 */     String[] args = (String[])args_input.clone();
/*  191 */     boolean result = false;
/*  192 */     int count = 0;
/*  193 */     for (String i : args) {
/*  194 */       if (count > 0) {
/*  195 */         i = i.trim().toLowerCase();
/*  196 */         i = i.replaceAll("\\\\", "");
/*  197 */         i = i.replaceAll("'", "");
/*  198 */         if ((i.equals("#count")) || (i.equals("#sum"))) {
/*  199 */           result = true;
/*      */         }
/*      */       }
/*  202 */       count++;
/*      */     }
/*  204 */     return result;
/*      */   }
/*      */   
/*      */   protected static List<Object> parseExcluded(CommandSender player, String[] args_input) {
/*  208 */     String[] args = (String[])args_input.clone();
/*  209 */     List<Object> excluded = new ArrayList();
/*  210 */     int count = 0;
/*  211 */     int next = 0;
/*  212 */     for (String i : args) {
/*  213 */       if (count > 0) {
/*  214 */         i = i.trim().toLowerCase();
/*  215 */         i = i.replaceAll("\\\\", "");
/*  216 */         i = i.replaceAll("'", "");
/*      */         
/*  218 */         if ((i.equals("e:")) || (i.equals("exclude:"))) {
/*  219 */           next = 5;
/*      */         }
/*  221 */         else if ((next == 5) || (i.startsWith("e:")) || (i.startsWith("exclude:"))) {
/*  222 */           i = i.replaceAll("exclude:", "");
/*  223 */           i = i.replaceAll("e:", "");
/*  224 */           if (i.contains(",")) {
/*  225 */             String[] i2 = i.split(",");
/*  226 */             for (String i3 : i2) {
/*  227 */               if (i3.equals("#natural")) {
/*  228 */                 excluded.addAll(natural_blocks);
/*      */               }
/*      */               else {
/*  231 */                 Material i3_material = Functions.getType(i3);
/*  232 */                 if (i3_material != null) {
/*  233 */                   excluded.add(i3_material);
/*      */                 }
/*      */                 else {
/*  236 */                   EntityType i3_entity = Functions.getEntityType(i3);
/*  237 */                   if (i3_entity != null) {
/*  238 */                     excluded.add(i3_entity);
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*  243 */             if (i.endsWith(",")) {
/*  244 */               next = 5;
/*      */             }
/*      */             else {
/*  247 */               next = 0;
/*      */             }
/*      */           }
/*      */           else {
/*  251 */             if (i.equals("#natural")) {
/*  252 */               excluded.addAll(natural_blocks);
/*      */             }
/*      */             else {
/*  255 */               Material i_material = Functions.getType(i);
/*  256 */               if (i_material != null) {
/*  257 */                 excluded.add(i_material);
/*      */               }
/*      */               else {
/*  260 */                 EntityType i_entity = Functions.getEntityType(i);
/*  261 */                 if (i_entity != null) {
/*  262 */                   excluded.add(i_entity);
/*      */                 }
/*      */               }
/*      */             }
/*  266 */             next = 0;
/*      */           }
/*      */         }
/*      */         else {
/*  270 */           next = 0;
/*      */         }
/*      */       }
/*  273 */       count++;
/*      */     }
/*  275 */     return excluded;
/*      */   }
/*      */   
/*      */   protected static List<String> parseExcludedUsers(CommandSender player, String[] args_input) {
/*  279 */     String[] args = (String[])args_input.clone();
/*  280 */     List<String> excluded = new ArrayList();
/*  281 */     int count = 0;
/*  282 */     int next = 0;
/*  283 */     for (String i : args) {
/*  284 */       if (count > 0) {
/*  285 */         i = i.trim().toLowerCase();
/*  286 */         i = i.replaceAll("\\\\", "");
/*  287 */         i = i.replaceAll("'", "");
/*      */         
/*  289 */         if ((i.equals("e:")) || (i.equals("exclude:"))) {
/*  290 */           next = 5;
/*      */         }
/*  292 */         else if ((next == 5) || (i.startsWith("e:")) || (i.startsWith("exclude:"))) {
/*  293 */           i = i.replaceAll("exclude:", "");
/*  294 */           i = i.replaceAll("e:", "");
/*  295 */           if (i.contains(",")) {
/*  296 */             String[] i2 = i.split(",");
/*  297 */             for (String i3 : i2) {
/*  298 */               boolean isBlock = false;
/*  299 */               if (i3.equals("#natural")) {
/*  300 */                 isBlock = true;
/*      */               }
/*      */               else {
/*  303 */                 Material i3_material = Functions.getType(i3);
/*  304 */                 if (i3_material != null) {
/*  305 */                   isBlock = true;
/*      */                 }
/*      */                 else {
/*  308 */                   EntityType i3_entity = Functions.getEntityType(i3);
/*  309 */                   if (i3_entity != null) {
/*  310 */                     isBlock = true;
/*      */                   }
/*      */                 }
/*      */               }
/*  314 */               if (!isBlock) {
/*  315 */                 excluded.add(i3);
/*      */               }
/*      */             }
/*  318 */             if (i.endsWith(",")) {
/*  319 */               next = 5;
/*      */             }
/*      */             else {
/*  322 */               next = 0;
/*      */             }
/*      */           }
/*      */           else {
/*  326 */             boolean isBlock = false;
/*  327 */             if (i.equals("#natural")) {
/*  328 */               isBlock = true;
/*      */             }
/*      */             else {
/*  331 */               Material i_material = Functions.getType(i);
/*  332 */               if (i_material != null) {
/*  333 */                 isBlock = true;
/*      */               }
/*      */               else {
/*  336 */                 EntityType i_entity = Functions.getEntityType(i);
/*  337 */                 if (i_entity != null) {
/*  338 */                   isBlock = true;
/*      */                 }
/*      */               }
/*      */             }
/*  342 */             if (!isBlock) {
/*  343 */               excluded.add(i);
/*      */             }
/*  345 */             next = 0;
/*      */           }
/*      */         }
/*      */         else {
/*  349 */           next = 0;
/*      */         }
/*      */       }
/*  352 */       count++;
/*      */     }
/*  354 */     return excluded;
/*      */   }
/*      */   
/*      */   protected static boolean parseForceGlobal(String[] args_input) {
/*  358 */     String[] args = (String[])args_input.clone();
/*  359 */     boolean result = false;
/*  360 */     int count = 0;
/*  361 */     int next = 0;
/*  362 */     for (String i : args) {
/*  363 */       if (count > 0) {
/*  364 */         i = i.trim().toLowerCase();
/*  365 */         i = i.replaceAll("\\\\", "");
/*  366 */         i = i.replaceAll("'", "");
/*      */         
/*  368 */         if ((i.equals("r:")) || (i.equals("radius:"))) {
/*  369 */           next = 2;
/*      */         }
/*  371 */         else if ((next == 2) || (i.startsWith("r:")) || (i.startsWith("radius:"))) {
/*  372 */           i = i.replaceAll("radius:", "");
/*  373 */           i = i.replaceAll("r:", "");
/*  374 */           if ((i.equals("#global")) || (i.equals("global")) || (i.equals("off")) || (i.equals("-1")) || (i.equals("none")) || (i.equals("false"))) {
/*  375 */             result = true;
/*      */           }
/*  377 */           else if (i.startsWith("#")) {
/*  378 */             int world_id = Functions.matchWorld(i);
/*  379 */             if (world_id > 0) {
/*  380 */               result = true;
/*      */             }
/*      */           }
/*  383 */           next = 0;
/*      */         }
/*      */         else {
/*  386 */           next = 0;
/*      */         }
/*      */       }
/*  389 */       count++;
/*      */     }
/*  391 */     return result;
/*      */   }
/*      */   
/*      */   protected static Location parseLocation(CommandSender user, String[] args) {
/*  395 */     Location location = null;
/*  396 */     if ((user instanceof Player)) {
/*  397 */       location = ((Player)user).getLocation();
/*      */     }
/*  399 */     else if ((user instanceof BlockCommandSender)) {
/*  400 */       location = ((BlockCommandSender)user).getBlock().getLocation();
/*      */     }
/*  402 */     if (location != null) {
/*  403 */       location = parseCoordinates(location, args);
/*      */     }
/*  405 */     return location;
/*      */   }
/*      */   
/*      */   protected static int parseNoisy(String[] args_input) {
/*  409 */     String[] args = (String[])args_input.clone();
/*  410 */     int noisy = 0;
/*  411 */     int count = 0;
/*  412 */     if (((Integer)Config.config.get("verbose")).intValue() == 1) {
/*  413 */       noisy = 1;
/*      */     }
/*  415 */     for (String i : args) {
/*  416 */       if (count > 0) {
/*  417 */         i = i.trim().toLowerCase();
/*  418 */         i = i.replaceAll("\\\\", "");
/*  419 */         i = i.replaceAll("'", "");
/*      */         
/*  421 */         if ((i.equals("n")) || (i.equals("noisy")) || (i.equals("v")) || (i.equals("verbose")) || (i.equals("#v")) || (i.equals("#verbose"))) {
/*  422 */           noisy = 1;
/*      */         }
/*  424 */         else if (i.equals("#silent")) {
/*  425 */           noisy = 0;
/*      */         }
/*      */       }
/*  428 */       count++;
/*      */     }
/*  430 */     return noisy;
/*      */   }
/*      */   
/*      */   protected static int parsePreview(String[] args_input) {
/*  434 */     String[] args = (String[])args_input.clone();
/*  435 */     int result = 0;
/*  436 */     int count = 0;
/*  437 */     for (String i : args) {
/*  438 */       if (count > 0) {
/*  439 */         i = i.trim().toLowerCase();
/*  440 */         i = i.replaceAll("\\\\", "");
/*  441 */         i = i.replaceAll("'", "");
/*  442 */         if (i.equals("#preview")) {
/*  443 */           result = 1;
/*      */         }
/*  445 */         else if (i.equals("#preview_cancel")) {
/*  446 */           result = 2;
/*      */         }
/*      */       }
/*  449 */       count++;
/*      */     }
/*  451 */     return result;
/*      */   }
/*      */   
/*      */   protected static Integer[] parseRadius(String[] args_input, CommandSender user, Location location) {
/*  455 */     String[] args = (String[])args_input.clone();
/*  456 */     Integer[] radius = null;
/*  457 */     int count = 0;
/*  458 */     int next = 0;
/*  459 */     for (String i : args) {
/*  460 */       if (count > 0) {
/*  461 */         i = i.trim().toLowerCase();
/*  462 */         i = i.replaceAll("\\\\", "");
/*  463 */         i = i.replaceAll("'", "");
/*      */         
/*  465 */         if ((i.equals("r:")) || (i.equals("radius:"))) {
/*  466 */           next = 2;
/*      */         }
/*  468 */         else if ((next == 2) || (i.startsWith("r:")) || (i.startsWith("radius:"))) {
/*  469 */           i = i.replaceAll("radius:", "");
/*  470 */           i = i.replaceAll("r:", "");
/*  471 */           if ((i.equals("#worldedit")) || (i.equals("#we"))) {
/*  472 */             WorldEditPlugin wep = WorldEdit.getWorldEdit(user.getServer());
/*  473 */             if ((wep != null) && ((user instanceof Player))) {
/*      */               try {
/*  475 */                 LocalSession ls = wep.getSession((Player)user);
/*  476 */                 com.sk89q.worldedit.world.World lw = ls.getSelectionWorld();
/*  477 */                 if (lw != null) {
/*  478 */                   Region region = ls.getSelection(lw);
/*  479 */                   if ((region != null) && (lw.getName().equals(((Player)user).getWorld().getName()))) {
/*  480 */                     Vector v1 = region.getMinimumPoint();
/*  481 */                     int x1 = v1.getBlockX();
/*  482 */                     int y1 = v1.getBlockY();
/*  483 */                     int z1 = v1.getBlockZ();
/*  484 */                     int w = region.getWidth();
/*  485 */                     int h = region.getHeight();
/*  486 */                     int l = region.getLength();
/*  487 */                     int max = w;
/*  488 */                     if (h > max) {
/*  489 */                       max = h;
/*      */                     }
/*  491 */                     if (l > max) {
/*  492 */                       max = l;
/*      */                     }
/*  494 */                     int xmin = x1;
/*  495 */                     int xmax = x1 + (w - 1);
/*  496 */                     int ymin = y1;
/*  497 */                     int ymax = y1 + (h - 1);
/*  498 */                     int zmin = z1;
/*  499 */                     int zmax = z1 + (l - 1);
/*  500 */                     radius = new Integer[] { Integer.valueOf(max), Integer.valueOf(xmin), Integer.valueOf(xmax), Integer.valueOf(ymin), Integer.valueOf(ymax), Integer.valueOf(zmin), Integer.valueOf(zmax), Integer.valueOf(1) };
/*      */                   }
/*      */                   
/*      */                 }
/*      */                 
/*      */               }
/*      */               catch (Exception e) {}
/*      */             }
/*      */           }
/*  509 */           else if (((!i.startsWith("#")) || (i.length() <= 1)) && (!i.equals("global")) && (!i.equals("off")) && (!i.equals("-1")) && (!i.equals("none")) && (!i.equals("false")))
/*      */           {
/*      */ 
/*      */ 
/*  513 */             int rcount = 0;
/*  514 */             int r_x = 0;
/*  515 */             int r_y = -1;
/*  516 */             int r_z = 0;
/*  517 */             String[] r_dat = { i };
/*  518 */             boolean validRadius = false;
/*  519 */             if (i.contains("x")) {
/*  520 */               r_dat = i.split("x");
/*      */             }
/*  522 */             for (String value : r_dat) {
/*  523 */               String i4 = value.replaceAll("[^0-9.]", "");
/*  524 */               if ((i4.length() > 0) && (i4.length() == value.length()) && (i4.replaceAll("[^0-9]", "").length() > 0)) {
/*  525 */                 double a1 = Double.parseDouble(i4);
/*  526 */                 if (rcount == 0) {
/*  527 */                   r_x = (int)a1;
/*  528 */                   r_z = (int)a1;
/*      */                 }
/*  530 */                 else if (rcount == 1) {
/*  531 */                   r_y = (int)a1;
/*      */                 }
/*  533 */                 else if (rcount == 2) {
/*  534 */                   r_z = (int)a1;
/*      */                 }
/*  536 */                 validRadius = true;
/*      */               }
/*  538 */               rcount++;
/*      */             }
/*  540 */             if (location != null) {
/*  541 */               int xmin = location.getBlockX() - r_x;
/*  542 */               int xmax = location.getBlockX() + r_x;
/*  543 */               int ymin = -1;
/*  544 */               int ymax = -1;
/*  545 */               int zmin = location.getBlockZ() - r_z;
/*  546 */               int zmax = location.getBlockZ() + r_z;
/*  547 */               if (r_y > -1) {
/*  548 */                 ymin = location.getBlockY() - r_y;
/*  549 */                 ymax = location.getBlockY() + r_y;
/*      */               }
/*  551 */               int max = r_x;
/*  552 */               if (r_y > max) {
/*  553 */                 max = r_y;
/*      */               }
/*  555 */               if (r_z > max) {
/*  556 */                 max = r_z;
/*      */               }
/*  558 */               if (validRadius) {
/*  559 */                 radius = new Integer[] { Integer.valueOf(max), Integer.valueOf(xmin), Integer.valueOf(xmax), Integer.valueOf(ymin), Integer.valueOf(ymax), Integer.valueOf(zmin), Integer.valueOf(zmax), Integer.valueOf(0) };
/*      */               }
/*      */               else {
/*  562 */                 radius = new Integer[] { Integer.valueOf(-1) };
/*      */               }
/*      */             }
/*      */           }
/*  566 */           next = 0;
/*      */         }
/*      */         else {
/*  569 */           next = 0;
/*      */         }
/*      */       }
/*  572 */       count++;
/*      */     }
/*  574 */     return radius;
/*      */   }
/*      */   
/*      */   protected static List<Object> parseRestricted(CommandSender player, String[] args_input) {
/*  578 */     String[] args = (String[])args_input.clone();
/*  579 */     List<Object> restricted = new ArrayList();
/*  580 */     int count = 0;
/*  581 */     int next = 0;
/*  582 */     for (String i : args) {
/*  583 */       if (count > 0) {
/*  584 */         i = i.trim().toLowerCase();
/*  585 */         i = i.replaceAll("\\\\", "");
/*  586 */         i = i.replaceAll("'", "");
/*      */         
/*  588 */         if ((i.equals("b:")) || (i.equals("block:")) || (i.equals("blocks:"))) {
/*  589 */           next = 4;
/*      */         }
/*  591 */         else if ((next == 4) || (i.startsWith("b:")) || (i.startsWith("block:")) || (i.startsWith("blocks:"))) {
/*  592 */           i = i.replaceAll("blocks:", "");
/*  593 */           i = i.replaceAll("block:", "");
/*  594 */           i = i.replaceAll("b:", "");
/*  595 */           if (i.contains(",")) {
/*  596 */             String[] i2 = i.split(",");
/*  597 */             for (String i3 : i2) {
/*  598 */               if (i3.equals("#natural")) {
/*  599 */                 restricted.addAll(natural_blocks);
/*      */               }
/*      */               else {
/*  602 */                 Material i3_material = Functions.getType(i3);
/*  603 */                 if (i3_material != null) {
/*  604 */                   restricted.add(i3_material);
/*      */                 }
/*      */                 else {
/*  607 */                   EntityType i3_entity = Functions.getEntityType(i3);
/*  608 */                   if (i3_entity != null) {
/*  609 */                     restricted.add(i3_entity);
/*      */                   }
/*      */                   else {
/*  612 */                     player.sendMessage("§3CoreProtect §f- Sorry, \"" + i3 + "\" is an invalid block name.");
/*  613 */                     player.sendMessage("§3CoreProtect §f- Please view \"/co help blocks\".");
/*  614 */                     return null;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*  619 */             if (i.endsWith(",")) {
/*  620 */               next = 4;
/*      */             }
/*      */             else {
/*  623 */               next = 0;
/*      */             }
/*      */           }
/*      */           else {
/*  627 */             if (i.equals("#natural")) {
/*  628 */               restricted.addAll(natural_blocks);
/*      */             }
/*      */             else {
/*  631 */               Material i_material = Functions.getType(i);
/*  632 */               if (i_material != null) {
/*  633 */                 restricted.add(i_material);
/*      */               }
/*      */               else {
/*  636 */                 EntityType i_entity = Functions.getEntityType(i);
/*  637 */                 if (i_entity != null) {
/*  638 */                   restricted.add(i_entity);
/*      */                 }
/*      */                 else {
/*  641 */                   player.sendMessage("§3CoreProtect §f- Sorry, \"" + i + "\" is an invalid block name.");
/*  642 */                   player.sendMessage("§3CoreProtect §f- Please view \"/co help blocks\".");
/*  643 */                   return null;
/*      */                 }
/*      */               }
/*      */             }
/*  647 */             next = 0;
/*      */           }
/*      */         }
/*      */         else {
/*  651 */           next = 0;
/*      */         }
/*      */       }
/*  654 */       count++;
/*      */     }
/*  656 */     return restricted;
/*      */   }
/*      */   
/*      */   protected static int parseTime(String[] args_input) {
/*  660 */     String[] args = (String[])args_input.clone();
/*  661 */     int time = 0;
/*  662 */     int count = 0;
/*  663 */     int next = 0;
/*  664 */     double w = 0.0D;
/*  665 */     double d = 0.0D;
/*  666 */     double h = 0.0D;
/*  667 */     double m = 0.0D;
/*  668 */     double s = 0.0D;
/*  669 */     for (String i : args) {
/*  670 */       if (count > 0) {
/*  671 */         i = i.trim().toLowerCase();
/*  672 */         i = i.replaceAll("\\\\", "");
/*  673 */         i = i.replaceAll("'", "");
/*      */         
/*  675 */         if ((i.equals("t:")) || (i.equals("time:"))) {
/*  676 */           next = 1;
/*      */         }
/*  678 */         else if ((next == 1) || (i.startsWith("t:")) || (i.startsWith("time:")))
/*      */         {
/*  680 */           i = i.replaceAll("time:", "");
/*  681 */           i = i.replaceAll("t:", "");
/*  682 */           i = i.replaceAll("y", "y:");
/*  683 */           i = i.replaceAll("m", "m:");
/*  684 */           i = i.replaceAll("w", "w:");
/*  685 */           i = i.replaceAll("d", "d:");
/*  686 */           i = i.replaceAll("h", "h:");
/*  687 */           i = i.replaceAll("s", "s:");
/*  688 */           String[] i2 = i.split(":");
/*  689 */           for (String i3 : i2) {
/*  690 */             if (i3.endsWith("w")) {
/*  691 */               String i4 = i3.replaceAll("[^0-9.]", "");
/*  692 */               if ((i4.length() > 0) && (i4.replaceAll("[^0-9]", "").length() > 0)) {
/*  693 */                 w = Double.parseDouble(i4);
/*      */               }
/*      */             }
/*  696 */             else if (i3.endsWith("d")) {
/*  697 */               String i4 = i3.replaceAll("[^0-9.]", "");
/*  698 */               if ((i4.length() > 0) && (i4.replaceAll("[^0-9]", "").length() > 0)) {
/*  699 */                 d = Double.parseDouble(i4);
/*      */               }
/*      */             }
/*  702 */             else if (i3.endsWith("h")) {
/*  703 */               String i4 = i3.replaceAll("[^0-9.]", "");
/*  704 */               if ((i4.length() > 0) && (i4.replaceAll("[^0-9]", "").length() > 0)) {
/*  705 */                 h = Double.parseDouble(i4);
/*      */               }
/*      */             }
/*  708 */             else if (i3.endsWith("m")) {
/*  709 */               String i4 = i3.replaceAll("[^0-9.]", "");
/*  710 */               if ((i4.length() > 0) && (i4.replaceAll("[^0-9]", "").length() > 0)) {
/*  711 */                 m = Double.parseDouble(i4);
/*      */               }
/*      */             }
/*  714 */             else if (i3.endsWith("s")) {
/*  715 */               String i4 = i3.replaceAll("[^0-9.]", "");
/*  716 */               if ((i4.length() > 0) && (i4.replaceAll("[^0-9]", "").length() > 0)) {
/*  717 */                 s = Double.parseDouble(i4);
/*      */               }
/*      */             }
/*      */           }
/*  721 */           double rs = w * 7.0D * 24.0D * 60.0D * 60.0D + d * 24.0D * 60.0D * 60.0D + h * 60.0D * 60.0D + m * 60.0D + s;
/*  722 */           time = (int)rs;
/*  723 */           next = 0;
/*      */         }
/*      */         else {
/*  726 */           next = 0;
/*      */         }
/*      */       }
/*  729 */       count++;
/*      */     }
/*  731 */     return time;
/*      */   }
/*      */   
/*      */   protected static String parseTimeString(String[] args_input) {
/*  735 */     String[] args = (String[])args_input.clone();
/*  736 */     String time = "";
/*  737 */     int count = 0;
/*  738 */     int next = 0;
/*  739 */     double w = 0.0D;
/*  740 */     double d = 0.0D;
/*  741 */     double h = 0.0D;
/*  742 */     double m = 0.0D;
/*  743 */     double s = 0.0D;
/*  744 */     for (String i : args) {
/*  745 */       if (count > 0) {
/*  746 */         i = i.trim().toLowerCase();
/*  747 */         i = i.replaceAll("\\\\", "");
/*  748 */         i = i.replaceAll("'", "");
/*      */         
/*  750 */         if ((i.equals("t:")) || (i.equals("time:"))) {
/*  751 */           next = 1;
/*      */         }
/*  753 */         else if ((next == 1) || (i.startsWith("t:")) || (i.startsWith("time:")))
/*      */         {
/*  755 */           i = i.replaceAll("time:", "");
/*  756 */           i = i.replaceAll("t:", "");
/*  757 */           i = i.replaceAll("y", "y:");
/*  758 */           i = i.replaceAll("m", "m:");
/*  759 */           i = i.replaceAll("w", "w:");
/*  760 */           i = i.replaceAll("d", "d:");
/*  761 */           i = i.replaceAll("h", "h:");
/*  762 */           i = i.replaceAll("s", "s:");
/*  763 */           String[] i2 = i.split(":");
/*  764 */           for (String i3 : i2) {
/*  765 */             if (i3.endsWith("w")) {
/*  766 */               String i4 = i3.replaceAll("[^0-9.]", "");
/*  767 */               if ((i4.length() > 0) && (i4.replaceAll("[^0-9]", "").length() > 0)) {
/*  768 */                 w = Double.parseDouble(i4);
/*  769 */                 time = time + " " + w + " week(s)";
/*      */               }
/*      */             }
/*  772 */             else if (i3.endsWith("d")) {
/*  773 */               String i4 = i3.replaceAll("[^0-9.]", "");
/*  774 */               if ((i4.length() > 0) && (i4.replaceAll("[^0-9]", "").length() > 0)) {
/*  775 */                 d = Double.parseDouble(i4);
/*  776 */                 time = time + " " + d + " day(s)";
/*      */               }
/*      */             }
/*  779 */             else if (i3.endsWith("h")) {
/*  780 */               String i4 = i3.replaceAll("[^0-9.]", "");
/*  781 */               if ((i4.length() > 0) && (i4.replaceAll("[^0-9]", "").length() > 0)) {
/*  782 */                 h = Double.parseDouble(i4);
/*  783 */                 time = time + " " + h + " hour(s)";
/*      */               }
/*      */             }
/*  786 */             else if (i3.endsWith("m")) {
/*  787 */               String i4 = i3.replaceAll("[^0-9.]", "");
/*  788 */               if ((i4.length() > 0) && (i4.replaceAll("[^0-9]", "").length() > 0)) {
/*  789 */                 m = Double.parseDouble(i4);
/*  790 */                 time = time + " " + m + " minute(s)";
/*      */               }
/*      */             }
/*  793 */             else if (i3.endsWith("s")) {
/*  794 */               String i4 = i3.replaceAll("[^0-9.]", "");
/*  795 */               if ((i4.length() > 0) && (i4.replaceAll("[^0-9]", "").length() > 0)) {
/*  796 */                 s = Double.parseDouble(i4);
/*  797 */                 time = time + " " + s + " second(s)";
/*      */               }
/*      */             }
/*      */           }
/*  801 */           next = 0;
/*      */         }
/*      */         else {
/*  804 */           next = 0;
/*      */         }
/*      */       }
/*  807 */       count++;
/*      */     }
/*  809 */     return time;
/*      */   }
/*      */   
/*      */   private static void parseUser(List<String> users, String string) {
/*  813 */     string = string.trim();
/*  814 */     if (string.contains(",")) {
/*  815 */       String[] data = string.split(",");
/*  816 */       for (String user : data) {
/*  817 */         validUserCheck(users, user);
/*      */       }
/*      */     }
/*      */     else {
/*  821 */       validUserCheck(users, string);
/*      */     }
/*      */   }
/*      */   
/*      */   protected static List<String> parseUsers(String[] args_input) {
/*  826 */     String[] args = (String[])args_input.clone();
/*  827 */     List<String> users = new ArrayList();
/*  828 */     int count = 0;
/*  829 */     int next = 0;
/*  830 */     for (String i : args) {
/*  831 */       if (count > 0) {
/*  832 */         i = i.trim().toLowerCase();
/*  833 */         i = i.replaceAll("\\\\", "");
/*  834 */         i = i.replaceAll("'", "");
/*      */         
/*  836 */         if (next == 2) {
/*  837 */           if (i.endsWith(",")) {
/*  838 */             next = 2;
/*      */           }
/*      */           else {
/*  841 */             next = 0;
/*      */           }
/*      */         }
/*  844 */         else if ((i.equals("p:")) || (i.equals("user:")) || (i.equals("u:"))) {
/*  845 */           next = 1;
/*      */         }
/*  847 */         else if ((next == 1) || (i.startsWith("p:")) || (i.startsWith("user:")) || (i.startsWith("u:"))) {
/*  848 */           i = i.replaceAll("user:", "");
/*  849 */           i = i.replaceAll("p:", "");
/*  850 */           i = i.replaceAll("u:", "");
/*  851 */           if (i.contains(",")) {
/*  852 */             String[] i2 = i.split(",");
/*  853 */             for (String i3 : i2) {
/*  854 */               parseUser(users, i3);
/*      */             }
/*  856 */             if (i.endsWith(",")) {
/*  857 */               next = 1;
/*      */             }
/*      */             else {
/*  860 */               next = 0;
/*      */             }
/*      */           }
/*      */           else {
/*  864 */             parseUser(users, i);
/*  865 */             next = 0;
/*      */           }
/*      */         }
/*  868 */         else if ((i.endsWith(",")) || (i.endsWith(":"))) {
/*  869 */           next = 2;
/*      */         }
/*  871 */         else if (i.contains(":")) {
/*  872 */           next = 0;
/*      */         }
/*      */         else {
/*  875 */           parseUser(users, i);
/*  876 */           next = 0;
/*      */         }
/*      */       }
/*  879 */       count++;
/*      */     }
/*  881 */     return users;
/*      */   }
/*      */   
/*      */   protected static int parseWorld(String[] args_input) {
/*  885 */     String[] args = (String[])args_input.clone();
/*  886 */     int world_id = 0;
/*  887 */     int count = 0;
/*  888 */     int next = 0;
/*  889 */     for (String i : args) {
/*  890 */       if (count > 0) {
/*  891 */         i = i.trim().toLowerCase();
/*  892 */         i = i.replaceAll("\\\\", "");
/*  893 */         i = i.replaceAll("'", "");
/*      */         
/*  895 */         if ((i.equals("r:")) || (i.equals("radius:"))) {
/*  896 */           next = 2;
/*      */         }
/*  898 */         else if ((next == 2) || (i.startsWith("r:")) || (i.startsWith("radius:"))) {
/*  899 */           i = i.replaceAll("radius:", "");
/*  900 */           i = i.replaceAll("r:", "");
/*  901 */           if ((i.equals("#worldedit")) || (i.equals("#we")) || (i.equals("#global")) || (i.equals("global")) || (i.equals("off")) || (i.equals("-1")) || (i.equals("none")) || (i.equals("false"))) {
/*  902 */             world_id = 0;
/*      */           }
/*  904 */           else if (i.startsWith("#")) {
/*  905 */             world_id = Functions.matchWorld(i);
/*      */           }
/*  907 */           next = 0;
/*      */         }
/*      */         else {
/*  910 */           next = 0;
/*      */         }
/*      */       }
/*  913 */       count++;
/*      */     }
/*  915 */     return world_id;
/*      */   }
/*      */   
/*      */   protected static boolean parseWorldEdit(String[] args_input) {
/*  919 */     String[] args = (String[])args_input.clone();
/*  920 */     boolean result = false;
/*  921 */     int count = 0;
/*  922 */     int next = 0;
/*  923 */     for (String i : args) {
/*  924 */       if (count > 0) {
/*  925 */         i = i.trim().toLowerCase();
/*  926 */         i = i.replaceAll("\\\\", "");
/*  927 */         i = i.replaceAll("'", "");
/*      */         
/*  929 */         if ((i.equals("r:")) || (i.equals("radius:"))) {
/*  930 */           next = 2;
/*      */         }
/*  932 */         else if ((next == 2) || (i.startsWith("r:")) || (i.startsWith("radius:"))) {
/*  933 */           i = i.replaceAll("radius:", "");
/*  934 */           i = i.replaceAll("r:", "");
/*  935 */           if ((i.equals("#worldedit")) || (i.equals("#we"))) {
/*  936 */             result = true;
/*      */           }
/*  938 */           next = 0;
/*      */         }
/*      */         else {
/*  941 */           next = 0;
/*      */         }
/*      */       }
/*  944 */       count++;
/*      */     }
/*  946 */     return result;
/*      */   }
/*      */   
/*      */   protected static String parseWorldName(String[] args_input) {
/*  950 */     String[] args = (String[])args_input.clone();
/*  951 */     String world_name = "";
/*  952 */     int count = 0;
/*  953 */     int next = 0;
/*  954 */     for (String i : args) {
/*  955 */       if (count > 0) {
/*  956 */         i = i.trim().toLowerCase();
/*  957 */         i = i.replaceAll("\\\\", "");
/*  958 */         i = i.replaceAll("'", "");
/*      */         
/*  960 */         if ((i.equals("r:")) || (i.equals("radius:"))) {
/*  961 */           next = 2;
/*      */         }
/*  963 */         else if ((next == 2) || (i.startsWith("r:")) || (i.startsWith("radius:"))) {
/*  964 */           i = i.replaceAll("radius:", "");
/*  965 */           i = i.replaceAll("r:", "");
/*  966 */           if ((i.equals("#worldedit")) || (i.equals("#we")) || (i.equals("#global")) || (i.equals("global")) || (i.equals("off")) || (i.equals("-1")) || (i.equals("none")) || (i.equals("false"))) {
/*  967 */             world_name = "";
/*      */           }
/*  969 */           else if (i.startsWith("#")) {
/*  970 */             world_name = i.replaceFirst("#", "");
/*      */           }
/*  972 */           next = 0;
/*      */         }
/*      */         else {
/*  975 */           next = 0;
/*      */         }
/*      */       }
/*  978 */       count++;
/*      */     }
/*  980 */     return world_name;
/*      */   }
/*      */   
/*      */   private static void validUserCheck(List<String> users, String user) {
/*  984 */     List<String> bad_users = Arrays.asList(new String[] { "n", "noisy", "v", "verbose", "#v", "#verbose", "#silent", "#preview", "#preview_cancel", "#count", "#sum" });
/*  985 */     String check = user.replaceAll("[^a-zA-Z0-9#_]", "");
/*  986 */     if ((check.equals(user)) && (check.length() > 0)) {
/*  987 */       if (user.equalsIgnoreCase("#global")) {
/*  988 */         user = "#global";
/*      */       }
/*  990 */       if (!bad_users.contains(user.toLowerCase())) {
/*  991 */         users.add(user);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   public boolean onCommand(CommandSender user, Command command, String commandLabel, String[] args)
/*      */   {
/*  998 */     String commandName = command.getName().toLowerCase();
/*      */     
/* 1000 */     if ((commandName.equals("core")) || (commandName.equals("coreprotect")) || (commandName.equals("co"))) {
/* 1001 */       int resultc = args.length;
/* 1002 */       if (resultc > -1) {
/* 1003 */         String corecommand = "help";
/* 1004 */         if (resultc > 0) {
/* 1005 */           corecommand = args[0].toLowerCase();
/*      */         }
/* 1007 */         boolean permission = false;
/* 1008 */         if (user.isOp()) {
/* 1009 */           permission = true;
/*      */         }
/* 1011 */         if (!permission) {
/* 1012 */           if ((user.hasPermission("coreprotect.rollback")) && ((corecommand.equals("rollback")) || (corecommand.equals("rb")) || (corecommand.equals("ro")) || (corecommand.equals("apply")) || (corecommand.equals("cancel")))) {
/* 1013 */             permission = true;
/*      */           }
/* 1015 */           else if ((user.hasPermission("coreprotect.restore")) && ((corecommand.equals("restore")) || (corecommand.equals("rs")) || (corecommand.equals("re")) || (corecommand.equals("undo")) || (corecommand.equals("apply")) || (corecommand.equals("cancel")))) {
/* 1016 */             permission = true;
/*      */           }
/* 1018 */           else if ((user.hasPermission("coreprotect.inspect")) && ((corecommand.equals("i")) || (corecommand.equals("inspect")))) {
/* 1019 */             permission = true;
/*      */           }
/* 1021 */           else if ((user.hasPermission("coreprotect.help")) && (corecommand.equals("help"))) {
/* 1022 */             permission = true;
/*      */           }
/* 1024 */           else if ((user.hasPermission("coreprotect.purge")) && (corecommand.equals("purge"))) {
/* 1025 */             permission = true;
/*      */           }
/* 1027 */           else if ((user.hasPermission("coreprotect.lookup")) && ((corecommand.equals("l")) || (corecommand.equals("lookup")) || (corecommand.equals("near")))) {
/* 1028 */             permission = true;
/*      */           }
/* 1030 */           else if ((user.hasPermission("coreprotect.reload")) && (corecommand.equals("reload"))) {
/* 1031 */             permission = true;
/*      */           }
/*      */         }
/*      */         
/* 1035 */         if ((corecommand.equals("rollback")) || (corecommand.equals("restore")) || (corecommand.equals("rb")) || (corecommand.equals("rs")) || (corecommand.equals("ro")) || (corecommand.equals("re"))) {
/* 1036 */           RollbackRestoreCommand.runCommand(user, permission, args, 0);
/*      */         }
/* 1038 */         else if (corecommand.equals("apply")) {
/* 1039 */           ApplyCommand.runCommand(user, permission, args);
/*      */         }
/* 1041 */         else if (corecommand.equals("cancel")) {
/* 1042 */           CancelCommand.runCommand(user, permission, args);
/*      */         }
/* 1044 */         else if (corecommand.equals("undo")) {
/* 1045 */           UndoCommand.runCommand(user, permission, args);
/*      */         }
/* 1047 */         else if (corecommand.equals("help")) {
/* 1048 */           HelpCommand.runCommand(user, permission, args);
/*      */         }
/* 1050 */         else if (corecommand.equals("purge")) {
/* 1051 */           PurgeCommand.runCommand(user, permission, args);
/*      */         }
/* 1053 */         else if ((corecommand.equals("inspect")) || (corecommand.equals("i"))) {
/* 1054 */           InspectCommand.runCommand(user, permission, args);
/*      */         }
/* 1056 */         else if ((corecommand.equals("lookup")) || (corecommand.equals("l"))) {
/* 1057 */           LookupCommand.runCommand(user, permission, args);
/*      */         }
/* 1059 */         else if (corecommand.equals("near")) {
/* 1060 */           LookupCommand.runCommand(user, permission, new String[] { "l", "r:5x5" });
/*      */         }
/* 1062 */         else if (corecommand.equals("version")) {
/* 1063 */           VersionCommand.runCommand(user, permission, args);
/*      */         }
/* 1065 */         else if (corecommand.equals("reload")) {
/* 1066 */           ReloadCommand.runCommand(user, permission, args);
/*      */         }
/*      */         else {
/* 1069 */           user.sendMessage("§3CoreProtect §f- Command \"§3/co " + corecommand + "§f\" not found.");
/*      */         }
/*      */       }
/*      */       else {
/* 1073 */         user.sendMessage("§3CoreProtect §f- Please use \"§3/co <parameters>§f\".");
/*      */       }
/* 1075 */       return true;
/*      */     }
/*      */     
/* 1078 */     return false;
/*      */   }
/*      */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\command\CommandHandler.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */