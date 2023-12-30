package team.hiddenblue.wealthtrack.service;

import team.hiddenblue.wealthtrack.pojo.ExpensesRecord;

import java.util.Date;
import java.util.Map;

public interface ExpensesRecordService {
    public Map<String, Object> getPagedExpenseRecord(Integer userId, Integer ledgerId, String year, String month, String date, Boolean type, Integer pageNum, Integer pageSize);

    public Integer insert(int userId, int ledgerId, Double value, boolean type, String kind, String remark, Date dateRaw);

    public Object delete(Integer id, Integer userId);

    public Boolean update(ExpensesRecord expensesRecord);

    public Map<String, Object> getSelecetdExpensesRecord(int userId, String kind, String remark, Integer ledgerId, String year, String month, String date, Boolean type, Integer pageNum, Integer pageSize);
}
