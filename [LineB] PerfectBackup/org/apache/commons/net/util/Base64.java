/*      */ package org.apache.commons.net.util;
/*      */ 
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.math.BigInteger;
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
/*      */ public class Base64
/*      */ {
/*      */   private static final int DEFAULT_BUFFER_RESIZE_FACTOR = 2;
/*      */   private static final int DEFAULT_BUFFER_SIZE = 8192;
/*      */   static final int CHUNK_SIZE = 76;
/*   73 */   private static final byte[] CHUNK_SEPARATOR = { 13, 10 };
/*      */   
/*   75 */   private static final byte[] EMPTY_BYTE_ARRAY = new byte[0];
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   84 */   private static final byte[] STANDARD_ENCODE_TABLE = {
/*   85 */     65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 
/*   86 */     78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 
/*   87 */     97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 
/*   88 */     110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 
/*   89 */     48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*   97 */   private static final byte[] URL_SAFE_ENCODE_TABLE = {
/*   98 */     65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 
/*   99 */     78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 
/*  100 */     97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 
/*  101 */     110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 
/*  102 */     48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 45, 95 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final byte PAD = 61;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  121 */   private static final byte[] DECODE_TABLE = {
/*  122 */     -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
/*  123 */     -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 
/*  124 */     -1, -1, -1, -1, -1, -1, -1, -1, -1, 62, -1, 62, -1, 63, 52, 53, 54, 
/*  125 */     55, 56, 57, 58, 59, 60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 
/*  126 */     5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 
/*  127 */     24, 25, -1, -1, -1, -1, 63, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 
/*  128 */     35, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51 };
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int MASK_6BITS = 63;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static final int MASK_8BITS = 255;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final byte[] encodeTable;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final int lineLength;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final byte[] lineSeparator;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final int decodeSize;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private final int encodeSize;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private byte[] buffer;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int pos;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int readPos;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int currentLinePos;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int modulus;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private boolean eof;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private int x;
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Base64()
/*      */   {
/*  221 */     this(false);
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
/*      */ 
/*      */ 
/*      */ 
/*      */   public Base64(boolean urlSafe)
/*      */   {
/*  240 */     this(76, CHUNK_SEPARATOR, urlSafe);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public Base64(int lineLength)
/*      */   {
/*  262 */     this(lineLength, CHUNK_SEPARATOR);
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
/*      */   public Base64(int lineLength, byte[] lineSeparator)
/*      */   {
/*  288 */     this(lineLength, lineSeparator, false);
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
/*      */   public Base64(int lineLength, byte[] lineSeparator, boolean urlSafe)
/*      */   {
/*  317 */     if (lineSeparator == null) {
/*  318 */       lineLength = 0;
/*  319 */       lineSeparator = EMPTY_BYTE_ARRAY;
/*      */     }
/*  321 */     this.lineLength = (lineLength > 0 ? lineLength / 4 * 4 : 0);
/*  322 */     this.lineSeparator = new byte[lineSeparator.length];
/*  323 */     System.arraycopy(lineSeparator, 0, this.lineSeparator, 0, lineSeparator.length);
/*  324 */     if (lineLength > 0) {
/*  325 */       this.encodeSize = (4 + lineSeparator.length);
/*      */     } else {
/*  327 */       this.encodeSize = 4;
/*      */     }
/*  329 */     this.decodeSize = (this.encodeSize - 1);
/*  330 */     if (containsBase64Byte(lineSeparator)) {
/*  331 */       String sep = newStringUtf8(lineSeparator);
/*  332 */       throw new IllegalArgumentException("lineSeperator must not contain base64 characters: [" + sep + "]");
/*      */     }
/*  334 */     this.encodeTable = (urlSafe ? URL_SAFE_ENCODE_TABLE : STANDARD_ENCODE_TABLE);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public boolean isUrlSafe()
/*      */   {
/*  344 */     return this.encodeTable == URL_SAFE_ENCODE_TABLE;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   boolean hasData()
/*      */   {
/*  353 */     return this.buffer != null;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   int avail()
/*      */   {
/*  362 */     return this.buffer != null ? this.pos - this.readPos : 0;
/*      */   }
/*      */   
/*      */   private void resizeBuffer()
/*      */   {
/*  367 */     if (this.buffer == null) {
/*  368 */       this.buffer = new byte[' '];
/*  369 */       this.pos = 0;
/*  370 */       this.readPos = 0;
/*      */     } else {
/*  372 */       byte[] b = new byte[this.buffer.length * 2];
/*  373 */       System.arraycopy(this.buffer, 0, b, 0, this.buffer.length);
/*  374 */       this.buffer = b;
/*      */     }
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
/*      */   int readResults(byte[] b, int bPos, int bAvail)
/*      */   {
/*  391 */     if (this.buffer != null) {
/*  392 */       int len = Math.min(avail(), bAvail);
/*  393 */       if (this.buffer != b) {
/*  394 */         System.arraycopy(this.buffer, this.readPos, b, bPos, len);
/*  395 */         this.readPos += len;
/*  396 */         if (this.readPos >= this.pos) {
/*  397 */           this.buffer = null;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  402 */         this.buffer = null;
/*      */       }
/*  404 */       return len;
/*      */     }
/*  406 */     return this.eof ? -1 : 0;
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
/*      */ 
/*      */   void setInitialBuffer(byte[] out, int outPos, int outAvail)
/*      */   {
/*  423 */     if ((out != null) && (out.length == outAvail)) {
/*  424 */       this.buffer = out;
/*  425 */       this.pos = outPos;
/*  426 */       this.readPos = outPos;
/*      */     }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   void encode(byte[] in, int inPos, int inAvail)
/*      */   {
/*  449 */     if (this.eof) {
/*  450 */       return;
/*      */     }
/*      */     
/*      */ 
/*  454 */     if (inAvail < 0) {
/*  455 */       this.eof = true;
/*  456 */       if ((this.buffer == null) || (this.buffer.length - this.pos < this.encodeSize)) {
/*  457 */         resizeBuffer();
/*      */       }
/*  459 */       switch (this.modulus) {
/*      */       case 1: 
/*  461 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 2 & 0x3F)];
/*  462 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x << 4 & 0x3F)];
/*      */         
/*  464 */         if (this.encodeTable == STANDARD_ENCODE_TABLE) {
/*  465 */           this.buffer[(this.pos++)] = 61;
/*  466 */           this.buffer[(this.pos++)] = 61;
/*      */         }
/*  468 */         break;
/*      */       
/*      */       case 2: 
/*  471 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 10 & 0x3F)];
/*  472 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 4 & 0x3F)];
/*  473 */         this.buffer[(this.pos++)] = this.encodeTable[(this.x << 2 & 0x3F)];
/*      */         
/*  475 */         if (this.encodeTable == STANDARD_ENCODE_TABLE) {
/*  476 */           this.buffer[(this.pos++)] = 61;
/*      */         }
/*  478 */         break;
/*      */       }
/*      */       
/*      */       
/*  482 */       if ((this.lineLength > 0) && (this.pos > 0)) {
/*  483 */         System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
/*  484 */         this.pos += this.lineSeparator.length;
/*      */       }
/*      */     } else {
/*  487 */       for (int i = 0; i < inAvail; i++) {
/*  488 */         if ((this.buffer == null) || (this.buffer.length - this.pos < this.encodeSize)) {
/*  489 */           resizeBuffer();
/*      */         }
/*  491 */         this.modulus = (++this.modulus % 3);
/*  492 */         int b = in[(inPos++)];
/*  493 */         if (b < 0) {
/*  494 */           b += 256;
/*      */         }
/*  496 */         this.x = ((this.x << 8) + b);
/*  497 */         if (this.modulus == 0) {
/*  498 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 18 & 0x3F)];
/*  499 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 12 & 0x3F)];
/*  500 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x >> 6 & 0x3F)];
/*  501 */           this.buffer[(this.pos++)] = this.encodeTable[(this.x & 0x3F)];
/*  502 */           this.currentLinePos += 4;
/*  503 */           if ((this.lineLength > 0) && (this.lineLength <= this.currentLinePos)) {
/*  504 */             System.arraycopy(this.lineSeparator, 0, this.buffer, this.pos, this.lineSeparator.length);
/*  505 */             this.pos += this.lineSeparator.length;
/*  506 */             this.currentLinePos = 0;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
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
/*      */   void decode(byte[] in, int inPos, int inAvail)
/*      */   {
/*  537 */     if (this.eof) {
/*  538 */       return;
/*      */     }
/*  540 */     if (inAvail < 0) {
/*  541 */       this.eof = true;
/*      */     }
/*  543 */     for (int i = 0; i < inAvail; i++) {
/*  544 */       if ((this.buffer == null) || (this.buffer.length - this.pos < this.decodeSize)) {
/*  545 */         resizeBuffer();
/*      */       }
/*  547 */       byte b = in[(inPos++)];
/*  548 */       if (b == 61)
/*      */       {
/*  550 */         this.eof = true;
/*  551 */         break;
/*      */       }
/*  553 */       if ((b >= 0) && (b < DECODE_TABLE.length)) {
/*  554 */         int result = DECODE_TABLE[b];
/*  555 */         if (result >= 0) {
/*  556 */           this.modulus = (++this.modulus % 4);
/*  557 */           this.x = ((this.x << 6) + result);
/*  558 */           if (this.modulus == 0) {
/*  559 */             this.buffer[(this.pos++)] = ((byte)(this.x >> 16 & 0xFF));
/*  560 */             this.buffer[(this.pos++)] = ((byte)(this.x >> 8 & 0xFF));
/*  561 */             this.buffer[(this.pos++)] = ((byte)(this.x & 0xFF));
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  571 */     if ((this.eof) && (this.modulus != 0)) {
/*  572 */       this.x <<= 6;
/*  573 */       switch (this.modulus) {
/*      */       case 2: 
/*  575 */         this.x <<= 6;
/*  576 */         this.buffer[(this.pos++)] = ((byte)(this.x >> 16 & 0xFF));
/*  577 */         break;
/*      */       case 3: 
/*  579 */         this.buffer[(this.pos++)] = ((byte)(this.x >> 16 & 0xFF));
/*  580 */         this.buffer[(this.pos++)] = ((byte)(this.x >> 8 & 0xFF));
/*  581 */         break;
/*      */       }
/*      */       
/*      */     }
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
/*      */   public static boolean isBase64(byte octet)
/*      */   {
/*  597 */     return (octet == 61) || ((octet >= 0) && (octet < DECODE_TABLE.length) && (DECODE_TABLE[octet] != -1));
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
/*      */   public static boolean isArrayByteBase64(byte[] arrayOctet)
/*      */   {
/*  610 */     for (int i = 0; i < arrayOctet.length; i++) {
/*  611 */       if ((!isBase64(arrayOctet[i])) && (!isWhiteSpace(arrayOctet[i]))) {
/*  612 */         return false;
/*      */       }
/*      */     }
/*  615 */     return true;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   private static boolean containsBase64Byte(byte[] arrayOctet)
/*      */   {
/*  626 */     byte[] arrayOfByte = arrayOctet;int j = arrayOctet.length; for (int i = 0; i < j; i++) { byte element = arrayOfByte[i];
/*      */       
/*  628 */       if (isBase64(element)) {
/*  629 */         return true;
/*      */       }
/*      */     }
/*  632 */     return false;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] encodeBase64(byte[] binaryData)
/*      */   {
/*  643 */     return encodeBase64(binaryData, false);
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
/*      */   public static String encodeBase64String(byte[] binaryData)
/*      */   {
/*  657 */     return newStringUtf8(encodeBase64(binaryData, true));
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
/*      */   public static String encodeBase64StringUnChunked(byte[] binaryData)
/*      */   {
/*  671 */     return newStringUtf8(encodeBase64(binaryData, false));
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
/*      */   public static String encodeBase64String(byte[] binaryData, boolean useChunking)
/*      */   {
/*  684 */     return newStringUtf8(encodeBase64(binaryData, useChunking));
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
/*      */   public static byte[] encodeBase64URLSafe(byte[] binaryData)
/*      */   {
/*  697 */     return encodeBase64(binaryData, false, true);
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
/*      */   public static String encodeBase64URLSafeString(byte[] binaryData)
/*      */   {
/*  710 */     return newStringUtf8(encodeBase64(binaryData, false, true));
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] encodeBase64Chunked(byte[] binaryData)
/*      */   {
/*  721 */     return encodeBase64(binaryData, true);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte[] decode(String pArray)
/*      */   {
/*  733 */     return decode(getBytesUtf8(pArray));
/*      */   }
/*      */   
/*      */   private byte[] getBytesUtf8(String pArray) {
/*      */     try {
/*  738 */       return pArray.getBytes("UTF8");
/*      */     } catch (UnsupportedEncodingException e) {
/*  740 */       throw new RuntimeException(e);
/*      */     }
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte[] decode(byte[] pArray)
/*      */   {
/*  752 */     reset();
/*  753 */     if ((pArray == null) || (pArray.length == 0)) {
/*  754 */       return pArray;
/*      */     }
/*  756 */     long len = pArray.length * 3 / 4;
/*  757 */     byte[] buf = new byte[(int)len];
/*  758 */     setInitialBuffer(buf, 0, buf.length);
/*  759 */     decode(pArray, 0, pArray.length);
/*  760 */     decode(pArray, 0, -1);
/*      */     
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*  768 */     byte[] result = new byte[this.pos];
/*  769 */     readResults(result, 0, result.length);
/*  770 */     return result;
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
/*      */   public static byte[] encodeBase64(byte[] binaryData, boolean isChunked)
/*      */   {
/*  785 */     return encodeBase64(binaryData, isChunked, false);
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
/*      */ 
/*      */ 
/*      */   public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe)
/*      */   {
/*  803 */     return encodeBase64(binaryData, isChunked, urlSafe, Integer.MAX_VALUE);
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] encodeBase64(byte[] binaryData, boolean isChunked, boolean urlSafe, int maxResultSize)
/*      */   {
/*  823 */     if ((binaryData == null) || (binaryData.length == 0)) {
/*  824 */       return binaryData;
/*      */     }
/*      */     
/*  827 */     long len = getEncodeLength(binaryData, isChunked ? 76 : 0, isChunked ? CHUNK_SEPARATOR : EMPTY_BYTE_ARRAY);
/*  828 */     if (len > maxResultSize) {
/*  829 */       throw new IllegalArgumentException("Input array too big, the output array would be bigger (" + 
/*  830 */         len + 
/*  831 */         ") than the specified maxium size of " + 
/*  832 */         maxResultSize);
/*      */     }
/*      */     
/*  835 */     Base64 b64 = isChunked ? new Base64(urlSafe) : new Base64(0, CHUNK_SEPARATOR, urlSafe);
/*  836 */     return b64.encode(binaryData);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] decodeBase64(String base64String)
/*      */   {
/*  848 */     return new Base64().decode(base64String);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public static byte[] decodeBase64(byte[] base64Data)
/*      */   {
/*  859 */     return new Base64().decode(base64Data);
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
/*      */   private static boolean isWhiteSpace(byte byteToCheck)
/*      */   {
/*  872 */     switch (byteToCheck) {
/*      */     case 9: 
/*      */     case 10: 
/*      */     case 13: 
/*      */     case 32: 
/*  877 */       return true;
/*      */     }
/*  879 */     return false;
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
/*      */   public String encodeToString(byte[] pArray)
/*      */   {
/*  892 */     return newStringUtf8(encode(pArray));
/*      */   }
/*      */   
/*      */   private static String newStringUtf8(byte[] encode) {
/*  896 */     String str = null;
/*      */     try {
/*  898 */       str = new String(encode, "UTF8");
/*      */     } catch (UnsupportedEncodingException ue) {
/*  900 */       throw new RuntimeException(ue);
/*      */     }
/*  902 */     return str;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   public byte[] encode(byte[] pArray)
/*      */   {
/*  913 */     reset();
/*  914 */     if ((pArray == null) || (pArray.length == 0)) {
/*  915 */       return pArray;
/*      */     }
/*  917 */     long len = getEncodeLength(pArray, this.lineLength, this.lineSeparator);
/*  918 */     byte[] buf = new byte[(int)len];
/*  919 */     setInitialBuffer(buf, 0, buf.length);
/*  920 */     encode(pArray, 0, pArray.length);
/*  921 */     encode(pArray, 0, -1);
/*      */     
/*  923 */     if (this.buffer != buf) {
/*  924 */       readResults(buf, 0, buf.length);
/*      */     }
/*      */     
/*      */ 
/*  928 */     if ((isUrlSafe()) && (this.pos < buf.length)) {
/*  929 */       byte[] smallerBuf = new byte[this.pos];
/*  930 */       System.arraycopy(buf, 0, smallerBuf, 0, this.pos);
/*  931 */       buf = smallerBuf;
/*      */     }
/*  933 */     return buf;
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
/*      */   private static long getEncodeLength(byte[] pArray, int chunkSize, byte[] chunkSeparator)
/*      */   {
/*  949 */     chunkSize = chunkSize / 4 * 4;
/*      */     
/*  951 */     long len = pArray.length * 4 / 3;
/*  952 */     long mod = len % 4L;
/*  953 */     if (mod != 0L) {
/*  954 */       len += 4L - mod;
/*      */     }
/*  956 */     if (chunkSize > 0) {
/*  957 */       boolean lenChunksPerfectly = len % chunkSize == 0L;
/*  958 */       len += len / chunkSize * chunkSeparator.length;
/*  959 */       if (!lenChunksPerfectly) {
/*  960 */         len += chunkSeparator.length;
/*      */       }
/*      */     }
/*  963 */     return len;
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
/*      */   public static BigInteger decodeInteger(byte[] pArray)
/*      */   {
/*  976 */     return new BigInteger(1, decodeBase64(pArray));
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
/*      */   public static byte[] encodeInteger(BigInteger bigInt)
/*      */   {
/*  990 */     if (bigInt == null) {
/*  991 */       throw new NullPointerException("encodeInteger called with null parameter");
/*      */     }
/*  993 */     return encodeBase64(toIntegerBytes(bigInt), false);
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   static byte[] toIntegerBytes(BigInteger bigInt)
/*      */   {
/* 1004 */     int bitlen = bigInt.bitLength();
/*      */     
/* 1006 */     bitlen = bitlen + 7 >> 3 << 3;
/* 1007 */     byte[] bigBytes = bigInt.toByteArray();
/*      */     
/* 1009 */     if ((bigInt.bitLength() % 8 != 0) && (bigInt.bitLength() / 8 + 1 == bitlen / 8)) {
/* 1010 */       return bigBytes;
/*      */     }
/*      */     
/* 1013 */     int startSrc = 0;
/* 1014 */     int len = bigBytes.length;
/*      */     
/*      */ 
/* 1017 */     if (bigInt.bitLength() % 8 == 0) {
/* 1018 */       startSrc = 1;
/* 1019 */       len--;
/*      */     }
/* 1021 */     int startDst = bitlen / 8 - len;
/* 1022 */     byte[] resizedBytes = new byte[bitlen / 8];
/* 1023 */     System.arraycopy(bigBytes, startSrc, resizedBytes, startDst, len);
/* 1024 */     return resizedBytes;
/*      */   }
/*      */   
/*      */ 
/*      */ 
/*      */   private void reset()
/*      */   {
/* 1031 */     this.buffer = null;
/* 1032 */     this.pos = 0;
/* 1033 */     this.readPos = 0;
/* 1034 */     this.currentLinePos = 0;
/* 1035 */     this.modulus = 0;
/* 1036 */     this.eof = false;
/*      */   }
/*      */   
/*      */ 
/*      */   int getLineLength()
/*      */   {
/* 1042 */     return this.lineLength;
/*      */   }
/*      */   
/*      */   byte[] getLineSeparator() {
/* 1046 */     return (byte[])this.lineSeparator.clone();
/*      */   }
/*      */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\PerfectBackup.jar!\org\apache\commons\net\util\Base64.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       0.7.1
 */