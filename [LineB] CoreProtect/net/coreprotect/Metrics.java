/*     */ package net.coreprotect;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.Proxy;
/*     */ import java.net.URL;
/*     */ import java.net.URLConnection;
/*     */ import java.net.URLEncoder;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.configuration.InvalidConfigurationException;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfigurationOptions;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.scheduler.BukkitTask;
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
/*     */ public class Metrics
/*     */ {
/*     */   private static final int REVISION = 7;
/*     */   private static final String BASE_URL = "http://report.mcstats.org";
/*     */   private static final String REPORT_URL = "/plugin/%s";
/*     */   private static final int PING_INTERVAL = 15;
/*     */   private final Plugin plugin;
/*     */   
/*     */   public static class Graph
/*     */   {
/*     */     private final String name;
/*  74 */     private final Set<Metrics.Plotter> plotters = new LinkedHashSet();
/*     */     
/*     */     private Graph(String name) {
/*  77 */       this.name = name;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void addPlotter(Metrics.Plotter plotter)
/*     */     {
/*  87 */       this.plotters.add(plotter);
/*     */     }
/*     */     
/*     */     public boolean equals(Object object)
/*     */     {
/*  92 */       if (!(object instanceof Graph)) {
/*  93 */         return false;
/*     */       }
/*     */       
/*  96 */       Graph graph = (Graph)object;
/*  97 */       return graph.name.equals(this.name);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getName()
/*     */     {
/* 106 */       return this.name;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Set<Metrics.Plotter> getPlotters()
/*     */     {
/* 115 */       return Collections.unmodifiableSet(this.plotters);
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 120 */       return this.name.hashCode();
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected void onOptOut() {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void removePlotter(Metrics.Plotter plotter)
/*     */     {
/* 136 */       this.plotters.remove(plotter);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static abstract class Plotter
/*     */   {
/*     */     private final String name;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Plotter()
/*     */     {
/* 154 */       this("Default");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Plotter(String name)
/*     */     {
/* 164 */       this.name = name;
/*     */     }
/*     */     
/*     */     public boolean equals(Object object)
/*     */     {
/* 169 */       if (!(object instanceof Plotter)) {
/* 170 */         return false;
/*     */       }
/*     */       
/* 173 */       Plotter plotter = (Plotter)object;
/* 174 */       return (plotter.name.equals(this.name)) && (plotter.getValue() == getValue());
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getColumnName()
/*     */     {
/* 183 */       return this.name;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public abstract int getValue();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 197 */       return getColumnName().hashCode();
/*     */     }
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
/*     */     public void reset() {}
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
/*     */   private static void appendJSONPair(StringBuilder json, String key, String value)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 236 */     boolean isValueNumeric = false;
/*     */     try
/*     */     {
/* 239 */       if ((value.equals("0")) || (!value.endsWith("0"))) {
/* 240 */         Double.parseDouble(value);
/* 241 */         isValueNumeric = true;
/*     */       }
/*     */     }
/*     */     catch (NumberFormatException e) {
/* 245 */       isValueNumeric = false;
/*     */     }
/*     */     
/* 248 */     if (json.charAt(json.length() - 1) != '{') {
/* 249 */       json.append(',');
/*     */     }
/*     */     
/* 252 */     json.append(escapeJSON(key));
/* 253 */     json.append(':');
/*     */     
/* 255 */     if (isValueNumeric) {
/* 256 */       json.append(value);
/*     */     }
/*     */     else {
/* 259 */       json.append(escapeJSON(value));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String escapeJSON(String text)
/*     */   {
/* 270 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 272 */     builder.append('"');
/* 273 */     for (int index = 0; index < text.length(); index++) {
/* 274 */       char chr = text.charAt(index);
/*     */       
/* 276 */       switch (chr) {
/*     */       case '"': 
/*     */       case '\\': 
/* 279 */         builder.append('\\');
/* 280 */         builder.append(chr);
/* 281 */         break;
/*     */       case '\b': 
/* 283 */         builder.append("\\b");
/* 284 */         break;
/*     */       case '\t': 
/* 286 */         builder.append("\\t");
/* 287 */         break;
/*     */       case '\n': 
/* 289 */         builder.append("\\n");
/* 290 */         break;
/*     */       case '\r': 
/* 292 */         builder.append("\\r");
/* 293 */         break;
/*     */       default: 
/* 295 */         if (chr < ' ') {
/* 296 */           String t = "000" + Integer.toHexString(chr);
/* 297 */           builder.append("\\u" + t.substring(t.length() - 4));
/*     */         }
/*     */         else {
/* 300 */           builder.append(chr);
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/* 305 */     builder.append('"');
/*     */     
/* 307 */     return builder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static byte[] gzip(String input)
/*     */   {
/* 317 */     baos = new ByteArrayOutputStream();
/* 318 */     GZIPOutputStream gzos = null;
/*     */     try
/*     */     {
/* 321 */       gzos = new GZIPOutputStream(baos);
/* 322 */       gzos.write(input.getBytes("UTF-8"));
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
/* 337 */       return baos.toByteArray();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 325 */       e.printStackTrace();
/*     */     }
/*     */     finally {
/* 328 */       if (gzos != null) {
/*     */         try {
/* 330 */           gzos.close();
/*     */         }
/*     */         catch (IOException ignore) {}
/*     */       }
/*     */     }
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
/*     */   private static String urlEncode(String text)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 348 */     return URLEncoder.encode(text, "UTF-8");
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
/* 359 */   private final Set<Graph> graphs = Collections.synchronizedSet(new HashSet());
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final YamlConfiguration configuration;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final File configurationFile;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final String guid;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final boolean debug;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 384 */   private final Object optOutLock = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 389 */   private volatile BukkitTask task = null;
/*     */   
/*     */   public Metrics(Plugin plugin) throws IOException {
/* 392 */     if (plugin == null) {
/* 393 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/*     */     
/* 396 */     this.plugin = plugin;
/*     */     
/*     */ 
/* 399 */     this.configurationFile = getConfigFile();
/* 400 */     this.configuration = YamlConfiguration.loadConfiguration(this.configurationFile);
/*     */     
/*     */ 
/* 403 */     this.configuration.addDefault("opt-out", Boolean.valueOf(false));
/* 404 */     this.configuration.addDefault("guid", UUID.randomUUID().toString());
/* 405 */     this.configuration.addDefault("debug", Boolean.valueOf(false));
/*     */     
/*     */ 
/* 408 */     if (this.configuration.get("guid", null) == null) {
/* 409 */       this.configuration.options().header("http://mcstats.org").copyDefaults(true);
/* 410 */       this.configuration.save(this.configurationFile);
/*     */     }
/*     */     
/*     */ 
/* 414 */     this.guid = this.configuration.getString("guid");
/* 415 */     this.debug = this.configuration.getBoolean("debug", false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addGraph(Graph graph)
/*     */   {
/* 425 */     if (graph == null) {
/* 426 */       throw new IllegalArgumentException("Graph cannot be null");
/*     */     }
/*     */     
/* 429 */     this.graphs.add(graph);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Graph createGraph(String name)
/*     */   {
/* 441 */     if (name == null) {
/* 442 */       throw new IllegalArgumentException("Graph name cannot be null");
/*     */     }
/*     */     
/*     */ 
/* 446 */     Graph graph = new Graph(name, null);
/*     */     
/*     */ 
/* 449 */     this.graphs.add(graph);
/*     */     
/*     */ 
/* 452 */     return graph;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void disable()
/*     */     throws IOException
/*     */   {
/* 462 */     synchronized (this.optOutLock)
/*     */     {
/* 464 */       if (!isOptOut()) {
/* 465 */         this.configuration.set("opt-out", Boolean.valueOf(true));
/* 466 */         this.configuration.save(this.configurationFile);
/*     */       }
/*     */       
/*     */ 
/* 470 */       if (this.task != null) {
/* 471 */         this.task.cancel();
/* 472 */         this.task = null;
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void enable()
/*     */     throws IOException
/*     */   {
/* 484 */     synchronized (this.optOutLock)
/*     */     {
/* 486 */       if (isOptOut()) {
/* 487 */         this.configuration.set("opt-out", Boolean.valueOf(false));
/* 488 */         this.configuration.save(this.configurationFile);
/*     */       }
/*     */       
/*     */ 
/* 492 */       if (this.task == null) {
/* 493 */         start();
/*     */       }
/*     */     }
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
/*     */   public File getConfigFile()
/*     */   {
/* 509 */     File pluginsFolder = this.plugin.getDataFolder().getParentFile();
/*     */     
/*     */ 
/* 512 */     return new File(new File(pluginsFolder, "PluginMetrics"), "config.yml");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isMineshafterPresent()
/*     */   {
/*     */     try
/*     */     {
/* 522 */       Class.forName("mineshafter.MineServer");
/* 523 */       return true;
/*     */     }
/*     */     catch (Exception e) {}
/* 526 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isOptOut()
/*     */   {
/* 536 */     synchronized (this.optOutLock)
/*     */     {
/*     */       try {
/* 539 */         this.configuration.load(getConfigFile());
/*     */       }
/*     */       catch (IOException ex) {
/* 542 */         if (this.debug) {
/* 543 */           Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
/*     */         }
/* 545 */         return true;
/*     */       }
/*     */       catch (InvalidConfigurationException ex) {
/* 548 */         if (this.debug) {
/* 549 */           Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
/*     */         }
/* 551 */         return true;
/*     */       }
/* 553 */       return this.configuration.getBoolean("opt-out", false);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void postPlugin(boolean isPing)
/*     */     throws IOException
/*     */   {
/* 562 */     PluginDescriptionFile description = this.plugin.getDescription();
/* 563 */     String pluginName = description.getName();
/* 564 */     boolean onlineMode = Bukkit.getServer().getOnlineMode();
/* 565 */     String pluginVersion = description.getVersion();
/* 566 */     String serverVersion = Bukkit.getVersion();
/* 567 */     int playersOnline = Bukkit.getServer().getOnlinePlayers().size();
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 572 */     StringBuilder json = new StringBuilder(1024);
/* 573 */     json.append('{');
/*     */     
/*     */ 
/* 576 */     appendJSONPair(json, "guid", this.guid);
/* 577 */     appendJSONPair(json, "plugin_version", pluginVersion);
/* 578 */     appendJSONPair(json, "server_version", serverVersion);
/* 579 */     appendJSONPair(json, "players_online", Integer.toString(playersOnline));
/*     */     
/*     */ 
/* 582 */     String osname = System.getProperty("os.name");
/* 583 */     String osarch = System.getProperty("os.arch");
/* 584 */     String osversion = System.getProperty("os.version");
/* 585 */     String java_version = System.getProperty("java.version");
/* 586 */     int coreCount = Runtime.getRuntime().availableProcessors();
/*     */     
/*     */ 
/* 589 */     if (osarch.equals("amd64")) {
/* 590 */       osarch = "x86_64";
/*     */     }
/*     */     
/* 593 */     appendJSONPair(json, "osname", osname);
/* 594 */     appendJSONPair(json, "osarch", osarch);
/* 595 */     appendJSONPair(json, "osversion", osversion);
/* 596 */     appendJSONPair(json, "cores", Integer.toString(coreCount));
/* 597 */     appendJSONPair(json, "auth_mode", onlineMode ? "1" : "0");
/* 598 */     appendJSONPair(json, "java_version", java_version);
/*     */     
/*     */ 
/* 601 */     if (isPing) {
/* 602 */       appendJSONPair(json, "ping", "1");
/*     */     }
/*     */     
/* 605 */     if (this.graphs.size() > 0) {
/* 606 */       synchronized (this.graphs) {
/* 607 */         json.append(',');
/* 608 */         json.append('"');
/* 609 */         json.append("graphs");
/* 610 */         json.append('"');
/* 611 */         json.append(':');
/* 612 */         json.append('{');
/*     */         
/* 614 */         boolean firstGraph = true;
/*     */         
/* 616 */         Iterator<Graph> iter = this.graphs.iterator();
/*     */         
/* 618 */         while (iter.hasNext()) {
/* 619 */           Graph graph = (Graph)iter.next();
/*     */           
/* 621 */           StringBuilder graphJson = new StringBuilder();
/* 622 */           graphJson.append('{');
/*     */           
/* 624 */           for (Plotter plotter : graph.getPlotters()) {
/* 625 */             appendJSONPair(graphJson, plotter.getColumnName(), Integer.toString(plotter.getValue()));
/*     */           }
/*     */           
/* 628 */           graphJson.append('}');
/*     */           
/* 630 */           if (!firstGraph) {
/* 631 */             json.append(',');
/*     */           }
/*     */           
/* 634 */           json.append(escapeJSON(graph.getName()));
/* 635 */           json.append(':');
/* 636 */           json.append(graphJson);
/*     */           
/* 638 */           firstGraph = false;
/*     */         }
/*     */         
/* 641 */         json.append('}');
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 646 */     json.append('}');
/*     */     
/*     */ 
/* 649 */     URL url = new URL("http://report.mcstats.org" + String.format("/plugin/%s", new Object[] { urlEncode(pluginName) }));
/*     */     
/*     */ 
/*     */     URLConnection connection;
/*     */     
/*     */     URLConnection connection;
/*     */     
/* 656 */     if (isMineshafterPresent()) {
/* 657 */       connection = url.openConnection(Proxy.NO_PROXY);
/*     */     }
/*     */     else {
/* 660 */       connection = url.openConnection();
/*     */     }
/*     */     
/* 663 */     byte[] uncompressed = json.toString().getBytes();
/* 664 */     byte[] compressed = gzip(json.toString());
/*     */     
/*     */ 
/* 667 */     connection.addRequestProperty("User-Agent", "MCStats/7");
/* 668 */     connection.addRequestProperty("Content-Type", "application/json");
/* 669 */     connection.addRequestProperty("Content-Encoding", "gzip");
/* 670 */     connection.addRequestProperty("Content-Length", Integer.toString(compressed.length));
/* 671 */     connection.addRequestProperty("Accept", "application/json");
/* 672 */     connection.addRequestProperty("Connection", "close");
/*     */     
/* 674 */     connection.setDoOutput(true);
/*     */     
/* 676 */     if (this.debug) {
/* 677 */       System.out.println("[Metrics] Prepared request for " + pluginName + " uncompressed=" + uncompressed.length + " compressed=" + compressed.length);
/*     */     }
/*     */     
/*     */ 
/* 681 */     OutputStream os = connection.getOutputStream();
/* 682 */     os.write(compressed);
/* 683 */     os.flush();
/*     */     
/*     */ 
/* 686 */     BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 687 */     String response = reader.readLine();
/*     */     
/*     */ 
/* 690 */     os.close();
/* 691 */     reader.close();
/*     */     
/* 693 */     if ((response == null) || (response.startsWith("ERR")) || (response.startsWith("7"))) {
/* 694 */       if (response == null) {
/* 695 */         response = "null";
/*     */       }
/* 697 */       else if (response.startsWith("7")) {
/* 698 */         response = response.substring(response.startsWith("7,") ? 2 : 1);
/*     */       }
/*     */       
/* 701 */       throw new IOException(response);
/*     */     }
/*     */     
/*     */ 
/* 705 */     if ((response.equals("1")) || (response.contains("This is your first update this hour"))) {
/* 706 */       synchronized (this.graphs) {
/* 707 */         Iterator<Graph> iter = this.graphs.iterator();
/*     */         
/* 709 */         while (iter.hasNext()) {
/* 710 */           Graph graph = (Graph)iter.next();
/*     */           
/* 712 */           for (Plotter plotter : graph.getPlotters()) {
/* 713 */             plotter.reset();
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean start()
/*     */   {
/* 729 */     synchronized (this.optOutLock)
/*     */     {
/* 731 */       if (isOptOut()) {
/* 732 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 736 */       if (this.task != null) {
/* 737 */         return true;
/*     */       }
/*     */       
/*     */ 
/* 741 */       this.task = this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(this.plugin, new Runnable()
/*     */       {
/* 743 */         private boolean firstPost = true;
/*     */         
/*     */         public void run()
/*     */         {
/*     */           try
/*     */           {
/* 749 */             synchronized (Metrics.this.optOutLock)
/*     */             {
/* 751 */               if ((Metrics.this.isOptOut()) && (Metrics.this.task != null)) {
/* 752 */                 Metrics.this.task.cancel();
/* 753 */                 Metrics.this.task = null;
/*     */                 
/* 755 */                 for (Metrics.Graph graph : Metrics.this.graphs) {
/* 756 */                   graph.onOptOut();
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 764 */             Metrics.this.postPlugin(!this.firstPost);
/*     */             
/*     */ 
/*     */ 
/* 768 */             this.firstPost = false;
/*     */           }
/*     */           catch (IOException e) {
/* 771 */             if (Metrics.this.debug)
/* 772 */               Bukkit.getLogger().log(Level.INFO, "[Metrics] " + e.getMessage()); } } }, 0L, 18000L);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 778 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\Metrics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */