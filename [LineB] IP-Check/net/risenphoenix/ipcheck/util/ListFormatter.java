/*    */ package net.risenphoenix.ipcheck.util;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
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
/*    */ public class ListFormatter
/*    */ {
/*    */   private ArrayList<String> input;
/* 38 */   private FormatFilter filter = null;
/*    */   
/*    */   public ListFormatter(ArrayList<String> input) {
/* 41 */     this.input = input;
/*    */   }
/*    */   
/*    */   public ListFormatter(ArrayList<String> input, FormatFilter filter) {
/* 45 */     this.input = input;
/* 46 */     this.filter = filter;
/*    */   }
/*    */   
/*    */   public StringBuilder getFormattedList()
/*    */   {
/* 51 */     ArrayList<String> filtered = new ArrayList();
/*    */     
/*    */     String[] convert;
/*    */     
/* 55 */     if (this.filter != null) { String result;
/* 56 */       for (Iterator localIterator = this.input.iterator(); localIterator.hasNext(); 
/*    */           
/* 58 */           filtered.add(result))
/*    */       {
/* 56 */         String s = (String)localIterator.next();
/* 57 */         result = this.filter.execute(s);
/* 58 */         if ((result == null) || (result.equals(""))) {}
/*    */       }
/*    */       
/* 61 */       String[] convert = new String[filtered.size()];
/* 62 */       filtered.toArray(convert);
/*    */     } else {
/* 64 */       convert = new String[this.input.size()];
/* 65 */       this.input.toArray(convert);
/*    */     }
/*    */     
/*    */ 
/* 69 */     StringBuilder sb = new StringBuilder();
/*    */     
/*    */ 
/* 72 */     for (int i = 0; i < convert.length; i++) {
/* 73 */       if (convert.length == 1) {
/* 74 */         sb.append(convert[0]);
/* 75 */         break; }
/* 76 */       if (convert.length == 2) {
/* 77 */         sb.append(convert[0]);
/* 78 */         sb.append(" and ");
/* 79 */         sb.append(convert[1]);
/* 80 */         break; }
/* 81 */       if (convert.length > 2) {
/* 82 */         sb.append(convert[i]);
/*    */         
/* 84 */         if (i == convert.length - 2) {
/* 85 */           sb.append(" and ");
/* 86 */         } else if (i == convert.length - 1) {
/* 87 */           sb.append(".");
/*    */         } else {
/* 89 */           sb.append(", ");
/*    */         }
/*    */       }
/*    */     }
/*    */     
/* 94 */     return sb;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\util\ListFormatter.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */