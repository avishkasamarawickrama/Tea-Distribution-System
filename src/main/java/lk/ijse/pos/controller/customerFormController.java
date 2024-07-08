package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.CustomerBO;
import lk.ijse.pos.dto.CustomerDTO;
import lk.ijse.pos.view.tdm.CustomerTm;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class customerFormController {

    @FXML
    public TableColumn<?, ?> colContact;
    @FXML
    public TableColumn<?, ?> colEmail;
    @FXML
    public TableColumn<?, ?> colId;
    @FXML
    public TableColumn<?, ?> colName;
    @FXML
    public AnchorPane root;
    @FXML
    public TableView<CustomerTm> tblCustomer;
    @FXML
    public TextField txtContactNo;
    @FXML
    public TextField txtCustomerId;
    @FXML
    public TextField txtEmail;
    @FXML
    public TextField txtName;
    public JFXButton btnDelete;
    public JFXButton btnSave;
    public JFXButton btnAddNewCustomer;

    CustomerBO customerBO  = (CustomerBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.CUSTOMER);

    public void initialize() {

        tblCustomer.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        tblCustomer.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblCustomer.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("email"));
        tblCustomer.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("contact"));

        initUI();

        tblCustomer.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCustomerId.setText(newValue.getCustomer_id());
                txtName.setText(newValue.getName());
                txtEmail.setText(newValue.getEmail());
                txtContactNo.setText(newValue.getContact() + "");

                txtCustomerId.setDisable(false);
                txtName.setDisable(false);
                txtEmail.setDisable(false);
               txtContactNo .setDisable(false);
            }
        });

        txtEmail.setOnAction(event -> btnSave.fire());
        loadAllCustomers();

    }

    private void loadAllCustomers() {
        tblCustomer.getItems().clear();
        try{
            ArrayList<CustomerDTO>allCustomers= customerBO.getAllCustomers();

            for (CustomerDTO c: allCustomers){
                tblCustomer.getItems().add(new CustomerTm(c.getCustomer_id(),c.getName(),c.getEmail(),c.getContact()));

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }

    private void initUI(){
        txtCustomerId.clear();
        txtName.clear();
        txtEmail.clear();
        txtContactNo.clear();
        txtCustomerId.setDisable(true);
        txtName.setDisable(true);
        txtEmail.setDisable(true);
        txtContactNo.setDisable(true);
        txtCustomerId.setDisable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }



     public void btnAddNewOnAction (ActionEvent actionEvent){
         txtCustomerId.setDisable(false);
         txtName.setDisable(false);
         txtEmail.setDisable(false);
         txtContactNo.setDisable(false);
         txtCustomerId.clear();
         txtCustomerId.setText(generateNewId());
         txtName.clear();
         txtEmail.clear();
         txtContactNo.clear();
         txtEmail.requestFocus();
         btnSave.setDisable(false);
         btnSave.setText("Save");
         tblCustomer.getSelectionModel().clearSelection();

     }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {

        String customer_id = tblCustomer.getSelectionModel().getSelectedItem().getCustomer_id();
        try{
            if (!existCustomer(customer_id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + customer_id).show();
            }

            customerBO.deleteCustomer(customer_id);

            tblCustomer.getItems().remove(tblCustomer.getSelectionModel().getSelectedItem());
            tblCustomer.getSelectionModel().clearSelection();

            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + customer_id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        String customer_id = txtCustomerId.getText();
        String name = txtName.getText();
        String email = txtEmail.getText();
        int contact = Integer.parseInt(txtContactNo.getText());

        if (!txtName.getText().matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtName.requestFocus();
            return;
        } else if (!txtEmail.getText().matches("[A-Za-z0-9 ]")) {
            new Alert(Alert.AlertType.ERROR, "email invalid").show();
            txtEmail.requestFocus();
            return;
        }else if (!txtContactNo.getText().matches("^\\d{10}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtContactNo.requestFocus();
            return;
        }


        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Customer*/
            try {
                if (existCustomer(customer_id)) {
                    new Alert(Alert.AlertType.ERROR, customer_id + " already exists").show();
                }

                //Add Customer
                customerBO.addCustomer(new CustomerDTO(customer_id,name,email,contact));

                tblCustomer.getItems().add(new CustomerTm(customer_id, name, email,contact));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the customer " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            /*Update customer*/
            try {
                if (!existCustomer(customer_id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + customer_id).show();
                }

                //Update Customer
                customerBO.updateCustomer(new CustomerDTO(customer_id,name,email,contact));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + customer_id + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            CustomerTm selectedCustomer = tblCustomer.getSelectionModel().getSelectedItem();
            selectedCustomer.setName(name);
            selectedCustomer.setEmail(email);
            selectedCustomer.setContact(contact);
            tblCustomer.refresh();
        }

        btnAddNewCustomer.fire();
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return customerBO.existCustomer(id);
    }

    private String generateNewId() {
        try {
            //Generate New ID
            return customerBO.generateNewCustomerID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblCustomer.getItems().isEmpty()) {
            return "C00-001";
        } else {
            String id = getLastCustomerId();
            int newCustomerId = Integer.parseInt(id.replace("C", "")) + 1;
            return String.format("C00-%03d", newCustomerId);
        }

    }
    private String getLastCustomerId() {
        List<CustomerTm> tempCustomersList = new ArrayList<>(tblCustomer.getItems());
        Collections.sort(tempCustomersList);
        return tempCustomersList.get(tempCustomersList.size() - 1).getCustomer_id();
    }

    public void txtCustomerOnAction(ActionEvent actionEvent) {

    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {

    }
}

  /*  @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        int customer_id = Integer.parseInt(txtCustomerId.getText());
        String customer_name = txtName.getText();
        int contact = Integer.parseInt(txtContactNo.getText());
        String email = txtEmail.getText();

        String sql = "UPDATE customer SET name =?, email =?, contact =? WHERE Customer_id =?";

        boolean isUpdate = CustomerRepo.update2(customer_id, customer_name,contact, email);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Customer Updated Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Customer Not Updated").show();
        }
    }*/

/*

    @FXML
    void txtCustomerOnAction(ActionEvent event) {
        String id = txtCustomerId.getText();

        String sql = "SELECT * FROM customer WHERE customerId =?";

        try{
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1, Integer.parseInt(id));

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String name = resultSet.getString(2);
                int contact = Integer.parseInt(String.valueOf(resultSet.getInt(3)));
                String email = resultSet.getString(4);


                txtName.setText(name);
                txtContactNo.setText(String.valueOf(contact));
                txtEmail.setText(email);

            } else {
                new Alert(Alert.AlertType.ERROR, "Customer Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"Customer ID Not Found!");
        }
    }
*/


