/*    */ package me.minebuilders.clearlag.config.configupdater;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.List;
/*    */ import me.minebuilders.clearlag.config.configupdater.rawvalues.ConfigComment;
/*    */ import me.minebuilders.clearlag.config.configupdater.rawvalues.ConfigValue;
/*    */ 
/*    */ 
/*    */ public class ConfigSection
/*    */ {
/*    */   private String key;
/* 15 */   private List<ConfigValue> body = new ArrayList();
/*    */   
/*    */   public boolean hasKey() {
/* 18 */     return this.key != null;
/*    */   }
/*    */   
/*    */   public void setKey(String key) {
/* 22 */     this.key = key;
/* 23 */     this.body.add(new ConfigComment(key));
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 27 */     return this.key;
/*    */   }
/*    */   
/*    */   public void addConfigValue(ConfigValue cv) {
/* 31 */     this.body.add(cv);
/*    */   }
/*    */   
/*    */   public List<ConfigValue> getBody() {
/* 35 */     return this.body;
/*    */   }
/*    */   
/*    */   public void merge(ConfigSection cs) {
/* 39 */     for (Iterator i$ = cs.getBody().iterator(); i$.hasNext();) { cv = (ConfigValue)i$.next();
/* 40 */       for (ConfigValue cv2 : this.body) {
/* 41 */         if (cv.equals(cv2))
/* 42 */           cv2.merge(cv);
/*    */       }
/*    */     }
/*    */     ConfigValue cv;
/*    */   }
/*    */   
/*    */   public void writeToFile(BufferedWriter writer) throws IOException {
/* 49 */     for (ConfigValue s : this.body) {
/* 50 */       s.writeToFile(writer);
/* 51 */       writer.newLine();
/*    */     }
/*    */   }
/*    */   
/*    */   public String toString()
/*    */   {
/* 57 */     return this.key;
/*    */   }
/*    */   
/*    */   public boolean equals(Object ob)
/*    */   {
/* 62 */     return this.key.equals(ob.toString());
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configupdater\ConfigSection.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */