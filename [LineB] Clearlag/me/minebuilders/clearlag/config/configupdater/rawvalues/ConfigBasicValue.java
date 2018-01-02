/*    */ package me.minebuilders.clearlag.config.configupdater.rawvalues;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ 
/*    */ public class ConfigBasicValue implements ConfigValue
/*    */ {
/*    */   private String key;
/*    */   private String value;
/*    */   
/*    */   public ConfigBasicValue(String key, String value)
/*    */   {
/* 12 */     this.key = key;
/* 13 */     this.value = value;
/*    */   }
/*    */   
/*    */   public String getKey()
/*    */   {
/* 18 */     return this.key;
/*    */   }
/*    */   
/*    */   public void merge(ConfigValue value)
/*    */   {
/* 23 */     this.value = ((String)value.getValue());
/*    */   }
/*    */   
/*    */   public void writeToFile(BufferedWriter writer) throws java.io.IOException
/*    */   {
/* 28 */     writer.write("  " + this.key + ":" + this.value);
/*    */   }
/*    */   
/*    */   public boolean equals(Object b)
/*    */   {
/* 33 */     return ((b instanceof ConfigBasicValue)) && (((ConfigBasicValue)b).getKey().equals(this.key));
/*    */   }
/*    */   
/*    */   public String getValue()
/*    */   {
/* 38 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configupdater\rawvalues\ConfigBasicValue.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */