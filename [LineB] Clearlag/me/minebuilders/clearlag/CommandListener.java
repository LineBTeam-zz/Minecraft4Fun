/*    */ package me.minebuilders.clearlag;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.minebuilders.clearlag.modules.CommandModule;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.Command;
/*    */ import org.bukkit.command.CommandExecutor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.command.PluginCommand;
/*    */ 
/*    */ public class CommandListener implements CommandExecutor
/*    */ {
/* 14 */   private List<CommandModule> cmds = new ArrayList();
/*    */   
/*    */   public CommandListener() {
/* 17 */     Clearlag.getInstance().getCommand("lagg").setExecutor(this);
/*    */   }
/*    */   
/*    */   public boolean onCommand(CommandSender s, Command command, String label, String[] args)
/*    */   {
/* 22 */     if ((args.length == 0) || (getCmd(args[0]) == null))
/*    */     {
/* 24 */       List<CommandModule> cmds = getUserCmds(s);
/*    */       
/* 26 */       if (cmds.size() == 0) {
/* 27 */         s.sendMessage(ChatColor.RED + "You do not have permission to use this command!");
/* 28 */         return false;
/*    */       }
/*    */       
/* 31 */       s.sendMessage("§3-------------(§b§lYour Clearlag Commands§3)-------------");
/*    */       
/* 33 */       for (CommandModule cmd : cmds) {
/* 34 */         s.sendMessage(" §4- §8/§3lagg §b" + cmd.getName() + " §f -  " + cmd.getDescription());
/*    */       }
/*    */       
/* 37 */       s.sendMessage(ChatColor.DARK_AQUA + "----------------------------------------------------");
/*    */     }
/*    */     else {
/* 40 */       getCmd(args[0]).processCmd(s, args);
/*    */     }
/* 42 */     return true;
/*    */   }
/*    */   
/*    */   public void addCmd(CommandModule cmd)
/*    */   {
/* 47 */     this.cmds.add(cmd);
/*    */   }
/*    */   
/*    */   private CommandModule getCmd(String s) {
/* 51 */     for (CommandModule cmd : this.cmds) {
/* 52 */       if (cmd.getName().equalsIgnoreCase(s)) {
/* 53 */         return cmd;
/*    */       }
/*    */     }
/* 56 */     return null;
/*    */   }
/*    */   
/*    */   public List<CommandModule> getUserCmds(CommandSender p) {
/* 60 */     List<CommandModule> mod = new ArrayList();
/*    */     
/* 62 */     for (CommandModule cmd : this.cmds) {
/* 63 */       if (p.hasPermission("lagg." + cmd.getName())) {
/* 64 */         mod.add(cmd);
/*    */       }
/*    */     }
/*    */     
/* 68 */     return mod;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\CommandListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */