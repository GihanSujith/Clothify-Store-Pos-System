package org.store.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDto {
    private String orderId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public List<OrderDetailDto> getOrderDetailDtoList() {
        return orderDetailDtoList;
    }

    public void setOrderDetailDtoList(List<OrderDetailDto> orderDetailDtoList) {
        this.orderDetailDtoList = orderDetailDtoList;
    }

    private Date orderDate;
    private String customerID;
    private List<OrderDetailDto> orderDetailDtoList;
}
