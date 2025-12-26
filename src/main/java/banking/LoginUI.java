package banking;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginUI {

    private AccountService service = new AccountService();

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
            String username = usernameField.getText();
            String password = passwordField.getText();
            User loggedIn = null;

            for (User u : FakeDatabase.users) {
                if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                    loggedIn = u;
                    break;
                }
            }

            if (loggedIn == null) {
                msg.setText("Invalid credentials");
            } else if (loggedIn instanceof Admin admin) {
                AdminControllerGUI adminGUI = new AdminControllerGUI(service, stage);
                adminGUI.show();
            } else if (loggedIn instanceof Client client) {
                ClientController clientGUI = new ClientController(service, client.getAccount(), stage);
                clientGUI.show();
            }
        });

        root.getChildren().addAll(new Label("Login"), usernameField, passwordField, loginBtn, msg);

        stage.setScene(new Scene(root, 300, 200));
        stage.setTitle("Banking App Login");
        stage.show();
    }
}
