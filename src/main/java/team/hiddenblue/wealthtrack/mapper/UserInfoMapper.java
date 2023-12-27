package team.hiddenblue.wealthtrack.mapper;

import org.apache.ibatis.annotations.*;
import team.hiddenblue.wealthtrack.pojo.User;
import team.hiddenblue.wealthtrack.pojo.UserInfo;

@Mapper
public interface UserInfoMapper {
    @Select("SELECT * FROM user_info WHERE user_id = #{userId}")
    UserInfo selectById(@Param("userId") int userId);

    @Update("<script>"
            + "UPDATE user_info"
            + "<set>"
            + "<if test='birthday != null'>birthday = #{birthday}</if>"
            + "</set>"
            + "WHERE user_id = #{userId}"
            + "</script>")
    void updateInfo(@Param("userId") int userId,
                    @Param("birthday") String birthday);

    @Insert("INSERT INTO user_info (birthday) VALUES (#{birthday})")
    @Options(useGeneratedKeys = true,keyColumn = "user_id",keyProperty = "userId")
    void insert(UserInfo userInfo);
}
