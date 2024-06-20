package org.store.controller;

import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.mindrot.jbcrypt.BCrypt;
import org.store.entity.User;
import org.store.utill.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginFormController {
    public AnchorPane loginFormContext;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    private CrudUtill CrudUtil;


    public void btnLoginOnAction(ActionEvent actionEvent) {
        User user = getLogInUser();
        if (user!=null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/admin_dashboard.fxml"));
                Parent root1 = (Parent) fxmlLoader.load();
                Stage stage = new Stage();
                stage.setScene(new Scene(root1));
                // Get the current window
                Stage currentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                // Close the previous window
                currentStage.close();
                stage.setResizable(false);
                stage.show();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            new Alert(Alert.AlertType.ERROR, "Your Password or Email incorrect try again!").show();
        }
    }

    private User getLogInUser() {
        try {
            ResultSet rst= CrudUtil.execute("SELECT * FROM user\n" +
                    "WHERE email = ? AND password = ? AND (user_type = 'admin' OR user_type = 'user');",txtEmail.getText(),txtPassword.getText());

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    }