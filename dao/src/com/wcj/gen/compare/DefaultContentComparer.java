package com.wcj.gen.compare;

import japa.parser.JavaParser;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.Node;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.ClassOrInterfaceDeclaration;
import japa.parser.ast.body.FieldDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.type.ClassOrInterfaceType;

import java.io.InputStream;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class DefaultContentComparer {
	
	public String compare(InputStream leftContent, InputStream righContentt) {
		try {
			CompilationUnit left = JavaParser.parse(leftContent);
			CompilationUnit right = JavaParser.parse(righContentt);
			
			CompareInfo leftInfo = getInfo(left);
			CompareInfo rightInfo = getInfo(right);
			
			ClassOrInterfaceDeclaration  leftDef = leftInfo.getClassOrInterface();
			ClassOrInterfaceDeclaration  rightDef = rightInfo.getClassOrInterface();
			
			List<ClassOrInterfaceType> leftExtends = ensureNotNull(leftDef.getExtends());
			List<ClassOrInterfaceType> leftImpls = ensureNotNull(leftDef.getImplements());
			
			List<ClassOrInterfaceType> rightExtends = ensureNotNull(rightDef.getExtends());
			List<ClassOrInterfaceType> rightImpls = ensureNotNull(rightDef.getImplements());
			
			if(rightExtends.size() > leftExtends.size()){
				leftInfo.getClassOrInterface().setExtends(rightInfo.getClassOrInterface().getExtends());
			}
			
			if(rightImpls.size() > leftImpls.size()){
				leftInfo.getClassOrInterface().setImplements(rightInfo.getClassOrInterface().getImplements());
			}			
			
			Iterator<Entry<String, FieldDeclaration>> it = rightInfo.getFields().entrySet().iterator();
			while(it.hasNext()){
				Entry<String, FieldDeclaration> en = it.next();
				if(!leftInfo.getFields().containsKey(en.getKey())){
					leftInfo.getClassOrInterface().getMembers().add(en.getValue());
				}
			}
			
			Iterator<Entry<String, MethodDeclaration>> it2 = rightInfo.getMethods().entrySet().iterator();
			Map<String, MethodDeclaration> leftMethods = leftInfo.getMethods();
			while(it2.hasNext()){
				Entry<String, MethodDeclaration> en = it2.next();
				if(!leftMethods.containsKey(en.getKey())){
					leftInfo.getClassOrInterface().getMembers().add(en.getValue());
				}
			}
			
			Iterator<Entry<String, ImportDeclaration>> it3 = rightInfo.getImports().entrySet().iterator();
			while(it3.hasNext()){
				Entry<String, ImportDeclaration> en = it3.next();
				if(!leftInfo.getImports().containsKey(en.getKey())){
					left.getImports().add(en.getValue());
				}
			}
			return left.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private static CompareInfo getInfo(CompilationUnit left){
		List<TypeDeclaration> types = left.getTypes();
		ClassOrInterfaceDeclaration coid = null;
		Map<String, MethodDeclaration> methods = new LinkedHashMap<String, MethodDeclaration>();
		Map<String, FieldDeclaration> fields = new LinkedHashMap<String, FieldDeclaration>();
		Map<String, ImportDeclaration> imports = new LinkedHashMap<String, ImportDeclaration>();
		for(TypeDeclaration td : types){
			if(td instanceof ClassOrInterfaceDeclaration){
				coid = (ClassOrInterfaceDeclaration)td;
				List<BodyDeclaration> bodys = coid.getMembers();
				for(BodyDeclaration bd : bodys){
					if(bd instanceof MethodDeclaration){
						MethodDeclaration _m = (MethodDeclaration)bd;
						StringBuilder method_unique_key = new StringBuilder();
						List<Parameter> params = _m.getParameters();
						if(params == null || params.size() == 0){
							method_unique_key.append(_m.getName());
						}else{
							method_unique_key.append(_m.getName());
							for(int i=0;i<params.size();i++){
								method_unique_key.append("_");
								method_unique_key.append(params.get(i).getType());
							}
						}
						methods.put(method_unique_key.toString(), _m);
					}else if(bd instanceof FieldDeclaration){
						FieldDeclaration _m = (FieldDeclaration)bd;
						fields.put(_m.getVariables().get(0).toString(), _m);
					}
				}
			}
		}
		List<ImportDeclaration> importss = left.getImports();
		if(importss != null){
			for(ImportDeclaration id : importss){
				imports.put(id.getName().getName(), id);
			}
		}
		CompareInfo info = new CompareInfo(coid, fields, methods, imports);
		return info;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Node> List<T> ensureNotNull(List<T> objects){
		return objects == null ? Collections.EMPTY_LIST : objects;
	}
}
class CompareInfo {
	private ClassOrInterfaceDeclaration classOrInterface;
	private Map<String, FieldDeclaration> fields;
	private Map<String, MethodDeclaration> methods;
	private Map<String, ImportDeclaration> imports;
	
	public CompareInfo(ClassOrInterfaceDeclaration classOrInterface,
			Map<String, FieldDeclaration> fields,
			Map<String, MethodDeclaration> methods, Map<String, ImportDeclaration> imports) {
		this.classOrInterface = classOrInterface;
		this.fields = fields;
		this.methods = methods;
		this.imports = imports;
	}
	public ClassOrInterfaceDeclaration getClassOrInterface() {
		return classOrInterface;
	}
	public Map<String, FieldDeclaration> getFields() {
		return fields;
	}
	public Map<String, MethodDeclaration> getMethods() {
		return methods;
	}
	public Map<String, ImportDeclaration> getImports() {
		return imports;
	}
}
