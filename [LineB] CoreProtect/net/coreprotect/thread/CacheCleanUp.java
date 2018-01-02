/*    */ package net.coreprotect.thread;
/*    */ 
/*    */ import java.util.Iterator;
/*    */ import java.util.Map.Entry;
/*    */ import net.coreprotect.model.Config;
/*    */ 
/*    */ public class CacheCleanUp implements Runnable
/*    */ {
/*    */   public void run()
/*    */   {
/* 11 */     while (Config.server_running == true) {
/*    */       try {
/* 13 */         int c = 0;
/* 14 */         while (c < 4) {
/* 15 */           Thread.sleep(5000L);
/* 16 */           int scan_time = 30;
/* 17 */           java.util.Map<String, Object[]> cache = Config.lookup_cache;
/* 18 */           switch (c) {
/*    */           case 1: 
/* 20 */             cache = Config.break_cache;
/* 21 */             break;
/*    */           case 2: 
/* 23 */             cache = Config.piston_cache;
/* 24 */             break;
/*    */           case 3: 
/* 26 */             cache = Config.entity_cache;
/* 27 */             scan_time = 3600;
/*    */           }
/*    */           
/* 30 */           int timestamp = (int)(System.currentTimeMillis() / 1000L) - scan_time;
/* 31 */           Iterator<Map.Entry<String, Object[]>> it = cache.entrySet().iterator(); for (;;) { if (it.hasNext()) {
/*    */               try {
/* 33 */                 Map.Entry<String, Object[]> entry = (Map.Entry)it.next();
/* 34 */                 Object[] data = (Object[])entry.getValue();
/* 35 */                 int time = ((Integer)data[0]).intValue();
/* 36 */                 if (time < timestamp) {
/*    */                   try {
/* 38 */                     it.remove();
/*    */                   }
/*    */                   catch (Exception e) {}
/*    */                 }
/*    */               }
/*    */               catch (Exception e) {}
/*    */             }
/*    */           }
/*    */           
/*    */ 
/* 48 */           c++;
/*    */         }
/*    */       }
/*    */       catch (Exception e) {
/* 52 */         e.printStackTrace();
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\thread\CacheCleanUp.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */