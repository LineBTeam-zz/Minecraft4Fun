/*    */ package net.risenphoenix.ipcheck.util;
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
/*    */ public class MessageParser
/*    */ {
/*    */   private String[] args;
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
/*    */   private int startPos;
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
/*    */   public MessageParser(String[] args, int startPos)
/*    */   {
/* 40 */     this.args = args;
/* 41 */     this.startPos = startPos;
/*    */   }
/*    */   
/*    */   public String parseMessage() {
/* 45 */     String message = null;
/*    */     
/* 47 */     if (this.args.length >= this.startPos) {
/* 48 */       StringBuilder msgParse = new StringBuilder();
/*    */       
/* 50 */       for (int i = this.startPos; i < this.args.length; i++) {
/* 51 */         msgParse.append(this.args[i]);
/* 52 */         if (i != this.args.length - 1) { msgParse.append(" ");
/*    */         }
/*    */       }
/* 55 */       message = msgParse.toString();
/*    */     }
/*    */     
/* 58 */     return message;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\util\MessageParser.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */