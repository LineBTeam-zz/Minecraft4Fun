/*    */ package me.mdrunaway.chatclear;
/*    */ 
/*    */ import org.bukkit.command.Command;
/*    */ 
/*    */ public class main extends org.bukkit.plugin.java.JavaPlugin implements org.bukkit.event.Listener {
/*    */   public static main plugin;
/*    */   Command cmd;
/*    */   org.bukkit.command.CommandSender sender;
/*    */   String[] args;
/*    */   org.bukkit.entity.Player p;
/*    */   
/*    */   public void onEnable() {
/* 13 */     System.out.println("ChatClear foi ligado");
/*    */   }
/*    */   
/*    */   public void onDisable() {
/* 18 */     System.out.println("ChatClear foi desligado");
/*    */   }
/*    */   
/*    */   public boolean onCommand(org.bukkit.command.CommandSender sender, Command cmd, String cmdLabel, String[] args) {
/* 23 */     if ((sender instanceof org.bukkit.entity.Player)) {
/* 24 */       this.p = ((org.bukkit.entity.Player)sender);
/*    */     } else {
/* 26 */       System.out.println("Esse comando tem que ser executado como um Jogador!");
/* 27 */       return true;
/*    */     }
/* 29 */     if (cmd.getName().equalsIgnoreCase("cc"))
/* 30 */       return new CommandCC(cmd, args, this.p, this).execute();
/* 31 */     if (cmd.getName().equalsIgnoreCase("chatclear"))
/* 32 */       return new CommandChatClear(cmd, args, this.p, this).execute();
/* 33 */     if (cmd.getName().equalsIgnoreCase("cco"))
/* 34 */       return new CommandCCO(cmd, args, this.p, this).execute();
/* 35 */     if (cmd.getName().equalsIgnoreCase("CCM"))
/* 36 */       return new CommandCCM(cmd, args, this.p, this).execute();
/* 37 */     return true;
/*    */   }
/*    */ }
