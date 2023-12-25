package team.hiddenblue.wealthtrack.config;

import lombok.Data;
import org.springframework.stereotype.Component;

/**
 * 设置合合信息开发者信息(合合信息官网注册账号)
 */
@Component
public class TextInConfig {
    /**
     * 合合信息账号appID
     */

    private static String appId;
    /**
     * 合合信息账号的secretCode
     */
    private static String secretCode;

    public static String getAppId() {
        return appId;
    }

    public static void setAppId(String appId) {
        TextInConfig.appId = appId;
    }

    public static String getSecretCode() {
        return secretCode;
    }

    public static void setSecretCode(String secretCode) {
        TextInConfig.secretCode = secretCode;
    }
}
