/*    */ package me.minebuilders.clearlag.removetype;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import me.minebuilders.clearlag.annotations.ConfigValue;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.Item;
/*    */ import org.bukkit.entity.Projectile;
/*    */ 
/*    */ @me.minebuilders.clearlag.annotations.ConfigPath(path="limit")
/*    */ public class LimitClear extends me.minebuilders.clearlag.modules.ClearModule
/*    */ {
/*    */   @ConfigValue
/*    */   private boolean item;
/*    */   @ConfigValue
/* 17 */   private List<Integer> itemFilter = new ArrayList();
/*    */   @ConfigValue
/* 19 */   private List<String> worldFilter = new ArrayList();
/*    */   
/*    */   @ConfigValue
/*    */   private boolean itemframe;
/*    */   
/*    */   @ConfigValue
/*    */   private boolean fallingBlock;
/*    */   @ConfigValue
/*    */   private boolean boat;
/*    */   @ConfigValue
/*    */   private boolean experienceOrb;
/*    */   @ConfigValue
/*    */   private boolean painting;
/*    */   @ConfigValue
/*    */   private boolean projectile;
/*    */   @ConfigValue
/*    */   private boolean primedTnt;
/*    */   @ConfigValue
/*    */   private boolean minecart;
/*    */   
/*    */   public boolean isRemovable(Entity e)
/*    */   {
/* 41 */     if ((e instanceof Item)) return (this.item) && (!this.itemFilter.contains(Integer.valueOf(((Item)e).getItemStack().getTypeId())));
/* 42 */     if ((e instanceof org.bukkit.entity.ItemFrame)) return this.itemframe;
/* 43 */     if ((e instanceof org.bukkit.entity.FallingBlock)) return this.fallingBlock;
/* 44 */     if ((e instanceof org.bukkit.entity.Boat)) return (e.isEmpty()) && (this.boat);
/* 45 */     if ((e instanceof org.bukkit.entity.ExperienceOrb)) return this.experienceOrb;
/* 46 */     if ((e instanceof org.bukkit.entity.Painting)) return this.painting;
/* 47 */     if ((e instanceof Projectile)) return this.projectile;
/* 48 */     if ((e instanceof org.bukkit.entity.TNTPrimed)) return this.primedTnt;
/* 49 */     return ((e instanceof org.bukkit.entity.Minecart)) && (e.isEmpty()) && (this.minecart);
/*    */   }
/*    */   
/*    */   public boolean isWorldEnabled(World w)
/*    */   {
/* 54 */     return !this.worldFilter.contains(w.getName());
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\removetype\LimitClear.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */