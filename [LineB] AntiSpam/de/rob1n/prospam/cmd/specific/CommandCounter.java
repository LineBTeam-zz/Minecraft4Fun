/*     */ package de.rob1n.prospam.cmd.specific;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import de.rob1n.prospam.chatter.Chatter;
/*     */ import de.rob1n.prospam.chatter.ChatterHandler;
/*     */ import de.rob1n.prospam.cmd.Command;
/*     */ import de.rob1n.prospam.data.specific.Settings;
/*     */ import de.rob1n.prospam.gui.Item;
/*     */ import de.rob1n.prospam.gui.Item.ClickAction;
/*     */ import java.util.Date;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ 
/*     */ public class CommandCounter extends Command implements de.rob1n.prospam.cmd.CommandWithGui
/*     */ {
/*     */   public CommandCounter(ProSpam plugin)
/*     */   {
/*  27 */     super(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/*  33 */     return "counter";
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  39 */     return "Displays spam history of the player. Since last restart";
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getArgs()
/*     */   {
/*  45 */     return new String[] { "<player>" };
/*     */   }
/*     */   
/*     */   public String[] getAliases()
/*     */   {
/*  50 */     return new String[] { "stats", "statistics" };
/*     */   }
/*     */   
/*     */   public void execute(CommandSender sender, String[] parameter)
/*     */     throws IllegalArgumentException
/*     */   {
/*  56 */     if ((isPlayer(sender)) && (parameter.length == 1))
/*     */     {
/*  58 */       Player player = (Player)sender;
/*     */       
/*  60 */       showGui(player);
/*     */     }
/*     */     else
/*     */     {
/*  64 */       if (parameter.length <= 1) {
/*  65 */         throw new IllegalArgumentException();
/*     */       }
/*     */       
/*  68 */       printStats(sender, Bukkit.getServer().getOfflinePlayer(parameter[1]).getUniqueId());
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showGui(Player player)
/*     */   {
/*  75 */     Set<Item> items = new HashSet();
/*     */     
/*  77 */     int iter = 0;
/*  78 */     for (final Player p : Bukkit.getServer().getOnlinePlayers())
/*     */     {
/*     */ 
/*  81 */       items.add(new Item(iter++, new org.bukkit.inventory.ItemStack(Material.SKULL_ITEM, 1, (short)3), p.getName(), "Click to print spam statistics", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/*  86 */           CommandCounter.this.printStats(player, p.getUniqueId());
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*  91 */     this.plugin.getGuiManager().openInventoryGui(player, "Player spam stats", items);
/*     */   }
/*     */   
/*     */   private void printStats(final CommandSender sender, final UUID uuid)
/*     */   {
/*  96 */     if (uuid == null)
/*     */     {
/*  98 */       sender.sendMessage(ProSpam.error("No stats for this player"));
/*  99 */       return;
/*     */     }
/*     */     
/* 102 */     sender.sendMessage(ProSpam.prefixed("Por favor aguarde, verificando status..."));
/*     */     
/* 104 */     Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new BukkitRunnable()
/*     */     {
/*     */ 
/*     */       public void run()
/*     */       {
/* 109 */         Chatter chatter = ChatterHandler.getChatter(uuid);
/*     */         
/* 111 */         String name = Bukkit.getOfflinePlayer(uuid).getName();
/*     */         
/* 113 */         sender.sendMessage(ProSpam.prefixed("Spam violations of '" + name + "' since last restart"));
/* 114 */         sender.sendMessage(ProSpam.prefixed(ChatColor.DARK_GRAY + "ID: " + chatter.getUUID()));
/* 115 */         sender.sendMessage("|  " + ChatColor.GRAY + "Caps: " + ChatColor.RESET + chatter.getSpamCountCaps());
/* 116 */         sender.sendMessage("|  " + ChatColor.GRAY + "Char spam: " + ChatColor.RESET + chatter.getSpamCountChars());
/* 117 */         sender.sendMessage("|  " + ChatColor.GRAY + "Flood: " + ChatColor.RESET + chatter.getSpamCountFlood());
/* 118 */         sender.sendMessage("|  " + ChatColor.GRAY + "Similar messages: " + ChatColor.RESET + chatter.getSpamCountSimilar());
/* 119 */         sender.sendMessage("|  " + ChatColor.GRAY + "Urls: " + ChatColor.RESET + chatter.getSpamCountUrls());
/* 120 */         sender.sendMessage("|  " + ChatColor.GRAY + "Blacklisted words: " + ChatColor.RESET + chatter.getSpamCountBlacklist());
/*     */         
/* 122 */         long spamStarted = chatter.getSpamStarted();
/* 123 */         if (spamStarted != 0L)
/*     */         {
/* 125 */           int nextReset = (int)(spamStarted + CommandCounter.this.settings.trigger_counter_reset * 1000 * 60 - new Date().getTime()) / 60000;
/*     */           
/* 127 */           sender.sendMessage("|  " + ChatColor.DARK_PURPLE + "Reset " + (nextReset > 0 ? "in " + nextReset + " minutes. Or type /prospam reset " + name : "with the next spam"));
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */ }


