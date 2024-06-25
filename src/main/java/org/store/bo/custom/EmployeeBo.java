package org.store.bo.custom;

import org.store.bo.SuperBo;
import org.store.dto.CustomerDto;
import org.store.dto.EmployeeDto;

public interface EmployeeBo extends SuperBo {
    boolean saveEmployee(EmployeeDto dto);
    boolean deleteById(String id);
}
