import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class UserInteract {
    public static void main(String[] args) {
        //匯入資料 //todo 從網路載入
 /*
        String path = "data/data.json";
        if(MyDataDAO.readJsonFile(path) != null){
            MyDataDAO.importArrToDatabase(Objects.requireNonNull(MyDataDAO.readJsonFile(path)));
            System.out.println("導入資料完畢");
        }else {System.out.println("無法導入資料庫");}
*/
        //宣告主窗口
        JFrame frame = new JFrame("主選單");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLayout(new BorderLayout());
        //宣告按鈕
        JButton addButton = new JButton("新增資料");
        JButton searchButton = new JButton("查詢");
        JButton updateButton = new JButton("修改");//修改可更新字串、數字、日期(格式yyyy/MM/dd)
        //todo 改為依照條件後讓使用者選擇，在查詢頁面也可以新增按鈕讓使用者刪除
        //todo 透過查詢
        JButton deleteButton = new JButton("刪除");
        JButton exportButton = new JButton("導出資料");
        // 添加按鈕
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1));
        buttonPanel.add(addButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(exportButton);
        frame.add(buttonPanel, BorderLayout.CENTER);
        //按鈕事件
//新增功能//新增功能//新增功能//新增功能//新增功能//新增功能//新增功能//新增功能//新增功能
        ActionListener addListener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataInputPanel.showDataInputDialog();
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
                JDialog dialog = optionPane.createDialog(null, "查詢資料");
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
                            System.out.println("有資料");
                            
                            //渲染結果視窗
                            new DataShowTable(rsList);

                            //導出查詢結果確認視窗
                            int option = JOptionPane.showConfirmDialog(null, "要導出資料嗎？", "確認", JOptionPane.YES_NO_OPTION);
                            if (option == JOptionPane.YES_OPTION) {
                                String inputFileName = JOptionPane.showInputDialog(null, "請輸入檔案名稱" );
                                MyDataDAO.exportDataToJsonfile(rsList, inputFileName);
                            }
                            
                        }else {
                            JOptionPane.showMessageDialog(dialog, "查無資料！", "錯誤", JOptionPane.ERROR_MESSAGE);
                            dialog.dispose();
                        }
                    }catch (NullPointerException ne){System.out.println("選擇欄位為空");}
                    //顯示輸入框
                }
            }
        };//做成查詢功能包含修改跟刪除
        searchButton.addActionListener(modifyListener);
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
//修改功能//修改功能//修改功能//修改功能//修改功能//修改功能//修改功能//修改功能//修改功能
        ActionListener updateListener= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //選擇資料的下拉清單
                List<String> modifyList = MyDataDAO.getFiledList("HospitalList","HospitalName");
                JOptionPane modifyOptionPanel = new JOptionPane();
                JComboBox<String> comboBox = new JComboBox<>(modifyList.toArray(new String[0]));
                modifyOptionPanel.setMessage(new Object[]{"請選擇要修改的資料", comboBox});
                modifyOptionPanel.setOptionType(JOptionPane.OK_CANCEL_OPTION);
                JDialog modifydialog = modifyOptionPanel.createDialog(null, "修改資料");
                modifydialog.setVisible(true);
                if (modifyOptionPanel.getValue() != null && modifyOptionPanel.getValue().equals(JOptionPane.OK_OPTION)) {
                    //設定要修改的資料欄位
                    String selectDataName = Objects.requireNonNull(comboBox.getSelectedItem()).toString();
                    //顯示修改欄位的下拉清單
                    List<String> updateFieldList = MyDataDAO.getAllColumnList("HospitalList");//呼叫DAO獲得欄位清單
                    JOptionPane fieldOptionPane = new JOptionPane();
                    JComboBox<String> fieldComboBox = new JComboBox<>(updateFieldList.toArray(new String[0]));
                    fieldOptionPane.setMessage(new Object[]{"請選擇要修改的欄位", fieldComboBox});
                    fieldOptionPane.setOptionType(JOptionPane.OK_CANCEL_OPTION);
                    JDialog fieldOptiondialog = fieldOptionPane.createDialog(null, "修改資料");
                    fieldOptiondialog.setVisible(true);//顯示視窗
                    if (fieldOptionPane.getValue() != null && fieldOptionPane.getValue().equals(JOptionPane.OK_OPTION)) {
                        String selectField = Objects.requireNonNull(fieldComboBox.getSelectedItem()).toString();
                        //準備取得使用者要修改的值
                        //如果選擇特定欄位// 判斷電話、日期格式、持續要求使用者輸入正確格式
                        String value = null;
                            if (selectField.equals("PhoneNumber")) {
                                // 判斷電話格式
                                boolean phoneNumberNotOK = true;
                                while (phoneNumberNotOK){
                                    value = JOptionPane.showInputDialog(null,"請輸入電話");
                                    if (!value.matches("\\(?(0[2345678])\\)?[-.\\s]?\\d{7,8}")) {
                                        JOptionPane.showMessageDialog(null, "電話號碼格式不正確！", "錯誤", JOptionPane.ERROR_MESSAGE);
                                    }else {phoneNumberNotOK=false;}
                                }
                                MyDataDAO.updateData(selectDataName,selectField,value);
                            } else
                            if (selectField.equals("LastUpdateTime")) {
                            // 判斷日期格式
                                boolean lastUpdateTimeNotOK = true;
                                while (lastUpdateTimeNotOK){
                                    value = JOptionPane.showInputDialog(null,"請輸入日期!");
                                    if (!value.matches("\\d{7}")) {
                                        JOptionPane.showMessageDialog(null, "日期格式不正確！", "錯誤", JOptionPane.ERROR_MESSAGE);
                                    }else {lastUpdateTimeNotOK=false;}
                                }
                                MyDataDAO.updateData(selectDataName,selectField,value);
                            }else {
                                //若是其他字串欄位
                                value = JOptionPane.showInputDialog(null,"請輸入修改內容");
                                //送出內容
                                MyDataDAO.updateData(selectDataName,selectField,value);
                            }
                    }
                }

            }
        };
        updateButton.addActionListener(updateListener);
        //顯示主選單
        frame.setVisible(true);
    }

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
                }*///無用
}

