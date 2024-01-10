package team.hiddenblue.wealthtrack.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.hamcrest.core.IsNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.multipart.MultipartFile;
import team.hiddenblue.wealthtrack.controller.ExpensesRecordController;
import team.hiddenblue.wealthtrack.dto.ExpensesRecordDto;
import team.hiddenblue.wealthtrack.dto.ExpensesRecordResult;
import team.hiddenblue.wealthtrack.dto.Result;
import team.hiddenblue.wealthtrack.exception.AppException;
import team.hiddenblue.wealthtrack.service.ExpensesRecordService;
import team.hiddenblue.wealthtrack.service.TextInService;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class ExpensesRecordControllerTest {

    @Mock
    private ExpensesRecordService expensesRecordService;

    @Mock
    private TextInService textInService;

    @Mock
    private ExpensesRecordResult mockExpensesRecordResult; // 模拟的ExpensesRecordResult


    @InjectMocks
    private ExpensesRecordController expensesRecordController;

    @Test
    public void testInsertByTicket_Valid() throws IOException {
        // 黑盒测试 - 有效类
        MultipartFile photo = new MockMultipartFile("file", "test.jpg", "image/jpeg", new byte[10]);
        when(textInService.insertByTicket(any())).thenReturn(mockExpensesRecordResult);

        Object result = expensesRecordController.insertByTicket(photo);

        assertNotNull(result);
    }

    @Test
    public void testInsertByTicket_Invalid() throws IOException {
        // 黑盒测试 - 无效类
        MultipartFile emptyImg = new MockMultipartFile("file", "empty.jpg", "image/jpeg", new byte[0]);

        assertThrows(AppException.class, () -> {
            expensesRecordController.insertByTicket(emptyImg);
        });
    }

    @Test
    public void testInsertByVoice_Valid() {
        // 黑盒测试 - 有效类
        Map<String, String> map = new HashMap<>();
        map.put("sentence", "Some voice converted text");
        when(textInService.insertByVoice(anyString())).thenReturn(mockExpensesRecordResult);

        Object result = expensesRecordController.insertByVoice(map);

        assertNotNull(result);

    }

    //白盒测试 - 条件覆盖
    // 处理不同类型的返回值
    @Test
    public void testInsertByTicket_DifferentResults() throws IOException {
        MultipartFile photo = new MockMultipartFile("file", "test.jpg", "image/jpeg", new byte[10]);

        // 模拟返回成功的结果
        when(textInService.insertByTicket(any())).thenReturn(mockExpensesRecordResult);
        Object successResult = expensesRecordController.insertByTicket(photo);
        assertNotNull(successResult);

        // 模拟返回失败的结果
        when(textInService.insertByTicket(any())).thenReturn(mockExpensesRecordResult);
        Object failureResult = expensesRecordController.insertByTicket(photo);
        assertNotNull(failureResult);
    }

    //白盒测试 - 条件覆盖
    // 处理不同类型的返回值
    @Test
    public void testInsertByVoice_DifferentInputs() {
        Map<String, String> validMap = new HashMap<>();
        validMap.put("sentence", "Valid sentence");
        when(textInService.insertByVoice("Valid sentence")).thenReturn(mockExpensesRecordResult);

        Object validResult = expensesRecordController.insertByVoice(validMap);
        assertNotNull(validResult);

        Map<String, String> invalidMap = new HashMap<>();
        invalidMap.put("sentence", "");
        when(textInService.insertByVoice("")).thenReturn(mockExpensesRecordResult);

        Object invalidResult = expensesRecordController.insertByVoice(invalidMap);
        assertNotNull(invalidResult);

    }

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * 黑盒测试-有效类
     * 测试用户删除收支记录
     *
     * @throws Exception
     */
    @Test
    public void testDeleteExpenseRecord() throws Exception {
        try (MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            //模拟用户登录，用户id为66。
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(66);
            // 模拟 ExpensesRecordService 的 delete 方法,id为收支记录id，userId为用户id
            when(expensesRecordService.delete(310, 66)).thenReturn(true);
            // 执行删除操作的测试
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(expensesRecordController).build();
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/expenses/{id}", 310)).andExpect(MockMvcResultMatchers.jsonPath("$.code").value(200)).andExpect(MockMvcResultMatchers.jsonPath("$.data").value(IsNull.nullValue())).andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("删除成功！"));

            // 验证 delete 方法被调用
            verify(expensesRecordService).delete(310, 66);
        }
    }

    /**
     * 黑盒测试-无效类
     * 没有权限的删除
     *
     * @throws Exception
     */
    @Test
    public void testDeleteExpenseRecordWithNoAuth() throws Exception {
        try (MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            //模拟用户登录，用户id为1。
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(1);
            // 模拟 ExpensesRecordService 的 delete 方法,id为收支记录，userId为用户id
            when(expensesRecordService.delete(310, 1)).thenReturn(false);

            // 执行删除操作的测试
            MockMvc mockMvc = MockMvcBuilders.standaloneSetup(expensesRecordController).build();
            mockMvc.perform(MockMvcRequestBuilders.delete("/api/expenses/{id}", 310)).andExpect(MockMvcResultMatchers.jsonPath("$.code").value(400)).andExpect(MockMvcResultMatchers.jsonPath("$.data").value(IsNull.nullValue())).andExpect(MockMvcResultMatchers.jsonPath("$.msg").value("删除失败！"));

            // 验证 delete 方法被调用
            verify(expensesRecordService).delete(310, 1);
        }
    }

    /**
     * 白盒测试 - 条件覆盖
     * 用户修改记录成功
     */
    @Test
    public void testUpdateSuccess() {
        try (MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            // 模拟登录用户
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(66);
            ExpensesRecordDto mockedExpensesRecordDto = ExpensesRecordDto.builder().id(14).date("2016-12-15").kind("food").type(true).value(1145.14).remark("测试记录的修改-超市购物").build();
            mockedExpensesRecordDto.setUserId(66);
            // 模拟更新操作成功
            when(expensesRecordService.update(any(ExpensesRecordDto.class))).thenReturn(true);
            // 执行测试
            Result result = (Result) expensesRecordController.update(mockedExpensesRecordDto);
            // 验证结果
            assertEquals(200, result.getCode());
            assertNull(result.getData());
            assertEquals("修改成功！", result.getMsg());
            // 验证方法调用次数等
            verify(expensesRecordService, times(1)).update(any(ExpensesRecordDto.class));
        }
    }

    /**
     * 白盒测试 - 条件覆盖
     * 用户修改记录失败
     */
    @Test
    public void testUpdateFailure() {
        try (MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            // 模拟登录用户
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(66);
            ExpensesRecordDto mockedExpensesRecordDto = ExpensesRecordDto.builder().id(14).date("2016-12-15").kind("food").type(true).value(1145.14).remark("测试记录的修改-超市购物").build();
            mockedExpensesRecordDto.setUserId(66);
            // 模拟更新操作失败
            when(expensesRecordService.update(any(ExpensesRecordDto.class))).thenReturn(false);
            // 执行测试
            Result result = (Result) expensesRecordController.update(mockedExpensesRecordDto);
            // 验证结果
            assertEquals(400, result.getCode());
            assertNull(result.getData());
            assertEquals("修改失败！", result.getMsg());
            // 验证方法调用次数等
            verify(expensesRecordService, times(1)).update(any(ExpensesRecordDto.class));
        }
    }
}

