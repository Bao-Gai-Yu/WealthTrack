package team.hiddenblue.wealthtrack.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 设置合合信息开发者信息(合合信息官网注册账号)
 */
@Component
public class TextInConfig {

    /**
     * 合合信息账号appID
     */
    @Value("${app.x-ti-app-id}")
    private String appId;

    /**
     * 合合信息账号的secretCode
     */
    @Value("${app.x-ti-secret-code}")
    private String secretCode;

    public String getAppId() {
        return appId;
    }



    public  String getSecretCode() {
        return secretCode;
    }


}
