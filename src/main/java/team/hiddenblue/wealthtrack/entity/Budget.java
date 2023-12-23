package team.hiddenblue.wealthtrack.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;
@Data
@Builder
public class Budget {
    /**
     * 收支记录id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     * 收支类型
     */
    private Integer type;
    /**
     * 收支时间
     */
    private Date time;
    /**
     * 收支金额
     */
    private Double value;
}
