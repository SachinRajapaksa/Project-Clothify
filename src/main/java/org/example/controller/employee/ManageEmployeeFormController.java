package org.example.controller.employee;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import com.sun.tools.javac.Main;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.example.controller.employee.EmployeeController;
import org.example.controller.login.LoginController;
import org.example.db.DBConnection;
import org.example.dto.User;
import org.modelmapper.internal.bytebuddy.asm.Advice;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageEmployeeFormController implements Initializable {
    public TextField txtEmpID;
    public JFXTextField txtFirstName;
    public JFXTextField txtLastName;
    public DatePicker dateDOB;
    public JFXTextField txtEmail;
    public JFXTextField txtPassword;
    public JFXComboBox cmbAccType;

    public JFXCheckBox CBAcc;
    private Stage stage;
    private Scene scene;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadDropMenu();
        genarateEmpID();


    }

    public void txtEmpOnAction(ActionEvent actionEvent) {
        btnSearchOnAction(actionEvent);
    }


    public void btnSearchOnAction(ActionEvent actionEvent) {
        User user = EmployeeController.getInstance().searchUser(txtEmpID.getText());
        txtFirstName.setText(user.getFirstName());
        txtLastName.setText(user.getLastName());
        dateDOB.getValue();
        txtEmail.setText(user.getEMail());
        txtPassword.setText(user.getPassword());
        cmbAccType.setValue(user.getAccType());
    }



    public void btnRemoveOnAction(ActionEvent actionEvent) {
        try {
            boolean execute = DBConnection.getInstance().getConnection().createStatement().execute("DELETE FROM user WHERE empId='" + txtEmpID.getText() + "'");
            System.out.println(execute);
            if(execute==false)
                new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure To Delete Employee" + "  " + txtFirstName.getText()).show();
                clearText();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);


        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
       try {
                boolean execute = DBConnection.getInstance().getConnection().createStatement().execute("DELETE FROM user WHERE empId='" + txtEmpID.getText() + "'");

           try {
               btnAddOnAction(actionEvent);
           } catch (ParseException e) {
               throw new RuntimeException(e);
           }
       } catch (SQLException | ClassNotFoundException e) {
           throw new RuntimeException(e);
       }


    }


    public void btnAddOnAction(ActionEvent actionEvent) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date=format.parse(dateDOB.getValue().toString());

        User user = new User(
                txtEmpID.getText(),
                txtFirstName.getText(),
                txtLastName.getText(),
                date,
                txtEmail.getText(),
                txtPassword.getText(),
                cmbAccType.getValue().toString()
        );
        boolean b = EmployeeController.getInstance().addUser(user);
        if(b==false){
            new Alert(Alert.AlertType.CONFIRMATION,"Are You Sure To Add Employee" + "  " + txtFirstName.getText()).show();
            clearText();
            genarateEmpID();


        }


    }
    private void loadDropMenu(){
        ObservableList <Object> items = FXCollections.observableArrayList();
        items.add("Admin");
        items.add("Employee");
        cmbAccType.setItems(items);
    }


    public void genarateEmpID() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM user");
            Integer count=0;
            while(resultSet.next()){
                count=resultSet.getInt(1);
            }
        if (count==0){
            txtEmpID.setText("EMP001");
        }
        String lastEmpId = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT empId\n" +
                    "FROM user\n" +
                    "ORDER BY empId DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()){
                lastEmpId=resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastEmpId);
                if(matcher.find()){
                int num=Integer.parseInt(matcher.group(1));
                num++;
                txtEmpID.setText(String.format("EMP%03d",num));
                }else {
                new Alert(Alert.AlertType.WARNING,"EMP ID ERROR").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void CBExistAccountOnAction(ActionEvent actionEvent) {
        BooleanProperty booleanProperty = txtEmpID.editableProperty();
        booleanProperty.setValue(!booleanProperty.getValue());
        if (booleanProperty.getValue()==false) {
            genarateEmpID();
        }
    }
    private void clearText(){
        txtFirstName.setText(null);
        txtLastName.setText(null);
        txtEmail.setText(null);
        dateDOB.setValue(null);
        txtPassword.setText(null);
        cmbAccType.setItems(cmbAccType.getItems());
    }

    public void btnCloseOnAction() {

    }
    }

