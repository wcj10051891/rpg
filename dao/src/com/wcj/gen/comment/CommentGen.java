package com.wcj.gen.comment;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.wcj.gen.TableMetaData;
import com.wcj.gen.VelocityRuner;
import com.wcj.util.Utils;

public class CommentGen
{

    private static final String outputPath = "src/gen/comment/comment.txt";

    private static final String tplPath = "src/gen/comment/comment.vm";

    private static final Set<String> includes = new HashSet<String>();

    static
    {
        includes.add("activity_config");
    }

    public static void main(String[] args) throws Exception
    {
        Map<String, TableMetaData> tables = Utils.getTableInfos();
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
