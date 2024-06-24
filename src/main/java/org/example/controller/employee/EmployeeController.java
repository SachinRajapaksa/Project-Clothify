package org.example.controller.employee;


import org.example.Util.CrudUtil;

import org.example.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeController implements EmployeeService {
    private static EmployeeController instance;
    private EmployeeController(){}
    public static EmployeeController getInstance(){
        if(instance==null){
        return instance=new EmployeeController();
        }
        return instance;
    }

    public boolean addUser(User user)  {
        try {
            String SQL="INSERT INTO user VALUES(?,?,?,?,?,?,?) ";
            CrudUtil.execute(
                    SQL,
                    user.getEmpId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getDob(),
                    user.getEMail(),
                    user.getPassword(),
                    user.getAccType()
                );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public User searchUser(String empId) {
    try {
        ResultSet resultSet = CrudUtil.execute("SELECT * FROM user WHERE empId=?", empId);
        while (resultSet.next()){
        return new User(
                resultSet.getString(1),
                resultSet.getString(2),
                resultSet.getString(3),
                resultSet.getDate(4),
                resultSet.getString(5),
                resultSet.getString(6),
                resultSet.getString(7)
        );
        }
    } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
    }
        return null;

    }



}
