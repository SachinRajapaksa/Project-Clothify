import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application{
    public static void main(String[] args){
        launch();


    }
    @Override
    public void start(Stage Pstage) throws Exception {
        Pstage.setScene(new Scene(FXMLLoader.load(Main.class.getResource("view/login-form.fxml"))));
        Pstage.getIcons().add(new Image(Main.class.getResourceAsStream("img/icon.png")));
        Pstage.setTitle("Clothify");
        Pstage.initStyle(StageStyle.DECORATED);
        Pstage.show();
        Pstage.setOnShown(e ->Pstage.close());


    }
}

