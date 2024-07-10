package org.example.controller.login;

import org.example.dto.User;

public interface LoginService {
    User findUser(String eMail , String Password);
}
