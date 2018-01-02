/*     */ package de.rob1n.prospam.data.specific;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import de.rob1n.prospam.data.ConfigFile;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.configuration.MemorySection;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ 
/*     */ public class Settings
/*     */   extends ConfigFile
/*     */ {
/*     */   public static final String OPTIONS_ENABLED = "enabled";
/*     */   public static final String OPTIONS_FILTER_COMMANDS = "filter-commands";
/*     */   public static final String OPTIONS_LOG_SPAM = "log-spam";
/*     */   public static final String OPTIONS_FILTER_ENABLED_CAPS = "filter-enabled-caps";
/*     */   public static final String OPTIONS_FILTER_ENABLED_CHARS = "filter-enabled-chars";
/*     */   public static final String OPTIONS_FILTER_ENABLED_FLOOD = "filter-enabled-flood";
/*     */   public static final String OPTIONS_FILTER_ENABLED_SIMILAR = "filter-enabled-similar";
/*     */   public static final String OPTIONS_FILTER_ENABLED_URLS = "filter-enabled-urls";
/*     */   public static final String OPTIONS_FILTER_ENABLED_BLACKLIST = "filter-enabled-blacklist";
/*     */   public static final String OPTIONS_WHITELIST_ENABLED = "whitelist-enabled";
/*     */   public static final String OPTIONS_FILTER_CAPS_MAX = "filter-caps-max";
/*     */   public static final String OPTIONS_FILTER_FLOOD_LOCK = "filter-flood-lock";
/*     */   public static final String OPTIONS_FILTER_LINES_SIMILAR = "filter-lines-similar";
/*     */   public static final String OPTIONS_TRIGGER_ENABLED_CAPS = "trigger-enabled-caps";
/*     */   public static final String OPTIONS_TRIGGER_ENABLED_CHARS = "trigger-enabled-chars";
/*     */   public static final String OPTIONS_TRIGGER_ENABLED_FLOOD = "trigger-enabled-flood";
/*     */   public static final String OPTIONS_TRIGGER_ENABLED_SIMILAR = "trigger-enabled-similar";
/*     */   public static final String OPTIONS_TRIGGER_ENABLED_URLS = "trigger-enabled-urls";
/*     */   public static final String OPTIONS_TRIGGER_ENABLED_BLACKLIST = "trigger-enabled-blacklist";
/*     */   public static final String OPTIONS_TRIGGER_COUNTER_RESET = "trigger-counter-reset";
/*     */   public static final String OPTIONS_TRIGGER_CAPS = "trigger-caps";
/*     */   public static final String OPTIONS_TRIGGER_CHARS = "trigger-chars";
/*     */   public static final String OPTIONS_TRIGGER_FLOOD = "trigger-flood";
/*     */   public static final String OPTIONS_TRIGGER_SIMILAR = "trigger-similar";
/*     */   public static final String OPTIONS_TRIGGER_URLS = "trigger-urls";
/*     */   public static final String OPTIONS_TRIGGER_BLACKLIST = "trigger-blacklist";
/*     */   public boolean enabled;
/*     */   public boolean log_spam;
/*     */   public List<String> filter_commands;
/*     */   public boolean filter_enabled_caps;
/*     */   public boolean filter_enabled_chars;
/*     */   public boolean filter_enabled_flood;
/*     */   public boolean filter_enabled_similar;
/*     */   public boolean filter_enabled_urls;
/*     */   public boolean filter_enabled_blacklist;
/*     */   public boolean whitelist_enabled;
/*     */   public int filter_caps_max;
/*     */   public int filter_flood_lock;
/*     */   public int filter_lines_similar;
/*     */   public boolean trigger_enabled_caps;
/*     */   public boolean trigger_enabled_chars;
/*     */   public boolean trigger_enabled_flood;
/*     */   public boolean trigger_enabled_similar;
/*     */   public boolean trigger_enabled_urls;
/*     */   public boolean trigger_enabled_blacklist;
/*     */   public int trigger_counter_reset;
/*     */   public HashMap<Integer, List<String>> trigger_caps;
/*     */   public HashMap<Integer, List<String>> trigger_chars;
/*     */   public HashMap<Integer, List<String>> trigger_flood;
/*     */   public HashMap<Integer, List<String>> trigger_similar;
/*     */   public HashMap<Integer, List<String>> trigger_urls;
/*     */   public HashMap<Integer, List<String>> trigger_blacklist;
/*     */   
/*     */   public Settings(ProSpam plugin, String fileName)
/*     */   {
/*  70 */     super(plugin, fileName);
/*     */   }
/*     */   
/*     */   private HashMap<Integer, List<String>> loadTrigger(String triggerName)
/*     */   {
/*  75 */     HashMap<Integer, List<String>> map = new HashMap();
/*     */     
/*     */     try
/*     */     {
/*  79 */       MemorySection triggerCaps = (MemorySection)getConfig().get(triggerName);
/*  80 */       if (triggerCaps != null)
/*     */       {
/*  82 */         Set<String> occurencesKey = triggerCaps.getKeys(false);
/*     */         
/*  84 */         for (String occString : occurencesKey)
/*     */         {
/*  86 */           int occ = Integer.parseInt(occString);
/*     */           
/*  88 */           List<String> cmdList = getConfig().getStringList(triggerName + "." + occ);
/*     */           
/*  90 */           map.put(Integer.valueOf(occ), cmdList);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/*  96 */       ProSpam.log(Level.SEVERE, "Could not parse " + triggerName);
/*     */     }
/*     */     
/*  99 */     return map;
/*     */   }
/*     */   
/*     */ 
/*     */   protected void loadSettings()
/*     */   {
/* 105 */     this.enabled = getConfig().getBoolean("enabled", true);
/*     */     
/* 107 */     this.filter_commands = getConfig().getStringList("filter-commands");
/*     */     
/* 109 */     this.log_spam = getConfig().getBoolean("log-spam", true);
/*     */     
/* 111 */     this.filter_enabled_caps = getConfig().getBoolean("filter-enabled-caps", true);
/* 112 */     this.filter_enabled_chars = getConfig().getBoolean("filter-enabled-chars", true);
/* 113 */     this.filter_enabled_flood = getConfig().getBoolean("filter-enabled-flood", true);
/* 114 */     this.filter_enabled_similar = getConfig().getBoolean("filter-enabled-similar", true);
/* 115 */     this.filter_enabled_urls = getConfig().getBoolean("filter-enabled-urls", true);
/* 116 */     this.filter_enabled_blacklist = getConfig().getBoolean("filter-enabled-blacklist", true);
/*     */     
/* 118 */     this.whitelist_enabled = getConfig().getBoolean("whitelist-enabled", true);
/* 119 */     this.filter_caps_max = getConfig().getInt("filter-caps-max", 61);
/* 120 */     this.filter_flood_lock = getConfig().getInt("filter-flood-lock", 3);
/* 121 */     this.filter_lines_similar = getConfig().getInt("filter-lines-similar", 120);
/*     */     
/* 123 */     this.trigger_enabled_caps = getConfig().getBoolean("trigger-enabled-caps", false);
/* 124 */     this.trigger_enabled_chars = getConfig().getBoolean("trigger-enabled-chars", false);
/* 125 */     this.trigger_enabled_flood = getConfig().getBoolean("trigger-enabled-flood", false);
/* 126 */     this.trigger_enabled_similar = getConfig().getBoolean("trigger-enabled-similar", false);
/* 127 */     this.trigger_enabled_urls = getConfig().getBoolean("trigger-enabled-urls", false);
/* 128 */     this.trigger_enabled_blacklist = getConfig().getBoolean("trigger-enabled-blacklist", false);
/*     */     
/* 130 */     this.trigger_counter_reset = getConfig().getInt("trigger-counter-reset", 240);
/*     */     
/* 132 */     this.trigger_caps = loadTrigger("trigger-caps");
/* 133 */     this.trigger_chars = loadTrigger("trigger-chars");
/* 134 */     this.trigger_flood = loadTrigger("trigger-flood");
/* 135 */     this.trigger_similar = loadTrigger("trigger-similar");
/* 136 */     this.trigger_urls = loadTrigger("trigger-urls");
/* 137 */     this.trigger_blacklist = loadTrigger("trigger-blacklist");
/*     */   }
/*     */   
/*     */ 
/*     */   protected void saveSettings()
/*     */   {
/* 143 */     getConfig().set("enabled", Boolean.valueOf(this.enabled));
/*     */     
/* 145 */     getConfig().set("filter-commands", this.filter_commands);
/*     */     
/* 147 */     getConfig().set("log-spam", Boolean.valueOf(this.log_spam));
/*     */     
/* 149 */     getConfig().set("filter-enabled-caps", Boolean.valueOf(this.filter_enabled_caps));
/* 150 */     getConfig().set("filter-enabled-chars", Boolean.valueOf(this.filter_enabled_chars));
/* 151 */     getConfig().set("filter-enabled-flood", Boolean.valueOf(this.filter_enabled_flood));
/* 152 */     getConfig().set("filter-enabled-similar", Boolean.valueOf(this.filter_enabled_similar));
/* 153 */     getConfig().set("filter-enabled-urls", Boolean.valueOf(this.filter_enabled_urls));
/* 154 */     getConfig().set("filter-enabled-blacklist", Boolean.valueOf(this.filter_enabled_blacklist));
/*     */     
/* 156 */     getConfig().set("whitelist-enabled", Boolean.valueOf(this.whitelist_enabled));
/* 157 */     getConfig().set("filter-caps-max", Integer.valueOf(this.filter_caps_max));
/* 158 */     getConfig().set("filter-flood-lock", Integer.valueOf(this.filter_flood_lock));
/* 159 */     getConfig().set("filter-lines-similar", Integer.valueOf(this.filter_lines_similar));
/*     */     
/* 161 */     getConfig().set("trigger-enabled-caps", Boolean.valueOf(this.trigger_enabled_caps));
/* 162 */     getConfig().set("trigger-enabled-chars", Boolean.valueOf(this.trigger_enabled_chars));
/* 163 */     getConfig().set("trigger-enabled-flood", Boolean.valueOf(this.trigger_enabled_flood));
/* 164 */     getConfig().set("trigger-enabled-similar", Boolean.valueOf(this.trigger_enabled_similar));
/* 165 */     getConfig().set("trigger-enabled-urls", Boolean.valueOf(this.trigger_enabled_urls));
/* 166 */     getConfig().set("trigger-enabled-blacklist", Boolean.valueOf(this.trigger_enabled_blacklist));
/*     */     
/* 168 */     getConfig().set("trigger-counter-reset", Integer.valueOf(this.trigger_counter_reset));
/*     */     
/* 170 */     getConfig().set("trigger-caps", this.trigger_caps);
/* 171 */     getConfig().set("trigger-chars", this.trigger_chars);
/* 172 */     getConfig().set("trigger-flood", this.trigger_flood);
/* 173 */     getConfig().set("trigger-similar", this.trigger_similar);
/* 174 */     getConfig().set("trigger-urls", this.trigger_urls);
/* 175 */     getConfig().set("trigger-blacklist", this.trigger_blacklist);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\data\specific\Settings.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */