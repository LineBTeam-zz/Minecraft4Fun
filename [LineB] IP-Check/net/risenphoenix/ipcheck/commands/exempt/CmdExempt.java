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
/*     */ 
/*     */ 
/*     */ public class CmdExempt
/*     */   extends Command
/*     */ {
/*     */   private DatabaseController db;
/*     */   private String argument;
/*  54 */   private String ip_filter = "\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}\\.\\d{1,3}";
/*     */   
/*     */   public CmdExempt(Plugin plugin, String[] callArgs, CommandType type) {
/*  57 */     super(plugin, callArgs, type);
/*  58 */     this.db = IPCheck.getInstance().getDatabaseController();
/*     */     
/*  60 */     setName(getLocalString("CMD_EXEMPT"));
/*  61 */     setHelp(getLocalString("HELP_EXEMPT"));
/*  62 */     setSyntax("ipc exempt <PLAYER | IP>");
/*  63 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.exempt") });
/*     */     
/*     */ 
/*     */ 
/*  67 */     setConversationFactory(new ConversationFactory(plugin));
/*     */   }
/*     */   
/*     */ 
/*     */   public void onExecute(CommandSender sender, String[] args)
/*     */   {
/*  73 */     this.argument = args[1];
/*     */     
/*     */ 
/*  76 */     if (this.argument.matches(this.ip_filter)) {
/*  77 */       if (this.db.isValidIP(this.argument))
/*     */       {
/*  79 */         getConversationFactory().withFirstPrompt(this);
/*  80 */         getConversationFactory()
/*  81 */           .buildConversation((Conversable)sender).begin();
/*     */       } else {
/*  83 */         sendPlayerMessage(sender, getLocalString("NO_FIND"));
/*     */       }
/*     */       
/*     */ 
/*     */     }
/*  88 */     else if (this.db.isValidPlayer(this.argument))
/*     */     {
/*  90 */       getConversationFactory().withFirstPrompt(this);
/*  91 */       getConversationFactory()
/*  92 */         .buildConversation((Conversable)sender).begin();
/*     */     } else {
/*  94 */       sendPlayerMessage(sender, getLocalString("NO_FIND"));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public String getPromptText(ConversationContext context)
/*     */   {
/* 101 */     context.getForWhom().sendRawMessage(getPlugin()
/* 102 */       .formatPlayerMessage(getLocalString("EXEMPT_PROMPT")));
/*     */     
/* 104 */     context.getForWhom().sendRawMessage(ChatColor.GOLD + "  (" + ChatColor.RED + "0" + ChatColor.GOLD + "). " + ChatColor.YELLOW + "Cancel");
/*     */     
/*     */ 
/*     */ 
/* 108 */     context.getForWhom().sendRawMessage(ChatColor.GOLD + "  (" + ChatColor.RED + "1" + ChatColor.GOLD + "). " + ChatColor.YELLOW + "Login-Check Exemption");
/*     */     
/*     */ 
/*     */ 
/* 112 */     return ChatColor.GOLD + "  (" + ChatColor.RED + "2" + ChatColor.GOLD + "). " + ChatColor.YELLOW + "Rejoin-Warn Exemption";
/*     */   }
/*     */   
/*     */ 
/*     */   public Prompt acceptValidatedInput(ConversationContext con, String s)
/*     */   {
/* 118 */     if ((s.equalsIgnoreCase("one")) || (s.equals("1"))) {
/* 119 */       createLoginExemption(con);
/* 120 */     } else if ((s.equalsIgnoreCase("two")) || (s.equals("2"))) {
/* 121 */       createRejoinExemption(con);
/* 122 */     } else if ((s.equalsIgnoreCase("zero")) || (s.equalsIgnoreCase("cancel")) || 
/* 123 */       (s.equals("0"))) {
/* 124 */       con.getForWhom().sendRawMessage(getPlugin().formatPlayerMessage(
/* 125 */         getLocalString("EXEMPT_PROMPT_CANCEL")));
/*     */     }
/*     */     
/* 128 */     return Prompt.END_OF_CONVERSATION;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isInputValid(ConversationContext context, String s)
/*     */   {
/* 135 */     return (s.equalsIgnoreCase("one")) || (s.equals("1")) || (s.equalsIgnoreCase("two")) || (s.equals("2")) || (s.equalsIgnoreCase("zero")) || (s.equals("0"));
/*     */   }
/*     */   
/*     */   private void createLoginExemption(ConversationContext context) {
/* 139 */     if (this.argument.matches(this.ip_filter))
/*     */     {
/* 141 */       IPObject ipo = this.db.getIPObject(this.argument);
/*     */       
/* 143 */       if (!ipo.getExemptStatus()) {
/* 144 */         this.db.exemptIP(this.argument);
/*     */       } else {
/* 146 */         context.getForWhom().sendRawMessage(getPlugin()
/* 147 */           .formatPlayerMessage(getLocalString("NO_MODIFY")));
/* 148 */         return;
/*     */       }
/*     */       
/* 151 */       context.getForWhom().sendRawMessage(getPlugin()
/* 152 */         .formatPlayerMessage(getLocalString("IP_EXEMPT_SUC")));
/*     */     }
/*     */     else {
/* 155 */       UserObject upo = this.db.getUserObject(this.argument);
/*     */       
/*     */ 
/* 158 */       if (!upo.getExemptStatus()) {
/* 159 */         this.db.exemptPlayer(this.argument);
/*     */       } else {
/* 161 */         context.getForWhom().sendRawMessage(getPlugin()
/* 162 */           .formatPlayerMessage(getLocalString("NO_MODIFY")));
/* 163 */         return;
/*     */       }
/*     */       
/* 166 */       context.getForWhom().sendRawMessage(getPlugin()
/* 167 */         .formatPlayerMessage(getLocalString("PLAYER_EXEMPT_SUC")));
/*     */     }
/*     */   }
/*     */   
/*     */   private void createRejoinExemption(ConversationContext context) {
/* 172 */     if (this.argument.matches(this.ip_filter))
/*     */     {
/* 174 */       IPObject ipo = this.db.getIPObject(this.argument);
/*     */       
/*     */ 
/* 177 */       if (!ipo.getRejoinExemptStatus()) {
/* 178 */         this.db.setRejoinExemptIP(this.argument, true);
/*     */       } else {
/* 180 */         context.getForWhom().sendRawMessage(getPlugin()
/* 181 */           .formatPlayerMessage(getLocalString("NO_MODIFY")));
/* 182 */         return;
/*     */       }
/*     */       
/* 185 */       context.getForWhom().sendRawMessage(getPlugin()
/* 186 */         .formatPlayerMessage(getLocalString("IP_EXEMPT_SUC")));
/*     */     }
/*     */     else {
/* 189 */       UserObject upo = this.db.getUserObject(this.argument);
/*     */       
/*     */ 
/* 192 */       if (!upo.getRejoinExemptStatus()) {
/* 193 */         this.db.setRejoinExemptPlayer(this.argument, true);
/*     */       } else {
/* 195 */         context.getForWhom().sendRawMessage(getPlugin()
/* 196 */           .formatPlayerMessage(getLocalString("NO_MODIFY")));
/* 197 */         return;
/*     */       }
/*     */       
/* 200 */       context.getForWhom().sendRawMessage(getPlugin()
/* 201 */         .formatPlayerMessage(getLocalString("PLAYER_EXEMPT_SUC")));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\exempt\CmdExempt.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */