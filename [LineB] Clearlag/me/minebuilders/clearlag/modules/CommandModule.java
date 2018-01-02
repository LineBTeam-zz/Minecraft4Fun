/*    */ package me.minebuilders.clearlag.modules;
/*    */ 
/*    */ import java.util.Arrays;
/*    */ import me.minebuilders.clearlag.CommandListener;
/*    */ import me.minebuilders.clearlag.annotations.AutoWire;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public abstract class CommandModule
/*    */   extends ClearlagModule
/*    */ {
/*    */   @AutoWire
/*    */   private CommandListener commandListener;
/*    */   protected String name;
/* 17 */   protected int argLength = 0;
/* 18 */   protected String usage = "";
/* 19 */   protected String desc = "";
/*    */   
/*    */   public void processCmd(CommandSender s, String[] arg)
/*    */   {
/* 23 */     if (!s.hasPermission("lagg." + this.name)) {
/* 24 */       s.sendMessage(ChatColor.RED + "You do not have permission to use §8/§7lagg " + this.name);
/* 25 */     } else if (this.argLength >= arg.length) {
/* 26 */       s.sendMessage(ChatColor.RED + "Wrong usage: §8/§7lagg " + this.usage);
/*    */     }
/*    */     else {
/* 29 */       if (arg.length >= 1) {
/* 30 */         arg = (String[])Arrays.copyOfRange(arg, 1, arg.length);
/*    */       }
/* 32 */       if ((s instanceof Player)) {
/* 33 */         run((Player)s, arg);
/*    */       } else
/* 35 */         run(s, arg);
/*    */     }
/*    */   }
/*    */   
/*    */   protected void run(Player player, String[] args) {
/* 40 */     run(player, args);
/*    */   }
/*    */   
/*    */   protected void run(CommandSender sender, String[] args) {
/* 44 */     sender.sendMessage(ChatColor.RED + "This command can only be used by players!");
/*    */   }
/*    */   
/*    */   public void setEnabled()
/*    */   {
/* 49 */     super.setEnabled();
/*    */     
/* 51 */     this.commandListener.addCmd(this);
/*    */   }
/*    */   
/*    */   public String getName() {
/* 55 */     return this.name;
/*    */   }
/*    */   
/*    */   public int getArgLength() {
/* 59 */     return this.argLength;
/*    */   }
/*    */   
/*    */   public String getUsage() {
/* 63 */     return this.usage;
/*    */   }
/*    */   
/*    */   public String getDescription() {
/* 67 */     return this.desc;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\modules\CommandModule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */