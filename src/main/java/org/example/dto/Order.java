package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Order {
    private String orderId;
    private Date date;
    private double total;
    private String PayementMethod;
    private List<OrderDetails> orderDetailsList;
}
