/*    */ package de.NeonnBukkit.NoRain;
/*    */ 
/*    */ import org.bukkit.Server;
/*    */ import org.bukkit.command.ConsoleCommandSender;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.weather.WeatherChangeEvent;
/*    */ import org.bukkit.plugin.PluginManager;
/*    */ 
/*    */ public class NoRain extends org.bukkit.plugin.java.JavaPlugin implements Listener
/*    */ {
/*    */   public void onEnable()
/*    */   {
/* 15 */     getServer().getConsoleSender().sendMessage("§bNoRain §e" + getDescription().getVersion() + " §6Ativado!");
/* 19 */     getServer().getPluginManager().registerEvents(this, this);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onWeatherChange(WeatherChangeEvent event)
/*    */   {
/* 25 */     if (event.toWeatherState()) {
/* 26 */       event.setCancelled(true);
/*    */     }
/*    */   }
/*    */ }


