/*    */ package net.risenphoenix.commons.stores;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.configuration.ConfigurationOption;
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
/*    */ public class ConfigurationStore
/*    */   extends Store
/*    */ {
/*    */   private ArrayList<ConfigurationOption> options;
/*    */   
/*    */   public ConfigurationStore(Plugin plugin)
/*    */   {
/* 43 */     super(plugin);
/* 44 */     this.options = new ArrayList();
/* 45 */     initializeStore();
/*    */   }
/*    */   
/*    */   public final void add(ConfigurationOption option) {
/* 49 */     this.options.add(option);
/*    */   }
/*    */   
/*    */   public final ArrayList<ConfigurationOption> getOptions() {
/* 53 */     return this.options;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\stores\ConfigurationStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */