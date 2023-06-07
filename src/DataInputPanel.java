import javax.swing.*;
import java.awt.*;
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
        faxField = new JTextField(20);
        emailField = new JTextField(20);
        addressField = new JTextField(20);
        websiteField = new JTextField(20);
        xCoordinateField = new JTextField(20);
        yCoordinateField = new JTextField(20);
        notesField = new JTextField(20);
        lastUpdateTimeField = new JTextField(20);

        inputPanel.add(new JLabel("資源彙整機關："));
        inputPanel.add(resourceAgencyField);
        inputPanel.add(new JLabel("醫院名稱："));
        inputPanel.add(hospitalNameField);
        inputPanel.add(new JLabel("連絡電話："));
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
}