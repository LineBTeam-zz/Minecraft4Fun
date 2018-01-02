/*    */ package com.evilmidget38;
/*    */ 
/*    */ import java.io.OutputStream;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URL;
/*    */ import java.nio.ByteBuffer;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.UUID;
/*    */ import org.json.simple.JSONArray;
/*    */ import org.json.simple.JSONObject;
/*    */ import org.json.simple.parser.JSONParser;
/*    */ 
/*    */ public class UUIDFetcher implements java.util.concurrent.Callable<Map<String, UUID>>
/*    */ {
/*    */   private static final double PROFILES_PER_REQUEST = 100.0D;
/*    */   private static final String PROFILE_URL = "https://api.mojang.com/profiles/minecraft";
/* 19 */   private final JSONParser jsonParser = new JSONParser();
/*    */   private final List<String> names;
/*    */   private final boolean rateLimiting;
/*    */   
/*    */   public UUIDFetcher(List<String> names, boolean rateLimiting) {
/* 24 */     this.names = com.google.common.collect.ImmutableList.copyOf(names);
/* 25 */     this.rateLimiting = rateLimiting;
/*    */   }
/*    */   
/*    */   public UUIDFetcher(List<String> names) {
/* 29 */     this(names, true);
/*    */   }
/*    */   
/*    */   public Map<String, UUID> call() throws Exception {
/* 33 */     Map<String, UUID> uuidMap = new HashMap();
/* 34 */     int requests = (int)Math.ceil(this.names.size() / 100.0D);
/* 35 */     for (int i = 0; i < requests; i++) {
/* 36 */       HttpURLConnection connection = createConnection();
/* 37 */       String body = JSONArray.toJSONString(this.names.subList(i * 100, Math.min((i + 1) * 100, this.names.size())));
/* 38 */       writeBody(connection, body);
/* 39 */       JSONArray array = (JSONArray)this.jsonParser.parse(new java.io.InputStreamReader(connection.getInputStream()));
/* 40 */       for (Object profile : array) {
/* 41 */         JSONObject jsonProfile = (JSONObject)profile;
/* 42 */         String id = (String)jsonProfile.get("id");
/* 43 */         String name = (String)jsonProfile.get("name");
/* 44 */         UUID uuid = getUUID(id);
/* 45 */         uuidMap.put(name, uuid);
/*    */       }
/* 47 */       if ((this.rateLimiting) && (i != requests - 1)) {
/* 48 */         Thread.sleep(100L);
/*    */       }
/*    */     }
/* 51 */     return uuidMap;
/*    */   }
/*    */   
/*    */   private static void writeBody(HttpURLConnection connection, String body) throws Exception {
/* 55 */     OutputStream stream = connection.getOutputStream();
/* 56 */     stream.write(body.getBytes());
/* 57 */     stream.flush();
/* 58 */     stream.close();
/*    */   }
/*    */   
/*    */   private static HttpURLConnection createConnection() throws Exception {
/* 62 */     URL url = new URL("https://api.mojang.com/profiles/minecraft");
/* 63 */     HttpURLConnection connection = (HttpURLConnection)url.openConnection();
/* 64 */     connection.setRequestMethod("POST");
/* 65 */     connection.setRequestProperty("Content-Type", "application/json");
/* 66 */     connection.setUseCaches(false);
/* 67 */     connection.setDoInput(true);
/* 68 */     connection.setDoOutput(true);
/* 69 */     return connection;
/*    */   }
/*    */   
/*    */   private static UUID getUUID(String id) {
/* 73 */     return UUID.fromString(id.substring(0, 8) + "-" + id.substring(8, 12) + "-" + id.substring(12, 16) + "-" + id.substring(16, 20) + "-" + id.substring(20, 32));
/*    */   }
/*    */   
/*    */   public static byte[] toBytes(UUID uuid) {
/* 77 */     ByteBuffer byteBuffer = ByteBuffer.wrap(new byte[16]);
/* 78 */     byteBuffer.putLong(uuid.getMostSignificantBits());
/* 79 */     byteBuffer.putLong(uuid.getLeastSignificantBits());
/* 80 */     return byteBuffer.array();
/*    */   }
/*    */   
/*    */   public static UUID fromBytes(byte[] array) {
/* 84 */     if (array.length != 16) {
/* 85 */       throw new IllegalArgumentException("Illegal byte array length: " + array.length);
/*    */     }
/* 87 */     ByteBuffer byteBuffer = ByteBuffer.wrap(array);
/* 88 */     long mostSignificant = byteBuffer.getLong();
/* 89 */     long leastSignificant = byteBuffer.getLong();
/* 90 */     return new UUID(mostSignificant, leastSignificant);
/*    */   }
/*    */   
/*    */   public static UUID getUUIDOf(String name) throws Exception {
/* 94 */     return (UUID)new UUIDFetcher(java.util.Arrays.asList(new String[] { name })).call().get(name);
/*    */   }
/*    */ }


