package team.hiddenblue.wealthtrack.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import team.hiddenblue.wealthtrack.pojo.LedgerPermission;

@Mapper
public interface LedgerPermissionMapper {
    @Select("select * from ledger_permission where user_id = #{userId} and ledger_id = #{ledgerId}")
    public LedgerPermission getOne(Integer userId, Integer ledgerId);
}
