package application;

import javafx.application.Application;
import javafx.stage.Stage;
import view.ViewManeger;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        // get main Stage from view
        ViewManeger maneger = new ViewManeger();
        primaryStage = maneger.getMainStage();

        primaryStage.setTitle("-PA4-");
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
