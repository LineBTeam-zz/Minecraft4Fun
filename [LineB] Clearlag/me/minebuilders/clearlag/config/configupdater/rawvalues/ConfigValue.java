package me.minebuilders.clearlag.config.configupdater.rawvalues;

import java.io.BufferedWriter;
import java.io.IOException;

public abstract interface ConfigValue
{
  public abstract String getKey();
  
  public abstract Object getValue();
  
  public abstract void merge(ConfigValue paramConfigValue);
  
  public abstract void writeToFile(BufferedWriter paramBufferedWriter)
    throws IOException;
}


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\config\configupdater\rawvalues\ConfigValue.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */