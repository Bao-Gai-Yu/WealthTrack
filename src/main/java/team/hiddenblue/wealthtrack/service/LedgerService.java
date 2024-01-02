package team.hiddenblue.wealthtrack.service;

import team.hiddenblue.wealthtrack.dto.LedgerDto;
import team.hiddenblue.wealthtrack.dto.LedgerUsersResult;
import team.hiddenblue.wealthtrack.pojo.Ledger;
import team.hiddenblue.wealthtrack.pojo.LedgerPermission;
import team.hiddenblue.wealthtrack.pojo.User;
import team.hiddenblue.wealthtrack.pojo.UserInfo;

import java.util.List;

public interface LedgerService {
    Integer insert(LedgerDto ledger);
    void createDefault(User user);

    Object selectLedgerByUserId(int userId);

    Boolean update(LedgerDto ledger);

    Boolean delete(int operatorId, int ledgerId);

    void share(int userId, String password);

    List<LedgerUsersResult> getAllUsersByLedgerId(int ledgerId);

    void createDefault(User user);
}
