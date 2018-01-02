/*    */ package net.risenphoenix.commons.commands;
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
/*    */ public class ParseResult
/*    */ {
/*    */   private final Command cmd;
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
/*    */   private final ResultType type;
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
/*    */   public ParseResult(ResultType type, Command cmd)
/*    */   {
/* 39 */     this.type = type;
/* 40 */     this.cmd = cmd;
/*    */   }
/*    */   
/*    */   public final ResultType getResult() {
/* 44 */     return this.type;
/*    */   }
/*    */   
/*    */   public final Command getCommand() {
/* 48 */     return this.cmd;
/*    */   }
/*    */   
/*    */   public final CommandType getCommandType() {
/* 52 */     return this.cmd.getType();
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\commands\ParseResult.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */