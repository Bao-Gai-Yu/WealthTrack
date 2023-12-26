package team.hiddenblue.wealthtrack.constant;

import lombok.Getter;

@Getter
public enum ResponseCode {
    /**
     * 成功
     */
    SUCCESS(200),
    /**
     * 失败
     */
    FAIL(400),
    /**
     * 无权限
     */
    UN_AUTH(401),
    /**
     * 禁止
     */
    FORBIDDEN(403),
    /**
     * 找不到资源
     */
    NOT_FOUND(404),
    SERVER_ERROR(500);

    private final Integer code;

    ResponseCode(Integer code) {
        this.code = code;
    }
}
