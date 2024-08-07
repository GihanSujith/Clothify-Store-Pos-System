package org.store.dao.custom.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.store.dao.custom.CustomerDao;
import org.store.entity.Customer;
import org.store.utill.CrudUtill;
import org.store.utill.HibernateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public boolean save(Customer entity) {

        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
        /*try {
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
        return false;*/

    }

}
