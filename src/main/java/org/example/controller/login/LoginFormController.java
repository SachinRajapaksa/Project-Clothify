package org.example.controller.login;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

import java.util.Objects;

public class LoginFormController {
    public TextField txtEmail;
    public TextField txtPassword;

    public void btnLoginOnAction(ActionEvent actionEvent) {
        if(Objects.equals(txtEmail.getText(), "Admin") && Objects.equals(txtPassword.getText(), "admin")){
            System.out.println("Login");
        }
        else{
            System.out.println("Error");
        }

    }

    public void btnCancelOnAction(ActionEvent actionEvent) {

    }
}
