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
/*     */ public class VariableParser
/*     */   extends Parser
/*     */ {
/*     */   public VariableParser(CommandManager mngr, Command cmd, String[] args)
/*     */   {
/*  40 */     super(mngr, cmd, args);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ComparisonResult parseCommand()
/*     */   {
/*  50 */     int callSize = this.cmd.getCallArgs().length;
/*  51 */     int inputSize = this.input.length;
/*     */     
/*     */     String[] INPUT_ARGS;
/*     */     
/*     */     String[] COMMAND_ARGS;
/*     */     
/*     */     String[] INPUT_ARGS;
/*     */     
/*  59 */     if (callSize > inputSize) {
/*  60 */       String[] COMMAND_ARGS = new String[callSize];
/*  61 */       INPUT_ARGS = new String[callSize];
/*     */     } else {
/*  63 */       COMMAND_ARGS = new String[inputSize];
/*  64 */       INPUT_ARGS = new String[inputSize];
/*     */     }
/*     */     
/*     */ 
/*  68 */     for (int i = 0; i < this.cmd.getCallArgs().length; i++) {
/*  69 */       COMMAND_ARGS[i] = this.cmd.getCallArgs()[i];
/*     */     }
/*  71 */     for (int i = 0; i < this.input.length; i++) {
/*  72 */       INPUT_ARGS[i] = this.input[i];
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  77 */     if (callSize > inputSize) {
/*  78 */       for (int i = inputSize; i < INPUT_ARGS.length; i++) {
/*  79 */         INPUT_ARGS[i] = "null";
/*     */       }
/*     */     } else {
/*  82 */       for (int i = callSize; i < COMMAND_ARGS.length; i++) {
/*  83 */         COMMAND_ARGS[i] = "null";
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*  88 */     for (int i = 0; i < COMMAND_ARGS.length; i++)
/*     */     {
/*  90 */       if (this.cmdManager.debugMode()) {
/*  91 */         System.out.println("Command Expected: " + COMMAND_ARGS[i]);
/*  92 */         System.out.println("Received: " + INPUT_ARGS[i]);
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  99 */       if (!COMMAND_ARGS[i].equals("VAR_ARG_OPT"))
/*     */       {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 107 */         if ((COMMAND_ARGS[i].equals("VAR_ARG")) && 
/* 108 */           (INPUT_ARGS[i].equals("null")))
/* 109 */           return ComparisonResult.ARG_ERR;
/* 110 */         if (!COMMAND_ARGS[i].equals("VAR_ARG"))
/*     */         {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 117 */           if (COMMAND_ARGS[i].equals("null")) {
/* 118 */             return ComparisonResult.ARG_ERR;
/*     */           }
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 124 */           if (!INPUT_ARGS[i].equalsIgnoreCase(COMMAND_ARGS[i])) {
/* 125 */             return ComparisonResult.BAD;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 132 */     return ComparisonResult.GOOD;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\commands\parsers\VariableParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */