/*    */ package me.minebuilders.clearlag.config.configvalues;
/*    */ 
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ 
/*    */ 
/*    */ public class ColoredStringCV
/*    */   implements ConfigData<String>
/*    */ {
/*    */   public String getValue(String path)
/*    */   {
/* 13 */     return Util.color(Config.getConfig().getString(path));
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configvalues\ColoredStringCV.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */