package org.store.controller.order;

import org.store.controller.item.ItemController;
import org.store.db.DBConnection;
import org.store.dto.OrderDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrderController {
    private static OrderController instance;

    private OrderController(){}

    public boolean placeOrder(OrderDto orderDto) throws SQLException, ClassNotFoundException {
        Connection connection =  DBConnection.getInstance().getConnection();
        try {

            connection.setAutoCommit(false);
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO orders VALUE(?,?,?)");
            psTm.setString(1,orderDto.getOrderId());
            psTm.setObject(2,orderDto.getOrderDate());
            psTm.setString(3,orderDto.getCustomerID());

            boolean isOrderAdd = psTm.execute();

            if (!isOrderAdd){
                boolean isOrderDetailAdd = OrderDetailsController.getInstance().addOrderDetail(orderDto.getOrderDetailDtoList());
                if (isOrderDetailAdd){
                    boolean isUpdateStock = ItemController.getInstance().updateStock(orderDto.getOrderDetailDtoList());
                    if (isUpdateStock){
                        connection.setAutoCommit(true);
                        return true;
                    }
                }
            }
            connection.rollback();
            return false;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            connection.setAutoCommit(true);
        }

    }

    public static OrderController getInstance(){
        if (instance==null){
            return instance=new OrderController();
        }
        return instance;
    }
}
