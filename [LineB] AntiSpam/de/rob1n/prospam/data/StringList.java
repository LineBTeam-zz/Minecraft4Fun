/*    */ package de.rob1n.prospam.data;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Collection;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class StringList
/*    */   extends ArrayList<String>
/*    */ {
/*    */   public StringList(Collection<String> c)
/*    */   {
/* 14 */     super(c);
/*    */   }
/*    */   
/*    */   public boolean containsIgnoreCase(String str)
/*    */   {
/* 19 */     for (String s : this)
/*    */     {
/* 21 */       if (str.equalsIgnoreCase(s))
/* 22 */         return true;
/*    */     }
/* 24 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\data\StringList.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */