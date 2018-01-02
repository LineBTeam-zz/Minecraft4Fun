/*    */ package me.minebuilders.clearlag.commands;
/*    */ 
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.modules.CommandModule;
/*    */ import me.minebuilders.clearlag.tasks.TPSTask;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class TpsCmd extends CommandModule
/*    */ {
/*    */   @me.minebuilders.clearlag.annotations.AutoWire
/*    */   private TPSTask tpsTask;
/*    */   
/*    */   public TpsCmd()
/*    */   {
/* 15 */     this.name = "tps";
/* 16 */     this.desc = "(Checks the servers tick rate)";
/*    */   }
/*    */   
/*    */   protected void run(CommandSender sender, String[] args)
/*    */   {
/* 21 */     Util.msg(this.tpsTask.getStringTPS(), sender);
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\commands\TpsCmd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */