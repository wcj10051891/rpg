package com.wabao.mogame.protoc.gen;

import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.DocletTag;

@Ignore
public abstract class Utils {
	public static String getFullComment(AbstractJavaEntity javaEntity) {
		StringBuilder comments = new StringBuilder();
		String comment = javaEntity.getComment();
		if (comment != null)
			comments.append("\t * ")
					.append(comment.replaceAll(System.lineSeparator(),
							System.lineSeparator() + "\t * "))
					.append(System.lineSeparator());
		for (DocletTag docletTag : javaEntity.getTags()) {
			String tagName = docletTag.getName();
			String comm = docletTag.getValue();
			comments.append("\t * @").append(tagName).append(" ").append(comm);
			comments.append(System.lineSeparator());
		}
		if (comments.length() > 0) {
			return "\t/**" + System.lineSeparator() + comments + "\t */"
					+ System.lineSeparator();
		}
		return "";
	}
}