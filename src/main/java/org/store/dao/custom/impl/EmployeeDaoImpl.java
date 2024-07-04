package org.store.dao.custom.impl;

import javafx.collections.ObservableList;
import org.hibernate.Session;
import org.store.dao.custom.EmployeeDao;
import org.store.entity.Employee;
import org.store.utill.HibernateUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeDaoImpl implements EmployeeDao {

    @Override
    public boolean save(Employee entity) {
        Session session = HibernateUtil.getSession();
        session.getTransaction().begin();
        session.persist(entity);
        session.getTransaction().commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String id) {
        return false;
    }
}
