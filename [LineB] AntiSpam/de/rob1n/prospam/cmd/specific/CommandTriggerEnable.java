/*    */ package de.rob1n.prospam.cmd.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.Command;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CommandTriggerEnable extends Command
/*    */ {
/*    */   public CommandTriggerEnable(ProSpam plugin)
/*    */   {
/* 13 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getName()
/*    */   {
/* 19 */     return "trigger-enable";
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 25 */     return "Enable spam triggers";
/*    */   }
/*    */   
/*    */ 
/*    */   public String[] getArgs()
/*    */   {
/* 31 */     return new String[] { "<caps|chars|flood|similar|urls|blacklist>" };
/*    */   }
/*    */   
/*    */   public void execute(CommandSender sender, String[] parameter)
/*    */     throws IllegalArgumentException
/*    */   {
/* 37 */     if (parameter.length <= 1) {
/* 38 */       throw new IllegalArgumentException();
/*    */     }
/* 40 */     String triggerName = parameter[1];
/* 41 */     if (triggerName.equalsIgnoreCase("caps")) {
/* 42 */       this.settings.trigger_enabled_caps = true;
/* 43 */     } else if (triggerName.equalsIgnoreCase("chars")) {
/* 44 */       this.settings.trigger_enabled_chars = true;
/* 45 */     } else if (triggerName.equalsIgnoreCase("flood")) {
/* 46 */       this.settings.trigger_enabled_flood = true;
/* 47 */     } else if (triggerName.equalsIgnoreCase("similar")) {
/* 48 */       this.settings.trigger_enabled_similar = true;
/* 49 */     } else if (triggerName.equalsIgnoreCase("urls")) {
/* 50 */       this.settings.trigger_enabled_urls = true;
/* 51 */     } else if (triggerName.equalsIgnoreCase("blacklist")) {
/* 52 */       this.settings.trigger_enabled_blacklist = true;
/*    */     } else {
/* 54 */       throw new IllegalArgumentException();
/*    */     }
/* 56 */     if (!this.settings.save())
/*    */     {
/* 58 */       sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*    */     }
/*    */     else
/*    */     {
/* 62 */       sender.sendMessage(ProSpam.prefixed(StringUtils.capitalize(triggerName) + " triggers successfully enabled"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandTriggerEnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */