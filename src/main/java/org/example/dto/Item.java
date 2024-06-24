package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Item {
    private String itemCode;
    private String suppId;
    private String description;
    private String size;
    private Integer qty;
    private Double unitPrice;



}
