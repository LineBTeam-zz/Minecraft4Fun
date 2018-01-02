/*     */ package net.risenphoenix.ipcheck.commands.exempt.list;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.Command;
/*     */ import net.risenphoenix.commons.commands.CommandType;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*     */ import net.risenphoenix.ipcheck.util.ListFormatter;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CmdExemptListAll
/*     */   extends Command
/*     */ {
/*     */   public CmdExemptListAll(Plugin plugin, String[] callArgs, CommandType type)
/*     */   {
/*  49 */     super(plugin, callArgs, type);
/*     */     
/*  51 */     setName(getLocalString("CMD_EXEMPT_LIST"));
/*  52 */     setHelp(getLocalString("HELP_EXEMPT_LIST"));
/*  53 */     setSyntax("ipc exempt-list");
/*  54 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.list"), new Permission("ipcheck.showip") });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onExecute(CommandSender sender, String[] args)
/*     */   {
/*  63 */     executeList(sender, args, ListType.ALL);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void executeList(CommandSender sender, String[] args, ListType type)
/*     */   {
/*  71 */     DatabaseController db = IPCheck.getInstance().getDatabaseController();
/*     */     
/*     */ 
/*  74 */     ArrayList<String> ipExempt = db.getIPExemptList();
/*  75 */     ArrayList<String> userExempt = db.getPlayerExemptList();
/*     */     
/*     */ 
/*  78 */     StringBuilder ip_list = new ListFormatter(ipExempt).getFormattedList();
/*     */     
/*     */ 
/*     */ 
/*  82 */     StringBuilder user_list = new ListFormatter(userExempt).getFormattedList();
/*     */     
/*     */ 
/*  85 */     sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     
/*     */ 
/*     */ 
/*  89 */     if ((type.equals(ListType.ALL)) || (type.equals(ListType.IP))) {
/*  90 */       sendPlayerMessage(sender, ChatColor.GOLD + 
/*  91 */         getLocalString("EXEMPT_LIST_IP") + " " + ChatColor.RED + ipExempt
/*  92 */         .size(), false);
/*     */       
/*  94 */       sendPlayerMessage(sender, ip_list.toString(), false);
/*     */     }
/*     */     
/*  97 */     if (type.equals(ListType.ALL)) {
/*  98 */       sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 103 */     if ((type.equals(ListType.ALL)) || (type.equals(ListType.PLAYER))) {
/* 104 */       sendPlayerMessage(sender, ChatColor.GOLD + 
/* 105 */         getLocalString("EXEMPT_LIST_PLAYER") + " " + ChatColor.RED + userExempt
/* 106 */         .size(), false);
/*     */       
/* 108 */       sendPlayerMessage(sender, user_list.toString(), false);
/*     */     }
/*     */     
/* 111 */     sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     
/*     */ 
/* 114 */     if (type.equals(ListType.ALL))
/*     */     {
/* 116 */       sendPlayerMessage(sender, ChatColor.GOLD + 
/* 117 */         getLocalString("EXEMPT_LIST_TALLY") + " " + ChatColor.RED + (ipExempt
/* 118 */         .size() + userExempt.size()), false);
/*     */       
/* 120 */       sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     }
/*     */   }
/*     */   
/*     */   public static enum ListType
/*     */   {
/* 126 */     PLAYER, 
/* 127 */     IP, 
/* 128 */     ALL;
/*     */     
/*     */     private ListType() {}
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\exempt\list\CmdExemptListAll.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */