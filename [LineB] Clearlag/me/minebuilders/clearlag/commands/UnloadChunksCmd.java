/*    */ package me.minebuilders.clearlag.commands;
/*    */ 
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.event.world.ChunkUnloadEvent;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class UnloadChunksCmd extends me.minebuilders.clearlag.modules.CommandModule
/*    */ {
/*    */   public UnloadChunksCmd()
/*    */   {
/* 15 */     this.name = "unloadchunks";
/* 16 */     this.desc = "(Unloads unused chunks)";
/*    */   }
/*    */   
/*    */ 
/*    */   protected void run(CommandSender sender, String[] args)
/*    */   {
/* 22 */     int chunkcount = 0;
/*    */     
/* 24 */     PluginManager pluginManager = Bukkit.getPluginManager();
/*    */     
/* 26 */     for (World world : Bukkit.getServer().getWorlds())
/*    */     {
/* 28 */       for (Chunk chunk : world.getLoadedChunks())
/*    */       {
/* 30 */         ChunkUnloadEvent event = new ChunkUnloadEvent(chunk);
/*    */         
/* 32 */         pluginManager.callEvent(event);
/*    */         
/* 34 */         if ((!event.isCancelled()) && (chunk.unload(true, true))) {
/* 35 */           chunkcount++;
/*    */         }
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 41 */     Util.msg("§3" + chunkcount + " §bchunks have been unloaded!", sender);
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\commands\UnloadChunksCmd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */