package org.store.controller.customer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.store.db.DBConnection;
import org.store.dto.CustomerDto;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerController {

    private static CustomerController instance;

    private CustomerController(){}

    public static CustomerController getInstance(){
        if(instance==null){
            return instance=new CustomerController();
        }
        return instance;
    }
    public ObservableList<CustomerDto> loadCustomers() {
        try {

            ResultSet resultSet = DBConnection.getInstance().getConnection().createStatement().executeQuery("SELECT * FROM customer");
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
}
