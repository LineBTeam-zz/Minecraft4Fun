/*     */ package org.apache.commons.net;
/*     */ 
/*     */ import java.util.EventObject;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ProtocolCommandEvent
/*     */   extends EventObject
/*     */ {
/*     */   private static final long serialVersionUID = 403743538418947240L;
/*     */   private final int __replyCode;
/*     */   private final boolean __isCommand;
/*     */   private final String __message;
/*     */   private final String __command;
/*     */   
/*     */   public ProtocolCommandEvent(Object source, String command, String message)
/*     */   {
/*  57 */     super(source);
/*  58 */     this.__replyCode = 0;
/*  59 */     this.__message = message;
/*  60 */     this.__isCommand = true;
/*  61 */     this.__command = command;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ProtocolCommandEvent(Object source, int replyCode, String message)
/*     */   {
/*  81 */     super(source);
/*  82 */     this.__replyCode = replyCode;
/*  83 */     this.__message = message;
/*  84 */     this.__isCommand = false;
/*  85 */     this.__command = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCommand()
/*     */   {
/*  98 */     return this.__command;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getReplyCode()
/*     */   {
/* 111 */     return this.__replyCode;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isCommand()
/*     */   {
/* 123 */     return this.__isCommand;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isReply()
/*     */   {
/* 135 */     return !isCommand();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 146 */     return this.__message;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ProtocolCommandEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */