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
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class CommandMaxCaps extends Command implements CommandWithGui
/*     */ {
/*     */   public CommandMaxCaps(ProSpam plugin)
/*     */   {
/*  20 */     super(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/*  26 */     return "max-caps";
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  32 */     return "Maximum percent of caps a word can have";
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getArgs()
/*     */   {
/*  38 */     return new String[] { "<percent>" };
/*     */   }
/*     */   
/*     */   public void execute(CommandSender sender, String[] parameter)
/*     */     throws IllegalArgumentException
/*     */   {
/*  44 */     if (isPlayer(sender))
/*     */     {
/*  46 */       Player player = (Player)sender;
/*     */       
/*  48 */       showGui(player);
/*     */     }
/*     */     else
/*     */     {
/*  52 */       if (parameter.length <= 1) {
/*  53 */         throw new IllegalArgumentException();
/*     */       }
/*     */       int maxCapsPercent;
/*     */       try
/*     */       {
/*  58 */         maxCapsPercent = Integer.parseInt(parameter[1]);
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/*  62 */         throw new IllegalArgumentException();
/*     */       }
/*     */       
/*  65 */       if ((maxCapsPercent < 0) || (maxCapsPercent > 100)) {
/*  66 */         throw new IllegalArgumentException();
/*     */       }
/*  68 */       this.settings.filter_caps_max = maxCapsPercent;
/*     */       
/*  70 */       sender.sendMessage(ProSpam.prefixed("Max. caps per word successfully set to " + maxCapsPercent + "%"));
/*     */       
/*  72 */       if (!this.settings.save()) {
/*  73 */         sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   public void showGui(Player player) {
/*  79 */     Set<Item> items = new java.util.HashSet();
/*  80 */     int valueFilterMayCaps = this.settings.filter_caps_max;
/*     */     
/*     */ 
/*  83 */     for (int i = 0; i <= 8; i++)
/*     */     {
/*  85 */       final int percent = (int)Math.floor(i * 12.5F);
/*     */       ItemStack itemStack;
/*     */       ItemStack itemStack;
/*  88 */       if (percent == valueFilterMayCaps) itemStack = new ItemStack(Material.ENDER_PEARL); else {
/*  89 */         itemStack = new ItemStack(Material.SLIME_BALL);
/*     */       }
/*  91 */       items.add(new Item(i, itemStack, percent + "%", "Max. " + percent + "% caps till lowercase", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/*  96 */           CommandMaxCaps.this.settings.filter_caps_max = percent;
/*     */           
/*  98 */           if (!CommandMaxCaps.this.settings.save())
/*     */           {
/* 100 */             player.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 105 */             CommandMaxCaps.this.showGui(player);
/* 106 */             player.sendMessage(ProSpam.prefixed("Max. caps per word successfully set to " + percent + "%"));
/*     */           }
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*     */ 
/* 113 */     for (int i = 9; i <= 17; i++)
/*     */     {
/* 115 */       items.add(Item.getSpacerItem(i));
/*     */     }
/*     */     
/*     */ 
/* 119 */     items.add(new Item(18, new ItemStack(Material.ENDER_PORTAL), "Back", "Back to the filter settings", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 124 */         CommandFilters commandFilters = new CommandFilters(CommandMaxCaps.this.plugin);
/* 125 */         commandFilters.showGui(player);
/*     */       }
/*     */       
/* 128 */     }));
/* 129 */     this.plugin.getGuiManager().openInventoryGui(player, "Max. caps till lowercase", items);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandMaxCaps.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */