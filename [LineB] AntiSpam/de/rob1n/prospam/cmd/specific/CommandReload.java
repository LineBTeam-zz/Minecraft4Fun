/*    */ package de.rob1n.prospam.cmd.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.Command;
/*    */ import de.rob1n.prospam.data.DataHandler;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CommandReload extends Command
/*    */ {
/*    */   public CommandReload(ProSpam plugin)
/*    */   {
/* 12 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getName()
/*    */   {
/* 18 */     return "reload";
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 24 */     return "Reload settings from the config file";
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
/* 36 */     this.plugin.getDataHandler().loadAll();
/*    */     
/* 38 */     sender.sendMessage(ProSpam.prefixed("Settings successfully loaded."));
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandReload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */