package lk.ijse.pos.view.tdm;

import java.math.BigDecimal;
import java.time.LocalDate;

public class salaryTm implements Comparable<salaryTm> {

    private String no;
    private BigDecimal amount;
    private LocalDate date;
    private String employee_id;

    public salaryTm(){}

    public salaryTm(String no,BigDecimal amount,LocalDate date,String employee_id){
        this.no=no;
        this.amount=amount;
        this.date=date;
        this.employee_id=employee_id;
    }
    public salaryTm(String no,BigDecimal amount,LocalDate date){
        this.no=no;
        this.amount=amount;
        this.date=date;

    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    @Override
    public int compareTo(salaryTm other) {
        return this.no.compareTo(other.no);
    }
    @Override
    public String toString() {
        return "salaryTm{" +
                "no='" + no + '\'' +
                ", amount='" + amount + '\'' +
                ", date=" + date +
                ", employee_id=" + employee_id +
                '}';
    }
}
