package org.example.controller.user;


import org.example.dto.User;

public interface UserService {

    boolean addUser(User user);
     User searchUser(String empId);

}
