/*    */ package org.apache.commons.net.util;
/*    */ 
/*    */ import java.io.Serializable;
/*    */ import java.util.EventListener;
/*    */ import java.util.Iterator;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
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
/*    */ public class ListenerList
/*    */   implements Serializable, Iterable<EventListener>
/*    */ {
/*    */   private static final long serialVersionUID = -1934227607974228213L;
/*    */   private final CopyOnWriteArrayList<EventListener> __listeners;
/*    */   
/*    */   public ListenerList()
/*    */   {
/* 36 */     this.__listeners = new CopyOnWriteArrayList();
/*    */   }
/*    */   
/*    */   public void addListener(EventListener listener)
/*    */   {
/* 41 */     this.__listeners.add(listener);
/*    */   }
/*    */   
/*    */   public void removeListener(EventListener listener)
/*    */   {
/* 46 */     this.__listeners.remove(listener);
/*    */   }
/*    */   
/*    */   public int getListenerCount()
/*    */   {
/* 51 */     return this.__listeners.size();
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Iterator<EventListener> iterator()
/*    */   {
/* 63 */     return this.__listeners.iterator();
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\util\ListenerList.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */