package team.hiddenblue.wealthtrack.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LedgerPermission {
    /**
     * 账本使用记录
     */
    private Integer id;
    /**
     * 使用账本的用户id
     */
    private Integer userId;
    /**
     * 被使用的账本id
     */
    private Integer ledgerId;
}
