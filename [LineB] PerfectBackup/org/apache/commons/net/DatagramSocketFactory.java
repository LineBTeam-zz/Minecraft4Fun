package org.apache.commons.net;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public abstract interface DatagramSocketFactory
{
  public abstract DatagramSocket createDatagramSocket()
    throws SocketException;
  
  public abstract DatagramSocket createDatagramSocket(int paramInt)
    throws SocketException;
  
  public abstract DatagramSocket createDatagramSocket(int paramInt, InetAddress paramInetAddress)
    throws SocketException;
}


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\DatagramSocketFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */