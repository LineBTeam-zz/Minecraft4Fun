package org.apache.commons.net.ftp.parser;

import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFileEntryParser;

public abstract interface FTPFileEntryParserFactory
{
  public abstract FTPFileEntryParser createFileEntryParser(String paramString)
    throws ParserInitializationException;
  
  public abstract FTPFileEntryParser createFileEntryParser(FTPClientConfig paramFTPClientConfig)
    throws ParserInitializationException;
}


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\parser\FTPFileEntryParserFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */