package org.example.controller.front;
import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.util.Duration;
import org.example.controller.item.ItemController;
import org.example.db.DBConnection;
import org.example.dto.Item;
import org.example.dto.Linetbl;
import org.example.dto.Order;
import org.example.dto.OrderDetails;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

public class FrontSystemFormController implements Initializable {
    public Label lblLoginUser;
    public Label lblDate;
    public Label lblInvoice;
    public Label lblTotal;
    public TextField txtItemCode;
    public TextField txtItemDesc;
    public TextField txtSize;
    public TextField txtStock;
    public TextField txtQTY;
    public TextField txtUnitPrice;
    public TextField txtLineTotal;
    public ComboBox cmbPayType;
    public TextField txtTotal;
    public Label lblBillVal;
    public Label lblReciveVal;
    public Label lblBalanceVal;
    public TableView tblOrder;
    public TableColumn colNo;
    public TableColumn colItemDesc;
    public TableColumn colSize;
    public TableColumn colQty;
    public TableColumn colTotal;
    public Label lblTime;
    public TableColumn colItemCode;
    public JFXButton btnEnd;
    public JFXButton btnSave;
    public static String username;

    int no = 0;

    double total = 0.00;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lblLoginUser.setText(username);
        loadDateAndTime();
        colNo.setCellValueFactory(new PropertyValueFactory<>("no"));
        colItemCode.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colItemDesc.setCellValueFactory(new PropertyValueFactory<>("itemDescription"));
        colSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("lineTotal"));
        loadPayMenu();
        txtItemCode.requestFocus();



    }

    private void genarateOrderId() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM orders");
            Integer count = 0;
            while (resultSet.next()) {
                count = resultSet.getInt(1);
            }
            if (count == 0) {
                lblInvoice.setText("IN010000");
            }
            String lastOrderId = "";
            ResultSet resultSet1 = connection.createStatement().executeQuery("SELECT orderId\n" +
                    "FROM orders\n" +
                    "ORDER BY orderID DESC\n" +
                    "LIMIT 1;");
            if (resultSet1.next()) {
                lastOrderId = resultSet1.getString(1);
                Pattern pattern = Pattern.compile("[A-Za-z](\\d+)");
                Matcher matcher = pattern.matcher(lastOrderId);
                if (matcher.find()) {
                    int num = parseInt(matcher.group(1));
                    num++;
                    lblInvoice.setText(String.format("IN0%03d", num));
                } else {
                    new Alert(Alert.AlertType.WARNING, "Invoice Number Error").show();
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    private void loadDateAndTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        lblDate.setText(sdf.format(date));

        Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime time = LocalTime.now();
            lblTime.setText(
                    time.getHour() + ":" + time.getMinute() + ":" + time.getSecond()
            );

        }),
                new KeyFrame(Duration.seconds(Double.parseDouble("1")))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    public void btnSearchOnAction(ActionEvent event) {
    }

    public void btnItemOnAction(ActionEvent event) {

    }

    public void btnSaveOnAction() throws ClassNotFoundException {
        if(tblOrder.getItems().isEmpty()){
            new Alert(Alert.AlertType.ERROR,"No Grid Found").show();
        }
        else
        try {
            genarateOrderId();
            String OrderId = lblInvoice.getText();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            Date orderDate = format.parse(lblDate.getText());

            ArrayList<OrderDetails> OrderDetailslist = new ArrayList<>();

            for (Linetbl linetbl : list) {

                int no = linetbl.getNo();
                String itemCode = linetbl.getItemCode();
                String desc = linetbl.getItemDescription();
                String size = linetbl.getSize();
                int qty = linetbl.getQty();
                double total = Double.parseDouble(lblTotal.getText());
                String payType = cmbPayType.getValue().toString();

                OrderDetailslist.add(new OrderDetails(no, OrderId, orderDate, itemCode, desc, size, qty, total, payType));
                FrontController.getInstance().addOrderDetails(new OrderDetails(no, OrderId, orderDate, itemCode, desc, size, qty, total, payType));
            }
            Order order = new Order(OrderId, orderDate, Double.parseDouble(lblTotal.getText()), cmbPayType.getValue().toString(), OrderDetailslist);
            boolean b = FrontController.getInstance().addOrder(order);

            if (!b && !tblOrder.getItems().isEmpty()) {

                new Alert(Alert.AlertType.CONFIRMATION, "Added").show();


                clearAll();
            } else new Alert(Alert.AlertType.ERROR, "Not Added").show();


        } catch (ParseException | SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void btnPrintOnAction() {
    }

    public void btnCancelOnAction() {
        cleartxt();
        clearAll();
    }

    public void btnEndBillOnAction() {
        btnSave.requestFocus();

    }

    public void txtItemCodeOnAction() {
        Item item = ItemController.getInstance().searchItem(txtItemCode.getText());
        txtItemDesc.setText(item.getDescription());
        txtSize.setText(item.getSize());
        txtStock.setText(item.getQty().toString());
        txtUnitPrice.setText(item.getUnitPrice().toString());
        txtQTY.requestFocus();
    }

    public void txtQTYOnAction() {
        int a = parseInt(txtQTY.getText());
        double b = Double.parseDouble(txtUnitPrice.getText());
        double tot = a * b;
        txtLineTotal.setText(String.valueOf(tot));
        txtLineTotal.requestFocus();
    }

    public void txtTotalLineOnAction() {
        if (parseInt(txtQTY.getText()) <= parseInt(txtStock.getText())) {
            if(tblOrder.getItems().isEmpty()){
                no=1;
            }else {
            no= (int) colNo.getCellData(Integer.parseInt(String.valueOf(tblOrder.getItems().size()-1)))+1;}

            String itemCode = txtItemCode.getText().toUpperCase();
            String desc = txtItemDesc.getText();
            String size = txtSize.getText();
            int qty = parseInt(txtQTY.getText());
            double tot = Double.parseDouble(txtLineTotal.getText());
            Linetbl linetbl = new Linetbl(no, itemCode, desc, size, qty, tot);
            list.add(linetbl);
            tblOrder.setItems(list);



            total = total + tot;
            String s = total + "0";
            lblTotal.setText(s);
            txtTotal.setText(s);
            lblBillVal.setText(s);
            cleartxt();
            txtItemCode.requestFocus();

        } else new Alert(Alert.AlertType.ERROR, "Out Of Stock").show();
    }

    private void cleartxt() {
        txtItemCode.setText(null);
        txtItemDesc.setText(null);
        txtSize.setText(null);
        txtStock.setText(null);
        txtQTY.setText(null);
        txtUnitPrice.setText(null);
        txtLineTotal.setText(null);
        lblInvoice.setText(null);
    }

    private void clearAll() {
        tblOrder.getItems().clear();
        lblTotal.setText("0.00");
        txtTotal.setText("0.00");
        no = 0;
        total = 0.00;
        lblInvoice.setText(null);
        lblBillVal.setText("0.00");
        lblReciveVal.setText("0.00");
        lblBalanceVal.setText("0.00");
    }

    private void loadPayMenu() {
        ObservableList<Object> payMenu = FXCollections.observableArrayList();
        payMenu.add("Card");
        payMenu.add("Cash");
        payMenu.add("Online");
        payMenu.add("Credit");
        cmbPayType.setItems(payMenu);

    }

    public void txtTotalOnAction() throws ClassNotFoundException {
        cmbPayType.setValue("Cash");

        lblReciveVal.setText(txtTotal.getText());
        double a = Double.parseDouble(lblBillVal.getText());
        double b = Double.parseDouble(lblReciveVal.getText());
        double c = b - a;
        lblBalanceVal.setText(c + "0");
        if (c >= 0) {
            btnEnd.requestFocus();
        } else new Alert(Alert.AlertType.ERROR, "Received Balance Insufficient ").show();


    }


    public void btnHomeOnAction() {
        cleartxt();
        clearAll();
        genarateOrderId();

    }

    ObservableList<Linetbl> list = FXCollections.observableArrayList();

    public void btnDeleteOnAction() {
        int newno=1;
        total=0.00;
        TableView.TableViewSelectionModel<Linetbl> tableViewSelectionModel = tblOrder.getSelectionModel();
        ObservableList<Integer> listS = tableViewSelectionModel.getSelectedIndices();
        Integer[] selectIndices = new Integer[listS.size()];
        selectIndices = listS.toArray(selectIndices);

        for (int i = selectIndices.length -1; i >= 0; i--) {
            tableViewSelectionModel.clearSelection(selectIndices[i].intValue());
            tblOrder.getItems().remove(selectIndices[i].intValue());




        }
        for(int y=tblOrder.getItems().size()-1;y>-1;y--){
            Linetbl linetbl = new Linetbl(newno,colItemCode.getCellData(y).toString(),colItemDesc.getCellData(y).toString(),colSize.getCellData(y).toString(),Integer.parseInt(colQty.getCellData(y).toString()),Double.parseDouble(colTotal.getCellData(y).toString()));
            tblOrder.getItems().remove(y);

            list.add(linetbl);
            newno++;
            updatePrice();
        }
        updatePrice();

    }

    private void updatePrice() {
        if(tblOrder.getItems().isEmpty()){
            btnHomeOnAction();
        }

        else{
        Double newTot=0.00;
        for (int x=0;x<tblOrder.getItems().size(); x++) {
            double d=Double.parseDouble(colTotal.getCellObservableValue(x).getValue().toString());
            newTot=d+newTot;
            total=newTot;
        }

        }
        lblTotal.setText(String.valueOf(total));
        txtTotal.setText(String.valueOf(total));
        lblBillVal.setText(String.valueOf(total));

    }


}

