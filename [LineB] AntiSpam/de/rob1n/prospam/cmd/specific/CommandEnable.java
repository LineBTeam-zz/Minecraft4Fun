/*    */ package de.rob1n.prospam.cmd.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.Command;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CommandEnable extends Command
/*    */ {
/*    */   public CommandEnable(ProSpam plugin)
/*    */   {
/* 12 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getName()
/*    */   {
/* 18 */     return "enable";
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 24 */     return "Enables the plugin";
/*    */   }
/*    */   
/*    */ 
/*    */   public String[] getArgs()
/*    */   {
/* 30 */     return new String[] { "" };
/*    */   }
/*    */   
/*    */ 
/*    */   public void execute(CommandSender sender, String[] parameter)
/*    */   {
/* 36 */     this.settings.enabled = true;
/*    */     
/* 38 */     if (!this.settings.save())
/*    */     {
/* 40 */       sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*    */     }
/*    */     else
/*    */     {
/* 44 */       sender.sendMessage(ProSpam.prefixed("Plugin successfully enabled."));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandEnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */