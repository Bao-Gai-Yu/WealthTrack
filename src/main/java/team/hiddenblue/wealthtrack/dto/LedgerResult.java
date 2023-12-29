package team.hiddenblue.wealthtrack.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LedgerResult {
    /**
     * 账本id
     */
    private Integer id;
    /**
     * 账本名称
     */
    private String name;
    /**
     * 账本所有者的用户id
     */
    private Integer ownerId;
    /**
     * 账本创建所用的模板
     */
    private String template;
}
