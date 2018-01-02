/*    */ package me.minebuilders.clearlag.config;
/*    */ 
/*    */ import me.minebuilders.clearlag.config.configvalues.ConfigData;
/*    */ import me.minebuilders.clearlag.config.configvalues.EntityTypeListCV;
/*    */ import me.minebuilders.clearlag.config.configvalues.EntityTypeSet;
/*    */ import me.minebuilders.clearlag.config.configvalues.PrimitiveCV;
/*    */ 
/*    */ public enum ConfigValueType
/*    */ {
/* 10 */   ENTITY_TYPE_ARRAY(new me.minebuilders.clearlag.config.configvalues.EntityTypeArrayCV()), 
/* 11 */   ENTITY_TYPE_LIST(new EntityTypeListCV()), 
/* 12 */   ENTITY_TYPE_TABLE(new me.minebuilders.clearlag.config.configvalues.EntityTypeTable()), 
/* 13 */   WARN_ARRAY(new me.minebuilders.clearlag.config.configvalues.WarnMapCV()), 
/* 14 */   COLORED_STRING(new me.minebuilders.clearlag.config.configvalues.ColoredStringCV()), 
/* 15 */   ENTITY_TYPE_SET(new EntityTypeSet()), 
/* 16 */   PRIMITIVE(new PrimitiveCV());
/*    */   
/*    */   private ConfigData configData;
/*    */   
/*    */   private ConfigValueType(ConfigData configData) {
/* 21 */     this.configData = configData;
/*    */   }
/*    */   
/*    */   public ConfigData getConfigData() {
/* 25 */     return this.configData;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\ConfigValueType.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */