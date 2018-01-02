/*     */ package net.risenphoenix.commons;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import net.risenphoenix.commons.commands.CommandManager;
/*     */ import net.risenphoenix.commons.commands.CommandType;
/*     */ import net.risenphoenix.commons.commands.ParseResult;
/*     */ import net.risenphoenix.commons.commands.ResultType;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.commons.localization.LocalizationManager;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.PluginDescriptionFile;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
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
/*     */ public class Plugin
/*     */   extends JavaPlugin
/*     */ {
/*     */   private LocalizationManager LM;
/*     */   private CommandManager CM;
/*     */   private ConfigurationManager ConfigM;
/*     */   private String pluginName;
/*  53 */   private ChatColor pluginColor = ChatColor.GOLD;
/*  54 */   private ChatColor messageColor = ChatColor.WHITE;
/*     */   
/*     */ 
/*     */ 
/*     */   public final void onEnable()
/*     */   {
/*  60 */     this.ConfigM = new ConfigurationManager(this);
/*  61 */     this.pluginName = ("[" + getDescription().getName() + "] ");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  66 */     this.LM = new LocalizationManager(this, getConfig().getString("language"));
/*  67 */     this.CM = new CommandManager(this);
/*  68 */     onStartup();
/*     */   }
/*     */   
/*     */   public final void onDisable()
/*     */   {
/*  73 */     onShutdown();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final boolean onCommand(CommandSender sender, org.bukkit.command.Command root, String commandLabel, String[] args)
/*     */   {
/*  83 */     List<String> argsPre = new LinkedList(Arrays.asList(args));
/*  84 */     argsPre.add(0, root.getName());
/*     */     
/*     */ 
/*  87 */     String[] argsFinal = new String[argsPre.size()];
/*  88 */     argsFinal = (String[])argsPre.toArray(argsFinal);
/*     */     
/*     */ 
/*     */ 
/*  92 */     ParseResult pResult = this.CM.parseCommand(argsFinal);
/*     */     
/*     */ 
/*  95 */     if (pResult.getResult() == ResultType.SUCCESS)
/*     */     {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 104 */       net.risenphoenix.commons.commands.Command cmd = null;
/*     */       
/*     */ 
/*     */       try
/*     */       {
/* 109 */         Class<?> clazz = Class.forName(pResult
/* 110 */           .getCommand().getClass().getName());
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 115 */         Constructor<?> ctor = clazz.getConstructor(new Class[] { Plugin.class, String[].class, CommandType.class });
/*     */         
/*     */ 
/*     */ 
/* 119 */         cmd = (net.risenphoenix.commons.commands.Command)ctor.newInstance(new Object[] { this, pResult
/* 120 */           .getCommand().getCallArgs(), pResult
/* 121 */           .getCommand().getType() });
/*     */       } catch (Exception e) {
/* 123 */         e.printStackTrace();
/*     */       }
/*     */       
/*     */ 
/* 127 */       if (cmd != null) {
/* 128 */         if ((sender instanceof Player)) {
/* 129 */           if (!cmd.onCall(sender, args)) {
/* 130 */             sendPlayerMessage(sender, this.LM
/* 131 */               .getLocalString("PERM_ERR"));
/*     */           }
/*     */           
/*     */ 
/*     */         }
/* 136 */         else if (!cmd.canConsoleExecute()) {
/* 137 */           sendConsoleMessage(Level.INFO, this.LM
/* 138 */             .getLocalString("NO_CONSOLE"));
/*     */         } else {
/* 140 */           cmd.onCall(sender, args);
/*     */         }
/*     */       }
/*     */       else {
/* 144 */         sendPlayerMessage(sender, getLocalizationManager()
/* 145 */           .getLocalString("CMD_NULL_ERR"));
/*     */       }
/*     */       
/*     */     }
/* 149 */     else if (pResult.getResult() == ResultType.BAD_NUM_ARGS) {
/* 150 */       sendPlayerMessage(sender, this.LM
/* 151 */         .getLocalString("NUM_ARGS_ERR"));
/*     */ 
/*     */     }
/* 154 */     else if (pResult.getResult() == ResultType.FAIL) {
/* 155 */       sendPlayerMessage(sender, this.LM.getLocalString("NO_CMD"));
/*     */     }
/*     */     
/* 158 */     return true;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onStartup()
/*     */   {
/* 165 */     throw new UnsupportedOperationException(this.LM.getLocalString("NO_IMPLEMENT"));
/*     */   }
/*     */   
/*     */   public void onShutdown()
/*     */   {
/* 170 */     throw new UnsupportedOperationException(this.LM.getLocalString("NO_IMPLEMENT"));
/*     */   }
/*     */   
/*     */   public final Map<String, String> getVersionInfo() {
/* 174 */     Map<String, String> info = new HashMap();
/*     */     
/* 176 */     info.put("NAME", "RP-Commons");
/* 177 */     info.put("VERSION", "v1.1.0");
/* 178 */     info.put("AUTHOR", "Jacob Keep");
/* 179 */     info.put("BUILD", "201");
/* 180 */     info.put("JVM_COMPAT", "1.7");
/*     */     
/* 182 */     return info;
/*     */   }
/*     */   
/*     */   public final LocalizationManager getLocalizationManager() {
/* 186 */     return this.LM;
/*     */   }
/*     */   
/*     */   public final ConfigurationManager getConfigurationManager() {
/* 190 */     return this.ConfigM;
/*     */   }
/*     */   
/*     */   public final CommandManager getCommandManager() {
/* 194 */     return this.CM;
/*     */   }
/*     */   
/*     */   public final void setPluginName(ChatColor color, String name) {
/* 198 */     this.pluginName = ("[" + name + "] ");
/* 199 */     this.pluginColor = color;
/*     */   }
/*     */   
/*     */   public final void setMessageColor(ChatColor color) {
/* 203 */     this.messageColor = color;
/*     */   }
/*     */   
/*     */   public final void sendPlayerMessage(CommandSender sender, String message) {
/* 207 */     sendPlayerMessage(sender, message, true);
/*     */   }
/*     */   
/*     */   public final void sendPlayerMessage(CommandSender sender, String message, boolean useName)
/*     */   {
/* 212 */     sender.sendMessage((useName ? this.pluginColor + this.pluginName : "") + this.messageColor + message);
/*     */   }
/*     */   
/*     */   public final void sendConsoleMessage(Level level, String message)
/*     */   {
/* 217 */     Bukkit.getLogger().log(level, this.pluginName + message);
/*     */   }
/*     */   
/*     */   public final String formatPlayerMessage(String msg) {
/* 221 */     return this.pluginColor + this.pluginName + this.messageColor + msg;
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\Plugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */