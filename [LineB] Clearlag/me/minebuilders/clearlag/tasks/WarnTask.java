/*    */ package me.minebuilders.clearlag.tasks;
/*    */ 
/*    */ import me.minebuilders.clearlag.Clearlag;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ 
/*    */ public class WarnTask extends me.minebuilders.clearlag.modules.EventModule
/*    */ {
/*    */   private boolean resetConfig;
/*    */   
/*    */   public WarnTask(boolean resetConfig)
/*    */   {
/* 15 */     this.resetConfig = resetConfig;
/*    */   }
/*    */   
/*    */   @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
/*    */   public void onJoin(PlayerJoinEvent e) {
/* 20 */     Player p = e.getPlayer();
/*    */     
/* 22 */     if (p.hasPermission("lagg.clear")) {
/* 23 */       p.sendMessage("§8§l]=============(§7§lClearlag Updated§8§l)=============[");
/* 24 */       p.sendMessage("§6§lNew Version: §7§l" + Clearlag.getInstance().getDescription().getVersion());
/* 25 */       p.sendMessage("§6Please check §7§nhttp://dev.bukkit.org/bukkit-plugins/clearlagg/");
/*    */       
/* 27 */       if (this.resetConfig) {
/* 28 */         p.sendMessage("§cWARNING: §7Clearlag was forced to reset your config due to an update!");
/* 29 */         p.sendMessage("§cWARNING: §7Please re-modify your clearlag config!");
/* 30 */         p.sendMessage("§8This message will go away once you either reboot, or reload clearlag!");
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\tasks\WarnTask.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */