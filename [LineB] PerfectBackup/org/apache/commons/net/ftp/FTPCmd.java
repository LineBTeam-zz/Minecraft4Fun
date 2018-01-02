/*     */ package org.apache.commons.net.ftp;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public enum FTPCmd
/*     */ {
/*  25 */   ABOR, 
/*  26 */   ACCT, 
/*  27 */   ALLO, 
/*  28 */   APPE, 
/*  29 */   CDUP, 
/*  30 */   CWD, 
/*  31 */   DELE, 
/*  32 */   EPRT, 
/*  33 */   EPSV, 
/*  34 */   FEAT, 
/*  35 */   HELP, 
/*  36 */   LIST, 
/*  37 */   MDTM, 
/*  38 */   MFMT, 
/*  39 */   MKD, 
/*  40 */   MLSD, 
/*  41 */   MLST, 
/*  42 */   MODE, 
/*  43 */   NLST, 
/*  44 */   NOOP, 
/*  45 */   PASS, 
/*  46 */   PASV, 
/*  47 */   PORT, 
/*  48 */   PWD, 
/*  49 */   QUIT, 
/*  50 */   REIN, 
/*  51 */   REST, 
/*  52 */   RETR, 
/*  53 */   RMD, 
/*  54 */   RNFR, 
/*  55 */   RNTO, 
/*  56 */   SITE, 
/*  57 */   SMNT, 
/*  58 */   STAT, 
/*  59 */   STOR, 
/*  60 */   STOU, 
/*  61 */   STRU, 
/*  62 */   SYST, 
/*  63 */   TYPE, 
/*  64 */   USER;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*  69 */   public static final FTPCmd ABORT = ABOR;
/*  70 */   public static final FTPCmd ACCOUNT = ACCT;
/*  71 */   public static final FTPCmd ALLOCATE = ALLO;
/*  72 */   public static final FTPCmd APPEND = APPE;
/*  73 */   public static final FTPCmd CHANGE_TO_PARENT_DIRECTORY = CDUP;
/*  74 */   public static final FTPCmd CHANGE_WORKING_DIRECTORY = CWD;
/*  75 */   public static final FTPCmd DATA_PORT = PORT;
/*  76 */   public static final FTPCmd DELETE = DELE;
/*  77 */   public static final FTPCmd FEATURES = FEAT;
/*  78 */   public static final FTPCmd FILE_STRUCTURE = STRU;
/*  79 */   public static final FTPCmd GET_MOD_TIME = MDTM;
/*  80 */   public static final FTPCmd LOGOUT = QUIT;
/*  81 */   public static final FTPCmd MAKE_DIRECTORY = MKD;
/*  82 */   public static final FTPCmd MOD_TIME = MDTM;
/*  83 */   public static final FTPCmd NAME_LIST = NLST;
/*  84 */   public static final FTPCmd PASSIVE = PASV;
/*  85 */   public static final FTPCmd PASSWORD = PASS;
/*  86 */   public static final FTPCmd PRINT_WORKING_DIRECTORY = PWD;
/*  87 */   public static final FTPCmd REINITIALIZE = REIN;
/*  88 */   public static final FTPCmd REMOVE_DIRECTORY = RMD;
/*  89 */   public static final FTPCmd RENAME_FROM = RNFR;
/*  90 */   public static final FTPCmd RENAME_TO = RNTO;
/*  91 */   public static final FTPCmd REPRESENTATION_TYPE = TYPE;
/*  92 */   public static final FTPCmd RESTART = REST;
/*  93 */   public static final FTPCmd RETRIEVE = RETR;
/*  94 */   public static final FTPCmd SET_MOD_TIME = MFMT;
/*  95 */   public static final FTPCmd SITE_PARAMETERS = SITE;
/*  96 */   public static final FTPCmd STATUS = STAT;
/*  97 */   public static final FTPCmd STORE = STOR;
/*  98 */   public static final FTPCmd STORE_UNIQUE = STOU;
/*  99 */   public static final FTPCmd STRUCTURE_MOUNT = SMNT;
/* 100 */   public static final FTPCmd SYSTEM = SYST;
/* 101 */   public static final FTPCmd TRANSFER_MODE = MODE;
/* 102 */   public static final FTPCmd USERNAME = USER;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public final String getCommand()
/*     */   {
/* 113 */     return name();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\FTPCmd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */