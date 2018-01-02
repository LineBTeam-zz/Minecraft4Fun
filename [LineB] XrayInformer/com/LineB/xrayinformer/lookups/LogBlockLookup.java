/*     */ package com.LineB.xrayinformer.lookups;
/*     */ 
/*     */ import com.LineB.xrayinformer.ClearedPlayerFile;
/*     */ import com.LineB.xrayinformer.Config;
/*     */ import com.LineB.xrayinformer.XrayInformer;
/*     */ import de.diddiz.LogBlock.BlockChange;
/*     */ import de.diddiz.LogBlock.LogBlock;
/*     */ import de.diddiz.LogBlock.QueryParams;
/*     */ import de.diddiz.LogBlock.QueryParams.BlockChangeType;
/*     */ import de.diddiz.LogBlock.QueryParams.SummarizationMode;
/*     */ import de.diddiz.util.Block;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LogBlockLookup
/*     */ {
/*     */   private XrayInformer plugin;
/*     */   
/*     */   public LogBlockLookup()
/*     */   {
/*  37 */     this.plugin = XrayInformer.getInstance();
/*     */   }
/*     */   
/*     */   public int stoneLookup(String player, String world, int hours) throws SQLException {
/*  41 */     LogBlock logBlock = (LogBlock)Bukkit.getServer().getPluginManager().getPlugin("LogBlock");
/*     */     
/*  43 */     QueryParams params = new QueryParams(logBlock);
/*     */     
/*  45 */     params.setPlayer(player);
/*  46 */     params.bct = QueryParams.BlockChangeType.DESTROYED;
/*  47 */     params.limit = -1;
/*  48 */     params.since = (hours * 60);
/*  49 */     params.world = this.plugin.getServer().getWorld(world);
/*     */     
/*  51 */     List<Block> lookupList = new ArrayList();
/*  52 */     lookupList.add(new Block(1, 0));
/*     */     
/*  54 */     params.types = lookupList;
/*  55 */     params.needCount = true;
/*     */     
/*  57 */     int count = logBlock.getCount(params);
/*     */     
/*  59 */     return count;
/*     */   }
/*     */   
/*     */   public int oreLookup(String player, int oreid, String world, int hours) throws SQLException
/*     */   {
/*  64 */     LogBlock logBlock = (LogBlock)this.plugin.getServer().getPluginManager().getPlugin("LogBlock");
/*     */     
/*  66 */     QueryParams params = new QueryParams(logBlock);
/*     */     
/*  68 */     params.setPlayer(player);
/*  69 */     params.bct = QueryParams.BlockChangeType.DESTROYED;
/*  70 */     params.limit = -1;
/*  71 */     params.since = (hours * 60);
/*  72 */     params.world = this.plugin.getServer().getWorld(world);
/*     */     
/*  74 */     List<Block> lookupList = new ArrayList();
/*  75 */     lookupList.add(new Block(Material.getMaterial(oreid).getId(), 0));
/*     */     
/*  77 */     params.types = lookupList;
/*  78 */     params.needCount = true;
/*     */     
/*  80 */     int count = logBlock.getCount(params);
/*     */     
/*  82 */     return count;
/*     */   }
/*     */   
/*     */   public List<String[]> playerLookup(CommandSender sender, int oreid, String world)
/*     */   {
/*  87 */     LogBlock logBlock = (LogBlock)this.plugin.getServer().getPluginManager().getPlugin("LogBlock");
/*     */     
/*  89 */     QueryParams params = new QueryParams(logBlock);
/*     */     
/*  91 */     params.bct = QueryParams.BlockChangeType.DESTROYED;
/*  92 */     params.limit = -1;
/*  93 */     params.world = this.plugin.getServer().getWorld(Config.defaultWorld);
/*     */     
/*  95 */     List<Block> lookupList = new ArrayList();
/*  96 */     lookupList.add(new Block(Material.getMaterial(oreid).getId(), 0));
/*     */     
/*  98 */     params.types = lookupList;
/*  99 */     params.needPlayer = true;
/*     */     
/* 101 */     params.sum = QueryParams.SummarizationMode.PLAYERS;
/*     */     
/* 103 */     List<String[]> namesAndOresList = new ArrayList();
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 108 */       for (BlockChange bc : logBlock.getBlockChanges(params)) {
/* 109 */         String[] nameOreStoneString = new String[3];
/* 110 */         int since = -1;
/* 111 */         if (ClearedPlayerFile.wasPlayerCleared(bc.playerName)) {
/* 112 */           since = ClearedPlayerFile.getHoursFromClear(bc.playerName);
/*     */         }
/*     */         
/* 115 */         nameOreStoneString[0] = bc.playerName;
/* 116 */         nameOreStoneString[1] = Integer.toString(oreLookup(bc.playerName, oreid, world, since));
/* 117 */         nameOreStoneString[2] = Integer.toString(stoneLookup(bc.playerName, world, since));
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 123 */         namesAndOresList.add(nameOreStoneString);
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 136 */     return namesAndOresList;
/*     */   }
/*     */ }

 */