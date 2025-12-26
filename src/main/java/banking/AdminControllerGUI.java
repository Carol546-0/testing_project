package banking;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
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
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));

        // --- Client Status Section ---
        Label clientLabel = new Label("Client Management:");
        VBox clientBox = new VBox(5);
        for (User u : FakeDatabase.users) {
            if (u instanceof Client) {
                HBox row = new HBox(10);
                Label name = new Label(u.getUsername() + " (" + u.getAccount().getStatus() + ")");
                Button vBtn = new Button("Verify");
                vBtn.setOnAction(e -> { u.getAccount().setStatus("Verified"); show(); });
                Button sBtn = new Button("Suspend");
                sBtn.setOnAction(e -> { u.getAccount().setStatus("Suspended"); show(); });
                row.getChildren().addAll(name, vBtn, sBtn);
                clientBox.getChildren().add(row);
            }
        }

        // --- Transfer Approval Section ---
        Label transLabel = new Label("Pending Transfers:");
        VBox transBox = new VBox(5);
        for (TransferRequest tr : service.getAllTransfers()) {
            if (tr.getStatus() == TransferStatus.PENDING) {
                HBox row = new HBox(10);
                Label details = new Label("Amt: $" + tr.getAmount());
                Button appBtn = new Button("Approve");
                appBtn.setOnAction(e -> { service.processTransfer(tr, true); show(); });
                row.getChildren().addAll(details, appBtn);
                transBox.getChildren().add(row);
            }
        }

        Button logout = new Button("Logout");
        logout.setOnAction(e -> new LoginUI().show(stage));

        root.getChildren().addAll(clientLabel, clientBox, new Separator(), transLabel, transBox, logout);
        stage.setScene(new Scene(root, 500, 500));
        stage.setTitle("Admin Control Panel");
    }
}