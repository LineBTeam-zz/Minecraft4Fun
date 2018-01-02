/*    */ package me.minebuilders.clearlag.config.configvalues;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.StringTokenizer;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import me.minebuilders.clearlag.entities.EntityTable;
/*    */ import me.minebuilders.clearlag.entities.attributes.EntityAttribute;
/*    */ import me.minebuilders.clearlag.entities.attributes.EntityMountedAttribute;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ 
/*    */ public class EntityTypeTable implements ConfigData<EntityTable>
/*    */ {
/*    */   public EntityTable getValue(String path)
/*    */   {
/* 18 */     java.util.List<String> entities = Config.getConfig().getStringList(path);
/*    */     
/* 20 */     EntityTable table = new EntityTable();
/*    */     
/* 22 */     for (String s : entities) {
/* 23 */       StringTokenizer tk = new StringTokenizer(s);
/*    */       
/* 25 */       String cName = tk.nextToken();
/*    */       
/* 27 */       EntityType t = Util.getEntityTypeFromString(cName);
/*    */       
/* 29 */       if (t != null)
/*    */       {
/* 31 */         ArrayList<EntityAttribute<Entity>> attributes = new ArrayList();
/*    */         try
/*    */         {
/* 34 */           while (tk.hasMoreTokens())
/*    */           {
/* 36 */             EntityAttribute<Entity> attribute = null;
/*    */             
/* 38 */             String tok = tk.nextToken();
/*    */             
/* 40 */             boolean reversed = tok.startsWith("!");
/*    */             
/* 42 */             if (reversed) {
/* 43 */               tok = tok.substring(1);
/*    */             }
/* 45 */             if (tok.startsWith("name=\"")) {
/* 46 */               StringBuilder name = new StringBuilder();
/*    */               
/* 48 */               name.append(tok.substring(6, tok.endsWith("\"") ? tok.length() - 1 : tok.length()));
/*    */               
/* 50 */               while (tk.hasMoreTokens()) {
/* 51 */                 tok = tk.nextToken();
/*    */                 
/* 53 */                 if (tok.endsWith("\"")) {
/* 54 */                   name.append(" ").append(tok.substring(0, tok.length() - 1));
/* 55 */                   break;
/*    */                 }
/* 57 */                 name.append(" ").append(tok);
/*    */               }
/*    */               
/* 60 */               attribute = new me.minebuilders.clearlag.entities.attributes.EntityNameAttribute(name.toString());
/*    */             }
/* 62 */             else if (tok.startsWith("hasName")) {
/* 63 */               attribute = new me.minebuilders.clearlag.entities.attributes.EntityHasNameAttribute();
/* 64 */             } else if (tok.startsWith("liveTime=")) {
/* 65 */               attribute = new me.minebuilders.clearlag.entities.attributes.EntityLifeLimitAttribute(Integer.parseInt(tok.substring(9)));
/* 66 */             } else if (tok.startsWith("isMounted")) {
/* 67 */               attribute = new EntityMountedAttribute();
/*    */             }
/* 69 */             if (attribute != null) {
/* 70 */               attribute.setReversed(reversed);
/* 71 */               attributes.add(attribute);
/*    */             } else {
/* 73 */               Util.warning("Invalid entity attribute path=\"" + path + "\" token=" + tok + "\"");
/*    */             }
/*    */           }
/* 76 */         } catch (Exception e) { Util.warning("Failed to read entity path=\"" + path + "\" type=" + t.name() + "\" error=\"" + e + "\"");
/*    */         }
/*    */         
/* 79 */         if (attributes.isEmpty()) {
/* 80 */           table.setEntity(t);
/*    */         } else
/* 82 */           table.setEntityAttributes(t, attributes);
/*    */       } else {
/* 84 */         Util.warning("Invalid entity type specified path=\"" + path + "\" value=" + cName);
/*    */       }
/*    */     }
/*    */     
/* 88 */     return table;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configvalues\EntityTypeTable.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */