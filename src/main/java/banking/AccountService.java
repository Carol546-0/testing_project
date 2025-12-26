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
        if (approve) tr.getFrom().withdraw(tr.getAmount());
        tr.setStatus(approve ? TransferStatus.APPROVED : TransferStatus.DECLINED);
    }
}
