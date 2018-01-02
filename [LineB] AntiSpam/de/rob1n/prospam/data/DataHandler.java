/*    */ package de.rob1n.prospam.data;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.data.specific.Blacklist;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import de.rob1n.prospam.data.specific.Strings;
/*    */ import de.rob1n.prospam.data.specific.Whitelist;
/*    */ 
/*    */ public class DataHandler
/*    */ {
/*    */   private final Settings settings;
/*    */   private final Strings strings;
/*    */   private final Whitelist whitelist;
/*    */   private final Blacklist blacklist;
/*    */   
/*    */   public DataHandler(ProSpam plugin)
/*    */   {
/* 18 */     this.settings = new Settings(plugin, "config.yml");
/* 19 */     this.strings = new Strings(plugin, "strings.yml");
/* 20 */     this.whitelist = new Whitelist(plugin, "whitelist.yml");
/* 21 */     this.blacklist = new Blacklist(plugin, "blacklist.yml");
/*    */   }
/*    */   
/*    */   public Settings getSettings()
/*    */   {
/* 26 */     return this.settings;
/*    */   }
/*    */   
/*    */   public Strings getStrings()
/*    */   {
/* 31 */     return this.strings;
/*    */   }
/*    */   
/*    */   public Whitelist getWhitelist()
/*    */   {
/* 36 */     return this.whitelist;
/*    */   }
/*    */   
/*    */   public Blacklist getBlacklist()
/*    */   {
/* 41 */     return this.blacklist;
/*    */   }
/*    */   
/*    */   public void loadAll()
/*    */   {
/* 46 */     this.settings.load();
/* 47 */     this.strings.load();
/* 48 */     this.whitelist.load();
/* 49 */     this.blacklist.load();
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\data\DataHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */