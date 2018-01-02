/*    */ package net.risenphoenix.commons.stores;
/*    */ 
/*    */ import net.risenphoenix.commons.Plugin;
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
/*    */ public class Store
/*    */ {
/*    */   public final Plugin plugin;
/*    */   
/*    */   public Store(Plugin plugin)
/*    */   {
/* 40 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   public void initializeStore()
/*    */   {
/* 45 */     throw new UnsupportedOperationException(this.plugin.getLocalizationManager().getLocalString("NO_IMPLEMENT"));
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\stores\Store.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */