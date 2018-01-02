/*    */ package me.minebuilders.clearlag.tasks;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.annotations.AutoWire;
/*    */ import me.minebuilders.clearlag.annotations.ConfigModule;
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import me.minebuilders.clearlag.config.ConfigValueType;
/*    */ import me.minebuilders.clearlag.managers.EntityManager;
/*    */ import me.minebuilders.clearlag.removetype.AutoClear;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ 
/*    */ @ConfigPath(path="auto-removal")
/*    */ public class ClearTask extends me.minebuilders.clearlag.modules.TaskModule
/*    */ {
/*    */   @ConfigModule
/* 19 */   private AutoClear autoClear = new AutoClear();
/*    */   
/*    */ 
/*    */   @ConfigValue
/*    */   private int autoremovalInterval;
/*    */   
/*    */   @ConfigValue(valueType=ConfigValueType.COLORED_STRING)
/*    */   private String broadcastMessage;
/*    */   
/*    */   @ConfigValue(valueType=ConfigValueType.WARN_ARRAY)
/*    */   private HashMap<Integer, String> warnings;
/*    */   
/*    */   @AutoWire
/*    */   private EntityManager entityManager;
/*    */   
/* 34 */   private int interval = 0;
/*    */   
/*    */   public void run()
/*    */   {
/* 38 */     this.interval += 1;
/*    */     
/* 40 */     if (this.warnings.containsKey(Integer.valueOf(this.interval))) {
/* 41 */       Util.broadcast(((String)this.warnings.get(Integer.valueOf(this.interval))).replace("+remaining", "" + (this.autoremovalInterval - this.interval)));
/*    */     }
/*    */     
/* 44 */     if (this.interval >= this.autoremovalInterval) {
/* 45 */       int i = this.entityManager.removeEntities(this.autoClear);
/*    */       
/* 47 */       if (Config.getConfig().getBoolean("auto-removal.broadcast-removal")) {
/* 48 */         Util.broadcast(this.broadcastMessage.replace("+RemoveAmount", String.valueOf(i)));
/*    */       }
/*    */       
/* 51 */       this.interval = 0;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\tasks\ClearTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */