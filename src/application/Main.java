package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManager;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        // get main Stage from view
        ViewManager manager = new ViewManager();
        primaryStage = manager.getMainStage();

        primaryStage.setTitle("Penguin Space");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
