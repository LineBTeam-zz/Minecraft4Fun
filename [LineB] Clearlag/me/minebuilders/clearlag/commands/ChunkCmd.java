/*    */ package me.minebuilders.clearlag.commands;
/*    */ 
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.modules.CommandModule;
/*    */ import org.bukkit.Chunk;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class ChunkCmd extends CommandModule
/*    */ {
/*    */   public ChunkCmd()
/*    */   {
/* 13 */     this.name = "chunk";
/* 14 */     this.usage = "[list-size]";
/* 15 */     this.desc = "(Finds laggy chunks)";
/*    */   }
/*    */   
/*    */ 
/*    */   protected void run(CommandSender sender, String[] args)
/*    */   {
/* 21 */     int size = 5;
/*    */     
/* 23 */     if ((args.length >= 1) && (Util.isInteger(args[0]))) {
/* 24 */       size = Integer.parseInt(args[0]);
/*    */     }
/*    */     
/* 27 */     Integer[] sizes = new Integer[size];
/* 28 */     Chunk[] chunks = new Chunk[size];
/*    */     
/* 30 */     for (World world : org.bukkit.Bukkit.getWorlds()) {
/* 31 */       for (Chunk c : world.getLoadedChunks())
/*    */       {
/* 33 */         int amount = c.getEntities().length;
/*    */         
/* 35 */         for (int i = 0; i < size; i++)
/*    */         {
/* 37 */           if ((sizes[i] == null) || (sizes[i].intValue() < amount))
/*    */           {
/* 39 */             Util.shiftRight(chunks, i);
/* 40 */             Util.shiftRight(sizes, i);
/*    */             
/* 42 */             chunks[i] = c;
/* 43 */             sizes[i] = Integer.valueOf(amount);
/*    */             
/* 45 */             break;
/*    */           }
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 51 */     sender.sendMessage("§7§m                           §7( §bLargest Chunks §7)§m                           ");
/*    */     
/* 53 */     for (int i = 0; i < sizes.length; i++) {
/* 54 */       Chunk c = chunks[i];
/* 55 */       sender.sendMessage("§4" + (i + 1) + "§7) §3World: §b" + c.getWorld().getName() + "§7, §3x: §b" + c.getX() + "§7, §3z: §b" + c.getZ() + "   §3Entities: §b" + sizes[i]);
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\commands\ChunkCmd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */