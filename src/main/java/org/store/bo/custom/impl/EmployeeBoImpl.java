package org.store.bo.custom.impl;

import org.modelmapper.ModelMapper;
import org.store.bo.custom.EmployeeBo;
import org.store.dao.DaoFactory;
import org.store.dao.custom.CustomerDao;
import org.store.dao.custom.EmployeeDao;
import org.store.dto.EmployeeDto;
import org.store.entity.Customer;
import org.store.entity.Employee;
import org.store.utill.DaoType;

public class EmployeeBoImpl implements EmployeeBo {

    private EmployeeDao employeeDao = DaoFactory.getInstance().getDao(DaoType.EMPLOYEE);
    @Override
    public boolean saveEmployee(EmployeeDto dto) {
        return employeeDao.save(new ModelMapper().map(dto, Employee.class));
    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }
}
