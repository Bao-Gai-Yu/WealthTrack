package team.hiddenblue.wealthtrack.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.hiddenblue.wealthtrack.mapper.UserInfoMapper;
import team.hiddenblue.wealthtrack.mapper.UserMapper;
import team.hiddenblue.wealthtrack.pojo.User;
import team.hiddenblue.wealthtrack.pojo.UserInfo;
import team.hiddenblue.wealthtrack.service.UserService;
import team.hiddenblue.wealthtrack.constant.ErrorCode;
import team.hiddenblue.wealthtrack.exception.AppException;
import java.util.regex.Pattern;

/**
 * @author heoeh
 * @version 1.3
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackForClassName="RuntimeException")
@CacheConfig(cacheNames = "ExpireOneMin")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

//    final LedgerService ledgerService;
    public static final String DATE = "^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))$";
    /**
     * 通过 ID 查询用户
     */
    @Cacheable(value = "ExpireOneMin", key = "'user_'+#userId")
    public User getById(Integer userId) {
        return userMapper.selectById(userId);
    }

    /**
     * 通过 openId 查询用户
     */
    @Cacheable(value = "ExpireOneMin", key = "'user_'+#openId")
    public User getByOpenId(String openId) {
        return userMapper.selectByOpenId(openId);
    }

    /**
     * 通过 openId 查询用户是否存在
     */
    @Cacheable(value = "ExpireOneMin", key = "'not_exist_'+#openId")
    public boolean exist(String openId) {
        Long count = userMapper.exist(openId);
        return count != 0;
    }

    /**
     * 获取当前登陆用户
     */
    public User getLoginUser() {
        int userId = StpUtil.getLoginIdAsInt();
        return getById(userId);
    }

    /**
     * 注册用户
     */
    @Transactional(rollbackFor = Exception.class)
    public int insert(String username, String openId, String birthday) {
        System.out.println(openId);
        System.out.println(username);
        System.out.println(birthday);
        if (exist(openId)) {
            throw new AppException(ErrorCode.USER_EXISTED);
        }
        if (!Pattern.matches(DATE, birthday)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }

        try {
//            User user=new User();
//            user.setUsername(username);
//            user.setOpenId(openId);
            User user = User.builder().username(username).openId(openId).build();
            System.out.println(user);
            userMapper.insert(user);
            int userId = user.getUserId();
            UserInfo userInfo = UserInfo.builder().userId(userId)
                    .birthday(birthday).build();
            userInfoMapper.insert(userInfo);
//            ledgerService.createDefault(user);
            return userId;
        } catch (Exception e) {
            // 处理异常
            System.err.println("Error occurred: " + e.getMessage());
            e.printStackTrace(); // 打印堆栈跟踪以进行调试
            throw e;
        }

    }
}
