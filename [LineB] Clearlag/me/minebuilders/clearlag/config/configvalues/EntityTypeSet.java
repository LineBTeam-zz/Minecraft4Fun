/*    */ package me.minebuilders.clearlag.config.configvalues;
/*    */ 
/*    */ import java.util.HashSet;
/*    */ import java.util.List;
/*    */ import java.util.Set;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class EntityTypeSet
/*    */   implements ConfigData<Set<EntityType>>
/*    */ {
/*    */   public Set<EntityType> getValue(String path)
/*    */   {
/* 18 */     List<String> entities = Config.getConfig().getStringList(path);
/*    */     
/* 20 */     Set<EntityType> types = new HashSet(entities.size());
/*    */     
/* 22 */     for (String st : entities)
/*    */     {
/* 24 */       EntityType t = Util.getEntityTypeFromString(st);
/*    */       
/* 26 */       if (t != null) {
/* 27 */         types.add(t);
/*    */       } else {
/* 29 */         Util.warning("Config Error at line " + path + ":");
/* 30 */         Util.warning(st + " is not a valid Entity!");
/*    */       }
/*    */     }
/*    */     
/* 34 */     return types;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configvalues\EntityTypeSet.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */