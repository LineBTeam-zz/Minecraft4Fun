/*     */ package net.risenphoenix.ipcheck.commands.ban;
/*     */ 
/*     */ import java.sql.Timestamp;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.Command;
/*     */ import net.risenphoenix.commons.commands.CommandType;
/*     */ import net.risenphoenix.commons.configuration.ConfigurationManager;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.actions.ActionBanAll;
/*     */ import net.risenphoenix.ipcheck.actions.ActionBroadcast;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*     */ import net.risenphoenix.ipcheck.objects.StatsObject;
/*     */ import net.risenphoenix.ipcheck.util.MessageParser;
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
/*     */ public class CmdBanAll
/*     */   extends Command
/*     */ {
/*     */   private IPCheck ipc;
/*     */   
/*     */   public CmdBanAll(Plugin plugin, String[] callArgs, CommandType type)
/*     */   {
/*  55 */     super(plugin, callArgs, type);
/*     */     
/*  57 */     this.ipc = IPCheck.getInstance();
/*     */     
/*  59 */     setName(getLocalString("CMD_BANALL"));
/*  60 */     setHelp(getLocalString("HELP_BANALL"));
/*  61 */     setSyntax("ipc banall <START_TIME> <STOP_TIME | now> [MESSAGE]");
/*  62 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.ban"), new Permission("ipcheck.banall") });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onExecute(CommandSender sender, String[] args)
/*     */   {
/*  70 */     executeBan(sender, args, true);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void executeBan(CommandSender sender, String[] args, boolean ban)
/*     */   {
/*  81 */     if (!getPlugin().getConfigurationManager().getBoolean("should-manage-bans")) {
/*  82 */       sendPlayerMessage(sender, getLocalString("DISABLE_ERR"));
/*  83 */       return;
/*     */     }
/*     */     
/*     */ 
/*  87 */     String message = new MessageParser(args, 3).parseMessage();
/*     */     
/*     */ 
/*  90 */     if ((message == null) || (message.length() <= 0))
/*     */     {
/*  92 */       message = IPCheck.getInstance().getConfigurationManager().getString("ban-message");
/*     */     }
/*     */     
/*  95 */     SimpleDateFormat sParse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */     
/*     */ 
/*     */     try
/*     */     {
/* 100 */       currentTime = sParse.parse(this.ipc.getDatabaseController()
/* 101 */         .getCurrentTimeStamp());
/*     */     } catch (ParseException e) {
/*     */       Date currentTime;
/* 104 */       sendPlayerMessage(sender, getLocalString("TIME_STAMP_ERR")); return;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */     Date currentTime;
/*     */     
/*     */ 
/* 112 */     ArrayList<ModifyItem> setOne = new ArrayList();
/* 113 */     ArrayList<ModifyItem> setTwo = null;
/* 114 */     boolean hasFirst = false;
/*     */     
/*     */ 
/* 117 */     for (int i = 1; i < args.length; i++) {
/* 118 */       if (!hasFirst) {
/* 119 */         hasFirst = true;
/* 120 */         setOne = getModificationItems(args[i]);
/*     */       }
/* 122 */       else if (!args[i].equalsIgnoreCase("now")) {
/* 123 */         setTwo = getModificationItems(args[i]);
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 130 */     Timestamp tsOne = new Timestamp(modifyDateStamp(new Date(currentTime.getTime()), setOne).getTime());
/*     */     
/* 132 */     Timestamp tsTwo = setTwo != null ? new Timestamp(modifyDateStamp(new Date(currentTime.getTime()), setTwo).getTime()) : null;
/*     */     
/*     */ 
/* 135 */     String timeArgOne = sParse.format(tsOne);
/*     */     
/* 137 */     String timeArgTwo = tsTwo != null ? sParse.format(tsTwo) : sParse.format(new Timestamp(currentTime.getTime()));
/*     */     
/*     */ 
/*     */ 
/* 141 */     Object[] results = new ActionBanAll(IPCheck.getInstance(), timeArgOne, timeArgTwo, message, ban).execute();
/*     */     
/*     */ 
/* 144 */     int count = ((Integer)results[0]).intValue();
/*     */     
/*     */ 
/* 147 */     if (ban) {
/* 148 */       IPCheck.getInstance().getStatisticsObject().logPlayerBan(count);
/*     */     } else {
/* 150 */       IPCheck.getInstance().getStatisticsObject().logPlayerUnban(count);
/*     */     }
/*     */     
/*     */     String broadcastMsg;
/*     */     
/*     */     String broadcastMsg;
/*     */     
/* 157 */     if (ban) { String broadcastMsg;
/* 158 */       if (count == 1) {
/* 159 */         broadcastMsg = ChatColor.GOLD + "Player " + ChatColor.RED + "%s" + ChatColor.GOLD + " was banned by " + ChatColor.GREEN + "%s" + ChatColor.GOLD + " for: %s";
/*     */       }
/*     */       else
/*     */       {
/* 163 */         broadcastMsg = ChatColor.GOLD + "Player " + ChatColor.GREEN + "%s" + ChatColor.GOLD + " banned " + ChatColor.RED + "%s" + ChatColor.GOLD + " accounts for: " + "%s";
/*     */       }
/*     */     }
/*     */     else {
/*     */       String broadcastMsg;
/* 168 */       if (count == 1) {
/* 169 */         broadcastMsg = ChatColor.GOLD + "Player " + ChatColor.RED + "%s" + ChatColor.GOLD + " was banned by " + ChatColor.GREEN + "%s.";
/*     */       }
/*     */       else
/*     */       {
/* 173 */         broadcastMsg = ChatColor.GOLD + "Player " + ChatColor.GREEN + "%s" + ChatColor.GOLD + " unbanned " + ChatColor.RED + "%s" + ChatColor.GOLD + " accounts.";
/*     */       }
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/* 180 */     if (count > 0)
/*     */     {
/* 182 */       if (ban) {
/* 183 */         if (count == 1)
/*     */         {
/* 185 */           ActionBroadcast ab = new ActionBroadcast(broadcastMsg, new String[] { results[1].toString(), sender.getName(), message }, new Permission[] { new Permission("ipcheck.seeban") }, false);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 190 */           ab.execute();
/*     */         }
/*     */         else {
/* 193 */           ActionBroadcast ab = new ActionBroadcast(broadcastMsg, new String[] { sender.getName(), count + "", message }, new Permission[] { new Permission("ipcheck.seeban") }, false);
/*     */           
/*     */ 
/*     */ 
/*     */ 
/* 198 */           ab.execute();
/*     */         }
/*     */         
/*     */       }
/* 202 */       else if (count == 1)
/*     */       {
/* 204 */         ActionBroadcast ab = new ActionBroadcast(broadcastMsg, new String[] { results[1].toString(), sender.getName() }, new Permission[] { new Permission("ipcheck.seeban") }, false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 209 */         ab.execute();
/*     */       }
/*     */       else {
/* 212 */         ActionBroadcast ab = new ActionBroadcast(broadcastMsg, new String[] { sender.getName(), count + "" }, new Permission[] { new Permission("ipcheck.seeban") }, false);
/*     */         
/*     */ 
/*     */ 
/*     */ 
/* 217 */         ab.execute();
/*     */       }
/*     */       
/*     */     }
/*     */     else {
/* 222 */       sendPlayerMessage(sender, getLocalString("NO_MODIFY"));
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */   private ArrayList<ModifyItem> getModificationItems(String arg)
/*     */   {
/* 229 */     ArrayList<ModifyItem> modItems = new ArrayList();
/*     */     
/*     */ 
/* 232 */     String[] values = arg.split("(?<=\\D)(?=\\d)|(?<=\\d)(?=\\D)");
/* 233 */     int length = values.length;
/*     */     
/*     */ 
/*     */ 
/* 237 */     if (values.length % 2 != 0) { length--;
/*     */     }
/*     */     
/* 240 */     boolean startsNum = false;
/*     */     
/*     */     try
/*     */     {
/* 244 */       Integer.parseInt(values[0]);
/* 245 */       startsNum = true;
/*     */     }
/*     */     catch (NumberFormatException localNumberFormatException) {}
/*     */     
/* 249 */     for (int i = 0; i < length - 1; i += 2)
/*     */     {
/* 251 */       if (startsNum) {
/* 252 */         modItems.add(new ModifyItem(values[(i + 1)], 
/* 253 */           Integer.parseInt(values[i])));
/*     */       } else {
/* 255 */         modItems.add(new ModifyItem(values[i], 
/* 256 */           Integer.parseInt(values[(i + 1)])));
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 261 */     return modItems;
/*     */   }
/*     */   
/*     */ 
/*     */   private Date modifyDateStamp(Date date, ArrayList<ModifyItem> modItems)
/*     */   {
/* 267 */     long stamp = date.getTime() / 1000L;
/*     */     
/*     */ 
/* 270 */     for (ModifyItem m : modItems)
/*     */     {
/* 272 */       if (m.getModifier().equalsIgnoreCase("d")) {
/* 273 */         int val = m.getValue() * 86400;
/* 274 */         stamp -= val;
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 279 */       else if (m.getModifier().equalsIgnoreCase("h")) {
/* 280 */         int val = m.getValue() * 3600;
/* 281 */         stamp -= val;
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 286 */       else if (m.getModifier().equalsIgnoreCase("m")) {
/* 287 */         int val = m.getValue() * 60;
/* 288 */         stamp -= val;
/*     */ 
/*     */ 
/*     */ 
/*     */       }
/* 293 */       else if (m.getModifier().equalsIgnoreCase("s")) {
/* 294 */         stamp -= m.getValue();
/*     */       }
/*     */     }
/*     */     
/*     */ 
/* 299 */     date = new Date(stamp * 1000L);
/* 300 */     return date;
/*     */   }
/*     */   
/*     */   private class ModifyItem
/*     */   {
/*     */     private String modifier;
/*     */     private int value;
/*     */     
/*     */     ModifyItem(String modifier, int value) {
/* 309 */       this.modifier = modifier;
/* 310 */       this.value = value;
/*     */     }
/*     */     
/*     */     public String getModifier() {
/* 314 */       return this.modifier;
/*     */     }
/*     */     
/*     */     public int getValue() {
/* 318 */       return this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\ban\CmdBanAll.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */