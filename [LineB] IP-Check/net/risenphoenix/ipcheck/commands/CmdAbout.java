/*    */ package net.risenphoenix.ipcheck.commands;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.commands.Command;
/*    */ import net.risenphoenix.commons.commands.CommandType;
/*    */ import net.risenphoenix.ipcheck.IPCheck;
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
/*    */ 
/*    */ public class CmdAbout
/*    */   extends Command
/*    */ {
/*    */   public CmdAbout(Plugin plugin, String[] callArgs, CommandType type)
/*    */   {
/* 45 */     super(plugin, callArgs, type);
/*    */     
/* 47 */     setName(getLocalString("CMD_ABOUT"));
/* 48 */     setHelp(getLocalString("HELP_ABOUT"));
/* 49 */     setSyntax("ipc about");
/* 50 */     setPermissions(new Permission[] { new Permission("ipcheck.use") });
/*    */   }
/*    */   
/*    */   public void onExecute(CommandSender sender, String[] args)
/*    */   {
/* 55 */     Map<String, String> info = getPlugin().getVersionInfo();
/* 56 */     String output = String.format(getLocalString("ABOUT_TEXT"), new Object[] {
/* 57 */       IPCheck.getInstance().getVersion(), Integer.valueOf(IPCheck.getInstance()
/* 58 */       .getBuildNumber()), info.get("NAME"), info.get("VERSION"), info
/* 59 */       .get("BUILD"), info.get("AUTHOR") });
/*    */     
/* 61 */     sendPlayerMessage(sender, output);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\CmdAbout.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */