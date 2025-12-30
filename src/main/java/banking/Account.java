package banking;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Account {
    private String accountNumber;
    private double balance;
    private String status; 
    private List<String> transactionHistory; // NEW: Store history

    public Account(double initialBalance) {
        this.balance = initialBalance;
        this.status = "Unverified";
        this.accountNumber = String.format("%06d", new Random().nextInt(999999));
        this.transactionHistory = new ArrayList<>();
        addTransaction("Account opened with balance: $" + initialBalance);
    }

    public void addTransaction(String message) {
        transactionHistory.add(message);
    }

    public List<String> getTransactionHistory() {
        return transactionHistory;
    }

    public boolean deposit(double amount) {
        if (!status.equals("Verified") || amount <= 0) return false;
        balance += amount;
        addTransaction("Deposit: +$" + amount + " | Balance: $" + balance);
        return true;
    }

    public boolean withdraw(double amount) {
        if (!status.equals("Verified") || amount > balance || amount <= 0) return false;
        balance -= amount;
        addTransaction("Withdrawal: -$" + amount + " | Balance: $" + balance);
        return true;
    }

    public void forceDebit(double amount) { 
        this.balance -= amount; 
        addTransaction("Transfer Sent: -$" + amount + " | Balance: $" + balance);
    }
    
    public void forceCredit(double amount) { 
        this.balance += amount; 
        addTransaction("Transfer Received: +$" + amount + " | Balance: $" + balance);
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}