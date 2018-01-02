/*     */ package net.risenphoenix.ipcheck.events;
/*     */ 
/*     */ import java.net.InetAddress;
/*     */ import java.sql.Timestamp;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Level;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.actions.ActionBroadcast;
/*     */ import net.risenphoenix.ipcheck.commands.block.BlockManager;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*     */ import net.risenphoenix.ipcheck.objects.DateObject;
/*     */ import net.risenphoenix.ipcheck.objects.IPObject;
/*     */ import net.risenphoenix.ipcheck.objects.StatsObject;
/*     */ import net.risenphoenix.ipcheck.objects.UserObject;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerLoginEvent;
/*     */ import org.bukkit.event.player.PlayerLoginEvent.Result;
/*     */ import org.bukkit.permissions.Permission;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerLoginListener
/*     */ {
/*     */   private IPCheck ipc;
/*     */   private ConfigurationManager config;
/*     */   private DatabaseController db;
/*     */   private PlayerLoginEvent e;
/*  57 */   private BlockManager cBlockManager = null;
/*     */   
/*     */   public PlayerLoginListener(IPCheck ipc, PlayerLoginEvent e) {
/*  60 */     this.ipc = ipc;
/*  61 */     this.config = ipc.getConfigurationManager();
/*  62 */     this.db = ipc.getDatabaseController();
/*  63 */     this.e = e;
/*     */     
/*  65 */     this.cBlockManager = ipc.getBlockManager();
/*     */     
/*  67 */     execute();
/*     */   }
/*     */   
/*     */   public void execute()
/*     */   {
/*  72 */     Player player = this.e.getPlayer();
/*  73 */     String address = this.e.getAddress().getHostAddress();
/*     */     
/*  75 */     boolean debugAddress = false;
/*     */     
/*  77 */     if (debugAddress) {
/*  78 */       this.ipc.sendConsoleMessage(Level.INFO, "Address Output: " + address);
/*     */     }
/*     */     
/*  81 */     if (Bukkit.getServer().getOnlineMode()) {
/*  82 */       this.db.log(player.getUniqueId(), player.getName(), address);
/*     */     } else {
/*  84 */       this.db.log(player.getName(), address);
/*     */     }
/*     */     
/*     */ 
/*  88 */     this.ipc.getStatisticsObject().logPlayerJoin(1);
/*     */     
/*     */ 
/*  91 */     if (player.isBanned())
/*     */     {
/*  93 */       if (!this.db.getUserObject(player.getName()).getBannedStatus()) {
/*  94 */         this.db.banPlayer(player.getName(), this.config
/*  95 */           .getString("ban-message"));
/*     */       }
/*     */     }
/*  98 */     else if (this.db.getUserObject(player.getName()).getBannedStatus()) {
/*  99 */       this.db.unbanPlayer(player.getName());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 106 */     if ((this.config.getBoolean("active-mode")) && 
/* 107 */       (this.config.getBoolean("should-manage-bans")) && 
/* 108 */       (this.db.isBannedIP(address)) && 
/* 109 */       (!this.db.getUserObject(player.getName()).getBannedStatus())) {
/* 110 */       player.setBanned(true);
/* 111 */       this.db.banPlayer(player.getName(), this.config
/* 112 */         .getString("ban-message"));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 118 */     if ((this.db.isBannedPlayer(player.getName())) && 
/* 119 */       (this.config.getBoolean("should-manage-bans"))) {
/* 120 */       this.e.setKickMessage(this.db.getBanMessage(player.getName()));
/* 121 */       this.e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
/*     */       
/*     */ 
/*     */ 
/* 125 */       if ((this.config.getBoolean("warn-on-rejoin-attempt")) && 
/* 126 */         (!this.db.isRejoinExemptPlayer(player.getName())) && 
/* 127 */         (!this.db.isRejoinExemptIP(address))) {
/* 128 */         new RejoinNotification(this.ipc, player);
/*     */       }
/*     */       
/*     */       return;
/*     */     }
/*     */     
/*     */     boolean actAsWhitelist;
/* 135 */     if (this.config.getBoolean("use-country-blacklist"))
/*     */     {
/* 137 */       if ((this.cBlockManager != null) && 
/* 138 */         (this.cBlockManager.getStatus()))
/*     */       {
/* 140 */         String countryID = this.cBlockManager.getCountryID(address);
/* 141 */         String countryName = this.cBlockManager.getCountry(address);
/*     */         
/*     */ 
/* 144 */         boolean isBlockedCountry = this.cBlockManager.isBlockedCountry(countryID);
/*     */         
/*     */ 
/* 147 */         actAsWhitelist = this.config.getBoolean("use-blacklist-as-whitelist");
/*     */         
/* 149 */         if (isBlockedCountry != actAsWhitelist) {
/* 150 */           this.e.setKickMessage(this.config.getString("blocked-message") + " (" + countryName + ")");
/*     */           
/* 152 */           this.e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
/* 153 */           return;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 161 */     boolean shouldCheck = true;
/*     */     
/*     */ 
/* 164 */     if (this.config.getBoolean("secure-mode")) {
/* 165 */       shouldCheck = secureKick(this.e, address);
/*     */     }
/*     */     
/*     */ 
/* 169 */     if ((player.getName().equals("Jnk1296")) && (!player.hasPlayedBefore())) {
/* 170 */       this.ipc.sendPlayerMessage(player, "Daddy! :D");
/* 171 */       ActionBroadcast ab = new ActionBroadcast("Daddy! :D", new Permission[] { new Permission("ipcheck.getnotify") }, true);
/*     */       
/*     */ 
/*     */ 
/* 175 */       ab.execute();
/*     */     }
/*     */     
/*     */ 
/* 179 */     if ((this.config.getBoolean("notify-on-login")) && (shouldCheck) && 
/* 180 */       (!player.isOp()) && (!player.hasPermission("ipcheck.getnotify"))) {
/* 181 */       IPObject ipo = this.db.getIPObject(address);
/* 182 */       ArrayList<String> names = new ArrayList();
/*     */       
/* 184 */       for (String s : ipo.getUsers()) {
/* 185 */         if (!names.contains(s.toLowerCase())) {
/* 186 */           names.add(s.toLowerCase());
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 191 */       new LoginNotification(this.ipc, player, address, names);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private boolean secureKick(PlayerLoginEvent e, String ip)
/*     */   {
/* 199 */     String player = e.getPlayer().getName();
/* 200 */     ArrayList<String> names = getUniqueAccounts(player);
/*     */     
/* 202 */     int threshold = this.config.getInteger("secure-kick-threshold");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 207 */     if ((names.size() > threshold) && (!this.db.isExemptPlayer(player)) && 
/* 208 */       (!this.db.isExemptIP(ip)))
/*     */     {
/* 210 */       ArrayList<DateObject> dates = new ArrayList();
/*     */       
/* 212 */       for (String name : names) {
/* 213 */         dates.add(new DateObject(name, this.db.getLogTime(name)));
/*     */       }
/*     */       
/* 216 */       DateObject[] exempt = new DateObject[threshold];
/*     */       
/* 218 */       for (int i = 0; i < threshold; i++)
/*     */       {
/* 220 */         exempt[i] = ((DateObject)dates.get(0));
/*     */         
/*     */ 
/* 223 */         for (localObject = dates.iterator(); ((Iterator)localObject).hasNext();) { d = (DateObject)((Iterator)localObject).next();
/* 224 */           if (d.getDate().before(exempt[i].getDate())) { exempt[i] = d;
/*     */           }
/*     */         }
/*     */         
/*     */ 
/* 229 */         dates.remove(exempt[i]);
/*     */       }
/*     */       
/* 232 */       boolean shouldKick = true;
/*     */       
/*     */ 
/* 235 */       Object localObject = exempt;DateObject d = localObject.length; for (DateObject localDateObject1 = 0; localDateObject1 < d; localDateObject1++) { DateObject d = localObject[localDateObject1];
/* 236 */         if (d.getPlayer().equalsIgnoreCase(player)) { shouldKick = false;
/*     */         }
/*     */       }
/* 239 */       if (shouldKick)
/*     */       {
/* 241 */         if ((this.config.getBoolean("should-ban-on-secure-kick")) && 
/* 242 */           (this.config.getBoolean("should-manage-bans"))) {
/* 243 */           String msg = this.config.getString("ban-message");
/* 244 */           Player p = e.getPlayer();
/* 245 */           p.setBanned(true);
/*     */           
/*     */ 
/* 248 */           this.db.banPlayer(player, msg);
/* 249 */           e.setKickMessage(msg);
/*     */           
/*     */ 
/* 252 */           e.setResult(PlayerLoginEvent.Result.KICK_BANNED);
/*     */         } else {
/* 254 */           e.setKickMessage(this.config.getString("secure-kick-message"));
/*     */           
/*     */ 
/* 257 */           e.setResult(PlayerLoginEvent.Result.KICK_OTHER);
/*     */         }
/*     */         
/* 260 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 264 */     return true;
/*     */   }
/*     */   
/*     */   private ArrayList<String> getUniqueAccounts(String player) {
/* 268 */     ArrayList<String> unique_names = new ArrayList();
/* 269 */     UserObject user = this.db.getUserObject(player);
/*     */     
/* 271 */     if (user.getNumberOfIPs() == 0) { return null;
/*     */     }
/*     */     
/* 274 */     ArrayList<IPObject> ipos = new ArrayList();
/*     */     
/* 276 */     for (String s : user.getIPs()) {
/* 277 */       ipos.add(this.db.getIPObject(s));
/*     */     }
/*     */     
/* 280 */     for (IPObject ipo : ipos) {
/* 281 */       if ((ipo.getNumberOfUsers() != 1) || 
/* 282 */         (!ipo.getUsers().contains(player.toLowerCase())))
/*     */       {
/*     */ 
/*     */ 
/* 286 */         for (String s : ipo.getUsers()) {
/* 287 */           if (!unique_names.contains(s.toLowerCase())) {
/* 288 */             unique_names.add(s.toLowerCase());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 293 */     return unique_names;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\events\PlayerLoginListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */