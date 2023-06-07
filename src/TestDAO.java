import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestDAO {
    public static void main(String[] args) {
//        Mydata data1=new Mydata();
//        data1.setHospitalName("test");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
//        Date date = null;
//        try {
//            date = sdf.parse("1998/04/23");
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        data1.setLastUpdateTime(date);
//        MyDataDAO.insertData(data1);

//        Mydata data2=new Mydata();
//        data2 = MyDataDAO.selectData(18);
//        if (data2 != null) {
//            System.out.println(data2.getWebsite());
//        }

        MyDataDAO.updateData(16,"ResourceAgency","高雄縣政府");


    }
}
