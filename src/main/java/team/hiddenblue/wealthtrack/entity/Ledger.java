package team.hiddenblue.wealthtrack.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Ledger {
    /**
     * 账本id
     */
    private Integer id;
    /**
     * 账本名称
     */
    private String name;
    /**
     * 账本密码
     */
    private String password;
    /**
     * 账本可见性
     * true - 公开
     * false - 私密
     */
    private Boolean isPublic;
    /**
     * 账本所有者的用户id
     */
    private Integer ownerId;
    /**
     * 账本创建所用的模板
     */
    private String template;
}
