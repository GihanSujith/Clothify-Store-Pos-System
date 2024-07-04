package org.store.dao;

import org.store.bo.custom.impl.EmployeeBoImpl;
import org.store.dao.custom.impl.CustomerDaoImpl;
import org.store.dao.custom.impl.EmployeeDaoImpl;

import org.store.utill.DaoType;

import static org.store.utill.BoType.EMPLOYEE;

public class DaoFactory {

    private static DaoFactory instance;
    private DaoFactory(){}

    public static DaoFactory getInstance(){
        return instance!=null?instance:(instance=new DaoFactory());
    }
    public <T extends SuperDao>T getDao(DaoType type){
        switch (type){
            case CUSTOMER:return (T)new CustomerDaoImpl();
            case EMPLOYEE:return (T) new EmployeeDaoImpl();

        }
        return null;
    }
}
