package javaFXSettings;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import static globalVariables.GlobalVariables.countCores;
import static globalVariables.GlobalVariables.accepterStatus;
public class Main extends Application {

    public static void main(String[] args) {
        countCores = Runtime.getRuntime().availableProcessors();
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/sample.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 316, 381);
        stage.getIcons().add(new Image("icon.jpg"));
        stage.setTitle("Accepter");
        stage.setScene(scene);
        stage.show();
        stage.setResizable(false);
        stage.setOnCloseRequest(e -> {
            try {
                accepterStatus = false;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
    }
}
