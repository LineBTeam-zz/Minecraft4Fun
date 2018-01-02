/*    */ package net.risenphoenix.ipcheck.commands.block;
/*    */ 
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.commands.Command;
/*    */ import net.risenphoenix.commons.commands.CommandType;
/*    */ import net.risenphoenix.commons.configuration.ConfigurationManager;
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
/*    */ public class CmdBlock
/*    */   extends Command
/*    */ {
/*    */   private BlockManager cBlockManager;
/*    */   private ConfigurationManager CM;
/*    */   
/*    */   public CmdBlock(Plugin plugin, String[] callArgs, CommandType type)
/*    */   {
/* 47 */     super(plugin, callArgs, type);
/*    */     
/* 49 */     IPCheck ipc = IPCheck.getInstance();
/* 50 */     this.cBlockManager = ipc.getBlockManager();
/* 51 */     this.CM = ipc.getConfigurationManager();
/*    */     
/* 53 */     setName(getLocalString("CMD_BLOCK"));
/* 54 */     setHelp(getLocalString("HELP_BLOCK"));
/* 55 */     setSyntax("ipc block <COUNTRY_ID | help>");
/* 56 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.block") });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public void onExecute(CommandSender sender, String[] args)
/*    */   {
/* 64 */     if (this.cBlockManager != null)
/*    */     {
/*    */ 
/* 67 */       if (!this.cBlockManager.getStatus()) {
/* 68 */         sendPlayerMessage(sender, getLocalString("GEOIP_DISABLED"));
/* 69 */         return;
/*    */       }
/*    */       
/*    */ 
/* 73 */       if (args[1].equalsIgnoreCase("help")) {
/* 74 */         sendPlayerMessage(sender, getLocalString("BLOCK_HELP"));
/* 75 */         return;
/*    */       }
/*    */       
/* 78 */       boolean result = this.cBlockManager.blockCountry(args[1]);
/*    */       
/* 80 */       String msg = result ? getLocalString("BLOCK_SUC") : getLocalString("BLOCK_ERR");
/*    */       
/*    */ 
/* 83 */       String addon = "";
/* 84 */       if (result)
/*    */       {
/* 86 */         addon = " " + getLocalString("BLACK_LIST_OFF");
/*    */       }
/*    */       
/*    */ 
/* 90 */       sendPlayerMessage(sender, msg + addon);
/*    */     }
/*    */     else {
/* 93 */       sendPlayerMessage(sender, getLocalString("BLOCK_CMD_DISABLE"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\block\CmdBlock.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */