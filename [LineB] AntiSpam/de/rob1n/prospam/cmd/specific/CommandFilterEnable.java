/*    */ package de.rob1n.prospam.cmd.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.Command;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CommandFilterEnable extends Command
/*    */ {
/*    */   public CommandFilterEnable(ProSpam plugin)
/*    */   {
/* 12 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getName()
/*    */   {
/* 18 */     return "filter-enable";
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 24 */     return "Enables a Filter";
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
/* 41 */       this.settings.filter_enabled_caps = true;
/* 42 */     } else if (filterName.equalsIgnoreCase("chars")) {
/* 43 */       this.settings.filter_enabled_chars = true;
/* 44 */     } else if (filterName.equalsIgnoreCase("flood")) {
/* 45 */       this.settings.filter_enabled_flood = true;
/* 46 */     } else if (filterName.equalsIgnoreCase("similar")) {
/* 47 */       this.settings.filter_enabled_similar = true;
/* 48 */     } else if (filterName.equalsIgnoreCase("urls")) {
/* 49 */       this.settings.filter_enabled_urls = true;
/* 50 */     } else if (filterName.equalsIgnoreCase("blacklist")) {
/* 51 */       this.settings.filter_enabled_blacklist = true;
/*    */     } else {
/* 53 */       throw new IllegalArgumentException();
/*    */     }
/* 55 */     if (!this.settings.save())
/*    */     {
/* 57 */       sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*    */     }
/*    */     else
/*    */     {
/* 61 */       sender.sendMessage(ProSpam.prefixed(StringUtils.capitalize(filterName) + " filter successfully enabled"));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandFilterEnable.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */