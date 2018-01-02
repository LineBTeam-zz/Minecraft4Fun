/*     */ package me.minebuilders.clearlag;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ 
/*     */ 
/*     */ public class Util
/*     */ {
/*  16 */   private static final Logger log = Logger.getLogger("Minecraft");
/*     */   
/*     */   public static void log(String m) {
/*  19 */     log.info("[ClearLag] " + m);
/*     */   }
/*     */   
/*     */   public static void warning(String m) {
/*  23 */     log.warning("[ClearLag] " + m);
/*     */   }
/*     */   
/*     */   public static void msg(String m, CommandSender s) {
/*  27 */     s.sendMessage(color("&6[&aClearLag&6] &a" + m));
/*     */   }
/*     */   
/*     */   public static void scm(String m, CommandSender s) {
/*  31 */     s.sendMessage(color(m));
/*     */   }
/*     */   
/*     */   public static void broadcast(String m) {
/*  35 */     Bukkit.broadcastMessage(color(m));
/*     */   }
/*     */   
/*     */   public static void shiftRight(Object[] list, int dropIndex)
/*     */   {
/*  40 */     if (list.length < 2) { return;
/*     */     }
/*  42 */     System.arraycopy(list, dropIndex, list, dropIndex + 1, list.length - 1 - dropIndex);
/*     */   }
/*     */   
/*     */   public static boolean isInteger(String s)
/*     */   {
/*  47 */     return isInteger(s, 10);
/*     */   }
/*     */   
/*     */   public static boolean isInteger(String s, int radix) {
/*  51 */     if (s.isEmpty()) return false;
/*  52 */     for (int i = 0; i < s.length(); i++) {
/*  53 */       if ((i == 0) && (s.charAt(i) == '-')) {
/*  54 */         if (s.length() == 1) { return false;
/*     */         }
/*     */       }
/*  57 */       else if (Character.digit(s.charAt(i), radix) < 0) return false;
/*     */     }
/*  59 */     return true;
/*     */   }
/*     */   
/*     */   public static String color(String s) {
/*  63 */     return ChatColor.translateAlternateColorCodes('&', s);
/*     */   }
/*     */   
/*     */   public static EntityType getEntityTypeFromString(String s)
/*     */   {
/*  68 */     EntityType et = EntityType.fromName(s);
/*     */     
/*  70 */     if (et != null) {
/*  71 */       return et;
/*     */     }
/*     */     
/*  74 */     s = s.replace("_", "").replace(" ", "");
/*  75 */     for (EntityType e : EntityType.values()) {
/*  76 */       if (e != null) {
/*  77 */         String name = e.name().replace("_", "");
/*  78 */         if (name.equalsIgnoreCase(s)) {
/*  79 */           return e;
/*     */         }
/*     */       }
/*     */     }
/*  83 */     return null;
/*     */   }
/*     */   
/*     */   public static int removeEntitys(List<Entity> e) {
/*  87 */     int i = 0;
/*  88 */     for (Entity en : e) {
/*  89 */       en.remove();
/*  90 */       i++;
/*     */     }
/*  92 */     return i;
/*     */   }
/*     */   
/*     */   public static boolean isClass(String clazz) {
/*     */     try {
/*  97 */       Class.forName(clazz);
/*     */     } catch (ClassNotFoundException e) {
/*  99 */       return false;
/*     */     }
/* 101 */     return true;
/*     */   }
/*     */   
/*     */   public static String getRawBukkitVersion() {
/* 105 */     return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
/*     */   }
/*     */   
/*     */   public static String getBukkitVersion() {
/* 109 */     String[] v = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3].split("-")[0].split("_");
/* 110 */     return v[0].replace("v", "") + "." + v[1];
/*     */   }
/*     */   
/*     */   public static Date parseTime(String time) {
/*     */     try {
/* 115 */       String[] frag = time.split("-");
/*     */       
/* 117 */       if (frag.length < 2) {
/* 118 */         return new Date();
/*     */       }
/*     */       
/* 121 */       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
/* 122 */       return dateFormat.parse(frag[0] + "-" + frag[1] + "-" + frag[2]);
/*     */     } catch (Exception e) {}
/* 124 */     return new Date();
/*     */   }
/*     */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\Util.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */