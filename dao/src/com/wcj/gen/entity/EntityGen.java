package com.wcj.gen.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import com.wcj.gen.TableMetaData;
import com.wcj.gen.TableNameMap;
import com.wcj.util.Config;
import com.wcj.util.Utils;

/**
    读取指定库的schema信息，找到所有表信息，字段信息，然后根据指定的表名来生成对应的Entity类，并更新表名类名映射文件。

    也可以生成分表的Entity，指定的表名格式为player_item_1或player_item_2，会生成PlayerItem
 * @author wangchengjie	wcj10051891@gmail.com
 */
public class EntityGen
{
	private static Config cfg = new Config("daoGen.cfg");
    private static final String packageName = cfg.getString("entity.package.name", "com.cndw.xianhun.persist.entity");
    private static final String tplPath = cfg.getString("entity.gen.template.file", "src/gen/entity/entity.vm");
    private static final String outputPath = cfg.getString("entity.gen.output.dir", "../xianhun/src/com/cndw/xianhun/persist/entity");
    
    public static final Pattern shardingTablePattern = Pattern.compile("\\_\\d+$");
    
    // 排除列表，不生成
    private static final Set<String> excludes = new HashSet<String>(Arrays.asList(cfg.getString("entity.gen.excludes").split(",")));
    // 包含列表，如果是空，表示生成所有表，如果有值表示生成指定表
    private static final Set<String> includes = new HashSet<String>(Arrays.asList(cfg.getString("entity.gen.includes").split(",")));
    
    public static void main(String[] args) throws Exception
    {
    	TableNameMap tableNameMap = new TableNameMap(cfg.getString("entity.gen.tablemapfile", "src/gen/entity/map.properties"));
        Properties map = tableNameMap.load(true);
        Iterator<Entry<String, TableMetaData>> it = Utils.getTableInfos().entrySet().iterator();
        while (it.hasNext())
        {
            Entry<String, TableMetaData> en = it.next();
            TableMetaData table = en.getValue();
            String tableName = table.getTableName();

            if (excludes.contains(tableName))
                continue;
            if (includes.isEmpty() || includes.contains(tableName))
            {
                //如果发现是需要分表的table，就改变tableName，去掉末尾的_1，最后记录到map中为com.entity.PlayerItem=player_item_1
                if(shardingTablePattern.matcher(tableName).find())
                    table.setTableName(tableName.replaceFirst(shardingTablePattern.pattern(), ""));
                
                String[] result = new EntityGenerator(tplPath, table, packageName, outputPath).generate();
                map.put(result[0], tableName);
                System.out.println("entity gen success:" + outputPath + "/" + result[0]);
            }
        }
        tableNameMap.store();
    }
}
