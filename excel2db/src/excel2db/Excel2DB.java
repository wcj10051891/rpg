package excel2db;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jxl.Cell;
import jxl.CellType;
import jxl.Sheet;
import jxl.Workbook;

public class Excel2DB
{
    static String excelDir = "table/";
    static final String suffix = ".xls";
    static final Pattern ptn = Pattern.compile("[^D]\\s*(" + excelDir + ".*\\" + suffix + ")");
    static String svnlook = "/usr/local/subversion/bin/svnlook";
    static final int column_index = 1;
    static final int values_index = 2;
    static int insert_batch_size = 1000;
    static String url = "jdbc:mysql://192.168.1.106:3306/xianhun_dev?autoReconnect=true&useUnicode=true&characterEncoding=utf-8";
    static String user = "root";
    static String password = "root";
    static ThreadLocal<Connection> connection = new ThreadLocal<Connection>();

    public static void main(String[] args) throws Exception
    {
        String repos = args[0];
        String txn = args[1];
        if (args.length > 2)
            svnlook = args[2];
        if (args.length > 3)
            insert_batch_size = Integer.parseInt(args[3]);
        if (args.length > 4)
            url = args[4];
        if (args.length > 5)
            user = args[5];
        if (args.length > 6)
            password = args[6];
        if (args.length > 7)
            excelDir = args[7];

        try
        {
            Process changed = new ProcessBuilder(cmds(svnlook + " changed " + repos + " -t " + txn)).start();
            String cmd = svnlook + " cat -t " + txn + " " + repos + " ";
            StringTokenizer st = new StringTokenizer(new String(readBin(changed.getInputStream())), "\n");
            changed.destroy();

            while (st.hasMoreTokens())
            {
                Matcher m = ptn.matcher(st.nextToken());
                if (m.matches())
                {
                    String path = m.group(1);
                    Process cat = new ProcessBuilder(cmds(cmd + path)).start();
                    Workbook book = Workbook.getWorkbook(cat.getInputStream());
                    String tableName = path.replace(excelDir, "").replace(suffix, "");
                    List<String> sqls = getSql(tableName, book);
                    sqls.add(0, "DELETE FROM " + tableName);
                    execute(sqls);
                    book.close();
                    cat.destroy();
                }
            }
            getConnection().commit();
            close();
        }
        catch (Exception e)
        {
            getConnection().rollback();
            close();
            e.printStackTrace();
            Runtime.getRuntime().exit(1);
        }
    }

    private static String[] cmds(String cmd)
    {
        StringTokenizer st = new StringTokenizer(cmd);
        int n = st.countTokens();
        String[] result = new String[n];
        for (int i = 0; st.hasMoreTokens(); i++)
            result[i] = st.nextToken();
        return result;
    }

    public static byte[] readBin(InputStream in) throws Exception
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        int len = -1;
        while ((len = in.read(buf)) != -1)
            out.write(buf, 0, len);
        return out.toByteArray();
    }

    public static List<String> getSql(String tableName, Workbook book)
    {
        List<String> rows = getRowSql(book);
        int size = rows.size();
        int n = size / insert_batch_size;
        if (size % insert_batch_size > 0)
            n++;
        List<String> result = new LinkedList<String>();
        String insert = getInsertSql(tableName, book);
        StringBuilder subRows = new StringBuilder();
        for (int i = 0; i < n; i++)
        {
            int toIndex = 0;
            if (i == n - 1)
                toIndex = size;
            else
                toIndex = (i + 1) * insert_batch_size;
            subRows.append(rows.subList(i * insert_batch_size, toIndex).toString());
            subRows.deleteCharAt(0).deleteCharAt(subRows.length() - 1);
            result.add(insert + " " + subRows.toString() + ";");
            subRows.delete(0, subRows.length());
        }
        return result;
    }

    public static List<String> getRowSql(Workbook book)
    {
        List<String> result = new ArrayList<String>();
        Sheet sheet = book.getSheet(0);
        int size = sheet.getRows();
        StringBuilder values = new StringBuilder();
        for (int i = values_index; i < size; i++)
        {
            values.append("(");
            for (Cell c : sheet.getRow(i))
                values.append(getCellValue(c)).append(",");
            values.deleteCharAt(values.length() - 1);
            values.append(")");
            result.add(values.toString());
            values.delete(0, values.length());
        }
        return result;
    }

    public static String getInsertSql(String tableName, Workbook book)
    {
        StringBuilder sql = new StringBuilder("insert into ").append(tableName).append("(");
        for (Cell c : book.getSheet(0).getRow(column_index))
            sql.append("`").append(c.getContents()).append("`").append(",");
        sql.deleteCharAt(sql.length() - 1);
        sql.append(") values ");
        return sql.toString();
    }

    public static String getCellValue(Cell c)
    {
        CellType type = c.getType();
        if (type == CellType.BOOLEAN || type == CellType.NUMBER)
            return c.getContents();
        
        if (type == CellType.EMPTY)
            return null;

        return "'" + c.getContents() + "'";
    }

    private static Connection getConnection() throws Exception
    {
        if (connection.get() == null)
        {
            Class.forName("com.mysql.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url, user, password);
            conn.setAutoCommit(false);
            connection.set(conn);
        }
        return connection.get();
    }

    private static void execute(List<String> sqls) throws Exception
    {
        Statement st = getConnection().createStatement();
        for (String sql : sqls)
            st.addBatch(sql);
        st.executeBatch();
        st.close();
    }

    private static void close() throws Exception
    {
        if (getConnection() != null)
        {
            connection.get().close();
            connection.remove();
        }
    }
}