package team.hiddenblue.wealthtrack.mapper;

import org.apache.ibatis.annotations.*;
import team.hiddenblue.wealthtrack.dto.LedgerResult;
import team.hiddenblue.wealthtrack.pojo.Ledger;
import team.hiddenblue.wealthtrack.pojo.LedgerPermission;

@Mapper
public interface LedgerMapper {
    /**
     *向ledger表插入新行
     *向ledger_permission插入新行
     */
    @Insert("INSERT INTO ledger(name, password, is_public, owner_id, template)" +
            " VALUES(#{name}, #{password}, #{isPublic}, #{ownerId}, #{template})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    public Integer insertOne(Ledger ledger);
    @Insert("INSERT INTO ledger_permission(user_id, ledger_id)" +
            " VALUES(#{ownerId}, #{id})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    public Integer insertPermission(LedgerPermission ledgerPermission);
    @Select("SELECT id,name,owner_id,template FROM ledger WHERE id = #{id}")
    public LedgerResult selectByLedgerId(Integer id);
    @Update("UPDATE ledger SET name = #{name}, password = #{password}, ownerId = #{ownerId}, template = #{template} WHERE id = #{id}")
    public Integer update(Integer id, String name, String password, Boolean isPublic, Integer ownerId, String template);
    /**
     *删除ledger中的项后同样需要在ledger_permission表中删除项
     *向ledger_permission插入新行
     */
    @Delete("DELETE FROM ledger WHERE id = #{ledgerId}")
    public Boolean delLedger(Integer ledgerId);
    @Delete("DELETE FROM ledger_permission WHERE ledger_id = #{ledgerId}")
    public Boolean delPermission(Integer ledgerId);

}
