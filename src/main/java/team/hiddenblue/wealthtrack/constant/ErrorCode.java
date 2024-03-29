package team.hiddenblue.wealthtrack.constant;

import lombok.Getter;

/**
 * @author heoeh
 * @Desc 错误类型枚举类
 * @version 1.0
 */

@Getter
public enum ErrorCode {
    /** 身份认证失败 */
    NOT_LOGIN(200100, "未登陆"),
    /** 用户已存在 */
    USER_EXISTED(200101, "用户已存在"),
    /** 用户不存在 */
    USER_NOT_EXISTED(200102, "用户不存在"),

    /** 服务器异常 */
    SERVER_ERROR(200300, "服务器异常"),
    /** OpenID异常 */
    OPEN_ID_ERROR(200301, "OpenID异常"),
    /** 参数有误 */
    PARAM_ERROR(200302, "参数有误"),
    /** AccessToken异常 */
    ACCESS_TOKEN_ERROR(200303, "AccessToken异常"),
    /** 图片类型不支持 */
    IMAGE_TYPE_ERROR(200304, "图片类型不支持"),

    /** 上传文件大小不符 */
    IMAGE_SIZE_ERROR(200305, "上传文件大小不符，文件大小不超过 10M"),
    /** 文件类型不支持 */
    FILE_TYPE_ERROR(200306, "文件类型不支持"),
    /** 图片尺寸不符 */
    IMAGE_MEASUREMENT_ERROR(200307, "图片尺寸不符，图像宽高须介于 20 和 10000（像素）之间"),
    /** 公开账本没有设置密码 */
    LEDGER_PUBLIC_WITHOUT_PASSWORD_ERROR(200308, "公开账本必须要设置密码"),
    /** 该账本不存在或无权限操作 */
    LEDGER_PERMISSION_ERROR(200309, "该账本不存在或无权限操作"),
    /** 该账本不存在或密码错误 */
    LEDGER_NOT_PUBLIC_OR_NOT_EXISTS_ERROR(200310, "该账本是私密的或不存在"),
    /** 账本密码错误 */
    LEDGER_PASSWORD_ERROR(200311, "账本密码错误"),
    /** 已经加入该账本 */
    LEDGER_HAS_JOINED_ERROR(200311, "已经加入该账本"),

    /** 空图片 */
    EMPTY_PHOTO(200312,"图片为空"),

    /** 非法请求 */
    ILLEGAL_REQUEST(200404, "非法请求"),


    ;

    private final Integer code;
    private final String message;

    ErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
