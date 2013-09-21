/*    */ package com.wabao.protoc.gen;
/*    */ 
/*    */ import com.thoughtworks.qdox.model.Type;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Set;
/*    */ 
/*    */ @Ignore
/*    */ public abstract class TypeConverter
/*    */ {
/* 11 */   public static Map<String, String> java2proto = new HashMap();
/* 12 */   private static Type collectionType = new Type("java.util.Collection");
/*    */ 
/*    */   static
/*    */   {
/* 20 */     java2proto.put("boolean", "bool");
/* 21 */     java2proto.put("java.lang.Boolean", "bool");
/*    */ 
/* 23 */     java2proto.put("int", "int32");
/* 24 */     java2proto.put("java.lang.Integer", "int32");
/*    */ 
/* 26 */     java2proto.put("long", "int64");
/* 27 */     java2proto.put("java.lang.Long", "int64");
/*    */ 
/* 29 */     java2proto.put("float", "float");
/* 30 */     java2proto.put("java.lang.Float", "float");
/*    */ 
/* 32 */     java2proto.put("double", "double");
/* 33 */     java2proto.put("java.lang.Double", "double");
/*    */ 
/* 35 */     java2proto.put("java.lang.String", "string");
/*    */ 
/* 37 */     java2proto.put("ByteString", "bytes");
/* 38 */     java2proto.put("com.google.protobuf.ByteString", "bytes");
/*    */   }
/*    */ 
/*    */   public static boolean isCollection(Type type) {
/* 42 */     return type.isA(collectionType);
/*    */   }
/*    */ 
/*    */   public static String convert(Type type, Set<String> imports) {
/* 46 */     if ((type.isArray()) && (type.getDimensions() != 1))
/* 47 */       throw new IllegalArgumentException("only support one dimension array.");
/* 48 */     if (isCollection(type)) {
/* 49 */       Type[] actualTypeArguments = type.getActualTypeArguments();
/* 50 */       if ((actualTypeArguments == null) || (actualTypeArguments.length != 1))
/* 51 */         throw new IllegalArgumentException("generic type parameter error.");
/*    */     }
/* 53 */     String fqn = type.getFullyQualifiedName();
/* 54 */     String result = (String)java2proto.get(fqn);
/* 55 */     if (result == null) {
/* 56 */       if (isCollection(type))
/* 57 */         result = convert(type.getActualTypeArguments()[0], imports);
/*    */       else {
/* 59 */         result = type.getGenericValue();
/*    */       }
/*    */     }
/* 62 */     if (result == null) {
/* 63 */       throw new IllegalArgumentException("type convert error:" + type);
/*    */     }
/* 65 */     if ((!java2proto.containsKey(fqn)) && (!fqn.contains("java.")))
/* 66 */       imports.add(fqn);
/* 67 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\wcj\Desktop\proto\src\proto.jar
 * Qualified Name:     com.wabao.protoc.gen.TypeConverter
 * JD-Core Version:    0.6.2
 */