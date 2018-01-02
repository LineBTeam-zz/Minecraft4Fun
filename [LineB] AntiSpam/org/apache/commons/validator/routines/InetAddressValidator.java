/*    */ package org.apache.commons.validator.routines;
/*    */ 
/*    */ import java.io.Serializable;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InetAddressValidator
/*    */   implements Serializable
/*    */ {
/*    */   private static final long serialVersionUID = -919201640201914789L;
/*    */   private static final String IPV4_REGEX = "^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$";
/* 44 */   private static final InetAddressValidator VALIDATOR = new InetAddressValidator();
/*    */   
/*    */ 
/* 47 */   private final RegexValidator ipv4Validator = new RegexValidator("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */   public static InetAddressValidator getInstance()
/*    */   {
/* 54 */     return VALIDATOR;
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isValid(String inetAddress)
/*    */   {
/* 63 */     return isValidInet4Address(inetAddress);
/*    */   }
/*    */   
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   public boolean isValidInet4Address(String inet4Address)
/*    */   {
/* 73 */     String[] groups = this.ipv4Validator.match(inet4Address);
/*    */     
/* 75 */     if (groups == null) { return false;
/*    */     }
/*    */     
/* 78 */     for (int i = 0; i <= 3; i++) {
/* 79 */       String ipSegment = groups[i];
/* 80 */       if ((ipSegment == null) || (ipSegment.length() <= 0)) {
/* 81 */         return false;
/*    */       }
/*    */       
/* 84 */       int iIpSegment = 0;
/*    */       try
/*    */       {
/* 87 */         iIpSegment = Integer.parseInt(ipSegment);
/*    */       } catch (NumberFormatException e) {
/* 89 */         return false;
/*    */       }
/*    */       
/* 92 */       if (iIpSegment > 255) {
/* 93 */         return false;
/*    */       }
/*    */     }
/*    */     
/*    */ 
/* 98 */     return true;
/*    */   }
/*    */ }


/* Location:              D:\[ Plugins ] Minecraft - Editar\ProSpam-0.9.97-SNAPSHOT.jar!\org\apache\commons\validator\routines\InetAddressValidator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */