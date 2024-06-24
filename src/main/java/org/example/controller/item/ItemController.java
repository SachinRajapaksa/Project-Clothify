package org.example.controller.item;

import org.example.Util.CrudUtil;
import org.example.dto.Item;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ItemController implements ItemService {
    private ItemController(){}
    private static ItemController instance;

    public static ItemController getInstance(){
        if(instance==null) {
            instance=new ItemController();
        }
        return instance;
    }
    public boolean addItem(Item item){
        String SQL="INSERT INTO item VALUES(?,?,?,?,?,?)";
        try {
            CrudUtil.execute(
                    SQL,
                    item.getItemCode(),
                    item.getSuppId(),
                    item.getDescription(),
                    item.getSize(),
                    item.getQty(),
                    item.getUnitPrice()
            );
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return false;

    }
    public Item searchItem(String ItemCode){
        try {
           ResultSet resultSet= CrudUtil.execute("SELECT * FROM item WHERE itemCode=?",ItemCode);
           while (resultSet.next()){
               return new Item(
                       resultSet.getString(1),
                       resultSet.getString(2),
                       resultSet.getString(3),
                       resultSet.getString(4),
                       resultSet.getInt(5),
                       resultSet.getDouble(6)
                       );
           }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return null;

    }
}
