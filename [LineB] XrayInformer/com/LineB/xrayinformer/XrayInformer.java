/*     */ package com.LineB.xrayinformer;
/*     */ 
/*     */ import de.diddiz.LogBlock.LogBlock;
/*     */ import java.util.logging.Logger;
/*     */ import net.coreprotect.CoreProtect;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class XrayInformer
/*     */   extends JavaPlugin
/*     */ {
/*  23 */   public final Config config = new Config(this);
/*  24 */   boolean banned = false;
/*     */   private static XrayInformer instance;
/*     */   private String version;
/*  27 */   public final String msgBorder = ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + 
/*  28 */     ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + 
/*  29 */     ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + 
/*  30 */     ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + 
/*  31 */     ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + 
/*  32 */     ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + 
/*  33 */     ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + 
/*  34 */     ChatColor.DARK_GREEN + "-" + ChatColor.GREEN + "-" + ChatColor.DARK_GREEN + "-";
/*     */   
/*     */ 
/*     */   public void onEnable()
/*     */   {
/*  39 */     instance = this;
/*     */     
/*  41 */     this.config.load();
/*  42 */     detectLogger("CoreProtect");
/*  43 */     ClearedPlayerFile.loadClearedPlayers();
/*     */     
/*  45 */     getServer().getPluginManager().registerEvents(new Listeners(), this);
/*  46 */     getCommand("xcheck").setExecutor(new Cmd());
/*     */     
/*  48 */     PluginDescriptionFile pdfFile = getDescription();
/*  49 */     this.version = pdfFile.getVersion();
/*  50 */     getLogger().info("XrayInformer v" + this.version + " enabled.");
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDisable()
/*     */   {
/*  56 */     getLogger().info("XrayInformer disabled");
/*     */   }
/*     */   
/*     */   public static XrayInformer getInstance() {
/*  60 */     return instance;
/*     */   }
/*     */   
/*     */   private void detectLogger(String loggingPlugin) {
/*  64 */     Plugin p = getServer().getPluginManager().getPlugin(loggingPlugin);
/*     */     String str;
/*  66 */     switch ((str = loggingPlugin).hashCode()) {case 919627280:  if (str.equals("CoreProtect")) break; break; case 2051342281:  if (!str.equals("LogBlock")) {
/*     */         return;
/*  68 */         if ((p == null) || (!(p instanceof CoreProtect))) {
/*  69 */           detectLogger("LogBlock");
/*  70 */           return;
/*     */         }
/*  72 */         this.config.setLogger(loggingPlugin);
/*     */       }
/*     */       else {
/*  75 */         if ((p == null) || (!(p instanceof LogBlock)))
/*     */         {
/*     */ 
/*     */ 
/*  79 */           getLogger().severe("Neither CoreProtect or LogBlock has been detected. Disabling XrayInformer.");
/*  80 */           p = getServer().getPluginManager().getPlugin("XrayInformer");
/*  81 */           getServer().getPluginManager().disablePlugin(p);
/*     */         }
/*  83 */         this.config.setLogger(loggingPlugin);
/*     */       }
/*     */       break;
/*     */     }
/*     */   }
/*     */   
/*     */   public void showInfo(CommandSender sender) {
/*  90 */     sender.sendMessage(this.msgBorder);
/*  91 */     sender.sendMessage(ChatColor.GREEN + "XrayInformer v" + this.version + " feito pela LineB");
/*  92 */     sender.sendMessage(this.msgBorder);
/*  93 */     sender.sendMessage(ChatColor.YELLOW + "/xcheck <player name> " + ChatColor.WHITE + "- Calculates a player's xray probability");
/*  94 */     sender.sendMessage(ChatColor.YELLOW + "/xcheck clear <player name> " + ChatColor.WHITE + "- Clears a player's xray stats");
/*  95 */     sender.sendMessage(ChatColor.YELLOW + "/xcheck reload " + ChatColor.WHITE + "- Reloads the config");
/*  96 */     sender.sendMessage(ChatColor.YELLOW + "/xcheck help " + ChatColor.WHITE + "- Displays more detailed command usage");
/*  97 */     sender.sendMessage(this.msgBorder);
/*     */   }
/*     */   
/*     */   public void showHelp(CommandSender sender)
/*     */   {
/* 102 */     sender.sendMessage(this.msgBorder);
/* 103 */     sender.sendMessage(ChatColor.GREEN + "XrayInformer Command Usage");
/* 104 */     sender.sendMessage(this.msgBorder);
/* 105 */     sender.sendMessage(ChatColor.YELLOW + "/xcheck " + ChatColor.WHITE + "<player name>|all " + ChatColor.RED + " [required]");
/* 106 */     sender.sendMessage(ChatColor.YELLOW + "/xcheck " + ChatColor.WHITE + "world:<world name> " + ChatColor.GREEN + " [optional]");
/* 107 */     sender.sendMessage(ChatColor.YELLOW + "/xcheck " + ChatColor.WHITE + "ore:<ore id #> " + ChatColor.GREEN + " [optional, required on player:all]");
/* 108 */     sender.sendMessage(ChatColor.YELLOW + "/xcheck " + ChatColor.WHITE + "maxrate:<percentage> " + ChatColor.RED + " [required on player:all]");
/* 109 */     sender.sendMessage(ChatColor.YELLOW + "/xcheck " + ChatColor.WHITE + "since:<time in hours> " + ChatColor.GREEN + " [optional]");
/* 110 */     sender.sendMessage(ChatColor.YELLOW + "/xcheck " + ChatColor.WHITE + "banned:true " + ChatColor.GREEN + " [optional], hides banned players from /xcheck all");
/* 111 */     sender.sendMessage(ChatColor.YELLOW + "example: " + ChatColor.WHITE + "/xcheck guestplayer123 world:farm ore:14 since:12");
/* 112 */     sender.sendMessage(ChatColor.YELLOW + "example for mass check: " + ChatColor.WHITE + "/xcheck all ore:56 maxrate:3");
/* 113 */     sender.sendMessage(this.msgBorder);
/*     */   }
/*     */   
/*     */   public void clearPlayer(CommandSender sender, String player) throws Exception
/*     */   {
/* 118 */     ClearedPlayerFile.clearPlayer(player);
/*     */     
/* 120 */     sender.sendMessage(ChatColor.RED + "[ LineB - Security ]" + ChatColor.WHITE + " O status do Xray do jogador " + player + " foi limpo.");
/*     */   }
/*     */ }


