package gen.dao;

import gen.Config;
import gen.SvnDownloader;
import gen.TableInfo;
import gen.TableMetaData;
import gen.TableNameMap;
import gen.Utils;
import gen.VelocityRuner;
import gen.compare.DefaultContentComparer;
import gen.entity.EntityGen;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/**
 * 读取生成Entity时候产生的TableNameMap，Entity类名->表名的映射文件，
 * 取的类名表名信息，来生成相应Dao，XXXXDao，包含一些常用CRUD方法，
 * 而且会从svn下载一份对应的最新版本，与生成版本比较合并，不会覆盖。
 * 
 * 也可以生成分表的dao，指定的表名格式为player_item_1或player_item_2，会生成PlayerItemDao
 * @author wangchengjie	wcj10051891@gmail.com
 */
public class DaoGen
{
	private static Config cfg = new Config("daoGen.cfg");
    private static final String packageName = cfg.getString("dao.package.name", "com.cndw.xianhun.persist.dao");
    private static final String outputPath = cfg.getString("dao.gen.output.file", "../xianhun/src/com/cndw/xianhun/persist/dao/{0}Dao.java");
    private static final String templatePath = cfg.getString("dao.gen.template.file", "src/gen/dao/daoTemplate.vm");
    private static final String shardTemplatePath = cfg.getString("dao.gen.shard.template.file", "src/gen/dao/shardDao.vm");
    
    // 排除列表，不生成
    private static final Set<String> excludes = new HashSet<String>(Arrays.asList(cfg.getString("dao.gen.excludes").split(",")));
    // 包含列表，如果是空，表示生成所有表，如果有值表示生成指定表
    private static final Set<String> includes = new HashSet<String>(Arrays.asList(cfg.getString("dao.gen.includes").split(",")));

    public static void main(String[] args) throws Exception
    {
    	TableNameMap tableNameMap = new TableNameMap(cfg.getString("entity.gen.tablemapfile", "src/gen/entity/map.properties"));
        Properties map = tableNameMap.load(true);
        Map<String, TableMetaData> tableInfos = null;
        for(Iterator<Object> it = map.keySet().iterator();it.hasNext();)
        {
            String className = String.valueOf(it.next());
            String tableName = map.getProperty(className);

            if (excludes.contains(tableName))
                continue;

            if (includes.isEmpty() || includes.contains(tableName))
            {
                if(tableInfos == null)
                {
                    tableInfos = new TableInfo(cfg).get();
                }
                TableMetaData tableMetaData = tableInfos.get(tableName);
                if(tableMetaData != null)
                {

                    if(EntityGen.shardingTablePattern.matcher(tableName).find())
                        genShardDao(className, tableName.replaceFirst(EntityGen.shardingTablePattern.pattern(), ""), tableMetaData.getColumnNames());
                    else
                        genDao(className, tableName, tableMetaData.getColumnNames());
                }
            }
        }
    }

    private static void genDao(String classFullName, String tableName, List<String> properties) throws Exception
    {
        Map<String, Object> ctx = new HashMap<String, Object>();

        int index = classFullName.lastIndexOf(".");
        String className = classFullName;
        if(index != -1)
            className = classFullName.substring(index + 1);
        ctx.put("packageName", packageName);
        ctx.put("className", className);
        ctx.put("entityName", Utils.firstLowerCase(className));
        ctx.put("tableName", tableName);
        ctx.put("properties", properties);
        ctx.put("classFullName", classFullName);
        String outPutPath = MessageFormat.format(outputPath, className);
        VelocityRuner.run(ctx, templatePath, outPutPath);
//        和svn对比
//        File generated = new File(outPutPath);
//        String svnOldDaoFullClassName = packageName + "." + className + "Dao";
//        compareToSvnLastest(generated, svnOldDaoFullClassName);
        System.out.println("gen normal dao success:" + className + "->" + outPutPath);
    }
    
    private static void genShardDao(String classFullName, String tableName, List<String> properties) throws Exception
    {
        Map<String, Object> ctx = new HashMap<String, Object>();
        ctx.put("packageName", packageName);
        int index = classFullName.lastIndexOf(".");
        String className = classFullName;
        if(index != -1)
            className = classFullName.substring(index + 1);
        ctx.put("className", className);
        ctx.put("entityName", Utils.firstLowerCase(className));
        ctx.put("tableName", tableName);
        ctx.put("properties", properties);
        ctx.put("shardConfigValue", "playerId");
        ctx.put("classFullName", classFullName);

        String outPutPath = MessageFormat.format(outputPath, className);
        VelocityRuner.run(ctx, shardTemplatePath, MessageFormat.format(outputPath, className));

        File generated = new File(outPutPath);

        String svnOldDaoFullClassName = packageName + "." + className + "Dao";
        compareToSvnLastest(generated, svnOldDaoFullClassName);

        System.out.println("gen shard dao success: " + className + "->" + outPutPath);
    }

    private static void compareToSvnLastest(File o, String className)
    {
        byte[] data = SvnDownloader.getFile(className);

        if (data != null && data.length > 0)
        {
            try
            {
                String result = new DefaultContentComparer().compare(new FileInputStream(o), new ByteArrayInputStream(data));

                if (result != null && result.length() > 0)
                {
                    Utils.writeFile(o, result);
                }
            }
            catch (Exception e)
            {
                System.out.println("compare to svn lastest failure.");
            }
        }
    }

}
