/*     */ package net.risenphoenix.ipcheck.commands;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.Command;
/*     */ import net.risenphoenix.commons.commands.CommandManager;
/*     */ import net.risenphoenix.commons.commands.CommandType;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
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
/*     */ public class CmdHelp
/*     */   extends Command
/*     */ {
/*     */   public CmdHelp(Plugin plugin, String[] callArgs, CommandType type)
/*     */   {
/*  48 */     super(plugin, callArgs, type);
/*     */     
/*  50 */     setName(getLocalString("CMD_HELP"));
/*  51 */     setHelp(getLocalString("HELP_HELP"));
/*  52 */     setSyntax("ipc help [PAGE]");
/*  53 */     setPermissions(new Permission[] { new Permission("ipcheck.use") });
/*     */   }
/*     */   
/*     */ 
/*     */   public void onExecute(CommandSender sender, String[] args)
/*     */   {
/*  59 */     ArrayList<Command> list = new ArrayList();
/*     */     
/*     */ 
/*     */ 
/*  63 */     for (Command cmd : getPlugin().getCommandManager().getAllCommands()) {
/*  64 */       if (cmd.canExecute(sender)) { list.add(cmd);
/*     */       }
/*     */     }
/*     */     
/*  68 */     Object cmdSort = new ArrayList(list);
/*  69 */     Collections.sort((List)cmdSort, new Comparator()
/*     */     {
/*     */       public int compare(Command o1, Command o2) {
/*  72 */         return o1.getName().compareTo(o2.getName());
/*     */       }
/*     */       
/*     */ 
/*  76 */     });
/*  77 */     list = new ArrayList((Collection)cmdSort);
/*     */     
/*     */ 
/*  80 */     int pages = list.size() / 4;
/*  81 */     if (list.size() % 4 != 0) { pages++;
/*     */     }
/*     */     
/*  84 */     int pageNumber = 1;
/*     */     
/*     */ 
/*  87 */     sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     
/*     */ 
/*     */ 
/*  91 */     if (args.length == 2) {
/*     */       try {
/*  93 */         pageNumber = Integer.parseInt(args[1]);
/*     */       } catch (NumberFormatException e) {
/*  95 */         sendPlayerMessage(sender, getLocalString("ILL_ARGS_ERR"));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 100 */     if (pageNumber > pages) {
/* 101 */       pageNumber = pages;
/* 102 */     } else if (pageNumber < 1) {
/* 103 */       pageNumber = 1;
/*     */     }
/*     */     
/*     */ 
/* 107 */     int commandNumber = 0;
/*     */     
/*     */ 
/* 110 */     for (Command cmd : list) {
/* 111 */       if ((commandNumber >= (pageNumber - 1) * 4) && (commandNumber < (pageNumber - 1) * 4 + 4))
/*     */       {
/* 113 */         sendPlayerMessage(sender, ChatColor.GREEN + cmd.getName() + ":", false);
/*     */         
/* 115 */         sendPlayerMessage(sender, ChatColor.YELLOW + " " + cmd
/* 116 */           .getHelp(), false);
/* 117 */         sendPlayerMessage(sender, ChatColor.RED + "    " + "Syntax:" + ChatColor.LIGHT_PURPLE + " /" + cmd
/* 118 */           .getSyntax(), false);
/*     */         
/*     */ 
/* 121 */         if (commandNumber < (pageNumber - 1) * 4 + 4 - 1) {
/* 122 */           sendPlayerMessage(sender, "", false);
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 127 */       commandNumber++;
/*     */     }
/*     */     
/*     */ 
/* 131 */     if (pageNumber < pages) {
/* 132 */       sendPlayerMessage(sender, " ", false);
/* 133 */       sendPlayerMessage(sender, ChatColor.RED + "Type " + ChatColor.YELLOW + "/ipc help " + (pageNumber + 1) + ChatColor.RED + " for more information.", false);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 138 */     sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     
/*     */ 
/*     */ 
/* 142 */     list.clear();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\CmdHelp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */