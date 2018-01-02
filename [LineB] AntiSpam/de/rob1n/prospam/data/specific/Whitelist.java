/*    */ package de.rob1n.prospam.data.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.data.ConfigFile;
/*    */ import java.util.List;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ 
/*    */ public class Whitelist
/*    */   extends ConfigFile
/*    */ {
/*    */   public static final String OPTION_WHITELIST = "whitelist";
/*    */   public List<String> whitelist;
/*    */   
/*    */   public Whitelist(ProSpam plugin, String fileName)
/*    */   {
/* 16 */     super(plugin, fileName);
/*    */   }
/*    */   
/*    */ 
/*    */   protected void loadSettings()
/*    */   {
/* 22 */     this.whitelist = getConfig().getStringList("whitelist");
/*    */   }
/*    */   
/*    */ 
/*    */   protected void saveSettings()
/*    */   {
/* 28 */     getConfig().set("whitelist", this.whitelist);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\data\specific\Whitelist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */