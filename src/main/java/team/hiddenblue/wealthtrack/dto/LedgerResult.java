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
