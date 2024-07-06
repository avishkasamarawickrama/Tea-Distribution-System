/*
package lk.ijse.pos.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class loginFormController {


    public AnchorPane rootNode;

    @FXML
    private TextField txtPassword;

    @FXML
    private TextField txtUserName;

    public void btnLoginOnAction(ActionEvent actionEvent) throws IOException {

        String userId = txtUserName.getText();
        String pw = txtPassword.getText();

        try {
            checkCredential(userId, pw);
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }

    }

    private void checkCredential(String userId, String pw) throws SQLException, IOException {
        String sql = "SELECT userName, password FROM user WHERE userName = ?";

        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement pstm = connection.prepareStatement(sql);
        pstm.setObject(1, userId);

        ResultSet resultSet = pstm.executeQuery();
        if (resultSet.next()) {
            String dbPw = resultSet.getString("password");

            if (pw.equals(dbPw)) {
                if (isValied()) {
                    navigateToTheDashboard();
                } else {
                    new Alert(Alert.AlertType.ERROR, "sorry! Unable to login").show();
                }
            } else {
                new Alert(Alert.AlertType.ERROR, "sorry! password is incorrect!").show();
            }

        } else {
            new Alert(Alert.AlertType.INFORMATION, "sorry! user id can't be find!").show();
        }
    }

    private void navigateToTheDashboard() {
        AnchorPane rootNode = null;
        try {
            rootNode = FXMLLoader.load(this.getClass().getResource("/view/dashboard.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        Scene scene = new Scene(rootNode);

        Stage stage = (Stage) this.rootNode.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.setTitle("Dashboard Form");
    }



    @FXML
    void txtPasswordOnAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.pos.controller.loginFormController.TextFields.PASSWORD, txtPassword);
    }

    public boolean isValied() {
        if (!Regex.setTextColor(lk.ijse.Tea.util.TextFields.NAME, txtUserName)) return false;
        if (!Regex.setTextColor(lk.ijse.Tea.util.TextFields.PASSWORD, txtPassword)) return false;
        return true;
    }

    @FXML
    void txtUserNameAction(KeyEvent event) {
        Regex.setTextColor(lk.ijse.Tea.util.TextFields.NAME, txtUserName);
    }

}*/
