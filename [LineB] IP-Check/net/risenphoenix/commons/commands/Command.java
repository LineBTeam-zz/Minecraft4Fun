/*     */ package net.risenphoenix.commons.commands;
/*     */ 
/*     */ import java.util.logging.Level;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.conversations.ConversationContext;
/*     */ import org.bukkit.conversations.ConversationFactory;
/*     */ import org.bukkit.conversations.Prompt;
/*     */ import org.bukkit.conversations.ValidatingPrompt;
/*     */ import org.bukkit.permissions.Permission;
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
/*     */ public class Command
/*     */   extends ValidatingPrompt
/*     */ {
/*     */   private final Plugin plugin;
/*     */   private final CommandType type;
/*     */   private String name;
/*     */   private String syntax;
/*     */   private String help;
/*  64 */   private boolean isConsoleExecutable = true;
/*     */   
/*     */   private String[] callArgs;
/*  67 */   private int requiredArgs = 0;
/*     */   
/*  69 */   private Permission[] commandPerms = null;
/*     */   
/*     */   private LocalizationManager LM;
/*  72 */   private ConversationFactory conFactory = null;
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
/*     */   public Command(Plugin plugin, String[] callArgs, CommandType type)
/*     */   {
/*  93 */     this.plugin = plugin;
/*  94 */     this.callArgs = callArgs;
/*  95 */     this.type = type;
/*  96 */     this.LM = this.plugin.getLocalizationManager();
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
/*     */   @Deprecated
/*     */   public Command(Plugin plugin, String[] callArgs, int requiredArgs, CommandType type)
/*     */   {
/* 128 */     this.plugin = plugin;
/* 129 */     this.callArgs = callArgs;
/* 130 */     this.requiredArgs = requiredArgs;
/* 131 */     this.type = type;
/* 132 */     this.LM = this.plugin.getLocalizationManager();
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
/*     */   public final boolean onCall(CommandSender sender, String[] args)
/*     */   {
/* 159 */     if (!canExecute(sender)) return false;
/* 160 */     onExecute(sender, args);
/* 161 */     return true;
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
/*     */ 
/*     */ 
/*     */   public final void setConsoleExecutable(boolean consoleCanExecute)
/*     */   {
/* 177 */     this.isConsoleExecutable = consoleCanExecute;
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
/*     */   public void onExecute(CommandSender sender, String[] args)
/*     */   {
/* 201 */     throw new UnsupportedOperationException(getLocalString("NO_IMPLEMENT"));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getName()
/*     */   {
/* 210 */     return this.name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void setName(String cmdName)
/*     */   {
/* 221 */     this.name = cmdName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getSyntax()
/*     */   {
/* 230 */     return this.syntax;
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
/*     */ 
/*     */ 
/*     */   public final void setSyntax(String cmdSyntax)
/*     */   {
/* 246 */     this.syntax = cmdSyntax;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getHelp()
/*     */   {
/* 255 */     return this.help;
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
/*     */   public final void setHelp(String helpDesc)
/*     */   {
/* 268 */     this.help = helpDesc;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final Permission[] getPermissions()
/*     */   {
/* 278 */     return this.commandPerms;
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
/*     */   public final void setPermissions(Permission[] perms)
/*     */   {
/* 291 */     this.commandPerms = perms;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean canConsoleExecute()
/*     */   {
/* 301 */     return this.isConsoleExecutable;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String[] getCallArgs()
/*     */   {
/* 311 */     return this.callArgs;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public final int getArgumentsRequired()
/*     */   {
/* 322 */     return this.requiredArgs;
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
/*     */   public final boolean canExecute(CommandSender sender)
/*     */   {
/* 335 */     if ((this.commandPerms == null) || (sender.isOp())) { return true;
/*     */     }
/* 337 */     for (int i = 0; i < this.commandPerms.length; i++) {
/* 338 */       if (!sender.hasPermission(this.commandPerms[i])) { return false;
/*     */       }
/*     */     }
/* 341 */     return true;
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
/*     */   public final String getLocalString(String key)
/*     */   {
/* 354 */     return this.LM.getLocalString(key);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final Plugin getPlugin()
/*     */   {
/* 361 */     return this.plugin;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final CommandType getType()
/*     */   {
/* 368 */     return this.type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void sendPlayerMessage(CommandSender sender, String message)
/*     */   {
/* 376 */     sendPlayerMessage(sender, message, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void sendPlayerMessage(CommandSender sender, String message, boolean useName)
/*     */   {
/* 386 */     this.plugin.sendPlayerMessage(sender, message, useName);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void sendConsoleMessage(Level level, String message)
/*     */   {
/* 394 */     this.plugin.sendConsoleMessage(level, message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final void setConversationFactory(ConversationFactory factory)
/*     */   {
/* 402 */     this.conFactory = factory;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final ConversationFactory getConversationFactory()
/*     */   {
/* 410 */     return this.conFactory;
/*     */   }
/*     */   
/*     */ 
/*     */   public String getPromptText(ConversationContext context)
/*     */   {
/* 416 */     throw new UnsupportedOperationException(getLocalString("NO_IMPLEMENT"));
/*     */   }
/*     */   
/*     */ 
/*     */   public Prompt acceptValidatedInput(ConversationContext context, String in)
/*     */   {
/* 422 */     throw new UnsupportedOperationException(getLocalString("NO_IMPLEMENT"));
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean isInputValid(ConversationContext context, String in)
/*     */   {
/* 428 */     throw new UnsupportedOperationException(getLocalString("NO_IMPLEMENT"));
/*     */   }
/*     */ }


