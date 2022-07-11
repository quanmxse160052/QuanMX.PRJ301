package quanmx.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.sql.DataSource;

/**
 *
 * @author Dell
 */
public class DBHelper implements Serializable {

    public static Connection makeConnection() throws ClassNotFoundException, SQLException, NamingException {
        //1. get file system
        Context currentContext = new InitialContext();
        //2. get file system of container context 
        Context tomcatContext = (Context) currentContext.lookup("java:comp/env");
        //3. get datasource
        DataSource datasource = (DataSource) tomcatContext.lookup("DataSourceAhihi");
//        DataSource datasource = (DataSource) currentContext.lookup("java:comp/env/DataSourceAhihi");
        //4. get connection        
        Connection connection = datasource.getConnection();
        return connection;
//        //1. load Driver
        ////        com.microsoft.sqlserver.jdbc.SQLServerDriver
        //        Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        //        
        //        //2. make connection string
        //        String url = "jdbc:sqlserver://localhost:1433;databaseName=PRJ301;instanceName=QUANMXSQLSV";
        //        //3. open connection
        //        Connection con = DriverManager.getConnection(url, "sa", "123456");
        //        return con;
    }

    public static void getSiteMaps(ServletContext context) throws IOException {

        String siteMapPath = context.getInitParameter("SITEMAPS_FILE_PATH");
        InputStream is = null;
        Properties siteMaps = null;
        try {
            siteMaps = new Properties();
            is = context.getResourceAsStream(siteMapPath);
            siteMaps.load(is);
            context.setAttribute("SITEMAPS", siteMaps);
        } finally {
            if (is != null) {
                is.close();
            }
        }

    }
}
