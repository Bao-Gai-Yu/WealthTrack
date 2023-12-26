package team.hiddenblue.wealthtrack.service;

import team.hiddenblue.wealthtrack.dto.ExpenseRecordResult;
import team.hiddenblue.wealthtrack.pojo.ExpensesRecord;

import java.util.List;

public interface ExpensesRecordService {
    public List<ExpensesRecord> getPagedExpenseRecord(Integer userId, Integer ledgerId, String year, String month, String date, Boolean type, Integer pageNum, Integer pageSize);

    public Integer insert(ExpensesRecord expensesRecord);

    public Object delete(Integer id, Integer userId);

    public Boolean update(ExpensesRecord expensesRecord);
}
