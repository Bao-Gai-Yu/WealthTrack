package team.hiddenblue.wealthtrack.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team.hiddenblue.wealthtrack.constant.ErrorCode;
import team.hiddenblue.wealthtrack.constant.ResponseCode;
import team.hiddenblue.wealthtrack.dto.LedgerDto;
import team.hiddenblue.wealthtrack.dto.LedgerResult;
import team.hiddenblue.wealthtrack.exception.AppException;
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
    public Integer insert(LedgerDto ledger) {
        int userId = StpUtil.getLoginIdAsInt();
//        String md5Pwd = Md5Util.getMD5String(ledger.getPassword());
        Ledger insertLedger = Ledger.builder()
                .id(ledger.getId())
                .name(ledger.getName())
                .password(null)
                .isPublic(ledger.getIsPublic())
                .ownerId(userId)
                .template(ledger.getTemplate()).build();
        Integer insertLedgerRes = ledgerMapper.insertOne(insertLedger);
        LedgerPermission ledgerPermission = LedgerPermission.builder()
                .userId(insertLedger.getOwnerId())
                .ledgerId(insertLedger.getId()).build();
        Integer insertPermissionRes = ledgerMapper.insertPermission(ledgerPermission);
        if (insertLedgerRes != 0 && insertPermissionRes != 0) {
            return insertLedgerRes;
        }
        return -ResponseCode.SERVER_ERROR.getCode();
    }

    /**
     * 获取当前用户参与的所有账本信息
     */
    @Override
    public Object selectLedgerByUserId(int userId) {
        //获取用户当前参与的账本信息
        List<LedgerPermission> ledgerPermissions = ledgerPermissionMapper.selectByUser(userId);
        if (ledgerPermissions == null) {
            return null;
        }
        List<LedgerResult> ledgers = new ArrayList<>();
        for (LedgerPermission ledgerPermission : ledgerPermissions) {
            LedgerResult ledger = ledgerMapper.selectByLedgerId(ledgerPermission.getLedgerId());
            if(ledger!=null){
                ledgers.add(ledger);
            }
        }
        System.out.println(ledgers);
        return ledgers;
    }

    /**
     * 修改账本信息
     * 此处需要更新的密码同样进行MD5加密
     */
    @Override
    public Boolean update(LedgerDto ledger) {
        int userId = StpUtil.getLoginIdAsInt();
//        //检查需要修改的账本是否是用户所有
//        if(ledgerMapper.selectByLedgerId(ledger.getId()).getOwnerId()!=userId){
//            return null;
//        }
//        String md5Pwd = Md5Util.getMD5String(ledger.getPassword());
        /**
         * 根据不同情况存在不同的更新方式
         * (1)账单没有进行转让，即ledger的OwnerId信息没有更改，此时处理如下
         * (2)账单进行了转让，即ledger的OwnerId变更了，此时还需要更新ledger_permission
         */
        Integer ownerId = ledgerMapper.getOwnerId(ledger.getOwner());
        //若没有找到用户ID则默认此项不作修改
        if (ownerId == null) {
            System.out.println("该用户名不存在,自动转换成默认用户名");
            ownerId = userId;
        }
        Integer updatedRow = ledgerMapper.update(ledger.getId(),
                ledger.getName(),
                ledger.getIsPublic(),
                ownerId,
                ledger.getTemplate()
        );
        return updatedRow != 0;
    }

    /**
     * 删除账本
     */
    @Override
    public Boolean delete(int operatorId, int ledgerId) {
        if (ledgerMapper.selectByLedgerId(ledgerId) == null) {
            System.out.println("没有找到账单ID所属的账单信息");
            return false;
        }
        //防止在共享账本中非账本拥有者误删账本
        if(ledgerMapper.getLedgerOwner(ledgerId)!=operatorId){
            System.out.println("你不是该账单拥有者，无权限删除！");
            return false;
        }
        if (ledgerMapper.delLedger(ledgerId) && ledgerMapper.delPermission(ledgerId)) {
            return true;
        }
        return false;
    }

    /**
     * 共享账本操作，加入别人的账本
     * @param userId
     * @param password
     */
    @Override
    public void share(int userId, String password) {
        Ledger ledger = ledgerMapper.findByPassword(password);
        if (ledger == null || !ledger.getIsPublic()) {
            throw new AppException(ErrorCode.LEDGER_NOT_PUBLIC_OR_NOT_EXISTS_ERROR);
        }

        LedgerPermission ledgerPermission = ledgerPermissionMapper.getOne(userId, ledger.getId());
        if (ledgerPermission != null) {
            throw new AppException(ErrorCode.LEDGER_HAS_JOINED_ERROR);
        }

        ledgerMapper.insertPermission(LedgerPermission.builder()
                .ledgerId(ledger.getId())
                .userId(userId).build());
    }
}
