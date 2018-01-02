/*    */ package net.risenphoenix.commons.stores;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.risenphoenix.commons.Plugin;
/*    */ import net.risenphoenix.commons.commands.Command;
/*    */ import net.risenphoenix.commons.localization.LocalizationManager;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandStore
/*    */   extends Store
/*    */ {
/*    */   private ArrayList<Command> commands;
/*    */   
/*    */   public CommandStore(Plugin plugin)
/*    */   {
/* 43 */     super(plugin);
/* 44 */     this.commands = new ArrayList();
/* 45 */     initializeStore();
/*    */   }
/*    */   
/*    */   public void initializeStore()
/*    */   {
/* 50 */     throw new UnsupportedOperationException(this.plugin.getLocalizationManager().getLocalString("NO_IMPLEMENT"));
/*    */   }
/*    */   
/*    */   public final void add(Command cmd) {
/* 54 */     this.commands.add(cmd);
/*    */   }
/*    */   
/*    */   public final ArrayList<Command> getCommands() {
/* 58 */     return this.commands;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\IP-Check.jar!\net\risenphoenix\commons\stores\CommandStore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */