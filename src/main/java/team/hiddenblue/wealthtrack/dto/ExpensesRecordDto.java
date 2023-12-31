package team.hiddenblue.wealthtrack.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public class ExpensesRecordDto {
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
     * 账号
     */
    private Integer accountId;
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
     * 创建日期
     */
    private Date createDate;
    /**
     * 用户姓名
     */
    private String username;
}
