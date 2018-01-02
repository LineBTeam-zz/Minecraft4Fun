/*     */ package com.LineB.xrayinformer;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ 
/*     */ 
/*     */ public class ClearedPlayerFile
/*     */ {
/*     */   private static XrayInformer plugin;
/*  16 */   private static File clearedPlayers = new File("plugins/XrayInformer/" + File.separator + "ClearedPlayers.yml");
/*  17 */   private static FileConfiguration cpinfo = YamlConfiguration.loadConfiguration(clearedPlayers);
/*  18 */   private static List<String> cpList = new ArrayList();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void loadClearedPlayers()
/*     */   {
/*  25 */     plugin = XrayInformer.getInstance();
/*  26 */     if (!clearedPlayers.exists()) {
/*     */       try {
/*  28 */         clearedPlayers.createNewFile();
/*  29 */         cpinfo.set("cleared_players", cpList);
/*     */       }
/*     */       catch (IOException localIOException) {}
/*     */     }
/*     */     
/*     */ 
/*  35 */     saveClearedPlayers();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void saveClearedPlayers()
/*     */   {
/*     */     try
/*     */     {
/*  45 */       cpinfo.save(clearedPlayers);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  49 */       plugin.getLogger().log(Level.SEVERE, "[Error] An error was encountered while trying to save the ClearedPlayers file.");
/*  50 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void clearPlayer(String player)
/*     */   {
/*  60 */     cpList = cpinfo.getStringList("cleared_players");
/*  61 */     String clearString = player + " " + System.currentTimeMillis();
/*  62 */     cpList.add(clearString);
/*  63 */     cpinfo.set("cleared_players", cpList);
/*  64 */     saveClearedPlayers();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static boolean wasPlayerCleared(String player)
/*     */   {
/*  74 */     cpList = cpinfo.getStringList("cleared_players");
/*  75 */     for (String s : cpList) {
/*  76 */       if (s.contains(player)) {
/*  77 */         return true;
/*     */       }
/*     */     }
/*  80 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static int getHoursFromClear(String player)
/*     */   {
/*  90 */     long clearTime = 0L;
/*  91 */     cpList = cpinfo.getStringList("cleared_players");
/*  92 */     for (String s : cpList) {
/*  93 */       if (s.contains(player)) {
/*  94 */         String[] temp = s.split(" ");
/*  95 */         clearTime = Long.valueOf(temp[1]).longValue();
/*     */       }
/*     */     }
/*  98 */     long now = System.currentTimeMillis();
/*  99 */     long difference = (now - clearTime) / 1000L / 60L;
/* 100 */     int hours = (int)(difference / 60L);
/* 101 */     if (hours < 1) {
/* 102 */       hours = 1;
/*     */     }
/* 104 */     return hours;
/*     */   }
/*     */ }


