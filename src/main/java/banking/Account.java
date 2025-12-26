package banking;

import java.util.Random;

public class Account {
    private String accountNumber;
    private double balance;
    private String status; // "Unverified", "Verified", "Suspended", "Closed"

    public Account(double initialBalance) {
        this.balance = initialBalance;
        this.status = "Unverified"; // Default state
        this.accountNumber = String.format("%06d", new Random().nextInt(999999));
    }

    public boolean deposit(double amount) {
        if (!status.equals("Verified") || amount <= 0) return false;
        balance += amount;
        return true;
    }

    public boolean withdraw(double amount) {
        if (!status.equals("Verified") || amount > balance || amount <= 0) return false;
        balance -= amount;
        return true;
    }

    // Helper for Admin-approved transfers
    public void forceDebit(double amount) { this.balance -= amount; }
    public void forceCredit(double amount) { this.balance += amount; }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}