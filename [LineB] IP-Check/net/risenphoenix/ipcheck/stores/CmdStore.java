/*     */ package net.risenphoenix.ipcheck.stores;
/*     */ 
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.CommandType;
/*     */ import net.risenphoenix.commons.stores.CommandStore;
/*     */ import net.risenphoenix.ipcheck.commands.CmdAbout;
/*     */ import net.risenphoenix.ipcheck.commands.CmdCheck;
/*     */ import net.risenphoenix.ipcheck.commands.CmdHelp;
/*     */ import net.risenphoenix.ipcheck.commands.CmdKick;
/*     */ import net.risenphoenix.ipcheck.commands.CmdPurge;
/*     */ import net.risenphoenix.ipcheck.commands.CmdReload;
/*     */ import net.risenphoenix.ipcheck.commands.CmdScan;
/*     */ import net.risenphoenix.ipcheck.commands.CmdStatus;
/*     */ import net.risenphoenix.ipcheck.commands.ban.CmdBan;
/*     */ import net.risenphoenix.ipcheck.commands.ban.CmdBanAll;
/*     */ import net.risenphoenix.ipcheck.commands.ban.CmdModBan;
/*     */ import net.risenphoenix.ipcheck.commands.ban.CmdSBan;
/*     */ import net.risenphoenix.ipcheck.commands.ban.CmdUnban;
/*     */ import net.risenphoenix.ipcheck.commands.ban.CmdUnbanAll;
/*     */ import net.risenphoenix.ipcheck.commands.block.CmdBlock;
/*     */ import net.risenphoenix.ipcheck.commands.block.CmdUnblock;
/*     */ import net.risenphoenix.ipcheck.commands.exempt.CmdExempt;
/*     */ import net.risenphoenix.ipcheck.commands.exempt.CmdUnexempt;
/*     */ import net.risenphoenix.ipcheck.commands.exempt.list.CmdExemptListAll;
/*     */ import net.risenphoenix.ipcheck.commands.exempt.list.CmdExemptListIP;
/*     */ import net.risenphoenix.ipcheck.commands.exempt.list.CmdExemptListPlayer;
/*     */ import net.risenphoenix.ipcheck.commands.protect.CmdProtect;
/*     */ import net.risenphoenix.ipcheck.commands.protect.CmdUnprotect;
/*     */ import net.risenphoenix.ipcheck.commands.toggle.CmdToggle;
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
/*     */ public class CmdStore
/*     */   extends CommandStore
/*     */ {
/*     */   public CmdStore(Plugin plugin)
/*     */   {
/*  49 */     super(plugin);
/*     */   }
/*     */   
/*     */ 
/*     */   public void initializeStore()
/*     */   {
/*  55 */     add(new CmdAbout(this.plugin, new String[] { "ipc", "about" }, CommandType.STATIC));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  60 */     add(new CmdHelp(this.plugin, new String[] { "ipc", "help", "VAR_ARG_OPT" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  65 */     add(new CmdBan(this.plugin, new String[] { "ipc", "ban", "VAR_ARG" }, CommandType.DYNAMIC));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  70 */     add(new CmdSBan(this.plugin, new String[] { "ipc", "sban", "VAR_ARG" }, CommandType.DYNAMIC));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  75 */     add(new CmdBanAll(this.plugin, new String[] { "ipc", "banall", "VAR_ARG", "VAR_ARG" }, CommandType.DYNAMIC));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  80 */     add(new CmdUnban(this.plugin, new String[] { "ipc", "unban", "VAR_ARG" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  85 */     add(new CmdUnbanAll(this.plugin, new String[] { "ipc", "unbanall", "VAR_ARG", "VAR_ARG" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  90 */     add(new CmdModBan(this.plugin, new String[] { "ipc", "modban", "VAR_ARG" }, CommandType.DYNAMIC));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*  95 */     add(new CmdKick(this.plugin, new String[] { "ipc", "kick", "VAR_ARG" }, CommandType.DYNAMIC));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 100 */     add(new CmdExempt(this.plugin, new String[] { "ipc", "exempt", "VAR_ARG" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 105 */     add(new CmdUnexempt(this.plugin, new String[] { "ipc", "unexempt", "VAR_ARG" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 110 */     add(new CmdExemptListIP(this.plugin, new String[] { "ipc", "exempt-list", "ip" }, CommandType.STATIC));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 115 */     add(new CmdExemptListPlayer(this.plugin, new String[] { "ipc", "exempt-list", "player" }, CommandType.STATIC));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 120 */     add(new CmdExemptListAll(this.plugin, new String[] { "ipc", "exempt-list" }, CommandType.STATIC));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 125 */     add(new CmdBlock(this.plugin, new String[] { "ipc", "block", "VAR_ARG" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 130 */     add(new CmdUnblock(this.plugin, new String[] { "ipc", "unblock", "VAR_ARG" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 135 */     add(new CmdProtect(this.plugin, new String[] { "ipc", "protect", "VAR_ARG" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 140 */     add(new CmdUnprotect(this.plugin, new String[] { "ipc", "unprotect", "VAR_ARG" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 145 */     add(new CmdScan(this.plugin, new String[] { "ipc", "scan" }, CommandType.STATIC));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 150 */     add(new CmdStatus(this.plugin, new String[] { "ipc", "status", "VAR_ARG_OPT" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 155 */     add(new CmdPurge(this.plugin, new String[] { "ipc", "purge", "VAR_ARG" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 160 */     add(new CmdToggle(this.plugin, new String[] { "ipc", "toggle", "VAR_ARG" }, CommandType.VARIABLE));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 165 */     add(new CmdReload(this.plugin, new String[] { "ipc", "reload" }, CommandType.STATIC));
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 170 */     add(new CmdCheck(this.plugin, new String[] { "ipc", "VAR_ARG" }, CommandType.VARIABLE));
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\stores\CmdStore.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */