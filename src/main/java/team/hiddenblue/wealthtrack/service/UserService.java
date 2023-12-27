package team.hiddenblue.wealthtrack.service;

import team.hiddenblue.wealthtrack.pojo.User;

public interface UserService {
    public User getById(Integer userId);
    public User getByOpenId(String openId);
    public boolean exist(String openId);
    public User getLoginUser();
    public int insert(String username, String openId, String birthday);
}
