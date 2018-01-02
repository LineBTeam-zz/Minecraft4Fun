/*    */ package net.coreprotect.command;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CancelCommand
/*    */ {
/*    */   protected static void runCommand(CommandSender user, boolean permission, String[] args)
/*    */   {
/*    */     try
/*    */     {
/* 12 */       if (net.coreprotect.model.Config.last_rollback.get(user.getName()) != null) {
/* 13 */         java.util.List<Object[]> list = (java.util.List)net.coreprotect.model.Config.last_rollback.get(user.getName());
/* 14 */         int time = ((Integer)((Object[])list.get(0))[0]).intValue();
/* 15 */         args = (String[])list.get(1);
/* 16 */         boolean valid = false;
/* 17 */         for (int i = 0; i < args.length; i++) {
/* 18 */           if (args[i].equals("#preview")) {
/* 19 */             valid = true;
/* 20 */             args[i] = args[i].replaceAll("#preview", "#preview_cancel");
/*    */           }
/*    */         }
/* 23 */         if (!valid) {
/* 24 */           user.sendMessage("§3CoreProtect §f- No pending rollback/restore found.");
/*    */         }
/*    */         else {
/* 27 */           net.coreprotect.model.Config.last_rollback.remove(user.getName());
/* 28 */           RollbackRestoreCommand.runCommand(user, permission, args, time);
/*    */         }
/*    */       }
/*    */       else {
/* 32 */         user.sendMessage("§3CoreProtect §f- No pending rollback/restore found.");
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 36 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\command\CancelCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */