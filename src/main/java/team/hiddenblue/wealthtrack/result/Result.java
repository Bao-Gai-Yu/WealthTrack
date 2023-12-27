package team.hiddenblue.wealthtrack.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import team.hiddenblue.wealthtrack.constant.ResponseCode;


/**
 * @author patrick_star
 * @version 1.0
 */
@Data
@AllArgsConstructor
public class Result<T> {
    private Integer code;
    private String msg;
    private T data;

    public static <N> Result<N> SUCCESS() { return new Result<>(ResponseCode.SUCCESS.getCode(), "success",null); }

    public static <N> Result<N> SUCCESS(N data) { return new Result<>(ResponseCode.SUCCESS.getCode(), "success", data); }

    public static <N> Result<N> SUCCESS(String msg, N data) { return new Result<>(ResponseCode.SUCCESS.getCode(), msg, data); }

    public static <N> Result<N> FAIL() { return new Result<>(ResponseCode.FAIL.getCode(), "fail", null); }

    public static <N> Result<N> FAIL(N data) { return new Result<>(ResponseCode.FAIL.getCode(), "fail", data); }

    public static <N> Result<N> FAIL(String msg, N data) { return new Result<>(ResponseCode.FAIL.getCode(), msg, data); }
}
