/*    */ package de.rob1n.prospam.cmd;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.specific.CommandDisable;
/*    */ import de.rob1n.prospam.cmd.specific.CommandHelp;
/*    */ import de.rob1n.prospam.cmd.specific.CommandTriggerCounterReset;
/*    */ import de.rob1n.prospam.exception.IllegalCommandNameException;
/*    */ 
/*    */ public class CommandList extends java.util.ArrayList<Command>
/*    */ {
/*    */   private static final long serialVersionUID = 6333444737973299230L;
/*    */   
/*    */   public CommandList(ProSpam plugin)
/*    */   {
/* 15 */     add(new CommandHelp(plugin));
/* 16 */     add(new de.rob1n.prospam.cmd.specific.CommandEnable(plugin));
/* 17 */     add(new CommandDisable(plugin));
/* 18 */     add(new de.rob1n.prospam.cmd.specific.CommandReload(plugin));
/* 19 */     add(new de.rob1n.prospam.cmd.specific.CommandFilterEnable(plugin));
/* 20 */     add(new de.rob1n.prospam.cmd.specific.CommandFilterDisable(plugin));
/* 21 */     add(new de.rob1n.prospam.cmd.specific.CommandFilters(plugin));
/* 22 */     add(new de.rob1n.prospam.cmd.specific.CommandCounter(plugin));
/* 23 */     add(new de.rob1n.prospam.cmd.specific.CommandWhitelistEnable(plugin));
/* 24 */     add(new de.rob1n.prospam.cmd.specific.CommandWhitelistDisable(plugin));
/* 25 */     add(new de.rob1n.prospam.cmd.specific.CommandMaxCaps(plugin));
/* 26 */     add(new de.rob1n.prospam.cmd.specific.CommandLinesLocked(plugin));
/* 27 */     add(new de.rob1n.prospam.cmd.specific.CommandLinesSimilar(plugin));
/* 28 */     add(new de.rob1n.prospam.cmd.specific.CommandTriggerEnable(plugin));
/* 29 */     add(new de.rob1n.prospam.cmd.specific.CommandTriggerDisable(plugin));
/* 30 */     add(new CommandTriggerCounterReset(plugin));
/* 31 */     add(new de.rob1n.prospam.cmd.specific.CommandTriggers(plugin));
/* 32 */     add(new de.rob1n.prospam.cmd.specific.CommandTriggerCaps(plugin));
/* 33 */     add(new de.rob1n.prospam.cmd.specific.CommandTriggerChars(plugin));
/* 34 */     add(new de.rob1n.prospam.cmd.specific.CommandTriggerFlood(plugin));
/* 35 */     add(new de.rob1n.prospam.cmd.specific.CommandTriggerSimilar(plugin));
/* 36 */     add(new de.rob1n.prospam.cmd.specific.CommandTriggerUrls(plugin));
/* 37 */     add(new de.rob1n.prospam.cmd.specific.CommandTriggerBlacklist(plugin));
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public Command get(String cmdName)
/*    */     throws IllegalCommandNameException
/*    */   {
/* 49 */     for (Command command : this)
/*    */     {
/* 51 */       Command cmd = command;
/*    */       
/* 53 */       if (cmd.getName().equalsIgnoreCase(cmdName)) { return cmd;
/*    */       }
/* 55 */       for (String alias : cmd.getAliases())
/*    */       {
/* 57 */         if (alias.equalsIgnoreCase(cmdName)) { return cmd;
/*    */         }
/*    */       }
/*    */     }
/* 61 */     throw new IllegalCommandNameException();
/*    */   }
/*    */ }

