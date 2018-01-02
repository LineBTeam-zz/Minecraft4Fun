/*    */ package net.risenphoenix.ipcheck.commands.exempt.list;
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
/*    */ public class CmdExemptListIP
/*    */   extends Command
/*    */ {
/*    */   public CmdExemptListIP(Plugin plugin, String[] callArgs, CommandType type)
/*    */   {
/* 43 */     super(plugin, callArgs, type);
/*    */     
/* 45 */     setName(getLocalString("CMD_EXEMPT_LIST_IP"));
/* 46 */     setHelp(getLocalString("HELP_EXEMPT_LIST_IP"));
/* 47 */     setSyntax("ipc exempt-list ip");
/* 48 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.list"), new Permission("ipcheck.showip") });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onExecute(CommandSender sender, String[] args)
/*    */   {
/* 58 */     CmdExemptListAll cmd = (CmdExemptListAll)getPlugin().getCommandManager().getCommand(
/* 59 */       getLocalString("CMD_EXEMPT_LIST"));
/*    */     
/*    */ 
/* 62 */     if (cmd != null) {
/* 63 */       cmd.executeList(sender, args, CmdExemptListAll.ListType.IP);
/*    */     } else {
/* 65 */       sendPlayerMessage(sender, getLocalString("CMD_FETCH_ERR"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\exempt\list\CmdExemptListIP.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */