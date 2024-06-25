package org.store.controller.employee;

import javafx.collections.ObservableList;
import org.store.dto.CustomerDto;
import org.store.dto.EmployeeDto;

public interface EmployeeService {
    CustomerDto searchCustomer(String employeeId);

    ObservableList<EmployeeDto> loadEmployees();
    boolean addEmployee(EmployeeDto employeeDto);
}
