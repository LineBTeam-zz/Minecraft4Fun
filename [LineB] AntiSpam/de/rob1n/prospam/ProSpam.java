/*     */ package de.rob1n.prospam;
/*     */ 
/*     */ import de.rob1n.prospam.chatter.ChatterHandler;
/*     */ import de.rob1n.prospam.cmd.CommandHandler;
/*     */ import de.rob1n.prospam.data.DataHandler;
/*     */ import de.rob1n.prospam.filter.FilterHandler;
/*     */ import de.rob1n.prospam.gui.GuiManager;
/*     */ import de.rob1n.prospam.listener.ChatListener;
/*     */ import java.util.logging.Level;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.plugin.PluginManager;
/*     */ 
/*     */ public class ProSpam extends org.bukkit.plugin.java.JavaPlugin
/*     */ {
/*     */   public static final String CMD_LABEL = "prospam";
/*     */   public static final String LOG_PREFIX = "[LineB - AntiSpam] ";
/*  23 */   private static final Logger LOGGER = Logger.getLogger("Minecraft.ProSpam");
/*     */   
/*  25 */   private CommandHandler mCommandHandler = null;
/*  26 */   private DataHandler mDataHandler = null;
/*  27 */   private ChatListener mChatListener = null;
/*  28 */   private FilterHandler mFilterHandler = null;
/*  29 */   private ChatterHandler mChatterHandler = null;
/*  30 */   private GuiManager mGuiManager = null;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public void onEnable()
/*     */   {
/*     */     try
/*     */     {
/*  39 */       Bukkit.getPlayer(new java.util.UUID(2L, 1L));
/*     */     }
/*     */     catch (NoSuchMethodError e)
/*     */     {
/*  43 */       log(Level.SEVERE, "Server version not supported. Please download an earlier version of ProSpam.");
/*  44 */       setEnabled(false);
/*  45 */       return;
/*     */     }
/*     */     
/*  48 */     this.mDataHandler = new DataHandler(this);
/*  49 */     this.mChatterHandler = new ChatterHandler(this);
/*  50 */     this.mChatListener = new ChatListener(this);
/*  51 */     this.mFilterHandler = new FilterHandler(this);
/*  52 */     this.mCommandHandler = new CommandHandler(this);
/*  53 */     this.mGuiManager = new GuiManager(this);
/*     */     
/*  55 */     getServer().getPluginManager().registerEvents(this.mChatListener, this);
/*  56 */     getCommand("prospam").setTabCompleter(this.mCommandHandler);
/*     */   }
/*     */   
/*     */ 
/*     */   public void onDisable()
/*     */   {
/*  62 */     this.mCommandHandler = null;
/*  63 */     this.mDataHandler = null;
/*  64 */     this.mChatListener = null;
/*  65 */     this.mFilterHandler = null;
/*  66 */     this.mChatterHandler = null;
/*  67 */     this.mGuiManager = null;
/*     */   }
/*     */   
/*     */ 
/*     */   public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
/*     */   {
/*  73 */     return this.mCommandHandler.execute(sender, args);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String prefixed(String message)
/*     */   {
/*  83 */     return "[" + ChatColor.ITALIC + ChatColor.YELLOW + "LineB - AntiSpam" + ChatColor.RESET + "] " + message;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public static String error(String message)
/*     */   {
/*  93 */     return prefixed("[" + ChatColor.DARK_RED + "Erro" + ChatColor.RESET + "] " + message);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public static void log(Level level, String txt)
/*     */   {
/* 101 */     LOGGER.log(level, "[LineB - AntiSpam] " + txt);
/*     */   }
/*     */   
/*     */   public DataHandler getDataHandler() {
/* 105 */     return this.mDataHandler;
/*     */   }
/*     */   
/*     */   public FilterHandler getFilterHandler()
/*     */   {
/* 110 */     return this.mFilterHandler;
/*     */   }
/*     */   
/*     */   public ChatterHandler getChatterHandler()
/*     */   {
/* 115 */     return this.mChatterHandler;
/*     */   }
/*     */   
/*     */   public CommandHandler getCommandHandler()
/*     */   {
/* 120 */     return this.mCommandHandler;
/*     */   }
/*     */   
/*     */   public GuiManager getGuiManager()
/*     */   {
/* 125 */     return this.mGuiManager;
/*     */   }
/*     */ }


