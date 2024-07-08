package lk.ijse.pos.controller;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class AddNewUserFormController {

    @FXML
    private Button btnSave;

    @FXML
    private Label lblUserId;

    @FXML
    private AnchorPane rooter;

    @FXML
    private TextField txtPassword;



    @FXML
    private TextField txtUserName;

    @FXML
    void btnClickOnAction(ActionEvent event) {
        String id = lblUserId.getText();
        String name = txtUserName.getText();
        String password = txtPassword.getText();


        String sql = "INSERT INTO user VALUES(?,?,?)";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement pstm = connection.prepareStatement(sql);

            pstm.setInt(1, Integer.parseInt(id));
            pstm.setString(2,name);
            pstm.setString(3,password);


            boolean isSaved = pstm.executeUpdate() > 0;
            if (isSaved) {
                new Alert(Alert.AlertType.INFORMATION, "user Saved Successfully").show();
            }
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
        }
    }



}
