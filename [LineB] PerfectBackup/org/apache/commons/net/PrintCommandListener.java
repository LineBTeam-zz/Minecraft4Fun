/*     */ package org.apache.commons.net;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PrintCommandListener
/*     */   implements ProtocolCommandListener
/*     */ {
/*     */   private final PrintWriter __writer;
/*     */   private final boolean __nologin;
/*     */   private final char __eolMarker;
/*     */   private final boolean __directionMarker;
/*     */   
/*     */   public PrintCommandListener(PrintStream stream)
/*     */   {
/*  48 */     this(new PrintWriter(stream));
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
/*     */   public PrintCommandListener(PrintStream stream, boolean suppressLogin)
/*     */   {
/*  61 */     this(new PrintWriter(stream), suppressLogin);
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
/*     */   public PrintCommandListener(PrintStream stream, boolean suppressLogin, char eolMarker)
/*     */   {
/*  75 */     this(new PrintWriter(stream), suppressLogin, eolMarker);
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
/*     */   public PrintCommandListener(PrintStream stream, boolean suppressLogin, char eolMarker, boolean showDirection)
/*     */   {
/*  90 */     this(new PrintWriter(stream), suppressLogin, eolMarker, showDirection);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public PrintCommandListener(PrintWriter writer)
/*     */   {
/* 100 */     this(writer, false);
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
/*     */   public PrintCommandListener(PrintWriter writer, boolean suppressLogin)
/*     */   {
/* 113 */     this(writer, suppressLogin, '\000');
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
/*     */   public PrintCommandListener(PrintWriter writer, boolean suppressLogin, char eolMarker)
/*     */   {
/* 128 */     this(writer, suppressLogin, eolMarker, false);
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
/*     */   public PrintCommandListener(PrintWriter writer, boolean suppressLogin, char eolMarker, boolean showDirection)
/*     */   {
/* 144 */     this.__writer = writer;
/* 145 */     this.__nologin = suppressLogin;
/* 146 */     this.__eolMarker = eolMarker;
/* 147 */     this.__directionMarker = showDirection;
/*     */   }
/*     */   
/*     */ 
/*     */   public void protocolCommandSent(ProtocolCommandEvent event)
/*     */   {
/* 153 */     if (this.__directionMarker) {
/* 154 */       this.__writer.print("> ");
/*     */     }
/* 156 */     if (this.__nologin) {
/* 157 */       String cmd = event.getCommand();
/* 158 */       if (("PASS".equalsIgnoreCase(cmd)) || ("USER".equalsIgnoreCase(cmd))) {
/* 159 */         this.__writer.print(cmd);
/* 160 */         this.__writer.println(" *******");
/*     */       } else {
/* 162 */         String IMAP_LOGIN = "LOGIN";
/* 163 */         if ("LOGIN".equalsIgnoreCase(cmd)) {
/* 164 */           String msg = event.getMessage();
/* 165 */           msg = msg.substring(0, msg.indexOf("LOGIN") + "LOGIN".length());
/* 166 */           this.__writer.print(msg);
/* 167 */           this.__writer.println(" *******");
/*     */         } else {
/* 169 */           this.__writer.print(getPrintableString(event.getMessage()));
/*     */         }
/*     */       }
/*     */     } else {
/* 173 */       this.__writer.print(getPrintableString(event.getMessage()));
/*     */     }
/* 175 */     this.__writer.flush();
/*     */   }
/*     */   
/*     */   private String getPrintableString(String msg) {
/* 179 */     if (this.__eolMarker == 0) {
/* 180 */       return msg;
/*     */     }
/* 182 */     int pos = msg.indexOf("\r\n");
/* 183 */     if (pos > 0) {
/* 184 */       StringBuilder sb = new StringBuilder();
/* 185 */       sb.append(msg.substring(0, pos));
/* 186 */       sb.append(this.__eolMarker);
/* 187 */       sb.append(msg.substring(pos));
/* 188 */       return sb.toString();
/*     */     }
/* 190 */     return msg;
/*     */   }
/*     */   
/*     */   public void protocolReplyReceived(ProtocolCommandEvent event)
/*     */   {
/* 195 */     if (this.__directionMarker) {
/* 196 */       this.__writer.print("< ");
/*     */     }
/* 198 */     this.__writer.print(event.getMessage());
/* 199 */     this.__writer.flush();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\PrintCommandListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */