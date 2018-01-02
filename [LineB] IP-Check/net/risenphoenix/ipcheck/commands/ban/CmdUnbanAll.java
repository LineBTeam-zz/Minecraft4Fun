/*    */ package net.risenphoenix.ipcheck.commands.ban;
/*    */ 
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.commands.Command;
/*    */ import net.risenphoenix.commons.commands.CommandManager;
/*    */ import net.risenphoenix.commons.commands.CommandType;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.permissions.Permission;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CmdUnbanAll
/*    */   extends Command
/*    */ {
/*    */   public CmdUnbanAll(Plugin plugin, String[] callArgs, CommandType type)
/*    */   {
/* 43 */     super(plugin, callArgs, type);
/*    */     
/* 45 */     setName(getLocalString("CMD_UNBANALL"));
/* 46 */     setHelp(getLocalString("HELP_UNBANALL"));
/* 47 */     setSyntax("ipc unbanall <START_TIME> <STOP_TIME | now>");
/* 48 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.unban"), new Permission("ipcheck.unbanall") });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onExecute(CommandSender sender, String[] args)
/*    */   {
/* 58 */     CmdBanAll cmd = (CmdBanAll)getPlugin().getCommandManager().getCommand(getLocalString("CMD_BANALL"));
/*    */     
/*    */ 
/* 61 */     if (cmd != null) {
/* 62 */       cmd.executeBan(sender, args, false);
/*    */     } else {
/* 64 */       sendPlayerMessage(sender, getLocalString("CMD_FETCH_ERR"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\ban\CmdUnbanAll.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */