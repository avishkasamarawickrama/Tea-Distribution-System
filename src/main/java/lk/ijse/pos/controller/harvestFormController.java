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
import lk.ijse.pos.bo.custom.HarvestBO;
import lk.ijse.pos.dto.HarvestDTO;
import lk.ijse.pos.dto.employeeDTO;
import lk.ijse.pos.view.tdm.HarvestTm;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class harvestFormController {

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colFieldId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<HarvestTm> tblHarvest;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtFieldId;

    @FXML
    private TextField txtHarvestId;

    @FXML
    private TextField txtQuantity;

    public JFXButton btnDelete;
    public JFXButton btnSave;
    public JFXButton btnAddNewHarvest;

    HarvestBO harvestBO  = (HarvestBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.HARVEST);

    public void initialize(){

        tblHarvest.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("harvest_no"));
        tblHarvest.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblHarvest.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("date"));
        tblHarvest.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("field_id"));


        initUI();

        tblHarvest.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtHarvestId.setText(newValue.getHarvest_no());
                txtQuantity.setText(String.valueOf(newValue.getQty()));
                txtDate.setText(String.valueOf(newValue.getDate()));
                txtFieldId.setText(String.valueOf(newValue.getField_id()));


                txtHarvestId.setDisable(false);
                txtQuantity.setDisable(false);
                txtDate.setDisable(false);
                txtFieldId.setDisable(false);

            }
        });

        txtDate.setOnAction(event -> btnSave.fire());
        loadAllHarvest();
    }



    private void loadAllHarvest() {
        tblHarvest.getItems().clear();
        try {
            ArrayList<HarvestDTO> allHarvest = harvestBO.getAllHarvest();

            for (HarvestDTO h : allHarvest) {
                tblHarvest.getItems().add(new HarvestTm(h.getHarvest_no(), h.getQty(), h.getDate(),h.getField_id()));

            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    public void btnAddNewOnAction (ActionEvent actionEvent){
        txtHarvestId.setDisable(false);
        txtQuantity.setDisable(false);
        txtDate.setDisable(false);
        txtFieldId.setDisable(false);
        txtHarvestId.clear();
        txtHarvestId.setText(generateNewId());
        txtQuantity.clear();
        txtDate.clear();
        txtFieldId.clear();
        txtDate.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblHarvest.getSelectionModel().clearSelection();

    }


    @FXML
    void btnDeleteOnAction (ActionEvent event){

        String harvest_no = tblHarvest.getSelectionModel().getSelectedItem().getHarvest_no();
        try {
            if (!existHarvest(harvest_no)) {
                new Alert(Alert.AlertType.ERROR, "There is no such harvest associated with the id " + harvest_no).show();
            }

            harvestBO.deleteHarvest(harvest_no);

            tblHarvest.getItems().remove(tblHarvest.getSelectionModel().getSelectedItem());
            tblHarvest.getSelectionModel().clearSelection();

            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the harvest " + harvest_no).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }

    private void initUI() {
        txtHarvestId.clear();
        txtQuantity.clear();
        txtDate.clear();
        txtFieldId.clear();
        txtHarvestId.setDisable(true);
        txtQuantity.setDisable(true);
        txtDate.setDisable(true);
        txtFieldId.setDisable(true);
        txtHarvestId.setDisable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }

    @FXML
    void btnSaveOnAction (ActionEvent event){
        String harvest_no = txtHarvestId.getText();
        int qty = Integer.parseInt(txtQuantity.getText());
        LocalDate date = LocalDate.parse(txtDate.getText());
        String field_id = txtFieldId.getId();

        if (!txtQuantity.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQuantity.requestFocus();
            return;
        } else if (!txtDate.getText().matches("^\\\\d{4}-\\\\d{2}-\\\\d{2}$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtDate.requestFocus();
            return;

        }


        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existHarvest(harvest_no)) {
                    new Alert(Alert.AlertType.ERROR, harvest_no + " already exists").show();
                }

                harvestBO.addHarvest(new HarvestDTO(harvest_no, qty, date,field_id));

                tblHarvest.getItems().add(new HarvestTm(harvest_no, qty, date,field_id));
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to save the harvest " + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (!existHarvest(harvest_no)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such harvest associated with the no " + harvest_no).show();
                }


                harvestBO.updateHarvest(new HarvestDTO(harvest_no, qty, date));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, "Failed to update the harvest " + harvest_no + e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            HarvestTm selectedHarvest = tblHarvest.getSelectionModel().getSelectedItem();
            selectedHarvest.setQty(qty);
            selectedHarvest.setDate(date);
            selectedHarvest.setField_id(field_id);
            tblHarvest.refresh();
        }

        btnAddNewHarvest.fire();
    }

    boolean existHarvest (String harvest_no) throws SQLException, ClassNotFoundException {
        return harvestBO.existHarvest(harvest_no);
    }

    private String generateNewId () {
        try {
            //Generate New ID
            return harvestBO.generateNewHarvestID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (tblHarvest.getItems().isEmpty()) {
            return "H00-001";
        } else {
            String harvest_no = getLastHarvestId();
            int newHarvestId = Integer.parseInt(harvest_no.replace("H", "")) + 1;
            return String.format("H00-%03d", newHarvestId);
        }

    }
    private String getLastHarvestId() {
        List<HarvestTm> tempHarvestList = new ArrayList<>(tblHarvest.getItems());
        Collections.sort(tempHarvestList);
        return tempHarvestList.get(tempHarvestList.size() - 1).getHarvest_no();
    }
}
  /*  @FXML
    void btnSaveOnAction(ActionEvent event) {
        int harvest_no = Integer.parseInt(txtHarvestId.getText());
        int field_id = Integer.parseInt(txtFieldId.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        String date = (txtDate.getText());


        String sql = "INSERT INTO harvest VALUES(?,?,?,?)";

        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1,harvest_no);
            pstm.setInt(2,field_id);
            pstm.setInt(3,quantity);
            pstm.setString(4,date);


            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "harvest Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    public void initialize() {

        setCellValueFactory();
        loadAllFields();
        System.out.println("Check");
    }
    private void loadAllFields() {
        ObservableList<HarvestTm> obList = FXCollections.observableArrayList();

        try {
            List<Harvest> harvestList = HarvestRepo.getAll();
            for (Harvest harvest : harvestList) {
                HarvestTm tm = new HarvestTm(
                        harvest.getHarvest_no(),
                        harvest.getField_id(),
                        harvest.getQuantity(),
                        harvest.getDate()

                );

                obList.add(tm);
            }

            tblHarvest.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("harvest_no"));
        colFieldId.setCellValueFactory(new PropertyValueFactory<>("field_id"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));


    }






    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtHarvestId.getText());

        String sql = "DELETE FROM harvest WHERE harvest_id =?";

        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1,id);

            boolean isDeleted = pstm.executeUpdate() > 0;
            if (isDeleted) {
                new Alert(Alert.AlertType.INFORMATION, "harvest Deleted Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



    @FXML
    void btnUpdateOnAction(ActionEvent event) throws SQLException {
        int harvest_no = Integer.parseInt(txtHarvestId.getText());
        int field_id = Integer.parseInt(txtFieldId.getText());
        int quantity = Integer.parseInt(txtQuantity.getText());
        String date = txtDate.getText();


        String sql = "UPDATE harvest SET field_id =?, quantity =?,date=? WHERE harvest_no =?";


        boolean isUpdate = HarvestRepo.update2(harvest_no, field_id ,quantity,date);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "data Updated Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "data Not Updated").show();
        }
    }


    @FXML
    void txtHarvestOnAction(ActionEvent event) {
        String id = txtHarvestId.getText();

        String sql = "SELECT * FROM harvest WHERE harvest_no =?";

        try{
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1, Integer.parseInt(id));

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                int field_id = resultSet.getInt(2);
                int  quantity = resultSet.getInt(3);
                String  date = resultSet.getString(4);


                txtFieldId.setText(String.valueOf(field_id));
                txtQuantity.setText(String.valueOf(quantity));
                txtDate.setText(date);


            } else {
                new Alert(Alert.AlertType.ERROR, "data Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"data ID Not Found!");
        }
    }

}


*/