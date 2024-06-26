package org.store.controller.item;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.store.db.DBConnection;
import org.store.dto.CustomerDto;
import org.store.dto.ItemDto;
import org.store.dto.OrderDetailDto;
import org.store.utill.CrudUtill;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class ItemController {

    private static ItemController instance;

    private ItemController(){}

    public static ItemController getInstance(){
        if(instance==null){
            return instance=new ItemController();
        }
        return instance;
    }
    public ObservableList<ItemDto> loadItems() {
        try {

            ResultSet resultSet = CrudUtill.execute("SELECT * FROM item");
            ObservableList<ItemDto> customerDtoList= FXCollections.observableArrayList();

            while (resultSet.next()) {
                    customerDtoList.add(
                       new ItemDto(
                                resultSet.getString(1),
                                resultSet.getString(2),
                                resultSet.getString(3),
                                resultSet.getInt(4),
                                resultSet.getDouble(5),
                                resultSet.getDouble(6),
                                resultSet.getString(7),
                                resultSet.getString(8)

                        )

                        );

            }
            return customerDtoList;
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public ItemDto searchItem(String itemCode){
        try {
            ResultSet resultSet = CrudUtill.execute( "SELECT * FROM item WHERE ItemCode=?",itemCode);
                while (resultSet.next()) {
                    return new ItemDto(
                            resultSet.getString(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            resultSet.getInt(4),
                            resultSet.getDouble(5),
                            resultSet.getDouble(6),
                            resultSet.getString(7),
                            resultSet.getString(8)
                    );

                }


        } catch (ClassNotFoundException | SQLException  e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public boolean updateStock(List<OrderDetailDto> orderDetailDtoList){
        boolean isUpdate = false;
        for (OrderDetailDto orderDetailDto:orderDetailDtoList){
            isUpdate = updateStock(orderDetailDto);
        }
        if (isUpdate){
            return true;
        }
        return false;
    }

    public boolean updateStock(OrderDetailDto orderDetailDto){

        try {
            Object isUpdate = CrudUtill.execute(
                    "UPDATE item SET Qty=Qty-? WHERE ItemCode = ?",
                    orderDetailDto.getQty(),
                    orderDetailDto.getItemCode()
            );
            return (Boolean) isUpdate;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
