package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import model.PersonalException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws PersonalException {
        primaryStage.setTitle("Personalbuero Datenbank Verwaltung");
        RootBorderPane rootBorderPane = new RootBorderPane();
        Scene scene = new Scene(rootBorderPane, 600,250);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void showAlert(Alert.AlertType alertType, String message){
        Alert alert = new Alert(alertType, message);
        alert.setResizable(true);
        alert.setHeaderText(null);
        alert.setTitle("Attention");
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
