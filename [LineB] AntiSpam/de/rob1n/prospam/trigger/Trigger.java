/*     */ package de.rob1n.prospam.trigger;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import de.rob1n.prospam.chatter.Chatter;
/*     */ import de.rob1n.prospam.data.specific.Settings;
/*     */ import de.rob1n.prospam.exception.IllegalCommandNameException;
/*     */ import de.rob1n.prospam.exception.PlayerNotOnlineException;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class Trigger
/*     */ {
/*     */   protected final ProSpam plugin;
/*     */   protected final Settings settings;
/*     */   
/*     */   public Trigger(ProSpam plugin)
/*     */   {
/*  24 */     this.plugin = plugin;
/*  25 */     this.settings = plugin.getDataHandler().getSettings();
/*     */   }
/*     */   
/*     */   public boolean execute(Chatter chatter, int spamCount, HashMap<Integer, List<String>> cmdMap)
/*     */   {
/*  30 */     long timeNow = new Date().getTime();
/*  31 */     long spamStarted = chatter.getSpamStarted();
/*     */     
/*  33 */     List<String> everytimeActions = null;
/*  34 */     Iterator<Map.Entry<Integer, List<String>>> it = cmdMap.entrySet().iterator();
/*     */     Player player;
/*     */     try
/*     */     {
/*  38 */       player = this.plugin.getChatterHandler().getPlayer(chatter);
/*     */     }
/*     */     catch (PlayerNotOnlineException e)
/*     */     {
/*  42 */       ProSpam.log(Level.WARNING, "Could not execute trigger: " + e.getMessage());
/*  43 */       return false;
/*     */     }
/*     */     
/*  46 */     if ((this.settings.trigger_counter_reset != 0) && (timeNow - spamStarted >= this.settings.trigger_counter_reset * 1000 * 60)) {
/*  47 */       chatter.resetSpamViolations();
/*     */     }
/*  49 */     while (it.hasNext())
/*     */     {
/*  51 */       Map.Entry<Integer, List<String>> pairs = (Map.Entry)it.next();
/*     */       
/*     */       try
/*     */       {
/*  55 */         int key = ((Integer)pairs.getKey()).intValue();
/*  56 */         List<String> cmds = (List)pairs.getValue();
/*     */         
/*  58 */         if (key == 0) {
/*  59 */           everytimeActions = cmds;
/*     */         }
/*  61 */         if (key == spamCount)
/*     */         {
/*  63 */           everytimeActions = null;
/*     */           
/*  65 */           execute(player, cmds);
/*     */         }
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  70 */         ProSpam.log(Level.WARNING, "Could not execute trigger: " + e.getMessage());
/*  71 */         return false;
/*     */       }
/*     */     }
/*     */     
/*  75 */     if (everytimeActions != null)
/*     */     {
/*     */       try
/*     */       {
/*  79 */         execute(player, everytimeActions);
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/*  83 */         ProSpam.log(Level.WARNING, "Could not execute trigger: " + e.getMessage());
/*  84 */         return false;
/*     */       }
/*     */     }
/*     */     
/*  88 */     return true;
/*     */   }
/*     */   
/*     */   private void execute(Player player, List<String> cmds) throws IllegalCommandNameException, PlayerNotOnlineException
/*     */   {
/*  93 */     for (String cmd : cmds)
/*     */     {
/*     */ 
/*     */       try
/*     */       {
/*     */ 
/*  99 */         cmd = cmd.replaceAll("\\{u\\}", player.getName());
/*     */         
/* 101 */         cmd = cmd.replaceAll("\\{i\\}", player.getUniqueId().toString());
/*     */         
/* 103 */         cmd = cmd.substring(1);
/*     */ 
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 108 */         throw new IllegalCommandNameException();
/*     */       }
/*     */       
/* 111 */       if (player.isOnline())
/*     */       {
/* 113 */         String rawMsgCmd = "raw ";
/*     */         
/*     */ 
/* 116 */         if (cmd.startsWith(rawMsgCmd))
/*     */         {
/*     */ 
/* 119 */           String colorReadyCmd = org.bukkit.ChatColor.translateAlternateColorCodes('&', cmd);
/*     */           
/*     */ 
/* 122 */           player.sendMessage(colorReadyCmd.substring(rawMsgCmd.length()));
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 127 */           String colorReadyCmd = de.rob1n.prospam.data.ConfigFile.replaceColorCodes(cmd);
/*     */           
/*     */ 
/* 130 */           if (!Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), colorReadyCmd)) {
/* 131 */             throw new IllegalCommandNameException();
/*     */           }
/*     */         }
/* 134 */         ProSpam.log(Level.INFO, "Trigger executed: /" + cmd);
/*     */       }
/*     */       else {
/* 137 */         throw new PlayerNotOnlineException();
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\trigger\Trigger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */