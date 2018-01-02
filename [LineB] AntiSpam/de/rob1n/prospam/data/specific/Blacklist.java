/*    */ package de.rob1n.prospam.data.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.data.ConfigFile;
/*    */ import java.util.List;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ 
/*    */ public class Blacklist
/*    */   extends ConfigFile
/*    */ {
/*    */   public static final String OPTION_COVER_CHARS = "cover-chars";
/*    */   public static final String OPTION_BLACKLIST = "blacklist";
/*    */   public String cover_chars;
/*    */   public List<String> blacklist;
/*    */   
/*    */   public Blacklist(ProSpam plugin, String fileName)
/*    */   {
/* 18 */     super(plugin, fileName);
/*    */   }
/*    */   
/*    */ 
/*    */   protected void loadSettings()
/*    */   {
/* 24 */     this.cover_chars = getConfig().getString("cover-chars", "*§$&%#");
/* 25 */     this.blacklist = getConfig().getStringList("blacklist");
/*    */   }
/*    */   
/*    */ 
/*    */   protected void saveSettings()
/*    */   {
/* 31 */     getConfig().set("cover-chars", this.cover_chars);
/* 32 */     getConfig().set("blacklist", this.blacklist);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\data\specific\Blacklist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */