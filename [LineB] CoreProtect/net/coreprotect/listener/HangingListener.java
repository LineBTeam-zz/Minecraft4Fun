/*     */ package net.coreprotect.listener;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.Statement;
/*     */ import java.util.Map;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.consumer.Queue;
/*     */ import net.coreprotect.database.Database;
/*     */ import net.coreprotect.database.Lookup;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.bukkit.Art;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockFace;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Hanging;
/*     */ import org.bukkit.entity.ItemFrame;
/*     */ import org.bukkit.entity.Painting;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.hanging.HangingBreakByEntityEvent;
/*     */ import org.bukkit.event.hanging.HangingBreakEvent;
/*     */ import org.bukkit.event.hanging.HangingBreakEvent.RemoveCause;
/*     */ import org.bukkit.event.hanging.HangingPlaceEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class HangingListener
/*     */   extends Queue
/*     */   implements Listener
/*     */ {
/*     */   protected static void inspectItemFrame(Block block, final Player player)
/*     */   {
/*  60 */     Runnable runnable = new Runnable()
/*     */     {
/*     */       public void run()
/*     */       {
/*     */         try
/*     */         {
/*  36 */           Connection connection = Database.getConnection(false);
/*  37 */           if (connection != null) {
/*  38 */             Statement statement = connection.createStatement();
/*  39 */             String blockdata = Lookup.block_lookup(statement, this.val$block, player.getName(), 0, 1, 7);
/*  40 */             if (blockdata.contains("\n")) {
/*  41 */               for (String b : blockdata.split("\n")) {
/*  42 */                 player.sendMessage(b);
/*     */               }
/*     */               
/*  45 */             } else if (blockdata.length() > 0) {
/*  46 */               player.sendMessage(blockdata);
/*     */             }
/*  48 */             statement.close();
/*  49 */             connection.close();
/*     */           }
/*     */           else {
/*  52 */             player.sendMessage("§3CoreProtect §f- Database busy. Please try again later.");
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/*  56 */           e.printStackTrace();
/*     */         }
/*     */         
/*     */       }
/*  60 */     };
/*  61 */     Thread thread = new Thread(runnable);
/*  62 */     thread.start();
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onHangingBreak(HangingBreakEvent event) {
/*  67 */     HangingBreakEvent.RemoveCause cause = event.getCause();
/*  68 */     Entity entity = event.getEntity();
/*  69 */     Block block_event = event.getEntity().getLocation().getBlock();
/*  70 */     if ((((entity instanceof ItemFrame)) || ((entity instanceof Painting))) && (
/*  71 */       (cause.equals(HangingBreakEvent.RemoveCause.EXPLOSION)) || (cause.equals(HangingBreakEvent.RemoveCause.PHYSICS)) || (cause.equals(HangingBreakEvent.RemoveCause.OBSTRUCTION)))) {
/*  72 */       String p = "#explosion";
/*  73 */       Block ab = null;
/*  74 */       if (cause.equals(HangingBreakEvent.RemoveCause.PHYSICS)) {
/*  75 */         p = "#physics";
/*     */       }
/*  77 */       else if (cause.equals(HangingBreakEvent.RemoveCause.OBSTRUCTION)) {
/*  78 */         p = "#obstruction";
/*     */       }
/*  80 */       if (!cause.equals(HangingBreakEvent.RemoveCause.EXPLOSION)) {
/*  81 */         Hanging he = (Hanging)entity;
/*  82 */         BlockFace attached = he.getAttachedFace();
/*  83 */         ab = he.getLocation().getBlock().getRelative(attached);
/*     */       }
/*  85 */       Material t = Material.AIR;
/*  86 */       int d = 0;
/*  87 */       if ((entity instanceof ItemFrame)) {
/*  88 */         t = Material.ITEM_FRAME;
/*  89 */         ItemFrame itemframe = (ItemFrame)entity;
/*  90 */         if (itemframe.getItem() != null) {
/*  91 */           d = Functions.block_id(itemframe.getItem().getType());
/*     */         }
/*     */       }
/*  94 */       else if ((entity instanceof Painting)) {
/*  95 */         t = Material.PAINTING;
/*  96 */         Painting painting = (Painting)entity;
/*  97 */         d = Functions.getArtId(painting.getArt().toString(), true);
/*     */       }
/*  99 */       if ((!event.isCancelled()) && (Functions.checkConfig(block_event.getWorld(), "natural-break") == 1)) {
/* 100 */         Queue.queueNaturalBlockBreak(p, block_event.getState(), ab, t, d);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   protected void onHangingBreakByEntity(HangingBreakByEntityEvent event)
/*     */   {
/* 108 */     Entity entity = event.getEntity();
/* 109 */     Entity remover = event.getRemover();
/* 110 */     Block block_event = event.getEntity().getLocation().getBlock();
/*     */     
/* 112 */     boolean inspecting = false;
/* 113 */     if ((event.getRemover() instanceof Player)) {
/* 114 */       Player player = (Player)event.getRemover();
/* 115 */       if ((Config.inspecting.get(player.getName()) != null) && 
/* 116 */         (((Boolean)Config.inspecting.get(player.getName())).booleanValue()))
/*     */       {
/* 118 */         inspectItemFrame(block_event, player);
/* 119 */         event.setCancelled(true);
/* 120 */         inspecting = true;
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 125 */     if (((entity instanceof ItemFrame)) || ((entity instanceof Painting))) {
/* 126 */       String p = "#entity";
/* 127 */       if (remover != null) {
/* 128 */         if ((remover instanceof Player)) {
/* 129 */           Player player = (Player)remover;
/* 130 */           p = player.getName();
/*     */         }
/* 132 */         else if (remover.getType() != null) {
/* 133 */           p = "#" + remover.getType().name().toLowerCase();
/*     */         }
/*     */       }
/* 136 */       Material t = Material.AIR;
/* 137 */       int d = 0;
/* 138 */       if ((entity instanceof ItemFrame)) {
/* 139 */         t = Material.ITEM_FRAME;
/* 140 */         ItemFrame itemframe = (ItemFrame)entity;
/* 141 */         if (itemframe.getItem() != null) {
/* 142 */           d = Functions.block_id(itemframe.getItem().getType());
/*     */         }
/*     */       }
/* 145 */       else if ((entity instanceof Painting)) {
/* 146 */         t = Material.PAINTING;
/* 147 */         Painting painting = (Painting)entity;
/* 148 */         d = Functions.getArtId(painting.getArt().toString(), true);
/*     */       }
/*     */       
/* 151 */       if ((!event.isCancelled()) && (Functions.checkConfig(block_event.getWorld(), "block-break") == 1) && (!inspecting)) {
/* 152 */         Queue.queueBlockBreak(p, block_event.getState(), t, d);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.HIGHEST)
/*     */   protected void onHangingPlace(HangingPlaceEvent event) {
/* 159 */     Entity entity = event.getEntity();
/* 160 */     Player player = event.getPlayer();
/* 161 */     if (((entity instanceof ItemFrame)) || ((entity instanceof Painting))) {
/* 162 */       Block block_event = event.getEntity().getLocation().getBlock();
/* 163 */       Material t = Material.AIR;
/* 164 */       int d = 0;
/* 165 */       if ((entity instanceof ItemFrame)) {
/* 166 */         t = Material.ITEM_FRAME;
/* 167 */         d = 0;
/*     */       }
/* 169 */       else if ((entity instanceof Painting)) {
/* 170 */         t = Material.PAINTING;
/* 171 */         Painting painting = (Painting)entity;
/* 172 */         d = Functions.getArtId(painting.getArt().toString(), true);
/*     */       }
/*     */       
/* 175 */       int inspect = 0;
/* 176 */       if ((Config.inspecting.get(player) != null) && 
/* 177 */         (((Boolean)Config.inspecting.get(player)).booleanValue())) {
/* 178 */         inspect = 1;
/* 179 */         event.setCancelled(true);
/*     */       }
/*     */       
/* 182 */       if ((!event.isCancelled()) && (Functions.checkConfig(block_event.getWorld(), "block-place") == 1) && (inspect == 0)) {
/* 183 */         Queue.queueBlockPlace(player.getName(), block_event.getState(), t, d);
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\listener\HangingListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */