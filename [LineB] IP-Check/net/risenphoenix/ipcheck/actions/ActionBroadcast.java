/*     */ package net.risenphoenix.ipcheck.actions;
/*     */ 
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.command.ConsoleCommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.permissions.Permission;
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
/*     */ public class ActionBroadcast
/*     */ {
/*     */   private IPCheck ipc;
/*     */   private String message;
/*  43 */   private String[] values = null;
/*  44 */   private Permission[] requiredPerms = null;
/*     */   private boolean useName;
/*     */   
/*     */   public ActionBroadcast(String message, boolean useName) {
/*  48 */     this.message = message;
/*  49 */     this.ipc = IPCheck.getInstance();
/*  50 */     this.useName = useName;
/*     */   }
/*     */   
/*     */   public ActionBroadcast(String message, Permission[] perms, boolean useName) {
/*  54 */     this.message = message;
/*  55 */     this.requiredPerms = perms;
/*  56 */     this.ipc = IPCheck.getInstance();
/*  57 */     this.useName = useName;
/*     */   }
/*     */   
/*     */   public ActionBroadcast(String message, String[] values, boolean useName) {
/*  61 */     this.message = message;
/*  62 */     this.values = values;
/*  63 */     this.ipc = IPCheck.getInstance();
/*  64 */     this.useName = useName;
/*     */   }
/*     */   
/*     */   public ActionBroadcast(String message, String[] values, Permission[] perms, boolean useName)
/*     */   {
/*  69 */     this.message = message;
/*  70 */     this.values = values;
/*  71 */     this.requiredPerms = perms;
/*  72 */     this.ipc = IPCheck.getInstance();
/*  73 */     this.useName = useName;
/*     */   }
/*     */   
/*     */   public void execute()
/*     */   {
/*  78 */     Player[] players = this.ipc.getOnlinePlayers();
/*     */     
/*     */ 
/*  81 */     String finalMsg = formatMessage();
/*     */     
/*     */ 
/*  84 */     for (Player p : players)
/*     */     {
/*  86 */       if ((this.requiredPerms == null) || 
/*  87 */         (hasPermission(p)))
/*     */       {
/*     */ 
/*     */ 
/*  91 */         this.ipc.sendPlayerMessage(p, finalMsg, this.useName);
/*     */       }
/*     */     }
/*     */     
/*  95 */     Bukkit.getConsoleSender().sendMessage(finalMsg);
/*     */   }
/*     */   
/*     */   private String formatMessage() {
/*  99 */     return String.format(this.message, this.values);
/*     */   }
/*     */   
/*     */   private boolean hasPermission(Player player)
/*     */   {
/* 104 */     if (player.isOp()) { return true;
/*     */     }
/*     */     
/* 107 */     for (Permission p : this.requiredPerms) {
/* 108 */       if (!player.hasPermission(p)) { return false;
/*     */       }
/*     */     }
/*     */     
/* 112 */     return true;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\actions\ActionBroadcast.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */