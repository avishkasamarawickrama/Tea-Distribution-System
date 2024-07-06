package lk.ijse.pos.entity;


public class Customer  {

    private String  customer_id;
    private String name;
    private String email;
    private int contact;

    public Customer(){}


    public Customer(String id, String name, String email, int contact){
        this.customer_id= id;
        this.name=name;
        this.email=email;
        this.contact=contact;
    }
    public String getCustomer_id() {return customer_id;
    }

    public void setCustomer_id(String customer_id) {this.customer_id = customer_id;
    }

    public String getName() {return name;
    }

    public void setName(String customer_name) {this.name = customer_name;
    }

    public String getEmail() {return email;
    }

    public void setEmail(String email) {this.email = email;
    }

    public int getContact() {return contact;
    }

    public void setContact(int contact) {this.contact = contact;
    }


    @Override
    public String toString() {
        return "Customer{" +
                "id='" + customer_id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", contact='" + contact + '\'' +
                '}';
    }



}





