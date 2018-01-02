/*      */ package org.apache.commons.net.ftp;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.BufferedOutputStream;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.InputStreamReader;
/*      */ import java.io.OutputStream;
/*      */ import java.io.OutputStreamWriter;
/*      */ import java.net.Inet6Address;
/*      */ import java.net.InetAddress;
/*      */ import java.net.InetSocketAddress;
/*      */ import java.net.ServerSocket;
/*      */ import java.net.Socket;
/*      */ import java.net.SocketException;
/*      */ import java.net.SocketTimeoutException;
/*      */ import java.net.UnknownHostException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Locale;
/*      */ import java.util.Properties;
/*      */ import java.util.Random;
/*      */ import java.util.Set;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import javax.net.ServerSocketFactory;
/*      */ import javax.net.SocketFactory;
/*      */ import org.apache.commons.net.MalformedServerReplyException;
/*      */ import org.apache.commons.net.ftp.parser.DefaultFTPFileEntryParserFactory;
/*      */ import org.apache.commons.net.ftp.parser.FTPFileEntryParserFactory;
/*      */ import org.apache.commons.net.ftp.parser.MLSxEntryParser;
/*      */ import org.apache.commons.net.io.CRLFLineReader;
/*      */ import org.apache.commons.net.io.CopyStreamAdapter;
/*      */ import org.apache.commons.net.io.CopyStreamEvent;
/*      */ import org.apache.commons.net.io.CopyStreamListener;
/*      */ import org.apache.commons.net.io.FromNetASCIIInputStream;
/*      */ import org.apache.commons.net.io.SocketInputStream;
/*      */ import org.apache.commons.net.io.SocketOutputStream;
/*      */ import org.apache.commons.net.io.ToNetASCIIOutputStream;
/*      */ import org.apache.commons.net.io.Util;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class FTPClient
/*      */   extends FTP
/*      */   implements Configurable
/*      */ {
/*      */   public static final String FTP_SYSTEM_TYPE = "org.apache.commons.net.ftp.systemType";
/*      */   public static final String FTP_SYSTEM_TYPE_DEFAULT = "org.apache.commons.net.ftp.systemType.default";
/*      */   public static final String SYSTEM_TYPE_PROPERTIES = "/systemType.properties";
/*      */   public static final int ACTIVE_LOCAL_DATA_CONNECTION_MODE = 0;
/*      */   public static final int ACTIVE_REMOTE_DATA_CONNECTION_MODE = 1;
/*      */   public static final int PASSIVE_LOCAL_DATA_CONNECTION_MODE = 2;
/*      */   public static final int PASSIVE_REMOTE_DATA_CONNECTION_MODE = 3;
/*      */   private int __dataConnectionMode;
/*      */   private int __dataTimeout;
/*      */   private int __passivePort;
/*      */   private String __passiveHost;
/*      */   private final Random __random;
/*      */   private int __activeMinPort;
/*      */   private int __activeMaxPort;
/*      */   private InetAddress __activeExternalHost;
/*      */   private InetAddress __reportActiveExternalHost;
/*      */   private InetAddress __passiveLocalHost;
/*      */   private int __fileType;
/*      */   private int __fileFormat;
/*      */   private int __fileStructure;
/*      */   private int __fileTransferMode;
/*      */   private boolean __remoteVerificationEnabled;
/*      */   private long __restartOffset;
/*      */   private FTPFileEntryParserFactory __parserFactory;
/*      */   private int __bufferSize;
/*      */   private int __sendDataSocketBufferSize;
/*      */   private int __receiveDataSocketBufferSize;
/*      */   private boolean __listHiddenFiles;
/*      */   private boolean __useEPSVwithIPv4;
/*      */   private String __systemName;
/*      */   private FTPFileEntryParser __entryParser;
/*      */   private String __entryParserKey;
/*      */   private FTPClientConfig __configuration;
/*      */   private CopyStreamListener __copyStreamListener;
/*      */   private long __controlKeepAliveTimeout;
/*  391 */   private int __controlKeepAliveReplyTimeout = 1000;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  396 */   private boolean __passiveNatWorkaround = true;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*  401 */   private static final Pattern __PARMS_PAT = Pattern.compile(
/*  402 */     "(\\d{1,3},\\d{1,3},\\d{1,3},\\d{1,3}),(\\d{1,3}),(\\d{1,3})");
/*      */   
/*      */ 
/*      */ 
/*  406 */   private boolean __autodetectEncoding = false;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private HashMap<String, Set<String>> __featuresMap;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static Properties getOverrideProperties()
/*      */   {
/*  436 */     return PropertiesSingleton.PROPERTIES;
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
/*      */   public FTPClient()
/*      */   {
/*  459 */     __initDefaults();
/*  460 */     this.__dataTimeout = -1;
/*  461 */     this.__remoteVerificationEnabled = true;
/*  462 */     this.__parserFactory = new DefaultFTPFileEntryParserFactory();
/*  463 */     this.__configuration = null;
/*  464 */     this.__listHiddenFiles = false;
/*  465 */     this.__useEPSVwithIPv4 = false;
/*  466 */     this.__random = new Random();
/*  467 */     this.__passiveLocalHost = null;
/*      */   }
/*      */   
/*      */ 
/*      */   private void __initDefaults()
/*      */   {
/*  473 */     this.__dataConnectionMode = 0;
/*  474 */     this.__passiveHost = null;
/*  475 */     this.__passivePort = -1;
/*  476 */     this.__activeExternalHost = null;
/*  477 */     this.__reportActiveExternalHost = null;
/*  478 */     this.__activeMinPort = 0;
/*  479 */     this.__activeMaxPort = 0;
/*  480 */     this.__fileType = 0;
/*  481 */     this.__fileStructure = 7;
/*  482 */     this.__fileFormat = 4;
/*  483 */     this.__fileTransferMode = 10;
/*  484 */     this.__restartOffset = 0L;
/*  485 */     this.__systemName = null;
/*  486 */     this.__entryParser = null;
/*  487 */     this.__entryParserKey = "";
/*  488 */     this.__featuresMap = null;
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
/*      */   static String __parsePathname(String reply)
/*      */   {
/*  510 */     String param = reply.substring(4);
/*  511 */     if (param.startsWith("\"")) {
/*  512 */       StringBuilder sb = new StringBuilder();
/*  513 */       boolean quoteSeen = false;
/*      */       
/*  515 */       for (int i = 1; i < param.length(); i++) {
/*  516 */         char ch = param.charAt(i);
/*  517 */         if (ch == '"') {
/*  518 */           if (quoteSeen) {
/*  519 */             sb.append(ch);
/*  520 */             quoteSeen = false;
/*      */           }
/*      */           else {
/*  523 */             quoteSeen = true;
/*      */           }
/*      */         } else {
/*  526 */           if (quoteSeen) {
/*  527 */             return sb.toString();
/*      */           }
/*  529 */           sb.append(ch);
/*      */         }
/*      */       }
/*  532 */       if (quoteSeen) {
/*  533 */         return sb.toString();
/*      */       }
/*      */     }
/*      */     
/*  537 */     return param;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected void _parsePassiveModeReply(String reply)
/*      */     throws MalformedServerReplyException
/*      */   {
/*  546 */     Matcher m = __PARMS_PAT.matcher(reply);
/*  547 */     if (!m.find()) {
/*  548 */       throw new MalformedServerReplyException(
/*  549 */         "Could not parse passive host information.\nServer Reply: " + reply);
/*      */     }
/*      */     
/*  552 */     this.__passiveHost = m.group(1).replace(',', '.');
/*      */     
/*      */     try
/*      */     {
/*  556 */       int oct1 = Integer.parseInt(m.group(2));
/*  557 */       int oct2 = Integer.parseInt(m.group(3));
/*  558 */       this.__passivePort = (oct1 << 8 | oct2);
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/*  562 */       throw new MalformedServerReplyException(
/*  563 */         "Could not parse passive port information.\nServer Reply: " + reply);
/*      */     }
/*      */     
/*  566 */     if (this.__passiveNatWorkaround) {
/*      */       try {
/*  568 */         InetAddress host = InetAddress.getByName(this.__passiveHost);
/*      */         
/*  570 */         if (host.isSiteLocalAddress()) {
/*  571 */           InetAddress remote = getRemoteAddress();
/*  572 */           if (!remote.isSiteLocalAddress()) {
/*  573 */             String hostAddress = remote.getHostAddress();
/*  574 */             fireReplyReceived(0, 
/*  575 */               "[Replacing site local address " + this.__passiveHost + " with " + hostAddress + "]\n");
/*  576 */             this.__passiveHost = hostAddress;
/*      */           }
/*      */         }
/*      */       } catch (UnknownHostException e) {
/*  580 */         throw new MalformedServerReplyException(
/*  581 */           "Could not parse passive host information.\nServer Reply: " + reply);
/*      */       }
/*      */     }
/*      */   }
/*      */   
/*      */   protected void _parseExtendedPassiveModeReply(String reply)
/*      */     throws MalformedServerReplyException
/*      */   {
/*  589 */     reply = 
/*  590 */       reply.substring(reply.indexOf('(') + 1, reply.indexOf(')')).trim();
/*      */     
/*      */ 
/*  593 */     char delim1 = reply.charAt(0);
/*  594 */     char delim2 = reply.charAt(1);
/*  595 */     char delim3 = reply.charAt(2);
/*  596 */     char delim4 = reply.charAt(reply.length() - 1);
/*      */     
/*  598 */     if ((delim1 != delim2) || (delim2 != delim3) || 
/*  599 */       (delim3 != delim4)) {
/*  600 */       throw new MalformedServerReplyException(
/*  601 */         "Could not parse extended passive host information.\nServer Reply: " + reply);
/*      */     }
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  607 */       port = Integer.parseInt(reply.substring(3, reply.length() - 1));
/*      */     }
/*      */     catch (NumberFormatException e) {
/*      */       int port;
/*  611 */       throw new MalformedServerReplyException(
/*  612 */         "Could not parse extended passive host information.\nServer Reply: " + reply);
/*      */     }
/*      */     
/*      */     int port;
/*      */     
/*  617 */     this.__passiveHost = getRemoteAddress().getHostAddress();
/*  618 */     this.__passivePort = port;
/*      */   }
/*      */   
/*      */   private boolean __storeFile(FTPCmd command, String remote, InputStream local)
/*      */     throws IOException
/*      */   {
/*  624 */     return _storeFile(command.getCommand(), remote, local);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected boolean _storeFile(String command, String remote, InputStream local)
/*      */     throws IOException
/*      */   {
/*  633 */     Socket socket = _openDataConnection_(command, remote);
/*      */     
/*  635 */     if (socket == null) {
/*  636 */       return false;
/*      */     }
/*      */     
/*  639 */     OutputStream output = getBufferedOutputStream(socket.getOutputStream());
/*      */     
/*  641 */     if (this.__fileType == 0) {
/*  642 */       output = new ToNetASCIIOutputStream(output);
/*      */     }
/*      */     
/*  645 */     CSL csl = null;
/*  646 */     if (this.__controlKeepAliveTimeout > 0L) {
/*  647 */       csl = new CSL(this, this.__controlKeepAliveTimeout, this.__controlKeepAliveReplyTimeout);
/*      */     }
/*      */     
/*      */ 
/*      */     try
/*      */     {
/*  653 */       Util.copyStream(local, output, getBufferSize(), 
/*  654 */         -1L, __mergeListeners(csl), 
/*  655 */         false);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  659 */       Util.closeQuietly(socket);
/*  660 */       if (csl != null) {
/*  661 */         csl.cleanUp();
/*      */       }
/*  663 */       throw e;
/*      */     }
/*      */     
/*  666 */     output.close();
/*  667 */     socket.close();
/*  668 */     if (csl != null) {
/*  669 */       csl.cleanUp();
/*      */     }
/*      */     
/*  672 */     boolean ok = completePendingCommand();
/*  673 */     return ok;
/*      */   }
/*      */   
/*      */   private OutputStream __storeFileStream(FTPCmd command, String remote)
/*      */     throws IOException
/*      */   {
/*  679 */     return _storeFileStream(command.getCommand(), remote);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected OutputStream _storeFileStream(String command, String remote)
/*      */     throws IOException
/*      */   {
/*  688 */     Socket socket = _openDataConnection_(command, remote);
/*      */     
/*  690 */     if (socket == null) {
/*  691 */       return null;
/*      */     }
/*      */     
/*  694 */     OutputStream output = socket.getOutputStream();
/*  695 */     if (this.__fileType == 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  703 */       output = getBufferedOutputStream(output);
/*  704 */       output = new ToNetASCIIOutputStream(output);
/*      */     }
/*      */     
/*  707 */     return new SocketOutputStream(socket, output);
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
/*      */   @Deprecated
/*      */   protected Socket _openDataConnection_(int command, String arg)
/*      */     throws IOException
/*      */   {
/*  734 */     return _openDataConnection_(FTPCommand.getCommand(command), arg);
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
/*      */   protected Socket _openDataConnection_(FTPCmd command, String arg)
/*      */     throws IOException
/*      */   {
/*  759 */     return _openDataConnection_(command.getCommand(), arg);
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
/*      */   protected Socket _openDataConnection_(String command, String arg)
/*      */     throws IOException
/*      */   {
/*  784 */     if ((this.__dataConnectionMode != 0) && 
/*  785 */       (this.__dataConnectionMode != 2)) {
/*  786 */       return null;
/*      */     }
/*      */     
/*  789 */     boolean isInet6Address = getRemoteAddress() instanceof Inet6Address;
/*      */     
/*      */     Socket socket;
/*      */     
/*  793 */     if (this.__dataConnectionMode == 0)
/*      */     {
/*      */ 
/*      */ 
/*  797 */       ServerSocket server = this._serverSocketFactory_.createServerSocket(getActivePort(), 1, getHostAddress());
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       try
/*      */       {
/*  807 */         if (isInet6Address) {
/*  808 */           if (!FTPReply.isPositiveCompletion(eprt(getReportHostAddress(), server.getLocalPort()))) {
/*  809 */             return null;
/*      */           }
/*      */         }
/*  812 */         else if (!FTPReply.isPositiveCompletion(port(getReportHostAddress(), server.getLocalPort()))) {
/*  813 */           return null;
/*      */         }
/*      */         
/*      */ 
/*  817 */         if ((this.__restartOffset > 0L) && (!restart(this.__restartOffset))) {
/*  818 */           return null;
/*      */         }
/*      */         
/*  821 */         if (!FTPReply.isPositivePreliminary(sendCommand(command, arg))) {
/*  822 */           return null;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  829 */         if (this.__dataTimeout >= 0) {
/*  830 */           server.setSoTimeout(this.__dataTimeout);
/*      */         }
/*  832 */         Socket socket = server.accept();
/*      */         
/*      */ 
/*  835 */         if (this.__dataTimeout >= 0) {
/*  836 */           socket.setSoTimeout(this.__dataTimeout);
/*      */         }
/*  838 */         if (this.__receiveDataSocketBufferSize > 0) {
/*  839 */           socket.setReceiveBufferSize(this.__receiveDataSocketBufferSize);
/*      */         }
/*  841 */         if (this.__sendDataSocketBufferSize > 0) {
/*  842 */           socket.setSendBufferSize(this.__sendDataSocketBufferSize);
/*      */         }
/*      */       } finally {
/*  845 */         server.close(); } Socket socket; server.close();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  858 */       boolean attemptEPSV = (isUseEPSVwithIPv4()) || (isInet6Address);
/*  859 */       if ((attemptEPSV) && (epsv() == 229))
/*      */       {
/*  861 */         _parseExtendedPassiveModeReply((String)this._replyLines.get(0));
/*      */       }
/*      */       else
/*      */       {
/*  865 */         if (isInet6Address) {
/*  866 */           return null;
/*      */         }
/*      */         
/*  869 */         if (pasv() != 227) {
/*  870 */           return null;
/*      */         }
/*  872 */         _parsePassiveModeReply((String)this._replyLines.get(0));
/*      */       }
/*      */       
/*  875 */       socket = this._socketFactory_.createSocket();
/*  876 */       if (this.__receiveDataSocketBufferSize > 0) {
/*  877 */         socket.setReceiveBufferSize(this.__receiveDataSocketBufferSize);
/*      */       }
/*  879 */       if (this.__sendDataSocketBufferSize > 0) {
/*  880 */         socket.setSendBufferSize(this.__sendDataSocketBufferSize);
/*      */       }
/*  882 */       if (this.__passiveLocalHost != null) {
/*  883 */         socket.bind(new InetSocketAddress(this.__passiveLocalHost, 0));
/*      */       }
/*      */       
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  890 */       if (this.__dataTimeout >= 0) {
/*  891 */         socket.setSoTimeout(this.__dataTimeout);
/*      */       }
/*      */       
/*  894 */       socket.connect(new InetSocketAddress(this.__passiveHost, this.__passivePort), this.connectTimeout);
/*  895 */       if ((this.__restartOffset > 0L) && (!restart(this.__restartOffset)))
/*      */       {
/*  897 */         socket.close();
/*  898 */         return null;
/*      */       }
/*      */       
/*  901 */       if (!FTPReply.isPositivePreliminary(sendCommand(command, arg)))
/*      */       {
/*  903 */         socket.close();
/*  904 */         return null;
/*      */       }
/*      */     }
/*      */     
/*  908 */     if ((this.__remoteVerificationEnabled) && (!verifyRemote(socket)))
/*      */     {
/*  910 */       socket.close();
/*      */       
/*  912 */       throw new IOException(
/*  913 */         "Host attempting data connection " + socket.getInetAddress().getHostAddress() + 
/*  914 */         " is not same as server " + getRemoteAddress().getHostAddress());
/*      */     }
/*      */     
/*  917 */     return socket;
/*      */   }
/*      */   
/*      */ 
/*      */   protected void _connectAction_()
/*      */     throws IOException
/*      */   {
/*  924 */     super._connectAction_();
/*  925 */     __initDefaults();
/*      */     
/*      */ 
/*  928 */     if (this.__autodetectEncoding)
/*      */     {
/*  930 */       ArrayList<String> oldReplyLines = new ArrayList(this._replyLines);
/*  931 */       int oldReplyCode = this._replyCode;
/*  932 */       if ((hasFeature("UTF8")) || (hasFeature("UTF-8")))
/*      */       {
/*  934 */         setControlEncoding("UTF-8");
/*  935 */         this._controlInput_ = 
/*  936 */           new CRLFLineReader(new InputStreamReader(this._input_, getControlEncoding()));
/*  937 */         this._controlOutput_ = 
/*  938 */           new BufferedWriter(new OutputStreamWriter(this._output_, getControlEncoding()));
/*      */       }
/*      */       
/*  941 */       this._replyLines.clear();
/*  942 */       this._replyLines.addAll(oldReplyLines);
/*  943 */       this._replyCode = oldReplyCode;
/*      */     }
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
/*      */   public void setDataTimeout(int timeout)
/*      */   {
/*  960 */     this.__dataTimeout = timeout;
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
/*      */   public void setParserFactory(FTPFileEntryParserFactory parserFactory)
/*      */   {
/*  973 */     this.__parserFactory = parserFactory;
/*      */   }
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
/*  986 */     super.disconnect();
/*  987 */     __initDefaults();
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
/*      */   public void setRemoteVerificationEnabled(boolean enable)
/*      */   {
/* 1002 */     this.__remoteVerificationEnabled = enable;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isRemoteVerificationEnabled()
/*      */   {
/* 1014 */     return this.__remoteVerificationEnabled;
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
/*      */   public boolean login(String username, String password)
/*      */     throws IOException
/*      */   {
/* 1034 */     user(username);
/*      */     
/* 1036 */     if (FTPReply.isPositiveCompletion(this._replyCode)) {
/* 1037 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1042 */     if (!FTPReply.isPositiveIntermediate(this._replyCode)) {
/* 1043 */       return false;
/*      */     }
/*      */     
/* 1046 */     return FTPReply.isPositiveCompletion(pass(password));
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
/*      */   public boolean login(String username, String password, String account)
/*      */     throws IOException
/*      */   {
/* 1070 */     user(username);
/*      */     
/* 1072 */     if (FTPReply.isPositiveCompletion(this._replyCode)) {
/* 1073 */       return true;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1078 */     if (!FTPReply.isPositiveIntermediate(this._replyCode)) {
/* 1079 */       return false;
/*      */     }
/*      */     
/* 1082 */     pass(password);
/*      */     
/* 1084 */     if (FTPReply.isPositiveCompletion(this._replyCode)) {
/* 1085 */       return true;
/*      */     }
/*      */     
/* 1088 */     if (!FTPReply.isPositiveIntermediate(this._replyCode)) {
/* 1089 */       return false;
/*      */     }
/*      */     
/* 1092 */     return FTPReply.isPositiveCompletion(acct(account));
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
/*      */   public boolean logout()
/*      */     throws IOException
/*      */   {
/* 1109 */     return FTPReply.isPositiveCompletion(quit());
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
/*      */   public boolean changeWorkingDirectory(String pathname)
/*      */     throws IOException
/*      */   {
/* 1128 */     return FTPReply.isPositiveCompletion(cwd(pathname));
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
/*      */   public boolean changeToParentDirectory()
/*      */     throws IOException
/*      */   {
/* 1146 */     return FTPReply.isPositiveCompletion(cdup());
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
/*      */   public boolean structureMount(String pathname)
/*      */     throws IOException
/*      */   {
/* 1165 */     return FTPReply.isPositiveCompletion(smnt(pathname));
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
/*      */   boolean reinitialize()
/*      */     throws IOException
/*      */   {
/* 1183 */     rein();
/*      */     
/* 1185 */     if ((FTPReply.isPositiveCompletion(this._replyCode)) || (
/* 1186 */       (FTPReply.isPositivePreliminary(this._replyCode)) && 
/* 1187 */       (FTPReply.isPositiveCompletion(getReply()))))
/*      */     {
/*      */ 
/* 1190 */       __initDefaults();
/*      */       
/* 1192 */       return true;
/*      */     }
/*      */     
/* 1195 */     return false;
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
/*      */   public void enterLocalActiveMode()
/*      */   {
/* 1210 */     this.__dataConnectionMode = 0;
/* 1211 */     this.__passiveHost = null;
/* 1212 */     this.__passivePort = -1;
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
/*      */   public void enterLocalPassiveMode()
/*      */   {
/* 1233 */     this.__dataConnectionMode = 2;
/*      */     
/*      */ 
/* 1236 */     this.__passiveHost = null;
/* 1237 */     this.__passivePort = -1;
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
/*      */   public boolean enterRemoteActiveMode(InetAddress host, int port)
/*      */     throws IOException
/*      */   {
/* 1268 */     if (FTPReply.isPositiveCompletion(port(host, port)))
/*      */     {
/* 1270 */       this.__dataConnectionMode = 1;
/* 1271 */       this.__passiveHost = null;
/* 1272 */       this.__passivePort = -1;
/* 1273 */       return true;
/*      */     }
/* 1275 */     return false;
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
/*      */   public boolean enterRemotePassiveMode()
/*      */     throws IOException
/*      */   {
/* 1302 */     if (pasv() != 227) {
/* 1303 */       return false;
/*      */     }
/*      */     
/* 1306 */     this.__dataConnectionMode = 3;
/* 1307 */     _parsePassiveModeReply((String)this._replyLines.get(0));
/*      */     
/* 1309 */     return true;
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
/*      */   public String getPassiveHost()
/*      */   {
/* 1326 */     return this.__passiveHost;
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
/*      */   public int getPassivePort()
/*      */   {
/* 1343 */     return this.__passivePort;
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
/*      */   public int getDataConnectionMode()
/*      */   {
/* 1356 */     return this.__dataConnectionMode;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int getActivePort()
/*      */   {
/* 1366 */     if ((this.__activeMinPort > 0) && (this.__activeMaxPort >= this.__activeMinPort))
/*      */     {
/* 1368 */       if (this.__activeMaxPort == this.__activeMinPort) {
/* 1369 */         return this.__activeMaxPort;
/*      */       }
/*      */       
/* 1372 */       return this.__random.nextInt(this.__activeMaxPort - this.__activeMinPort + 1) + this.__activeMinPort;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1377 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private InetAddress getHostAddress()
/*      */   {
/* 1389 */     if (this.__activeExternalHost != null)
/*      */     {
/* 1391 */       return this.__activeExternalHost;
/*      */     }
/*      */     
/*      */ 
/*      */ 
/* 1396 */     return getLocalAddress();
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
/*      */   private InetAddress getReportHostAddress()
/*      */   {
/* 1409 */     if (this.__reportActiveExternalHost != null) {
/* 1410 */       return this.__reportActiveExternalHost;
/*      */     }
/* 1412 */     return getHostAddress();
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
/*      */   public void setActivePortRange(int minPort, int maxPort)
/*      */   {
/* 1425 */     this.__activeMinPort = minPort;
/* 1426 */     this.__activeMaxPort = maxPort;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setActiveExternalIPAddress(String ipAddress)
/*      */     throws UnknownHostException
/*      */   {
/* 1439 */     this.__activeExternalHost = InetAddress.getByName(ipAddress);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPassiveLocalIPAddress(String ipAddress)
/*      */     throws UnknownHostException
/*      */   {
/* 1451 */     this.__passiveLocalHost = InetAddress.getByName(ipAddress);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setPassiveLocalIPAddress(InetAddress inetAddress)
/*      */   {
/* 1462 */     this.__passiveLocalHost = inetAddress;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public InetAddress getPassiveLocalIPAddress()
/*      */   {
/* 1473 */     return this.__passiveLocalHost;
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
/*      */   public void setReportActiveExternalIPAddress(String ipAddress)
/*      */     throws UnknownHostException
/*      */   {
/* 1487 */     this.__reportActiveExternalHost = InetAddress.getByName(ipAddress);
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
/*      */   public boolean setFileType(int fileType)
/*      */     throws IOException
/*      */   {
/* 1519 */     if (FTPReply.isPositiveCompletion(type(fileType)))
/*      */     {
/* 1521 */       this.__fileType = fileType;
/* 1522 */       this.__fileFormat = 4;
/* 1523 */       return true;
/*      */     }
/* 1525 */     return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean setFileType(int fileType, int formatOrByteSize)
/*      */     throws IOException
/*      */   {
/* 1568 */     if (FTPReply.isPositiveCompletion(type(fileType, formatOrByteSize)))
/*      */     {
/* 1570 */       this.__fileType = fileType;
/* 1571 */       this.__fileFormat = formatOrByteSize;
/* 1572 */       return true;
/*      */     }
/* 1574 */     return false;
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
/*      */   public boolean setFileStructure(int structure)
/*      */     throws IOException
/*      */   {
/* 1596 */     if (FTPReply.isPositiveCompletion(stru(structure)))
/*      */     {
/* 1598 */       this.__fileStructure = structure;
/* 1599 */       return true;
/*      */     }
/* 1601 */     return false;
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
/*      */   public boolean setFileTransferMode(int mode)
/*      */     throws IOException
/*      */   {
/* 1623 */     if (FTPReply.isPositiveCompletion(mode(mode)))
/*      */     {
/* 1625 */       this.__fileTransferMode = mode;
/* 1626 */       return true;
/*      */     }
/* 1628 */     return false;
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
/*      */   public boolean remoteRetrieve(String filename)
/*      */     throws IOException
/*      */   {
/* 1649 */     if ((this.__dataConnectionMode == 1) || 
/* 1650 */       (this.__dataConnectionMode == 3)) {
/* 1651 */       return FTPReply.isPositivePreliminary(retr(filename));
/*      */     }
/* 1653 */     return false;
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
/*      */   public boolean remoteStore(String filename)
/*      */     throws IOException
/*      */   {
/* 1676 */     if ((this.__dataConnectionMode == 1) || 
/* 1677 */       (this.__dataConnectionMode == 3)) {
/* 1678 */       return FTPReply.isPositivePreliminary(stor(filename));
/*      */     }
/* 1680 */     return false;
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
/*      */   public boolean remoteStoreUnique(String filename)
/*      */     throws IOException
/*      */   {
/* 1704 */     if ((this.__dataConnectionMode == 1) || 
/* 1705 */       (this.__dataConnectionMode == 3)) {
/* 1706 */       return FTPReply.isPositivePreliminary(stou(filename));
/*      */     }
/* 1708 */     return false;
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
/*      */   public boolean remoteStoreUnique()
/*      */     throws IOException
/*      */   {
/* 1732 */     if ((this.__dataConnectionMode == 1) || 
/* 1733 */       (this.__dataConnectionMode == 3)) {
/* 1734 */       return FTPReply.isPositivePreliminary(stou());
/*      */     }
/* 1736 */     return false;
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
/*      */   public boolean remoteAppend(String filename)
/*      */     throws IOException
/*      */   {
/* 1760 */     if ((this.__dataConnectionMode == 1) || 
/* 1761 */       (this.__dataConnectionMode == 3)) {
/* 1762 */       return FTPReply.isPositivePreliminary(appe(filename));
/*      */     }
/* 1764 */     return false;
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean completePendingCommand()
/*      */     throws IOException
/*      */   {
/* 1813 */     return FTPReply.isPositiveCompletion(getReply());
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
/*      */   public boolean retrieveFile(String remote, OutputStream local)
/*      */     throws IOException
/*      */   {
/* 1845 */     return _retrieveFile(FTPCmd.RETR.getCommand(), remote, local);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   protected boolean _retrieveFile(String command, String remote, OutputStream local)
/*      */     throws IOException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: aload_1
/*      */     //   2: aload_2
/*      */     //   3: invokevirtual 320	org/apache/commons/net/ftp/FTPClient:_openDataConnection_	(Ljava/lang/String;Ljava/lang/String;)Ljava/net/Socket;
/*      */     //   6: astore 4
/*      */     //   8: aload 4
/*      */     //   10: ifnonnull +5 -> 15
/*      */     //   13: iconst_0
/*      */     //   14: ireturn
/*      */     //   15: aload_0
/*      */     //   16: aload 4
/*      */     //   18: invokevirtual 736	java/net/Socket:getInputStream	()Ljava/io/InputStream;
/*      */     //   21: invokespecial 740	org/apache/commons/net/ftp/FTPClient:getBufferedInputStream	(Ljava/io/InputStream;)Ljava/io/InputStream;
/*      */     //   24: astore 5
/*      */     //   26: aload_0
/*      */     //   27: getfield 145	org/apache/commons/net/ftp/FTPClient:__fileType	I
/*      */     //   30: ifne +14 -> 44
/*      */     //   33: new 744	org/apache/commons/net/io/FromNetASCIIInputStream
/*      */     //   36: dup
/*      */     //   37: aload 5
/*      */     //   39: invokespecial 746	org/apache/commons/net/io/FromNetASCIIInputStream:<init>	(Ljava/io/InputStream;)V
/*      */     //   42: astore 5
/*      */     //   44: aconst_null
/*      */     //   45: astore 6
/*      */     //   47: aload_0
/*      */     //   48: getfield 339	org/apache/commons/net/ftp/FTPClient:__controlKeepAliveTimeout	J
/*      */     //   51: lconst_0
/*      */     //   52: lcmp
/*      */     //   53: ifle +21 -> 74
/*      */     //   56: new 341	org/apache/commons/net/ftp/FTPClient$CSL
/*      */     //   59: dup
/*      */     //   60: aload_0
/*      */     //   61: aload_0
/*      */     //   62: getfield 339	org/apache/commons/net/ftp/FTPClient:__controlKeepAliveTimeout	J
/*      */     //   65: aload_0
/*      */     //   66: getfield 98	org/apache/commons/net/ftp/FTPClient:__controlKeepAliveReplyTimeout	I
/*      */     //   69: invokespecial 343	org/apache/commons/net/ftp/FTPClient$CSL:<init>	(Lorg/apache/commons/net/ftp/FTPClient;JI)V
/*      */     //   72: astore 6
/*      */     //   74: aload 5
/*      */     //   76: aload_3
/*      */     //   77: aload_0
/*      */     //   78: invokevirtual 346	org/apache/commons/net/ftp/FTPClient:getBufferSize	()I
/*      */     //   81: ldc2_w 349
/*      */     //   84: aload_0
/*      */     //   85: aload 6
/*      */     //   87: invokespecial 351	org/apache/commons/net/ftp/FTPClient:__mergeListeners	(Lorg/apache/commons/net/io/CopyStreamListener;)Lorg/apache/commons/net/io/CopyStreamListener;
/*      */     //   90: iconst_0
/*      */     //   91: invokestatic 355	org/apache/commons/net/io/Util:copyStream	(Ljava/io/InputStream;Ljava/io/OutputStream;IJLorg/apache/commons/net/io/CopyStreamListener;Z)J
/*      */     //   94: pop2
/*      */     //   95: goto +28 -> 123
/*      */     //   98: astore 7
/*      */     //   100: aload 5
/*      */     //   102: invokestatic 749	org/apache/commons/net/io/Util:closeQuietly	(Ljava/io/Closeable;)V
/*      */     //   105: aload 4
/*      */     //   107: invokestatic 361	org/apache/commons/net/io/Util:closeQuietly	(Ljava/net/Socket;)V
/*      */     //   110: aload 6
/*      */     //   112: ifnull +8 -> 120
/*      */     //   115: aload 6
/*      */     //   117: invokevirtual 365	org/apache/commons/net/ftp/FTPClient$CSL:cleanUp	()V
/*      */     //   120: aload 7
/*      */     //   122: athrow
/*      */     //   123: aload 5
/*      */     //   125: invokestatic 749	org/apache/commons/net/io/Util:closeQuietly	(Ljava/io/Closeable;)V
/*      */     //   128: aload 4
/*      */     //   130: invokestatic 361	org/apache/commons/net/io/Util:closeQuietly	(Ljava/net/Socket;)V
/*      */     //   133: aload 6
/*      */     //   135: ifnull +8 -> 143
/*      */     //   138: aload 6
/*      */     //   140: invokevirtual 365	org/apache/commons/net/ftp/FTPClient$CSL:cleanUp	()V
/*      */     //   143: aload_0
/*      */     //   144: invokevirtual 374	org/apache/commons/net/ftp/FTPClient:completePendingCommand	()Z
/*      */     //   147: istore 7
/*      */     //   149: iload 7
/*      */     //   151: ireturn
/*      */     // Line number table:
/*      */     //   Java source line #1854	-> byte code offset #0
/*      */     //   Java source line #1856	-> byte code offset #8
/*      */     //   Java source line #1857	-> byte code offset #13
/*      */     //   Java source line #1860	-> byte code offset #15
/*      */     //   Java source line #1861	-> byte code offset #26
/*      */     //   Java source line #1862	-> byte code offset #33
/*      */     //   Java source line #1865	-> byte code offset #44
/*      */     //   Java source line #1866	-> byte code offset #47
/*      */     //   Java source line #1867	-> byte code offset #56
/*      */     //   Java source line #1873	-> byte code offset #74
/*      */     //   Java source line #1874	-> byte code offset #81
/*      */     //   Java source line #1875	-> byte code offset #90
/*      */     //   Java source line #1873	-> byte code offset #91
/*      */     //   Java source line #1876	-> byte code offset #95
/*      */     //   Java source line #1877	-> byte code offset #100
/*      */     //   Java source line #1878	-> byte code offset #105
/*      */     //   Java source line #1879	-> byte code offset #110
/*      */     //   Java source line #1880	-> byte code offset #115
/*      */     //   Java source line #1882	-> byte code offset #120
/*      */     //   Java source line #1877	-> byte code offset #123
/*      */     //   Java source line #1878	-> byte code offset #128
/*      */     //   Java source line #1879	-> byte code offset #133
/*      */     //   Java source line #1880	-> byte code offset #138
/*      */     //   Java source line #1885	-> byte code offset #143
/*      */     //   Java source line #1886	-> byte code offset #149
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	152	0	this	FTPClient
/*      */     //   0	152	1	command	String
/*      */     //   0	152	2	remote	String
/*      */     //   0	152	3	local	OutputStream
/*      */     //   6	123	4	socket	Socket
/*      */     //   24	100	5	input	InputStream
/*      */     //   45	94	6	csl	CSL
/*      */     //   98	23	7	localObject	Object
/*      */     //   147	3	7	ok	boolean
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   74	98	98	finally
/*      */   }
/*      */   
/*      */   public InputStream retrieveFileStream(String remote)
/*      */     throws IOException
/*      */   {
/* 1918 */     return _retrieveFileStream(FTPCmd.RETR.getCommand(), remote);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   protected InputStream _retrieveFileStream(String command, String remote)
/*      */     throws IOException
/*      */   {
/* 1927 */     Socket socket = _openDataConnection_(command, remote);
/*      */     
/* 1929 */     if (socket == null) {
/* 1930 */       return null;
/*      */     }
/*      */     
/* 1933 */     InputStream input = socket.getInputStream();
/* 1934 */     if (this.__fileType == 0)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/* 1942 */       input = getBufferedInputStream(input);
/* 1943 */       input = new FromNetASCIIInputStream(input);
/*      */     }
/* 1945 */     return new SocketInputStream(socket, input);
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
/*      */   public boolean storeFile(String remote, InputStream local)
/*      */     throws IOException
/*      */   {
/* 1976 */     return __storeFile(FTPCmd.STOR, remote, local);
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
/*      */   public OutputStream storeFileStream(String remote)
/*      */     throws IOException
/*      */   {
/* 2007 */     return __storeFileStream(FTPCmd.STOR, remote);
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
/*      */   public boolean appendFile(String remote, InputStream local)
/*      */     throws IOException
/*      */   {
/* 2038 */     return __storeFile(FTPCmd.APPE, remote, local);
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
/*      */   public OutputStream appendFileStream(String remote)
/*      */     throws IOException
/*      */   {
/* 2068 */     return __storeFileStream(FTPCmd.APPE, remote);
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
/*      */   public boolean storeUniqueFile(String remote, InputStream local)
/*      */     throws IOException
/*      */   {
/* 2100 */     return __storeFile(FTPCmd.STOU, remote, local);
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
/*      */ 
/*      */   public OutputStream storeUniqueFileStream(String remote)
/*      */     throws IOException
/*      */   {
/* 2133 */     return __storeFileStream(FTPCmd.STOU, remote);
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
/*      */   public boolean storeUniqueFile(InputStream local)
/*      */     throws IOException
/*      */   {
/* 2162 */     return __storeFile(FTPCmd.STOU, null, local);
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
/*      */   public OutputStream storeUniqueFileStream()
/*      */     throws IOException
/*      */   {
/* 2192 */     return __storeFileStream(FTPCmd.STOU, null);
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
/*      */   public boolean allocate(int bytes)
/*      */     throws IOException
/*      */   {
/* 2210 */     return FTPReply.isPositiveCompletion(allo(bytes));
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
/*      */   public boolean features()
/*      */     throws IOException
/*      */   {
/* 2231 */     return FTPReply.isPositiveCompletion(feat());
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
/*      */   public String[] featureValues(String feature)
/*      */     throws IOException
/*      */   {
/* 2245 */     if (!initFeatureMap()) {
/* 2246 */       return null;
/*      */     }
/* 2248 */     Set<String> entries = (Set)this.__featuresMap.get(feature.toUpperCase(Locale.ENGLISH));
/* 2249 */     if (entries != null) {
/* 2250 */       return (String[])entries.toArray(new String[entries.size()]);
/*      */     }
/* 2252 */     return null;
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
/*      */   public String featureValue(String feature)
/*      */     throws IOException
/*      */   {
/* 2267 */     String[] values = featureValues(feature);
/* 2268 */     if (values != null) {
/* 2269 */       return values[0];
/*      */     }
/* 2271 */     return null;
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
/*      */   public boolean hasFeature(String feature)
/*      */     throws IOException
/*      */   {
/* 2287 */     if (!initFeatureMap()) {
/* 2288 */       return false;
/*      */     }
/* 2290 */     return this.__featuresMap.containsKey(feature.toUpperCase(Locale.ENGLISH));
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
/*      */   public boolean hasFeature(String feature, String value)
/*      */     throws IOException
/*      */   {
/* 2309 */     if (!initFeatureMap()) {
/* 2310 */       return false;
/*      */     }
/* 2312 */     Set<String> entries = (Set)this.__featuresMap.get(feature.toUpperCase(Locale.ENGLISH));
/* 2313 */     if (entries != null) {
/* 2314 */       return entries.contains(value);
/*      */     }
/* 2316 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */   private boolean initFeatureMap()
/*      */     throws IOException
/*      */   {
/* 2323 */     if (this.__featuresMap == null)
/*      */     {
/* 2325 */       boolean success = FTPReply.isPositiveCompletion(feat());
/*      */       
/* 2327 */       this.__featuresMap = new HashMap();
/* 2328 */       if (!success)
/* 2329 */         return false;
/*      */       String[] arrayOfString;
/* 2331 */       int j = (arrayOfString = getReplyStrings()).length; for (int i = 0; i < j; i++) { String l = arrayOfString[i];
/* 2332 */         if (l.startsWith(" "))
/*      */         {
/* 2334 */           String value = "";
/* 2335 */           int varsep = l.indexOf(' ', 1);
/* 2336 */           if (varsep > 0) {
/* 2337 */             String key = l.substring(1, varsep);
/* 2338 */             value = l.substring(varsep + 1);
/*      */           } else {
/* 2340 */             key = l.substring(1);
/*      */           }
/* 2342 */           String key = key.toUpperCase(Locale.ENGLISH);
/* 2343 */           Set<String> entries = (Set)this.__featuresMap.get(key);
/* 2344 */           if (entries == null) {
/* 2345 */             entries = new HashSet();
/* 2346 */             this.__featuresMap.put(key, entries);
/*      */           }
/* 2348 */           entries.add(value);
/*      */         }
/*      */       }
/*      */     }
/* 2352 */     return true;
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
/*      */   public boolean allocate(int bytes, int recordSize)
/*      */     throws IOException
/*      */   {
/* 2371 */     return FTPReply.isPositiveCompletion(allo(bytes, recordSize));
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
/*      */   public boolean doCommand(String command, String params)
/*      */     throws IOException
/*      */   {
/* 2393 */     return FTPReply.isPositiveCompletion(sendCommand(command, params));
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
/*      */   public String[] doCommandAsStrings(String command, String params)
/*      */     throws IOException
/*      */   {
/* 2414 */     boolean success = FTPReply.isPositiveCompletion(sendCommand(command, params));
/* 2415 */     if (success) {
/* 2416 */       return getReplyStrings();
/*      */     }
/* 2418 */     return null;
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
/*      */   public FTPFile mlistFile(String pathname)
/*      */     throws IOException
/*      */   {
/* 2432 */     boolean success = FTPReply.isPositiveCompletion(sendCommand(FTPCmd.MLST, pathname));
/* 2433 */     if (success) {
/* 2434 */       String entry = getReplyStrings()[1].substring(1);
/* 2435 */       return MLSxEntryParser.parseEntry(entry);
/*      */     }
/* 2437 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public FTPFile[] mlistDir()
/*      */     throws IOException
/*      */   {
/* 2450 */     return mlistDir(null);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public FTPFile[] mlistDir(String pathname)
/*      */     throws IOException
/*      */   {
/* 2463 */     FTPListParseEngine engine = initiateMListParsing(pathname);
/* 2464 */     return engine.getFiles();
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
/*      */   public FTPFile[] mlistDir(String pathname, FTPFileFilter filter)
/*      */     throws IOException
/*      */   {
/* 2478 */     FTPListParseEngine engine = initiateMListParsing(pathname);
/* 2479 */     return engine.getFiles(filter);
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
/*      */   protected boolean restart(long offset)
/*      */     throws IOException
/*      */   {
/* 2503 */     this.__restartOffset = 0L;
/* 2504 */     return FTPReply.isPositiveIntermediate(rest(Long.toString(offset)));
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
/*      */   public void setRestartOffset(long offset)
/*      */   {
/* 2525 */     if (offset >= 0L) {
/* 2526 */       this.__restartOffset = offset;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long getRestartOffset()
/*      */   {
/* 2538 */     return this.__restartOffset;
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
/*      */   public boolean rename(String from, String to)
/*      */     throws IOException
/*      */   {
/* 2559 */     if (!FTPReply.isPositiveIntermediate(rnfr(from))) {
/* 2560 */       return false;
/*      */     }
/*      */     
/* 2563 */     return FTPReply.isPositiveCompletion(rnto(to));
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
/*      */   public boolean abort()
/*      */     throws IOException
/*      */   {
/* 2581 */     return FTPReply.isPositiveCompletion(abor());
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
/*      */   public boolean deleteFile(String pathname)
/*      */     throws IOException
/*      */   {
/* 2599 */     return FTPReply.isPositiveCompletion(dele(pathname));
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
/*      */   public boolean removeDirectory(String pathname)
/*      */     throws IOException
/*      */   {
/* 2618 */     return FTPReply.isPositiveCompletion(rmd(pathname));
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
/*      */   public boolean makeDirectory(String pathname)
/*      */     throws IOException
/*      */   {
/* 2639 */     return FTPReply.isPositiveCompletion(mkd(pathname));
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
/*      */   public String printWorkingDirectory()
/*      */     throws IOException
/*      */   {
/* 2658 */     if (pwd() != 257) {
/* 2659 */       return null;
/*      */     }
/*      */     
/* 2662 */     return __parsePathname((String)this._replyLines.get(this._replyLines.size() - 1));
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
/*      */   public boolean sendSiteCommand(String arguments)
/*      */     throws IOException
/*      */   {
/* 2680 */     return FTPReply.isPositiveCompletion(site(arguments));
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
/*      */   public String getSystemType()
/*      */     throws IOException
/*      */   {
/* 2711 */     if (this.__systemName == null) {
/* 2712 */       if (FTPReply.isPositiveCompletion(syst()))
/*      */       {
/* 2714 */         this.__systemName = ((String)this._replyLines.get(this._replyLines.size() - 1)).substring(4);
/*      */       }
/*      */       else {
/* 2717 */         String systDefault = System.getProperty("org.apache.commons.net.ftp.systemType.default");
/* 2718 */         if (systDefault != null) {
/* 2719 */           this.__systemName = systDefault;
/*      */         } else {
/* 2721 */           throw new IOException("Unable to determine system type - response: " + getReplyString());
/*      */         }
/*      */       }
/*      */     }
/* 2725 */     return this.__systemName;
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
/*      */   public String listHelp()
/*      */     throws IOException
/*      */   {
/* 2745 */     if (FTPReply.isPositiveCompletion(help())) {
/* 2746 */       return getReplyString();
/*      */     }
/* 2748 */     return null;
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
/*      */   public String listHelp(String command)
/*      */     throws IOException
/*      */   {
/* 2768 */     if (FTPReply.isPositiveCompletion(help(command))) {
/* 2769 */       return getReplyString();
/*      */     }
/* 2771 */     return null;
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
/*      */   public boolean sendNoOp()
/*      */     throws IOException
/*      */   {
/* 2790 */     return FTPReply.isPositiveCompletion(noop());
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public String[] listNames(String pathname)
/*      */     throws IOException
/*      */   {
/* 2825 */     Socket socket = _openDataConnection_(FTPCmd.NLST, getListArguments(pathname));
/*      */     
/* 2827 */     if (socket == null) {
/* 2828 */       return null;
/*      */     }
/*      */     
/* 2831 */     BufferedReader reader = 
/* 2832 */       new BufferedReader(new InputStreamReader(socket.getInputStream(), getControlEncoding()));
/*      */     
/* 2834 */     ArrayList<String> results = new ArrayList();
/*      */     String line;
/* 2836 */     while ((line = reader.readLine()) != null) { String line;
/* 2837 */       results.add(line);
/*      */     }
/*      */     
/* 2840 */     reader.close();
/* 2841 */     socket.close();
/*      */     
/* 2843 */     if (completePendingCommand())
/*      */     {
/* 2845 */       String[] names = new String[results.size()];
/* 2846 */       return (String[])results.toArray(names);
/*      */     }
/*      */     
/* 2849 */     return null;
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
/*      */   public String[] listNames()
/*      */     throws IOException
/*      */   {
/* 2876 */     return listNames(null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public FTPFile[] listFiles(String pathname)
/*      */     throws IOException
/*      */   {
/* 2930 */     FTPListParseEngine engine = initiateListParsing(null, pathname);
/* 2931 */     return engine.getFiles();
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public FTPFile[] listFiles()
/*      */     throws IOException
/*      */   {
/* 2977 */     return listFiles(null);
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
/*      */   public FTPFile[] listFiles(String pathname, FTPFileFilter filter)
/*      */     throws IOException
/*      */   {
/* 2992 */     FTPListParseEngine engine = initiateListParsing(null, pathname);
/* 2993 */     return engine.getFiles(filter);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public FTPFile[] listDirectories()
/*      */     throws IOException
/*      */   {
/* 3035 */     return listDirectories(null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public FTPFile[] listDirectories(String parent)
/*      */     throws IOException
/*      */   {
/* 3076 */     return listFiles(parent, FTPFileFilters.DIRECTORIES);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public FTPListParseEngine initiateListParsing()
/*      */     throws IOException
/*      */   {
/* 3115 */     return initiateListParsing(null);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public FTPListParseEngine initiateListParsing(String pathname)
/*      */     throws IOException
/*      */   {
/* 3171 */     return initiateListParsing(null, pathname);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public FTPListParseEngine initiateListParsing(String parserKey, String pathname)
/*      */     throws IOException
/*      */   {
/* 3234 */     if ((this.__entryParser == null) || (!this.__entryParserKey.equals(parserKey))) {
/* 3235 */       if (parserKey != null)
/*      */       {
/*      */ 
/* 3238 */         this.__entryParser = 
/* 3239 */           this.__parserFactory.createFileEntryParser(parserKey);
/* 3240 */         this.__entryParserKey = parserKey;
/*      */ 
/*      */ 
/*      */ 
/*      */       }
/* 3245 */       else if (this.__configuration != null) {
/* 3246 */         this.__entryParser = 
/* 3247 */           this.__parserFactory.createFileEntryParser(this.__configuration);
/* 3248 */         this.__entryParserKey = this.__configuration.getServerSystemKey();
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*      */ 
/* 3254 */         String systemType = System.getProperty("org.apache.commons.net.ftp.systemType");
/* 3255 */         if (systemType == null) {
/* 3256 */           systemType = getSystemType();
/* 3257 */           Properties override = getOverrideProperties();
/* 3258 */           if (override != null) {
/* 3259 */             String newType = override.getProperty(systemType);
/* 3260 */             if (newType != null) {
/* 3261 */               systemType = newType;
/*      */             }
/*      */           }
/*      */         }
/* 3265 */         this.__entryParser = this.__parserFactory.createFileEntryParser(systemType);
/* 3266 */         this.__entryParserKey = systemType;
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 3271 */     return initiateListParsing(this.__entryParser, pathname);
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private FTPListParseEngine initiateListParsing(FTPFileEntryParser parser, String pathname)
/*      */     throws IOException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getstatic 1062	org/apache/commons/net/ftp/FTPCmd:LIST	Lorg/apache/commons/net/ftp/FTPCmd;
/*      */     //   4: aload_0
/*      */     //   5: aload_2
/*      */     //   6: invokevirtual 989	org/apache/commons/net/ftp/FTPClient:getListArguments	(Ljava/lang/String;)Ljava/lang/String;
/*      */     //   9: invokevirtual 992	org/apache/commons/net/ftp/FTPClient:_openDataConnection_	(Lorg/apache/commons/net/ftp/FTPCmd;Ljava/lang/String;)Ljava/net/Socket;
/*      */     //   12: astore_3
/*      */     //   13: new 898	org/apache/commons/net/ftp/FTPListParseEngine
/*      */     //   16: dup
/*      */     //   17: aload_1
/*      */     //   18: invokespecial 1065	org/apache/commons/net/ftp/FTPListParseEngine:<init>	(Lorg/apache/commons/net/ftp/FTPFileEntryParser;)V
/*      */     //   21: astore 4
/*      */     //   23: aload_3
/*      */     //   24: ifnonnull +6 -> 30
/*      */     //   27: aload 4
/*      */     //   29: areturn
/*      */     //   30: aload 4
/*      */     //   32: aload_3
/*      */     //   33: invokevirtual 736	java/net/Socket:getInputStream	()Ljava/io/InputStream;
/*      */     //   36: aload_0
/*      */     //   37: invokevirtual 569	org/apache/commons/net/ftp/FTPClient:getControlEncoding	()Ljava/lang/String;
/*      */     //   40: invokevirtual 1068	org/apache/commons/net/ftp/FTPListParseEngine:readServerList	(Ljava/io/InputStream;Ljava/lang/String;)V
/*      */     //   43: goto +12 -> 55
/*      */     //   46: astore 5
/*      */     //   48: aload_3
/*      */     //   49: invokestatic 361	org/apache/commons/net/io/Util:closeQuietly	(Ljava/net/Socket;)V
/*      */     //   52: aload 5
/*      */     //   54: athrow
/*      */     //   55: aload_3
/*      */     //   56: invokestatic 361	org/apache/commons/net/io/Util:closeQuietly	(Ljava/net/Socket;)V
/*      */     //   59: aload_0
/*      */     //   60: invokevirtual 374	org/apache/commons/net/ftp/FTPClient:completePendingCommand	()Z
/*      */     //   63: pop
/*      */     //   64: aload 4
/*      */     //   66: areturn
/*      */     // Line number table:
/*      */     //   Java source line #3293	-> byte code offset #0
/*      */     //   Java source line #3295	-> byte code offset #13
/*      */     //   Java source line #3296	-> byte code offset #23
/*      */     //   Java source line #3298	-> byte code offset #27
/*      */     //   Java source line #3302	-> byte code offset #30
/*      */     //   Java source line #3303	-> byte code offset #43
/*      */     //   Java source line #3304	-> byte code offset #46
/*      */     //   Java source line #3305	-> byte code offset #48
/*      */     //   Java source line #3306	-> byte code offset #52
/*      */     //   Java source line #3305	-> byte code offset #55
/*      */     //   Java source line #3308	-> byte code offset #59
/*      */     //   Java source line #3309	-> byte code offset #64
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	67	0	this	FTPClient
/*      */     //   0	67	1	parser	FTPFileEntryParser
/*      */     //   0	67	2	pathname	String
/*      */     //   12	44	3	socket	Socket
/*      */     //   21	44	4	engine	FTPListParseEngine
/*      */     //   46	7	5	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   30	46	46	finally
/*      */   }
/*      */   
/*      */   /* Error */
/*      */   private FTPListParseEngine initiateMListParsing(String pathname)
/*      */     throws IOException
/*      */   {
/*      */     // Byte code:
/*      */     //   0: aload_0
/*      */     //   1: getstatic 1072	org/apache/commons/net/ftp/FTPCmd:MLSD	Lorg/apache/commons/net/ftp/FTPCmd;
/*      */     //   4: aload_1
/*      */     //   5: invokevirtual 992	org/apache/commons/net/ftp/FTPClient:_openDataConnection_	(Lorg/apache/commons/net/ftp/FTPCmd;Ljava/lang/String;)Ljava/net/Socket;
/*      */     //   8: astore_2
/*      */     //   9: new 898	org/apache/commons/net/ftp/FTPListParseEngine
/*      */     //   12: dup
/*      */     //   13: invokestatic 1075	org/apache/commons/net/ftp/parser/MLSxEntryParser:getInstance	()Lorg/apache/commons/net/ftp/parser/MLSxEntryParser;
/*      */     //   16: invokespecial 1065	org/apache/commons/net/ftp/FTPListParseEngine:<init>	(Lorg/apache/commons/net/ftp/FTPFileEntryParser;)V
/*      */     //   19: astore_3
/*      */     //   20: aload_2
/*      */     //   21: ifnonnull +5 -> 26
/*      */     //   24: aload_3
/*      */     //   25: areturn
/*      */     //   26: aload_3
/*      */     //   27: aload_2
/*      */     //   28: invokevirtual 736	java/net/Socket:getInputStream	()Ljava/io/InputStream;
/*      */     //   31: aload_0
/*      */     //   32: invokevirtual 569	org/apache/commons/net/ftp/FTPClient:getControlEncoding	()Ljava/lang/String;
/*      */     //   35: invokevirtual 1068	org/apache/commons/net/ftp/FTPListParseEngine:readServerList	(Ljava/io/InputStream;Ljava/lang/String;)V
/*      */     //   38: goto +17 -> 55
/*      */     //   41: astore 4
/*      */     //   43: aload_2
/*      */     //   44: invokestatic 361	org/apache/commons/net/io/Util:closeQuietly	(Ljava/net/Socket;)V
/*      */     //   47: aload_0
/*      */     //   48: invokevirtual 374	org/apache/commons/net/ftp/FTPClient:completePendingCommand	()Z
/*      */     //   51: pop
/*      */     //   52: aload 4
/*      */     //   54: athrow
/*      */     //   55: aload_2
/*      */     //   56: invokestatic 361	org/apache/commons/net/io/Util:closeQuietly	(Ljava/net/Socket;)V
/*      */     //   59: aload_0
/*      */     //   60: invokevirtual 374	org/apache/commons/net/ftp/FTPClient:completePendingCommand	()Z
/*      */     //   63: pop
/*      */     //   64: aload_3
/*      */     //   65: areturn
/*      */     // Line number table:
/*      */     //   Java source line #3321	-> byte code offset #0
/*      */     //   Java source line #3322	-> byte code offset #9
/*      */     //   Java source line #3323	-> byte code offset #20
/*      */     //   Java source line #3325	-> byte code offset #24
/*      */     //   Java source line #3329	-> byte code offset #26
/*      */     //   Java source line #3330	-> byte code offset #38
/*      */     //   Java source line #3331	-> byte code offset #41
/*      */     //   Java source line #3332	-> byte code offset #43
/*      */     //   Java source line #3333	-> byte code offset #47
/*      */     //   Java source line #3334	-> byte code offset #52
/*      */     //   Java source line #3332	-> byte code offset #55
/*      */     //   Java source line #3333	-> byte code offset #59
/*      */     //   Java source line #3335	-> byte code offset #64
/*      */     // Local variable table:
/*      */     //   start	length	slot	name	signature
/*      */     //   0	66	0	this	FTPClient
/*      */     //   0	66	1	pathname	String
/*      */     //   8	48	2	socket	Socket
/*      */     //   19	46	3	engine	FTPListParseEngine
/*      */     //   41	12	4	localObject	Object
/*      */     // Exception table:
/*      */     //   from	to	target	type
/*      */     //   26	41	41	finally
/*      */   }
/*      */   
/*      */   protected String getListArguments(String pathname)
/*      */   {
/* 3342 */     if (getListHiddenFiles())
/*      */     {
/* 3344 */       if (pathname != null)
/*      */       {
/* 3346 */         StringBuilder sb = new StringBuilder(pathname.length() + 3);
/* 3347 */         sb.append("-a ");
/* 3348 */         sb.append(pathname);
/* 3349 */         return sb.toString();
/*      */       }
/*      */       
/*      */ 
/* 3353 */       return "-a";
/*      */     }
/*      */     
/*      */ 
/* 3357 */     return pathname;
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
/*      */   public String getStatus()
/*      */     throws IOException
/*      */   {
/* 3375 */     if (FTPReply.isPositiveCompletion(stat())) {
/* 3376 */       return getReplyString();
/*      */     }
/* 3378 */     return null;
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
/*      */   public String getStatus(String pathname)
/*      */     throws IOException
/*      */   {
/* 3397 */     if (FTPReply.isPositiveCompletion(stat(pathname))) {
/* 3398 */       return getReplyString();
/*      */     }
/* 3400 */     return null;
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
/*      */   public String getModificationTime(String pathname)
/*      */     throws IOException
/*      */   {
/* 3416 */     if (FTPReply.isPositiveCompletion(mdtm(pathname))) {
/* 3417 */       return getReplyString();
/*      */     }
/* 3419 */     return null;
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
/*      */   public boolean setModificationTime(String pathname, String timeval)
/*      */     throws IOException
/*      */   {
/* 3441 */     return FTPReply.isPositiveCompletion(mfmt(pathname, timeval));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setBufferSize(int bufSize)
/*      */   {
/* 3451 */     this.__bufferSize = bufSize;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getBufferSize()
/*      */   {
/* 3459 */     return this.__bufferSize;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setSendDataSocketBufferSize(int bufSize)
/*      */   {
/* 3470 */     this.__sendDataSocketBufferSize = bufSize;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getSendDataSocketBufferSize()
/*      */   {
/* 3479 */     return this.__sendDataSocketBufferSize;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setReceieveDataSocketBufferSize(int bufSize)
/*      */   {
/* 3490 */     this.__receiveDataSocketBufferSize = bufSize;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getReceiveDataSocketBufferSize()
/*      */   {
/* 3499 */     return this.__receiveDataSocketBufferSize;
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
/*      */   public void configure(FTPClientConfig config)
/*      */   {
/* 3512 */     this.__configuration = config;
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
/*      */   public void setListHiddenFiles(boolean listHiddenFiles)
/*      */   {
/* 3525 */     this.__listHiddenFiles = listHiddenFiles;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getListHiddenFiles()
/*      */   {
/* 3534 */     return this.__listHiddenFiles;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isUseEPSVwithIPv4()
/*      */   {
/* 3544 */     return this.__useEPSVwithIPv4;
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
/*      */   public void setUseEPSVwithIPv4(boolean selected)
/*      */   {
/* 3563 */     this.__useEPSVwithIPv4 = selected;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setCopyStreamListener(CopyStreamListener listener)
/*      */   {
/* 3574 */     this.__copyStreamListener = listener;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public CopyStreamListener getCopyStreamListener()
/*      */   {
/* 3584 */     return this.__copyStreamListener;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setControlKeepAliveTimeout(long controlIdle)
/*      */   {
/* 3596 */     this.__controlKeepAliveTimeout = (controlIdle * 1000L);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public long getControlKeepAliveTimeout()
/*      */   {
/* 3605 */     return this.__controlKeepAliveTimeout / 1000L;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setControlKeepAliveReplyTimeout(int timeout)
/*      */   {
/* 3616 */     this.__controlKeepAliveReplyTimeout = timeout;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */   public int getControlKeepAliveReplyTimeout()
/*      */   {
/* 3624 */     return this.__controlKeepAliveReplyTimeout;
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
/*      */   public void setPassiveNatWorkaround(boolean enabled)
/*      */   {
/* 3640 */     this.__passiveNatWorkaround = enabled;
/*      */   }
/*      */   
/*      */   private OutputStream getBufferedOutputStream(OutputStream outputStream) {
/* 3644 */     if (this.__bufferSize > 0) {
/* 3645 */       return new BufferedOutputStream(outputStream, this.__bufferSize);
/*      */     }
/* 3647 */     return new BufferedOutputStream(outputStream);
/*      */   }
/*      */   
/*      */   private InputStream getBufferedInputStream(InputStream inputStream) {
/* 3651 */     if (this.__bufferSize > 0) {
/* 3652 */       return new BufferedInputStream(inputStream, this.__bufferSize);
/*      */     }
/* 3654 */     return new BufferedInputStream(inputStream);
/*      */   }
/*      */   
/*      */ 
/*      */   private static class CSL
/*      */     implements CopyStreamListener
/*      */   {
/*      */     private final FTPClient parent;
/*      */     private final long idle;
/*      */     private final int currentSoTimeout;
/* 3664 */     private long time = System.currentTimeMillis();
/*      */     private int notAcked;
/*      */     
/*      */     CSL(FTPClient parent, long idleTime, int maxWait) throws SocketException {
/* 3668 */       this.idle = idleTime;
/* 3669 */       this.parent = parent;
/* 3670 */       this.currentSoTimeout = parent.getSoTimeout();
/* 3671 */       parent.setSoTimeout(maxWait);
/*      */     }
/*      */     
/*      */     public void bytesTransferred(CopyStreamEvent event)
/*      */     {
/* 3676 */       bytesTransferred(event.getTotalBytesTransferred(), event.getBytesTransferred(), event.getStreamSize());
/*      */     }
/*      */     
/*      */ 
/*      */     public void bytesTransferred(long totalBytesTransferred, int bytesTransferred, long streamSize)
/*      */     {
/* 3682 */       long now = System.currentTimeMillis();
/* 3683 */       if (now - this.time > this.idle) {
/*      */         try {
/* 3685 */           this.parent.__noop();
/*      */         } catch (SocketTimeoutException e) {
/* 3687 */           this.notAcked += 1;
/*      */         }
/*      */         catch (IOException localIOException) {}
/* 3690 */         this.time = now;
/*      */       }
/*      */     }
/*      */     
/*      */     /* Error */
/*      */     void cleanUp()
/*      */       throws IOException
/*      */     {
/*      */       // Byte code:
/*      */       //   0: goto +10 -> 10
/*      */       //   3: aload_0
/*      */       //   4: getfield 34	org/apache/commons/net/ftp/FTPClient$CSL:parent	Lorg/apache/commons/net/ftp/FTPClient;
/*      */       //   7: invokevirtual 88	org/apache/commons/net/ftp/FTPClient:__getReplyNoReport	()V
/*      */       //   10: aload_0
/*      */       //   11: dup
/*      */       //   12: getfield 75	org/apache/commons/net/ftp/FTPClient$CSL:notAcked	I
/*      */       //   15: dup_x1
/*      */       //   16: iconst_1
/*      */       //   17: isub
/*      */       //   18: putfield 75	org/apache/commons/net/ftp/FTPClient$CSL:notAcked	I
/*      */       //   21: ifgt -18 -> 3
/*      */       //   24: goto +17 -> 41
/*      */       //   27: astore_1
/*      */       //   28: aload_0
/*      */       //   29: getfield 34	org/apache/commons/net/ftp/FTPClient$CSL:parent	Lorg/apache/commons/net/ftp/FTPClient;
/*      */       //   32: aload_0
/*      */       //   33: getfield 42	org/apache/commons/net/ftp/FTPClient$CSL:currentSoTimeout	I
/*      */       //   36: invokevirtual 44	org/apache/commons/net/ftp/FTPClient:setSoTimeout	(I)V
/*      */       //   39: aload_1
/*      */       //   40: athrow
/*      */       //   41: aload_0
/*      */       //   42: getfield 34	org/apache/commons/net/ftp/FTPClient$CSL:parent	Lorg/apache/commons/net/ftp/FTPClient;
/*      */       //   45: aload_0
/*      */       //   46: getfield 42	org/apache/commons/net/ftp/FTPClient$CSL:currentSoTimeout	I
/*      */       //   49: invokevirtual 44	org/apache/commons/net/ftp/FTPClient:setSoTimeout	(I)V
/*      */       //   52: return
/*      */       // Line number table:
/*      */       //   Java source line #3696	-> byte code offset #0
/*      */       //   Java source line #3697	-> byte code offset #3
/*      */       //   Java source line #3696	-> byte code offset #10
/*      */       //   Java source line #3699	-> byte code offset #24
/*      */       //   Java source line #3700	-> byte code offset #28
/*      */       //   Java source line #3701	-> byte code offset #39
/*      */       //   Java source line #3700	-> byte code offset #41
/*      */       //   Java source line #3702	-> byte code offset #52
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   0	53	0	this	CSL
/*      */       //   27	13	1	localObject	Object
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   0	27	27	finally
/*      */     }
/*      */   }
/*      */   
/*      */   private CopyStreamListener __mergeListeners(CopyStreamListener local)
/*      */   {
/* 3714 */     if (local == null) {
/* 3715 */       return this.__copyStreamListener;
/*      */     }
/* 3717 */     if (this.__copyStreamListener == null) {
/* 3718 */       return local;
/*      */     }
/*      */     
/* 3721 */     CopyStreamAdapter merged = new CopyStreamAdapter();
/* 3722 */     merged.addCopyStreamListener(local);
/* 3723 */     merged.addCopyStreamListener(this.__copyStreamListener);
/* 3724 */     return merged;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public void setAutodetectUTF8(boolean autodetect)
/*      */   {
/* 3736 */     this.__autodetectEncoding = autodetect;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean getAutodetectUTF8()
/*      */   {
/* 3745 */     return this.__autodetectEncoding;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   @Deprecated
/*      */   public String getSystemName()
/*      */     throws IOException
/*      */   {
/* 3756 */     if ((this.__systemName == null) && (FTPReply.isPositiveCompletion(syst()))) {
/* 3757 */       this.__systemName = ((String)this._replyLines.get(this._replyLines.size() - 1)).substring(4);
/*      */     }
/* 3759 */     return this.__systemName;
/*      */   }
/*      */   
/*      */   private static class PropertiesSingleton
/*      */   {
/*      */     static final Properties PROPERTIES;
/*      */     
/*      */     /* Error */
/*      */     static
/*      */     {
/*      */       // Byte code:
/*      */       //   0: ldc 10
/*      */       //   2: ldc 12
/*      */       //   4: invokevirtual 14	java/lang/Class:getResourceAsStream	(Ljava/lang/String;)Ljava/io/InputStream;
/*      */       //   7: astore_0
/*      */       //   8: aconst_null
/*      */       //   9: astore_1
/*      */       //   10: aload_0
/*      */       //   11: ifnull +53 -> 64
/*      */       //   14: new 20	java/util/Properties
/*      */       //   17: dup
/*      */       //   18: invokespecial 22	java/util/Properties:<init>	()V
/*      */       //   21: astore_1
/*      */       //   22: aload_1
/*      */       //   23: aload_0
/*      */       //   24: invokevirtual 25	java/util/Properties:load	(Ljava/io/InputStream;)V
/*      */       //   27: goto +28 -> 55
/*      */       //   30: astore_2
/*      */       //   31: aload_0
/*      */       //   32: invokevirtual 29	java/io/InputStream:close	()V
/*      */       //   35: goto +29 -> 64
/*      */       //   38: astore 4
/*      */       //   40: goto +24 -> 64
/*      */       //   43: astore_3
/*      */       //   44: aload_0
/*      */       //   45: invokevirtual 29	java/io/InputStream:close	()V
/*      */       //   48: goto +5 -> 53
/*      */       //   51: astore 4
/*      */       //   53: aload_3
/*      */       //   54: athrow
/*      */       //   55: aload_0
/*      */       //   56: invokevirtual 29	java/io/InputStream:close	()V
/*      */       //   59: goto +5 -> 64
/*      */       //   62: astore 4
/*      */       //   64: aload_1
/*      */       //   65: putstatic 34	org/apache/commons/net/ftp/FTPClient$PropertiesSingleton:PROPERTIES	Ljava/util/Properties;
/*      */       //   68: return
/*      */       // Line number table:
/*      */       //   Java source line #416	-> byte code offset #0
/*      */       //   Java source line #417	-> byte code offset #8
/*      */       //   Java source line #418	-> byte code offset #10
/*      */       //   Java source line #419	-> byte code offset #14
/*      */       //   Java source line #421	-> byte code offset #22
/*      */       //   Java source line #422	-> byte code offset #27
/*      */       //   Java source line #425	-> byte code offset #31
/*      */       //   Java source line #426	-> byte code offset #35
/*      */       //   Java source line #423	-> byte code offset #43
/*      */       //   Java source line #425	-> byte code offset #44
/*      */       //   Java source line #426	-> byte code offset #48
/*      */       //   Java source line #429	-> byte code offset #53
/*      */       //   Java source line #425	-> byte code offset #55
/*      */       //   Java source line #426	-> byte code offset #59
/*      */       //   Java source line #431	-> byte code offset #64
/*      */       //   Java source line #432	-> byte code offset #68
/*      */       // Local variable table:
/*      */       //   start	length	slot	name	signature
/*      */       //   7	49	0	resourceAsStream	InputStream
/*      */       //   9	56	1	p	Properties
/*      */       //   30	1	2	localIOException	IOException
/*      */       //   43	11	3	localObject	Object
/*      */       //   38	1	4	localIOException1	IOException
/*      */       //   51	1	4	localIOException2	IOException
/*      */       //   62	1	4	localIOException3	IOException
/*      */       // Exception table:
/*      */       //   from	to	target	type
/*      */       //   22	27	30	java/io/IOException
/*      */       //   31	35	38	java/io/IOException
/*      */       //   22	31	43	finally
/*      */       //   44	48	51	java/io/IOException
/*      */       //   55	59	62	java/io/IOException
/*      */     }
/*      */   }
/*      */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\FTPClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */