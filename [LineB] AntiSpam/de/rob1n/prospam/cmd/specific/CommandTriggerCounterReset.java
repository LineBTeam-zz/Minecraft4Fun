/*     */ package de.rob1n.prospam.cmd.specific;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import de.rob1n.prospam.chatter.Chatter;
/*     */ import de.rob1n.prospam.chatter.ChatterHandler;
/*     */ import de.rob1n.prospam.cmd.Command;
/*     */ import de.rob1n.prospam.data.specific.Settings;
/*     */ import java.util.Set;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ 
/*     */ public class CommandTriggerCounterReset extends Command
/*     */ {
/*     */   public CommandTriggerCounterReset(ProSpam plugin)
/*     */   {
/*  19 */     super(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/*  25 */     return "trigger-counter-reset";
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  31 */     return "Reset the violation counter immediately/every x minutes (0=never)";
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getArgs()
/*     */   {
/*  37 */     return new String[] { "[minutes]", "[player]" };
/*     */   }
/*     */   
/*     */   public String[] getAliases()
/*     */   {
/*  42 */     return new String[] { "reset", "counter-reset", "stats-reset", "reset-stats" };
/*     */   }
/*     */   
/*     */   public void execute(final CommandSender sender, final String[] parameter)
/*     */     throws IllegalArgumentException
/*     */   {
/*  48 */     if (parameter.length <= 1)
/*     */     {
/*     */ 
/*  51 */       Set<Chatter> chatters = this.plugin.getChatterHandler().getChatters();
/*  52 */       for (Chatter chatter : chatters)
/*     */       {
/*  54 */         chatter.resetSpamViolations();
/*     */       }
/*     */       
/*  57 */       sender.sendMessage(ProSpam.prefixed("Spam violation counter successfully resetted"));
/*     */ 
/*     */ 
/*     */     }
/*  61 */     else if (parameter.length == 2)
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/*  66 */         int resetTime = Integer.parseInt(parameter[1]);
/*     */         
/*  68 */         if (resetTime < 0) {
/*  69 */           throw new IllegalArgumentException();
/*     */         }
/*  71 */         this.settings.trigger_counter_reset = resetTime;
/*     */         
/*  73 */         if (!this.settings.save())
/*     */         {
/*  75 */           sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*     */ 
/*     */ 
/*     */         }
/*  79 */         else if (resetTime != 0) {
/*  80 */           sender.sendMessage(ProSpam.prefixed("Reset timer successfully set to " + resetTime + " minutes"));
/*     */         } else {
/*  82 */           sender.sendMessage(ProSpam.prefixed("Timed trigger counter reset disabled"));
/*     */         }
/*     */         
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/*  88 */         Bukkit.getScheduler().runTaskAsynchronously(this.plugin, new org.bukkit.scheduler.BukkitRunnable()
/*     */         {
/*     */ 
/*     */           public void run()
/*     */           {
/*     */ 
/*  94 */             java.util.UUID uuid = Bukkit.getServer().getOfflinePlayer(parameter[1]).getUniqueId();
/*     */             
/*  96 */             if (uuid != null)
/*     */             {
/*  98 */               Chatter chatter = ChatterHandler.getChatter(uuid);
/*     */               
/* 100 */               String name = Bukkit.getOfflinePlayer(uuid).getName();
/*     */               
/* 102 */               chatter.resetSpamViolations();
/* 103 */               sender.sendMessage(ProSpam.prefixed("Spam violation counter successfully resetted for " + name));
/*     */             }
/*     */             else
/*     */             {
/* 107 */               sender.sendMessage(ProSpam.error("No stats for this player"));
/*     */             }
/*     */           }
/*     */         });
/*     */       }
/*     */     }
/*     */     else {
/* 114 */       throw new IllegalArgumentException();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandTriggerCounterReset.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */