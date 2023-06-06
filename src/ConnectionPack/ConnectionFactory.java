package ConnectionPack;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class ConnectionFactory {
    private static String url = "jdbc:sqlserver://localhost:1433;databasename=midproject;encrypt=false";
    private static String account = "benny";
    private static String goword = "1qa2ws3ed";

    private static Connection conn = null;
    public static Connection getConn() throws SQLException {

            conn=DriverManager.getConnection(url,account,goword);
            return conn;

    }
}
