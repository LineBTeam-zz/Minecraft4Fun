/*    */ package de.rob1n.prospam.cmd.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.Command;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CommandDisable extends Command
/*    */ {
/*    */   public CommandDisable(ProSpam plugin)
/*    */   {
/* 12 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getName()
/*    */   {
/* 18 */     return "disable";
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 24 */     return "Disables the plugin";
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
/* 36 */     this.settings.enabled = false;
/*    */     
/* 38 */     if (!this.settings.save())
/*    */     {
/* 40 */       sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*    */     }
/*    */     else
/*    */     {
/* 44 */       sender.sendMessage(ProSpam.prefixed("Plugin successfully disabled."));
/*    */     }
/*    */   }
/*    */ }

