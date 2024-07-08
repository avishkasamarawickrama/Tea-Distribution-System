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
import lk.ijse.pos.bo.custom.InventoryBO;
import lk.ijse.pos.dto.fieldsDTO;
import lk.ijse.pos.dto.inventoryDTO;
import lk.ijse.pos.view.tdm.fieldsTm;
import lk.ijse.pos.view.tdm.inventoryTm;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class inventoryFormController {

    @FXML
    private TableColumn<?, ?> colHarvestId;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colQuantity;

    @FXML
    private TableColumn<?, ?> colname;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<inventoryTm> tblInventory;

    @FXML
    private TextField txtCategoryName;

    @FXML
    private TextField txtCategoryid;

    @FXML
    private TextField txtHarvestid;

    @FXML
    private TextField txtQuantity;

    @FXML
    private TextField txtUnitPrice;

    public JFXButton btnDelete;
    public JFXButton btnSave;

    public JFXButton btnAddNewInventory;

    InventoryBO inventoryBO = (InventoryBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.INVENTORY);

    public void initialize() {

        tblInventory.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("category_id"));
        tblInventory.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("category_name"));
        tblInventory.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("qty"));
        tblInventory.getColumns().get(3).setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        tblInventory.getColumns().get(4).setCellValueFactory(new PropertyValueFactory<>("harvest_no"));



        initUI();

        tblInventory.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtCategoryid.setText(newValue.getCategory_id());
                txtCategoryName.setText(newValue.getCategory_name());
                txtQuantity.setText(String.valueOf(newValue.getQtyOnHand()));
                txtUnitPrice.setText(String.valueOf(newValue.getUnitPrice()));
                txtHarvestid.setText(String.valueOf(newValue.getHarvest_no()));


                txtCategoryid.setDisable(false);
                txtCategoryName.setDisable(false);
                txtQuantity.setDisable(false);
                txtUnitPrice.setDisable(false);
                txtHarvestid.setDisable(false);

            }
        });

        txtUnitPrice.setOnAction(event -> btnSave.fire());
        loadAllInventory();
    }

    private void loadAllInventory() {
        tblInventory.getItems().clear();
        try {

            ArrayList<inventoryDTO> allIInventory = inventoryBO.getAllInventory();
            for (inventoryDTO i : allIInventory) {
                tblInventory.getItems().add(new inventoryTm(i.getCategory_id(), i.getCategory_name(), i.getQtyOnHand(),i.getUnitPrice(),i.getHarvest_no()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtCategoryid.clear();
        txtCategoryName.clear();
        txtQuantity.clear();
        txtUnitPrice.clear();
        txtHarvestid.clear();
        txtCategoryid.setDisable(true);
        txtCategoryName.setDisable(true);
        txtQuantity.setDisable(true);
        txtUnitPrice.setDisable(true);
        txtHarvestid.setDisable(true);
        txtCategoryid.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }


    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtCategoryid.setDisable(false);
        txtCategoryName.setDisable(false);
        txtQuantity.setDisable(false);
        txtUnitPrice.setDisable(false);
        txtHarvestid.setDisable(false);
        txtCategoryid.clear();
        txtCategoryid.setText(generateNewId());
        txtCategoryName.clear();
        txtQuantity.clear();
        txtQuantity.requestFocus();
        txtUnitPrice.clear();
        txtUnitPrice.requestFocus();
        txtHarvestid.clear();
        txtHarvestid.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblInventory.getSelectionModel().clearSelection();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        String category_id = tblInventory.getSelectionModel().getSelectedItem().getCategory_id();
        try {
            if (!existInventory(category_id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + category_id).show();
            }


            inventoryBO.deleteInventory(category_id);

            tblInventory.getItems().remove(tblInventory.getSelectionModel().getSelectedItem());
            tblInventory.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the category " + category_id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String category_id = txtCategoryid.getText();
        String category_name = txtCategoryName.getText();
        int qtyOnHand = Integer.parseInt(txtQuantity.getText());
        BigDecimal unitPrice =new BigDecimal(txtUnitPrice.getText());
        String harvest_no = txtHarvestid.getText();

        if (!txtQuantity.getText().matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtCategoryName.requestFocus();
            return;
        } else if (!txtQuantity.getText().matches("^\\d+$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty on hand").show();
            txtQuantity.requestFocus();
            return;
        }else if(!txtUnitPrice.getText().matches("^[0-9]+[.]?[0-9]*$")) {
            new Alert(Alert.AlertType.ERROR, "Invalid unit price").show();
            txtUnitPrice.requestFocus();
            return;
        }

        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existInventory(category_id)) {
                    new Alert(Alert.AlertType.ERROR, category_id + " already exists").show();
                }

                inventoryBO.addInventory(new inventoryDTO(category_id, category_name, qtyOnHand,unitPrice,harvest_no));

                tblInventory.getItems().add(new inventoryTm(category_id, category_name, qtyOnHand,unitPrice,harvest_no));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {

                if (!existInventory(category_id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + category_id).show();
                }

                inventoryBO.updateInventory(new inventoryDTO(category_id, category_name, qtyOnHand,unitPrice,harvest_no));

                inventoryTm selectedInventory = tblInventory.getSelectionModel().getSelectedItem();
                selectedInventory.setCategory_name(category_name);
                selectedInventory.setQtyOnHand(qtyOnHand);
                selectedInventory.setUnitPrice(unitPrice);
                selectedInventory.setHarvest_no(harvest_no);
                tblInventory.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        btnAddNewInventory.fire();
    }


    private boolean existInventory(String category_id) throws SQLException, ClassNotFoundException {
        return inventoryBO.existInventory(category_id);
    }


    private String generateNewId() {
        try {
            //Generate New ID
            return inventoryBO.generateNewInventoryID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblInventory.getItems().isEmpty()) {
            return "I00-001";
        } else {
            String category_id = getLastInventoryId();
            int newInventoryId = Integer.parseInt(category_id.replace("I", "")) + 1;
            return String.format("I00-%03d", newInventoryId);
        }

    }
    private String getLastInventoryId() {
        List<inventoryTm> tempInventoryList = new ArrayList<>(tblInventory.getItems());
        Collections.sort(tempInventoryList);
        return tempInventoryList.get(tempInventoryList.size() - 1).getCategory_id();
    }

}

   /* @FXML
    void btnSaveOnAction(ActionEvent event) {
        int category_id = Integer.parseInt(txtCategoryid.getText());
        String category_name = txtCategoryName.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        int harvest_no = Integer.parseInt((txtHarvestid.getText()));


        String sql = "INSERT INTO inventory VALUES(?,?,?,?)";

        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1,category_id);
            pstm.setString(2,category_name);
            pstm.setInt(3,quantity);
            pstm.setInt(4,harvest_no);


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
        loadAllInventory();
        System.out.println("Check");
    }
    private void loadAllInventory() {
        ObservableList<inventoryTm> obList = FXCollections.observableArrayList();

        try {
            List<Inventory> inventoryList = inventoryRepo.getAll();
            for (Inventory inventory : inventoryList) {
                inventoryTm tm = new inventoryTm(
                        inventory.getCategory_id(),
                        inventory.getCategory_name(),
                        inventory.getQuantity(),
                        inventory.getHarvest_no(),
                        inventory.getUnit_price()

                );

                obList.add(tm);
            }

            tblInventory.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        colname.setCellValueFactory(new PropertyValueFactory<>("category_name"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colHarvestId.setCellValueFactory(new PropertyValueFactory<>("harvest_no"));


    }






    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtCategoryid.getText());

        String sql = "DELETE FROM inventory WHERE category_id =?";

        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1,id);

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
        int category_id = Integer.parseInt(txtCategoryid.getText());
        String category_name = txtCategoryName.getText();
        int quantity = Integer.parseInt(txtQuantity.getText());
        int harvest_no = Integer.parseInt(txtHarvestid.getText());
        String unit_price =(txtUnitPrice.getText());


        String sql = "UPDATE inventory SET category_name =?, quantity =?,harvest_no=?,unit_price=? WHERE category_id =?";


        boolean isUpdate = inventoryRepo.update2(category_id, category_name ,quantity,harvest_no,unit_price);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "data Updated Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "data Not Updated").show();
        }
    }


    @FXML
    void txtCategoryOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtCategoryid.getText());

        String sql = "SELECT * FROM inventory WHERE category_id =?";

        try{
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1, Integer.parseInt(String.valueOf(id)));

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                String  category_name = String.valueOf(resultSet.getInt(2));
                int  quantity = Integer.parseInt(resultSet.getString(3));
                int  harvest_no = Integer.parseInt(resultSet.getString(4));


                txtCategoryName.setText((category_name));
                txtQuantity.setText(String.valueOf(quantity));
                txtHarvestid.setText(String.valueOf(harvest_no));


            } else {
                new Alert(Alert.AlertType.ERROR, "data Not Found").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.INFORMATION,"data ID Not Found!");
        }
    }

*/