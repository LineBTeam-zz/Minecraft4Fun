/*    */ package me.mdrunaway.chatclear;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandCCM
/*    */ {
/*    */   org.bukkit.command.Command cmd;
/*    */   String[] args;
/*    */   Player p;
/*    */   main plugin;
/*    */   
/*    */   public CommandCCM(org.bukkit.command.Command cmd, String[] args, Player p, main plugin) {
/* 13 */     this.cmd = cmd;
/* 14 */     this.args = args;
/* 15 */     this.p = p;
/* 16 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   public boolean execute() {
/* 20 */     if (this.cmd.getName().equalsIgnoreCase("ccm")) {
/* 21 */       if ((this.p.isOp()) || (this.p.hasPermission("cc.ccm"))) {
/* 22 */         for (int i = 0; i <= 120; i++)
/* 23 */           org.bukkit.Bukkit.broadcastMessage(org.bukkit.ChatColor.MAGIC + "gettingwildgettingwildgettingwildgettingwildgettingwildgettingwildgettingwildgettingwildgettingwildgettingwild");
/* 24 */         org.bukkit.Bukkit.broadcastMessage(org.bukkit.ChatColor.GOLD + "[" + this.plugin.getName() + "] " + org.bukkit.ChatColor.DARK_RED + "O chat foi limpo por: " + org.bukkit.ChatColor.GOLD + this.p.getName());
/*    */       } else {
/* 26 */         this.p.sendMessage(org.bukkit.ChatColor.GOLD + "[" + this.plugin.getName() + "] " + org.bukkit.ChatColor.RED + " Você não tem permissão para isso!");
/*    */       }
/*    */     }
/* 29 */     return true;
/*    */   }
/*    */ }