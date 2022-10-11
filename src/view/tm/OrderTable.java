package view.tm;

public class OrderTable {
    private String bookId;
    private int qty;
    private double unitPrice;
    private double total;

    public OrderTable() {
    }

    public OrderTable(String bookId, int qty, double unitPrice, double total) {
        this.bookId = bookId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.total = total;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderTable{" +
                "bookId='" + bookId + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}
