package banking;

public class Account {

    private String clientName;
    private double balance;
    private String status; // Verified, Suspended, Closed

    public Account(String clientName, double balance) {
        this.clientName = clientName;
        this.balance = balance;
        this.status = "Verified";
    }

    public String getClientName() {
        return clientName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
