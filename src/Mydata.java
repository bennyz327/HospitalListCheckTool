import java.util.Date;
public class Mydata {
    Mydata(){super();}
    private String resourceAgency;
    private String hospitalName;
    private String phoneNumber;
//    private Integer phoneNumberInteger;
    private String fax;
//    private Integer faxInteger;
    private String email;
    private String address;
    private String website;
    private int xCoordinate;

    private int yCoordinate;

    private String notes;
    private Date lastUpdateTime;
    private Integer xCoordinateInteger;
    private Integer yCoordinateInteger;

    public String getResourceAgency() {
        return resourceAgency;
    }

    public void setResourceAgency(String resourceAgency) {
        this.resourceAgency = resourceAgency;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
//        this.phoneNumberInteger = phoneNumber;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(int xCoordinate) {
        this.xCoordinate = xCoordinate;
        this.xCoordinateInteger = xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(int yCoordinate) {
        this.yCoordinate = yCoordinate;
        this.yCoordinateInteger = yCoordinate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
    //以下Getter用來判斷是否Null
//    public Integer getPhoneNumberInteger() {
//        return phoneNumberInteger;
//    }
//    public Integer getFaxInteger() {
//        return faxInteger;
//    }

    public Integer getxCoordinateInteger() {
        return xCoordinateInteger;
    }

    public Integer getyCoordinateInteger() {
        return yCoordinateInteger;
    }
}
