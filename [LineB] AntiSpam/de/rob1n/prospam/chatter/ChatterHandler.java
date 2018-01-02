/*    */ package de.rob1n.prospam.chatter;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.exception.NotFoundException;
/*    */ import de.rob1n.prospam.exception.PlayerNotOnlineException;
/*    */ import java.util.HashSet;
/*    */ import java.util.Set;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class ChatterHandler
/*    */ {
/* 14 */   private static final Set<Chatter> chatters = new HashSet();
/*    */   
/*    */   private final ProSpam plugin;
/*    */   
/*    */   public ChatterHandler(ProSpam plugin)
/*    */   {
/* 20 */     this.plugin = plugin;
/*    */   }
/*    */   
/*    */   public static Chatter getChatter(UUID uuid, boolean addIfMissing) throws NotFoundException
/*    */   {
/* 25 */     for (Chatter c : chatters)
/*    */     {
/* 27 */       if ((c.getUUID() != null) && (c.getUUID().equals(uuid))) {
/* 28 */         return c;
/*    */       }
/*    */     }
/* 31 */     if (addIfMissing)
/*    */     {
/*    */ 
/* 34 */       Chatter newChatter = new Chatter(uuid);
/* 35 */       chatters.add(newChatter);
/*    */       
/* 37 */       return newChatter;
/*    */     }
/*    */     
/*    */ 
/* 41 */     throw new NotFoundException("Chatter não encontrado.");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static Chatter getChatter(UUID uuid)
/*    */   {
/*    */     try
/*    */     {
/* 54 */       return getChatter(uuid, true);
/*    */     }
/*    */     catch (NotFoundException e) {}
/*    */     
/*    */ 
/* 59 */     return null;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static Chatter getChatter(String name)
/*    */     throws NotFoundException
/*    */   {
/* 70 */     for (Chatter chatter : chatters)
/*    */     {
/* 72 */       Player player = chatter.getPlayer();
/*    */       
/*    */ 
/* 75 */       if ((player.getName() != null) && (player.getName().equalsIgnoreCase(name))) {
/* 76 */         return chatter;
/*    */       }
/*    */     }
/* 79 */     throw new NotFoundException("Chatter não encontrado.");
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Player getPlayer(Chatter chatter)
/*    */     throws PlayerNotOnlineException
/*    */   {
/* 89 */     Player player = this.plugin.getServer().getPlayer(chatter.getUUID());
/*    */     
/* 91 */     if (player == null) {
/* 92 */       throw new PlayerNotOnlineException();
/*    */     }
/* 94 */     return player;
/*    */   }
/*    */   
/*    */   public Set<Chatter> getChatters()
/*    */   {
/* 99 */     return chatters;
/*    */   }
/*    */ }
