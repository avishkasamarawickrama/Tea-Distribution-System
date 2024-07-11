package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.pos.bo.BOFactory;
import lk.ijse.pos.bo.custom.FieldBO;
import lk.ijse.pos.dto.fieldsDTO;
import lk.ijse.pos.view.tdm.employeeTm;
import lk.ijse.pos.view.tdm.fieldsTm;


import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class fieldsFormController {

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<?, ?> colId;

    @FXML
    private TableColumn<?, ?> colName;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<fieldsTm> tblFields;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtFieldsId;

    @FXML
    private TextField txtName;
    public JFXButton btnDelete;
    public JFXButton btnSave;

    public JFXButton btnAddNewField;
    FieldBO fieldBO = (FieldBO) BOFactory.getBoFactory().getBO(BOFactory.BOTypes.FIELD);

    public void initialize() {

        tblFields.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("field_id"));
        tblFields.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("name"));
        tblFields.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("address"));


        initUI();

        tblFields.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnSave.setText(newValue != null ? "Update" : "Save");
            btnSave.setDisable(newValue == null);

            if (newValue != null) {
                txtFieldsId.setText(newValue.getField_id());
                txtName.setText(newValue.getName());
                txtAddress.setText(newValue.getAddress());


                txtFieldsId.setDisable(false);
                txtName.setDisable(false);
                txtAddress.setDisable(false);

            }
        });

        txtAddress.setOnAction(event -> btnSave.fire());
        loadAllFields();
    }

    private void loadAllFields() {
        tblFields.getItems().clear();
        try {

            ArrayList<fieldsDTO> allIFields = fieldBO.getAllFields();
            for (fieldsDTO f : allIFields) {
                tblFields.getItems().add(new fieldsTm(f.getField_id(), f.getName(), f.getAddress()));
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void initUI() {
        txtFieldsId.clear();
        txtName.clear();
        txtAddress.clear();
        txtFieldsId.setDisable(true);
        txtName.setDisable(true);
        txtAddress.setDisable(true);
        txtFieldsId.setEditable(false);
        btnSave.setDisable(true);
        btnDelete.setDisable(true);
    }


    public void btnAddNew_OnAction(ActionEvent actionEvent) {
        txtFieldsId.setDisable(false);
        txtName.setDisable(false);
        txtAddress.setDisable(false);
        txtFieldsId.clear();
        txtFieldsId.setText(generateNewId());
        txtName.clear();
        txtAddress.clear();
        txtAddress.requestFocus();
        btnSave.setDisable(false);
        btnSave.setText("Save");
        tblFields.getSelectionModel().clearSelection();
    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {

        String field_id = tblFields.getSelectionModel().getSelectedItem().getField_id();
        try {
            if (!existField(field_id)) {
                new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + field_id).show();
            }


            fieldBO.deleteField(field_id);

            tblFields.getItems().remove(tblFields.getSelectionModel().getSelectedItem());
            tblFields.getSelectionModel().clearSelection();
            initUI();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to delete the item " + field_id).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        String field_id = txtFieldsId.getText();
        String name = txtName.getText();
        String address = txtAddress.getText();

        if (!name.matches("[A-Za-z ]+")) {
            new Alert(Alert.AlertType.ERROR, "Invalid name").show();
            txtName.requestFocus();
            return;
        } else if (!address.matches(".{3,}")) {
            new Alert(Alert.AlertType.ERROR, "Address should be at least 3 characters long").show();
            txtAddress.requestFocus();
            return;
        }


        if (btnSave.getText().equalsIgnoreCase("save")) {
            try {
                if (existField(field_id)) {
                    new Alert(Alert.AlertType.ERROR, field_id + " already exists").show();
                }

                fieldBO.addField(new fieldsDTO(field_id, name, address));

                tblFields.getItems().add(new fieldsTm(field_id, name, address));

            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            try {

                if (!existField(field_id)) {
                    new Alert(Alert.AlertType.ERROR, "There is no such item associated with the id " + field_id).show();
                }

                fieldBO.updateField(new fieldsDTO(field_id, name, address));

                fieldsTm selectedField = tblFields.getSelectionModel().getSelectedItem();
                selectedField.setName(name);
                selectedField.setName(address);
                tblFields.refresh();
            } catch (SQLException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        btnAddNewField.fire();
    }


    private boolean existField(String field_id) throws SQLException, ClassNotFoundException {
        return fieldBO.existField(field_id);
    }


    private String generateNewId() {
        try {
            //Generate New ID
            return fieldBO.generateNewFieldID();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new id " + e.getMessage()).show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


        if (tblFields.getItems().isEmpty()) {
            return "F00-001";
        } else {
            String field_id = getLastFieldId();
            int newFieldId = Integer.parseInt(field_id.replace("F", "")) + 1;
            return String.format("F00-%03d", newFieldId);
        }

    }
    private String getLastFieldId() {
        List<fieldsTm> tempFieldList = new ArrayList<>(tblFields.getItems());
        Collections.sort(tempFieldList);
        return tempFieldList.get(tempFieldList.size() - 1).getField_id();
    }

    public void txtFieldsOnAction(ActionEvent actionEvent) {
    }
}



