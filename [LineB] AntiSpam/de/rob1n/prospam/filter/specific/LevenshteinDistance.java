/*    */ package de.rob1n.prospam.filter.specific;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ class LevenshteinDistance
/*    */ {
/*    */   private static int minimum(int a, int b, int c)
/*    */   {
/* 43 */     return Math.min(Math.min(a, b), c);
/*    */   }
/*    */   
/*    */   public static int computeLevenshteinDistance(CharSequence str1, CharSequence str2)
/*    */   {
/* 48 */     int[][] distance = new int[str1.length() + 1][str2.length() + 1];
/*    */     
/* 50 */     for (int i = 0; i <= str1.length(); i++)
/* 51 */       distance[i][0] = i;
/* 52 */     for (int j = 1; j <= str2.length(); j++) {
/* 53 */       distance[0][j] = j;
/*    */     }
/* 55 */     for (int i = 1; i <= str1.length(); i++) {
/* 56 */       for (int j = 1; j <= str2.length(); j++) {
/* 57 */         distance[i][j] = minimum(distance[(i - 1)][j] + 1, distance[i][(j - 1)] + 1, distance[(i - 1)][(j - 1)] + (str1.charAt(i - 1) == str2.charAt(j - 1) ? 0 : 1));
/*    */       }
/*    */     }
/* 60 */     return distance[str1.length()][str2.length()];
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\filter\specific\LevenshteinDistance.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */