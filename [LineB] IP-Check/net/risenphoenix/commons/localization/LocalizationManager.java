/*     */ package net.risenphoenix.commons.localization;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.logging.Level;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.stores.LocalizationStore;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class LocalizationManager
/*     */ {
/*     */   private Map<String, String> translation;
/*     */   private Map<String, String> defaultTranslation;
/*     */   private String selectedLanguage;
/*     */   private FileConfiguration loadedLanguage;
/*     */   
/*     */   public LocalizationManager(Plugin plugin, String langID)
/*     */   {
/*  51 */     File f = new File(plugin.getDataFolder() + File.separator + langID + ".yml");
/*     */     
/*     */ 
/*  54 */     if (f.exists()) {
/*  55 */       this.selectedLanguage = langID;
/*     */     } else {
/*  57 */       this.selectedLanguage = "en";
/*  58 */       if (!langID.equalsIgnoreCase("en")) { plugin.sendConsoleMessage(Level.WARNING, "Translation Index " + langID + ".yml " + "could not be found. Falling back to Default Translation " + "(English).");
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  64 */     loadTranslation(f);
/*     */   }
/*     */   
/*     */   private final void loadTranslation(File path) {
/*  68 */     if ((!this.selectedLanguage.equals("en")) || (!this.selectedLanguage.isEmpty())) {
/*  69 */       this.loadedLanguage = YamlConfiguration.loadConfiguration(path);
/*  70 */       initializeTranslationIndex();
/*     */     }
/*     */     
/*     */ 
/*  74 */     loadDefaultTranslation();
/*     */   }
/*     */   
/*     */   private final void initializeTranslationIndex() {
/*  78 */     this.translation = new HashMap();
/*     */     
/*  80 */     for (Map.Entry<String, Object> entry : this.loadedLanguage.getValues(true)
/*  81 */       .entrySet()) {
/*  82 */       this.translation.put(entry.getKey(), entry.getValue().toString());
/*     */     }
/*     */   }
/*     */   
/*     */   private final void loadDefaultTranslation() {
/*  87 */     this.defaultTranslation = new HashMap();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  92 */     this.defaultTranslation.put("NO_IMPLEMENT", "Base class does not implement.");
/*     */     
/*  94 */     this.defaultTranslation.put("CMD_REG_ERR", "Failed to register command. Perhaps it is already registered? Command-ID: ");
/*     */     
/*  96 */     this.defaultTranslation.put("NUM_ARGS_ERR", "An incorrect number of arguments was specified.");
/*     */     
/*  98 */     this.defaultTranslation.put("ILL_ARGS_ERR", "An illegal argument was passed into the command.");
/*     */     
/* 100 */     this.defaultTranslation.put("PERMS_ERR", "You do not have permission to execute this command.");
/*     */     
/* 102 */     this.defaultTranslation.put("NO_CONSOLE", "This command cannot be executed from Console.");
/*     */     
/* 104 */     this.defaultTranslation.put("NO_CMD", "An invalid command was specified.");
/*     */     
/* 106 */     this.defaultTranslation.put("CMD_NULL_ERR", "An error occurred while generating a Command Instance. The command has been aborted.");
/*     */     
/* 108 */     this.defaultTranslation.put("BAD_PARSE_SET", "The parse instructions for this Parser have not been determined. Please override method Parser.parseCommand() in your parsing class.");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 113 */     this.defaultTranslation.put("BAD_CFG_SET", "Failed to register Configuration Option. Perhaps it is already specified? Cfg-ID: ");
/*     */     
/*     */ 
/* 116 */     this.defaultTranslation.put("CFG_INIT_ERR", "The Configuration could not be refreshed because it has not yet been initialized.");
/*     */     
/*     */ 
/*     */ 
/* 120 */     this.defaultTranslation.put("DB_CNCT_ERR", "An error occurred while attempting to connect to the database.");
/*     */     
/* 122 */     this.defaultTranslation.put("BAD_DB_DRVR", "The database driver requested could not be found. Requested driver: ");
/*     */     
/* 124 */     this.defaultTranslation.put("DB_OPEN_SUC", "Established connection to database.");
/*     */     
/* 126 */     this.defaultTranslation.put("DB_CLOSE_SUC", "The connection to the database was closed successfully.");
/*     */     
/* 128 */     this.defaultTranslation.put("DB_CLOSE_ERR", "An error occurred while attempting to close the connection to the database. Error: ");
/*     */     
/* 130 */     this.defaultTranslation.put("DB_QUERY_ERR", "An error occurred while attempting to query the database. Error: ");
/*     */     
/* 132 */     this.defaultTranslation.put("DB_QUERY_RETRY", "Retrying Query...");
/* 133 */     this.defaultTranslation.put("DB_PREP_STMT_ERR", "An error occurred while attempting to generate a prepared statement for the database.");
/*     */     
/*     */ 
/* 136 */     this.defaultTranslation.put("DB_DEBUG_ACTIVE", "Database Debugging is active. All SQL queries will be logged as they are received.");
/*     */     
/*     */ 
/* 139 */     this.defaultTranslation.put("BAD_SQL_INPUT", "A parameter passed to the StatementObject is invalid! Valid parameters are those of type String and type Integer.");
/*     */   }
/*     */   
/*     */ 
/*     */   public final String getLocalString(String key)
/*     */   {
/*     */     String value;
/*     */     
/* 147 */     if (this.translation != null) {
/* 148 */       String value = (String)this.translation.get(key);
/*     */       
/*     */ 
/* 151 */       if ((value == null) || (value.equals("null"))) {
/* 152 */         value = (String)this.defaultTranslation.get(key);
/*     */       }
/*     */     } else {
/* 155 */       value = (String)this.defaultTranslation.get(key);
/*     */     }
/*     */     
/* 158 */     if ((value == null) || (value.equals("null")) || (value.isEmpty())) {
/* 159 */       return "Invalid Translation-Key: " + key;
/*     */     }
/* 161 */     return value;
/*     */   }
/*     */   
/*     */   public final void addDefaultValue(String key, String value)
/*     */   {
/* 166 */     this.defaultTranslation.put(key, value);
/*     */   }
/*     */   
/*     */   public final void appendLocalizationStore(LocalizationStore values) {
/* 170 */     Map<String, String> finalMap = new HashMap();
/* 171 */     finalMap.putAll(this.defaultTranslation);
/* 172 */     finalMap.putAll(values.getValues());
/*     */     
/* 174 */     this.defaultTranslation = finalMap;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\localization\LocalizationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */