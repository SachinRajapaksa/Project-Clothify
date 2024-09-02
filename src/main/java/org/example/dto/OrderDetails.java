package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString

public class OrderDetails {

    private int no;
    private String orderId;
    private Date date;
    private String itemCode;
    private String itemDesc;
    private String size;
    private int qty;
    private double total;
    private String PayementMethod;


}
