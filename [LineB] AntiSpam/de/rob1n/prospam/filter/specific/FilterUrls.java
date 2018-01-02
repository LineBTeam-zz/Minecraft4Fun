/*    */ package de.rob1n.prospam.filter.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.chatter.Chatter;
/*    */ import de.rob1n.prospam.filter.Filter;
/*    */ import org.apache.commons.lang.StringUtils;
/*    */ import org.apache.commons.validator.routines.DomainValidator;
/*    */ import org.apache.commons.validator.routines.InetAddressValidator;
/*    */ import org.apache.commons.validator.routines.UrlValidator;
/*    */ 
/*    */ public class FilterUrls extends Filter
/*    */ {
/* 13 */   private final InetAddressValidator inetValidator = InetAddressValidator.getInstance();
/* 14 */   private final DomainValidator domainValidator = DomainValidator.getInstance();
/* 15 */   private final UrlValidator urlValidator = UrlValidator.getInstance();
/*    */   
/*    */   public FilterUrls(ProSpam plugin)
/*    */   {
/* 19 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   protected String executeFilter(Chatter chatter, String message)
/*    */   {
/* 25 */     String[] wordArr = message.split(" ");
/* 26 */     int wordArrLength = wordArr.length;
/*    */     
/* 28 */     for (int i = 0; i < wordArrLength; i++)
/*    */     {
/*    */ 
/* 31 */       if (!isWhitelisted(wordArr[i]))
/*    */       {
/*    */ 
/* 34 */         if (isURL(wordArr[i]))
/* 35 */           wordArr[i] = this.strings.filter_urls_blocked;
/*    */       }
/*    */     }
/* 38 */     return StringUtils.join(wordArr, " ");
/*    */   }
/*    */   
/*    */   private boolean isURL(String word)
/*    */   {
/* 43 */     return (this.inetValidator.isValid(word)) || (this.domainValidator.isValid(word)) || (this.urlValidator.isValid(word));
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\filter\specific\FilterUrls.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */