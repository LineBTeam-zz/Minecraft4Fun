/*     */ package de.rob1n.prospam.cmd;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import de.rob1n.prospam.exception.IllegalCommandNameException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.TabCompleter;
/*     */ 
/*     */ public class CommandHandler
/*     */   implements TabCompleter
/*     */ {
/*     */   private final CommandList commandList;
/*     */   
/*     */   public CommandHandler(ProSpam plugin)
/*     */   {
/*  19 */     this.commandList = new CommandList(plugin);
/*     */   }
/*     */   
/*     */   public boolean execute(CommandSender sender, String[] args)
/*     */   {
/*  24 */     Command cmd = null;
/*     */     
/*     */     try
/*     */     {
/*  28 */       cmd = this.commandList.get(args[0]);
/*  29 */       cmd.execute(sender, args);
/*     */     }
/*     */     catch (IllegalCommandNameException e)
/*     */     {
/*  33 */       sender.sendMessage(ProSpam.error("Comando ilegal"));
/*  34 */       return callHelpCommand(sender);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  38 */       if (cmd != null) {
/*  39 */         sender.sendMessage(ProSpam.error("Argumento ilegal. Precisa: " + StringUtils.join(cmd.getArgs(), " ")));
/*     */       } else {
/*  41 */         return callHelpCommand(sender);
/*     */       }
/*     */     }
/*  44 */     return true;
/*     */   }
/*     */   
/*     */   private boolean callHelpCommand(CommandSender sender)
/*     */   {
/*     */     try
/*     */     {
/*  51 */       this.commandList.get("help").execute(sender, null);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  55 */       ProSpam.log(Level.SEVERE, e.getMessage());
/*     */       
/*  57 */       return false;
/*     */     }
/*     */     
/*  60 */     return true;
/*     */   }
/*     */   
/*     */   public CommandList getCommandList()
/*     */   {
/*  65 */     return this.commandList;
/*     */   }
/*     */   
/*     */ 
/*     */   public List<String> onTabComplete(CommandSender commandSender, org.bukkit.command.Command command, String cmdName, String[] strings)
/*     */   {
/*  71 */     List<String> completions = new ArrayList();
/*  72 */     String toComplete = strings[0];
/*     */     
/*  74 */     if (toComplete.isEmpty())
/*     */     {
/*  76 */       for (Command cmd : this.commandList)
/*     */       {
/*  78 */         completions.add(cmd.getName());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/*  83 */       if (strings.length < 2)
/*     */       {
/*     */ 
/*  86 */         for (Command cmd : this.commandList)
/*     */         {
/*  88 */           if (cmd.getName().startsWith(toComplete))
/*     */           {
/*  90 */             completions.add(cmd.getName());
/*     */           }
/*     */           
/*  93 */           for (String alias : cmd.getAliases())
/*     */           {
/*  95 */             if (alias.startsWith(toComplete))
/*     */             {
/*  97 */               completions.add(alias);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       
/*     */       try
/*     */       {
/* 105 */         if ((toComplete.isEmpty()) && (strings.length >= 2))
/*     */         {
/* 107 */           toComplete = strings[(strings.length - 2)];
/*     */         }
/*     */         
/* 110 */         Command lastCmd = this.commandList.get(toComplete);
/*     */         
/* 112 */         if (strings.length >= 2)
/*     */         {
/* 114 */           if (lastCmd.getArgs().length > strings.length - 2)
/*     */           {
/* 116 */             String cmdArg = lastCmd.getArgs()[(strings.length - 2)];
/*     */             
/* 118 */             if (!cmdArg.isEmpty())
/*     */             {
/*     */ 
/* 121 */               commandSender.sendMessage(ProSpam.prefixed("Argumentos aceitos: " + cmdArg));
/*     */               
/* 123 */               if (cmdArg.equalsIgnoreCase("<player>"))
/*     */               {
/*     */ 
/* 126 */                 return null;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (IllegalCommandNameException ignored) {}
/*     */     }
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
/* 158 */     return completions;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\CommandHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */