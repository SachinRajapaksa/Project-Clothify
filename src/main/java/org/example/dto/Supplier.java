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
    private String suppID;
    private String suppName;
    private String suppCompany;
    private String suppEmail;
}
