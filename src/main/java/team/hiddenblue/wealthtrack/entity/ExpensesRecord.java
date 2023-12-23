package team.hiddenblue.wealthtrack.entity;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class ExpensesRecord {
    /**
     * 消费记录的ID
     */
    private Integer id;
    /**
     * 用户ID
     */
    private Integer userId;
    /**
     * 账本id
     */
    private Integer ledgerId;
    /**
     * 账户id
     */
    private Integer AccountId;
    /**
     * 消费金额
     */
    private Double value;
    /**
     * 收支类型(0 - 收入; 1 - 支出)
     */
    private Boolean type;
    /**
     * 消费种类
     */
    private String kind;
    /**
     * 消费记录的备注
     */
    private String remark;
    /**
     * 消费日期
     */
    private Date date;
    /**
     * 记录创建日期
     */
    private Date createDate;

}
