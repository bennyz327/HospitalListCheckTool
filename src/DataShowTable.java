import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.List;
public class DataShowTable extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public DataShowTable(List<Mydata> rsList) {
        setTitle("Query Result");
        setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        setSize(1000, 500);
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        Mydata firstData = rsList.get(0);// 获取第一条数据
        //設定欄位
        int columnCount = firstData.getClass().getDeclaredFields().length-2;
        for (int i = 0; i < columnCount; i++) {
            TableColumn column = new TableColumn(i);
            column.setPreferredWidth(100);
            tableModel.addColumn(firstData.getClass().getDeclaredFields()[i].getName());
        }

    //todo 先求有再求好
        // 添加2列給修改按钮列和删除按钮列
//        tableModel.addColumn("");
//        tableModel.addColumn("");
// 创建按钮渲染器和编辑器
//        table.getColumnModel().getColumn(columnCount).setCellRenderer(new MyButtonRender("修改"));
//        table.getColumnModel().getColumn(columnCount + 1).setCellRenderer(new MyButtonRender("刪除"));
        table.setRowHeight(30);


        // 渲染表格
        for (Mydata data : rsList) {
            //rowData代表每一筆資料
            Object[] rowData = new Object[columnCount];// + 2];
            rowData[0] = data.getResourceAgency();
            rowData[1] = data.getHospitalName();
            rowData[2] = data.getPhoneNumber();
            rowData[3] = data.getFax();
            rowData[4] = data.getEmail();
            rowData[5] = data.getAddress();
            rowData[6] = data.getWebsite();
            rowData[7] = data.getxCoordinate();
            rowData[8] = data.getyCoordinate();
            rowData[9] = data.getNotes();
            rowData[10] = data.getLastUpdateTime();
//            rowData[columnCount] = new JButton("Modify"); // 修改按钮
//            rowData[columnCount + 1] = new JButton("Delete"); // 删除按钮

            tableModel.addRow(rowData);
        }
        //窗口位置並顯示
        setLocation(200,100);
        setVisible(true);
    }

//測試用
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//                new DataShowTable();
//            }
//        });
//    }
}
