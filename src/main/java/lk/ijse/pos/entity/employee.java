package lk.ijse.pos.entity;


public class employee  {

    private String employee_id;
    private String name;
    private int contact;
    private String status;
    private String id;
    private String field_id;

    public employee(String employeeId, String name, int contact, String status){

    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getContact() {
        return contact;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getField_id() {
        return field_id;
    }

    public void setField_id(String field_id) {
        this.field_id = field_id;
    }

    public employee(String id, String name , int contact, String status,String Fid){
        this.employee_id=id;
        this.name=name;
        this.contact=contact;
        this.status=status;
        this.field_id=Fid;
    }


    @Override
    public String toString() {
        return "employee{" +
                "id='" + employee_id + '\'' +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", status='" + status + '\'' +
                ", id='" + id + '\'' +
                ", field_id='" + field_id + '\'' +
                '}';
    }


}
