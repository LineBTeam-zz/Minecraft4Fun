/*    */ package com.maxmind.geoip;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Location
/*    */ {
/*    */   public String countryCode;
/*    */   
/*    */ 
/*    */   public String countryName;
/*    */   
/*    */ 
/*    */   public String region;
/*    */   
/*    */ 
/*    */   public String city;
/*    */   
/*    */ 
/*    */   public String postalCode;
/*    */   
/*    */ 
/*    */   public float latitude;
/*    */   
/*    */ 
/*    */   public float longitude;
/*    */   
/*    */ 
/*    */   public int dma_code;
/*    */   
/*    */   public int area_code;
/*    */   
/*    */   public int metro_code;
/*    */   
/*    */   private static final double EARTH_DIAMETER = 12756.4D;
/*    */   
/*    */   private static final double PI = 3.14159265D;
/*    */   
/*    */   private static final double RAD_CONVERT = 0.017453292500000002D;
/*    */   
/*    */ 
/*    */   public double distance(Location loc)
/*    */   {
/* 43 */     float lat1 = this.latitude;
/* 44 */     float lon1 = this.longitude;
/* 45 */     float lat2 = loc.latitude;
/* 46 */     float lon2 = loc.longitude;
/*    */     
/*    */ 
/* 49 */     lat1 = (float)(lat1 * 0.017453292500000002D);
/* 50 */     lat2 = (float)(lat2 * 0.017453292500000002D);
/*    */     
/*    */ 
/* 53 */     double delta_lat = lat2 - lat1;
/* 54 */     double delta_lon = (lon2 - lon1) * 0.017453292500000002D;
/*    */     
/*    */ 
/*    */ 
/* 58 */     double temp = Math.pow(Math.sin(delta_lat / 2.0D), 2.0D) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(delta_lon / 2.0D), 2.0D);
/*    */     
/* 60 */     return 12756.4D * Math.atan2(Math.sqrt(temp), Math.sqrt(1.0D - temp));
/*    */   }
/*    */ }
