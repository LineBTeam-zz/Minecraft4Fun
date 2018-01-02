/*    */ package net.coreprotect.command;
/*    */ 
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class InspectCommand
/*    */ {
/*    */   protected static void runCommand(CommandSender player, boolean permission, String[] args)
/*    */   {
/*  9 */     if (permission == true)
/*    */     {
/* 11 */       int command = -1;
/* 12 */       if (net.coreprotect.model.Config.inspecting.get(player.getName()) == null) {
/* 13 */         net.coreprotect.model.Config.inspecting.put(player.getName(), Boolean.valueOf(false));
/*    */       }
/*    */       
/* 16 */       if (args.length > 1) {
/* 17 */         String action = args[1];
/* 18 */         if (action.equalsIgnoreCase("on")) {
/* 19 */           command = 1;
/*    */         }
/* 21 */         else if (action.equalsIgnoreCase("off")) {
/* 22 */           command = 0;
/*    */         }
/*    */       }
/*    */       
/* 26 */       if (!((Boolean)net.coreprotect.model.Config.inspecting.get(player.getName())).booleanValue()) {
/* 27 */         if (command == 0) {
/* 28 */           player.sendMessage("§3CoreProtect §f- Inspector already disabled.");
/*    */         }
/*    */         else {
/* 31 */           player.sendMessage("§3CoreProtect §f- Inspector now enabled.");
/* 32 */           net.coreprotect.model.Config.inspecting.put(player.getName(), Boolean.valueOf(true));
/*    */         }
/*    */         
/*    */       }
/* 36 */       else if (command == 1) {
/* 37 */         player.sendMessage("§3CoreProtect §f- Inspector already enabled.");
/*    */       }
/*    */       else {
/* 40 */         player.sendMessage("§3CoreProtect §f- Inspector now disabled.");
/* 41 */         net.coreprotect.model.Config.inspecting.put(player.getName(), Boolean.valueOf(false));
/*    */       }
/*    */       
/*    */     }
/*    */     else
/*    */     {
/* 47 */       player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\command\InspectCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */