/*     */ package net.risenphoenix.ipcheck.commands.exempt;
/*     */ 
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.Command;
/*     */ import net.risenphoenix.commons.commands.CommandType;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*     */ import net.risenphoenix.ipcheck.objects.IPObject;
/*     */ import net.risenphoenix.ipcheck.objects.UserObject;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.conversations.Conversable;
/*     */ import org.bukkit.conversations.Conversation;
/*     */ import org.bukkit.conversations.ConversationContext;
/*     */ import org.bukkit.conversations.ConversationFactory;
/*     */ import org.bukkit.conversations.Prompt;
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
/*     */ public class CmdUnexempt
/*     */   extends Command
/*     */ {
/*     */   private DatabaseController db;
/*     */   private String argument;
/*     */   
/*     */   public CmdUnexempt(Plugin plugin, String[] callArgs, CommandType type)
/*     */   {
/*  55 */     super(plugin, callArgs, type);
/*  56 */     this.db = IPCheck.getInstance().getDatabaseController();
/*     */     
/*  58 */     setName(getLocalString("CMD_UNEXEMPT"));
/*  59 */     setHelp(getLocalString("HELP_UNEXEMPT"));
/*  60 */     setSyntax("ipc unexempt <PLAYER | IP>");
/*  61 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.unexempt") });
/*     */     
/*     */ 
/*     */ 
/*  65 */     setConversationFactory(new ConversationFactory(plugin));
/*     */   }
/*     */   
/*     */ 
/*     */   public void onExecute(CommandSender sender, String[] args)
/*     */   {
/*  71 */     String ip_filter = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
/*  72 */     this.argument = args[1];
/*     */     
/*     */ 
/*  75 */     if (this.argument.matches(ip_filter)) {
/*  76 */       if (this.db.isValidIP(this.argument))
/*     */       {
/*  78 */         IPObject ipo = this.db.getIPObject(this.argument);
/*     */         
/*     */ 
/*  81 */         if (ipo.getExemptStatus()) {
/*  82 */           this.db.unexemptIP(this.argument);
/*     */         } else {
/*  84 */           sendPlayerMessage(sender, getLocalString("NO_MODIFY"));
/*  85 */           return;
/*     */         }
/*     */         
/*  88 */         sendPlayerMessage(sender, getLocalString("EXEMPT_DEL_SUC"));
/*     */       } else {
/*  90 */         sendPlayerMessage(sender, getLocalString("NO_FIND"));
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */     }
/*  97 */     else if (this.db.isValidPlayer(this.argument))
/*     */     {
/*  99 */       getConversationFactory().withFirstPrompt(this);
/* 100 */       getConversationFactory()
/* 101 */         .buildConversation((Conversable)sender).begin();
/*     */     } else {
/* 103 */       sendPlayerMessage(sender, getLocalString("NO_FIND"));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String getPromptText(ConversationContext context)
/*     */   {
/* 110 */     context.getForWhom().sendRawMessage(getPlugin()
/* 111 */       .formatPlayerMessage(getLocalString("UNEXEMPT_PROMPT")));
/*     */     
/* 113 */     context.getForWhom().sendRawMessage(ChatColor.GOLD + "  (" + ChatColor.RED + "0" + ChatColor.GOLD + "). " + ChatColor.YELLOW + "Cancel");
/*     */     
/*     */ 
/*     */ 
/* 117 */     context.getForWhom().sendRawMessage(ChatColor.GOLD + "  (" + ChatColor.RED + "1" + ChatColor.GOLD + "). " + ChatColor.YELLOW + "Login-Check Exemption");
/*     */     
/*     */ 
/*     */ 
/* 121 */     return ChatColor.GOLD + "  (" + ChatColor.RED + "2" + ChatColor.GOLD + "). " + ChatColor.YELLOW + "Rejoin-Warn Exemption";
/*     */   }
/*     */   
/*     */ 
/*     */   public Prompt acceptValidatedInput(ConversationContext con, String s)
/*     */   {
/* 127 */     if ((s.equalsIgnoreCase("one")) || (s.equals("1"))) {
/* 128 */       removeLoginExemption(con);
/* 129 */     } else if ((s.equalsIgnoreCase("two")) || (s.equals("2"))) {
/* 130 */       removeRejoinExemption(con);
/* 131 */     } else if ((s.equalsIgnoreCase("zero")) || (s.equalsIgnoreCase("cancel")) || 
/* 132 */       (s.equals("0"))) {
/* 133 */       con.getForWhom().sendRawMessage(getPlugin().formatPlayerMessage(
/* 134 */         getLocalString("EXEMPT_PROMPT_CANCEL")));
/*     */     }
/*     */     
/* 137 */     return Prompt.END_OF_CONVERSATION;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isInputValid(ConversationContext context, String s)
/*     */   {
/* 144 */     return (s.equalsIgnoreCase("one")) || (s.equals("1")) || (s.equalsIgnoreCase("two")) || (s.equals("2")) || (s.equalsIgnoreCase("zero")) || (s.equals("0"));
/*     */   }
/*     */   
/*     */   private void removeLoginExemption(ConversationContext context) {
/* 148 */     UserObject upo = this.db.getUserObject(this.argument);
/*     */     
/*     */ 
/* 151 */     if (upo.getExemptStatus()) {
/* 152 */       this.db.unexemptPlayer(this.argument);
/*     */     } else {
/* 154 */       context.getForWhom().sendRawMessage(getPlugin()
/* 155 */         .formatPlayerMessage(getLocalString("NO_MODIFY")));
/* 156 */       return;
/*     */     }
/*     */     
/* 159 */     context.getForWhom().sendRawMessage(getPlugin()
/* 160 */       .formatPlayerMessage(getLocalString("EXEMPT_DEL_SUC")));
/*     */   }
/*     */   
/*     */   private void removeRejoinExemption(ConversationContext context) {
/* 164 */     UserObject upo = this.db.getUserObject(this.argument);
/*     */     
/*     */ 
/* 167 */     if (upo.getRejoinExemptStatus()) {
/* 168 */       this.db.setRejoinExemptPlayer(this.argument, false);
/*     */     } else {
/* 170 */       context.getForWhom().sendRawMessage(getPlugin()
/* 171 */         .formatPlayerMessage(getLocalString("NO_MODIFY")));
/* 172 */       return;
/*     */     }
/*     */     
/* 175 */     context.getForWhom().sendRawMessage(getPlugin()
/* 176 */       .formatPlayerMessage(getLocalString("EXEMPT_DEL_SUC")));
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\exempt\CmdUnexempt.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */