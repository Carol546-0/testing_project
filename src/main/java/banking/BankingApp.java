package banking;

import javafx.application.Application;
import javafx.stage.Stage;

public class BankingApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        LoginUI loginUI = new LoginUI();
        loginUI.show(primaryStage);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
