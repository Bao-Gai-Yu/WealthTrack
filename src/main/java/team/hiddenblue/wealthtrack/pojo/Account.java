package team.hiddenblue.wealthtrack.pojo;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Account {
    /**
     * 账户id
     */
    private Integer id;
    /**
     * 用户id
     */
    private Integer userId;
    /**
     *账户名称
     */
    private String name;
}
