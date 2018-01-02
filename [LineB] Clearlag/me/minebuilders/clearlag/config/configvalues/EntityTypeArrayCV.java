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
/*    */ public class EntityTypeArrayCV
/*    */   implements ConfigData<EntityType[]>
/*    */ {
/*    */   public EntityType[] getValue(String path)
/*    */   {
/* 17 */     List<EntityType> types = new ArrayList();
/*    */     
/* 19 */     for (String st : Config.getConfig().getStringList(path))
/*    */     {
/* 21 */       EntityType t = Util.getEntityTypeFromString(st);
/*    */       
/* 23 */       if (t != null) {
/* 24 */         types.add(t);
/*    */       } else {
/* 26 */         Util.warning("Config Error at line " + path + ":");
/* 27 */         Util.warning(st + " is not a valid Entity!");
/*    */       }
/*    */     }
/*    */     
/* 31 */     return (EntityType[])types.toArray(new EntityType[types.size()]);
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configvalues\EntityTypeArrayCV.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */