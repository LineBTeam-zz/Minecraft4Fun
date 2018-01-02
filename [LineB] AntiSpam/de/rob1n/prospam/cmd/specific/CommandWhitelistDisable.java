/*    */ package de.rob1n.prospam.cmd.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.Command;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CommandWhitelistDisable extends Command
/*    */ {
/*    */   public CommandWhitelistDisable(ProSpam plugin)
/*    */   {
/* 12 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getName()
/*    */   {
/* 18 */     return "whitelist-disable";
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 24 */     return "Disable the whitelist";
/*    */   }
/*    */   
/*    */ 
/*    */   public String[] getArgs()
/*    */   {
/* 30 */     return new String[] { "" };
/*    */   }
/*    */   
/*    */   public void execute(CommandSender sender, String[] parameter)
/*    */     throws IllegalArgumentException
/*    */   {
/* 36 */     this.settings.whitelist_enabled = false;
/*    */     
/* 38 */     if (!this.settings.save())
/*    */     {
/* 40 */       sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*    */     }
/*    */     else
/*    */     {
/* 44 */       sender.sendMessage(ProSpam.prefixed("Whitelist successfully disabled."));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandWhitelistDisable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */