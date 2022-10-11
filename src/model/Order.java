package model;

import java.time.LocalDate;
import java.util.List;

public class Order {
    private String orderId;
    private String cusId;
    private LocalDate date;
    private double totalAmount;
    private List<OrderDetails> details;

    public Order() {
    }

    public Order(String orderId, String cusId, LocalDate date, double totalAmount, List<OrderDetails> details) {
        this.orderId = orderId;
        this.cusId = cusId;
        this.date = date;
        this.totalAmount = totalAmount;
        this.details = details;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCusId() {
        return cusId;
    }

    public void setCusId(String cusId) {
        this.cusId = cusId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public List<OrderDetails> getDetails() {
        return details;
    }

    public void setDetails(List<OrderDetails> details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", cusId='" + cusId + '\'' +
                ", date=" + date +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
