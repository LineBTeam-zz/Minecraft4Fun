/*    */ package net.coreprotect.patch.script;
/*    */ 
/*    */ import java.sql.Statement;
/*    */ 
/*    */ public class __2_10_0
/*    */ {
/*    */   protected static boolean patch(Statement statement)
/*    */   {
/*    */     try
/*    */     {
/* 11 */       if (((Integer)net.coreprotect.model.Config.config.get("use-mysql")).intValue() == 1) {
/* 12 */         statement.executeUpdate("ALTER TABLE " + net.coreprotect.model.Config.prefix + "user ADD COLUMN uuid varchar(64), ADD INDEX(uuid)");
/*    */       }
/*    */       else {
/* 15 */         statement.executeUpdate("ALTER TABLE " + net.coreprotect.model.Config.prefix + "user ADD COLUMN uuid TEXT;");
/* 16 */         statement.executeUpdate("CREATE INDEX IF NOT EXISTS uuid_index ON " + net.coreprotect.model.Config.prefix + "user(uuid);");
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 20 */       e.printStackTrace();
/*    */     }
/*    */     
/* 23 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\patch\script\__2_10_0.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */