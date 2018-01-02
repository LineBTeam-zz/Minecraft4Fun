/*     */ package org.apache.commons.validator.routines;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class UrlValidator
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 7557161713937335013L;
/*     */   public static final long ALLOW_ALL_SCHEMES = 1L;
/*     */   public static final long ALLOW_2_SLASHES = 2L;
/*     */   public static final long NO_FRAGMENTS = 4L;
/*     */   public static final long ALLOW_LOCAL_URLS = 8L;
/*     */   private static final String AUTHORITY_CHARS_REGEX = "\\p{Alnum}\\-\\.";
/*     */   private static final String URL_REGEX = "^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?";
/* 110 */   private static final Pattern URL_PATTERN = Pattern.compile("^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?");
/*     */   
/*     */ 
/*     */ 
/*     */   private static final int PARSE_URL_SCHEME = 2;
/*     */   
/*     */ 
/*     */   private static final int PARSE_URL_AUTHORITY = 4;
/*     */   
/*     */ 
/*     */   private static final int PARSE_URL_PATH = 5;
/*     */   
/*     */ 
/*     */   private static final int PARSE_URL_QUERY = 7;
/*     */   
/*     */ 
/*     */   private static final int PARSE_URL_FRAGMENT = 9;
/*     */   
/*     */ 
/*     */   private static final String SCHEME_REGEX = "^\\p{Alpha}[\\p{Alnum}\\+\\-\\.]*";
/*     */   
/*     */ 
/* 132 */   private static final Pattern SCHEME_PATTERN = Pattern.compile("^\\p{Alpha}[\\p{Alnum}\\+\\-\\.]*");
/*     */   
/*     */ 
/*     */   private static final String AUTHORITY_REGEX = "^([\\p{Alnum}\\-\\.]*)(:\\d*)?(.*)?";
/*     */   
/* 137 */   private static final Pattern AUTHORITY_PATTERN = Pattern.compile("^([\\p{Alnum}\\-\\.]*)(:\\d*)?(.*)?");
/*     */   
/*     */ 
/*     */   private static final int PARSE_AUTHORITY_HOST_IP = 1;
/*     */   
/*     */ 
/*     */   private static final int PARSE_AUTHORITY_PORT = 2;
/*     */   
/*     */   private static final int PARSE_AUTHORITY_EXTRA = 3;
/*     */   
/*     */   private static final String PATH_REGEX = "^(/[-\\w:@&?=+,.!/~*'%$_;\\(\\)]*)?$";
/*     */   
/* 149 */   private static final Pattern PATH_PATTERN = Pattern.compile("^(/[-\\w:@&?=+,.!/~*'%$_;\\(\\)]*)?$");
/*     */   
/*     */   private static final String QUERY_REGEX = "^(.*)$";
/* 152 */   private static final Pattern QUERY_PATTERN = Pattern.compile("^(.*)$");
/*     */   
/*     */   private static final String LEGAL_ASCII_REGEX = "^\\p{ASCII}+$";
/* 155 */   private static final Pattern ASCII_PATTERN = Pattern.compile("^\\p{ASCII}+$");
/*     */   
/*     */   private static final String PORT_REGEX = "^:(\\d{1,5})$";
/* 158 */   private static final Pattern PORT_PATTERN = Pattern.compile("^:(\\d{1,5})$");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final long options;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private final Set allowedSchemes;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private final RegexValidator authorityValidator;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 180 */   private static final String[] DEFAULT_SCHEMES = { "http", "https", "ftp" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 185 */   private static final UrlValidator DEFAULT_URL_VALIDATOR = new UrlValidator();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static UrlValidator getInstance()
/*     */   {
/* 192 */     return DEFAULT_URL_VALIDATOR;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public UrlValidator()
/*     */   {
/* 199 */     this(null);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UrlValidator(String[] schemes)
/*     */   {
/* 211 */     this(schemes, 0L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UrlValidator(long options)
/*     */   {
/* 221 */     this(null, null, options);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UrlValidator(String[] schemes, long options)
/*     */   {
/* 232 */     this(schemes, null, options);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public UrlValidator(RegexValidator authorityValidator, long options)
/*     */   {
/* 244 */     this(null, authorityValidator, options);
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
/*     */   public UrlValidator(String[] schemes, RegexValidator authorityValidator, long options)
/*     */   {
/* 258 */     this.options = options;
/*     */     
/* 260 */     if (isOn(1L)) {
/* 261 */       this.allowedSchemes = Collections.EMPTY_SET;
/*     */     } else {
/* 263 */       if (schemes == null) {
/* 264 */         schemes = DEFAULT_SCHEMES;
/*     */       }
/* 266 */       this.allowedSchemes = new HashSet();
/* 267 */       this.allowedSchemes.addAll(Arrays.asList(schemes));
/*     */     }
/*     */     
/* 270 */     this.authorityValidator = authorityValidator;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isValid(String value)
/*     */   {
/* 282 */     if (value == null) {
/* 283 */       return false;
/*     */     }
/*     */     
/* 286 */     if (!ASCII_PATTERN.matcher(value).matches()) {
/* 287 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 291 */     Matcher urlMatcher = URL_PATTERN.matcher(value);
/* 292 */     if (!urlMatcher.matches()) {
/* 293 */       return false;
/*     */     }
/*     */     
/* 296 */     String scheme = urlMatcher.group(2);
/* 297 */     if (!isValidScheme(scheme)) {
/* 298 */       return false;
/*     */     }
/*     */     
/* 301 */     String authority = urlMatcher.group(4);
/* 302 */     if ((!"file".equals(scheme)) || (!"".equals(authority)))
/*     */     {
/*     */ 
/*     */ 
/* 306 */       if (!isValidAuthority(authority)) {
/* 307 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 311 */     if (!isValidPath(urlMatcher.group(5))) {
/* 312 */       return false;
/*     */     }
/*     */     
/* 315 */     if (!isValidQuery(urlMatcher.group(7))) {
/* 316 */       return false;
/*     */     }
/*     */     
/* 319 */     if (!isValidFragment(urlMatcher.group(9))) {
/* 320 */       return false;
/*     */     }
/*     */     
/* 323 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isValidScheme(String scheme)
/*     */   {
/* 335 */     if (scheme == null) {
/* 336 */       return false;
/*     */     }
/*     */     
/* 339 */     if (!SCHEME_PATTERN.matcher(scheme).matches()) {
/* 340 */       return false;
/*     */     }
/*     */     
/* 343 */     if (isOff(1L))
/*     */     {
/* 345 */       if (!this.allowedSchemes.contains(scheme)) {
/* 346 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 350 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isValidAuthority(String authority)
/*     */   {
/* 360 */     if (authority == null) {
/* 361 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 365 */     if ((this.authorityValidator != null) && 
/* 366 */       (this.authorityValidator.isValid(authority))) {
/* 367 */       return true;
/*     */     }
/*     */     
/*     */ 
/* 371 */     Matcher authorityMatcher = AUTHORITY_PATTERN.matcher(authority);
/* 372 */     if (!authorityMatcher.matches()) {
/* 373 */       return false;
/*     */     }
/*     */     
/* 376 */     String hostLocation = authorityMatcher.group(1);
/*     */     
/*     */ 
/* 379 */     DomainValidator domainValidator = DomainValidator.getInstance(isOn(8L));
/* 380 */     if (!domainValidator.isValid(hostLocation))
/*     */     {
/* 382 */       InetAddressValidator inetAddressValidator = InetAddressValidator.getInstance();
/*     */       
/* 384 */       if (!inetAddressValidator.isValid(hostLocation))
/*     */       {
/* 386 */         return false;
/*     */       }
/*     */     }
/*     */     
/* 390 */     String port = authorityMatcher.group(2);
/* 391 */     if ((port != null) && 
/* 392 */       (!PORT_PATTERN.matcher(port).matches())) {
/* 393 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 397 */     String extra = authorityMatcher.group(3);
/* 398 */     if ((extra != null) && (extra.trim().length() > 0)) {
/* 399 */       return false;
/*     */     }
/*     */     
/* 402 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isValidPath(String path)
/*     */   {
/* 411 */     if (path == null) {
/* 412 */       return false;
/*     */     }
/*     */     
/* 415 */     if (!PATH_PATTERN.matcher(path).matches()) {
/* 416 */       return false;
/*     */     }
/*     */     
/* 419 */     int slash2Count = countToken("//", path);
/* 420 */     if ((isOff(2L)) && (slash2Count > 0)) {
/* 421 */       return false;
/*     */     }
/*     */     
/* 424 */     int slashCount = countToken("/", path);
/* 425 */     int dot2Count = countToken("..", path);
/* 426 */     if ((dot2Count > 0) && 
/* 427 */       (slashCount - slash2Count - 1 <= dot2Count)) {
/* 428 */       return false;
/*     */     }
/*     */     
/*     */ 
/* 432 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isValidQuery(String query)
/*     */   {
/* 441 */     if (query == null) {
/* 442 */       return true;
/*     */     }
/*     */     
/* 445 */     return QUERY_PATTERN.matcher(query).matches();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected boolean isValidFragment(String fragment)
/*     */   {
/* 454 */     if (fragment == null) {
/* 455 */       return true;
/*     */     }
/*     */     
/* 458 */     return isOff(4L);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int countToken(String token, String target)
/*     */   {
/* 468 */     int tokenIndex = 0;
/* 469 */     int count = 0;
/* 470 */     while (tokenIndex != -1) {
/* 471 */       tokenIndex = target.indexOf(token, tokenIndex);
/* 472 */       if (tokenIndex > -1) {
/* 473 */         tokenIndex++;
/* 474 */         count++;
/*     */       }
/*     */     }
/* 477 */     return count;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isOn(long flag)
/*     */   {
/* 489 */     return (this.options & flag) > 0L;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private boolean isOff(long flag)
/*     */   {
/* 501 */     return (this.options & flag) == 0L;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\org\apache\commons\validator\routines\UrlValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */