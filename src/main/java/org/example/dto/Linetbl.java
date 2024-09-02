package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Linetbl {
    private int no;
    private String itemCode;
    private String itemDescription;
    private String size;
    private Integer qty;
    private Double lineTotal;

}
