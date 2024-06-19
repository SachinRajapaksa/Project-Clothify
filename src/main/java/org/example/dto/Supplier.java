package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class Supplier {
    private String SuppID;
    private String SuppName;
    private String SuppCompany;
    private String SuppEmail;
}
