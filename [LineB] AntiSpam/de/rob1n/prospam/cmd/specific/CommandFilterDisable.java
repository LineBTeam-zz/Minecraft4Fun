/*    */ package de.rob1n.prospam.cmd.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.Command;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CommandFilterDisable extends Command
/*    */ {
/*    */   public CommandFilterDisable(ProSpam plugin)
/*    */   {
/* 12 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getName()
/*    */   {
/* 18 */     return "filter-disable";
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 24 */     return "Disables a Filter";
/*    */   }
/*    */   
/*    */ 
/*    */   public String[] getArgs()
/*    */   {
/* 30 */     return new String[] { "<caps|chars|flood|similar|urls|blacklist>" };
/*    */   }
/*    */   
/*    */ 
/*    */   public void execute(CommandSender sender, String[] parameter)
/*    */   {
/* 36 */     if (parameter.length <= 1) {
/* 37 */       throw new IllegalArgumentException();
/*    */     }
/* 39 */     String filterName = parameter[1];
/* 40 */     if (filterName.equalsIgnoreCase("caps")) {
/* 41 */       this.settings.filter_enabled_caps = false;
/* 42 */     } else if (filterName.equalsIgnoreCase("chars")) {
/* 43 */       this.settings.filter_enabled_chars = false;
/* 44 */     } else if (filterName.equalsIgnoreCase("flood")) {
/* 45 */       this.settings.filter_enabled_flood = false;
/* 46 */     } else if (filterName.equalsIgnoreCase("similar")) {
/* 47 */       this.settings.filter_enabled_similar = false;
/* 48 */     } else if (filterName.equalsIgnoreCase("urls")) {
/* 49 */       this.settings.filter_enabled_urls = false;
/* 50 */     } else if (filterName.equalsIgnoreCase("blacklist")) {
/* 51 */       this.settings.filter_enabled_blacklist = false;
/*    */     } else {
/* 53 */       throw new IllegalArgumentException();
/*    */     }
/* 55 */     if (!this.settings.save())
/*    */     {
/* 57 */       sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*    */     }
/*    */     else
/*    */     {
/* 61 */       sender.sendMessage(ProSpam.prefixed(StringUtils.capitalize(filterName) + " filter successfully disabled"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandFilterDisable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */