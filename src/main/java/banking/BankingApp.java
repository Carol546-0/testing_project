package banking;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class BankingApp extends Application {

    private Label balanceLabel;
    private Label statusLabel;

    @Override
    public void start(Stage stage) {

        Account account = new Account("Alice", 1000);
        AccountService service = new AccountService();
        ClientController controller = new ClientController(service, account);

        Label nameLabel = new Label("Client: " + account.getClientName());
        balanceLabel = new Label("Balance: " + account.getBalance());
        statusLabel = new Label("Status: " + account.getStatus());

        TextField amountField = new TextField();
        amountField.setPromptText("Enter amount");

        Button depositBtn = new Button("Deposit");
        Button withdrawBtn = new Button("Withdraw");

        Button suspendBtn = new Button("Suspend (Admin)");
        Button verifyBtn = new Button("Verify (Admin)");
        Button closeBtn = new Button("Close (Admin)");

        depositBtn.setOnAction(e -> handleDeposit(controller, amountField));
        withdrawBtn.setOnAction(e -> handleWithdraw(controller, amountField));

        suspendBtn.setOnAction(e -> {
            service.suspend(account);
            updateUI(account);
        });

        verifyBtn.setOnAction(e -> {
            service.verify(account);
            updateUI(account);
        });

        closeBtn.setOnAction(e -> {
            service.close(account);
            updateUI(account);
        });

        VBox root = new VBox(10,
                nameLabel,
                balanceLabel,
                statusLabel,
                amountField,
                depositBtn,
                withdrawBtn,
                new Separator(),
                suspendBtn,
                verifyBtn,
                closeBtn
        );

        root.setPadding(new Insets(20));

        stage.setScene(new Scene(root, 350, 400));
        stage.setTitle("Banking System");
        stage.show();
    }

    private void handleDeposit(ClientController controller, TextField field) {
        try {
            double amount = Double.parseDouble(field.getText());
            boolean ok = controller.deposit(amount);
            if (!ok) showError("Deposit not allowed");
            updateUI(controller.getAccount());
            field.clear();
        } catch (NumberFormatException ex) {
            showError("Enter a valid number");
        }
    }

    private void handleWithdraw(ClientController controller, TextField field) {
        try {
            double amount = Double.parseDouble(field.getText());
            boolean ok = controller.withdraw(amount);
            if (!ok) showError("Withdraw not allowed");
            updateUI(controller.getAccount());
            field.clear();
        } catch (NumberFormatException ex) {
            showError("Enter a valid number");
        }
    }

    private void updateUI(Account account) {
        balanceLabel.setText("Balance: " + account.getBalance());
        statusLabel.setText("Status: " + account.getStatus());
    }

    private void showError(String msg) {
        Alert alert = new Alert(Alert.AlertType.ERROR, msg);
        alert.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
