/*    */ package me.minebuilders.clearlag.config.configupdater.rawvalues;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ConfigListValue implements ConfigValue
/*    */ {
/*    */   private String key;
/* 11 */   private List<String> strings = new ArrayList();
/*    */   
/*    */   public ConfigListValue(String value) {
/* 14 */     this.key = value;
/*    */   }
/*    */   
/*    */   public String getKey()
/*    */   {
/* 19 */     return this.key;
/*    */   }
/*    */   
/*    */ 
/*    */   public void merge(ConfigValue value)
/*    */   {
/* 25 */     this.strings = ((List)value.getValue());
/*    */   }
/*    */   
/*    */   public void addValue(String s) {
/* 29 */     this.strings.add(s);
/*    */   }
/*    */   
/*    */   public void writeToFile(BufferedWriter writer) throws IOException
/*    */   {
/* 34 */     writer.write("  " + this.key + ":");
/*    */     
/* 36 */     for (String s : this.strings) {
/* 37 */       writer.newLine();
/* 38 */       writer.write(s);
/*    */     }
/*    */   }
/*    */   
/*    */   public boolean equals(Object b)
/*    */   {
/* 44 */     return ((b instanceof ConfigListValue)) && (((ConfigListValue)b).getKey().equals(this.key));
/*    */   }
/*    */   
/*    */   public Object getValue()
/*    */   {
/* 49 */     return this.strings;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configupdater\rawvalues\ConfigListValue.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */