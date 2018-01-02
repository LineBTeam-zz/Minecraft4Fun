/*     */ package de.rob1n.prospam.cmd.specific;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import de.rob1n.prospam.cmd.Command;
/*     */ import de.rob1n.prospam.cmd.CommandList;
/*     */ import de.rob1n.prospam.data.specific.Settings;
/*     */ import de.rob1n.prospam.gui.Item;
/*     */ import de.rob1n.prospam.gui.Item.ClickAction;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.lang.StringUtils;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.DyeColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ 
/*     */ public class CommandHelp extends Command implements de.rob1n.prospam.cmd.CommandWithGui
/*     */ {
/*     */   public CommandHelp(ProSpam plugin)
/*     */   {
/*  23 */     super(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/*  29 */     return "help";
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  35 */     return "How to use the plugin";
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getArgs()
/*     */   {
/*  41 */     return new String[] { "" };
/*     */   }
/*     */   
/*     */ 
/*     */   public void execute(CommandSender sender, String[] parameter)
/*     */   {
/*  47 */     CommandList cmdList = this.plugin.getCommandHandler().getCommandList();
/*     */     
/*  49 */     if (isPlayer(sender))
/*     */     {
/*  51 */       Player player = (Player)sender;
/*     */       
/*  53 */       showGui(player);
/*     */     }
/*     */     else
/*     */     {
/*  57 */       sender.sendMessage(ProSpam.prefixed("[Version: " + this.plugin.getDescription().getVersion() + " by prodaim] " + "List of Commands"));
/*  58 */       for (Command cmd : cmdList)
/*     */       {
/*  60 */         sender.sendMessage(ChatColor.GRAY + "/prospam " + ChatColor.LIGHT_PURPLE + StringUtils.join(cmd.getArgs(), " "));
/*  61 */         sender.sendMessage(ChatColor.ITALIC + cmd.getDescription() + ChatColor.RESET);
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void showGui(Player player)
/*     */   {
/*  70 */     Set<Item> items = new java.util.HashSet();
/*     */     
/*     */ 
/*  73 */     if (this.settings.enabled)
/*     */     {
/*  75 */       items.add(new Item(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.GREEN.getData()), "ProSpam is enabled", Item.NO_CLICK_ACTION));
/*  76 */       items.add(new Item(1, new ItemStack(Material.LEVER), "Disable ProSpam", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/*  81 */           CommandDisable commandDisable = new CommandDisable(CommandHelp.this.plugin);
/*  82 */           commandDisable.execute(player, new String[0]);
/*     */           
/*     */ 
/*  85 */           CommandHelp.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     else
/*     */     {
/*  91 */       items.add(new Item(0, new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)DyeColor.RED.getData()), "ProSpam is disabled", Item.NO_CLICK_ACTION));
/*  92 */       items.add(new Item(1, new ItemStack(Material.LEVER), "Enable ProSpam", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/*  97 */           CommandEnable commandEnable = new CommandEnable(CommandHelp.this.plugin);
/*  98 */           commandEnable.execute(player, new String[0]);
/*     */           
/*     */ 
/* 101 */           CommandHelp.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*     */ 
/* 107 */     items.add(new Item(3, new ItemStack(Material.PAPER), "Reload Config", "Reloads the settings from config.yml", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 112 */         CommandReload commandReload = new CommandReload(CommandHelp.this.plugin);
/* 113 */         commandReload.execute(player, new String[0]);
/*     */         
/*     */ 
/* 116 */         CommandHelp.this.showGui(player);
/*     */       }
/*     */     }));
/*     */     
/*     */ 
/* 121 */     for (int i = 9; i <= 17; i++)
/*     */     {
/* 123 */       items.add(Item.getSpacerItem(i));
/*     */     }
/*     */     
/*     */ 
/* 127 */     items.add(new Item(18, new ItemStack(Material.BOOK_AND_QUILL), "Manage filters", "All the filter settings", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 132 */         CommandFilters commandFilters = new CommandFilters(CommandHelp.this.plugin);
/* 133 */         commandFilters.showGui(player);
/*     */       }
/*     */       
/*     */ 
/* 137 */     }));
/* 138 */     items.add(new Item(24, new ItemStack(Material.SKULL_ITEM, 1, (short)3), "Player spam stats", "Shows spam stats for each player", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 143 */         CommandCounter commandCounter = new CommandCounter(CommandHelp.this.plugin);
/* 144 */         commandCounter.showGui(player);
/*     */       }
/*     */     }));
/*     */     
/*     */ 
/* 149 */     if (this.settings.whitelist_enabled)
/*     */     {
/* 151 */       items.add(new Item(26, new ItemStack(Material.MAP), "Disable whitelist", "Click to disable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 156 */           CommandWhitelistDisable commandWhitelistDisable = new CommandWhitelistDisable(CommandHelp.this.plugin);
/* 157 */           commandWhitelistDisable.execute(player, new String[0]);
/*     */           
/*     */ 
/* 160 */           CommandHelp.this.showGui(player);
/*     */         }
/*     */         
/*     */ 
/*     */       }));
/*     */     } else {
/* 166 */       items.add(new Item(26, new ItemStack(Material.EMPTY_MAP), "Enable whitelist", "Click to enable", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 171 */           CommandWhitelistEnable commandWhitelistEnable = new CommandWhitelistEnable(CommandHelp.this.plugin);
/* 172 */           commandWhitelistEnable.execute(player, new String[0]);
/*     */           
/*     */ 
/* 175 */           CommandHelp.this.showGui(player);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/* 180 */     this.plugin.getGuiManager().openInventoryGui(player, "ProSpam by prodaim. Commands", items);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandHelp.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */