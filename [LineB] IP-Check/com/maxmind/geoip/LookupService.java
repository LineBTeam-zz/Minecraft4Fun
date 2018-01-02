/*      */ package com.maxmind.geoip;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.RandomAccessFile;
/*      */ import java.net.InetAddress;
/*      */ import java.net.UnknownHostException;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class LookupService
/*      */ {
/*   76 */   private RandomAccessFile file = null;
/*   77 */   private File databaseFile = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   82 */   private DatabaseInfo databaseInfo = null;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*   87 */   byte databaseType = 1;
/*      */   
/*      */   int[] databaseSegments;
/*      */   
/*      */   int recordLength;
/*      */   
/*      */   String licenseKey;
/*      */   
/*      */   int dboptions;
/*      */   
/*      */   byte[] dbbuffer;
/*      */   byte[] index_cache;
/*      */   long mtime;
/*      */   int last_netmask;
/*      */   private static final int US_OFFSET = 1;
/*      */   private static final int CANADA_OFFSET = 677;
/*      */   private static final int WORLD_OFFSET = 1353;
/*      */   private static final int FIPS_RANGE = 360;
/*      */   private static final int COUNTRY_BEGIN = 16776960;
/*      */   private static final int STATE_BEGIN_REV0 = 16700000;
/*      */   private static final int STATE_BEGIN_REV1 = 16000000;
/*      */   private static final int STRUCTURE_INFO_MAX_SIZE = 20;
/*      */   private static final int DATABASE_INFO_MAX_SIZE = 100;
/*      */   public static final int GEOIP_STANDARD = 0;
/*      */   public static final int GEOIP_MEMORY_CACHE = 1;
/*      */   public static final int GEOIP_CHECK_CACHE = 2;
/*      */   public static final int GEOIP_INDEX_CACHE = 4;
/*      */   public static final int GEOIP_UNKNOWN_SPEED = 0;
/*      */   public static final int GEOIP_DIALUP_SPEED = 1;
/*      */   public static final int GEOIP_CABLEDSL_SPEED = 2;
/*      */   public static final int GEOIP_CORPORATE_SPEED = 3;
/*      */   private static final int SEGMENT_RECORD_LENGTH = 3;
/*      */   private static final int STANDARD_RECORD_LENGTH = 3;
/*      */   private static final int ORG_RECORD_LENGTH = 4;
/*      */   private static final int MAX_RECORD_LENGTH = 4;
/*      */   private static final int MAX_ORG_RECORD_LENGTH = 300;
/*      */   private static final int FULL_RECORD_LENGTH = 60;
/*  124 */   private final Country UNKNOWN_COUNTRY = new Country("--", "N/A");
/*      */   
/*  126 */   private static final String[] countryCode = { "--", "AP", "EU", "AD", "AE", "AF", "AG", "AI", "AL", "AM", "CW", "AO", "AQ", "AR", "AS", "AT", "AU", "AW", "AZ", "BA", "BB", "BD", "BE", "BF", "BG", "BH", "BI", "BJ", "BM", "BN", "BO", "BR", "BS", "BT", "BV", "BW", "BY", "BZ", "CA", "CC", "CD", "CF", "CG", "CH", "CI", "CK", "CL", "CM", "CN", "CO", "CR", "CU", "CV", "CX", "CY", "CZ", "DE", "DJ", "DK", "DM", "DO", "DZ", "EC", "EE", "EG", "EH", "ER", "ES", "ET", "FI", "FJ", "FK", "FM", "FO", "FR", "SX", "GA", "GB", "GD", "GE", "GF", "GH", "GI", "GL", "GM", "GN", "GP", "GQ", "GR", "GS", "GT", "GU", "GW", "GY", "HK", "HM", "HN", "HR", "HT", "HU", "ID", "IE", "IL", "IN", "IO", "IQ", "IR", "IS", "IT", "JM", "JO", "JP", "KE", "KG", "KH", "KI", "KM", "KN", "KP", "KR", "KW", "KY", "KZ", "LA", "LB", "LC", "LI", "LK", "LR", "LS", "LT", "LU", "LV", "LY", "MA", "MC", "MD", "MG", "MH", "MK", "ML", "MM", "MN", "MO", "MP", "MQ", "MR", "MS", "MT", "MU", "MV", "MW", "MX", "MY", "MZ", "NA", "NC", "NE", "NF", "NG", "NI", "NL", "NO", "NP", "NR", "NU", "NZ", "OM", "PA", "PE", "PF", "PG", "PH", "PK", "PL", "PM", "PN", "PR", "PS", "PT", "PW", "PY", "QA", "RE", "RO", "RU", "RW", "SA", "SB", "SC", "SD", "SE", "SG", "SH", "SI", "SJ", "SK", "SL", "SM", "SN", "SO", "SR", "ST", "SV", "SY", "SZ", "TC", "TD", "TF", "TG", "TH", "TJ", "TK", "TM", "TN", "TO", "TL", "TR", "TT", "TV", "TW", "TZ", "UA", "UG", "UM", "US", "UY", "UZ", "VA", "VC", "VE", "VG", "VI", "VN", "VU", "WF", "WS", "YE", "YT", "RS", "ZA", "ZM", "ME", "ZW", "A1", "A2", "O1", "AX", "GG", "IM", "JE", "BL", "MF", "BQ", "SS", "O1" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  151 */   private static final String[] countryName = { "N/A", "Asia/Pacific Region", "Europe", "Andorra", "United Arab Emirates", "Afghanistan", "Antigua and Barbuda", "Anguilla", "Albania", "Armenia", "Curacao", "Angola", "Antarctica", "Argentina", "American Samoa", "Austria", "Australia", "Aruba", "Azerbaijan", "Bosnia and Herzegovina", "Barbados", "Bangladesh", "Belgium", "Burkina Faso", "Bulgaria", "Bahrain", "Burundi", "Benin", "Bermuda", "Brunei Darussalam", "Bolivia", "Brazil", "Bahamas", "Bhutan", "Bouvet Island", "Botswana", "Belarus", "Belize", "Canada", "Cocos (Keeling) Islands", "Congo, The Democratic Republic of the", "Central African Republic", "Congo", "Switzerland", "Cote D'Ivoire", "Cook Islands", "Chile", "Cameroon", "China", "Colombia", "Costa Rica", "Cuba", "Cape Verde", "Christmas Island", "Cyprus", "Czech Republic", "Germany", "Djibouti", "Denmark", "Dominica", "Dominican Republic", "Algeria", "Ecuador", "Estonia", "Egypt", "Western Sahara", "Eritrea", "Spain", "Ethiopia", "Finland", "Fiji", "Falkland Islands (Malvinas)", "Micronesia, Federated States of", "Faroe Islands", "France", "Sint Maarten (Dutch part)", "Gabon", "United Kingdom", "Grenada", "Georgia", "French Guiana", "Ghana", "Gibraltar", "Greenland", "Gambia", "Guinea", "Guadeloupe", "Equatorial Guinea", "Greece", "South Georgia and the South Sandwich Islands", "Guatemala", "Guam", "Guinea-Bissau", "Guyana", "Hong Kong", "Heard Island and McDonald Islands", "Honduras", "Croatia", "Haiti", "Hungary", "Indonesia", "Ireland", "Israel", "India", "British Indian Ocean Territory", "Iraq", "Iran, Islamic Republic of", "Iceland", "Italy", "Jamaica", "Jordan", "Japan", "Kenya", "Kyrgyzstan", "Cambodia", "Kiribati", "Comoros", "Saint Kitts and Nevis", "Korea, Democratic People's Republic of", "Korea, Republic of", "Kuwait", "Cayman Islands", "Kazakhstan", "Lao People's Democratic Republic", "Lebanon", "Saint Lucia", "Liechtenstein", "Sri Lanka", "Liberia", "Lesotho", "Lithuania", "Luxembourg", "Latvia", "Libya", "Morocco", "Monaco", "Moldova, Republic of", "Madagascar", "Marshall Islands", "Macedonia", "Mali", "Myanmar", "Mongolia", "Macau", "Northern Mariana Islands", "Martinique", "Mauritania", "Montserrat", "Malta", "Mauritius", "Maldives", "Malawi", "Mexico", "Malaysia", "Mozambique", "Namibia", "New Caledonia", "Niger", "Norfolk Island", "Nigeria", "Nicaragua", "Netherlands", "Norway", "Nepal", "Nauru", "Niue", "New Zealand", "Oman", "Panama", "Peru", "French Polynesia", "Papua New Guinea", "Philippines", "Pakistan", "Poland", "Saint Pierre and Miquelon", "Pitcairn Islands", "Puerto Rico", "Palestinian Territory", "Portugal", "Palau", "Paraguay", "Qatar", "Reunion", "Romania", "Russian Federation", "Rwanda", "Saudi Arabia", "Solomon Islands", "Seychelles", "Sudan", "Sweden", "Singapore", "Saint Helena", "Slovenia", "Svalbard and Jan Mayen", "Slovakia", "Sierra Leone", "San Marino", "Senegal", "Somalia", "Suriname", "Sao Tome and Principe", "El Salvador", "Syrian Arab Republic", "Swaziland", "Turks and Caicos Islands", "Chad", "French Southern Territories", "Togo", "Thailand", "Tajikistan", "Tokelau", "Turkmenistan", "Tunisia", "Tonga", "Timor-Leste", "Turkey", "Trinidad and Tobago", "Tuvalu", "Taiwan", "Tanzania, United Republic of", "Ukraine", "Uganda", "United States Minor Outlying Islands", "United States", "Uruguay", "Uzbekistan", "Holy See (Vatican City State)", "Saint Vincent and the Grenadines", "Venezuela", "Virgin Islands, British", "Virgin Islands, U.S.", "Vietnam", "Vanuatu", "Wallis and Futuna", "Samoa", "Yemen", "Mayotte", "Serbia", "South Africa", "Zambia", "Montenegro", "Zimbabwe", "Anonymous Proxy", "Satellite Provider", "Other", "Aland Islands", "Guernsey", "Isle of Man", "Jersey", "Saint Barthelemy", "Saint Martin", "Bonaire, Saint Eustatius and Saba", "South Sudan", "Other" };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static
/*      */   {
/*  218 */     if (countryCode.length != countryName.length) {
/*  219 */       throw new AssertionError("countryCode.length!=countryName.length");
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LookupService(String databaseFile)
/*      */     throws IOException
/*      */   {
/*  232 */     this(new File(databaseFile));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LookupService(File databaseFile)
/*      */     throws IOException
/*      */   {
/*  245 */     this.databaseFile = databaseFile;
/*  246 */     this.file = new RandomAccessFile(databaseFile, "r");
/*  247 */     init();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LookupService(String databaseFile, int options)
/*      */     throws IOException
/*      */   {
/*  264 */     this(new File(databaseFile), options);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public LookupService(File databaseFile, int options)
/*      */     throws IOException
/*      */   {
/*  281 */     this.databaseFile = databaseFile;
/*  282 */     this.file = new RandomAccessFile(databaseFile, "r");
/*  283 */     this.dboptions = options;
/*  284 */     init();
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private void init()
/*      */     throws IOException
/*      */   {
/*  295 */     byte[] delim = new byte[3];
/*  296 */     byte[] buf = new byte[3];
/*      */     
/*  298 */     if (this.file == null) {
/*  299 */       return;
/*      */     }
/*  301 */     if ((this.dboptions & 0x2) != 0) {
/*  302 */       this.mtime = this.databaseFile.lastModified();
/*      */     }
/*  304 */     this.file.seek(this.file.length() - 3L);
/*  305 */     for (int i = 0; i < 20; i++) {
/*  306 */       this.file.readFully(delim);
/*  307 */       if ((delim[0] == -1) && (delim[1] == -1) && (delim[2] == -1)) {
/*  308 */         this.databaseType = this.file.readByte();
/*  309 */         if (this.databaseType >= 106)
/*      */         {
/*      */ 
/*  312 */           this.databaseType = ((byte)(this.databaseType - 105));
/*      */         }
/*      */         
/*  315 */         if (this.databaseType == 7) {
/*  316 */           this.databaseSegments = new int[1];
/*  317 */           this.databaseSegments[0] = 16700000;
/*  318 */           this.recordLength = 3; break; }
/*  319 */         if (this.databaseType == 3) {
/*  320 */           this.databaseSegments = new int[1];
/*  321 */           this.databaseSegments[0] = 16000000;
/*  322 */           this.recordLength = 3; break; }
/*  323 */         if ((this.databaseType != 6) && (this.databaseType != 2) && (this.databaseType != 5) && (this.databaseType != 23) && (this.databaseType != 4) && (this.databaseType != 22) && (this.databaseType != 11) && (this.databaseType != 24) && (this.databaseType != 9) && (this.databaseType != 21) && (this.databaseType != 32) && (this.databaseType != 33) && (this.databaseType != 31) && (this.databaseType != 30)) {
/*      */           break;
/*      */         }
/*      */         
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  337 */         this.databaseSegments = new int[1];
/*  338 */         this.databaseSegments[0] = 0;
/*  339 */         if ((this.databaseType == 6) || (this.databaseType == 2) || (this.databaseType == 21) || (this.databaseType == 32) || (this.databaseType == 33) || (this.databaseType == 31) || (this.databaseType == 30) || (this.databaseType == 9))
/*      */         {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  347 */           this.recordLength = 3;
/*      */         } else {
/*  349 */           this.recordLength = 4;
/*      */         }
/*  351 */         this.file.readFully(buf);
/*  352 */         for (int j = 0; j < 3; j++) {
/*  353 */           this.databaseSegments[0] += (unsignedByteToInt(buf[j]) << j * 8);
/*      */         }
/*      */       }
/*      */       
/*      */ 
/*  358 */       this.file.seek(this.file.getFilePointer() - 4L);
/*      */     }
/*      */     
/*  361 */     if ((this.databaseType == 1) || (this.databaseType == 12) || (this.databaseType == 8) || (this.databaseType == 10))
/*      */     {
/*      */ 
/*      */ 
/*  365 */       this.databaseSegments = new int[1];
/*  366 */       this.databaseSegments[0] = 16776960;
/*  367 */       this.recordLength = 3;
/*      */     }
/*  369 */     if ((this.dboptions & 0x1) == 1) {
/*  370 */       int l = (int)this.file.length();
/*  371 */       this.dbbuffer = new byte[l];
/*  372 */       this.file.seek(0L);
/*  373 */       this.file.readFully(this.dbbuffer, 0, l);
/*  374 */       this.databaseInfo = getDatabaseInfo();
/*  375 */       this.file.close();
/*      */     }
/*  377 */     if ((this.dboptions & 0x4) != 0) {
/*  378 */       int l = this.databaseSegments[0] * this.recordLength * 2;
/*  379 */       this.index_cache = new byte[l];
/*  380 */       if (this.index_cache != null) {
/*  381 */         this.file.seek(0L);
/*  382 */         this.file.readFully(this.index_cache, 0, l);
/*      */       }
/*      */     } else {
/*  385 */       this.index_cache = null;
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */   public void close()
/*      */   {
/*      */     try
/*      */     {
/*  394 */       if (this.file != null) {
/*  395 */         this.file.close();
/*      */       }
/*  397 */       this.file = null;
/*      */     }
/*      */     catch (IOException e) {}
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Country getCountryV6(String ipAddress)
/*      */   {
/*      */     try
/*      */     {
/*  413 */       addr = InetAddress.getByName(ipAddress);
/*      */     } catch (UnknownHostException e) { InetAddress addr;
/*  415 */       return this.UNKNOWN_COUNTRY; }
/*      */     InetAddress addr;
/*  417 */     return getCountryV6(addr);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Country getCountry(String ipAddress)
/*      */   {
/*      */     try
/*      */     {
/*  430 */       addr = InetAddress.getByName(ipAddress);
/*      */     } catch (UnknownHostException e) { InetAddress addr;
/*  432 */       return this.UNKNOWN_COUNTRY; }
/*      */     InetAddress addr;
/*  434 */     return getCountry(bytesToLong(addr.getAddress()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized Country getCountry(InetAddress ipAddress)
/*      */   {
/*  445 */     return getCountry(bytesToLong(ipAddress.getAddress()));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Country getCountryV6(InetAddress addr)
/*      */   {
/*  456 */     if ((this.file == null) && ((this.dboptions & 0x1) == 0)) {
/*  457 */       throw new IllegalStateException("Database has been closed.");
/*      */     }
/*  459 */     int ret = seekCountryV6(addr) - 16776960;
/*  460 */     if (ret == 0) {
/*  461 */       return this.UNKNOWN_COUNTRY;
/*      */     }
/*  463 */     return new Country(countryCode[ret], countryName[ret]);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Country getCountry(long ipAddress)
/*      */   {
/*  475 */     if ((this.file == null) && ((this.dboptions & 0x1) == 0)) {
/*  476 */       throw new IllegalStateException("Database has been closed.");
/*      */     }
/*  478 */     int ret = seekCountry(ipAddress) - 16776960;
/*  479 */     if (ret == 0) {
/*  480 */       return this.UNKNOWN_COUNTRY;
/*      */     }
/*  482 */     return new Country(countryCode[ret], countryName[ret]);
/*      */   }
/*      */   
/*      */   public int getID(String ipAddress)
/*      */   {
/*      */     try
/*      */     {
/*  489 */       addr = InetAddress.getByName(ipAddress);
/*      */     } catch (UnknownHostException e) { InetAddress addr;
/*  491 */       return 0; }
/*      */     InetAddress addr;
/*  493 */     return getID(bytesToLong(addr.getAddress()));
/*      */   }
/*      */   
/*      */   public int getID(InetAddress ipAddress) {
/*  497 */     return getID(bytesToLong(ipAddress.getAddress()));
/*      */   }
/*      */   
/*      */   public synchronized int getID(long ipAddress) {
/*  501 */     if ((this.file == null) && ((this.dboptions & 0x1) == 0)) {
/*  502 */       throw new IllegalStateException("Database has been closed.");
/*      */     }
/*  504 */     int ret = seekCountry(ipAddress) - this.databaseSegments[0];
/*  505 */     return ret;
/*      */   }
/*      */   
/*      */   public int last_netmask() {
/*  509 */     return this.last_netmask;
/*      */   }
/*      */   
/*      */   public void netmask(int nm) {
/*  513 */     this.last_netmask = nm;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public synchronized DatabaseInfo getDatabaseInfo()
/*      */   {
/*  522 */     if (this.databaseInfo != null) {
/*  523 */       return this.databaseInfo;
/*      */     }
/*      */     try {
/*  526 */       _check_mtime();
/*  527 */       boolean hasStructureInfo = false;
/*  528 */       byte[] delim = new byte[3];
/*      */       
/*  530 */       this.file.seek(this.file.length() - 3L);
/*  531 */       for (int i = 0; i < 20; i++) {
/*  532 */         int read = this.file.read(delim);
/*  533 */         if ((read == 3) && ((delim[0] & 0xFF) == 255) && ((delim[1] & 0xFF) == 255) && ((delim[2] & 0xFF) == 255))
/*      */         {
/*  535 */           hasStructureInfo = true;
/*  536 */           break;
/*      */         }
/*  538 */         this.file.seek(this.file.getFilePointer() - 4L);
/*      */       }
/*      */       
/*  541 */       if (hasStructureInfo) {
/*  542 */         this.file.seek(this.file.getFilePointer() - 6L);
/*      */       }
/*      */       else
/*      */       {
/*  546 */         this.file.seek(this.file.length() - 3L);
/*      */       }
/*      */       
/*  549 */       for (int i = 0; i < 100; i++) {
/*  550 */         this.file.readFully(delim);
/*  551 */         if ((delim[0] == 0) && (delim[1] == 0) && (delim[2] == 0)) {
/*  552 */           byte[] dbInfo = new byte[i];
/*  553 */           this.file.readFully(dbInfo);
/*      */           
/*  555 */           this.databaseInfo = new DatabaseInfo(new String(dbInfo));
/*  556 */           return this.databaseInfo;
/*      */         }
/*  558 */         this.file.seek(this.file.getFilePointer() - 4L);
/*      */       }
/*      */     } catch (Exception e) {
/*  561 */       e.printStackTrace();
/*      */     }
/*  563 */     return new DatabaseInfo("");
/*      */   }
/*      */   
/*      */   synchronized void _check_mtime() {
/*      */     try {
/*  568 */       if ((this.dboptions & 0x2) != 0) {
/*  569 */         long t = this.databaseFile.lastModified();
/*  570 */         if (t != this.mtime)
/*      */         {
/*      */ 
/*  573 */           close();
/*  574 */           this.file = new RandomAccessFile(this.databaseFile, "r");
/*  575 */           this.databaseInfo = null;
/*  576 */           init();
/*      */         }
/*      */       }
/*      */     } catch (IOException e) {
/*  580 */       System.out.println("file not found");
/*      */     }
/*      */   }
/*      */   
/*      */   public Location getLocationV6(String str)
/*      */   {
/*      */     try
/*      */     {
/*  588 */       addr = InetAddress.getByName(str);
/*      */     } catch (UnknownHostException e) { InetAddress addr;
/*  590 */       return null;
/*      */     }
/*      */     InetAddress addr;
/*  593 */     return getLocationV6(addr);
/*      */   }
/*      */   
/*      */   public Location getLocation(InetAddress addr)
/*      */   {
/*  598 */     return getLocation(bytesToLong(addr.getAddress()));
/*      */   }
/*      */   
/*      */   public Location getLocation(String str)
/*      */   {
/*      */     try
/*      */     {
/*  605 */       addr = InetAddress.getByName(str);
/*      */     } catch (UnknownHostException e) { InetAddress addr;
/*  607 */       return null;
/*      */     }
/*      */     InetAddress addr;
/*  610 */     return getLocation(addr);
/*      */   }
/*      */   
/*      */   public synchronized Region getRegion(String str)
/*      */   {
/*      */     try {
/*  616 */       addr = InetAddress.getByName(str);
/*      */     } catch (UnknownHostException e) { InetAddress addr;
/*  618 */       return null;
/*      */     }
/*      */     InetAddress addr;
/*  621 */     return getRegion(bytesToLong(addr.getAddress()));
/*      */   }
/*      */   
/*      */   public synchronized Region getRegion(long ipnum) {
/*  625 */     Region record = new Region();
/*  626 */     int seek_region = 0;
/*  627 */     if (this.databaseType == 7) {
/*  628 */       seek_region = seekCountry(ipnum) - 16700000;
/*  629 */       char[] ch = new char[2];
/*  630 */       if (seek_region >= 1000) {
/*  631 */         record.countryCode = "US";
/*  632 */         record.countryName = "United States";
/*  633 */         ch[0] = ((char)((seek_region - 1000) / 26 + 65));
/*  634 */         ch[1] = ((char)((seek_region - 1000) % 26 + 65));
/*  635 */         record.region = new String(ch);
/*      */       } else {
/*  637 */         record.countryCode = countryCode[seek_region];
/*  638 */         record.countryName = countryName[seek_region];
/*  639 */         record.region = "";
/*      */       }
/*  641 */     } else if (this.databaseType == 3) {
/*  642 */       seek_region = seekCountry(ipnum) - 16000000;
/*  643 */       char[] ch = new char[2];
/*  644 */       if (seek_region < 1) {
/*  645 */         record.countryCode = "";
/*  646 */         record.countryName = "";
/*  647 */         record.region = "";
/*  648 */       } else if (seek_region < 677) {
/*  649 */         record.countryCode = "US";
/*  650 */         record.countryName = "United States";
/*  651 */         ch[0] = ((char)((seek_region - 1) / 26 + 65));
/*  652 */         ch[1] = ((char)((seek_region - 1) % 26 + 65));
/*  653 */         record.region = new String(ch);
/*  654 */       } else if (seek_region < 1353) {
/*  655 */         record.countryCode = "CA";
/*  656 */         record.countryName = "Canada";
/*  657 */         ch[0] = ((char)((seek_region - 677) / 26 + 65));
/*  658 */         ch[1] = ((char)((seek_region - 677) % 26 + 65));
/*  659 */         record.region = new String(ch);
/*      */       } else {
/*  661 */         record.countryCode = countryCode[((seek_region - 1353) / 360)];
/*      */         
/*  663 */         record.countryName = countryName[((seek_region - 1353) / 360)];
/*      */         
/*  665 */         record.region = "";
/*      */       }
/*      */     }
/*  668 */     return record;
/*      */   }
/*      */   
/*      */   public synchronized Location getLocationV6(InetAddress addr)
/*      */   {
/*  673 */     byte[] record_buf = new byte[60];
/*  674 */     int record_buf_offset = 0;
/*  675 */     Location record = new Location();
/*  676 */     int str_length = 0;
/*      */     
/*  678 */     double latitude = 0.0D;double longitude = 0.0D;
/*      */     try
/*      */     {
/*  681 */       int seek_country = seekCountryV6(addr);
/*  682 */       if (seek_country == this.databaseSegments[0]) {
/*  683 */         return null;
/*      */       }
/*  685 */       int record_pointer = seek_country + (2 * this.recordLength - 1) * this.databaseSegments[0];
/*      */       
/*      */ 
/*  688 */       if ((this.dboptions & 0x1) == 1)
/*      */       {
/*  690 */         System.arraycopy(this.dbbuffer, record_pointer, record_buf, 0, 
/*  691 */           Math.min(this.dbbuffer.length - record_pointer, 60));
/*      */       }
/*      */       else
/*      */       {
/*  695 */         this.file.seek(record_pointer);
/*  696 */         this.file.readFully(record_buf);
/*      */       }
/*      */       
/*      */ 
/*  700 */       record.countryCode = countryCode[unsignedByteToInt(record_buf[0])];
/*  701 */       record.countryName = countryName[unsignedByteToInt(record_buf[0])];
/*  702 */       record_buf_offset++;
/*      */       
/*      */ 
/*  705 */       while (record_buf[(record_buf_offset + str_length)] != 0)
/*  706 */         str_length++;
/*  707 */       if (str_length > 0) {
/*  708 */         record.region = new String(record_buf, record_buf_offset, str_length);
/*      */       }
/*      */       
/*  711 */       record_buf_offset += str_length + 1;
/*  712 */       str_length = 0;
/*      */       
/*      */ 
/*  715 */       while (record_buf[(record_buf_offset + str_length)] != 0)
/*  716 */         str_length++;
/*  717 */       if (str_length > 0) {
/*  718 */         record.city = new String(record_buf, record_buf_offset, str_length, "ISO-8859-1");
/*      */       }
/*      */       
/*  721 */       record_buf_offset += str_length + 1;
/*  722 */       str_length = 0;
/*      */       
/*      */ 
/*  725 */       while (record_buf[(record_buf_offset + str_length)] != 0)
/*  726 */         str_length++;
/*  727 */       if (str_length > 0) {
/*  728 */         record.postalCode = new String(record_buf, record_buf_offset, str_length);
/*      */       }
/*      */       
/*  731 */       record_buf_offset += str_length + 1;
/*      */       
/*      */ 
/*  734 */       for (int j = 0; j < 3; j++)
/*  735 */         latitude += (unsignedByteToInt(record_buf[(record_buf_offset + j)]) << j * 8);
/*  736 */       record.latitude = ((float)latitude / 10000.0F - 180.0F);
/*  737 */       record_buf_offset += 3;
/*      */       
/*      */ 
/*  740 */       for (j = 0; j < 3; j++) {
/*  741 */         longitude += (unsignedByteToInt(record_buf[(record_buf_offset + j)]) << j * 8);
/*      */       }
/*  743 */       record.longitude = ((float)longitude / 10000.0F - 180.0F);
/*      */       
/*  745 */       record.dma_code = (record.metro_code = 0);
/*  746 */       record.area_code = 0;
/*  747 */       if (this.databaseType == 2)
/*      */       {
/*  749 */         int metroarea_combo = 0;
/*  750 */         if ("US".equals(record.countryCode)) {
/*  751 */           record_buf_offset += 3;
/*  752 */           for (j = 0; j < 3; j++) {
/*  753 */             metroarea_combo += (unsignedByteToInt(record_buf[(record_buf_offset + j)]) << j * 8);
/*      */           }
/*  755 */           record.metro_code = (record.dma_code = metroarea_combo / 1000);
/*  756 */           record.area_code = (metroarea_combo % 1000);
/*      */         }
/*      */       }
/*      */     } catch (IOException e) {
/*  760 */       System.err.println("IO Exception while seting up segments");
/*      */     }
/*  762 */     return record;
/*      */   }
/*      */   
/*      */   public synchronized Location getLocation(long ipnum)
/*      */   {
/*  767 */     byte[] record_buf = new byte[60];
/*  768 */     int record_buf_offset = 0;
/*  769 */     Location record = new Location();
/*  770 */     int str_length = 0;
/*      */     
/*  772 */     double latitude = 0.0D;double longitude = 0.0D;
/*      */     try
/*      */     {
/*  775 */       int seek_country = seekCountry(ipnum);
/*  776 */       if (seek_country == this.databaseSegments[0]) {
/*  777 */         return null;
/*      */       }
/*  779 */       int record_pointer = seek_country + (2 * this.recordLength - 1) * this.databaseSegments[0];
/*      */       
/*      */ 
/*  782 */       if ((this.dboptions & 0x1) == 1)
/*      */       {
/*  784 */         System.arraycopy(this.dbbuffer, record_pointer, record_buf, 0, 
/*  785 */           Math.min(this.dbbuffer.length - record_pointer, 60));
/*      */       }
/*      */       else
/*      */       {
/*  789 */         this.file.seek(record_pointer);
/*      */         try
/*      */         {
/*  792 */           this.file.readFully(record_buf);
/*      */         }
/*      */         catch (IOException e) {}
/*      */       }
/*      */       
/*  797 */       record.countryCode = countryCode[unsignedByteToInt(record_buf[0])];
/*  798 */       record.countryName = countryName[unsignedByteToInt(record_buf[0])];
/*  799 */       record_buf_offset++;
/*      */       
/*      */ 
/*  802 */       while (record_buf[(record_buf_offset + str_length)] != 0)
/*  803 */         str_length++;
/*  804 */       if (str_length > 0) {
/*  805 */         record.region = new String(record_buf, record_buf_offset, str_length);
/*      */       }
/*      */       
/*  808 */       record_buf_offset += str_length + 1;
/*  809 */       str_length = 0;
/*      */       
/*      */ 
/*  812 */       while (record_buf[(record_buf_offset + str_length)] != 0)
/*  813 */         str_length++;
/*  814 */       if (str_length > 0) {
/*  815 */         record.city = new String(record_buf, record_buf_offset, str_length, "ISO-8859-1");
/*      */       }
/*      */       
/*  818 */       record_buf_offset += str_length + 1;
/*  819 */       str_length = 0;
/*      */       
/*      */ 
/*  822 */       while (record_buf[(record_buf_offset + str_length)] != 0)
/*  823 */         str_length++;
/*  824 */       if (str_length > 0) {
/*  825 */         record.postalCode = new String(record_buf, record_buf_offset, str_length);
/*      */       }
/*      */       
/*  828 */       record_buf_offset += str_length + 1;
/*      */       
/*      */ 
/*  831 */       for (int j = 0; j < 3; j++)
/*  832 */         latitude += (unsignedByteToInt(record_buf[(record_buf_offset + j)]) << j * 8);
/*  833 */       record.latitude = ((float)latitude / 10000.0F - 180.0F);
/*  834 */       record_buf_offset += 3;
/*      */       
/*      */ 
/*  837 */       for (j = 0; j < 3; j++) {
/*  838 */         longitude += (unsignedByteToInt(record_buf[(record_buf_offset + j)]) << j * 8);
/*      */       }
/*  840 */       record.longitude = ((float)longitude / 10000.0F - 180.0F);
/*      */       
/*  842 */       record.dma_code = (record.metro_code = 0);
/*  843 */       record.area_code = 0;
/*  844 */       if (this.databaseType == 2)
/*      */       {
/*  846 */         int metroarea_combo = 0;
/*  847 */         if (record.countryCode == "US") {
/*  848 */           record_buf_offset += 3;
/*  849 */           for (j = 0; j < 3; j++) {
/*  850 */             metroarea_combo += (unsignedByteToInt(record_buf[(record_buf_offset + j)]) << j * 8);
/*      */           }
/*  852 */           record.metro_code = (record.dma_code = metroarea_combo / 1000);
/*  853 */           record.area_code = (metroarea_combo % 1000);
/*      */         }
/*      */       }
/*      */     } catch (IOException e) {
/*  857 */       System.err.println("IO Exception while seting up segments");
/*      */     }
/*  859 */     return record;
/*      */   }
/*      */   
/*      */   public String getOrg(InetAddress addr) {
/*  863 */     return getOrg(bytesToLong(addr.getAddress()));
/*      */   }
/*      */   
/*      */   public String getOrg(String str)
/*      */   {
/*      */     try {
/*  869 */       addr = InetAddress.getByName(str);
/*      */     } catch (UnknownHostException e) { InetAddress addr;
/*  871 */       return null; }
/*      */     InetAddress addr;
/*  873 */     return getOrg(addr);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public synchronized String getOrg(long ipnum)
/*      */   {
/*  880 */     int str_length = 0;
/*  881 */     byte[] buf = new byte['Ĭ'];
/*      */     
/*      */     try
/*      */     {
/*  885 */       int seek_org = seekCountry(ipnum);
/*  886 */       if (seek_org == this.databaseSegments[0]) {
/*  887 */         return null;
/*      */       }
/*      */       
/*  890 */       int record_pointer = seek_org + (2 * this.recordLength - 1) * this.databaseSegments[0];
/*      */       
/*  892 */       if ((this.dboptions & 0x1) == 1)
/*      */       {
/*  894 */         System.arraycopy(this.dbbuffer, record_pointer, buf, 0, 
/*  895 */           Math.min(this.dbbuffer.length - record_pointer, 300));
/*      */       }
/*      */       else
/*      */       {
/*  899 */         this.file.seek(record_pointer);
/*      */         try
/*      */         {
/*  902 */           this.file.readFully(buf);
/*      */         }
/*      */         catch (IOException e) {}
/*      */       }
/*  906 */       while (buf[str_length] != 0) {
/*  907 */         str_length++;
/*      */       }
/*  909 */       return new String(buf, 0, str_length, "ISO-8859-1");
/*      */     }
/*      */     catch (IOException e) {
/*  912 */       System.out.println("IO Exception"); }
/*  913 */     return null;
/*      */   }
/*      */   
/*      */   public String getOrgV6(String str)
/*      */   {
/*      */     try
/*      */     {
/*  920 */       addr = InetAddress.getByName(str);
/*      */     } catch (UnknownHostException e) { InetAddress addr;
/*  922 */       return null; }
/*      */     InetAddress addr;
/*  924 */     return getOrgV6(addr);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   public synchronized String getOrgV6(InetAddress addr)
/*      */   {
/*  931 */     int str_length = 0;
/*  932 */     byte[] buf = new byte['Ĭ'];
/*      */     
/*      */     try
/*      */     {
/*  936 */       int seek_org = seekCountryV6(addr);
/*  937 */       if (seek_org == this.databaseSegments[0]) {
/*  938 */         return null;
/*      */       }
/*      */       
/*  941 */       int record_pointer = seek_org + (2 * this.recordLength - 1) * this.databaseSegments[0];
/*      */       
/*  943 */       if ((this.dboptions & 0x1) == 1)
/*      */       {
/*  945 */         System.arraycopy(this.dbbuffer, record_pointer, buf, 0, 
/*  946 */           Math.min(this.dbbuffer.length - record_pointer, 300));
/*      */       }
/*      */       else
/*      */       {
/*  950 */         this.file.seek(record_pointer);
/*  951 */         this.file.readFully(buf);
/*      */       }
/*  953 */       while (buf[str_length] != 0) {
/*  954 */         str_length++;
/*      */       }
/*  956 */       return new String(buf, 0, str_length, "ISO-8859-1");
/*      */     }
/*      */     catch (IOException e) {
/*  959 */       System.out.println("IO Exception"); }
/*  960 */     return null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized int seekCountryV6(InetAddress addr)
/*      */   {
/*  972 */     byte[] v6vec = addr.getAddress();
/*      */     
/*  974 */     if (v6vec.length == 4)
/*      */     {
/*      */ 
/*      */ 
/*  978 */       byte[] t = new byte[16];
/*  979 */       System.arraycopy(v6vec, 0, t, 12, 4);
/*  980 */       v6vec = t;
/*      */     }
/*      */     
/*  983 */     byte[] buf = new byte[8];
/*  984 */     int[] x = new int[2];
/*  985 */     int offset = 0;
/*  986 */     _check_mtime();
/*  987 */     for (int depth = 127; depth >= 0; depth--) {
/*  988 */       if ((this.dboptions & 0x1) == 1)
/*      */       {
/*  990 */         for (int i = 0; i < 8; i++) {
/*  991 */           buf[i] = this.dbbuffer[(2 * this.recordLength * offset + i)];
/*      */         }
/*  993 */       } else if ((this.dboptions & 0x4) != 0)
/*      */       {
/*  995 */         for (int i = 0; i < 8; i++) {
/*  996 */           buf[i] = this.index_cache[(2 * this.recordLength * offset + i)];
/*      */         }
/*      */       } else {
/*      */         try
/*      */         {
/* 1001 */           this.file.seek(2 * this.recordLength * offset);
/* 1002 */           this.file.readFully(buf);
/*      */         } catch (IOException e) {
/* 1004 */           System.out.println("IO Exception");
/*      */         }
/*      */       }
/* 1007 */       for (int i = 0; i < 2; i++) {
/* 1008 */         x[i] = 0;
/* 1009 */         for (int j = 0; j < this.recordLength; j++) {
/* 1010 */           int y = buf[(i * this.recordLength + j)];
/* 1011 */           if (y < 0) {
/* 1012 */             y += 256;
/*      */           }
/* 1014 */           x[i] += (y << j * 8);
/*      */         }
/*      */       }
/*      */       
/* 1018 */       int bnum = 127 - depth;
/* 1019 */       int idx = bnum >> 3;
/* 1020 */       int b_mask = 1 << (bnum & 0x7 ^ 0x7);
/* 1021 */       if ((v6vec[idx] & b_mask) > 0) {
/* 1022 */         if (x[1] >= this.databaseSegments[0]) {
/* 1023 */           this.last_netmask = (128 - depth);
/* 1024 */           return x[1];
/*      */         }
/* 1026 */         offset = x[1];
/*      */       } else {
/* 1028 */         if (x[0] >= this.databaseSegments[0]) {
/* 1029 */           this.last_netmask = (128 - depth);
/* 1030 */           return x[0];
/*      */         }
/* 1032 */         offset = x[0];
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1037 */     System.err.println("Error seeking country while seeking " + addr
/* 1038 */       .getHostAddress());
/* 1039 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private synchronized int seekCountry(long ipAddress)
/*      */   {
/* 1050 */     byte[] buf = new byte[8];
/* 1051 */     int[] x = new int[2];
/* 1052 */     int offset = 0;
/* 1053 */     _check_mtime();
/* 1054 */     for (int depth = 31; depth >= 0; depth--) {
/* 1055 */       if ((this.dboptions & 0x1) == 1)
/*      */       {
/* 1057 */         for (int i = 0; i < 2 * this.recordLength; i++) {
/* 1058 */           buf[i] = this.dbbuffer[(2 * this.recordLength * offset + i)];
/*      */         }
/* 1060 */       } else if ((this.dboptions & 0x4) != 0)
/*      */       {
/* 1062 */         for (int i = 0; i < 2 * this.recordLength; i++) {
/* 1063 */           buf[i] = this.index_cache[(2 * this.recordLength * offset + i)];
/*      */         }
/*      */       } else {
/*      */         try
/*      */         {
/* 1068 */           this.file.seek(2 * this.recordLength * offset);
/* 1069 */           this.file.readFully(buf);
/*      */         } catch (IOException e) {
/* 1071 */           System.out.println("IO Exception");
/*      */         }
/*      */       }
/* 1074 */       for (int i = 0; i < 2; i++) {
/* 1075 */         x[i] = 0;
/* 1076 */         for (int j = 0; j < this.recordLength; j++) {
/* 1077 */           int y = buf[(i * this.recordLength + j)];
/* 1078 */           if (y < 0) {
/* 1079 */             y += 256;
/*      */           }
/* 1081 */           x[i] += (y << j * 8);
/*      */         }
/*      */       }
/*      */       
/* 1085 */       if ((ipAddress & 1 << depth) > 0L) {
/* 1086 */         if (x[1] >= this.databaseSegments[0]) {
/* 1087 */           this.last_netmask = (32 - depth);
/* 1088 */           return x[1];
/*      */         }
/* 1090 */         offset = x[1];
/*      */       } else {
/* 1092 */         if (x[0] >= this.databaseSegments[0]) {
/* 1093 */           this.last_netmask = (32 - depth);
/* 1094 */           return x[0];
/*      */         }
/* 1096 */         offset = x[0];
/*      */       }
/*      */     }
/*      */     
/*      */ 
/* 1101 */     System.err.println("Error seeking country while seeking " + ipAddress);
/* 1102 */     return 0;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static long bytesToLong(byte[] address)
/*      */   {
/* 1113 */     long ipnum = 0L;
/* 1114 */     for (int i = 0; i < 4; i++) {
/* 1115 */       long y = address[i];
/* 1116 */       if (y < 0L) {
/* 1117 */         y += 256L;
/*      */       }
/* 1119 */       ipnum += (y << (3 - i) * 8);
/*      */     }
/* 1121 */     return ipnum;
/*      */   }
/*      */   
/*      */   private static int unsignedByteToInt(byte b) {
/* 1125 */     return b & 0xFF;
/*      */   }
/*      */ }


