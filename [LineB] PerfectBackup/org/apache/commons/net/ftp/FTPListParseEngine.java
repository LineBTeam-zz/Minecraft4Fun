/*     */ package org.apache.commons.net.ftp;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import org.apache.commons.net.util.Charsets;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FTPListParseEngine
/*     */ {
/*  79 */   private List<String> entries = new LinkedList();
/*  80 */   private ListIterator<String> _internalIterator = this.entries.listIterator();
/*     */   private final FTPFileEntryParser parser;
/*     */   
/*     */   public FTPListParseEngine(FTPFileEntryParser parser)
/*     */   {
/*  85 */     this.parser = parser;
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
/*     */   public void readServerList(InputStream stream, String encoding)
/*     */     throws IOException
/*     */   {
/* 103 */     this.entries = new LinkedList();
/* 104 */     readStream(stream, encoding);
/* 105 */     this.parser.preParse(this.entries);
/* 106 */     resetIterator();
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
/*     */   private void readStream(InputStream stream, String encoding)
/*     */     throws IOException
/*     */   {
/* 125 */     BufferedReader reader = new BufferedReader(
/* 126 */       new InputStreamReader(stream, Charsets.toCharset(encoding)));
/*     */     
/* 128 */     String line = this.parser.readNextEntry(reader);
/*     */     
/* 130 */     while (line != null)
/*     */     {
/* 132 */       this.entries.add(line);
/* 133 */       line = this.parser.readNextEntry(reader);
/*     */     }
/* 135 */     reader.close();
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
/*     */   public FTPFile[] getNext(int quantityRequested)
/*     */   {
/* 162 */     List<FTPFile> tmpResults = new LinkedList();
/* 163 */     int count = quantityRequested;
/* 164 */     while ((count > 0) && (this._internalIterator.hasNext())) {
/* 165 */       String entry = (String)this._internalIterator.next();
/* 166 */       FTPFile temp = this.parser.parseFTPEntry(entry);
/* 167 */       tmpResults.add(temp);
/* 168 */       count--;
/*     */     }
/* 170 */     return (FTPFile[])tmpResults.toArray(new FTPFile[tmpResults.size()]);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public FTPFile[] getPrevious(int quantityRequested)
/*     */   {
/* 201 */     List<FTPFile> tmpResults = new LinkedList();
/* 202 */     int count = quantityRequested;
/* 203 */     while ((count > 0) && (this._internalIterator.hasPrevious())) {
/* 204 */       String entry = (String)this._internalIterator.previous();
/* 205 */       FTPFile temp = this.parser.parseFTPEntry(entry);
/* 206 */       tmpResults.add(0, temp);
/* 207 */       count--;
/*     */     }
/* 209 */     return (FTPFile[])tmpResults.toArray(new FTPFile[tmpResults.size()]);
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
/*     */   public FTPFile[] getFiles()
/*     */     throws IOException
/*     */   {
/* 224 */     return getFiles(FTPFileFilters.NON_NULL);
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
/*     */   public FTPFile[] getFiles(FTPFileFilter filter)
/*     */     throws IOException
/*     */   {
/* 248 */     List<FTPFile> tmpResults = new ArrayList();
/* 249 */     Iterator<String> iter = this.entries.iterator();
/* 250 */     while (iter.hasNext()) {
/* 251 */       String entry = (String)iter.next();
/* 252 */       FTPFile temp = this.parser.parseFTPEntry(entry);
/* 253 */       if (filter.accept(temp)) {
/* 254 */         tmpResults.add(temp);
/*     */       }
/*     */     }
/* 257 */     return (FTPFile[])tmpResults.toArray(new FTPFile[tmpResults.size()]);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasNext()
/*     */   {
/* 269 */     return this._internalIterator.hasNext();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean hasPrevious()
/*     */   {
/* 280 */     return this._internalIterator.hasPrevious();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void resetIterator()
/*     */   {
/* 287 */     this._internalIterator = this.entries.listIterator();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   @Deprecated
/*     */   public void readServerList(InputStream stream)
/*     */     throws IOException
/*     */   {
/* 300 */     readServerList(stream, null);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\FTPListParseEngine.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */