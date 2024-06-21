package org.store.controller;

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
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;
import org.store.db.DBConnection;
import org.store.dto.CustomerDto;
import org.store.dto.tm.CustomerTM;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.collections.FXCollections.observableArrayList;

public class AddCustomerFormController implements Initializable {
    public AnchorPane LoadFormContent;
    public Label lblDate;
    public Label lblTime;
    public TableView tblCustomer;
    public TableColumn colCustomerId;
    public TableColumn colCustomerTitle;
    public TableColumn colCustomerName;
    public TableColumn colDob;
    public TableColumn colNic;
    public TableColumn colAddress;
    public TableColumn colEmail;
    public TableColumn colContactNo;
    public TableColumn colBankName;
    public TableColumn colBankAccountNo;
    public JFXTextField txtCustomerID;
    public JFXTextField txtName;
    public JFXTextField txtNic;
    public JFXTextField txtAddress;
    public JFXTextField txtEmail;
    public JFXTextField txtContactNo;
    public JFXTextField txtbankName;
    public DatePicker dateDob;
    public JFXComboBox cmbTitle;
    public JFXTextField txtBankAccountNo;
    private List<CustomerDto> customerDtoList;

    public void backBtnOnAction(ActionEvent actionEvent) throws IOException {
        URL resource = this.getClass().getResource("/view/admin_dashboard.fxml");

        assert resource != null;

        Parent load = FXMLLoader.load(resource);
        this.LoadFormContent.getChildren().clear();
        this.LoadFormContent.getChildren().add(load);

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCustomerId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colCustomerTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colCustomerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colDob.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colNic.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colContactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        colBankName.setCellValueFactory(new PropertyValueFactory<>("bankName"));
        colBankAccountNo.setCellValueFactory(new PropertyValueFactory<>("bankAccountNo"));
        loadDateAndTime();
        loadDropMenu();
        loadCustomers();
        loadCustomerTable();
    }

    private void loadCustomerTable() {
        ObservableList<CustomerTM> customerTableData = FXCollections.observableArrayList();

        customerDtoList.forEach(customerDto ->{
            CustomerTM customerTM = new CustomerTM(
                    customerDto.getId(),
                    customerDto.getTitle(),
                    customerDto.getName(),
                    customerDto.getDob(),
                    customerDto.getNic(),
                    customerDto.getAddress(),
                    customerDto.getEmail(),
                    customerDto.getContactNo(),
                    customerDto.getBankName(),
                    customerDto.getBankAccountNo()
            );
            customerTableData.add(customerTM);
        });

        tblCustomer.setItems(customerTableData);


    }

    private void loadCustomers() {
        customerDtoList=new ArrayList<>();
        try {
            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM customer");
            while (resultSet.next()){
                CustomerDto customerDto = new CustomerDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getDouble(8),
                        resultSet.getString(9),
                        resultSet.getString(10)
                );
                customerDtoList.add(customerDto);

            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadDropMenu() {
        ObservableList<Object> items = FXCollections.observableArrayList();
        items.add("MRS");
        items.add("MR");
        items.add("MS");
        items.add("MISS");
        cmbTitle.setItems(items);
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

    public void btnUpdateOnAction(ActionEvent actionEvent) {
    }

    public void btnAddCustomerOnAction(ActionEvent actionEvent) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateDob.getValue().toString());
        CustomerDto customerDto = new CustomerDto(txtCustomerID.getText(),
                cmbTitle.getValue().toString(),
                txtName.getText(),
                date,
                txtNic.getText(),
                txtAddress.getText(),
                txtEmail.getText(),
                Double.parseDouble(txtContactNo.getText()),
                txtbankName.getText(),
                txtBankAccountNo.getText()
        );


        try {

            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?,?)");
            psTm.setString(1,customerDto.getId());
            psTm.setString(2,customerDto.getTitle());
            psTm.setString(3,customerDto.getName());
            psTm.setObject(4,customerDto.getDob());
            psTm.setString(5,customerDto.getNic());
            psTm.setString(6,customerDto.getAddress());
            psTm.setString(7,customerDto.getEmail());
            psTm.setDouble(8,customerDto.getContactNo());
            psTm.setString(9,customerDto.getBankName());
            psTm.setString(10,customerDto.getBankAccountNo());

            psTm.execute();
            loadCustomers();
            loadCustomerTable();
            clearText();


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnDeleteOnAction(ActionEvent actionEvent) {
        try {
            boolean execute = DBConnection.getInstance().getConnection().createStatement().execute("DELETE FROM customer WHERE CustId='" + txtCustomerID.getText() + "'");
            loadCustomers();
            loadCustomerTable();
            clearText();
            if(execute){
                System.out.println("Customer Deleted");
            }else {
                System.out.println("Customer Not Deleted");
            }
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnClearOnAction(ActionEvent actionEvent) {
        txtCustomerID.setText(null);
        cmbTitle.setValue(null);
        txtName.setText(null);
        dateDob.setValue(null);
        txtNic.setText(null);
        txtAddress.setText(null);
        txtEmail.setText(null);
        txtContactNo.setText(null);
        txtbankName.setText(null);
        txtBankAccountNo.setText(null);
    }

    public void btnSearchOnAction(ActionEvent actionEvent) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Connection connection = DBConnection.getInstance().getConnection();
            ResultSet resultSet = connection.createStatement().executeQuery("SELECT * FROM customer WHERE CustId='"+txtCustomerID.getText()+"'" );
            while (resultSet.next()){
                CustomerDto customerDto = new CustomerDto(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDate(4),
                        resultSet.getString(5),
                        resultSet.getString(6),
                        resultSet.getString(7),
                        resultSet.getDouble(8),
                        resultSet.getString(9),
                        resultSet.getString(10)
                );




                cmbTitle.setValue(customerDto.getTitle());
                txtName.setText(customerDto.getName());
                txtNic.setText(customerDto.getNic());
                txtAddress.setText(customerDto.getAddress());
                txtEmail.setText(customerDto.getEmail());
                txtContactNo.setText(String.valueOf(customerDto.getContactNo()));
                txtbankName.setText(customerDto.getBankName());
                txtBankAccountNo.setText(customerDto.getBankAccountNo());

            }

        } catch (ClassNotFoundException | SQLException  e) {
            throw new RuntimeException(e);
        }
    }

    private void clearText(){
        txtCustomerID.setText(null);
        cmbTitle.setValue(null);
        txtName.setText(null);
        dateDob.setValue(null);
        txtNic.setText(null);
        txtAddress.setText(null);
        txtEmail.setText(null);
        txtContactNo.setText(null);
        txtbankName.setText(null);
        txtBankAccountNo.setText(null);
    }

}
