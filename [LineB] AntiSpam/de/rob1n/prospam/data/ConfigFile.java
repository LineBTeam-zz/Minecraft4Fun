/*    */ package de.rob1n.prospam.data;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.util.logging.Level;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ConfigFile
/*    */ {
/*    */   protected final ProSpam plugin;
/*    */   private final String fileName;
/*    */   private final File configFile;
/* 19 */   private FileConfiguration fileConfiguration = null;
/*    */   
/*    */   public ConfigFile(ProSpam plugin, String fileName)
/*    */   {
/* 23 */     this.plugin = plugin;
/* 24 */     this.fileName = fileName;
/*    */     
/* 26 */     File dataFolder = plugin.getDataFolder();
/* 27 */     if (dataFolder == null) {
/* 28 */       throw new IllegalStateException("Data Folder not found");
/*    */     }
/* 30 */     this.configFile = new File(plugin.getDataFolder(), fileName);
/*    */     
/* 32 */     loadSettings();
/*    */     
/*    */ 
/* 35 */     save();
/*    */   }
/*    */   
/*    */   public FileConfiguration getConfig()
/*    */   {
/* 40 */     if (this.fileConfiguration == null) {
/* 41 */       load();
/*    */     }
/* 43 */     return this.fileConfiguration;
/*    */   }
/*    */   
/*    */   public void load()
/*    */   {
/*    */     try
/*    */     {
/* 50 */       this.fileConfiguration = YamlConfiguration.loadConfiguration(this.configFile);
/*    */       
/*    */ 
/* 53 */       InputStream configStream = this.plugin.getResource(this.fileName);
/* 54 */       if (configStream != null)
/*    */       {
/* 56 */         YamlConfiguration defaultConfig = YamlConfiguration.loadConfiguration(configStream);
/* 57 */         this.fileConfiguration.setDefaults(defaultConfig);
/*    */       }
/*    */       
/* 60 */       loadSettings();
/*    */     }
/*    */     catch (IllegalArgumentException e)
/*    */     {
/* 64 */       ProSpam.log(Level.SEVERE, "Could not load Config file");
/*    */     }
/*    */   }
/*    */   
/*    */   protected abstract void loadSettings();
/*    */   
/*    */   protected abstract void saveSettings();
/*    */   
/*    */   public boolean save() {
/* 73 */     if (this.fileConfiguration != null)
/*    */     {
/* 75 */       saveSettings();
/*    */       
/*    */       try
/*    */       {
/* 79 */         getConfig().save(this.configFile);
/* 80 */         return true;
/*    */       }
/*    */       catch (IOException e)
/*    */       {
/* 84 */         ProSpam.log(Level.SEVERE, "Could not save Config file");
/*    */       }
/*    */     }
/*    */     
/* 88 */     return false;
/*    */   }
/*    */   
/*    */   public static String replaceColorCodes(String str)
/*    */   {
/* 93 */     for (ChatColor cc : )
/*    */     {
/* 95 */       str = str.replace("&" + cc.name(), cc.toString());
/*    */     }
/*    */     
/* 98 */     return str;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\data\ConfigFile.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */