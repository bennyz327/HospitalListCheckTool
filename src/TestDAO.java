import javax.swing.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
//        Mydata data1=new Mydata();
//        data1.setHospitalName("home");
//        data1.setPhoneNumber("02345678");
//        System.out.println(data1.getPhoneNumber());
//        try {
//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//            Date date = sdf.parse("1998/04/23");
//            data1.setLastUpdateTime(date);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }

//        MyDataDAO.insertData(data1);

//        Mydata data2=new Mydata();
//        data2 = MyDataDAO.selectData(18);
//        if (data2 != null) {
//            System.out.println(data2.getWebsite());
//        }

//        MyDataDAO.updateData(3,"LastUpdateTime","123/01/01");
        JOptionPane optionPane = new JOptionPane();
        List<String> fieldList=new ArrayList<>();
        fieldList.add("A");
        fieldList.add("B");
        fieldList.add("C");
        for (String s : fieldList) {
            System.out.println(s);
        }


    }
}
