/*    */ package me.minebuilders.clearlag.config.configupdater.rawvalues;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ 
/*    */ public class ConfigComment implements ConfigValue
/*    */ {
/*    */   private String key;
/*    */   
/*    */   public ConfigComment(String value)
/*    */   {
/* 11 */     this.key = value;
/*    */   }
/*    */   
/*    */   public String getKey()
/*    */   {
/* 16 */     return this.key;
/*    */   }
/*    */   
/*    */ 
/*    */   public void merge(ConfigValue value) {}
/*    */   
/*    */ 
/*    */   public void writeToFile(BufferedWriter writer)
/*    */     throws java.io.IOException
/*    */   {
/* 26 */     if (this.key != null) writer.write(this.key);
/*    */   }
/*    */   
/*    */   public boolean equals(Object b)
/*    */   {
/* 31 */     return false;
/*    */   }
/*    */   
/*    */   public Object getValue()
/*    */   {
/* 36 */     return this.key;
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configupdater\rawvalues\ConfigComment.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */