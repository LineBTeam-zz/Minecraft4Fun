/*     */ package de.rob1n.prospam.cmd.specific;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import de.rob1n.prospam.cmd.Command;
/*     */ import de.rob1n.prospam.gui.Item;
/*     */ import de.rob1n.prospam.gui.Item.ClickAction;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public abstract class CommandTrigger extends Command
/*     */ {
/*     */   public CommandTrigger(ProSpam plugin)
/*     */   {
/*  20 */     super(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */   public abstract void saveInSettings(int paramInt, List<String> paramList);
/*     */   
/*     */   public abstract HashMap<Integer, List<String>> getTriggers();
/*     */   
/*     */   public void execute(CommandSender sender, String[] parameter)
/*     */     throws IllegalArgumentException
/*     */   {
/*  31 */     if ((isPlayer(sender)) && (parameter.length <= 1))
/*     */     {
/*  33 */       Player player = (Player)sender;
/*     */       
/*  35 */       CommandFilters commandFilters = new CommandFilters(this.plugin);
/*  36 */       commandFilters.showGui(player);
/*  37 */       return;
/*     */     }
/*     */     
/*  40 */     if (parameter.length <= 1) { throw new IllegalArgumentException();
/*     */     }
/*  42 */     int vNumber = 0;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/*  47 */       vNumber = Integer.parseInt(parameter[1]);
/*  48 */       parameter[1] = "";
/*     */     }
/*     */     catch (NumberFormatException ignored) {}
/*     */     
/*  52 */     if (vNumber < 0) { throw new IllegalArgumentException();
/*     */     }
/*  54 */     parameter[0] = "";
/*  55 */     List<String> cmds = getTriggerCmds(vNumber, parameter);
/*     */     
/*  57 */     saveInSettings(vNumber, cmds);
/*     */     
/*  59 */     if (cmds.size() > 0) sender.sendMessage(ProSpam.prefixed("Trigger successfully modified")); else {
/*  60 */       sender.sendMessage(ProSpam.prefixed("Trigger successfully removed"));
/*     */     }
/*  62 */     if (!this.settings.save()) sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*     */   }
/*     */   
/*     */   private List<String> getTriggerCmds(int vNumber, String[] params)
/*     */   {
/*  67 */     String paramString = org.apache.commons.lang.StringUtils.join(params, " ");
/*  68 */     String[] cmds = paramString.split(",");
/*     */     
/*  70 */     List<String> cmdList = (List)getTriggers().get(Integer.valueOf(vNumber));
/*     */     
/*  72 */     if (cmdList == null)
/*     */     {
/*  74 */       cmdList = new java.util.ArrayList();
/*     */     }
/*     */     
/*  77 */     if (paramString.trim().isEmpty()) { return cmdList;
/*     */     }
/*  79 */     for (String cmd : cmds)
/*     */     {
/*  81 */       cmd = cmd.trim();
/*     */       
/*  83 */       if (cmd.length() <= 0) { throw new IllegalArgumentException();
/*     */       }
/*  85 */       if (cmd.charAt(0) != '/') cmdList.add("/" + cmd); else {
/*  86 */         cmdList.add(cmd);
/*     */       }
/*     */     }
/*  89 */     return cmdList;
/*     */   }
/*     */   
/*     */   public void showGui(Player player, final String filter, final HashMap<Integer, List<String>> triggers)
/*     */   {
/*  94 */     Set<Item> items = new java.util.HashSet();
/*     */     
/*  96 */     for (int i = 0; i < 9; i += 2)
/*     */     {
/*  98 */       final int violationCount = i / 2 + 1 >= 5 ? 0 : i / 2 + 1;
/*     */       
/* 100 */       if (violationCount == 0)
/*     */       {
/* 102 */         items.add(new Item(i, new ItemStack(Material.SKULL_ITEM), "EVERY OTHER time", "Executed on all other occasions", Item.NO_CLICK_ACTION));
/*     */       }
/*     */       else
/*     */       {
/* 106 */         items.add(new Item(i, new ItemStack(Material.SKULL_ITEM), "At " + violationCount + ". time", "Executed on " + violationCount + ". violation", Item.NO_CLICK_ACTION));
/*     */       }
/*     */       
/* 109 */       items.add(new Item(i + 18, new ItemStack(Material.BOOK_AND_QUILL), "Edit commands", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 114 */           CommandTrigger.this.showEditCommandsGui(player, filter, triggers, violationCount);
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/* 119 */     for (int i = 1; i < 8; i += 2)
/*     */     {
/* 121 */       items.add(Item.getSpacerItem(i));
/* 122 */       items.add(Item.getSpacerItem(i + 9));
/* 123 */       items.add(Item.getSpacerItem(i + 18));
/*     */     }
/*     */     
/*     */ 
/* 127 */     items.add(new Item(27, new ItemStack(Material.ENDER_PORTAL), "Back to filter actions", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 132 */         CommandFilters commandFilters = new CommandFilters(CommandTrigger.this.plugin);
/* 133 */         commandFilters.showGui(player);
/*     */       }
/*     */       
/* 136 */     }));
/* 137 */     this.plugin.getGuiManager().openInventoryGui(player, "Actions - " + filter + " spam", items);
/*     */   }
/*     */   
/*     */   private void showEditCommandsGui(Player player, final String filter, final HashMap<Integer, List<String>> triggers, final int violationKey)
/*     */   {
/* 142 */     Set<Item> items = new java.util.HashSet();
/*     */     int iter;
/* 144 */     if (triggers.get(Integer.valueOf(violationKey)) != null)
/*     */     {
/* 146 */       List<String> cmds = (List)triggers.get(Integer.valueOf(violationKey));
/* 147 */       iter = 0;
/*     */       
/* 149 */       for (final String cmd : cmds)
/*     */       {
/*     */ 
/* 152 */         if (iter >= 26) {
/*     */           break;
/*     */         }
/*     */         
/*     */ 
/* 157 */         items.add(new Item(iter++, new ItemStack(Material.RECORD_8), cmd, "Click to remove", new Item.ClickAction()
/*     */         {
/*     */ 
/*     */           public void onClick(Player player)
/*     */           {
/* 162 */             ((List)triggers.get(Integer.valueOf(violationKey))).remove(cmd);
/*     */             
/* 164 */             CommandTrigger.this.settings.save();
/*     */             
/* 166 */             CommandTrigger.this.showEditCommandsGui(player, filter, triggers, violationKey);
/*     */           }
/*     */         }));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 173 */     items.add(new Item(35, new ItemStack(Material.NETHER_STAR), "Add command", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 178 */         player.closeInventory();
/*     */         
/* 180 */         player.sendMessage(ProSpam.prefixed("Add a command, executed if a player violates the " + filter + " filter the " + violationKey + ". time"));
/* 181 */         player.sendMessage(ProSpam.prefixed("Type " + ChatColor.AQUA + "/prospam " + CommandTrigger.this.getAliases()[0] + " " + violationKey + " <command>"));
/* 182 */         player.sendMessage(ProSpam.prefixed(ChatColor.GRAY + "Example: " + ChatColor.ITALIC + "/prospam " + CommandTrigger.this.getAliases()[0] + " " + violationKey + " /raw Hey {u}, No Spam!"));
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/*     */       
/*     */ 
/*     */ 
/*     */ 
/* 191 */     }));
/* 192 */     items.add(new Item(27, new ItemStack(Material.ENDER_PORTAL), "Back to filter actions", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 197 */         CommandTrigger.this.showGui(player, filter, triggers);
/*     */       }
/*     */       
/* 200 */     }));
/* 201 */     this.plugin.getGuiManager().openInventoryGui(player, "Edit - " + filter + " commands", items);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandTrigger.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */