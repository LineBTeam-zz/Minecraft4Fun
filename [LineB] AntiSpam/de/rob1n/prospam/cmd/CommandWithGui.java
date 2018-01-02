package de.rob1n.prospam.cmd;

import org.bukkit.entity.Player;

public abstract interface CommandWithGui
  extends CommandInterface
{
  public abstract void showGui(Player paramPlayer);
}


