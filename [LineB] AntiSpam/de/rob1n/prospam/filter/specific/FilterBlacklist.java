/*    */ package de.rob1n.prospam.filter.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.chatter.Chatter;
/*    */ import de.rob1n.prospam.data.DataHandler;
/*    */ import de.rob1n.prospam.data.specific.Blacklist;
/*    */ import de.rob1n.prospam.filter.Filter;
/*    */ import java.util.List;
/*    */ import java.util.Random;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class FilterBlacklist
/*    */   extends Filter
/*    */ {
/*    */   private final Blacklist blacklist;
/*    */   private final List<String> blacklistStrings;
/* 18 */   private final Random rand = new Random();
/*    */   
/*    */   public FilterBlacklist(ProSpam plugin)
/*    */   {
/* 22 */     super(plugin);
/*    */     
/* 24 */     this.blacklist = this.dataHandler.getBlacklist();
/* 25 */     this.blacklistStrings = this.blacklist.blacklist;
/*    */   }
/*    */   
/*    */ 
/*    */   protected String executeFilter(Chatter chatter, String message)
/*    */   {
/* 31 */     String cleanedMessage = message;
/*    */     
/*    */     try
/*    */     {
/* 35 */       for (String blacklistEntry : this.blacklistStrings)
/*    */       {
/* 37 */         boolean hasParameterIgnoreLine = blacklistEntry.contains("{i}");
/* 38 */         boolean hasParameterExact = blacklistEntry.contains("{e}");
/*    */         
/* 40 */         if (hasParameterIgnoreLine) {
/* 41 */           blacklistEntry = blacklistEntry.replace("{i}", "");
/*    */         }
/* 43 */         if (hasParameterExact) {
/* 44 */           blacklistEntry = blacklistEntry.replace("{e}", "");
/*    */         }
/* 46 */         if (message.toLowerCase().contains(blacklistEntry.toLowerCase()))
/*    */         {
/* 48 */           if (hasParameterExact)
/*    */           {
/* 50 */             String msgBefore = cleanedMessage;
/*    */             
/* 52 */             cleanedMessage = cleanedMessage.replaceAll("(?i)\\b" + Pattern.quote(blacklistEntry) + "\\b", Matcher.quoteReplacement(getCensorChars(blacklistEntry)));
/*    */             
/* 54 */             if ((hasParameterIgnoreLine) && (!cleanedMessage.equalsIgnoreCase(msgBefore))) {
/* 55 */               return null;
/*    */             }
/*    */           }
/*    */           else {
/* 59 */             if (hasParameterIgnoreLine) {
/* 60 */               return null;
/*    */             }
/* 62 */             cleanedMessage = cleanedMessage.replaceAll("(?i)" + Pattern.quote(blacklistEntry), Matcher.quoteReplacement(getCensorChars(blacklistEntry)));
/*    */           }
/*    */         }
/*    */       }
/*    */       
/* 67 */       return cleanedMessage;
/*    */     }
/*    */     catch (NullPointerException ignored) {}
/*    */     
/* 71 */     return message;
/*    */   }
/*    */   
/*    */   private String getCensorChars(String word)
/*    */   {
/* 76 */     String coverChars = this.blacklist.cover_chars;
/* 77 */     StringBuilder sb = new StringBuilder(word.length());
/*    */     
/* 79 */     if (coverChars.trim().isEmpty()) {
/* 80 */       coverChars = "*";
/*    */     }
/* 82 */     for (int i = 0; i < word.length(); i++)
/*    */     {
/* 84 */       if ((i == 0) && (word.length() > 1)) {
/* 85 */         sb.append(word.charAt(0));
/*    */       }
/*    */       else {
/* 88 */         char randomChar = coverChars.charAt(this.rand.nextInt(coverChars.length()));
/* 89 */         sb.append(randomChar);
/*    */       }
/*    */     }
/*    */     
/* 93 */     return sb.toString();
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\filter\specific\FilterBlacklist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */