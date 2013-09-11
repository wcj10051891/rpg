package gen.comment;

import gen.Config;
import gen.TableInfo;
import gen.TableMetaData;
import gen.VelocityRuner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CommentGen
{
	private static Config cfg = new Config("daoGen.cfg");

	private static final String tplPath = cfg.getString("comment.gen.template.file", "src/com/wcj/gen/comment/comment.vm");
    private static final String outputPath = cfg.getString("comment.gen.output.file", "src/com/wcj/gen/comment/comment.txt");
    private static final Set<String> includes = new HashSet<String>(Arrays.asList(cfg.getString("comment.gen.includes").split(",")));

    public static void main(String[] args) throws Exception
    {
        Map<String, TableMetaData> tables = new TableInfo(cfg).get();
        if(includes.isEmpty())
        {
            Map<String, Object> ctx = new HashMap<String, Object>();
            ctx.put("tables", tables);
            VelocityRuner.run(ctx, tplPath, outputPath);
        }
        else
        {
            Map<String, Object> ctx = new HashMap<String, Object>();
            Map<String, TableMetaData> t1 = new HashMap<String, TableMetaData>(1);
            for(String tableName : includes)
            {
                if(tables.containsKey(tableName))
                    t1.put(tableName, tables.get(tableName));
            }
            ctx.put("tables", t1);
            VelocityRuner.run(ctx, tplPath, outputPath);
        }
    }
}