/*     */ package com.certox;
/*     */ 
/*     */ import com.evilmidget38.UUIDFetcher;
/*     */ import java.io.PrintStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.URL;
/*     */ import java.sql.SQLException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Scanner;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
/*     */ import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent.Result;
/*     */ import org.bukkit.event.server.ServerListPingEvent;
/*     */ 
/*     */ public class Core extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener
/*     */ {
/*  27 */   public MySQL db = new MySQL();
/*  28 */   Core plugin = this;
/*     */   
/*  30 */   Boolean forceMode = Boolean.valueOf(false);
/*  31 */   String forceModeMsg = "";
/*     */   
/*  33 */   Boolean DownloadBlacklist = Boolean.valueOf(true);
/*     */   
/*  35 */   Boolean serverListPing = Boolean.valueOf(false);
/*  36 */   String serverListPingMsg = "";
/*     */   
/*  38 */   Boolean offlineMode = Boolean.valueOf(true);
/*     */   
/*  40 */   String kickMsg = "";
/*     */   
/*  42 */   boolean enabled = true;
/*  43 */   boolean debug = false;
/*     */   
/*  45 */   private Map<String, Object> activeBlacklist = new HashMap();
/*     */   
/*     */ 
/*     */ 
/*     */   public void onLoad()
/*     */   {
/*  51 */     getConfig().addDefault("AntiJoinBot.MySQL.Offline", Boolean.valueOf(true));
/*  52 */     getConfig().addDefault("AntiJoinBot.MySQL.Host", "localhost");
/*  53 */     getConfig().addDefault("AntiJoinBot.MySQL.Port", Integer.valueOf(3306));
/*  54 */     getConfig().addDefault("AntiJoinBot.MySQL.Database", "ajb_intelligent_blacklist");
/*  55 */     getConfig().addDefault("AntiJoinBot.MySQL.User", "<username>");
/*  56 */     getConfig().addDefault("AntiJoinBot.MySQL.Password", "<password>");
/*     */     
/*  58 */     this.activeBlacklist.put("http://www,stopforumspam,com/api?ip=", "yes");
/*  59 */     this.activeBlacklist.put("http://www,shroomery,org/ythan/proxycheck,php?ip=", "Y");
/*     */     
/*  61 */     getConfig().addDefault("AntiJoinBot.Blacklists", this.activeBlacklist);
/*  62 */     getConfig().addDefault("AntiJoinBot.DownloadBlacklist.Enabled", Boolean.valueOf(true));
/*     */     
/*  64 */     getConfig().addDefault("AntiJoinBot.ServerListPing.Enabled", Boolean.valueOf(false));
/*  65 */     getConfig().addDefault("AntiJoinBot.ServerListPing.Message", "Desative o Proxy");
/*     */     
/*  67 */     getConfig().addDefault("AntiJoinBot.Force.Enabled", Boolean.valueOf(false));
/*  68 */     getConfig().addDefault("AntiJoinBot.Force.Message", "Verificação de Proxy, por favor, re-entre!");
/*     */     
/*  70 */     getConfig().addDefault("AntiJoinBot.Kick.Message", "Proxy Detectado. (Desative/Reinicie o roteador)");
/*     */     
/*  72 */     getConfig().addDefault("AntiJoinBot.Warmup.Enabled", Boolean.valueOf(true));
/*  73 */     getConfig().addDefault("AntiJoinBot.Warmup.Seconds", Integer.valueOf(60));
/*     */     
/*  75 */     getConfig().addDefault("AntiJoinBot.OfflineMode.Enabled", Boolean.valueOf(true));
/*  76 */     getConfig().addDefault("AntiJoinBot.OfflineMode.MigrateUUID", Boolean.valueOf(false));
/*     */     
/*  78 */     getConfig().addDefault("AntiJoinBot.Debug.Enabled", Boolean.valueOf(false));
/*     */     
/*  80 */     getConfig().options().copyDefaults(true);
/*  81 */     saveConfig();
/*  82 */     reloadConfig();
/*     */     
/*  84 */     this.debug = getConfig().getBoolean("AntiJoinBot.Debug.Enabled");
/*     */     
/*  86 */     this.offlineMode = Boolean.valueOf(getConfig().getBoolean("AntiJoinBot.OfflineMode.Enabled"));
/*     */     
/*  88 */     this.serverListPing = Boolean.valueOf(getConfig().getBoolean("AntiJoinBot.ServerListPing.Enabled"));
/*  89 */     this.serverListPingMsg = getConfig().getString("AntiJoinBot.ServerListPing.Message");
/*     */     
/*  91 */     this.forceMode = Boolean.valueOf(getConfig().getBoolean("AntiJoinBot.Force.Enabled"));
/*  92 */     this.forceModeMsg = getConfig().getString("AntiJoinBot.Force.Message");
/*     */     
/*  94 */     this.kickMsg = getConfig().getString("AntiJoinBot.Kick.Message");
/*     */     
/*  96 */     this.activeBlacklist = getConfig().getConfigurationSection("AntiJoinBot.Blacklists").getValues(false);
/*  97 */     this.DownloadBlacklist = Boolean.valueOf(getConfig().getBoolean("AntiJoinBot.DownloadBlacklist.Enabled"));
/*     */   }
/*     */   
/*     */   public void onEnable()
/*     */   {
/*     */     try
/*     */     {
/* 104 */       this.db.connect(getConfig().getString("AntiJoinBot.MySQL.Host"), getConfig().getInt("AntiJoinBot.MySQL.Port"), getConfig().getString("AntiJoinBot.MySQL.Database"), getConfig().getString("AntiJoinBot.MySQL.User"), getConfig().getString("AntiJoinBot.MySQL.Password"), getConfig().getBoolean("AntiJoinBot.MySQL.Offline"));
/* 105 */       if (this.offlineMode.booleanValue()) {
/* 106 */         this.db.initDBOffline();
/*     */       } else {
/* 108 */         this.db.initDB();
/*     */       }
/*     */     } catch (ClassNotFoundException e) {
/* 111 */       System.out.println(">>>>>>>>>>>> MySQL DRIVER NOT FOUND <<<<<<<<<<<<");
/* 112 */       e.printStackTrace();
/*     */     } catch (SQLException e) {
/* 114 */       e.printStackTrace();
/*     */     }
/*     */     
/* 117 */     if ((getConfig().getBoolean("AntiJoinBot.OfflineMode.MigrateUUID")) && (!this.offlineMode.booleanValue())) {
/*     */       try {
/* 119 */         this.db.alterUUID();
/* 120 */       } catch (Exception e) { if (this.debug) {
/* 121 */           debug("[DB] Erro com a estrutura do banco de dados:");
/* 122 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       try {
/* 126 */         this.db.migrateUUID();
/* 127 */         this.db.closeConnection();
/* 128 */         this.db.connect(getConfig().getString("AntiJoinBot.MySQL.Host"), getConfig().getInt("AntiJoinBot.MySQL.Port"), getConfig().getString("AntiJoinBot.MySQL.Database"), getConfig().getString("AntiJoinBot.MySQL.User"), getConfig().getString("AntiJoinBot.MySQL.Password"), getConfig().getBoolean("AntiJoinBot.MySQL.Offline"));
/* 129 */         this.db.initDB();
/* 130 */       } catch (Exception e) { if (this.debug) {
/* 131 */           debug("[DB] Erro ao migrar banco de dados:");
/* 132 */           e.printStackTrace();
/*     */         } else {
/* 134 */           System.out.println("[AJB] Erro ao migrar Banco de Dados. Ative o Debug para mais informações:");
/*     */         }
/*     */       }
/* 137 */       System.out.println("[AJB] Database migrado com sucesso.");
/* 138 */       getConfig().set("AntiJoinBot.OfflineMode.MigrateUUID", Boolean.valueOf(false));
/* 139 */       saveConfig();
/*     */     }
/*     */     
/* 142 */     if (getConfig().getBoolean("AntiJoinBot.Warmup.Enabled")) {
/* 143 */       System.out.println("AntiJoinBot enabled in " + getConfig().getInt("AntiJoinBot.Warmup.Seconds") + " seconds");
/* 144 */       getServer().getScheduler().runTaskLaterAsynchronously(this, new Runnable() {
/*     */         public void run() {
/* 146 */           System.out.println("AntiJoinBot está ativo");
/* 147 */           org.bukkit.Bukkit.getPluginManager().registerEvents(Core.this.plugin, Core.this.plugin); } }, getConfig().getInt("AntiJoinBot.Warmup.Seconds") * 20);
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*     */ 
/* 153 */       org.bukkit.Bukkit.getPluginManager().registerEvents(this.plugin, this.plugin);
/*     */     }
/*     */     try
/*     */     {
/* 157 */       if (this.offlineMode.booleanValue()) {
/* 158 */         this.db.loadDBOfflinetoRAM();
/*     */       } else {
/* 160 */         this.db.loadDBtoRAM();
/*     */       }
/*     */     } catch (SQLException e) {
/* 163 */       e.printStackTrace();
/*     */     }
/*     */     
/* 166 */     if (this.DownloadBlacklist.booleanValue()) {
/*     */       try {
/* 168 */         Scanner Blacklist = new Scanner(new URL("http://myip.ms/files/blacklist/csf/latest_blacklist.txt").openStream());
/* 169 */         System.out.println("[AJB] Atualizando Blacklist...");
/* 170 */         this.db.startTransaction();
/* 171 */         while (Blacklist.hasNextLine()) {
/* 172 */           String IP = Blacklist.nextLine();
/* 173 */           if ((IP.matches("\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}")) && (!this.db.ipBlacklist.containsKey(IP))) {
/*     */             try {
/* 175 */               this.db.setIP(IP, true);
/*     */             } catch (Exception e) {
/* 177 */               debug("[DL] SQL Error");
/* 178 */               e.printStackTrace();
/*     */             }
/*     */           }
/*     */         }
/* 182 */         this.db.commit();
/* 183 */         Blacklist.close();
/* 184 */         System.out.println("[AJB] Blacklist atualizada com sucesso:");
/* 185 */       } catch (Exception e) { if (this.debug) {
/* 186 */           debug("[DL] Error Downloading the Blacklist:");
/* 187 */           e.printStackTrace();
/*     */         } else {
/* 189 */           System.out.println("[AJB] Error ao atualizar Blacklist:");
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 197 */       org.mcstats.Metrics metrics = new org.mcstats.Metrics(this);
/* 198 */       metrics.start();
/*     */     } catch (java.io.IOException localIOException) {}
/*     */   }
/*     */   
/*     */   public Boolean isProxy(String IP) {
/* 203 */     if ((IP.equals("127.0.0.1")) || (IP.equals("localhost")) || (IP.matches("192\\.168\\.[01]{1}\\.[0-9]{1,3}")))
/* 204 */       return Boolean.valueOf(false);
/* 205 */     for (String s : this.activeBlacklist.keySet()) {
/*     */       try {
/* 207 */         String res = "";
/* 208 */         Scanner scanner = new Scanner(new URL(s.replace(",", ".") + IP).openStream());
/* 209 */         while (scanner.hasNextLine()) {
/* 210 */           res = res + scanner.nextLine();
/*     */         }
/* 212 */         String[] args = ((String)this.activeBlacklist.get(s)).split(",");
/* 213 */         String[] arrayOfString1; int j = (arrayOfString1 = args).length; for (int i = 0; i < j; i++) { String arg = arrayOfString1[i];
/* 214 */           if (res.contains(arg)) {
/* 215 */             debug(s.replace(",", ".") + ": (" + IP + " --> true)");
/* 216 */             return Boolean.valueOf(true);
/*     */           }
/*     */           
/* 219 */           debug(s.replace(",", ".") + ": (" + IP + " --> false)");
/* 220 */           scanner.close();
/*     */         }
/* 222 */       } catch (Exception e) { if (this.debug) {
/* 223 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     }
/* 227 */     return Boolean.valueOf(false);
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onPlayerJoinEvent(PlayerJoinEvent e) throws Exception {
/* 232 */     if (!this.enabled) {
/* 233 */       return;
/*     */     }
/* 235 */     if (!this.db.isConnected())
/* 236 */       this.db.connect(getConfig().getString("AntiJoinBot.MySQL.Host"), getConfig().getInt("AntiJoinBot.MySQL.Port"), getConfig().getString("AntiJoinBot.MySQL.Database"), getConfig().getString("AntiJoinBot.MySQL.User"), getConfig().getString("AntiJoinBot.MySQL.Password"), getConfig().getBoolean("AntiJoinBot.MySQL.Offline"));
/* 237 */     if (e.getPlayer().hasPermission("ajb.bypass")) {
/* 238 */       if (this.offlineMode.booleanValue()) {
/* 239 */         if (!this.db.userBlacklist.containsKey(e.getPlayer())) {
/* 240 */           this.db.setUserName(e.getPlayer(), false);
/*     */         }
/* 242 */       } else if (!this.db.userBlacklist.containsKey(e.getPlayer().getUniqueId().toString())) {
/* 243 */         this.db.setUser(e.getPlayer(), false);
/*     */       }
/* 245 */       return;
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onServerListPingEvent(ServerListPingEvent e) throws Exception {
/* 251 */     if (!this.serverListPing.booleanValue())
/* 252 */       return;
/* 253 */     if (!this.db.isConnected())
/* 254 */       this.db.connect(getConfig().getString("AntiJoinBot.MySQL.Host"), getConfig().getInt("AntiJoinBot.MySQL.Port"), getConfig().getString("AntiJoinBot.MySQL.Database"), getConfig().getString("AntiJoinBot.MySQL.User"), getConfig().getString("AntiJoinBot.MySQL.Password"), getConfig().getBoolean("AntiJoinBot.MySQL.Offline"));
/* 255 */     if (this.db.ipBlacklist.containsKey(e.getAddress().getHostAddress())) {
/* 256 */       debug("[M] ipBlacklist: " + e.getAddress().getHostAddress() + " --> " + this.db.ipBlacklist.get(e.getAddress().getHostAddress()));
/* 257 */       if (((Boolean)this.db.ipBlacklist.get(e.getAddress().getHostAddress())).booleanValue()) {
/* 258 */         e.setMotd(this.serverListPingMsg);
/*     */       }
/* 260 */       return;
/*     */     }
/* 262 */     final String IP = e.getAddress().getHostAddress();
/* 263 */     getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
/*     */       public void run() {
/*     */         try {
/* 266 */           Core.this.plugin.db.setIP(IP, Core.this.isProxy(IP).booleanValue());
/*     */         } catch (Exception e) {
/* 268 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onPlayerLoginEvent(PlayerLoginEvent e) throws Exception
/*     */   {
/* 277 */     if (!this.enabled)
/* 278 */       return;
/* 279 */     if (!this.db.isConnected())
/* 280 */       this.db.connect(getConfig().getString("AntiJoinBot.MySQL.Host"), getConfig().getInt("AntiJoinBot.MySQL.Port"), getConfig().getString("AntiJoinBot.MySQL.Database"), getConfig().getString("AntiJoinBot.MySQL.User"), getConfig().getString("AntiJoinBot.MySQL.Password"), getConfig().getBoolean("AntiJoinBot.MySQL.Offline"));
/* 281 */     debug("[M] JOIN: " + e.getAddress().getHostAddress() + " --> " + e.getPlayer().getName());
/* 282 */     if (this.offlineMode.booleanValue()) {
/* 283 */       if (this.db.userBlacklist.containsKey(e.getPlayer())) {
/* 284 */         debug("[M] userBlacklist: " + e.getPlayer().getName() + " --> " + this.db.userBlacklist.get(e.getPlayer()));
/* 285 */         if (((Boolean)this.db.userBlacklist.get(e.getPlayer())).booleanValue()) {
/* 286 */           e.disallow(PlayerLoginEvent.Result.KICK_OTHER, this.kickMsg);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/* 291 */     else if (this.db.userBlacklist.containsKey(e.getPlayer().getUniqueId().toString())) {
/* 292 */       debug("[M] userBlacklist: " + e.getPlayer().getName() + " --> " + this.db.userBlacklist.get(e.getPlayer().getUniqueId().toString()));
/* 293 */       if (((Boolean)this.db.userBlacklist.get(e.getPlayer().getUniqueId())).booleanValue()) {
/* 294 */         e.disallow(PlayerLoginEvent.Result.KICK_OTHER, this.kickMsg);
/*     */       }
/* 296 */       return;
/*     */     }
/*     */     
/* 299 */     if (this.db.ipBlacklist.containsKey(e.getAddress().getHostAddress())) {
/* 300 */       debug("[M] ipBlacklist: " + e.getAddress().getHostAddress() + " --> " + this.db.ipBlacklist.get(e.getAddress().getHostAddress()));
/* 301 */       if (((Boolean)this.db.ipBlacklist.get(e.getAddress().getHostAddress())).booleanValue()) {
/* 302 */         e.disallow(PlayerLoginEvent.Result.KICK_OTHER, this.kickMsg);
/*     */       }
/* 304 */       return;
/*     */     }
/*     */     
/* 307 */     if (this.forceMode.booleanValue()) {
/* 308 */       debug("[M] FORCE: " + e.getAddress().getHostAddress() + " --> " + e.getPlayer().getName());
/* 309 */       final String IP = e.getAddress().getHostAddress();
/* 310 */       getServer().getScheduler().runTaskAsynchronously(this, new Runnable() {
/*     */         public void run() {
/*     */           try {
/* 313 */             Core.this.plugin.db.setIP(IP, Core.this.isProxy(IP).booleanValue());
/*     */           } catch (Exception e) {
/* 315 */             e.printStackTrace();
/*     */           }
/*     */           
/*     */         }
/* 319 */       });
/* 320 */       e.disallow(PlayerLoginEvent.Result.KICK_OTHER, this.forceModeMsg);
/* 321 */       return;
/*     */     }
/* 323 */     if (isProxy(e.getAddress().getHostAddress()).booleanValue()) {
/* 324 */       this.db.setIP(e.getAddress().getHostAddress(), true);
/* 325 */       e.disallow(PlayerLoginEvent.Result.KICK_OTHER, this.kickMsg);
/*     */     }
/*     */     else {
/* 328 */       this.db.setIP(e.getAddress().getHostAddress(), false);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   public void onPlayerLoginEvent(AsyncPlayerPreLoginEvent e) throws Exception {
/* 334 */     if ((!this.enabled) || (this.forceMode.booleanValue())) {
/* 335 */       return;
/*     */     }
/* 337 */     if (!this.db.isConnected())
/* 338 */       this.db.connect(getConfig().getString("AntiJoinBot.MySQL.Host"), getConfig().getInt("AntiJoinBot.MySQL.Port"), getConfig().getString("AntiJoinBot.MySQL.Database"), getConfig().getString("AntiJoinBot.MySQL.User"), getConfig().getString("AntiJoinBot.MySQL.Password"), getConfig().getBoolean("AntiJoinBot.MySQL.Offline"));
/* 339 */     debug("[A] JOIN: " + e.getAddress().getHostAddress() + " --> " + e.getName());
/* 340 */     if (this.offlineMode.booleanValue()) {
/* 341 */       if (this.db.userBlacklist.containsKey(e.getName())) {
/* 342 */         debug("[A] userBlacklist: " + e.getName() + " --> " + this.db.userBlacklist.get(e.getName()));
/* 343 */         if (((Boolean)this.db.userBlacklist.get(e.getName())).booleanValue()) {
/* 344 */           e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, this.kickMsg);
/*     */         }
/*     */         
/*     */       }
/*     */     }
/* 349 */     else if (this.db.userBlacklist.containsKey(e.getUniqueId().toString())) {
/* 350 */       debug("[A] userBlacklist: " + e.getName() + " --> " + this.db.userBlacklist.get(e.getUniqueId().toString()));
/* 351 */       if (((Boolean)this.db.userBlacklist.get(e.getUniqueId())).booleanValue()) {
/* 352 */         e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, this.kickMsg);
/*     */       }
/* 354 */       return;
/*     */     }
/*     */     
/* 357 */     if (this.db.ipBlacklist.containsKey(e.getAddress().getHostAddress())) {
/* 358 */       debug("[A] ipBlacklist: " + e.getAddress().getHostAddress() + " --> " + this.db.ipBlacklist.get(e.getAddress().getHostAddress()));
/* 359 */       if (((Boolean)this.db.ipBlacklist.get(e.getAddress().getHostAddress())).booleanValue()) {
/* 360 */         e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, this.kickMsg);
/*     */       }
/* 362 */       return;
/*     */     }
/*     */     
/* 365 */     if (isProxy(e.getAddress().getHostAddress()).booleanValue()) {
/* 366 */       this.db.setIP(e.getAddress().getHostAddress(), true);
/* 367 */       e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_OTHER, this.kickMsg);
/*     */     }
/*     */     else {
/* 370 */       this.db.setIP(e.getAddress().getHostAddress(), false);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) {
/* 375 */     if (((sender.hasPermission("ajb.reload")) || (sender.hasPermission("ajb.toggle")) || (sender.hasPermission("ajb.add"))) && (cmd.getName().equalsIgnoreCase("ajb")) && (args.length == 0)) {
/* 376 */       sender.sendMessage("[AJB] §cValid commands: §rhelp, toggle, add, block, reload");
/*     */     }
/*     */     
/* 379 */     if ((cmd.getName().equalsIgnoreCase("ajb")) && (args.length == 1)) { String str;
/* 380 */       switch ((str = args[0]).hashCode()) {case -934641255:  if (str.equals("reload")) break; break; case -868304044:  if (str.equals("toggle")) {} break; case 96417:  if (str.equals("add")) {} break; case 3198785:  if (str.equals("help")) {} break; case 93832333:  if (!str.equals("block")) {
/*     */           break label611;
/* 382 */           if (sender.hasPermission("ajb.reload")) {
/* 383 */             reloadConfig();
/*     */             try {
/* 385 */               if (this.db.isConnected())
/* 386 */                 this.db.closeConnection();
/* 387 */               this.db.connect(getConfig().getString("AntiJoinBot.MySQL.Host"), getConfig().getInt("AntiJoinBot.MySQL.Port"), getConfig().getString("AntiJoinBot.MySQL.Database"), getConfig().getString("AntiJoinBot.MySQL.User"), getConfig().getString("AntiJoinBot.MySQL.Password"), getConfig().getBoolean("AntiJoinBot.MySQL.Offline"));
/* 388 */               if (this.offlineMode.booleanValue()) {
/* 389 */                 this.db.initDBOffline();
/*     */               } else {
/* 391 */                 this.db.initDB();
/*     */               }
/* 393 */               sender.sendMessage("[AJB] §cConfig & database reloaded successfully");
/*     */             } catch (Exception e) {
/* 395 */               sender.sendMessage("[AJB] §cConfig reloaded but failed to reload the DB Connection.");
/* 396 */               if (!this.debug) break label611; }
/* 397 */             e.printStackTrace();
/*     */             
/*     */ 
/*     */             break label611;
/*     */             
/*     */ 
/* 403 */             if (sender.hasPermission("ajb.toggle")) {
/* 404 */               if (this.enabled) {
/* 405 */                 this.enabled = false;
/*     */               } else {
/* 407 */                 this.enabled = true;
/*     */               }
/* 409 */               sender.sendMessage("[AJB] §cPlugin ativado: §r" + this.enabled);
/*     */               
/*     */               break label611;
/*     */               
/* 413 */               if ((sender.hasPermission("ajb.reload")) || (sender.hasPermission("ajb.toggle")) || (sender.hasPermission("ajb.add"))) {
/* 414 */                 sender.sendMessage("[AJB] §cValid Commands:");
/* 415 */                 sender.sendMessage("/ajb toggle - §cActivates/Deactivates the plugin");
/* 416 */                 sender.sendMessage("/ajb reload - §cReloads configuration and DB connection");
/* 417 */                 sender.sendMessage("/ajb add <player> - §cAdds the given player to the AJB whitelist");
/* 418 */                 sender.sendMessage("/ajb block <player> - §cAdds the given player to the AJB blacklist");
/*     */                 
/*     */                 break label611;
/*     */                 
/* 422 */                 if ((sender.hasPermission("ajb.reload")) || (sender.hasPermission("ajb.toggle")) || (sender.hasPermission("ajb.add")))
/* 423 */                   sender.sendMessage("[AJB] §cUsage: §r/ajb add <player>");
/*     */               }
/*     */             }
/*     */           }
/* 427 */         } else if ((sender.hasPermission("ajb.reload")) || (sender.hasPermission("ajb.toggle")) || (sender.hasPermission("ajb.add"))) {
/* 428 */           sender.sendMessage("[AJB] §cUsage: §r/ajb block <player>");
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/*     */     label611:
/* 434 */     if (sender.hasPermission("ajb.add")) {
/*     */       try {
/* 436 */         if ((cmd.getName().equalsIgnoreCase("ajb")) && (args.length == 2)) {
/* 437 */           if (!this.db.isConnected())
/* 438 */             this.db.connect(getConfig().getString("AntiJoinBot.MySQL.Host"), getConfig().getInt("AntiJoinBot.MySQL.Port"), getConfig().getString("AntiJoinBot.MySQL.Database"), getConfig().getString("AntiJoinBot.MySQL.User"), getConfig().getString("AntiJoinBot.MySQL.Password"), getConfig().getBoolean("AntiJoinBot.MySQL.Offline"));
/* 439 */           switch ((e = args[0]).hashCode()) {case 96417:  if (e.equals("add")) break; break; case 93832333:  if (!e.equals("block")) {
/*     */               break label1083;
/* 441 */               if (this.offlineMode.booleanValue()) {
/* 442 */                 this.db.setUserName(args[1], false);
/* 443 */                 sender.sendMessage("[AJB] §cPlayer added to whitelist: §r" + args[1]);
/*     */               }
/* 445 */               else if (UUIDFetcher.getUUIDOf(args[1]) == null) {
/* 446 */                 sender.sendMessage("[AJB] §cCannot get UUID of §r" + args[1] + "§c. Check the nickname!");
/*     */               } else {
/* 448 */                 this.db.setUser(UUIDFetcher.getUUIDOf(args[1]), false);
/* 449 */                 sender.sendMessage("[AJB] §cPlayer added to whitelist: §r" + args[1]);
/*     */               }
/*     */               
/*     */ 
/*     */             }
/* 454 */             else if (this.offlineMode.booleanValue()) {
/* 455 */               this.db.setUserName(args[1], true);
/* 456 */               sender.sendMessage("[AJB] §cPlayer added to blacklist: §r" + args[1]);
/*     */             }
/* 458 */             else if (UUIDFetcher.getUUIDOf(args[1]) == null) {
/* 459 */               sender.sendMessage("[AJB] §cCannot get UUID of §r" + args[1] + "§c. Check the nickname!");
/*     */             } else {
/* 461 */               this.db.setUser(UUIDFetcher.getUUIDOf(args[1]), true);
/* 462 */               sender.sendMessage("[AJB] §cPlayer added to blacklist: §r" + args[1]);
/*     */             }
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (Exception e) {
/* 469 */         sender.sendMessage("[AJB] §cError adding player to the database");
/* 470 */         if (this.debug)
/* 471 */           e.printStackTrace();
/*     */       }
/*     */     }
/*     */     label1083:
/* 475 */     return false;
/*     */   }
/*     */   
/*     */   public void debug(String text) {
/* 479 */     if (this.debug) {
/* 480 */       System.out.println("[AJB]: [D] " + text);
/*     */     }
/*     */   }
/*     */   
/*     */   public void onDisable() {
/*     */     try {
/* 486 */       if (this.db.isConnected()) {
/* 487 */         this.db.closeConnection();
/*     */       }
/*     */     } catch (SQLException localSQLException) {}
/* 490 */     org.bukkit.Bukkit.getPluginManager().disablePlugin(this.plugin);
/*     */   }
/*     */ }


