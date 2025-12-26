package banking;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminControllerGUI {

    private AccountService service;
    private Stage stage;

    public AdminControllerGUI(AccountService service, Stage stage) {
        this.service = service;
        this.stage = stage;
    }

    public void show() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label title = new Label("Admin Panel");

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> new LoginUI().show(stage));

        root.getChildren().addAll(title, logoutBtn);

        stage.setScene(new Scene(root, 400, 300));
        stage.setTitle("Admin Panel");
        stage.show();
    }
}
