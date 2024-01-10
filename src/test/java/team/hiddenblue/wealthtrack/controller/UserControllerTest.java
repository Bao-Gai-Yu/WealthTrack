package team.hiddenblue.wealthtrack.controller;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.MockitoAnnotations;
import team.hiddenblue.wealthtrack.dto.Code2SessionDto;
import team.hiddenblue.wealthtrack.exception.AppException;
import team.hiddenblue.wealthtrack.pojo.User;
import team.hiddenblue.wealthtrack.pojo.UserInfo;
import team.hiddenblue.wealthtrack.service.UserInfoService;
import team.hiddenblue.wealthtrack.service.UserService;
import team.hiddenblue.wealthtrack.service.WeChatService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import static org.mockito.Mockito.*;
import cn.dev33.satoken.stp.StpUtil;
class UserControllerTest {
    @Mock
    private UserService userService;

    @Mock
    private User mockUser; // 模拟 User 对象

    @Mock
    private UserInfo mockUserInfo; // 模拟 User 对象

    @Mock
    private UserInfoService userInfoService;

    @Mock
    private WeChatService weChatService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void testModifyBirthday() {
        // 黑盒测试 - 有效类
        // 测试正确的日期格式
        // 模拟 StpUtil 的行为
        try (MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(1); // 假设用户ID为1

            // 模拟 userInfoService 的行为
            doNothing().when(userInfoService).updateInfo(1, "1990-01-01");

            // 调用测试方法
            Object result = userController.modifyBirthday("1990-01-01");

            // 验证结果
            assertNotNull(result);
            // 可以添加更多的断言来验证返回的 JSON 数据等

            // 验证 userInfoService 的 updateInfo 方法被正确调用
            verify(userInfoService).updateInfo(1, "1990-01-01");
        }
    }

    @Test
    public void testModifyBirthday_InvalidInput() {
        // 黑盒测试 - 无效类
        // 测试不存在的日期
        // 模拟 StpUtil 的行为
        try (MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(1); // 假设用户ID为1

            // 输入一个无效的生日字符串
            String invalidBirthday = "1990-01-33";

            // 调用测试方法
            Object result = userController.modifyBirthday(invalidBirthday);


            // 验证 userInfoService 的 updateInfo 方法未被调用
            verify(userInfoService, times(0)).updateInfo(anyInt(), anyString());
        }
    }

    @Test
    public void testGetUserAvatar_WithAvatar() {
        // 白盒测试 - 条件覆盖
        // （用户头像URL存在）
        try (MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(1); // 假设用户ID为1
            UserInfo mockUserInfo = mock(UserInfo.class);
            when(mockUserInfo.getAvatar()).thenReturn("avatarUrl");
            when(userInfoService.getById(1)).thenReturn(mockUserInfo);

            // 调用方法
            Object result = userController.getUserAvatar();

            // 验证头像URL获取成功
            assertNotNull(result);
            // 其他断言，验证返回对象的属性等
        }
    }

    @Test
    public void testGetUserAvatar_WithoutAvatar() {
        // 白盒测试 - 条件覆盖
        // （用户头像URL不存在）
        try (MockedStatic<StpUtil> mockedStpUtil = Mockito.mockStatic(StpUtil.class)) {
            mockedStpUtil.when(StpUtil::getLoginIdAsInt).thenReturn(68); // 假设用户ID为68
            UserInfo mockUserInfo = mock(UserInfo.class);
            when(mockUserInfo.getAvatar()).thenReturn(null);
            when(userInfoService.getById(1)).thenReturn(mockUserInfo);

            // 调用方法
            Object result = userController.getUserAvatar();

            // 验证头像URL获取失败
            assertNotNull(result);
            // 其他断言，比如验证返回对象是否表示失败等
        }
    }


}