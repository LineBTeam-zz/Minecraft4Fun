/*    */ package net.risenphoenix.ipcheck.commands;
/*    */ 
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.commands.Command;
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
/*    */ public class CmdReload
/*    */   extends Command
/*    */ {
/*    */   public CmdReload(Plugin plugin, String[] callArgs, CommandType type)
/*    */   {
/* 42 */     super(plugin, callArgs, type);
/*    */     
/* 44 */     setName(getLocalString("CMD_RELOAD"));
/* 45 */     setHelp(getLocalString("HELP_RELOAD"));
/* 46 */     setSyntax("ipc reload");
/* 47 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.reload") });
/*    */   }
/*    */   
/*    */ 
/*    */   public void onExecute(CommandSender sender, String[] args)
/*    */   {
/* 53 */     getPlugin().onDisable();
/* 54 */     getPlugin().onEnable();
/* 55 */     sendPlayerMessage(sender, getLocalString("RELOAD"));
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\CmdReload.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */