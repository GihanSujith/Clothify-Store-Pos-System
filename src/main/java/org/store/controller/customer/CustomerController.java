package org.store.controller.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.store.db.DBConnection;
import org.store.dto.CustomerDto;
import org.store.utill.CrudUtill;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class CustomerController implements CustomerService{

    private static CustomerController instance;

    private CustomerController(){}

    public static CustomerController getInstance(){
        if(instance==null){
            return instance=new CustomerController();
        }
        return instance;
    }
    public CustomerDto searchCustomer(String customerId){
        try {
            ResultSet resultSet = CrudUtill.execute("SELECT * FROM customer WHERE CustId=?",customerId);
                while (resultSet.next()) {
                    return new CustomerDto(
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

                }


        } catch (ClassNotFoundException | SQLException  e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public ObservableList<CustomerDto> loadCustomers() {
        try {

            ResultSet resultSet = CrudUtill.execute("SELECT * FROM customer");
            ObservableList<CustomerDto> customerDtoList= FXCollections.observableArrayList();

            while (resultSet.next()) {
                customerDtoList.add(
                        new CustomerDto(
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
                        )
                );
            }
            return customerDtoList;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public boolean addCustomer(CustomerDto customerDto){
        try {
            String SQL = "INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?,?)";
            CrudUtill.execute(
                    SQL,
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


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
