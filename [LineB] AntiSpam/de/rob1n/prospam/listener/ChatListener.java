/*    */ package de.rob1n.prospam.listener;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.chatter.ChatMessage;
/*    */ import de.rob1n.prospam.chatter.Chatter;
/*    */ import de.rob1n.prospam.chatter.ChatterHandler;
/*    */ import de.rob1n.prospam.data.DataHandler;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*    */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*    */ 
/*    */ public class ChatListener implements Listener
/*    */ {
/*    */   private final ProSpam plugin;
/*    */   private final Settings settings;
/*    */   
/*    */   public ChatListener(ProSpam plugin)
/*    */   {
/* 22 */     this.plugin = plugin;
/* 23 */     this.settings = plugin.getDataHandler().getSettings();
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onPlayerChat(AsyncPlayerChatEvent event)
/*    */   {
/* 29 */     String filtered = filterText(event.getPlayer(), event.getMessage());
/*    */     
/* 31 */     if (filtered != null)
/*    */     {
/* 33 */       event.setMessage(filtered);
/*    */     }
/*    */     else
/*    */     {
/* 37 */       event.setCancelled(true);
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler(ignoreCancelled=true)
/*    */   public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event)
/*    */   {
/* 44 */     String txt = event.getMessage();
/*    */     
/* 46 */     for (String cmd : this.settings.filter_commands)
/*    */     {
/* 48 */       String[] cmdStr = txt.split(" ");
/*    */       
/* 50 */       if ((cmdStr.length >= 1) && (cmdStr[0].equalsIgnoreCase(cmd)))
/*    */       {
/* 52 */         String filtered = filterText(event.getPlayer(), txt.substring(cmd.length()));
/*    */         
/* 54 */         if (filtered != null)
/*    */         {
/* 56 */           event.setMessage(cmd + filtered);
/*    */         }
/*    */         else
/*    */         {
/* 60 */           event.setCancelled(true);
/*    */         }
/*    */         
/* 63 */         return;
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private String filterText(Player player, String txt)
/*    */   {
/* 76 */     if (this.settings.enabled)
/*    */     {
/* 78 */       Chatter chatter = ChatterHandler.getChatter(player.getUniqueId());
/* 79 */       String filteredTxt = this.plugin.getFilterHandler().execute(player, txt);
/*    */       
/* 81 */       if (filteredTxt != null)
/*    */       {
/*    */ 
/* 84 */         chatter.addMessage(new ChatMessage(filteredTxt));
/*    */         
/* 86 */         return filteredTxt;
/*    */       }
/*    */       
/* 89 */       return null;
/*    */     }
/*    */     
/*    */ 
/* 93 */     return txt;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\listener\ChatListener.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */