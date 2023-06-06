package ConnectionPack;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ConnTest {
    public static void main(String[] args) {
        String sql = "SELECT [Tid]\n" +
                "      ,[Tname]\n" +
                "  FROM [dbo].[Testconnect]";
        try(Connection conn = ConnectionFactory.getConn()) {
            Statement stmt = conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            if (stmt.execute(sql)) {
                ResultSet rs = stmt.getResultSet();
                while(rs.next()){
                    System.out.println(rs.getInt("Tid"));
                    System.out.println(rs.getString("Tname"));
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
