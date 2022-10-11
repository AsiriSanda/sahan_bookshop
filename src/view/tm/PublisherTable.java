package view.tm;

import javafx.scene.control.Button;

public class PublisherTable {
    private String pubId;
    private String pubName;
    private String pubTitle;
    private double pubPayments;
    private Button button;

    public PublisherTable() {
    }

    public PublisherTable(String pubId, String pubName, String pubTitle, double pubPayments, Button button) {
        this.pubId = pubId;
        this.pubName = pubName;
        this.pubTitle = pubTitle;
        this.pubPayments = pubPayments;
        this.button = button;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public String getPubName() {
        return pubName;
    }

    public void setPubName(String pubName) {
        this.pubName = pubName;
    }

    public String getPubTitle() {
        return pubTitle;
    }

    public void setPubTitle(String pubTitle) {
        this.pubTitle = pubTitle;
    }

    public double getPubPayments() {
        return pubPayments;
    }

    public void setPubPayments(double pubPayments) {
        this.pubPayments = pubPayments;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "PublisherTable{" +
                "pubId='" + pubId + '\'' +
                ", pubName='" + pubName + '\'' +
                ", pubTitle='" + pubTitle + '\'' +
                ", pubPayments=" + pubPayments +
                '}';
    }
}
