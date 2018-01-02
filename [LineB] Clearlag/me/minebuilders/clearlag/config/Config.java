/*     */ package me.minebuilders.clearlag.config;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.InputStream;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.List;
/*     */ import me.minebuilders.clearlag.Clearlag;
/*     */ import me.minebuilders.clearlag.Util;
/*     */ import me.minebuilders.clearlag.annotations.ConfigModule;
/*     */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*     */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*     */ import me.minebuilders.clearlag.config.configupdater.ConfigSection;
/*     */ import me.minebuilders.clearlag.config.configupdater.rawvalues.ConfigComment;
/*     */ import me.minebuilders.clearlag.config.configupdater.rawvalues.ConfigListValue;
/*     */ import me.minebuilders.clearlag.config.configvalues.ConfigData;
/*     */ import me.minebuilders.clearlag.modules.Module;
/*     */ import me.minebuilders.clearlag.tasks.WarnTask;
/*     */ import org.bukkit.configuration.Configuration;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ 
/*     */ public class Config
/*     */ {
/*  26 */   private static Configuration c = Clearlag.getInstance().getConfig();
/*     */   
/*     */   private WarnTask warnTask;
/*     */   
/*  30 */   private final int version = 9;
/*     */   
/*     */   public static Configuration getConfig() {
/*  33 */     return c;
/*     */   }
/*     */   
/*     */   public Config() {
/*  37 */     if (!new File(Clearlag.getInstance().getDataFolder(), "config.yml").exists()) {
/*  38 */       Util.log("Config not found. Generating default config...");
/*  39 */       Clearlag.getInstance().saveDefaultConfig();
/*     */     }
/*  41 */     else if (!isConfigUpdated())
/*     */     {
/*  43 */       boolean resetConfig = false;
/*     */       
/*  45 */       if ((c.getBoolean("config-updater.force-update")) && (!isConfigUpdated())) {
/*  46 */         resetConfig("Old-Config.yml");
/*  47 */         resetConfig = true;
/*  48 */       } else if (!isConfigUpdated())
/*     */       {
/*     */         try {
/*  51 */           updateConfig();
/*     */         } catch (Exception e) {
/*  53 */           Util.warning("Clearlag was unable to update your configuration and was forced to rename your current config and create a new one!");
/*  54 */           Util.warning("Please run your config through a parser to check for errors: http://yaml-online-parser.appspot.com/");
/*     */           
/*  56 */           resetConfig("Invalid-Config.yml");
/*  57 */           resetConfig = true;
/*     */         }
/*     */       }
/*     */       
/*  61 */       this.warnTask = new WarnTask(resetConfig);
/*     */     }
/*     */     
/*  64 */     reloadConfig();
/*     */   }
/*     */   
/*     */   public boolean isConfigUpdated() {
/*  68 */     return (Util.isInteger(c.getString("settings.config-version"))) && (c.getInt("settings.config-version") >= 9);
/*     */   }
/*     */   
/*     */   public String fieldToConfigValue(Field field)
/*     */   {
/*  73 */     StringBuilder sb = new StringBuilder();
/*     */     
/*  75 */     for (char c : field.getName().toCharArray()) {
/*  76 */       if (Character.isUpperCase(c)) {
/*  77 */         sb.append("-");
/*  78 */         sb.append(Character.toLowerCase(c));
/*     */       } else {
/*  80 */         sb.append(c);
/*     */       }
/*     */     }
/*     */     
/*  84 */     return sb.toString();
/*     */   }
/*     */   
/*     */   public void setModuleConfigValues() throws Exception
/*     */   {
/*  89 */     for (Module module : )
/*     */     {
/*  91 */       if (module.isEnabled()) {
/*  92 */         setObjectConfigValues(module);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void setObjectConfigValues(Object object) throws Exception
/*     */   {
/*  99 */     Class<?> clazz = object.getClass();
/*     */     
/* 101 */     String path = null;
/*     */     
/* 103 */     ConfigPath configPath = (ConfigPath)clazz.getAnnotation(ConfigPath.class);
/*     */     
/* 105 */     if (configPath != null) {
/* 106 */       path = configPath.path();
/*     */     }
/*     */     
/* 109 */     while ((clazz != null) && (clazz != Object.class) && (clazz != Module.class))
/*     */     {
/* 111 */       for (Field field : clazz.getDeclaredFields()) {
/* 112 */         ConfigValue configValue = (ConfigValue)field.getAnnotation(ConfigValue.class);
/* 113 */         ConfigModule configModule = (ConfigModule)field.getAnnotation(ConfigModule.class);
/*     */         
/* 115 */         if (configValue != null) {
/* 116 */           field.setAccessible(true);
/*     */           
/* 118 */           ConfigData cd = configValue.valueType().getConfigData();
/*     */           
/* 120 */           Object ob = cd.getValue(configValue.path().length() <= 1 ? path + "." + fieldToConfigValue(field) : configValue.path());
/*     */           
/* 122 */           if (ob == null) {
/* 123 */             Object tp = field.get(object);
/*     */             
/* 125 */             if (tp != null) {
/* 126 */               ob = tp.getClass().newInstance();
/*     */             }
/*     */           }
/* 129 */           field.set(object, (cd instanceof me.minebuilders.clearlag.config.configvalues.PrimitiveCV) ? me.minebuilders.clearlag.reflection.ReflectionUtil.castPrimitedValues(field.getType(), ob) : ob);
/*     */         }
/*     */         
/*     */ 
/* 133 */         if (configModule != null) {
/* 134 */           field.setAccessible(true);
/*     */           
/* 136 */           setObjectConfigValues(field.get(object));
/*     */         }
/*     */       }
/*     */       
/* 140 */       clazz = clazz.getSuperclass();
/*     */     }
/*     */   }
/*     */   
/*     */   public static boolean containsReloadableFields(Object ob) {
/* 145 */     for (Field field : ob.getClass().getDeclaredFields()) {
/* 146 */       if ((field.isAnnotationPresent(ConfigValue.class)) || (field.isAnnotationPresent(ConfigModule.class))) {
/* 147 */         return true;
/*     */       }
/*     */     }
/* 150 */     return false;
/*     */   }
/*     */   
/*     */   private void resetConfig(String renamedName) {
/* 154 */     File newf = new File(Clearlag.getInstance().getDataFolder().getAbsolutePath(), "config.yml");
/* 155 */     File oldf = new File(Clearlag.getInstance().getDataFolder().getAbsolutePath(), renamedName);
/*     */     
/* 157 */     if (oldf.exists()) { oldf.delete();
/*     */     }
/* 159 */     newf.renameTo(new File(Clearlag.getInstance().getDataFolder().getAbsolutePath(), renamedName));
/*     */     
/* 161 */     Clearlag.getInstance().saveDefaultConfig();
/*     */     
/* 163 */     Clearlag.getInstance().reloadConfig();
/*     */   }
/*     */   
/*     */   public void reloadConfig() {
/* 167 */     Clearlag.getInstance().reloadConfig();
/* 168 */     c = Clearlag.getInstance().getConfig();
/*     */   }
/*     */   
/*     */   public void updateConfig() throws Exception {
/* 172 */     Util.log("Updating config to v" + Clearlag.getInstance().getDescription().getVersion() + "...");
/*     */     
/* 174 */     List<ConfigSection> oldfile = loadFile(new FileInputStream(Clearlag.getInstance().getDataFolder() + "/config.yml"));
/*     */     
/* 176 */     List<ConfigSection> newfile = loadFile(Clearlag.getInstance().getResource("config.yml"));
/*     */     
/* 178 */     File file = new File(Clearlag.getInstance().getDataFolder() + "/config.yml");
/*     */     
/* 180 */     BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(file.getAbsoluteFile()));
/*     */     
/* 182 */     for (ConfigSection v : newfile)
/*     */     {
/* 184 */       for (ConfigSection vs : oldfile) {
/* 185 */         if (v.equals(vs)) {
/* 186 */           v.merge(vs);
/*     */         }
/*     */       }
/*     */       
/* 190 */       v.writeToFile(bw);
/*     */     }
/*     */     
/* 193 */     bw.close();
/*     */     
/* 195 */     Util.log("Successfully updated config to v" + Clearlag.getInstance().getDescription().getVersion() + "!");
/*     */   }
/*     */   
/*     */   public List<ConfigSection> loadFile(InputStream f) throws Exception
/*     */   {
/* 200 */     BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(f));
/*     */     
/* 202 */     List<ConfigSection> list = new java.util.ArrayList();
/*     */     
/* 204 */     ConfigSection cs = new ConfigSection();
/*     */     
/* 206 */     String line = reader.readLine();
/*     */     
/* 208 */     while (line != null)
/*     */     {
/* 210 */       String info = line.trim();
/*     */       
/* 212 */       if (info.equals("")) {
/* 213 */         cs.addConfigValue(new ConfigComment(line));
/* 214 */         list.add(cs);
/* 215 */         cs = new ConfigSection();
/* 216 */       } else if (info.startsWith("#")) {
/* 217 */         cs.addConfigValue(new ConfigComment(line));
/* 218 */       } else if (line.contains(":"))
/*     */       {
/* 220 */         String[] sl = info.split(":");
/*     */         
/* 222 */         if (sl.length > 1) {
/* 223 */           cs.addConfigValue(new me.minebuilders.clearlag.config.configupdater.rawvalues.ConfigBasicValue(line.startsWith("    ") ? "  " + sl[0] : sl[0], sl[0].startsWith("config-version") ? " 9" : sl[1]));
/*     */         }
/* 225 */         else if (!line.startsWith(" ")) {
/* 226 */           cs.setKey(info);
/*     */         }
/*     */         else {
/* 229 */           ConfigListValue clist = new ConfigListValue(sl[0]);
/*     */           
/* 231 */           for (line = reader.readLine(); (line != null) && (line.trim().startsWith("-")); line = reader.readLine()) {
/* 232 */             clist.addValue(line);
/*     */           }
/*     */           
/* 235 */           cs.addConfigValue(clist);
/* 236 */           cs.addConfigValue(new ConfigComment(line));
/*     */         }
/*     */       }
/*     */       else {
/* 240 */         cs.addConfigValue(new ConfigComment(line));
/*     */       }
/*     */       
/* 243 */       line = reader.readLine();
/*     */     }
/*     */     
/* 246 */     cs.addConfigValue(new ConfigComment(line));
/* 247 */     list.add(cs);
/*     */     
/* 249 */     return list;
/*     */   }
/*     */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\Config.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */