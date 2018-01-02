/*      */ package org.apache.commons.net.ftp;
/*      */ 
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.IOException;
/*      */ import java.net.Inet4Address;
/*      */ import java.net.Inet6Address;
/*      */ import java.net.InetAddress;
/*      */ import java.net.SocketException;
/*      */ import java.util.ArrayList;
/*      */ import org.apache.commons.net.MalformedServerReplyException;
/*      */ import org.apache.commons.net.ProtocolCommandSupport;
/*      */ import org.apache.commons.net.SocketClient;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FTP
/*      */   extends SocketClient
/*      */ {
/*      */   public static final int DEFAULT_DATA_PORT = 20;
/*      */   public static final int DEFAULT_PORT = 21;
/*      */   public static final int ASCII_FILE_TYPE = 0;
/*      */   public static final int EBCDIC_FILE_TYPE = 1;
/*      */   public static final int BINARY_FILE_TYPE = 2;
/*      */   public static final int LOCAL_FILE_TYPE = 3;
/*      */   public static final int NON_PRINT_TEXT_FORMAT = 4;
/*      */   public static final int TELNET_TEXT_FORMAT = 5;
/*      */   public static final int CARRIAGE_CONTROL_TEXT_FORMAT = 6;
/*      */   public static final int FILE_STRUCTURE = 7;
/*      */   public static final int RECORD_STRUCTURE = 8;
/*      */   public static final int PAGE_STRUCTURE = 9;
/*      */   public static final int STREAM_TRANSFER_MODE = 10;
/*      */   public static final int BLOCK_TRANSFER_MODE = 11;
/*      */   public static final int COMPRESSED_TRANSFER_MODE = 12;
/*      */   public static final String DEFAULT_CONTROL_ENCODING = "ISO-8859-1";
/*      */   public static final int REPLY_CODE_LEN = 3;
/*      */   private static final String __modes = "AEILNTCFRPSBC";
/*      */   protected int _replyCode;
/*      */   protected ArrayList<String> _replyLines;
/*      */   protected boolean _newReplyString;
/*      */   protected String _replyString;
/*      */   protected String _controlEncoding;
/*      */   protected ProtocolCommandSupport _commandSupport_;
/*  238 */   protected boolean strictMultilineParsing = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected BufferedReader _controlInput_;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected BufferedWriter _controlOutput_;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public FTP()
/*      */   {
/*  266 */     setDefaultPort(21);
/*  267 */     this._replyLines = new ArrayList();
/*  268 */     this._newReplyString = false;
/*  269 */     this._replyString = null;
/*  270 */     this._controlEncoding = "ISO-8859-1";
/*  271 */     this._commandSupport_ = new ProtocolCommandSupport(this);
/*      */   }
/*      */   
/*      */   private boolean __strictCheck(String line, String code)
/*      */   {
/*  276 */     return (!line.startsWith(code)) || (line.charAt(3) != ' ');
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean __lenientCheck(String line)
/*      */   {
/*  285 */     return (line.length() <= 3) || (line.charAt(3) == '-') || 
/*  286 */       (!Character.isDigit(line.charAt(0)));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void __getReply()
/*      */     throws IOException
/*      */   {
/*  294 */     __getReply(true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void __getReplyNoReport()
/*      */     throws IOException
/*      */   {
/*  304 */     __getReply(false);
/*      */   }
/*      */   
/*      */ 
/*      */   private void __getReply(boolean reportReply)
/*      */     throws IOException
/*      */   {
/*  311 */     this._newReplyString = true;
/*  312 */     this._replyLines.clear();
/*      */     
/*  314 */     String line = this._controlInput_.readLine();
/*      */     
/*  316 */     if (line == null) {
/*  317 */       throw new FTPConnectionClosedException(
/*  318 */         "Connection closed without indication.");
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*  323 */     int length = line.length();
/*  324 */     if (length < 3) {
/*  325 */       throw new MalformedServerReplyException(
/*  326 */         "Truncated server reply: " + line);
/*      */     }
/*      */     
/*  329 */     String code = null;
/*      */     try
/*      */     {
/*  332 */       code = line.substring(0, 3);
/*  333 */       this._replyCode = Integer.parseInt(code);
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/*  337 */       throw new MalformedServerReplyException(
/*  338 */         "Could not parse response code.\nServer Reply: " + line);
/*      */     }
/*      */     
/*  341 */     this._replyLines.add(line);
/*      */     
/*      */ 
/*  344 */     if ((length > 3) && (line.charAt(3) == '-'))
/*      */     {
/*      */       do
/*      */       {
/*  348 */         line = this._controlInput_.readLine();
/*      */         
/*  350 */         if (line == null) {
/*  351 */           throw new FTPConnectionClosedException(
/*  352 */             "Connection closed without indication.");
/*      */         }
/*      */         
/*  355 */         this._replyLines.add(line);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/*  361 */       while (isStrictMultilineParsing() ? __strictCheck(line, code) : __lenientCheck(line));
/*      */     }
/*      */     
/*  364 */     fireReplyReceived(this._replyCode, getReplyString());
/*      */     
/*  366 */     if (this._replyCode == 421) {
/*  367 */       throw new FTPConnectionClosedException("FTP response 421 received.  Server closed connection.");
/*      */     }
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   protected void _connectAction_()
/*      */     throws IOException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: invokespecial 202	org/apache/commons/net/SocketClient:_connectAction_	()V
/*      */     //   4: aload_0
/*      */     //   5: new 204	org/apache/commons/net/io/CRLFLineReader
/*      */     //   8: dup
/*      */     //   9: new 206	java/io/InputStreamReader
/*      */     //   12: dup
/*      */     //   13: aload_0
/*      */     //   14: getfield 208	org/apache/commons/net/ftp/FTP:_input_	Ljava/io/InputStream;
/*      */     //   17: aload_0
/*      */     //   18: invokevirtual 212	org/apache/commons/net/ftp/FTP:getControlEncoding	()Ljava/lang/String;
/*      */     //   21: invokespecial 215	java/io/InputStreamReader:<init>	(Ljava/io/InputStream;Ljava/lang/String;)V
/*      */     //   24: invokespecial 218	org/apache/commons/net/io/CRLFLineReader:<init>	(Ljava/io/Reader;)V
/*      */     //   27: putfield 131	org/apache/commons/net/ftp/FTP:_controlInput_	Ljava/io/BufferedReader;
/*      */     //   30: aload_0
/*      */     //   31: new 221	java/io/BufferedWriter
/*      */     //   34: dup
/*      */     //   35: new 223	java/io/OutputStreamWriter
/*      */     //   38: dup
/*      */     //   39: aload_0
/*      */     //   40: getfield 225	org/apache/commons/net/ftp/FTP:_output_	Ljava/io/OutputStream;
/*      */     //   43: aload_0
/*      */     //   44: invokevirtual 212	org/apache/commons/net/ftp/FTP:getControlEncoding	()Ljava/lang/String;
/*      */     //   47: invokespecial 229	java/io/OutputStreamWriter:<init>	(Ljava/io/OutputStream;Ljava/lang/String;)V
/*      */     //   50: invokespecial 232	java/io/BufferedWriter:<init>	(Ljava/io/Writer;)V
/*      */     //   53: putfield 235	org/apache/commons/net/ftp/FTP:_controlOutput_	Ljava/io/BufferedWriter;
/*      */     //   56: aload_0
/*      */     //   57: getfield 237	org/apache/commons/net/ftp/FTP:connectTimeout	I
/*      */     //   60: ifle +87 -> 147
/*      */     //   63: aload_0
/*      */     //   64: getfield 240	org/apache/commons/net/ftp/FTP:_socket_	Ljava/net/Socket;
/*      */     //   67: invokevirtual 244	java/net/Socket:getSoTimeout	()I
/*      */     //   70: istore_1
/*      */     //   71: aload_0
/*      */     //   72: getfield 240	org/apache/commons/net/ftp/FTP:_socket_	Ljava/net/Socket;
/*      */     //   75: aload_0
/*      */     //   76: getfield 237	org/apache/commons/net/ftp/FTP:connectTimeout	I
/*      */     //   79: invokevirtual 249	java/net/Socket:setSoTimeout	(I)V
/*      */     //   82: aload_0
/*      */     //   83: invokespecial 252	org/apache/commons/net/ftp/FTP:__getReply	()V
/*      */     //   86: aload_0
/*      */     //   87: getfield 171	org/apache/commons/net/ftp/FTP:_replyCode	I
/*      */     //   90: invokestatic 254	org/apache/commons/net/ftp/FTPReply:isPositivePreliminary	(I)Z
/*      */     //   93: ifeq +43 -> 136
/*      */     //   96: aload_0
/*      */     //   97: invokespecial 252	org/apache/commons/net/ftp/FTP:__getReply	()V
/*      */     //   100: goto +36 -> 136
/*      */     //   103: astore_2
/*      */     //   104: new 122	java/io/IOException
/*      */     //   107: dup
/*      */     //   108: ldc_w 260
/*      */     //   111: invokespecial 262	java/io/IOException:<init>	(Ljava/lang/String;)V
/*      */     //   114: astore_3
/*      */     //   115: aload_3
/*      */     //   116: aload_2
/*      */     //   117: invokevirtual 263	java/io/IOException:initCause	(Ljava/lang/Throwable;)Ljava/lang/Throwable;
/*      */     //   120: pop
/*      */     //   121: aload_3
/*      */     //   122: athrow
/*      */     //   123: astore 4
/*      */     //   125: aload_0
/*      */     //   126: getfield 240	org/apache/commons/net/ftp/FTP:_socket_	Ljava/net/Socket;
/*      */     //   129: iload_1
/*      */     //   130: invokevirtual 249	java/net/Socket:setSoTimeout	(I)V
/*      */     //   133: aload 4
/*      */     //   135: athrow
/*      */     //   136: aload_0
/*      */     //   137: getfield 240	org/apache/commons/net/ftp/FTP:_socket_	Ljava/net/Socket;
/*      */     //   140: iload_1
/*      */     //   141: invokevirtual 249	java/net/Socket:setSoTimeout	(I)V
/*      */     //   144: goto +21 -> 165
/*      */     //   147: aload_0
/*      */     //   148: invokespecial 252	org/apache/commons/net/ftp/FTP:__getReply	()V
/*      */     //   151: aload_0
/*      */     //   152: getfield 171	org/apache/commons/net/ftp/FTP:_replyCode	I
/*      */     //   155: invokestatic 254	org/apache/commons/net/ftp/FTPReply:isPositivePreliminary	(I)Z
/*      */     //   158: ifeq +7 -> 165
/*      */     //   161: aload_0
/*      */     //   162: invokespecial 252	org/apache/commons/net/ftp/FTP:__getReply	()V
/*      */     //   165: return
/*      */     // Line number table:
/*      */     //   Java source line #378	-> byte code offset #0
/*      */     //   Java source line #379	-> byte code offset #4
/*      */     //   Java source line #380	-> byte code offset #5
/*      */     //   Java source line #379	-> byte code offset #27
/*      */     //   Java source line #381	-> byte code offset #30
/*      */     //   Java source line #382	-> byte code offset #31
/*      */     //   Java source line #381	-> byte code offset #53
/*      */     //   Java source line #383	-> byte code offset #56
/*      */     //   Java source line #384	-> byte code offset #63
/*      */     //   Java source line #385	-> byte code offset #71
/*      */     //   Java source line #387	-> byte code offset #82
/*      */     //   Java source line #389	-> byte code offset #86
/*      */     //   Java source line #390	-> byte code offset #96
/*      */     //   Java source line #392	-> byte code offset #100
/*      */     //   Java source line #393	-> byte code offset #104
/*      */     //   Java source line #394	-> byte code offset #115
/*      */     //   Java source line #395	-> byte code offset #121
/*      */     //   Java source line #396	-> byte code offset #123
/*      */     //   Java source line #397	-> byte code offset #125
/*      */     //   Java source line #398	-> byte code offset #133
/*      */     //   Java source line #397	-> byte code offset #136
/*      */     //   Java source line #399	-> byte code offset #144
/*      */     //   Java source line #400	-> byte code offset #147
/*      */     //   Java source line #402	-> byte code offset #151
/*      */     //   Java source line #403	-> byte code offset #161
/*      */     //   Java source line #406	-> byte code offset #165
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	166	0	this	FTP
/*      */     //   70	71	1	original	int
/*      */     //   103	14	2	e	java.net.SocketTimeoutException
/*      */     //   114	8	3	ioe	IOException
/*      */     //   123	11	4	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   82	100	103	java/net/SocketTimeoutException
/*      */     //   82	123	123	finally
/*      */   }
/*      */   
/*      */   public void setControlEncoding(String encoding)
/*      */   {
/*  418 */     this._controlEncoding = encoding;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getControlEncoding()
/*      */   {
/*  427 */     return this._controlEncoding;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void disconnect()
/*      */     throws IOException
/*      */   {
/*  443 */     super.disconnect();
/*  444 */     this._controlInput_ = null;
/*  445 */     this._controlOutput_ = null;
/*  446 */     this._newReplyString = false;
/*  447 */     this._replyString = null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int sendCommand(String command, String args)
/*      */     throws IOException
/*      */   {
/*  473 */     if (this._controlOutput_ == null) {
/*  474 */       throw new IOException("Connection is not open");
/*      */     }
/*      */     
/*  477 */     String message = __buildMessage(command, args);
/*      */     
/*  479 */     __send(message);
/*      */     
/*  481 */     fireCommandSent(command, message);
/*      */     
/*  483 */     __getReply();
/*  484 */     return this._replyCode;
/*      */   }
/*      */   
/*      */   private String __buildMessage(String command, String args) {
/*  488 */     StringBuilder __commandBuffer = new StringBuilder();
/*      */     
/*  490 */     __commandBuffer.append(command);
/*      */     
/*  492 */     if (args != null)
/*      */     {
/*  494 */       __commandBuffer.append(' ');
/*  495 */       __commandBuffer.append(args);
/*      */     }
/*  497 */     __commandBuffer.append("\r\n");
/*  498 */     return __commandBuffer.toString();
/*      */   }
/*      */   
/*      */   private void __send(String message) throws IOException, FTPConnectionClosedException, SocketException
/*      */   {
/*      */     try {
/*  504 */       this._controlOutput_.write(message);
/*  505 */       this._controlOutput_.flush();
/*      */     }
/*      */     catch (SocketException e)
/*      */     {
/*  509 */       if (!isConnected())
/*      */       {
/*  511 */         throw new FTPConnectionClosedException("Connection unexpectedly closed.");
/*      */       }
/*      */       
/*      */ 
/*  515 */       throw e;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void __noop()
/*      */     throws IOException
/*      */   {
/*  528 */     String msg = __buildMessage(FTPCmd.NOOP.getCommand(), null);
/*  529 */     __send(msg);
/*  530 */     __getReplyNoReport();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public int sendCommand(int command, String args)
/*      */     throws IOException
/*      */   {
/*  558 */     return sendCommand(FTPCommand.getCommand(command), args);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int sendCommand(FTPCmd command)
/*      */     throws IOException
/*      */   {
/*  582 */     return sendCommand(command, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int sendCommand(FTPCmd command, String args)
/*      */     throws IOException
/*      */   {
/*  608 */     return sendCommand(command.getCommand(), args);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int sendCommand(String command)
/*      */     throws IOException
/*      */   {
/*  631 */     return sendCommand(command, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int sendCommand(int command)
/*      */     throws IOException
/*      */   {
/*  656 */     return sendCommand(command, null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getReplyCode()
/*      */   {
/*  670 */     return this._replyCode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getReply()
/*      */     throws IOException
/*      */   {
/*  692 */     __getReply();
/*  693 */     return this._replyCode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] getReplyStrings()
/*      */   {
/*  706 */     return (String[])this._replyLines.toArray(new String[this._replyLines.size()]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public String getReplyString()
/*      */   {
/*  720 */     if (!this._newReplyString) {
/*  721 */       return this._replyString;
/*      */     }
/*      */     
/*  724 */     StringBuilder buffer = new StringBuilder(256);
/*      */     
/*  726 */     for (String line : this._replyLines) {
/*  727 */       buffer.append(line);
/*  728 */       buffer.append("\r\n");
/*      */     }
/*      */     
/*  731 */     this._newReplyString = false;
/*      */     
/*  733 */     return this._replyString = buffer.toString();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int user(String username)
/*      */     throws IOException
/*      */   {
/*  753 */     return sendCommand(FTPCmd.USER, username);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int pass(String password)
/*      */     throws IOException
/*      */   {
/*  771 */     return sendCommand(FTPCmd.PASS, password);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int acct(String account)
/*      */     throws IOException
/*      */   {
/*  790 */     return sendCommand(FTPCmd.ACCT, account);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int abor()
/*      */     throws IOException
/*      */   {
/*  809 */     return sendCommand(FTPCmd.ABOR);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int cwd(String directory)
/*      */     throws IOException
/*      */   {
/*  828 */     return sendCommand(FTPCmd.CWD, directory);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int cdup()
/*      */     throws IOException
/*      */   {
/*  846 */     return sendCommand(FTPCmd.CDUP);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int quit()
/*      */     throws IOException
/*      */   {
/*  864 */     return sendCommand(FTPCmd.QUIT);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int rein()
/*      */     throws IOException
/*      */   {
/*  882 */     return sendCommand(FTPCmd.REIN);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int smnt(String dir)
/*      */     throws IOException
/*      */   {
/*  901 */     return sendCommand(FTPCmd.SMNT, dir);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int port(InetAddress host, int port)
/*      */     throws IOException
/*      */   {
/*  922 */     StringBuilder info = new StringBuilder(24);
/*      */     
/*  924 */     info.append(host.getHostAddress().replace('.', ','));
/*  925 */     int num = port >>> 8;
/*  926 */     info.append(',');
/*  927 */     info.append(num);
/*  928 */     info.append(',');
/*  929 */     num = port & 0xFF;
/*  930 */     info.append(num);
/*      */     
/*  932 */     return sendCommand(FTPCmd.PORT, info.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int eprt(InetAddress host, int port)
/*      */     throws IOException
/*      */   {
/*  964 */     StringBuilder info = new StringBuilder();
/*      */     
/*      */ 
/*      */ 
/*  968 */     String h = host.getHostAddress();
/*  969 */     int num = h.indexOf("%");
/*  970 */     if (num > 0) {
/*  971 */       h = h.substring(0, num);
/*      */     }
/*      */     
/*  974 */     info.append("|");
/*      */     
/*  976 */     if ((host instanceof Inet4Address)) {
/*  977 */       info.append("1");
/*  978 */     } else if ((host instanceof Inet6Address)) {
/*  979 */       info.append("2");
/*      */     }
/*  981 */     info.append("|");
/*  982 */     info.append(h);
/*  983 */     info.append("|");
/*  984 */     info.append(port);
/*  985 */     info.append("|");
/*      */     
/*  987 */     return sendCommand(FTPCmd.EPRT, info.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int pasv()
/*      */     throws IOException
/*      */   {
/* 1007 */     return sendCommand(FTPCmd.PASV);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int epsv()
/*      */     throws IOException
/*      */   {
/* 1028 */     return sendCommand(FTPCmd.EPSV);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int type(int fileType, int formatOrByteSize)
/*      */     throws IOException
/*      */   {
/* 1050 */     StringBuilder arg = new StringBuilder();
/*      */     
/* 1052 */     arg.append("AEILNTCFRPSBC".charAt(fileType));
/* 1053 */     arg.append(' ');
/* 1054 */     if (fileType == 3) {
/* 1055 */       arg.append(formatOrByteSize);
/*      */     } else {
/* 1057 */       arg.append("AEILNTCFRPSBC".charAt(formatOrByteSize));
/*      */     }
/*      */     
/* 1060 */     return sendCommand(FTPCmd.TYPE, arg.toString());
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int type(int fileType)
/*      */     throws IOException
/*      */   {
/* 1081 */     return sendCommand(FTPCmd.TYPE, 
/* 1082 */       "AEILNTCFRPSBC".substring(fileType, fileType + 1));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int stru(int structure)
/*      */     throws IOException
/*      */   {
/* 1102 */     return sendCommand(FTPCmd.STRU, 
/* 1103 */       "AEILNTCFRPSBC".substring(structure, structure + 1));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int mode(int mode)
/*      */     throws IOException
/*      */   {
/* 1123 */     return sendCommand(FTPCmd.MODE, 
/* 1124 */       "AEILNTCFRPSBC".substring(mode, mode + 1));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int retr(String pathname)
/*      */     throws IOException
/*      */   {
/* 1146 */     return sendCommand(FTPCmd.RETR, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int stor(String pathname)
/*      */     throws IOException
/*      */   {
/* 1169 */     return sendCommand(FTPCmd.STOR, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int stou()
/*      */     throws IOException
/*      */   {
/* 1190 */     return sendCommand(FTPCmd.STOU);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int stou(String pathname)
/*      */     throws IOException
/*      */   {
/* 1213 */     return sendCommand(FTPCmd.STOU, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int appe(String pathname)
/*      */     throws IOException
/*      */   {
/* 1236 */     return sendCommand(FTPCmd.APPE, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int allo(int bytes)
/*      */     throws IOException
/*      */   {
/* 1255 */     return sendCommand(FTPCmd.ALLO, Integer.toString(bytes));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int feat()
/*      */     throws IOException
/*      */   {
/* 1268 */     return sendCommand(FTPCmd.FEAT);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int allo(int bytes, int recordSize)
/*      */     throws IOException
/*      */   {
/* 1288 */     return sendCommand(FTPCmd.ALLO, Integer.toString(bytes) + " R " + 
/* 1289 */       Integer.toString(recordSize));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int rest(String marker)
/*      */     throws IOException
/*      */   {
/* 1308 */     return sendCommand(FTPCmd.REST, marker);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int mdtm(String file)
/*      */     throws IOException
/*      */   {
/* 1317 */     return sendCommand(FTPCmd.MDTM, file);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int mfmt(String pathname, String timeval)
/*      */     throws IOException
/*      */   {
/* 1340 */     return sendCommand(FTPCmd.MFMT, timeval + " " + pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int rnfr(String pathname)
/*      */     throws IOException
/*      */   {
/* 1360 */     return sendCommand(FTPCmd.RNFR, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int rnto(String pathname)
/*      */     throws IOException
/*      */   {
/* 1379 */     return sendCommand(FTPCmd.RNTO, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int dele(String pathname)
/*      */     throws IOException
/*      */   {
/* 1398 */     return sendCommand(FTPCmd.DELE, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int rmd(String pathname)
/*      */     throws IOException
/*      */   {
/* 1417 */     return sendCommand(FTPCmd.RMD, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int mkd(String pathname)
/*      */     throws IOException
/*      */   {
/* 1436 */     return sendCommand(FTPCmd.MKD, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int pwd()
/*      */     throws IOException
/*      */   {
/* 1454 */     return sendCommand(FTPCmd.PWD);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int list()
/*      */     throws IOException
/*      */   {
/* 1475 */     return sendCommand(FTPCmd.LIST);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int list(String pathname)
/*      */     throws IOException
/*      */   {
/* 1498 */     return sendCommand(FTPCmd.LIST, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int mlsd()
/*      */     throws IOException
/*      */   {
/* 1520 */     return sendCommand(FTPCmd.MLSD);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int mlsd(String path)
/*      */     throws IOException
/*      */   {
/* 1544 */     return sendCommand(FTPCmd.MLSD, path);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int mlst()
/*      */     throws IOException
/*      */   {
/* 1566 */     return sendCommand(FTPCmd.MLST);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int mlst(String path)
/*      */     throws IOException
/*      */   {
/* 1590 */     return sendCommand(FTPCmd.MLST, path);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int nlst()
/*      */     throws IOException
/*      */   {
/* 1611 */     return sendCommand(FTPCmd.NLST);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int nlst(String pathname)
/*      */     throws IOException
/*      */   {
/* 1634 */     return sendCommand(FTPCmd.NLST, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int site(String parameters)
/*      */     throws IOException
/*      */   {
/* 1653 */     return sendCommand(FTPCmd.SITE, parameters);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int syst()
/*      */     throws IOException
/*      */   {
/* 1671 */     return sendCommand(FTPCmd.SYST);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int stat()
/*      */     throws IOException
/*      */   {
/* 1689 */     return sendCommand(FTPCmd.STAT);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int stat(String pathname)
/*      */     throws IOException
/*      */   {
/* 1708 */     return sendCommand(FTPCmd.STAT, pathname);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int help()
/*      */     throws IOException
/*      */   {
/* 1726 */     return sendCommand(FTPCmd.HELP);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int help(String command)
/*      */     throws IOException
/*      */   {
/* 1745 */     return sendCommand(FTPCmd.HELP, command);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int noop()
/*      */     throws IOException
/*      */   {
/* 1763 */     return sendCommand(FTPCmd.NOOP);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isStrictMultilineParsing()
/*      */   {
/* 1772 */     return this.strictMultilineParsing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setStrictMultilineParsing(boolean strictMultilineParsing)
/*      */   {
/* 1781 */     this.strictMultilineParsing = strictMultilineParsing;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected ProtocolCommandSupport getCommandSupport()
/*      */   {
/* 1789 */     return this._commandSupport_;
/*      */   }
/*      */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\FTP.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */