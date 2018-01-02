/*    */ package net.coreprotect.command;
/*    */ 
/*    */ import java.util.Map;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class UndoCommand
/*    */ {
/*    */   protected static void runCommand(CommandSender user, boolean permission, String[] args)
/*    */   {
/*    */     try
/*    */     {
/* 12 */       if (net.coreprotect.model.Config.last_rollback.get(user.getName()) != null) {
/* 13 */         java.util.List<Object[]> list = (java.util.List)net.coreprotect.model.Config.last_rollback.get(user.getName());
/* 14 */         int time = ((Integer)((Object[])list.get(0))[0]).intValue();
/* 15 */         args = (String[])list.get(1);
/* 16 */         for (String arg : args) {
/* 17 */           if (arg.equals("#preview")) {
/* 18 */             CancelCommand.runCommand(user, permission, args);
/* 19 */             return;
/*    */           }
/*    */         }
/* 22 */         boolean valid = true;
/* 23 */         if ((args[0].equals("rollback")) || (args[0].equals("rb")) || (args[0].equals("ro"))) {
/* 24 */           args[0] = "restore";
/*    */         }
/* 26 */         else if ((args[0].equals("restore")) || (args[0].equals("rs")) || (args[0].equals("re"))) {
/* 27 */           args[0] = "rollback";
/*    */         }
/*    */         else {
/* 30 */           valid = false;
/*    */         }
/* 32 */         if (valid == true) {
/* 33 */           net.coreprotect.model.Config.last_rollback.remove(user.getName());
/* 34 */           RollbackRestoreCommand.runCommand(user, permission, args, time);
/*    */         }
/*    */       }
/*    */       else {
/* 38 */         user.sendMessage("§3CoreProtect §f- No previous rollback/restore found.");
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 42 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\command\UndoCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */