/*     */ package net.risenphoenix.ipcheck.objects;
/*     */ 
/*     */ import com.maxmind.geoip.LookupService;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.logging.Level;
/*     */ import java.util.zip.GZIPInputStream;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
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
/*     */ public class GeoIPObject
/*     */ {
/*  46 */   private LookupService ls = null;
/*     */   private IPCheck ipc;
/*     */   private LocalizationManager LM;
/*     */   private ConfigurationManager CM;
/*     */   
/*     */   public GeoIPObject(IPCheck ipc)
/*     */   {
/*  53 */     this.ipc = ipc;
/*  54 */     this.LM = ipc.getLocalizationManager();
/*  55 */     this.CM = ipc.getConfigurationManager();
/*     */     
/*  57 */     initializeDatabase();
/*     */   }
/*     */   
/*     */ 
/*     */   private void initializeDatabase()
/*     */   {
/*     */     File database;
/*     */     
/*  65 */     if (this.CM.getBoolean("use-geoip-services")) {
/*  66 */       database = new File(this.ipc.getDataFolder(), "GeoIP.dat");
/*     */     } else {
/*     */       return;
/*     */     }
/*     */     
/*     */     File database;
/*  72 */     if (!database.exists())
/*     */     {
/*  74 */       downloadDatabase();
/*     */       
/*  76 */       if (!database.exists()) {
/*  77 */         this.ipc.sendConsoleMessage(Level.SEVERE, this.LM
/*  78 */           .getLocalString("GEOIP_DB_MISSING"));
/*  79 */         return;
/*     */       }
/*     */     }
/*     */     
/*     */     try
/*     */     {
/*  85 */       this.ls = new LookupService(database);
/*     */     } catch (IOException e) {
/*  87 */       this.ipc.sendConsoleMessage(Level.SEVERE, this.LM
/*  88 */         .getLocalString("GEOIP_DB_READ_ERR"));
/*     */     }
/*     */   }
/*     */   
/*     */   private void downloadDatabase() {
/*  93 */     String URL = "http://geolite.maxmind.com/download/geoip/database/GeoLiteCountry/GeoIP.dat.gz";
/*     */     
/*     */ 
/*  96 */     if (this.CM.getBoolean("allow-geoip-download")) {
/*  97 */       this.ipc.sendConsoleMessage(Level.INFO, this.LM
/*  98 */         .getLocalString("GEOIP_DOWNLOAD"));
/*     */       try
/*     */       {
/* 101 */         URL dURL = new URL(URL);
/* 102 */         URLConnection conn = dURL.openConnection();
/*     */         
/* 104 */         conn.setConnectTimeout(8000);
/* 105 */         conn.connect();
/*     */         
/* 107 */         InputStream input = new GZIPInputStream(conn.getInputStream());
/*     */         
/* 109 */         File databaseLoc = new File(this.ipc.getDataFolder() + "/GeoIP.dat");
/*     */         
/* 111 */         OutputStream output = new FileOutputStream(databaseLoc);
/*     */         
/* 113 */         byte[] dlBuffer = new byte['ࠀ'];
/* 114 */         int length = input.read(dlBuffer);
/* 115 */         while (length >= 0) {
/* 116 */           output.write(dlBuffer, 0, length);
/* 117 */           length = input.read(dlBuffer);
/*     */         }
/*     */         
/* 120 */         output.close();
/* 121 */         input.close();
/*     */       } catch (IOException e) {
/* 123 */         this.ipc.sendConsoleMessage(Level.SEVERE, e.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public LookupService getLookupService() {
/* 129 */     return this.ls;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\objects\GeoIPObject.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */