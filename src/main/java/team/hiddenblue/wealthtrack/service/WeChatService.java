package team.hiddenblue.wealthtrack.service;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import team.hiddenblue.wealthtrack.config.WechatConfig;
import team.hiddenblue.wealthtrack.constant.ErrorCode;
import team.hiddenblue.wealthtrack.exception.AppException;
import team.hiddenblue.wealthtrack.dto.Code2SessionDto;

/**
 * @author heoeh
 * @version 1.0
 */
@Service
public class WeChatService {

    public Code2SessionDto code2Session(String code) {
        System.out.println( WechatConfig.getAppid());
        String url = "https://api.weixin.qq.com/sns/jscode2session?" +
                "appid=" + WechatConfig.getAppid() +
                "&secret=" + WechatConfig.getSecret() +
                "&js_code=" + code +
                "&grant_type=authorization_code";
        String result = HttpUtil.get(url);
        JSONObject jsonObject = JSONObject.parseObject(result);
        Code2SessionDto code2Session = new Code2SessionDto()
                .setOpenId(jsonObject.getString("openid"))
                .setSessionKey(jsonObject.getString("session_key"))
                .setUnionId(jsonObject.getString("unionid"))
                .setErrCode(jsonObject.getInteger("errcode"))
                .setErrMsg(jsonObject.getString("errmsg"));
        if (!StringUtils.hasLength(code2Session.getOpenId()) ) {
            throw new AppException(ErrorCode.OPEN_ID_ERROR);
        }
        return code2Session;
    }
}
