/*     */ package net.risenphoenix.ipcheck.objects;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.commands.block.BlockManager;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*     */ import net.risenphoenix.ipcheck.util.FormatFilter;
/*     */ import net.risenphoenix.ipcheck.util.ListFormatter;
/*     */ import net.risenphoenix.ipcheck.util.TimeCalculator;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ReportObject
/*     */ {
/*     */   private Plugin plugin;
/*     */   private DatabaseController db;
/*     */   private ConfigurationManager config;
/*     */   private LocalizationManager local;
/*     */   private ArrayList<StringBuilder> SBs;
/*     */   private ArrayList<String> singleAlts;
/*     */   private ArrayList<String> uniqueAlts;
/*  61 */   private boolean useUUIDResults = false;
/*     */   private String UUIDResults;
/*     */   private OfflinePlayer player;
/*     */   private boolean forPlayer;
/*     */   
/*     */   public ReportObject(IPCheck ipCheck)
/*     */   {
/*  68 */     this.plugin = ipCheck;
/*  69 */     this.db = ipCheck.getDatabaseController();
/*  70 */     this.config = ipCheck.getConfigurationManager();
/*  71 */     this.local = ipCheck.getLocalizationManager();
/*     */     
/*  73 */     this.SBs = new ArrayList();
/*  74 */     this.singleAlts = new ArrayList();
/*  75 */     this.uniqueAlts = new ArrayList();
/*     */   }
/*     */   
/*     */   public void onExecute(CommandSender sender, String arg)
/*     */   {
/*  80 */     String ip_filter = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
/*     */     
/*     */ 
/*  83 */     this.forPlayer = (!arg.toLowerCase().matches(ip_filter));
/*     */     
/*     */ 
/*  86 */     if (this.forPlayer) {
/*  87 */       this.player = Bukkit.getOfflinePlayer(arg);
/*     */       
/*     */ 
/*  90 */       if (this.player.isBanned())
/*     */       {
/*  92 */         if (!this.db.getUserObject(arg).getBannedStatus()) {
/*  93 */           this.db.banPlayer(this.player.getName(), this.config
/*  94 */             .getString("ban-message"));
/*     */         }
/*     */       }
/*  97 */       else if (this.db.getUserObject(arg).getBannedStatus()) {
/*  98 */         this.db.unbanPlayer(this.player.getName());
/*     */       }
/*     */       
/*     */ 
/* 102 */       FetchResult fResult = fetchPlayerData(arg);
/*     */       
/*     */ 
/* 105 */       if (fResult == FetchResult.NOT_FOUND) {
/* 106 */         this.plugin.sendPlayerMessage(sender, this.local
/* 107 */           .getLocalString("NO_FIND"));
/* 108 */         return;
/*     */       }
/*     */     } else {
/* 111 */       IPObject ipo = this.db.getIPObject(arg);
/* 112 */       this.singleAlts = ipo.getUsers();
/*     */       
/* 114 */       if (ipo.getNumberOfUsers() == 0) {
/* 115 */         this.plugin.sendPlayerMessage(sender, this.local
/* 116 */           .getLocalString("NO_FIND"));
/* 117 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 122 */     outputHead(sender, arg);
/* 123 */     outputBody(sender, arg);
/* 124 */     outputFoot(sender, arg);
/*     */   }
/*     */   
/*     */   private void outputHead(CommandSender sender, String arg)
/*     */   {
/* 129 */     this.plugin.sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     
/*     */ 
/* 132 */     if (!this.forPlayer) {
/* 133 */       if ((sender.hasPermission("ipcheck.showip")) || (sender.isOp())) {
/* 134 */         this.plugin.sendPlayerMessage(sender, ChatColor.GOLD + this.local
/* 135 */           .getLocalString("REPORT_HEAD_ONE") + " " + ChatColor.GREEN + arg + ChatColor.GOLD + " ... " + ChatColor.RED + this.singleAlts
/*     */           
/*     */ 
/* 138 */           .size(), false);
/*     */       } else {
/* 140 */         this.plugin.sendPlayerMessage(sender, ChatColor.GOLD + this.local
/* 141 */           .getLocalString("REPORT_HEAD_TWO") + " " + ChatColor.RED + this.singleAlts
/* 142 */           .size(), false);
/*     */       }
/*     */     } else {
/* 145 */       this.plugin.sendPlayerMessage(sender, ChatColor.GOLD + this.local
/* 146 */         .getLocalString("REPORT_HEAD_ONE") + " " + ChatColor.GREEN + arg + ChatColor.GOLD + " ... " + ChatColor.RED + this.uniqueAlts
/*     */         
/* 148 */         .size(), false);
/*     */     }
/*     */   }
/*     */   
/*     */   private void outputBody(CommandSender sender, String arg)
/*     */   {
/* 154 */     if (((this.forPlayer) && (this.uniqueAlts.size() > 0)) || ((!this.forPlayer) && 
/* 155 */       (this.singleAlts.size() > 0)))
/*     */     {
/* 157 */       this.plugin.sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */       
/*     */ 
/* 160 */       if (!this.forPlayer)
/*     */       {
/* 162 */         String output = new ListFormatter(this.singleAlts).getFormattedList().toString();
/*     */         
/* 164 */         if (sender.hasPermission("ipcheck.showip")) {
/* 165 */           this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/*     */           
/* 167 */             .getLocalString("REPORT_BODY_ONE"), false);
/*     */           
/*     */ 
/* 170 */           this.plugin.sendPlayerMessage(sender, ChatColor.YELLOW + output, false);
/*     */         }
/*     */         else {
/* 173 */           this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/*     */           
/* 175 */             .getLocalString("REPORT_BODY_TWO") + " " + ChatColor.YELLOW + output, false);
/*     */         }
/*     */         
/*     */ 
/* 179 */         return;
/*     */       }
/* 181 */       this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/* 182 */         .getLocalString("REPORT_BODY_TWO") + " ", false);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 188 */       for (int i = 0; i < this.SBs.size(); i++) {
/* 189 */         StringBuilder sb = (StringBuilder)this.SBs.get(i);
/* 190 */         StringBuilder ipAddress = new StringBuilder();
/* 191 */         String full = sb.toString();
/*     */         
/* 193 */         for (int j = 0; j < full.length(); j++) {
/* 194 */           if (full.charAt(j) == '|') break;
/* 195 */           ipAddress.append(full.charAt(j));
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 202 */         String out = full.replace(ipAddress.toString() + "|", "");
/*     */         
/*     */ 
/*     */ 
/* 206 */         if ((!sender.hasPermission("ipcheck.showip")) && 
/* 207 */           (!sender.isOp())) {
/* 208 */           this.plugin.sendPlayerMessage(sender, ChatColor.RED + "####:", false);
/*     */         }
/*     */         else {
/* 211 */           String ipAdd = ipAddress.toString();
/*     */           
/* 213 */           this.plugin.sendPlayerMessage(sender, ChatColor.RED + ipAdd + ":", false);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 218 */         this.plugin.sendPlayerMessage(sender, out, false);
/*     */         
/*     */ 
/* 221 */         if (i < this.SBs.size() - 1) sender.sendMessage("");
/*     */       }
/*     */     }
/*     */     else {
/* 225 */       this.plugin.sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */       
/* 227 */       this.plugin.sendPlayerMessage(sender, this.local
/* 228 */         .getLocalString("REPORT_BODY_FOUR"), false);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 233 */     if (this.forPlayer)
/*     */     {
/* 235 */       if (this.useUUIDResults) {
/* 236 */         this.plugin.sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */         
/*     */ 
/* 239 */         this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/*     */         
/* 241 */           .getLocalString("UUID_HEAD") + " ", false);
/* 242 */         this.plugin.sendPlayerMessage(sender, ChatColor.YELLOW + this.UUIDResults + " ", false);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void outputFoot(CommandSender sender, String arg)
/*     */   {
/* 250 */     this.plugin.sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     
/*     */ 
/* 253 */     if (this.forPlayer) {
/* 254 */       if (this.player != null)
/*     */       {
/* 256 */         String ipOutput = this.db.getLastKnownIP(arg);
/*     */         
/* 258 */         if ((sender.hasPermission("ipcheck.showip")) || (sender.isOp()))
/*     */         {
/* 260 */           this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/* 261 */             .getLocalString("REPORT_FOOT_LAST_IP") + " " + ChatColor.YELLOW + ipOutput, false);
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 267 */         String out = IPCheck.getInstance().getBlockManager().getCountry(ipOutput);
/*     */         
/*     */ 
/* 270 */         String country = ChatColor.YELLOW + " " + (out != null ? out : this.local.getLocalString("LOCATION_UNAVAILABLE"));
/*     */         
/* 272 */         this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/* 273 */           .getLocalString("REPORT_FOOT_LOCATION") + country, false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 278 */         String lastLog = ChatColor.YELLOW + " " + new TimeCalculator(arg).getLastTime();
/*     */         
/* 280 */         this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/* 281 */           .getLocalString("REPORT_FOOT_PTIME") + lastLog, false);
/*     */         
/*     */ 
/* 284 */         this.plugin.sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 289 */         String banStatus = ChatColor.GREEN + " False";
/*     */         
/*     */ 
/* 292 */         this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/* 293 */           .getLocalString("REPORT_FOOT_PBAN") + banStatus, false);
/*     */         
/*     */ 
/*     */ 
/* 297 */         String exmStatus = ChatColor.RED + " False";
/*     */         
/*     */ 
/* 300 */         this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/* 301 */           .getLocalString("REPORT_FOOT_PEXM") + exmStatus, false);
/*     */         
/*     */ 
/*     */ 
/* 305 */         String proStatus = ChatColor.RED + " False";
/*     */         
/*     */ 
/* 308 */         this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/* 309 */           .getLocalString("REPORT_FOOT_PPRO") + proStatus, false);
/*     */         
/*     */ 
/*     */ 
/* 313 */         String exmRejoinStatus = ChatColor.GREEN + " True";
/*     */         
/*     */ 
/* 316 */         if (this.db.isBannedPlayer(this.player.getName())) {
/* 317 */           this.plugin.sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */           
/*     */ 
/* 320 */           this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/*     */           
/* 322 */             .getLocalString("REPORT_FOOT_PREXM") + exmRejoinStatus, false);
/*     */         }
/*     */       }
/*     */       else {
/* 326 */         this.plugin.sendPlayerMessage(sender, ChatColor.RED + "ERROR: " + ChatColor.GOLD + this.local
/*     */         
/* 328 */           .getLocalString("REPORT_FOOT_ERR"), false);
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 333 */       String out = IPCheck.getInstance().getBlockManager().getCountry(arg);
/*     */       
/*     */ 
/* 336 */       String country = ChatColor.YELLOW + " " + (out != null ? out : this.local.getLocalString("LOCATION_UNAVAILABLE"));
/*     */       
/* 338 */       this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/* 339 */         .getLocalString("REPORT_FOOT_LOCATION") + country, false);
/*     */       
/*     */ 
/*     */ 
/* 343 */       String banStatus = ChatColor.GREEN + " False";
/*     */       
/*     */ 
/* 346 */       this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/* 347 */         .getLocalString("REPORT_FOOT_IBAN") + banStatus, false);
/*     */       
/*     */ 
/*     */ 
/* 351 */       String exmStatus = ChatColor.RED + " False";
/*     */       
/*     */ 
/* 354 */       this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/* 355 */         .getLocalString("REPORT_FOOT_IEXM") + exmStatus, false);
/*     */       
/*     */ 
/*     */ 
/* 359 */       String rexmStatus = ChatColor.RED + " False";
/*     */       
/*     */ 
/* 362 */       this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/* 363 */         .getLocalString("REPORT_FOOT_IREXM") + rexmStatus, false);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 368 */     if ((this.forPlayer) && 
/* 369 */       ((sender.hasPermission("ipcheck.showbanreason")) || (sender.isOp())) && 
/* 370 */       (this.player != null) && 
/* 371 */       (this.db.isBannedPlayer(arg))) {
/* 372 */       String banMsg = this.db.getBanMessage(this.player.getName());
/*     */       
/* 374 */       if ((banMsg == null) || (banMsg.length() <= 0))
/*     */       {
/* 376 */         banMsg = this.local.getLocalString("REPORT_BAN_GENERIC");
/*     */       }
/*     */       
/* 379 */       this.plugin.sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + this.local
/*     */       
/* 381 */         .getLocalString("REPORT_BAN_HEAD") + " " + ChatColor.YELLOW + banMsg, false);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 389 */     this.plugin.sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private FetchResult fetchPlayerData(final String arg)
/*     */   {
/* 396 */     UserObject user = this.db.getUserObject(arg);
/*     */     
/*     */ 
/* 399 */     if (user.getNumberOfIPs() == 0) { return FetchResult.NOT_FOUND;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 404 */     ArrayList<IPObject> ipos = new ArrayList();
/*     */     
/*     */     String s;
/* 407 */     for (Iterator localIterator1 = user.getIPs().iterator(); localIterator1.hasNext(); ipos.add(this.db.getIPObject(s))) { s = (String)localIterator1.next();
/*     */     }
/*     */     
/* 410 */     for (IPObject ipo : ipos)
/*     */     {
/*     */ 
/*     */ 
/* 414 */       if ((ipo.getNumberOfUsers() != 1) || 
/* 415 */         (!ipo.getUsers().contains(arg.toLowerCase())))
/*     */       {
/*     */ 
/*     */ 
/* 419 */         sb = new StringBuilder();
/*     */         
/*     */ 
/* 422 */         sb.append(ipo.getIP() + "|");
/*     */         
/*     */ 
/* 425 */         FormatFilter fFilter = new FormatFilter() {
/* 426 */           private ArrayList<String> inputs = new ArrayList();
/*     */           
/*     */ 
/*     */           public String execute(String input)
/*     */           {
/* 431 */             if (this.inputs.contains(input.toLowerCase())) {
/* 432 */               return null;
/*     */             }
/* 434 */             this.inputs.add(input.toLowerCase());
/*     */             
/*     */ 
/*     */ 
/* 438 */             return !input.equalsIgnoreCase(arg) ? input : null;
/*     */ 
/*     */           }
/*     */           
/*     */ 
/* 443 */         };
/* 444 */         ListFormatter format = new ListFormatter(ipo.getUsers(), fFilter);
/* 445 */         sb.append(format.getFormattedList());
/*     */         
/*     */ 
/* 448 */         for (String s : ipo.getUsers()) {
/* 449 */           String val = s.toLowerCase();
/* 450 */           if ((!val.equalsIgnoreCase(arg)) && 
/* 451 */             (!this.uniqueAlts.contains(val))) { this.uniqueAlts.add(val);
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 457 */         if (!sb.toString().equals(ipo.getIP() + "|")) { this.SBs.add(sb);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */     StringBuilder sb;
/* 463 */     if (Bukkit.getOnlineMode()) {
/* 464 */       this.useUUIDResults = true;
/*     */       
/*     */ 
/*     */ 
/* 468 */       if (user.getUUID() != null)
/*     */       {
/* 470 */         Object uuid_links = this.db.getPlayersByUUID(user.getUUID());
/*     */         
/* 472 */         ArrayList<String> unique_uuid_results = new ArrayList();
/*     */         
/*     */ 
/* 475 */         for (String s : (ArrayList)uuid_links) {
/* 476 */           if (!s.toLowerCase().equals(arg.toLowerCase())) {
/* 477 */             if (!unique_uuid_results.contains(s))
/* 478 */               unique_uuid_results.add(s);
/*     */           }
/*     */         }
/* 481 */         if (unique_uuid_results.size() == 0) {
/* 482 */           this.UUIDResults = this.local.getLocalString("NO_UUID_RES");
/*     */         } else {
/* 484 */           ListFormatter format = new ListFormatter(unique_uuid_results);
/*     */           
/*     */ 
/* 487 */           this.UUIDResults = format.getFormattedList().toString();
/*     */         }
/*     */       } else {
/* 490 */         this.UUIDResults = this.local.getLocalString("NO_UUID_RES");
/*     */       }
/*     */     }
/*     */     
/* 494 */     return FetchResult.GOOD;
/*     */   }
/*     */   
/*     */   private static enum FetchResult {
/* 498 */     GOOD, 
/* 499 */     NOT_FOUND;
/*     */     
/*     */     private FetchResult() {}
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\objects\ReportObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */