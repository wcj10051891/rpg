package com.wcj.dao.core.sharding;

import java.lang.annotation.Annotation;

import com.wcj.dao.annotation.IdModSharding;

public class IdModStrategy extends ShardingStrategy {
	public String process(String sql, Annotation annotation, Object arg) {
		IdModSharding shardingAnnotation = (IdModSharding) annotation;
		String tableName = shardingAnnotation.tableName();
		return sql.replaceFirst(tableName,
				tableName.replaceFirst(shardingAnnotation.replaceRegex(),
						String.valueOf(((Number) arg).longValue() % shardingAnnotation.shardingCount() + 1)));
	}
}
