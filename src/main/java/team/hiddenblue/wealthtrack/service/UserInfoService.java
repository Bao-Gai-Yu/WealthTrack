package team.hiddenblue.wealthtrack.service;
import team.hiddenblue.wealthtrack.pojo.UserInfo;


public interface UserInfoService {

    public UserInfo getById(int userId);
    public int updateInfo(int userId, String birthday);
}
