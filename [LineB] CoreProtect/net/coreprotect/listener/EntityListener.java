/*     */ package net.coreprotect.listener;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import net.coreprotect.CoreProtect;
/*     */ import net.coreprotect.Functions;
/*     */ import net.coreprotect.consumer.Queue;
/*     */ import net.coreprotect.database.Database;
/*     */ import net.coreprotect.database.Logger;
/*     */ import net.coreprotect.model.BlockInfo;
/*     */ import net.coreprotect.model.Config;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.attribute.Attributable;
/*     */ import org.bukkit.attribute.Attribute;
/*     */ import org.bukkit.attribute.AttributeInstance;
/*     */ import org.bukkit.attribute.AttributeModifier;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.Sign;
/*     */ import org.bukkit.entity.AbstractHorse;
/*     */ import org.bukkit.entity.Ageable;
/*     */ import org.bukkit.entity.AnimalTamer;
/*     */ import org.bukkit.entity.ArmorStand;
/*     */ import org.bukkit.entity.Arrow;
/*     */ import org.bukkit.entity.ChestedHorse;
/*     */ import org.bukkit.entity.Creeper;
/*     */ import org.bukkit.entity.EnderCrystal;
/*     */ import org.bukkit.entity.EnderDragon;
/*     */ import org.bukkit.entity.EnderDragonPart;
/*     */ import org.bukkit.entity.Enderman;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.EntityType;
/*     */ import org.bukkit.entity.Horse;
/*     */ import org.bukkit.entity.IronGolem;
/*     */ import org.bukkit.entity.ItemFrame;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Llama;
/*     */ import org.bukkit.entity.Minecart;
/*     */ import org.bukkit.entity.Ocelot;
/*     */ import org.bukkit.entity.Pig;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Sheep;
/*     */ import org.bukkit.entity.Silverfish;
/*     */ import org.bukkit.entity.Skeleton;
/*     */ import org.bukkit.entity.Slime;
/*     */ import org.bukkit.entity.Snowman;
/*     */ import org.bukkit.entity.TNTPrimed;
/*     */ import org.bukkit.entity.Tameable;
/*     */ import org.bukkit.entity.ThrownPotion;
/*     */ import org.bukkit.entity.Villager;
/*     */ import org.bukkit.entity.Wither;
/*     */ import org.bukkit.entity.WitherSkull;
/*     */ import org.bukkit.entity.Wolf;
/*     */ import org.bukkit.entity.Zombie;
/*     */ import org.bukkit.entity.ZombieVillager;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.EntityBlockFormEvent;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.EntityChangeBlockEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent;
/*     */ import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.entity.EntityExplodeEvent;
/*     */ import org.bukkit.inventory.HorseInventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.LlamaInventory;
/*     */ import org.bukkit.inventory.MerchantRecipe;
/*     */ import org.bukkit.material.MaterialData;
/*     */ import org.bukkit.projectiles.ProjectileSource;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class EntityListener
/*     */   extends Queue
/*     */   implements Listener
/*     */ {
/*     */   @EventHandler
/*     */   public void onCreatureSpawn(CreatureSpawnEvent event)
/*     */   {
/* 108 */     if (event.isCancelled()) {
/* 109 */       return;
/*     */     }
/* 111 */     if (event.getEntityType().equals(EntityType.ARMOR_STAND)) {
/* 112 */       World world = event.getEntity().getWorld();
/* 113 */       Location location = event.getEntity().getLocation();
/* 114 */       String key = world.getName() + "-" + location.getBlockX() + "-" + location.getBlockY() + "-" + location.getBlockZ();
/* 115 */       Iterator<Map.Entry<UUID, Object[]>> it = Config.entity_block_mapper.entrySet().iterator();
/* 116 */       while (it.hasNext()) {
/* 117 */         Map.Entry<UUID, Object[]> pair = (Map.Entry)it.next();
/* 118 */         UUID uuid = (UUID)pair.getKey();
/* 119 */         Object[] data = (Object[])pair.getValue();
/* 120 */         if (((data[0].equals(key)) || (data[1].equals(key))) && (Functions.getEntityMaterial(event.getEntityType()).equals(data[2]))) {
/* 121 */           Player player = CoreProtect.getInstance().getServer().getPlayer(uuid);
/* 122 */           Queue.queueBlockPlace(player.getName(), location.getBlock().getState(), location.getBlock(), location.getBlock().getState(), (Material)data[2], (int)event.getEntity().getLocation().getYaw(), 1);
/* 123 */           it.remove();
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onEntityBlockForm(EntityBlockFormEvent event) {
/* 131 */     World world = event.getBlock().getWorld();
/* 132 */     if ((!event.isCancelled()) && (Functions.checkConfig(world, "entity-change") == 1)) {
/* 133 */       Entity entity = event.getEntity();
/* 134 */       Block block = event.getBlock();
/* 135 */       BlockState newState = event.getNewState();
/* 136 */       String e = "";
/* 137 */       if ((entity instanceof Snowman)) {
/* 138 */         e = "#snowman";
/*     */       }
/* 140 */       if (e.length() > 0) {
/* 141 */         Queue.queueBlockPlace(e, block.getState(), newState.getType(), Functions.getData(newState));
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onEntityChangeBlock(EntityChangeBlockEvent event) {
/* 148 */     World world = event.getBlock().getWorld();
/* 149 */     if ((!event.isCancelled()) && (Functions.checkConfig(world, "entity-change") == 1)) {
/* 150 */       Entity entity = event.getEntity();
/* 151 */       Block block = event.getBlock();
/* 152 */       Material newtype = event.getTo();
/* 153 */       Material type = event.getBlock().getType();
/* 154 */       int data = Functions.getData(event.getBlock());
/* 155 */       String e = "";
/* 156 */       if ((entity instanceof Enderman)) {
/* 157 */         e = "#enderman";
/*     */       }
/* 159 */       else if ((entity instanceof EnderDragon)) {
/* 160 */         e = "#enderdragon";
/*     */       }
/* 162 */       else if ((entity instanceof Wither)) {
/* 163 */         e = "#wither";
/*     */       }
/* 165 */       else if (((entity instanceof Silverfish)) && 
/* 166 */         (newtype.equals(Material.AIR))) {
/* 167 */         e = "#silverfish";
/*     */       }
/*     */       
/* 170 */       if (e.length() > 0) {
/* 171 */         if (newtype.equals(Material.AIR)) {
/* 172 */           Queue.queueBlockBreak(e, block.getState(), type, data);
/*     */         }
/*     */         else {
/* 175 */           Queue.queueBlockPlace(e, block);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
/* 183 */     Entity damager = event.getDamager();
/* 184 */     if (((event.getEntity() instanceof ItemFrame)) || ((event.getEntity() instanceof ArmorStand)) || ((event.getEntity() instanceof EnderCrystal))) {
/* 185 */       boolean inspecting = false;
/* 186 */       String user = "#entity";
/* 187 */       if (damager != null) {
/* 188 */         Entity entity = event.getEntity();
/* 189 */         Block block = entity.getLocation().getBlock();
/*     */         
/* 191 */         if ((damager instanceof Player)) {
/* 192 */           Player player = (Player)damager;
/* 193 */           user = player.getName();
/*     */           
/* 195 */           if ((Config.inspecting.get(player.getName()) != null) && 
/* 196 */             (((Boolean)Config.inspecting.get(player.getName())).booleanValue())) {
/* 197 */             HangingListener.inspectItemFrame(block, player);
/* 198 */             event.setCancelled(true);
/* 199 */             inspecting = true;
/*     */           }
/*     */           
/*     */         }
/* 203 */         else if ((damager instanceof Arrow)) {
/* 204 */           Arrow arrow = (Arrow)damager;
/* 205 */           ProjectileSource source = arrow.getShooter();
/* 206 */           if ((source instanceof Player)) {
/* 207 */             Player player = (Player)source;
/* 208 */             user = player.getName();
/*     */           }
/*     */         }
/* 211 */         else if ((damager instanceof TNTPrimed)) {
/* 212 */           user = "#tnt";
/*     */         }
/* 214 */         else if ((damager instanceof Minecart)) {
/* 215 */           String name = damager.getType().name();
/* 216 */           if (name.contains("TNT")) {
/* 217 */             user = "#tnt";
/*     */           }
/*     */         }
/* 220 */         else if ((damager instanceof Creeper)) {
/* 221 */           user = "#creeper";
/*     */         }
/* 223 */         else if (((damager instanceof EnderDragon)) || ((damager instanceof EnderDragonPart))) {
/* 224 */           user = "#enderdragon";
/*     */         }
/* 226 */         else if (((damager instanceof Wither)) || ((damager instanceof WitherSkull))) {
/* 227 */           user = "#wither";
/*     */         }
/* 229 */         else if (damager.getType() != null) {
/* 230 */           user = "#" + damager.getType().name().toLowerCase();
/*     */         }
/*     */         
/* 233 */         if ((!event.isCancelled()) && (Functions.checkConfig(entity.getWorld(), "block-break") == 1) && (!inspecting)) {
/* 234 */           if ((entity instanceof ItemFrame)) {
/* 235 */             ItemFrame frame = (ItemFrame)event.getEntity();
/* 236 */             int data = 0;
/* 237 */             if (frame.getItem() != null) {
/* 238 */               data = Functions.block_id(frame.getItem().getType());
/*     */             }
/* 240 */             Queue.queueBlockBreak(user, block.getState(), Material.ITEM_FRAME, data);
/* 241 */             Queue.queueBlockPlace(user, block.getState(), Material.ITEM_FRAME, 0);
/*     */           }
/* 243 */           else if ((entity instanceof ArmorStand)) {
/* 244 */             Database.containerBreakCheck(user, Material.ARMOR_STAND, entity, block.getLocation());
/* 245 */             Queue.queueBlockBreak(user, block.getState(), Material.ARMOR_STAND, (int)entity.getLocation().getYaw());
/*     */           }
/* 247 */           else if ((entity instanceof EnderCrystal)) {
/* 248 */             EnderCrystal crystal = (EnderCrystal)event.getEntity();
/* 249 */             Queue.queueBlockBreak(user, block.getState(), Material.END_CRYSTAL, crystal.isShowingBottom() ? 1 : 0);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDeath(EntityDeathEvent event) {
/* 258 */     LivingEntity entity = event.getEntity();
/* 259 */     if (entity == null) {
/* 260 */       return;
/*     */     }
/* 262 */     if (Functions.checkConfig(entity.getWorld(), "entity-kills") == 1) {
/* 263 */       EntityDamageEvent damage = entity.getLastDamageCause();
/* 264 */       if (damage != null) {
/* 265 */         String e = "";
/* 266 */         boolean skip = true;
/* 267 */         if ((Functions.checkConfig(entity.getWorld(), "skip-generic-data") == 0) || ((!(entity instanceof Zombie)) && (!(entity instanceof Skeleton)))) {
/* 268 */           skip = false;
/*     */         }
/* 270 */         if ((damage instanceof EntityDamageByEntityEvent)) {
/* 271 */           EntityDamageByEntityEvent attack = (EntityDamageByEntityEvent)damage;
/* 272 */           Entity attacker = attack.getDamager();
/* 273 */           if ((attacker instanceof Player)) {
/* 274 */             Player player = (Player)attacker;
/* 275 */             e = player.getName();
/*     */           }
/* 277 */           else if ((attacker instanceof Arrow)) {
/* 278 */             Arrow arrow = (Arrow)attacker;
/* 279 */             ProjectileSource shooter = arrow.getShooter();
/* 280 */             if ((shooter instanceof Player)) {
/* 281 */               Player player = (Player)shooter;
/* 282 */               e = player.getName();
/*     */             }
/* 284 */             else if ((shooter instanceof LivingEntity)) {
/* 285 */               EntityType entityType = ((LivingEntity)shooter).getType();
/* 286 */               if (entityType != null) {
/* 287 */                 String name = entityType.name().toLowerCase();
/* 288 */                 e = "#" + name;
/*     */               }
/*     */             }
/*     */           }
/* 292 */           else if ((attacker instanceof ThrownPotion)) {
/* 293 */             ThrownPotion potion = (ThrownPotion)attacker;
/* 294 */             ProjectileSource shooter = potion.getShooter();
/* 295 */             if ((shooter instanceof Player)) {
/* 296 */               Player player = (Player)shooter;
/* 297 */               e = player.getName();
/*     */             }
/* 299 */             else if ((shooter instanceof LivingEntity)) {
/* 300 */               EntityType entityType = ((LivingEntity)shooter).getType();
/* 301 */               if (entityType != null) {
/* 302 */                 String name = entityType.name().toLowerCase();
/* 303 */                 e = "#" + name;
/*     */               }
/*     */             }
/*     */           }
/* 307 */           else if (attacker.getType().name() != null) {
/* 308 */             e = "#" + attacker.getType().name().toLowerCase();
/*     */           }
/*     */         }
/*     */         else {
/* 312 */           EntityDamageEvent.DamageCause cause = damage.getCause();
/* 313 */           if (cause.equals(EntityDamageEvent.DamageCause.FIRE)) {
/* 314 */             e = "#fire";
/*     */           }
/* 316 */           else if (cause.equals(EntityDamageEvent.DamageCause.FIRE_TICK)) {
/* 317 */             if (!skip) {
/* 318 */               e = "#fire";
/*     */             }
/*     */           }
/* 321 */           else if (cause.equals(EntityDamageEvent.DamageCause.LAVA)) {
/* 322 */             e = "#lava";
/*     */           }
/* 324 */           else if (cause.equals(EntityDamageEvent.DamageCause.BLOCK_EXPLOSION)) {
/* 325 */             e = "#explosion";
/*     */           }
/*     */         }
/* 328 */         EntityType entity_type = entity.getType();
/* 329 */         if (e.length() == 0)
/*     */         {
/* 331 */           if (!skip) {
/* 332 */             if ((!(entity instanceof Player)) && (entity_type.name() != null))
/*     */             {
/*     */ 
/* 335 */               e = "#" + entity_type.name().toLowerCase();
/*     */             }
/* 337 */             else if ((entity instanceof Player)) {
/* 338 */               e = entity.getName();
/*     */             }
/*     */           }
/*     */         }
/* 342 */         if (e.startsWith("#wither")) {
/* 343 */           e = "#wither";
/*     */         }
/* 345 */         if (e.startsWith("#enderdragon")) {
/* 346 */           e = "#enderdragon";
/*     */         }
/* 348 */         if ((e.startsWith("#primedtnt")) || (e.startsWith("#tnt"))) {
/* 349 */           e = "#tnt";
/*     */         }
/* 351 */         if (e.startsWith("#lightning")) {
/* 352 */           e = "#lightning";
/*     */         }
/* 354 */         if (e.length() > 0) {
/* 355 */           List<Object> data = new ArrayList();
/* 356 */           List<Object> age = new ArrayList();
/* 357 */           List<Object> tame = new ArrayList();
/* 358 */           List<Object> attributes = new ArrayList();
/* 359 */           List<Object> info = new ArrayList();
/* 360 */           EntityType type = entity_type;
/*     */           
/* 362 */           if ((entity instanceof Ageable)) {
/* 363 */             Ageable ageable = (Ageable)entity;
/* 364 */             age.add(Integer.valueOf(ageable.getAge()));
/* 365 */             age.add(Boolean.valueOf(ageable.getAgeLock()));
/* 366 */             age.add(Boolean.valueOf(ageable.isAdult()));
/* 367 */             age.add(Boolean.valueOf(ageable.canBreed()));
/* 368 */             age.add(null);
/*     */           }
/* 370 */           if ((entity instanceof Tameable)) {
/* 371 */             Tameable tameable = (Tameable)entity;
/* 372 */             tame.add(Boolean.valueOf(tameable.isTamed()));
/* 373 */             if ((tameable.isTamed() == true) && 
/* 374 */               (tameable.getOwner() != null)) {
/* 375 */               tame.add(tameable.getOwner().getName());
/*     */             }
/*     */           }
/*     */           
/* 379 */           if ((entity instanceof Attributable)) {
/* 380 */             Attributable attributable = entity;
/* 381 */             for (Attribute attribute : Attribute.values()) {
/* 382 */               AttributeInstance attributeInstance = attributable.getAttribute(attribute);
/* 383 */               if (attributeInstance != null) {
/* 384 */                 List<Object> attributeData = new ArrayList();
/* 385 */                 List<Object> attributeModifiers = new ArrayList();
/* 386 */                 attributeData.add(attributeInstance.getAttribute());
/* 387 */                 attributeData.add(Double.valueOf(attributeInstance.getBaseValue()));
/* 388 */                 for (AttributeModifier modifier : attributeInstance.getModifiers()) {
/* 389 */                   attributeModifiers.add(modifier.serialize());
/*     */                 }
/* 391 */                 attributeData.add(attributeModifiers);
/* 392 */                 attributes.add(attributeData);
/*     */               }
/*     */             }
/*     */           }
/*     */           
/* 397 */           if ((entity instanceof Creeper)) {
/* 398 */             Creeper creeper = (Creeper)entity;
/* 399 */             info.add(Boolean.valueOf(creeper.isPowered()));
/*     */           }
/* 401 */           else if ((entity instanceof Enderman)) {
/* 402 */             Enderman enderman = (Enderman)entity;
/* 403 */             info.add(enderman.getCarriedMaterial().toItemStack().serialize());
/*     */           }
/* 405 */           else if ((entity instanceof IronGolem)) {
/* 406 */             IronGolem irongolem = (IronGolem)entity;
/* 407 */             info.add(Boolean.valueOf(irongolem.isPlayerCreated()));
/*     */           }
/* 409 */           else if ((entity instanceof Ocelot)) {
/* 410 */             Ocelot ocelot = (Ocelot)entity;
/* 411 */             info.add(ocelot.getCatType());
/* 412 */             info.add(Boolean.valueOf(ocelot.isSitting()));
/*     */           }
/* 414 */           else if ((entity instanceof Pig)) {
/* 415 */             Pig pig = (Pig)entity;
/* 416 */             info.add(Boolean.valueOf(pig.hasSaddle()));
/*     */           }
/* 418 */           else if ((entity instanceof Sheep)) {
/* 419 */             Sheep sheep = (Sheep)entity;
/* 420 */             info.add(Boolean.valueOf(sheep.isSheared()));
/* 421 */             info.add(sheep.getColor());
/*     */           }
/* 423 */           else if ((entity instanceof Skeleton)) {
/* 424 */             info.add(null);
/*     */           }
/* 426 */           else if ((entity instanceof Slime)) {
/* 427 */             Slime slime = (Slime)entity;
/* 428 */             info.add(Integer.valueOf(slime.getSize()));
/*     */           }
/* 430 */           else if ((entity instanceof Villager)) {
/* 431 */             Villager villager = (Villager)entity;
/* 432 */             info.add(villager.getProfession());
/* 433 */             info.add(Integer.valueOf(villager.getRiches()));
/*     */             
/* 435 */             List<Object> recipes = new ArrayList();
/* 436 */             for (MerchantRecipe merchantRecipe : villager.getRecipes()) {
/* 437 */               List<Object> recipe = new ArrayList();
/* 438 */               List<Object> ingredients = new ArrayList();
/* 439 */               List<Object> itemMap = new ArrayList();
/* 440 */               ItemStack item = merchantRecipe.getResult().clone();
/* 441 */               List<List<Map<String, Object>>> metadata = Logger.getItemMeta(item, item.getType(), 0);
/* 442 */               item.setItemMeta(null);
/* 443 */               itemMap.add(item.serialize());
/* 444 */               itemMap.add(metadata);
/* 445 */               recipe.add(itemMap);
/* 446 */               recipe.add(Integer.valueOf(merchantRecipe.getUses()));
/* 447 */               recipe.add(Integer.valueOf(merchantRecipe.getMaxUses()));
/* 448 */               recipe.add(Boolean.valueOf(merchantRecipe.hasExperienceReward()));
/* 449 */               for (ItemStack ingredient : merchantRecipe.getIngredients()) {
/* 450 */                 itemMap = new ArrayList();
/* 451 */                 item = ingredient.clone();
/* 452 */                 metadata = Logger.getItemMeta(item, item.getType(), 0);
/* 453 */                 item.setItemMeta(null);
/* 454 */                 itemMap.add(item.serialize());
/* 455 */                 itemMap.add(metadata);
/* 456 */                 ingredients.add(itemMap);
/*     */               }
/* 458 */               recipe.add(ingredients);
/* 459 */               recipes.add(recipe);
/*     */             }
/* 461 */             info.add(recipes);
/*     */           }
/* 463 */           else if ((entity instanceof Wolf)) {
/* 464 */             Wolf wolf = (Wolf)entity;
/* 465 */             info.add(Boolean.valueOf(wolf.isSitting()));
/* 466 */             info.add(wolf.getCollarColor());
/*     */           }
/* 468 */           else if ((entity instanceof ZombieVillager)) {
/* 469 */             ZombieVillager zombieVillager = (ZombieVillager)entity;
/* 470 */             info.add(Boolean.valueOf(zombieVillager.isBaby()));
/* 471 */             info.add(zombieVillager.getVillagerProfession());
/*     */           }
/* 473 */           else if ((entity instanceof Zombie)) {
/* 474 */             Zombie zombie = (Zombie)entity;
/* 475 */             info.add(Boolean.valueOf(zombie.isBaby()));
/* 476 */             info.add(null);
/* 477 */             info.add(null);
/*     */           }
/* 479 */           else if ((entity instanceof AbstractHorse)) {
/* 480 */             AbstractHorse abstractHorse = (AbstractHorse)entity;
/* 481 */             info.add(null);
/* 482 */             info.add(null);
/* 483 */             info.add(Integer.valueOf(abstractHorse.getDomestication()));
/* 484 */             info.add(Double.valueOf(abstractHorse.getJumpStrength()));
/* 485 */             info.add(Integer.valueOf(abstractHorse.getMaxDomestication()));
/* 486 */             info.add(null);
/* 487 */             info.add(null);
/*     */             
/* 489 */             if ((entity instanceof Horse)) {
/* 490 */               Horse horse = (Horse)entity;
/* 491 */               ItemStack armor = horse.getInventory().getArmor();
/* 492 */               if (armor != null) {
/* 493 */                 info.add(armor.serialize());
/*     */               }
/*     */               else {
/* 496 */                 info.add(null);
/*     */               }
/* 498 */               ItemStack saddle = horse.getInventory().getSaddle();
/* 499 */               if (saddle != null) {
/* 500 */                 info.add(saddle.serialize());
/*     */               }
/*     */               else {
/* 503 */                 info.add(null);
/*     */               }
/*     */               
/* 506 */               info.add(horse.getColor());
/* 507 */               info.add(horse.getStyle());
/*     */             }
/* 509 */             else if ((entity instanceof ChestedHorse)) {
/* 510 */               ChestedHorse chestedHorse = (ChestedHorse)entity;
/* 511 */               info.add(Boolean.valueOf(chestedHorse.isCarryingChest()));
/*     */               
/* 513 */               if ((entity instanceof Llama)) {
/* 514 */                 Llama llama = (Llama)entity;
/* 515 */                 ItemStack decor = llama.getInventory().getDecor();
/* 516 */                 if (decor != null) {
/* 517 */                   info.add(decor.serialize());
/*     */                 }
/*     */                 else {
/* 520 */                   info.add(null);
/*     */                 }
/* 522 */                 info.add(llama.getColor());
/*     */               }
/*     */             }
/*     */           }
/* 526 */           data.add(age);
/* 527 */           data.add(tame);
/* 528 */           data.add(info);
/* 529 */           data.add(Boolean.valueOf(entity.isCustomNameVisible()));
/* 530 */           data.add(entity.getCustomName());
/* 531 */           data.add(attributes);
/* 532 */           if (!(entity instanceof Player)) {
/* 533 */             Queue.queueEntityKill(e, entity.getLocation(), data, type);
/*     */           }
/*     */           else {
/* 536 */             Queue.queuePlayerKill(e, entity.getLocation(), entity.getName());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(priority=EventPriority.MONITOR)
/*     */   protected void onEntityExplode(EntityExplodeEvent event) {
/* 545 */     Entity entity = event.getEntity();
/* 546 */     World world = event.getLocation().getWorld();
/* 547 */     String user = "#explosion";
/* 548 */     if ((entity instanceof TNTPrimed)) {
/* 549 */       user = "#tnt";
/*     */     }
/* 551 */     else if ((entity instanceof Minecart)) {
/* 552 */       String name = entity.getType().name();
/* 553 */       if (name.contains("TNT")) {
/* 554 */         user = "#tnt";
/*     */       }
/*     */     }
/* 557 */     else if ((entity instanceof Creeper)) {
/* 558 */       user = "#creeper";
/*     */     }
/* 560 */     else if (((entity instanceof EnderDragon)) || ((entity instanceof EnderDragonPart))) {
/* 561 */       user = "#enderdragon";
/*     */     }
/* 563 */     else if (((entity instanceof Wither)) || ((entity instanceof WitherSkull))) {
/* 564 */       user = "#wither";
/*     */     }
/* 566 */     else if ((entity instanceof EnderCrystal)) {
/* 567 */       user = "#endercrystal";
/*     */     }
/*     */     
/* 570 */     int log = 0;
/* 571 */     if (Functions.checkConfig(world, "explosions") == 1) {
/* 572 */       log = 1;
/*     */     }
/* 574 */     if (((user.equals("#enderdragon")) || (user.equals("#wither"))) && (Functions.checkConfig(world, "entity-change") == 0)) {
/* 575 */       log = 0;
/*     */     }
/*     */     
/* 578 */     if ((!event.isCancelled()) && (log == 1)) {
/* 579 */       List<Block> b = event.blockList();
/* 580 */       List<Block> nb = new ArrayList();
/*     */       
/* 582 */       if (Functions.checkConfig(world, "natural-break") == 1) {
/* 583 */         for (Block block : b) {
/* 584 */           int x = block.getX();
/* 585 */           int y = block.getY();
/* 586 */           int z = block.getZ();
/* 587 */           Location l1 = new Location(world, x + 1, y, z);
/* 588 */           Location l2 = new Location(world, x - 1, y, z);
/* 589 */           Location l3 = new Location(world, x, y, z + 1);
/* 590 */           Location l4 = new Location(world, x, y, z - 1);
/* 591 */           Location l5 = new Location(world, x, y + 1, z);
/*     */           
/* 593 */           int l = 1;
/* 594 */           int m = 6;
/* 595 */           while (l < m) {
/* 596 */             Location lc = l1;
/* 597 */             if (l == 2) {
/* 598 */               lc = l2;
/*     */             }
/* 600 */             if (l == 3) {
/* 601 */               lc = l3;
/*     */             }
/* 603 */             if (l == 4) {
/* 604 */               lc = l4;
/*     */             }
/* 606 */             if (l == 5) {
/* 607 */               lc = l5;
/*     */             }
/* 609 */             Block block_t = world.getBlockAt(lc);
/* 610 */             Material t = block_t.getType();
/* 611 */             if ((BlockInfo.track_any.contains(t)) || (BlockInfo.track_top.contains(t)) || (BlockInfo.track_side.contains(t))) {
/* 612 */               Block bl = world.getBlockAt(lc);
/* 613 */               nb.add(bl);
/*     */             }
/* 615 */             l++;
/*     */           }
/*     */         }
/*     */       }
/* 619 */       for (Block block : b) {
/* 620 */         if (!nb.contains(block)) {
/* 621 */           nb.add(block);
/*     */         }
/*     */       }
/*     */       
/* 625 */       for (Block block : nb) {
/* 626 */         Material blockType = block.getType();
/* 627 */         BlockState blockState = block.getState();
/* 628 */         if (((blockType.equals(Material.SIGN_POST)) || (blockType.equals(Material.WALL_SIGN))) && 
/* 629 */           (Functions.checkConfig(world, "sign-text") == 1)) {
/*     */           try {
/* 631 */             Sign sign = (Sign)blockState;
/* 632 */             String line1 = sign.getLine(0);
/* 633 */             String line2 = sign.getLine(1);
/* 634 */             String line3 = sign.getLine(2);
/* 635 */             String line4 = sign.getLine(3);
/* 636 */             Queue.queueSignText(user, blockState, line1, line2, line3, line4, 5);
/*     */           }
/*     */           catch (Exception e) {
/* 639 */             e.printStackTrace();
/*     */           }
/*     */         }
/*     */         
/* 643 */         Database.containerBreakCheck(user, blockType, block, block.getLocation());
/* 644 */         Queue.queueBlockBreak(user, blockState, blockType, Functions.getData(block));
/*     */       }
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\listener\EntityListener.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */