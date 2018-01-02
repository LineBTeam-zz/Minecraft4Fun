/*    */ package net.risenphoenix.ipcheck.commands.protect;
/*    */ 
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.commands.Command;
/*    */ import net.risenphoenix.commons.commands.CommandType;
/*    */ import net.risenphoenix.ipcheck.IPCheck;
/*    */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*    */ import net.risenphoenix.ipcheck.objects.UserObject;
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
/*    */ public class CmdProtect
/*    */   extends Command
/*    */ {
/*    */   private DatabaseController db;
/*    */   
/*    */   public CmdProtect(Plugin plugin, String[] callArgs, CommandType type)
/*    */   {
/* 47 */     super(plugin, callArgs, type);
/*    */     
/* 49 */     this.db = IPCheck.getInstance().getDatabaseController();
/*    */     
/* 51 */     setName(getLocalString("CMD_PROTECT"));
/* 52 */     setHelp(getLocalString("HELP_PROTECT"));
/* 53 */     setSyntax("ipc protect <PLAYER>");
/* 54 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.protect") });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onExecute(CommandSender sender, String[] args)
/*    */   {
/* 62 */     String ip_filter = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
/*    */     
/* 64 */     if (args[1].matches(ip_filter)) {
/* 65 */       sendPlayerMessage(sender, getLocalString("PROTECT_IP_ERR"));
/* 66 */       return;
/*    */     }
/*    */     
/* 69 */     if (!this.db.isValidPlayer(args[1])) {
/* 70 */       sendPlayerMessage(sender, getLocalString("NO_FIND"));
/* 71 */       return;
/*    */     }
/*    */     
/* 74 */     UserObject upo = this.db.getUserObject(args[1]);
/*    */     
/* 76 */     if (!upo.getProtectedStatus()) {
/* 77 */       this.db.protectPlayer(args[1]);
/*    */     } else {
/* 79 */       sendPlayerMessage(sender, getLocalString("NO_MODIFY"));
/* 80 */       return;
/*    */     }
/*    */     
/* 83 */     sendPlayerMessage(sender, getLocalString("PROTECT_SUC"));
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\protect\CmdProtect.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */