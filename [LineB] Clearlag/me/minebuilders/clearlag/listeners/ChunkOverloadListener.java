/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.modules.EventModule;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Location;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerMoveEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ 
/*    */ @ConfigPath(path="player-speed-limiter")
/*    */ public class ChunkOverloadListener
/*    */   extends EventModule
/*    */ {
/* 19 */   public HashMap<String, Long> players = new HashMap();
/*    */   
/*    */   @ConfigValue
/*    */   private long chunkToChunkTime;
/*    */   
/*    */   @ConfigValue
/*    */   private boolean limitOnlyFly;
/*    */   
/*    */   @EventHandler
/*    */   public void onMove(PlayerMoveEvent event)
/*    */   {
/* 30 */     if ((event.getFrom().getBlockX() >> 4 == event.getTo().getBlockX() >> 4) && (event.getFrom().getBlockZ() >> 4 == event.getTo().getBlockZ() >> 4)) {
/* 31 */       return;
/*    */     }
/*    */     
/* 34 */     if ((this.limitOnlyFly) && (!event.getPlayer().isFlying())) {
/* 35 */       return;
/*    */     }
/*    */     
/* 38 */     if (((Long)this.players.get(event.getPlayer().getName())).longValue() > System.currentTimeMillis()) {
/* 39 */       Location l = event.getFrom();
/* 40 */       event.setTo(new Location(l.getWorld(), l.getBlockX() + 0.5D, l.getBlockY(), l.getBlockZ() + 0.5D, event.getTo().getYaw(), event.getTo().getPitch()));
/*    */     } else {
/* 42 */       this.players.put(event.getPlayer().getName(), Long.valueOf(System.currentTimeMillis() + this.chunkToChunkTime));
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onJoin(PlayerJoinEvent event) {
/* 48 */     this.players.put(event.getPlayer().getName(), Long.valueOf(System.currentTimeMillis() + this.chunkToChunkTime));
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onLeave(PlayerQuitEvent event) {
/* 53 */     this.players.remove(event.getPlayer().getName());
/*    */   }
/*    */   
/*    */   public void setEnabled()
/*    */   {
/* 58 */     super.setEnabled();
/* 59 */     for (Player p : Bukkit.getOnlinePlayers()) {
/* 60 */       this.players.put(p.getName(), Long.valueOf(System.currentTimeMillis() + this.chunkToChunkTime));
/*    */     }
/*    */   }
/*    */   
/*    */   public void setDisabled()
/*    */   {
/* 66 */     super.setDisabled();
/* 67 */     this.players.clear();
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\ChunkOverloadListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */