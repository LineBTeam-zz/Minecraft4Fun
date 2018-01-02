/*    */ package com.solarcloud7.cloudtrade;
/*    */ 
/*    */ import java.io.BufferedWriter;
/*    */ import java.io.File;
/*    */ import java.text.DateFormat;
/*    */ import java.util.Calendar;
/*    */ import java.util.List;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class TradeLogger
/*    */ {
/*    */   TradeLogger(TradeManager _tm, List<ItemStack> _senderItems, List<ItemStack> _targetItems)
/*    */   {
/*    */     try
/*    */     {
/* 18 */       File a = new File("plugins/CloudTrade/tradelogs.txt");
/* 19 */       java.io.FileWriter f = new java.io.FileWriter(a, true);
/* 20 */       if (!a.exists()) {
/* 21 */         a.createNewFile();
/*    */       }
/* 23 */       BufferedWriter out = new BufferedWriter(f);
/*    */       
/* 25 */       DateFormat dateFormat = new java.text.SimpleDateFormat("EEE, MMM d, hh:mm aaa");
/* 26 */       Calendar cal = Calendar.getInstance();
/* 27 */       String sDate = dateFormat.format(cal.getTime());
/*    */       
/* 29 */       String sTPsenderName = _tm.getTPsender().getPlayer().getName();
/* 30 */       String sTPtargetName = _tm.getTPtarget().getPlayer().getName();
/*    */       
/* 32 */       out.write("[" + sDate + "]  [" + sTPsenderName + "]-[" + sTPtargetName + "]");
/* 33 */       out.newLine();
/* 34 */       out.write("   " + sTPsenderName + "]:");
/* 35 */       for (ItemStack i : _senderItems) {
/* 36 */         if (i != null)
/*    */         {
/*    */ 
/* 39 */           out.write(" {" + i.getType().name() + " x" + i.getAmount() + "}"); }
/*    */       }
/* 41 */       out.newLine();
/* 42 */       out.write("   " + sTPtargetName + "]:");
/* 43 */       for (ItemStack i : _senderItems) {
/* 44 */         if (i != null)
/*    */         {
/*    */ 
/* 47 */           out.write(" {" + i.getType().name() + " x" + i.getAmount() + "}"); }
/*    */       }
/* 49 */       out.newLine();
/* 50 */       out.newLine();
/* 51 */       out.close();
/*    */     } catch (Exception e) {
/* 53 */       e.printStackTrace();
/*    */     }
/*    */   }
/*    */ }


