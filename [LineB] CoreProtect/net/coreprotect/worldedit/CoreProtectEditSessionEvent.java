/*    */ package net.coreprotect.worldedit;
/*    */ 
/*    */ import com.sk89q.worldedit.EditSession.Stage;
/*    */ import com.sk89q.worldedit.WorldEdit;
/*    */ import com.sk89q.worldedit.event.extent.EditSessionEvent;
/*    */ import com.sk89q.worldedit.extension.platform.Actor;
/*    */ import com.sk89q.worldedit.util.eventbus.EventBus;
/*    */ import net.coreprotect.CoreProtect;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ 
/*    */ public class CoreProtectEditSessionEvent
/*    */ {
/* 14 */   private static boolean initialized = false;
/*    */   
/*    */   public static boolean isInitialized() {
/* 17 */     return initialized;
/*    */   }
/*    */   
/*    */   public static void register() {
/*    */     try {
/* 22 */       CoreProtect.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(CoreProtect.getInstance(), new Runnable()
/*    */       {
/*    */         public void run() {
/*    */           try {
/* 26 */             WorldEdit.getInstance().getEventBus().register(new CoreProtectEditSessionEvent());
/* 27 */             CoreProtectEditSessionEvent.access$002(true);
/*    */           }
/*    */           catch (Exception e) {
/* 30 */             System.out.println("[CoreProtect] Unable to initialize WorldEdit logging."); } } }, 0L);
/*    */ 
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/*    */ 
/* 36 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */   
/*    */   @com.sk89q.worldedit.util.eventbus.Subscribe
/*    */   public void wrapForLogging(EditSessionEvent event) {
/* 42 */     Actor actor = event.getActor();
/* 43 */     com.sk89q.worldedit.world.World world = event.getWorld();
/* 44 */     if ((actor != null) && (event.getStage().equals(EditSession.Stage.BEFORE_CHANGE))) {
/* 45 */       event.setExtent(new CoreProtectLogger(actor, world, event.getExtent()));
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\worldedit\CoreProtectEditSessionEvent.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */