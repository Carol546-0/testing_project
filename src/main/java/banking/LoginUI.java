package banking;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginUI {
    // FIXED: Make this static so it persists after logout/login
    private static AccountService service = new AccountService();

    public void show(Stage stage) {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");

        Button loginBtn = new Button("Login");
        Label msg = new Label();

        loginBtn.setOnAction(e -> {
            String user = usernameField.getText();
            String pass = passwordField.getText();
            User loggedIn = FakeDatabase.users.stream()
                .filter(u -> u.getUsername().equals(user) && u.getPassword().equals(pass))
                .findFirst().orElse(null);

            if (loggedIn instanceof Admin) {
                new AdminControllerGUI(service, stage).show();
            } else if (loggedIn instanceof Client client) {
                new ClientController(service, client, stage).show();
            } else {
                msg.setText("Invalid Login");
            }
        });

        root.getChildren().addAll(new Label("Banking Login"), usernameField, passwordField, loginBtn, msg);
        stage.setScene(new Scene(root, 300, 250));
        stage.setTitle("Login");
        stage.show();
    }
}