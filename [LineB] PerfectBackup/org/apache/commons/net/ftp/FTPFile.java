/*     */ package org.apache.commons.net.ftp;
/*     */ 
/*     */ import java.io.Serializable;
/*     */ import java.util.Calendar;
/*     */ import java.util.Formatter;
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
/*     */ public class FTPFile
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 9010790363003271996L;
/*     */   public static final int FILE_TYPE = 0;
/*     */   public static final int DIRECTORY_TYPE = 1;
/*     */   public static final int SYMBOLIC_LINK_TYPE = 2;
/*     */   public static final int UNKNOWN_TYPE = 3;
/*     */   public static final int USER_ACCESS = 0;
/*     */   public static final int GROUP_ACCESS = 1;
/*     */   public static final int WORLD_ACCESS = 2;
/*     */   public static final int READ_PERMISSION = 0;
/*     */   public static final int WRITE_PERMISSION = 1;
/*     */   public static final int EXECUTE_PERMISSION = 2;
/*     */   private int _type;
/*     */   private int _hardLinkCount;
/*     */   private long _size;
/*     */   private String _rawListing;
/*     */   private String _user;
/*     */   private String _group;
/*     */   private String _name;
/*     */   private String _link;
/*     */   private Calendar _date;
/*     */   private final boolean[][] _permissions;
/*     */   
/*     */   public FTPFile()
/*     */   {
/*  71 */     this._permissions = new boolean[3][3];
/*  72 */     this._rawListing = null;
/*  73 */     this._type = 3;
/*     */     
/*     */ 
/*  76 */     this._hardLinkCount = 0;
/*  77 */     this._size = -1L;
/*  78 */     this._user = "";
/*  79 */     this._group = "";
/*  80 */     this._date = null;
/*  81 */     this._name = null;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setRawListing(String rawListing)
/*     */   {
/*  93 */     this._rawListing = rawListing;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getRawListing()
/*     */   {
/* 104 */     return this._rawListing;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isDirectory()
/*     */   {
/* 116 */     return this._type == 1;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isFile()
/*     */   {
/* 127 */     return this._type == 0;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isSymbolicLink()
/*     */   {
/* 138 */     return this._type == 2;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isUnknown()
/*     */   {
/* 149 */     return this._type == 3;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setType(int type)
/*     */   {
/* 161 */     this._type = type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getType()
/*     */   {
/* 173 */     return this._type;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/* 184 */     this._name = name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getName()
/*     */   {
/* 194 */     return this._name;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setSize(long size)
/*     */   {
/* 204 */     this._size = size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public long getSize()
/*     */   {
/* 215 */     return this._size;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setHardLinkCount(int links)
/*     */   {
/* 227 */     this._hardLinkCount = links;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public int getHardLinkCount()
/*     */   {
/* 239 */     return this._hardLinkCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setGroup(String group)
/*     */   {
/* 251 */     this._group = group;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getGroup()
/*     */   {
/* 263 */     return this._group;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setUser(String user)
/*     */   {
/* 275 */     this._user = user;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getUser()
/*     */   {
/* 286 */     return this._user;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setLink(String link)
/*     */   {
/* 298 */     this._link = link;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String getLink()
/*     */   {
/* 311 */     return this._link;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setTimestamp(Calendar date)
/*     */   {
/* 324 */     this._date = date;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public Calendar getTimestamp()
/*     */   {
/* 335 */     return this._date;
/*     */   }
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
/*     */   public void setPermission(int access, int permission, boolean value)
/*     */   {
/* 352 */     this._permissions[access][permission] = value;
/*     */   }
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
/*     */   public boolean hasPermission(int access, int permission)
/*     */   {
/* 368 */     return this._permissions[access][permission];
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toString()
/*     */   {
/* 379 */     return getRawListing();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public String toFormattedString()
/*     */   {
/* 391 */     StringBuilder sb = new StringBuilder();
/* 392 */     Formatter fmt = new Formatter(sb);
/* 393 */     sb.append(formatType());
/* 394 */     sb.append(permissionToString(0));
/* 395 */     sb.append(permissionToString(1));
/* 396 */     sb.append(permissionToString(2));
/* 397 */     fmt.format(" %4d", new Object[] { Integer.valueOf(getHardLinkCount()) });
/* 398 */     fmt.format(" %-8s %-8s", new Object[] { getGroup(), getUser() });
/* 399 */     fmt.format(" %8d", new Object[] { Long.valueOf(getSize()) });
/* 400 */     Calendar timestamp = getTimestamp();
/* 401 */     if (timestamp != null) {
/* 402 */       fmt.format(" %1$tY-%1$tm-%1$td %1$tH:%1$tM:%1$tS", new Object[] { timestamp });
/* 403 */       fmt.format(" %1$tZ", new Object[] { timestamp });
/* 404 */       sb.append(' ');
/*     */     }
/* 406 */     sb.append(' ');
/* 407 */     sb.append(getName());
/* 408 */     fmt.close();
/* 409 */     return sb.toString();
/*     */   }
/*     */   
/*     */   private char formatType() {
/* 413 */     switch (this._type) {
/*     */     case 0: 
/* 415 */       return '-';
/*     */     case 1: 
/* 417 */       return 'd';
/*     */     case 2: 
/* 419 */       return 'l';
/*     */     }
/* 421 */     return '?';
/*     */   }
/*     */   
/*     */   private String permissionToString(int access) {
/* 425 */     StringBuilder sb = new StringBuilder();
/* 426 */     if (hasPermission(access, 0)) {
/* 427 */       sb.append('r');
/*     */     } else {
/* 429 */       sb.append('-');
/*     */     }
/* 431 */     if (hasPermission(access, 1)) {
/* 432 */       sb.append('w');
/*     */     } else {
/* 434 */       sb.append('-');
/*     */     }
/* 436 */     if (hasPermission(access, 2)) {
/* 437 */       sb.append('x');
/*     */     } else {
/* 439 */       sb.append('-');
/*     */     }
/* 441 */     return sb.toString();
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\ftp\FTPFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */