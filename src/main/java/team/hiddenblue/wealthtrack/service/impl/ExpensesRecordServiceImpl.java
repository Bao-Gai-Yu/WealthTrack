package team.hiddenblue.wealthtrack.service.impl;

import org.springframework.context.ApplicationContextException;
import org.springframework.stereotype.Service;
import team.hiddenblue.wealthtrack.mapper.ExpensesRecordMapper;
import team.hiddenblue.wealthtrack.service.ExpensesRecordService;
import team.hiddenblue.wealthtrack.util.TimeUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Service
public class ExpensesRecordServiceImpl implements ExpensesRecordService {
    ExpensesRecordMapper expensesRecordMapper;

    @Override
    public Map<String, Object> getPagedExpenseRecord(Integer userId, Integer ledgerId, String year, String month, String date, Boolean type, Integer pageNum, Integer pageSize) {
        /**
         * 查询开始时间
         */
        Date startTime = null;
        /**
         * 查询结束时间
         */
        Date endTime = null;
        try {
            if (!date.equals("")) {
                startTime = TimeUtil.tranStringToDate(date);
            } else if (!month.equals("")) {
                startTime = TimeUtil.tranStringToDate(month + "-01");
                endTime = TimeUtil.tranStringToDate(month + "-01");
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(endTime);
                endCalendar.add(Calendar.MONTH, 1);
                endTime = endCalendar.getTime();
            } else if (!year.equals("")) {
                startTime = TimeUtil.tranStringToDate(month + "-01-01");
                endTime = TimeUtil.tranStringToDate(month + "-01-01");
                Calendar endCalendar = Calendar.getInstance();
                endCalendar.setTime(endTime);
                endCalendar.add(Calendar.YEAR, 1);
                endTime = endCalendar.getTime();
            }
        } catch (ParseException pe) {
            pe.printStackTrace();
        }



        return null;

    }
}
