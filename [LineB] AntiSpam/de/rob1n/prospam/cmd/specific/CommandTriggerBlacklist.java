/*    */ package de.rob1n.prospam.cmd.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.CommandWithGui;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandTriggerBlacklist
/*    */   extends CommandTrigger implements CommandWithGui
/*    */ {
/*    */   public CommandTriggerBlacklist(ProSpam plugin)
/*    */   {
/* 15 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getName()
/*    */   {
/* 21 */     return "trigger-blacklist";
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 27 */     return "Trigger a server command if someone is posting a word from the blacklist";
/*    */   }
/*    */   
/*    */ 
/*    */   public String[] getArgs()
/*    */   {
/* 33 */     return new String[] { "[violation #]", "<commands>" };
/*    */   }
/*    */   
/*    */   public String getUsage() {
/* 37 */     return "trigger-blacklist ";
/*    */   }
/*    */   
/*    */ 
/*    */   public String[] getAliases()
/*    */   {
/* 43 */     return new String[] { "tb", "tbl", "t-b", "t-bl" };
/*    */   }
/*    */   
/*    */ 
/*    */   public void saveInSettings(int vNumber, List<String> cmds)
/*    */   {
/* 49 */     this.settings.trigger_blacklist.put(Integer.valueOf(vNumber), cmds);
/*    */   }
/*    */   
/*    */ 
/*    */   public HashMap<Integer, List<String>> getTriggers()
/*    */   {
/* 55 */     return this.settings.trigger_blacklist;
/*    */   }
/*    */   
/*    */ 
/*    */   public void showGui(Player player)
/*    */   {
/* 61 */     showGui(player, "Blacklist", this.settings.trigger_blacklist);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandTriggerBlacklist.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */