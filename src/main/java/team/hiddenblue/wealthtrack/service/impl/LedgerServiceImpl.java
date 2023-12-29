package team.hiddenblue.wealthtrack.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.hiddenblue.wealthtrack.constant.ResponseCode;
import team.hiddenblue.wealthtrack.dto.LedgerResult;
import team.hiddenblue.wealthtrack.mapper.LedgerMapper;
import team.hiddenblue.wealthtrack.mapper.LedgerPermissionMapper;
import team.hiddenblue.wealthtrack.pojo.Ledger;
import team.hiddenblue.wealthtrack.pojo.LedgerPermission;
import team.hiddenblue.wealthtrack.service.LedgerService;
import team.hiddenblue.wealthtrack.util.Md5Util;

import java.util.ArrayList;
import java.util.List;

@Service
public class LedgerServiceImpl implements LedgerService {
    @Autowired
    LedgerMapper ledgerMapper;
    @Autowired
    LedgerPermissionMapper ledgerPermissionMapper;

    /**
     * 创建账本
     * 此处对账本密码进行MD5加密
     */
    @Override
    public Integer insert(Ledger ledger) {
        int userId = StpUtil.getLoginIdAsInt();
        String md5Pwd = Md5Util.getMD5String(ledger.getPassword());
        ledger.setPassword(md5Pwd);
        ledger.setOwnerId(userId);
        Integer insertLedgerRes = ledgerMapper.insertOne(ledger);
        LedgerPermission ledgerPermission = LedgerPermission.builder()
                .userId(ledger.getOwnerId())
                .ledgerId(ledger.getId()).build();
        Integer insertPermissionRes = ledgerMapper.insertPermission(ledgerPermission);
        if(insertLedgerRes != 0 && insertPermissionRes != 0){
            return insertLedgerRes;
        }
        return -ResponseCode.SERVER_ERROR.getCode();
    }

    /**
     *获取当前用户参与的所有账本信息
     */
    @Override
    public Object selectLedgerByUserId(int userId) {
        //获取用户当前参与的账本信息
        List<LedgerPermission> ledgerPermissions = ledgerPermissionMapper.selectByUser(userId);
        if(ledgerPermissions==null){
            return null;
        }
        List<LedgerResult> ledgers = new ArrayList<>();
        for(LedgerPermission ledgerPermission:ledgerPermissions){
            LedgerResult ledger = ledgerMapper.selectByLedgerId(ledgerPermission.getLedgerId());
            ledgers.add(ledger);
        }
        return ledgers;
    }

    /**
     * 修改账本信息
     * 此处需要更新的密码同样进行MD5加密
     */
    @Override
    public Boolean update(Ledger ledger) {
        int userId = StpUtil.getLoginIdAsInt();
        //检查需要修改的账本是否是用户所有
        if(ledgerMapper.selectByLedgerId(ledger.getId()).getOwnerId()!=userId){
            return null;
        }
        String md5Pwd = Md5Util.getMD5String(ledger.getPassword());
        /**
         * 根据不同情况存在不同的更新方式
         * (1)账单没有进行转让，即ledger的OwnerId信息没有更改，此时处理如下
         * (2)账单进行了转让，即ledger的OwnerId变更了，此时还需要更新ledger_permission
         */
        Integer updatedRow = ledgerMapper.update(ledger.getId(),
                ledger.getName(),
                md5Pwd,
                ledger.getIsPublic(),
                ledger.getOwnerId(),
                ledger.getTemplate()
                );
        return updatedRow != 0;
    }

    /**
     * 删除账本
     */
    @Override
    public Boolean delete(int ownerId, int ledgerId) {
        if(ledgerMapper.selectByLedgerId(ledgerId).getOwnerId()!=ownerId){
            return false;
        }
        if(ledgerMapper.delLedger(ledgerId) && ledgerMapper.delPermission(ledgerId)){
            return true;
        }
        return false;
    }
}