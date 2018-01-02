/*     */ package com.maxmind.geoip;
/*     */ 
/*     */ import java.text.SimpleDateFormat;
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
/*     */ 
/*     */ 
/*     */ public class DatabaseInfo
/*     */ {
/*     */   public static final int COUNTRY_EDITION = 1;
/*     */   public static final int REGION_EDITION_REV0 = 7;
/*     */   public static final int REGION_EDITION_REV1 = 3;
/*     */   public static final int CITY_EDITION_REV0 = 6;
/*     */   public static final int CITY_EDITION_REV1 = 2;
/*     */   public static final int ORG_EDITION = 5;
/*     */   public static final int ISP_EDITION = 4;
/*     */   public static final int PROXY_EDITION = 8;
/*     */   public static final int ASNUM_EDITION = 9;
/*     */   public static final int NETSPEED_EDITION = 10;
/*     */   public static final int DOMAIN_EDITION = 11;
/*     */   public static final int COUNTRY_EDITION_V6 = 12;
/*     */   public static final int ASNUM_EDITION_V6 = 21;
/*     */   public static final int ISP_EDITION_V6 = 22;
/*     */   public static final int ORG_EDITION_V6 = 23;
/*     */   public static final int DOMAIN_EDITION_V6 = 24;
/*     */   public static final int CITY_EDITION_REV1_V6 = 30;
/*     */   public static final int CITY_EDITION_REV0_V6 = 31;
/*     */   public static final int NETSPEED_EDITION_REV1 = 32;
/*     */   public static final int NETSPEED_EDITION_REV1_V6 = 33;
/*  70 */   private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
/*     */   
/*     */ 
/*     */ 
/*     */   private String info;
/*     */   
/*     */ 
/*     */ 
/*     */   public DatabaseInfo(String info)
/*     */   {
/*  80 */     this.info = info;
/*     */   }
/*     */   
/*     */   public int getType() {
/*  84 */     if ((this.info == null) || (this.info.equals(""))) {
/*  85 */       return 1;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*  90 */     return Integer.parseInt(this.info.substring(4, 7)) - 105;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isPremium()
/*     */   {
/* 100 */     return this.info.indexOf("FREE") < 0;
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   public java.util.Date getDate()
/*     */   {
/*     */     // Byte code:
/*     */     //   0: iconst_0
/*     */     //   1: istore_1
/*     */     //   2: iload_1
/*     */     //   3: aload_0
/*     */     //   4: getfield 2	com/maxmind/geoip/DatabaseInfo:info	Ljava/lang/String;
/*     */     //   7: invokevirtual 10	java/lang/String:length	()I
/*     */     //   10: bipush 9
/*     */     //   12: isub
/*     */     //   13: if_icmpge +65 -> 78
/*     */     //   16: aload_0
/*     */     //   17: getfield 2	com/maxmind/geoip/DatabaseInfo:info	Ljava/lang/String;
/*     */     //   20: iload_1
/*     */     //   21: invokevirtual 11	java/lang/String:charAt	(I)C
/*     */     //   24: invokestatic 12	java/lang/Character:isWhitespace	(C)Z
/*     */     //   27: ifeq +45 -> 72
/*     */     //   30: aload_0
/*     */     //   31: getfield 2	com/maxmind/geoip/DatabaseInfo:info	Ljava/lang/String;
/*     */     //   34: iload_1
/*     */     //   35: iconst_1
/*     */     //   36: iadd
/*     */     //   37: iload_1
/*     */     //   38: bipush 9
/*     */     //   40: iadd
/*     */     //   41: invokevirtual 6	java/lang/String:substring	(II)Ljava/lang/String;
/*     */     //   44: astore_2
/*     */     //   45: getstatic 13	com/maxmind/geoip/DatabaseInfo:formatter	Ljava/text/SimpleDateFormat;
/*     */     //   48: dup
/*     */     //   49: astore_3
/*     */     //   50: monitorenter
/*     */     //   51: getstatic 13	com/maxmind/geoip/DatabaseInfo:formatter	Ljava/text/SimpleDateFormat;
/*     */     //   54: aload_2
/*     */     //   55: invokevirtual 14	java/text/SimpleDateFormat:parse	(Ljava/lang/String;)Ljava/util/Date;
/*     */     //   58: aload_3
/*     */     //   59: monitorexit
/*     */     //   60: areturn
/*     */     //   61: astore 4
/*     */     //   63: aload_3
/*     */     //   64: monitorexit
/*     */     //   65: aload 4
/*     */     //   67: athrow
/*     */     //   68: astore_3
/*     */     //   69: goto +9 -> 78
/*     */     //   72: iinc 1 1
/*     */     //   75: goto -73 -> 2
/*     */     //   78: aconst_null
/*     */     //   79: areturn
/*     */     // Line number table:
/*     */     //   Java source line #109	-> byte code offset #0
/*     */     //   Java source line #110	-> byte code offset #16
/*     */     //   Java source line #111	-> byte code offset #30
/*     */     //   Java source line #113	-> byte code offset #45
/*     */     //   Java source line #114	-> byte code offset #51
/*     */     //   Java source line #115	-> byte code offset #61
/*     */     //   Java source line #116	-> byte code offset #68
/*     */     //   Java source line #118	-> byte code offset #69
/*     */     //   Java source line #109	-> byte code offset #72
/*     */     //   Java source line #121	-> byte code offset #78
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	80	0	this	DatabaseInfo
/*     */     //   1	72	1	i	int
/*     */     //   44	11	2	dateString	String
/*     */     //   49	15	3	Ljava/lang/Object;	Object
/*     */     //   68	2	3	pe	java.text.ParseException
/*     */     //   61	5	4	localObject1	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   51	60	61	finally
/*     */     //   61	65	61	finally
/*     */     //   45	60	68	java/text/ParseException
/*     */     //   61	68	68	java/text/ParseException
/*     */   }
/*     */   
/*     */   public String toString()
/*     */   {
/* 126 */     return this.info;
/*     */   }
/*     */ }


