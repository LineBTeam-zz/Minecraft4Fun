/*     */ package org.apache.commons.net.util;
/*     */ 
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.CertificateException;
/*     */ import java.security.cert.X509Certificate;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import javax.net.ssl.X509TrustManager;
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
/*     */ public final class TrustManagerUtils
/*     */ {
/*  35 */   private static final X509Certificate[] EMPTY_X509CERTIFICATE_ARRAY = new X509Certificate[0];
/*     */   
/*     */   private static class TrustManager implements X509TrustManager
/*     */   {
/*     */     private final boolean checkServerValidity;
/*     */     
/*     */     TrustManager(boolean checkServerValidity) {
/*  42 */       this.checkServerValidity = checkServerValidity;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void checkClientTrusted(X509Certificate[] certificates, String authType) {}
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public void checkServerTrusted(X509Certificate[] certificates, String authType)
/*     */       throws CertificateException
/*     */     {
/*  58 */       if (this.checkServerValidity) { X509Certificate[] arrayOfX509Certificate;
/*  59 */         int j = (arrayOfX509Certificate = certificates).length; for (int i = 0; i < j; i++) { X509Certificate certificate = arrayOfX509Certificate[i];
/*     */           
/*  61 */           certificate.checkValidity();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public X509Certificate[] getAcceptedIssuers()
/*     */     {
/*  72 */       return TrustManagerUtils.EMPTY_X509CERTIFICATE_ARRAY;
/*     */     }
/*     */   }
/*     */   
/*  76 */   private static final X509TrustManager ACCEPT_ALL = new TrustManager(false);
/*     */   
/*  78 */   private static final X509TrustManager CHECK_SERVER_VALIDITY = new TrustManager(true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static X509TrustManager getAcceptAllTrustManager()
/*     */   {
/*  86 */     return ACCEPT_ALL;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static X509TrustManager getValidateServerCertificateTrustManager()
/*     */   {
/*  96 */     return CHECK_SERVER_VALIDITY;
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
/*     */   public static X509TrustManager getDefaultTrustManager(KeyStore keyStore)
/*     */     throws GeneralSecurityException
/*     */   {
/* 110 */     String defaultAlgorithm = TrustManagerFactory.getDefaultAlgorithm();
/* 111 */     TrustManagerFactory instance = TrustManagerFactory.getInstance(defaultAlgorithm);
/* 112 */     instance.init(keyStore);
/* 113 */     return (X509TrustManager)instance.getTrustManagers()[0];
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\util\TrustManagerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */