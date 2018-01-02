/*    */ package de.rob1n.prospam.gui;
/*    */ 
/*    */ import org.bukkit.ChatColor;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HiddenId
/*    */ {
/* 11 */   private static int counter = 0;
/*    */   
/* 13 */   private static final String[] HIDDEN_NUMBER = { ChatColor.AQUA + "", ChatColor.BLUE + "", ChatColor.DARK_AQUA + "", ChatColor.DARK_GRAY + "", ChatColor.DARK_BLUE + "", ChatColor.DARK_PURPLE + "", ChatColor.DARK_GREEN + "", ChatColor.DARK_RED + "", ChatColor.GOLD + "", ChatColor.GREEN + "" };
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
/*    */   public static String getNext()
/*    */   {
/* 32 */     StringBuilder sb = new StringBuilder();
/* 33 */     int rest = counter++;
/*    */     
/*    */     do
/*    */     {
/* 37 */       sb.append(HIDDEN_NUMBER[(rest % 10)]);
/* 38 */       rest /= 10;
/*    */     }
/* 40 */     while (rest >= 10);
/*    */     
/* 42 */     return ChatColor.RESET;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static String grabId(String txt)
/*    */   {
/* 52 */     StringBuilder sb = new StringBuilder();
/*    */     
/*    */     try
/*    */     {
/* 56 */       txt = txt.substring(0, txt.indexOf(ChatColor.RESET + ""));
/*    */       
/* 58 */       while (txt.indexOf('§') > -1)
/*    */       {
/* 60 */         int index = txt.indexOf('§');
/*    */         
/* 62 */         sb.append(txt.substring(index, index + 2));
/* 63 */         txt = txt.substring(0, index);
/*    */       }
/*    */     }
/*    */     catch (Exception ignored) {}
/*    */     
/* 68 */     return ChatColor.RESET;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\gui\HiddenId.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */