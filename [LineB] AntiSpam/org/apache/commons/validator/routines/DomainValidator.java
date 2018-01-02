/*     */ package org.apache.commons.validator.routines;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
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
/*     */ public class DomainValidator
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -4407125112880174009L;
/*     */   private static final String DOMAIN_LABEL_REGEX = "\\p{Alnum}(?>[\\p{Alnum}-]*\\p{Alnum})*";
/*     */   private static final String TOP_LABEL_REGEX = "\\p{Alpha}{2,}";
/*     */   private static final String DOMAIN_NAME_REGEX = "^(?:\\p{Alnum}(?>[\\p{Alnum}-]*\\p{Alnum})*\\.)+(\\p{Alpha}{2,})$";
/*     */   private final boolean allowLocal;
/*  79 */   private static final DomainValidator DOMAIN_VALIDATOR = new DomainValidator(false);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  85 */   private static final DomainValidator DOMAIN_VALIDATOR_WITH_LOCAL = new DomainValidator(true);
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  90 */   private final RegexValidator domainRegex = new RegexValidator("^(?:\\p{Alnum}(?>[\\p{Alnum}-]*\\p{Alnum})*\\.)+(\\p{Alpha}{2,})$");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  95 */   private final RegexValidator hostnameRegex = new RegexValidator("\\p{Alnum}(?>[\\p{Alnum}-]*\\p{Alnum})*");
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DomainValidator getInstance()
/*     */   {
/* 104 */     return DOMAIN_VALIDATOR;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static DomainValidator getInstance(boolean allowLocal)
/*     */   {
/* 114 */     if (allowLocal) {
/* 115 */       return DOMAIN_VALIDATOR_WITH_LOCAL;
/*     */     }
/* 117 */     return DOMAIN_VALIDATOR;
/*     */   }
/*     */   
/*     */   private DomainValidator(boolean allowLocal)
/*     */   {
/* 122 */     this.allowLocal = allowLocal;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isValid(String domain)
/*     */   {
/* 133 */     String[] groups = this.domainRegex.match(domain);
/* 134 */     if ((groups != null) && (groups.length > 0))
/* 135 */       return isValidTld(groups[0]);
/* 136 */     if ((this.allowLocal) && 
/* 137 */       (this.hostnameRegex.isValid(domain))) {
/* 138 */       return true;
/*     */     }
/*     */     
/* 141 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isValidTld(String tld)
/*     */   {
/* 152 */     if ((this.allowLocal) && (isValidLocalTld(tld))) {
/* 153 */       return true;
/*     */     }
/* 155 */     return (isValidInfrastructureTld(tld)) || (isValidGenericTld(tld)) || (isValidCountryCodeTld(tld));
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
/*     */   public boolean isValidInfrastructureTld(String iTld)
/*     */   {
/* 168 */     return INFRASTRUCTURE_TLD_LIST.contains(chompLeadingDot(iTld.toLowerCase()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isValidGenericTld(String gTld)
/*     */   {
/* 179 */     return GENERIC_TLD_LIST.contains(chompLeadingDot(gTld.toLowerCase()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isValidCountryCodeTld(String ccTld)
/*     */   {
/* 190 */     return COUNTRY_CODE_TLD_LIST.contains(chompLeadingDot(ccTld.toLowerCase()));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isValidLocalTld(String iTld)
/*     */   {
/* 201 */     return LOCAL_TLD_LIST.contains(chompLeadingDot(iTld.toLowerCase()));
/*     */   }
/*     */   
/*     */   private String chompLeadingDot(String str) {
/* 205 */     if (str.startsWith(".")) {
/* 206 */       return str.substring(1);
/*     */     }
/* 208 */     return str;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 217 */   private static final String[] INFRASTRUCTURE_TLDS = { "arpa", "root" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/* 222 */   private static final String[] GENERIC_TLDS = { "aero", "asia", "biz", "cat", "com", "coop", "info", "jobs", "mobi", "museum", "name", "net", "org", "pro", "tel", "travel", "gov", "edu", "mil", "int" };
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
/* 245 */   private static final String[] COUNTRY_CODE_TLDS = { "ac", "ad", "ae", "af", "ag", "ai", "al", "am", "an", "ao", "aq", "ar", "as", "at", "au", "aw", "ax", "az", "ba", "bb", "bd", "be", "bf", "bg", "bh", "bi", "bj", "bm", "bn", "bo", "br", "bs", "bt", "bv", "bw", "by", "bz", "ca", "cc", "cd", "cf", "cg", "ch", "ci", "ck", "cl", "cm", "cn", "co", "cr", "cu", "cv", "cx", "cy", "cz", "de", "dj", "dk", "dm", "do", "dz", "ec", "ee", "eg", "er", "es", "et", "eu", "fi", "fj", "fk", "fm", "fo", "fr", "ga", "gb", "gd", "ge", "gf", "gg", "gh", "gi", "gl", "gm", "gn", "gp", "gq", "gr", "gs", "gt", "gu", "gw", "gy", "hk", "hm", "hn", "hr", "ht", "hu", "id", "ie", "il", "im", "in", "io", "iq", "ir", "is", "it", "je", "jm", "jo", "jp", "ke", "kg", "kh", "ki", "km", "kn", "kp", "kr", "kw", "ky", "kz", "la", "lb", "lc", "li", "lk", "lr", "ls", "lt", "lu", "lv", "ly", "ma", "mc", "md", "me", "mg", "mh", "mk", "ml", "mm", "mn", "mo", "mp", "mq", "mr", "ms", "mt", "mu", "mv", "mw", "mx", "my", "mz", "na", "nc", "ne", "nf", "ng", "ni", "nl", "no", "np", "nr", "nu", "nz", "om", "pa", "pe", "pf", "pg", "ph", "pk", "pl", "pm", "pn", "pr", "ps", "pt", "pw", "py", "qa", "re", "ro", "rs", "ru", "rw", "sa", "sb", "sc", "sd", "se", "sg", "sh", "si", "sj", "sk", "sl", "sm", "sn", "so", "sr", "st", "su", "sv", "sy", "sz", "tc", "td", "tf", "tg", "th", "tj", "tk", "tl", "tm", "tn", "to", "tp", "tr", "tt", "tv", "tw", "tz", "ua", "ug", "uk", "um", "us", "uy", "uz", "va", "vc", "ve", "vg", "vi", "vn", "vu", "wf", "ws", "ye", "yt", "yu", "za", "zm", "zw" };
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
/* 497 */   private static final String[] LOCAL_TLDS = { "localhost", "localdomain" };
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 503 */   private static final List INFRASTRUCTURE_TLD_LIST = Arrays.asList(INFRASTRUCTURE_TLDS);
/*     */   
/* 505 */   private static final List GENERIC_TLD_LIST = Arrays.asList(GENERIC_TLDS);
/*     */   
/* 507 */   private static final List COUNTRY_CODE_TLD_LIST = Arrays.asList(COUNTRY_CODE_TLDS);
/*     */   
/* 509 */   private static final List LOCAL_TLD_LIST = Arrays.asList(LOCAL_TLDS);
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\org\apache\commons\validator\routines\DomainValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */