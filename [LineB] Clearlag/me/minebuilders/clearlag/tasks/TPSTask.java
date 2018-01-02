/*    */ package me.minebuilders.clearlag.tasks;
/*    */ 
/*    */ import me.minebuilders.clearlag.Clearlag;
/*    */ import me.minebuilders.clearlag.events.TPSUpdateEvent;
/*    */ import me.minebuilders.clearlag.modules.TaskModule;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class TPSTask extends TaskModule
/*    */ {
/*    */   private long mills;
/* 13 */   private double[] tpsArr = new double[10];
/*    */   
/* 15 */   private int index = 0;
/*    */   
/*    */   public void setEnabled()
/*    */   {
/* 19 */     super.setEnabled();
/*    */     
/* 21 */     for (int i = 0; i < 10; i++) {
/* 22 */       this.tpsArr[i] = 20.0D;
/*    */     }
/*    */   }
/*    */   
/*    */   protected int startTask() {
/* 27 */     this.mills = 0L;
/* 28 */     return Bukkit.getScheduler().scheduleSyncRepeatingTask(Clearlag.getInstance(), this, 120L, getInterval());
/*    */   }
/*    */   
/*    */   public double getTPS() {
/* 32 */     double tpsSum = 0.0D;
/*    */     
/* 34 */     for (double d : this.tpsArr) {
/* 35 */       tpsSum += d;
/*    */     }
/*    */     
/* 38 */     return Math.round(tpsSum / 10.0D * 100.0D) / 100.0D;
/*    */   }
/*    */   
/*    */   public String getStringTPS() {
/* 42 */     return getColor() + String.valueOf(getTPS());
/*    */   }
/*    */   
/*    */   public ChatColor getColor() {
/* 46 */     double tps = getTPS();
/*    */     
/* 48 */     if (tps > 17.0D) return ChatColor.GREEN;
/* 49 */     if (tps > 13.0D) return ChatColor.GOLD;
/* 50 */     return ChatColor.RED;
/*    */   }
/*    */   
/*    */   public void run() {
/* 54 */     if (this.mills > 0L)
/*    */     {
/*    */ 
/* 57 */       double diff = System.currentTimeMillis() - this.mills - 1000.0D;
/*    */       
/* 59 */       if (diff < 0.0D)
/* 60 */         diff = Math.abs(diff);
/*    */       double tps;
/* 62 */       double tps; if (diff == 0.0D) {
/* 63 */         tps = 20.0D;
/*    */       } else {
/* 65 */         tps = 20.0D - diff / 50.0D;
/*    */       }
/*    */       
/* 68 */       if (tps < 0.0D) {
/* 69 */         tps = 0.0D;
/*    */       }
/* 71 */       this.tpsArr[(this.index++)] = tps;
/*    */       
/* 73 */       if (this.index >= this.tpsArr.length) {
/* 74 */         this.index = 0;
/*    */         
/* 76 */         Bukkit.getPluginManager().callEvent(new TPSUpdateEvent(getTPS()));
/*    */       }
/*    */     }
/* 79 */     this.mills = System.currentTimeMillis();
/*    */   }
/*    */   
/*    */   public int getInterval()
/*    */   {
/* 84 */     return 20;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\tasks\TPSTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */