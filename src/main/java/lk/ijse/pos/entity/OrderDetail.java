package lk.ijse.pos.entity;


import java.io.Serializable;
import java.math.BigDecimal;

public class OrderDetail  {

      private  String order_id;
        private String category_id;
        private String category_name;
        private int qty;
        private BigDecimal unitPrice;
        private BigDecimal total;

        public OrderDetail(){}

    public OrderDetail(String category_id, String category_name, int qty, BigDecimal unitPrice, BigDecimal total){
            this.category_id=category_id;
            this.category_name=category_name;
            this.qty= qty;
            this.unitPrice=unitPrice;
            this.total=total;

    }
    public OrderDetail(String order_id, String category_id , int qty, BigDecimal unitPrice){
            this.order_id= order_id;
            this.category_id= category_id;
            this.qty=qty;
            this.unitPrice=unitPrice;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "OrderDetail{" +
                "id='" + category_id + '\'' +
                ", name='" + category_name + '\'' +
                ", qty=" + qty +
                ", unitPrice=" + unitPrice +
                ", total=" + total +
                '}';
    }
}

