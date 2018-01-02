/*    */ package net.coreprotect.command;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.Statement;
/*    */ import net.coreprotect.database.Database;
/*    */ import net.coreprotect.model.Config;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class RenameCommand
/*    */   extends Config
/*    */ {
/*    */   protected static void runCommand(CommandSender player, boolean permission, String[] args)
/*    */   {
/* 24 */     int resultc = args.length;
/* 25 */     if (Config.converter_running == true) {
/* 26 */       player.sendMessage("§3CoreProtect §f- Upgrade in progress. Please try again later.");
/* 27 */       return;
/*    */     }
/* 29 */     if (Config.purge_running == true) {
/* 30 */       player.sendMessage("§3CoreProtect §f- Purge in progress. Please try again later.");
/* 31 */       return;
/*    */     }
/* 33 */     if (permission == true) {
/* 34 */       if ((player instanceof Player)) {
/* 35 */         player.sendMessage("§3CoreProtect §f- This command must be used via the console.");
/* 36 */         return;
/*    */       }
/* 38 */       if (resultc <= 2) {
/* 39 */         player.sendMessage("§3CoreProtect §f- Please use \"/co rename <args>\".");
/* 40 */         return;
/*    */       }
/* 42 */       String rename_command = args[1].toLowerCase();
/* 43 */       if (!rename_command.equals("world")) {
/* 44 */         player.sendMessage("§3CoreProtect §f- Please use \"/co rename <args>\".");
/* 45 */         return;
/*    */       }
/* 47 */       if (resultc > 3) {
/* 48 */         CommandSender final_player = player;
/* 49 */         final String old_world = args[2];
/* 50 */         final String new_world = args[3];
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/* 87 */         Runnable runnable = new Runnable()
/*    */         {
/*    */           public void run()
/*    */           {
/*    */             try
/*    */             {
/* 55 */               Connection connection = Database.getConnection(false);
/* 56 */               if (connection == null) {
/* 57 */                 this.val$final_player.sendMessage("§3CoreProtect §f- Database busy. Please try again later.");
/* 58 */                 return;
/*    */               }
/* 60 */               int wid = -1;
/* 61 */               PreparedStatement preparedStmt = connection.prepareStatement("SELECT rowid FROM " + Config.prefix + "world WHERE world LIKE ?");
/* 62 */               preparedStmt.setString(1, old_world);
/* 63 */               ResultSet rs = preparedStmt.executeQuery();
/* 64 */               while (rs.next()) {
/* 65 */                 wid = rs.getInt("rowid");
/*    */               }
/* 67 */               rs.close();
/* 68 */               if (wid == -1) {
/* 69 */                 this.val$final_player.sendMessage("§3CoreProtect §f- World \"" + old_world + "\" not found.");
/* 70 */                 connection.close();
/* 71 */                 return;
/*    */               }
/* 73 */               preparedStmt = connection.prepareStatement("UPDATE " + Config.prefix + "world SET world = ? WHERE rowid='" + wid + "'");
/* 74 */               preparedStmt.setString(1, new_world);
/* 75 */               preparedStmt.executeUpdate();
/* 76 */               Statement statement = connection.createStatement();
/*    */               
/* 78 */               statement.close();
/* 79 */               connection.close();
/* 80 */               this.val$final_player.sendMessage("§3CoreProtect §f- World \"" + old_world + "\" renamed to \"" + new_world + "\".");
/*    */             }
/*    */             catch (Exception e) {
/* 83 */               e.printStackTrace();
/*    */             }
/*    */             
/*    */           }
/* 87 */         };
/* 88 */         Thread thread = new Thread(runnable);
/* 89 */         thread.start();
/*    */       }
/*    */       else {
/* 92 */         player.sendMessage("§3CoreProtect §f- Please use \"/co rename world <old> <new>\".");
/*    */       }
/*    */     }
/*    */     else {
/* 96 */       player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\command\RenameCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */