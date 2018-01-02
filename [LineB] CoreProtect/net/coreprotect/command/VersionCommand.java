/*    */ package net.coreprotect.command;
/*    */ 
/*    */ import java.util.Map;
/*    */ import net.coreprotect.CoreProtect;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.plugin.PluginDescriptionFile;
/*    */ 
/*    */ public class VersionCommand
/*    */ {
/*    */   protected static void runCommand(CommandSender player, boolean permission, String[] args)
/*    */   {
/* 12 */     PluginDescriptionFile pdfFile = CoreProtect.getInstance().getDescription();
/*    */     
/* 14 */     String versionCheck = "";
/* 15 */     if (((Integer)net.coreprotect.model.Config.config.get("check-updates")).intValue() == 1) {
/* 16 */       String latestVersion = net.coreprotect.thread.CheckUpdate.latestVersion();
/* 17 */       if (latestVersion != null) {
/* 18 */         versionCheck = " (Latest Version: v" + latestVersion + ")";
/*    */       }
/*    */     }
/*    */     
/* 22 */     player.sendMessage("§f----- §3CoreProtect §f-----");
/* 23 */     player.sendMessage("§3Version: §fCoreProtect v" + pdfFile.getVersion() + "." + versionCheck);
/*    */     
/* 25 */     if (((Integer)net.coreprotect.model.Config.config.get("use-mysql")).intValue() == 1) {
/* 26 */       player.sendMessage("§3Storage: §fUsing MySQL.");
/*    */     }
/*    */     else {
/* 29 */       player.sendMessage("§3Storage: §fUsing SQLite.");
/*    */     }
/* 31 */     player.sendMessage("§3Download: §fhttp://coreprotect.net/download/");
/* 32 */     player.sendMessage("§3Sponsor: §fhttp://hosthorde.com");
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\command\VersionCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */