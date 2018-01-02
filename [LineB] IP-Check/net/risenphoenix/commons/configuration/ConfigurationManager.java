/*     */ package net.risenphoenix.commons.configuration;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
/*     */ import net.risenphoenix.commons.stores.ConfigurationStore;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.FileConfigurationOptions;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConfigurationManager
/*     */ {
/*     */   private final Plugin plugin;
/*     */   private Map<String, Object> configurationOptions;
/*  51 */   private ArrayList<ConfigurationOption> options = null;
/*     */   
/*     */   public ConfigurationManager(Plugin plugin) {
/*  54 */     this.plugin = plugin;
/*  55 */     this.plugin.getConfig().options().copyDefaults(true);
/*  56 */     this.plugin.saveConfig();
/*  57 */     this.plugin.reloadConfig();
/*     */   }
/*     */   
/*     */   public final void initializeConfigurationStore(ConfigurationStore store) {
/*  61 */     this.options = store.getOptions();
/*  62 */     this.configurationOptions = createValueMap(this.options);
/*     */   }
/*     */   
/*     */   public final HashMap<String, Object> createValueMap(ArrayList<ConfigurationOption> options)
/*     */   {
/*  67 */     HashMap valueMap = new HashMap();
/*     */     
/*  69 */     for (ConfigurationOption configOpt : options) {
/*     */       try {
/*  71 */         if (configOpt.getType() == ConfigurationOption.ConfigOptionType.Boolean) {
/*  72 */           valueMap.put(configOpt.getIdentifier(), 
/*  73 */             Boolean.valueOf(this.plugin.getConfig()
/*  74 */             .getBoolean(configOpt.getIdentifier())));
/*     */         }
/*  76 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.BooleanList) {
/*  77 */           valueMap.put(configOpt.getIdentifier(), this.plugin
/*  78 */             .getConfig()
/*  79 */             .getBooleanList(configOpt.getIdentifier()));
/*     */         }
/*  81 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.ByteList) {
/*  82 */           valueMap.put(configOpt.getIdentifier(), this.plugin
/*  83 */             .getConfig()
/*  84 */             .getByteList(configOpt.getIdentifier()));
/*     */         }
/*  86 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.CharList) {
/*  87 */           valueMap.put(configOpt.getIdentifier(), this.plugin
/*  88 */             .getConfig()
/*  89 */             .getCharacterList(configOpt.getIdentifier()));
/*     */         }
/*  91 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.FloatList) {
/*  92 */           valueMap.put(configOpt.getIdentifier(), this.plugin
/*  93 */             .getConfig()
/*  94 */             .getFloatList(configOpt.getIdentifier()));
/*     */         }
/*  96 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.Integer) {
/*  97 */           valueMap.put(configOpt.getIdentifier(), 
/*  98 */             Integer.valueOf(this.plugin.getConfig()
/*  99 */             .getInt(configOpt.getIdentifier())));
/*     */         }
/* 101 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.IntegerList) {
/* 102 */           valueMap.put(configOpt.getIdentifier(), this.plugin
/* 103 */             .getConfig()
/* 104 */             .getIntegerList(configOpt.getIdentifier()));
/*     */         }
/* 106 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.Long) {
/* 107 */           valueMap.put(configOpt.getIdentifier(), 
/* 108 */             Long.valueOf(this.plugin.getConfig()
/* 109 */             .getLong(configOpt.getIdentifier())));
/*     */         }
/* 111 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.LongList) {
/* 112 */           valueMap.put(configOpt.getIdentifier(), this.plugin
/* 113 */             .getConfig()
/* 114 */             .getLongList(configOpt.getIdentifier()));
/*     */         }
/* 116 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.String) {
/* 117 */           valueMap.put(configOpt.getIdentifier(), this.plugin
/* 118 */             .getConfig()
/* 119 */             .getString(configOpt.getIdentifier()));
/*     */         }
/* 121 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.StringList) {
/* 122 */           valueMap.put(configOpt.getIdentifier(), this.plugin
/* 123 */             .getConfig()
/* 124 */             .getStringList(configOpt.getIdentifier()));
/*     */         }
/* 126 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.Double) {
/* 127 */           valueMap.put(configOpt.getIdentifier(), 
/* 128 */             Double.valueOf(this.plugin.getConfig()
/* 129 */             .getDouble(configOpt.getIdentifier())));
/*     */         }
/* 131 */         else if (configOpt.getType() == ConfigurationOption.ConfigOptionType.DoubleList) {
/* 132 */           valueMap.put(configOpt.getIdentifier(), this.plugin
/* 133 */             .getConfig()
/* 134 */             .getDoubleList(configOpt.getIdentifier()));
/*     */         }
/*     */       } catch (Exception e) {
/* 137 */         this.plugin.sendConsoleMessage(Level.WARNING, this.plugin
/* 138 */           .getLocalizationManager()
/* 139 */           .getLocalString("BAD_CFG_SET") + configOpt
/* 140 */           .getIdentifier());
/*     */       }
/*     */     }
/*     */     
/* 144 */     return valueMap;
/*     */   }
/*     */   
/*     */   private Object getConfigurationOption(String identifier) {
/* 148 */     return this.configurationOptions.get(identifier);
/*     */   }
/*     */   
/*     */   public final boolean getBoolean(String identifier) {
/* 152 */     return Boolean.parseBoolean(
/* 153 */       getConfigurationOption(identifier).toString());
/*     */   }
/*     */   
/*     */   public final List<Boolean> getBooleanList(String identifier) {
/* 157 */     return (List)getConfigurationOption(identifier);
/*     */   }
/*     */   
/*     */   public final List<Byte> getByteList(String identifier) {
/* 161 */     return (List)getConfigurationOption(identifier);
/*     */   }
/*     */   
/*     */   public final List<Character> getCharacterList(String identifier) {
/* 165 */     return (List)getConfigurationOption(identifier);
/*     */   }
/*     */   
/*     */   public final List<Float> getFloatList(String identifier) {
/* 169 */     return (List)getConfigurationOption(identifier);
/*     */   }
/*     */   
/*     */   public final int getInteger(String identifier) {
/* 173 */     return Integer.parseInt(getConfigurationOption(identifier).toString());
/*     */   }
/*     */   
/*     */   public final List<Integer> getIntegerList(String identifier) {
/* 177 */     return (List)getConfigurationOption(identifier);
/*     */   }
/*     */   
/*     */   public final long getLong(String identifier) {
/* 181 */     return Long.parseLong(getConfigurationOption(identifier).toString());
/*     */   }
/*     */   
/*     */   public final List<Long> getLongList(String identifier) {
/* 185 */     return (List)getConfigurationOption(identifier);
/*     */   }
/*     */   
/*     */   public final String getString(String identifier) {
/* 189 */     return (String)getConfigurationOption(identifier);
/*     */   }
/*     */   
/*     */   public final List<String> getStringList(String identifier) {
/* 193 */     return (List)getConfigurationOption(identifier);
/*     */   }
/*     */   
/*     */   public final void setConfigurationOption(String identifier, Object arg0) {
/* 197 */     this.plugin.getConfig().set(identifier, arg0);
/* 198 */     this.plugin.saveConfig();
/*     */     
/* 200 */     if (this.options != null) {
/* 201 */       this.configurationOptions = createValueMap(this.options);
/*     */     } else {
/* 203 */       this.plugin.sendConsoleMessage(Level.SEVERE, this.plugin
/* 204 */         .getLocalizationManager()
/* 205 */         .getLocalString("CFG_INIT_ERR"));
/*     */     }
/*     */   }
/*     */   
/*     */   public final void rewriteConfiguration() {
/* 210 */     Map<String, Object> current = this.configurationOptions;
/*     */     
/*     */ 
/* 213 */     File f = new File(this.plugin.getDataFolder() + File.separator + "config.yml");
/*     */     
/* 215 */     f.delete();
/*     */   }
/*     */ }


 