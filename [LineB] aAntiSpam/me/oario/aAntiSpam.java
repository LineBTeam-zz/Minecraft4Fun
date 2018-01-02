/*     */ package me.oario;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class aAntiSpam extends org.bukkit.plugin.java.JavaPlugin implements Listener
/*     */ {
/*     */   public void onEnable()
/*     */   {
/*  20 */     loadConfig();
/*  21 */     org.bukkit.Bukkit.getPluginManager().registerEvents(this, this);
/*  22 */     time = Integer.valueOf(getConfig().getInt("aAntiSpam.Time"));
/*  23 */     message = getConfig().getString("aAntiSpam.Message");
/*  24 */     logger.log(Level.INFO, "aAntiSpam activated.");
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*  28 */     saveConfig();
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*     */   {
/*  33 */     CommandSender p = sender;
/*  34 */     if (cmd.getName().equalsIgnoreCase("aantispam")) {
/*  35 */       if ((!p.hasPermission("aantispam.op")) || (!p.isOp())) {
/*  36 */         p.sendMessage("§6[ Anti-Spam ]§bVocê não possui permissão!");
/*  37 */         return true;
/*     */       }
/*  39 */       if (args.length == 0) {
/*  40 */         p.sendMessage("§6[ Anti-Spam ]§b/aantispam reload");
/*  41 */         p.sendMessage("§6[ Anti-Spam ]§b/aantispam set <Tempo em segundos>");
/*  42 */         return true; }
/*  43 */       if (args[0].equalsIgnoreCase("reload")) {
/*  44 */         reloadConfig();
/*  45 */         saveConfig();
/*  46 */         p.sendMessage("§6[ Anti-Spam ]§bConfiguração recarregada !");
/*  47 */         time = Integer.valueOf(getConfig().getInt("aAntiSpam.Time"));
/*  48 */         message = getConfig().getString("aAntiSpam.Message");
/*  49 */         return true; }
/*  50 */       if (args[0].equalsIgnoreCase("set"))
/*     */       {
/*     */         try {
/*  53 */           i = Integer.valueOf(Integer.parseInt(args[1]));
/*     */         } catch (Exception ex) { Integer i;
/*  55 */           p.sendMessage("§6[ Anti-Spam ]§b" + args[1] + " não é um número.");
/*  56 */           return true; }
/*     */         Integer i;
/*  58 */         p.sendMessage("§6[ Anti-Spam ]§bTime alterado para " + i + " !");
/*  59 */         getConfig().set("aAntiSpam.Time", i);
/*  60 */         chat.clear();
/*  61 */         saveConfig();
/*  62 */         reloadConfig();
/*  63 */         time = Integer.valueOf(getConfig().getInt("aAntiSpam.Time"));
/*  64 */         message = getConfig().getString("aAntiSpam.Message");
/*  65 */         return true;
/*     */       }
/*  67 */       p.sendMessage("§6[ Anti-Spam ]§b/aantispam reload");
/*  68 */       p.sendMessage("§6[ Anti-Spam ]§b/aantispam set <Tempo em Segundos>");
/*  69 */       return true;
/*     */     }
/*     */     
/*  72 */     return false;
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.NORMAL)
/*     */   public void aAntiSpam(AsyncPlayerChatEvent e) {
/*  77 */     Player p = e.getPlayer();
/*  78 */     if ((!p.hasPermission("aantispam.ignore")) || (!p.isOp())) {
/*  79 */       Long atime = Long.valueOf(System.currentTimeMillis());
/*  80 */       if (chat.containsKey(p.getName())) {
/*  81 */         Long now = (Long)chat.get(p.getName());
/*  82 */         if (now.longValue() > atime.longValue()) {
/*  83 */           p.sendMessage(message.replace("&", "§").replace("%time%", time.toString()));
/*  84 */           e.setCancelled(true);
/*     */         } else {
/*  86 */           chat.remove(p.getName());
/*  87 */           chat.put(p.getName(), Long.valueOf(atime.longValue() + time.intValue() * 1000));
/*     */         }
/*     */       } else {
/*  90 */         chat.put(p.getName(), Long.valueOf(atime.longValue() + time.intValue() * 1000));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*  95 */   public static final Logger logger = Logger.getLogger("Minecraft");
/*  96 */   public static HashMap<String, Long> chat = new HashMap();
/*  97 */   public static Integer time = Integer.valueOf(3);
/*  98 */   public static String message = "§bVocê só pode escrever a cada §e%time% &bsegundos.";
/*     */   
/*     */   public void loadConfig() {
/* 101 */     getConfig().addDefault("aAntiSpam.Time", Integer.valueOf(3));
/* 102 */     getConfig().addDefault("aAntiSpam.Message", "&bVocê só pode escreve a cada &e%time% &bsegundos.");
/* 103 */     getConfig().options().copyDefaults(true);
/* 104 */     saveConfig();
/*     */   }
/*     */ }