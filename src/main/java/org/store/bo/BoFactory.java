package org.store.bo;

import org.store.bo.custom.impl.CustomerBoImpl;
import org.store.bo.custom.impl.EmployeeBoImpl;
import org.store.utill.BoType;

public class BoFactory {

    private static BoFactory instance;
    private BoFactory(){}

    public static BoFactory getInstance(){
        return instance!=null?instance:(instance=new BoFactory());
    }
    public <T extends SuperBo>T getBo(BoType type){
        switch (type){
            case CUSTOMER:return (T) new CustomerBoImpl();
            case EMPLOYEE:return (T) new EmployeeBoImpl();
        }
        return null;
    }
}
