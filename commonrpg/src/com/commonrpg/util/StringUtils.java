package com.commonrpg.util;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

public abstract class StringUtils {
	public static String toString(Object o, boolean multiLine) {
		return ToStringBuilder.reflectionToString(o, multiLine ? ToStringStyle.MULTI_LINE_STYLE
				: ToStringStyle.DEFAULT_STYLE);
	}

	public static String toString(Object o) {
		return toString(o, true);
	}
}
