/*     */ package de.rob1n.prospam.filter;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import de.rob1n.prospam.chatter.Chatter;
/*     */ import de.rob1n.prospam.data.DataHandler;
/*     */ import de.rob1n.prospam.data.specific.Settings;
/*     */ import de.rob1n.prospam.data.specific.Strings;
/*     */ import de.rob1n.prospam.filter.specific.FilterBlacklist;
/*     */ import de.rob1n.prospam.filter.specific.FilterCaps;
/*     */ import de.rob1n.prospam.filter.specific.FilterChars;
/*     */ import de.rob1n.prospam.filter.specific.FilterFlood;
/*     */ import de.rob1n.prospam.filter.specific.FilterSimilar;
/*     */ import de.rob1n.prospam.filter.specific.FilterUrls;
/*     */ import de.rob1n.prospam.trigger.Trigger;
/*     */ import java.text.MessageFormat;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class FilterHandler
/*     */ {
/*     */   private final ProSpam plugin;
/*     */   private final FilterBlacklist filterBlacklist;
/*     */   private final FilterCaps filterCaps;
/*     */   private final FilterChars filterChars;
/*     */   private final FilterSimilar filterSimilar;
/*     */   private final FilterUrls filterUrls;
/*     */   private final FilterFlood filterFlood;
/*     */   private final Settings settings;
/*     */   private final Strings strings;
/*     */   private final Trigger trigger;
/*     */   
/*     */   public FilterHandler(ProSpam plugin)
/*     */   {
/*  38 */     this.plugin = plugin;
/*     */     
/*  40 */     this.filterBlacklist = new FilterBlacklist(plugin);
/*  41 */     this.filterCaps = new FilterCaps(plugin);
/*  42 */     this.filterChars = new FilterChars(plugin);
/*  43 */     this.filterSimilar = new FilterSimilar(plugin);
/*  44 */     this.filterUrls = new FilterUrls(plugin);
/*  45 */     this.filterFlood = new FilterFlood(plugin);
/*     */     
/*  47 */     this.settings = plugin.getDataHandler().getSettings();
/*  48 */     this.strings = plugin.getDataHandler().getStrings();
/*     */     
/*  50 */     this.trigger = new Trigger(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String execute(Player player, String chatMessage)
/*     */   {
/*  62 */     if (!player.hasPermission("prospam.nocheck"))
/*     */     {
/*  64 */       Chatter chatter = de.rob1n.prospam.chatter.ChatterHandler.getChatter(player.getUniqueId());
/*     */       
/*  66 */       List<String> filters_triggered = new java.util.ArrayList();
/*  67 */       String previouslyFilteredMessage = chatMessage;
/*     */       
/*  69 */       String filteredMessage = chatMessage;
/*     */       
/*     */       try
/*     */       {
/*  73 */         if (this.settings.filter_enabled_chars)
/*     */         {
/*  75 */           filteredMessage = this.filterChars.execute(chatter, filteredMessage);
/*     */           
/*     */ 
/*  78 */           if ((filteredMessage == null) || (!previouslyFilteredMessage.equals(filteredMessage)))
/*     */           {
/*  80 */             chatter.increaseSpamCountChars();
/*  81 */             filters_triggered.add("Chars");
/*     */             
/*  83 */             if (this.settings.trigger_enabled_chars)
/*  84 */               this.trigger.execute(chatter, chatter.getSpamCountChars(), this.settings.trigger_chars);
/*     */           }
/*  86 */           previouslyFilteredMessage = filteredMessage;
/*     */           
/*  88 */           if (filteredMessage == null) {
/*  89 */             return null;
/*     */           }
/*     */         }
/*  92 */         if (this.settings.filter_enabled_caps)
/*     */         {
/*  94 */           filteredMessage = this.filterCaps.execute(chatter, filteredMessage);
/*     */           
/*     */ 
/*  97 */           if ((filteredMessage == null) || (!previouslyFilteredMessage.equals(filteredMessage)))
/*     */           {
/*  99 */             chatter.increaseSpamCountCaps();
/* 100 */             filters_triggered.add("Caps");
/*     */             
/* 102 */             if (this.settings.trigger_enabled_caps)
/* 103 */               this.trigger.execute(chatter, chatter.getSpamCountCaps(), this.settings.trigger_caps);
/*     */           }
/* 105 */           previouslyFilteredMessage = filteredMessage;
/*     */           
/* 107 */           if (filteredMessage == null) {
/* 108 */             return null;
/*     */           }
/*     */         }
/* 111 */         if (this.settings.filter_enabled_urls)
/*     */         {
/* 113 */           filteredMessage = this.filterUrls.execute(chatter, filteredMessage);
/*     */           
/*     */ 
/* 116 */           if ((filteredMessage == null) || (!previouslyFilteredMessage.equals(filteredMessage)))
/*     */           {
/* 118 */             chatter.increaseSpamCountUrls();
/* 119 */             filters_triggered.add("URL");
/*     */             
/* 121 */             if (this.settings.trigger_enabled_urls)
/* 122 */               this.trigger.execute(chatter, chatter.getSpamCountUrls(), this.settings.trigger_urls);
/*     */           }
/* 124 */           previouslyFilteredMessage = filteredMessage;
/*     */           
/* 126 */           if (filteredMessage == null) {
/* 127 */             return null;
/*     */           }
/*     */         }
/* 130 */         if (this.settings.filter_enabled_flood)
/*     */         {
/* 132 */           filteredMessage = this.filterFlood.execute(chatter, filteredMessage);
/*     */           
/* 134 */           if (filteredMessage == null)
/*     */           {
/* 136 */             chatter.increaseSpamCountFlood();
/* 137 */             filters_triggered.add("Flood");
/* 138 */             informSpam(player.getUniqueId(), player.getName(), filters_triggered, chatMessage);
/*     */             
/* 140 */             if (this.settings.trigger_enabled_flood) {
/* 141 */               this.trigger.execute(chatter, chatter.getSpamCountFlood(), this.settings.trigger_flood);
/*     */             }
/* 143 */             if ((this.strings.filter_lines_locked != null) && (!this.strings.filter_lines_locked.isEmpty())) {
/* 144 */               player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.strings.filter_lines_locked));
/*     */             }
/* 146 */             return null;
/*     */           }
/*     */         }
/*     */         
/* 150 */         if (this.settings.filter_enabled_similar)
/*     */         {
/* 152 */           filteredMessage = this.filterSimilar.execute(chatter, filteredMessage);
/*     */           
/* 154 */           if (filteredMessage == null)
/*     */           {
/* 156 */             chatter.increaseSpamCountSimilar();
/* 157 */             filters_triggered.add("Similar");
/* 158 */             informSpam(player.getUniqueId(), player.getName(), filters_triggered, chatMessage);
/*     */             
/* 160 */             if (this.settings.trigger_enabled_similar) {
/* 161 */               this.trigger.execute(chatter, chatter.getSpamCountSimilar(), this.settings.trigger_similar);
/*     */             }
/* 163 */             if ((this.strings.filter_lines_similar != null) && (!this.strings.filter_lines_similar.isEmpty())) {
/* 164 */               player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.strings.filter_lines_similar));
/*     */             }
/* 166 */             return null;
/*     */           }
/*     */         }
/*     */         
/* 170 */         if (this.settings.filter_enabled_blacklist)
/*     */         {
/* 172 */           filteredMessage = this.filterBlacklist.execute(chatter, filteredMessage);
/*     */           
/*     */ 
/* 175 */           if ((filteredMessage == null) || (!previouslyFilteredMessage.equals(filteredMessage)))
/*     */           {
/* 177 */             chatter.increaseSpamCountBlacklist();
/* 178 */             filters_triggered.add("Blacklist");
/*     */             
/* 180 */             if (this.settings.trigger_enabled_blacklist) {
/* 181 */               this.trigger.execute(chatter, chatter.getSpamCountBlacklist(), this.settings.trigger_blacklist);
/*     */             }
/*     */           }
/* 184 */           if (filteredMessage == null)
/*     */           {
/* 186 */             informSpam(player.getUniqueId(), player.getName(), filters_triggered, chatMessage);
/*     */             
/* 188 */             if ((this.strings.blacklist_lines_ignored != null) && (!this.strings.blacklist_lines_ignored.isEmpty())) {
/* 189 */               player.sendMessage(ChatColor.translateAlternateColorCodes('&', this.strings.blacklist_lines_ignored));
/*     */             }
/* 191 */             return null;
/*     */           }
/*     */         }
/*     */         
/* 195 */         if (filters_triggered.size() > 0) {
/* 196 */           informSpam(player.getUniqueId(), player.getName(), filters_triggered, chatMessage);
/*     */         }
/* 198 */         return filteredMessage;
/*     */       }
/*     */       catch (IllegalArgumentException e)
/*     */       {
/* 202 */         ProSpam.log(Level.SEVERE, e.getMessage());
/*     */       }
/*     */       catch (NullPointerException e)
/*     */       {
/* 206 */         ProSpam.log(Level.SEVERE, e.getMessage());
/*     */       }
/*     */     }
/*     */     
/* 210 */     return chatMessage;
/*     */   }
/*     */   
/*     */   private void informSpam(UUID uuid, String playerName, List<String> triggeredFilters, String origMessage)
/*     */   {
/* 215 */     Player[] players = this.plugin.getServer().getOnlinePlayers();
/* 216 */     String joinedFilters = StringUtils.join(triggeredFilters, ", ");
/*     */     
/* 218 */     if (this.settings.log_spam) {
/* 219 */       ProSpam.log(Level.INFO, MessageFormat.format("{0} [UUID: {1}] triggered a spam filter: {2} [{3}]", new Object[] { playerName, uuid, joinedFilters, origMessage }));
/*     */     }
/* 221 */     for (Player player : players)
/*     */     {
/* 223 */       if (player.hasPermission("prospam.inform")) {
/* 224 */         player.sendMessage(ProSpam.prefixed(ChatColor.translateAlternateColorCodes('&', MessageFormat.format(this.strings.trigger_information, new Object[] { playerName, joinedFilters }))));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\filter\FilterHandler.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */