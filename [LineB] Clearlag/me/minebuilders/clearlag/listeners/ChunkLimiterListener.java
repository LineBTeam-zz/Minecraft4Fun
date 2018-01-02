/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import me.minebuilders.clearlag.modules.EventModule;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.world.ChunkLoadEvent;
/*    */ 
/*    */ @ConfigPath(path="chunk-limiter")
/*    */ public class ChunkLimiterListener extends EventModule
/*    */ {
/*    */   @ConfigValue
/*    */   private int limit;
/*    */   @ConfigValue
/*    */   private boolean createNewChunks;
/*    */   
/*    */   @EventHandler
/*    */   public void onChunkLoad(ChunkLoadEvent event)
/*    */   {
/* 23 */     if (((!this.createNewChunks) && (event.isNewChunk())) || (countChunks() >= this.limit)) {
/* 24 */       event.getChunk().unload(false);
/*    */       
/* 26 */       for (Chunk c : event.getWorld().getLoadedChunks()) {
/* 27 */         c.unload(true, true);
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   public int countChunks() {
/* 33 */     int size = 0;
/* 34 */     for (World w : Bukkit.getWorlds()) {
/* 35 */       size += w.getLoadedChunks().length;
/*    */     }
/* 37 */     return size;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\ChunkLimiterListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */