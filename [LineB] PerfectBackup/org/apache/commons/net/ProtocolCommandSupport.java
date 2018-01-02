/*     */ package org.apache.commons.net;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.EventListener;
/*     */ import org.apache.commons.net.util.ListenerList;
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
/*     */ public class ProtocolCommandSupport
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8017692739988399978L;
/*     */   private final Object __source;
/*     */   private final ListenerList __listeners;
/*     */   
/*     */   public ProtocolCommandSupport(Object source)
/*     */   {
/*  51 */     this.__listeners = new ListenerList();
/*  52 */     this.__source = source;
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
/*     */   public void fireCommandSent(String command, String message)
/*     */   {
/*  71 */     ProtocolCommandEvent event = new ProtocolCommandEvent(this.__source, command, message);
/*     */     
/*  73 */     for (EventListener listener : this.__listeners)
/*     */     {
/*  75 */       ((ProtocolCommandListener)listener).protocolCommandSent(event);
/*     */     }
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
/*     */   public void fireReplyReceived(int replyCode, String message)
/*     */   {
/*  95 */     ProtocolCommandEvent event = new ProtocolCommandEvent(this.__source, replyCode, message);
/*     */     
/*  97 */     for (EventListener listener : this.__listeners)
/*     */     {
/*  99 */       ((ProtocolCommandListener)listener).protocolReplyReceived(event);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addProtocolCommandListener(ProtocolCommandListener listener)
/*     */   {
/* 110 */     this.__listeners.addListener(listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeProtocolCommandListener(ProtocolCommandListener listener)
/*     */   {
/* 120 */     this.__listeners.removeListener(listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getListenerCount()
/*     */   {
/* 131 */     return this.__listeners.getListenerCount();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ProtocolCommandSupport.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */