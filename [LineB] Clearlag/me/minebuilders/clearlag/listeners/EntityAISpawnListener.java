/*    */ package me.minebuilders.clearlag.listeners;
/*    */ 
/*    */ import java.lang.reflect.Field;
/*    */ import java.lang.reflect.Method;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import me.minebuilders.clearlag.Util;
/*    */ import me.minebuilders.clearlag.annotations.ConfigPath;
/*    */ import me.minebuilders.clearlag.config.Config;
/*    */ import me.minebuilders.clearlag.modules.EventModule;
/*    */ import me.minebuilders.clearlag.reflection.ReflectionUtil;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.World;
/*    */ import org.bukkit.configuration.Configuration;
/*    */ import org.bukkit.configuration.ConfigurationSection;
/*    */ import org.bukkit.entity.Entity;
/*    */ import org.bukkit.entity.EntityType;
/*    */ import org.bukkit.entity.LivingEntity;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.EventPriority;
/*    */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*    */ 
/*    */ @ConfigPath(path="mob-range")
/*    */ public class EntityAISpawnListener extends EventModule
/*    */ {
/* 26 */   private Map<EntityType, Double> mobRanges = new HashMap();
/*    */   private Method getHandleMethod;
/*    */   private Method getAttriInstanceMethod;
/*    */   private Method setAttriMethod;
/*    */   private Object followRangeConst;
/*    */   
/*    */   @EventHandler(priority=EventPriority.MONITOR, ignoreCancelled=true)
/*    */   public void onCreatureSpawn(CreatureSpawnEvent event)
/*    */     throws Exception
/*    */   {
/* 36 */     setEntityRange(event.getEntity());
/*    */   }
/*    */   
/*    */   public void setEnabled()
/*    */   {
/*    */     try
/*    */     {
/* 43 */       String version = Util.getRawBukkitVersion();
/*    */       
/* 45 */       Class<?> craftEntity = Class.forName("org.bukkit.craftbukkit." + version + ".entity.CraftEntity");
/* 46 */       Class<?> nmsEntity = Class.forName("net.minecraft.server." + version + ".EntityLiving");
/* 47 */       Class<?> genAttributes = Class.forName("net.minecraft.server." + version + ".GenericAttributes");
/* 48 */       Class<?> attribClass = Class.forName("net.minecraft.server." + version + ".AttributeInstance");
/*    */       
/* 50 */       Field followRangeField = genAttributes.getDeclaredField(version.contains("1_7") ? "b" : "FOLLOW_RANGE");
/* 51 */       followRangeField.setAccessible(true);
/*    */       
/* 53 */       this.followRangeConst = followRangeField.get(null);
/*    */       
/* 55 */       this.setAttriMethod = ReflectionUtil.getMethodByName(attribClass, "setValue");
/* 56 */       this.setAttriMethod.setAccessible(true);
/*    */       
/* 58 */       this.getHandleMethod = craftEntity.getDeclaredMethod("getHandle", new Class[0]);
/* 59 */       this.getHandleMethod.setAccessible(true);
/*    */       
/* 61 */       this.getAttriInstanceMethod = ReflectionUtil.getMethodByName(nmsEntity, "getAttributeInstance");
/* 62 */       this.getAttriInstanceMethod.setAccessible(true);
/*    */       
/* 64 */       for (World w : Bukkit.getWorlds()) {
/* 65 */         for (Entity e : w.getEntities()) {
/* 66 */           if ((e instanceof LivingEntity)) {
/* 67 */             setEntityRange(e);
/*    */           }
/*    */         }
/*    */       }
/* 71 */       super.setEnabled();
/*    */     }
/*    */     catch (Exception e) {
/* 74 */       Util.warning("Failed to initialize 'mob-range' controller ~ This is possibly caused by an unsupported Bukkit/Spigot server version");
/* 75 */       return;
/*    */     }
/*    */     
/* 78 */     Configuration config = Config.getConfig();
/*    */     
/* 80 */     for (String s : config.getConfigurationSection("mob-range").getKeys(false)) {
/* 81 */       EntityType type = Util.getEntityTypeFromString(s);
/*    */       
/* 83 */       if (type != null) {
/* 84 */         this.mobRanges.put(type, Double.valueOf(config.getDouble("mob-range." + s)));
/*    */       }
/*    */     }
/*    */   }
/*    */   
/*    */   private void setEntityRange(Entity e) throws Exception {
/* 90 */     Double value = (Double)this.mobRanges.get(e.getType());
/*    */     
/* 92 */     if (value != null) {
/* 93 */       this.setAttriMethod.invoke(this.getAttriInstanceMethod.invoke(this.getHandleMethod.invoke(e, new Object[0]), new Object[] { this.followRangeConst }), new Object[] { value });
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\MINECRAFT_PRONTO\Clearlag.jar!\me\minebuilders\clearlag\listeners\EntityAISpawnListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */