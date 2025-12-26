package banking;

public class TransferRequest {
    private Account from;
    private Account to;
    private int amount;
    private TransferStatus status;

    public TransferRequest(Account from, Account to, int amount) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.status = TransferStatus.PENDING;
    }

    public Account getFrom() { return from; }
    public Account getTo() { return to; }
    public int getAmount() { return amount; }

    public TransferStatus getStatus() { return status; }
    public void setStatus(TransferStatus status) { this.status = status; }
}
