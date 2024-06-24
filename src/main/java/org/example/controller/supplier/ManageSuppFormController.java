package org.example.controller.supplier;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.BooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import org.example.db.DBConnection;
import org.example.dto.Supplier;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageSuppFormController implements Initializable {
    public TextField txtSupplierID;
    public Button btnSearch;
    public JFXTextField txtSupplierName;
    public JFXTextField txtCompanyName;
    public JFXTextField txtEmail;
    public JFXCheckBox CBSup;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genarateSupID();
    }



    public void btnSearchOnAction() {
        Supplier supplier = SupplierController.getInstance().searchSupplier(txtSupplierID.getText());
        txtSupplierName.setText(supplier.getSuppName());
        txtCompanyName.setText(supplier.getSuppCompany());
        txtEmail.setText(supplier.getSuppEmail());

    }

    public void btnRemoveOnAction() {
        try {
            boolean execute = DBConnection.getInstance().getConnection().createStatement().execute("DELETE FROM supplier WHERE suppId='" + txtSupplierID.getText() + "'");
            if(execute==false)
                new Alert(Alert.AlertType.INFORMATION, "Are You Sure To Delete Employee" + "  " + txtSupplierName.getText()).show();
            clearText();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction(ActionEvent actionEvent) {
        try {
            DBConnection.getInstance().getConnection().createStatement().execute("DELETE FROM supplier WHERE suppId='" + txtSupplierID.getText() + "'");

            btnAddOnAction();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnAddOnAction() {
        Supplier supplier = new Supplier(
                txtSupplierID.getText(),
                txtSupplierName.getText(),
                txtCompanyName.getText(),
                txtEmail.getText()
        );
        boolean b = SupplierController.getInstance().addSupplier(supplier);
        if (b == false) {
            new Alert(Alert.AlertType.INFORMATION, "Are you Sure to add " + txtSupplierName.getText() + " Supplier").show();
        }
        clearText();
        genarateSupID();
    }

    private void clearText() {
        txtSupplierName.setText(null);
        txtCompanyName.setText(null);
        txtEmail.setText(null);
    }

    public void CBExistSupplierOnAction() {
        BooleanProperty booleanProperty = txtSupplierID.editableProperty();
        booleanProperty.setValue(!booleanProperty.getValue());
        if (booleanProperty.getValue()==false) {
            genarateSupID();
            clearText();
        }

    }

    public void genarateSupID() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM supplier");
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count == 0) {
                txtSupplierID.setText("SUP001");
            }
            String lastSupID = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT SuppId\n" +
                    "FROM supplier\n" +
                    "ORDER BY SuppId DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastSupID = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastSupID);
                if (matcher.find()) {
                    int num = Integer.parseInt(matcher.group(1));
                    num++;
                    txtSupplierID.setText(String.format("SUP%03d", num));
                } else {
                    new Alert(Alert.AlertType.WARNING, "SUP ID ERROR").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public void txtSupOnAction(ActionEvent actionEvent) {
        btnSearchOnAction();
    }
}
