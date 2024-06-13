package org.example.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private String empId;
    private String firstName;
    private String lastName;
    private Date dob;
    private String eMail;
    private String password;
    private String accType;


}
