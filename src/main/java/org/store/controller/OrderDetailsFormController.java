package org.store.controller;

import com.jfoenix.controls.JFXComboBox;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import org.store.controller.customer.CustomerController;
import org.store.controller.item.ItemController;
import org.store.controller.order.OrderController;
import org.store.dto.CustomerDto;
import org.store.dto.ItemDto;
import org.store.dto.OrderDetailDto;
import org.store.dto.OrderDto;
import org.store.dto.tm.ItemTM;


import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

public class OrderDetailsFormController implements Initializable {
    public AnchorPane LoadFormContent;
    public Label lblDate;
    public Label lblTime;
    public TableView tblOrder;
    public TableColumn colOrderId;
    public TableColumn colDate;
    public TableColumn colItemCode;

    public TableColumn colQty;
    public TableColumn colDiscount;

    public JFXComboBox cmbOrderId;
    public JFXComboBox cmbItemCode;
    public TableColumn colCustomerId;
    public TableView tblOrderDetails;
    public TableColumn colOrderId2;
    public TableColumn colPrice;

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/admin_dashboard.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);
        this.LoadFormContent.getChildren().clear();
        this.LoadFormContent.getChildren().add(load);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDateAndTime();
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

}
