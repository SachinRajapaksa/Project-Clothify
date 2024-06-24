package org.example.controller.item;

import org.example.dto.Item;


public interface ItemService {
    boolean addItem(Item item);
    Item searchItem(String ItemCode);
}
