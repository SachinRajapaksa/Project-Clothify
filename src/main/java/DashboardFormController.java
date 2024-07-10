
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.Getter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


@Getter

public class DashboardFormController implements Initializable {
    public static String username=" ";
    public static String acctype=" ";


    public Label txtUsername;
    public Label txtUserType;

    public void btnEmpOnAction() throws Exception {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("view/add-emp-form.fxml"))));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("img/emp1.png")));
        stage.setTitle("Manage Employee");
        stage.show();

    }

    public void btnItemOnAction() throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("view/add-item-form.fxml"))));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("img/item.png")));
        stage.setTitle("Manage Item");
        stage.show();
    }

    public void btnSupplierOnAction() throws IOException {
        Stage stage = new Stage();
        stage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("view/add-supplier-form.fxml"))));
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("img/supplier.png")));
        stage.setTitle("Manage Supplier");
        stage.show();
    }

    public void btnFrontSystemOnAction() {


    }


    public void btnReportOnActon() {
        setLabels();
    }


    public  void setLabels (){
        txtUsername.setText(username);
        txtUserType.setText(acctype);


    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setLabels();

    }

    public void btnReload() {
      setLabels();
    }



}
