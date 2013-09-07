package gen;

import com.thoughtworks.qdox.model.AbstractJavaEntity;
import com.thoughtworks.qdox.model.DocletTag;

public abstract class AsGenUtils {
	public static String getFullComment(AbstractJavaEntity javaEntity) {
		StringBuilder comments = new StringBuilder();
		String comment = javaEntity.getComment();
		if(comment != null)
			comments.append("\t\t * ").append(comment).append(System.lineSeparator());
		for (DocletTag docletTag : javaEntity.getTags()) {
			String tagName = docletTag.getName();
			String comm = docletTag.getValue();
			comments.append("\t\t * @").append(tagName).append(" ").append(comm);
			comments.append(System.lineSeparator());
		}
		if(comments.length() > 0)
			return new StringBuilder("\t\t/**")
				.append(System.lineSeparator())
				.append(comments)
				.append("\t\t */")
				.append(System.lineSeparator()).toString();
		
		return "";
	}
}
