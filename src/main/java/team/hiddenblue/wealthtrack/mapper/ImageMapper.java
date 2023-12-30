package team.hiddenblue.wealthtrack.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ImageMapper {

    @Update("update user_info set  avatar = #{imageUrl} where user_id = #{userId}")
    public void insertHeadImage(String imageUrl, int userId);
}
