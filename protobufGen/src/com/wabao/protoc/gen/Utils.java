/*    */ package com.wabao.protoc.gen;
/*    */ 
/*    */ import com.thoughtworks.qdox.model.AbstractJavaEntity;
/*    */ import com.thoughtworks.qdox.model.DocletTag;
/*    */ 
/*    */ @Ignore
/*    */ public abstract class Utils
/*    */ {
/*    */   public static String getFullComment(AbstractJavaEntity javaEntity)
/*    */   {
/*  9 */     StringBuilder comments = new StringBuilder();
/* 10 */     String comment = javaEntity.getComment();
/* 11 */     if (comment != null)
/* 12 */       comments.append("\t * ").append(comment.replaceAll(System.lineSeparator(), System.lineSeparator() + "\t * ")).append(System.lineSeparator());
/* 13 */     for (DocletTag docletTag : javaEntity.getTags()) {
/* 14 */       String tagName = docletTag.getName();
/* 15 */       String comm = docletTag.getValue();
/* 16 */       comments.append("\t * @").append(tagName).append(" ").append(comm);
/* 17 */       comments.append(System.lineSeparator());
/*    */     }
/* 19 */     if (comments.length() > 0) {
/* 20 */       return "\t/**" + 
/* 21 */         System.lineSeparator() + 
/* 22 */         comments + 
/* 23 */         "\t */" + 
/* 24 */         System.lineSeparator();
/*    */     }
/* 26 */     return "";
/*    */   }
/*    */ }

/* Location:           C:\Users\wcj\Desktop\proto\src\proto.jar
 * Qualified Name:     com.wabao.protoc.gen.Utils
 * JD-Core Version:    0.6.2
 */