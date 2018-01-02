/*     */ package me.au2001.PerfectBackup;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class PerfectBackup extends JavaPlugin
/*     */ {
/*  21 */   HashMap<CommandSender, Integer> restoreconf = new HashMap();
/*  22 */   HashMap<CommandSender, Integer> deleteconf = new HashMap();
/*     */   public static Plugin plugin;
/*     */   public static FileConfiguration config;
/*     */   public static FileConfiguration lang;
/*     */   public static File backupfolder;
/*     */   public static String prefix;
/*     */   
/*     */   public void onEnable()
/*     */   {
/*  31 */     if (!getDataFolder().isDirectory()) {
/*  32 */       BackupUtils.setFilePerms(getDataFolder());
/*  33 */       getDataFolder().mkdir();
/*  34 */       BackupUtils.setFilePerms(getDataFolder());
/*     */     }
/*     */     
/*  37 */     File conffile = new File(getDataFolder(), "config.yml");
/*  38 */     if (!conffile.isFile()) {
/*  39 */       BackupUtils.setFilePerms(conffile);
/*  40 */       saveDefaultConfig();
/*  41 */       BackupUtils.setFilePerms(conffile);
/*     */     }
/*     */     
/*  44 */     File langfile = new File(getDataFolder(), "language.yml");
/*  45 */     if (!langfile.isFile()) {
/*  46 */       BackupUtils.setFilePerms(langfile);
/*  47 */       saveResource(langfile.getName(), false);
/*  48 */       BackupUtils.setFilePerms(langfile);
/*     */     }
/*     */     
/*  51 */     plugin = this;
/*  52 */     config = getConfig();
/*  53 */     lang = org.bukkit.configuration.file.YamlConfiguration.loadConfiguration(langfile);
/*  54 */     prefix = ChatColor.translateAlternateColorCodes('&', lang.getString("prefix").replace("\\n", "\n"));
/*     */     
/*  56 */     BackupRestorer.log = config.getBoolean("debug");
/*     */     
/*  58 */     backupfolder = new File(config.getString("localpath"));
/*  59 */     if (!backupfolder.isDirectory()) {
/*  60 */       BackupUtils.setFilePerms(backupfolder);
/*  61 */       backupfolder.mkdirs();
/*  62 */       BackupUtils.setFilePerms(backupfolder);
/*     */     }
/*     */     try
/*     */     {
/*  66 */       new MetricsLite(plugin).start();
/*     */     } catch (IOException e) {
/*  68 */       Bukkit.getLogger().info(ChatColor.stripColor(prefix) + "Error while loading MetricsLite:");
/*  69 */       e.printStackTrace();
/*     */     }
/*     */     
/*  72 */     if ((config.isString("interval")) && (config.getString("interval").matches("[0-9]+ [tsmhd]"))) {
/*  73 */       long unit = Long.parseLong(config.getString("interval").split(" ")[0]);
/*  74 */       if (config.getString("interval").split(" ")[1].equals("s")) { unit *= 20L;
/*  75 */       } else if (config.getString("interval").split(" ")[1].equals("m")) { unit *= 1200L;
/*  76 */       } else if (config.getString("interval").split(" ")[1].equals("h")) { unit *= 72000L;
/*  77 */       } else if (config.getString("interval").split(" ")[1].equals("d")) { unit *= 1728000L;
/*     */       }
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
/* 101 */       new BukkitRunnable()
/*     */       {
/*     */         public void run()
/*     */         {
/*  80 */           if (PerfectBackup.plugin.isEnabled()) {
/*  81 */             String message = ChatColor.translateAlternateColorCodes('&', PerfectBackup.lang.getString("started").replace("\\n", "\n"));
/*  82 */             PerfectBackup.this.getLogger().info(ChatColor.stripColor(message));
/*  83 */             for (Player player : Bukkit.getOnlinePlayers())
/*  84 */               if ((PerfectBackup.config.getBoolean("broadcast")) || (player.hasPermission("PerfectBackup.warning")))
/*  85 */                 player.sendMessage(PerfectBackup.prefix + message);
/*  86 */             if (BackupUtils.createBackup() != null) {
/*  87 */               message = ChatColor.translateAlternateColorCodes('&', PerfectBackup.lang.getString("ended").replace("\\n", "\n"));
/*  88 */               PerfectBackup.this.getLogger().info(ChatColor.stripColor(message));
/*  89 */               for (Player player : Bukkit.getOnlinePlayers())
/*  90 */                 if ((PerfectBackup.config.getBoolean("broadcast")) || (player.hasPermission("PerfectBackup.warning")))
/*  91 */                   player.sendMessage(PerfectBackup.prefix + message);
/*     */             } else {
/*  93 */               message = ChatColor.translateAlternateColorCodes('&', PerfectBackup.lang.getString("failed").replace("\\n", "\n"));
/*  94 */               PerfectBackup.this.getLogger().warning(ChatColor.stripColor(message));
/*  95 */               for (Player player : Bukkit.getOnlinePlayers())
/*  96 */                 if ((PerfectBackup.config.getBoolean("broadcast")) || (player.hasPermission("PerfectBackup.warning")))
/*  97 */                   player.sendMessage(PerfectBackup.prefix + message);
/*     */             }
/*  99 */           } else { cancel();
/*     */           }
/* 101 */         } }.runTaskTimerAsynchronously(plugin, unit, unit); }
/* 102 */     if (config.isString("crontask"))
/* 103 */       new CronRunnable(config.getString("crontask")); {
/*     */         public void exec() {
/* 105 */           if (PerfectBackup.plugin.isEnabled()) {
/* 106 */             String message = ChatColor.translateAlternateColorCodes('&', PerfectBackup.lang.getString("started").replace("\\n", "\n"));
/* 107 */             PerfectBackup.this.getLogger().info(ChatColor.stripColor(message));
/* 108 */             for (Player player : Bukkit.getOnlinePlayers())
/* 109 */               if ((PerfectBackup.config.getBoolean("broadcast")) || (player.hasPermission("PerfectBackup.warning")))
/* 110 */                 player.sendMessage(PerfectBackup.prefix + message);
/* 111 */             if (BackupUtils.createBackup() != null) {
/* 112 */               message = ChatColor.translateAlternateColorCodes('&', PerfectBackup.lang.getString("ended").replace("\\n", "\n"));
/* 113 */               PerfectBackup.this.getLogger().info(ChatColor.stripColor(message));
/* 114 */               for (Player player : Bukkit.getOnlinePlayers())
/* 115 */                 if ((PerfectBackup.config.getBoolean("broadcast")) || (player.hasPermission("PerfectBackup.warning")))
/* 116 */                   player.sendMessage(PerfectBackup.prefix + message);
/*     */             } else {
/* 118 */               message = ChatColor.translateAlternateColorCodes('&', PerfectBackup.lang.getString("failed").replace("\\n", "\n"));
/* 119 */               PerfectBackup.this.getLogger().warning(ChatColor.stripColor(message));
/* 120 */               for (Player player : Bukkit.getOnlinePlayers())
/* 121 */                 if ((PerfectBackup.config.getBoolean("broadcast")) || (player.hasPermission("PerfectBackup.warning")))
/* 122 */                   player.sendMessage(PerfectBackup.prefix + message);
/*     */             }
/* 124 */           } else { cancel();
/*     */           }
/*     */         }
/*     */       } }
/*     */   
/*     */   public boolean onCommand(final CommandSender sender, Command cmd, String commandLabel, String[] args) { int i;
/*     */     File backup;
/* 131 */     if (cmd.getLabel().equals("backups")) {
/* 132 */       if (sender.hasPermission("PerfectBackup.list")) {
/* 133 */         File[] files = BackupUtils.listBackups();
/* 134 */         if (files.length > 0) {
/* 135 */           if ((args.length == 0) || (args[0].matches("[0-9]+"))) {
/* 136 */             long page = args.length > 0 ? Long.parseLong(args[0]) : 1L;
/* 137 */             int total = (int)Math.ceil(files.length / 10.0D);
/* 138 */             if (page <= total) {
/* 139 */               sender.sendMessage(prefix + files.length + " backup criado (" + ChatColor.RED + "page " + page + "/" + total + ChatColor.RESET + "):");
/* 140 */               i = 0;
/* 141 */               File[] arrayOfFile2; int j = (arrayOfFile2 = files).length; for (int i = 0; i < j; i++) { backup = arrayOfFile2[i];
/* 142 */                 i++; if (i > page * 10L) break; if (i > (page - 1L) * 10L) {
/* 143 */                   String size = BackupUtils.bytesToString(backup.length());
/* 144 */                   sender.sendMessage(files.length - i + 1 + ". \"" + ChatColor.ITALIC + backup.getName() + ChatColor.RESET + "\" (" + size + ")");
/*     */                 }
/*     */               }
/*     */             } else {
/* 148 */               sender.sendMessage(prefix + ChatColor.RED + "Invalid page number, it is out of range.");
/*     */             }
/*     */           } else {
/* 151 */             sender.sendMessage(prefix + ChatColor.RED + "Numero inválido, precisa ser positivo.");
/*     */           }
/*     */         } else {
/* 154 */           sender.sendMessage(prefix + ChatColor.RED + "Nenhum backup criado ainda.");
/*     */         }
/*     */       } else {
/* 157 */         sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', lang.getString("permission").replace("\\n", "\n")));
/*     */       }
/* 159 */     } else if (cmd.getLabel().equals("backup")) {
/* 160 */       if (sender.hasPermission("PerfectBackup.backup")) {
/* 161 */         String message = ChatColor.translateAlternateColorCodes('&', lang.getString("manualstart").replace("\\n", "\n"));
/* 162 */         getLogger().info(ChatColor.stripColor(message));
/* 163 */         for (Player player : Bukkit.getOnlinePlayers()) {
/* 164 */           if ((config.getBoolean("manualbroadcast")) || (player.hasPermission("PerfectBackup.warning"))) {
/* 165 */             player.sendMessage(prefix + message);
/*     */           }
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
/* 187 */         new Thread(null, new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             World world;
/* 168 */             for (Iterator localIterator1 = Bukkit.getWorlds().iterator(); localIterator1.hasNext(); world.save()) world = (World)localIterator1.next();
/* 169 */             File backup = BackupUtils.createBackup();
/* 170 */             if (backup != null) {
/* 171 */               String message = ChatColor.translateAlternateColorCodes('&', PerfectBackup.lang.getString("manualend").replace("\\n", "\n"));
/* 172 */               PerfectBackup.this.getLogger().info(ChatColor.stripColor(message));
/* 173 */               for (Player player : Bukkit.getOnlinePlayers())
/* 174 */                 if ((PerfectBackup.config.getBoolean("manualbroadcast")) || (player.hasPermission("PerfectBackup.warning")))
/* 175 */                   player.sendMessage(PerfectBackup.prefix + message);
/* 176 */               if (((backup.exists()) && (backup.isDirectory())) || (backup.isFile())) {
/* 177 */                 sender.sendMessage(PerfectBackup.prefix + "\"" + backup.getName() + "\" (" + BackupUtils.bytesToString(backup.length()) + ")");
/*     */               }
/*     */             } else {
/* 180 */               String message = ChatColor.translateAlternateColorCodes('&', PerfectBackup.lang.getString("manualfail").replace("\\n", "\n"));
/* 181 */               PerfectBackup.this.getLogger().info(ChatColor.stripColor(message));
/* 182 */               for (Player player : Bukkit.getOnlinePlayers())
/* 183 */                 if ((PerfectBackup.config.getBoolean("manualbroadcast")) || (player.hasPermission("PerfectBackup.warning")))
/* 184 */                   player.sendMessage(PerfectBackup.prefix + message);
/*     */             }
/*     */           }
/* 187 */         }, "PerfectBackup").start();
/*     */       } else {
/* 189 */         sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', lang.getString("permission").replace("\\n", "\n")));
/*     */       }
/* 191 */     } else if (cmd.getLabel().equals("restore")) {
/* 192 */       if (sender.hasPermission("PerfectBackup.restore")) {
/* 193 */         if ((args.length == 0) || ((args[0].matches("[0-9]{0,9}")) && (Integer.parseInt(args[0]) > 0))) {
/* 194 */           if ((args.length == 0) || (Integer.parseInt(args[0]) <= BackupUtils.listBackups().length)) {
/* 195 */             if (!this.restoreconf.containsKey(sender)) {
/* 196 */               int id = args.length > 0 ? Integer.parseInt(args[0]) : BackupUtils.listBackups().length;
/* 197 */               if (id > 0) {
/* 198 */                 sender.sendMessage(prefix + "Você realmente quer restaurar o backup #" + id + "?");
/* 199 */                 sender.sendMessage(prefix + "O servidor será reiniciado e não será possivel desfazer!");
/* 200 */                 sender.sendMessage(prefix + "Se quiser continuar digite /restore em menos de 10s.");
/* 201 */                 this.restoreconf.put(sender, Integer.valueOf(id));
/* 202 */                 new BukkitRunnable() {
/*     */                   public void run() {
/* 204 */                     if (PerfectBackup.this.restoreconf.remove(sender) != null)
/* 205 */                       sender.sendMessage(PerfectBackup.prefix + "A restauração foi cancelada.");
/*     */                   }
/* 207 */                 }.runTaskLater(plugin, 200L);
/*     */               } else {
/* 209 */                 sender.sendMessage(prefix + ChatColor.RED + "Nenhum backup criado ainda.");
/*     */               }
/*     */             } else {
/* 212 */               int id = ((Integer)this.restoreconf.remove(sender)).intValue();
/* 213 */               sender.sendMessage(prefix + "Restaurando o backup #" + id + ", por favor aguarde...");
/*     */               try {
/* 215 */                 File zip = BackupUtils.listBackups()[(id - 1)];
/* 216 */                 File tmp = new File("");
/* 217 */                 for (Player player : Bukkit.getOnlinePlayers())
/* 218 */                   player.kickPlayer(ChatColor.translateAlternateColorCodes('&', lang.getString("kick").replace("\\n", "\n")));
/* 219 */                 BackupRestorer.main(new String[] { zip.getAbsolutePath(), tmp.getAbsolutePath(), "true" });
/* 220 */                 if (config.getBoolean("restorerestart", false)) {
/*     */                   try { File[] arrayOfFile1;
/* 222 */                     backup = (arrayOfFile1 = new File(".").listFiles()).length; for (i = 0; i < backup; i++) { File file = arrayOfFile1[i];
/* 223 */                       if ((file.isFile()) && (file.getName().endsWith(".jar"))) {
/* 224 */                         Runtime.getRuntime().exec("cd " + args[1] + ";sleep 3;java -server -jar " + file.getName());
/* 225 */                         getLogger().info("Reiniciando o servidor " + file.getName());
/* 226 */                         break;
/*     */                       }
/*     */                     }
/*     */                   } catch (IOException e) {
/* 230 */                     getLogger().info("Erro enquanto reiniciava o server:");
/* 231 */                     e.printStackTrace();
/* 232 */                     getLogger().info("Reinicie o servidor manualmente.");
/*     */                   }
/*     */                 }
/* 235 */                 Bukkit.shutdown();
/*     */               } catch (Exception e) {
/* 237 */                 sender.sendMessage(prefix + ChatColor.RED + "Erro enquanto restaurava o server, leia os logs.");
/* 238 */                 e.printStackTrace();
/*     */               }
/*     */             }
/*     */           } else {
/* 242 */             sender.sendMessage(prefix + ChatColor.RED + "Backup inválido, ID muito antiga:");
/*     */           }
/*     */         } else {
/* 245 */           sender.sendMessage(prefix + ChatColor.RED + "Backup inválido, precisa ser positivo:");
/*     */         }
/*     */       } else {
/* 248 */         sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', lang.getString("permission").replace("\\n", "\n")));
/*     */       }
/* 250 */     } else if (cmd.getLabel().equals("delbackup")) {
/* 251 */       if (sender.hasPermission("PerfectBackup.delbackup")) {
/* 252 */         if ((args.length == 0) || ((args[0].matches("[0-9]{0,9}")) && (Integer.parseInt(args[0]) > 0))) {
/* 253 */           if ((args.length == 0) || (Integer.parseInt(args[0]) <= BackupUtils.listBackups().length)) {
/* 254 */             if (!this.deleteconf.containsKey(sender)) {
/* 255 */               int id = args.length > 0 ? Integer.parseInt(args[0]) : BackupUtils.listBackups().length;
/* 256 */               if (id > 0) {
/* 257 */                 sender.sendMessage(prefix + "Você realmente quer deletar o backup: #" + id + "?");
/* 258 */                 sender.sendMessage(prefix + "Digite se sim: /delbackup em menos de 10s.");
/* 259 */                 this.deleteconf.put(sender, Integer.valueOf(id));
/* 260 */                 new BukkitRunnable() {
/*     */                   public void run() {
/* 262 */                     if (PerfectBackup.this.deleteconf.remove(sender) != null)
/* 263 */                       sender.sendMessage(PerfectBackup.prefix + "Deletar backup cancelado.");
/*     */                   }
/* 265 */                 }.runTaskLater(plugin, 200L);
/*     */               } else {
/* 267 */                 sender.sendMessage(prefix + ChatColor.RED + "Nenhum backup foi criado ainda.");
/*     */               }
/*     */             } else {
/* 270 */               int id = ((Integer)this.deleteconf.remove(sender)).intValue();
/* 271 */               File file = BackupUtils.listBackups()[(id - 1)];
/* 272 */               String size = BackupUtils.bytesToString(file.length());
/* 273 */               BackupUtils.deleteFile(file);
/* 274 */               sender.sendMessage(prefix + "Backup deletado com sucesso: #" + id + " (" + size + ").");
/*     */             }
/*     */           } else {
/* 277 */             sender.sendMessage(prefix + ChatColor.RED + "Backup inválido, ID muito antiga:");
/*     */           }
/*     */         } else {
/* 280 */           sender.sendMessage(prefix + ChatColor.RED + "Backup inválido, precisa ser positivo.");
/*     */         }
/*     */       } else {
/* 283 */         sender.sendMessage(prefix + ChatColor.translateAlternateColorCodes('&', lang.getString("permission").replace("\\n", "\n")));
/*     */       }
/*     */     }
/* 286 */     return false;
/*     */   }
/*     */ }


