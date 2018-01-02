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
/*     */ public class CmdUnban
/*     */   extends Command
/*     */ {
/*     */   public CmdUnban(Plugin plugin, String[] callArgs, CommandType type)
/*     */   {
/*  46 */     super(plugin, callArgs, type);
/*     */     
/*  48 */     setName(getLocalString("CMD_UNBAN"));
/*  49 */     setHelp(getLocalString("HELP_UNBAN"));
/*  50 */     setSyntax("ipc unban <PLAYER | IP>");
/*  51 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.unban") });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onExecute(CommandSender sender, String[] args)
/*     */   {
/*  59 */     if (!getPlugin().getConfigurationManager().getBoolean("should-manage-bans")) {
/*  60 */       sendPlayerMessage(sender, getLocalString("DISABLE_ERR"));
/*  61 */       return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  66 */     Object[] results = new ActionBan(IPCheck.getInstance(), sender, args[1], null, false).execute();
/*     */     
/*  68 */     int count = ((Integer)results[0]).intValue();
/*     */     
/*     */ 
/*  71 */     IPCheck.getInstance().getStatisticsObject().logPlayerUnban(count);
/*     */     
/*     */     String broadcastMsg;
/*     */     
/*     */     String broadcastMsg;
/*  76 */     if (count == 1) {
/*  77 */       broadcastMsg = ChatColor.GOLD + "Player " + ChatColor.RED + "%s" + ChatColor.GOLD + " was unbanned by " + ChatColor.GREEN + "%s" + ChatColor.GOLD + ".";
/*     */     }
/*     */     else
/*     */     {
/*  81 */       broadcastMsg = ChatColor.GOLD + "Player " + ChatColor.GREEN + "%s" + ChatColor.GOLD + " unbanned " + ChatColor.RED + "%s" + ChatColor.GOLD + " accounts.";
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  86 */     if (count > 0) {
/*  87 */       if (count == 1)
/*     */       {
/*  89 */         ActionBroadcast ab = new ActionBroadcast(broadcastMsg, new String[] { results[1].toString(), sender.getName() }, new Permission[] { new Permission("ipcheck.seeban") }, false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/*  94 */         ab.execute();
/*     */       }
/*     */       else {
/*  97 */         ActionBroadcast ab = new ActionBroadcast(broadcastMsg, new String[] { sender.getName(), count + "" }, new Permission[] { new Permission("ipcheck.seeban") }, false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 102 */         ab.execute();
/*     */       }
/*     */     } else {
/* 105 */       sendPlayerMessage(sender, getLocalString("NO_MODIFY"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\ban\CmdUnban.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */