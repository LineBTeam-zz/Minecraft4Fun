/*    */ package de.rob1n.prospam.cmd.specific;
/*    */ 
/*    */ import de.rob1n.prospam.ProSpam;
/*    */ import de.rob1n.prospam.cmd.CommandWithGui;
/*    */ import de.rob1n.prospam.data.specific.Settings;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandTriggerUrls
/*    */   extends CommandTrigger implements CommandWithGui
/*    */ {
/*    */   public CommandTriggerUrls(ProSpam plugin)
/*    */   {
/* 15 */     super(plugin);
/*    */   }
/*    */   
/*    */ 
/*    */   public String getName()
/*    */   {
/* 21 */     return "trigger-urls";
/*    */   }
/*    */   
/*    */ 
/*    */   public String getDescription()
/*    */   {
/* 27 */     return "Trigger a server command if someone is posting a url";
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
/* 39 */     return new String[] { "tu", "tur", "t-u", "t-ur", "turl", "t-url" };
/*    */   }
/*    */   
/*    */ 
/*    */   public void saveInSettings(int vNumber, List<String> cmds)
/*    */   {
/* 45 */     this.settings.trigger_urls.put(Integer.valueOf(vNumber), cmds);
/*    */   }
/*    */   
/*    */ 
/*    */   public HashMap<Integer, List<String>> getTriggers()
/*    */   {
/* 51 */     return this.settings.trigger_urls;
/*    */   }
/*    */   
/*    */ 
/*    */   public void showGui(Player player)
/*    */   {
/* 57 */     showGui(player, "URLs", this.settings.trigger_urls);
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\de\rob1n\prospam\cmd\specific\CommandTriggerUrls.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */