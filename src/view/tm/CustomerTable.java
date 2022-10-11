package view.tm;

import javafx.scene.control.Button;

public class CustomerTable {
    private String cusId;
    private String cusName;
    private String cusAddress;
    private String cusContact;
    private javafx.scene.control.Button button;

    public CustomerTable() {
    }

    public CustomerTable(String cusId, String cusName, String cusAddress, String cusContact, Button button) {
        this.cusId = cusId;
        this.cusName = cusName;
        this.cusAddress = cusAddress;
        this.cusContact = cusContact;
        this.button = button;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public String getCusName() {
        return cusName;
    }

    public void setCusName(String cusName) {
        this.cusName = cusName;
    }

    public String getCusAddress() {
        return cusAddress;
    }

    public void setCusAddress(String cusAddress) {
        this.cusAddress = cusAddress;
    }

    public String getCusContact() {
        return cusContact;
    }

    public void setCusContact(String cusContact) {
        this.cusContact = cusContact;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "CustomerTable{" +
                "cusId='" + cusId + '\'' +
                ", cusName='" + cusName + '\'' +
                ", cusAddress='" + cusAddress + '\'' +
                ", cusContact='" + cusContact + '\'' +
                '}';
    }
}
