package org.store.bo.custom.impl;

import org.modelmapper.ModelMapper;
import org.store.bo.custom.CustomerBo;
import org.store.dao.DaoFactory;
import org.store.dao.custom.CustomerDao;
import org.store.dto.CustomerDto;
import org.store.entity.Customer;
import org.store.utill.DaoType;

public class CustomerBoImpl implements CustomerBo {

    private CustomerDao customerDao = DaoFactory.getInstance().getDao(DaoType.CUSTOMER);
    @Override
    public boolean saveCustomer(CustomerDto dto) {
        return customerDao.save(new ModelMapper().map(dto, Customer.class));

    }

    @Override
    public boolean deleteById(String id) {
        return false;
    }
}
