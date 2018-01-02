/*    */ package me.minebuilders.clearlag.commands;
/*    */ 
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.modules.CommandModule;
/*    */ import me.minebuilders.clearlag.tasks.HaltTask;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class HaltCmd extends CommandModule
/*    */ {
/*    */   @me.minebuilders.clearlag.annotations.AutoWire
/*    */   private HaltTask halttask;
/*    */   
/*    */   public HaltCmd()
/*    */   {
/* 15 */     this.name = "halt";
/* 16 */     this.desc = "(Halts most server activity)";
/*    */   }
/*    */   
/*    */ 
/*    */   protected void run(CommandSender sender, String[] args)
/*    */   {
/* 22 */     if (!this.halttask.isEnabled()) {
/* 23 */       this.halttask.setEnabled();
/* 24 */       Util.msg("§bServer activity has been §chalted§b!", sender);
/*    */     } else {
/* 26 */       this.halttask.setDisabled();
/* 27 */       Util.msg("§aServer activity is no longer halted!", sender);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\commands\HaltCmd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */