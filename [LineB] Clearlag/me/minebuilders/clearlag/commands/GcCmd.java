/*    */ package me.minebuilders.clearlag.commands;
/*    */ 
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class GcCmd extends me.minebuilders.clearlag.modules.CommandModule
/*    */ {
/*    */   public GcCmd()
/*    */   {
/* 10 */     this.name = "gc";
/* 11 */     this.desc = "(Request garbage collecter)";
/*    */   }
/*    */   
/*    */ 
/*    */   protected void run(CommandSender sender, String[] args)
/*    */   {
/* 17 */     Util.msg("§bAttemping to free memory!", sender);
/*    */     
/* 19 */     System.gc();
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\commands\GcCmd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */