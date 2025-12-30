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

        // --- SECTION 1: USER MANAGEMENT ---
        Label userLabel = new Label("Client Management (Status & History)");
        userLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        VBox userBox = new VBox(10);

        for (User u : FakeDatabase.users) {
            if (u instanceof Client) {
                HBox row = new HBox(10);
                Label details = new Label(u.getUsername() + " [" + u.getAccount().getStatus() + "]");
                
                Button vBtn = new Button("Verify");
                vBtn.setOnAction(e -> { u.getAccount().setStatus("Verified"); show(); });

                Button sBtn = new Button("Suspend");
                sBtn.setOnAction(e -> { u.getAccount().setStatus("Suspended"); show(); });

                Button cBtn = new Button("Close");
                cBtn.setStyle("-fx-background-color: #ff4444; -fx-text-fill: white;");
                cBtn.setOnAction(e -> { u.getAccount().setStatus("Closed"); show(); });

                Button viewBtn = new Button("View Statement");
                viewBtn.setOnAction(e -> showClientStatement(u));

                row.getChildren().addAll(details, vBtn, sBtn, cBtn, viewBtn);
                userBox.getChildren().add(row);
            }
        }

        // --- SECTION 2: TRANSFER APPROVALS ---
        Label transLabel = new Label("Pending Transfer Requests");
        transLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        VBox transBox = new VBox(8);
        
        boolean hasPending = false;
        for (TransferRequest tr : service.getAllTransfers()) {
            if (tr.getStatus() == TransferStatus.PENDING) {
                hasPending = true;
                HBox row = new HBox(10);
                Label details = new Label("From: " + tr.getFrom().getAccountNumber() + " | Amt: $" + tr.getAmount());
                
                Button appBtn = new Button("Approve");
                appBtn.setOnAction(e -> { 
                    service.processTransfer(tr, true); 
                    show(); 
                });

                Button decBtn = new Button("Decline");
                decBtn.setOnAction(e -> {
                    service.processTransfer(tr, false);
                    show();
                });

                row.getChildren().addAll(details, appBtn, decBtn);
                transBox.getChildren().add(row);
            }
        }

        if (!hasPending) {
            transBox.getChildren().add(new Label("No pending transfers found."));
        }

        // --- LOGOUT ---
        Button logout = new Button("Logout");
        logout.setOnAction(e -> new LoginUI().show(stage));

        root.getChildren().addAll(userLabel, userBox, new Separator(), transLabel, transBox, new Separator(), logout);
        
        stage.setScene(new Scene(root, 650, 600));
        stage.setTitle("Admin Control Panel");
        stage.show();
    }

    /**
     * Opens a new window to view the specific transaction history of a client.
     */
    private void showClientStatement(User u) {
        Stage subStage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label title = new Label("Audit Trail: " + u.getUsername());
        title.setStyle("-fx-font-weight: bold;");

        ListView<String> listView = new ListView<>();
        // Make sure your Account.java has getTransactionHistory() implemented
        listView.getItems().addAll(u.getAccount().getTransactionHistory());

        root.getChildren().addAll(title, listView);
        subStage.setScene(new Scene(root, 400, 400));
        subStage.setTitle("Statement Review");
        subStage.show();
    }
}