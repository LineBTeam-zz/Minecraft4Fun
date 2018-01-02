/*    */ package de.rob1n.prospam.filter.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.chatter.ChatMessage;
/*    */ import de.rob1n.prospam.chatter.Chatter;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import de.rob1n.prospam.exception.ChatterHasNoMessagesException;
/*    */ import de.rob1n.prospam.filter.Filter;
/*    */ import java.util.Date;
/*    */ 
/*    */ public class FilterFlood extends Filter
/*    */ {
/*    */   public FilterFlood(ProSpam plugin)
/*    */   {
/* 15 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   protected String executeFilter(Chatter chatter, String message)
/*    */   {
/* 21 */     long timeNow = new Date().getTime();
/*    */     
/*    */     try
/*    */     {
/* 25 */       if ((timeNow - chatter.getLastMessage().timeSubmitted) / 1000L <= this.settings.filter_flood_lock) {
/* 26 */         return null;
/*    */       }
/*    */     }
/*    */     catch (ChatterHasNoMessagesException ignored) {}
/* 30 */     return message;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\filter\specific\FilterFlood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */