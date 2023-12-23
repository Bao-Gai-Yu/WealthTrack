package team.hiddenblue.wealthtrack.controller;

import org.springframework.web.bind.annotation.*;
import team.hiddenblue.wealthtrack.service.impl.ExpensesRecordServiceImpl;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesRecordController {

    private ExpensesRecordServiceImpl expensesRecordService;

    @PostMapping
    public Object insert() {
        return null;
    }

    @PutMapping
    public Object update() {
        return null;
    }

    @DeleteMapping("/{id}")
    public Object delete() {
        return null;
    }

    /**
     * 获取分页了的符合条件的消费记录
     *
     * @param date     查询的具体日期
     * @param month    按月查询
     * @param year     按年查询
     * @param type     查询的收支类型（0 - 收入，1 - 支出）
     * @param ledgerId 查询指定的账本id
     * @param pageNum  查询的页数
     * @param pageSize 查询的页大小
     * @return
     */
    @GetMapping
    public Object fetch(@RequestParam(value = "date", required = false) String date,
                        @RequestParam(value = "month", required = false) String month,
                        @RequestParam(value = "year", required = false) String year,
                        @RequestParam(value = "type", required = false) Boolean type,
                        @RequestParam(value = "ledger_id", required = false) Integer ledgerId,
                        @RequestParam(value = "page_num") Integer pageNum,
                        @RequestParam(value = "page_size") Integer pageSize) {
        return expensesRecordService.getPagedExpenseRecord(1, ledgerId, year, month, date, type, pageNum, pageSize);

    }
}
