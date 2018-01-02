/*    */ package me.au2001.PerfectBackup;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.io.IOException;
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class BackupRestorer
/*    */ {
/*  9 */   public static boolean log = false;
/* 10 */   public static String prefix = "[LineB - Backup] ";
/*    */   
/*    */   public static void main(String[] args) {
/* 13 */     log = (args.length < 3) || ((!args[2].equalsIgnoreCase("true")) && (!args[2].equalsIgnoreCase("yes")) && (!args[2].equalsIgnoreCase("1")));
/* 14 */     if (args.length >= 2) {
/* 15 */       if ((new File(args[0]).exists()) && (new File(args[1]).exists())) {
/* 16 */         if (log) System.out.println(prefix + "Iniciando restauração de backup...");
/* 17 */         File from = new File(args[0]);
/* 18 */         File to = new File(args[1]);
/* 19 */         if ((from.isFile()) && (from.getName().endsWith(".zip"))) {
/* 20 */           if (log) System.out.println(prefix + "Descomprimindo backup...");
/* 21 */           BackupUtils.unzip(from, to);
/* 22 */         } else if (from.isDirectory()) {
/* 23 */           if (log) System.out.println(prefix + "Copiando backup para o local...");
/*    */           try {
/* 25 */             java.nio.file.Files.copy(from.toPath(), to.toPath(), new java.nio.file.CopyOption[0]);
/*    */           } catch (IOException e) {
/* 27 */             System.out.println(prefix + "Erro enquanto copiava pasta:");
/* 28 */             e.printStackTrace();
/*    */           }
/* 30 */         } else if (from.exists()) {
/* 31 */           System.out.println(prefix + "O backup informado não possui ID válida:");
/* 32 */           System.exit(1);
/*    */         } else {
/* 34 */           System.out.println(prefix + "O backup não pode ser encontrado.");
/* 35 */           System.exit(1);
/*    */         }
/* 37 */         if (log) System.out.println(prefix + "Sucesso ao restaurar Backup:");
/* 38 */         System.exit(0);
/* 39 */       } else if (!new File(args[0]).exists()) {
/* 40 */         System.out.println(prefix + "Error, the specified backup directory does not exist.");
/* 41 */         System.exit(1);
/* 42 */       } else if (new File(args[1]).exists()) {
/* 43 */         System.out.println(prefix + "Error, o diretório especificado não existe.");
/* 44 */         System.exit(1);
/*    */       }
/*    */     } else {
/* 47 */       System.out.println(prefix + "Ajuda: PerfectBackup.jar <backup_path> <server_path> [log]");
/* 48 */       System.exit(1);
/*    */     }
/* 50 */     System.out.println(prefix + "Um erro desconhecido ocorreu durante o backup!");
/*    */   }
/*    */ }


