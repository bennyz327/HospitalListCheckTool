import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserInteract {
    public static void main(String[] args) {
        /* //todo 之後改為讓使用者選擇是否要導入範例資料
        String path = "data/data.json";
        if(MyDataDAO.readJsonFile(path) != null){
            MyDataDAO.importArrToDatabase(Objects.requireNonNull(MyDataDAO.readJsonFile(path)));
            System.out.println("導入資料完畢");
        }else {System.out.println("無法導入資料庫");}
        */

        //GUI
        //创建主窗口
        JFrame frame = new JFrame("主選單");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());

        // 创建选项按钮
        JButton addButton = new JButton("新增資料");

        JButton modifyButton = new JButton("查詢");
        //todo 改為依照條件後讓使用者選擇，在查詢頁面也可以新增按鈕讓使用者刪除
        //todo 透過查詢
        JButton deleteButton = new JButton("刪除");
        JButton exportButton = new JButton("導出資料");

        // 添加按钮到主窗口
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.add(addButton);
        buttonPanel.add(modifyButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportButton);
        frame.add(buttonPanel, BorderLayout.CENTER);
//新增功能//新增功能//新增功能//新增功能//新增功能//新增功能//新增功能//新增功能//新增功能
        ActionListener addListener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDataInputDialog();
            }
        };//實作按鈕事件
        addButton.addActionListener(addListener);//監聽按鈕事件
//查詢功能//查詢功能//查詢功能//查詢功能//查詢功能//查詢功能//查詢功能//查詢功能//查詢功能
        ActionListener modifyListener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> fieldList = MyDataDAO.getColumnList("HospitalList");//呼叫DAO獲得欄位清單
                //篩選可用來查詢的
                //建立視窗
                JOptionPane optionPane = new JOptionPane();
                JComboBox<String> comboBox = new JComboBox<>(fieldList.toArray(new String[0]));
                optionPane.setMessage(new Object[]{"請選擇要用來查詢的欄位", comboBox});
                optionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
                JDialog dialog = optionPane.createDialog(null, "修改資料");
                //顯示視窗
                dialog.setVisible(true);
                if (optionPane.getValue() != null && optionPane.getValue().equals(JOptionPane.OK_OPTION)) {
                    try {
                        String selectedFieldName = Objects.requireNonNull(comboBox.getSelectedItem()).toString();   //获取用户选择的字段名称
                        System.out.println(selectedFieldName);
                        String key = JOptionPane.showInputDialog(null,"請輸入查詢關鍵字");
                        System.out.println(key);
                        List<Mydata> rsList = MyDataDAO.selectDataByColumn(selectedFieldName,key);
                        if(!rsList.isEmpty()){
                            System.out.println("有資料");;new DataShowTable(rsList);
                        }else {
                            JOptionPane.showMessageDialog(dialog, "查無資料！", "錯誤", JOptionPane.ERROR_MESSAGE);
                            dialog.dispose();
                        }
                    }catch (NullPointerException ne){System.out.println("選擇欄位為空");}
                    //顯示輸入框
                }
            }
        };//做成查詢功能包含修改跟刪除
        modifyButton.addActionListener(modifyListener);

//導出功能//導出功能//導出功能//導出功能//導出功能//導出功能//導出功能//導出功能//導出功能
        //todo 讓使用者可以選擇路徑
        ActionListener exportListenner= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int option = JOptionPane.showConfirmDialog(null, "確定要導出資料嗎？", "確認", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                System.out.println("下令");
                MyDataDAO.exportAllDataToJsonfile("HospitalList");
                System.out.println("完成");}
            }
        };
        exportButton.addActionListener(exportListenner);
//刪除功能//刪除功能//刪除功能//刪除功能//刪除功能//刪除功能//刪除功能//刪除功能//刪除功能
        ActionListener deleteListenner= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> FiledList = MyDataDAO.getFiledList("HospitalList","HospitalName");
                JOptionPane deleteoptionPane = new JOptionPane();
                JComboBox<String> comboBox = new JComboBox<>(FiledList.toArray(new String[0]));
                deleteoptionPane.setMessage(new Object[]{"請選擇要刪除的資料", comboBox});
                deleteoptionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
                JDialog dialog = deleteoptionPane.createDialog(null, "刪除資料");
                //顯示視窗
                dialog.setVisible(true);
                if (deleteoptionPane.getValue() != null && deleteoptionPane.getValue().equals(JOptionPane.OK_OPTION)) {
                    System.out.println(comboBox.getSelectedItem());
                    MyDataDAO.deleteDataByName((String) comboBox.getSelectedItem());
                }
            }
        };
        deleteButton.addActionListener(deleteListenner);

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
                if(!phoneNumber.getText().isEmpty()){
                    if(phoneNumber.getText().matches("\\(?(0[2345678])\\)?[-.\\s]?\\d{7,8}")){
                        newData.setPhoneNumber(phoneNumber.getText());
                    }else {
                        JOptionPane.showMessageDialog(dialog, "電話號碼格式不正確！", "錯誤", JOptionPane.ERROR_MESSAGE);
                        dialog.dispose();// 關閉當前對話框，重新開啟新的對話框
                        showDataInputDialog();
                        return;
                    }
                }

                if(!fax.getText().isEmpty()){newData.setFax(fax.getText());}
                if(!email.getText().isEmpty()){newData.setEmail(email.getText());}
                if(!address.getText().isEmpty()){newData.setAddress(address.getText());}
                if(!website.getText().isEmpty()){newData.setWebsite(website.getText());}
                //todo 做數字判斷
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
        };//todo 可以提取"從輸入視窗獲取使用者資料的實作"
        ActionListener cancelButton=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        };
        inputPanel.getConfirmButton().addActionListener(confirmButton);
        inputPanel.getCancelButton().addActionListener(cancelButton);
        //準備完成並顯示視窗
        dialog.setVisible(true);
    }//屬性輸入視窗 todo 分割出去成單獨class

}

