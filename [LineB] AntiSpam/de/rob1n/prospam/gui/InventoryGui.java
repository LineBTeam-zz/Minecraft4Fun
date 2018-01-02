/*     */ package de.rob1n.prospam.gui;
/*     */ 
/*     */ import de.rob1n.prospam.ProSpam;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ 
/*     */ public class InventoryGui
/*     */ {
/*     */   public static final int MAX_ITEMS = 36;
/*     */   public static final int MAX_PAGES = 20;
/*     */   public static final int NAVIGATION_START = 36;
/*     */   private String id;
/*  26 */   private Map<Integer, Set<Item>> itemMap = new HashMap();
/*  27 */   private List<ItemStack[]> inventories = new ArrayList();
/*     */   private Inventory inventory;
/*     */   private int activePage;
/*     */   private Item navigatePrevious;
/*     */   private Item navigateNext;
/*     */   
/*     */   public InventoryGui(String name, Set<Item> items) {
/*  34 */     this.id = HiddenId.getNext();
/*  35 */     this.activePage = 0;
/*     */     
/*     */ 
/*  38 */     if (name.length() + this.id.length() > 32)
/*     */     {
/*  40 */       name = name.substring(0, 32 - this.id.length());
/*     */     }
/*     */     
/*  43 */     this.inventory = Bukkit.createInventory(null, 45, name + this.id);
/*     */     
/*  45 */     initInventories(items);
/*  46 */     fillInventories(items);
/*     */     
/*  48 */     initNavigationItems();
/*  49 */     addNavigationItemsToInventories();
/*     */   }
/*     */   
/*     */   public void open(Player player)
/*     */   {
/*  54 */     open(0);
/*  55 */     player.openInventory(this.inventory);
/*     */   }
/*     */   
/*     */   private void open(int page)
/*     */   {
/*  60 */     ItemStack[] itemStacks = (ItemStack[])this.inventories.get(page);
/*     */     
/*  62 */     this.inventory.clear();
/*  63 */     int i = 0; for (int n = itemStacks.length; i < n; i++)
/*     */     {
/*  65 */       this.inventory.setItem(i, itemStacks[i]);
/*     */     }
/*     */   }
/*     */   
/*     */   public String getId()
/*     */   {
/*  71 */     return this.id;
/*     */   }
/*     */   
/*     */ 
/*     */   public Item.ClickAction getClickAction(int slot)
/*     */   {
/*  77 */     Item.ClickAction dummyClickAction = new Item.ClickAction()
/*     */     {
/*     */       public void onClick(Player player) {}
/*     */     };
/*     */     
/*     */ 
/*     */     Set<Item> items;
/*     */     
/*     */ 
/*     */     try
/*     */     {
/*  88 */       items = (Set)this.itemMap.get(Integer.valueOf(this.activePage));
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  92 */       ProSpam.log(Level.WARNING, "Couldn't get ClickAction: " + e.getMessage());
/*  93 */       return dummyClickAction;
/*     */     }
/*     */     
/*  96 */     for (Item item : items)
/*     */     {
/*  98 */       if (item.getSlot() == slot) {
/*  99 */         return item.getClickAction();
/*     */       }
/*     */     }
/* 102 */     return dummyClickAction;
/*     */   }
/*     */   
/*     */   private void initNavigationItems()
/*     */   {
/* 107 */     ItemStack itemStack = new ItemStack(Material.BOOK);
/* 108 */     ItemMeta itemMeta = itemStack.getItemMeta();
/* 109 */     itemMeta.addEnchant(Enchantment.SILK_TOUCH, 1, true);
/* 110 */     itemStack.setItemMeta(itemMeta);
/*     */     
/* 112 */     this.navigatePrevious = new Item(36, itemStack, "Vorherige Seite", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 117 */         int previousPage = InventoryGui.this.activePage - 1;
/* 118 */         if (previousPage >= 0)
/*     */         {
/* 120 */           InventoryGui.this.activePage = previousPage;
/* 121 */           InventoryGui.this.open(InventoryGui.this.activePage);
/*     */         }
/*     */         
/*     */       }
/* 125 */     });
/* 126 */     itemStack = new ItemStack(itemStack);
/* 127 */     this.navigateNext = new Item(44, itemStack, "Nächste Seite", new Item.ClickAction()
/*     */     {
/*     */ 
/*     */       public void onClick(Player player)
/*     */       {
/* 132 */         int nextPage = InventoryGui.this.activePage + 1;
/* 133 */         if (nextPage <= InventoryGui.this.inventories.size() - 1)
/*     */         {
/* 135 */           InventoryGui.this.activePage = nextPage;
/* 136 */           InventoryGui.this.open(InventoryGui.this.activePage);
/*     */         }
/*     */       }
/*     */     });
/*     */   }
/*     */   
/*     */   private void initInventories(Set<Item> items)
/*     */   {
/* 144 */     int max = 0;
/*     */     
/* 146 */     for (Item item : items)
/*     */     {
/* 148 */       if (item.getSlot() > max) {
/* 149 */         max = item.getSlot();
/*     */       }
/*     */     }
/* 152 */     int i = 0; for (int m = max / 36; i <= m; i++)
/*     */     {
/* 154 */       if (i > 20) {
/*     */         break;
/*     */       }
/* 157 */       this.inventories.add(new ItemStack[45]);
/* 158 */       this.itemMap.put(Integer.valueOf(i), new HashSet());
/*     */     }
/*     */   }
/*     */   
/*     */   private void fillInventories(Set<Item> items)
/*     */   {
/* 164 */     for (Item item : items)
/*     */     {
/* 166 */       if ((item.getSlot() < 0) || (item.getSlot() > 720)) {
/*     */         break;
/*     */       }
/* 169 */       int invNumber = item.getSlot() / 36;
/* 170 */       int slotNumber = item.getSlot() % 36;
/*     */       
/*     */       try
/*     */       {
/* 174 */         ((Set)this.itemMap.get(Integer.valueOf(invNumber))).add(item);
/* 175 */         ((ItemStack[])this.inventories.get(invNumber))[slotNumber] = item.getItemStack();
/*     */       }
/*     */       catch (Exception e)
/*     */       {
/* 179 */         ProSpam.log(Level.WARNING, "Error while filling up the GUI: " + e.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private void addNavigationItemsToInventories()
/*     */   {
/* 186 */     int page = 0;
/* 187 */     for (ItemStack[] inventory : this.inventories)
/*     */     {
/* 189 */       if (page > 0)
/*     */       {
/* 191 */         inventory[this.navigatePrevious.getSlot()] = this.navigatePrevious.getItemStack();
/* 192 */         ((Set)this.itemMap.get(Integer.valueOf(page))).add(this.navigatePrevious);
/*     */       }
/*     */       
/* 195 */       if (page < this.inventories.size() - 1)
/*     */       {
/* 197 */         inventory[this.navigateNext.getSlot()] = this.navigateNext.getItemStack();
/* 198 */         ((Set)this.itemMap.get(Integer.valueOf(page))).add(this.navigateNext);
/*     */       }
/*     */       
/* 201 */       page++;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\gui\InventoryGui.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */