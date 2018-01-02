/*    */ package me.minebuilders.clearlag.modules;
/*    */ 
/*    */ import me.minebuilders.clearlag.Clearlag;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public abstract class EventModule extends ClearlagModule implements Listener
/*    */ {
/*    */   public void setEnabled()
/*    */   {
/* 12 */     super.setEnabled();
/*    */     
/* 14 */     Bukkit.getPluginManager().registerEvents(this, Clearlag.getInstance());
/*    */   }
/*    */   
/*    */   public void setDisabled()
/*    */   {
/* 19 */     super.setDisabled();
/*    */     
/* 21 */     org.bukkit.event.HandlerList.unregisterAll(this);
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\modules\EventModule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */