package org.store.controller.employee;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.store.dto.CustomerDto;
import org.store.dto.EmployeeDto;
import org.store.utill.CrudUtill;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeController implements EmployeeService{
    @Override
    public CustomerDto searchCustomer(String employeeId) {
        return null;
    }

    private static EmployeeController instance;

    private EmployeeController (){}

    public static EmployeeController getInstance(){
        if(instance==null){
            return instance=new EmployeeController();
        }
        return instance;
    }

    public boolean addEmployee(EmployeeDto employeeDto){
        try {
            String SQL = "INSERT INTO employee VALUES (?,?,?,?)";
            CrudUtill.execute(
                    SQL,
                    employeeDto.getId(),
                    employeeDto.getName(),
                    employeeDto.getCompany(),
                    employeeDto.getEmail()
            );


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public ObservableList<EmployeeDto> loadEmployees() {
        try {

            ResultSet resultSet = CrudUtill.execute("SELECT * FROM employee");
            ObservableList<EmployeeDto> employeeDtoList= FXCollections.observableArrayList();

            while (resultSet.next()) {
                employeeDtoList.add(
                        new EmployeeDto(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getString(4)
                        )
                );
            }
            return employeeDtoList;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
