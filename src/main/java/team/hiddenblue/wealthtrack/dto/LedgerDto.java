package team.hiddenblue.wealthtrack.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@Builder
public class LedgerDto {
    /**
     * 账本id
     */
    private int id;
    /**
     * 账本名称
     */
    private String name;
    /**
     * 账本可见性
     * true - 公开
     * false - 私密
     */
    private Boolean isPublic;
    /**
     * 账本所有者用户名
     */
    private String owner;
    /**
     * 账本创建所用的模板
     */
    private String template;
}
