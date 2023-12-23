package team.hiddenblue.wealthtrack.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 用户性别
     */
    private String gender;
    /**
     * 用户生日
     */
    private String birthday;
}
