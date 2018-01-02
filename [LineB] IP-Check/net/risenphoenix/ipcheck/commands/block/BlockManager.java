/*     */ package net.risenphoenix.ipcheck.commands.block;
/*     */ 
/*     */ import com.maxmind.geoip.Country;
/*     */ import com.maxmind.geoip.LookupService;
/*     */ import java.util.List;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.objects.GeoIPObject;
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
/*     */ public class BlockManager
/*     */ {
/*  40 */   private boolean isEnabled = false;
/*     */   private IPCheck ipc;
/*     */   private LookupService ls;
/*     */   
/*     */   public BlockManager(IPCheck ipc)
/*     */   {
/*  46 */     this.ipc = ipc;
/*     */     
/*  48 */     if (ipc.getConfigurationManager().getBoolean("use-geoip-services")) {
/*  49 */       this.isEnabled = true;
/*  50 */       this.ls = ipc.getGeoIPObject().getLookupService();
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean getStatus() {
/*  55 */     return this.isEnabled;
/*     */   }
/*     */   
/*     */   public String getCountry(String ip) {
/*  59 */     if (!this.isEnabled) return null;
/*  60 */     if (this.ls != null)
/*  61 */       return this.ls.getCountry(ip).getName();
/*  62 */     return null;
/*     */   }
/*     */   
/*     */   public String getCountryID(String ip) {
/*  66 */     if (!this.isEnabled) return null;
/*  67 */     if (this.ls != null)
/*  68 */       return this.ls.getCountry(ip).getCode();
/*  69 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean isBlockedCountry(String country)
/*     */   {
/*  76 */     List<String> c_block = this.ipc.getConfigurationManager().getStringList("country-blacklist");
/*     */     
/*     */ 
/*  79 */     for (String s : c_block) {
/*  80 */       if (s.equalsIgnoreCase(country)) { return true;
/*     */       }
/*     */     }
/*  83 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean blockCountry(String country)
/*     */   {
/*  90 */     List<String> c_block = this.ipc.getConfigurationManager().getStringList("country-blacklist");
/*     */     
/*     */ 
/*  93 */     boolean result = !c_block.contains(country.toLowerCase());
/*  94 */     if (result) { c_block.add(country.toLowerCase());
/*     */     }
/*     */     
/*  97 */     this.ipc.getConfigurationManager().setConfigurationOption("country-blacklist", c_block);
/*     */     
/*     */ 
/* 100 */     return result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public boolean unblockCountry(String country)
/*     */   {
/* 107 */     List<String> c_block = this.ipc.getConfigurationManager().getStringList("country-blacklist");
/*     */     
/*     */ 
/* 110 */     boolean result = c_block.contains(country.toLowerCase());
/* 111 */     if (result) { c_block.remove(country.toLowerCase());
/*     */     }
/*     */     
/* 114 */     this.ipc.getConfigurationManager().setConfigurationOption("country-blacklist", c_block);
/*     */     
/*     */ 
/* 117 */     return result;
/*     */   }
/*     */   
/*     */   public LookupService getLookupService() {
/* 121 */     return this.ls;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\block\BlockManager.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */