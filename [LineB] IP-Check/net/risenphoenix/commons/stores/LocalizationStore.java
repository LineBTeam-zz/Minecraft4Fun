/*    */ package net.risenphoenix.commons.stores;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import net.risenphoenix.commons.Plugin;
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
/*    */ public class LocalizationStore
/*    */   extends Store
/*    */ {
/*    */   private Map<String, String> values;
/*    */   
/*    */   public LocalizationStore(Plugin plugin)
/*    */   {
/* 43 */     super(plugin);
/* 44 */     this.values = new HashMap();
/* 45 */     initializeStore();
/*    */   }
/*    */   
/*    */   public final void add(String key, String value) {
/* 49 */     this.values.put(key, value);
/*    */   }
/*    */   
/*    */   public final Map<String, String> getValues() {
/* 53 */     return this.values;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\stores\LocalizationStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */