package tem;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * JavaFX entry point for Tem.
 */
public class Main extends Application {
    private final Tem tem = new Tem();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane anchorPane = fxmlLoader.load();
            Scene scene = new Scene(anchorPane);
            stage.setTitle("Tem");
            stage.setMinHeight(220.0);
            stage.setMinWidth(417.0);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setTem(tem);
            stage.show();
        } catch (IOException exception) {
            throw new IllegalStateException("Failed to load /view/MainWindow.fxml", exception);
        }
    }
}
