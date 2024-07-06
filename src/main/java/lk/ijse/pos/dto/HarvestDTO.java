package lk.ijse.pos.dto;
import java.io.Serializable;
import java.time.LocalDate;

public class HarvestDTO  implements Serializable {

    private String harvest_no;
    private int qty;
    private LocalDate date;
    private String field_id;

    public HarvestDTO(){

    }

    public HarvestDTO(String no, int qty, LocalDate date, String id){
        this.harvest_no= no;
        this.qty=qty;
        this.date=date;
        this.field_id= id;
    }
    public HarvestDTO(String no, int qty, LocalDate date){
        this.harvest_no= no;
        this.qty=qty;
        this.date=date;

    }
    public String getHarvest_no() {return harvest_no;
    }

    public void setHarvest_no(String harvest_no) {this.harvest_no = harvest_no;
    }

    public int getQty() {return qty;
    }

    public void setQty(int qty) {this.qty = qty;
    }

    public LocalDate getDate() {return date;
    }

    public void setDate(LocalDate date) {this.date = date;
    }

    public String getField_id() {return field_id;
    }

    public void setField_id(String field_id) {this.field_id = field_id;
    }



    @Override
    public String toString() {
        return "HarvestTm{" +
                "No='" + harvest_no + '\'' +
                ", qty=" + qty +
                ", date='" + date + '\'' +
                ", field_id='" + field_id + '\'' +
                '}';
    }

}
