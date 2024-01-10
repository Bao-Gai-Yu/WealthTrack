package team.hiddenblue.wealthtrack.pojo;

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
     * 用户生日
     */
    private String birthday;

    /**
     * 头像
     */
    private String avatar;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
