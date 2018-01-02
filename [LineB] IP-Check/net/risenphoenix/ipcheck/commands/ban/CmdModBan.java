/*    */ package net.risenphoenix.ipcheck.commands.ban;
/*    */ 
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.commands.Command;
/*    */ import net.risenphoenix.commons.commands.CommandType;
/*    */ import net.risenphoenix.ipcheck.IPCheck;
/*    */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*    */ import net.risenphoenix.ipcheck.objects.UserObject;
/*    */ import net.risenphoenix.ipcheck.util.MessageParser;
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
/*    */ public class CmdModBan
/*    */   extends Command
/*    */ {
/*    */   public CmdModBan(Plugin plugin, String[] callArgs, CommandType type)
/*    */   {
/* 45 */     super(plugin, callArgs, type);
/*    */     
/* 47 */     setName(getLocalString("CMD_MODBAN"));
/* 48 */     setHelp(getLocalString("HELP_MODBAN"));
/* 49 */     setSyntax("ipc modban <PLAYER> <MESSAGE>");
/* 50 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.banmodify") });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onExecute(CommandSender sender, String[] args)
/*    */   {
/* 59 */     if (args.length <= 2) {
/* 60 */       sendPlayerMessage(sender, getLocalString("NUM_ARGS_ERR"));
/* 61 */       return;
/*    */     }
/*    */     
/*    */ 
/* 65 */     String ip_filter = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
/*    */     
/* 67 */     if (args[1].matches(ip_filter)) {
/* 68 */       sendPlayerMessage(sender, getLocalString("MODBAN_IP"));
/* 69 */       return;
/*    */     }
/* 71 */     String msg = new MessageParser(args, 2).parseMessage();
/*    */     
/* 73 */     UserObject upo = IPCheck.getInstance().getDatabaseController().getUserObject(args[1]);
/*    */     
/* 75 */     if (upo != null) {
/* 76 */       if (upo.getBannedStatus())
/*    */       {
/* 78 */         IPCheck.getInstance().getDatabaseController().banPlayer(args[1], msg);
/*    */         
/* 80 */         sendPlayerMessage(sender, getLocalString("MODBAN_SUC"));
/*    */       } else {
/* 82 */         sendPlayerMessage(sender, getLocalString("NO_MODIFY"));
/*    */       }
/*    */     } else {
/* 85 */       sendPlayerMessage(sender, getLocalString("NO_FIND"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\ban\CmdModBan.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */