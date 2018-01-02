/*    */ package me.mdrunaway.chatclear;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandCCO
/*    */ {
/*    */   org.bukkit.command.Command cmd;
/*    */   String[] args;
/*    */   Player p;
/*    */   main plugin;
/*    */   
/*    */   public CommandCCO(org.bukkit.command.Command cmd, String[] args, Player p, main plugin) {
/* 13 */     this.cmd = cmd;
/* 14 */     this.args = args;
/* 15 */     this.p = p;
/* 16 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   public boolean execute() {
/* 20 */     if (this.cmd.getName().equalsIgnoreCase("cco")) {
/* 21 */       if ((this.p.isOp()) || (this.p.hasPermission("cc.cco"))) {
/* 22 */         for (int i = 0; i <= 120; i++)
/* 23 */           org.bukkit.Bukkit.broadcastMessage("");
/*    */       } else {
/* 25 */         this.p.sendMessage(org.bukkit.ChatColor.GOLD + "[" + this.plugin.getName() + "] " + org.bukkit.ChatColor.RED + " Você não tem permissão para isso!");
/*    */       }
/*    */     }
/* 28 */     return true;
/*    */   }
/*    */ }
