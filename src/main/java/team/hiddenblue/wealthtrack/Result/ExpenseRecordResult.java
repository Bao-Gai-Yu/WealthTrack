package team.hiddenblue.wealthtrack.Result;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ExpenseRecordResult {
    /**
     * 记录id
     */
    Integer id;

    /**
     * 0表示收入，1表示支出
     */
    @NotNull(message="收支类型不能为空值")
    Boolean type;

    /**
     * 消费金额
     */
    @NotNull(message="消费金额不能为空值")
    String value;

    /**
     * 消费类型
     */
    @NotNull(message="消费类型不能为空值")
    String kind;

    /**
     * 消费备注
     */
    String remark;

    /**
     * 消费日期
     */
    @NotNull(message="消费日期不能为空值")
    String date;

    /**
     * 消费账本id
     */
    @NotNull(message="账本id不能为空值")
    Integer ledgerId;
}
