package lk.ijse.pos.view.tdm;

import java.time.LocalDate;

public class OrdersTm {

    private String order_id;
    private LocalDate date;
    private String customer_id ;

    public  OrdersTm(){}

    public OrdersTm(String order_id,LocalDate date,String customer_id){
        this.order_id=order_id;
        this.date=date;
        this.customer_id=customer_id;
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
