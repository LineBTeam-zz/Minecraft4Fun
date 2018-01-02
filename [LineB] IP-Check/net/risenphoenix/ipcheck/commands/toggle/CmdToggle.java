/*     */ package net.risenphoenix.ipcheck.commands.toggle;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.Command;
/*     */ import net.risenphoenix.commons.commands.CommandType;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
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
/*     */ public class CmdToggle
/*     */   extends Command
/*     */ {
/*     */   private ConfigurationManager config;
/*     */   private ArrayList<ToggleOption> options;
/*     */   
/*     */   public CmdToggle(Plugin plugin, String[] callArgs, CommandType type)
/*     */   {
/*  49 */     super(plugin, callArgs, type);
/*     */     
/*  51 */     this.config = getPlugin().getConfigurationManager();
/*  52 */     this.options = new ArrayList();
/*     */     
/*     */ 
/*     */ 
/*  56 */     this.options.add(new ToggleOption(
/*  57 */       getPlugin(), "notify-on-login", "TOGGLE_NOTIFY", new String[] { "login-notify", "notification", "notify" }));
/*     */     
/*     */ 
/*     */ 
/*  61 */     this.options.add(new ToggleOption(
/*  62 */       getPlugin(), "descriptive-notice", "TOGGLE_DETAIL", new String[] { "detail-notify", "detail", "dn" }));
/*     */     
/*     */ 
/*     */ 
/*  66 */     this.options.add(new ToggleOption(
/*  67 */       getPlugin(), "secure-mode", "TOGGLE_SECURE", new String[] { "secure-mode", "secure", "sm" }));
/*     */     
/*     */ 
/*     */ 
/*  71 */     this.options.add(new ToggleOption(
/*  72 */       getPlugin(), "active-mode", "TOGGLE_ACTIVE", new String[] { "active-mode", "active", "am" }));
/*     */     
/*     */ 
/*     */ 
/*  76 */     this.options.add(new ToggleOption(
/*  77 */       getPlugin(), "use-country-blacklist", "TOGGLE_BLACKLIST", new String[] { "country-block", "black-list", "cb" }));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  82 */     this.options.add(new ToggleOption(
/*  83 */       getPlugin(), "whitelist-mode", "TOGGLE_WHITELIST", new String[] { "cb-whitelist", "whitelist", "wl" }));
/*     */     
/*     */ 
/*     */ 
/*  87 */     this.options.add(new ToggleOption(
/*  88 */       getPlugin(), "use-geoip-services", "TOGGLE_GEOIP", new String[] { "geoip-services", "geoip", "gs" }));
/*     */     
/*     */ 
/*     */ 
/*  92 */     this.options.add(new ToggleOption(
/*  93 */       getPlugin(), "warn-on-rejoin-attempt", "TOGGLE_REJOIN", new String[] { "rejoin-attempt", "rejoin", "ra" }));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  98 */     setName(getLocalString("CMD_TOGGLE"));
/*  99 */     setHelp(getLocalString("HELP_TOGGLE"));
/* 100 */     setSyntax("ipc toggle <OPTION_ID | help>");
/* 101 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.toggle") });
/*     */   }
/*     */   
/*     */ 
/*     */   public void onExecute(CommandSender sender, String[] args)
/*     */   {
/* 107 */     ToggleOption option = null;
/*     */     
/*     */ 
/* 110 */     for (ToggleOption to : this.options) {
/* 111 */       String[] callValues = to.getCallValues();
/*     */       
/* 113 */       for (int i = 0; i < callValues.length; i++) {
/* 114 */         if (args[1].equalsIgnoreCase(callValues[i])) {
/* 115 */           option = to;
/* 116 */           break;
/*     */         }
/*     */       }
/*     */       
/* 120 */       if (option != null) {
/*     */         break;
/*     */       }
/*     */     }
/* 124 */     if (args[1].equalsIgnoreCase("help")) {
/* 125 */       sendPlayerMessage(sender, ChatColor.DARK_GRAY + "---------------------------------------------", false);
/*     */       
/*     */ 
/* 128 */       for (ToggleOption to : this.options) {
/* 129 */         StringBuilder sb = new StringBuilder();
/* 130 */         String[] callValues = to.getCallValues();
/*     */         
/* 132 */         sb.append(" " + to.getDisplayID() + ":" + ChatColor.YELLOW + " < | ");
/*     */         
/*     */ 
/* 135 */         for (int i = 0; i < callValues.length; i++) {
/* 136 */           sb.append(ChatColor.LIGHT_PURPLE + callValues[i] + ChatColor.YELLOW + " | ");
/*     */         }
/*     */         
/*     */ 
/* 140 */         sb.append(ChatColor.YELLOW + ">");
/* 141 */         sendPlayerMessage(sender, sb.toString(), false);
/*     */       }
/*     */       
/* 144 */       sendPlayerMessage(sender, ChatColor.DARK_GRAY + "---------------------------------------------", false);
/*     */       
/* 146 */       return;
/*     */     }
/*     */     
/*     */ 
/* 150 */     if (option == null) {
/* 151 */       sendPlayerMessage(sender, getLocalString("TOGGLE_INVALID"));
/* 152 */       return;
/*     */     }
/*     */     
/*     */ 
/* 156 */     Boolean newValue = Boolean.valueOf(option.onExecute());
/* 157 */     ChatColor color = newValue.booleanValue() ? ChatColor.GREEN : ChatColor.RED;
/*     */     
/*     */ 
/* 160 */     sendPlayerMessage(sender, option.getDisplayID() + " set to: " + color + newValue);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\toggle\CmdToggle.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */