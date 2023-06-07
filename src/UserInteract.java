import java.io.*;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class UserInteract {
    public static void main(String[] args) {
        try (FileInputStream is = new FileInputStream("data/data.json");
             BufferedInputStream bis = new BufferedInputStream(is);
             InputStreamReader isr = new InputStreamReader(bis, StandardCharsets.UTF_8);) {
            // 讀取 JSON 檔案
            StringBuilder jsonBuilder = new StringBuilder();
            int datastream;
            while ((datastream = isr.read()) != -1) {
                jsonBuilder.append((char) datastream);
            }
            String importJsonContent = jsonBuilder.toString();
            String jsonContent = importJsonContent.replaceAll("[\\n\\r\\t\\s\\u00A0]", "");
            //解析JSON檔案
            JsonArray jsArr = JsonParser.parseString(jsonContent).getAsJsonArray();
            Mydata mdata = new Mydata();
            for (int i = 0; i < jsArr.size(); i++) {
                JsonObject jsonObject = jsArr.get(i).getAsJsonObject();
                mdata.setResourceAgency(jsonObject.get("資源彙整機關").getAsString());
                mdata.setHospitalName(jsonObject.get("醫院名稱").getAsString());
                int convertPhone = Integer.parseInt(jsonObject.get("連絡電話").getAsString().replaceAll("[()]", ""));
                mdata.setPhoneNumber(convertPhone);//去除括號轉換成數字
                //todo 處裡空值
                try {
                    int convertFax = Integer.parseInt(jsonObject.get("傳真").getAsString());
                    mdata.setFax(convertFax);//去除括號轉換成數字
                }catch (NumberFormatException e){}
                mdata.setEmail(jsonObject.get("電子郵件").getAsString());
                mdata.setAddress(jsonObject.get("地址").getAsString());
                mdata.setWebsite(jsonObject.get("相關網址").getAsString());
                //todo 處裡空值
                try {
                    mdata.setxCoordinate(jsonObject.get("X坐標").getAsInt());
                }catch (NumberFormatException e){}
                //todo 處裡空值
                try {
                    mdata.setyCoordinate(jsonObject.get("Y坐標").getAsInt());
                }catch (NumberFormatException e){}
                mdata.setNotes(jsonObject.get("備註").getAsString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd");
                Date date = sdf.parse(jsonObject.get("最後更新時間").getAsString());
                mdata.setLastUpdateTime(date);//解析時間格式
                //插入資料庫
                System.out.println("是否成功插入: "+MyDataDAO.insertData(mdata));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
    }



}

