package org.store.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.store.db.DBConnection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;


public class LoginFormController implements Initializable {
    public AnchorPane loginFormContext;
    @FXML
    private JFXPasswordField pwdPassword;
    @FXML
    private JFXTextField txtEmail;

    public JFXButton loginButton;

    @FXML
    private JFXButton canselButton;
    @FXML
    private Label lblLoginMessage;




    public void btnLoginOnAction(ActionEvent actionEvent) {
        if (txtEmail.getText().isBlank() == false && pwdPassword.getText().isBlank() == false){
            validateLogin();
        }else{
            lblLoginMessage.setText("Please enter email and password");
        }
    }



    public void btnCanselOnAction(ActionEvent actionEvent) throws IOException {
       Stage stage = (Stage) canselButton.getScene().getWindow();
       stage.close();
    }

    public void validateLogin(){
        DBConnection connectNow = null;
        try {
            connectNow = new DBConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM UserAccount WHERE username = '" + txtEmail.getText() + "' AND password ='" + pwdPassword.getText() + "'";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while (queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    //lblLoginMessage.setText("Congratulations!");
                    createAccountForm();
                }else{
                    lblLoginMessage.setText("Invalid login. Please try again.");
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }

    }

    public void createAccountForm(){
        try{

            Parent root = FXMLLoader.load(getClass().getResource("/view/user_register_form.fxml"));
            Stage registerStage = new Stage();
            registerStage.initStyle(StageStyle.UNDECORATED);
            registerStage.setScene(new Scene(root,800,550));
            registerStage.show();

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}