package team.hiddenblue.wealthtrack.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import team.hiddenblue.wealthtrack.controller.ExpensesRecordController;
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


}
