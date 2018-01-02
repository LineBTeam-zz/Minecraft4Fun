/*     */ package net.risenphoenix.ipcheck.commands.ban;
/*     */ 
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.Command;
/*     */ import net.risenphoenix.commons.commands.CommandType;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.actions.ActionBroadcast;
/*     */ import net.risenphoenix.ipcheck.actions.ActionSBan;
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
/*     */ public class CmdSBan
/*     */   extends Command
/*     */ {
/*     */   public CmdSBan(Plugin plugin, String[] callArgs, CommandType type)
/*     */   {
/*  47 */     super(plugin, callArgs, type);
/*     */     
/*  49 */     setName(getLocalString("CMD_SBAN"));
/*  50 */     setHelp(getLocalString("HELP_SBAN"));
/*  51 */     setSyntax("ipc sban <PLAYER> [MESSAGE]");
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
/*  74 */     String ip_filter = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
/*     */     
/*     */ 
/*  77 */     if (args[1].matches(ip_filter)) {
/*  78 */       sendPlayerMessage(sender, getLocalString("SBAN_IP_HELP"));
/*  79 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  84 */     Object[] results = new ActionSBan(IPCheck.getInstance(), args[1], message).execute();
/*     */     
/*     */ 
/*  87 */     int count = ((Integer)results[0]).intValue();
/*     */     
/*     */ 
/*  90 */     IPCheck.getInstance().getStatisticsObject().logPlayerBan(count);
/*     */     
/*     */ 
/*  93 */     if (count == 0) {
/*  94 */       sendPlayerMessage(sender, getLocalString("NO_MODIFY"));
/*  95 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 100 */     String broadcastMsg = ChatColor.GOLD + "Player " + ChatColor.RED + "%s" + ChatColor.GOLD + " was banned by " + ChatColor.GREEN + "%s" + ChatColor.GOLD + " for: %s";
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 106 */     ActionBroadcast ab = new ActionBroadcast(broadcastMsg, new String[] {results[1].toString(), sender.getName(), message }, new Permission[] { new Permission("ipcheck.seeban") }, false);
/*     */     
/*     */ 
/*     */ 
/* 110 */     ab.execute();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\ban\CmdSBan.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */