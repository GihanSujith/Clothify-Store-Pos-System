package org.store.controller.customer;

import javafx.collections.ObservableList;
import org.store.dto.CustomerDto;

public interface CustomerService {
    CustomerDto searchCustomer(String customerId);

    ObservableList<CustomerDto> loadCustomers();
    boolean addCustomer(CustomerDto customerDto);
}
