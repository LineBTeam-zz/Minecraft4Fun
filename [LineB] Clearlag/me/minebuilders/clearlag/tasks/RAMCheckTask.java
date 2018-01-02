/*    */ package me.minebuilders.clearlag.tasks;
/*    */ 
/*    */ import java.util.List;
/*    */ import me.minebuilders.clearlag.RAMUtil;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import me.minebuilders.clearlag.modules.TaskModule;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ 
/*    */ @ConfigPath(path="ram-meter")
/*    */ public class RAMCheckTask extends TaskModule
/*    */ {
/*    */   @ConfigValue
/*    */   private List<String> commands;
/*    */   @ConfigValue
/*    */   private int ramLimit;
/*    */   
/*    */   public void run()
/*    */   {
/* 23 */     if (RAMUtil.getUsedMemory() > this.ramLimit) {
/*    */       try {
/* 25 */         for (String s : this.commands)
/* 26 */           Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s);
/*    */       } catch (Exception e) {
/* 28 */         Util.warning("RAMCheckTask was unable to dispatch commands!");
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public int getInterval()
/*    */   {
/* 35 */     return Config.getConfig().getInt("ram-meter.interval") * 20;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\tasks\RAMCheckTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */