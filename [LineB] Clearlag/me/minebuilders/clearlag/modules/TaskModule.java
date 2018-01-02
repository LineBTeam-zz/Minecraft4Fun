/*    */ package me.minebuilders.clearlag.modules;
/*    */ 
/*    */ import me.minebuilders.clearlag.Clearlag;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ public abstract class TaskModule extends ClearlagModule implements Runnable
/*    */ {
/*  9 */   protected int taskid = -2;
/*    */   
/*    */   public void setEnabled()
/*    */   {
/* 13 */     super.setEnabled();
/*    */     
/* 15 */     this.taskid = startTask();
/*    */   }
/*    */   
/*    */   protected int startTask() {
/* 19 */     return Bukkit.getScheduler().scheduleSyncRepeatingTask(Clearlag.getInstance(), this, getInterval(), getInterval());
/*    */   }
/*    */   
/*    */   public void setDisabled()
/*    */   {
/* 24 */     super.setDisabled();
/* 25 */     Bukkit.getScheduler().cancelTask(this.taskid);
/*    */   }
/*    */   
/*    */   public int getInterval() {
/* 29 */     return 20;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\modules\TaskModule.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */