package org.example.controller.supplier;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.example.Util.CrudUtil;
import org.example.dto.Supplier;

import java.sql.ResultSet;
import java.sql.SQLException;


public class SupplierController implements SupplierService {

    private static SupplierController instance;

    private SupplierController() {
    }

    public static SupplierController getInstance() {
        if (instance == null) {
            instance = new SupplierController();
        }
        return instance;
    }


    public boolean addSupplier(Supplier supplier) {
        String SQL = "INSERT INTO supplier VALUES(?,?,?,?)";
        try {
            CrudUtil.execute(
                    SQL,
                    supplier.getSuppID(),
                    supplier.getSuppName(),
                    supplier.getSuppCompany(),
                    supplier.getSuppEmail()

            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;
    }

    public Supplier searchSupplier(String SupplierID) {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier WHERE SuppId=?", SupplierID);
            while (resultSet.next()) {
                return new Supplier(
                        resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4)

                );
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public ObservableList<Supplier> getAllSupp() {
        try {
            ResultSet resultSet = CrudUtil.execute("SELECT * FROM supplier");
            ObservableList<Supplier> listSupp = FXCollections.observableArrayList();
            while (resultSet.next()) {
                listSupp.add(
                        new Supplier(
                                resultSet.getString("suppId"),
                                resultSet.getString("suppName"),
                                resultSet.getString("suppCompany"),
                                resultSet.getString("supEmail")
                        )
                );
            }

            return listSupp;
        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }

    }
}


