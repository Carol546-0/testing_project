package banking;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.Optional;

public class ClientController {
    private AccountService service;
    private Client client;
    private Stage stage;

    public ClientController(AccountService service, Client client, Stage stage) {
        this.service = service;
        this.client = client;
        this.stage = stage;
    }

    public void show() {
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        Label infoLabel = new Label("User: " + client.getUsername() + " | Acc: " + client.getAccount().getAccountNumber());
        Label balanceLabel = new Label("Balance: $" + client.getAccount().getBalance());
        Label statusLabel = new Label("Status: " + client.getAccount().getStatus());
        
        TextField amountInput = new TextField();
        amountInput.setPromptText("Amount for Deposit/Withdraw");

        Button depBtn = new Button("Deposit");
        depBtn.setOnAction(e -> {
            if (client.getAccount().deposit(Double.parseDouble(amountInput.getText()))) {
                show(); // Refresh UI on success
            } else {
                new Alert(Alert.AlertType.ERROR, "Deposit failed. Ensure you are Verified.").showAndWait();
            }
        });

        Button withBtn = new Button("Withdraw");
        withBtn.setOnAction(e -> {
            if (client.getAccount().withdraw(Double.parseDouble(amountInput.getText()))) {
                show(); // Refresh UI on success
            } else {
                new Alert(Alert.AlertType.ERROR, "Withdraw failed. Check balance and status.").showAndWait();
            }
        });

        Button transBtn = new Button("Request Transfer");
        transBtn.setOnAction(e -> openTransferDialog());

        // New Button: View Statement
        Button statementBtn = new Button("View Statement");
        statementBtn.setOnAction(e -> showStatementWindow());

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> new LoginUI().show(stage));

        root.getChildren().addAll(infoLabel, statusLabel, balanceLabel, amountInput, depBtn, withBtn, transBtn, statementBtn, logoutBtn);
        stage.setScene(new Scene(root, 400, 500));
        stage.setTitle("Client Dashboard");
        stage.show();
    }

    private void showStatementWindow() {
        Stage subStage = new Stage();
        VBox root = new VBox(10);
        root.setPadding(new Insets(15));

        Label title = new Label("Transaction History for " + client.getAccount().getAccountNumber());
        title.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");

        ListView<String> listView = new ListView<>();
        // Fetches history from the updated Account class
        listView.getItems().addAll(client.getAccount().getTransactionHistory());

        root.getChildren().addAll(title, listView);
        subStage.setScene(new Scene(root, 450, 400));
        subStage.setTitle("Account Statement");
        subStage.show();
    }

    private void openTransferDialog() {
        if (!client.getAccount().getStatus().equals("Verified")) {
            new Alert(Alert.AlertType.ERROR, "You must be Verified to transfer!").showAndWait();
            return;
        }

        Dialog<TransferRequest> dialog = new Dialog<>();
        dialog.setTitle("New Transfer Request");
        ButtonType sendBtn = new ButtonType("Send Request", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(sendBtn, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10); grid.setVgap(10);
        TextField toUser = new TextField(); 
        TextField toAcc = new TextField();
        TextField amount = new TextField();

        grid.add(new Label("Recipient Username:"), 0, 0); grid.add(toUser, 1, 0);
        grid.add(new Label("Recipient Account #:"), 0, 1); grid.add(toAcc, 1, 1);
        grid.add(new Label("Amount:"), 0, 2); grid.add(amount, 1, 2);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(btn -> {
            if (btn == sendBtn) {
                try {
                    int amt = Integer.parseInt(amount.getText());
                    
                    // Added: Check balance before allowing the request
                    if (client.getAccount().getBalance() < amt) {
                        new Alert(Alert.AlertType.ERROR, "Insufficient funds to request this transfer!").showAndWait();
                        return null;
                    }

                    User recipient = FakeDatabase.findUser(toUser.getText(), toAcc.getText());
                    if (recipient != null && recipient.getAccount().getStatus().equals("Verified")) {
                        return new TransferRequest(client.getAccount(), recipient.getAccount(), amt);
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Recipient not found or not Verified!").showAndWait();
                    }
                } catch (NumberFormatException ex) {
                    new Alert(Alert.AlertType.ERROR, "Invalid amount format.").showAndWait();
                }
            }
            return null;
        });

        Optional<TransferRequest> result = dialog.showAndWait();
        result.ifPresent(req -> {
            service.addTransfer(req);
            new Alert(Alert.AlertType.INFORMATION, "Request submitted to Admin for approval.").showAndWait();
        });
    }
}