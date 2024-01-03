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
            " VALUES(#{userId}, #{ledgerId})")
    @Options(useGeneratedKeys = true,keyColumn = "id",keyProperty = "id")
    public Integer insertPermission(LedgerPermission ledgerPermission);
    @Select("SELECT id, name, is_public, username, template FROM ledger,user WHERE id = #{id} AND owner_id = user_id")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "name", column = "name"),
            @Result(property = "isPublic", column = "is_public"),
            @Result(property = "owner", column = "username"),
            @Result(property = "template", column = "template")
    })
    public LedgerResult selectByLedgerId(Integer id);

    @Select("SELECT user_id FROM user WHERE username = #{username}")
    public Integer getOwnerId(String username);

    @Select("SELECT owner_id FROM ledger WHERE id = #{id}")
    public Integer selectOwnerId(Integer id);

    @Select("SELECT owner_id FROM ledger WHERE id = #{ledgerId}")
    public Integer getLedgerOwner(Integer ledgerId);
    @Update("UPDATE ledger SET name = #{name}, is_public = #{isPublic},owner_id = #{ownerId},password = #{password} WHERE id = #{id}")
    public Integer update(Integer id, String name, Boolean isPublic, Integer ownerId, String template, String password);

    /**
     *删除ledger中的项后同样需要在ledger_permission表中删除项
     *向ledger_permission插入新行
     */
    @Delete("DELETE FROM ledger WHERE id = #{ledgerId}")
    public Boolean delLedger(Integer ledgerId);
    @Delete("DELETE FROM ledger_permission WHERE ledger_id = #{ledgerId}")
    public Boolean delPermission(Integer ledgerId);
    @Delete("DELETE FROM expenses_record WHERE ledger_id = #{ledgerId}")
    public Boolean delExpenses(Integer ledgerId);

    /**
     * 通过账本密钥查找账本
     * @param password
     * @return
     */
    @Select("select * from ledger where password = #{password}")
    Ledger findByPassword(String password);

    @Select("select password from ledger where id = #{ledgerId}")
    public String query(Integer ledgerId);

    @Select("select * from ledger where id=#{ledgerId}")
    Ledger findLedger(Integer ledgerId);

}
