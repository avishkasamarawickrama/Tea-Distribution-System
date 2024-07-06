/*
package lk.ijse.pos.controller;

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
import lk.ijse.Tea.db.Dbconnection;
import lk.ijse.Tea.model.Orders;
import lk.ijse.Tea.model.tm.OrdersTm;
import lk.ijse.Tea.repo.OrdersRepo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ordersFormController {

    @FXML
    private TableColumn<?, ?> colCusId;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colId;


    @FXML
    private TableColumn<?, ?> colCategoryId;


    @FXML
    private TableColumn<?, ?> colPayId;

    @FXML
    private TableColumn<?, ?> colStatus;

    @FXML
    private AnchorPane root;

    @FXML
    private TableView<OrdersTm> tblOrders;

    @FXML
    private TextField txtCategoryID;

    @FXML
    private TextField txtCustomerid;

    @FXML
    private TextField txtDate;

    @FXML
    private TextField txtOrderId;

    @FXML
    private TextField txtPaymentid;

    @FXML
    private TextField txtStatus;

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        int order_id = Integer.parseInt(txtOrderId.getText());
        String status = (txtStatus.getText());
        int payment_id = Integer.parseInt(txtPaymentid.getText());
        int customer_id = Integer.parseInt((txtCustomerid.getText()));
        int category_id = Integer.parseInt((txtCategoryID.getText()));
        String date = (txtDate.getText());


        String sql = "INSERT INTO orders VALUES(?,?,?,?,?,?)";

        try {
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1,order_id);
            pstm.setString(2,status);
            pstm.setInt(3,payment_id);
            pstm.setInt(4,customer_id);
            pstm.setInt(4,category_id);
            pstm.setString(4,date);


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
        loadAllorders();
        System.out.println("Check");
    }
    private void loadAllorders() {
        ObservableList<OrdersTm> obList = FXCollections.observableArrayList();

        try {
            List<Orders> ordersList = OrdersRepo.getAll();
            for (Orders order : ordersList) {
                OrdersTm tm = new OrdersTm(
                        order.getOrder_id(),
                        order.getCustomer_id(),
                        order.getDate()

                );

                obList.add(tm);
            }

            tblOrders.setItems(obList);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void setCellValueFactory() {
        colId.setCellValueFactory(new PropertyValueFactory<>("order_id"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colPayId.setCellValueFactory(new PropertyValueFactory<>("payment_id"));
        colCusId.setCellValueFactory(new PropertyValueFactory<>("customer_id"));
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("date"));


    }


    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        int id = Integer.parseInt(txtOrderId.getText());

        String sql = "DELETE FROM orders WHERE order_id =?";

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
        int order_id = Integer.parseInt(txtOrderId.getText());
        String status = (txtStatus.getText());
        int payment_id = Integer.parseInt(txtPaymentid.getText());
        int customer_id = Integer.parseInt(txtCustomerid.getText());
        int category_id = Integer.parseInt(txtCategoryID.getText());
        String date = (txtDate.getText());


        String sql = "UPDATE orders SET status =?, payment_id =?,customer_id=? ,category_id=?,date=? WHERE order_id =?";


        boolean isUpdate = OrdersRepo.update2(order_id, status ,payment_id,customer_id,category_id, date);
        if (isUpdate) {
            new Alert(Alert.AlertType.INFORMATION, "data Updated Successfully").show();
        }else {
            new Alert(Alert.AlertType.ERROR, "data Not Updated").show();
        }
    }


    @FXML
    void txtOrderOnAction(ActionEvent event) {
        String id = txtOrderId.getText();

        String sql = "SELECT * FROM orders WHERE order_id =?";

        try{
            Connection connection = Dbconnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1, Integer.parseInt(id));

            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {

                String  status = String.valueOf(resultSet.getInt(2));
                int  payment_id = Integer.parseInt(resultSet.getString(3));
                int  customer_id = Integer.parseInt(resultSet.getString(4));
                int  category_id = Integer.parseInt(resultSet.getString(5));
                String  date = resultSet.getString(6);


                txtStatus.setText(String.valueOf(status));
                txtPaymentid.setText(String.valueOf(payment_id));
                txtCustomerid.setText(String.valueOf(customer_id));
                txtCategoryID.setText(String.valueOf(category_id));
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
