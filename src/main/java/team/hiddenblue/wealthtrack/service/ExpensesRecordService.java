package team.hiddenblue.wealthtrack.service;

import java.util.Map;
import java.util.Objects;

public interface ExpensesRecordService {
    public Map<String, Object> getPagedExpenseRecord(Integer userId, Integer ledgerId, String year, String month, String date, Boolean type, Integer pageNum, Integer pageSize);
}
