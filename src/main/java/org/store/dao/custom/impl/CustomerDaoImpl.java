package org.store.dao.custom.impl;

import org.store.dao.custom.CustomerDao;
import org.store.dto.CustomerDto;
import org.store.entity.Customer;
import org.store.utill.CrudUtill;

import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer entity) {
        try {
            String SQL = "INSERT INTO customer VALUES (?,?,?,?,?,?,?,?,?,?)";
            CrudUtill.execute(
                    SQL,
                    entity.getId(),
                    entity.getTitle(),
                    entity.getName(),
                    entity.getDob(),
                    entity.getNic(),
                    entity.getAddress(),
                    entity.getEmail(),
                    entity.getContactNo(),
                    entity.getBankName(),
                    entity.getBankAccountNo()
            );


        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return false;

    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
