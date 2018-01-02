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
/*     */ public class CommandLinesSimilar extends Command implements CommandWithGui
/*     */ {
/*     */   public CommandLinesSimilar(ProSpam plugin)
/*     */   {
/*  20 */     super(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */   public String getName()
/*     */   {
/*  26 */     return "lines-similar";
/*     */   }
/*     */   
/*     */ 
/*     */   public String getDescription()
/*     */   {
/*  32 */     return "Timespan players are forbidden to post a similar chatline";
/*     */   }
/*     */   
/*     */ 
/*     */   public String[] getArgs()
/*     */   {
/*  38 */     return new String[] { "<seconds>" };
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
/*     */       int similarLockTime;
/*     */       try
/*     */       {
/*  58 */         similarLockTime = Integer.parseInt(parameter[1]);
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/*  62 */         throw new IllegalArgumentException();
/*     */       }
/*     */       
/*  65 */       if (similarLockTime < 0) {
/*  66 */         throw new IllegalArgumentException();
/*     */       }
/*  68 */       this.settings.filter_lines_similar = similarLockTime;
/*     */       
/*  70 */       if (!this.settings.save())
/*     */       {
/*  72 */         sender.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*     */       }
/*     */       else
/*     */       {
/*  76 */         sender.sendMessage(ProSpam.prefixed("Timespan successfully set to " + similarLockTime + " seconds"));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   public void showGui(Player player)
/*     */   {
/*  84 */     Set<Item> items = new java.util.HashSet();
/*  85 */     int valueFilterLinesSimilar = this.settings.filter_lines_similar;
/*     */     
/*     */ 
/*  88 */     for (int i = 0; i <= 8; i++)
/*     */     {
/*  90 */       final int secs = i * (i * 2);
/*     */       ItemStack itemStack;
/*     */       ItemStack itemStack;
/*  93 */       if (secs == valueFilterLinesSimilar) itemStack = new ItemStack(Material.ENDER_PEARL); else {
/*  94 */         itemStack = new ItemStack(Material.SLIME_BALL);
/*     */       }
/*  96 */       items.add(new Item(i, itemStack, secs + " seconds", "Time between similar msgs", new Item.ClickAction()
/*     */       {
/*     */ 
/*     */         public void onClick(Player player)
/*     */         {
/* 101 */           CommandLinesSimilar.this.settings.filter_lines_similar = secs;
/*     */           
/* 103 */           if (!CommandLinesSimilar.this.settings.save())
/*     */           {
/* 105 */             player.sendMessage(ProSpam.prefixed("Could not save state in the config file!"));
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 110 */             CommandLinesSimilar.this.showGui(player);
/*     */             
/* 112 */             player.sendMessage(ProSpam.prefixed("Timespan successfully set to " + secs + " seconds"));
/*     */           }
/*     */         }
/*     */       }));
/*     */     }
/*     */     
/*     */ 
/* 119 */     for (int i = 9; i <= 17; i++)
/*     */     {
/* 121 */       items.add(Item.getSpacerItem(i));
/*     */     }
/*     */     
/*     */ 
/* 125 */     items.add(new Item(18, new ItemStack(Material.ENDER_PORTAL), "Back", "Back to the filter settings", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 130 */         CommandFilters commandFilters = new CommandFilters(CommandLinesSimilar.this.plugin);
/* 131 */         commandFilters.showGui(player);
/*     */       }
/*     */       
/* 134 */     }));
/* 135 */     this.plugin.getGuiManager().openInventoryGui(player, "Similar settings", items);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandLinesSimilar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */