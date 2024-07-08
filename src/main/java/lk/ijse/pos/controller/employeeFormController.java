package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import lk.ijse.pos.bo.custom.EmployeeBO;
import lk.ijse.pos.dto.employeeDTO;
import lk.ijse.pos.view.tdm.employeeTm;
import lk.ijse.pos.view.tdm.fieldsTm;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class employeeFormController {

    @FXML
    private TableColumn<?, ?> colContact;

    @FXML
    private TableColumn<?, ?> colFieldId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<employeeTm> tblEmployee;
    @FXML
    private TableView<employeeTm> tblField;
    @FXML
    private TableView<employeeTm> tblUser;

    @FXML
    private TextField txtContactNo;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtFieldId;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtStatus;

    public JFXButton btnDelete;
    public JFXButton btnSave;
    public JFXButton btnAddNewEmployee;

    EmployeeBO employeeBO = (EmployeeBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.EMPLOYEE);

    public void initialize(){

        tblEmployee.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        tblEmployee.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblEmployee.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("contact"));
        tblEmployee.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("status"));
        tblEmployee.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("field_id"));

        initUI();

        tblEmployee.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtEmployeeId.setText(newValue.getEmployee_id());
                txtName.setText(newValue.getName());
                txtContactNo.setText(String.valueOf(newValue.getContact()));
                txtStatus.setText(newValue.getStatus() + "");
                txtFieldId.setText(newValue.getField_id() + "");

                txtEmployeeId.setDisable(false);
                txtName.setDisable(false);
                txtContactNo.setDisable(false);
                txtStatus .setDisable(false);
                txtFieldId .setDisable(false);
            }
        });

        txtContactNo.setOnAction(event -> btnSave.fire());
        loadAllEmployee();
        }



    private void loadAllEmployee() {
        tblEmployee.getItems().clear();
        try {
            ArrayList<employeeDTO> allEmployee = employeeBO.getAllEmployee();

            for (employeeDTO c : allEmployee) {
                tblEmployee.getItems().add(new employeeTm(c.getEmployee_id(), c.getName(), c.getContact(), c.getStatus(),c.getField_id()));

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



        public void btnAddNewOnAction (ActionEvent actionEvent){
            txtEmployeeId.setDisable(false);
            txtName.setDisable(false);
            txtContactNo.setDisable(false);
            txtStatus.setDisable(false);
            txtFieldId.setDisable(false);
            txtEmployeeId.clear();
            txtEmployeeId.setText(generateNewId());
            txtName.clear();
            txtContactNo.clear();
            txtStatus.clear();
            txtFieldId.clear();
            txtContactNo.requestFocus();
            btnSave.setDisable(false);
            btnSave.setText("Save");
            tblEmployee.getSelectionModel().clearSelection();

        }


        @FXML
        void btnDeleteOnAction (ActionEvent event){

            String employee_id = tblEmployee.getSelectionModel().getSelectedItem().getEmployee_id();
            try {
                if (!existEmployee(employee_id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + employee_id).show();
                }

                employeeBO.deleteEmployee(employee_id);

                tblEmployee.getItems().remove(tblEmployee.getSelectionModel().getSelectedItem());
                tblEmployee.getSelectionModel().clearSelection();

                initUI();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + employee_id).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }


        }

    private void initUI() {
        txtEmployeeId.clear();
        txtName.clear();
        txtContactNo.clear();
        txtStatus.clear();
        txtFieldId.clear();
        txtEmployeeId.setDisable(true);
        txtName.setDisable(true);
        txtContactNo.setDisable(true);
        txtStatus.setDisable(true);
        txtFieldId.setDisable(true);
        txtEmployeeId.setDisable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
        void btnSaveOnAction (ActionEvent event){
            String employee_id = txtEmployeeId.getText();
            String name = txtName.getText();
            int contact = Integer.parseInt(txtContactNo.getText());
            String status = txtStatus.getText();
            String field_id = txtFieldId.getText();

            if (!txtName.getText().matches("[A-Za-z ]+")) {
                new Alert(Alert.AlertType.ERROR, "Invalid name").show();
                txtName.requestFocus();
                return;
            } else if (!txtContactNo.getText().matches("^\\d{10}$")) {
                new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
                txtContactNo.requestFocus();
                return;
            } else if (!txtStatus.getText().matches("[A-Za-z ]+")) {
                new Alert(Alert.AlertType.ERROR, "Invalid status").show();
                txtStatus.requestFocus();
                return;
            }


            if (btnSave.getText().equalsIgnoreCase("save")) {
                /*Save Customer*/
                try {
                    if (existEmployee(employee_id)) {
                        new Alert(Alert.AlertType.ERROR, employee_id + " already exists").show();
                    }

                    //Add Customer
                    employeeBO.addEmployee(new employeeDTO(employee_id, name, contact, status,field_id));

                    tblEmployee.getItems().add(new employeeTm(employee_id, name, contact, status,field_id));
                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to save the employee " + e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            } else {
                /*Update employee*/
                try {
                    if (!existEmployee(employee_id)) {
                        new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + employee_id).show();
                    }

                    //Update employee
                    employeeBO.updateEmployee(new employeeDTO(employee_id, name, contact, status,field_id));

                } catch (SQLException e) {
                    new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + employee_id + e.getMessage()).show();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                employeeTm selectedEmployee = tblEmployee.getSelectionModel().getSelectedItem();
                selectedEmployee.setName(name);
                selectedEmployee.setContact(contact);
                selectedEmployee.setStatus(status);
                selectedEmployee.setField_id(field_id);
                tblEmployee.refresh();
            }

            btnAddNewEmployee.fire();
        }

        boolean existEmployee (String employee_id) throws SQLException, ClassNotFoundException {
            return employeeBO.existEmployee(employee_id);
        }

        private String generateNewId () {
            try {
                //Generate New ID
                return employeeBO.generateNewEmployeeID();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if (tblEmployee.getItems().isEmpty()) {
                return "E00-001";
            } else {
                String employee_id = getLastEmployeeId();
                int newEmployeeId = Integer.parseInt(employee_id.replace("E", "")) + 1;
                return String.format("E00-%03d", newEmployeeId);
            }

        }
    private String getLastEmployeeId() {
        List<employeeTm> tempEmployeeList = new ArrayList<>(tblEmployee.getItems());
        Collections.sort(tempEmployeeList);
        return tempEmployeeList.get(tempEmployeeList.size() - 1).getEmployee_id();
    }
    }
   /* public void initialize() {

        setCellValueFactory();
        loadAllCustomers();
        System.out.println("Check");
    }
    private void loadAllCustomers() {
        ObservableList<employeeTm> obList = FXCollections.observableArrayList();

        try {
            List<Employee> employeeList = employeeRepo.getAll();
            for (Employee employee : employeeList) {
                employeeTm tm = new employeeTm(
                        employee.getEmployee_id(),
                        employee.getEmployee_name(),
                        employee.getContact(),
                        employee.getStatus(),
                        employee.getField_id()
                );

                obList.add(tm);
            }

            tblEmployee.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        colName.setCellValueFactory(new PropertyValueFactory<>("employee_name"));
        colContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colFieldId.setCellValueFactory(new PropertyValueFactory<>("field_no"));

    }



    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtEmployeeId.getText());

        String sql = "DELETE FROM employee WHERE employee_id =?";

        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1,id);

            boolean isDeleted = pstm.executeUpdate() > 0;
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "Employee Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        int employee_id = Integer.parseInt(txtEmployeeId.getText());
        String employee_name = txtName.getText();
        int contact = Integer.parseInt(txtContactNo.getText());
        String status = txtStatus.getText();
        int field_id = Integer.parseInt(txtFieldId.getText());

        String sql = "UPDATE employee SET employee_name =?, contact =?, status =?,field_id=? WHERE employee_id =?";

        boolean isUpdate = employeeRepo.update2(employee_id, employee_name,contact, status,field_id);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "Employee Updated Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "Employee Not Updated").show();
        }
    }


    @FXML
    void txtEmployeeOnAction(ActionEvent event) {
        int employee_id = Integer.parseInt(txtEmployeeId.getText());

        String sql = "SELECT * FROM employeee WHERE employee_id =?";

        try{
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1, Integer.parseInt(String.valueOf(employee_id)));

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String employee_name = resultSet.getString(2);
                int contact = Integer.parseInt(String.valueOf(resultSet.getInt(3)));
                String status = resultSet.getString(4);
                int field_id = resultSet.getInt(5);


                txtName.setText(employee_name);
                txtContactNo.setText(String.valueOf(contact));
                txtStatus.setText(status);
                txtFieldId.setText(String.valueOf(field_id));

            } else {
                new Alert(Alert.AlertType.ERROR, "employee Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"employee ID Not Found!");
        }
    }
    }*/