/*     */ package de.rob1n.prospam.cmd.specific;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import de.rob1n.prospam.cmd.Command;
/*     */ import de.rob1n.prospam.data.specific.Settings;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ 
/*     */ public class CommandTriggers
/*     */   extends Command
/*     */ {
/*     */   public CommandTriggers(ProSpam plugin)
/*     */   {
/*  17 */     super(plugin);
/*     */   }
/*     */   
/*     */   public String getName()
/*     */   {
/*  22 */     return "triggers";
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  28 */     return "Displays the defined spam triggers";
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getArgs()
/*     */   {
/*  34 */     return new String[] { "[caps|chars|flood|similar|urls|blacklist]" };
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getAliases()
/*     */   {
/*  40 */     return new String[] { "trigger", "t" };
/*     */   }
/*     */   
/*     */   public void execute(CommandSender sender, String[] parameter)
/*     */     throws IllegalArgumentException
/*     */   {
/*  46 */     String trigger = parameter.length <= 1 ? "all" : parameter[1];
/*     */     
/*  48 */     if ((!trigger.equalsIgnoreCase("all")) && (!trigger.equalsIgnoreCase("caps")) && (!trigger.equalsIgnoreCase("chars")) && (!trigger.equalsIgnoreCase("flood")) && (!trigger.equalsIgnoreCase("similar")) && (!trigger.equalsIgnoreCase("urls")) && (!trigger.equalsIgnoreCase("blacklist")))
/*     */     {
/*  50 */       throw new IllegalArgumentException();
/*     */     }
/*  52 */     sender.sendMessage(ProSpam.prefixed("Displaying triggers"));
/*     */     
/*  54 */     if ((trigger.equalsIgnoreCase("caps")) || (trigger.equals("all")))
/*     */     {
/*  56 */       sender.sendMessage(ChatColor.GOLD + "Caps triggers" + ChatColor.RESET + " [" + (this.settings.trigger_enabled_caps ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()) + ChatColor.RESET + "]");
/*     */       
/*  58 */       displayTrigger(sender, this.settings.trigger_caps);
/*     */     }
/*  60 */     if ((trigger.equalsIgnoreCase("chars")) || (trigger.equals("all")))
/*     */     {
/*  62 */       sender.sendMessage(ChatColor.GOLD + "Chars triggers" + ChatColor.RESET + " [" + (this.settings.trigger_enabled_chars ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()) + ChatColor.RESET + "]");
/*     */       
/*  64 */       displayTrigger(sender, this.settings.trigger_chars);
/*     */     }
/*  66 */     if ((trigger.equalsIgnoreCase("flood")) || (trigger.equals("all")))
/*     */     {
/*  68 */       sender.sendMessage(ChatColor.GOLD + "Flood triggers" + ChatColor.RESET + " [" + (this.settings.trigger_enabled_flood ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()) + ChatColor.RESET + "]");
/*     */       
/*  70 */       displayTrigger(sender, this.settings.trigger_flood);
/*     */     }
/*  72 */     if ((trigger.equalsIgnoreCase("similar")) || (trigger.equals("all")))
/*     */     {
/*  74 */       sender.sendMessage(ChatColor.GOLD + "Similar triggers" + ChatColor.RESET + " [" + (this.settings.trigger_enabled_similar ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()) + ChatColor.RESET + "]");
/*     */       
/*  76 */       displayTrigger(sender, this.settings.trigger_similar);
/*     */     }
/*  78 */     if ((trigger.equalsIgnoreCase("urls")) || (trigger.equals("all")))
/*     */     {
/*  80 */       sender.sendMessage(ChatColor.GOLD + "URL triggers" + ChatColor.RESET + " [" + (this.settings.trigger_enabled_urls ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()) + ChatColor.RESET + "]");
/*     */       
/*  82 */       displayTrigger(sender, this.settings.trigger_urls);
/*     */     }
/*  84 */     if ((trigger.equalsIgnoreCase("blacklist")) || (trigger.equals("all")))
/*     */     {
/*  86 */       sender.sendMessage(ChatColor.GOLD + "Blacklist triggers" + ChatColor.RESET + " [" + (this.settings.trigger_enabled_blacklist ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()) + ChatColor.RESET + "]");
/*     */       
/*  88 */       displayTrigger(sender, this.settings.trigger_blacklist);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private void displayTrigger(CommandSender sender, HashMap<Integer, List<String>> trigger)
/*     */   {
/*  95 */     for (Map.Entry<Integer, List<String>> pairs : trigger.entrySet())
/*     */     {
/*  97 */       spamOccurence = ((Integer)pairs.getKey()).intValue();
/*     */       
/*  99 */       for (String cmd : (List)pairs.getValue())
/*     */       {
/* 101 */         sender.sendMessage("|  " + ChatColor.GRAY + "[" + spamOccurence + "] " + ChatColor.RESET + cmd);
/*     */       }
/*     */     }
/*     */     int spamOccurence;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandTriggers.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */