/*     */ package net.risenphoenix.commons.commands;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.logging.Level;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.parsers.DynamicParser;
/*     */ import net.risenphoenix.commons.commands.parsers.Parser;
/*     */ import net.risenphoenix.commons.commands.parsers.StaticParser;
/*     */ import net.risenphoenix.commons.commands.parsers.VariableParser;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
/*     */ import net.risenphoenix.commons.stores.CommandStore;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandManager
/*     */ {
/*     */   private final Plugin plugin;
/*  46 */   private ArrayList<Command> commands = new ArrayList();
/*     */   
/*  48 */   private boolean debugMode = false;
/*     */   
/*     */   public CommandManager(Plugin plugin) {
/*  51 */     this.plugin = plugin;
/*     */   }
/*     */   
/*     */   public final void registerStore(CommandStore cmdStore) {
/*  55 */     this.commands = cmdStore.getCommands();
/*     */   }
/*     */   
/*     */   public final boolean registerCommand(Command cmd) {
/*  59 */     if (this.commands.add(cmd)) {
/*  60 */       return true;
/*     */     }
/*  62 */     this.plugin.sendConsoleMessage(Level.WARNING, this.plugin
/*  63 */       .getLocalizationManager()
/*  64 */       .getLocalString("CMD_REG_ERR") + cmd
/*  65 */       .getName());
/*     */     
/*  67 */     return false;
/*     */   }
/*     */   
/*     */   public final Command getCommand(String identifier)
/*     */   {
/*  72 */     for (Command cmd : this.commands) {
/*  73 */       if (cmd.getName().equalsIgnoreCase(identifier)) { return cmd;
/*     */       }
/*     */     }
/*  76 */     return null;
/*     */   }
/*     */   
/*     */   public final ArrayList<Command> getAllCommands() {
/*  80 */     return this.commands;
/*     */   }
/*     */   
/*     */   public final ParseResult parseCommand(String[] args) {
/*  84 */     for (Command cmd : this.commands) {
/*     */       Parser parser;
/*     */       Parser parser;
/*  87 */       if (cmd.getType() == CommandType.STATIC) {
/*  88 */         parser = new StaticParser(this, cmd, args); } else { Parser parser;
/*  89 */         if (cmd.getType() == CommandType.VARIABLE) {
/*  90 */           parser = new VariableParser(this, cmd, args); } else { Parser parser;
/*  91 */           if (cmd.getType() == CommandType.DYNAMIC) {
/*  92 */             parser = new DynamicParser(this, cmd, args);
/*     */           } else
/*  94 */             parser = new StaticParser(this, cmd, args);
/*     */         }
/*     */       }
/*  97 */       ComparisonResult result = parser.parseCommand();
/*     */       
/*  99 */       if (result.equals(ComparisonResult.GOOD))
/* 100 */         return new ParseResult(ResultType.SUCCESS, cmd);
/* 101 */       if (result.equals(ComparisonResult.ARG_ERR)) {
/* 102 */         return new ParseResult(ResultType.BAD_NUM_ARGS, cmd);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 109 */     return new ParseResult(ResultType.FAIL, null);
/*     */   }
/*     */   
/*     */   public Plugin getPlugin() {
/* 113 */     return this.plugin;
/*     */   }
/*     */   
/*     */   public void setDebugMode(boolean flag) {
/* 117 */     this.debugMode = flag;
/*     */   }
/*     */   
/*     */   public boolean debugMode() {
/* 121 */     return this.debugMode;
/*     */   }
/*     */ }


