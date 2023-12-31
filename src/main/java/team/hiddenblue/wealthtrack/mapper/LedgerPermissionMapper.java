package team.hiddenblue.wealthtrack.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import team.hiddenblue.wealthtrack.dto.LedgerUsersResult;
import team.hiddenblue.wealthtrack.pojo.LedgerPermission;
import team.hiddenblue.wealthtrack.pojo.UserInfo;

import java.util.List;

@Mapper
public interface LedgerPermissionMapper {
    @Select("select * from ledger_permission where user_id = #{userId} and ledger_id = #{ledgerId}")
    public LedgerPermission getOne(Integer userId, Integer ledgerId);
    @Select("select * from ledger_permission where user_id = #{userId}")
    public List<LedgerPermission> selectByUser(Integer userId);

    /**
     * 根据账本id返回用户信息
     * @param ledgerId
     * @return
     */
    @Select("SELECT u.username, ui.avatar " +
            "FROM user u " +
            "JOIN ledger_permission l ON u.user_id = l.user_id " +
            "JOIN user_info ui ON u.user_id = ui.user_id " +
            "WHERE l.ledger_id = #{ledgerId};")
    List<LedgerUsersResult> getAllUsersByLedgerId(int ledgerId);
}
