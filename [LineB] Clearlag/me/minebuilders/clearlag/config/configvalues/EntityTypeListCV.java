/*    */ package me.minebuilders.clearlag.config.configvalues;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityTypeListCV
/*    */   implements ConfigData<List<EntityType>>
/*    */ {
/*    */   public List<EntityType> getValue(String path)
/*    */   {
/* 17 */     List<String> entities = Config.getConfig().getStringList(path);
/*    */     
/* 19 */     List<EntityType> types = new ArrayList(entities.size());
/*    */     
/* 21 */     for (String st : entities)
/*    */     {
/* 23 */       EntityType t = Util.getEntityTypeFromString(st);
/*    */       
/* 25 */       if (t != null) {
/* 26 */         types.add(t);
/*    */       } else {
/* 28 */         Util.warning("Config Error at line " + path + ":");
/* 29 */         Util.warning(st + " is not a valid Entity!");
/*    */       }
/*    */     }
/*    */     
/* 33 */     return types;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configvalues\EntityTypeListCV.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */