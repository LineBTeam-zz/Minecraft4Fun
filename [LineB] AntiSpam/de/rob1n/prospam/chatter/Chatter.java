/*     */ package de.rob1n.prospam.chatter;
/*     */ 
/*     */ import de.rob1n.prospam.exception.ChatterHasNoMessagesException;
/*     */ import de.rob1n.prospam.exception.NotFoundException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Date;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Server;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ public class Chatter
/*     */ {
/*     */   private final UUID uuid;
/*  17 */   private final List<ChatMessage> submittedMessages = new ArrayList();
/*  18 */   private long spamStarted = 0L;
/*     */   
/*     */ 
/*     */   private static final int MAX_MESSAGE_HISTORY = 40;
/*     */   
/*  23 */   private int spamCountCaps = 0;
/*  24 */   private int spamCountChars = 0;
/*  25 */   private int spamCountFlood = 0;
/*  26 */   private int spamCountSimilar = 0;
/*  27 */   private int spamCountUrls = 0;
/*  28 */   private int spamCountBlacklist = 0;
/*     */   
/*     */   public Chatter(UUID uuid)
/*     */   {
/*  32 */     this.uuid = uuid;
/*     */   }
/*     */   
/*     */   public Chatter(UUID uuid, ChatMessage message)
/*     */   {
/*  37 */     this(uuid);
/*  38 */     this.submittedMessages.add(message);
/*     */   }
/*     */   
/*     */   public Player getPlayer() throws NotFoundException
/*     */   {
/*  43 */     Player player = Bukkit.getServer().getPlayer(this.uuid);
/*     */     
/*  45 */     if (player != null)
/*     */     {
/*  47 */       return player;
/*     */     }
/*     */     
/*  50 */     throw new NotFoundException("Jogador não encontrado.");
/*     */   }
/*     */   
/*     */   public void increaseSpamCountCaps()
/*     */   {
/*  55 */     this.spamCountCaps += 1;
/*  56 */     setSpamStarted();
/*     */   }
/*     */   
/*     */   public void increaseSpamCountChars()
/*     */   {
/*  61 */     this.spamCountChars += 1;
/*  62 */     setSpamStarted();
/*     */   }
/*     */   
/*     */   public void increaseSpamCountFlood()
/*     */   {
/*  67 */     this.spamCountFlood += 1;
/*  68 */     setSpamStarted();
/*     */   }
/*     */   
/*     */   public void increaseSpamCountSimilar()
/*     */   {
/*  73 */     this.spamCountSimilar += 1;
/*  74 */     setSpamStarted();
/*     */   }
/*     */   
/*     */   public void increaseSpamCountUrls()
/*     */   {
/*  79 */     this.spamCountUrls += 1;
/*  80 */     setSpamStarted();
/*     */   }
/*     */   
/*     */   public void increaseSpamCountBlacklist()
/*     */   {
/*  85 */     this.spamCountBlacklist += 1;
/*  86 */     setSpamStarted();
/*     */   }
/*     */   
/*     */   private void setSpamStarted()
/*     */   {
/*  91 */     if (this.spamStarted == 0L) {
/*  92 */       this.spamStarted = new Date().getTime();
/*     */     }
/*     */   }
/*     */   
/*     */   public long getSpamStarted() {
/*  97 */     return this.spamStarted;
/*     */   }
/*     */   
/*     */   public int getSpamCountCaps()
/*     */   {
/* 102 */     return this.spamCountCaps;
/*     */   }
/*     */   
/*     */   public int getSpamCountChars()
/*     */   {
/* 107 */     return this.spamCountChars;
/*     */   }
/*     */   
/*     */   public int getSpamCountFlood()
/*     */   {
/* 112 */     return this.spamCountFlood;
/*     */   }
/*     */   
/*     */   public int getSpamCountSimilar()
/*     */   {
/* 117 */     return this.spamCountSimilar;
/*     */   }
/*     */   
/*     */   public int getSpamCountUrls()
/*     */   {
/* 122 */     return this.spamCountUrls;
/*     */   }
/*     */   
/*     */   public int getSpamCountBlacklist()
/*     */   {
/* 127 */     return this.spamCountBlacklist;
/*     */   }
/*     */   
/*     */   public void resetSpamViolations()
/*     */   {
/* 132 */     this.spamCountCaps = 0;
/* 133 */     this.spamCountChars = 0;
/* 134 */     this.spamCountFlood = 0;
/* 135 */     this.spamCountSimilar = 0;
/* 136 */     this.spamCountUrls = 0;
/* 137 */     this.spamCountBlacklist = 0;
/*     */     
/* 139 */     this.spamStarted = 0L;
/*     */   }
/*     */   
/*     */ 
/*     */   public synchronized void addMessage(ChatMessage message)
/*     */   {
/*     */     try
/*     */     {
/* 147 */       if (this.submittedMessages.size() >= 40) {
/* 148 */         this.submittedMessages.remove(0);
/*     */       }
/*     */     }
/*     */     catch (Exception ignored) {}
/* 152 */     this.submittedMessages.add(message);
/*     */   }
/*     */   
/*     */   public synchronized ChatMessage getLastMessage() throws ChatterHasNoMessagesException
/*     */   {
/* 157 */     if (this.submittedMessages.size() <= 0)
/*     */     {
/* 159 */       throw new ChatterHasNoMessagesException();
/*     */     }
/*     */     
/* 162 */     return (ChatMessage)this.submittedMessages.get(this.submittedMessages.size() - 1);
/*     */   }
/*     */   
/*     */   public synchronized List<ChatMessage> getMessages()
/*     */   {
/* 167 */     return this.submittedMessages;
/*     */   }
/*     */   
/*     */   public UUID getUUID()
/*     */   {
/* 172 */     return this.uuid;
/*     */   }
/*     */ }


