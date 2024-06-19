package org.example.controller.supplier;

import org.example.dto.Supplier;

public interface SupplierService {
    boolean addSupplier(Supplier supplier);
    Supplier searchSupplier(String SupplierID);
}
