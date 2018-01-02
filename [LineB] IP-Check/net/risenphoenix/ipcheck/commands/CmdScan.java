/*     */ package net.risenphoenix.ipcheck.commands;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import net.risenphoenix.commons.Plugin;
/*     */ import net.risenphoenix.commons.commands.Command;
/*     */ import net.risenphoenix.commons.commands.CommandType;
/*     */ import net.risenphoenix.ipcheck.IPCheck;
/*     */ import net.risenphoenix.ipcheck.database.DatabaseController;
/*     */ import net.risenphoenix.ipcheck.objects.IPObject;
/*     */ import net.risenphoenix.ipcheck.objects.UserObject;
/*     */ import net.risenphoenix.ipcheck.util.ListFormatter;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.command.CommandSender;
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
/*     */ public class CmdScan
/*     */   extends Command
/*     */ {
/*     */   private IPCheck ipc;
/*     */   
/*     */   public CmdScan(Plugin plugin, String[] callArgs, CommandType type)
/*     */   {
/*  52 */     super(plugin, callArgs, type);
/*     */     
/*     */ 
/*  55 */     this.ipc = IPCheck.getInstance();
/*     */     
/*  57 */     setName(getLocalString("CMD_SCAN"));
/*  58 */     setHelp(getLocalString("HELP_SCAN"));
/*  59 */     setSyntax("ipc scan");
/*  60 */     setPermissions(new Permission[] { new Permission("ipcheck.use"), new Permission("ipcheck.scan") });
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public void onExecute(CommandSender sender, String[] args)
/*     */   {
/*  67 */     Player[] online = this.ipc.getOnlinePlayers();
/*  68 */     ArrayList<Player> detected = new ArrayList();
/*     */     
/*     */ 
/*  71 */     for (Player p : online)
/*     */     {
/*  73 */       ArrayList<String> unique_names = new ArrayList();
/*     */       
/*     */ 
/*     */ 
/*  77 */       UserObject user = this.ipc.getDatabaseController().getUserObject(p.getName());
/*     */       
/*     */ 
/*  80 */       if (user.getNumberOfIPs() != 0)
/*     */       {
/*     */ 
/*  83 */         ArrayList<IPObject> ipos = new ArrayList();
/*     */         
/*  85 */         for (String ip : user.getIPs()) {
/*  86 */           ipos.add(this.ipc.getDatabaseController().getIPObject(ip));
/*     */         }
/*     */         
/*     */ 
/*  90 */         for (IPObject ipo : ipos)
/*     */         {
/*     */ 
/*     */ 
/*  94 */           if ((ipo.getNumberOfUsers() != 1) || 
/*  95 */             (!ipo.getUsers().contains(p.getName()
/*  96 */             .toLowerCase())))
/*     */           {
/*     */ 
/*     */ 
/* 100 */             for (String s : ipo.getUsers()) {
/* 101 */               if ((!s.equalsIgnoreCase(p.getName())) && 
/* 102 */                 (!unique_names.contains(s.toLowerCase()))) {
/* 103 */                 unique_names.add(s.toLowerCase());
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */         
/*     */ 
/*     */ 
/* 111 */         if (unique_names.size() > 0) { detected.add(p);
/*     */         }
/*     */       }
/*     */     }
/* 115 */     if (detected.size() > 0)
/*     */     {
/* 117 */       Object convert = new ArrayList();
/* 118 */       Player p; for (Iterator localIterator1 = detected.iterator(); localIterator1.hasNext(); ((ArrayList)convert).add(p.getName())) { p = (Player)localIterator1.next();
/*     */       }
/*     */       
/* 121 */       sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */       
/* 123 */       sendPlayerMessage(sender, ChatColor.RED + 
/* 124 */         getLocalString("SCAN_TITLE"));
/* 125 */       sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */       
/*     */ 
/*     */ 
/* 129 */       StringBuilder list = new ListFormatter((ArrayList)convert).getFormattedList();
/*     */       
/*     */ 
/* 132 */       sendPlayerMessage(sender, list.toString(), false);
/* 133 */       sendPlayerMessage(sender, ChatColor.DARK_GRAY + "------------------------------------------------", false);
/*     */     }
/*     */     else {
/* 136 */       sendPlayerMessage(sender, getLocalString("SCAN_CLEAN"));
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\ipcheck\commands\CmdScan.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */