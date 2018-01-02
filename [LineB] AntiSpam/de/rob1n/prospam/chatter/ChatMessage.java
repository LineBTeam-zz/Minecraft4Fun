/*    */ package de.rob1n.prospam.chatter;
/*    */ 
/*    */ import java.util.Date;
/*    */ 
/*    */ public class ChatMessage
/*    */ {
/*    */   public final long timeSubmitted;
/*    */   public final String message;
/*    */   
/*    */   public ChatMessage(String message)
/*    */   {
/* 12 */     this.timeSubmitted = new Date().getTime();
/* 13 */     this.message = message;
/*    */   }
/*    */ }
