package team.hiddenblue.wealthtrack.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import team.hiddenblue.wealthtrack.constant.ResponseCode;

/**
 *  返回给前端的数据
 */
@Data
@AllArgsConstructor
public class Result<T> {
    /**
     * 响应信息
     */
    private String msg;
    /**
     * 响应码
     */
    private Integer code;
    /**
     * 响应数据
     */
    private T data;

    public static <T> Result<T> SUCCESS(){return new Result("SUCCESS",ResponseCode.SUCCESS.getCode(),null);}
    public static <T> Result<T> SUCCESS(T data){return new Result("SUCCESS",ResponseCode.SUCCESS.getCode(),data);}
    public static <T> Result<T> SUCCESS(String msg){return new Result(msg,ResponseCode.SUCCESS.getCode(),null);}
    public static <T> Result<T> SUCCESS(String msg, T data){return new Result(msg,ResponseCode.SUCCESS.getCode(),data);}
    public static <T> Result<T> FAIL(){return new Result("FAIL",ResponseCode.FAIL.getCode(),null);}
    public static <T> Result<T> FAIL(T data){return new Result("FAIL",ResponseCode.FAIL.getCode(),data);}
    public static <T> Result<T> FAIL(String msg){return new Result(msg,ResponseCode.FAIL.getCode(),null);}
    public static <T> Result<T> FAIL(String msg, T data){return new Result(msg,ResponseCode.FAIL.getCode(),data);}
    public static <T> Result<T> UN_AUTH(){return new Result("UN_AUTH",ResponseCode.UN_AUTH.getCode(),null);}
    public static <T> Result<T> UN_AUTH(T data){return new Result("UN_AUTH",ResponseCode.UN_AUTH.getCode(),data);}
    public static <T> Result<T> UN_AUTH(String msg){return new Result(msg,ResponseCode.UN_AUTH.getCode(),null);}
    public static <T> Result<T> UN_AUTH(String msg, T data){return new Result(msg,ResponseCode.UN_AUTH.getCode(),data);}
    public static <T> Result<T> FORBIDDEN(){return new Result("FORBIDDEN",ResponseCode.FORBIDDEN.getCode(),null);}
    public static <T> Result<T> FORBIDDEN(T data){return new Result("FORBIDDEN",ResponseCode.FORBIDDEN.getCode(),data);}
    public static <T> Result<T> FORBIDDEN(String msg){return new Result(msg,ResponseCode.FORBIDDEN.getCode(),null);}
    public static <T> Result<T> FORBIDDEN(String msg, T data){return new Result(msg,ResponseCode.FORBIDDEN.getCode(),data);}
    public static <T> Result<T> NOT_FOUND(){return new Result("NOT_FOUND",ResponseCode.NOT_FOUND.getCode(),null);}
    public static <T> Result<T> NOT_FOUND(T data){return new Result("NOT_FOUND",ResponseCode.NOT_FOUND.getCode(),data);}
    public static <T> Result<T> NOT_FOUND(String msg){return new Result(msg,ResponseCode.NOT_FOUND.getCode(),null);}
    public static <T> Result<T> NOT_FOUND(String msg, T data){return new Result(msg,ResponseCode.NOT_FOUND.getCode(),data);}
    public static <T> Result<T> SERVER_ERROR(){return new Result("SERVER_ERROR",ResponseCode.SERVER_ERROR.getCode(),null);}
    public static <T> Result<T> SERVER_ERROR(T data){return new Result("SERVER_ERROR",ResponseCode.SERVER_ERROR.getCode(),data);}
    public static <T> Result<T> SERVER_ERROR(String msg){return new Result(msg,ResponseCode.SERVER_ERROR.getCode(),null);}
    public static <T> Result<T> SERVER_ERROR(String msg, T data){return new Result(msg,ResponseCode.SERVER_ERROR.getCode(),data);}
}
