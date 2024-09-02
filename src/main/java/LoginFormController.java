import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.controller.front.FrontSystemFormController;
import org.example.controller.login.LoginController;
import org.example.dto.User;
import java.io.IOException;

import java.util.Objects;

public class LoginFormController {
    public TextField txtEmail;
    public TextField txtPassword;

    public void btnLoginOnAction(ActionEvent event) throws IOException {
        User user = LoginController.getInstance().findUser(txtEmail.getText(), txtPassword.getText());

        if (user == null) {
            new Alert(Alert.AlertType.ERROR, "Username Or Password Incorrect").show();

        }

        if (Objects.equals(user.getEMail(), txtEmail.getText()) && Objects.equals(user.getPassword(), txtPassword.getText())) {
            Parent root = FXMLLoader.load(Main.class.getResource("view/dashboard-form.fxml"));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            DashboardFormController.acctype=user.getAccType();
            DashboardFormController.username=user.getEMail();
            FrontSystemFormController.username=user.getFirstName();
        }

    }

    public void btnCancelOnAction() {
        System.exit(0);
    }

}
