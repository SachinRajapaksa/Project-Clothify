package org.example.controller.item;

import org.example.dto.Item;
import org.example.dto.Supplier;

public interface ItemService {
    boolean addItem(Item item);
    Item searchItem(String ItemCode);
}
