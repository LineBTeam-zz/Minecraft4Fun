/*    */ package de.rob1n.prospam.cmd.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.CommandWithGui;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandTriggerSimilar
/*    */   extends CommandTrigger implements CommandWithGui
/*    */ {
/*    */   public CommandTriggerSimilar(ProSpam plugin)
/*    */   {
/* 15 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getName()
/*    */   {
/* 21 */     return "trigger-similar";
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 27 */     return "Trigger a server command if someone posts a message multiple times";
/*    */   }
/*    */   
/*    */ 
/*    */   public String[] getArgs()
/*    */   {
/* 33 */     return new String[] { "[violation #]", "<commands>" };
/*    */   }
/*    */   
/*    */ 
/*    */   public String[] getAliases()
/*    */   {
/* 39 */     return new String[] { "ts", "tsi", "t-s", "t-si" };
/*    */   }
/*    */   
/*    */ 
/*    */   public void saveInSettings(int vNumber, List<String> cmds)
/*    */   {
/* 45 */     this.settings.trigger_similar.put(Integer.valueOf(vNumber), cmds);
/*    */   }
/*    */   
/*    */ 
/*    */   public HashMap<Integer, List<String>> getTriggers()
/*    */   {
/* 51 */     return this.settings.trigger_similar;
/*    */   }
/*    */   
/*    */ 
/*    */   public void showGui(Player player)
/*    */   {
/* 57 */     showGui(player, "Similar", this.settings.trigger_similar);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandTriggerSimilar.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */