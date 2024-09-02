package org.example.controller.front;

import org.example.dto.OrderDetails;

public interface FrontService {
    boolean addOrder(OrderDetails orderDetails);
    boolean addOrderDetails(OrderDetails orderDetails);
}
