/*    */ package me.minebuilders.clearlag.tasks;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.util.Date;
/*    */ import me.minebuilders.clearlag.Clearlag;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ import org.bukkit.scheduler.BukkitTask;
/*    */ 
/*    */ @me.minebuilders.clearlag.annotations.ConfigPath(path="log-purger")
/*    */ public class LogPurger extends me.minebuilders.clearlag.modules.TaskModule
/*    */ {
/*    */   public void run()
/*    */   {
/* 18 */     long time = new Date().getTime() - 86400000L * Config.getConfig().getLong("log-purger.days-old");
/*    */     
/* 20 */     File folder = new File(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/logs");
/*    */     
/* 22 */     if (!folder.exists()) {
/* 23 */       return;
/*    */     }
/*    */     
/* 26 */     File[] files = folder.listFiles();
/*    */     
/* 28 */     int deleted = 0;
/*    */     
/* 30 */     for (File file : files) {
/* 31 */       if ((file.isFile()) && (file.getName().endsWith(".log.gz")) && (time > Util.parseTime(file.getName().replace(".log.gz", "")).getTime())) {
/* 32 */         file.delete();
/* 33 */         deleted++;
/*    */       }
/*    */     }
/*    */     
/* 37 */     Util.log(deleted + " Logs have been removed!");
/*    */   }
/*    */   
/*    */   public int startTask()
/*    */   {
/* 42 */     return Bukkit.getScheduler().runTaskLaterAsynchronously(Clearlag.getInstance(), this, 0L).getTaskId();
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\tasks\LogPurger.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */