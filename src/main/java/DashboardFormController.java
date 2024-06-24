
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;

import java.io.IOException;
@Getter

public class DashboardFormController  {

    public void btnEmpOnAction() throws Exception {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/add-emp-form.fxml"))));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("img/emp.png")));
        stage.setTitle("Manage Employee");
        stage.show();


    }

    public void btnFrontSystemOnAction(ActionEvent actionEvent) {
    }

    public void btnItemOnAction() throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/add-item-form.fxml"))));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("img/item.png")));
        stage.setTitle("Manage Item");
        stage.show();


    }

    public void btnSupplierOnAction() throws IOException {
        Stage stage1 = new Stage();
        stage1.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/add-supplier-form.fxml"))));
        stage1.getIcons().add(new Image(Main.class.getResourceAsStream("img/supplier.png")));
        stage1.setTitle("Manage Supplier");
        stage1.show();
        if (stage1.isShowing()) {
            //Stage stage = new Stage();
            //Main.closeDash(stage);

        }



    }


    public void btnReportOnActon(ActionEvent actionEvent) {
    }


}
