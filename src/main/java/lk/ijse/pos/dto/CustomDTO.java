package lk.ijse.pos.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public class CustomDTO {
    //customer
    private String customer_id;
    private String name;
    private String email;
    private int contact;

    //employee
    private String employee_id;
    private String employee_name;
    private int em_contact;
    private String status;
    private String id;


    //field
    private String field_id;
    private String field_name;
    private String address;

    //harvest
    private String harvest_no;

    // private int qty;
    private LocalDate date;
    //  private String field_id;


    //inventory
    private String category_id;
    private String category_name;
    private int qtyOnHand;
    private BigDecimal unitPrice;
    // private String harvest_no;

    //orders
    private String order_id;

    // private LocalDate date;
    private String customerID ;


    //orderDetails

    //  private String category_id;
    //  private String category_name;
    private int qty;
    private BigDecimal UnitPrice;

    //salary

    private String no;
    private BigDecimal amount;
    //  private LocalDate date;
    private String employeeID;


    public CustomDTO() {
    }

    public CustomDTO(String oid, LocalDate date, String customerID, String category_id, int qty, BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
        this.order_id = oid;
        this.date = date;
        this.customerID = customerID;
        this.category_id = category_id;
        this.qty = qty;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCode() {
        return category_id;
    }

    public void setCode(String code) {
        this.category_id = code;
    }

    public String getDescription() {
        return category_name;
    }

    public void setDescription(String description) {
        this.category_name = description;
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

    public String getOid() {
        return order_id;
    }

    public void setOid(String oid) {
        this.order_id = oid;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getItemCode() {
        return category_id;
    }

    public void setItemCode(String itemCode) {
        this.category_name = itemCode;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
