package org.example.controller.item;

import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.beans.property.BooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.controller.supplier.SupplierController;
import org.example.db.DBConnection;
import org.example.dto.Item;
import org.example.dto.Supplier;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManageItemFormController implements Initializable {

    public TextField txtItemCode;
    public ComboBox cmbSuppId;
    public TextField txtSupplierCompany;
    public JFXCheckBox cbExistItem;
    public JFXTextField txtDescription;
    public ComboBox cmbSizes;
    public JFXTextField txtQty;
    public JFXTextField txtUnitPrice;
    public Label lblSuppStatus;
    public Label lblSizeStatus;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        genarateItemCode();
        dropMenuSize();
        dropMenuSuppID();


        cmbSuppId.getSelectionModel().selectedItemProperty().addListener(((observable, oldValue, newValue) ->
                setCompanyForTxt((String) newValue)));
    }

    public void txtItemCodeOnAction(ActionEvent actionEvent) {
        btnSearchOnAction();
    }

    public void btnSearchOnAction() {
        Item item = ItemController.getInstance().searchItem(txtItemCode.getText());
        cmbSuppId.setValue(item.getSuppId());
        txtDescription.setText(item.getDescription());
        cmbSizes.setValue(item.getSize());
        txtQty.setText(item.getQty().toString());
        txtUnitPrice.setText(item.getUnitPrice().toString());


    }

    public void CBExistItemOnAction() {
        clearText();
        BooleanProperty booleanProperty = txtItemCode.editableProperty();
        booleanProperty.setValue(!booleanProperty.getValue());
        if (booleanProperty.getValue() == false) {
            genarateItemCode();
            clearText();

        }

    }

    private void clearText() {
        cmbSuppId.setValue(null);
        lblSuppStatus.setText("Select Supp ID");
        txtSupplierCompany.setText("select Supplier ID");
        txtDescription.setText(null);
        cmbSizes.setValue(null);
        cmbSizes.setPromptText("select");
        txtQty.setText(null);
        txtUnitPrice.setText(null);


    }



    public void btnAddOnAction() {
        if (cmbSuppId.getValue() != null && cmbSizes.getValue() != null) {
            lblSuppStatus.setText(null);
            lblSizeStatus.setText(null);
            Item item = new Item(
                    txtItemCode.getText(),
                    cmbSuppId.getValue().toString(),
                    txtDescription.getText(),
                    cmbSizes.getValue().toString(),
                    Integer.parseInt(txtQty.getText()),
                    Double.parseDouble(txtUnitPrice.getText())
            );

            boolean b = ItemController.getInstance().addItem(item);
            if (!b) {

                new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to add " + txtDescription.getText() + " item Menu").show();
                clearText();
                genarateItemCode();


            }

        } else if ((cmbSuppId.getValue() == null || txtSupplierCompany.getText() == "Supplier Company") && cmbSizes.getValue() != null) {
            lblSizeStatus.setText(null);
            lblSuppStatus.setText("Select Supplier");
        } else if (cmbSizes.getValue() == null && (cmbSuppId.getValue() != null || !Objects.equals(txtSupplierCompany.getText(), "Supplier Company"))) {
            lblSuppStatus.setText(null);
            lblSizeStatus.setText("Select Size");
        } else {
            lblSuppStatus.setText("Select Supplier");
            lblSizeStatus.setText("Select Size");

        }

    }


    public void btnAddmoreSizes() {
        if (cmbSuppId.getValue() != null && cmbSizes.getValue() != null) {
            lblSuppStatus.setText(null);
            lblSizeStatus.setText(null);
            Item item = new Item(
                    txtItemCode.getText(),
                    cmbSuppId.getValue().toString(),
                    txtDescription.getText(),
                    cmbSizes.getValue().toString(),
                    Integer.parseInt(txtQty.getText()),
                    Double.parseDouble(txtUnitPrice.getText())
            );

            boolean b = ItemController.getInstance().addItem(item);
            if (!b) {

                new Alert(Alert.AlertType.CONFIRMATION, "Are you sure to add " + txtDescription.getText() + " item Menu").show();
                cmbSizes.setValue(null);
                txtQty.setText(null);
                txtUnitPrice.setText(null);

                genarateItemCode();

            }


        } else if ((cmbSuppId.getValue() == null || txtSupplierCompany.getText() == "Supplier Company") && cmbSizes.getValue() != null) {
            lblSizeStatus.setText(null);
            lblSuppStatus.setText("Select Supplier");
        } else if (cmbSizes.getValue() == null && (cmbSuppId.getValue() != null || !Objects.equals(txtSupplierCompany.getText(), "Supplier Company"))) {
            lblSuppStatus.setText(null);
            lblSizeStatus.setText("Select Size");
        } else {
            lblSuppStatus.setText("Select Supplier");
            lblSizeStatus.setText("Select Size");

        }


    }

    public void btnRemoveOnAction() {
        try {
            boolean execute = DBConnection.getInstance().getConnection().createStatement().execute("DELETE FROM item WHERE itemCode='" + txtItemCode.getText() + "'");
            if (execute == false) {

                new Alert(Alert.AlertType.INFORMATION, "Are You Sure To Remove  " + txtDescription.getText() + " " + cmbSizes.getValue().toString() + " Item").show();
                clearText();

            }


        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void btnUpdateOnAction() {
        try {
            DBConnection.getInstance().getConnection().createStatement().execute("DELETE FROM item WHERE itemCode='" + txtItemCode.getText() + "'");
            btnAddOnAction();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }


    private void genarateItemCode() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM item");
            int count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count == 0) {
                txtItemCode.setText("I000001");
            }
            String lastItemcode = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT itemCode\n" +
                    "FROM item\n" +
                    "ORDER BY itemCode DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastItemcode = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastItemcode);
                if (matcher.find()) {
                    int num = Integer.parseInt(matcher.group(1));
                    num++;
                    txtItemCode.setText(String.format("I%06d", num));
                } else {
                    new Alert(Alert.AlertType.WARNING, "SUP ID ERROR").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void dropMenuSize() {
        ObservableList<Object> size = FXCollections.observableArrayList();
        size.add("S");
        size.add("XS");
        size.add("XXS");
        size.add("M");
        size.add("L");
        size.add("XL");
        size.add("XXL");
        size.add("None");
        cmbSizes.setItems(size);
    }

    private void dropMenuSuppID() {
        ObservableList<Supplier> allSupp = SupplierController.getInstance().getAllSupp();
        ObservableList<Object> SuppIds = FXCollections.observableArrayList();

        allSupp.forEach(supplier -> {
            SuppIds.add(supplier.getSuppID());
            cmbSuppId.setItems(SuppIds);
        });


    }

    private void setCompanyForTxt(String suppId) {
        Supplier supplier = SupplierController.getInstance().searchSupplier(suppId);
        txtSupplierCompany.setText(supplier.getSuppCompany());


    }

}
