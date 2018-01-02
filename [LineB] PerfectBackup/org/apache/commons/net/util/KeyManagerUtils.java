/*     */ package org.apache.commons.net.util;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.net.Socket;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.KeyStore;
/*     */ import java.security.KeyStoreException;
/*     */ import java.security.Principal;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.Enumeration;
/*     */ import javax.net.ssl.KeyManager;
/*     */ import javax.net.ssl.X509ExtendedKeyManager;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class KeyManagerUtils
/*     */ {
/*  67 */   private static final String DEFAULT_STORE_TYPE = ;
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
/*     */   public static KeyManager createClientKeyManager(KeyStore ks, String keyAlias, String keyPass)
/*     */     throws GeneralSecurityException
/*     */   {
/*  85 */     ClientKeyStore cks = new ClientKeyStore(ks, keyAlias != null ? keyAlias : findAlias(ks), keyPass);
/*  86 */     return new X509KeyManager(cks);
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
/*     */   public static KeyManager createClientKeyManager(String storeType, File storePath, String storePass, String keyAlias, String keyPass)
/*     */     throws IOException, GeneralSecurityException
/*     */   {
/* 103 */     KeyStore ks = loadStore(storeType, storePath, storePass);
/* 104 */     return createClientKeyManager(ks, keyAlias, keyPass);
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
/*     */   public static KeyManager createClientKeyManager(File storePath, String storePass, String keyAlias)
/*     */     throws IOException, GeneralSecurityException
/*     */   {
/* 120 */     return createClientKeyManager(DEFAULT_STORE_TYPE, storePath, storePass, keyAlias, storePass);
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
/*     */   public static KeyManager createClientKeyManager(File storePath, String storePass)
/*     */     throws IOException, GeneralSecurityException
/*     */   {
/* 136 */     return createClientKeyManager(DEFAULT_STORE_TYPE, storePath, storePass, null, storePass);
/*     */   }
/*     */   
/*     */   /* Error */
/*     */   private static KeyStore loadStore(String storeType, File storePath, String storePass)
/*     */     throws KeyStoreException, IOException, GeneralSecurityException
/*     */   {
/*     */     // Byte code:
/*     */     //   0: aload_0
/*     */     //   1: invokestatic 72	java/security/KeyStore:getInstance	(Ljava/lang/String;)Ljava/security/KeyStore;
/*     */     //   4: astore_3
/*     */     //   5: aconst_null
/*     */     //   6: astore 4
/*     */     //   8: new 76	java/io/FileInputStream
/*     */     //   11: dup
/*     */     //   12: aload_1
/*     */     //   13: invokespecial 78	java/io/FileInputStream:<init>	(Ljava/io/File;)V
/*     */     //   16: astore 4
/*     */     //   18: aload_3
/*     */     //   19: aload 4
/*     */     //   21: aload_2
/*     */     //   22: invokevirtual 81	java/lang/String:toCharArray	()[C
/*     */     //   25: invokevirtual 85	java/security/KeyStore:load	(Ljava/io/InputStream;[C)V
/*     */     //   28: goto +13 -> 41
/*     */     //   31: astore 5
/*     */     //   33: aload 4
/*     */     //   35: invokestatic 89	org/apache/commons/net/io/Util:closeQuietly	(Ljava/io/Closeable;)V
/*     */     //   38: aload 5
/*     */     //   40: athrow
/*     */     //   41: aload 4
/*     */     //   43: invokestatic 89	org/apache/commons/net/io/Util:closeQuietly	(Ljava/io/Closeable;)V
/*     */     //   46: aload_3
/*     */     //   47: areturn
/*     */     // Line number table:
/*     */     //   Java source line #141	-> byte code offset #0
/*     */     //   Java source line #142	-> byte code offset #5
/*     */     //   Java source line #144	-> byte code offset #8
/*     */     //   Java source line #145	-> byte code offset #18
/*     */     //   Java source line #146	-> byte code offset #28
/*     */     //   Java source line #147	-> byte code offset #33
/*     */     //   Java source line #148	-> byte code offset #38
/*     */     //   Java source line #147	-> byte code offset #41
/*     */     //   Java source line #149	-> byte code offset #46
/*     */     // Local variable table:
/*     */     //   start	length	slot	name	signature
/*     */     //   0	48	0	storeType	String
/*     */     //   0	48	1	storePath	File
/*     */     //   0	48	2	storePass	String
/*     */     //   4	43	3	ks	KeyStore
/*     */     //   6	36	4	stream	java.io.FileInputStream
/*     */     //   31	8	5	localObject	Object
/*     */     // Exception table:
/*     */     //   from	to	target	type
/*     */     //   8	31	31	finally
/*     */   }
/*     */   
/*     */   private static String findAlias(KeyStore ks)
/*     */     throws KeyStoreException
/*     */   {
/* 153 */     Enumeration<String> e = ks.aliases();
/* 154 */     while (e.hasMoreElements()) {
/* 155 */       String entry = (String)e.nextElement();
/* 156 */       if (ks.isKeyEntry(entry)) {
/* 157 */         return entry;
/*     */       }
/*     */     }
/* 160 */     throw new KeyStoreException("Cannot find a private key entry");
/*     */   }
/*     */   
/*     */   private static class ClientKeyStore
/*     */   {
/*     */     private final X509Certificate[] certChain;
/*     */     private final PrivateKey key;
/*     */     private final String keyAlias;
/*     */     
/*     */     ClientKeyStore(KeyStore ks, String keyAlias, String keyPass) throws GeneralSecurityException
/*     */     {
/* 171 */       this.keyAlias = keyAlias;
/* 172 */       this.key = ((PrivateKey)ks.getKey(this.keyAlias, keyPass.toCharArray()));
/* 173 */       Certificate[] certs = ks.getCertificateChain(this.keyAlias);
/* 174 */       X509Certificate[] X509certs = new X509Certificate[certs.length];
/* 175 */       for (int i = 0; i < certs.length; i++) {
/* 176 */         X509certs[i] = ((X509Certificate)certs[i]);
/*     */       }
/* 178 */       this.certChain = X509certs;
/*     */     }
/*     */     
/*     */     final X509Certificate[] getCertificateChain() {
/* 182 */       return this.certChain;
/*     */     }
/*     */     
/*     */     final PrivateKey getPrivateKey() {
/* 186 */       return this.key;
/*     */     }
/*     */     
/*     */     final String getAlias() {
/* 190 */       return this.keyAlias;
/*     */     }
/*     */   }
/*     */   
/*     */   private static class X509KeyManager extends X509ExtendedKeyManager
/*     */   {
/*     */     private final KeyManagerUtils.ClientKeyStore keyStore;
/*     */     
/*     */     X509KeyManager(KeyManagerUtils.ClientKeyStore keyStore) {
/* 199 */       this.keyStore = keyStore;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     public String chooseClientAlias(String[] keyType, Principal[] issuers, Socket socket)
/*     */     {
/* 206 */       return this.keyStore.getAlias();
/*     */     }
/*     */     
/*     */ 
/*     */     public X509Certificate[] getCertificateChain(String alias)
/*     */     {
/* 212 */       return this.keyStore.getCertificateChain();
/*     */     }
/*     */     
/*     */     public String[] getClientAliases(String keyType, Principal[] issuers)
/*     */     {
/* 217 */       return new String[] { this.keyStore.getAlias() };
/*     */     }
/*     */     
/*     */ 
/*     */     public PrivateKey getPrivateKey(String alias)
/*     */     {
/* 223 */       return this.keyStore.getPrivateKey();
/*     */     }
/*     */     
/*     */     public String[] getServerAliases(String keyType, Principal[] issuers)
/*     */     {
/* 228 */       return null;
/*     */     }
/*     */     
/*     */     public String chooseServerAlias(String keyType, Principal[] issuers, Socket socket)
/*     */     {
/* 233 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\util\KeyManagerUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */