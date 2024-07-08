package lk.ijse.pos.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

public class OrdersDTO implements Serializable {

    private String order_id;
    private LocalDate date;
    private String customer_id ;

    List<OrderDetailDTO> orderDetail;
    public OrdersDTO(String orderId, LocalDate orderDate, String customerId, List<OrderDetailDTO> orderDetails){}

    public OrdersDTO(String order_id, LocalDate date, String customer_id){
        this.order_id=order_id;
        this.date=date;
        this.customer_id=customer_id;
    }

    public List<OrderDetailDTO> getOrderDetail() {
        return orderDetail;
    }
    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(String customer_id) {
        this.customer_id = customer_id;
    }
    @Override
    public String toString() {
        return "OrderTm{" +
                "order_id='" + order_id + '\'' +
                ", date='" + date + '\'' +
                ", customer_id=" + customer_id +
                '}';
    }
}
