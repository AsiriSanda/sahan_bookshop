package model;

public class OrderDetails {
    private String orderId;
    private String bookId;
    private int qty;
    private double unitPrice;
    private double totalAmount;

    public OrderDetails() {
    }

    public OrderDetails(String orderId, String bookId, int qty, double unitPrice, double totalAmount) {
        this.orderId = orderId;
        this.bookId = bookId;
        this.qty = qty;
        this.unitPrice = unitPrice;
        this.totalAmount = totalAmount;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
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

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return "OrderDetails{" +
                "orderId='" + orderId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
