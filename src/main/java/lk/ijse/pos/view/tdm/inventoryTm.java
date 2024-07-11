package lk.ijse.pos.view.tdm;


import java.math.BigDecimal;


public class inventoryTm  implements Comparable<inventoryTm>{

    private String category_id;
    private String category_name;
    private int qtyOnHand;
    private BigDecimal unitPrice;
    private String harvest_no;


    public inventoryTm(){}

    public inventoryTm(String id,String name,int qtyOnHand,BigDecimal unitPrice,String harvest_no){
        this.category_id=id;
        this.category_name=name;
        this.qtyOnHand= qtyOnHand;
        this.unitPrice=unitPrice;
        this.harvest_no=harvest_no;
    }
    public inventoryTm(String id,String name,int qtyOnHand,BigDecimal unitPrice){
        this.category_id=id;
        this.category_name=name;
        this.qtyOnHand= qtyOnHand;
        this.unitPrice=unitPrice;

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

    public int getQtyOnHand() {
        return qtyOnHand;
    }

    public void setQtyOnHand(int qtyOnHand) {
        this.qtyOnHand = qtyOnHand;
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
    public int compareTo(inventoryTm other) {
        return this.category_id.compareTo(other.category_id);
    }
    @Override
    public String toString() {
        return "inventoryTm{" +
                "id='" + category_id + '\'' +
                ", name='" + category_name + '\'' +
                ", qtyOnHand=" + qtyOnHand +
                ", unitPrice=" + unitPrice +
                ", harvest_no=" + harvest_no +
                '}';
    }
}
