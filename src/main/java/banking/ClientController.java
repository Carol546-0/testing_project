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
            client.getAccount().deposit(Double.parseDouble(amountInput.getText()));
            show(); // Refresh
        });

        Button withBtn = new Button("Withdraw");
        withBtn.setOnAction(e -> {
            client.getAccount().withdraw(Double.parseDouble(amountInput.getText()));
            show(); // Refresh
        });

        Button transBtn = new Button("Request Transfer");
        transBtn.setOnAction(e -> openTransferDialog());

        Button logoutBtn = new Button("Logout");
        logoutBtn.setOnAction(e -> new LoginUI().show(stage));

        root.getChildren().addAll(infoLabel, statusLabel, balanceLabel, amountInput, depBtn, withBtn, transBtn, logoutBtn);
        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Client Dashboard");
    }

    private void openTransferDialog() {
        if (!client.getAccount().getStatus().equals("Verified")) {
            new Alert(Alert.AlertType.ERROR, "You must be Verified to transfer!").showAndWait();
            return;
        }

        Dialog<TransferRequest> dialog = new Dialog<>();
        dialog.setTitle("New Transfer");
        ButtonType sendBtn = new ButtonType("Send", ButtonBar.ButtonData.OK_DONE);
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
                User recipient = FakeDatabase.findUser(toUser.getText(), toAcc.getText());
                if (recipient != null && recipient.getAccount().getStatus().equals("Verified")) {
                    return new TransferRequest(client.getAccount(), recipient.getAccount(), Integer.parseInt(amount.getText()));
                } else {
                    new Alert(Alert.AlertType.ERROR, "Recipient not found or not Verified!").showAndWait();
                }
            }
            return null;
        });

        Optional<TransferRequest> result = dialog.showAndWait();
        result.ifPresent(req -> service.addTransfer(req));
    }
}