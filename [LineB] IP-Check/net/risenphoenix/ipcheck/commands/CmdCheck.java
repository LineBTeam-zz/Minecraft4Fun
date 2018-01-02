/*    */ package net.risenphoenix.ipcheck.commands;
/*    */ 
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.commands.Command;
/*    */ import net.risenphoenix.commons.commands.CommandType;
/*    */ import net.risenphoenix.ipcheck.IPCheck;
/*    */ import net.risenphoenix.ipcheck.objects.ReportObject;
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
/*    */ public class CmdCheck
/*    */   extends Command
/*    */ {
/*    */   public CmdCheck(Plugin plugin, String[] callArgs, CommandType type)
/*    */   {
/* 44 */     super(plugin, callArgs, type);
/*    */     
/* 46 */     setName("Check");
/* 47 */     setHelp(getLocalString("HELP_CHECK"));
/* 48 */     setSyntax("ipc <PLAYER | IP>");
/* 49 */     setPermissions(new Permission[] { new Permission("ipcheck.use") });
/*    */   }
/*    */   
/*    */   public void onExecute(CommandSender sender, String[] args)
/*    */   {
/* 54 */     new ReportObject(IPCheck.getInstance()).onExecute(sender, args[0]);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\CmdCheck.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */