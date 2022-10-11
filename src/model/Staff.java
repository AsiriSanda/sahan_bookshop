package model;

public class Staff {

    private String staffId;
    private String staffName;
    private String staffAddress;
    private int staffAge;
    private String staffContact;

    public Staff() {
    }

    public Staff(String staffId, String staffName, String staffAddress, int staffAge, String staffContact) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffAddress = staffAddress;
        this.staffAge = staffAge;
        this.staffContact = staffContact;
    }

    public String getStaffId() {
        return staffId;
    }

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffAddress() {
        return staffAddress;
    }

    public void setStaffAddress(String staffAddress) {
        this.staffAddress = staffAddress;
    }

    public int getStaffAge() {
        return staffAge;
    }

    public void setStaffAge(int staffAge) {
        this.staffAge = staffAge;
    }

    public String getStaffContact() {
        return staffContact;
    }

    public void setStaffContact(String staffContact) {
        this.staffContact = staffContact;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "staffId='" + staffId + '\'' +
                ", staffName='" + staffName + '\'' +
                ", staffAddress='" + staffAddress + '\'' +
                ", staffAge=" + staffAge +
                ", staffContact='" + staffContact + '\'' +
                '}';
    }
}
