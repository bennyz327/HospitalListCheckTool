import ConnectionPack.ConnectionFactory;
import com.google.gson.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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
            pstmt.setString(4, mdata.getFax() != null ? mdata.getFax() : null);
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
            //todo 應該要設定成當下時間
            if(mdata.getLastUpdateTime()!=null) {
                //把UtilDate轉Calendar加1911年之後轉回UtilDate再轉SqlDate
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(mdata.getLastUpdateTime());
                calendar.add(Calendar.YEAR, 1911);
                java.util.Date adDate = calendar.getTime();
                Date sqldate = new Date(adDate.getTime());

                pstmt.setDate(11, sqldate);
            }else {pstmt.setNull(11, Types.DATE);}
            //賦值結束
            //執行SQL
            int count = pstmt.executeUpdate();
            System.out.println("成功新增資料 "+count+" 筆資料");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }//輸入的Mydata //物件屬性可以有null了
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
    }//根據id刪除資料
    static public int deleteDataByName(String name){
        String sql = "DELETE FROM HospitalList WHERE HospitalName=?";
        try(Connection conn = ConnectionFactory.getConn())  {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,name);
            int count = pstmt.executeUpdate();
            System.out.println(count+"筆資料受到影響");
            return count;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    } //根據名稱刪除資料 //todo 會有重複資料一起被刪除的問題
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
                rsMyData.setFax(rs.getString("Fax"));
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
    }//根據id查詢資料，將整筆資料返回成Mydata物件//根據欄位名稱查詢資料 //todo 先寫HospitalName跟ResourceAgency的模糊查詢
    static public List<Mydata> selectDataByColumn(String columnName,String keyword){
        //todo 用參數傳遞column不管怎麼改都會報錯，先暫時直接連接字串QQ
        String sql = "SELECT [ResourceAgency],[HospitalName],[PhoneNumber],[Fax],[Email],[Address],[Website],[xCoordinate],[yCoordinate],[Notes],[LastUpdateTime],[id] FROM HospitalList Where "+columnName+" LIKE ?";
        try(Connection conn = ConnectionFactory.getConn())  {
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "%" + keyword + "%");
//            pstmt.setString(1,columnName);
//            String keystring = "N'%"+keyword+"%'";
//            pstmt.setString(2,keystring);
            ResultSet rs = pstmt.executeQuery();
            List<Mydata> rsList=new ArrayList<>();
            while (rs.next()){
                //測試資料是否進來System.out.println(rs.getString(1));
                Mydata rsMyData = new Mydata();
                rsMyData.setResourceAgency(rs.getString("ResourceAgency"));
                rsMyData.setHospitalName(rs.getString("HospitalName"));
                rsMyData.setPhoneNumber(rs.getString("PhoneNumber"));
                rsMyData.setFax(rs.getString("Fax"));
                rsMyData.setEmail(rs.getString("Email"));
                rsMyData.setAddress(rs.getString("Address"));
                rsMyData.setWebsite(rs.getString("Website"));
                rsMyData.setxCoordinate(rs.getInt("xCoordinate"));
                rsMyData.setyCoordinate(rs.getInt("yCoordinate"));
                rsMyData.setNotes(rs.getString("Notes"));
                rsMyData.setLastUpdateTime(rs.getDate("LastUpdateTime"));
                rsList.add(rsMyData);
            }
            System.out.println("資料獲取完畢");
            return rsList;
        }catch (SQLException e){e.printStackTrace();return null;}
    }
    static public void updateData(String selectDataName,String field,Object value){
        String replaceField = field.replaceAll("[;\"'{}\\[\\]]", "");
        String sql = "UPDATE HospitalList SET "+replaceField+" = ? WHERE HospitalName = ?";
        try (Connection conn = ConnectionFactory.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            //若更新日期則嘗試解析格式再導入
            //todo 當更改時間之外的欄位時也需要更新LastUpdateTime欄位
            if("LastUpdateTime".equals(field)){
                try{
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                    java.util.Date convertDate = sdf.parse(value.toString());
                    pstmt.setObject(1, convertDate);
                }catch (ParseException e){e.printStackTrace();}}
            else {
                pstmt.setObject(1, value);}
            pstmt.setString(2,selectDataName);
            //執行更新
            int rowsAffected = pstmt.executeUpdate();
            System.out.println("Updated " + rowsAffected + " rows.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }//可更新字串、數字、日期(格式yyyy/MM/dd)
    static public JsonArray readJsonFile(String filePath){
        try (FileInputStream is = new FileInputStream(filePath);
                BufferedInputStream bis = new BufferedInputStream(is);
                InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);) {
            StringBuilder jsonStringBuilder = new StringBuilder();
            int datastream;
            //讀取檔案進入StringBuilder
            while ((datastream = isr.read())!=-1){
                char C = (char) datastream;
                jsonStringBuilder.append(C);}
            String jsonString = jsonStringBuilder.toString();
            //處理感擾字符，u00A0好像是特殊空格的Unicode編碼
            String jsonContent = jsonString.replaceAll("[\\n\\r\\t\\s\\u00A0]", "");
            //解析JSON檔案後return
            return JsonParser.parseString(jsonString).getAsJsonArray();
        }catch (IOException e){
            e.printStackTrace();
            System.out.println("找不到該路徑中相應的檔案");
            return null;
        }


    }//處理字串後，給套件解析Json字串，並返回JsonArray
    static public void importArrToDatabase(JsonArray jsArr){
        Mydata mdata = new Mydata();
        try {
            for (int i = 0; i < jsArr.size(); i++) {
                JsonObject jsonObject = jsArr.get(i).getAsJsonObject();
                //todo 處裡空值 可交由insert判斷?
                mdata.setResourceAgency(jsonObject.get("資源彙整機關").getAsString());
                mdata.setHospitalName(jsonObject.get("醫院名稱").getAsString());
                mdata.setPhoneNumber(jsonObject.get("連絡電話").getAsString());//不轉換了，直接當字串丟進去
                mdata.setFax(jsonObject.get("傳真").getAsString());//同上一行
                mdata.setEmail(jsonObject.get("電子郵件").getAsString());
                mdata.setAddress(jsonObject.get("地址").getAsString());
                mdata.setWebsite(jsonObject.get("相關網址").getAsString());
                if(!jsonObject.get("X坐標").getAsString().isEmpty()){mdata.setxCoordinate(jsonObject.get("X坐標").getAsInt());}//避免getAsInt報錯
                if(!jsonObject.get("X坐標").getAsString().isEmpty()){mdata.setyCoordinate(jsonObject.get("Y坐標").getAsInt());}
                mdata.setNotes(jsonObject.get("備註").getAsString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd");//解析時間格式
                java.util.Date date = sdf.parse(jsonObject.get("最後更新時間").getAsString());
                mdata.setLastUpdateTime(date);
                System.out.println("是否成功插入: "+MyDataDAO.insertData(mdata));//把物件丟給插入方法新增到資料庫
            }
        }catch (ParseException e) {System.out.println("時間轉換失敗");e.printStackTrace();}
    }//把Json檔案解析後的陣列導入資料庫
    static public List<String> getColumnList(String tableName) {
        List<String> columnNames = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConn()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, tableName, null);
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                if (columnName.equals("ResourceAgency") || columnName.equals("HospitalName") || columnName.equals("Address")){columnNames.add(columnName);}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columnNames;
    }//只返回某資料表的"資料彙整機關"、"醫院名稱"、"地址"
    static public List<String> getAllColumnList(String tableName) {
        List<String> columnNames = new ArrayList<>();
        try (Connection conn = ConnectionFactory.getConn()) {
            DatabaseMetaData metaData = conn.getMetaData();
            ResultSet rs = metaData.getColumns(null, null, tableName, null);
            while (rs.next()) {
                String columnName = rs.getString("COLUMN_NAME");
                if(!"id".equals(columnName)){columnNames.add(columnName);}
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columnNames;
    }//返回某資料表全部欄位(除了id)
    static public List<String> getFiledList(String tableName, String feildName){
        List<String> FiledList = new ArrayList<>();
        String sql = "SELECT "+feildName+" FROM "+tableName;
        try (Connection conn = ConnectionFactory.getConn();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
//            pstmt.setString(1,feildName);
//            pstmt.setString(2,tableName);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int i = 0;
                FiledList.add(rs.getString(feildName));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return FiledList;
    }//返回某欄位所有元素
    static public void exportAllDataToJsonfile(String tableName){
        String sql = "SELECT * FROM " + tableName;
        try (Connection conn = ConnectionFactory.getConn();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter fileWriter = new FileWriter(tableName + ".json");

            while (rs.next()) {
                Mydata data = new Mydata();
                data.setResourceAgency(rs.getString("ResourceAgency"));
                data.setHospitalName(rs.getString("HospitalName"));
                data.setPhoneNumber(rs.getString("PhoneNumber"));
                data.setFax(rs.getString("Fax"));
                data.setEmail(rs.getString("Email"));
                data.setAddress(rs.getString("Address"));
                data.setWebsite(rs.getString("Website"));
                data.setxCoordinate(rs.getInt("xCoordinate"));
                data.setyCoordinate(rs.getInt("yCoordinate"));
                data.setNotes(rs.getString("Notes"));
                data.setLastUpdateTime(rs.getDate("LastUpdateTime"));
                // 将对象转换为 JSON 字符串并写入文件
                String json = gson.toJson(data);
                fileWriter.write(json);
                fileWriter.write("\n"); // 可选，每个对象之间换行分隔
            }

            fileWriter.close();
            System.out.println("Data exported successfully to " + tableName + ".json");
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
    }//指定資料表全部匯出 //todo 日期輸出跟原本不一致
    static public void exportDataToJsonfile(List<Mydata> selectList,String fileName) {
    	Gson gson = new GsonBuilder().setPrettyPrinting().create();
    	for (Mydata mydata : selectList) {
    		try(FileWriter fileWriter = new FileWriter(fileName + ".json");){
    			String json = gson.toJson(mydata);
                fileWriter.append(json);
                fileWriter.append("\n");
    		}catch (IOException e) { /*導出失敗*/}
		}
    }
/*    static public java.util.Date RocDateStringToAdDate(String str){
        DateTimeFormatter rocdtf=DateTimeFormatter.ofPattern("yyy/MM/dd");
        LocalDate rocDate = LocalDate.parse(str, rocdtf);
        rocDate=rocDate.plusYears(1911);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            java.util.Date adDate = sdf.parse(String.valueOf(rocDate));
            return adDate;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }*///todo 似乎不會用到
//String replaceField = field.replaceAll("[;\"'{}\\[\\]]", "");// 過濾用語句
}


