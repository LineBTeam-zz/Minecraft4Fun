/*    */ package org.apache.commons.net.util;
/*    */ 
/*    */ import java.nio.charset.Charset;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Charsets
/*    */ {
/*    */   public static Charset toCharset(String charsetName)
/*    */   {
/* 38 */     return charsetName == null ? Charset.defaultCharset() : Charset.forName(charsetName);
/*    */   }
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
/*    */   public static Charset toCharset(String charsetName, String defaultCharsetName)
/*    */   {
/* 52 */     return charsetName == null ? Charset.forName(defaultCharsetName) : Charset.forName(charsetName);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\util\Charsets.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */