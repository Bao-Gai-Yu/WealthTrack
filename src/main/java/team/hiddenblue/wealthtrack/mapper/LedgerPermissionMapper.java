package team.hiddenblue.wealthtrack.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import team.hiddenblue.wealthtrack.pojo.LedgerPermission;

import java.util.List;

@Mapper
public interface LedgerPermissionMapper {
    @Select("select * from ledger_permission where user_id = #{userId} and ledger_id = #{ledgerId}")
    public LedgerPermission getOne(Integer userId, Integer ledgerId);
    @Select("select * from ledger_permission where user_id = #{userId}")
    public List<LedgerPermission> selectByUser(Integer userId);

}
