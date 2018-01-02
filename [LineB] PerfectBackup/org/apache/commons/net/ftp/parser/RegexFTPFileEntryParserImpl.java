/*     */ package org.apache.commons.net.ftp.parser;
/*     */ 
/*     */ import java.util.regex.MatchResult;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import java.util.regex.PatternSyntaxException;
/*     */ import org.apache.commons.net.ftp.FTPFileEntryParserImpl;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class RegexFTPFileEntryParserImpl
/*     */   extends FTPFileEntryParserImpl
/*     */ {
/*  43 */   private Pattern pattern = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  48 */   private MatchResult result = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*  54 */   protected Matcher _matcher_ = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public RegexFTPFileEntryParserImpl(String regex)
/*     */   {
/*  72 */     setRegex(regex);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean matches(String s)
/*     */   {
/*  84 */     this.result = null;
/*  85 */     this._matcher_ = this.pattern.matcher(s);
/*  86 */     if (this._matcher_.matches()) {
/*  87 */       this.result = this._matcher_.toMatchResult();
/*     */     }
/*  89 */     return this.result != null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getGroupCnt()
/*     */   {
/*  99 */     if (this.result == null) {
/* 100 */       return 0;
/*     */     }
/* 102 */     return this.result.groupCount();
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
/*     */   public String group(int matchnum)
/*     */   {
/* 116 */     if (this.result == null) {
/* 117 */       return null;
/*     */     }
/* 119 */     return this.result.group(matchnum);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getGroupsAsString()
/*     */   {
/* 130 */     StringBuilder b = new StringBuilder();
/* 131 */     for (int i = 1; i <= this.result.groupCount(); i++) {
/* 132 */       b.append(i).append(") ").append(this.result.group(i)).append(
/* 133 */         System.getProperty("line.separator"));
/*     */     }
/* 135 */     return b.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean setRegex(String regex)
/*     */   {
/*     */     try
/*     */     {
/* 148 */       this.pattern = Pattern.compile(regex);
/* 149 */       return true;
/*     */     } catch (PatternSyntaxException pse) {
/* 151 */       throw new IllegalArgumentException("Unparseable regex supplied: " + 
/* 152 */         regex);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\RegexFTPFileEntryParserImpl.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */