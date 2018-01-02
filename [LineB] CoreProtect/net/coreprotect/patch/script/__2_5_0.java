/*    */ package net.coreprotect.patch.script;
/*    */ 
/*    */ import java.sql.Statement;
/*    */ import net.coreprotect.model.Config;
/*    */ 
/*    */ public class __2_5_0
/*    */ {
/*    */   protected static boolean patch(Statement statement)
/*    */   {
/*    */     try
/*    */     {
/* 12 */       if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/*    */         try {
/* 14 */           statement.executeUpdate("ALTER TABLE " + Config.prefix + "sign MODIFY line_1 VARCHAR(100)");
/* 15 */           statement.executeUpdate("ALTER TABLE " + Config.prefix + "sign MODIFY line_2 VARCHAR(100)");
/* 16 */           statement.executeUpdate("ALTER TABLE " + Config.prefix + "sign MODIFY line_3 VARCHAR(100)");
/* 17 */           statement.executeUpdate("ALTER TABLE " + Config.prefix + "sign MODIFY line_4 VARCHAR(100)");
/* 18 */           statement.executeUpdate("ALTER TABLE " + Config.prefix + "user MODIFY user VARCHAR(32)");
/*    */         }
/*    */         catch (Exception e) {
/* 21 */           e.printStackTrace();
/*    */         }
/*    */         
/* 24 */         if (!net.coreprotect.patch.Patch.continuePatch()) {
/* 25 */           return false;
/*    */         }
/*    */       }
/*    */       
/* 29 */       statement.executeUpdate("ALTER TABLE " + Config.prefix + "block ADD COLUMN meta BLOB");
/*    */     }
/*    */     catch (Exception e) {
/* 32 */       e.printStackTrace();
/*    */     }
/*    */     
/* 35 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\patch\script\__2_5_0.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */