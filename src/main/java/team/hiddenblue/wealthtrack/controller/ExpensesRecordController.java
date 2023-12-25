package team.hiddenblue.wealthtrack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.hiddenblue.wealthtrack.pojo.ExpensesRecord;
import team.hiddenblue.wealthtrack.service.ExpensesRecordService;
import team.hiddenblue.wealthtrack.service.TextInService;
import team.hiddenblue.wealthtrack.service.impl.ExpensesRecordServiceImpl;
import team.hiddenblue.wealthtrack.service.impl.TextInServiceImpl;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/expenses")
public class ExpensesRecordController {
    @Autowired
    private ExpensesRecordService expensesRecordService;

    @Autowired
    private TextInService textInService;

    /**
     * 插入新的消费记录
     *
     * @param expensesRecord 前端传来的数据
     * @return true - 插入成功， false - 插入失败
     */
    @PostMapping
    public Object insert(@RequestBody ExpensesRecord expensesRecord) {
        return expensesRecordService.insert(expensesRecord);
    }

    /**
     * 修改消费记录
     *
     * @param expensesRecord
     * @return
     */
    @PutMapping
    public Object update(@RequestBody ExpensesRecord expensesRecord) {
        return expensesRecordService.update(expensesRecord);
    }

    /**
     * 删除消费记录
     *
     * @param id 欲删除的消费记录id
     * @return true - 删除成功， false - 可能是没有权限，也可能是记录不存在
     */
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Integer id) {
        return expensesRecordService.delete(id, 1);
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

    /**
     *根据火车票自动插入消费记录
     * @param photo
     * @return
     * @throws IOException
     */
    @PostMapping("/train_ticket")
    public Object insertByTicket(@RequestParam("photo") MultipartFile photo) throws IOException{
        return textInService.insertByTicket(photo.getBytes());
    }

    /**
     * 根据声音自动录入消费记录
     * @param map 包括由声音转化来的文字
     * @return
     */
    @PostMapping("/voice")
    public Object insertByVoice(@RequestBody Map<String, String> map){
        String sentence = map.get("sentence");
        return textInService.insertByVoice(sentence);
    }


}
