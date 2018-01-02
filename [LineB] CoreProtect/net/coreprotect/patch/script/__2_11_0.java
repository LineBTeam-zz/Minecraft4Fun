/*    */ package net.coreprotect.patch.script;
/*    */ 
/*    */ import java.sql.Statement;
/*    */ import java.util.Map;
/*    */ import net.coreprotect.model.Config;
/*    */ import org.bukkit.Art;
/*    */ import org.bukkit.Material;
/*    */ 
/*    */ public class __2_11_0
/*    */ {
/*    */   protected static boolean patch(Statement statement)
/*    */   {
/*    */     try
/*    */     {
/* 15 */       if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/* 16 */         statement.executeUpdate("START TRANSACTION");
/*    */       }
/*    */       else {
/* 19 */         statement.executeUpdate("BEGIN TRANSACTION");
/*    */       }
/*    */       
/* 22 */       for (Art artType : Art.values()) {
/* 23 */         Integer type = Integer.valueOf(artType.getId());
/* 24 */         String name = artType.toString().toLowerCase();
/* 25 */         statement.executeUpdate("INSERT INTO " + Config.prefix + "art_map (id, art) VALUES ('" + type + "', '" + name + "')");
/* 26 */         Config.art.put(name, type);
/* 27 */         Config.art_reversed.put(type, name);
/* 28 */         if (type.intValue() > Config.art_id) {
/* 29 */           Config.art_id = type.intValue();
/*    */         }
/*    */       }
/*    */       
/* 33 */       for (org.bukkit.entity.EntityType entityType : org.bukkit.entity.EntityType.values()) {
/* 34 */         Integer type = Integer.valueOf(entityType.getTypeId());
/* 35 */         String name = entityType.toString().toLowerCase();
/* 36 */         statement.executeUpdate("INSERT INTO " + Config.prefix + "entity_map (id, entity) VALUES ('" + type + "', '" + name + "')");
/* 37 */         Config.entities.put(name, type);
/* 38 */         Config.entities_reversed.put(type, name);
/* 39 */         if (type.intValue() > Config.entity_id) {
/* 40 */           Config.entity_id = type.intValue();
/*    */         }
/*    */       }
/*    */       
/* 44 */       for (Material material : Material.values()) {
/* 45 */         Integer type = Integer.valueOf(material.getId());
/* 46 */         String name = material.toString().toLowerCase();
/* 47 */         statement.executeUpdate("INSERT INTO " + Config.prefix + "material_map (id, material) VALUES ('" + type + "', '" + name + "')");
/* 48 */         Config.materials.put(name, type);
/* 49 */         Config.materials_reversed.put(type, name);
/* 50 */         if (type.intValue() > Config.material_id) {
/* 51 */           Config.material_id = type.intValue();
/*    */         }
/*    */       }
/*    */       
/* 55 */       if (((Integer)Config.config.get("use-mysql")).intValue() == 1) {
/* 56 */         statement.executeUpdate("COMMIT");
/*    */       }
/*    */       else {
/* 59 */         statement.executeUpdate("COMMIT TRANSACTION");
/*    */       }
/*    */     }
/*    */     catch (Exception e) {
/* 63 */       e.printStackTrace();
/*    */     }
/*    */     
/* 66 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\patch\script\__2_11_0.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */