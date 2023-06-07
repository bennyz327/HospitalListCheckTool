
import ConnectionPack.ConnectionFactory;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UserInteract {
    public static void main(String[] args) {
        /*//不要重複調用插入
        //導入Json檔案  //todo 移到DAO
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
                String convertPhone = jsonObject.get("連絡電話").getAsString().replaceAll("[()]", "");
                mdata.setPhoneNumber(convertPhone);//去除括號轉換成數字
                //todo 處裡空值 可交由insert判斷?
                try {
                    int convertFax = Integer.parseInt(jsonObject.get("傳真").getAsString());
                    mdata.setFax(convertFax);//去除括號轉換成數字
                }catch (NumberFormatException e){}
                mdata.setEmail(jsonObject.get("電子郵件").getAsString());
                mdata.setAddress(jsonObject.get("地址").getAsString());
                mdata.setWebsite(jsonObject.get("相關網址").getAsString());
                //todo 處裡空值 可交由insert判斷?
                try {
                    mdata.setxCoordinate(jsonObject.get("X坐標").getAsInt());
                }catch (NumberFormatException e){}
                //todo 處裡空值 可交由insert判斷?
                try {
                    mdata.setyCoordinate(jsonObject.get("Y坐標").getAsInt());
                }catch (NumberFormatException e){}
                mdata.setNotes(jsonObject.get("備註").getAsString());
                SimpleDateFormat sdf = new SimpleDateFormat("yyy/MM/dd");
                java.util.Date date = sdf.parse(jsonObject.get("最後更新時間").getAsString());
                mdata.setLastUpdateTime(date);//解析時間格式
                //插入資料庫
                System.out.println("是否成功插入: "+MyDataDAO.insertData(mdata));
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }//導入Json結束*/

        System.out.println("導入資料完畢");
        // 创建主窗口
        JFrame frame = new JFrame("主選單");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // 创建选项按钮
        JButton addButton = new JButton("新增功能");
        JButton modifyButton = new JButton("修改功能");
        JButton searchButton = new JButton("根據ID查詢功能");
        JButton deleteButton = new JButton("根據ID刪除功能");

        // 添加按钮到主窗口
        JPanel buttonPanel = new JPanel(new GridLayout(4, 1));
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(deleteButton);
        frame.add(buttonPanel, BorderLayout.CENTER);

        ActionListener addListener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDataInputDialog();
            }
        };
        addButton.addActionListener(addListener);//實作按鈕事件

        frame.setVisible(true);
    }

    private static void showDataInputDialog() {
        // 建立對話框
        DataInputPanel inputPanel = new DataInputPanel();
        JDialog dialog = new JDialog();
        dialog.setTitle("新增功能");
        dialog.setSize(400, 500);
        dialog.setResizable(false);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.add(inputPanel, BorderLayout.CENTER);
        //要接收資料的物件

        // 监听确认按钮点击事件
        ActionListener confirmButton=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){

                // 获取用户输入的属性值
                JTextField resourceAgency = inputPanel.getResourceAgencyField();
                JTextField hospitalName = inputPanel.getHospitalNameField();
                JTextField phoneNumber = inputPanel.getPhoneNumberField();
                JTextField fax = inputPanel.getFaxField();
                JTextField email = inputPanel.getEmailField();
                JTextField address = inputPanel.getAddressField();
                JTextField website = inputPanel.getWebsiteField();
                JTextField xCoordinate = inputPanel.getxCoordinateField();
                JTextField yCoordinate = inputPanel.getyCoordinateField();
                JTextField notes = inputPanel.getNotesField();
                JTextField lastUpdateTime = inputPanel.getLastUpdateTimeField();
                //準備要送入資料庫的物件和其中屬性
                Mydata newData=new Mydata();
                if(!resourceAgency.getText().isEmpty()){newData.setResourceAgency(resourceAgency.getText());}
                if(!hospitalName.getText().isEmpty()){newData.setHospitalName(hospitalName.getText());}
                if(!phoneNumber.getText().isEmpty()){newData.setPhoneNumber(phoneNumber.getText());}
                if(!fax.getText().isEmpty()){newData.setFax(Integer.parseInt(fax.getText()));}
                if(!email.getText().isEmpty()){newData.setEmail(email.getText());}
                if(!address.getText().isEmpty()){newData.setAddress(address.getText());}
                if(!website.getText().isEmpty()){newData.setWebsite(website.getText());}
                if(!xCoordinate.getText().isEmpty()){newData.setxCoordinate(Integer.parseInt(xCoordinate.getText()));}
                if(!yCoordinate.getText().isEmpty()){newData.setyCoordinate(Integer.parseInt(yCoordinate.getText()));}
                if(!notes.getText().isEmpty()){newData.setNotes(notes.getText());}
                if(!lastUpdateTime.getText().isEmpty()){
                    try {
                        Date sdf=new SimpleDateFormat("yyyy/MM/dd").parse("lastUpdateTime.getText()");
                        newData.setLastUpdateTime(sdf);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
                //呼叫方法
                MyDataDAO.insertData(newData);

                /*String sql = "insert into HospitalList(ResourceAgency,HospitalName,PhoneNumber,Fax,Email,Address,Website,xCoordinate,yCoordinate,Notes,LastUpdateTime) values (?,?,?,?,?,?,?,?,?,?,?)";
                try (Connection conn = ConnectionFactory.getConn();
                     PreparedStatement pstmt=conn.prepareStatement(sql);) {
                    //設置每個屬性值到SQL，若沒有值則設為null
                    if(!resourceAgency.getText().isEmpty()){pstmt.setString(1, resourceAgency.getText());}else {pstmt.setNull(1, Types.NVARCHAR);}
                    if(!hospitalName.getText().isEmpty()){pstmt.setString(2, hospitalName.getText());}else {pstmt.setNull(2, Types.NVARCHAR);}
                    if(!phoneNumber.getText().isEmpty()){pstmt.setInt(3, Integer.parseInt(phoneNumber.getText()));}else {pstmt.setNull(3, Types.INTEGER);}
                    if(!fax.getText().isEmpty()){pstmt.setInt(4, Integer.parseInt(fax.getText()));}else {pstmt.setNull(4, Types.INTEGER);}
                    if(!email.getText().isEmpty()){pstmt.setString(5, email.getText());}else {pstmt.setNull(5, Types.NVARCHAR);}
                    if(!address.getText().isEmpty()){pstmt.setString(6, address.getText());}else {pstmt.setNull(6, Types.NVARCHAR);}
                    if(!website.getText().isEmpty()){pstmt.setString(7, website.getText());}else {pstmt.setNull(7, Types.NVARCHAR);}
//                    8
//                    9
                    if (!lastUpdateTime.getText().isEmpty()) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                        try {
                            Date convertDate = (Date) sdf.parse(lastUpdateTime.getText());
                            pstmt.setDate(11, new java.sql.Date(convertDate.getTime()));
                        } catch (ParseException ex) {
                            // 处理日期格式不符合要求的情况
                            ex.printStackTrace();
                        }
                    } else {pstmt.setNull(11, Types.DATE);}

                }catch (SQLException ex){
                    ex.printStackTrace();
                }*/
                // 关闭对话框
                dialog.dispose();
            }
        };
        inputPanel.getConfirmButton().addActionListener(confirmButton);
        //準備完成並顯示視窗
        dialog.setVisible(true);
    }//顯示MyData相應的屬性輸入視窗

}

