/*     */ package net.coreprotect.consumer;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class Consumer
/*     */   implements Runnable
/*     */ {
/*  15 */   public static int current_consumer = 0;
/*  16 */   public static boolean is_paused = false;
/*  17 */   private static boolean running = false;
/*  18 */   protected static boolean pause_success = false;
/*  19 */   static Map<Integer, ArrayList<Object[]>> consumer = Collections.synchronizedMap(new HashMap());
/*  20 */   static Map<Integer, Integer> consumer_id = Collections.synchronizedMap(new HashMap());
/*  21 */   static Map<Integer, Map<Integer, String[]>> consumer_users = Collections.synchronizedMap(new HashMap());
/*  22 */   static Map<Integer, Map<Integer, String>> consumer_strings = Collections.synchronizedMap(new HashMap());
/*  23 */   static Map<Integer, Map<Integer, Object>> consumer_object = Collections.synchronizedMap(new HashMap());
/*  24 */   static Map<Integer, Map<Integer, String[]>> consumer_signs = Collections.synchronizedMap(new HashMap());
/*  25 */   static Map<Integer, Map<Integer, ItemStack[]>> consumer_containers = Collections.synchronizedMap(new HashMap());
/*  26 */   static Map<Integer, Map<Integer, Object>> consumer_inventories = Collections.synchronizedMap(new HashMap());
/*  27 */   static Map<Integer, Map<Integer, List<BlockState>>> consumer_block_list = Collections.synchronizedMap(new HashMap());
/*  28 */   static Map<Integer, Map<Integer, List<Object[]>>> consumer_object_array_list = Collections.synchronizedMap(new HashMap());
/*  29 */   static Map<Integer, Map<Integer, List<Object>>> consumer_object_list = Collections.synchronizedMap(new HashMap());
/*     */   
/*     */   private static void errorDelay()
/*     */   {
/*     */     try {
/*  34 */       Thread.sleep(30000L);
/*     */     }
/*     */     catch (Exception e) {
/*  37 */       e.printStackTrace();
/*     */     }
/*     */   }
/*     */   
/*     */   public static int getConsumerId() {
/*  42 */     return ((Integer)consumer_id.get(Integer.valueOf(current_consumer))).intValue();
/*     */   }
/*     */   
/*     */   public static void initialize() {
/*  46 */     consumer.put(Integer.valueOf(0), new ArrayList());
/*  47 */     consumer.put(Integer.valueOf(1), new ArrayList());
/*  48 */     consumer_users.put(Integer.valueOf(0), new HashMap());
/*  49 */     consumer_users.put(Integer.valueOf(1), new HashMap());
/*  50 */     consumer_strings.put(Integer.valueOf(0), new HashMap());
/*  51 */     consumer_strings.put(Integer.valueOf(1), new HashMap());
/*  52 */     consumer_object.put(Integer.valueOf(0), new HashMap());
/*  53 */     consumer_object.put(Integer.valueOf(1), new HashMap());
/*  54 */     consumer_signs.put(Integer.valueOf(0), new HashMap());
/*  55 */     consumer_signs.put(Integer.valueOf(1), new HashMap());
/*  56 */     consumer_inventories.put(Integer.valueOf(0), new HashMap());
/*  57 */     consumer_inventories.put(Integer.valueOf(1), new HashMap());
/*  58 */     consumer_block_list.put(Integer.valueOf(0), new HashMap());
/*  59 */     consumer_block_list.put(Integer.valueOf(1), new HashMap());
/*  60 */     consumer_object_array_list.put(Integer.valueOf(0), new HashMap());
/*  61 */     consumer_object_array_list.put(Integer.valueOf(1), new HashMap());
/*  62 */     consumer_object_list.put(Integer.valueOf(0), new HashMap());
/*  63 */     consumer_object_list.put(Integer.valueOf(1), new HashMap());
/*  64 */     consumer_containers.put(Integer.valueOf(0), new HashMap());
/*  65 */     consumer_containers.put(Integer.valueOf(1), new HashMap());
/*  66 */     consumer_id.put(Integer.valueOf(0), Integer.valueOf(0));
/*  67 */     consumer_id.put(Integer.valueOf(1), Integer.valueOf(0));
/*     */   }
/*     */   
/*     */   public static boolean isRunning() {
/*  71 */     return running;
/*     */   }
/*     */   
/*     */   private static void pauseConsumer() {
/*     */     try {
/*  76 */       while ((Config.server_running == true) && ((is_paused == true) || (Config.purge_running == true))) {
/*  77 */         pause_success = true;
/*  78 */         Thread.sleep(100L);
/*     */       }
/*     */     }
/*     */     catch (Exception e) {
/*  82 */       e.printStackTrace();
/*     */     }
/*  84 */     pause_success = false;
/*     */   }
/*     */   
/*     */   public void run()
/*     */   {
/*  89 */     running = true;
/*  90 */     is_paused = false;
/*  91 */     while ((Config.server_running == true) || (Config.converter_running == true)) {
/*     */       try {
/*  93 */         int process_id = 0;
/*  94 */         if (current_consumer == 0) {
/*  95 */           current_consumer = 1;
/*  96 */           consumer_id.put(Integer.valueOf(current_consumer), Integer.valueOf(0));
/*     */         }
/*     */         else {
/*  99 */           process_id = 1;
/* 100 */           current_consumer = 0;
/* 101 */           consumer_id.put(Integer.valueOf(current_consumer), Integer.valueOf(0));
/*     */         }
/* 103 */         Thread.sleep(500L);
/* 104 */         pauseConsumer();
/* 105 */         Process.processConsumer(process_id);
/*     */       }
/*     */       catch (Exception e) {
/* 108 */         e.printStackTrace();
/* 109 */         errorDelay();
/*     */       }
/*     */     }
/* 112 */     running = false;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\consumer\Consumer.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */