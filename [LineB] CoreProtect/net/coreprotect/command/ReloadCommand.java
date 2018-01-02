/*    */ package net.coreprotect.command;
/*    */ 
/*    */ import net.coreprotect.model.Config;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class ReloadCommand
/*    */ {
/*    */   protected static void runCommand(CommandSender player, boolean permission, String[] args)
/*    */   {
/* 10 */     if (permission == true) {
/* 11 */       if (Config.converter_running == true) {
/* 12 */         player.sendMessage("§3CoreProtect §f- Upgrade in progress. Please try again later.");
/* 13 */         return;
/*    */       }
/* 15 */       if (Config.purge_running == true) {
/* 16 */         player.sendMessage("§3CoreProtect §f- Purge in progress. Please try again later.");
/* 17 */         return;
/*    */       }
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
/* 36 */       Runnable runnable = new Runnable()
/*    */       {
/*    */         public void run()
/*    */         {
/*    */           try
/*    */           {
/* 23 */             Config.performInitialization();
/* 24 */             if (((Integer)Config.config.get("check-updates")).intValue() == 1) {
/* 25 */               Thread checkUpdateThread = new Thread(new net.coreprotect.thread.CheckUpdate(false));
/* 26 */               checkUpdateThread.start();
/*    */             }
/*    */             
/* 29 */             this.val$player.sendMessage("§3CoreProtect §f- Configuration reloaded.");
/*    */           }
/*    */           catch (Exception e) {
/* 32 */             e.printStackTrace();
/*    */           }
/*    */           
/*    */         }
/* 36 */       };
/* 37 */       Thread thread = new Thread(runnable);
/* 38 */       thread.start();
/*    */     }
/*    */     else {
/* 41 */       player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\command\ReloadCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */