package team.hiddenblue.wealthtrack.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import team.hiddenblue.wealthtrack.constant.ResponseCode;
import team.hiddenblue.wealthtrack.dto.ExpensesRecordDto;
import team.hiddenblue.wealthtrack.pojo.ExpensesRecord;
import team.hiddenblue.wealthtrack.dto.Result;
import team.hiddenblue.wealthtrack.service.ExpensesRecordService;
import team.hiddenblue.wealthtrack.service.TextInService;

import java.io.IOException;
import java.net.URLDecoder;
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
     * @param expensesRecordDto 前端传来的数据
     * @return json数据，包含：msg - 状态信息, code - 状态码, data - 表示MySQL中影响的行数
     */
    @PostMapping
    public Object insert(@RequestBody ExpensesRecordDto expensesRecordDto) {
        System.out.println("Dto: " + expensesRecordDto);
        if(expensesRecordDto.getRemark() == null){
            expensesRecordDto.setRemark("");
        }
        //获取用户ID
        Integer userId = StpUtil.getLoginIdAsInt();
        Integer insert = expensesRecordService.insert(userId, expensesRecordDto.getLedgerId(), expensesRecordDto.getValue(), expensesRecordDto.getType(), expensesRecordDto.getKind(), expensesRecordDto.getRemark(), expensesRecordDto.getDate());
        if (insert == -ResponseCode.UN_AUTH.getCode()) {
            return Result.UN_AUTH("无权操作该账本:UserId->" + userId + " LedgerId->" + expensesRecordDto.getLedgerId());
        } else if (insert == -ResponseCode.SERVER_ERROR.getCode()) {
            return Result.SERVER_ERROR("服务器开小差了");
        } else {
            return Result.SUCCESS("插入成功！", insert);
        }
    }

    /**
     * 修改消费记录
     *
     * @param expensesRecord 前端传来的数据
     * @return json数据，包含：msg - 状态信息, code - 状态码, data - null
     */
    @PutMapping
    public Object update(@RequestBody ExpensesRecord expensesRecord) {
        expensesRecord.setUserId(StpUtil.getLoginIdAsInt());
        Boolean updated = expensesRecordService.update(expensesRecord);
        if (updated) {
            return Result.SUCCESS("修改成功！");
        } else {
            return Result.FAIL("修改失败！");
        }
    }

    /**
     * 删除消费记录
     *
     * @param id 欲删除的消费记录id
     * @return json数据，包含：msg - 状态信息, code - 状态码, data - null
     */
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable Integer id) {
        Boolean deleted = (Boolean) expensesRecordService.delete(id, StpUtil.getLoginIdAsInt());
        if (deleted) {
            return Result.SUCCESS("删除成功！");
        } else {
            return Result.FAIL("删除失败！");
        }
    }

    /**
     * 获取分页了的符合条件的消费记录
     * 查的是当前账本的记录（不按照userId查询，因为共享账本需要显示所有在该账本编辑的用户的记录）
     *
     * @param date     查询的具体日期
     * @param month    按月查询
     * @param year     按年查询
     * @param type     查询的收支类型（0 - 收入，1 - 支出）
     * @param ledgerId 查询指定的账本id
     * @param pageNum  查询的页数
     * @param pageSize 查询的页大小
     * @return json数据，包含：msg - 状态信息, code - 状态码, data - 查询结果
     */
    @GetMapping
    public Object fetch(@RequestParam(value = "date", required = false) String date,
                        @RequestParam(value = "month", required = false) String month,
                        @RequestParam(value = "year", required = false) String year,
                        @RequestParam(value = "type", required = false) Boolean type,
                        @RequestParam(value = "ledger_id", required = false) Integer ledgerId,
                        @RequestParam(value = "page_num") Integer pageNum,
                        @RequestParam(value = "page_size") Integer pageSize,
                        @CookieValue(value = "satoken") String pbSession) {
        System.out.println("controller-fetch-type:" + type);
        System.out.println("pbSession: " + pbSession);
        System.out.println("TokenValue: " + StpUtil.getTokenValue());
        if (!StpUtil.getTokenValue().equals(pbSession)) {
            return Result.FORBIDDEN("会话错误，操作失败！");
        }
        Map<String, Object> fetchResult = expensesRecordService.getPagedExpenseRecord(StpUtil.getLoginIdAsInt(), ledgerId, year, month, date, type, pageNum, pageSize);
        Integer resSize = (Integer) fetchResult.get("total");
        if (resSize == 0) {
            System.out.println("未查询到结果！");
            return Result.SUCCESS("未查询到结果！", fetchResult);
        } else {
            System.out.println("查询成功！");
            Object obj = Result.SUCCESS("查询成功！", fetchResult);
            System.out.println(obj);
            return Result.SUCCESS("查询成功！", fetchResult);
        }
    }

    /**
     * 对kind进行精确查询和remark模糊查询
     * 全账本搜索当前用户的相关记录
     *
     * @param kind          种类
     * @param encodedRemark 前端传入的备注（经过URL编码的字符串）
     * @return json数据，包含：msg - 状态信息, code - 状态码, data - 查询结果
     */
    @GetMapping("/select")
    public Object querySelect(@RequestParam(value = "kind") String kind,
                              @RequestParam(value = "remark") String encodedRemark,
                              @RequestParam(value = "ledger_id") Integer ledgerId,
                              @RequestParam(value = "date", required = false) String date,
                              @RequestParam(value = "month", required = false) String month,
                              @RequestParam(value = "year", required = false) String year,
                              @RequestParam(value = "type", required = false) Boolean type,
                              @RequestParam(value = "page_num") Integer pageNum,
                              @RequestParam(value = "page_size") Integer pageSize,
                              @CookieValue(value = "satoken") String pbSession) {
        System.out.println("Do querySelect");
        System.out.println("pbSession:" + pbSession);
        System.out.println("tokenValue:" + StpUtil.getTokenValue());
        System.out.println(type == null);
        if (!StpUtil.getTokenValue().equals(pbSession)) {
            return Result.FORBIDDEN("会话错误，操作失败！");
        }
        System.out.println("encodedRemark: " + encodedRemark);
        String decodedRemark = URLDecoder.decode(encodedRemark);
        System.out.println("decodedRemark: " + decodedRemark);
        Map<String, Object> queryResult = expensesRecordService.getSelecetdExpensesRecord(StpUtil.getLoginIdAsInt(), kind, decodedRemark, ledgerId, year, month, date, type, pageNum, pageSize);
        return Result.SUCCESS("查询成功！", queryResult);
    }

    /**
     * 根据火车票自动插入消费记录
     *
     * @param photo
     * @return
     * @throws IOException
     */
    @PostMapping("/train_ticket")
    public Object insertByTicket(@RequestParam("photo") MultipartFile photo) throws IOException {
        if(photo != null && !photo.isEmpty()) {
            return Result.SUCCESS(textInService.insertByTicket(photo.getBytes()));
        }
        else{
            return Result.NOT_FOUND("图片为空");
        }
    }

    /**
     * 根据声音自动录入消费记录
     *
     * @param map 包括由声音转化来的文字
     * @return
     */
    @PostMapping("/voice")
    public Object insertByVoice(@RequestBody Map<String, String> map) {
        String sentence = map.get("sentence");
        return Result.SUCCESS(textInService.insertByVoice(sentence));
    }

    @PostMapping("/photo")
    public Object insertByCommonImg(@RequestParam("photo") MultipartFile photo) throws IOException {
        if(photo != null && !photo.isEmpty()) {
            return Result.SUCCESS(textInService.insertByCommonImg(photo.getBytes()));
        }
        else{
            return Result.NOT_FOUND("图片为空");
        }
    }


    /**
     * 根据商铺小票自动识别消费记录
     * @param photo
     * @return
     * @throws IOException
     */
    @PostMapping("/receipt")
    public Object insertByReceipt(@RequestParam("photo") MultipartFile photo) throws IOException{
        if(photo != null && !photo.isEmpty()) {
            return Result.SUCCESS(textInService.insertByReceipt(photo.getBytes()));
        }
        else{
            return Result.NOT_FOUND("图片为空");
        }
    }

}
