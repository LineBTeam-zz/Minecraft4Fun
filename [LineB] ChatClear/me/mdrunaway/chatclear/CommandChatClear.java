/*    */ package me.mdrunaway.chatclear;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ 
/*    */ public class CommandChatClear {
/*    */   org.bukkit.command.Command cmd;
/*    */   String[] args;
/*    */   org.bukkit.entity.Player p;
/*    */   main plugin;
/*    */   
/*    */   public CommandChatClear(org.bukkit.command.Command cmd, String[] args, org.bukkit.entity.Player p, main plugin) {
/* 12 */     this.cmd = cmd;
/* 13 */     this.args = args;
/* 14 */     this.p = p;
/* 15 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   public boolean execute() {
/* 19 */     if (this.cmd.getName().equalsIgnoreCase("ChatClear")) {
/* 20 */       this.p.sendMessage(ChatColor.GOLD + "[" + this.plugin.getName() + "] " + ChatColor.GREEN + " Nome: ChatClear");
/*    */     }
/* 26 */     return true;
/*    */   }
/*    */ }