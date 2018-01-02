/*    */ package net.risenphoenix.commons.database;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.util.logging.Level;
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.localization.LocalizationManager;
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
/*    */ public class StatementObject
/*    */ {
/*    */   private final Plugin plugin;
/*    */   private String SQL;
/* 43 */   private Object[] values = null;
/*    */   
/*    */   public StatementObject(Plugin plugin, String SQL) {
/* 46 */     this.plugin = plugin;
/* 47 */     this.SQL = SQL;
/*    */   }
/*    */   
/*    */   public StatementObject(Plugin plugin, String SQL, Object[] values) {
/* 51 */     this.plugin = plugin;
/* 52 */     this.SQL = SQL;
/* 53 */     this.values = values;
/*    */   }
/*    */   
/*    */   public PreparedStatement getStatement(Connection c) {
/*    */     try {
/* 58 */       PreparedStatement prepStmt = c.prepareStatement(this.SQL);
/*    */       
/* 60 */       if (this.values != null) {
/* 61 */         for (int i = 1; i <= this.values.length; i++)
/*    */         {
/* 63 */           if ((this.values[(i - 1)] instanceof Integer)) {
/* 64 */             prepStmt.setInt(i, ((Integer)this.values[(i - 1)]).intValue());
/*    */ 
/*    */ 
/*    */           }
/* 68 */           else if ((this.values[(i - 1)] instanceof String)) {
/* 69 */             prepStmt.setString(i, this.values[(i - 1)].toString());
/*    */           }
/*    */           else
/*    */           {
/* 73 */             this.plugin.sendConsoleMessage(Level.SEVERE, this.plugin
/* 74 */               .getLocalizationManager()
/* 75 */               .getLocalString("BAD_SQL_INPUT"));
/*    */           }
/*    */         }
/*    */       }
/* 79 */       return prepStmt;
/*    */     } catch (Exception e) {
/* 81 */       this.plugin.sendConsoleMessage(Level.SEVERE, this.plugin
/* 82 */         .getLocalizationManager()
/* 83 */         .getLocalString("DB_PREP_STMT_ERR"));
/* 84 */       e.printStackTrace();
/*    */     }
/*    */     
/* 87 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\database\StatementObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */