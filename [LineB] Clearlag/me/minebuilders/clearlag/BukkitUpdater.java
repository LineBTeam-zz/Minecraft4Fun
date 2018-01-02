/*     */ package me.minebuilders.clearlag;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.json.simple.JSONArray;
/*     */ import org.json.simple.JSONObject;
/*     */ 
/*     */ public class BukkitUpdater implements Runnable
/*     */ {
/*     */   private static final String HOST = "https://api.curseforge.com/servermods/files?projectIds=37824";
/*     */   private String newversion;
/*     */   private String download;
/*     */   private File file;
/*     */   
/*     */   public BukkitUpdater(File file)
/*     */   {
/*  21 */     this.file = file;
/*     */     
/*  23 */     new Thread(this).start();
/*     */   }
/*     */   
/*     */   private int versionToInt(String s) {
/*  27 */     return Integer.parseInt(s.replaceAll("[^\\d.]", "").replace(".", "").trim());
/*     */   }
/*     */   
/*     */   private boolean updateAvailable() throws Exception
/*     */   {
/*  32 */     Util.log("Checking for updates compatible with your bukkit version [" + Util.getBukkitVersion() + "]...");
/*     */     
/*  34 */     HttpURLConnection conn = createConnection("https://api.curseforge.com/servermods/files?projectIds=37824", 6000);
/*     */     
/*  36 */     BufferedReader reader = new BufferedReader(new java.io.InputStreamReader(conn.getInputStream()));
/*  37 */     String response = reader.readLine();
/*  38 */     String bukkitversion = Util.getBukkitVersion();
/*  39 */     JSONArray array = (JSONArray)org.json.simple.JSONValue.parse(response);
/*  40 */     JSONObject line = null;
/*     */     
/*  42 */     boolean isLegacy = bukkitversion.contains("1.7");
/*     */     
/*  44 */     for (int i = array.size() - 1; i > 17; i--)
/*     */     {
/*  46 */       line = (JSONObject)array.get(i);
/*     */       
/*  48 */       String ver = (String)line.get("gameVersion");
/*     */       
/*  50 */       if ((ver.contains(bukkitversion)) || ((!isLegacy) && (!ver.contains("1.7")))) {
/*     */         break;
/*     */       }
/*     */     }
/*     */     
/*  55 */     this.newversion = ((String)line.get("name")).split(" ")[1];
/*  56 */     this.download = ((String)line.get("downloadUrl"));
/*     */     
/*  58 */     return versionToInt(Clearlag.getInstance().getDescription().getVersion()) < versionToInt(this.newversion);
/*     */   }
/*     */   
/*     */   private HttpURLConnection createConnection(String url, int timeout) throws Exception {
/*  62 */     HttpURLConnection conn = (HttpURLConnection)new java.net.URL(url).openConnection();
/*     */     
/*  64 */     conn.setConnectTimeout(timeout);
/*     */     
/*  66 */     conn.setInstanceFollowRedirects(true);
/*     */     
/*  68 */     conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.82 Safari/537.36");
/*     */     
/*  70 */     return conn;
/*     */   }
/*     */   
/*     */   public void run() {
/*     */     try {
/*  75 */       if (updateAvailable()) {
/*  76 */         Util.log("Clearlag version " + this.newversion + " update available! Downloading...");
/*     */         
/*  78 */         if (!Bukkit.getUpdateFolderFile().exists()) {
/*  79 */           Bukkit.getUpdateFolderFile().mkdir();
/*     */         }
/*  81 */         HttpURLConnection conn = createConnection(this.download, 15000);
/*     */         
/*  83 */         int response = conn.getResponseCode();
/*     */         
/*  85 */         if ((response == 301) || (response == 302)) {
/*  86 */           this.download = conn.getHeaderField("Location");
/*  87 */           conn = createConnection(this.download, 15000);
/*     */         }
/*     */         try {
/*  90 */           BufferedInputStream in = new BufferedInputStream(conn.getInputStream());Throwable localThrowable3 = null;
/*  91 */           try { FileOutputStream fout = new FileOutputStream(Bukkit.getUpdateFolderFile().getAbsolutePath() + "/" + this.file.getName());Throwable localThrowable4 = null;
/*     */             try {
/*  93 */               byte[] data = new byte['Ѐ'];
/*     */               
/*     */               int count;
/*  96 */               while ((count = in.read(data, 0, 1024)) != -1) {
/*  97 */                 fout.write(data, 0, count);
/*     */               }
/*     */               
/* 100 */               Util.log("Updating finished! Restart your server for files to take effect");
/*     */             }
/*     */             catch (Throwable localThrowable1)
/*     */             {
/*  90 */               localThrowable4 = localThrowable1;throw localThrowable1; } finally {} } catch (Throwable localThrowable2) { localThrowable3 = localThrowable2;throw localThrowable2;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           }
/*     */           finally
/*     */           {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 102 */             if (in != null) if (localThrowable3 != null) try { in.close(); } catch (Throwable x2) { localThrowable3.addSuppressed(x2); } else in.close();
/* 103 */           } } catch (Exception e) { Util.log("Failed to download the latest update!");
/*     */         }
/*     */       }
/*     */       else {
/* 107 */         Util.log("No updates found!");
/*     */       }
/*     */     } catch (Exception e) {
/* 110 */       Util.warning("Clearlag failed to check for updates - bukkit may be down");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\BukkitUpdater.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */