/*    */ package de.rob1n.prospam.filter.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.chatter.Chatter;
/*    */ import de.rob1n.prospam.filter.Filter;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class FilterChars
/*    */   extends Filter
/*    */ {
/*    */   public FilterChars(ProSpam plugin)
/*    */   {
/* 15 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   protected String executeFilter(Chatter chatter, String message)
/*    */   {
/* 21 */     Player[] onlinePlayers = this.plugin.getServer().getOnlinePlayers();
/*    */     
/* 23 */     String[] wordArr = message.split(" ");
/* 24 */     int wordArrLength = wordArr.length;
/*    */     
/* 26 */     for (int i = 0; i < wordArrLength; i++)
/*    */     {
/*    */ 
/* 29 */       if (!isPlayerName(onlinePlayers, wordArr[i]))
/*    */       {
/*    */ 
/*    */ 
/* 33 */         if (!isWhitelisted(wordArr[i]))
/*    */         {
/*    */ 
/*    */ 
/* 37 */           if ((!StringUtils.isNumeric(wordArr[i])) || (wordArr[i].length() > 10))
/*    */           {
/*    */ 
/*    */ 
/* 41 */             wordArr[i] = wordArr[i].replaceAll("(?i)(.)\\1\\1+", "$1$1$1"); } }
/*    */       }
/*    */     }
/* 44 */     return StringUtils.join(wordArr, " ");
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\filter\specific\FilterChars.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */