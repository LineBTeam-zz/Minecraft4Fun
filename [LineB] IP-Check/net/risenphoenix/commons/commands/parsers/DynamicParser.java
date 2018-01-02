/*     */ package net.risenphoenix.commons.commands.parsers;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import net.risenphoenix.commons.commands.Command;
/*     */ import net.risenphoenix.commons.commands.CommandManager;
/*     */ import net.risenphoenix.commons.commands.ComparisonResult;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DynamicParser
/*     */   extends Parser
/*     */ {
/*     */   public DynamicParser(CommandManager mngr, Command cmd, String[] args)
/*     */   {
/*  40 */     super(mngr, cmd, args);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ComparisonResult parseCommand()
/*     */   {
/*  54 */     String[] COMMAND_ARGS = new String[this.cmd.getCallArgs().length];
/*  55 */     String[] INPUT_ARGS = new String[this.cmd.getCallArgs().length];
/*     */     
/*     */ 
/*  58 */     for (int i = 0; i < this.cmd.getCallArgs().length; i++) {
/*  59 */       COMMAND_ARGS[i] = this.cmd.getCallArgs()[i];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  66 */     for (int i = 0; i < this.cmd.getCallArgs().length; i++) {
/*  67 */       if (i >= this.input.length) {
/*  68 */         INPUT_ARGS[i] = "null";
/*     */       } else {
/*  70 */         INPUT_ARGS[i] = this.input[i];
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  75 */     for (int i = 0; i < COMMAND_ARGS.length; i++)
/*     */     {
/*  77 */       if (this.cmdManager.debugMode()) {
/*  78 */         System.out.println("Command Expected: " + COMMAND_ARGS[i]);
/*  79 */         System.out.println("Received: " + INPUT_ARGS[i]);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  86 */       if (!COMMAND_ARGS[i].equals("VAR_ARG_OPT"))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  94 */         if ((COMMAND_ARGS[i].equals("VAR_ARG")) && 
/*  95 */           (INPUT_ARGS[i].equals("null")))
/*  96 */           return ComparisonResult.ARG_ERR;
/*  97 */         if (!COMMAND_ARGS[i].equals("VAR_ARG"))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 104 */           if (!INPUT_ARGS[i].equalsIgnoreCase(COMMAND_ARGS[i])) {
/* 105 */             return ComparisonResult.BAD;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 112 */     return ComparisonResult.GOOD;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\commands\parsers\DynamicParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */