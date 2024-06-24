package org.store.controller.order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.store.controller.customer.CustomerController;
import org.store.dto.CustomerDto;
import org.store.entity.Employee;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class PlaceOrderFormController implements Initializable {
    public Label lblTime;
    public Label lblDate;
    public AnchorPane LoadFormContent;
    
    public JFXTextField txtQtyFroCustomer;
    public Label lblItemCode;
    public Label lblOrderId;
    public Label lblDiscount;
    public Label lblPrice;
    public Label lblQty;
    public Label lblEmail;
    public Label lblContact;
    public Label lblName;
    public Label lblNetTotal;
    public Label lblItemCode1;
    public Label lblEmail1;
    public JFXComboBox cmbItemCodes;
    public JFXComboBox cmbCustomerIDs;

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("view/admin_dashboard.fxml");

        assert resource != null;

        Parent load = (Parent) FXMLLoader.load(resource);
        this.LoadFormContent.getChildren().clear();
        this.LoadFormContent.getChildren().add(load);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
        loadCustomerIDs();
        generateOrderId();
    }

    private void generateOrderId() {
    }

    private void loadCustomerIDs() {
        ObservableList<CustomerDto> allCustomers = CustomerController.getInstance().loadCustomers();

        ObservableList ids = FXCollections.observableArrayList();

       allCustomers.forEach(customerDto -> {
           ids.add(customerDto.getId());
       });
       cmbCustomerIDs.setItems(ids);
    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime cTime = LocalTime.now();
            lblTime.setText(
                    cTime.getHour() + ":" + cTime.getMinute() + ":" + cTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();

    }

    public void btnPlaceOrderOnAction(ActionEvent actionEvent) {
    }

    public void txtAddtoCartOnAction(ActionEvent actionEvent) {
    }

    public void btnAddToCartOnAction(ActionEvent actionEvent) {
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }
}
