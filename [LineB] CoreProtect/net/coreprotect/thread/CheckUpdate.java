/*     */ package net.coreprotect.thread;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import net.coreprotect.CoreProtect;
/*     */ import net.coreprotect.Functions;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.json.simple.JSONArray;
/*     */ import org.json.simple.JSONObject;
/*     */ 
/*     */ public class CheckUpdate implements Runnable
/*     */ {
/*  17 */   private boolean startup = true;
/*  18 */   private static String latestVersion = null;
/*     */   
/*     */   public CheckUpdate(boolean startup) {
/*  21 */     this.startup = startup;
/*     */   }
/*     */   
/*     */   public static String latestVersion() {
/*  25 */     return latestVersion;
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/*     */     try {
/*  31 */       int status = 0;
/*  32 */       HttpURLConnection connection = null;
/*  33 */       String version = CoreProtect.getInstance().getDescription().getVersion();
/*     */       try
/*     */       {
/*  36 */         URL url = new URL("https://api.curseforge.com/servermods/files?projectIds=37375");
/*  37 */         connection = (HttpURLConnection)url.openConnection();
/*  38 */         connection.setRequestMethod("GET");
/*  39 */         connection.setRequestProperty("Accept-Charset", "UTF-8");
/*  40 */         connection.setRequestProperty("X-API-Key", "dd6940ea59515bc48f617e3cd12d923e5eb7dab4");
/*  41 */         connection.setRequestProperty("User-Agent", "CoreProtect/v" + version + " (by Intelli)");
/*  42 */         connection.setDoOutput(true);
/*  43 */         connection.setConnectTimeout(5000);
/*  44 */         connection.connect();
/*  45 */         status = connection.getResponseCode();
/*     */       }
/*     */       catch (Exception e) {}
/*     */       
/*     */ 
/*     */ 
/*  51 */       if (status == 200) {
/*     */         try {
/*  53 */           BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/*  54 */           String response = reader.readLine();
/*  55 */           JSONArray array = (JSONArray)org.json.simple.JSONValue.parse(response);
/*     */           
/*  57 */           if (array.size() > 0) {
/*  58 */             String remoteVersion = ((String)((JSONObject)array.get(array.size() - 1)).get("name")).replaceAll("[^0-9.]", "");
/*  59 */             if (remoteVersion.contains(".")) {
/*  60 */               Thread.sleep(2000L);
/*  61 */               boolean newVersion = Functions.newVersion(version, remoteVersion);
/*     */               
/*  63 */               if (newVersion) {
/*  64 */                 latestVersion = remoteVersion;
/*  65 */                 if (this.startup) {
/*  66 */                   System.out.println("--------------------");
/*  67 */                   System.out.println("[CoreProtect] Version " + remoteVersion + " is now available.");
/*  68 */                   System.out.println("[CoreProtect] Download: Type \"/co version\" in-game.");
/*  69 */                   System.out.println("--------------------");
/*  70 */                   System.out.println("[Sponsor] Unlimited MC Hosting: www.hosthorde.com");
/*  71 */                   System.out.println("--------------------");
/*     */                 }
/*     */               }
/*     */               else {
/*  75 */                 latestVersion = null;
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         catch (Exception e) {
/*  81 */           e.printStackTrace();
/*     */         }
/*     */       }
/*     */       
/*     */       try
/*     */       {
/*  87 */         int port = CoreProtect.getInstance().getServer().getPort();
/*  88 */         String stats = port + ":" + version;
/*  89 */         URL url = new URL("http://stats.coreprotect.net/u/?data=" + stats);
/*  90 */         connection = (HttpURLConnection)url.openConnection();
/*  91 */         connection.setRequestMethod("GET");
/*  92 */         connection.setRequestProperty("Accept-Charset", "UTF-8");
/*  93 */         connection.setRequestProperty("User-Agent", "CoreProtect");
/*  94 */         connection.setConnectTimeout(5000);
/*  95 */         connection.connect();
/*  96 */         connection.getResponseCode();
/*  97 */         connection.disconnect();
/*     */ 
/*     */       }
/*     */       catch (Exception e) {}
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 104 */       System.err.println("[CoreProtect] An error occurred while checking for updates.");
/* 105 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\thread\CheckUpdate.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */