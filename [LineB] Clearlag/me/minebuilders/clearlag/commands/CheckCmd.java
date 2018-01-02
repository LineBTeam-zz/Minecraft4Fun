/*     */ package me.minebuilders.clearlag.commands;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import me.minebuilders.clearlag.RAMUtil;
/*     */ import me.minebuilders.clearlag.Util;
/*     */ import me.minebuilders.clearlag.modules.CommandModule;
/*     */ import me.minebuilders.clearlag.tasks.TPSTask;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Chunk;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.CreatureSpawner;
/*     */ import org.bukkit.block.Hopper;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Creature;
/*     */ import org.bukkit.entity.Monster;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ 
/*     */ public class CheckCmd extends CommandModule
/*     */ {
/*     */   @me.minebuilders.clearlag.annotations.AutoWire
/*     */   private TPSTask tpsTask;
/*     */   
/*     */   public CheckCmd()
/*     */   {
/*  28 */     this.name = "check";
/*  29 */     this.desc = "(Counts entities in your world(s))";
/*  30 */     this.usage = "[world1, world2...]";
/*     */   }
/*     */   
/*     */ 
/*     */   protected void run(CommandSender sender, String[] args)
/*     */   {
/*     */     List<World> worlds;
/*     */     
/*  38 */     if (args.length > 0)
/*     */     {
/*  40 */       List<World> worlds = new ArrayList(args.length);
/*     */       
/*  42 */       for (String arg : args) {
/*  43 */         World world = Bukkit.getWorld(arg);
/*     */         
/*  45 */         if (world != null) {
/*  46 */           worlds.add(world);
/*     */         } else {
/*  48 */           sender.sendMessage(ChatColor.RED + "Invalid world specified: " + arg);
/*  49 */           return;
/*     */         }
/*     */       }
/*     */     } else {
/*  53 */       worlds = Bukkit.getWorlds();
/*     */     }
/*     */     
/*  56 */     int removed1 = 0;int mobs = 0;int animals = 0;int chunks = 0;int spawners = 0;int activehoppers = 0;int inactivehoppers = 0;int players = 0;
/*     */     
/*  58 */     for (World w : worlds)
/*     */     {
/*  60 */       for (Chunk c : w.getLoadedChunks()) {
/*  61 */         for (org.bukkit.block.BlockState bt : c.getTileEntities()) {
/*  62 */           if ((bt instanceof CreatureSpawner)) {
/*  63 */             spawners++;
/*  64 */           } else if ((bt instanceof Hopper)) {
/*  65 */             if (!isHopperEmpty((Hopper)bt)) {
/*  66 */               activehoppers++;
/*     */             } else {
/*  68 */               inactivehoppers++;
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*  73 */         for (org.bukkit.entity.Entity e : c.getEntities()) {
/*  74 */           if ((e instanceof Monster)) { mobs++;
/*  75 */           } else if ((e instanceof Player)) { players++;
/*  76 */           } else if ((e instanceof Creature)) { animals++;
/*  77 */           } else if ((e instanceof org.bukkit.entity.Item)) removed1++;
/*     */         }
/*  79 */         chunks++;
/*     */       }
/*     */     }
/*     */     
/*  83 */     Util.scm("&4*&3&m                          &8(&a&lServer Status&8)&3&m                           &4*", sender);
/*  84 */     sender.sendMessage(ChatColor.DARK_AQUA + "   Items on the ground: " + ChatColor.AQUA + removed1);
/*  85 */     sender.sendMessage(ChatColor.DARK_AQUA + "   Mobs alive: " + ChatColor.AQUA + mobs);
/*  86 */     sender.sendMessage(ChatColor.DARK_AQUA + "   Frienly-Mobs alive: " + ChatColor.AQUA + animals);
/*  87 */     sender.sendMessage(ChatColor.DARK_AQUA + "   Players alive: " + ChatColor.AQUA + players);
/*  88 */     sender.sendMessage(ChatColor.DARK_AQUA + "   Chunks loaded: " + ChatColor.AQUA + chunks);
/*  89 */     sender.sendMessage(ChatColor.DARK_AQUA + "   Active hoppers: " + ChatColor.AQUA + activehoppers);
/*  90 */     sender.sendMessage(ChatColor.DARK_AQUA + "   Idle hoppers: " + ChatColor.AQUA + inactivehoppers);
/*  91 */     sender.sendMessage(ChatColor.DARK_AQUA + "   Active mob spawners: " + ChatColor.AQUA + spawners);
/*  92 */     sender.sendMessage(ChatColor.DARK_AQUA + "   Current TPS: " + ChatColor.AQUA + this.tpsTask.getStringTPS());
/*  93 */     sender.sendMessage(ChatColor.DARK_AQUA + "   RAM Usage: " + ChatColor.AQUA + Long.toString(RAMUtil.getUsedMemory()) + "§7/§b" + Long.toString(RAMUtil.getMaxMemory()) + "MB");
/*  94 */     Util.scm("&4*&3&m                                                                             &4*", sender);
/*     */   }
/*     */   
/*     */   private boolean isHopperEmpty(Hopper hop) {
/*  98 */     for (org.bukkit.inventory.ItemStack it : hop.getInventory().getContents()) {
/*  99 */       if (it != null) return false;
/*     */     }
/* 101 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\commands\CheckCmd.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */