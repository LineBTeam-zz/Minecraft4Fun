/*    */ package de.rob1n.prospam.cmd.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.CommandWithGui;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandTriggerFlood
/*    */   extends CommandTrigger implements CommandWithGui
/*    */ {
/*    */   public CommandTriggerFlood(ProSpam plugin)
/*    */   {
/* 15 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getName()
/*    */   {
/* 21 */     return "trigger-flood";
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 27 */     return "Trigger a server command if someone is posting too many messages in a defined time";
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
/* 39 */     return new String[] { "tf", "tfl", "t-f", "t-fl" };
/*    */   }
/*    */   
/*    */ 
/*    */   public void saveInSettings(int vNumber, List<String> cmds)
/*    */   {
/* 45 */     this.settings.trigger_flood.put(Integer.valueOf(vNumber), cmds);
/*    */   }
/*    */   
/*    */ 
/*    */   public HashMap<Integer, List<String>> getTriggers()
/*    */   {
/* 51 */     return this.settings.trigger_flood;
/*    */   }
/*    */   
/*    */ 
/*    */   public void showGui(Player player)
/*    */   {
/* 57 */     showGui(player, "Flood", this.settings.trigger_flood);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandTriggerFlood.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */