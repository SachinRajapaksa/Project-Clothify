package org.example.controller.login;


import org.example.Util.CrudUtil;
import org.example.dto.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController implements LoginService {
    private LoginController(){}
    private static LoginController instance;

    public static LoginController getInstance(){
        if(instance==null){
            instance=new LoginController();
        }
        return instance;
    }

    public User findUser(String eMail ,String Password){
        try {
            ResultSet resultSet= CrudUtil.execute("SELECT * FROM user WHERE eMail=? AND Password=?",eMail,Password);
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

