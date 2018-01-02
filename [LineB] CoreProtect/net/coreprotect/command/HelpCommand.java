/*     */ package net.coreprotect.command;
/*     */ 
/*     */ import org.bukkit.command.CommandSender;
/*     */ 
/*     */ public class HelpCommand {
/*     */   protected static void runCommand(CommandSender player, boolean permission, String[] args) {
/*   7 */     int resultc = args.length;
/*   8 */     if (permission == true) {
/*   9 */       if (resultc > 1) {
/*  10 */         String helpcommand_original = args[1];
/*  11 */         String helpcommand = args[1].toLowerCase();
/*  12 */         helpcommand = helpcommand.replaceAll("[^a-zA-Z]", "");
/*  13 */         player.sendMessage("§f----- §3CoreProtect Help §f-----");
/*  14 */         if (helpcommand.equals("help")) {
/*  15 */           player.sendMessage("§3/co help §f- Displays a list of all commands.");
/*     */         }
/*  17 */         else if ((helpcommand.equals("inspect")) || (helpcommand.equals("inspector")) || (helpcommand.equals("i"))) {
/*  18 */           player.sendMessage("§3With the inspector enabled, you can do the following:");
/*  19 */           player.sendMessage("* Left-click a block to see who placed that block.");
/*  20 */           player.sendMessage("* Right-click a block to see what adjacent block was removed.");
/*  21 */           player.sendMessage("* Place a block to see what block was removed at the location.");
/*  22 */           player.sendMessage("* Place a block in liquid (etc) to see who placed it.");
/*  23 */           player.sendMessage("* Right-click on a door, chest, etc, to see who last used it.");
/*  24 */           player.sendMessage("§7§oTip: You can use just \"/co i\" for quicker access.");
/*     */         }
/*  26 */         else if ((helpcommand.equals("rollback")) || (helpcommand.equals("rollbacks")) || (helpcommand.equals("rb")) || (helpcommand.equals("ro"))) {
/*  27 */           player.sendMessage("§3/co rollback §7<params> §f- Perform the rollback.");
/*  28 */           player.sendMessage("§3| §7u:<users> §f- Specify the user(s) to rollback.");
/*  29 */           player.sendMessage("§3| §7t:<time> §f- Specify the amount of time to rollback.");
/*  30 */           player.sendMessage("§3| §7r:<radius> §f- Specify a radius area to limit the rollback to.");
/*  31 */           player.sendMessage("§3| §7a:<action> §f- Restrict the rollback to a certain action.");
/*  32 */           player.sendMessage("§3| §7b:<blocks> §f- Restrict the rollback to certain block types.");
/*  33 */           player.sendMessage("§3| §7e:<exclude> §f- Exclude blocks/users from the rollback.");
/*  34 */           player.sendMessage("§7§oPlease see \"/co help <param>\" for detailed parameter info.");
/*     */         }
/*  36 */         else if ((helpcommand.equals("restore")) || (helpcommand.equals("restores")) || (helpcommand.equals("re")) || (helpcommand.equals("rs"))) {
/*  37 */           player.sendMessage("§3/co restore §7<params> §f- Perform the restore.");
/*  38 */           player.sendMessage("§3| §7u:<users> §f- Specify the user(s) to restore.");
/*  39 */           player.sendMessage("§3| §7t:<time> §f- Specify the amount of time to restore.");
/*  40 */           player.sendMessage("§3| §7r:<radius> §f- Specify a radius area to limit the restore to.");
/*  41 */           player.sendMessage("§3| §7a:<action> §f- Restrict the rollback to a certain action.");
/*  42 */           player.sendMessage("§3| §7b:<blocks> §f- Restrict the restore to certain block types.");
/*  43 */           player.sendMessage("§3| §7e:<exclude> §f- Exclude blocks/users from the restore.");
/*  44 */           player.sendMessage("§7§oPlease see \"/co help <param>\" for detailed parameter info.");
/*     */         }
/*  46 */         else if ((helpcommand.equals("lookup")) || (helpcommand.equals("lookups")) || (helpcommand.equals("l"))) {
/*  47 */           player.sendMessage("§3/co lookup <params>");
/*  48 */           player.sendMessage("§3/co l <params> §f- Command shortcut.");
/*  49 */           player.sendMessage("§3/co lookup <page> §f- Use after inspecting a block to view logs.");
/*     */           
/*  51 */           player.sendMessage("§7§oPlease see \"/co help params\" for detailed parameters.");
/*     */ 
/*     */         }
/*  54 */         else if ((helpcommand.equals("params")) || (helpcommand.equals("param")) || (helpcommand.equals("parameters")) || (helpcommand.equals("parameter"))) {
/*  55 */           player.sendMessage("§3/co lookup §7<params> §f- Perform the lookup.");
/*  56 */           player.sendMessage("§3| §7u:<users> §f- Specify the user(s) to lookup.");
/*  57 */           player.sendMessage("§3| §7t:<time> §f- Specify the amount of time to lookup.");
/*  58 */           player.sendMessage("§3| §7r:<radius> §f- Specify a radius area to limit the lookup to.");
/*  59 */           player.sendMessage("§3| §7a:<action> §f- Restrict the rollback to a certain action.");
/*  60 */           player.sendMessage("§3| §7b:<blocks> §f- Restrict the lookup to certain block types.");
/*  61 */           player.sendMessage("§3| §7e:<exclude> §f- Exclude blocks/users from the lookup.");
/*  62 */           player.sendMessage("§7§oPlease see \"/co help <param>\" for detailed parameter info.");
/*     */         }
/*  64 */         else if ((helpcommand.equals("purge")) || (helpcommand.equals("purges"))) {
/*  65 */           player.sendMessage("§3/co purge t:<time> §f- Delete data older than specified time.");
/*  66 */           player.sendMessage("§7§oFor example, \"/co purge t:30d\" will delete all data older than one month, and only keep the last 30 days of data.");
/*     */         }
/*  68 */         else if (helpcommand.equals("version")) {
/*  69 */           player.sendMessage("§3/co version §f- Shows the version of CoreProtect you're using.");
/*     */         }
/*  71 */         else if ((helpcommand.equals("u")) || (helpcommand.equals("user")) || (helpcommand.equals("users")) || (helpcommand.equals("uuser")) || (helpcommand.equals("uusers"))) {
/*  72 */           player.sendMessage("§3/co lookup u:<users> §f- Specify the user(s) to lookup.");
/*  73 */           player.sendMessage("§7§oExamples: [u:Notch], [u:Notch,#enderman]");
/*     */         }
/*  75 */         else if ((helpcommand.equals("t")) || (helpcommand.equals("time")) || (helpcommand.equals("ttime"))) {
/*  76 */           player.sendMessage("§3/co lookup t:<time> §f- Specify the amount of time to lookup.");
/*  77 */           player.sendMessage("§7§oExamples: [t:2w,5d,7h,2m,10s], [t:5d2h], [t:2.50h]");
/*     */         }
/*  79 */         else if ((helpcommand.equals("r")) || (helpcommand.equals("radius")) || (helpcommand.equals("rradius"))) {
/*  80 */           player.sendMessage("§3/co lookup r:<radius> §f- Specify a radius area.");
/*  81 */           player.sendMessage("§7§oExamples: [r:10] (Only make changes within 10 blocks of you)");
/*     */         }
/*  83 */         else if ((helpcommand.equals("a")) || (helpcommand.equals("action")) || (helpcommand.equals("actions")) || (helpcommand.equals("aaction"))) {
/*  84 */           player.sendMessage("§3/co lookup a:<action> §f- Restrict the lookup to a certain action.");
/*  85 */           player.sendMessage("§7§oExamples: [a:block], [a:+block], [a:-block] [a:click], [a:container], [a:kill], [a:chat], [a:command], [a:session], [a:username]");
/*     */         }
/*  87 */         else if ((helpcommand.equals("b")) || (helpcommand.equals("block")) || (helpcommand.equals("blocks")) || (helpcommand.equals("bblock")) || (helpcommand.equals("bblocks"))) {
/*  88 */           player.sendMessage("§3/co lookup b:<blocks> §f- Restrict the lookup to certain blocks.");
/*  89 */           player.sendMessage("§7§oExamples: [b:stone], [b:stone,wood,bedrock]");
/*  90 */           player.sendMessage("§7§oBlock Names: http://minecraft.gamepedia.com/Blocks");
/*     */         }
/*  92 */         else if ((helpcommand.equals("e")) || (helpcommand.equals("exclude")) || (helpcommand.equals("eexclude"))) {
/*  93 */           player.sendMessage("§3/co lookup e:<exclude> §f- Exclude blocks/users.");
/*  94 */           player.sendMessage("§7§oExamples: [e:stone], [e:Notch], e:[stone,Notch]");
/*  95 */           player.sendMessage("§7§oBlock Names: http://minecraft.gamepedia.com/Blocks");
/*     */         }
/*     */         else {
/*  98 */           player.sendMessage("§fInformation for command \"§3/co help " + helpcommand_original + "§f\" not found.");
/*     */         }
/*     */       }
/*     */       else {
/* 102 */         player.sendMessage("§f----- §3CoreProtect Help §f-----");
/* 103 */         player.sendMessage("§3/co help §7<command> §f- Display more info for that command.");
/* 104 */         player.sendMessage("§3/co §7inspect §f- Turns the block inspector on or off.");
/* 105 */         player.sendMessage("§3/co §7rollback §3<params> §f- Rollback block data.");
/* 106 */         player.sendMessage("§3/co §7restore §3<params> §f- Restore block data.");
/* 107 */         player.sendMessage("§3/co §7lookup §3<params> §f- Advanced block data lookup.");
/* 108 */         player.sendMessage("§3/co §7purge §3<params> §f- Delete old block data.");
/* 109 */         player.sendMessage("§3/co §7reload §f- Reloads the configuration file.");
/* 110 */         player.sendMessage("§3/co §7version §f- Displays the plugin version.");
/*     */       }
/*     */     }
/*     */     else {
/* 114 */       player.sendMessage("§3CoreProtect §f- You do not have permission to do that.");
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\CoreProtect_2.14.2.jar!\net\coreprotect\command\HelpCommand.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       0.7.1
 */