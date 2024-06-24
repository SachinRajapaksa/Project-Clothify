package org.example.controller.employee;


import org.example.dto.User;

public interface EmployeeService {

    boolean addUser(User user);
     User searchUser(String empId);

}
