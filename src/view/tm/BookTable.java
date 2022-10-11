package view.tm;

import javafx.scene.control.Button;

public class BookTable {
    private String bookId;
    private String pubId;
    private String bookTitle;
    private String bookAuthor;
    private int Qty;
    private double Price;
    private Button button;

    public BookTable() {
    }

    public BookTable(String bookId, String pubId, String bookTitle, String bookAuthor, int qty, double price, Button button) {
        this.bookId = bookId;
        this.pubId = pubId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        Qty = qty;
        Price = price;
        this.button = button;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getPubId() {
        return pubId;
    }

    public void setPubId(String pubId) {
        this.pubId = pubId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public int getQty() {
        return Qty;
    }

    public void setQty(int qty) {
        Qty = qty;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    public Button getButton() {
        return button;
    }

    public void setButton(Button button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return "BookTable{" +
                "bookId='" + bookId + '\'' +
                ", pubId='" + pubId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", Qty=" + Qty +
                ", Price=" + Price +
                '}';
    }
}
