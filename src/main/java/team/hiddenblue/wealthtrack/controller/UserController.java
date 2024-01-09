package team.hiddenblue.wealthtrack.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team.hiddenblue.wealthtrack.constant.ErrorCode;
import team.hiddenblue.wealthtrack.exception.AppException;
import team.hiddenblue.wealthtrack.pojo.User;
import team.hiddenblue.wealthtrack.pojo.UserInfo;
import team.hiddenblue.wealthtrack.dto.Result;
import team.hiddenblue.wealthtrack.service.UserInfoService;
import team.hiddenblue.wealthtrack.service.UserService;
import team.hiddenblue.wealthtrack.service.WeChatService;


import java.util.Map;

/**
 * @author heoeh
 * @version 1.1
 */
@Validated
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    final UserService userService;

    final UserInfoService userInfoService;

    final WeChatService weChatService;

    /**
     * 微信登录接口
     *
     * @param code 参数形式传入的用户code
     * @return json数据，包含状态码和状态信息
     */
    @ResponseBody
    @GetMapping("/login/{code}")
    public Object loginByWechat(@PathVariable String code) {
        String openId = weChatService.code2Session(code).getOpenId();
        System.out.println(openId);
        User user = userService.getByOpenId(openId);
        System.out.println(user);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        StpUtil.logout(user.getUserId());
        StpUtil.login(user.getUserId());
        UserInfo userInfo = userInfoService.getById(user.getUserId());
        return Result.SUCCESS(Map.of("username", user.getUsername(),
                "birthday", userInfo.getBirthday()));
    }


    /**
     * 微信登录接口
     *
     * @param openId 参数形式传入的用户openId
     * @return json数据，包含状态码和状态信息
     */
    @ResponseBody
    @GetMapping("/loginByOpenId/{openId}")
    public Object loginByOpenId(@PathVariable String openId) {
        User user = userService.getByOpenId(openId);
        System.out.println(user);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        StpUtil.logout(user.getUserId());
        StpUtil.login(user.getUserId());
        UserInfo userInfo = userInfoService.getById(user.getUserId());
        return Result.SUCCESS(Map.of("username", user.getUsername(),
                "birthday", userInfo.getBirthday()));
    }

    /**
     * 微信注册接口
     *
     * @param code     参数形式传入的微信小程序code
     * @param username 参数形式传入的用户名
     * @return json数据，包含状态码和状态信息
     */
    @ResponseBody
    @GetMapping("/register")
    public Object registerByWechat(
            @RequestParam(value = "code") String code,
            @RequestParam(value = "username") String username,
            @RequestParam(value = "birthday") String birthday) {
        String openId = weChatService.code2Session(code).getOpenId();
        System.out.println(openId);
        int userId = userService.insert(username, openId, birthday);
        System.out.println(userId);
        StpUtil.login(userId);
        return Result.SUCCESS();
    }

    /**
     * 测试登录接口
     *
     * @param uid 参数形式传入的用户id
     * @return json数据，包含状态码和状态信息
     */
    @ResponseBody
    @GetMapping("/test/login/{uid}")
    public Object login(@PathVariable Integer uid) {
        User user = userService.getById(uid);
        if (user == null) {
            throw new AppException(ErrorCode.USER_NOT_EXISTED);
        }
        StpUtil.logout(uid);
        StpUtil.login(uid);
        UserInfo userInfo = userInfoService.getById(uid);
        return Result.SUCCESS(Map.of("username", user.getUsername(),
                "birthday", userInfo.getBirthday()));
    }


    /**
     * 用户修改生日接口
     *
     * @param birthday 生日
     * @return json数据，包含状态码和状态信息
     */
    @GetMapping("/change/birthday")
    public Object modifyBirthday(@RequestParam String birthday) {
        int userId = StpUtil.getLoginIdAsInt();
        System.out.println(userId);
        userInfoService.updateInfo(userId,  birthday);
        return Result.SUCCESS();
    }

    /**
     * 获取用户头像
     * @return
     */
    @GetMapping("/avatar")
    public Object getUserAvatar() {
        // 使用SaToken获取当前登录的用户ID
        int userId = StpUtil.getLoginIdAsInt();
        System.out.println(userId);
        UserInfo userInfo = userInfoService.getById(userId);
        String avatarUrl = userInfo.getAvatar();
        if(avatarUrl != null){
            return Result.SUCCESS("获取头像成功",avatarUrl);
        }
        else{
            return Result.FAIL("获取头像失败");
        }

    }
}
