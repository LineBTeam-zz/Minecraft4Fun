/*     */ package net.risenphoenix.ipcheck.commands;
/*     */ 
/*     */ import java.util.Map;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.Command;
/*     */ import net.risenphoenix.commons.commands.CommandType;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.objects.StatsObject;
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
/*     */ public class CmdStatus
/*     */   extends Command
/*     */ {
/*     */   public CmdStatus(Plugin plugin, String[] callArgs, CommandType type)
/*     */   {
/*  45 */     super(plugin, callArgs, type);
/*     */     
/*  47 */     setName(getLocalString("CMD_STATUS"));
/*  48 */     setHelp(getLocalString("HELP_STATUS"));
/*  49 */     setSyntax("ipc status [adv | advanced | etc]");
/*  50 */     setPermissions(new Permission[] { new Permission("ipcheck.use") });
/*     */   }
/*     */   
/*     */ 
/*     */   public void onExecute(CommandSender sender, String[] args)
/*     */   {
/*  56 */     StatsObject stats = IPCheck.getInstance().getStatisticsObject();
/*     */     
/*     */ 
/*  59 */     getPlugin().sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     
/*     */ 
/*  62 */     sendPlayerMessage(sender, getLocalString("STATS_HEADER"));
/*     */     
/*     */ 
/*  65 */     getPlugin().sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     
/*     */ 
/*     */ 
/*  69 */     sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/*  70 */       getLocalString("STATS_PVER") + ChatColor.YELLOW + stats
/*  71 */       .getPluginVersion(), false);
/*     */     
/*  73 */     sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/*  74 */       getLocalString("STATS_LVER") + ChatColor.YELLOW + 
/*  75 */       (String)stats.getLibraryVersion().get("VERSION"), false);
/*     */     
/*  77 */     sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/*  78 */       getLocalString("STATS_DB_TYPE") + ChatColor.YELLOW + stats
/*  79 */       .getDatabaseType(), false);
/*     */     
/*  81 */     sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/*  82 */       getLocalString("STATS_JVER") + ChatColor.YELLOW + stats
/*  83 */       .getJavaVersion(), false);
/*     */     
/*  85 */     sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/*  86 */       getLocalString("STATS_OS") + ChatColor.YELLOW + stats
/*  87 */       .getOperatingSystem(), false);
/*     */     
/*  89 */     sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/*  90 */       getLocalString("STATS_OS_ARCH") + ChatColor.YELLOW + stats
/*  91 */       .getOperatingSystemArch(), false);
/*     */     
/*     */ 
/*  94 */     getPlugin().sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     
/*     */ 
/*  97 */     if (args.length == 2) {
/*  98 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/*  99 */         getLocalString("STATS_PLOG") + ChatColor.YELLOW + stats
/* 100 */         .getPlayersLogged(), false);
/*     */       
/* 102 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 103 */         getLocalString("STATS_ILOG") + ChatColor.YELLOW + stats
/* 104 */         .getIPsLogged(), false);
/*     */       
/* 106 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 107 */         getLocalString("STATS_PEXM") + ChatColor.YELLOW + stats
/* 108 */         .getPlayersExempt(), false);
/*     */       
/* 110 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 111 */         getLocalString("STATS_IEXM") + ChatColor.YELLOW + stats
/* 112 */         .getIPsExempt(), false);
/*     */       
/* 114 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 115 */         getLocalString("STATS_RPEXM") + ChatColor.YELLOW + stats
/* 116 */         .getPlayersRejoinExempt(), false);
/*     */       
/* 118 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 119 */         getLocalString("STATS_RIEXM") + ChatColor.YELLOW + stats
/* 120 */         .getIPsRejoinExempt(), false);
/*     */       
/*     */ 
/* 123 */       getPlugin().sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */       
/*     */ 
/* 126 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 127 */         getLocalString("STATS_PBAN") + ChatColor.YELLOW + stats
/* 128 */         .getPlayersBanned(), false);
/*     */       
/* 130 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 131 */         getLocalString("STATS_IBAN") + ChatColor.YELLOW + stats
/* 132 */         .getIPsBanned(), false);
/*     */       
/* 134 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 135 */         getLocalString("STATS_PLOGS") + ChatColor.YELLOW + stats
/* 136 */         .getLogPlayerSession(), false);
/*     */       
/* 138 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 139 */         getLocalString("STATS_PBANS") + ChatColor.YELLOW + stats
/* 140 */         .getBannedPlayerSession(), false);
/*     */       
/* 142 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 143 */         getLocalString("STATS_PUNBANS") + ChatColor.YELLOW + stats
/* 144 */         .getUnbannedPlayerSession(), false);
/*     */       
/* 146 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 147 */         getLocalString("STATS_WARNS") + ChatColor.YELLOW + stats
/* 148 */         .getWarningIssuedSession(), false);
/*     */       
/* 150 */       sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 151 */         getLocalString("STATS_KICKS") + ChatColor.YELLOW + stats
/* 152 */         .getKickIssuedSession(), false);
/*     */       
/*     */ 
/* 155 */       getPlugin().sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     }
/*     */     
/*     */ 
/* 159 */     String isTrue = ChatColor.GREEN + "True";
/* 160 */     String isFalse = ChatColor.RED + "False";
/*     */     
/* 162 */     sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 163 */       getLocalString("STATS_SECURE") + ChatColor.YELLOW + (stats
/* 164 */       .getSecureStatus() ? isTrue : isFalse), false);
/*     */     
/* 166 */     sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 167 */       getLocalString("STATS_ACTIVE") + ChatColor.YELLOW + (stats
/* 168 */       .getActiveStatus() ? isTrue : isFalse), false);
/*     */     
/* 170 */     sendPlayerMessage(sender, ChatColor.LIGHT_PURPLE + 
/* 171 */       getLocalString("STATS_BLACKLIST") + ChatColor.YELLOW + (stats
/* 172 */       .getBlackListStatus() ? isTrue : isFalse), false);
/*     */     
/*     */ 
/* 175 */     getPlugin().sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\CmdStatus.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */