package view.tm;

import javafx.scene.control.Button;

public class StaffTable {
    private String staffId;
    private String staffName;
    private String staffAddress;
    private int staffAge;
    private String staffContact;
    private Button button;

    public StaffTable() {
    }

    public StaffTable(String staffId, String staffName, String staffAddress, int staffAge, String staffContact, Button button) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.staffAddress = staffAddress;
        this.staffAge = staffAge;
        this.staffContact = staffContact;
        this.button = button;
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

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "StaffTable{" +
                "staffId='" + staffId + '\'' +
                ", staffName='" + staffName + '\'' +
                ", staffAddress='" + staffAddress + '\'' +
                ", staffAge=" + staffAge +
                ", staffContact='" + staffContact + '\'' +
                '}';
    }
}



