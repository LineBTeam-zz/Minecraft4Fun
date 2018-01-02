/*     */ package net.coreprotect.patch;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.lang.reflect.Method;
/*     */ import java.security.ProtectionDomain;
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.Statement;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Comparator;
/*     */ import java.util.List;
/*     */ import java.util.jar.JarEntry;
/*     */ import java.util.jar.JarInputStream;
/*     */ import net.coreprotect.CoreProtect;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.consumer.Consumer;
/*     */ import net.coreprotect.database.Database;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ 
/*     */ public class Patch extends CoreProtect
/*     */ {
/*  24 */   private static boolean patching = false;
/*     */   
/*     */   public static boolean continuePatch() {
/*  27 */     if ((patching) && (Config.server_running)) {
/*  28 */       return true;
/*     */     }
/*  30 */     return false;
/*     */   }
/*     */   
/*     */   protected static String getClassVersion(String version) {
/*  34 */     return version.split(".__")[1].replaceAll("_", ".");
/*     */   }
/*     */   
/*     */   public static Integer[] getLastVersion(Connection connection) {
/*  38 */     Integer[] last_version = { Integer.valueOf(0), Integer.valueOf(0), Integer.valueOf(0) };
/*     */     try {
/*  40 */       String query = "SELECT version FROM " + Config.prefix + "version ORDER BY rowid DESC LIMIT 0, 1";
/*  41 */       Statement statement = connection.createStatement();
/*  42 */       ResultSet rs = statement.executeQuery(query);
/*  43 */       while (rs.next()) {
/*  44 */         String version = rs.getString("version");
/*  45 */         if (!version.contains(".")) {
/*  46 */           int version_int = Integer.parseInt(version);
/*  47 */           version = String.format("%3.2f", new Object[] { Double.valueOf(version_int / 100.0D) });
/*     */         }
/*  49 */         version = version.replaceAll(",", ".");
/*  50 */         String[] old_version_split = version.split("\\.");
/*  51 */         if (old_version_split.length > 2) {
/*  52 */           last_version[0] = Integer.valueOf(Integer.parseInt(old_version_split[0]));
/*  53 */           last_version[1] = Integer.valueOf(Integer.parseInt(old_version_split[1]));
/*  54 */           last_version[2] = Integer.valueOf(Integer.parseInt(old_version_split[2]));
/*     */         }
/*     */         else {
/*  57 */           int revision = 0;
/*  58 */           String parse = old_version_split[1];
/*  59 */           if (parse.length() > 1) {
/*  60 */             revision = Integer.parseInt(parse.substring(1));
/*     */           }
/*  62 */           last_version[0] = Integer.valueOf(Integer.parseInt(old_version_split[0]));
/*  63 */           last_version[1] = Integer.valueOf(0);
/*  64 */           last_version[2] = Integer.valueOf(revision);
/*     */         }
/*     */       }
/*  67 */       rs.close();
/*  68 */       statement.close();
/*     */     }
/*     */     catch (Exception e) {
/*  71 */       e.printStackTrace();
/*     */     }
/*     */     
/*  74 */     return last_version;
/*     */   }
/*     */   
/*     */   private static List<String> getPatches() {
/*  78 */     List<String> patches = new ArrayList();
/*     */     try
/*     */     {
/*  81 */       File pluginFile = new File(CoreProtect.class.getProtectionDomain().getCodeSource().getLocation().toURI());
/*  82 */       if (pluginFile.getPath().endsWith(".jar")) {
/*  83 */         JarInputStream jarInputStream = new JarInputStream(new FileInputStream(pluginFile));
/*     */         for (;;) {
/*  85 */           JarEntry jarEntry = jarInputStream.getNextJarEntry();
/*  86 */           if (jarEntry == null) {
/*     */             break;
/*     */           }
/*  89 */           String className = jarEntry.getName();
/*  90 */           if ((className.startsWith("net/coreprotect/patch/script/__")) && (className.endsWith(".class"))) {
/*  91 */             Class<?> patchClass = Class.forName(className.substring(0, className.length() - 6).replaceAll("/", "."));
/*  92 */             String patchVersion = getClassVersion(patchClass.getName());
/*  93 */             if (!Functions.newVersion(getPluginVersion(), patchVersion)) {
/*  94 */               patches.add(patchVersion);
/*     */             }
/*     */           }
/*     */         }
/*  98 */         jarInputStream.close();
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
/* 110 */       java.util.Collections.sort(patches, new Comparator()
/*     */       {
/*     */         public int compare(String o1, String o2) {
/* 113 */           if (Functions.newVersion(o1, o2)) {
/* 114 */             return -1;
/*     */           }
/* 116 */           if (Functions.newVersion(o2, o1)) {
/* 117 */             return 1;
/*     */           }
/* 119 */           return 0;
/*     */         }
/*     */       });
/*     */     }
/*     */     catch (Exception e) {
/* 124 */       e.printStackTrace();
/*     */     }
/*     */     
/* 127 */     return patches;
/*     */   }
/*     */   
/*     */   private static Integer[] getPluginVersion() {
/* 131 */     String[] versionSplit = CoreProtect.getInstance().getDescription().getVersion().split("\\.");
/* 132 */     return new Integer[] { Integer.valueOf(Integer.parseInt(versionSplit[0])), Integer.valueOf(Integer.parseInt(versionSplit[1])), Integer.valueOf(Integer.parseInt(versionSplit[2])) };
/*     */   }
/*     */   
/*     */   public static void processConsumer() {
/*     */     try {
/* 137 */       Functions.messageOwner("Processing new data. Please wait...");
/* 138 */       Consumer.is_paused = false;
/* 139 */       Thread.sleep(1000L);
/* 140 */       while (Consumer.is_paused == true) {
/* 141 */         Thread.sleep(500L);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 145 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   private static int runPatcher(Integer[] last_version, Integer[] version) {
/* 150 */     int result = -1;
/* 151 */     patching = true;
/*     */     try
/*     */     {
/* 154 */       boolean patched = false;
/* 155 */       boolean allPatches = true;
/* 156 */       Connection connection = Database.getConnection(true);
/* 157 */       Statement statement = connection.createStatement();
/* 158 */       Integer[] newVersion = last_version;
/*     */       
/*     */ 
/* 161 */       if ((newVersion[1].intValue() == 0) && (newVersion[2].intValue() > 0)) {
/* 162 */         newVersion[1] = newVersion[2];
/* 163 */         newVersion[2] = Integer.valueOf(0);
/*     */       }
/*     */       
/* 166 */       List<String> patches = getPatches();
/* 167 */       for (String patchData : patches) {
/* 168 */         String[] thePatch = patchData.split("\\.");
/* 169 */         int patchMajor = Integer.parseInt(thePatch[0]);
/* 170 */         int patchMinor = Integer.parseInt(thePatch[1]);
/* 171 */         int patchRevision = Integer.parseInt(thePatch[2]);
/* 172 */         Integer[] patchVersion = { Integer.valueOf(patchMajor), Integer.valueOf(patchMinor), Integer.valueOf(patchRevision) };
/*     */         
/* 174 */         boolean performPatch = Functions.newVersion(newVersion, patchVersion);
/* 175 */         if (performPatch == true) {
/* 176 */           boolean success = false;
/*     */           try {
/* 178 */             Functions.messageOwner("-----");
/* 179 */             Functions.messageOwner("Performing v" + patchData + " upgrade. Please wait...");
/* 180 */             Functions.messageOwner("-----");
/*     */             
/* 182 */             if (continuePatch()) {
/* 183 */               Class<?> patchClass = Class.forName("net.coreprotect.patch.script.__" + patchData.replaceAll("\\.", "_"));
/* 184 */               Method patchMethod = patchClass.getDeclaredMethod("patch", new Class[] { Statement.class });
/* 185 */               patchMethod.setAccessible(true);
/* 186 */               success = ((Boolean)patchMethod.invoke(null, new Object[] { statement })).booleanValue();
/*     */             }
/*     */           }
/*     */           catch (Exception e) {
/* 190 */             e.printStackTrace();
/*     */           }
/*     */           
/* 193 */           if (success == true) {
/* 194 */             patched = true;
/* 195 */             newVersion = patchVersion;
/*     */           }
/*     */           else {
/* 198 */             allPatches = false;
/* 199 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       
/* 204 */       if (allPatches) {
/* 205 */         if (patched) {
/* 206 */           result = 1;
/*     */         }
/*     */         else {
/* 209 */           result = 0;
/*     */         }
/*     */       }
/*     */       
/*     */ 
/* 214 */       int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 215 */       if (result >= 0) {
/* 216 */         statement.executeUpdate("INSERT INTO " + Config.prefix + "version (time,version) VALUES ('" + unixtimestamp + "', '" + version[0] + "." + version[1] + "." + version[2] + "')");
/*     */       }
/* 218 */       else if (patched) {
/* 219 */         statement.executeUpdate("INSERT INTO " + Config.prefix + "version (time,version) VALUES ('" + unixtimestamp + "', '" + newVersion[0] + "." + newVersion[1] + "." + newVersion[2] + "')");
/*     */       }
/*     */       
/* 222 */       statement.close();
/* 223 */       connection.close();
/*     */     }
/*     */     catch (Exception e) {
/* 226 */       e.printStackTrace();
/*     */     }
/*     */     
/* 229 */     patching = false;
/* 230 */     return result;
/*     */   }
/*     */   
/*     */   public static void versionCheck(Statement statement) {
/*     */     try {
/* 235 */       Integer[] current_version = getPluginVersion();
/* 236 */       Integer[] last_version = getLastVersion(statement.getConnection());
/*     */       
/* 238 */       boolean newVersion = Functions.newVersion(last_version, current_version);
/* 239 */       if ((newVersion == true) && (last_version[0].intValue() > 0) && (!Config.converter_running)) {
/* 240 */         Config.converter_running = true;
/* 241 */         Consumer.is_paused = true;
/* 242 */         Integer[] v_old = last_version;
/* 243 */         final Integer[] v_new = current_version;
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
/* 268 */         Thread thread = new Thread(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/*     */             try
/*     */             {
/* 248 */               int finished = Patch.runPatcher(this.val$v_old, v_new);
/* 249 */               Config.converter_running = false;
/* 250 */               if (finished == 1) {
/* 251 */                 Patch.processConsumer();
/* 252 */                 Functions.messageOwner("-----");
/* 253 */                 Functions.messageOwner("Successfully upgraded to v" + CoreProtect.getInstance().getDescription().getVersion() + ".");
/* 254 */                 Functions.messageOwner("-----");
/*     */               }
/* 256 */               else if (finished == 0) {
/* 257 */                 Consumer.is_paused = false;
/*     */               }
/* 259 */               else if (finished == -1) {
/* 260 */                 Functions.messageOwner("Upgrade interrupted. Will try again on restart.");
/*     */               }
/*     */             }
/*     */             catch (Exception e) {
/* 264 */               e.printStackTrace();
/*     */             }
/*     */             
/*     */           }
/* 268 */         });
/* 269 */         thread.start();
/*     */       }
/* 271 */       else if (last_version[0].intValue() == 0) {
/* 272 */         int unixtimestamp = (int)(System.currentTimeMillis() / 1000L);
/* 273 */         statement.executeUpdate("INSERT INTO " + Config.prefix + "version (time,version) VALUES ('" + unixtimestamp + "', '" + current_version[0] + "." + current_version[1] + "." + current_version[2] + "')");
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/* 277 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\patch\Patch.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */