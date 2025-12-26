package banking;

public class AccountService {

    public boolean processDeposit(Account account, double amount) {
        if (account.getStatus().equals("Closed") || account.getStatus().equals("Suspended"))
            return false;

        if (amount <= 0) return false;

        account.setBalance(account.getBalance() + amount);
        return true;
    }

    public boolean processWithdraw(Account account, double amount) {
        if (account.getStatus().equals("Closed") || account.getStatus().equals("Suspended"))
            return false;

        if (amount <= 0 || amount > account.getBalance())
            return false;

        account.setBalance(account.getBalance() - amount);
        return true;
    }

    // Admin actions
    public void suspend(Account account) {
        account.setStatus("Suspended");
    }

    public void verify(Account account) {
        account.setStatus("Verified");
    }

    public void close(Account account) {
        account.setStatus("Closed");
    }
}
