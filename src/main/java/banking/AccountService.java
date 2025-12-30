package banking;

import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private List<TransferRequest> transfers = new ArrayList<>();

    public void addTransfer(TransferRequest tr) {
        transfers.add(tr);
    }

    public List<TransferRequest> getAllTransfers() {
        return transfers;
    }

    public void processTransfer(TransferRequest tr, boolean approve) {
        if (approve && tr.getStatus() == TransferStatus.PENDING) {
            if (tr.getFrom().getBalance() >= tr.getAmount()) {
                // Record transfer details in history
                tr.getFrom().forceDebit(tr.getAmount());
                tr.getTo().forceCredit(tr.getAmount());
                
                tr.setStatus(TransferStatus.APPROVED);
            } else {
                tr.setStatus(TransferStatus.DECLINED);
            }
        } else {
            tr.setStatus(TransferStatus.DECLINED);
        }
    }
}