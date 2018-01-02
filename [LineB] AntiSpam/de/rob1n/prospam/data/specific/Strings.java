/*    */ package de.rob1n.prospam.data.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.data.ConfigFile;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ 
/*    */ public class Strings extends ConfigFile
/*    */ {
/*    */   public static final String STRINGS_FILTER_LINES_SIMILAR = "filter-lines-similar";
/*    */   public static final String STRINGS_FILTER_LINES_LOCKED = "filter-lines-locked";
/*    */   public static final String STRINGS_FILTER_URLS_BLOCKED = "filter-urls-blocked";
/*    */   public static final String STRINGS_BLACKKLIST_LINES_IGNORED = "blacklist-lines-ignored";
/*    */   public static final String STRINGS_TRIGGER_INFORMATION = "trigger-information";
/*    */   public String filter_lines_similar;
/*    */   public String filter_lines_locked;
/*    */   public String filter_urls_blocked;
/*    */   public String blacklist_lines_ignored;
/*    */   public String trigger_information;
/*    */   
/*    */   public Strings(ProSpam plugin, String fileName)
/*    */   {
/* 22 */     super(plugin, fileName);
/*    */   }
/*    */   
/*    */ 
/*    */   protected void loadSettings()
/*    */   {
/* 28 */     this.filter_lines_similar = getConfig().getString("filter-lines-similar", "Your message has been ignored");
/* 29 */     this.filter_lines_locked = getConfig().getString("filter-lines-locked", "Please wait a moment till you send your next message");
/* 30 */     this.filter_urls_blocked = getConfig().getString("filter-urls-blocked", "<url blocked>");
/* 31 */     this.blacklist_lines_ignored = getConfig().getString("blacklist-lines-ignored", "Your message has been ignored due to a bad word");
/* 32 */     this.trigger_information = getConfig().getString("trigger-information", "&7{0} triggered a filter ({1})");
/*    */   }
/*    */   
/*    */ 
/*    */   protected void saveSettings()
/*    */   {
/* 38 */     getConfig().set("filter-lines-similar", this.filter_lines_similar);
/* 39 */     getConfig().set("filter-lines-locked", this.filter_lines_locked);
/* 40 */     getConfig().set("filter-urls-blocked", this.filter_urls_blocked);
/* 41 */     getConfig().set("blacklist-lines-ignored", this.blacklist_lines_ignored);
/* 42 */     getConfig().set("trigger-information", this.trigger_information);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\data\specific\Strings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */