/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.util.Locale;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.apache.commons.net.ftp.Configurable;
/*     */ import org.apache.commons.net.ftp.FTPClientConfig;
/*     */ import org.apache.commons.net.ftp.FTPFileEntryParser;
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
/*     */ public class DefaultFTPFileEntryParserFactory
/*     */   implements FTPFileEntryParserFactory
/*     */ {
/*     */   private static final String JAVA_IDENTIFIER = "\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*";
/*     */   private static final String JAVA_QUALIFIED_NAME = "(\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*\\.)+\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*";
/*  46 */   private static final Pattern JAVA_QUALIFIED_NAME_PATTERN = Pattern.compile("(\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*\\.)+\\p{javaJavaIdentifierStart}(\\p{javaJavaIdentifierPart})*");
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
/*     */   public FTPFileEntryParser createFileEntryParser(String key)
/*     */   {
/*  92 */     if (key == null) {
/*  93 */       throw new ParserInitializationException("Parser key cannot be null");
/*     */     }
/*  95 */     return createFileEntryParser(key, null);
/*     */   }
/*     */   
/*     */   private FTPFileEntryParser createFileEntryParser(String key, FTPClientConfig config)
/*     */   {
/* 100 */     FTPFileEntryParser parser = null;
/*     */     
/*     */ 
/* 103 */     if (JAVA_QUALIFIED_NAME_PATTERN.matcher(key).matches()) {
/*     */       try
/*     */       {
/* 106 */         Class<?> parserClass = Class.forName(key);
/*     */         try {
/* 108 */           parser = (FTPFileEntryParser)parserClass.newInstance();
/*     */         } catch (ClassCastException e) {
/* 110 */           throw new ParserInitializationException(parserClass.getName() + 
/* 111 */             " does not implement the interface " + 
/* 112 */             "org.apache.commons.net.ftp.FTPFileEntryParser.", e);
/*     */         } catch (Exception e) {
/* 114 */           throw new ParserInitializationException("Error initializing parser", e);
/*     */         } catch (ExceptionInInitializerError e) {
/* 116 */           throw new ParserInitializationException("Error initializing parser", e);
/*     */         }
/*     */       }
/*     */       catch (ClassNotFoundException localClassNotFoundException) {}
/*     */     }
/*     */     
/*     */ 
/* 123 */     if (parser == null) {
/* 124 */       String ukey = key.toUpperCase(Locale.ENGLISH);
/* 125 */       if (ukey.indexOf("UNIX") >= 0)
/*     */       {
/* 127 */         parser = new UnixFTPEntryParser(config);
/*     */       }
/* 129 */       else if (ukey.indexOf("VMS") >= 0)
/*     */       {
/* 131 */         parser = new VMSVersioningFTPEntryParser(config);
/*     */       }
/* 133 */       else if (ukey.indexOf("WINDOWS") >= 0)
/*     */       {
/* 135 */         parser = createNTFTPEntryParser(config);
/*     */       }
/* 137 */       else if (ukey.indexOf("OS/2") >= 0)
/*     */       {
/* 139 */         parser = new OS2FTPEntryParser(config);
/*     */       }
/* 141 */       else if ((ukey.indexOf("OS/400") >= 0) || 
/* 142 */         (ukey.indexOf("AS/400") >= 0))
/*     */       {
/* 144 */         parser = createOS400FTPEntryParser(config);
/*     */       }
/* 146 */       else if (ukey.indexOf("MVS") >= 0)
/*     */       {
/* 148 */         parser = new MVSFTPEntryParser();
/*     */       }
/* 150 */       else if (ukey.indexOf("NETWARE") >= 0)
/*     */       {
/* 152 */         parser = new NetwareFTPEntryParser(config);
/*     */       }
/* 154 */       else if (ukey.indexOf("MACOS PETER") >= 0)
/*     */       {
/* 156 */         parser = new MacOsPeterFTPEntryParser(config);
/*     */       }
/* 158 */       else if (ukey.indexOf("TYPE: L8") >= 0)
/*     */       {
/*     */ 
/*     */ 
/* 162 */         parser = new UnixFTPEntryParser(config);
/*     */       }
/*     */       else
/*     */       {
/* 166 */         throw new ParserInitializationException("Unknown parser type: " + key);
/*     */       }
/*     */     }
/*     */     
/* 170 */     if ((parser instanceof Configurable)) {
/* 171 */       ((Configurable)parser).configure(config);
/*     */     }
/* 173 */     return parser;
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
/*     */   public FTPFileEntryParser createFileEntryParser(FTPClientConfig config)
/*     */     throws ParserInitializationException
/*     */   {
/* 201 */     String key = config.getServerSystemKey();
/* 202 */     return createFileEntryParser(key, config);
/*     */   }
/*     */   
/*     */ 
/*     */   public FTPFileEntryParser createUnixFTPEntryParser()
/*     */   {
/* 208 */     return new UnixFTPEntryParser();
/*     */   }
/*     */   
/*     */   public FTPFileEntryParser createVMSVersioningFTPEntryParser()
/*     */   {
/* 213 */     return new VMSVersioningFTPEntryParser();
/*     */   }
/*     */   
/*     */   public FTPFileEntryParser createNetwareFTPEntryParser() {
/* 217 */     return new NetwareFTPEntryParser();
/*     */   }
/*     */   
/*     */   public FTPFileEntryParser createNTFTPEntryParser()
/*     */   {
/* 222 */     return createNTFTPEntryParser(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private FTPFileEntryParser createNTFTPEntryParser(FTPClientConfig config)
/*     */   {
/* 234 */     if ((config != null) && 
/* 235 */       ("WINDOWS".equals(config.getServerSystemKey())))
/*     */     {
/* 237 */       return new NTFTPEntryParser(config);
/*     */     }
/* 239 */     return new CompositeFileEntryParser(
/* 240 */       new FTPFileEntryParser[] {
/* 241 */       new NTFTPEntryParser(config), 
/* 242 */       new UnixFTPEntryParser(config) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public FTPFileEntryParser createOS2FTPEntryParser()
/*     */   {
/* 249 */     return new OS2FTPEntryParser();
/*     */   }
/*     */   
/*     */   public FTPFileEntryParser createOS400FTPEntryParser()
/*     */   {
/* 254 */     return createOS400FTPEntryParser(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private FTPFileEntryParser createOS400FTPEntryParser(FTPClientConfig config)
/*     */   {
/* 266 */     if ((config != null) && 
/* 267 */       ("OS/400".equals(config.getServerSystemKey())))
/*     */     {
/* 269 */       return new OS400FTPEntryParser(config);
/*     */     }
/* 271 */     return new CompositeFileEntryParser(
/* 272 */       new FTPFileEntryParser[] {
/* 273 */       new OS400FTPEntryParser(config), 
/* 274 */       new UnixFTPEntryParser(config) });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public FTPFileEntryParser createMVSEntryParser()
/*     */   {
/* 281 */     return new MVSFTPEntryParser();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\DefaultFTPFileEntryParserFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */