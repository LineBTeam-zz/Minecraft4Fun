/*     */ package de.rob1n.prospam.cmd.specific;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import de.rob1n.prospam.cmd.Command;
/*     */ import de.rob1n.prospam.cmd.CommandWithGui;
/*     */ import de.rob1n.prospam.data.specific.Settings;
/*     */ import de.rob1n.prospam.gui.GuiManager;
/*     */ import de.rob1n.prospam.gui.Item;
/*     */ import de.rob1n.prospam.gui.Item.ClickAction;
/*     */ import java.util.Set;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class CommandFilters extends Command implements CommandWithGui
/*     */ {
/*     */   public CommandFilters(ProSpam plugin)
/*     */   {
/*  22 */     super(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/*  28 */     return "filters";
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  34 */     return "Display filter states";
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getArgs()
/*     */   {
/*  40 */     return new String[] { "" };
/*     */   }
/*     */   
/*     */   public void execute(CommandSender sender, String[] parameter)
/*     */     throws IllegalArgumentException
/*     */   {
/*  46 */     if (isPlayer(sender))
/*     */     {
/*  48 */       Player player = (Player)sender;
/*     */       
/*  50 */       showGui(player);
/*     */     }
/*     */     else
/*     */     {
/*  54 */       sender.sendMessage(ProSpam.prefixed("Filter states"));
/*  55 */       sender.sendMessage("|  Caps Filter" + ChatColor.GRAY + " is " + ChatColor.RESET + (this.settings.filter_enabled_caps ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()));
/*  56 */       sender.sendMessage("|  Char Filter" + ChatColor.GRAY + " is " + ChatColor.RESET + (this.settings.filter_enabled_chars ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()));
/*  57 */       sender.sendMessage("|  Flood Filter" + ChatColor.GRAY + " is " + ChatColor.RESET + (this.settings.filter_enabled_flood ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()));
/*  58 */       sender.sendMessage("|  Similar Filter" + ChatColor.GRAY + " is " + ChatColor.RESET + (this.settings.filter_enabled_similar ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()));
/*  59 */       sender.sendMessage("|  Url Filter" + ChatColor.GRAY + " is " + ChatColor.RESET + (this.settings.filter_enabled_urls ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()));
/*  60 */       sender.sendMessage("|  Blacklist Filter" + ChatColor.GRAY + " is " + ChatColor.RESET + (this.settings.filter_enabled_blacklist ? ChatColor.DARK_GREEN + "enabled" : new StringBuilder().append(ChatColor.DARK_RED).append("disabled").toString()));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showGui(Player player)
/*     */   {
/*  67 */     Set<Item> items = new java.util.HashSet();
/*  68 */     final CommandFilterEnable commandFilterEnable = new CommandFilterEnable(this.plugin);
/*  69 */     final CommandFilterDisable commandFilterDisable = new CommandFilterDisable(this.plugin);
/*  70 */     final CommandTriggerEnable commandTriggerEnable = new CommandTriggerEnable(this.plugin);
/*  71 */     final CommandTriggerDisable commandTriggerDisable = new CommandTriggerDisable(this.plugin);
/*     */     
/*     */ 
/*  74 */     if (this.settings.filter_enabled_caps)
/*     */     {
/*  76 */       items.add(new Item(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "Caps filter", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/*  81 */           commandFilterDisable.execute(player, new String[] { "", "caps" });
/*     */           
/*     */ 
/*  84 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/*  90 */       items.add(new Item(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "Caps filter", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/*  95 */           commandFilterEnable.execute(player, new String[] { "", "caps" });
/*     */           
/*     */ 
/*  98 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*     */ 
/* 104 */     if (this.settings.filter_enabled_chars)
/*     */     {
/* 106 */       items.add(new Item(1, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "Chars filter", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 111 */           commandFilterDisable.execute(player, new String[] { "", "chars" });
/*     */           
/*     */ 
/* 114 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 120 */       items.add(new Item(1, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "Chars filter", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 125 */           commandFilterEnable.execute(player, new String[] { "", "chars" });
/*     */           
/*     */ 
/* 128 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/* 133 */     items.add(Item.getSpacerItem(2));
/*     */     
/*     */ 
/* 136 */     if (this.settings.filter_enabled_flood)
/*     */     {
/* 138 */       items.add(new Item(3, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "Flood filter", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 143 */           commandFilterDisable.execute(player, new String[] { "", "flood" });
/*     */           
/*     */ 
/* 146 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 152 */       items.add(new Item(3, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "Flood filter", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 157 */           commandFilterEnable.execute(player, new String[] { "", "flood" });
/*     */           
/*     */ 
/* 160 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*     */ 
/* 166 */     if (this.settings.filter_enabled_similar)
/*     */     {
/* 168 */       items.add(new Item(4, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "Similar filter", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 173 */           commandFilterDisable.execute(player, new String[] { "", "similar" });
/*     */           
/*     */ 
/* 176 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 182 */       items.add(new Item(4, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "Similar filter", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 187 */           commandFilterEnable.execute(player, new String[] { "", "similar" });
/*     */           
/*     */ 
/* 190 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/* 195 */     items.add(Item.getSpacerItem(5));
/* 196 */     items.add(Item.getSpacerItem(6));
/*     */     
/*     */ 
/* 199 */     if (this.settings.filter_enabled_urls)
/*     */     {
/* 201 */       items.add(new Item(7, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "URL filter", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 206 */           commandFilterDisable.execute(player, new String[] { "", "urls" });
/*     */           
/*     */ 
/* 209 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 215 */       items.add(new Item(7, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "URL filter", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 220 */           commandFilterEnable.execute(player, new String[] { "", "urls" });
/*     */           
/*     */ 
/* 223 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*     */ 
/* 229 */     if (this.settings.filter_enabled_blacklist)
/*     */     {
/* 231 */       items.add(new Item(8, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "Blacklist filter", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 236 */           commandFilterDisable.execute(player, new String[] { "", "blacklist" });
/*     */           
/*     */ 
/* 239 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 245 */       items.add(new Item(8, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "Blacklist filter", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 250 */           commandFilterEnable.execute(player, new String[] { "", "blacklist" });
/*     */           
/*     */ 
/* 253 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*     */ 
/* 259 */     items.add(new Item(9, new ItemStack(Material.PAPER), "Max. % caps per word", "if more % -> toLowercase", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 264 */         CommandMaxCaps commandMaxCaps = new CommandMaxCaps(CommandFilters.this.plugin);
/* 265 */         commandMaxCaps.showGui(player);
/*     */       }
/*     */       
/* 268 */     }));
/* 269 */     items.add(Item.getSpacerItem(11));
/*     */     
/*     */ 
/* 272 */     items.add(new Item(12, new ItemStack(Material.PAPER), "Min. time between messages", "time < min, text gets ignored", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 277 */         CommandLinesLocked commandLinesLocked = new CommandLinesLocked(CommandFilters.this.plugin);
/* 278 */         commandLinesLocked.showGui(player);
/*     */       }
/*     */       
/*     */ 
/* 282 */     }));
/* 283 */     items.add(new Item(13, new ItemStack(Material.PAPER), "Time between similar messages", "if similar, text gets ignored", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 288 */         CommandLinesSimilar commandLinesSimilar = new CommandLinesSimilar(CommandFilters.this.plugin);
/* 289 */         commandLinesSimilar.showGui(player);
/*     */       }
/*     */       
/* 292 */     }));
/* 293 */     items.add(Item.getSpacerItem(14));
/* 294 */     items.add(Item.getSpacerItem(15));
/*     */     
/*     */ 
/* 297 */     if (this.settings.trigger_enabled_caps)
/*     */     {
/* 299 */       items.add(new Item(18, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "Caps actions", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 304 */           commandTriggerDisable.execute(player, new String[] { "", "caps" });
/*     */           
/*     */ 
/* 307 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 313 */       items.add(new Item(18, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "Caps actions", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 318 */           commandTriggerEnable.execute(player, new String[] { "", "caps" });
/*     */           
/*     */ 
/* 321 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*     */ 
/* 327 */     if (this.settings.trigger_enabled_chars)
/*     */     {
/* 329 */       items.add(new Item(19, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "Chars actions", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 334 */           commandTriggerDisable.execute(player, new String[] { "", "chars" });
/*     */           
/*     */ 
/* 337 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 343 */       items.add(new Item(19, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "Chars actions", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 348 */           commandTriggerEnable.execute(player, new String[] { "", "chars" });
/*     */           
/*     */ 
/* 351 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/* 356 */     items.add(Item.getSpacerItem(20));
/*     */     
/*     */ 
/* 359 */     if (this.settings.trigger_enabled_flood)
/*     */     {
/* 361 */       items.add(new Item(21, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "Flood actions", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 366 */           commandTriggerDisable.execute(player, new String[] { "", "flood" });
/*     */           
/*     */ 
/* 369 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 375 */       items.add(new Item(21, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "Flood actions", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 380 */           commandTriggerEnable.execute(player, new String[] { "", "flood" });
/*     */           
/*     */ 
/* 383 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*     */ 
/* 389 */     if (this.settings.trigger_enabled_similar)
/*     */     {
/* 391 */       items.add(new Item(22, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "Similar txt actions", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 396 */           commandTriggerDisable.execute(player, new String[] { "", "similar" });
/*     */           
/*     */ 
/* 399 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 405 */       items.add(new Item(22, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "Similar txt actions", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 410 */           commandTriggerEnable.execute(player, new String[] { "", "similar" });
/*     */           
/*     */ 
/* 413 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/* 418 */     items.add(Item.getSpacerItem(23));
/* 419 */     items.add(Item.getSpacerItem(24));
/*     */     
/*     */ 
/* 422 */     if (this.settings.trigger_enabled_urls)
/*     */     {
/* 424 */       items.add(new Item(25, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "URL actions", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 429 */           commandTriggerDisable.execute(player, new String[] { "", "urls" });
/*     */           
/*     */ 
/* 432 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 438 */       items.add(new Item(25, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "URL actions", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 443 */           commandTriggerEnable.execute(player, new String[] { "", "urls" });
/*     */           
/*     */ 
/* 446 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*     */ 
/* 452 */     if (this.settings.trigger_enabled_blacklist)
/*     */     {
/* 454 */       items.add(new Item(26, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "Blacklist actions", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 459 */           commandTriggerDisable.execute(player, new String[] { "", "blacklist" });
/*     */           
/*     */ 
/* 462 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 468 */       items.add(new Item(26, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "Blacklist actions", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 473 */           commandTriggerEnable.execute(player, new String[] { "", "blacklist" });
/*     */           
/*     */ 
/* 476 */           CommandFilters.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*     */ 
/* 482 */     items.add(new Item(27, new ItemStack(Material.WRITTEN_BOOK), "Actions on caps spam", "Click to edit", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 487 */         CommandTriggerCaps commandTriggerCaps = new CommandTriggerCaps(CommandFilters.this.plugin);
/* 488 */         commandTriggerCaps.showGui(player);
/*     */       }
/*     */       
/*     */ 
/* 492 */     }));
/* 493 */     items.add(new Item(28, new ItemStack(Material.WRITTEN_BOOK), "Actions on char spam", "Click to edit", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 498 */         CommandTriggerChars commandTriggerChars = new CommandTriggerChars(CommandFilters.this.plugin);
/* 499 */         commandTriggerChars.showGui(player);
/*     */       }
/*     */       
/* 502 */     }));
/* 503 */     items.add(Item.getSpacerItem(29));
/*     */     
/*     */ 
/* 506 */     items.add(new Item(30, new ItemStack(Material.WRITTEN_BOOK), "Actions on flood spam", "Click to edit", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 511 */         CommandTriggerFlood commandTriggerFlood = new CommandTriggerFlood(CommandFilters.this.plugin);
/* 512 */         commandTriggerFlood.showGui(player);
/*     */       }
/*     */       
/*     */ 
/* 516 */     }));
/* 517 */     items.add(new Item(31, new ItemStack(Material.WRITTEN_BOOK), "Actions on similar spam", "Click to edit", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 522 */         CommandTriggerSimilar commandTriggerSimilar = new CommandTriggerSimilar(CommandFilters.this.plugin);
/* 523 */         commandTriggerSimilar.showGui(player);
/*     */       }
/*     */       
/* 526 */     }));
/* 527 */     items.add(Item.getSpacerItem(32));
/* 528 */     items.add(Item.getSpacerItem(33));
/*     */     
/*     */ 
/* 531 */     items.add(new Item(34, new ItemStack(Material.WRITTEN_BOOK), "Actions on URL spam", "Click to edit", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 536 */         CommandTriggerUrls commandTriggerUrls = new CommandTriggerUrls(CommandFilters.this.plugin);
/* 537 */         commandTriggerUrls.showGui(player);
/*     */       }
/*     */       
/*     */ 
/* 541 */     }));
/* 542 */     items.add(new Item(35, new ItemStack(Material.WRITTEN_BOOK), "Actions on blacklist spam", "Click to edit", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 547 */         CommandTriggerBlacklist commandTriggerBlacklist = new CommandTriggerBlacklist(CommandFilters.this.plugin);
/* 548 */         commandTriggerBlacklist.showGui(player);
/*     */       }
/*     */       
/* 551 */     }));
/* 552 */     this.plugin.getGuiManager().openInventoryGui(player, "Spamfilter Management", items);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandFilters.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */