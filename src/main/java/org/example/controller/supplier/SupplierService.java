package org.example.controller.supplier;

import javafx.collections.ObservableList;
import org.example.dto.Supplier;

public interface SupplierService {
    boolean addSupplier(Supplier supplier);
    Supplier searchSupplier(String SupplierID);
    ObservableList<Supplier> getAllSupp();
}
