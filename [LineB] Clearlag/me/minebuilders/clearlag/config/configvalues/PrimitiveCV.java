/*    */ package me.minebuilders.clearlag.config.configvalues;
/*    */ 
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ 
/*    */ 
/*    */ public class PrimitiveCV
/*    */   implements ConfigData<Object>
/*    */ {
/*    */   public Object getValue(String path)
/*    */   {
/* 12 */     return Config.getConfig().get(path);
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configvalues\PrimitiveCV.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */