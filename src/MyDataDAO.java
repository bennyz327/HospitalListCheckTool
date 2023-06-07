import ConnectionPack.ConnectionFactory;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MyDataDAO {
    static public boolean insertData(Mydata mdata){
        String sql = "insert into HospitalList(ResourceAgency,HospitalName,PhoneNumber,Fax,Email,Address,Website,xCoordinate,yCoordinate,Notes,LastUpdateTime)\n" +
                "values (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConn();
             PreparedStatement pstmt =conn.prepareStatement(sql)) {
            pstmt.setString(1,mdata.getResourceAgency());
            pstmt.setString(2, mdata.getHospitalName());
            pstmt.setInt(3,mdata.getPhoneNumber());
            pstmt.setInt(4,mdata.getFax());
            pstmt.setString(5,mdata.getEmail());
            pstmt.setString(6,mdata.getAddress());
            pstmt.setString(7,mdata.getWebsite());
            pstmt.setInt(8,mdata.getxCoordinate());
            pstmt.setInt(9,mdata.getyCoordinate());
            pstmt.setString(10,mdata.getNotes());
            Date sqldate=new Date(mdata.getLastUpdateTime().getTime());
            pstmt.setDate(11,sqldate);//將util的Date轉換為sql的Date
            int count = pstmt.executeUpdate();
            System.out.println(count+"筆資料受到影響");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }
}
