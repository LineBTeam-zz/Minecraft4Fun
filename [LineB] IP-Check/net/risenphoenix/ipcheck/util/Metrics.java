/*     */ package net.risenphoenix.ipcheck.util;
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
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedHashSet;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import java.util.zip.GZIPOutputStream;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
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
/*  91 */   private final Set<Graph> graphs = Collections.synchronizedSet(new HashSet());
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
/* 116 */   private final Object optOutLock = new Object();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 121 */   private volatile BukkitTask task = null;
/*     */   
/*     */   public Metrics(Plugin plugin) throws IOException {
/* 124 */     if (plugin == null) {
/* 125 */       throw new IllegalArgumentException("Plugin cannot be null");
/*     */     }
/*     */     
/* 128 */     this.plugin = plugin;
/*     */     
/*     */ 
/* 131 */     this.configurationFile = getConfigFile();
/* 132 */     this.configuration = YamlConfiguration.loadConfiguration(this.configurationFile);
/*     */     
/*     */ 
/* 135 */     this.configuration.addDefault("opt-out", Boolean.valueOf(false));
/* 136 */     this.configuration.addDefault("guid", UUID.randomUUID().toString());
/* 137 */     this.configuration.addDefault("debug", Boolean.valueOf(false));
/*     */     
/*     */ 
/* 140 */     if (this.configuration.get("guid", null) == null) {
/* 141 */       this.configuration.options().header("http://mcstats.org").copyDefaults(true);
/* 142 */       this.configuration.save(this.configurationFile);
/*     */     }
/*     */     
/*     */ 
/* 146 */     this.guid = this.configuration.getString("guid");
/* 147 */     this.debug = this.configuration.getBoolean("debug", false);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Graph createGraph(String name)
/*     */   {
/* 158 */     if (name == null) {
/* 159 */       throw new IllegalArgumentException("Graph name cannot be null");
/*     */     }
/*     */     
/*     */ 
/* 163 */     Graph graph = new Graph(name, null);
/*     */     
/*     */ 
/* 166 */     this.graphs.add(graph);
/*     */     
/*     */ 
/* 169 */     return graph;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addGraph(Graph graph)
/*     */   {
/* 178 */     if (graph == null) {
/* 179 */       throw new IllegalArgumentException("Graph cannot be null");
/*     */     }
/*     */     
/* 182 */     this.graphs.add(graph);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean start()
/*     */   {
/* 193 */     synchronized (this.optOutLock)
/*     */     {
/* 195 */       if (isOptOut()) {
/* 196 */         return false;
/*     */       }
/*     */       
/*     */ 
/* 200 */       if (this.task != null) {
/* 201 */         return true;
/*     */       }
/*     */       
/*     */ 
/* 205 */       this.task = this.plugin.getServer().getScheduler().runTaskTimerAsynchronously(this.plugin, new Runnable()
/*     */       {
/* 207 */         private boolean firstPost = true;
/*     */         
/*     */         public void run()
/*     */         {
/*     */           try {
/* 212 */             synchronized (Metrics.this.optOutLock)
/*     */             {
/* 214 */               if ((Metrics.this.isOptOut()) && (Metrics.this.task != null)) {
/* 215 */                 Metrics.this.task.cancel();
/* 216 */                 Metrics.this.task = null;
/*     */                 
/* 218 */                 for (Metrics.Graph graph : Metrics.this.graphs) {
/* 219 */                   graph.onOptOut();
/*     */                 }
/*     */               }
/*     */             }
/*     */             
/*     */ 
/*     */ 
/*     */ 
/* 227 */             Metrics.this.postPlugin(!this.firstPost);
/*     */             
/*     */ 
/*     */ 
/* 231 */             this.firstPost = false;
/*     */           } catch (IOException e) {
/* 233 */             if (Metrics.this.debug)
/* 234 */               Bukkit.getLogger().log(Level.INFO, "[Metrics] " + e.getMessage()); } } }, 0L, 18000L);
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 240 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isOptOut()
/*     */   {
/* 250 */     synchronized (this.optOutLock)
/*     */     {
/*     */       try {
/* 253 */         this.configuration.load(getConfigFile());
/*     */       } catch (IOException ex) {
/* 255 */         if (this.debug) {
/* 256 */           Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
/*     */         }
/* 258 */         return true;
/*     */       } catch (InvalidConfigurationException ex) {
/* 260 */         if (this.debug) {
/* 261 */           Bukkit.getLogger().log(Level.INFO, "[Metrics] " + ex.getMessage());
/*     */         }
/* 263 */         return true;
/*     */       }
/* 265 */       return this.configuration.getBoolean("opt-out", false);
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
/* 276 */     synchronized (this.optOutLock)
/*     */     {
/* 278 */       if (isOptOut()) {
/* 279 */         this.configuration.set("opt-out", Boolean.valueOf(false));
/* 280 */         this.configuration.save(this.configurationFile);
/*     */       }
/*     */       
/*     */ 
/* 284 */       if (this.task == null) {
/* 285 */         start();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void disable()
/*     */     throws IOException
/*     */   {
/* 297 */     synchronized (this.optOutLock)
/*     */     {
/* 299 */       if (!isOptOut()) {
/* 300 */         this.configuration.set("opt-out", Boolean.valueOf(true));
/* 301 */         this.configuration.save(this.configurationFile);
/*     */       }
/*     */       
/*     */ 
/* 305 */       if (this.task != null) {
/* 306 */         this.task.cancel();
/* 307 */         this.task = null;
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
/* 323 */     File pluginsFolder = this.plugin.getDataFolder().getParentFile();
/*     */     
/*     */ 
/* 326 */     return new File(new File(pluginsFolder, "PluginMetrics"), "config.yml");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private void postPlugin(boolean isPing)
/*     */     throws IOException
/*     */   {
/* 334 */     PluginDescriptionFile description = this.plugin.getDescription();
/* 335 */     String pluginName = description.getName();
/* 336 */     boolean onlineMode = Bukkit.getServer().getOnlineMode();
/* 337 */     String pluginVersion = description.getVersion();
/* 338 */     String serverVersion = Bukkit.getVersion();
/*     */     
/*     */ 
/* 341 */     int playersOnline = IPCheck.getInstance().getOnlinePlayers().length;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 346 */     StringBuilder json = new StringBuilder(1024);
/* 347 */     json.append('{');
/*     */     
/*     */ 
/* 350 */     appendJSONPair(json, "guid", this.guid);
/* 351 */     appendJSONPair(json, "plugin_version", pluginVersion);
/* 352 */     appendJSONPair(json, "server_version", serverVersion);
/* 353 */     appendJSONPair(json, "players_online", Integer.toString(playersOnline));
/*     */     
/*     */ 
/* 356 */     String osname = System.getProperty("os.name");
/* 357 */     String osarch = System.getProperty("os.arch");
/* 358 */     String osversion = System.getProperty("os.version");
/* 359 */     String java_version = System.getProperty("java.version");
/* 360 */     int coreCount = Runtime.getRuntime().availableProcessors();
/*     */     
/*     */ 
/* 363 */     if (osarch.equals("amd64")) {
/* 364 */       osarch = "x86_64";
/*     */     }
/*     */     
/* 367 */     appendJSONPair(json, "osname", osname);
/* 368 */     appendJSONPair(json, "osarch", osarch);
/* 369 */     appendJSONPair(json, "osversion", osversion);
/* 370 */     appendJSONPair(json, "cores", Integer.toString(coreCount));
/* 371 */     appendJSONPair(json, "auth_mode", onlineMode ? "1" : "0");
/* 372 */     appendJSONPair(json, "java_version", java_version);
/*     */     
/*     */ 
/* 375 */     if (isPing) {
/* 376 */       appendJSONPair(json, "ping", "1");
/*     */     }
/*     */     
/* 379 */     if (this.graphs.size() > 0) {
/* 380 */       synchronized (this.graphs) {
/* 381 */         json.append(',');
/* 382 */         json.append('"');
/* 383 */         json.append("graphs");
/* 384 */         json.append('"');
/* 385 */         json.append(':');
/* 386 */         json.append('{');
/*     */         
/* 388 */         boolean firstGraph = true;
/*     */         
/* 390 */         Iterator<Graph> iter = this.graphs.iterator();
/*     */         
/* 392 */         while (iter.hasNext()) {
/* 393 */           Graph graph = (Graph)iter.next();
/*     */           
/* 395 */           StringBuilder graphJson = new StringBuilder();
/* 396 */           graphJson.append('{');
/*     */           
/* 398 */           for (Plotter plotter : graph.getPlotters()) {
/* 399 */             appendJSONPair(graphJson, plotter.getColumnName(), Integer.toString(plotter.getValue()));
/*     */           }
/*     */           
/* 402 */           graphJson.append('}');
/*     */           
/* 404 */           if (!firstGraph) {
/* 405 */             json.append(',');
/*     */           }
/*     */           
/* 408 */           json.append(escapeJSON(graph.getName()));
/* 409 */           json.append(':');
/* 410 */           json.append(graphJson);
/*     */           
/* 412 */           firstGraph = false;
/*     */         }
/*     */         
/* 415 */         json.append('}');
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 420 */     json.append('}');
/*     */     
/*     */ 
/* 423 */     URL url = new URL("http://report.mcstats.org" + String.format("/plugin/%s", new Object[] { urlEncode(pluginName) }));
/*     */     
/*     */ 
/*     */     URLConnection connection;
/*     */     
/*     */     URLConnection connection;
/*     */     
/* 430 */     if (isMineshafterPresent()) {
/* 431 */       connection = url.openConnection(Proxy.NO_PROXY);
/*     */     } else {
/* 433 */       connection = url.openConnection();
/*     */     }
/*     */     
/*     */ 
/* 437 */     byte[] uncompressed = json.toString().getBytes();
/* 438 */     byte[] compressed = gzip(json.toString());
/*     */     
/*     */ 
/* 441 */     connection.addRequestProperty("User-Agent", "MCStats/7");
/* 442 */     connection.addRequestProperty("Content-Type", "application/json");
/* 443 */     connection.addRequestProperty("Content-Encoding", "gzip");
/* 444 */     connection.addRequestProperty("Content-Length", Integer.toString(compressed.length));
/* 445 */     connection.addRequestProperty("Accept", "application/json");
/* 446 */     connection.addRequestProperty("Connection", "close");
/*     */     
/* 448 */     connection.setDoOutput(true);
/*     */     
/* 450 */     if (this.debug) {
/* 451 */       System.out.println("[Metrics] Prepared request for " + pluginName + " uncompressed=" + uncompressed.length + " compressed=" + compressed.length);
/*     */     }
/*     */     
/*     */ 
/* 455 */     OutputStream os = connection.getOutputStream();
/* 456 */     os.write(compressed);
/* 457 */     os.flush();
/*     */     
/*     */ 
/* 460 */     BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
/* 461 */     String response = reader.readLine();
/*     */     
/*     */ 
/* 464 */     os.close();
/* 465 */     reader.close();
/*     */     
/* 467 */     if ((response == null) || (response.startsWith("ERR")) || (response.startsWith("7"))) {
/* 468 */       if (response == null) {
/* 469 */         response = "null";
/* 470 */       } else if (response.startsWith("7")) {
/* 471 */         response = response.substring(response.startsWith("7,") ? 2 : 1);
/*     */       }
/*     */       
/* 474 */       throw new IOException(response);
/*     */     }
/*     */     
/* 477 */     if ((response.equals("1")) || (response.contains("This is your first update this hour"))) {
/* 478 */       synchronized (this.graphs) {
/* 479 */         Iterator<Graph> iter = this.graphs.iterator();
/*     */         
/* 481 */         while (iter.hasNext()) {
/* 482 */           Graph graph = (Graph)iter.next();
/*     */           
/* 484 */           for (Plotter plotter : graph.getPlotters()) {
/* 485 */             plotter.reset();
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
/*     */   public static byte[] gzip(String input)
/*     */   {
/* 500 */     baos = new ByteArrayOutputStream();
/* 501 */     GZIPOutputStream gzos = null;
/*     */     try
/*     */     {
/* 504 */       gzos = new GZIPOutputStream(baos);
/* 505 */       gzos.write(input.getBytes("UTF-8"));
/*     */       
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 515 */       return baos.toByteArray();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 507 */       e.printStackTrace();
/*     */     } finally {
/* 509 */       if (gzos != null) {
/* 510 */         try { gzos.close();
/*     */         }
/*     */         catch (IOException localIOException3) {}
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isMineshafterPresent()
/*     */   {
/*     */     try
/*     */     {
/* 525 */       Class.forName("mineshafter.MineServer");
/* 526 */       return true;
/*     */     } catch (Exception e) {}
/* 528 */     return false;
/*     */   }
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
/* 541 */     boolean isValueNumeric = false;
/*     */     try
/*     */     {
/* 544 */       if ((value.equals("0")) || (!value.endsWith("0"))) {
/* 545 */         Double.parseDouble(value);
/* 546 */         isValueNumeric = true;
/*     */       }
/*     */     } catch (NumberFormatException e) {
/* 549 */       isValueNumeric = false;
/*     */     }
/*     */     
/* 552 */     if (json.charAt(json.length() - 1) != '{') {
/* 553 */       json.append(',');
/*     */     }
/*     */     
/* 556 */     json.append(escapeJSON(key));
/* 557 */     json.append(':');
/*     */     
/* 559 */     if (isValueNumeric) {
/* 560 */       json.append(value);
/*     */     } else {
/* 562 */       json.append(escapeJSON(value));
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
/* 573 */     StringBuilder builder = new StringBuilder();
/*     */     
/* 575 */     builder.append('"');
/* 576 */     for (int index = 0; index < text.length(); index++) {
/* 577 */       char chr = text.charAt(index);
/*     */       
/* 579 */       switch (chr) {
/*     */       case '"': 
/*     */       case '\\': 
/* 582 */         builder.append('\\');
/* 583 */         builder.append(chr);
/* 584 */         break;
/*     */       case '\b': 
/* 586 */         builder.append("\\b");
/* 587 */         break;
/*     */       case '\t': 
/* 589 */         builder.append("\\t");
/* 590 */         break;
/*     */       case '\n': 
/* 592 */         builder.append("\\n");
/* 593 */         break;
/*     */       case '\r': 
/* 595 */         builder.append("\\r");
/* 596 */         break;
/*     */       default: 
/* 598 */         if (chr < ' ') {
/* 599 */           String t = "000" + Integer.toHexString(chr);
/* 600 */           builder.append("\\u" + t.substring(t.length() - 4));
/*     */         } else {
/* 602 */           builder.append(chr);
/*     */         }
/*     */         break;
/*     */       }
/*     */     }
/* 607 */     builder.append('"');
/*     */     
/* 609 */     return builder.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private static String urlEncode(String text)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 619 */     return URLEncoder.encode(text, "UTF-8");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static class Graph
/*     */   {
/*     */     private final String name;
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 636 */     private final Set<Metrics.Plotter> plotters = new LinkedHashSet();
/*     */     
/*     */     private Graph(String name) {
/* 639 */       this.name = name;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getName()
/*     */     {
/* 648 */       return this.name;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void addPlotter(Metrics.Plotter plotter)
/*     */     {
/* 657 */       this.plotters.add(plotter);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void removePlotter(Metrics.Plotter plotter)
/*     */     {
/* 666 */       this.plotters.remove(plotter);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Set<Metrics.Plotter> getPlotters()
/*     */     {
/* 675 */       return Collections.unmodifiableSet(this.plotters);
/*     */     }
/*     */     
/*     */     public int hashCode()
/*     */     {
/* 680 */       return this.name.hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object object)
/*     */     {
/* 685 */       if (!(object instanceof Graph)) {
/* 686 */         return false;
/*     */       }
/*     */       
/* 689 */       Graph graph = (Graph)object;
/* 690 */       return graph.name.equals(this.name);
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     protected void onOptOut() {}
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
/* 714 */       this("Default");
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public Plotter(String name)
/*     */     {
/* 723 */       this.name = name;
/*     */     }
/*     */     
/*     */ 
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
/*     */ 
/*     */ 
/*     */     public String getColumnName()
/*     */     {
/* 741 */       return this.name;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public void reset() {}
/*     */     
/*     */ 
/*     */ 
/*     */     public int hashCode()
/*     */     {
/* 752 */       return getColumnName().hashCode();
/*     */     }
/*     */     
/*     */     public boolean equals(Object object)
/*     */     {
/* 757 */       if (!(object instanceof Plotter)) {
/* 758 */         return false;
/*     */       }
/*     */       
/* 761 */       Plotter plotter = (Plotter)object;
/* 762 */       return (plotter.name.equals(this.name)) && (plotter.getValue() == getValue());
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\util\Metrics.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */