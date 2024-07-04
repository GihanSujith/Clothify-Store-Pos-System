package org.store.controller.order;

import org.store.dto.OrderDetailDto;
import org.store.utill.CrudUtill;

import java.sql.SQLException;
import java.util.List;

import static org.store.utill.CrudUtill.*;
import static org.store.utill.CrudUtill.execute;

public class OrderDetailsController {

    private static OrderDetailsController instance;
    private OrderDetailsController(){}

    public boolean addOrderDetail(List<OrderDetailDto> orderDetailDtoList){
        boolean isAdd=false;
        for (OrderDetailDto orderDetailDto:orderDetailDtoList){
             isAdd = addOrderDetail(orderDetailDto);
        }
        if (isAdd){
            return true;
        }
        return false;
    }

    public boolean addOrderDetail(OrderDetailDto orderDetailDto){
        try {
            Object isAdd = CrudUtill.execute(
                    "INSERT INTO orderdetail VALUE(?,?,?,?)",
                    orderDetailDto.getOrderId(),
                    orderDetailDto.getItemCode(),
                    orderDetailDto.getQty(),
                    orderDetailDto.getDiscount()
            );
            return (Boolean) isAdd;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static OrderDetailsController getInstance(){
        if (instance==null){
            return instance=new OrderDetailsController();
        }
        return instance;
    }
}
