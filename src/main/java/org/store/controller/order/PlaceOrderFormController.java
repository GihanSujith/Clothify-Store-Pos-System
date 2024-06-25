package org.store.controller.order;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.store.controller.customer.CustomerController;
import org.store.controller.item.ItemController;
import org.store.db.DBConnection;
import org.store.dto.CustomerDto;
import org.store.dto.ItemDto;
import org.store.dto.OrderDetailDto;
import org.store.dto.OrderDto;
import org.store.dto.tm.ItemTM;
import org.store.entity.Employee;
import org.store.utill.CrudUtill;

import java.beans.Statement;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.store.db.DBConnection.*;

public class PlaceOrderFormController implements Initializable {
    public Label lblTime;
    public Label lblDate;
    public AnchorPane LoadFormContent;
    
    public JFXTextField txtQtyFroCustomer;

    public Label lblOrderId;

    public Label lblQty;
    public Label lblEmail;
    public Label lblContact;
    public Label lblName;
    public Label lblNetTotal;

    public JFXComboBox cmbCustomerIDs;
    public JFXComboBox cmbItemCode;
    public Label lblDescription;
    public Label lblSize;
    public Label lblType;
    public Label lblSellingPrice;
    public TableColumn colItemCode;
    public TableColumn colQty;
    public TableColumn colUnitPrice;
    public TableColumn colType;
    public TableColumn colAmount;

    public TableColumn colDesc;
    public TableColumn colSize;
    public TableView tblCart;

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
        loadItemCodes();

        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("sellingPrice"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAmount.setCellValueFactory(new PropertyValueFactory<>("amount"));
        colDesc.setCellValueFactory(new PropertyValueFactory<>("description"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));

        cmbCustomerIDs.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            setCustomerFroLbl((String) newValue);
        });

        cmbItemCode.getSelectionModel().selectedItemProperty().addListener((observable,oldValue,newValue) -> {
            setItemDataFroLbl((String)newValue);
        });
    }

    private void setItemDataFroLbl(String itemCode) {
        ItemDto itemDto = ItemController.getInstance().searchItem(itemCode);
        lblSellingPrice.setText(String.valueOf(itemDto.getSellingPrice()));
        lblType.setText(itemDto.getType());
        lblDescription.setText(itemDto.getDescription());
        lblQty.setText(String.valueOf(itemDto.getQuantity()));
        lblSize.setText(itemDto.getSize());

    }

    private void setCustomerFroLbl(String customerId) {
        CustomerDto customerDto = CustomerController.getInstance().searchCustomer(customerId);
        lblName.setText(customerDto.getName());
        lblContact.setText(String.valueOf(customerDto.getContactNo()));
        lblEmail.setText(customerDto.getEmail());
    }


    private void loadItemCodes(){
        ObservableList<ItemDto> allItems = ItemController.getInstance().loadItems();

        ObservableList<String> itemCodes=FXCollections.observableArrayList();

        allItems.forEach(itemDto -> {
            itemCodes.add(itemDto.getItemCode());
        });
        cmbItemCode.setItems(itemCodes);

    }


    private void generateOrderId() {
        try {

            ResultSet resultSet = CrudUtill.execute("SELECT COUNT(*) FROM orders");
            Integer count = 0;
            while (resultSet.next()){
                count = resultSet.getInt(1);

            }
            if (count == 0) {
                lblOrderId.setText("D001");
            }
            String lastOrderId="";
            ResultSet resultSet1 = CrudUtill.execute( "SELECT OrderID\n" +
                    "FROM orders\n" +
                    "ORDER BY OrderID DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()){
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int number = Integer.parseInt(matcher.group(1));
                    number++;
                    lblOrderId.setText(String.format("D%03d", number));
                } else {
                    new Alert(Alert.AlertType.WARNING,"hello").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
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
        try {

            String orderId = lblOrderId.getText();
            DateFormat date = new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = date.parse(lblDate.getText());
            String customerId = cmbCustomerIDs.getValue().toString();

            List<OrderDetailDto> orderDetailList = new ArrayList<>();


            for (ItemTM itemTM : cartList){
               String oID = lblOrderId.getText();
                String itemCode = itemTM.getItemCode();
                Integer qty = itemTM.getQty();
                Double discount = itemTM.getDiscount();
                orderDetailList.add(new OrderDetailDto(oID,itemCode,qty,discount));
            }

            OrderDto orderDto = new OrderDto(orderId, orderDate, customerId, orderDetailList);
            boolean isOrderplace = OrderController.getInstance().placeOrder(orderDto);
            if (isOrderplace){
                generateOrderId();
                new Alert(Alert.AlertType.INFORMATION,"Order Place !!").show();
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



    public void txtAddtoCartOnAction(ActionEvent actionEvent) {
        btnAddToCartOnAction(actionEvent);
    }

    ObservableList<ItemTM> cartList = FXCollections.observableArrayList();
    public void btnAddToCartOnAction(ActionEvent actionEvent) {

        String itemCode =(String) cmbItemCode.getValue();
        Integer qtyFroCustomer = Integer.parseInt(txtQtyFroCustomer.getText());
        Double sellingPrice = Double.valueOf(lblSellingPrice.getText());
        String type = lblType.getText();
        Double amount = qtyFroCustomer*sellingPrice;
        String description = lblDescription.getText();
        String size = lblSize.getText();
        ItemTM itemTM = new ItemTM(itemCode, qtyFroCustomer, sellingPrice, type, amount, description, size,0.0);
        System.out.println(itemTM);

        int qtyStock = Integer.parseInt(lblQty.getText());
        if (qtyStock>qtyFroCustomer){
            new Alert(Alert.AlertType.WARNING,"Invalid QTY").show();
            return;
        }

        cartList.add(itemTM);
        tblCart.setItems(cartList);
        calcNetTotal();
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
    }

    public void calcNetTotal(){
        double ttl=0;
        for (ItemTM carObj : cartList){
            ttl+=carObj.getAmount();
        }
        lblNetTotal.setText(String.valueOf(ttl)+" /=");
    }

    public void btnCommitOnAction(ActionEvent actionEvent) {
        try {
           Connection connection = DBConnection.getInstance().getConnection();
           connection.setAutoCommit(true);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnRollBackOnAction(ActionEvent actionEvent) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            connection.rollback();
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
