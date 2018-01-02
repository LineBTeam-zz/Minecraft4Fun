/*     */ package com.LineB.xrayinformer;
/*     */ 
/*     */ import com.LineB.xrayinformer.lookups.CoreProtectLookup;
/*     */ import com.LineB.xrayinformer.lookups.LogBlockLookup;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ 
/*     */ public class Listeners implements org.bukkit.event.Listener
/*     */ {
/*     */   private XrayInformer plugin;
/*     */   
/*     */   public Listeners()
/*     */   {
/*  21 */     this.plugin = XrayInformer.getInstance();
/*     */   }
/*     */   
/*  24 */   private LogBlockLookup lb = new LogBlockLookup();
/*  25 */   private CoreProtectLookup cp = new CoreProtectLookup();
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerJoin(final PlayerJoinEvent evt) {
/*  29 */     Player playName = evt.getPlayer();
/*     */     
/*  31 */     if ((this.plugin.config.isActive("checkOnPlayerJoin")) && (
/*  32 */       (!playName.hasPermission("xcheck.bypasscheck")) || (!playName.isOp())))
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 171 */       new org.bukkit.scheduler.BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*  37 */           String playerName = evt.getPlayer().getName();
/*  38 */           String world = Config.defaultWorld;
/*  39 */           int hours = -1;
/*     */           try
/*     */           {
/*  42 */             if (ClearedPlayerFile.wasPlayerCleared(playerName)) {
/*  43 */               hours = ClearedPlayerFile.getHoursFromClear(playerName);
/*     */             }
/*     */             
/*  46 */             int level = 0;
/*     */             
/*  48 */             int count_stone = 0;
/*     */             
/*  50 */             int diamond_count = 0;
/*  51 */             int gold_count = 0;
/*  52 */             int lapis_count = 0;
/*  53 */             int iron_count = 0;
/*  54 */             int mossy_count = 0;
/*  55 */             int emerald_count = 0;
/*     */             
/*  57 */             if (Listeners.this.plugin.getConfig().getString("logging_plugin").equalsIgnoreCase("logblock")) {
/*  58 */               count_stone = Listeners.this.lb.stoneLookup(playerName, world, hours);
/*     */               
/*  60 */               diamond_count = Listeners.this.lb.oreLookup(playerName, 56, world, hours);
/*  61 */               gold_count = Listeners.this.lb.oreLookup(playerName, 14, world, hours);
/*  62 */               lapis_count = Listeners.this.lb.oreLookup(playerName, 21, world, hours);
/*  63 */               iron_count = Listeners.this.lb.oreLookup(playerName, 15, world, hours);
/*  64 */               mossy_count = Listeners.this.lb.oreLookup(playerName, 48, world, hours);
/*  65 */               emerald_count = Listeners.this.lb.oreLookup(playerName, 129, world, hours);
/*     */             }
/*  67 */             if (Listeners.this.plugin.getConfig().getString("logging_plugin").equalsIgnoreCase("coreprotect")) {
/*  68 */               count_stone = Listeners.this.cp.stoneLookup(playerName, world, hours);
/*     */               
/*  70 */               diamond_count = Listeners.this.cp.oreLookup(playerName, 56, world, hours);
/*  71 */               gold_count = Listeners.this.cp.oreLookup(playerName, 14, world, hours);
/*  72 */               lapis_count = Listeners.this.cp.oreLookup(playerName, 21, world, hours);
/*  73 */               iron_count = Listeners.this.cp.oreLookup(playerName, 15, world, hours);
/*  74 */               mossy_count = Listeners.this.cp.oreLookup(playerName, 48, world, hours);
/*  75 */               emerald_count = Listeners.this.cp.oreLookup(playerName, 129, world, hours);
/*     */             }
/*     */             
/*  78 */             String dia = "";
/*  79 */             String gld = "";
/*  80 */             String lap = "";
/*  81 */             String emr = "";
/*  82 */             String irn = "";
/*  83 */             String msy = "";
/*     */             
/*  85 */             if ((Listeners.this.plugin.config.isActive("diamond")) && (diamond_count > 0))
/*     */             {
/*  87 */               float d = (float)(diamond_count * 100.0D / count_stone);
/*  88 */               if (d > Listeners.this.plugin.config.getRate("confirmed", "diamond"))
/*     */               {
/*  90 */                 dia = "diamond, ";
/*     */               }
/*  92 */               level = (int)(level + d * 10.0F);
/*     */             }
/*     */             
/*  95 */             if ((Listeners.this.plugin.config.isActive("gold")) && (gold_count > 0))
/*     */             {
/*  97 */               float d = (float)(gold_count * 100.0D / count_stone);
/*  98 */               if (d > Listeners.this.plugin.config.getRate("confirmed", "gold"))
/*     */               {
/* 100 */                 gld = "gold, ";
/*     */               }
/* 102 */               level = (int)(level + d * 3.0F);
/*     */             }
/*     */             
/* 105 */             if ((Listeners.this.plugin.config.isActive("lapis")) && (lapis_count > 0))
/*     */             {
/* 107 */               float d = (float)(lapis_count * 100.0D / count_stone);
/* 108 */               if (d > Listeners.this.plugin.config.getRate("confirmed", "lapis"))
/*     */               {
/* 110 */                 lap = "lapis, ";
/*     */               }
/* 112 */               level = (int)(level + d * 10.0F);
/*     */             }
/*     */             
/* 115 */             if ((Listeners.this.plugin.config.isActive("emerald")) && (emerald_count > 0))
/*     */             {
/* 117 */               float d = (float)(emerald_count * 100.0D / count_stone);
/* 118 */               if (d > Listeners.this.plugin.config.getRate("confirmed", "emerald"))
/*     */               {
/* 120 */                 emr = "emerald, ";
/*     */               }
/* 122 */               level = (int)(level + d * 10.0F);
/*     */             }
/*     */             
/* 125 */             if ((Listeners.this.plugin.config.isActive("iron")) && (iron_count > 0))
/*     */             {
/* 127 */               float d = (float)(iron_count * 100.0D / count_stone);
/* 128 */               if (d > Listeners.this.plugin.config.getRate("confirmed", "iron"))
/*     */               {
/* 130 */                 irn = "iron, ";
/*     */               }
/* 132 */               level = (int)(level + d * 1.0F);
/*     */             }
/*     */             
/* 135 */             if ((Listeners.this.plugin.config.isActive("mossy")) && (mossy_count > 0))
/*     */             {
/* 137 */               float d = (float)(mossy_count * 100.0D / count_stone);
/* 138 */               if (d > Listeners.this.plugin.config.getRate("confirmed", "mossy"))
/*     */               {
/* 140 */                 msy = "mossy, ";
/*     */               }
/* 142 */               level = (int)(level + d * 7.0F);
/*     */             }
/*     */             
/* 145 */             if (count_stone < 500)
/*     */             {
/* 147 */               level = (int)(level * 0.5D);
/*     */             }
/* 149 */             else if (count_stone > 1000)
/*     */             {
/* 151 */               level *= 2;
/*     */             }
/*     */             
/* 154 */             if ((dia != "") || (gld != "") || (lap != "") || (emr != "") || (irn != "") || (msy != ""))
/*     */             {
/* 156 */               for (Player staff : Listeners.this.plugin.getServer().getOnlinePlayers())
/*     */               {
/* 158 */                 if ((staff.hasPermission("xcheck.receive")) || (staff.isOp()))
/*     */                 {
/* 160 */                   staff.sendMessage(ChatColor.RED + "[ LineB - Security ] Jogador " + playerName + " obteve muitos " + dia + gld + lap + emr + irn + msy + " e pode ser um Cheater.");
/* 161 */                   Listeners.this.plugin.getLogger().info("[ LineB - Security ] Jogador  " + playerName + " obteve muitos " + dia + gld + lap + emr + irn + msy + " e pode ser um Cheater.");
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 168 */             e.printStackTrace();
/*     */           }
/*     */         }
/* 171 */       }.runTaskAsynchronously(this.plugin);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onOreBreak(BlockBreakEvent event)
/*     */   {
/* 178 */     String player = event.getPlayer().getName();
/* 179 */     Material block = event.getBlock().getType();
/* 180 */     if ((block != Material.DIAMOND_ORE) && (block != Material.IRON_ORE) && (block != Material.GOLD_ORE) && (block != Material.LAPIS_ORE) && 
/* 181 */       (block != Material.EMERALD_ORE) && (block != Material.MOSSY_COBBLESTONE)) {
/* 182 */       return;
/*     */     }
/* 184 */     if ((block == Material.IRON_ORE) && (this.plugin.getConfig().getBoolean("logOreBreaks.iron"))) {
/* 185 */       for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
/* 186 */         if (staff.hasPermission("xcheck.receive")) {
/* 187 */           staff.sendMessage(ChatColor.RED + "[ LineB-Security ] " + ChatColor.AQUA + player + " minerou Ferro.");
/*     */         }
/*     */       }
/*     */     }
/* 191 */     if ((block == Material.GOLD_ORE) && (this.plugin.getConfig().getBoolean("logOreBreaks.gold"))) {
/* 192 */       for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
/* 193 */         if (staff.hasPermission("xcheck.receive")) {
/* 194 */           staff.sendMessage(ChatColor.RED + "[ LineB-Security ] " + ChatColor.AQUA + player + " minerou Ouro.");
/*     */         }
/*     */       }
/*     */     }
/* 198 */     if ((block == Material.LAPIS_ORE) && (this.plugin.getConfig().getBoolean("logOreBreaks.lapis"))) {
/* 199 */       for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
/* 200 */         if (staff.hasPermission("xcheck.receive")) {
/* 201 */           staff.sendMessage(ChatColor.RED + "[ LineB-Security ] " + ChatColor.AQUA + player + " minerou Lápis.");
/*     */         }
/*     */       }
/*     */     }
/* 205 */     if ((block == Material.EMERALD_ORE) && (this.plugin.getConfig().getBoolean("logOreBreaks.emerald"))) {
/* 206 */       for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
/* 207 */         if (staff.hasPermission("xcheck.receive")) {
/* 208 */           staff.sendMessage(ChatColor.RED + "[ LineB-Security ] " + ChatColor.AQUA + player + " minerou Esmeraldas.");
/*     */         }
/*     */       }
/*     */     }
/* 212 */     if ((block == Material.DIAMOND_ORE) && (this.plugin.getConfig().getBoolean("logOreBreaks.diamond"))) {
/* 213 */       for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
/* 214 */         if (staff.hasPermission("xcheck.receive")) {
/* 215 */           staff.sendMessage(ChatColor.RED + "[ LineB-Security ] " + ChatColor.AQUA + player + " minerou Diamantes.");
/*     */         }
/*     */       }
/*     */     }
/* 219 */     if ((block == Material.MOSSY_COBBLESTONE) && (this.plugin.getConfig().getBoolean("logOreBreaks.mossy"))) {
/* 220 */       for (Player staff : this.plugin.getServer().getOnlinePlayers()) {
/* 221 */         if (staff.hasPermission("xcheck.receive")) {
/* 222 */           staff.sendMessage(ChatColor.RED + "[ LineB-Security ] " + ChatColor.AQUA + player + " minerou Mossy.");
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ }


