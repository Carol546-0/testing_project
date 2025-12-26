package banking;

public class ClientController {

    private final AccountService service;
    private final Account account;

    public ClientController(AccountService service, Account account) {
        this.service = service;
        this.account = account;
    }

    public boolean deposit(double amount) {
        return service.processDeposit(account, amount);
    }

    public boolean withdraw(double amount) {
        return service.processWithdraw(account, amount);
    }

    public Account getAccount() {
        return account;
    }
}
