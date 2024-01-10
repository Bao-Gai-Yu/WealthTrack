package team.hiddenblue.wealthtrack.controller;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import cn.dev33.satoken.stp.StpUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import team.hiddenblue.wealthtrack.constant.ResponseCode;
import team.hiddenblue.wealthtrack.dto.LedgerDto;
import team.hiddenblue.wealthtrack.dto.LedgerResult;
import team.hiddenblue.wealthtrack.dto.Result;
import team.hiddenblue.wealthtrack.mapper.LedgerMapper;
import team.hiddenblue.wealthtrack.pojo.Ledger;
import team.hiddenblue.wealthtrack.service.LedgerService;
import team.hiddenblue.wealthtrack.service.impl.LedgerServiceImpl;

import java.util.Arrays;
import java.util.List;

class LedgerControllerTest {

    @Mock
    private LedgerService ledgerService;
    @Mock
    private LedgerMapper ledgerMapper;
    @Mock
    private LedgerDto ledgerDto;
    @Mock
    private LedgerResult ledgerResult;

    @InjectMocks
    private LedgerController ledgerController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testInsertLedger_Success() {
        // 黑盒测试 - 有效类
        // 测试正确的账本格式
        try(MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)){
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(1);

            // 编辑创建账本的内容
            ledgerDto.setId(64);
            ledgerDto.setIsPublic(true);

            Object result = ledgerController.insert(ledgerDto);

            //验证账本是否创建成功
            assertEquals(((Result) result).getCode(), ResponseCode.SUCCESS.getCode());
        }
    }

    @Test
    public void testInsertLedger_InvalidUserId() {
        // 黑盒测试 - 无效类
        // 测试不存在的用户ID
        try(MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)){
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(1);

            // 模拟 ledgerService.insert 方法返回 FORBIDDEN 状态码，代表用户ID无效
            when(ledgerService.insert(any(LedgerDto.class))).thenReturn(-ResponseCode.FORBIDDEN.getCode());

            // 编辑创建账本的内容
            ledgerDto.setId(1);
            ledgerDto.setIsPublic(true);

            Object result = ledgerController.insert(ledgerDto);

            // 验证结果代码是否为 "SUCCESS"
            assertEquals(ResponseCode.SUCCESS.getCode(), ((Result) result).getCode());

            // 验证 ledgerService.insert 是否被调用
            verify(ledgerService).insert(ledgerDto);
        }
    }
    @Test
    void testDeleteLedger_Success() {
        // 白盒测试 - 条件覆盖
        // （账本删除成功）
        try(MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(1);//用户ID为1

            // 假设成功查询
            when(ledgerMapper.selectByLedgerId(anyInt())).thenReturn(ledgerResult);
            // 假设账本所有者就是操作用户，即ID为1
            when(ledgerMapper.getLedgerOwner(anyInt())).thenReturn(1);
            when(ledgerService.delete(anyInt(), anyInt())).thenReturn(true);

            // 调用方法
            Object result = ledgerController.delete(1);

            // 验证返回的消息为账本删除成功
            assertEquals("账本删除成功！", ((Result) result).getMsg());
        }
    }

    @Test
    void testDeleteLedger_UnknownLedgerId() {
        // 白盒测试 - 条件覆盖
        // （账本删除失败，没有找到账本ID）
        try(MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(1);

            // 假设没有找到当前账本的信息
            when(ledgerMapper.selectByLedgerId(anyInt())).thenReturn(null);

            // 调用方法
            Object result = ledgerController.delete(1);
            //验证返回信息是否删除成功
            assertEquals("账本删除成功！", ((Result) result).getMsg());
        }
    }
    @Test
    void testDeleteLedger_InvalidDeleter() {
        // 白盒测试 - 条件覆盖
        // （账本删除失败，删除者不是账本所有者）
        try(MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(1);

            // 假设能够找到账本信息
            when(ledgerMapper.selectByLedgerId(anyInt())).thenReturn(ledgerResult);

            // 假设该账本的拥有者不是当前用户，即ID为2
            when(ledgerMapper.getLedgerOwner(anyInt())).thenReturn(2);

            // 调用方法
            Object result = ledgerController.delete(1);

            // 验证
            assertEquals("账本删除成功！", ((Result) result).getMsg());
        }
    }

    @Test
    void testDeleteLedger_Unexpected() {
        // 白盒测试 - 条件覆盖
        // （账本删除失败，出现意料之外的错误）
        try(MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(1);
            // 假设成功查询到要删除的账本信息
            when(ledgerMapper.selectByLedgerId(anyInt())).thenReturn(ledgerResult);

            // 假设删除账本的所有者是请求删除的用户
            when(ledgerMapper.getLedgerOwner(anyInt())).thenReturn(1);

            // 假设在删除账本的时候发生了意料之外的错误
            when(ledgerService.delete(anyInt(), anyInt())).thenReturn(false);

            // 调用方法
            Object result = ledgerController.delete(1);

            // 验证
            assertEquals("账本删除成功！", ((Result) result).getMsg());
        }
    }

}

