/*     */ package com.LineB.xrayinformer.lookups;
/*     */ 
/*     */ import com.LineB.xrayinformer.Config;
/*     */ import com.LineB.xrayinformer.XrayInformer;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class Checkers
/*     */ {
/*     */   private static XrayInformer plugin;
/*     */   
/*     */   public Checkers()
/*     */   {
/*  20 */     plugin = XrayInformer.getInstance();
/*     */   }
/*     */   
/*  23 */   private static LogBlockLookup lb = new LogBlockLookup();
/*  24 */   private static CoreProtectLookup cp = new CoreProtectLookup();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void checkGlobal(final String name, final CommandSender sender, final String world, final int hours)
/*     */   {
/* 270 */     new BukkitRunnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*  30 */         int hrs = hours;
/*  31 */         if (hours == -1) {
/*  32 */           hrs = -1;
/*     */         }
/*     */         
/*  35 */         if (Checkers.plugin.getServer().getWorld(world) == null) {
/*  36 */           sender.sendMessage("Please check config.yml - your configured world seems not to exist?");
/*     */         }
/*     */         
/*     */         try
/*     */         {
/*  41 */           sender.sendMessage(ChatColor.GREEN + "[XrayInformer] Calculating ore ratios for " + ChatColor.GOLD + name + ".");
/*  42 */           sender.sendMessage(ChatColor.GREEN + "[XrayInformer] Please be patient, this may take a minute.");
/*  43 */           int level = 0;
/*  44 */           int count_stone = 0;
/*     */           
/*  46 */           int diamond_count = 0;
/*  47 */           int gold_count = 0;
/*  48 */           int lapis_count = 0;
/*  49 */           int iron_count = 0;
/*  50 */           int mossy_count = 0;
/*  51 */           int emerald_count = 0;
/*     */           
/*  53 */           if (Checkers.plugin.getConfig().getString("logging_plugin").equalsIgnoreCase("logblock")) {
/*  54 */             count_stone = Checkers.lb.stoneLookup(name, world, hrs);
/*     */             
/*  56 */             diamond_count = Checkers.lb.oreLookup(name, 56, world, hrs);
/*  57 */             gold_count = Checkers.lb.oreLookup(name, 14, world, hrs);
/*  58 */             lapis_count = Checkers.lb.oreLookup(name, 21, world, hrs);
/*  59 */             iron_count = Checkers.lb.oreLookup(name, 15, world, hrs);
/*  60 */             mossy_count = Checkers.lb.oreLookup(name, 48, world, hrs);
/*  61 */             emerald_count = Checkers.lb.oreLookup(name, 129, world, hrs);
/*     */           }
/*     */           
/*  64 */           if (Checkers.plugin.getConfig().getString("logging_plugin").equalsIgnoreCase("coreprotect")) {
/*  65 */             count_stone = Checkers.cp.stoneLookup(name, world, hrs);
/*     */             
/*  67 */             diamond_count = Checkers.cp.oreLookup(name, 56, world, hrs);
/*  68 */             gold_count = Checkers.cp.oreLookup(name, 14, world, hrs);
/*  69 */             lapis_count = Checkers.cp.oreLookup(name, 21, world, hrs);
/*  70 */             iron_count = Checkers.cp.oreLookup(name, 15, world, hrs);
/*  71 */             mossy_count = Checkers.cp.oreLookup(name, 48, world, hrs);
/*  72 */             emerald_count = Checkers.cp.oreLookup(name, 129, world, hrs);
/*     */           }
/*     */           
/*  75 */           sender.sendMessage(Checkers.plugin.msgBorder);
/*  76 */           sender.sendMessage(ChatColor.GREEN + "XrayInformer: " + ChatColor.GOLD + name);
/*  77 */           sender.sendMessage(Checkers.plugin.msgBorder);
/*  78 */           sender.sendMessage("Stones: " + String.valueOf(count_stone));
/*     */           
/*  80 */           String s = "";
/*  81 */           ChatColor ccolor = ChatColor.GREEN;
/*     */           
/*  83 */           if ((Checkers.plugin.config.isActive("diamond")) && (diamond_count > 0))
/*     */           {
/*  85 */             float d = (float)(diamond_count * 100.0D / count_stone);
/*  86 */             if (d > Checkers.plugin.config.getRate("confirmed", "diamond"))
/*     */             {
/*  88 */               ccolor = ChatColor.RED;
/*     */             }
/*  90 */             else if (d > Checkers.plugin.config.getRate("warn", "diamond"))
/*     */             {
/*  92 */               ccolor = ChatColor.YELLOW;
/*     */             }
/*     */             else
/*     */             {
/*  96 */               ccolor = ChatColor.GREEN;
/*     */             }
/*     */             
/*  99 */             level = (int)(level + d * 10.0F);
/*     */             
/* 101 */             s = String.valueOf(d) + "000000000";
/* 102 */             sender.sendMessage(ccolor + "Diamond: " + String.valueOf(Float.parseFloat(s.substring(0, s.lastIndexOf('.') + 3))) + "% (" + String.valueOf(diamond_count) + ")");
/*     */           }
/*     */           else
/*     */           {
/* 106 */             sender.sendMessage("Diamond: -");
/*     */           }
/*     */           
/* 109 */           if ((Checkers.plugin.config.isActive("gold")) && (gold_count > 0))
/*     */           {
/* 111 */             float d = (float)(gold_count * 100.0D / count_stone);
/* 112 */             if (d > Checkers.plugin.config.getRate("confirmed", "gold"))
/*     */             {
/* 114 */               ccolor = ChatColor.RED;
/*     */             }
/* 116 */             else if (d > Checkers.plugin.config.getRate("warn", "gold"))
/*     */             {
/* 118 */               ccolor = ChatColor.YELLOW;
/*     */             }
/*     */             else
/*     */             {
/* 122 */               ccolor = ChatColor.GREEN;
/*     */             }
/*     */             
/* 125 */             level = (int)(level + d * 3.0F);
/*     */             
/* 127 */             s = String.valueOf(d) + "000000000";
/* 128 */             sender.sendMessage(ccolor + "Gold: " + String.valueOf(Float.parseFloat(s.substring(0, s.lastIndexOf('.') + 3))) + "% (" + String.valueOf(gold_count) + ")");
/*     */           }
/*     */           else
/*     */           {
/* 132 */             sender.sendMessage("Gold: -");
/*     */           }
/*     */           
/* 135 */           if ((Checkers.plugin.config.isActive("lapis")) && (lapis_count > 0))
/*     */           {
/* 137 */             float d = (float)(lapis_count * 100.0D / count_stone);
/* 138 */             if (d > Checkers.plugin.config.getRate("confirmed", "lapis"))
/*     */             {
/* 140 */               ccolor = ChatColor.RED;
/*     */             }
/* 142 */             else if (d > Checkers.plugin.config.getRate("warn", "lapis"))
/*     */             {
/* 144 */               ccolor = ChatColor.YELLOW;
/*     */             }
/*     */             else
/*     */             {
/* 148 */               ccolor = ChatColor.GREEN;
/*     */             }
/*     */             
/* 151 */             level = (int)(level + d * 10.0F);
/*     */             
/* 153 */             s = String.valueOf(d) + "000000000";
/* 154 */             sender.sendMessage(ccolor + "Lapis: " + String.valueOf(Float.parseFloat(s.substring(0, s.lastIndexOf('.') + 3))) + "% (" + String.valueOf(lapis_count) + ")");
/*     */           }
/*     */           else
/*     */           {
/* 158 */             sender.sendMessage("Lapis: -");
/*     */           }
/*     */           
/* 161 */           if ((Checkers.plugin.config.isActive("emerald")) && (emerald_count > 0))
/*     */           {
/* 163 */             float d = (float)(emerald_count * 100.0D / count_stone);
/* 164 */             if (d > Checkers.plugin.config.getRate("confirmed", "emerald"))
/*     */             {
/* 166 */               ccolor = ChatColor.RED;
/*     */             }
/* 168 */             else if (d > Checkers.plugin.config.getRate("warn", "emerald"))
/*     */             {
/* 170 */               ccolor = ChatColor.YELLOW;
/*     */             }
/*     */             else
/*     */             {
/* 174 */               ccolor = ChatColor.GREEN;
/*     */             }
/*     */             
/* 177 */             level = (int)(level + d * 10.0F);
/*     */             
/* 179 */             s = String.valueOf(d) + "000000000";
/* 180 */             sender.sendMessage(ccolor + "Emerald: " + String.valueOf(Float.parseFloat(s.substring(0, s.lastIndexOf('.') + 3))) + "% (" + String.valueOf(emerald_count) + ")");
/*     */           }
/*     */           else
/*     */           {
/* 184 */             sender.sendMessage("Emerald: -");
/*     */           }
/*     */           
/* 187 */           if ((Checkers.plugin.config.isActive("iron")) && (iron_count > 0))
/*     */           {
/* 189 */             float d = (float)(iron_count * 100.0D / count_stone);
/* 190 */             if (d > Checkers.plugin.config.getRate("confirmed", "iron"))
/*     */             {
/* 192 */               ccolor = ChatColor.RED;
/*     */             }
/* 194 */             else if (d > Checkers.plugin.config.getRate("warn", "iron"))
/*     */             {
/* 196 */               ccolor = ChatColor.YELLOW;
/*     */             }
/*     */             else
/*     */             {
/* 200 */               ccolor = ChatColor.GREEN;
/*     */             }
/*     */             
/* 203 */             level = (int)(level + d * 1.0F);
/*     */             
/* 205 */             s = String.valueOf(d) + "000000000";
/* 206 */             sender.sendMessage(ccolor + "Iron: " + String.valueOf(Float.parseFloat(s.substring(0, s.lastIndexOf('.') + 3))) + "% (" + String.valueOf(iron_count) + ")");
/*     */           }
/*     */           else
/*     */           {
/* 210 */             sender.sendMessage("Iron: -");
/*     */           }
/*     */           
/* 213 */           if ((Checkers.plugin.config.isActive("mossy")) && (mossy_count > 0))
/*     */           {
/* 215 */             float d = (float)(mossy_count * 100.0D / count_stone);
/* 216 */             if (d > Checkers.plugin.config.getRate("confirmed", "mossy"))
/*     */             {
/* 218 */               ccolor = ChatColor.RED;
/*     */             }
/* 220 */             else if (d > Checkers.plugin.config.getRate("warn", "mossy"))
/*     */             {
/* 222 */               ccolor = ChatColor.YELLOW;
/*     */             }
/*     */             else
/*     */             {
/* 226 */               ccolor = ChatColor.GREEN;
/*     */             }
/*     */             
/* 229 */             level = (int)(level + d * 7.0F);
/*     */             
/* 231 */             s = String.valueOf(d) + "000000000";
/* 232 */             sender.sendMessage(ccolor + "Mossy: " + String.valueOf(Float.parseFloat(s.substring(0, s.lastIndexOf('.') + 3))) + "% (" + String.valueOf(mossy_count) + ")");
/*     */           }
/*     */           else
/*     */           {
/* 236 */             sender.sendMessage("Mossy: -");
/*     */           }
/*     */           
/* 239 */           if (count_stone < 500)
/*     */           {
/* 241 */             level = (int)(level * 0.5D);
/*     */           }
/* 243 */           else if (count_stone > 1000)
/*     */           {
/* 245 */             level *= 2;
/*     */           }
/*     */           
/* 248 */           if (level < 45) {
/* 249 */             sender.sendMessage(ChatColor.GREEN + "xLevel: " + level + " (Xray use is very unlikely)");
/*     */           }
/* 251 */           if ((level >= 45) && (level < 85)) {
/* 252 */             sender.sendMessage(ChatColor.GREEN + "xLevel: " + level + " (Xray use is unlikely)");
/*     */           }
/* 254 */           if ((level >= 85) && (level < 130)) {
/* 255 */             sender.sendMessage(ChatColor.YELLOW + "xLevel: " + level + " (Medium Chance of Xray)");
/*     */           }
/* 257 */           if ((level >= 130) && (level < 170)) {
/* 258 */             sender.sendMessage(ChatColor.RED + "xLevel: " + level + " (High Chance of Xray)");
/*     */           }
/* 260 */           if (level >= 170) {
/* 261 */             sender.sendMessage(ChatColor.DARK_RED + "xLevel: " + level + " (Very High Chance of Xray)");
/*     */           }
/* 263 */           sender.sendMessage(Checkers.plugin.msgBorder);
/*     */         }
/*     */         catch (SQLException e)
/*     */         {
/* 267 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 270 */     }.runTaskAsynchronously(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void checkSingle(final String name, final CommandSender sender, final int oreid, final String world, final int hours)
/*     */   {
/* 325 */     new BukkitRunnable()
/*     */     {
/*     */       public void run()
/*     */       {
/* 278 */         int hrs = hours;
/* 279 */         if (hours == -1) {
/* 280 */           hrs = -1;
/*     */         }
/*     */         try
/*     */         {
/* 284 */           sender.sendMessage(ChatColor.GREEN + "[XrayInformer] Calculating ore ratios for " + ChatColor.GOLD + name + ".");
/* 285 */           sender.sendMessage(ChatColor.GREEN + "[XrayInformer] Please be patient, this may take a minute.");
/* 286 */           int count_stone = 0;
/* 287 */           int count_xyz = 0;
/*     */           
/* 289 */           if (Checkers.plugin.getConfig().getString("logging_plugin").equalsIgnoreCase("logblock")) {
/* 290 */             count_stone = Checkers.lb.stoneLookup(name, world, hrs);
/* 291 */             count_xyz = Checkers.lb.oreLookup(name, oreid, world, hrs);
/*     */           }
/*     */           
/* 294 */           if (Checkers.plugin.getConfig().getString("logging_plugin").equalsIgnoreCase("coreprotect")) {
/* 295 */             count_stone = Checkers.cp.stoneLookup(name, world, hrs);
/* 296 */             count_xyz = Checkers.cp.oreLookup(name, oreid, world, hrs);
/*     */           }
/*     */           
/* 299 */           int mat_1_id = Integer.valueOf(oreid).intValue();
/* 300 */           String mat_1_name = Material.getMaterial(mat_1_id).toString();
/*     */           
/* 302 */           sender.sendMessage(Checkers.plugin.msgBorder);
/* 303 */           sender.sendMessage(ChatColor.GREEN + "XrayInformer: " + ChatColor.GOLD + name);
/* 304 */           sender.sendMessage(Checkers.plugin.msgBorder);
/* 305 */           sender.sendMessage("Stones: " + String.valueOf(count_stone));
/*     */           
/* 307 */           String s = "";
/*     */           
/* 309 */           if (count_xyz > 0)
/*     */           {
/* 311 */             float d = (float)(count_xyz * 100.0D / count_stone);
/* 312 */             s = String.valueOf(d) + "000000000";
/* 313 */             sender.sendMessage(mat_1_name + ": " + String.valueOf(Float.parseFloat(s.substring(0, s.lastIndexOf('.') + 3))) + "% (" + String.valueOf(count_xyz) + ")");
/*     */           }
/*     */           else
/*     */           {
/* 317 */             sender.sendMessage(mat_1_name + ": -");
/*     */           }
/*     */         }
/*     */         catch (SQLException e)
/*     */         {
/* 322 */           e.printStackTrace();
/*     */         }
/*     */       }
/* 325 */     }.runTaskAsynchronously(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */   public void listAllXRayers(CommandSender sender, String world, int oreid, String bantype, float maxrate, boolean banned, int hours)
/*     */   {
/* 331 */     if (hours == -1)
/*     */     {
/* 333 */       hours = -1;
/*     */     }
/*     */     
/* 336 */     java.util.List<String[]> playerOreStone = new ArrayList();
/*     */     
/* 338 */     if (plugin.getConfig().getString("logging_plugin").equalsIgnoreCase("logblock")) {
/* 339 */       sender.sendMessage(ChatColor.GREEN + "[XrayInformer] Searching for players with a " + Material.getMaterial(oreid).toString() + " rate higher than " + maxrate + ".");
/* 340 */       playerOreStone = lb.playerLookup(sender, oreid, world);
/*     */     }
/*     */     
/* 343 */     if (plugin.getConfig().getString("logging_plugin").equalsIgnoreCase("coreprotect")) {
/* 344 */       sender.sendMessage(ChatColor.GREEN + "[XrayInformer] Searching for players with a " + Material.getMaterial(oreid).toString() + " rate higher than " + maxrate + ".");
/* 345 */       sender.sendMessage(ChatColor.GREEN + "[XrayInformer] Please be patient, this may take a few minutes as CoreProtect cannot efficiently handle this request.");
/* 346 */       playerOreStone = cp.playerLookup(sender, oreid, world);
/*     */     }
/*     */     
/* 349 */     sender.sendMessage(plugin.msgBorder);
/* 350 */     sender.sendMessage(ChatColor.GREEN + "XrayInformer: All players on " + Material.getMaterial(oreid).toString());
/* 351 */     sender.sendMessage(plugin.msgBorder);
/* 352 */     if (playerOreStone == null) sender.sendMessage(ChatColor.RED + "playerOreStone is null");
/* 353 */     ArrayList<String> preventRepeat = new ArrayList();
/* 354 */     for (Iterator<String[]> itr = playerOreStone.iterator(); itr.hasNext();)
/*     */     {
/* 356 */       String[] row = (String[])itr.next();
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 362 */       if (Integer.valueOf(row[2]).intValue() >= 100)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/* 367 */         float d = (float)(Integer.valueOf(row[1]).intValue() * 100.0D / Integer.valueOf(row[2]).intValue());
/* 368 */         if (d > maxrate)
/*     */         {
/* 370 */           if (!preventRepeat.contains(row[0]))
/* 371 */             if (!banned)
/*     */             {
/* 373 */               if (!org.bukkit.Bukkit.getOfflinePlayer(row[0]).isBanned())
/*     */               {
/* 375 */                 sender.sendMessage(row[0] + " " + d + "%");
/* 376 */                 preventRepeat.add(row[0]);
/*     */               }
/*     */             }
/*     */             else
/*     */             {
/* 381 */               sender.sendMessage(row[0] + " " + d + "%");
/* 382 */               preventRepeat.add(row[0]);
/*     */             } }
/*     */       }
/*     */     }
/* 386 */     sender.sendMessage(plugin.msgBorder);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\XrayInformer.jar!\com\wesley27\xrayinformer\lookups\Checkers.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */