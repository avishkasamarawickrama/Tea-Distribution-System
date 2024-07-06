/*
package lk.ijse.pos.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.ijse.Tea.model.*;
import lk.ijse.Tea.model.tm.CartTm;
import lk.ijse.Tea.repo.CustomerRepo;
import lk.ijse.Tea.repo.OrdersRepo;
import lk.ijse.Tea.repo.PlaceOrderRepo;
import lk.ijse.Tea.repo.inventoryRepo;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class orderDetailsFormController {

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private JFXComboBox<String> cmbCustomerId;

    @FXML
    private JFXComboBox<String> cmbCategoryCode;


    @FXML
    private TableColumn<?, ?> colAction;

    @FXML
    private TableColumn<?, ?> colCategoryId;


    @FXML
    private TableColumn<?, ?> colQty;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private TableColumn<?, ?> colUnitPrice;

    @FXML
    private Label lblCustomerName;

    @FXML
    private Label lblCategoryName;


    @FXML
    private Label lblNetTotal;

    @FXML
    private Label lblOrderDate;

    @FXML
    private Label lblOrderId;

    @FXML
    private Label lblQtyOnHand;

    @FXML
    private Label lblUnitPrice;

    @FXML
    private AnchorPane pane;

    @FXML
    private TableView<CartTm> tblOrderCart;

    @FXML
    private TextField txtQty;
    private ObservableList<CartTm> cartList;
    private double netTotal;

    public void initialize(){
        setCellValueFactory();
        loadNextOrderId();
        getCustomerIds();
        getCategoryNo();
        setDate();
    }

    private void setCellValueFactory() {
        colCategoryId.setCellValueFactory(new PropertyValueFactory<>("category_id"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unit_price"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        colAction.setCellValueFactory(new PropertyValueFactory<>("btnRemove"));
    }

    private void loadNextOrderId() {
        String currentId = OrdersRepo.currentId();
        String nextId = nextId(currentId);

        lblOrderId.setText(nextId);
    }

    private String nextId(String currentId) {
        if (currentId != null) {
            String[] split = currentId.split("O");
         //   System.out.println("Arrays.toString(split) = " + Arrays.toString(split));
            int id = Integer.parseInt(split[1]);
            return "O" + ++id;

        }
        return "O1";
    }


    private void setDate() {
        LocalDate now = LocalDate.now();
        lblOrderDate.setText(String.valueOf(now));
    }
    @FXML
    void btnAddToCartOnAction(ActionEvent event) {
        int category_id = Integer.parseInt(cmbCategoryCode.getValue());
        String category_name = lblCategoryName.getText();
        Double unit_price = Double.valueOf((lblUnitPrice.getText()));
        int  quantity = Integer.parseInt((txtQty.getText()));
        double total = (unit_price * quantity);
        JFXButton btnRemove = new JFXButton("remove");
        btnRemove.setCursor(Cursor.HAND);

        btnRemove.setOnAction((e) -> {
            ButtonType yes = new ButtonType("yes", ButtonBar.ButtonData.OK_DONE);
            ButtonType no = new ButtonType("no", ButtonBar.ButtonData.CANCEL_CLOSE);

            Optional<ButtonType> type = new Alert(Alert.AlertType.INFORMATION, "Are you sure to remove?", yes, no).showAndWait();

            if(type.orElse(no) == yes) {
                int selectedIndex = tblOrderCart.getSelectionModel().getSelectedIndex();
                cartList.remove(selectedIndex);

                tblOrderCart.refresh();
                calculateNetTotal();
            }
        });

        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            if (Objects.equals(category_id, colCategoryId.getCellData(i))) {
                quantity += cartList.get(i).getQuantity();
                total = unit_price * quantity;

                cartList.get(i).setQuantity(quantity);
                cartList.get(i).setTotal(total);

                tblOrderCart.refresh();
                calculateNetTotal();
                txtQty.setText("");
                return;
            }
        }

        CartTm cartTm = new CartTm(category_id, category_name, quantity, unit_price, total, btnRemove);

        cartList.add(cartTm);

        tblOrderCart.setItems(cartList);
        txtQty.setText("");
        calculateNetTotal();
    }

    private void calculateNetTotal() {
        netTotal = 0;
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            netTotal += (double) colTotal.getCellData(i);
        }
        lblNetTotal.setText(String.valueOf(netTotal));
    }
    @FXML
    void btnBackOnAction(ActionEvent event) {
        AnchorPane anchorPane = null;
        try {
            anchorPane = FXMLLoader.load(getClass().getResource("/view/dashboard.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Stage stage = (Stage) pane.getScene().getWindow();

        stage.setScene(new Scene(anchorPane));
        stage.setTitle("Dashboard Form");
        stage.centerOnScreen();
    }



    @FXML
    void btnPlaceOrderOnAction(ActionEvent event) {

        int order_id = Integer.parseInt(lblOrderId.getText());
        int customer_id = Integer.parseInt(cmbCustomerId.getValue());
        String  date = String.valueOf(LocalDate.now());

        var order = new Orders(order_id, customer_id, date);

        List<OrderDetail> odList = new ArrayList<>();
        for (int i = 0; i < tblOrderCart.getItems().size(); i++) {
            CartTm tm = (CartTm) cartList.get(i);

            OrderDetail od = new OrderDetail(
                    order_id,
                    (int) tm.getQuantity(),
                    (int) tm.getUnit_price(),
                    tm.getCategory_id()
            );
            odList.add(od);
        }

        PlaceOrder po = new PlaceOrder(order, odList);
        try {
            boolean isPlaced = PlaceOrderRepo.placeOrder(po);
            if(isPlaced) {
                new Alert(Alert.AlertType.CONFIRMATION, "order placed!").show();
            } else {
                new Alert(Alert.AlertType.WARNING, "order not placed!").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }


    @FXML
    void cmbCustomerOnAction(ActionEvent event) {
        String id = cmbCustomerId.getValue();
        try {
            Customer customer = CustomerRepo.searchById(id);


            lblCustomerName.setText(customer.getCustomer_name());


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void getCustomerIds() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = CustomerRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbCustomerId.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void cmbCategoryOnAction(ActionEvent event) {

        String id = (String) cmbCategoryCode.getValue();
        try {
            Inventory inventory = inventoryRepo.searchById(Integer.parseInt(id));


            lblUnitPrice.setText(inventory.getUnit_price());
            lblQtyOnHand.setText(String.valueOf(inventory.getQuantity()));
            lblCategoryName.setText(String.valueOf(inventory.getCategory_name()));


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void getCategoryNo() {
        ObservableList<String> obList = FXCollections.observableArrayList();

        try {
            List<String> idList = inventoryRepo.getIds();

            for(String id : idList) {
                obList.add(id);
            }

            cmbCategoryCode.setItems(obList);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void txtQtyOnAction(ActionEvent event) {

    }
    @FXML
    void btnNewCustomerOnAction(ActionEvent event) {
        AnchorPane root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/view/CustomerForm.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }


}*/
