package team.hiddenblue.wealthtrack.service.impl;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.hiddenblue.wealthtrack.constant.ResponseCode;
import team.hiddenblue.wealthtrack.dto.ExpenseRecordResult;
import team.hiddenblue.wealthtrack.pojo.ExpensesRecord;
import team.hiddenblue.wealthtrack.pojo.LedgerPermission;
import team.hiddenblue.wealthtrack.mapper.ExpensesRecordMapper;
import team.hiddenblue.wealthtrack.mapper.LedgerPermissionMapper;
import team.hiddenblue.wealthtrack.service.ExpensesRecordService;
import team.hiddenblue.wealthtrack.util.TimeUtil;

import java.text.ParseException;
import java.util.*;

@Service
public class ExpensesRecordServiceImpl implements ExpensesRecordService {
    @Autowired
    ExpensesRecordMapper expensesRecordMapper;
    @Autowired
    LedgerPermissionMapper ledgerPermissionMapper;

    @Override
    public List<ExpensesRecord> getPagedExpenseRecord(Integer userId, Integer ledgerId, String year, String month, String date, Boolean type, Integer pageNum, Integer pageSize) {
        //查询开始的时间
        Date startTime = null;
        //查询结束的时间
        Date endTime = null;
        try {

            if (!date.equals("")) {//如果提供了具体日期
                startTime = TimeUtil.tranStringToDate(date);
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(startTime);
                endCalendar.add(Calendar.DAY_OF_YEAR, 1);
                endTime = endCalendar.getTime();
            } else if (!month.equals("")) {//如果只提供了某年某月，转换时间为某个月起止
                startTime = TimeUtil.tranStringToDate(month + "-01");
                endTime = TimeUtil.tranStringToDate(month + "-01");
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(endTime);
                endCalendar.add(Calendar.MONTH, 1);
                endTime = endCalendar.getTime();
            } else if (!year.equals("")) {//如果只提供了年份，转换时间为某年起止。
                startTime = TimeUtil.tranStringToDate(year + "-01-01");
                endTime = TimeUtil.tranStringToDate(year + "-01-01");
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(endTime);
                endCalendar.add(Calendar.YEAR, 1);
                endTime = endCalendar.getTime();
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }

        System.out.println(startTime);
        System.out.println(endTime);

        //校验是否具有操作当前账本的权限
        LedgerPermission ledgerPermission = ledgerPermissionMapper.getOne(userId, ledgerId);
        if (ledgerPermission == null) {
            return null;
        }

        int offset = (pageNum - 1) * pageSize;
        RowBounds rowBounds = new RowBounds(offset, pageSize);

        List<ExpensesRecord> list = expensesRecordMapper.getPagedByTimeZone(rowBounds, userId, ledgerId, type, startTime, endTime);
        return list;

    }

    @Override
    public Integer insert(ExpensesRecord expensesRecord) {
        //校验是否具有操作当前账本的权限
        LedgerPermission ledgerPermission = ledgerPermissionMapper.getOne(expensesRecord.getUserId(), expensesRecord.getLedgerId());
        if (ledgerPermission == null) {
            return -ResponseCode.UN_AUTH.getCode();
        }
        Integer insertRes = expensesRecordMapper.insert(expensesRecord.getUserId(),
                expensesRecord.getLedgerId(),
                expensesRecord.getValue(),
                expensesRecord.getType(),
                expensesRecord.getKind(),
                expensesRecord.getRemark(),
                expensesRecord.getDate());
        if (insertRes != 0) {
            return insertRes;
        }
        return -ResponseCode.SERVER_ERROR.getCode();
    }

    /**
     * 判断用户对该条消费记录是否有操作权限
     *
     * @param id     消费记录id
     * @param userId 用户id
     * @return true - 没有操作权限
     */
    public boolean hasExpensesRecordPermission(Integer id, Integer userId) {
        Long cnt = expensesRecordMapper.selectCount(id, userId);
        return cnt == 0;
    }

    @Override
    public Boolean delete(Integer id, Integer userId) {
        if (hasExpensesRecordPermission(id, userId)) {
            return false;
        }
        return expensesRecordMapper.delete(id);
    }

    @Override
    public Boolean update(ExpensesRecord expensesRecord) {
        Integer id = expensesRecord.getId();
        Integer userId = expensesRecord.getUserId();
        Double value = expensesRecord.getValue();
        Boolean type = expensesRecord.getType();
        String kind = expensesRecord.getKind();
        Date date = expensesRecord.getDate();
        String remark = expensesRecord.getRemark();
        //检查是否有更新记录的权限
        if (hasExpensesRecordPermission(id, userId)) {
            return false;
        }
        int effectedRow = expensesRecordMapper.update(id, value, type, kind, date, remark);
        return effectedRow != 0;
    }
}
