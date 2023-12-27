package team.hiddenblue.wealthtrack.service;



import team.hiddenblue.wealthtrack.pojo.UserInfo;



/**
 * @author heoeh
 * @version 1.2
 */
public interface UserInfoService {

    public UserInfo getById(int userId);
    public void updateInfo(int userId, String birthday);


}
