package lk.ijse.pos.view.tdm;

public class fieldsTm implements Comparable<fieldsTm>{

    private String field_id;
    private String name;
    private String address;

    public fieldsTm(){

    }

    public fieldsTm(String id, String name, String address){
        this.field_id=id;
        this.name=name;
        this.address=address;

    }

    public String getField_id() {return field_id;
    }

    public void setField_id(String field_id) {this.field_id = field_id;
    }

    public String getName() {return name;
    }

    public void setName(String name) {this.name = name;
    }

    public String getAddress() {return address;
    }

    public void setAddress(String address) {this.address = address;
    }

    @Override
    public int compareTo(fieldsTm other) {
        return this.field_id.compareTo(other.field_id);
    }
    @Override
    public String toString(){
        return "fieldsTm{" +
                "id='" + field_id + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }




}
