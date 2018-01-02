/*    */ package de.rob1n.prospam.filter.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.chatter.ChatMessage;
/*    */ import de.rob1n.prospam.chatter.Chatter;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import de.rob1n.prospam.filter.Filter;
/*    */ import java.util.Date;
/*    */ import java.util.List;
/*    */ 
/*    */ public class FilterSimilar
/*    */   extends Filter
/*    */ {
/*    */   public FilterSimilar(ProSpam plugin)
/*    */   {
/* 16 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   protected String executeFilter(Chatter chatter, String message)
/*    */   {
/* 22 */     long timeNow = new Date().getTime();
/*    */     
/* 24 */     List<ChatMessage> previousMessages = chatter.getMessages();
/* 25 */     for (ChatMessage previousMessage : previousMessages)
/*    */     {
/* 27 */       if ((timeNow - previousMessage.timeSubmitted) / 1000L <= this.settings.filter_lines_similar)
/*    */       {
/*    */ 
/* 30 */         if (LevenshteinDistance.computeLevenshteinDistance(message, previousMessage.message) <= message.length() / 4 + 1) {
/* 31 */           return null;
/*    */         }
/*    */       }
/*    */     }
/* 35 */     return message;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\filter\specific\FilterSimilar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */