/*     */ package org.apache.commons.net.util;
/*     */ 
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
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
/*     */ public class SubnetUtils
/*     */ {
/*     */   private static final String IP_ADDRESS = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})";
/*     */   private static final String SLASH_FORMAT = "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,3})";
/*  32 */   private static final Pattern addressPattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
/*  33 */   private static final Pattern cidrPattern = Pattern.compile("(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})/(\\d{1,3})");
/*     */   
/*     */   private static final int NBITS = 32;
/*  36 */   private int netmask = 0;
/*  37 */   private int address = 0;
/*  38 */   private int network = 0;
/*  39 */   private int broadcast = 0;
/*     */   
/*     */ 
/*  42 */   private boolean inclusiveHostCount = false;
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SubnetUtils(String cidrNotation)
/*     */   {
/*  52 */     calculate(cidrNotation);
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public SubnetUtils(String address, String mask)
/*     */   {
/*  63 */     calculate(toCidrNotation(address, mask));
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public boolean isInclusiveHostCount()
/*     */   {
/*  73 */     return this.inclusiveHostCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   public void setInclusiveHostCount(boolean inclusiveHostCount)
/*     */   {
/*  83 */     this.inclusiveHostCount = inclusiveHostCount;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   public final class SubnetInfo
/*     */   {
/*     */     private SubnetInfo() {}
/*     */     
/*     */ 
/*     */ 
/*  95 */     private int netmask() { return SubnetUtils.this.netmask; }
/*  96 */     private int network() { return SubnetUtils.this.network; }
/*  97 */     private int address() { return SubnetUtils.this.address; }
/*  98 */     private int broadcast() { return SubnetUtils.this.broadcast; }
/*     */     
/*     */     private int low() {
/* 101 */       return 
/* 102 */         broadcast() - network() > 1 ? network() + 1 : SubnetUtils.this.isInclusiveHostCount() ? network() : 0;
/*     */     }
/*     */     
/*     */     private int high() {
/* 106 */       return 
/* 107 */         broadcast() - network() > 1 ? broadcast() - 1 : SubnetUtils.this.isInclusiveHostCount() ? broadcast() : 0;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public boolean isInRange(String address)
/*     */     {
/* 118 */       return isInRange(SubnetUtils.this.toInteger(address));
/*     */     }
/*     */     
/*     */     private boolean isInRange(int address) {
/* 122 */       int diff = address - low();
/* 123 */       return (diff >= 0) && (diff <= high() - low());
/*     */     }
/*     */     
/*     */     public String getBroadcastAddress() {
/* 127 */       return SubnetUtils.this.format(SubnetUtils.access$5(SubnetUtils.this, broadcast()));
/*     */     }
/*     */     
/*     */     public String getNetworkAddress() {
/* 131 */       return SubnetUtils.this.format(SubnetUtils.access$5(SubnetUtils.this, network()));
/*     */     }
/*     */     
/*     */     public String getNetmask() {
/* 135 */       return SubnetUtils.this.format(SubnetUtils.access$5(SubnetUtils.this, netmask()));
/*     */     }
/*     */     
/*     */     public String getAddress() {
/* 139 */       return SubnetUtils.this.format(SubnetUtils.access$5(SubnetUtils.this, address()));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getLowAddress()
/*     */     {
/* 149 */       return SubnetUtils.this.format(SubnetUtils.access$5(SubnetUtils.this, low()));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String getHighAddress()
/*     */     {
/* 159 */       return SubnetUtils.this.format(SubnetUtils.access$5(SubnetUtils.this, high()));
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public int getAddressCount()
/*     */     {
/* 168 */       int count = broadcast() - network() + (SubnetUtils.this.isInclusiveHostCount() ? 1 : -1);
/* 169 */       return count < 0 ? 0 : count;
/*     */     }
/*     */     
/*     */     public int asInteger(String address) {
/* 173 */       return SubnetUtils.this.toInteger(address);
/*     */     }
/*     */     
/*     */     public String getCidrSignature() {
/* 177 */       return SubnetUtils.this.toCidrNotation(
/* 178 */         SubnetUtils.access$6(SubnetUtils.this, SubnetUtils.access$5(SubnetUtils.this, address())), 
/* 179 */         SubnetUtils.access$6(SubnetUtils.this, SubnetUtils.access$5(SubnetUtils.this, netmask())));
/*     */     }
/*     */     
/*     */     public String[] getAllAddresses()
/*     */     {
/* 184 */       int ct = getAddressCount();
/* 185 */       String[] addresses = new String[ct];
/* 186 */       if (ct == 0) {
/* 187 */         return addresses;
/*     */       }
/* 189 */       int add = low(); for (int j = 0; add <= high(); j++) {
/* 190 */         addresses[j] = SubnetUtils.this.format(SubnetUtils.access$5(SubnetUtils.this, add));add++;
/*     */       }
/* 192 */       return addresses;
/*     */     }
/*     */     
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     public String toString()
/*     */     {
/* 201 */       StringBuilder buf = new StringBuilder();
/* 202 */       buf.append("CIDR Signature:\t[").append(getCidrSignature()).append("]")
/* 203 */         .append(" Netmask: [").append(getNetmask()).append("]\n")
/* 204 */         .append("Network:\t[").append(getNetworkAddress()).append("]\n")
/* 205 */         .append("Broadcast:\t[").append(getBroadcastAddress()).append("]\n")
/* 206 */         .append("First Address:\t[").append(getLowAddress()).append("]\n")
/* 207 */         .append("Last Address:\t[").append(getHighAddress()).append("]\n")
/* 208 */         .append("# Addresses:\t[").append(getAddressCount()).append("]\n");
/* 209 */       return buf.toString();
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   public final SubnetInfo getInfo()
/*     */   {
/* 217 */     return new SubnetInfo(null);
/*     */   }
/*     */   
/*     */ 
/*     */   private void calculate(String mask)
/*     */   {
/* 223 */     Matcher matcher = cidrPattern.matcher(mask);
/*     */     
/* 225 */     if (matcher.matches()) {
/* 226 */       this.address = matchAddress(matcher);
/*     */       
/*     */ 
/* 229 */       int cidrPart = rangeCheck(Integer.parseInt(matcher.group(5)), 0, 32);
/* 230 */       for (int j = 0; j < cidrPart; j++) {
/* 231 */         this.netmask |= 1 << 31 - j;
/*     */       }
/*     */       
/*     */ 
/* 235 */       this.network = (this.address & this.netmask);
/*     */       
/*     */ 
/* 238 */       this.broadcast = (this.network | this.netmask ^ 0xFFFFFFFF);
/*     */     } else {
/* 240 */       throw new IllegalArgumentException("Could not parse [" + mask + "]");
/*     */     }
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int toInteger(String address)
/*     */   {
/* 248 */     Matcher matcher = addressPattern.matcher(address);
/* 249 */     if (matcher.matches()) {
/* 250 */       return matchAddress(matcher);
/*     */     }
/* 252 */     throw new IllegalArgumentException("Could not parse [" + address + "]");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int matchAddress(Matcher matcher)
/*     */   {
/* 261 */     int addr = 0;
/* 262 */     for (int i = 1; i <= 4; i++) {
/* 263 */       int n = rangeCheck(Integer.parseInt(matcher.group(i)), -1, 255);
/* 264 */       addr |= (n & 0xFF) << 8 * (4 - i);
/*     */     }
/* 266 */     return addr;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private int[] toArray(int val)
/*     */   {
/* 273 */     int[] ret = new int[4];
/* 274 */     for (int j = 3; j >= 0; j--) {
/* 275 */       ret[j] |= val >>> 8 * (3 - j) & 0xFF;
/*     */     }
/* 277 */     return ret;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */   private String format(int[] octets)
/*     */   {
/* 284 */     StringBuilder str = new StringBuilder();
/* 285 */     for (int i = 0; i < octets.length; i++) {
/* 286 */       str.append(octets[i]);
/* 287 */       if (i != octets.length - 1) {
/* 288 */         str.append(".");
/*     */       }
/*     */     }
/* 291 */     return str.toString();
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   private int rangeCheck(int value, int begin, int end)
/*     */   {
/* 300 */     if ((value > begin) && (value <= end)) {
/* 301 */       return value;
/*     */     }
/*     */     
/* 304 */     throw new IllegalArgumentException("Value [" + value + "] not in range (" + begin + "," + end + "]");
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   int pop(int x)
/*     */   {
/* 312 */     x -= (x >>> 1 & 0x55555555);
/* 313 */     x = (x & 0x33333333) + (x >>> 2 & 0x33333333);
/* 314 */     x = x + (x >>> 4) & 0xF0F0F0F;
/* 315 */     x += (x >>> 8);
/* 316 */     x += (x >>> 16);
/* 317 */     return x & 0x3F;
/*     */   }
/*     */   
/*     */ 
/*     */ 
/*     */ 
/*     */   private String toCidrNotation(String addr, String mask)
/*     */   {
/* 325 */     return addr + "/" + pop(toInteger(mask));
/*     */   }
/*     */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\util\SubnetUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */