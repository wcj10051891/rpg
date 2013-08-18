package gen.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/**
 * 标记生成
 * 
 * @author <a href="mailto:xiaofeng.l@cndw.com">arbow</a>
 */
@Inherited
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
public @interface GenCode {
}
