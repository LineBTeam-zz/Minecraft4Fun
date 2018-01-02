/*    */ package me.minebuilders.clearlag.tasks;
/*    */ 
/*    */ import java.util.List;
/*    */ import me.minebuilders.clearlag.Clearlag;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.annotations.AutoWire;
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import me.minebuilders.clearlag.config.ConfigValueType;
/*    */ import me.minebuilders.clearlag.modules.TaskModule;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @ConfigPath(path="tps-meter")
/*    */ public class TPSCheckTask
/*    */   extends TaskModule
/*    */ {
/*    */   @ConfigValue
/*    */   private List<String> commands;
/*    */   @ConfigValue
/*    */   private List<String> recoverCommands;
/*    */   @ConfigValue
/*    */   private double tpsTrigger;
/*    */   @ConfigValue
/*    */   private double tpsRecover;
/*    */   @ConfigValue(valueType=ConfigValueType.COLORED_STRING)
/*    */   private String triggerBroadcastMessage;
/*    */   @ConfigValue(valueType=ConfigValueType.COLORED_STRING)
/*    */   private String recoverBroadcastMessage;
/*    */   @ConfigValue
/*    */   private boolean broadcastEnabled;
/*    */   @AutoWire
/*    */   private TPSTask tpsTask;
/* 42 */   private boolean isRecovered = true;
/*    */   
/*    */   public void run() {
/* 45 */     double tps = this.tpsTask.getTPS();
/*    */     
/* 47 */     if ((tps <= this.tpsTrigger) && (this.isRecovered))
/*    */     {
/* 49 */       if (this.broadcastEnabled) {
/* 50 */         Bukkit.broadcastMessage(this.triggerBroadcastMessage);
/*    */       }
/*    */       try {
/* 53 */         for (String s : this.commands)
/* 54 */           Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
/*    */       } catch (Exception e) {
/* 56 */         Util.warning("TPSCheckTask was unable to dispatch commands!");
/*    */       } finally {
/* 58 */         this.isRecovered = false;
/*    */       }
/* 60 */     } else if ((tps >= this.tpsRecover) && (!this.isRecovered))
/*    */     {
/*    */       try {
/* 63 */         if (this.broadcastEnabled) {
/* 64 */           Bukkit.broadcastMessage(this.recoverBroadcastMessage);
/*    */         }
/* 66 */         for (String s : this.recoverCommands)
/* 67 */           Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
/*    */       } catch (Exception e) {
/* 69 */         Util.warning("TPSCheckTask was unable to dispatch commands!");
/*    */       } finally {
/* 71 */         this.isRecovered = true;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public int getInterval()
/*    */   {
/* 78 */     return Config.getConfig().getInt("tps-meter.interval") * 20;
/*    */   }
/*    */   
/*    */   protected int startTask()
/*    */   {
/* 83 */     return Bukkit.getScheduler().scheduleSyncRepeatingTask(Clearlag.getInstance(), this, 420L, getInterval());
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\tasks\TPSCheckTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */