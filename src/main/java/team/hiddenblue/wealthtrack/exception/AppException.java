package team.hiddenblue.wealthtrack.exception;

import lombok.Getter;
import team.hiddenblue.wealthtrack.constant.ErrorCode;

@Getter
public class AppException extends RuntimeException{
    private final ErrorCode code;
    private final Object data;


    public AppException(ErrorCode code) {
        this.code = code;
        this.data = null;
    }

    public AppException(ErrorCode code, Object data) {
        this.code = code;
        this.data = data;
    }
}
