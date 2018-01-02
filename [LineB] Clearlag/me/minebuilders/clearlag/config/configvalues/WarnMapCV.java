/*    */ package me.minebuilders.clearlag.config.configvalues;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class WarnMapCV
/*    */   implements ConfigData<HashMap<Integer, String>>
/*    */ {
/*    */   public HashMap<Integer, String> getValue(String path)
/*    */   {
/* 15 */     HashMap<Integer, String> warns = new HashMap();
/*    */     
/* 17 */     for (String s : Config.getConfig().getStringList(path)) {
/* 18 */       int i = Integer.parseInt(s.split(" ")[0].replace("time:", ""));
/* 19 */       String msg = Util.color(s.replace("time:" + i, "").replace("msg:", "").trim());
/*    */       
/* 21 */       if (i > 0) {
/* 22 */         warns.put(Integer.valueOf(i), msg);
/*    */       } else {
/* 24 */         Util.warning("Config Error at line " + path + ":");
/* 25 */         Util.warning(s + " is an invalid warning!");
/*    */       }
/*    */     }
/*    */     
/* 29 */     return warns;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configvalues\WarnMapCV.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */