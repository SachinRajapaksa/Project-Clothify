package org.example.controller.user;

import org.example.Util.CrudUtil;
import org.example.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserController {
    private static UserController instance;
    private UserController(){}
    public static UserController getInstance(){
        if(instance==null){
        return instance=new UserController();
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
        User user = null;
        return user;

    }

}
