import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DataInputPanel extends JPanel {
    private JTextField resourceAgencyField;
    private JTextField hospitalNameField;
    private JTextField phoneNumberField;
    private JTextField faxField;
    private JTextField emailField;
    private JTextField addressField;
    private JTextField websiteField;
    private JTextField xCoordinateField;
    private JTextField yCoordinateField;
    private JTextField notesField;
    private JTextField lastUpdateTimeField;
    private JButton confirmButton;
    private JButton cancelButton;

    public DataInputPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridLayout(11, 2));
        resourceAgencyField = new JTextField(20);
        hospitalNameField = new JTextField(20);
        phoneNumberField = new JTextField(20);
        phoneNumberField.setText("電話格式：00-1234567(8)");
        faxField = new JTextField(20);
        emailField = new JTextField(20);
        addressField = new JTextField(20);
        websiteField = new JTextField(20);
        xCoordinateField = new JTextField(20);
        yCoordinateField = new JTextField(20);
        notesField = new JTextField(20);
        lastUpdateTimeField = new JTextField(20);
        lastUpdateTimeField.setText("日期格式：yyyMMdd");

        inputPanel.add(new JLabel("資源彙整機關："));
        inputPanel.add(resourceAgencyField);
        inputPanel.add(new JLabel("醫院名稱："));
        inputPanel.add(hospitalNameField);
        inputPanel.add(new JLabel("連絡電話："));
        //設定提示字在輸入框中
        inputPanel.add(phoneNumberField);
        inputPanel.add(new JLabel("傳真："));
        inputPanel.add(faxField);
        inputPanel.add(new JLabel("電子郵件："));
        inputPanel.add(emailField);
        inputPanel.add(new JLabel("地址："));
        inputPanel.add(addressField);
        inputPanel.add(new JLabel("相關網址："));
        inputPanel.add(websiteField);
        inputPanel.add(new JLabel("X坐標："));
        inputPanel.add(xCoordinateField);
        inputPanel.add(new JLabel("Y坐標："));
        inputPanel.add(yCoordinateField);
        inputPanel.add(new JLabel("備註："));
        inputPanel.add(notesField);
        inputPanel.add(new JLabel("最後更新時間："));
        inputPanel.add(lastUpdateTimeField);

        JPanel buttonPanel = new JPanel();
        confirmButton = new JButton("確認");
        cancelButton = new JButton("取消");
        buttonPanel.add(confirmButton);
        buttonPanel.add(cancelButton);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTextField getResourceAgencyField() {
        return resourceAgencyField;
    }

    public void setResourceAgencyField(JTextField resourceAgencyField) {
        this.resourceAgencyField = resourceAgencyField;
    }

    public JTextField getHospitalNameField() {
        return hospitalNameField;
    }

    public void setHospitalNameField(JTextField hospitalNameField) {
        this.hospitalNameField = hospitalNameField;
    }

    public JTextField getPhoneNumberField() {
        return phoneNumberField;
    }

    public void setPhoneNumberField(JTextField phoneNumberField) {
        this.phoneNumberField = phoneNumberField;
    }

    public JTextField getFaxField() {
        return faxField;
    }

    public void setFaxField(JTextField faxField) {
        this.faxField = faxField;
    }

    public JTextField getEmailField() {
        return emailField;
    }

    public void setEmailField(JTextField emailField) {
        this.emailField = emailField;
    }

    public JTextField getAddressField() {
        return addressField;
    }

    public void setAddressField(JTextField addressField) {
        this.addressField = addressField;
    }

    public JTextField getWebsiteField() {
        return websiteField;
    }

    public void setWebsiteField(JTextField websiteField) {
        this.websiteField = websiteField;
    }

    public JTextField getxCoordinateField() {
        return xCoordinateField;
    }

    public void setxCoordinateField(JTextField xCoordinateField) {
        this.xCoordinateField = xCoordinateField;
    }

    public JTextField getyCoordinateField() {
        return yCoordinateField;
    }

    public void setyCoordinateField(JTextField yCoordinateField) {
        this.yCoordinateField = yCoordinateField;
    }

    public JTextField getNotesField() {
        return notesField;
    }

    public void setNotesField(JTextField notesField) {
        this.notesField = notesField;
    }

    public JTextField getLastUpdateTimeField() {
        return lastUpdateTimeField;
    }

    public void setLastUpdateTimeField(JTextField lastUpdateTimeField) {
        this.lastUpdateTimeField = lastUpdateTimeField;
    }

    public JButton getConfirmButton() {
        return confirmButton;
    }

    public void setConfirmButton(JButton confirmButton) {
        this.confirmButton = confirmButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public void setCancelButton(JButton cancelButton) {
        this.cancelButton = cancelButton;
    }
    //創造輸入視窗
    public static void showDataInputDialog() {
        // 建立對話框
        DataInputPanel inputPanel = new DataInputPanel();
        JDialog dialog = new JDialog();
        dialog.setTitle("新增功能");
        dialog.setSize(400, 500);
        dialog.setResizable(false);
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());
        dialog.add(inputPanel, BorderLayout.CENTER);
        //按鈕事件
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
                if(hospitalName.getText().isEmpty()){
                        JOptionPane.showMessageDialog(dialog, "醫院名稱不能為空", "錯誤", JOptionPane.ERROR_MESSAGE);
                        //不關閉視窗，也不呼叫下面的插入方法MyDataDAO.insertData(newData)，讓使用者修改原本視窗的資料並繼續監聽確認按鈕
                        return;
                }else {
                    newData.setHospitalName(hospitalName.getText());
                }//強制要使用者輸入醫院名稱
                if(!phoneNumber.getText().isEmpty()){
                    //不是空值就判斷格式
                    if(phoneNumber.getText().matches("\\(?(0[2345678])\\)?[-.\\s]?\\d{7,8}")){
                        //格式判斷通過
                        newData.setPhoneNumber(phoneNumber.getText());
                    }else {
                        //格式判斷失敗
                        JOptionPane.showMessageDialog(dialog, "電話號碼格式不正確！", "錯誤", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }//電話欄位有輸入則會判斷格式
                if(!fax.getText().isEmpty()){newData.setFax(fax.getText());}
                if(!email.getText().isEmpty()){newData.setEmail(email.getText());}
                if(!address.getText().isEmpty()){newData.setAddress(address.getText());}
                if(!website.getText().isEmpty()){newData.setWebsite(website.getText());}
                if(!xCoordinate.getText().isEmpty()){newData.setxCoordinate(Integer.parseInt(xCoordinate.getText()));}//todo 未做輸入判斷
                if(!yCoordinate.getText().isEmpty()){newData.setyCoordinate(Integer.parseInt(yCoordinate.getText()));}
                if(!notes.getText().isEmpty()){newData.setNotes(notes.getText());}
                if(!lastUpdateTime.getText().isEmpty()){
                    if (lastUpdateTime.getText().matches("\\d{7}")){
                        try {
                            //todo 要提醒使用者時間接收的格式為yyyMMdd
                            DateTimeFormatter rocdtf=DateTimeFormatter.ofPattern("yyyMMdd");
                            LocalDate rocDate = LocalDate.parse(lastUpdateTime.getText(), rocdtf);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                            Date adDate = sdf.parse(String.valueOf(rocDate));

                            newData.setLastUpdateTime(adDate);
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                    }else {
                        JOptionPane.showMessageDialog(dialog, "日期格式不正確！", "錯誤", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }//轉換時間格式//有輸入則會判斷格式
                //呼叫插入方法
                MyDataDAO.insertData(newData);
                JOptionPane.showMessageDialog(null,"新增完成");
                // 關閉視窗
                dialog.dispose();
            }
        };//確認 //todo 可以提取"從輸入視窗獲取使用者資料的實作"
        inputPanel.getConfirmButton().addActionListener(confirmButton);
        ActionListener cancelButton=new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        };//取消
        inputPanel.getCancelButton().addActionListener(cancelButton);
        //準備完成並顯示視窗
        dialog.setVisible(true);
    }//創造全屬性的輸入視窗
}