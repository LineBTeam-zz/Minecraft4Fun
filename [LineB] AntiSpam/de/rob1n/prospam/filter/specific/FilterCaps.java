/*    */ package de.rob1n.prospam.filter.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.chatter.Chatter;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import de.rob1n.prospam.filter.Filter;
/*    */ import java.util.regex.Pattern;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.bukkit.Server;
/*    */ 
/*    */ public class FilterCaps extends Filter
/*    */ {
/*    */   public FilterCaps(ProSpam plugin)
/*    */   {
/* 15 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   protected String executeFilter(Chatter chatter, String message)
/*    */   {
/* 21 */     org.bukkit.entity.Player[] onlinePlayers = this.plugin.getServer().getOnlinePlayers();
/*    */     
/* 23 */     String[] wordArr = message.split(" ");
/* 24 */     int wordArrLength = wordArr.length;
/*    */     
/*    */ 
/* 27 */     if (!tooManyCaps(message)) {
/* 28 */       return message;
/*    */     }
/* 30 */     for (int i = 0; i < wordArrLength; i++)
/*    */     {
/*    */ 
/* 33 */       if (!isPlayerName(onlinePlayers, wordArr[i]))
/*    */       {
/*    */ 
/*    */ 
/* 37 */         if (!isSmilie(wordArr[i]))
/*    */         {
/*    */ 
/*    */ 
/* 41 */           if (!isWhitelisted(wordArr[i]))
/*    */           {
/*    */ 
/* 44 */             if (tooManyCaps(wordArr[i]))
/* 45 */               wordArr[i] = wordArr[i].toLowerCase(); } }
/*    */       }
/*    */     }
/* 48 */     return StringUtils.join(wordArr, " ");
/*    */   }
/*    */   
/*    */   private boolean isSmilie(String txt)
/*    */   {
/* 53 */     return Pattern.matches("(?i)X-?D{1,3}|D-?X{1,3}|:-?D{1,3}|D{1,3}-?:|D{1,3}-?:|:-?P{1,3}|:-?O{1,3}|O{1,3}-?:", txt);
/*    */   }
/*    */   
/*    */   private boolean tooManyCaps(String word)
/*    */   {
/* 58 */     return countCaps(word) >= word.length() / 100.0F * this.settings.filter_caps_max;
/*    */   }
/*    */   
/*    */   private int countCaps(String txt)
/*    */   {
/* 63 */     int counter = 0;
/*    */     
/*    */     try
/*    */     {
/* 67 */       for (int i = 0; i < txt.length(); i++)
/*    */       {
/* 69 */         if (Character.isUpperCase(txt.charAt(i))) {
/* 70 */           counter++;
/*    */         }
/*    */       }
/*    */     }
/*    */     catch (IndexOutOfBoundsException ignored) {}
/* 75 */     return counter;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\filter\specific\FilterCaps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */