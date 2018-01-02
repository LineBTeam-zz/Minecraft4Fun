/*    */ package net.risenphoenix.commons.configuration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ConfigurationOption
/*    */ {
/*    */   private ConfigOptionType type;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   private String identifier;
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public ConfigurationOption(ConfigOptionType type, String identifier)
/*    */   {
/* 39 */     this.type = type;
/* 40 */     this.identifier = identifier;
/*    */   }
/*    */   
/*    */   public final ConfigOptionType getType() {
/* 44 */     return this.type;
/*    */   }
/*    */   
/*    */   public final String getIdentifier() {
/* 48 */     return this.identifier;
/*    */   }
/*    */   
/*    */   public static enum ConfigOptionType {
/* 52 */     String, 
/* 53 */     Integer, 
/* 54 */     Boolean, 
/* 55 */     Long, 
/* 56 */     Double, 
/* 57 */     StringList, 
/* 58 */     IntegerList, 
/* 59 */     BooleanList, 
/* 60 */     FloatList, 
/* 61 */     LongList, 
/* 62 */     DoubleList, 
/* 63 */     CharList, 
/* 64 */     ByteList;
/*    */     
/*    */     private ConfigOptionType() {}
/*    */   }
/*    */ }


 