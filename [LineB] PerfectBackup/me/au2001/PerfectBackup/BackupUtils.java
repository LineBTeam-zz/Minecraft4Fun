/*     */ package me.au2001.PerfectBackup;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.nio.file.Files;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.zip.ZipEntry;
/*     */ import java.util.zip.ZipInputStream;
/*     */ import java.util.zip.ZipOutputStream;
/*     */ import org.apache.commons.net.ftp.FTPClient;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ 
/*     */ public class BackupUtils
/*     */ {
/*     */   public static void saveToFTP(File file) throws Exception
/*     */   {
/*  26 */     FTPClient ftp = new FTPClient();
/*     */     
/*  28 */     if (BackupRestorer.log) System.out.println(BackupRestorer.prefix + "Conectando ao server ftp..");
/*  29 */     ftp.connect(PerfectBackup.config.getString("ftp.host"), PerfectBackup.config.getInt("ftp.port"));
/*  30 */     if (BackupRestorer.log) System.out.println(BackupRestorer.prefix + "Autenticando...");
/*  31 */     ftp.login(PerfectBackup.config.getString("ftp.user"), PerfectBackup.config.getString("ftp.pass"));
/*  32 */     ftp.enterLocalPassiveMode();
/*  33 */     ftp.setFileType(2);
/*     */     
/*  35 */     boolean done = false;
/*  36 */     if (org.apache.commons.net.ftp.FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
/*  37 */       String path = PerfectBackup.config.getString("ftp.path");
/*  38 */       if (!path.endsWith("/")) path = path + "/";
/*  39 */       ftp.mkd(path);
/*     */       
/*  41 */       if (BackupRestorer.log) System.out.println(BackupRestorer.prefix + "Fazendo upload para o server...");
/*  42 */       InputStream is = new FileInputStream(file);
/*  43 */       if (ftp.storeFile(path + file.getName(), is)) done = true;
/*  44 */       is.close();
/*     */     }
/*     */     
/*  47 */     if (ftp.isConnected()) {
/*  48 */       ftp.logout();
/*  49 */       ftp.disconnect();
/*     */     }
/*  51 */     if (!done) throw new IOException(ftp.getReplyString());
/*     */   }
/*     */   
/*     */   public static File createBackup() {
/*  55 */     File backup = null;
/*     */     try {
/*  57 */       String name = PerfectBackup.config.getString("backupformat").replace("{DATE}", dateToString(System.currentTimeMillis()));
/*  58 */       if (PerfectBackup.config.getBoolean("zipbackups", false)) {
/*  59 */         backup = Files.createTempDirectory("PerfectBackup", new java.nio.file.attribute.FileAttribute[0]).toFile();
/*     */       } else {
/*  61 */         backup = new File(PerfectBackup.backupfolder, name);
/*  62 */         for (int i = 1; backup.exists(); i++) backup = new File(PerfectBackup.backupfolder, name + " (" + i + ")");
/*     */       }
/*  64 */       setFilePerms(backup);
/*  65 */       backup.mkdirs();
/*  66 */       setFilePerms(backup);
/*     */       
/*  68 */       if (BackupRestorer.log) System.out.println(BackupRestorer.prefix + "Copiando pastas para o backup...");
/*     */       Object localObject;
/*  70 */       int j; int i; if (PerfectBackup.config.getBoolean("backup.jarfile", false)) {
/*  71 */         j = (localObject = new File(".").listFiles()).length; for (i = 0; i < j; i++) { File file = localObject[i];
/*  72 */           if ((file.isFile()) && (file.getName().endsWith(".jar")))
/*  73 */             copyFile(file, backup, true);
/*     */         } }
/*  75 */       if (PerfectBackup.config.getBoolean("backup.properties", false)) copyFile(new File("server.properties"), backup, true);
/*  76 */       if (PerfectBackup.config.getBoolean("backup.ops", false)) copyFile(new File("ops.json"), backup, true);
/*  77 */       if (PerfectBackup.config.getBoolean("backup.whitelist", false)) copyFile(new File("whitelist.json"), backup, true);
/*  78 */       if (PerfectBackup.config.getBoolean("backup.spigotyml", false)) copyFile(new File("spigot.yml"), backup, true);
/*  79 */       if (PerfectBackup.config.getBoolean("backup.bukkityml", false)) copyFile(new File("bukkit.yml"), backup, true);
/*  80 */       if (PerfectBackup.config.getBoolean("backup.aliases", false)) copyFile(new File("commands.yml"), backup, true);
/*  81 */       if (PerfectBackup.config.getBoolean("backup.eula", false)) copyFile(new File("eula.txt"), backup, true);
/*  82 */       if (PerfectBackup.config.getBoolean("backup.logs", false)) { copyFile(new File("logs"), backup, true);
/*     */       }
/*  84 */       if (PerfectBackup.config.getBoolean("backup.metrics", false)) {
/*  85 */         copyFile(new File("plugins" + File.separator + "PluginMetrics"), backup, true);
/*     */       }
/*  87 */       if (PerfectBackup.config.getBoolean("backup.pluginjars", false)) {
/*  88 */         new File(backup, "plugins").mkdir();
/*  89 */         j = (localObject = new File("plugins").listFiles()).length; for (i = 0; i < j; i++) { File file = localObject[i];
/*  90 */           if ((file.isFile()) && (file.getName().endsWith(".jar"))) copyFile(file, new File(backup, "plugins"), true);
/*     */         }
/*     */       }
/*  93 */       if (PerfectBackup.config.getBoolean("backup.pluginconfs", false)) {
/*  94 */         new File(backup, "plugins").mkdir();
/*  95 */         j = (localObject = new File("plugins").listFiles()).length; for (i = 0; i < j; i++) { File file = localObject[i];
/*  96 */           if ((file.isDirectory()) && (Bukkit.getPluginManager().getPlugin(file.getName()) != null))
/*  97 */             copyFile(file, new File(backup, "plugins"), true); } }
/*     */       String world;
/*     */       label843:
/* 100 */       for (Iterator localIterator = PerfectBackup.config.getStringList("backup.worlds").iterator(); localIterator.hasNext(); 
/*     */           
/*     */ 
/*     */ 
/* 104 */           copyFile(new File(world), backup, true))
/*     */       {
/* 100 */         world = (String)localIterator.next();
/* 101 */         if (world.equals("*")) { World w;
/* 102 */           for (localObject = Bukkit.getWorlds().iterator(); ((Iterator)localObject).hasNext(); copyFile(new File(w.getName()), backup, true)) w = (World)((Iterator)localObject).next();
/* 103 */           break; }
/* 104 */         if ((!new File(world).isDirectory()) || (Bukkit.getWorld(world) == null))
/*     */           break label843;
/*     */       }
/* 107 */       for (String other : PerfectBackup.config.getStringList("backup.other")) {
/* 108 */         other = other.replace("/", File.separator);
/* 109 */         if (!other.startsWith("!")) {
/* 110 */           if (new File(other).isDirectory()) { copyFile(new File(other), backup, true);
/* 111 */           } else if (new File(other).isFile()) copyFile(new File(other), backup, true);
/* 112 */         } else { deleteFile(new File(backup, other.substring(1)));
/*     */         }
/*     */       }
/* 115 */       if (PerfectBackup.config.getBoolean("zipbackups", false)) {
/* 116 */         File to = new File(PerfectBackup.backupfolder, name + ".zip");
/* 117 */         for (int i = 1; to.exists(); i++) to = new File(PerfectBackup.backupfolder, name + " (" + i + ").zip");
/* 118 */         setFilePerms(to);
/* 119 */         zip(null, backup, to.getAbsolutePath(), true);
/* 120 */         setFilePerms(to);
/* 121 */         deleteFile(backup);
/* 122 */         backup = to;
/*     */       }
/*     */       
/* 125 */       boolean both = PerfectBackup.config.getString("ftp.mode").equalsIgnoreCase("both");
/* 126 */       boolean first = PerfectBackup.config.getString("ftp.mode").equalsIgnoreCase("first");
/* 127 */       boolean always = PerfectBackup.config.getString("ftp.mode").equalsIgnoreCase("always");
/* 128 */       if ((both) || (first) || (always)) {
/*     */         try {
/* 130 */           saveToFTP(backup);
/* 131 */           if ((first) || (always)) deleteFile(backup);
/*     */         } catch (IOException e) {
/* 133 */           if (always) {
/* 134 */             deleteFile(backup);
/* 135 */             backup = null;
/*     */           }
/* 137 */           System.out.println(BackupRestorer.prefix + "Error while sending file to FTP server:");
/* 138 */           e.printStackTrace();
/*     */         }
/*     */       }
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
/* 154 */       clean();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 142 */       e.printStackTrace();
/* 143 */       if (PerfectBackup.config.getString("ftp.mode").equalsIgnoreCase("fallback")) {
/*     */         try {
/* 145 */           saveToFTP(backup);
/* 146 */           deleteFile(backup);
/*     */         } catch (Exception ex) {
/* 148 */           System.out.println(BackupRestorer.prefix + "Error while falling back to FTP server:");
/* 149 */           ex.printStackTrace();
/* 150 */           backup = null;
/*     */         }
/*     */       }
/*     */     }
/*     */     
/* 155 */     return backup;
/*     */   }
/*     */   
/*     */   public static void setFilePerms(File file) {
/*     */     try {
/* 160 */       file.setExecutable(true);
/* 161 */       file.setReadable(true);
/* 162 */       file.setWritable(true);
/*     */     } catch (SecurityException e) {
/* 164 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static void copyFile(File from, File to, boolean check) {
/* 169 */     to = new File(to, from.getName());
/*     */     
/* 171 */     if (PerfectBackup.config.getStringList("backup.other").contains("!" + from.getPath().replace(File.separator, "/"))) return;
/* 172 */     if (PerfectBackup.config.getStringList("backup.other").contains("!" + from.getPath().replace(File.separator, "/") + "/")) { return;
/*     */     }
/* 174 */     if (from.isDirectory()) {
/* 175 */       if (!to.isDirectory()) to.mkdirs();
/* 176 */       File[] arrayOfFile; int j = (arrayOfFile = from.listFiles()).length; for (int i = 0; i < j; i++) { File file = arrayOfFile[i];copyFile(file, to, check);
/* 177 */       } } else if (from.isFile()) {
/* 178 */       if (to.exists()) to.delete();
/*     */       try {
/* 180 */         Files.copy(from.toPath(), to.toPath(), new java.nio.file.CopyOption[0]);
/*     */       } catch (IOException e) {
/* 182 */         e.printStackTrace();
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public static void clean() {
/* 188 */     int max = PerfectBackup.config.getInt("maxbackups");
/*     */     File[] files;
/* 190 */     while ((max > 0) && ((files = listBackups()).length > max)) { File[] files;
/* 191 */       if (BackupRestorer.log) System.out.println(BackupRestorer.prefix + "Deleting oldest backup...");
/* 192 */       File oldest = files[0];
/* 193 */       File[] arrayOfFile1; int j = (arrayOfFile1 = files).length; for (int i = 0; i < j; i++) { File file = arrayOfFile1[i]; if (file.lastModified() < oldest.lastModified()) oldest = file;
/*     */       }
/* 195 */       try { if (PerfectBackup.config.getString("ftp.mode").equalsIgnoreCase("limit")) saveToFTP(oldest);
/*     */       } catch (Exception e) {
/* 197 */         System.out.println(BackupRestorer.prefix + "Error while moving file to FTP server:");
/* 198 */         e.printStackTrace();
/*     */       }
/* 200 */       deleteFile(oldest);
/*     */     }
/*     */   }
/*     */   
/*     */   public static File[] listBackups() {
/* 205 */     if (PerfectBackup.backupfolder.isDirectory()) {
/* 206 */       ArrayList<File> backupList = new ArrayList();
/* 207 */       File[] arrayOfFile1; int j = (arrayOfFile1 = PerfectBackup.backupfolder.listFiles()).length; for (int i = 0; i < j; i++) { File backup = arrayOfFile1[i];
/* 208 */         if ((backup.isDirectory()) && (!backup.isHidden())) {
/* 209 */           backupList.add(backup);
/* 210 */         } else if ((backup.isFile()) && (backup.getName().endsWith(".zip")) && (!backup.isHidden()))
/* 211 */           backupList.add(backup);
/*     */       }
/* 213 */       File[] backups = (File[])backupList.toArray(new File[backupList.size()]);
/* 214 */       java.util.Arrays.sort(backups, new java.util.Comparator() {
/*     */         public int compare(File a, File b) {
/* 216 */           if (b.lastModified() - a.lastModified() < 2147483647L) {
/* 217 */             if (b.lastModified() - a.lastModified() > -2147483648L)
/* 218 */               return (int)(b.lastModified() - a.lastModified());
/* 219 */             return Integer.MIN_VALUE; }
/* 220 */           return Integer.MAX_VALUE;
/*     */         }
/* 222 */       });
/* 223 */       return backups; }
/* 224 */     return new File[0];
/*     */   }
/*     */   
/*     */   public static void deleteFile(File file) {
/* 228 */     if ((file.isDirectory()) && (file.listFiles().length > 0)) { File[] arrayOfFile;
/* 229 */       int j = (arrayOfFile = file.listFiles()).length; for (int i = 0; i < j; i++) { File subfile = arrayOfFile[i];deleteFile(subfile); }
/* 230 */       deleteFile(file);
/* 231 */     } else if (file.exists()) { file.delete();
/*     */     }
/*     */   }
/*     */   
/* 235 */   public static String dateToString(long unix) { Calendar c = Calendar.getInstance();
/* 236 */     c.setTimeInMillis(unix);
/* 237 */     String string = PerfectBackup.config.getString("dateformat");
/* 238 */     if ((string == null) || (string.isEmpty())) string = "MM/DD/YYYY hh:mm:ss";
/* 239 */     string = string.replace("MM", (c.get(2) < 9 ? "0" : "") + (c.get(2) + 1));
/* 240 */     string = string.replace("DD", (c.get(5) < 10 ? "0" : "") + c.get(5));
/* 241 */     string = string.replace("YYYY", c.get(1));
/* 242 */     string = string.replace("hh", (c.get(11) < 10 ? "0" : "") + c.get(11));
/* 243 */     string = string.replace("HH", (c.get(10) < 10 ? "0" : "") + c.get(10));
/* 244 */     string = string.replace("mm", (c.get(12) < 10 ? "0" : "") + c.get(12));
/* 245 */     string = string.replace("ss", (c.get(13) < 10 ? "0" : "") + c.get(13));
/* 246 */     string = string.replace("ms", c.get(14));
/* 247 */     string = string.replace("AM", c.get(9) == 0 ? "AM" : "PM");
/* 248 */     return string;
/*     */   }
/*     */   
/*     */   public static void unzip(File zip, File folder) {
/* 252 */     byte[] buffer = new byte['Ѐ'];
/*     */     try {
/* 254 */       if (!folder.exists()) { folder.mkdirs();
/*     */       }
/* 256 */       ZipInputStream zis = new ZipInputStream(new FileInputStream(zip));
/*     */       
/* 258 */       int i = 0;
/*     */       ZipEntry ze;
/* 260 */       while ((ze = zis.getNextEntry()) != null) { ZipEntry ze;
/* 261 */         String fileName = ze.getName();
/* 262 */         if (ze.getName().contains("/"))
/* 263 */           fileName = ze.getName().substring(ze.getName().lastIndexOf("/"));
/* 264 */         if (!fileName.equals("/")) {
/* 265 */           if (BackupRestorer.log) System.out.println(BackupRestorer.prefix + "Decompressing file " + fileName + "...");
/* 266 */           File newFile = new File(folder, fileName);
/* 267 */           if (newFile.exists()) deleteFile(newFile);
/* 268 */           new File(newFile.getParent()).mkdirs();
/* 269 */           FileOutputStream fos = new FileOutputStream(newFile);
/*     */           int len;
/* 271 */           while ((len = zis.read(buffer)) > 0) { int len; fos.write(buffer, 0, len); }
/* 272 */           fos.close();i++;
/*     */         }
/*     */       }
/*     */       
/* 276 */       if (BackupRestorer.log) { System.out.println(BackupRestorer.prefix + "Finished decompressing " + i + " file(s).");
/*     */       }
/* 278 */       zis.closeEntry();
/* 279 */       zis.close();
/*     */     } catch (IOException e) {
/* 281 */       System.out.println(BackupRestorer.prefix + "Error while decompressing file:");
/* 282 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static ZipOutputStream zip(ZipOutputStream zip, File tozip, String dir, boolean close) {
/* 287 */     byte[] buffer = new byte['Ѐ'];
/*     */     try {
/* 289 */       if (zip == null) {
/* 290 */         if (!new File(dir).isFile()) {
/* 291 */           new File(dir).getParentFile().mkdirs();
/* 292 */           new File(dir).createNewFile();
/*     */         }
/* 294 */         zip = new ZipOutputStream(new FileOutputStream(new File(dir)));
/* 295 */         dir = null; }
/*     */       FileInputStream in;
/* 297 */       int len; if (tozip.isFile()) {
/* 298 */         ZipEntry ze = new ZipEntry((dir != null ? dir : "") + tozip.getName());
/* 299 */         zip.putNextEntry(ze);
/* 300 */         if (BackupRestorer.log) System.out.println(BackupRestorer.prefix + "Compressing file " + ze.getName() + "...");
/* 301 */         in = new FileInputStream(tozip);
/*     */         
/* 303 */         while ((len = in.read(buffer)) > 0) { int len; zip.write(buffer, 0, len); }
/* 304 */         in.close();
/* 305 */       } else if (tozip.isDirectory()) {
/* 306 */         dir = dir != null ? dir + tozip.getName() + File.separator : "";
/* 307 */         File[] arrayOfFile; len = (arrayOfFile = tozip.listFiles()).length; for (in = 0; in < len; in++) { File subfile = arrayOfFile[in];
/* 308 */           if (subfile.exists()) zip(zip, subfile, dir, false);
/*     */         }
/*     */       }
/* 311 */     } catch (IOException e) { System.out.println(BackupRestorer.prefix + "Error while compressing file:");
/* 312 */       e.printStackTrace();
/*     */     }
/* 314 */     if (close) {
/*     */       try {
/* 316 */         zip.closeEntry();
/* 317 */         zip.close();
/*     */       } catch (IOException e) {
/* 319 */         System.out.println(BackupRestorer.prefix + "Error while closing compressed file:");
/* 320 */         e.printStackTrace();
/*     */       }
/* 322 */       if (BackupRestorer.log) System.out.println(BackupRestorer.prefix + "Finished decompressing file(s).");
/*     */     }
/* 324 */     return zip;
/*     */   }
/*     */   
/*     */   public static String bytesToString(long bytes) {
/* 328 */     int u = 1024;
/* 329 */     if (bytes / Math.pow(u, 3.0D) > 0.0D) return bytes / Math.pow(u, 3.0D) + " GB";
/* 330 */     if (bytes / Math.pow(u, 2.0D) > 0.0D) return bytes / Math.pow(u, 2.0D) + " MB";
/* 331 */     if (bytes / Math.pow(u, 1.0D) > 0.0D) return bytes / Math.pow(u, 1.0D) + " KB";
/* 332 */     return bytes + " B";
/*     */   }
/*     */ }
