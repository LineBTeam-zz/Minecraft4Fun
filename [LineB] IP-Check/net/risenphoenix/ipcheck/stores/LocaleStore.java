/*     */ package net.risenphoenix.ipcheck.stores;
/*     */ 
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.stores.LocalizationStore;
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
/*     */ public class LocaleStore
/*     */   extends LocalizationStore
/*     */ {
/*     */   public LocaleStore(Plugin plugin)
/*     */   {
/*  39 */     super(plugin);
/*     */   }
/*     */   
/*     */   public void initializeStore()
/*     */   {
/*  44 */     add("NO_FIND", "The player or IP specified could not be found.");
/*  45 */     add("PLAYER_EXEMPT_SUC", "Player added to Exemption List!");
/*  46 */     add("IP_EXEMPT_SUC", "IP-address added to Exemption List!");
/*  47 */     add("EXEMPT_DEL_SUC", "Exemption successfully removed!");
/*  48 */     add("EXEMPT_DEL_ERR", "Exemption specified does not exist.");
/*  49 */     add("EXEMPTION_FAIL", "An error occurred while attempting to add the exemption.");
/*     */     
/*  51 */     add("EXEMPT_LIST_IP", "Exempt IPs:");
/*  52 */     add("EXEMPT_LIST_PLAYER", "Exempt Players:");
/*  53 */     add("TOGGLE_SECURE", "Secure-Mode");
/*  54 */     add("TOGGLE_NOTIFY", "Notify-On-Login");
/*  55 */     add("TOGGLE_DETAIL", "Descriptive-Notify");
/*  56 */     add("TOGGLE_ACTIVE", "Active-Mode");
/*  57 */     add("TOGGLE_BLACKLIST", "Country-Black-List");
/*  58 */     add("TOGGLE_WHITELIST", "Country-White-List (invert)");
/*  59 */     add("TOGGLE_GEOIP", "GeoIP-Services");
/*  60 */     add("TOGGLE_REJOIN", "Rejoin-Warning");
/*  61 */     add("TOGGLE_INVALID", "You did not specify a valid option.");
/*  62 */     add("PURGE_SUC", "Successfully purged %s.");
/*  63 */     add("PURGE_ERR", "Failed to purge %s.");
/*  64 */     add("DISABLE_ERR", "This command has been disabled via configuration.");
/*  65 */     add("SCAN_CLEAN", "No players with multiple accounts are logged in right now.");
/*     */     
/*  67 */     add("TIME_RANGE_EMPTY", "No accounts were returned within the date-range given.");
/*     */     
/*  69 */     add("RELOAD", "Reload complete!");
/*  70 */     add("NO_MODIFY", "No accounts were modified.");
/*  71 */     add("CMD_FETCH_ERR", "An error occurred while attempting to fetch this Command from the Command Manager.");
/*     */     
/*  73 */     add("METRICS_ERR", "An error occurred while initializing the Metrics system.");
/*     */     
/*     */ 
/*     */ 
/*  77 */     add("CMD_CHECK", "Check");
/*  78 */     add("CMD_BAN", "Ban");
/*  79 */     add("CMD_UNBAN", "Unban");
/*  80 */     add("CMD_EXEMPT", "Exempt");
/*  81 */     add("CMD_UNEXEMPT", "Unexempt");
/*  82 */     add("CMD_RELOAD", "Reload");
/*  83 */     add("CMD_ABOUT", "About");
/*  84 */     add("CMD_TOGGLE", "Toggle");
/*  85 */     add("CMD_EXEMPT_LIST", "Exempt-List (all)");
/*  86 */     add("CMD_EXEMPT_LIST_IP", "Exempt-List (ip)");
/*  87 */     add("CMD_EXEMPT_LIST_PLAYER", "Exempt-List (player)");
/*  88 */     add("CMD_HELP", "Help");
/*  89 */     add("CMD_KICK", "Kick");
/*  90 */     add("CMD_SBAN", "Single-Ban");
/*  91 */     add("CMD_PURGE", "Purge");
/*  92 */     add("CMD_SCAN", "Scan");
/*  93 */     add("CMD_BANALL", "Ban-All");
/*  94 */     add("CMD_UNBANALL", "Unban-All");
/*  95 */     add("CMD_BLOCK", "Block");
/*  96 */     add("CMD_UNBLOCK", "Unblock");
/*  97 */     add("CMD_PROTECT", "Protect");
/*  98 */     add("CMD_UNPROTECT", "Unprotect");
/*  99 */     add("CMD_MODBAN", "Modify Ban");
/* 100 */     add("CMD_STATUS", "Status");
/*     */     
/*     */ 
/* 103 */     add("HELP_CHECK", "Displays information about the player or IP Specified.");
/*     */     
/* 105 */     add("HELP_BAN", "Bans the player or IP specified. In addition, this command will also ban any alternative accounts associated, plus the IP-address.");
/*     */     
/*     */ 
/* 108 */     add("HELP_UNBAN", "Unbans the Player or IP specified. Additionally, unbans any associated accounts, plus the IP-address.");
/*     */     
/*     */ 
/* 111 */     add("HELP_EXEMPT", "Exempts the IP or player specified from events-checking.");
/*     */     
/* 113 */     add("HELP_UNEXEMPT", "Removes the specified exemption from file.");
/*     */     
/* 115 */     add("HELP_RELOAD", "Reloads the IP-Check plugin.");
/* 116 */     add("HELP_ABOUT", "Displays Information about IP-Check.");
/*     */     
/* 118 */     add("HELP_TOGGLE", "Toggles the specified option. For a list of options, type ''/ipc toggle help''");
/*     */     
/* 120 */     add("HELP_EXEMPT_LIST", "Displays all players/ips that are exempt from events-checking.");
/*     */     
/* 122 */     add("HELP_EXEMPT_LIST_IP", "Displays all IPs which are exempt from events-checking.");
/*     */     
/* 124 */     add("HELP_EXEMPT_LIST_PLAYER", "Displays all players who are exempt from events-checking.");
/*     */     
/* 126 */     add("HELP_HELP", "Provides information about all of the associated IP-Check Commands.");
/*     */     
/* 128 */     add("HELP_KICK", "Kicks all players linked to player or IP specified.");
/*     */     
/* 130 */     add("HELP_SBAN", "Bans a single player from your server.");
/*     */     
/* 132 */     add("HELP_PURGE", "Deletes records of the IP or Player name specified.");
/*     */     
/* 134 */     add("HELP_SCAN", "Scans all players currently online to check for any who may possess multiple accounts.");
/*     */     
/* 136 */     add("HELP_BANALL", "Bans all accounts found within specified time frame.");
/*     */     
/* 138 */     add("HELP_UNBANALL", "Unbans all accounts found within specified time frame.");
/*     */     
/* 140 */     add("HELP_BLOCK", "Blocks the country specified, preventing anyone from joining the server from said country.");
/*     */     
/* 142 */     add("HELP_UNBLOCK", "Unblocks a blocked country, allowing players from said country to join the server.");
/*     */     
/* 144 */     add("HELP_PROTECT", "Protects the specified player from being banned by IP-Check.");
/*     */     
/* 146 */     add("HELP_UNPROTECT", "Allows the specified player to be banned by IP-Check.");
/*     */     
/* 148 */     add("HELP_MODBAN", "Allows you to modify the ban message of any banned player.");
/*     */     
/* 150 */     add("HELP_STATUS", "Displays IP-Check usage statistics.");
/*     */     
/*     */ 
/* 153 */     add("SCAN_TITLE", "Player Scan Results");
/* 154 */     add("SCAN_EXPLAIN", "The following players were found to have multiple accounts:");
/*     */     
/* 156 */     add("LOGIN_WARN", "Warning!");
/* 157 */     add("LOGIN_EXPLAIN", " may have multiple accounts!");
/*     */     
/* 159 */     add("REJOIN_WARN", "Notice!");
/* 160 */     add("REJOIN_EXPLAIN", " was kicked from the server due to a previous IP-Ban.");
/*     */     
/* 162 */     add("TIME_STAMP_ERR", "An error occurred while attempting to parse a time stamp. This should never happen. If you see this message, please contact the developers at dev-bukkit and inform them of the circumstances that caused this error.");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 167 */     add("SBAN_IP_HELP", "To ban an IP address, use '/ipc ban'");
/*     */     
/* 169 */     add("EXEMPT_LIST_TALLY", "Total Exemptions:");
/* 170 */     add("TOGGLE_HEAD", "List of Toggle Options:");
/* 171 */     add("ABOUT_TEXT", "Version %s build %s by Jacob Keep (Jnk1296). All rights reserved. Built against %s %s, build %s by %s. This product includes GeoLite data created by MaxMind, available from: http://www.maxmind.com/");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/* 177 */     add("REPORT_HEAD_ONE", "Alternate Accounts found for:");
/*     */     
/* 179 */     add("REPORT_HEAD_TWO", "Alternate Accounts found:");
/*     */     
/*     */ 
/* 182 */     add("REPORT_BODY_ONE", "The following players connect with the above IP address:");
/*     */     
/* 184 */     add("REPORT_BODY_TWO", "The following players connect using the same IP address:");
/*     */     
/* 186 */     add("REPORT_BODY_THREE", "Players and IPs associated with the search term:");
/*     */     
/* 188 */     add("REPORT_BODY_FOUR", "No alternate accounts were found for this user.");
/*     */     
/*     */ 
/* 191 */     add("REPORT_FOOT_LAST_IP", "Last Known IP:");
/* 192 */     add("REPORT_FOOT_LOCATION", "Last Location:");
/* 193 */     add("LOCATION_UNAVAILABLE", "GeoIP Services unavailable.");
/*     */     
/* 195 */     add("REPORT_FOOT_PTIME", "Last Login:");
/*     */     
/* 197 */     add("REPORT_FOOT_PBAN", "Player Banned:");
/* 198 */     add("REPORT_FOOT_PEXM", "Player Exempt:");
/*     */     
/* 200 */     add("REPORT_FOOT_PPRO", "Player Protected:");
/*     */     
/* 202 */     add("REPORT_FOOT_PREXM", "Will warn on Rejoin Attempt:");
/*     */     
/* 204 */     add("REPORT_FOOT_ERROR", "Player object returned was NULL");
/*     */     
/*     */ 
/* 207 */     add("REPORT_FOOT_IBAN", "IP Banned:");
/* 208 */     add("REPORT_FOOT_IEXM", "IP Exempt:");
/* 209 */     add("REPORT_FOOT_IREXM", "Warn when banned players rejoin under this IP:");
/*     */     
/* 211 */     add("REPORT_BAN_HEAD", "Ban Reason:");
/* 212 */     add("REPORT_BAN_GENERIC", "No Message");
/*     */     
/* 214 */     add("UUID_HEAD", "UUID Matches:");
/* 215 */     add("NO_UUID_RES", "There were no results for players sharing this UUID.");
/*     */     
/*     */ 
/*     */ 
/* 219 */     add("EXEMPT_PROMPT", "Please select the Exemption you wish to create:");
/*     */     
/* 221 */     add("UNEXEMPT_PROMPT", "Please select the Exemption you wish to remove:");
/*     */     
/* 223 */     add("EXEMPT_PROMPT_CANCEL", "Prompt aborted.");
/*     */     
/*     */ 
/* 226 */     add("GEOIP_DB_MISSING", "The GeoIP.dat database could not be found. This file must be available in order for GeoIP Services to function properly. It can be downloaded at: http://geolite.maxmind.com/download/geoip/database/GeoLiteCountry/GeoIP.dat.gz");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 231 */     add("GEOIP_DB_READ_ERR", "An error occurred while attempting to read the GeoIP database.");
/*     */     
/* 233 */     add("GEOIP_DOWNLOAD", "Attempting automatic download of GeoIP database from: http://geolite.maxmind.com/download/geoip/database/GeoLiteCountry/GeoIP.dat.gz...");
/*     */     
/*     */ 
/* 236 */     add("GEOIP_DISABLED", "GeoIP Services have been disabled via configuration.");
/* 237 */     add("BLOCK_CMD_DISABLE", "The Block Commands have been disabled because the Block Manager failed to initialize.");
/*     */     
/* 239 */     add("BLOCK_SUC", "Country ID successfully added to black list!");
/* 240 */     add("BLOCK_ERR", "This country ID has already been black-listed.");
/* 241 */     add("BLOCK_HELP", "Please visit http://dev.bukkit.org/bukkit-plugins/ip-check-jnk/pages/country-ids/ for a list of country IDs.");
/*     */     
/*     */ 
/* 244 */     add("UNBLOCK_SUC", "Country ID successfully removed from black-list!");
/* 245 */     add("UNBLOCK_ERR", "This Country ID was not found in the black-list.");
/* 246 */     add("BLACK_LIST_OFF", "You must enable the Country Black-List for this configuration to take effect.");
/*     */     
/*     */ 
/* 249 */     add("PROTECT_SUC", "Player successfully protected!");
/* 250 */     add("PROTECT_IP_ERR", "You cannot protect or unprotect an IP address.");
/* 251 */     add("UNPROTECT_SUC", "Player successfully unprotected!");
/*     */     
/* 253 */     add("MODBAN_IP", "IP addresses cannot have ban messages.");
/* 254 */     add("MODBAN_SUC", "Successfully modified ban message.");
/*     */     
/*     */ 
/* 257 */     add("STATS_HEADER", "Plugin Usage Statistics:");
/* 258 */     add("STATS_PVER", "IP-Check Version: ");
/* 259 */     add("STATS_LVER", "RP-Commons Version: ");
/* 260 */     add("STATS_DB_TYPE", "Database Type: ");
/* 261 */     add("STATS_JVER", "Java Version: ");
/* 262 */     add("STATS_OS", "Operating System: ");
/* 263 */     add("STATS_OS_ARCH", "System Architecture: ");
/* 264 */     add("STATS_PLOG", "Total Players Logged: ");
/* 265 */     add("STATS_ILOG", "Total IPs Logged: ");
/* 266 */     add("STATS_PEXM", "Total Players Exempt: ");
/* 267 */     add("STATS_IEXM", "Total IPs Exempt: ");
/* 268 */     add("STATS_RPEXM", "Total Players Rejoin Exempt: ");
/* 269 */     add("STATS_RIEXM", "Total IPs Rejoin Exempt: ");
/* 270 */     add("STATS_PBAN", "Total Players Banned: ");
/* 271 */     add("STATS_IBAN", "Total IPs Banned: ");
/* 272 */     add("STATS_PLOGS", "Player Logins this Session: ");
/* 273 */     add("STATS_PBANS", "Players Banned this Session: ");
/* 274 */     add("STATS_PUNBANS", "Players Unbanned this Session: ");
/* 275 */     add("STATS_WARNS", "Login Warnings this Session: ");
/* 276 */     add("STATS_KICKS", "Kicks Issued this Session: ");
/* 277 */     add("STATS_SECURE", "Secure Mode Status: ");
/* 278 */     add("STATS_ACTIVE", "Active Mode Status: ");
/* 279 */     add("STATS_BLACKLIST", "Country Black-List Status: ");
/*     */     
/* 281 */     add("VER_COMP_ERR", "This version of IP-Check is not fully compatible with the version of Bukkit you are running. Please upgrade your Bukkit installation or downgrade IP-Check to v2.0.2.");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 286 */     add("DEV_BUILD_WARN", "NOTICE: This is a development build of IP-Check! Automatic Updater and Metrics have been disabled! If you are seeing this message, please alert the plugin developer, as this should not appear.");
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 291 */     add("CONFIG_VER_MISMATCH", "WARNING: Your configuration is out of date! Please make note of your configuration settings, then delete your config.yml and restart the plugin.");
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\stores\LocaleStore.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */