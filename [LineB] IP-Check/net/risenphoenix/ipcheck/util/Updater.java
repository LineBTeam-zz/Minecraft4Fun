/*     */ package net.risenphoenix.ipcheck.util;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.MalformedURLException;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.util.Enumeration;
/*     */ import java.util.List;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipFile;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfigurationOptions;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.scheduler.BukkitRunnable;
/*     */ import org.json.simple.JSONArray;
/*     */ import org.json.simple.JSONObject;
/*     */ import org.json.simple.JSONValue;
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
/*     */ public class Updater
/*     */ {
/*     */   private static final String TITLE_VALUE = "name";
/*     */   private static final String LINK_VALUE = "downloadUrl";
/*     */   private static final String TYPE_VALUE = "releaseType";
/*     */   private static final String VERSION_VALUE = "gameVersion";
/*     */   private static final String QUERY = "/servermods/files?projectIds=";
/*     */   private static final String HOST = "https://api.curseforge.com";
/*     */   private static final String USER_AGENT = "Updater (by Gravity)";
/*     */   private static final String DELIMETER = "^v|[\\s_-]v";
/*  88 */   private static final String[] NO_UPDATE_TAG = { "-DEV", "-PRE", "-SNAPSHOT" };
/*     */   
/*     */ 
/*     */   private static final int BYTE_SIZE = 1024;
/*     */   
/*     */ 
/*     */   private static final String API_KEY_CONFIG_KEY = "api-key";
/*     */   
/*     */ 
/*     */   private static final String DISABLE_CONFIG_KEY = "disable";
/*     */   
/*     */   private static final String API_KEY_DEFAULT = "PUT_API_KEY_HERE";
/*     */   
/*     */   private static final boolean DISABLE_DEFAULT = false;
/*     */   
/*     */   private final Plugin plugin;
/*     */   
/*     */   private final UpdateType type;
/*     */   
/*     */   private final boolean announce;
/*     */   
/*     */   private final File file;
/*     */   
/*     */   private final File updateFolder;
/*     */   
/*     */   private final UpdateCallback callback;
/*     */   
/* 115 */   private int id = -1;
/*     */   
/* 117 */   private String apiKey = null;
/*     */   
/*     */ 
/*     */   private String versionName;
/*     */   
/*     */ 
/*     */   private String versionLink;
/*     */   
/*     */   private String versionType;
/*     */   
/*     */   private String versionGameVersion;
/*     */   
/*     */   private URL url;
/*     */   
/*     */   private Thread thread;
/*     */   
/* 133 */   private UpdateResult result = UpdateResult.SUCCESS;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static enum UpdateResult
/*     */   {
/* 142 */     SUCCESS, 
/*     */     
/*     */ 
/*     */ 
/* 146 */     NO_UPDATE, 
/*     */     
/*     */ 
/*     */ 
/* 150 */     DISABLED, 
/*     */     
/*     */ 
/*     */ 
/* 154 */     FAIL_DOWNLOAD, 
/*     */     
/*     */ 
/*     */ 
/* 158 */     FAIL_DBO, 
/*     */     
/*     */ 
/*     */ 
/* 162 */     FAIL_NOVERSION, 
/*     */     
/*     */ 
/*     */ 
/* 166 */     FAIL_BADID, 
/*     */     
/*     */ 
/*     */ 
/* 170 */     FAIL_APIKEY, 
/*     */     
/*     */ 
/*     */ 
/* 174 */     UPDATE_AVAILABLE;
/*     */     
/*     */ 
/*     */ 
/*     */     private UpdateResult() {}
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum UpdateType
/*     */   {
/* 184 */     DEFAULT, 
/*     */     
/*     */ 
/*     */ 
/* 188 */     NO_VERSION_CHECK, 
/*     */     
/*     */ 
/*     */ 
/* 192 */     NO_DOWNLOAD;
/*     */     
/*     */ 
/*     */ 
/*     */     private UpdateType() {}
/*     */   }
/*     */   
/*     */ 
/*     */   public static enum ReleaseType
/*     */   {
/* 202 */     ALPHA, 
/*     */     
/*     */ 
/*     */ 
/* 206 */     BETA, 
/*     */     
/*     */ 
/*     */ 
/* 210 */     RELEASE;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     private ReleaseType() {}
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Updater(Plugin plugin, int id, File file, UpdateType type, boolean announce)
/*     */   {
/* 223 */     this(plugin, id, file, type, null, announce);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Updater(Plugin plugin, int id, File file, UpdateType type, UpdateCallback callback)
/*     */   {
/* 236 */     this(plugin, id, file, type, callback, false);
/*     */   }
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
/*     */   public Updater(Plugin plugin, int id, File file, UpdateType type, UpdateCallback callback, boolean announce)
/*     */   {
/* 250 */     this.plugin = plugin;
/* 251 */     this.type = type;
/* 252 */     this.announce = announce;
/* 253 */     this.file = file;
/* 254 */     this.id = id;
/* 255 */     this.updateFolder = this.plugin.getServer().getUpdateFolderFile();
/* 256 */     this.callback = callback;
/*     */     
/* 258 */     File pluginFile = this.plugin.getDataFolder().getParentFile();
/* 259 */     File updaterFile = new File(pluginFile, "Updater");
/* 260 */     File updaterConfigFile = new File(updaterFile, "config.yml");
/*     */     
/* 262 */     YamlConfiguration config = new YamlConfiguration();
/* 263 */     config.options().header("This configuration file affects all plugins using the Updater system (version 2+ - http://forums.bukkit.org/threads/96681/ )\nIf you wish to use your API key, read http://wiki.bukkit.org/ServerMods_API and place it below.\nSome updating systems will not adhere to the disabled value, but these may be turned off in their plugin's configuration.");
/*     */     
/*     */ 
/* 266 */     config.addDefault("api-key", "PUT_API_KEY_HERE");
/* 267 */     config.addDefault("disable", Boolean.valueOf(false));
/*     */     
/* 269 */     if (!updaterFile.exists()) {
/* 270 */       fileIOOrError(updaterFile, updaterFile.mkdir(), true);
/*     */     }
/*     */     
/* 273 */     boolean createFile = !updaterConfigFile.exists();
/*     */     try {
/* 275 */       if (createFile) {
/* 276 */         fileIOOrError(updaterConfigFile, updaterConfigFile.createNewFile(), true);
/* 277 */         config.options().copyDefaults(true);
/* 278 */         config.save(updaterConfigFile);
/*     */       } else {
/* 280 */         config.load(updaterConfigFile);
/*     */       }
/*     */     } catch (Exception e) { String message;
/*     */       String message;
/* 284 */       if (createFile) {
/* 285 */         message = "The updater could not create configuration at " + updaterFile.getAbsolutePath();
/*     */       } else {
/* 287 */         message = "The updater could not load configuration at " + updaterFile.getAbsolutePath();
/*     */       }
/* 289 */       this.plugin.getLogger().log(Level.SEVERE, message, e);
/*     */     }
/*     */     
/* 292 */     if (config.getBoolean("disable")) {
/* 293 */       this.result = UpdateResult.DISABLED;
/* 294 */       return;
/*     */     }
/*     */     
/* 297 */     String key = config.getString("api-key");
/* 298 */     if (("PUT_API_KEY_HERE".equalsIgnoreCase(key)) || ("".equals(key))) {
/* 299 */       key = null;
/*     */     }
/*     */     
/* 302 */     this.apiKey = key;
/*     */     try
/*     */     {
/* 305 */       this.url = new URL("https://api.curseforge.com/servermods/files?projectIds=" + this.id);
/*     */     } catch (MalformedURLException e) {
/* 307 */       this.plugin.getLogger().log(Level.SEVERE, "The project ID provided for updating, " + this.id + " is invalid.", e);
/* 308 */       this.result = UpdateResult.FAIL_BADID;
/*     */     }
/*     */     
/* 311 */     if (this.result != UpdateResult.FAIL_BADID) {
/* 312 */       this.thread = new Thread(new UpdateRunnable(null));
/* 313 */       this.thread.start();
/*     */     } else {
/* 315 */       runUpdater();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UpdateResult getResult()
/*     */   {
/* 326 */     waitForThread();
/* 327 */     return this.result;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ReleaseType getLatestType()
/*     */   {
/* 337 */     waitForThread();
/* 338 */     if (this.versionType != null) {
/* 339 */       for (ReleaseType type : ReleaseType.values()) {
/* 340 */         if (this.versionType.equalsIgnoreCase(type.name())) {
/* 341 */           return type;
/*     */         }
/*     */       }
/*     */     }
/* 345 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLatestGameVersion()
/*     */   {
/* 354 */     waitForThread();
/* 355 */     return this.versionGameVersion;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLatestName()
/*     */   {
/* 364 */     waitForThread();
/* 365 */     return this.versionName;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLatestFileLink()
/*     */   {
/* 374 */     waitForThread();
/* 375 */     return this.versionLink;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void waitForThread()
/*     */   {
/* 383 */     if ((this.thread != null) && (this.thread.isAlive())) {
/*     */       try {
/* 385 */         this.thread.join();
/*     */       } catch (InterruptedException e) {
/* 387 */         this.plugin.getLogger().log(Level.SEVERE, null, e);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void saveFile(String file)
/*     */   {
/* 398 */     File folder = this.updateFolder;
/*     */     
/* 400 */     deleteOldFiles();
/* 401 */     if (!folder.exists()) {
/* 402 */       fileIOOrError(folder, folder.mkdir(), true);
/*     */     }
/* 404 */     downloadFile();
/*     */     
/*     */ 
/* 407 */     File dFile = new File(folder.getAbsolutePath(), file);
/* 408 */     if (dFile.getName().endsWith(".zip"))
/*     */     {
/* 410 */       unzip(dFile.getAbsolutePath());
/*     */     }
/* 412 */     if (this.announce) {
/* 413 */       this.plugin.getLogger().info("Finished updating.");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void downloadFile()
/*     */   {
/* 421 */     BufferedInputStream in = null;
/* 422 */     FileOutputStream fout = null;
/*     */     try {
/* 424 */       URL fileUrl = followRedirects(this.versionLink);
/* 425 */       int fileLength = fileUrl.openConnection().getContentLength();
/* 426 */       in = new BufferedInputStream(fileUrl.openStream());
/* 427 */       fout = new FileOutputStream(new File(this.updateFolder, this.file.getName()));
/*     */       
/* 429 */       byte[] data = new byte['Ѐ'];
/*     */       
/* 431 */       if (this.announce) {
/* 432 */         this.plugin.getLogger().info("About to download a new update: " + this.versionName);
/*     */       }
/* 434 */       long downloaded = 0L;
/* 435 */       int count; while ((count = in.read(data, 0, 1024)) != -1) {
/* 436 */         downloaded += count;
/* 437 */         fout.write(data, 0, count);
/* 438 */         int percent = (int)(downloaded * 100L / fileLength);
/* 439 */         if ((this.announce) && (percent % 10 == 0))
/* 440 */           this.plugin.getLogger().info("Downloading update: " + percent + "% of " + fileLength + " bytes.");
/*     */       }
/*     */       return;
/*     */     } catch (Exception ex) {
/* 444 */       this.plugin.getLogger().log(Level.WARNING, "The auto-updater tried to download a new update, but was unsuccessful.", ex);
/* 445 */       this.result = UpdateResult.FAIL_DOWNLOAD;
/*     */     } finally {
/*     */       try {
/* 448 */         if (in != null) {
/* 449 */           in.close();
/*     */         }
/*     */       } catch (IOException ex) {
/* 452 */         this.plugin.getLogger().log(Level.SEVERE, null, ex);
/*     */       }
/*     */       try {
/* 455 */         if (fout != null) {
/* 456 */           fout.close();
/*     */         }
/*     */       } catch (IOException ex) {
/* 459 */         this.plugin.getLogger().log(Level.SEVERE, null, ex);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private URL followRedirects(String location) throws IOException
/*     */   {
/*     */     HttpURLConnection conn;
/*     */     for (;;)
/*     */     {
/* 469 */       URL resourceUrl = new URL(location);
/* 470 */       conn = (HttpURLConnection)resourceUrl.openConnection();
/*     */       
/* 472 */       conn.setConnectTimeout(15000);
/* 473 */       conn.setReadTimeout(15000);
/* 474 */       conn.setInstanceFollowRedirects(false);
/* 475 */       conn.setRequestProperty("User-Agent", "Mozilla/5.0...");
/*     */       
/* 477 */       switch (conn.getResponseCode()) {
/*     */       case 301: 
/*     */       case 302: 
/* 480 */         String redLoc = conn.getHeaderField("Location");
/* 481 */         URL base = new URL(location);
/* 482 */         URL next = new URL(base, redLoc);
/* 483 */         location = next.toExternalForm();
/*     */       }
/*     */       
/*     */     }
/*     */     
/* 488 */     return conn.getURL();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void deleteOldFiles()
/*     */   {
/* 496 */     File[] list = listFilesOrError(this.updateFolder);
/* 497 */     for (File xFile : list) {
/* 498 */       if (xFile.getName().endsWith(".zip")) {
/* 499 */         fileIOOrError(xFile, xFile.mkdir(), true);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void unzip(String file)
/*     */   {
/* 510 */     File fSourceZip = new File(file);
/*     */     try {
/* 512 */       String zipPath = file.substring(0, file.length() - 4);
/* 513 */       ZipFile zipFile = new ZipFile(fSourceZip);
/* 514 */       Enumeration<? extends ZipEntry> e = zipFile.entries();
/* 515 */       while (e.hasMoreElements()) {
/* 516 */         ZipEntry entry = (ZipEntry)e.nextElement();
/* 517 */         File destinationFilePath = new File(zipPath, entry.getName());
/* 518 */         fileIOOrError(destinationFilePath.getParentFile(), destinationFilePath.getParentFile().mkdirs(), true);
/* 519 */         if (!entry.isDirectory()) {
/* 520 */           BufferedInputStream bis = new BufferedInputStream(zipFile.getInputStream(entry));
/*     */           
/* 522 */           byte[] buffer = new byte['Ѐ'];
/* 523 */           FileOutputStream fos = new FileOutputStream(destinationFilePath);
/* 524 */           BufferedOutputStream bos = new BufferedOutputStream(fos, 1024);
/* 525 */           int b; while ((b = bis.read(buffer, 0, 1024)) != -1) {
/* 526 */             bos.write(buffer, 0, b);
/*     */           }
/* 528 */           bos.flush();
/* 529 */           bos.close();
/* 530 */           bis.close();
/* 531 */           String name = destinationFilePath.getName();
/* 532 */           if ((name.endsWith(".jar")) && (pluginExists(name))) {
/* 533 */             File output = new File(this.updateFolder, name);
/* 534 */             fileIOOrError(output, destinationFilePath.renameTo(output), true);
/*     */           }
/*     */         }
/*     */       }
/* 538 */       zipFile.close();
/*     */       
/*     */ 
/* 541 */       moveNewZipFiles(zipPath);
/*     */     }
/*     */     catch (IOException e) {
/* 544 */       this.plugin.getLogger().log(Level.SEVERE, "The auto-updater tried to unzip a new update file, but was unsuccessful.", e);
/* 545 */       this.result = UpdateResult.FAIL_DOWNLOAD;
/*     */     } finally {
/* 547 */       fileIOOrError(fSourceZip, fSourceZip.delete(), false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private void moveNewZipFiles(String zipPath)
/*     */   {
/* 556 */     File[] list = listFilesOrError(new File(zipPath));
/* 557 */     for (File dFile : list) {
/* 558 */       if ((dFile.isDirectory()) && (pluginExists(dFile.getName())))
/*     */       {
/* 560 */         File oFile = new File(this.plugin.getDataFolder().getParent(), dFile.getName());
/*     */         
/* 562 */         File[] dList = listFilesOrError(dFile);
/*     */         
/* 564 */         File[] oList = listFilesOrError(oFile);
/* 565 */         for (File cFile : dList)
/*     */         {
/* 567 */           boolean found = false;
/* 568 */           for (File xFile : oList)
/*     */           {
/* 570 */             if (xFile.getName().equals(cFile.getName())) {
/* 571 */               found = true;
/* 572 */               break;
/*     */             }
/*     */           }
/* 575 */           if (!found)
/*     */           {
/* 577 */             File output = new File(oFile, cFile.getName());
/* 578 */             fileIOOrError(output, cFile.renameTo(output), true);
/*     */           }
/*     */           else {
/* 581 */             fileIOOrError(cFile, cFile.delete(), false);
/*     */           }
/*     */         }
/*     */       }
/* 585 */       fileIOOrError(dFile, dFile.delete(), false);
/*     */     }
/* 587 */     File zip = new File(zipPath);
/* 588 */     fileIOOrError(zip, zip.delete(), false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean pluginExists(String name)
/*     */   {
/* 598 */     File[] plugins = listFilesOrError(new File("plugins"));
/* 599 */     for (File file : plugins) {
/* 600 */       if (file.getName().equals(name)) {
/* 601 */         return true;
/*     */       }
/*     */     }
/* 604 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean versionCheck()
/*     */   {
/* 613 */     String title = this.versionName;
/* 614 */     if (this.type != UpdateType.NO_VERSION_CHECK) {
/* 615 */       String localVersion = this.plugin.getDescription().getVersion();
/* 616 */       if (title.split("^v|[\\s_-]v").length >= 2)
/*     */       {
/* 618 */         String remoteVersion = title.split("^v|[\\s_-]v")[(title.split("^v|[\\s_-]v").length - 1)].split(" ")[0];
/*     */         
/* 620 */         if ((hasTag(localVersion)) || (!shouldUpdate(localVersion, remoteVersion)))
/*     */         {
/* 622 */           this.result = UpdateResult.NO_UPDATE;
/* 623 */           return false;
/*     */         }
/*     */       }
/*     */       else {
/* 627 */         String authorInfo = " (" + (String)this.plugin.getDescription().getAuthors().get(0) + ")";
/* 628 */         this.plugin.getLogger().warning("The author of this plugin" + authorInfo + " has misconfigured their Auto Update system");
/* 629 */         this.plugin.getLogger().warning("File versions should follow the format 'PluginName vVERSION'");
/* 630 */         this.plugin.getLogger().warning("Please notify the author of this error.");
/* 631 */         this.result = UpdateResult.FAIL_NOVERSION;
/* 632 */         return false;
/*     */       }
/*     */     }
/* 635 */     return true;
/*     */   }
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
/*     */   public boolean shouldUpdate(String localVersion, String remoteVersion)
/*     */   {
/* 666 */     return !localVersion.equalsIgnoreCase(remoteVersion);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean hasTag(String version)
/*     */   {
/* 676 */     for (String string : NO_UPDATE_TAG) {
/* 677 */       if (version.contains(string)) {
/* 678 */         return true;
/*     */       }
/*     */     }
/* 681 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean read()
/*     */   {
/*     */     try
/*     */     {
/* 691 */       URLConnection conn = this.url.openConnection();
/* 692 */       conn.setConnectTimeout(5000);
/*     */       
/* 694 */       if (this.apiKey != null) {
/* 695 */         conn.addRequestProperty("X-API-Key", this.apiKey);
/*     */       }
/* 697 */       conn.addRequestProperty("User-Agent", "Updater (by Gravity)");
/*     */       
/* 699 */       conn.setDoOutput(true);
/*     */       
/* 701 */       BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
/* 702 */       String response = reader.readLine();
/*     */       
/* 704 */       JSONArray array = (JSONArray)JSONValue.parse(response);
/*     */       
/* 706 */       if (array.isEmpty()) {
/* 707 */         this.plugin.getLogger().warning("The updater could not find any files for the project id " + this.id);
/* 708 */         this.result = UpdateResult.FAIL_BADID;
/* 709 */         return false;
/*     */       }
/*     */       
/* 712 */       JSONObject latestUpdate = (JSONObject)array.get(array.size() - 1);
/* 713 */       this.versionName = ((String)latestUpdate.get("name"));
/* 714 */       this.versionLink = ((String)latestUpdate.get("downloadUrl"));
/* 715 */       this.versionType = ((String)latestUpdate.get("releaseType"));
/* 716 */       this.versionGameVersion = ((String)latestUpdate.get("gameVersion"));
/*     */       
/* 718 */       return true;
/*     */     } catch (IOException e) {
/* 720 */       if (e.getMessage().contains("HTTP response code: 403")) {
/* 721 */         this.plugin.getLogger().severe("dev.bukkit.org rejected the API key provided in plugins/Updater/config.yml");
/* 722 */         this.plugin.getLogger().severe("Please double-check your configuration to ensure it is correct.");
/* 723 */         this.result = UpdateResult.FAIL_APIKEY;
/*     */       } else {
/* 725 */         this.plugin.getLogger().severe("The updater could not contact dev.bukkit.org for updating.");
/* 726 */         this.plugin.getLogger().severe("If you have not recently modified your configuration and this is the first time you are seeing this message, the site may be experiencing temporary downtime.");
/* 727 */         this.result = UpdateResult.FAIL_DBO;
/*     */       }
/*     */     }
/* 730 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private void fileIOOrError(File file, boolean result, boolean create)
/*     */   {
/* 741 */     if (!result) {
/* 742 */       this.plugin.getLogger().severe("The updater could not " + (create ? "create" : "delete") + " file at: " + file.getAbsolutePath());
/*     */     }
/*     */   }
/*     */   
/*     */   private File[] listFilesOrError(File folder) {
/* 747 */     File[] contents = folder.listFiles();
/* 748 */     if (contents == null) {
/* 749 */       this.plugin.getLogger().severe("The updater could not access files at: " + this.updateFolder.getAbsolutePath());
/* 750 */       return new File[0];
/*     */     }
/* 752 */     return contents;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public static abstract interface UpdateCallback
/*     */   {
/*     */     public abstract void onFinish(Updater paramUpdater);
/*     */   }
/*     */   
/*     */ 
/*     */   private class UpdateRunnable
/*     */     implements Runnable
/*     */   {
/*     */     private UpdateRunnable() {}
/*     */     
/*     */ 
/*     */     public void run()
/*     */     {
/* 771 */       Updater.this.runUpdater();
/*     */     }
/*     */   }
/*     */   
/*     */   private void runUpdater() {
/* 776 */     if ((this.url != null) && (read()) && (versionCheck()))
/*     */     {
/* 778 */       if ((this.versionLink != null) && (this.type != UpdateType.NO_DOWNLOAD)) {
/* 779 */         String name = this.file.getName();
/*     */         
/* 781 */         if (this.versionLink.endsWith(".zip")) {
/* 782 */           name = this.versionLink.substring(this.versionLink.lastIndexOf("/") + 1);
/*     */         }
/* 784 */         saveFile(name);
/*     */       } else {
/* 786 */         this.result = UpdateResult.UPDATE_AVAILABLE;
/*     */       }
/*     */     }
/*     */     
/* 790 */     if (this.callback != null)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 796 */       new BukkitRunnable()
/*     */       {
/* 794 */         public void run() { Updater.this.runCallback(); } }
/*     */       
/* 796 */         .runTask(this.plugin);
/*     */     }
/*     */   }
/*     */   
/*     */   private void runCallback() {
/* 801 */     this.callback.onFinish(this);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\util\Updater.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */