package org.store.bo.custom;


import org.store.bo.SuperBo;
import org.store.dto.CustomerDto;

public interface CustomerBo extends SuperBo {
    boolean saveCustomer(CustomerDto dto);
    boolean deleteById(String id);
}
