package team.hiddenblue.wealthtrack.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 微信小程序的openId
     */
    private String openId;
}
