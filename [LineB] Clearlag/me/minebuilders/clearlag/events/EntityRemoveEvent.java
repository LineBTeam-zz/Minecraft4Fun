/*    */ package me.minebuilders.clearlag.events;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class EntityRemoveEvent
/*    */   extends Event
/*    */ {
/* 12 */   private static final HandlerList handlers = new HandlerList();
/*    */   private World world;
/*    */   private List<Entity> entities;
/*    */   
/*    */   public EntityRemoveEvent(List<Entity> entities, World w)
/*    */   {
/* 18 */     this.entities = entities;
/* 19 */     this.world = w;
/*    */   }
/*    */   
/*    */   public void addEntity(Entity e) {
/* 23 */     this.entities.add(e);
/*    */   }
/*    */   
/*    */   public void removeEntity(Entity e) {
/* 27 */     this.entities.remove(e);
/*    */   }
/*    */   
/*    */   public List<Entity> getEntityList() {
/* 31 */     return this.entities;
/*    */   }
/*    */   
/*    */   public World getWorld() {
/* 35 */     return this.world;
/*    */   }
/*    */   
/*    */   public HandlerList getHandlers()
/*    */   {
/* 40 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 44 */     return handlers;
/*    */   }
/*    */ }

