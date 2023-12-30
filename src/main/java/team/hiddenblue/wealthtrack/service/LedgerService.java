package team.hiddenblue.wealthtrack.service;

import team.hiddenblue.wealthtrack.dto.LedgerDto;
import team.hiddenblue.wealthtrack.pojo.Ledger;

public interface LedgerService {
    Integer insert(LedgerDto ledger);

    Object selectLedgerByUserId(int userId);

    Boolean update(LedgerDto ledger);

    Boolean delete(int operatorId, int ledgerId);

    void share(int userId, String password);
}
