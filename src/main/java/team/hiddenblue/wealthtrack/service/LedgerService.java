package team.hiddenblue.wealthtrack.service;

import team.hiddenblue.wealthtrack.pojo.Ledger;

public interface LedgerService {
    Integer insert(Ledger ledger);

    Object selectLedgerByUserId(int userId);

    Boolean update(Ledger ledger);

    Boolean delete(int ownerId, int ledgerId);
}
