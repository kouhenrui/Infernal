package com.example.infernal.util.exception;

import com.example.infernal.util.R.RCode;
import lombok.Data;

/**
 * @auther khr
 * @date 2022/8/15 11:19
 */
@Data
public class BusinessException extends RuntimeException {

    private Integer code;

    private String message;

    public BusinessException(int code, String message) {

        this.code=code;
        this.message=message;
    }
    public BusinessException( String message) {
        this.message=message;
    }

    public BusinessException(RCode rc) {
        this.code= rc.code();
        this.message= rc.message();
    }
}
