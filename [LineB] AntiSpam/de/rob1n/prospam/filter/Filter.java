/*    */ package de.rob1n.prospam.filter;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.chatter.Chatter;
/*    */ import de.rob1n.prospam.data.DataHandler;
/*    */ import de.rob1n.prospam.data.StringList;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import de.rob1n.prospam.data.specific.Strings;
/*    */ import de.rob1n.prospam.data.specific.Whitelist;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public abstract class Filter
/*    */ {
/*    */   protected final ProSpam plugin;
/*    */   protected final DataHandler dataHandler;
/*    */   protected final Settings settings;
/*    */   protected final Strings strings;
/*    */   protected final StringList whitelist;
/*    */   
/*    */   public Filter(ProSpam plugin)
/*    */   {
/* 23 */     this.plugin = plugin;
/*    */     
/* 25 */     this.dataHandler = plugin.getDataHandler();
/* 26 */     this.settings = this.dataHandler.getSettings();
/* 27 */     this.strings = this.dataHandler.getStrings();
/* 28 */     this.whitelist = new StringList(this.dataHandler.getWhitelist().whitelist);
/*    */   }
/*    */   
/*    */   public String execute(Chatter chatter, String message) throws IllegalArgumentException
/*    */   {
/* 33 */     if ((this.plugin == null) || (message == null)) {
/* 34 */       throw new IllegalArgumentException("There was a problem executing the filter");
/*    */     }
/* 36 */     return executeFilter(chatter, message);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected abstract String executeFilter(Chatter paramChatter, String paramString);
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   protected boolean isPlayerName(Player[] playersOnline, String word)
/*    */   {
/* 49 */     for (Player p : playersOnline)
/*    */     {
/*    */ 
/* 52 */       if (p.getName().equalsIgnoreCase(word)) {
/* 53 */         return true;
/*    */       }
/*    */     }
/* 56 */     return false;
/*    */   }
/*    */   
/*    */   protected boolean isWhitelisted(String word)
/*    */   {
/* 61 */     return (this.settings.whitelist_enabled) && (this.whitelist.containsIgnoreCase(word));
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\filter\Filter.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */