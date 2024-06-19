package org.store.controller.employee;


public class EmployeeController implements EmployeeService{

    private static EmployeeController instance;

    private EmployeeController (){}

    public static EmployeeController getInstance(){
        if(instance==null){
            return instance=new EmployeeController();
        }
        return instance;
    }

}
