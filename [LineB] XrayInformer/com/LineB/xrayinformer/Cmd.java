/*     */ package com.LineB.xrayinformer;
/*     */ 
/*     */ import com.LineB.xrayinformer.lookups.Checkers;
/*     */ import java.util.HashMap;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class Cmd
/*     */   implements CommandExecutor
/*     */ {
/*     */   private XrayInformer plugin;
/*     */   private Checkers checker;
/*     */   
/*     */   public Cmd()
/*     */   {
/*  20 */     this.plugin = XrayInformer.getInstance();
/*  21 */     this.checker = new Checkers();
/*     */   }
/*     */   
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*     */   {
/*  26 */     boolean succeed = false;
/*     */     
/*  28 */     if (cmd.getName().equalsIgnoreCase("xcheck")) {
/*  29 */       if ((sender.hasPermission("xcheck.check")) || (sender.isOp()))
/*     */       {
/*  31 */         String playername = "";
/*  32 */         if (args.length > 0) {
/*  33 */           if (!args[0].contains(":")) {
/*  34 */             playername = args[0];
/*     */           }
/*     */         }
/*     */         else {
/*  38 */           this.plugin.showInfo(sender);
/*  39 */           return true;
/*     */         }
/*     */         
/*  42 */         String world = "";
/*  43 */         int hours = -1;
/*  44 */         int oreid = 0;
/*     */         
/*  46 */         String bantype = "none";
/*  47 */         float maxrate = 0.0F;
/*     */         
/*     */ 
/*  50 */         HashMap<String, String> hm = new HashMap();
/*  51 */         String[] nonPlayerArgs = new String[args.length];
/*     */         try {
/*  53 */           if (!args[0].contains(":")) {
/*  54 */             if (args[0].equals("clear")) {
/*  55 */               for (int i = 2; i < args.length; i++) {
/*  56 */                 nonPlayerArgs[(i - 2)] = args[i];
/*     */               }
/*     */             } else {
/*  59 */               for (int i = 1; i < args.length; i++) {
/*  60 */                 nonPlayerArgs[(i - 1)] = args[i];
/*     */               }
/*     */             }
/*     */           } else {
/*  64 */             for (int i = 0; i < args.length; i++)
/*  65 */               nonPlayerArgs[i] = args[i];
/*     */           }
/*     */           String[] arrayOfString1;
/*  68 */           int j = (arrayOfString1 = nonPlayerArgs).length; for (int i = 0; i < j; i++) { String arg = arrayOfString1[i];
/*  69 */             if (arg == null) break;
/*  70 */             String[] tokens = arg.split(":");
/*  71 */             hm.put(tokens[0], tokens[1]);
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/*  75 */           e.printStackTrace();
/*     */         }
/*     */         
/*  78 */         if (hm.containsKey("maxrate")) {
/*  79 */           maxrate = Float.parseFloat(((String)hm.get("maxrate")).toString());
/*     */         }
/*     */         
/*  82 */         if (hm.containsKey("since")) {
/*  83 */           hours = Integer.parseInt(((String)hm.get("since")).toString());
/*     */         }
/*     */         
/*  86 */         if (hm.containsKey("banned")) {
/*  87 */           if (((String)hm.get("banned")).toString().equalsIgnoreCase("true")) {
/*  88 */             this.plugin.banned = true;
/*     */           } else {
/*  90 */             this.plugin.banned = false;
/*     */           }
/*     */         } else {
/*  93 */           this.plugin.banned = false;
/*     */         }
/*     */         
/*  96 */         if (hm.containsKey("world")) {
/*  97 */           world = ((String)hm.get("world")).toString();
/*  98 */           if (this.plugin.getServer().getWorld(world) == null) {
/*  99 */             sender.sendMessage(ChatColor.RED + "[XrayInformer]" + ChatColor.WHITE + " This world does not exist. Please check your world-parameter.");
/* 100 */             return true;
/*     */           }
/*     */         }
/*     */         
/* 104 */         if (hm.containsKey("ore")) {
/* 105 */           oreid = Integer.parseInt(((String)hm.get("ore")).toString());
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 113 */         if ((args.length == 1) && (args[0].equalsIgnoreCase("reload"))) {
/* 114 */           this.plugin.config.load();
/* 115 */           sender.sendMessage(ChatColor.RED + "[XrayInformer]" + ChatColor.WHITE + " Config reloaded.");
/* 116 */           return true;
/*     */         }
/*     */         
/*     */ 
/* 120 */         if ((args.length == 1) && (args[0].equalsIgnoreCase("help"))) {
/* 121 */           this.plugin.showHelp(sender);
/* 122 */           return true;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 133 */         if ((args.length == 2) && (args[0].equalsIgnoreCase("clear"))) {
/* 134 */           if ((sender.hasPermission("xcheck.clear")) || (sender.isOp())) {
/*     */             try {
/* 136 */               this.plugin.clearPlayer(sender, args[1]);
/*     */             }
/*     */             catch (Exception e) {
/* 139 */               e.printStackTrace();
/*     */             }
/* 141 */             return true;
/*     */           }
/* 143 */           sender.sendMessage(ChatColor.RED + "[XrayInformer]" + ChatColor.WHITE + " You do not have permission for this command.");
/* 144 */           return true;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 149 */         if (playername.length() == 0) {
/* 150 */           this.plugin.showInfo(sender);
/* 151 */           return true;
/*     */         }
/*     */         
/*     */ 
/* 155 */         if ((playername.length() > 0) && (world.length() == 0) && (oreid == 0)) {
/*     */           try
/*     */           {
/* 158 */             if (ClearedPlayerFile.wasPlayerCleared(playername)) {
/* 159 */               hours = ClearedPlayerFile.getHoursFromClear(playername);
/*     */             }
/*     */             
/* 162 */             world = Config.defaultWorld;
/* 163 */             this.checker.checkGlobal(playername, sender, world, hours);
/* 164 */             return true;
/*     */           }
/*     */           catch (Exception e) {
/* 167 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 173 */         if ((playername.length() > 0) && (world.length() > 0) && (oreid == 0)) {
/*     */           try {
/* 175 */             if (ClearedPlayerFile.wasPlayerCleared(playername)) {
/* 176 */               hours = ClearedPlayerFile.getHoursFromClear(playername);
/*     */             }
/*     */             
/* 179 */             this.checker.checkGlobal(playername, sender, world, hours);
/* 180 */             return true;
/*     */           }
/*     */           catch (Exception e) {
/* 183 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 189 */         if ((playername.length() > 0) && (world.length() > 0) && (oreid > 0)) {
/* 190 */           if ((playername.equalsIgnoreCase("all")) && (maxrate > 0.0F))
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 196 */             new Thread(new CustomRunnable(sender, world, oreid, bantype, maxrate, this.plugin.banned, hours)
/*     */             {
/*     */               public void run()
/*     */               {
/* 194 */                 Cmd.this.checker.listAllXRayers(this.sender, this.world, this.oreid, this.bantype, this.maxrate, this.banned, this.hours);
/*     */               }
/* 196 */             }).start();
/* 197 */             return true;
/*     */           }
/*     */           
/* 200 */           if (ClearedPlayerFile.wasPlayerCleared(playername)) {
/* 201 */             hours = ClearedPlayerFile.getHoursFromClear(playername);
/*     */           }
/*     */           
/* 204 */           this.checker.checkSingle(playername, sender, oreid, world, hours);
/* 205 */           return true;
/*     */         }
/*     */         
/*     */ 
/* 209 */         if ((playername.length() > 0) && (world.length() == 0) && (oreid > 0)) {
/* 210 */           world = Config.defaultWorld;
/* 211 */           if ((playername.equalsIgnoreCase("all")) && (maxrate > 0.0F)) {
/* 212 */             final CommandSender s = sender;
/* 213 */             final String w = world;
/* 214 */             final int oid = oreid;
/* 215 */             final String bt = bantype;
/* 216 */             final float mr = maxrate;
/* 217 */             final int h = hours;
/* 218 */             final boolean ban = this.plugin.banned;
/* 219 */             new BukkitRunnable()
/*     */             {
/*     */               public void run() {
/* 222 */                 Cmd.this.checker.listAllXRayers(s, w, oid, bt, mr, ban, h);
/*     */               }
/* 224 */             }.runTaskAsynchronously(this.plugin);
/* 225 */             return true;
/*     */           }
/*     */           
/* 228 */           if (ClearedPlayerFile.wasPlayerCleared(playername)) {
/* 229 */             hours = ClearedPlayerFile.getHoursFromClear(playername);
/*     */           }
/*     */           
/* 232 */           this.checker.checkSingle(playername, sender, oreid, world, hours);
/* 233 */           return true;
/*     */         }
/*     */       } else {
/* 236 */         sender.sendMessage(ChatColor.RED + "[XrayInformer]" + ChatColor.WHITE + " You do not have permission for this command.");
/* 237 */         return true;
/*     */       }
/*     */     }
/* 240 */     return succeed;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\XrayInformer.jar!\com\wesley27\xrayinformer\Cmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */