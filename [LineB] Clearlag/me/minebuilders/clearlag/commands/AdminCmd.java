/*     */ package me.minebuilders.clearlag.commands;
/*     */ 
/*     */ import me.minebuilders.clearlag.Clearlag;
/*     */ import me.minebuilders.clearlag.Util;
/*     */ import me.minebuilders.clearlag.annotations.AutoWire;
/*     */ import me.minebuilders.clearlag.config.Config;
/*     */ import me.minebuilders.clearlag.modules.CommandModule;
/*     */ import me.minebuilders.clearlag.modules.EventModule;
/*     */ import me.minebuilders.clearlag.modules.Module;
/*     */ import org.bukkit.command.CommandSender;
/*     */ 
/*     */ public class AdminCmd extends CommandModule
/*     */ {
/*     */   @AutoWire
/*     */   private Config config;
/*     */   
/*     */   public AdminCmd()
/*     */   {
/*  19 */     this.name = "admin";
/*  20 */     this.desc = "(Control Clearlag's modules)";
/*     */   }
/*     */   
/*     */ 
/*     */   protected void run(CommandSender sender, String[] args)
/*     */   {
/*  26 */     if (args.length == 0) {
/*  27 */       Util.scm("&3=-------------[&6-&3] &b&lModule Commands &3[&6-&3]---------------=", sender);
/*  28 */       Util.scm("&4  - &3/lagg admin &creload &b<module>", sender);
/*  29 */       Util.scm("&4  - &3/lagg admin &cstop &b<module>", sender);
/*  30 */       Util.scm("&4  - &3/lagg admin &cstart &b<module>", sender);
/*  31 */       Util.scm("&4  - &3/lagg admin &clist", sender);
/*  32 */       Util.scm("&3-----------------------------------------------------", sender);
/*     */     }
/*     */     else {
/*  35 */       String cmd = args[0];
/*     */       
/*  37 */       if (cmd.equalsIgnoreCase("reload"))
/*     */       {
/*  39 */         if (args.length == 1)
/*     */         {
/*  41 */           StringBuilder sb = new StringBuilder();
/*     */           
/*  43 */           for (Module m : Clearlag.getModules()) {
/*  44 */             if ((m.isEnabled()) && (Config.containsReloadableFields(m))) {
/*  45 */               sb.append(", ").append(m.getClass().getSimpleName());
/*     */             }
/*     */           }
/*     */           
/*  49 */           Util.scm("&aHabilitado módulos recarregados: &7" + (sb.length() > 1 ? sb.toString().substring(2) : "None"), sender);
/*     */         }
/*     */         else {
/*  52 */           Module mod = Clearlag.getModule(args[1]);
/*     */           
/*  54 */           if (mod != null) {
/*  55 */             if (!Config.containsReloadableFields(mod)) {
/*  56 */               Util.msg(mod.getClass().getSimpleName() + " &cnão contém campos recarregados!", sender);
/*  57 */             } else if (!mod.isEnabled()) {
/*  58 */               Util.msg("&cEste módulo não está habilitado!", sender);
/*     */             } else {
/*     */               try {
/*  61 */                 this.config.reloadConfig();
/*     */                 
/*  63 */                 this.config.setObjectConfigValues(mod);
/*     */                 
/*  65 */                 Util.msg("&aModule " + mod.getClass().getSimpleName() + " has been reloaded!", sender);
/*     */               } catch (Exception e) {
/*  67 */                 sender.sendMessage("§cMódulo de carregamento Falhou " + mod.getClass().getSimpleName());
/*     */               }
/*     */             }
/*     */           } else {
/*  71 */             Util.msg("&cInvalid module: " + args[1], sender);
/*     */           }
/*     */         }
/*     */       }
/*  75 */       else if (cmd.equalsIgnoreCase("stop"))
/*     */       {
/*  77 */         if (args.length == 1) {
/*  78 */           StringBuilder sb = new StringBuilder();
/*     */           
/*  80 */           for (Module m : Clearlag.getModules()) {
/*  81 */             if (m.isEnabled()) {
/*  82 */               sb.append(", ").append(m.getClass().getSimpleName());
/*     */             }
/*     */           }
/*     */           
/*  86 */           Util.scm("&aMódulos habilitados: &7" + sb.toString().substring(2), sender);
/*     */         }
/*     */         else {
/*  89 */           Module mod = Clearlag.getModule(args[1]);
/*     */           
/*  91 */           if (mod != null) {
/*  92 */             if (!mod.isEnabled()) {
/*  93 */               Util.msg("&cEste modulo não está habilitado", sender);
/*     */             } else {
/*  95 */               mod.setDisabled();
/*     */               
/*  97 */               Util.msg("&aModulo " + mod.getClass().getSimpleName() + " foi &cDesligado&a!", sender);
/*     */             }
/*     */           } else {
/* 100 */             Util.msg("&cMódulo inválido: " + args[1], sender);
/*     */           }
/*     */         }
/*     */       }
/* 104 */       else if (cmd.equalsIgnoreCase("start"))
/*     */       {
/* 106 */         if (args.length == 1) {
/* 107 */           StringBuilder sb = new StringBuilder();
/*     */           
/* 109 */           for (Module m : Clearlag.getModules()) {
/* 110 */             if (!m.isEnabled())
/* 111 */               sb.append(", ").append(m.getClass().getSimpleName());
/*     */           }
/* 113 */           Util.scm("&aStart-able Modules: &7" + sb.toString().substring(2), sender);
/*     */         }
/*     */         else {
/* 116 */           Module mod = Clearlag.getModule(args[1]);
/*     */           
/* 118 */           if (mod != null) {
/* 119 */             if (mod.isEnabled()) {
/* 120 */               Util.msg("&cEsse módulo foi habilitado!", sender);
/*     */             } else {
/* 122 */               mod.setEnabled();
/*     */               
/* 124 */               Util.msg("&aModulo " + mod.getClass().getSimpleName() + " foi habilitado!", sender);
/*     */             }
/*     */           } else {
/* 127 */             Util.msg("&cInvalido modulo: " + args[1], sender);
/*     */           }
/*     */         }
/*     */       }
/* 131 */       else if (cmd.equalsIgnoreCase("list"))
/*     */       {
/* 133 */         StringBuilder events = new StringBuilder();
/* 134 */         StringBuilder commands = new StringBuilder();
/* 135 */         StringBuilder tasks = new StringBuilder();
/* 136 */         StringBuilder modules = new StringBuilder();
/*     */         
/* 138 */         for (Module m : Clearlag.getModules())
/*     */         {
/* 140 */           String s = "&8, " + (m.isEnabled() ? "&a" : "&7") + m.getClass().getSimpleName();
/*     */           
/* 142 */           if ((m instanceof EventModule)) {
/* 143 */             events.append(s);
/* 144 */           } else if ((m instanceof CommandModule)) {
/* 145 */             commands.append(s);
/* 146 */           } else if ((m instanceof me.minebuilders.clearlag.modules.TaskModule)) {
/* 147 */             tasks.append(s);
/*     */           } else {
/* 149 */             modules.append(s);
/*     */           }
/*     */         }
/* 152 */         Util.scm("&3=--------------[&6-&3] &b&lModule Status &3[&6-&3]----------------=", sender);
/* 153 */         Util.scm("              &8[&7Cinza = Disabilitado&8]   &8[&aVerde = JHabilitado&8] ", sender);
/* 154 */         Util.scm("&6Listeners: " + events.toString().substring(4), sender);
/* 155 */         Util.scm("&6Commands: " + commands.toString().substring(4), sender);
/* 156 */         Util.scm("&6Tasks: " + tasks.toString().substring(4), sender);
/* 157 */         Util.scm("&6Modules: " + modules.toString().substring(4), sender);
/*     */       }
/*     */     }
/*     */   }
/*     */ }
