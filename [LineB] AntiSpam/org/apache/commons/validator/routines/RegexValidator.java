/*     */ package org.apache.commons.validator.routines;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class RegexValidator
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = -8832409930574867162L;
/*     */   private final Pattern[] patterns;
/*     */   
/*     */   public RegexValidator(String regex)
/*     */   {
/*  71 */     this(regex, true);
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
/*     */   public RegexValidator(String regex, boolean caseSensitive)
/*     */   {
/*  84 */     this(new String[] { regex }, caseSensitive);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RegexValidator(String[] regexs)
/*     */   {
/*  95 */     this(regexs, true);
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
/*     */   public RegexValidator(String[] regexs, boolean caseSensitive)
/*     */   {
/* 108 */     if ((regexs == null) || (regexs.length == 0)) {
/* 109 */       throw new IllegalArgumentException("Regular expressions are missing");
/*     */     }
/* 111 */     this.patterns = new Pattern[regexs.length];
/* 112 */     int flags = caseSensitive ? 0 : 2;
/* 113 */     for (int i = 0; i < regexs.length; i++) {
/* 114 */       if ((regexs[i] == null) || (regexs[i].length() == 0)) {
/* 115 */         throw new IllegalArgumentException("Regular expression[" + i + "] is missing");
/*     */       }
/* 117 */       this.patterns[i] = Pattern.compile(regexs[i], flags);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isValid(String value)
/*     */   {
/* 129 */     if (value == null) {
/* 130 */       return false;
/*     */     }
/* 132 */     for (int i = 0; i < this.patterns.length; i++) {
/* 133 */       if (this.patterns[i].matcher(value).matches()) {
/* 134 */         return true;
/*     */       }
/*     */     }
/* 137 */     return false;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String[] match(String value)
/*     */   {
/* 149 */     if (value == null) {
/* 150 */       return null;
/*     */     }
/* 152 */     for (int i = 0; i < this.patterns.length; i++) {
/* 153 */       Matcher matcher = this.patterns[i].matcher(value);
/* 154 */       if (matcher.matches()) {
/* 155 */         int count = matcher.groupCount();
/* 156 */         String[] groups = new String[count];
/* 157 */         for (int j = 0; j < count; j++) {
/* 158 */           groups[j] = matcher.group(j + 1);
/*     */         }
/* 160 */         return groups;
/*     */       }
/*     */     }
/* 163 */     return null;
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
/*     */   public String validate(String value)
/*     */   {
/* 176 */     if (value == null) {
/* 177 */       return null;
/*     */     }
/* 179 */     for (int i = 0; i < this.patterns.length; i++) {
/* 180 */       Matcher matcher = this.patterns[i].matcher(value);
/* 181 */       if (matcher.matches()) {
/* 182 */         int count = matcher.groupCount();
/* 183 */         if (count == 1) {
/* 184 */           return matcher.group(1);
/*     */         }
/* 186 */         StringBuffer buffer = new StringBuffer();
/* 187 */         for (int j = 0; j < count; j++) {
/* 188 */           String component = matcher.group(j + 1);
/* 189 */           if (component != null) {
/* 190 */             buffer.append(component);
/*     */           }
/*     */         }
/* 193 */         return buffer.toString();
/*     */       }
/*     */     }
/* 196 */     return null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 204 */     StringBuffer buffer = new StringBuffer();
/* 205 */     buffer.append("RegexValidator{");
/* 206 */     for (int i = 0; i < this.patterns.length; i++) {
/* 207 */       if (i > 0) {
/* 208 */         buffer.append(",");
/*     */       }
/* 210 */       buffer.append(this.patterns[i].pattern());
/*     */     }
/* 212 */     buffer.append("}");
/* 213 */     return buffer.toString();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\org\apache\commons\validator\routines\RegexValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */