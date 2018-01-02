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
/*    */ public class CmdExemptListPlayer
/*    */   extends Command
/*    */ {
/*    */   public CmdExemptListPlayer(Plugin plugin, String[] callArgs, CommandType type)
/*    */   {
/* 43 */     super(plugin, callArgs, type);
/*    */     
/* 45 */     setName(getLocalString("CMD_EXEMPT_LIST_PLAYER"));
/* 46 */     setHelp(getLocalString("HELP_EXEMPT_LIST_PLAYER"));
/* 47 */     setSyntax("ipc exempt-list player");
/* 48 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.list") });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onExecute(CommandSender sender, String[] args)
/*    */   {
/* 57 */     CmdExemptListAll cmd = (CmdExemptListAll)getPlugin().getCommandManager().getCommand(
/* 58 */       getLocalString("CMD_EXEMPT_LIST"));
/*    */     
/*    */ 
/* 61 */     if (cmd != null) {
/* 62 */       cmd.executeList(sender, args, CmdExemptListAll.ListType.PLAYER);
/*    */     } else {
/* 64 */       sendPlayerMessage(sender, getLocalString("CMD_FETCH_ERR"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\exempt\list\CmdExemptListPlayer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */