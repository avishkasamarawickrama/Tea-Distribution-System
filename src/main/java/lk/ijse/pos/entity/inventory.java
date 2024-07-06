package lk.ijse.pos.entity;

import java.math.BigDecimal;


public class inventory  {

    private String category_id;
    private String category_name;
    private int qtyOnHand;
    private BigDecimal unitPrice;
    private String harvest_no;


    public inventory(){}

    public inventory(String id, String name, int qty, BigDecimal unitPrice, String harvest_no){
        this.category_id=id;
        this.category_name=name;
        this.qtyOnHand= qty;
        this.unitPrice=unitPrice;
        this.harvest_no=harvest_no;
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
        return qtyOnHand;
    }

    public void setQty(int qty) {
        this.qtyOnHand = qty;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getHarvest_no() {
        return harvest_no;
    }

    public void setHarvest_no(String harvest_no) {
        this.harvest_no = harvest_no;
    }

    @Override
    public String toString() {
        return "inventory{" +
                "id='" + category_id + '\'' +
                ", name='" + category_name + '\'' +
                ", qty=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                ", harvest_no=" + harvest_no +
                '}';
    }
}
