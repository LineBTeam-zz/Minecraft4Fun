/*    */ package net.risenphoenix.ipcheck.commands;
/*    */ 
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.commands.Command;
/*    */ import net.risenphoenix.commons.commands.CommandType;
/*    */ import net.risenphoenix.ipcheck.IPCheck;
/*    */ import net.risenphoenix.ipcheck.database.DatabaseController;
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
/*    */ public class CmdPurge
/*    */   extends Command
/*    */ {
/*    */   private DatabaseController db;
/*    */   
/*    */   public CmdPurge(Plugin plugin, String[] callArgs, CommandType type)
/*    */   {
/* 46 */     super(plugin, callArgs, type);
/*    */     
/* 48 */     this.db = IPCheck.getInstance().getDatabaseController();
/*    */     
/* 50 */     setName(getLocalString("CMD_PURGE"));
/* 51 */     setHelp(getLocalString("HELP_PURGE"));
/* 52 */     setSyntax("ipc purge <PLAYER | IP>");
/* 53 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.purge") });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onExecute(CommandSender sender, String[] args)
/*    */   {
/* 62 */     String ip_filter = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
/*    */     
/*    */ 
/* 65 */     if (args[1].matches(ip_filter)) {
/* 66 */       if (this.db.isValidIP(args[1]))
/*    */       {
/* 68 */         this.db.purgeIP(args[1]);
/* 69 */         sendPlayerMessage(sender, String.format(
/* 70 */           getLocalString("PURGE_SUC"), new Object[] { args[1] }));
/*    */       }
/*    */       else {
/* 73 */         sendPlayerMessage(sender, getLocalString("NO_FIND"));
/*    */       }
/*    */       
/*    */ 
/*    */     }
/* 78 */     else if (this.db.isValidPlayer(args[1]))
/*    */     {
/* 80 */       this.db.purgePlayer(args[1]);
/* 81 */       sendPlayerMessage(sender, String.format(
/* 82 */         getLocalString("PURGE_SUC"), new Object[] { args[1] }));
/*    */     }
/*    */     else {
/* 85 */       sendPlayerMessage(sender, getLocalString("NO_FIND"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\CmdPurge.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */