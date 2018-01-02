/*    */ package net.risenphoenix.commons.commands.parsers;
/*    */ 
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.commands.Command;
/*    */ import net.risenphoenix.commons.commands.CommandManager;
/*    */ import net.risenphoenix.commons.commands.ComparisonResult;
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
/*    */ public class Parser
/*    */ {
/*    */   public CommandManager cmdManager;
/*    */   public Command cmd;
/*    */   public String[] input;
/*    */   
/*    */   public Parser(CommandManager mngr, Command cmd, String[] input)
/*    */   {
/* 44 */     this.cmdManager = mngr;
/* 45 */     this.cmd = cmd;
/* 46 */     this.input = input;
/*    */   }
/*    */   
/*    */   public ComparisonResult parseCommand()
/*    */   {
/* 51 */     throw new UnsupportedOperationException(this.cmdManager.getPlugin().getLocalizationManager().getLocalString("BAD_PARSE_SET"));
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\commands\parsers\Parser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */