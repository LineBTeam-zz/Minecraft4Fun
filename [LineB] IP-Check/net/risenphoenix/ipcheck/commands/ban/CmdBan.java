/*     */ package net.risenphoenix.ipcheck.commands.ban;
/*     */ 
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.Command;
/*     */ import net.risenphoenix.commons.commands.CommandType;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.actions.ActionBan;
/*     */ import net.risenphoenix.ipcheck.actions.ActionBroadcast;
/*     */ import net.risenphoenix.ipcheck.objects.StatsObject;
/*     */ import net.risenphoenix.ipcheck.util.MessageParser;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
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
/*     */ public class CmdBan
/*     */   extends Command
/*     */ {
/*     */   public CmdBan(Plugin plugin, String[] callArgs, CommandType type)
/*     */   {
/*  47 */     super(plugin, callArgs, type);
/*     */     
/*  49 */     setName(getLocalString("CMD_BAN"));
/*  50 */     setHelp(getLocalString("HELP_BAN"));
/*  51 */     setSyntax("ipc ban <PLAYER | IP> [MESSAGE]");
/*  52 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.ban") });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onExecute(CommandSender sender, String[] args)
/*     */   {
/*  60 */     if (!getPlugin().getConfigurationManager().getBoolean("should-manage-bans")) {
/*  61 */       sendPlayerMessage(sender, getLocalString("DISABLE_ERR"));
/*  62 */       return;
/*     */     }
/*     */     
/*     */ 
/*  66 */     String message = new MessageParser(args, 2).parseMessage();
/*     */     
/*     */ 
/*  69 */     if ((message == null) || (message.length() <= 0))
/*     */     {
/*  71 */       message = IPCheck.getInstance().getConfigurationManager().getString("ban-message");
/*     */     }
/*     */     
/*     */ 
/*  75 */     Object[] results = new ActionBan(IPCheck.getInstance(), sender, args[1], message, true).execute();
/*     */     
/*  77 */     int count = ((Integer)results[0]).intValue();
/*     */     
/*     */ 
/*  80 */     IPCheck.getInstance().getStatisticsObject().logPlayerBan(count);
/*     */     
/*     */     String broadcastMsg;
/*     */     
/*     */     String broadcastMsg;
/*     */     
/*  86 */     if (count == 1) {
/*  87 */       broadcastMsg = ChatColor.GOLD + "Player " + ChatColor.RED + "%s" + ChatColor.GOLD + " was banned by " + ChatColor.GREEN + "%s" + ChatColor.GOLD + " for: %s";
/*     */     }
/*     */     else
/*     */     {
/*  91 */       broadcastMsg = ChatColor.GOLD + "Player " + ChatColor.GREEN + "%s" + ChatColor.GOLD + " banned " + ChatColor.RED + "%s" + ChatColor.GOLD + " accounts for: " + "%s";
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  96 */     if (count > 0) {
/*  97 */       if (count == 1)
/*     */       {
/*  99 */         ActionBroadcast ab = new ActionBroadcast(broadcastMsg, new String[] { results[1].toString(), sender.getName(), message }, new Permission[] { new Permission("ipcheck.seeban") }, false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 104 */         ab.execute();
/*     */       }
/*     */       else {
/* 107 */         ActionBroadcast ab = new ActionBroadcast(broadcastMsg, new String[] { sender.getName(), count + "", message }, new Permission[] { new Permission("ipcheck.seeban") }, false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 112 */         ab.execute();
/*     */       }
/*     */     } else {
/* 115 */       sendPlayerMessage(sender, getLocalString("NO_MODIFY"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\ban\CmdBan.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */