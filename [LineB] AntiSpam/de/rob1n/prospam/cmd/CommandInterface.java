package de.rob1n.prospam.cmd;

import org.bukkit.command.CommandSender;

public abstract interface CommandInterface
{
  public abstract String getName();
  
  public abstract String getDescription();
  
  public abstract String[] getArgs();
  
  public abstract String[] getAliases();
  
  public abstract void execute(CommandSender paramCommandSender, String[] paramArrayOfString)
    throws IllegalArgumentException;
}


