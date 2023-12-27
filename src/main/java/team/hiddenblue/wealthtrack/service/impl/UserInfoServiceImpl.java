package team.hiddenblue.wealthtrack.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import team.hiddenblue.wealthtrack.constant.ErrorCode;
import team.hiddenblue.wealthtrack.exception.AppException;
import team.hiddenblue.wealthtrack.mapper.UserInfoMapper;
import team.hiddenblue.wealthtrack.pojo.UserInfo;
import team.hiddenblue.wealthtrack.service.UserInfoService;


import java.util.regex.Pattern;

/**
 * @author heoeh
 * @version 1.2
 */
@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "ExpireOneMin")
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoMapper userInfoMapper;
    public static final String DATE = "^(?:(?!0000)[0-9]{4}([-/.]?)(?:(?:0?[1-9]|1[0-2])\\1(?:0?[1-9]|1[0-9]|2[0-8])|(?:0?[13-9]|1[0-2])\\1(?:29|30)|(?:0?[13578]|1[02])\\1(?:31))|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)([-/.]?)0?2\\2(?:29))$";
    /**
     * 通过 ID 查询用户信息
     */
    @Cacheable(value = "ExpireOneMin", key = "'user_info_'+#userId")
    public UserInfo getById(int userId) {
        return userInfoMapper.selectById(userId);
    }

    /**
     * 更新用户信息
     */
    @CacheEvict(value = "NoExpire", key = "'user_info_'+#userId")
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(int userId, String birthday) {
        if (birthday != null && !Pattern.matches(DATE, birthday)) {
            throw new AppException(ErrorCode.PARAM_ERROR);
        }
        userInfoMapper.updateInfo(userId, birthday);
    }
}
