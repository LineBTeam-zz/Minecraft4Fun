/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import java.util.regex.MatchResult;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.regex.PatternSyntaxException;
/*     */ import org.apache.commons.net.ftp.FTPClientConfig;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class VMSVersioningFTPEntryParser
/*     */   extends VMSFTPEntryParser
/*     */ {
/*     */   private final Pattern _preparse_pattern_;
/*     */   private static final String PRE_PARSE_REGEX = "(.*);([0-9]+)\\s*.*";
/*     */   
/*     */   public VMSVersioningFTPEntryParser()
/*     */   {
/*  65 */     this(null);
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
/*     */   public VMSVersioningFTPEntryParser(FTPClientConfig config)
/*     */   {
/*  83 */     configure(config);
/*     */     
/*     */     try
/*     */     {
/*  87 */       this._preparse_pattern_ = Pattern.compile("(.*);([0-9]+)\\s*.*");
/*     */     }
/*     */     catch (PatternSyntaxException pse)
/*     */     {
/*  91 */       throw new IllegalArgumentException(
/*  92 */         "Unparseable regex supplied:  (.*);([0-9]+)\\s*.*");
/*     */     }
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
/*     */   public List<String> preParse(List<String> original)
/*     */   {
/* 108 */     HashMap<String, Integer> existingEntries = new HashMap();
/* 109 */     ListIterator<String> iter = original.listIterator();
/* 110 */     while (iter.hasNext()) {
/* 111 */       String entry = ((String)iter.next()).trim();
/* 112 */       MatchResult result = null;
/* 113 */       Matcher _preparse_matcher_ = this._preparse_pattern_.matcher(entry);
/* 114 */       if (_preparse_matcher_.matches()) {
/* 115 */         result = _preparse_matcher_.toMatchResult();
/* 116 */         String name = result.group(1);
/* 117 */         String version = result.group(2);
/* 118 */         Integer nv = Integer.valueOf(version);
/* 119 */         Integer existing = (Integer)existingEntries.get(name);
/* 120 */         if ((existing != null) && 
/* 121 */           (nv.intValue() < existing.intValue())) {
/* 122 */           iter.remove();
/*     */         }
/*     */         else
/*     */         {
/* 126 */           existingEntries.put(name, nv);
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 134 */     while (iter.hasPrevious()) {
/* 135 */       String entry = ((String)iter.previous()).trim();
/* 136 */       MatchResult result = null;
/* 137 */       Matcher _preparse_matcher_ = this._preparse_pattern_.matcher(entry);
/* 138 */       if (_preparse_matcher_.matches()) {
/* 139 */         result = _preparse_matcher_.toMatchResult();
/* 140 */         String name = result.group(1);
/* 141 */         String version = result.group(2);
/* 142 */         Integer nv = Integer.valueOf(version);
/* 143 */         Integer existing = (Integer)existingEntries.get(name);
/* 144 */         if ((existing != null) && 
/* 145 */           (nv.intValue() < existing.intValue())) {
/* 146 */           iter.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 152 */     return original;
/*     */   }
/*     */   
/*     */ 
/*     */   protected boolean isVersioning()
/*     */   {
/* 158 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\VMSVersioningFTPEntryParser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */