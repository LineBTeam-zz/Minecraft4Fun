/*    */ package net.risenphoenix.ipcheck.commands.toggle;
/*    */ 
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*    */ import net.risenphoenix.commons.localization.LocalizationManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ToggleOption
/*    */ {
/*    */   private Plugin plugin;
/*    */   private String optionID;
/*    */   private String displayID;
/*    */   private String[] callValues;
/*    */   
/*    */   public ToggleOption(Plugin plugin, String optionID, String displayID, String[] callValues)
/*    */   {
/* 51 */     this.plugin = plugin;
/* 52 */     this.optionID = optionID;
/* 53 */     this.displayID = displayID;
/* 54 */     this.callValues = callValues;
/*    */   }
/*    */   
/*    */ 
/*    */   public final boolean onExecute()
/*    */   {
/* 60 */     ConfigurationManager config = this.plugin.getConfigurationManager();
/* 61 */     boolean newValue = !config.getBoolean(this.optionID);
/*    */     
/*    */ 
/* 64 */     config.setConfigurationOption(this.optionID, Boolean.valueOf(newValue));
/*    */     
/*    */ 
/* 67 */     return newValue;
/*    */   }
/*    */   
/*    */   public final String[] getCallValues() {
/* 71 */     return this.callValues;
/*    */   }
/*    */   
/*    */   public final String getOptionID() {
/* 75 */     return this.optionID;
/*    */   }
/*    */   
/*    */   public final String getDisplayID() {
/* 79 */     return this.plugin.getLocalizationManager().getLocalString(this.displayID);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\toggle\ToggleOption.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */