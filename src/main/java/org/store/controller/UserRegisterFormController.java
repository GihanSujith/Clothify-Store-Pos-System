package org.store.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.store.db.DBConnection;

import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class UserRegisterFormController implements Initializable {
    public AnchorPane LoadFormContent;
    @FXML
    private JFXTextField txtFirstName;
    @FXML
    private JFXTextField txtLastName;
    @FXML
    private JFXTextField txtUserName;
    @FXML
    private JFXTextField confirmPasswordField;
    @FXML
    private JFXTextField setPasswordField;
    @FXML
    private Label lblRegistrationMessage;
    @FXML
    private Label lblConfirmPassword;
    @FXML
    private JFXButton registerButton;
    @FXML
    private JFXButton closeButton;

    public void btnRegisterOnAction(ActionEvent actionEvent) {

        if(setPasswordField.getText().equals(confirmPasswordField.getText())){
            registerUser();
            lblConfirmPassword.setText("");

        }else{
            lblConfirmPassword.setText("Password does not match");
        }
        registerUser();
    }

    public void btnCloseOnAction(ActionEvent actionEvent) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

    }

    public void registerUser(){
        DBConnection connectNow = null;
        try {
            connectNow = new DBConnection();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Connection connectDB = connectNow.getConnection();

        String firstname = txtFirstName.getText();
        String lastname = txtLastName.getText();
        String username = txtUserName.getText();
        String password = setPasswordField.getText();

        String insertFields = "INSERT INTO UserAccount(FirstName,LastName,UserName,Password) VALUES('";
        String insertValues = firstname+"','"+lastname+"','"+username+"','"+password+"')";
        String insertToRegister = insertFields + insertValues;

        try{
            Statement statement = connectDB.createStatement();
            statement.executeUpdate(insertToRegister);

            lblRegistrationMessage.setText("User has been registered successfully!");

        }catch (Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
