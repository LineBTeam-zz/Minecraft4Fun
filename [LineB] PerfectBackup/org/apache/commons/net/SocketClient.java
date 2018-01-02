/*     */ package org.apache.commons.net;
/*     */ 
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.net.InetAddress;
/*     */ import java.net.InetSocketAddress;
/*     */ import java.net.Proxy;
/*     */ import java.net.Socket;
/*     */ import java.net.SocketException;
/*     */ import java.nio.charset.Charset;
/*     */ import javax.net.ServerSocketFactory;
/*     */ import javax.net.SocketFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class SocketClient
/*     */ {
/*     */   public static final String NETASCII_EOL = "\r\n";
/*  65 */   private static final SocketFactory __DEFAULT_SOCKET_FACTORY = ;
/*     */   
/*     */ 
/*     */ 
/*  69 */   private static final ServerSocketFactory __DEFAULT_SERVER_SOCKET_FACTORY = ServerSocketFactory.getDefault();
/*     */   
/*     */ 
/*     */ 
/*     */   private ProtocolCommandSupport __commandSupport;
/*     */   
/*     */ 
/*     */   protected int _timeout_;
/*     */   
/*     */ 
/*     */   protected Socket _socket_;
/*     */   
/*     */ 
/*     */   protected int _defaultPort_;
/*     */   
/*     */ 
/*     */   protected InputStream _input_;
/*     */   
/*     */ 
/*     */   protected OutputStream _output_;
/*     */   
/*     */ 
/*     */   protected SocketFactory _socketFactory_;
/*     */   
/*     */ 
/*     */   protected ServerSocketFactory _serverSocketFactory_;
/*     */   
/*     */ 
/*     */   private static final int DEFAULT_CONNECT_TIMEOUT = 0;
/*     */   
/*     */ 
/* 100 */   protected int connectTimeout = 0;
/*     */   
/*     */ 
/* 103 */   private int receiveBufferSize = -1;
/*     */   
/*     */ 
/* 106 */   private int sendBufferSize = -1;
/*     */   
/*     */ 
/*     */ 
/*     */   private Proxy connProxy;
/*     */   
/*     */ 
/*     */ 
/* 114 */   private Charset charset = Charset.defaultCharset();
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SocketClient()
/*     */   {
/* 125 */     this._socket_ = null;
/* 126 */     this._input_ = null;
/* 127 */     this._output_ = null;
/* 128 */     this._timeout_ = 0;
/* 129 */     this._defaultPort_ = 0;
/* 130 */     this._socketFactory_ = __DEFAULT_SOCKET_FACTORY;
/* 131 */     this._serverSocketFactory_ = __DEFAULT_SERVER_SOCKET_FACTORY;
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
/*     */   protected void _connectAction_()
/*     */     throws IOException
/*     */   {
/* 153 */     this._socket_.setSoTimeout(this._timeout_);
/* 154 */     this._input_ = this._socket_.getInputStream();
/* 155 */     this._output_ = this._socket_.getOutputStream();
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
/*     */   public void connect(InetAddress host, int port)
/*     */     throws SocketException, IOException
/*     */   {
/* 175 */     this._socket_ = this._socketFactory_.createSocket();
/* 176 */     if (this.receiveBufferSize != -1) {
/* 177 */       this._socket_.setReceiveBufferSize(this.receiveBufferSize);
/*     */     }
/* 179 */     if (this.sendBufferSize != -1) {
/* 180 */       this._socket_.setSendBufferSize(this.sendBufferSize);
/*     */     }
/* 182 */     this._socket_.connect(new InetSocketAddress(host, port), this.connectTimeout);
/* 183 */     _connectAction_();
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
/*     */   public void connect(String hostname, int port)
/*     */     throws SocketException, IOException
/*     */   {
/* 203 */     connect(InetAddress.getByName(hostname), port);
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
/*     */   public void connect(InetAddress host, int port, InetAddress localAddr, int localPort)
/*     */     throws SocketException, IOException
/*     */   {
/* 226 */     this._socket_ = this._socketFactory_.createSocket();
/* 227 */     if (this.receiveBufferSize != -1) {
/* 228 */       this._socket_.setReceiveBufferSize(this.receiveBufferSize);
/*     */     }
/* 230 */     if (this.sendBufferSize != -1) {
/* 231 */       this._socket_.setSendBufferSize(this.sendBufferSize);
/*     */     }
/* 233 */     this._socket_.bind(new InetSocketAddress(localAddr, localPort));
/* 234 */     this._socket_.connect(new InetSocketAddress(host, port), this.connectTimeout);
/* 235 */     _connectAction_();
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
/*     */   public void connect(String hostname, int port, InetAddress localAddr, int localPort)
/*     */     throws SocketException, IOException
/*     */   {
/* 259 */     connect(InetAddress.getByName(hostname), port, localAddr, localPort);
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
/*     */   public void connect(InetAddress host)
/*     */     throws SocketException, IOException
/*     */   {
/* 277 */     connect(host, this._defaultPort_);
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
/*     */   public void connect(String hostname)
/*     */     throws SocketException, IOException
/*     */   {
/* 296 */     connect(hostname, this._defaultPort_);
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
/*     */   public void disconnect()
/*     */     throws IOException
/*     */   {
/* 312 */     closeQuietly(this._socket_);
/* 313 */     closeQuietly(this._input_);
/* 314 */     closeQuietly(this._output_);
/* 315 */     this._socket_ = null;
/* 316 */     this._input_ = null;
/* 317 */     this._output_ = null;
/*     */   }
/*     */   
/*     */   private void closeQuietly(Socket socket) {
/* 321 */     if (socket != null) {
/*     */       try {
/* 323 */         socket.close();
/*     */       }
/*     */       catch (IOException localIOException) {}
/*     */     }
/*     */   }
/*     */   
/*     */   private void closeQuietly(Closeable close) {
/* 330 */     if (close != null) {
/*     */       try {
/* 332 */         close.close();
/*     */       }
/*     */       catch (IOException localIOException) {}
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isConnected()
/*     */   {
/* 346 */     if (this._socket_ == null) {
/* 347 */       return false;
/*     */     }
/*     */     
/* 350 */     return this._socket_.isConnected();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isAvailable()
/*     */   {
/* 362 */     if (isConnected())
/*     */     {
/*     */       try {
/* 365 */         if (this._socket_.getInetAddress() == null) {
/* 366 */           return false;
/*     */         }
/* 368 */         if (this._socket_.getPort() == 0) {
/* 369 */           return false;
/*     */         }
/* 371 */         if (this._socket_.getRemoteSocketAddress() == null) {
/* 372 */           return false;
/*     */         }
/* 374 */         if (this._socket_.isClosed()) {
/* 375 */           return false;
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 380 */         if (this._socket_.isInputShutdown()) {
/* 381 */           return false;
/*     */         }
/* 383 */         if (this._socket_.isOutputShutdown()) {
/* 384 */           return false;
/*     */         }
/*     */         
/* 387 */         this._socket_.getInputStream();
/* 388 */         this._socket_.getOutputStream();
/*     */       }
/*     */       catch (IOException ioex)
/*     */       {
/* 392 */         return false;
/*     */       }
/* 394 */       return true;
/*     */     }
/* 396 */     return false;
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
/*     */   public void setDefaultPort(int port)
/*     */   {
/* 410 */     this._defaultPort_ = port;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getDefaultPort()
/*     */   {
/* 421 */     return this._defaultPort_;
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
/*     */   public void setDefaultTimeout(int timeout)
/*     */   {
/* 438 */     this._timeout_ = timeout;
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
/*     */   public int getDefaultTimeout()
/*     */   {
/* 451 */     return this._timeout_;
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
/*     */   public void setSoTimeout(int timeout)
/*     */     throws SocketException
/*     */   {
/* 469 */     this._socket_.setSoTimeout(timeout);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSendBufferSize(int size)
/*     */     throws SocketException
/*     */   {
/* 481 */     this.sendBufferSize = size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int getSendBufferSize()
/*     */   {
/* 490 */     return this.sendBufferSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setReceiveBufferSize(int size)
/*     */     throws SocketException
/*     */   {
/* 501 */     this.receiveBufferSize = size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected int getReceiveBufferSize()
/*     */   {
/* 510 */     return this.receiveBufferSize;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getSoTimeout()
/*     */     throws SocketException
/*     */   {
/* 522 */     return this._socket_.getSoTimeout();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTcpNoDelay(boolean on)
/*     */     throws SocketException
/*     */   {
/* 535 */     this._socket_.setTcpNoDelay(on);
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
/*     */   public boolean getTcpNoDelay()
/*     */     throws SocketException
/*     */   {
/* 550 */     return this._socket_.getTcpNoDelay();
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
/*     */   public void setKeepAlive(boolean keepAlive)
/*     */     throws SocketException
/*     */   {
/* 566 */     this._socket_.setKeepAlive(keepAlive);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean getKeepAlive()
/*     */     throws SocketException
/*     */   {
/* 578 */     return this._socket_.getKeepAlive();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSoLinger(boolean on, int val)
/*     */     throws SocketException
/*     */   {
/* 591 */     this._socket_.setSoLinger(on, val);
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
/*     */   public int getSoLinger()
/*     */     throws SocketException
/*     */   {
/* 605 */     return this._socket_.getSoLinger();
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
/*     */   public int getLocalPort()
/*     */   {
/* 620 */     return this._socket_.getLocalPort();
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
/*     */   public InetAddress getLocalAddress()
/*     */   {
/* 633 */     return this._socket_.getLocalAddress();
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
/*     */   public int getRemotePort()
/*     */   {
/* 647 */     return this._socket_.getPort();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public InetAddress getRemoteAddress()
/*     */   {
/* 658 */     return this._socket_.getInetAddress();
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
/*     */   public boolean verifyRemote(Socket socket)
/*     */   {
/* 675 */     InetAddress host1 = socket.getInetAddress();
/* 676 */     InetAddress host2 = getRemoteAddress();
/*     */     
/* 678 */     return host1.equals(host2);
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
/*     */   public void setSocketFactory(SocketFactory factory)
/*     */   {
/* 693 */     if (factory == null) {
/* 694 */       this._socketFactory_ = __DEFAULT_SOCKET_FACTORY;
/*     */     } else {
/* 696 */       this._socketFactory_ = factory;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/* 701 */     this.connProxy = null;
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
/*     */   public void setServerSocketFactory(ServerSocketFactory factory)
/*     */   {
/* 714 */     if (factory == null) {
/* 715 */       this._serverSocketFactory_ = __DEFAULT_SERVER_SOCKET_FACTORY;
/*     */     } else {
/* 717 */       this._serverSocketFactory_ = factory;
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setConnectTimeout(int connectTimeout)
/*     */   {
/* 728 */     this.connectTimeout = connectTimeout;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getConnectTimeout()
/*     */   {
/* 737 */     return this.connectTimeout;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public ServerSocketFactory getServerSocketFactory()
/*     */   {
/* 746 */     return this._serverSocketFactory_;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void addProtocolCommandListener(ProtocolCommandListener listener)
/*     */   {
/* 757 */     getCommandSupport().addProtocolCommandListener(listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void removeProtocolCommandListener(ProtocolCommandListener listener)
/*     */   {
/* 767 */     getCommandSupport().removeProtocolCommandListener(listener);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void fireReplyReceived(int replyCode, String reply)
/*     */   {
/* 778 */     if (getCommandSupport().getListenerCount() > 0) {
/* 779 */       getCommandSupport().fireReplyReceived(replyCode, reply);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected void fireCommandSent(String command, String message)
/*     */   {
/* 791 */     if (getCommandSupport().getListenerCount() > 0) {
/* 792 */       getCommandSupport().fireCommandSent(command, message);
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   protected void createCommandSupport()
/*     */   {
/* 800 */     this.__commandSupport = new ProtocolCommandSupport(this);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   protected ProtocolCommandSupport getCommandSupport()
/*     */   {
/* 811 */     return this.__commandSupport;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setProxy(Proxy proxy)
/*     */   {
/* 823 */     setSocketFactory(new DefaultSocketFactory(proxy));
/* 824 */     this.connProxy = proxy;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public Proxy getProxy()
/*     */   {
/* 832 */     return this.connProxy;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getCharsetName()
/*     */   {
/* 843 */     return this.charset.name();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Charset getCharset()
/*     */   {
/* 853 */     return this.charset;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setCharset(Charset charset)
/*     */   {
/* 863 */     this.charset = charset;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\SocketClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */