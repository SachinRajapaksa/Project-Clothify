package org.example.controller.front;

import org.example.Util.CrudUtil;
import org.example.db.DBConnection;
import org.example.dto.Order;
import org.example.dto.OrderDetails;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FrontController {
    private FrontController (){}
    private static FrontController instance;

    public static FrontController getInstance(){
        if(instance==null){
            return instance=new FrontController();
        }
        return instance;
    }
    public boolean addOrder(Order order) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        try {
            PreparedStatement psTm = connection.prepareStatement("INSERT INTO orders Values(?,?,?,?)");
            psTm.setString(1,order.getOrderId());
            psTm.setObject(2,order.getDate());
            psTm.setDouble(3,order.getTotal());
            psTm.setString(4,order.getPayementMethod());
            psTm.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
    public boolean addOrderDetails(OrderDetails orderDetails) throws SQLException, ClassNotFoundException {
        Connection connection = DBConnection.getInstance().getConnection();
        PreparedStatement psTm = connection.prepareStatement("INSERT INTO orderdetails values(?,?,?,?,?,?,?,?,?)");
        psTm.setInt(1,orderDetails.getNo());
        psTm.setString(2,orderDetails.getOrderId());
        psTm.setObject(3,orderDetails.getDate());
        psTm.setString(4,orderDetails.getItemCode());
        psTm.setString(5,orderDetails.getItemDesc());
        psTm.setString(6,orderDetails.getSize());
        psTm.setInt(7,orderDetails.getQty());
        psTm.setDouble(8,orderDetails.getTotal());
        psTm.setString(9,orderDetails.getPayementMethod());
        psTm.execute();
        return false;
    }
}
