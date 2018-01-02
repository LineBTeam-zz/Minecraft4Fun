/*     */ package com.LineB.xrayinformer.lookups;
/*     */ 
/*     */ import com.LineB.xrayinformer.ClearedPlayerFile;
/*     */ import com.LineB.xrayinformer.XrayInformer;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.coreprotect.CoreProtect;
/*     */ import net.coreprotect.CoreProtectAPI;
/*     */ import net.coreprotect.CoreProtectAPI.ParseResult;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.plugin.Plugin;
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
/*     */ public class CoreProtectLookup
/*     */ {
/*     */   private XrayInformer plugin;
/*  35 */   private CoreProtectAPI cp = null;
/*     */   
/*     */   public CoreProtectLookup() {
/*  38 */     this.plugin = XrayInformer.getInstance();
/*  39 */     this.cp = getCoreProtect();
/*     */   }
/*     */   
/*     */   private CoreProtectAPI getCoreProtect() {
/*  43 */     Plugin co = Bukkit.getServer().getPluginManager().getPlugin("CoreProtect");
/*     */     
/*     */ 
/*  46 */     if ((co == null) || (!(co instanceof CoreProtect))) {
/*  47 */       return null;
/*     */     }
/*     */     
/*     */ 
/*  51 */     CoreProtectAPI CoreProtect = ((CoreProtect)co).getAPI();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  57 */     if (!co.getConfig().getBoolean("api-enabled")) {
/*  58 */       this.plugin.getLogger().log(Level.WARNING, "XrayInformer cannot access CoreProtect records because the CoreProtect API is not enabled. Check your CoreProtect Configuration.");
/*     */       
/*  60 */       return null;
/*     */     }
/*     */     
/*     */ 
/*  64 */     if (CoreProtect.APIVersion() < 4) {
/*  65 */       this.plugin.getLogger().log(Level.WARNING, "XrayInformer cannot access CoreProtect records because you are using an incompatible version of the CoreProtect API. Please check your version of CoreProtect, and then contact the XrayInformer Developer.");
/*     */       
/*  67 */       return null;
/*     */     }
/*     */     
/*  70 */     return CoreProtect;
/*     */   }
/*     */   
/*     */   public int stoneLookup(String player, String world, int hours) throws SQLException {
/*  74 */     int count = 0;
/*     */     
/*  76 */     if (this.cp != null) {
/*  77 */       if (hours == -1)
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  83 */         hours = 2160;
/*     */       }
/*  85 */       int seconds = Math.abs(hours * 3600);
/*  86 */       List<Object> stone = new ArrayList();
/*  87 */       stone.add(Material.STONE);
/*     */       
/*  89 */       List<String[]> lookup = this.cp.performLookup(seconds, Arrays.asList(new String[] { player }), null, stone, null, Arrays.asList(new Integer[] { Integer.valueOf(0) }), 0, null);
/*     */       
/*  91 */       if (lookup != null) {
/*  92 */         for (String[] rows : lookup) {
/*  93 */           CoreProtectAPI.ParseResult row = this.cp.parseResult(rows);
/*  94 */           if (row.worldName().equals(world)) {
/*  95 */             count++;
/*     */           }
/*     */         }
/*  98 */         return count;
/*     */       }
/*     */       
/* 101 */       this.plugin.getLogger().log(Level.WARNING, "[ LineB - Security ] Atividade suspeita identificada:");
/*     */     }
/*     */     
/* 104 */     return count;
/*     */   }
/*     */   
/*     */   public int oreLookup(String player, int oreid, String world, int hours) throws SQLException
/*     */   {
/* 109 */     int count = 0;
/*     */     
/* 111 */     if (this.cp != null) {
/* 112 */       if (hours == -1) {
/* 113 */         hours = 2160;
/*     */       }
/*     */       
/*     */ 
/* 117 */       int seconds = Math.abs(hours * 3600);
/* 118 */       Material ore = Material.getMaterial(oreid);
/*     */       
/* 120 */       List<String[]> lookup = this.cp.performLookup(seconds, Arrays.asList(new String[] { player }), null, Arrays.asList(new Object[] { ore }), null, Arrays.asList(new Integer[] { Integer.valueOf(0) }), 0, null);
/*     */       
/* 122 */       if (lookup != null) {
/* 123 */         for (String[] rows : lookup) {
/* 124 */           CoreProtectAPI.ParseResult row = this.cp.parseResult(rows);
/* 125 */           if (row.worldName().equals(world)) {
/* 126 */             if ((oreid == 56) && (row.getY() < 17)) {
/* 127 */               count++;
/*     */             }
/* 129 */             if ((oreid == 14) && (row.getY() < 33)) {
/* 130 */               count++;
/*     */             }
/* 132 */             if ((oreid == 21) && (row.getY() < 32)) {
/* 133 */               count++;
/*     */             }
/* 135 */             if ((oreid == 129) && (row.getY() < 33)) {
/* 136 */               count++;
/*     */             }
/* 138 */             if ((oreid == 15) || (oreid == 48)) {
/* 139 */               count++;
/*     */             }
/*     */           }
/*     */         }
/* 143 */         return count;
/*     */       }
/*     */       
/* 146 */       this.plugin.getLogger().log(Level.WARNING, "[ LineB - Security ] Atividade suspeita identificada:");
/*     */     }
/*     */     
/* 149 */     return count;
/*     */   }
/*     */   
/*     */   public List<String[]> playerLookup(CommandSender sender, int oreid, String world)
/*     */   {
/* 154 */     if (this.cp != null) {
/* 155 */       Material ore = Material.getMaterial(oreid);
/*     */       
/* 157 */       OfflinePlayer[] allPlayers = Bukkit.getOfflinePlayers();
/* 158 */       List<String> users = new ArrayList();
/* 159 */       Object localObject1; int j = (localObject1 = allPlayers).length; for (int i = 0; i < j; i++) { OfflinePlayer p = localObject1[i];
/* 160 */         users.add(p.getName());
/*     */       }
/*     */       
/* 163 */       List<String[]> lookup = this.cp.performLookup(172800, users, null, Arrays.asList(new Object[] { ore }), null, Arrays.asList(new Integer[] { Integer.valueOf(0) }), 0, null);
/* 164 */       Object namesAndOresList = new ArrayList();
/*     */       
/* 166 */       if (lookup != null) {
/*     */         try {
/* 168 */           for (localObject1 = lookup.iterator(); ((Iterator)localObject1).hasNext();) { String[] rows = (String[])((Iterator)localObject1).next();
/* 169 */             CoreProtectAPI.ParseResult result = this.cp.parseResult(rows);
/* 170 */             String[] nameOreStoneString = new String[3];
/* 171 */             int hours = -1;
/* 172 */             if (ClearedPlayerFile.wasPlayerCleared(result.getPlayer())) {
/* 173 */               hours = ClearedPlayerFile.getHoursFromClear(result.getPlayer());
/*     */             }
/*     */             
/* 176 */             nameOreStoneString[0] = result.getPlayer();
/* 177 */             nameOreStoneString[1] = Integer.toString(oreLookup(result.getPlayer(), oreid, world, hours));
/* 178 */             nameOreStoneString[2] = Integer.toString(stoneLookup(result.getPlayer(), world, hours));
/*     */             
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 184 */             ((List)namesAndOresList).add(nameOreStoneString);
/*     */ 
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */ 
/* 194 */           e.printStackTrace();
/*     */         }
/* 196 */         return (List<String[]>)namesAndOresList;
/*     */       }
/*     */     }
/* 199 */     return null;
/*     */   }
/*     */ }