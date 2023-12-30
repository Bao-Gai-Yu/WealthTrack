package team.hiddenblue.wealthtrack.mapper;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.session.RowBounds;
import team.hiddenblue.wealthtrack.pojo.ExpensesRecord;

import java.util.Date;
import java.util.List;

@Mapper
public interface ExpensesRecordMapper {
    @Select("SELECT * FROM expenses_record WHERE user_id = #{userId}" +
            " AND ledger_id = #{ledgerId}" +
            " AND date >= #{startTime}" +
            " AND date <= #{endTime}")
    public List<ExpensesRecord> getPagedByTimeZone(RowBounds rowBounds, Integer userId, Integer ledgerId, Date startTime, Date endTime);

    @Select({"SELECT * FROM expenses_record WHERE user_id = #{userId}",
            " AND ledger_id = #{ledgerId}",
            " AND type = #{type}",
            " AND date >= #{startTime}",
            " AND date < #{endTime}"})
    public List<ExpensesRecord> getPagedByTimeZoneAndType(RowBounds rowBounds, Integer userId, Integer ledgerId, Boolean type, Date startTime, Date endTime);

    @Insert("INSERT INTO expenses_record(user_id, ledger_id, value, type, kind, remark, date, create_date)" +
            "VALUES(#{userId}, #{ledgerId}, #{value}, #{type}, #{kind}, #{remark}, #{date}, NOW())")
    public Integer insert(Integer userId, Integer ledgerId, Double value, Boolean type, String kind, String remark, Date date);

    @Select("SELECT COUNT(*) FROM expenses_record WHERE id = #{id} AND user_id = #{userId}")
    public Long selectCount(Integer id, Integer userId);

    @Delete("DELETE FROM expenses_record WHERE id = #{id}")
    public Boolean delete(Integer id);

    @Update("UPDATE expenses_record SET value = #{value}, type = #{type}, kind = #{kind}, date = #{date}, remark = #{remark} WHERE id = #{id}")
    public int update(Integer id, Double value, Boolean type, String kind, Date date, String remark);


}
