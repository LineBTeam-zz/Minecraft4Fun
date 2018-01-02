/*    */ package org.apache.commons.net.ftp;
/*    */ 
/*    */ import java.security.cert.CertificateException;
/*    */ import java.security.cert.X509Certificate;
/*    */ import javax.net.ssl.X509TrustManager;
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
/*    */ @Deprecated
/*    */ public class FTPSTrustManager
/*    */   implements X509TrustManager
/*    */ {
/* 33 */   private static final X509Certificate[] EMPTY_X509CERTIFICATE_ARRAY = new X509Certificate[0];
/*    */   
/*    */ 
/*    */ 
/*    */   public void checkClientTrusted(X509Certificate[] certificates, String authType) {}
/*    */   
/*    */ 
/*    */ 
/*    */   public void checkServerTrusted(X509Certificate[] certificates, String authType)
/*    */     throws CertificateException
/*    */   {
/*    */     X509Certificate[] arrayOfX509Certificate;
/*    */     
/*    */ 
/* 47 */     int j = (arrayOfX509Certificate = certificates).length; for (int i = 0; i < j; i++) { X509Certificate certificate = arrayOfX509Certificate[i];
/*    */       
/* 49 */       certificate.checkValidity();
/*    */     }
/*    */   }
/*    */   
/*    */ 
/*    */   public X509Certificate[] getAcceptedIssuers()
/*    */   {
/* 56 */     return EMPTY_X509CERTIFICATE_ARRAY;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\FTPSTrustManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */