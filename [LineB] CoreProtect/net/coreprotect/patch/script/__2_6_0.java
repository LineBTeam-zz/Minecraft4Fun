/*    */ package net.coreprotect.patch.script;
/*    */ 
/*    */ import java.sql.Statement;
/*    */ 
/*    */ public class __2_6_0
/*    */ {
/*    */   protected static boolean patch(Statement statement)
/*    */   {
/*    */     try
/*    */     {
/* 11 */       if (((Integer)net.coreprotect.model.Config.config.get("use-mysql")).intValue() == 1) {
/* 12 */         statement.executeUpdate("START TRANSACTION");
/* 13 */         statement.executeUpdate("CREATE TEMPORARY TABLE " + net.coreprotect.model.Config.prefix + "version_tmp(rowid int(8), time int(10), version varchar(16)) ENGINE=InnoDB");
/* 14 */         statement.executeUpdate("INSERT INTO " + net.coreprotect.model.Config.prefix + "version_tmp SELECT rowid,time,version FROM " + net.coreprotect.model.Config.prefix + "version;");
/* 15 */         statement.executeUpdate("DROP TABLE " + net.coreprotect.model.Config.prefix + "version;");
/* 16 */         statement.executeUpdate("CREATE TABLE " + net.coreprotect.model.Config.prefix + "version(rowid int(8) NOT NULL AUTO_INCREMENT,PRIMARY KEY(rowid),time int(10),version varchar(16)) ENGINE=InnoDB");
/* 17 */         statement.executeUpdate("INSERT INTO " + net.coreprotect.model.Config.prefix + "version SELECT rowid,time,version FROM " + net.coreprotect.model.Config.prefix + "version_tmp;");
/* 18 */         statement.executeUpdate("DROP TEMPORARY TABLE " + net.coreprotect.model.Config.prefix + "version_tmp;");
/* 19 */         statement.executeUpdate("COMMIT");
/*    */       }
/*    */       else {
/* 22 */         statement.executeUpdate("BEGIN TRANSACTION");
/* 23 */         statement.executeUpdate("CREATE TEMPORARY TABLE " + net.coreprotect.model.Config.prefix + "version_tmp (time INTEGER, version TEXT);");
/* 24 */         statement.executeUpdate("INSERT INTO " + net.coreprotect.model.Config.prefix + "version_tmp SELECT time,version FROM " + net.coreprotect.model.Config.prefix + "version;");
/* 25 */         statement.executeUpdate("DROP TABLE " + net.coreprotect.model.Config.prefix + "version;");
/* 26 */         statement.executeUpdate("CREATE TABLE " + net.coreprotect.model.Config.prefix + "version (time INTEGER, version TEXT);");
/* 27 */         statement.executeUpdate("INSERT INTO " + net.coreprotect.model.Config.prefix + "version SELECT time,version FROM " + net.coreprotect.model.Config.prefix + "version_tmp;");
/* 28 */         statement.executeUpdate("DROP TABLE " + net.coreprotect.model.Config.prefix + "version_tmp;");
/* 29 */         statement.executeUpdate("COMMIT TRANSACTION");
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 33 */       e.printStackTrace();
/*    */     }
/*    */     
/* 36 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\patch\script\__2_6_0.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */