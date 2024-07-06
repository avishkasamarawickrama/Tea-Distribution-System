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
import lk.ijse.pos.bo.custom.SalaryBO;
import lk.ijse.pos.dto.employeeDTO;
import lk.ijse.pos.dto.salaryDTO;
import lk.ijse.pos.view.tdm.employeeTm;
import lk.ijse.pos.view.tdm.salaryTm;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class salaryFormController {

    @FXML
    private TableColumn<?, ?> colAmount;

    @FXML
    private TableColumn<?, ?> colEmployeeId;

    @FXML
    private TableColumn<?, ?> colNo;

    @FXML
    private TableColumn<?, ?> colPaymentDate;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<salaryTm> tblSalary;

    @FXML
    private TextField txtAmount;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtEmployeeId;

    @FXML
    private TextField txtSalaryno;

    public JFXButton btnDelete;
    public JFXButton btnSave;

    public JFXButton btnAddNewSalary;


    SalaryBO salaryBO=(SalaryBO) BOFactory().getBoFactory().getBOFactory.BOTypes.SALARY;

    public void initialize(){

        tblSalary.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("no"));
        tblSalary.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("amount"));
        tblSalary.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("date"));


        initUI();

        tblSalary.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtSalaryno.setText(newValue.getEmployee_id());
                txtAmount.setText(String.valueOf(newValue.getAmount()));
                txtDate.setText(String.valueOf(newValue.getDate()));


                txtSalaryno.setDisable(false);
                txtAmount.setDisable(false);
                txtDate.setDisable(false);
            }
        });

        txtDate.setOnAction(event -> btnSave.fire());
        loadAllSalary();
    }



    private void loadAllSalary() {
        tblSalary.getItems().clear();
        try {
            ArrayList<salaryDTO> allSalary = salaryBO.getAllSalary();

            for (salaryDTO s : allSalary) {
                tblSalary.getItems().add(new salaryTm(s.getNo(), s.getAmount(), s.getDate()));

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    public void btnAddNewOnAction (ActionEvent actionEvent){
        txtSalaryno.setDisable(false);
        txtAmount.setDisable(false);
        txtDate.setDisable(false);
        txtSalaryno.clear();
        txtSalaryno.setText(generateNewId());
        txtAmount.clear();
        txtDate.clear();
        txtAmount.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblSalary.getSelectionModel().clearSelection();

    }


    @FXML
    void btnDeleteOnAction (ActionEvent event){

        String no = tblSalary.getSelectionModel().getSelectedItem().getNo();
        try {
            if (!existSalary(no)) {
                new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + no).show();
            }

            salaryBO.deleteSalary(no);

            tblSalary.getItems().remove(tblSalary.getSelectionModel().getSelectedItem());
            tblSalary.getSelectionModel().clearSelection();

            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + no).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void initUI() {
        txtSalaryno.clear();
        txtAmount.clear();
        txtDate.clear();
        txtSalaryno.setDisable(true);
        txtAmount.setDisable(true);
        txtDate.setDisable(true);
        txtSalaryno.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnSaveOnAction (ActionEvent event){
        String no = txtEmployeeId.getText();
        BigDecimal amount = new BigDecimal(txtAmount.getText());
        LocalDate date = LocalDate.ofEpochDay(Integer.parseInt(txtDate.getText()));


        if (!txtAmount.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtAmount.requestFocus();
            return;
        }else if (!txtDate.getText().matches("^\\\\d{4}-\\\\d{2}-\\\\d{2}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtDate.requestFocus();
            return;

        }


        if (btnSave.getText().equalsIgnoreCase("save")) {
            /*Save Customer*/
            try {
                if (existSalary(no)) {
                    new Alert(Alert.AlertType.ERROR, no + " already exists").show();
                }

                //Add Customer
                salaryBO.addSalary(new salaryDTO(no, amount, date));

                tblSalary.getItems().add(new salaryTm(no, amount, date));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the salary " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

        } else {
            /*Update employee*/
            try {
                if (!existSalary(no)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such employee associated with the id " + no).show();
                }

                //Update employee
                salaryBO.updateSalary(new salaryDTO(no, amount, date));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the customer " + no + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            salaryTm selectedSalary = tblSalary.getSelectionModel().getSelectedItem();
            selectedSalary.setAmount(amount);
            selectedSalary.setDate(date);
            tblSalary.refresh();
        }

        btnAddNewSalary.fire();
    }

    boolean existSalary (String no) throws SQLException, ClassNotFoundException {
        return salaryBO.existSalary(no);
    }

    private String generateNewId () {
        try {
            //Generate New ID
            return salaryBO.generateNewSalaryID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (tblSalary.getItems().isEmpty()) {
            return "S00-001";
        } else {
            String no = getLastSalaryId();
            int newNo = Integer.parseInt(no.replace("S", "")) + 1;
            return String.format("S00-%03d", newNo);
        }

    }
    private String getLastSalaryId() {
        List<salaryTm> tempSalaryList = new ArrayList<>(tblSalary.getItems());
        Collections.sort(tempSalaryList);
        return tempSalaryList.get(tempSalaryList.size() - 1).getNo();
    }
}
/*

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        int salary_no = Integer.parseInt(txtSalaryno.getText());
        int employee_id = Integer.parseInt(txtEmployeeId.getText());
        String date = txtDate.getText();
        String amount = txtAmount.getText();

        String sql = "INSERT INTO salary VALUES(?,?,?,?)";

        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1,(salary_no));
            pstm.setInt(2,employee_id);
            pstm.setString(3,date);
            pstm.setString(4,amount);

            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "data Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    public void initialize() {

        setCellValueFactory();
        loadAllCustomers();
        System.out.println("Check");
    }
    private void loadAllCustomers() {
        ObservableList<salaryTm> obList = FXCollections.observableArrayList();

        try {
            List<salary> salaryList = salaryRepo.getAll();
            for (salary salary : salaryList) {
                salaryTm tm = new salaryTm(
                        salary.getSalary_no(),
                        salary.getEmployee_id(),
                        salary.getDate(),
                        salary.getAmount()
                );

                obList.add(tm);
            }

            tblSalary.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colNo.setCellValueFactory(new PropertyValueFactory<>("salary_no"));
        colEmployeeId.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        colPaymentDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));

    }






    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int salary_no = Integer.parseInt(txtSalaryno.getText());

        String sql = "DELETE FROM salary WHERE salary_no =?";

        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1,salary_no);

            boolean isDeleted = pstm.executeUpdate() > 0;
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "data Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        int salary_no = Integer.parseInt(txtSalaryno.getText());
        int employee_id = Integer.parseInt(txtEmployeeId.getText());
        String date = (txtDate.getText());
        String amount = txtAmount.getText();

        String sql = "UPDATE salary SET employee_id =?, date =?, amount =? WHERE salary_no =?";

        boolean isUpdate = salaryRepo.update2(salary_no, employee_id,date, amount);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "data Updated Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "data Not Updated").show();
        }
    }


    @FXML
    void txtSalaryOnAction(ActionEvent event) {
        int salary_no = Integer.parseInt(txtSalaryno.getText());

        String sql = "SELECT * FROM salary WHERE salary_no =?";

        try{
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1, Integer.parseInt(String.valueOf(salary_no)));

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                int employee_id = resultSet.getInt(2);
                String date = (resultSet.getString(3));
                String amount = resultSet.getString(4);


                txtEmployeeId.setText(String.valueOf(employee_id));
                txtDate.setText(String.valueOf(date));
                txtAmount.setText(amount);

            } else {
                new Alert(Alert.AlertType.ERROR, "data Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"data ID Not Found!");
        }
    }


}
*/
