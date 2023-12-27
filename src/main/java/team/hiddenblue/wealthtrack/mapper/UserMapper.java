package team.hiddenblue.wealthtrack.mapper;


import org.apache.ibatis.annotations.*;
import team.hiddenblue.wealthtrack.pojo.User;
import team.hiddenblue.wealthtrack.pojo.UserInfo;


/**
 * @author heoeh
 * @version 1.0
 */
@Mapper
public interface UserMapper {

        @Select("SELECT * FROM user WHERE user_id = #{userId}")
        User selectById(@Param("userId") Integer userId);

        @Select("SELECT * FROM user WHERE open_id = #{openId}")
        User selectByOpenId(@Param("openId") String openId);

        @Select("SELECT COUNT(*) FROM user WHERE open_id = #{openId}")
        Long exist(@Param("openId") String openId);

        @Insert("INSERT INTO user (username, open_id) VALUES (#{username}, #{openId})")
        @Options(useGeneratedKeys = true,keyColumn = "user_id",keyProperty = "userId")
        void insert(User user);


    }


