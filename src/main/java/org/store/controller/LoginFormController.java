package org.store.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.store.utill.CrudUtill;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;


public class LoginFormController {
    public JFXTextField txtUserName;
    public JFXTextField pwdPassword;
    public JFXButton adminBtn;
    public JFXButton customerBtn;
    public AnchorPane loginFormContext;
    int attemptsLogAdmin = 0;
    int attemptsLogCustomer = 0;
    private CrudUtill CrudUtil;

    public void LogInAdminOnAction(ActionEvent actionEvent) {
        User user = getLogInUser();
        if (user!=null){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/dash_bord_form.fxml"));
                // fxmlLoader.setControllerFactory(controllerClass -> new DashBordFormController(user));
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

    public  User getLogInUser(){
        try {
            ResultSet rst= CrudUtil.execute("SELECT * FROM user\n" +
                    "WHERE email = ? AND password = ? AND (user_type = 'admin' OR user_type = 'user');",txtEmail.getText(),txtPassword.getText());
            while (rst.next()){

            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public void LogInCustomerOnAction(ActionEvent actionEvent) {


    }


}