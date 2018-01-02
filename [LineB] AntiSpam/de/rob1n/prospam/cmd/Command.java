/*    */ package de.rob1n.prospam.cmd;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.data.DataHandler;
/*    */ import de.rob1n.prospam.data.specific.Blacklist;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import de.rob1n.prospam.data.specific.Whitelist;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public abstract class Command implements CommandInterface
/*    */ {
/*    */   protected final ProSpam plugin;
/*    */   protected final Settings settings;
/*    */   protected final Blacklist blacklist;
/*    */   protected final Whitelist whitelist;
/*    */   
/*    */   public Command(ProSpam plugin)
/*    */   {
/* 20 */     this.plugin = plugin;
/*    */     
/* 22 */     this.settings = plugin.getDataHandler().getSettings();
/* 23 */     this.blacklist = plugin.getDataHandler().getBlacklist();
/* 24 */     this.whitelist = plugin.getDataHandler().getWhitelist();
/*    */   }
/*    */   
/*    */   protected boolean isPlayer(CommandSender sender)
/*    */   {
/* 29 */     return sender instanceof Player;
/*    */   }
/*    */   
/*    */   public String[] getAliases()
/*    */   {
/* 34 */     return new String[0];
/*    */   }
/*    */ }
