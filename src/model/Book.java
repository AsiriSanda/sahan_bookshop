package model;

public class Book {
    private String bookId;
    private String pubId;
    private String bookTitle;
    private String bookAuthor;
    private int Qty;
    private double Price;

    public Book() {
    }

    public Book(String bookId, String pubId, String bookTitle, String bookAuthor, int qty, double price) {
        this.bookId = bookId;
        this.pubId = pubId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        Qty = qty;
        Price = price;
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

    @Override
    public String toString() {
        return "Books{" +
                "bookId='" + bookId + '\'' +
                ", pubId='" + pubId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", Qty=" + Qty +
                ", Price=" + Price +
                '}';
    }
}
