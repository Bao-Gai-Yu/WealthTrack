package team.hiddenblue.wealthtrack.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team.hiddenblue.wealthtrack.constant.ResponseCode;
import team.hiddenblue.wealthtrack.dto.LedgerDto;
import team.hiddenblue.wealthtrack.dto.LedgerUsersResult;
import team.hiddenblue.wealthtrack.dto.Result;
import team.hiddenblue.wealthtrack.mapper.LedgerMapper;
import team.hiddenblue.wealthtrack.pojo.Ledger;
import team.hiddenblue.wealthtrack.pojo.LedgerPermission;
import team.hiddenblue.wealthtrack.pojo.UserInfo;
import team.hiddenblue.wealthtrack.service.LedgerService;

import java.util.List;

@RestController
@RequestMapping("/api/ledger")
@RequiredArgsConstructor
public class LedgerController {
    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private LedgerMapper ledgerMapper;

    /**
     * 新建账本
     *
     * @param ledger 前端发送来的新建账本有关数据
     * @return json数据，包含：msg - 状态信息, code - 状态码, data - 表示MySQL中影响的行数
     */
    @PostMapping
    public Object insert(@RequestBody LedgerDto ledger){
        Integer insert = ledgerService.insert(ledger);
        if (insert == -ResponseCode.SERVER_ERROR.getCode()) {
            return Result.SERVER_ERROR("服务器开小差了");
        }else if(insert == -ResponseCode.FORBIDDEN.getCode()){
            return Result.FORBIDDEN("非法的用户ID，禁止创建账本！");
        }
        else {
            return Result.SUCCESS("账本创建成功！", insert);
        }
    }

    /**
     * 获取当前用户参与的账本数据
     *
     * @return json数据，包含：msg - 状态信息, code - 状态码, data - 表示获取的账本信息
     */
    @GetMapping
    public Object fetchLedger(){
        //获取当前用户ID
        int userId = StpUtil.getLoginIdAsInt();
        //查询账本数据成功
        return Result.SUCCESS("获取成功！",ledgerService.selectLedgerByUserId(userId));
    }

    /**
     * 修改账本信息
     *
     * @param ledger 前端发送的新账本信息
     * @return json数据，包含：msg - 状态信息, code - 状态码, data - null
     */
    @PutMapping
    public Object update(@RequestBody LedgerDto ledger){
        int userId = StpUtil.getLoginIdAsInt();
        //检查需要修改的账本是否是用户所有
        if(ledgerMapper.selectOwnerId(ledger.getId())!=userId){
            return Result.FAIL("非账本所有者无法修改账本信息！");
        }
        Boolean isUpdated = ledgerService.update(ledger);
        if(isUpdated){
            return Result.SUCCESS("账本修改成功！");
        }else{
            return Result.FAIL("账本修改失败！");
        }
    }

    /**
     * 删除账本
     *
     * @param id 需要被删除的账本 id
     * @return json数据，包含：msg - 状态信息, code - 状态码, data - null
     */
    @DeleteMapping("/{id}")
    public Object delete(@PathVariable int id){
        //获取当前用户ID仍是采用sa-token
        Boolean isDeleted = ledgerService.delete(StpUtil.getLoginIdAsInt(),id);
        if(isDeleted){
            return Result.SUCCESS("账本删除成功！");
        }else{
            return Result.FAIL("账本删除失败！");
        }
    }

    /**
     * 查询账本密码
     *
     * @param ledgerId 查询的关键词
     * @return json数据，包含状态码和状态信息
     */
    @GetMapping("/query")
    public Object search(@RequestParam(value = "ledger_id") Integer ledgerId) {
        Ledger ledger = ledgerMapper.findLedger(ledgerId);
        if(ledger == null){
            return Result.FAIL("账本不存在");
        }
        return Result.SUCCESS("密钥获取成功",ledgerService.query(ledgerId));
    }
    /**
     * 共享账本，加入别人的账本
     * @param password 账本密钥
     * @return
     */
    @PostMapping("/share")
    public Object share(@RequestParam String password) {
        ledgerService.share(StpUtil.getLoginIdAsInt(), password);
        return Result.SUCCESS();
    }

    /**
     * 获取拥有某个账本的所有用户信息
     * @param ledgerId
     * @return
     */
    @GetMapping("/users")
    public Object getAllLedgerUsers(@RequestParam("ledger_id") int ledgerId){
        List<LedgerUsersResult> ledgerUsersResults = ledgerService.getAllUsersByLedgerId(ledgerId);
        if(ledgerUsersResults == null){
            return Result.NOT_FOUND("未找到该账本所属用户信息");
        }
        else {
            return Result.SUCCESS("获取成功", ledgerUsersResults);
        }
    }

}
