package model;

public class Publisher {
    private String pubId;
    private String pubName;
    private String pubTitle;
    private double pubPayments;

    public Publisher() {
    }

    public Publisher(String pubId, String pubName, String pubTitle, double pubPayments) {
        this.pubId = pubId;
        this.pubName = pubName;
        this.pubTitle = pubTitle;
        this.pubPayments = pubPayments;
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

    @Override
    public String toString() {
        return "Publisher{" +
                "pubId='" + pubId + '\'' +
                ", pubName='" + pubName + '\'' +
                ", pubTitle='" + pubTitle + '\'' +
                ", pubPayments=" + pubPayments +
                '}';
    }
}
