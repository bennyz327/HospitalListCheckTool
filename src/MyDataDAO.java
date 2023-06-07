import ConnectionPack.ConnectionFactory;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class MyDataDAO {
    static public boolean insertData(Mydata mdata){
        String sql = "insert into HospitalList(ResourceAgency,HospitalName,PhoneNumber,Fax,Email,Address,Website,xCoordinate,yCoordinate,Notes,LastUpdateTime)\n" +
                "values (?,?,?,?,?,?,?,?,?,?,?)";
        try (Connection conn = ConnectionFactory.getConn();
             PreparedStatement pstmt =conn.prepareStatement(sql)) {
            //每個屬性皆判斷是否空值測試(GPT生成取代原代碼區塊)
            pstmt.setString(1, mdata.getResourceAgency() != null ? mdata.getResourceAgency() : null);
            pstmt.setString(2, mdata.getHospitalName() != null ? mdata.getHospitalName() : null);
            pstmt.setString(3, mdata.getPhoneNumber() != null ? mdata.getPhoneNumber() : null);
            if (mdata.getFaxInteger() != null) {
                pstmt.setInt(4, mdata.getFax());
            } else {
                pstmt.setNull(4, Types.INTEGER);
            }
            pstmt.setString(5, mdata.getEmail() != null ? mdata.getEmail() : null);
            pstmt.setString(6, mdata.getAddress() != null ? mdata.getAddress() : null);
            pstmt.setString(7, mdata.getWebsite() != null ? mdata.getWebsite() : null);
            if (mdata.getxCoordinateInteger() != null) {
                pstmt.setInt(8, mdata.getxCoordinate());
            } else {
                pstmt.setNull(8, Types.INTEGER);
            }
            if (mdata.getyCoordinateInteger() != null) {
                pstmt.setInt(9, mdata.getyCoordinate());
            } else {
                pstmt.setNull(9, Types.INTEGER);
            }
            pstmt.setString(10, mdata.getNotes() != null ? mdata.getNotes() : null);
            if(mdata.getLastUpdateTime()!=null) {
                Date sqldate = new Date(mdata.getLastUpdateTime().getTime());
                pstmt.setDate(11, sqldate);//將util的Date轉換為sql的Date
            }else {pstmt.setNull(11, Types.DATE);}
            //賦值結束
            //執行SQL
            int count = pstmt.executeUpdate();
            System.out.println(count+"筆資料受到影響");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }//輸入的Mydata物件不能有空欄位值，因為SQL指令產生方式有缺陷
    static public void deleteData(int id){
        String sql = "DELETE FROM HospitalList WHERE id=?";
        try(Connection conn = ConnectionFactory.getConn())  {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            int count = pstmt.executeUpdate();
            System.out.println(count+"筆資料受到影響");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//done //根據id刪除資料 //todo 需要再包裝成使用者友好形式
    static public Mydata selectData(int id){
        String sql = "SELECT [ResourceAgency],[HospitalName],[PhoneNumber],[Fax],[Email],[Address],[Website],[xCoordinate],[yCoordinate],[Notes],[LastUpdateTime],[id] FROM HospitalList WHERE id=?";
        try(Connection conn = ConnectionFactory.getConn())  {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            Mydata rsMyData = null;
            // 因為調閱主鍵，斷定返回結果集只有單筆資料或是沒有資料
            if (rs.next()){
                rsMyData = new Mydata();
                rsMyData.setResourceAgency(rs.getString("ResourceAgency"));
                rsMyData.setHospitalName(rs.getString("HospitalName"));
                rsMyData.setPhoneNumber(rs.getString("PhoneNumber"));
                rsMyData.setFax(rs.getInt("Fax"));
                rsMyData.setEmail(rs.getString("Email"));
                rsMyData.setAddress(rs.getString("Address"));
                rsMyData.setWebsite(rs.getString("Website"));
                rsMyData.setxCoordinate(rs.getInt("xCoordinate"));
                rsMyData.setyCoordinate(rs.getInt("yCoordinate"));
                rsMyData.setNotes(rs.getString("Notes"));
                rsMyData.setLastUpdateTime(rs.getDate("LastUpdateTime"));
                return rsMyData;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("查詢錯誤");
        return null;
    }//done //根據id查詢資料，將整筆資料返回成Mydata物件
    static public void updateData(int id,String field,Object value){
        String sql = "UPDATE HospitalList SET "+field+" = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //若更新日期則嘗試解析格式再導入
            if("LastUpdateTime".equals(field)){
                try{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    java.util.Date convertDate = sdf.parse(value.toString());
                    pstmt.setObject(1, convertDate);
                }catch (ParseException e){e.printStackTrace();}}
            else {
                pstmt.setObject(1, value);}
            pstmt.setInt(2,id);
            //執行更新
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " rows.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//可更新字串、數字、日期(格式yyyy/MM/dd)
}


