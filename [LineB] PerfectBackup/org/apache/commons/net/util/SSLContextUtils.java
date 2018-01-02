/*    */ package org.apache.commons.net.util;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.security.GeneralSecurityException;
/*    */ import javax.net.ssl.KeyManager;
/*    */ import javax.net.ssl.SSLContext;
/*    */ import javax.net.ssl.TrustManager;
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
/*    */ public class SSLContextUtils
/*    */ {
/*    */   public static SSLContext createSSLContext(String protocol, KeyManager keyManager, TrustManager trustManager)
/*    */     throws IOException
/*    */   {
/* 46 */     return createSSLContext(protocol, 
/* 47 */       new KeyManager[] { keyManager == null ? null : keyManager }, 
/* 48 */       new TrustManager[] { trustManager == null ? null : trustManager });
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public static SSLContext createSSLContext(String protocol, KeyManager[] keyManagers, TrustManager[] trustManagers)
/*    */     throws IOException
/*    */   {
/*    */     try
/*    */     {
/* 63 */       SSLContext ctx = SSLContext.getInstance(protocol);
/* 64 */       ctx.init(keyManagers, trustManagers, null);
/*    */     } catch (GeneralSecurityException e) {
/* 66 */       IOException ioe = new IOException("Could not initialize SSL context");
/* 67 */       ioe.initCause(e);
/* 68 */       throw ioe; }
/*    */     SSLContext ctx;
/* 70 */     return ctx;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\util\SSLContextUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */